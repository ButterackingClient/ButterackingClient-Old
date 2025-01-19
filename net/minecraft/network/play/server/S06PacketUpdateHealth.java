/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S06PacketUpdateHealth
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private float health;
/*    */   private int foodLevel;
/*    */   private float saturationLevel;
/*    */   
/*    */   public S06PacketUpdateHealth() {}
/*    */   
/*    */   public S06PacketUpdateHealth(float healthIn, int foodLevelIn, float saturationIn) {
/* 18 */     this.health = healthIn;
/* 19 */     this.foodLevel = foodLevelIn;
/* 20 */     this.saturationLevel = saturationIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 27 */     this.health = buf.readFloat();
/* 28 */     this.foodLevel = buf.readVarIntFromBuffer();
/* 29 */     this.saturationLevel = buf.readFloat();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 36 */     buf.writeFloat(this.health);
/* 37 */     buf.writeVarIntToBuffer(this.foodLevel);
/* 38 */     buf.writeFloat(this.saturationLevel);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 45 */     handler.handleUpdateHealth(this);
/*    */   }
/*    */   
/*    */   public float getHealth() {
/* 49 */     return this.health;
/*    */   }
/*    */   
/*    */   public int getFoodLevel() {
/* 53 */     return this.foodLevel;
/*    */   }
/*    */   
/*    */   public float getSaturationLevel() {
/* 57 */     return this.saturationLevel;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S06PacketUpdateHealth.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */