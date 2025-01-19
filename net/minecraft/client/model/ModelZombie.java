/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ModelZombie extends ModelBiped {
/*    */   public ModelZombie() {
/*  8 */     this(0.0F, false);
/*    */   }
/*    */   
/*    */   protected ModelZombie(float modelSize, float p_i1167_2_, int textureWidthIn, int textureHeightIn) {
/* 12 */     super(modelSize, p_i1167_2_, textureWidthIn, textureHeightIn);
/*    */   }
/*    */   
/*    */   public ModelZombie(float modelSize, boolean p_i1168_2_) {
/* 16 */     super(modelSize, 0.0F, 64, p_i1168_2_ ? 32 : 64);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 25 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 26 */     float f = MathHelper.sin(this.swingProgress * 3.1415927F);
/* 27 */     float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * 3.1415927F);
/* 28 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/* 29 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 30 */     this.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
/* 31 */     this.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
/* 32 */     this.bipedRightArm.rotateAngleX = -1.5707964F;
/* 33 */     this.bipedLeftArm.rotateAngleX = -1.5707964F;
/* 34 */     this.bipedRightArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
/* 35 */     this.bipedLeftArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
/* 36 */     this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 37 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 38 */     this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/* 39 */     this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */