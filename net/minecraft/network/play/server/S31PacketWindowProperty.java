/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S31PacketWindowProperty
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int windowId;
/*    */   private int varIndex;
/*    */   private int varValue;
/*    */   
/*    */   public S31PacketWindowProperty() {}
/*    */   
/*    */   public S31PacketWindowProperty(int windowIdIn, int varIndexIn, int varValueIn) {
/* 18 */     this.windowId = windowIdIn;
/* 19 */     this.varIndex = varIndexIn;
/* 20 */     this.varValue = varValueIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 27 */     handler.handleWindowProperty(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.windowId = buf.readUnsignedByte();
/* 35 */     this.varIndex = buf.readShort();
/* 36 */     this.varValue = buf.readShort();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 43 */     buf.writeByte(this.windowId);
/* 44 */     buf.writeShort(this.varIndex);
/* 45 */     buf.writeShort(this.varValue);
/*    */   }
/*    */   
/*    */   public int getWindowId() {
/* 49 */     return this.windowId;
/*    */   }
/*    */   
/*    */   public int getVarIndex() {
/* 53 */     return this.varIndex;
/*    */   }
/*    */   
/*    */   public int getVarValue() {
/* 57 */     return this.varValue;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S31PacketWindowProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */