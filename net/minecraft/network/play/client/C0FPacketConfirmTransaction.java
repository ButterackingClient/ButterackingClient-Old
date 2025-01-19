/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ public class C0FPacketConfirmTransaction
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private int windowId;
/*    */   private short uid;
/*    */   private boolean accepted;
/*    */   
/*    */   public C0FPacketConfirmTransaction() {}
/*    */   
/*    */   public C0FPacketConfirmTransaction(int windowId, short uid, boolean accepted) {
/* 18 */     this.windowId = windowId;
/* 19 */     this.uid = uid;
/* 20 */     this.accepted = accepted;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 27 */     handler.processConfirmTransaction(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.windowId = buf.readByte();
/* 35 */     this.uid = buf.readShort();
/* 36 */     this.accepted = (buf.readByte() != 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 43 */     buf.writeByte(this.windowId);
/* 44 */     buf.writeShort(this.uid);
/* 45 */     buf.writeByte(this.accepted ? 1 : 0);
/*    */   }
/*    */   
/*    */   public int getWindowId() {
/* 49 */     return this.windowId;
/*    */   }
/*    */   
/*    */   public short getUid() {
/* 53 */     return this.uid;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C0FPacketConfirmTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */