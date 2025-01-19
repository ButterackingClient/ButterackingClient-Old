/*     */ package net.minecraft.realms;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class RealmsServerStatusPinger {
/*  28 */   private static final Logger LOGGER = LogManager.getLogger();
/*  29 */   private final List<NetworkManager> connections = Collections.synchronizedList(Lists.newArrayList());
/*     */   
/*     */   public void pingServer(final String p_pingServer_1_, final RealmsServerPing p_pingServer_2_) throws UnknownHostException {
/*  32 */     if (p_pingServer_1_ != null && !p_pingServer_1_.startsWith("0.0.0.0") && !p_pingServer_1_.isEmpty()) {
/*  33 */       RealmsServerAddress realmsserveraddress = RealmsServerAddress.parseString(p_pingServer_1_);
/*  34 */       final NetworkManager networkmanager = NetworkManager.createNetworkManagerAndConnect(InetAddress.getByName(realmsserveraddress.getHost()), realmsserveraddress.getPort(), false);
/*  35 */       this.connections.add(networkmanager);
/*  36 */       networkmanager.setNetHandler((INetHandler)new INetHandlerStatusClient() {
/*     */             private boolean field_154345_e = false;
/*     */             
/*     */             public void handleServerInfo(S00PacketServerInfo packetIn) {
/*  40 */               ServerStatusResponse serverstatusresponse = packetIn.getResponse();
/*     */               
/*  42 */               if (serverstatusresponse.getPlayerCountData() != null) {
/*  43 */                 p_pingServer_2_.nrOfPlayers = String.valueOf(serverstatusresponse.getPlayerCountData().getOnlinePlayerCount());
/*     */                 
/*  45 */                 if (ArrayUtils.isNotEmpty((Object[])serverstatusresponse.getPlayerCountData().getPlayers())) {
/*  46 */                   StringBuilder stringbuilder = new StringBuilder(); byte b; int i;
/*     */                   GameProfile[] arrayOfGameProfile;
/*  48 */                   for (i = (arrayOfGameProfile = serverstatusresponse.getPlayerCountData().getPlayers()).length, b = 0; b < i; ) { GameProfile gameprofile = arrayOfGameProfile[b];
/*  49 */                     if (stringbuilder.length() > 0) {
/*  50 */                       stringbuilder.append("\n");
/*     */                     }
/*     */                     
/*  53 */                     stringbuilder.append(gameprofile.getName());
/*     */                     b++; }
/*     */                   
/*  56 */                   if ((serverstatusresponse.getPlayerCountData().getPlayers()).length < serverstatusresponse.getPlayerCountData().getOnlinePlayerCount()) {
/*  57 */                     if (stringbuilder.length() > 0) {
/*  58 */                       stringbuilder.append("\n");
/*     */                     }
/*     */                     
/*  61 */                     stringbuilder.append("... and ").append(serverstatusresponse.getPlayerCountData().getOnlinePlayerCount() - (serverstatusresponse.getPlayerCountData().getPlayers()).length).append(" more ...");
/*     */                   } 
/*     */                   
/*  64 */                   p_pingServer_2_.playerList = stringbuilder.toString();
/*     */                 } 
/*     */               } else {
/*  67 */                 p_pingServer_2_.playerList = "";
/*     */               } 
/*     */               
/*  70 */               networkmanager.sendPacket((Packet)new C01PacketPing(Realms.currentTimeMillis()));
/*  71 */               this.field_154345_e = true;
/*     */             }
/*     */             
/*     */             public void handlePong(S01PacketPong packetIn) {
/*  75 */               networkmanager.closeChannel((IChatComponent)new ChatComponentText("Finished"));
/*     */             }
/*     */             
/*     */             public void onDisconnect(IChatComponent reason) {
/*  79 */               if (!this.field_154345_e) {
/*  80 */                 RealmsServerStatusPinger.LOGGER.error("Can't ping " + p_pingServer_1_ + ": " + reason.getUnformattedText());
/*     */               }
/*     */             }
/*     */           });
/*     */       
/*     */       try {
/*  86 */         networkmanager.sendPacket((Packet)new C00Handshake(RealmsSharedConstants.NETWORK_PROTOCOL_VERSION, realmsserveraddress.getHost(), realmsserveraddress.getPort(), EnumConnectionState.STATUS));
/*  87 */         networkmanager.sendPacket((Packet)new C00PacketServerQuery());
/*  88 */       } catch (Throwable throwable) {
/*  89 */         LOGGER.error(throwable);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tick() {
/*  95 */     synchronized (this.connections) {
/*  96 */       Iterator<NetworkManager> iterator = this.connections.iterator();
/*     */       
/*  98 */       while (iterator.hasNext()) {
/*  99 */         NetworkManager networkmanager = iterator.next();
/*     */         
/* 101 */         if (networkmanager.isChannelOpen()) {
/* 102 */           networkmanager.processReceivedPackets(); continue;
/*     */         } 
/* 104 */         iterator.remove();
/* 105 */         networkmanager.checkDisconnected();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAll() {
/* 112 */     synchronized (this.connections) {
/* 113 */       Iterator<NetworkManager> iterator = this.connections.iterator();
/*     */       
/* 115 */       while (iterator.hasNext()) {
/* 116 */         NetworkManager networkmanager = iterator.next();
/*     */         
/* 118 */         if (networkmanager.isChannelOpen()) {
/* 119 */           iterator.remove();
/* 120 */           networkmanager.closeChannel((IChatComponent)new ChatComponentText("Cancelled"));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\RealmsServerStatusPinger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */