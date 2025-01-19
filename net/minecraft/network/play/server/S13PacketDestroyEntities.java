/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S13PacketDestroyEntities
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int[] entityIDs;
/*    */   
/*    */   public S13PacketDestroyEntities() {}
/*    */   
/*    */   public S13PacketDestroyEntities(int... entityIDsIn) {
/* 16 */     this.entityIDs = entityIDsIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 23 */     this.entityIDs = new int[buf.readVarIntFromBuffer()];
/*    */     
/* 25 */     for (int i = 0; i < this.entityIDs.length; i++) {
/* 26 */       this.entityIDs[i] = buf.readVarIntFromBuffer();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeVarIntToBuffer(this.entityIDs.length);
/*    */     
/* 36 */     for (int i = 0; i < this.entityIDs.length; i++) {
/* 37 */       buf.writeVarIntToBuffer(this.entityIDs[i]);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 45 */     handler.handleDestroyEntities(this);
/*    */   }
/*    */   
/*    */   public int[] getEntityIDs() {
/* 49 */     return this.entityIDs;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S13PacketDestroyEntities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */