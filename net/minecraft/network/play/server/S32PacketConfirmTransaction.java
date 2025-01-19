/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S32PacketConfirmTransaction
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int windowId;
/*    */   private short actionNumber;
/*    */   private boolean field_148893_c;
/*    */   
/*    */   public S32PacketConfirmTransaction() {}
/*    */   
/*    */   public S32PacketConfirmTransaction(int windowIdIn, short actionNumberIn, boolean p_i45182_3_) {
/* 18 */     this.windowId = windowIdIn;
/* 19 */     this.actionNumber = actionNumberIn;
/* 20 */     this.field_148893_c = p_i45182_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 27 */     handler.handleConfirmTransaction(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.windowId = buf.readUnsignedByte();
/* 35 */     this.actionNumber = buf.readShort();
/* 36 */     this.field_148893_c = buf.readBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 43 */     buf.writeByte(this.windowId);
/* 44 */     buf.writeShort(this.actionNumber);
/* 45 */     buf.writeBoolean(this.field_148893_c);
/*    */   }
/*    */   
/*    */   public int getWindowId() {
/* 49 */     return this.windowId;
/*    */   }
/*    */   
/*    */   public short getActionNumber() {
/* 53 */     return this.actionNumber;
/*    */   }
/*    */   
/*    */   public boolean func_148888_e() {
/* 57 */     return this.field_148893_c;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S32PacketConfirmTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */