/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S0BPacketAnimation
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int entityId;
/*    */   private int type;
/*    */   
/*    */   public S0BPacketAnimation() {}
/*    */   
/*    */   public S0BPacketAnimation(Entity ent, int animationType) {
/* 18 */     this.entityId = ent.getEntityId();
/* 19 */     this.type = animationType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.entityId = buf.readVarIntFromBuffer();
/* 27 */     this.type = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeVarIntToBuffer(this.entityId);
/* 35 */     buf.writeByte(this.type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 42 */     handler.handleAnimation(this);
/*    */   }
/*    */   
/*    */   public int getEntityID() {
/* 46 */     return this.entityId;
/*    */   }
/*    */   
/*    */   public int getAnimationType() {
/* 50 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S0BPacketAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */