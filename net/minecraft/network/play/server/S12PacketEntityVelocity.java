/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S12PacketEntityVelocity
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int entityID;
/*    */   private int motionX;
/*    */   private int motionY;
/*    */   private int motionZ;
/*    */   
/*    */   public S12PacketEntityVelocity() {}
/*    */   
/*    */   public S12PacketEntityVelocity(Entity entityIn) {
/* 20 */     this(entityIn.getEntityId(), entityIn.motionX, entityIn.motionY, entityIn.motionZ);
/*    */   }
/*    */   
/*    */   public S12PacketEntityVelocity(int entityIDIn, double motionXIn, double motionYIn, double motionZIn) {
/* 24 */     this.entityID = entityIDIn;
/* 25 */     double d0 = 3.9D;
/*    */     
/* 27 */     if (motionXIn < -d0) {
/* 28 */       motionXIn = -d0;
/*    */     }
/*    */     
/* 31 */     if (motionYIn < -d0) {
/* 32 */       motionYIn = -d0;
/*    */     }
/*    */     
/* 35 */     if (motionZIn < -d0) {
/* 36 */       motionZIn = -d0;
/*    */     }
/*    */     
/* 39 */     if (motionXIn > d0) {
/* 40 */       motionXIn = d0;
/*    */     }
/*    */     
/* 43 */     if (motionYIn > d0) {
/* 44 */       motionYIn = d0;
/*    */     }
/*    */     
/* 47 */     if (motionZIn > d0) {
/* 48 */       motionZIn = d0;
/*    */     }
/*    */     
/* 51 */     this.motionX = (int)(motionXIn * 8000.0D);
/* 52 */     this.motionY = (int)(motionYIn * 8000.0D);
/* 53 */     this.motionZ = (int)(motionZIn * 8000.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 60 */     this.entityID = buf.readVarIntFromBuffer();
/* 61 */     this.motionX = buf.readShort();
/* 62 */     this.motionY = buf.readShort();
/* 63 */     this.motionZ = buf.readShort();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 70 */     buf.writeVarIntToBuffer(this.entityID);
/* 71 */     buf.writeShort(this.motionX);
/* 72 */     buf.writeShort(this.motionY);
/* 73 */     buf.writeShort(this.motionZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 80 */     handler.handleEntityVelocity(this);
/*    */   }
/*    */   
/*    */   public int getEntityID() {
/* 84 */     return this.entityID;
/*    */   }
/*    */   
/*    */   public int getMotionX() {
/* 88 */     return this.motionX;
/*    */   }
/*    */   
/*    */   public int getMotionY() {
/* 92 */     return this.motionY;
/*    */   }
/*    */   
/*    */   public int getMotionZ() {
/* 96 */     return this.motionZ;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S12PacketEntityVelocity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */