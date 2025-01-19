/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityBat;
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
/*     */ public class ModelBat
/*     */   extends ModelBase
/*     */ {
/*     */   private ModelRenderer batHead;
/*     */   private ModelRenderer batBody;
/*     */   private ModelRenderer batRightWing;
/*     */   private ModelRenderer batLeftWing;
/*     */   private ModelRenderer batOuterRightWing;
/*     */   private ModelRenderer batOuterLeftWing;
/*     */   
/*     */   public ModelBat() {
/*  36 */     this.textureWidth = 64;
/*  37 */     this.textureHeight = 64;
/*  38 */     this.batHead = new ModelRenderer(this, 0, 0);
/*  39 */     this.batHead.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
/*  40 */     ModelRenderer modelrenderer = new ModelRenderer(this, 24, 0);
/*  41 */     modelrenderer.addBox(-4.0F, -6.0F, -2.0F, 3, 4, 1);
/*  42 */     this.batHead.addChild(modelrenderer);
/*  43 */     ModelRenderer modelrenderer1 = new ModelRenderer(this, 24, 0);
/*  44 */     modelrenderer1.mirror = true;
/*  45 */     modelrenderer1.addBox(1.0F, -6.0F, -2.0F, 3, 4, 1);
/*  46 */     this.batHead.addChild(modelrenderer1);
/*  47 */     this.batBody = new ModelRenderer(this, 0, 16);
/*  48 */     this.batBody.addBox(-3.0F, 4.0F, -3.0F, 6, 12, 6);
/*  49 */     this.batBody.setTextureOffset(0, 34).addBox(-5.0F, 16.0F, 0.0F, 10, 6, 1);
/*  50 */     this.batRightWing = new ModelRenderer(this, 42, 0);
/*  51 */     this.batRightWing.addBox(-12.0F, 1.0F, 1.5F, 10, 16, 1);
/*  52 */     this.batOuterRightWing = new ModelRenderer(this, 24, 16);
/*  53 */     this.batOuterRightWing.setRotationPoint(-12.0F, 1.0F, 1.5F);
/*  54 */     this.batOuterRightWing.addBox(-8.0F, 1.0F, 0.0F, 8, 12, 1);
/*  55 */     this.batLeftWing = new ModelRenderer(this, 42, 0);
/*  56 */     this.batLeftWing.mirror = true;
/*  57 */     this.batLeftWing.addBox(2.0F, 1.0F, 1.5F, 10, 16, 1);
/*  58 */     this.batOuterLeftWing = new ModelRenderer(this, 24, 16);
/*  59 */     this.batOuterLeftWing.mirror = true;
/*  60 */     this.batOuterLeftWing.setRotationPoint(12.0F, 1.0F, 1.5F);
/*  61 */     this.batOuterLeftWing.addBox(0.0F, 1.0F, 0.0F, 8, 12, 1);
/*  62 */     this.batBody.addChild(this.batRightWing);
/*  63 */     this.batBody.addChild(this.batLeftWing);
/*  64 */     this.batRightWing.addChild(this.batOuterRightWing);
/*  65 */     this.batLeftWing.addChild(this.batOuterLeftWing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/*  72 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/*  73 */     this.batHead.render(scale);
/*  74 */     this.batBody.render(scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*  83 */     if (((EntityBat)entityIn).getIsBatHanging()) {
/*  84 */       float f = 57.295776F;
/*  85 */       this.batHead.rotateAngleX = headPitch / 57.295776F;
/*  86 */       this.batHead.rotateAngleY = 3.1415927F - netHeadYaw / 57.295776F;
/*  87 */       this.batHead.rotateAngleZ = 3.1415927F;
/*  88 */       this.batHead.setRotationPoint(0.0F, -2.0F, 0.0F);
/*  89 */       this.batRightWing.setRotationPoint(-3.0F, 0.0F, 3.0F);
/*  90 */       this.batLeftWing.setRotationPoint(3.0F, 0.0F, 3.0F);
/*  91 */       this.batBody.rotateAngleX = 3.1415927F;
/*  92 */       this.batRightWing.rotateAngleX = -0.15707964F;
/*  93 */       this.batRightWing.rotateAngleY = -1.2566371F;
/*  94 */       this.batOuterRightWing.rotateAngleY = -1.7278761F;
/*  95 */       this.batLeftWing.rotateAngleX = this.batRightWing.rotateAngleX;
/*  96 */       this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
/*  97 */       this.batOuterLeftWing.rotateAngleY = -this.batOuterRightWing.rotateAngleY;
/*     */     } else {
/*  99 */       float f1 = 57.295776F;
/* 100 */       this.batHead.rotateAngleX = headPitch / 57.295776F;
/* 101 */       this.batHead.rotateAngleY = netHeadYaw / 57.295776F;
/* 102 */       this.batHead.rotateAngleZ = 0.0F;
/* 103 */       this.batHead.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 104 */       this.batRightWing.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 105 */       this.batLeftWing.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 106 */       this.batBody.rotateAngleX = 0.7853982F + MathHelper.cos(ageInTicks * 0.1F) * 0.15F;
/* 107 */       this.batBody.rotateAngleY = 0.0F;
/* 108 */       this.batRightWing.rotateAngleY = MathHelper.cos(ageInTicks * 1.3F) * 3.1415927F * 0.25F;
/* 109 */       this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
/* 110 */       this.batRightWing.rotateAngleY *= 0.5F;
/* 111 */       this.batOuterLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY * 0.5F;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelBat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */