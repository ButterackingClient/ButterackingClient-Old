/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ 
/*    */ public class ModelSheep1 extends ModelQuadruped {
/*    */   private float headRotationAngleX;
/*    */   
/*    */   public ModelSheep1() {
/* 11 */     super(12, 0.0F);
/* 12 */     this.head = new ModelRenderer(this, 0, 0);
/* 13 */     this.head.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
/* 14 */     this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
/* 15 */     this.body = new ModelRenderer(this, 28, 8);
/* 16 */     this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
/* 17 */     this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
/* 18 */     float f = 0.5F;
/* 19 */     this.leg1 = new ModelRenderer(this, 0, 16);
/* 20 */     this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
/* 21 */     this.leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
/* 22 */     this.leg2 = new ModelRenderer(this, 0, 16);
/* 23 */     this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
/* 24 */     this.leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
/* 25 */     this.leg3 = new ModelRenderer(this, 0, 16);
/* 26 */     this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
/* 27 */     this.leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
/* 28 */     this.leg4 = new ModelRenderer(this, 0, 16);
/* 29 */     this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
/* 30 */     this.leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 38 */     super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
/* 39 */     this.head.rotationPointY = 6.0F + ((EntitySheep)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
/* 40 */     this.headRotationAngleX = ((EntitySheep)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 49 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 50 */     this.head.rotateAngleX = this.headRotationAngleX;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelSheep1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */