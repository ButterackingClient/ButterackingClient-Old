/*    */ package net.minecraft.server.network;
/*    */ 
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.handshake.INetHandlerHandshakeServer;
/*    */ import net.minecraft.network.handshake.client.C00Handshake;
/*    */ import net.minecraft.network.login.server.S00PacketDisconnect;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class NetHandlerHandshakeTCP
/*    */   implements INetHandlerHandshakeServer {
/*    */   public NetHandlerHandshakeTCP(MinecraftServer serverIn, NetworkManager netManager) {
/* 17 */     this.server = serverIn;
/* 18 */     this.networkManager = netManager;
/*    */   }
/*    */ 
/*    */   
/*    */   private final MinecraftServer server;
/*    */   
/*    */   private final NetworkManager networkManager;
/*    */   
/*    */   public void processHandshake(C00Handshake packetIn) {
/* 27 */     switch (packetIn.getRequestedState()) {
/*    */       case LOGIN:
/* 29 */         this.networkManager.setConnectionState(EnumConnectionState.LOGIN);
/*    */         
/* 31 */         if (packetIn.getProtocolVersion() > 47) {
/* 32 */           ChatComponentText chatcomponenttext = new ChatComponentText("Outdated server! I'm still on 1.8.9");
/* 33 */           this.networkManager.sendPacket((Packet)new S00PacketDisconnect((IChatComponent)chatcomponenttext));
/* 34 */           this.networkManager.closeChannel((IChatComponent)chatcomponenttext);
/* 35 */         } else if (packetIn.getProtocolVersion() < 47) {
/* 36 */           ChatComponentText chatcomponenttext1 = new ChatComponentText("Outdated client! Please use 1.8.9");
/* 37 */           this.networkManager.sendPacket((Packet)new S00PacketDisconnect((IChatComponent)chatcomponenttext1));
/* 38 */           this.networkManager.closeChannel((IChatComponent)chatcomponenttext1);
/*    */         } else {
/* 40 */           this.networkManager.setNetHandler((INetHandler)new NetHandlerLoginServer(this.server, this.networkManager));
/*    */         } 
/*    */         return;
/*    */ 
/*    */       
/*    */       case STATUS:
/* 46 */         this.networkManager.setConnectionState(EnumConnectionState.STATUS);
/* 47 */         this.networkManager.setNetHandler((INetHandler)new NetHandlerStatusServer(this.server, this.networkManager));
/*    */         return;
/*    */     } 
/*    */     
/* 51 */     throw new UnsupportedOperationException("Invalid intention " + packetIn.getRequestedState());
/*    */   }
/*    */   
/*    */   public void onDisconnect(IChatComponent reason) {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\network\NetHandlerHandshakeTCP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */