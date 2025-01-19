/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
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
/*     */ public class ModelIronGolem
/*     */   extends ModelBase
/*     */ {
/*     */   public ModelRenderer ironGolemHead;
/*     */   public ModelRenderer ironGolemBody;
/*     */   public ModelRenderer ironGolemRightArm;
/*     */   public ModelRenderer ironGolemLeftArm;
/*     */   public ModelRenderer ironGolemLeftLeg;
/*     */   public ModelRenderer ironGolemRightLeg;
/*     */   
/*     */   public ModelIronGolem() {
/*  39 */     this(0.0F);
/*     */   }
/*     */   
/*     */   public ModelIronGolem(float p_i1161_1_) {
/*  43 */     this(p_i1161_1_, -7.0F);
/*     */   }
/*     */   
/*     */   public ModelIronGolem(float p_i46362_1_, float p_i46362_2_) {
/*  47 */     int i = 128;
/*  48 */     int j = 128;
/*  49 */     this.ironGolemHead = (new ModelRenderer(this)).setTextureSize(i, j);
/*  50 */     this.ironGolemHead.setRotationPoint(0.0F, 0.0F + p_i46362_2_, -2.0F);
/*  51 */     this.ironGolemHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, p_i46362_1_);
/*  52 */     this.ironGolemHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, p_i46362_1_);
/*  53 */     this.ironGolemBody = (new ModelRenderer(this)).setTextureSize(i, j);
/*  54 */     this.ironGolemBody.setRotationPoint(0.0F, 0.0F + p_i46362_2_, 0.0F);
/*  55 */     this.ironGolemBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, p_i46362_1_);
/*  56 */     this.ironGolemBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, p_i46362_1_ + 0.5F);
/*  57 */     this.ironGolemRightArm = (new ModelRenderer(this)).setTextureSize(i, j);
/*  58 */     this.ironGolemRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
/*  59 */     this.ironGolemRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, p_i46362_1_);
/*  60 */     this.ironGolemLeftArm = (new ModelRenderer(this)).setTextureSize(i, j);
/*  61 */     this.ironGolemLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
/*  62 */     this.ironGolemLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, p_i46362_1_);
/*  63 */     this.ironGolemLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(i, j);
/*  64 */     this.ironGolemLeftLeg.setRotationPoint(-4.0F, 18.0F + p_i46362_2_, 0.0F);
/*  65 */     this.ironGolemLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i46362_1_);
/*  66 */     this.ironGolemRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(i, j);
/*  67 */     this.ironGolemRightLeg.mirror = true;
/*  68 */     this.ironGolemRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + p_i46362_2_, 0.0F);
/*  69 */     this.ironGolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, p_i46362_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/*  76 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/*  77 */     this.ironGolemHead.render(scale);
/*  78 */     this.ironGolemBody.render(scale);
/*  79 */     this.ironGolemLeftLeg.render(scale);
/*  80 */     this.ironGolemRightLeg.render(scale);
/*  81 */     this.ironGolemRightArm.render(scale);
/*  82 */     this.ironGolemLeftArm.render(scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*  91 */     this.ironGolemHead.rotateAngleY = netHeadYaw / 57.295776F;
/*  92 */     this.ironGolemHead.rotateAngleX = headPitch / 57.295776F;
/*  93 */     this.ironGolemLeftLeg.rotateAngleX = -1.5F * func_78172_a(limbSwing, 13.0F) * limbSwingAmount;
/*  94 */     this.ironGolemRightLeg.rotateAngleX = 1.5F * func_78172_a(limbSwing, 13.0F) * limbSwingAmount;
/*  95 */     this.ironGolemLeftLeg.rotateAngleY = 0.0F;
/*  96 */     this.ironGolemRightLeg.rotateAngleY = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 104 */     EntityIronGolem entityirongolem = (EntityIronGolem)entitylivingbaseIn;
/* 105 */     int i = entityirongolem.getAttackTimer();
/*     */     
/* 107 */     if (i > 0) {
/* 108 */       this.ironGolemRightArm.rotateAngleX = -2.0F + 1.5F * func_78172_a(i - partialTickTime, 10.0F);
/* 109 */       this.ironGolemLeftArm.rotateAngleX = -2.0F + 1.5F * func_78172_a(i - partialTickTime, 10.0F);
/*     */     } else {
/* 111 */       int j = entityirongolem.getHoldRoseTick();
/*     */       
/* 113 */       if (j > 0) {
/* 114 */         this.ironGolemRightArm.rotateAngleX = -0.8F + 0.025F * func_78172_a(j, 70.0F);
/* 115 */         this.ironGolemLeftArm.rotateAngleX = 0.0F;
/*     */       } else {
/* 117 */         this.ironGolemRightArm.rotateAngleX = (-0.2F + 1.5F * func_78172_a(p_78086_2_, 13.0F)) * p_78086_3_;
/* 118 */         this.ironGolemLeftArm.rotateAngleX = (-0.2F - 1.5F * func_78172_a(p_78086_2_, 13.0F)) * p_78086_3_;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private float func_78172_a(float p_78172_1_, float p_78172_2_) {
/* 124 */     return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / p_78172_2_ * 0.25F;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelIronGolem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */