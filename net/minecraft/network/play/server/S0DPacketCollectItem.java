/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S0DPacketCollectItem
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int collectedItemEntityId;
/*    */   private int entityId;
/*    */   
/*    */   public S0DPacketCollectItem() {}
/*    */   
/*    */   public S0DPacketCollectItem(int collectedItemEntityIdIn, int entityIdIn) {
/* 17 */     this.collectedItemEntityId = collectedItemEntityIdIn;
/* 18 */     this.entityId = entityIdIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 25 */     this.collectedItemEntityId = buf.readVarIntFromBuffer();
/* 26 */     this.entityId = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 33 */     buf.writeVarIntToBuffer(this.collectedItemEntityId);
/* 34 */     buf.writeVarIntToBuffer(this.entityId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 41 */     handler.handleCollectItem(this);
/*    */   }
/*    */   
/*    */   public int getCollectedItemEntityID() {
/* 45 */     return this.collectedItemEntityId;
/*    */   }
/*    */   
/*    */   public int getEntityID() {
/* 49 */     return this.entityId;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S0DPacketCollectItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */