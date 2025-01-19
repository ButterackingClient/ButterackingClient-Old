/*    */ package net.minecraft.client.network;
/*    */ 
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.handshake.INetHandlerHandshakeServer;
/*    */ import net.minecraft.network.handshake.client.C00Handshake;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.network.NetHandlerLoginServer;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer {
/*    */   private final MinecraftServer mcServer;
/*    */   
/*    */   public NetHandlerHandshakeMemory(MinecraftServer mcServerIn, NetworkManager networkManagerIn) {
/* 15 */     this.mcServer = mcServerIn;
/* 16 */     this.networkManager = networkManagerIn;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private final NetworkManager networkManager;
/*    */ 
/*    */   
/*    */   public void processHandshake(C00Handshake packetIn) {
/* 25 */     this.networkManager.setConnectionState(packetIn.getRequestedState());
/* 26 */     this.networkManager.setNetHandler((INetHandler)new NetHandlerLoginServer(this.mcServer, this.networkManager));
/*    */   }
/*    */   
/*    */   public void onDisconnect(IChatComponent reason) {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\network\NetHandlerHandshakeMemory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */