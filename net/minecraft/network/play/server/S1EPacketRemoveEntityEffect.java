/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ public class S1EPacketRemoveEntityEffect
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int entityId;
/*    */   private int effectId;
/*    */   
/*    */   public S1EPacketRemoveEntityEffect() {}
/*    */   
/*    */   public S1EPacketRemoveEntityEffect(int entityIdIn, PotionEffect effect) {
/* 18 */     this.entityId = entityIdIn;
/* 19 */     this.effectId = effect.getPotionID();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.entityId = buf.readVarIntFromBuffer();
/* 27 */     this.effectId = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeVarIntToBuffer(this.entityId);
/* 35 */     buf.writeByte(this.effectId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 42 */     handler.handleRemoveEntityEffect(this);
/*    */   }
/*    */   
/*    */   public int getEntityId() {
/* 46 */     return this.entityId;
/*    */   }
/*    */   
/*    */   public int getEffectId() {
/* 50 */     return this.effectId;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S1EPacketRemoveEntityEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */