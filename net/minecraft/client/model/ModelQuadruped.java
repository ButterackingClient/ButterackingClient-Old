/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ModelQuadruped extends ModelBase {
/*  8 */   public ModelRenderer head = new ModelRenderer(this, 0, 0);
/*    */   public ModelRenderer body;
/*    */   public ModelRenderer leg1;
/*    */   public ModelRenderer leg2;
/*    */   public ModelRenderer leg3;
/*    */   public ModelRenderer leg4;
/* 14 */   protected float childYOffset = 8.0F;
/* 15 */   protected float childZOffset = 4.0F;
/*    */   
/*    */   public ModelQuadruped(int p_i1154_1_, float p_i1154_2_) {
/* 18 */     this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, p_i1154_2_);
/* 19 */     this.head.setRotationPoint(0.0F, (18 - p_i1154_1_), -6.0F);
/* 20 */     this.body = new ModelRenderer(this, 28, 8);
/* 21 */     this.body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, p_i1154_2_);
/* 22 */     this.body.setRotationPoint(0.0F, (17 - p_i1154_1_), 2.0F);
/* 23 */     this.leg1 = new ModelRenderer(this, 0, 16);
/* 24 */     this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, p_i1154_1_, 4, p_i1154_2_);
/* 25 */     this.leg1.setRotationPoint(-3.0F, (24 - p_i1154_1_), 7.0F);
/* 26 */     this.leg2 = new ModelRenderer(this, 0, 16);
/* 27 */     this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, p_i1154_1_, 4, p_i1154_2_);
/* 28 */     this.leg2.setRotationPoint(3.0F, (24 - p_i1154_1_), 7.0F);
/* 29 */     this.leg3 = new ModelRenderer(this, 0, 16);
/* 30 */     this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, p_i1154_1_, 4, p_i1154_2_);
/* 31 */     this.leg3.setRotationPoint(-3.0F, (24 - p_i1154_1_), -5.0F);
/* 32 */     this.leg4 = new ModelRenderer(this, 0, 16);
/* 33 */     this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, p_i1154_1_, 4, p_i1154_2_);
/* 34 */     this.leg4.setRotationPoint(3.0F, (24 - p_i1154_1_), -5.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 41 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/*    */     
/* 43 */     if (this.isChild) {
/* 44 */       float f = 2.0F;
/* 45 */       GlStateManager.pushMatrix();
/* 46 */       GlStateManager.translate(0.0F, this.childYOffset * scale, this.childZOffset * scale);
/* 47 */       this.head.render(scale);
/* 48 */       GlStateManager.popMatrix();
/* 49 */       GlStateManager.pushMatrix();
/* 50 */       GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
/* 51 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/* 52 */       this.body.render(scale);
/* 53 */       this.leg1.render(scale);
/* 54 */       this.leg2.render(scale);
/* 55 */       this.leg3.render(scale);
/* 56 */       this.leg4.render(scale);
/* 57 */       GlStateManager.popMatrix();
/*    */     } else {
/* 59 */       this.head.render(scale);
/* 60 */       this.body.render(scale);
/* 61 */       this.leg1.render(scale);
/* 62 */       this.leg2.render(scale);
/* 63 */       this.leg3.render(scale);
/* 64 */       this.leg4.render(scale);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 74 */     float f = 57.295776F;
/* 75 */     this.head.rotateAngleX = headPitch / 57.295776F;
/* 76 */     this.head.rotateAngleY = netHeadYaw / 57.295776F;
/* 77 */     this.body.rotateAngleX = 1.5707964F;
/* 78 */     this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
/* 79 */     this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
/* 80 */     this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
/* 81 */     this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelQuadruped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */