/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ 
/*    */ public class ModelSheep2 extends ModelQuadruped {
/*    */   private float headRotationAngleX;
/*    */   
/*    */   public ModelSheep2() {
/* 11 */     super(12, 0.0F);
/* 12 */     this.head = new ModelRenderer(this, 0, 0);
/* 13 */     this.head.addBox(-3.0F, -4.0F, -6.0F, 6, 6, 8, 0.0F);
/* 14 */     this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
/* 15 */     this.body = new ModelRenderer(this, 28, 8);
/* 16 */     this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 0.0F);
/* 17 */     this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 25 */     super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
/* 26 */     this.head.rotationPointY = 6.0F + ((EntitySheep)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
/* 27 */     this.headRotationAngleX = ((EntitySheep)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 36 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 37 */     this.head.rotateAngleX = this.headRotationAngleX;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelSheep2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */