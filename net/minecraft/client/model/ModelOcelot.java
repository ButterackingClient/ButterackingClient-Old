/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
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
/*     */ public class ModelOcelot
/*     */   extends ModelBase
/*     */ {
/*     */   ModelRenderer ocelotBackLeftLeg;
/*     */   ModelRenderer ocelotBackRightLeg;
/*     */   ModelRenderer ocelotFrontLeftLeg;
/*     */   ModelRenderer ocelotFrontRightLeg;
/*     */   ModelRenderer ocelotTail;
/*     */   ModelRenderer ocelotTail2;
/*     */   ModelRenderer ocelotHead;
/*     */   ModelRenderer ocelotBody;
/*  49 */   int field_78163_i = 1;
/*     */   
/*     */   public ModelOcelot() {
/*  52 */     setTextureOffset("head.main", 0, 0);
/*  53 */     setTextureOffset("head.nose", 0, 24);
/*  54 */     setTextureOffset("head.ear1", 0, 10);
/*  55 */     setTextureOffset("head.ear2", 6, 10);
/*  56 */     this.ocelotHead = new ModelRenderer(this, "head");
/*  57 */     this.ocelotHead.addBox("main", -2.5F, -2.0F, -3.0F, 5, 4, 5);
/*  58 */     this.ocelotHead.addBox("nose", -1.5F, 0.0F, -4.0F, 3, 2, 2);
/*  59 */     this.ocelotHead.addBox("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2);
/*  60 */     this.ocelotHead.addBox("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2);
/*  61 */     this.ocelotHead.setRotationPoint(0.0F, 15.0F, -9.0F);
/*  62 */     this.ocelotBody = new ModelRenderer(this, 20, 0);
/*  63 */     this.ocelotBody.addBox(-2.0F, 3.0F, -8.0F, 4, 16, 6, 0.0F);
/*  64 */     this.ocelotBody.setRotationPoint(0.0F, 12.0F, -10.0F);
/*  65 */     this.ocelotTail = new ModelRenderer(this, 0, 15);
/*  66 */     this.ocelotTail.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
/*  67 */     this.ocelotTail.rotateAngleX = 0.9F;
/*  68 */     this.ocelotTail.setRotationPoint(0.0F, 15.0F, 8.0F);
/*  69 */     this.ocelotTail2 = new ModelRenderer(this, 4, 15);
/*  70 */     this.ocelotTail2.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
/*  71 */     this.ocelotTail2.setRotationPoint(0.0F, 20.0F, 14.0F);
/*  72 */     this.ocelotBackLeftLeg = new ModelRenderer(this, 8, 13);
/*  73 */     this.ocelotBackLeftLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
/*  74 */     this.ocelotBackLeftLeg.setRotationPoint(1.1F, 18.0F, 5.0F);
/*  75 */     this.ocelotBackRightLeg = new ModelRenderer(this, 8, 13);
/*  76 */     this.ocelotBackRightLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
/*  77 */     this.ocelotBackRightLeg.setRotationPoint(-1.1F, 18.0F, 5.0F);
/*  78 */     this.ocelotFrontLeftLeg = new ModelRenderer(this, 40, 0);
/*  79 */     this.ocelotFrontLeftLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
/*  80 */     this.ocelotFrontLeftLeg.setRotationPoint(1.2F, 13.8F, -5.0F);
/*  81 */     this.ocelotFrontRightLeg = new ModelRenderer(this, 40, 0);
/*  82 */     this.ocelotFrontRightLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
/*  83 */     this.ocelotFrontRightLeg.setRotationPoint(-1.2F, 13.8F, -5.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/*  90 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/*     */     
/*  92 */     if (this.isChild) {
/*  93 */       float f = 2.0F;
/*  94 */       GlStateManager.pushMatrix();
/*  95 */       GlStateManager.scale(1.5F / f, 1.5F / f, 1.5F / f);
/*  96 */       GlStateManager.translate(0.0F, 10.0F * scale, 4.0F * scale);
/*  97 */       this.ocelotHead.render(scale);
/*  98 */       GlStateManager.popMatrix();
/*  99 */       GlStateManager.pushMatrix();
/* 100 */       GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
/* 101 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/* 102 */       this.ocelotBody.render(scale);
/* 103 */       this.ocelotBackLeftLeg.render(scale);
/* 104 */       this.ocelotBackRightLeg.render(scale);
/* 105 */       this.ocelotFrontLeftLeg.render(scale);
/* 106 */       this.ocelotFrontRightLeg.render(scale);
/* 107 */       this.ocelotTail.render(scale);
/* 108 */       this.ocelotTail2.render(scale);
/* 109 */       GlStateManager.popMatrix();
/*     */     } else {
/* 111 */       this.ocelotHead.render(scale);
/* 112 */       this.ocelotBody.render(scale);
/* 113 */       this.ocelotTail.render(scale);
/* 114 */       this.ocelotTail2.render(scale);
/* 115 */       this.ocelotBackLeftLeg.render(scale);
/* 116 */       this.ocelotBackRightLeg.render(scale);
/* 117 */       this.ocelotFrontLeftLeg.render(scale);
/* 118 */       this.ocelotFrontRightLeg.render(scale);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 128 */     this.ocelotHead.rotateAngleX = headPitch / 57.295776F;
/* 129 */     this.ocelotHead.rotateAngleY = netHeadYaw / 57.295776F;
/*     */     
/* 131 */     if (this.field_78163_i != 3) {
/* 132 */       this.ocelotBody.rotateAngleX = 1.5707964F;
/*     */       
/* 134 */       if (this.field_78163_i == 2) {
/* 135 */         this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
/* 136 */         this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 0.3F) * 1.0F * limbSwingAmount;
/* 137 */         this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F + 0.3F) * 1.0F * limbSwingAmount;
/* 138 */         this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.0F * limbSwingAmount;
/* 139 */         this.ocelotTail2.rotateAngleX = 1.7278761F + 0.31415927F * MathHelper.cos(limbSwing) * limbSwingAmount;
/*     */       } else {
/* 141 */         this.ocelotBackLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
/* 142 */         this.ocelotBackRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.0F * limbSwingAmount;
/* 143 */         this.ocelotFrontLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.0F * limbSwingAmount;
/* 144 */         this.ocelotFrontRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
/*     */         
/* 146 */         if (this.field_78163_i == 1) {
/* 147 */           this.ocelotTail2.rotateAngleX = 1.7278761F + 0.7853982F * MathHelper.cos(limbSwing) * limbSwingAmount;
/*     */         } else {
/* 149 */           this.ocelotTail2.rotateAngleX = 1.7278761F + 0.47123894F * MathHelper.cos(limbSwing) * limbSwingAmount;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 160 */     EntityOcelot entityocelot = (EntityOcelot)entitylivingbaseIn;
/* 161 */     this.ocelotBody.rotationPointY = 12.0F;
/* 162 */     this.ocelotBody.rotationPointZ = -10.0F;
/* 163 */     this.ocelotHead.rotationPointY = 15.0F;
/* 164 */     this.ocelotHead.rotationPointZ = -9.0F;
/* 165 */     this.ocelotTail.rotationPointY = 15.0F;
/* 166 */     this.ocelotTail.rotationPointZ = 8.0F;
/* 167 */     this.ocelotTail2.rotationPointY = 20.0F;
/* 168 */     this.ocelotTail2.rotationPointZ = 14.0F;
/* 169 */     this.ocelotFrontRightLeg.rotationPointY = 13.8F;
/* 170 */     this.ocelotFrontRightLeg.rotationPointZ = -5.0F;
/* 171 */     this.ocelotBackRightLeg.rotationPointY = 18.0F;
/* 172 */     this.ocelotBackRightLeg.rotationPointZ = 5.0F;
/* 173 */     this.ocelotTail.rotateAngleX = 0.9F;
/*     */     
/* 175 */     if (entityocelot.isSneaking()) {
/* 176 */       this.ocelotBody.rotationPointY++;
/* 177 */       this.ocelotHead.rotationPointY += 2.0F;
/* 178 */       this.ocelotTail.rotationPointY++;
/* 179 */       this.ocelotTail2.rotationPointY += -4.0F;
/* 180 */       this.ocelotTail2.rotationPointZ += 2.0F;
/* 181 */       this.ocelotTail.rotateAngleX = 1.5707964F;
/* 182 */       this.ocelotTail2.rotateAngleX = 1.5707964F;
/* 183 */       this.field_78163_i = 0;
/* 184 */     } else if (entityocelot.isSprinting()) {
/* 185 */       this.ocelotTail2.rotationPointY = this.ocelotTail.rotationPointY;
/* 186 */       this.ocelotTail2.rotationPointZ += 2.0F;
/* 187 */       this.ocelotTail.rotateAngleX = 1.5707964F;
/* 188 */       this.ocelotTail2.rotateAngleX = 1.5707964F;
/* 189 */       this.field_78163_i = 2;
/* 190 */     } else if (entityocelot.isSitting()) {
/* 191 */       this.ocelotBody.rotateAngleX = 0.7853982F;
/* 192 */       this.ocelotBody.rotationPointY += -4.0F;
/* 193 */       this.ocelotBody.rotationPointZ += 5.0F;
/* 194 */       this.ocelotHead.rotationPointY += -3.3F;
/* 195 */       this.ocelotHead.rotationPointZ++;
/* 196 */       this.ocelotTail.rotationPointY += 8.0F;
/* 197 */       this.ocelotTail.rotationPointZ += -2.0F;
/* 198 */       this.ocelotTail2.rotationPointY += 2.0F;
/* 199 */       this.ocelotTail2.rotationPointZ += -0.8F;
/* 200 */       this.ocelotTail.rotateAngleX = 1.7278761F;
/* 201 */       this.ocelotTail2.rotateAngleX = 2.670354F;
/* 202 */       this.ocelotFrontRightLeg.rotateAngleX = -0.15707964F;
/* 203 */       this.ocelotFrontRightLeg.rotationPointY = 15.8F;
/* 204 */       this.ocelotFrontRightLeg.rotationPointZ = -7.0F;
/* 205 */       this.ocelotBackRightLeg.rotateAngleX = -1.5707964F;
/* 206 */       this.ocelotBackRightLeg.rotationPointY = 21.0F;
/* 207 */       this.ocelotBackRightLeg.rotationPointZ = 1.0F;
/* 208 */       this.field_78163_i = 3;
/*     */     } else {
/* 210 */       this.field_78163_i = 1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelOcelot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */