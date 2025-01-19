/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ 
/*    */ public class S1DPacketEntityEffect
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int entityId;
/*    */   private byte effectId;
/*    */   private byte amplifier;
/*    */   private int duration;
/*    */   private byte hideParticles;
/*    */   
/*    */   public S1DPacketEntityEffect() {}
/*    */   
/*    */   public S1DPacketEntityEffect(int entityIdIn, PotionEffect effect) {
/* 21 */     this.entityId = entityIdIn;
/* 22 */     this.effectId = (byte)(effect.getPotionID() & 0xFF);
/* 23 */     this.amplifier = (byte)(effect.getAmplifier() & 0xFF);
/*    */     
/* 25 */     if (effect.getDuration() > 32767) {
/* 26 */       this.duration = 32767;
/*    */     } else {
/* 28 */       this.duration = effect.getDuration();
/*    */     } 
/*    */     
/* 31 */     this.hideParticles = (byte)(effect.getIsShowParticles() ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 38 */     this.entityId = buf.readVarIntFromBuffer();
/* 39 */     this.effectId = buf.readByte();
/* 40 */     this.amplifier = buf.readByte();
/* 41 */     this.duration = buf.readVarIntFromBuffer();
/* 42 */     this.hideParticles = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 49 */     buf.writeVarIntToBuffer(this.entityId);
/* 50 */     buf.writeByte(this.effectId);
/* 51 */     buf.writeByte(this.amplifier);
/* 52 */     buf.writeVarIntToBuffer(this.duration);
/* 53 */     buf.writeByte(this.hideParticles);
/*    */   }
/*    */   
/*    */   public boolean func_149429_c() {
/* 57 */     return (this.duration == 32767);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 64 */     handler.handleEntityEffect(this);
/*    */   }
/*    */   
/*    */   public int getEntityId() {
/* 68 */     return this.entityId;
/*    */   }
/*    */   
/*    */   public byte getEffectId() {
/* 72 */     return this.effectId;
/*    */   }
/*    */   
/*    */   public byte getAmplifier() {
/* 76 */     return this.amplifier;
/*    */   }
/*    */   
/*    */   public int getDuration() {
/* 80 */     return this.duration;
/*    */   }
/*    */   
/*    */   public boolean func_179707_f() {
/* 84 */     return (this.hideParticles != 0);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S1DPacketEntityEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */