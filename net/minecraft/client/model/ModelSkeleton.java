/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntitySkeleton;
/*    */ 
/*    */ public class ModelSkeleton extends ModelZombie {
/*    */   public ModelSkeleton() {
/*  9 */     this(0.0F, false);
/*    */   }
/*    */   
/*    */   public ModelSkeleton(float p_i46303_1_, boolean p_i46303_2_) {
/* 13 */     super(p_i46303_1_, 0.0F, 64, 32);
/*    */     
/* 15 */     if (!p_i46303_2_) {
/* 16 */       this.bipedRightArm = new ModelRenderer(this, 40, 16);
/* 17 */       this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i46303_1_);
/* 18 */       this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
/* 19 */       this.bipedLeftArm = new ModelRenderer(this, 40, 16);
/* 20 */       this.bipedLeftArm.mirror = true;
/* 21 */       this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p_i46303_1_);
/* 22 */       this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
/* 23 */       this.bipedRightLeg = new ModelRenderer(this, 0, 16);
/* 24 */       this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i46303_1_);
/* 25 */       this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
/* 26 */       this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
/* 27 */       this.bipedLeftLeg.mirror = true;
/* 28 */       this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p_i46303_1_);
/* 29 */       this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 38 */     this.aimedBow = (((EntitySkeleton)entitylivingbaseIn).getSkeletonType() == 1);
/* 39 */     super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 48 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */