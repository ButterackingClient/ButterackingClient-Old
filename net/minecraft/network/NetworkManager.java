/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.SimpleChannelInboundHandler;
/*     */ import io.netty.channel.epoll.Epoll;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollSocketChannel;
/*     */ import io.netty.channel.local.LocalChannel;
/*     */ import io.netty.channel.local.LocalEventLoopGroup;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioSocketChannel;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.LazyLoadBase;
/*     */ import net.minecraft.util.MessageDeserializer;
/*     */ import net.minecraft.util.MessageDeserializer2;
/*     */ import net.minecraft.util.MessageSerializer;
/*     */ import net.minecraft.util.MessageSerializer2;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.MarkerManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkManager
/*     */   extends SimpleChannelInboundHandler<Packet>
/*     */ {
/*  55 */   private static final Logger logger = LogManager.getLogger();
/*  56 */   public static final Marker logMarkerNetwork = MarkerManager.getMarker("NETWORK");
/*  57 */   public static final Marker logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", logMarkerNetwork);
/*  58 */   public static final AttributeKey<EnumConnectionState> attrKeyConnectionState = AttributeKey.valueOf("protocol");
/*  59 */   public static final LazyLoadBase<NioEventLoopGroup> CLIENT_NIO_EVENTLOOP = new LazyLoadBase<NioEventLoopGroup>() {
/*     */       protected NioEventLoopGroup load() {
/*  61 */         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  64 */   public static final LazyLoadBase<EpollEventLoopGroup> CLIENT_EPOLL_EVENTLOOP = new LazyLoadBase<EpollEventLoopGroup>() {
/*     */       protected EpollEventLoopGroup load() {
/*  66 */         return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  69 */   public static final LazyLoadBase<LocalEventLoopGroup> CLIENT_LOCAL_EVENTLOOP = new LazyLoadBase<LocalEventLoopGroup>() {
/*     */       protected LocalEventLoopGroup load() {
/*  71 */         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*     */   private final EnumPacketDirection direction;
/*  75 */   private final Queue<InboundHandlerTuplePacketListener> outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
/*  76 */   private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
/*     */ 
/*     */ 
/*     */   
/*     */   private Channel channel;
/*     */ 
/*     */ 
/*     */   
/*     */   private SocketAddress socketAddress;
/*     */ 
/*     */   
/*     */   private INetHandler packetListener;
/*     */ 
/*     */   
/*     */   private IChatComponent terminationReason;
/*     */ 
/*     */   
/*     */   private boolean isEncrypted;
/*     */ 
/*     */   
/*     */   private boolean disconnected;
/*     */ 
/*     */ 
/*     */   
/*     */   public NetworkManager(EnumPacketDirection packetDirection) {
/* 101 */     this.direction = packetDirection;
/*     */   }
/*     */   
/*     */   public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception {
/* 105 */     super.channelActive(p_channelActive_1_);
/* 106 */     this.channel = p_channelActive_1_.channel();
/* 107 */     this.socketAddress = this.channel.remoteAddress();
/*     */     
/*     */     try {
/* 110 */       setConnectionState(EnumConnectionState.HANDSHAKING);
/* 111 */     } catch (Throwable throwable) {
/* 112 */       logger.fatal(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnectionState(EnumConnectionState newState) {
/* 120 */     this.channel.attr(attrKeyConnectionState).set(newState);
/* 121 */     this.channel.config().setAutoRead(true);
/* 122 */     logger.debug("Enabled auto read");
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext p_channelInactive_1_) throws Exception {
/* 126 */     closeChannel((IChatComponent)new ChatComponentTranslation("disconnect.endOfStream", new Object[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) throws Exception {
/*     */     ChatComponentTranslation chatcomponenttranslation;
/* 132 */     if (p_exceptionCaught_2_ instanceof io.netty.handler.timeout.TimeoutException) {
/* 133 */       chatcomponenttranslation = new ChatComponentTranslation("disconnect.timeout", new Object[0]);
/*     */     } else {
/* 135 */       chatcomponenttranslation = new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Internal Exception: " + p_exceptionCaught_2_ });
/*     */     } 
/*     */     
/* 138 */     closeChannel((IChatComponent)chatcomponenttranslation);
/*     */   }
/*     */   
/*     */   protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet<INetHandler> p_channelRead0_2_) throws Exception {
/* 142 */     if (this.channel.isOpen()) {
/*     */       try {
/* 144 */         p_channelRead0_2_.processPacket(this.packetListener);
/* 145 */       } catch (ThreadQuickExitException threadQuickExitException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNetHandler(INetHandler handler) {
/* 156 */     Validate.notNull(handler, "packetListener", new Object[0]);
/* 157 */     logger.debug("Set listener of {} to {}", new Object[] { this, handler });
/* 158 */     this.packetListener = handler;
/*     */   }
/*     */   
/*     */   public void sendPacket(Packet packetIn) {
/* 162 */     if (isChannelOpen()) {
/* 163 */       flushOutboundQueue();
/* 164 */       dispatchPacket(packetIn, null);
/*     */     } else {
/* 166 */       this.readWriteLock.writeLock().lock();
/*     */       
/*     */       try {
/* 169 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, null));
/*     */       } finally {
/* 171 */         this.readWriteLock.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendPacket(Packet packetIn, GenericFutureListener<? extends Future<? super Void>> listener, GenericFutureListener... listeners) {
/* 177 */     if (isChannelOpen()) {
/* 178 */       flushOutboundQueue();
/* 179 */       dispatchPacket(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add((Object[])listeners, 0, listener));
/*     */     } else {
/* 181 */       this.readWriteLock.writeLock().lock();
/*     */       
/*     */       try {
/* 184 */         this.outboundPacketsQueue.add(new InboundHandlerTuplePacketListener(packetIn, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add((Object[])listeners, 0, listener)));
/*     */       } finally {
/* 186 */         this.readWriteLock.writeLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dispatchPacket(final Packet inPacket, final GenericFutureListener[] futureListeners) {
/* 196 */     final EnumConnectionState enumconnectionstate = EnumConnectionState.getFromPacket(inPacket);
/* 197 */     final EnumConnectionState enumconnectionstate1 = (EnumConnectionState)this.channel.attr(attrKeyConnectionState).get();
/*     */     
/* 199 */     if (enumconnectionstate1 != enumconnectionstate) {
/* 200 */       logger.debug("Disabled auto read");
/* 201 */       this.channel.config().setAutoRead(false);
/*     */     } 
/*     */     
/* 204 */     if (this.channel.eventLoop().inEventLoop()) {
/* 205 */       if (enumconnectionstate != enumconnectionstate1) {
/* 206 */         setConnectionState(enumconnectionstate);
/*     */       }
/*     */       
/* 209 */       ChannelFuture channelfuture = this.channel.writeAndFlush(inPacket);
/*     */       
/* 211 */       if (futureListeners != null) {
/* 212 */         channelfuture.addListeners(futureListeners);
/*     */       }
/*     */       
/* 215 */       channelfuture.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */     } else {
/* 217 */       this.channel.eventLoop().execute(new Runnable() {
/*     */             public void run() {
/* 219 */               if (enumconnectionstate != enumconnectionstate1) {
/* 220 */                 NetworkManager.this.setConnectionState(enumconnectionstate);
/*     */               }
/*     */               
/* 223 */               ChannelFuture channelfuture1 = NetworkManager.this.channel.writeAndFlush(inPacket);
/*     */               
/* 225 */               if (futureListeners != null) {
/* 226 */                 channelfuture1.addListeners(futureListeners);
/*     */               }
/*     */               
/* 229 */               channelfuture1.addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void flushOutboundQueue() {
/* 239 */     if (this.channel != null && this.channel.isOpen()) {
/* 240 */       this.readWriteLock.readLock().lock();
/*     */       
/*     */       try {
/* 243 */         while (!this.outboundPacketsQueue.isEmpty()) {
/* 244 */           InboundHandlerTuplePacketListener networkmanager$inboundhandlertuplepacketlistener = this.outboundPacketsQueue.poll();
/* 245 */           dispatchPacket(networkmanager$inboundhandlertuplepacketlistener.packet, networkmanager$inboundhandlertuplepacketlistener.futureListeners);
/*     */         } 
/*     */       } finally {
/* 248 */         this.readWriteLock.readLock().unlock();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processReceivedPackets() {
/* 257 */     flushOutboundQueue();
/*     */     
/* 259 */     if (this.packetListener instanceof ITickable) {
/* 260 */       ((ITickable)this.packetListener).update();
/*     */     }
/*     */     
/* 263 */     this.channel.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress getRemoteAddress() {
/* 270 */     return this.socketAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeChannel(IChatComponent message) {
/* 277 */     if (this.channel.isOpen()) {
/* 278 */       this.channel.close().awaitUninterruptibly();
/* 279 */       this.terminationReason = message;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocalChannel() {
/* 288 */     return !(!(this.channel instanceof LocalChannel) && !(this.channel instanceof io.netty.channel.local.LocalServerChannel));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetworkManager createNetworkManagerAndConnect(InetAddress address, int serverPort, boolean useNativeTransport) {
/*     */     Class<NioSocketChannel> clazz;
/*     */     LazyLoadBase<NioEventLoopGroup> lazyLoadBase;
/* 299 */     final NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
/*     */ 
/*     */ 
/*     */     
/* 303 */     if (Epoll.isAvailable() && useNativeTransport) {
/* 304 */       Class<EpollSocketChannel> clazz1 = EpollSocketChannel.class;
/* 305 */       LazyLoadBase<EpollEventLoopGroup> lazyLoadBase1 = CLIENT_EPOLL_EVENTLOOP;
/*     */     } else {
/* 307 */       clazz = NioSocketChannel.class;
/* 308 */       lazyLoadBase = CLIENT_NIO_EVENTLOOP;
/*     */     } 
/*     */     
/* 311 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)lazyLoadBase.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>() {
/*     */           protected void initChannel(Channel p_initChannel_1_) throws Exception {
/*     */             try {
/* 314 */               p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/* 315 */             } catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */             
/* 319 */             p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(EnumPacketDirection.CLIENTBOUND)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(EnumPacketDirection.SERVERBOUND)).addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */           }
/* 321 */         })).channel(clazz)).connect(address, serverPort).syncUninterruptibly();
/* 322 */     return networkmanager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NetworkManager provideLocalClient(SocketAddress address) {
/* 330 */     final NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
/* 331 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)CLIENT_LOCAL_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>() {
/*     */           protected void initChannel(Channel p_initChannel_1_) throws Exception {
/* 333 */             p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */           }
/* 335 */         })).channel(LocalChannel.class)).connect(address).syncUninterruptibly();
/* 336 */     return networkmanager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableEncryption(SecretKey key) {
/* 343 */     this.isEncrypted = true;
/* 344 */     this.channel.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new NettyEncryptingDecoder(CryptManager.createNetCipherInstance(2, key)));
/* 345 */     this.channel.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new NettyEncryptingEncoder(CryptManager.createNetCipherInstance(1, key)));
/*     */   }
/*     */   
/*     */   public boolean getIsencrypted() {
/* 349 */     return this.isEncrypted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChannelOpen() {
/* 356 */     return (this.channel != null && this.channel.isOpen());
/*     */   }
/*     */   
/*     */   public boolean hasNoChannel() {
/* 360 */     return (this.channel == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public INetHandler getNetHandler() {
/* 367 */     return this.packetListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getExitMessage() {
/* 374 */     return this.terminationReason;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableAutoRead() {
/* 381 */     this.channel.config().setAutoRead(false);
/*     */   }
/*     */   
/*     */   public void setCompressionTreshold(int treshold) {
/* 385 */     if (treshold >= 0) {
/* 386 */       if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
/* 387 */         ((NettyCompressionDecoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
/*     */       } else {
/* 389 */         this.channel.pipeline().addBefore("decoder", "decompress", (ChannelHandler)new NettyCompressionDecoder(treshold));
/*     */       } 
/*     */       
/* 392 */       if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
/* 393 */         ((NettyCompressionEncoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(treshold);
/*     */       } else {
/* 395 */         this.channel.pipeline().addBefore("encoder", "compress", (ChannelHandler)new NettyCompressionEncoder(treshold));
/*     */       } 
/*     */     } else {
/* 398 */       if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
/* 399 */         this.channel.pipeline().remove("decompress");
/*     */       }
/*     */       
/* 402 */       if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
/* 403 */         this.channel.pipeline().remove("compress");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void checkDisconnected() {
/* 409 */     if (this.channel != null && !this.channel.isOpen())
/* 410 */       if (!this.disconnected) {
/* 411 */         this.disconnected = true;
/*     */         
/* 413 */         if (getExitMessage() != null) {
/* 414 */           getNetHandler().onDisconnect(getExitMessage());
/* 415 */         } else if (getNetHandler() != null) {
/* 416 */           getNetHandler().onDisconnect((IChatComponent)new ChatComponentText("Disconnected"));
/*     */         } 
/*     */       } else {
/* 419 */         logger.warn("handleDisconnection() called twice");
/*     */       }  
/*     */   }
/*     */   
/*     */   static class InboundHandlerTuplePacketListener
/*     */   {
/*     */     private final Packet packet;
/*     */     private final GenericFutureListener<? extends Future<? super Void>>[] futureListeners;
/*     */     
/*     */     public InboundHandlerTuplePacketListener(Packet inPacket, GenericFutureListener... inFutureListeners) {
/* 429 */       this.packet = inPacket;
/* 430 */       this.futureListeners = (GenericFutureListener<? extends Future<? super Void>>[])inFutureListeners;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\NetworkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */