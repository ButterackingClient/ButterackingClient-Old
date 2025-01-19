/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelEnderman
/*     */   extends ModelBiped
/*     */ {
/*     */   public boolean isCarrying;
/*     */   public boolean isAttacking;
/*     */   
/*     */   public ModelEnderman(float p_i46305_1_) {
/*  17 */     super(0.0F, -14.0F, 64, 32);
/*  18 */     float f = -14.0F;
/*  19 */     this.bipedHeadwear = new ModelRenderer(this, 0, 16);
/*  20 */     this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i46305_1_ - 0.5F);
/*  21 */     this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + f, 0.0F);
/*  22 */     this.bipedBody = new ModelRenderer(this, 32, 16);
/*  23 */     this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46305_1_);
/*  24 */     this.bipedBody.setRotationPoint(0.0F, 0.0F + f, 0.0F);
/*  25 */     this.bipedRightArm = new ModelRenderer(this, 56, 0);
/*  26 */     this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, p_i46305_1_);
/*  27 */     this.bipedRightArm.setRotationPoint(-3.0F, 2.0F + f, 0.0F);
/*  28 */     this.bipedLeftArm = new ModelRenderer(this, 56, 0);
/*  29 */     this.bipedLeftArm.mirror = true;
/*  30 */     this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, p_i46305_1_);
/*  31 */     this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + f, 0.0F);
/*  32 */     this.bipedRightLeg = new ModelRenderer(this, 56, 0);
/*  33 */     this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, p_i46305_1_);
/*  34 */     this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + f, 0.0F);
/*  35 */     this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
/*  36 */     this.bipedLeftLeg.mirror = true;
/*  37 */     this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, p_i46305_1_);
/*  38 */     this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + f, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*  47 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/*  48 */     this.bipedHead.showModel = true;
/*  49 */     float f = -14.0F;
/*  50 */     this.bipedBody.rotateAngleX = 0.0F;
/*  51 */     this.bipedBody.rotationPointY = f;
/*  52 */     this.bipedBody.rotationPointZ = -0.0F;
/*  53 */     this.bipedRightLeg.rotateAngleX -= 0.0F;
/*  54 */     this.bipedLeftLeg.rotateAngleX -= 0.0F;
/*  55 */     this.bipedRightArm.rotateAngleX = (float)(this.bipedRightArm.rotateAngleX * 0.5D);
/*  56 */     this.bipedLeftArm.rotateAngleX = (float)(this.bipedLeftArm.rotateAngleX * 0.5D);
/*  57 */     this.bipedRightLeg.rotateAngleX = (float)(this.bipedRightLeg.rotateAngleX * 0.5D);
/*  58 */     this.bipedLeftLeg.rotateAngleX = (float)(this.bipedLeftLeg.rotateAngleX * 0.5D);
/*  59 */     float f1 = 0.4F;
/*     */     
/*  61 */     if (this.bipedRightArm.rotateAngleX > f1) {
/*  62 */       this.bipedRightArm.rotateAngleX = f1;
/*     */     }
/*     */     
/*  65 */     if (this.bipedLeftArm.rotateAngleX > f1) {
/*  66 */       this.bipedLeftArm.rotateAngleX = f1;
/*     */     }
/*     */     
/*  69 */     if (this.bipedRightArm.rotateAngleX < -f1) {
/*  70 */       this.bipedRightArm.rotateAngleX = -f1;
/*     */     }
/*     */     
/*  73 */     if (this.bipedLeftArm.rotateAngleX < -f1) {
/*  74 */       this.bipedLeftArm.rotateAngleX = -f1;
/*     */     }
/*     */     
/*  77 */     if (this.bipedRightLeg.rotateAngleX > f1) {
/*  78 */       this.bipedRightLeg.rotateAngleX = f1;
/*     */     }
/*     */     
/*  81 */     if (this.bipedLeftLeg.rotateAngleX > f1) {
/*  82 */       this.bipedLeftLeg.rotateAngleX = f1;
/*     */     }
/*     */     
/*  85 */     if (this.bipedRightLeg.rotateAngleX < -f1) {
/*  86 */       this.bipedRightLeg.rotateAngleX = -f1;
/*     */     }
/*     */     
/*  89 */     if (this.bipedLeftLeg.rotateAngleX < -f1) {
/*  90 */       this.bipedLeftLeg.rotateAngleX = -f1;
/*     */     }
/*     */     
/*  93 */     if (this.isCarrying) {
/*  94 */       this.bipedRightArm.rotateAngleX = -0.5F;
/*  95 */       this.bipedLeftArm.rotateAngleX = -0.5F;
/*  96 */       this.bipedRightArm.rotateAngleZ = 0.05F;
/*  97 */       this.bipedLeftArm.rotateAngleZ = -0.05F;
/*     */     } 
/*     */     
/* 100 */     this.bipedRightArm.rotationPointZ = 0.0F;
/* 101 */     this.bipedLeftArm.rotationPointZ = 0.0F;
/* 102 */     this.bipedRightLeg.rotationPointZ = 0.0F;
/* 103 */     this.bipedLeftLeg.rotationPointZ = 0.0F;
/* 104 */     this.bipedRightLeg.rotationPointY = 9.0F + f;
/* 105 */     this.bipedLeftLeg.rotationPointY = 9.0F + f;
/* 106 */     this.bipedHead.rotationPointZ = -0.0F;
/* 107 */     this.bipedHead.rotationPointY = f + 1.0F;
/* 108 */     this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
/* 109 */     this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
/* 110 */     this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
/* 111 */     this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
/* 112 */     this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
/* 113 */     this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
/*     */     
/* 115 */     if (this.isAttacking) {
/* 116 */       float f2 = 1.0F;
/* 117 */       this.bipedHead.rotationPointY -= f2 * 5.0F;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelEnderman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */