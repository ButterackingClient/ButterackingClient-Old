/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelWolf
/*     */   extends ModelBase
/*     */ {
/*     */   public ModelRenderer wolfHeadMain;
/*     */   public ModelRenderer wolfBody;
/*     */   public ModelRenderer wolfLeg1;
/*     */   public ModelRenderer wolfLeg2;
/*     */   public ModelRenderer wolfLeg3;
/*     */   public ModelRenderer wolfLeg4;
/*     */   ModelRenderer wolfTail;
/*     */   ModelRenderer wolfMane;
/*     */   
/*     */   public ModelWolf() {
/*  51 */     float f = 0.0F;
/*  52 */     float f1 = 13.5F;
/*  53 */     this.wolfHeadMain = new ModelRenderer(this, 0, 0);
/*  54 */     this.wolfHeadMain.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 4, f);
/*  55 */     this.wolfHeadMain.setRotationPoint(-1.0F, f1, -7.0F);
/*  56 */     this.wolfBody = new ModelRenderer(this, 18, 14);
/*  57 */     this.wolfBody.addBox(-4.0F, -2.0F, -3.0F, 6, 9, 6, f);
/*  58 */     this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
/*  59 */     this.wolfMane = new ModelRenderer(this, 21, 0);
/*  60 */     this.wolfMane.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 7, f);
/*  61 */     this.wolfMane.setRotationPoint(-1.0F, 14.0F, 2.0F);
/*  62 */     this.wolfLeg1 = new ModelRenderer(this, 0, 18);
/*  63 */     this.wolfLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
/*  64 */     this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
/*  65 */     this.wolfLeg2 = new ModelRenderer(this, 0, 18);
/*  66 */     this.wolfLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
/*  67 */     this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
/*  68 */     this.wolfLeg3 = new ModelRenderer(this, 0, 18);
/*  69 */     this.wolfLeg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
/*  70 */     this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
/*  71 */     this.wolfLeg4 = new ModelRenderer(this, 0, 18);
/*  72 */     this.wolfLeg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
/*  73 */     this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
/*  74 */     this.wolfTail = new ModelRenderer(this, 9, 18);
/*  75 */     this.wolfTail.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
/*  76 */     this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
/*  77 */     this.wolfHeadMain.setTextureOffset(16, 14).addBox(-3.0F, -5.0F, 0.0F, 2, 2, 1, f);
/*  78 */     this.wolfHeadMain.setTextureOffset(16, 14).addBox(1.0F, -5.0F, 0.0F, 2, 2, 1, f);
/*  79 */     this.wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5F, 0.0F, -5.0F, 3, 3, 4, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/*  86 */     super.render(entityIn, p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale);
/*  87 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/*     */     
/*  89 */     if (this.isChild) {
/*  90 */       float f = 2.0F;
/*  91 */       GlStateManager.pushMatrix();
/*  92 */       GlStateManager.translate(0.0F, 5.0F * scale, 2.0F * scale);
/*  93 */       this.wolfHeadMain.renderWithRotation(scale);
/*  94 */       GlStateManager.popMatrix();
/*  95 */       GlStateManager.pushMatrix();
/*  96 */       GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
/*  97 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/*  98 */       this.wolfBody.render(scale);
/*  99 */       this.wolfLeg1.render(scale);
/* 100 */       this.wolfLeg2.render(scale);
/* 101 */       this.wolfLeg3.render(scale);
/* 102 */       this.wolfLeg4.render(scale);
/* 103 */       this.wolfTail.renderWithRotation(scale);
/* 104 */       this.wolfMane.render(scale);
/* 105 */       GlStateManager.popMatrix();
/*     */     } else {
/* 107 */       this.wolfHeadMain.renderWithRotation(scale);
/* 108 */       this.wolfBody.render(scale);
/* 109 */       this.wolfLeg1.render(scale);
/* 110 */       this.wolfLeg2.render(scale);
/* 111 */       this.wolfLeg3.render(scale);
/* 112 */       this.wolfLeg4.render(scale);
/* 113 */       this.wolfTail.renderWithRotation(scale);
/* 114 */       this.wolfMane.render(scale);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 123 */     EntityWolf entitywolf = (EntityWolf)entitylivingbaseIn;
/*     */     
/* 125 */     if (entitywolf.isAngry()) {
/* 126 */       this.wolfTail.rotateAngleY = 0.0F;
/*     */     } else {
/* 128 */       this.wolfTail.rotateAngleY = MathHelper.cos(p_78086_2_ * 0.6662F) * 1.4F * p_78086_3_;
/*     */     } 
/*     */     
/* 131 */     if (entitywolf.isSitting()) {
/* 132 */       this.wolfMane.setRotationPoint(-1.0F, 16.0F, -3.0F);
/* 133 */       this.wolfMane.rotateAngleX = 1.2566371F;
/* 134 */       this.wolfMane.rotateAngleY = 0.0F;
/* 135 */       this.wolfBody.setRotationPoint(0.0F, 18.0F, 0.0F);
/* 136 */       this.wolfBody.rotateAngleX = 0.7853982F;
/* 137 */       this.wolfTail.setRotationPoint(-1.0F, 21.0F, 6.0F);
/* 138 */       this.wolfLeg1.setRotationPoint(-2.5F, 22.0F, 2.0F);
/* 139 */       this.wolfLeg1.rotateAngleX = 4.712389F;
/* 140 */       this.wolfLeg2.setRotationPoint(0.5F, 22.0F, 2.0F);
/* 141 */       this.wolfLeg2.rotateAngleX = 4.712389F;
/* 142 */       this.wolfLeg3.rotateAngleX = 5.811947F;
/* 143 */       this.wolfLeg3.setRotationPoint(-2.49F, 17.0F, -4.0F);
/* 144 */       this.wolfLeg4.rotateAngleX = 5.811947F;
/* 145 */       this.wolfLeg4.setRotationPoint(0.51F, 17.0F, -4.0F);
/*     */     } else {
/* 147 */       this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
/* 148 */       this.wolfBody.rotateAngleX = 1.5707964F;
/* 149 */       this.wolfMane.setRotationPoint(-1.0F, 14.0F, -3.0F);
/* 150 */       this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
/* 151 */       this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
/* 152 */       this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
/* 153 */       this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
/* 154 */       this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
/* 155 */       this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
/* 156 */       this.wolfLeg1.rotateAngleX = MathHelper.cos(p_78086_2_ * 0.6662F) * 1.4F * p_78086_3_;
/* 157 */       this.wolfLeg2.rotateAngleX = MathHelper.cos(p_78086_2_ * 0.6662F + 3.1415927F) * 1.4F * p_78086_3_;
/* 158 */       this.wolfLeg3.rotateAngleX = MathHelper.cos(p_78086_2_ * 0.6662F + 3.1415927F) * 1.4F * p_78086_3_;
/* 159 */       this.wolfLeg4.rotateAngleX = MathHelper.cos(p_78086_2_ * 0.6662F) * 1.4F * p_78086_3_;
/*     */     } 
/*     */     
/* 162 */     this.wolfHeadMain.rotateAngleZ = entitywolf.getInterestedAngle(partialTickTime) + entitywolf.getShakeAngle(partialTickTime, 0.0F);
/* 163 */     this.wolfMane.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.08F);
/* 164 */     this.wolfBody.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.16F);
/* 165 */     this.wolfTail.rotateAngleZ = entitywolf.getShakeAngle(partialTickTime, -0.2F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 174 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 175 */     this.wolfHeadMain.rotateAngleX = headPitch / 57.295776F;
/* 176 */     this.wolfHeadMain.rotateAngleY = netHeadYaw / 57.295776F;
/* 177 */     this.wolfTail.rotateAngleX = ageInTicks;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelWolf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */