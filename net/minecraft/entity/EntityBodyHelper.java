/*    */ package net.minecraft.entity;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityBodyHelper
/*    */ {
/*    */   private EntityLivingBase theLiving;
/*    */   private int rotationTickCounter;
/*    */   private float prevRenderYawHead;
/*    */   
/*    */   public EntityBodyHelper(EntityLivingBase p_i1611_1_) {
/* 18 */     this.theLiving = p_i1611_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateRenderAngles() {
/* 25 */     double d0 = this.theLiving.posX - this.theLiving.prevPosX;
/* 26 */     double d1 = this.theLiving.posZ - this.theLiving.prevPosZ;
/*    */     
/* 28 */     if (d0 * d0 + d1 * d1 > 2.500000277905201E-7D) {
/* 29 */       this.theLiving.renderYawOffset = this.theLiving.rotationYaw;
/* 30 */       this.theLiving.rotationYawHead = computeAngleWithBound(this.theLiving.renderYawOffset, this.theLiving.rotationYawHead, 75.0F);
/* 31 */       this.prevRenderYawHead = this.theLiving.rotationYawHead;
/* 32 */       this.rotationTickCounter = 0;
/*    */     } else {
/* 34 */       float f = 75.0F;
/*    */       
/* 36 */       if (Math.abs(this.theLiving.rotationYawHead - this.prevRenderYawHead) > 15.0F) {
/* 37 */         this.rotationTickCounter = 0;
/* 38 */         this.prevRenderYawHead = this.theLiving.rotationYawHead;
/*    */       } else {
/* 40 */         this.rotationTickCounter++;
/* 41 */         int i = 10;
/*    */         
/* 43 */         if (this.rotationTickCounter > 10) {
/* 44 */           f = Math.max(1.0F - (this.rotationTickCounter - 10) / 10.0F, 0.0F) * 75.0F;
/*    */         }
/*    */       } 
/*    */       
/* 48 */       this.theLiving.renderYawOffset = computeAngleWithBound(this.theLiving.rotationYawHead, this.theLiving.renderYawOffset, f);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private float computeAngleWithBound(float p_75665_1_, float p_75665_2_, float p_75665_3_) {
/* 57 */     float f = MathHelper.wrapAngleTo180_float(p_75665_1_ - p_75665_2_);
/*    */     
/* 59 */     if (f < -p_75665_3_) {
/* 60 */       f = -p_75665_3_;
/*    */     }
/*    */     
/* 63 */     if (f >= p_75665_3_) {
/* 64 */       f = p_75665_3_;
/*    */     }
/*    */     
/* 67 */     return p_75665_1_ - f;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\EntityBodyHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */