/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ public class ModelBook
/*    */   extends ModelBase
/*    */ {
/* 10 */   public ModelRenderer coverRight = (new ModelRenderer(this)).setTextureOffset(0, 0).addBox(-6.0F, -5.0F, 0.0F, 6, 10, 0);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 15 */   public ModelRenderer coverLeft = (new ModelRenderer(this)).setTextureOffset(16, 0).addBox(0.0F, -5.0F, 0.0F, 6, 10, 0);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 20 */   public ModelRenderer pagesRight = (new ModelRenderer(this)).setTextureOffset(0, 10).addBox(0.0F, -4.0F, -0.99F, 5, 8, 1);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   public ModelRenderer pagesLeft = (new ModelRenderer(this)).setTextureOffset(12, 10).addBox(0.0F, -4.0F, -0.01F, 5, 8, 1);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 30 */   public ModelRenderer flippingPageRight = (new ModelRenderer(this)).setTextureOffset(24, 10).addBox(0.0F, -4.0F, 0.0F, 5, 8, 0);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public ModelRenderer flippingPageLeft = (new ModelRenderer(this)).setTextureOffset(24, 10).addBox(0.0F, -4.0F, 0.0F, 5, 8, 0);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public ModelRenderer bookSpine = (new ModelRenderer(this)).setTextureOffset(12, 0).addBox(-1.0F, -5.0F, 0.0F, 2, 10, 0);
/*    */   
/*    */   public ModelBook() {
/* 43 */     this.coverRight.setRotationPoint(0.0F, 0.0F, -1.0F);
/* 44 */     this.coverLeft.setRotationPoint(0.0F, 0.0F, 1.0F);
/* 45 */     this.bookSpine.rotateAngleY = 1.5707964F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 52 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/* 53 */     this.coverRight.render(scale);
/* 54 */     this.coverLeft.render(scale);
/* 55 */     this.bookSpine.render(scale);
/* 56 */     this.pagesRight.render(scale);
/* 57 */     this.pagesLeft.render(scale);
/* 58 */     this.flippingPageRight.render(scale);
/* 59 */     this.flippingPageLeft.render(scale);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 68 */     float f = (MathHelper.sin(limbSwing * 0.02F) * 0.1F + 1.25F) * netHeadYaw;
/* 69 */     this.coverRight.rotateAngleY = 3.1415927F + f;
/* 70 */     this.coverLeft.rotateAngleY = -f;
/* 71 */     this.pagesRight.rotateAngleY = f;
/* 72 */     this.pagesLeft.rotateAngleY = -f;
/* 73 */     this.flippingPageRight.rotateAngleY = f - f * 2.0F * limbSwingAmount;
/* 74 */     this.flippingPageLeft.rotateAngleY = f - f * 2.0F * ageInTicks;
/* 75 */     this.pagesRight.rotationPointX = MathHelper.sin(f);
/* 76 */     this.pagesLeft.rotationPointX = MathHelper.sin(f);
/* 77 */     this.flippingPageRight.rotationPointX = MathHelper.sin(f);
/* 78 */     this.flippingPageLeft.rotationPointX = MathHelper.sin(f);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */