/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ public class C00PacketKeepAlive
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private int key;
/*    */   
/*    */   public C00PacketKeepAlive() {}
/*    */   
/*    */   public C00PacketKeepAlive(int key) {
/* 16 */     this.key = key;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 23 */     handler.processKeepAlive(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 30 */     this.key = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 37 */     buf.writeVarIntToBuffer(this.key);
/*    */   }
/*    */   
/*    */   public int getKey() {
/* 41 */     return this.key;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C00PacketKeepAlive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */