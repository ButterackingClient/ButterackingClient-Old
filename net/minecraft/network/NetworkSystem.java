/*     */ package net.minecraft.network;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import io.netty.bootstrap.ServerBootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.epoll.Epoll;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollServerSocketChannel;
/*     */ import io.netty.channel.local.LocalAddress;
/*     */ import io.netty.channel.local.LocalEventLoopGroup;
/*     */ import io.netty.channel.local.LocalServerChannel;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioServerSocketChannel;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.client.network.NetHandlerHandshakeMemory;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.network.play.server.S40PacketDisconnect;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.network.NetHandlerHandshakeTCP;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.LazyLoadBase;
/*     */ import net.minecraft.util.MessageDeserializer;
/*     */ import net.minecraft.util.MessageDeserializer2;
/*     */ import net.minecraft.util.MessageSerializer;
/*     */ import net.minecraft.util.MessageSerializer2;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class NetworkSystem
/*     */ {
/*  51 */   private static final Logger logger = LogManager.getLogger();
/*  52 */   public static final LazyLoadBase<NioEventLoopGroup> eventLoops = new LazyLoadBase<NioEventLoopGroup>() {
/*     */       protected NioEventLoopGroup load() {
/*  54 */         return new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Server IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  57 */   public static final LazyLoadBase<EpollEventLoopGroup> SERVER_EPOLL_EVENTLOOP = new LazyLoadBase<EpollEventLoopGroup>() {
/*     */       protected EpollEventLoopGroup load() {
/*  59 */         return new EpollEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*  62 */   public static final LazyLoadBase<LocalEventLoopGroup> SERVER_LOCAL_EVENTLOOP = new LazyLoadBase<LocalEventLoopGroup>() {
/*     */       protected LocalEventLoopGroup load() {
/*  64 */         return new LocalEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final MinecraftServer mcServer;
/*     */ 
/*     */   
/*     */   public volatile boolean isAlive;
/*     */ 
/*     */   
/*  77 */   private final List<ChannelFuture> endpoints = Collections.synchronizedList(Lists.newArrayList());
/*  78 */   private final List<NetworkManager> networkManagers = Collections.synchronizedList(Lists.newArrayList());
/*     */   
/*     */   public NetworkSystem(MinecraftServer server) {
/*  81 */     this.mcServer = server;
/*  82 */     this.isAlive = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLanEndpoint(InetAddress address, int port) throws IOException {
/*  89 */     synchronized (this.endpoints) {
/*     */       Class<NioServerSocketChannel> clazz;
/*     */       
/*     */       LazyLoadBase<NioEventLoopGroup> lazyLoadBase;
/*  93 */       if (Epoll.isAvailable() && this.mcServer.shouldUseNativeTransport()) {
/*  94 */         Class<EpollServerSocketChannel> clazz1 = EpollServerSocketChannel.class;
/*  95 */         LazyLoadBase<EpollEventLoopGroup> lazyLoadBase1 = SERVER_EPOLL_EVENTLOOP;
/*  96 */         logger.info("Using epoll channel type");
/*     */       } else {
/*  98 */         clazz = NioServerSocketChannel.class;
/*  99 */         lazyLoadBase = eventLoops;
/* 100 */         logger.info("Using default channel type");
/*     */       } 
/*     */       
/* 103 */       this.endpoints.add(((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(clazz)).childHandler((ChannelHandler)new ChannelInitializer<Channel>() {
/*     */               protected void initChannel(Channel p_initChannel_1_) throws Exception {
/*     */                 try {
/* 106 */                   p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/* 107 */                 } catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */                 
/* 111 */                 p_initChannel_1_.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new PingResponseHandler(NetworkSystem.this)).addLast("splitter", (ChannelHandler)new MessageDeserializer2()).addLast("decoder", (ChannelHandler)new MessageDeserializer(EnumPacketDirection.SERVERBOUND)).addLast("prepender", (ChannelHandler)new MessageSerializer2()).addLast("encoder", (ChannelHandler)new MessageSerializer(EnumPacketDirection.CLIENTBOUND));
/* 112 */                 NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
/* 113 */                 NetworkSystem.this.networkManagers.add(networkmanager);
/* 114 */                 p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager);
/* 115 */                 networkmanager.setNetHandler((INetHandler)new NetHandlerHandshakeTCP(NetworkSystem.this.mcServer, networkmanager));
/*     */               }
/* 117 */             }).group((EventLoopGroup)lazyLoadBase.getValue()).localAddress(address, port)).bind().syncUninterruptibly());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress addLocalEndpoint() {
/*     */     ChannelFuture channelfuture;
/* 127 */     synchronized (this.endpoints) {
/* 128 */       channelfuture = ((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(LocalServerChannel.class)).childHandler((ChannelHandler)new ChannelInitializer<Channel>() {
/*     */             protected void initChannel(Channel p_initChannel_1_) throws Exception {
/* 130 */               NetworkManager networkmanager = new NetworkManager(EnumPacketDirection.SERVERBOUND);
/* 131 */               networkmanager.setNetHandler((INetHandler)new NetHandlerHandshakeMemory(NetworkSystem.this.mcServer, networkmanager));
/* 132 */               NetworkSystem.this.networkManagers.add(networkmanager);
/* 133 */               p_initChannel_1_.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager);
/*     */             }
/* 135 */           }).group((EventLoopGroup)eventLoops.getValue()).localAddress((SocketAddress)LocalAddress.ANY)).bind().syncUninterruptibly();
/* 136 */       this.endpoints.add(channelfuture);
/*     */     } 
/*     */     
/* 139 */     return channelfuture.channel().localAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void terminateEndpoints() {
/* 146 */     this.isAlive = false;
/*     */     
/* 148 */     for (ChannelFuture channelfuture : this.endpoints) {
/*     */       try {
/* 150 */         channelfuture.channel().close().sync();
/* 151 */       } catch (InterruptedException var4) {
/* 152 */         logger.error("Interrupted whilst closing channel");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void networkTick() {
/* 162 */     synchronized (this.networkManagers) {
/* 163 */       Iterator<NetworkManager> iterator = this.networkManagers.iterator();
/*     */       
/* 165 */       while (iterator.hasNext()) {
/* 166 */         final NetworkManager networkmanager = iterator.next();
/*     */         
/* 168 */         if (!networkmanager.hasNoChannel()) {
/* 169 */           if (!networkmanager.isChannelOpen()) {
/* 170 */             iterator.remove();
/* 171 */             networkmanager.checkDisconnected(); continue;
/*     */           } 
/*     */           try {
/* 174 */             networkmanager.processReceivedPackets();
/* 175 */           } catch (Exception exception) {
/* 176 */             if (networkmanager.isLocalChannel()) {
/* 177 */               CrashReport crashreport = CrashReport.makeCrashReport(exception, "Ticking memory connection");
/* 178 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Ticking connection");
/* 179 */               crashreportcategory.addCrashSectionCallable("Connection", new Callable<String>() {
/*     */                     public String call() throws Exception {
/* 181 */                       return networkmanager.toString();
/*     */                     }
/*     */                   });
/* 184 */               throw new ReportedException(crashreport);
/*     */             } 
/*     */             
/* 187 */             logger.warn("Failed to handle packet for " + networkmanager.getRemoteAddress(), exception);
/* 188 */             final ChatComponentText chatcomponenttext = new ChatComponentText("Internal server error");
/* 189 */             networkmanager.sendPacket((Packet)new S40PacketDisconnect((IChatComponent)chatcomponenttext), new GenericFutureListener<Future<? super Void>>() {
/*     */                   public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception {
/* 191 */                     networkmanager.closeChannel((IChatComponent)chatcomponenttext);
/*     */                   }
/* 193 */                 },  (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener[0]);
/* 194 */             networkmanager.disableAutoRead();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MinecraftServer getServer() {
/* 203 */     return this.mcServer;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\NetworkSystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */