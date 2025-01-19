/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class ModelHumanoidHead extends ModelSkeletonHead {
/*  6 */   private final ModelRenderer head = new ModelRenderer(this, 32, 0);
/*    */   
/*    */   public ModelHumanoidHead() {
/*  9 */     super(0, 0, 64, 64);
/* 10 */     this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.25F);
/* 11 */     this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 18 */     super.render(entityIn, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale);
/* 19 */     this.head.render(scale);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 28 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 29 */     this.head.rotateAngleY = this.skeletonHead.rotateAngleY;
/* 30 */     this.head.rotateAngleX = this.skeletonHead.rotateAngleX;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelHumanoidHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */