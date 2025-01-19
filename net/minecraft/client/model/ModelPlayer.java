/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ public class ModelPlayer extends ModelBiped {
/*     */   public ModelRenderer bipedLeftArmwear;
/*     */   public ModelRenderer bipedRightArmwear;
/*     */   public ModelRenderer bipedLeftLegwear;
/*     */   public ModelRenderer bipedRightLegwear;
/*     */   public ModelRenderer bipedBodyWear;
/*     */   private ModelRenderer bipedCape;
/*     */   private ModelRenderer bipedDeadmau5Head;
/*     */   private boolean smallArms;
/*     */   
/*     */   public ModelPlayer(float p_i46304_1_, boolean p_i46304_2_) {
/*  17 */     super(p_i46304_1_, 0.0F, 64, 64);
/*  18 */     this.smallArms = p_i46304_2_;
/*  19 */     this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
/*  20 */     this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, p_i46304_1_);
/*  21 */     this.bipedCape = new ModelRenderer(this, 0, 0);
/*  22 */     this.bipedCape.setTextureSize(64, 32);
/*  23 */     this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, p_i46304_1_);
/*     */     
/*  25 */     if (p_i46304_2_) {
/*  26 */       this.bipedLeftArm = new ModelRenderer(this, 32, 48);
/*  27 */       this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
/*  28 */       this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
/*  29 */       this.bipedRightArm = new ModelRenderer(this, 40, 16);
/*  30 */       this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
/*  31 */       this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
/*  32 */       this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
/*  33 */       this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
/*  34 */       this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
/*  35 */       this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
/*  36 */       this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
/*  37 */       this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
/*     */     } else {
/*  39 */       this.bipedLeftArm = new ModelRenderer(this, 32, 48);
/*  40 */       this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_);
/*  41 */       this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
/*  42 */       this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
/*  43 */       this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
/*  44 */       this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
/*  45 */       this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
/*  46 */       this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
/*  47 */       this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
/*     */     } 
/*     */     
/*  50 */     this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
/*  51 */     this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_);
/*  52 */     this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
/*  53 */     this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
/*  54 */     this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
/*  55 */     this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
/*  56 */     this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
/*  57 */     this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
/*  58 */     this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
/*  59 */     this.bipedBodyWear = new ModelRenderer(this, 16, 32);
/*  60 */     this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46304_1_ + 0.25F);
/*  61 */     this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/*  68 */     super.render(entityIn, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale);
/*  69 */     GlStateManager.pushMatrix();
/*     */     
/*  71 */     if (this.isChild) {
/*  72 */       float f = 2.0F;
/*  73 */       GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
/*  74 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/*  75 */       this.bipedLeftLegwear.render(scale);
/*  76 */       this.bipedRightLegwear.render(scale);
/*  77 */       this.bipedLeftArmwear.render(scale);
/*  78 */       this.bipedRightArmwear.render(scale);
/*  79 */       this.bipedBodyWear.render(scale);
/*     */     } else {
/*  81 */       if (entityIn.isSneaking()) {
/*  82 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/*  85 */       this.bipedLeftLegwear.render(scale);
/*  86 */       this.bipedRightLegwear.render(scale);
/*  87 */       this.bipedLeftArmwear.render(scale);
/*  88 */       this.bipedRightArmwear.render(scale);
/*  89 */       this.bipedBodyWear.render(scale);
/*     */     } 
/*     */     
/*  92 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   public void renderDeadmau5Head(float p_178727_1_) {
/*  96 */     copyModelAngles(this.bipedHead, this.bipedDeadmau5Head);
/*  97 */     this.bipedDeadmau5Head.rotationPointX = 0.0F;
/*  98 */     this.bipedDeadmau5Head.rotationPointY = 0.0F;
/*  99 */     this.bipedDeadmau5Head.render(p_178727_1_);
/*     */   }
/*     */   
/*     */   public void renderCape(float p_178728_1_) {
/* 103 */     this.bipedCape.render(p_178728_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 112 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 113 */     copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
/* 114 */     copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
/* 115 */     copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
/* 116 */     copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
/* 117 */     copyModelAngles(this.bipedBody, this.bipedBodyWear);
/*     */   }
/*     */   
/*     */   public void renderRightArm() {
/* 121 */     this.bipedRightArm.render(0.0625F);
/* 122 */     this.bipedRightArmwear.render(0.0625F);
/*     */   }
/*     */   
/*     */   public void renderLeftArm() {
/* 126 */     this.bipedLeftArm.render(0.0625F);
/* 127 */     this.bipedLeftArmwear.render(0.0625F);
/*     */   }
/*     */   
/*     */   public void setInvisible(boolean invisible) {
/* 131 */     super.setInvisible(invisible);
/* 132 */     this.bipedLeftArmwear.showModel = invisible;
/* 133 */     this.bipedRightArmwear.showModel = invisible;
/* 134 */     this.bipedLeftLegwear.showModel = invisible;
/* 135 */     this.bipedRightLegwear.showModel = invisible;
/* 136 */     this.bipedBodyWear.showModel = invisible;
/* 137 */     this.bipedCape.showModel = invisible;
/* 138 */     this.bipedDeadmau5Head.showModel = invisible;
/*     */   }
/*     */   
/*     */   public void postRenderArm(float scale) {
/* 142 */     if (this.smallArms) {
/* 143 */       this.bipedRightArm.rotationPointX++;
/* 144 */       this.bipedRightArm.postRender(scale);
/* 145 */       this.bipedRightArm.rotationPointX--;
/*     */     } else {
/* 147 */       this.bipedRightArm.postRender(scale);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */