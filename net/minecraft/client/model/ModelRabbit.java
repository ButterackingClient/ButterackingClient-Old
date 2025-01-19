/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
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
/*     */ public class ModelRabbit
/*     */   extends ModelBase
/*     */ {
/*     */   ModelRenderer rabbitLeftFoot;
/*     */   ModelRenderer rabbitRightFoot;
/*     */   ModelRenderer rabbitLeftThigh;
/*     */   ModelRenderer rabbitRightThigh;
/*     */   ModelRenderer rabbitBody;
/*     */   ModelRenderer rabbitLeftArm;
/*     */   ModelRenderer rabbitRightArm;
/*     */   ModelRenderer rabbitHead;
/*     */   ModelRenderer rabbitRightEar;
/*     */   ModelRenderer rabbitLeftEar;
/*     */   ModelRenderer rabbitTail;
/*     */   ModelRenderer rabbitNose;
/*  69 */   private float field_178701_m = 0.0F;
/*  70 */   private float field_178699_n = 0.0F;
/*     */   
/*     */   public ModelRabbit() {
/*  73 */     setTextureOffset("head.main", 0, 0);
/*  74 */     setTextureOffset("head.nose", 0, 24);
/*  75 */     setTextureOffset("head.ear1", 0, 10);
/*  76 */     setTextureOffset("head.ear2", 6, 10);
/*  77 */     this.rabbitLeftFoot = new ModelRenderer(this, 26, 24);
/*  78 */     this.rabbitLeftFoot.addBox(-1.0F, 5.5F, -3.7F, 2, 1, 7);
/*  79 */     this.rabbitLeftFoot.setRotationPoint(3.0F, 17.5F, 3.7F);
/*  80 */     this.rabbitLeftFoot.mirror = true;
/*  81 */     setRotationOffset(this.rabbitLeftFoot, 0.0F, 0.0F, 0.0F);
/*  82 */     this.rabbitRightFoot = new ModelRenderer(this, 8, 24);
/*  83 */     this.rabbitRightFoot.addBox(-1.0F, 5.5F, -3.7F, 2, 1, 7);
/*  84 */     this.rabbitRightFoot.setRotationPoint(-3.0F, 17.5F, 3.7F);
/*  85 */     this.rabbitRightFoot.mirror = true;
/*  86 */     setRotationOffset(this.rabbitRightFoot, 0.0F, 0.0F, 0.0F);
/*  87 */     this.rabbitLeftThigh = new ModelRenderer(this, 30, 15);
/*  88 */     this.rabbitLeftThigh.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 5);
/*  89 */     this.rabbitLeftThigh.setRotationPoint(3.0F, 17.5F, 3.7F);
/*  90 */     this.rabbitLeftThigh.mirror = true;
/*  91 */     setRotationOffset(this.rabbitLeftThigh, -0.34906584F, 0.0F, 0.0F);
/*  92 */     this.rabbitRightThigh = new ModelRenderer(this, 16, 15);
/*  93 */     this.rabbitRightThigh.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 5);
/*  94 */     this.rabbitRightThigh.setRotationPoint(-3.0F, 17.5F, 3.7F);
/*  95 */     this.rabbitRightThigh.mirror = true;
/*  96 */     setRotationOffset(this.rabbitRightThigh, -0.34906584F, 0.0F, 0.0F);
/*  97 */     this.rabbitBody = new ModelRenderer(this, 0, 0);
/*  98 */     this.rabbitBody.addBox(-3.0F, -2.0F, -10.0F, 6, 5, 10);
/*  99 */     this.rabbitBody.setRotationPoint(0.0F, 19.0F, 8.0F);
/* 100 */     this.rabbitBody.mirror = true;
/* 101 */     setRotationOffset(this.rabbitBody, -0.34906584F, 0.0F, 0.0F);
/* 102 */     this.rabbitLeftArm = new ModelRenderer(this, 8, 15);
/* 103 */     this.rabbitLeftArm.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2);
/* 104 */     this.rabbitLeftArm.setRotationPoint(3.0F, 17.0F, -1.0F);
/* 105 */     this.rabbitLeftArm.mirror = true;
/* 106 */     setRotationOffset(this.rabbitLeftArm, -0.17453292F, 0.0F, 0.0F);
/* 107 */     this.rabbitRightArm = new ModelRenderer(this, 0, 15);
/* 108 */     this.rabbitRightArm.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2);
/* 109 */     this.rabbitRightArm.setRotationPoint(-3.0F, 17.0F, -1.0F);
/* 110 */     this.rabbitRightArm.mirror = true;
/* 111 */     setRotationOffset(this.rabbitRightArm, -0.17453292F, 0.0F, 0.0F);
/* 112 */     this.rabbitHead = new ModelRenderer(this, 32, 0);
/* 113 */     this.rabbitHead.addBox(-2.5F, -4.0F, -5.0F, 5, 4, 5);
/* 114 */     this.rabbitHead.setRotationPoint(0.0F, 16.0F, -1.0F);
/* 115 */     this.rabbitHead.mirror = true;
/* 116 */     setRotationOffset(this.rabbitHead, 0.0F, 0.0F, 0.0F);
/* 117 */     this.rabbitRightEar = new ModelRenderer(this, 52, 0);
/* 118 */     this.rabbitRightEar.addBox(-2.5F, -9.0F, -1.0F, 2, 5, 1);
/* 119 */     this.rabbitRightEar.setRotationPoint(0.0F, 16.0F, -1.0F);
/* 120 */     this.rabbitRightEar.mirror = true;
/* 121 */     setRotationOffset(this.rabbitRightEar, 0.0F, -0.2617994F, 0.0F);
/* 122 */     this.rabbitLeftEar = new ModelRenderer(this, 58, 0);
/* 123 */     this.rabbitLeftEar.addBox(0.5F, -9.0F, -1.0F, 2, 5, 1);
/* 124 */     this.rabbitLeftEar.setRotationPoint(0.0F, 16.0F, -1.0F);
/* 125 */     this.rabbitLeftEar.mirror = true;
/* 126 */     setRotationOffset(this.rabbitLeftEar, 0.0F, 0.2617994F, 0.0F);
/* 127 */     this.rabbitTail = new ModelRenderer(this, 52, 6);
/* 128 */     this.rabbitTail.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2);
/* 129 */     this.rabbitTail.setRotationPoint(0.0F, 20.0F, 7.0F);
/* 130 */     this.rabbitTail.mirror = true;
/* 131 */     setRotationOffset(this.rabbitTail, -0.3490659F, 0.0F, 0.0F);
/* 132 */     this.rabbitNose = new ModelRenderer(this, 32, 9);
/* 133 */     this.rabbitNose.addBox(-0.5F, -2.5F, -5.5F, 1, 1, 1);
/* 134 */     this.rabbitNose.setRotationPoint(0.0F, 16.0F, -1.0F);
/* 135 */     this.rabbitNose.mirror = true;
/* 136 */     setRotationOffset(this.rabbitNose, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   private void setRotationOffset(ModelRenderer p_178691_1_, float p_178691_2_, float p_178691_3_, float p_178691_4_) {
/* 140 */     p_178691_1_.rotateAngleX = p_178691_2_;
/* 141 */     p_178691_1_.rotateAngleY = p_178691_3_;
/* 142 */     p_178691_1_.rotateAngleZ = p_178691_4_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 149 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/*     */     
/* 151 */     if (this.isChild) {
/* 152 */       float f = 2.0F;
/* 153 */       GlStateManager.pushMatrix();
/* 154 */       GlStateManager.translate(0.0F, 5.0F * scale, 2.0F * scale);
/* 155 */       this.rabbitHead.render(scale);
/* 156 */       this.rabbitLeftEar.render(scale);
/* 157 */       this.rabbitRightEar.render(scale);
/* 158 */       this.rabbitNose.render(scale);
/* 159 */       GlStateManager.popMatrix();
/* 160 */       GlStateManager.pushMatrix();
/* 161 */       GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
/* 162 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/* 163 */       this.rabbitLeftFoot.render(scale);
/* 164 */       this.rabbitRightFoot.render(scale);
/* 165 */       this.rabbitLeftThigh.render(scale);
/* 166 */       this.rabbitRightThigh.render(scale);
/* 167 */       this.rabbitBody.render(scale);
/* 168 */       this.rabbitLeftArm.render(scale);
/* 169 */       this.rabbitRightArm.render(scale);
/* 170 */       this.rabbitTail.render(scale);
/* 171 */       GlStateManager.popMatrix();
/*     */     } else {
/* 173 */       this.rabbitLeftFoot.render(scale);
/* 174 */       this.rabbitRightFoot.render(scale);
/* 175 */       this.rabbitLeftThigh.render(scale);
/* 176 */       this.rabbitRightThigh.render(scale);
/* 177 */       this.rabbitBody.render(scale);
/* 178 */       this.rabbitLeftArm.render(scale);
/* 179 */       this.rabbitRightArm.render(scale);
/* 180 */       this.rabbitHead.render(scale);
/* 181 */       this.rabbitRightEar.render(scale);
/* 182 */       this.rabbitLeftEar.render(scale);
/* 183 */       this.rabbitTail.render(scale);
/* 184 */       this.rabbitNose.render(scale);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 194 */     float f = ageInTicks - entityIn.ticksExisted;
/* 195 */     EntityRabbit entityrabbit = (EntityRabbit)entityIn;
/* 196 */     this.rabbitLeftEar.rotateAngleX = headPitch * 0.017453292F;
/* 197 */     this.rabbitHead.rotateAngleY = netHeadYaw * 0.017453292F;
/* 198 */     this.rabbitNose.rotateAngleY -= 0.2617994F;
/* 199 */     this.rabbitNose.rotateAngleY += 0.2617994F;
/* 200 */     this.field_178701_m = MathHelper.sin(entityrabbit.func_175521_o(f) * 3.1415927F);
/* 201 */     this.rabbitRightThigh.rotateAngleX = (this.field_178701_m * 50.0F - 21.0F) * 0.017453292F;
/* 202 */     this.rabbitRightFoot.rotateAngleX = this.field_178701_m * 50.0F * 0.017453292F;
/* 203 */     this.rabbitRightArm.rotateAngleX = (this.field_178701_m * -40.0F - 11.0F) * 0.017453292F;
/*     */   }
/*     */   
/*     */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {}
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelRabbit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */