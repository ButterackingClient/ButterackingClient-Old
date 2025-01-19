/*     */ package net.minecraft.client.network;
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import io.netty.bootstrap.Bootstrap;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.channel.SimpleChannelInboundHandler;
/*     */ import io.netty.channel.socket.nio.NioSocketChannel;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ServerAddress;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.network.EnumConnectionState;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.ServerStatusResponse;
/*     */ import net.minecraft.network.handshake.client.C00Handshake;
/*     */ import net.minecraft.network.status.INetHandlerStatusClient;
/*     */ import net.minecraft.network.status.client.C00PacketServerQuery;
/*     */ import net.minecraft.network.status.client.C01PacketPing;
/*     */ import net.minecraft.network.status.server.S00PacketServerInfo;
/*     */ import net.minecraft.network.status.server.S01PacketPong;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class OldServerPinger {
/*  49 */   private static final Splitter PING_RESPONSE_SPLITTER = Splitter.on(false).limit(6);
/*  50 */   private static final Logger logger = LogManager.getLogger();
/*  51 */   private final List<NetworkManager> pingDestinations = Collections.synchronizedList(Lists.newArrayList());
/*     */   
/*     */   public void ping(final ServerData server) throws UnknownHostException {
/*  54 */     ServerAddress serveraddress = ServerAddress.fromString(server.serverIP);
/*  55 */     final NetworkManager networkmanager = NetworkManager.createNetworkManagerAndConnect(InetAddress.getByName(serveraddress.getIP()), serveraddress.getPort(), false);
/*  56 */     this.pingDestinations.add(networkmanager);
/*  57 */     server.serverMOTD = "Pinging...";
/*  58 */     server.pingToServer = -1L;
/*  59 */     server.playerList = null;
/*  60 */     networkmanager.setNetHandler((INetHandler)new INetHandlerStatusClient() {
/*     */           private boolean field_147403_d = false;
/*     */           private boolean field_183009_e = false;
/*  63 */           private long field_175092_e = 0L;
/*     */           
/*     */           public void handleServerInfo(S00PacketServerInfo packetIn) {
/*  66 */             if (this.field_183009_e) {
/*  67 */               networkmanager.closeChannel((IChatComponent)new ChatComponentText("Received unrequested status"));
/*     */             } else {
/*  69 */               this.field_183009_e = true;
/*  70 */               ServerStatusResponse serverstatusresponse = packetIn.getResponse();
/*     */               
/*  72 */               if (serverstatusresponse.getServerDescription() != null) {
/*  73 */                 server.serverMOTD = serverstatusresponse.getServerDescription().getFormattedText();
/*     */               } else {
/*  75 */                 server.serverMOTD = "";
/*     */               } 
/*     */               
/*  78 */               if (serverstatusresponse.getProtocolVersionInfo() != null) {
/*  79 */                 server.gameVersion = serverstatusresponse.getProtocolVersionInfo().getName();
/*  80 */                 server.version = serverstatusresponse.getProtocolVersionInfo().getProtocol();
/*     */               } else {
/*  82 */                 server.gameVersion = "Old";
/*  83 */                 server.version = 0;
/*     */               } 
/*     */               
/*  86 */               if (serverstatusresponse.getPlayerCountData() != null) {
/*  87 */                 server.populationInfo = EnumChatFormatting.GRAY + serverstatusresponse.getPlayerCountData().getOnlinePlayerCount() + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + serverstatusresponse.getPlayerCountData().getMaxPlayers();
/*     */                 
/*  89 */                 if (ArrayUtils.isNotEmpty((Object[])serverstatusresponse.getPlayerCountData().getPlayers())) {
/*  90 */                   StringBuilder stringbuilder = new StringBuilder(); byte b; int i;
/*     */                   GameProfile[] arrayOfGameProfile;
/*  92 */                   for (i = (arrayOfGameProfile = serverstatusresponse.getPlayerCountData().getPlayers()).length, b = 0; b < i; ) { GameProfile gameprofile = arrayOfGameProfile[b];
/*  93 */                     if (stringbuilder.length() > 0) {
/*  94 */                       stringbuilder.append("\n");
/*     */                     }
/*     */                     
/*  97 */                     stringbuilder.append(gameprofile.getName());
/*     */                     b++; }
/*     */                   
/* 100 */                   if ((serverstatusresponse.getPlayerCountData().getPlayers()).length < serverstatusresponse.getPlayerCountData().getOnlinePlayerCount()) {
/* 101 */                     if (stringbuilder.length() > 0) {
/* 102 */                       stringbuilder.append("\n");
/*     */                     }
/*     */                     
/* 105 */                     stringbuilder.append("... and ").append(serverstatusresponse.getPlayerCountData().getOnlinePlayerCount() - (serverstatusresponse.getPlayerCountData().getPlayers()).length).append(" more ...");
/*     */                   } 
/*     */                   
/* 108 */                   server.playerList = stringbuilder.toString();
/*     */                 } 
/*     */               } else {
/* 111 */                 server.populationInfo = EnumChatFormatting.DARK_GRAY + "???";
/*     */               } 
/*     */               
/* 114 */               if (serverstatusresponse.getFavicon() != null) {
/* 115 */                 String s = serverstatusresponse.getFavicon();
/*     */                 
/* 117 */                 if (s.startsWith("data:image/png;base64,")) {
/* 118 */                   server.setBase64EncodedIconData(s.substring("data:image/png;base64,".length()));
/*     */                 } else {
/* 120 */                   OldServerPinger.logger.error("Invalid server icon (unknown format)");
/*     */                 } 
/*     */               } else {
/* 123 */                 server.setBase64EncodedIconData(null);
/*     */               } 
/*     */               
/* 126 */               this.field_175092_e = Minecraft.getSystemTime();
/* 127 */               networkmanager.sendPacket((Packet)new C01PacketPing(this.field_175092_e));
/* 128 */               this.field_147403_d = true;
/*     */             } 
/*     */           }
/*     */           
/*     */           public void handlePong(S01PacketPong packetIn) {
/* 133 */             long i = this.field_175092_e;
/* 134 */             long j = Minecraft.getSystemTime();
/* 135 */             server.pingToServer = j - i;
/* 136 */             networkmanager.closeChannel((IChatComponent)new ChatComponentText("Finished"));
/*     */           }
/*     */           
/*     */           public void onDisconnect(IChatComponent reason) {
/* 140 */             if (!this.field_147403_d) {
/* 141 */               OldServerPinger.logger.error("Can't ping " + server.serverIP + ": " + reason.getUnformattedText());
/* 142 */               server.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
/* 143 */               server.populationInfo = "";
/* 144 */               OldServerPinger.this.tryCompatibilityPing(server);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/*     */     try {
/* 150 */       networkmanager.sendPacket((Packet)new C00Handshake(47, serveraddress.getIP(), serveraddress.getPort(), EnumConnectionState.STATUS));
/* 151 */       networkmanager.sendPacket((Packet)new C00PacketServerQuery());
/* 152 */     } catch (Throwable throwable) {
/* 153 */       logger.error(throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void tryCompatibilityPing(final ServerData server) {
/* 158 */     final ServerAddress serveraddress = ServerAddress.fromString(server.serverIP);
/* 159 */     ((Bootstrap)((Bootstrap)((Bootstrap)(new Bootstrap()).group((EventLoopGroup)NetworkManager.CLIENT_NIO_EVENTLOOP.getValue())).handler((ChannelHandler)new ChannelInitializer<Channel>() {
/*     */           protected void initChannel(Channel p_initChannel_1_) throws Exception {
/*     */             try {
/* 162 */               p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/* 163 */             } catch (ChannelException channelException) {}
/*     */ 
/*     */ 
/*     */             
/* 167 */             p_initChannel_1_.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)new SimpleChannelInboundHandler<ByteBuf>() {
/*     */                     public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception {
/* 169 */                       super.channelActive(p_channelActive_1_);
/* 170 */                       ByteBuf bytebuf = Unpooled.buffer();
/*     */                       
/*     */                       try {
/* 173 */                         bytebuf.writeByte(254);
/* 174 */                         bytebuf.writeByte(1);
/* 175 */                         bytebuf.writeByte(250);
/* 176 */                         char[] achar = "MC|PingHost".toCharArray();
/* 177 */                         bytebuf.writeShort(achar.length); byte b; int i;
/*     */                         char[] arrayOfChar1;
/* 179 */                         for (i = (arrayOfChar1 = achar).length, b = 0; b < i; ) { char c0 = arrayOfChar1[b];
/* 180 */                           bytebuf.writeChar(c0);
/*     */                           b++; }
/*     */                         
/* 183 */                         bytebuf.writeShort(7 + 2 * serveraddress.getIP().length());
/* 184 */                         bytebuf.writeByte(127);
/* 185 */                         achar = serveraddress.getIP().toCharArray();
/* 186 */                         bytebuf.writeShort(achar.length);
/*     */                         
/* 188 */                         for (i = (arrayOfChar1 = achar).length, b = 0; b < i; ) { char c1 = arrayOfChar1[b];
/* 189 */                           bytebuf.writeChar(c1);
/*     */                           b++; }
/*     */                         
/* 192 */                         bytebuf.writeInt(serveraddress.getPort());
/* 193 */                         p_channelActive_1_.channel().writeAndFlush(bytebuf).addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */                       } finally {
/* 195 */                         bytebuf.release();
/*     */                       } 
/*     */                     }
/*     */                     
/*     */                     protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, ByteBuf p_channelRead0_2_) throws Exception {
/* 200 */                       short short1 = p_channelRead0_2_.readUnsignedByte();
/*     */                       
/* 202 */                       if (short1 == 255) {
/* 203 */                         String s = new String(p_channelRead0_2_.readBytes(p_channelRead0_2_.readShort() * 2).array(), Charsets.UTF_16BE);
/* 204 */                         String[] astring = (String[])Iterables.toArray(OldServerPinger.PING_RESPONSE_SPLITTER.split(s), String.class);
/*     */                         
/* 206 */                         if ("§1".equals(astring[0])) {
/* 207 */                           int i = MathHelper.parseIntWithDefault(astring[1], 0);
/* 208 */                           String s1 = astring[2];
/* 209 */                           String s2 = astring[3];
/* 210 */                           int j = MathHelper.parseIntWithDefault(astring[4], -1);
/* 211 */                           int k = MathHelper.parseIntWithDefault(astring[5], -1);
/* 212 */                           server.version = -1;
/* 213 */                           server.gameVersion = s1;
/* 214 */                           server.serverMOTD = s2;
/* 215 */                           server.populationInfo = EnumChatFormatting.GRAY + j + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + k;
/*     */                         } 
/*     */                       } 
/*     */                       
/* 219 */                       p_channelRead0_1_.close();
/*     */                     }
/*     */                     
/*     */                     public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) throws Exception {
/* 223 */                       p_exceptionCaught_1_.close();
/*     */                     }
/*     */                   }
/*     */                 });
/*     */           }
/* 228 */         })).channel(NioSocketChannel.class)).connect(serveraddress.getIP(), serveraddress.getPort());
/*     */   }
/*     */   
/*     */   public void pingPendingNetworks() {
/* 232 */     synchronized (this.pingDestinations) {
/* 233 */       Iterator<NetworkManager> iterator = this.pingDestinations.iterator();
/*     */       
/* 235 */       while (iterator.hasNext()) {
/* 236 */         NetworkManager networkmanager = iterator.next();
/*     */         
/* 238 */         if (networkmanager.isChannelOpen()) {
/* 239 */           networkmanager.processReceivedPackets(); continue;
/*     */         } 
/* 241 */         iterator.remove();
/* 242 */         networkmanager.checkDisconnected();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearPendingNetworks() {
/* 249 */     synchronized (this.pingDestinations) {
/* 250 */       Iterator<NetworkManager> iterator = this.pingDestinations.iterator();
/*     */       
/* 252 */       while (iterator.hasNext()) {
/* 253 */         NetworkManager networkmanager = iterator.next();
/*     */         
/* 255 */         if (networkmanager.isChannelOpen()) {
/* 256 */           iterator.remove();
/* 257 */           networkmanager.closeChannel((IChatComponent)new ChatComponentText("Cancelled"));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\network\OldServerPinger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */