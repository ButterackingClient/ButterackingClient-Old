/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ModelSnowMan extends ModelBase {
/*    */   public ModelRenderer body;
/*    */   public ModelRenderer bottomBody;
/*    */   public ModelRenderer head;
/*    */   public ModelRenderer rightHand;
/*    */   public ModelRenderer leftHand;
/*    */   
/*    */   public ModelSnowMan() {
/* 14 */     float f = 4.0F;
/* 15 */     float f1 = 0.0F;
/* 16 */     this.head = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
/* 17 */     this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f1 - 0.5F);
/* 18 */     this.head.setRotationPoint(0.0F, 0.0F + f, 0.0F);
/* 19 */     this.rightHand = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
/* 20 */     this.rightHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, f1 - 0.5F);
/* 21 */     this.rightHand.setRotationPoint(0.0F, 0.0F + f + 9.0F - 7.0F, 0.0F);
/* 22 */     this.leftHand = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
/* 23 */     this.leftHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, f1 - 0.5F);
/* 24 */     this.leftHand.setRotationPoint(0.0F, 0.0F + f + 9.0F - 7.0F, 0.0F);
/* 25 */     this.body = (new ModelRenderer(this, 0, 16)).setTextureSize(64, 64);
/* 26 */     this.body.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, f1 - 0.5F);
/* 27 */     this.body.setRotationPoint(0.0F, 0.0F + f + 9.0F, 0.0F);
/* 28 */     this.bottomBody = (new ModelRenderer(this, 0, 36)).setTextureSize(64, 64);
/* 29 */     this.bottomBody.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12, f1 - 0.5F);
/* 30 */     this.bottomBody.setRotationPoint(0.0F, 0.0F + f + 20.0F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 39 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 40 */     this.head.rotateAngleY = netHeadYaw / 57.295776F;
/* 41 */     this.head.rotateAngleX = headPitch / 57.295776F;
/* 42 */     this.body.rotateAngleY = netHeadYaw / 57.295776F * 0.25F;
/* 43 */     float f = MathHelper.sin(this.body.rotateAngleY);
/* 44 */     float f1 = MathHelper.cos(this.body.rotateAngleY);
/* 45 */     this.rightHand.rotateAngleZ = 1.0F;
/* 46 */     this.leftHand.rotateAngleZ = -1.0F;
/* 47 */     this.rightHand.rotateAngleY = 0.0F + this.body.rotateAngleY;
/* 48 */     this.leftHand.rotateAngleY = 3.1415927F + this.body.rotateAngleY;
/* 49 */     this.rightHand.rotationPointX = f1 * 5.0F;
/* 50 */     this.rightHand.rotationPointZ = -f * 5.0F;
/* 51 */     this.leftHand.rotationPointX = -f1 * 5.0F;
/* 52 */     this.leftHand.rotationPointZ = f * 5.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 59 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/* 60 */     this.body.render(scale);
/* 61 */     this.bottomBody.render(scale);
/* 62 */     this.head.render(scale);
/* 63 */     this.rightHand.render(scale);
/* 64 */     this.leftHand.render(scale);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelSnowMan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */