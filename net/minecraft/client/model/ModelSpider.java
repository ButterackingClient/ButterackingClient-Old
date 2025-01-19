/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
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
/*     */ public class ModelSpider
/*     */   extends ModelBase
/*     */ {
/*     */   public ModelRenderer spiderHead;
/*     */   public ModelRenderer spiderNeck;
/*     */   public ModelRenderer spiderBody;
/*     */   public ModelRenderer spiderLeg1;
/*     */   public ModelRenderer spiderLeg2;
/*     */   public ModelRenderer spiderLeg3;
/*     */   public ModelRenderer spiderLeg4;
/*     */   public ModelRenderer spiderLeg5;
/*     */   public ModelRenderer spiderLeg6;
/*     */   public ModelRenderer spiderLeg7;
/*     */   public ModelRenderer spiderLeg8;
/*     */   
/*     */   public ModelSpider() {
/*  63 */     float f = 0.0F;
/*  64 */     int i = 15;
/*  65 */     this.spiderHead = new ModelRenderer(this, 32, 4);
/*  66 */     this.spiderHead.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, f);
/*  67 */     this.spiderHead.setRotationPoint(0.0F, i, -3.0F);
/*  68 */     this.spiderNeck = new ModelRenderer(this, 0, 0);
/*  69 */     this.spiderNeck.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, f);
/*  70 */     this.spiderNeck.setRotationPoint(0.0F, i, 0.0F);
/*  71 */     this.spiderBody = new ModelRenderer(this, 0, 12);
/*  72 */     this.spiderBody.addBox(-5.0F, -4.0F, -6.0F, 10, 8, 12, f);
/*  73 */     this.spiderBody.setRotationPoint(0.0F, i, 9.0F);
/*  74 */     this.spiderLeg1 = new ModelRenderer(this, 18, 0);
/*  75 */     this.spiderLeg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
/*  76 */     this.spiderLeg1.setRotationPoint(-4.0F, i, 2.0F);
/*  77 */     this.spiderLeg2 = new ModelRenderer(this, 18, 0);
/*  78 */     this.spiderLeg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
/*  79 */     this.spiderLeg2.setRotationPoint(4.0F, i, 2.0F);
/*  80 */     this.spiderLeg3 = new ModelRenderer(this, 18, 0);
/*  81 */     this.spiderLeg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
/*  82 */     this.spiderLeg3.setRotationPoint(-4.0F, i, 1.0F);
/*  83 */     this.spiderLeg4 = new ModelRenderer(this, 18, 0);
/*  84 */     this.spiderLeg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
/*  85 */     this.spiderLeg4.setRotationPoint(4.0F, i, 1.0F);
/*  86 */     this.spiderLeg5 = new ModelRenderer(this, 18, 0);
/*  87 */     this.spiderLeg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
/*  88 */     this.spiderLeg5.setRotationPoint(-4.0F, i, 0.0F);
/*  89 */     this.spiderLeg6 = new ModelRenderer(this, 18, 0);
/*  90 */     this.spiderLeg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
/*  91 */     this.spiderLeg6.setRotationPoint(4.0F, i, 0.0F);
/*  92 */     this.spiderLeg7 = new ModelRenderer(this, 18, 0);
/*  93 */     this.spiderLeg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2, f);
/*  94 */     this.spiderLeg7.setRotationPoint(-4.0F, i, -1.0F);
/*  95 */     this.spiderLeg8 = new ModelRenderer(this, 18, 0);
/*  96 */     this.spiderLeg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2, f);
/*  97 */     this.spiderLeg8.setRotationPoint(4.0F, i, -1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 104 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/* 105 */     this.spiderHead.render(scale);
/* 106 */     this.spiderNeck.render(scale);
/* 107 */     this.spiderBody.render(scale);
/* 108 */     this.spiderLeg1.render(scale);
/* 109 */     this.spiderLeg2.render(scale);
/* 110 */     this.spiderLeg3.render(scale);
/* 111 */     this.spiderLeg4.render(scale);
/* 112 */     this.spiderLeg5.render(scale);
/* 113 */     this.spiderLeg6.render(scale);
/* 114 */     this.spiderLeg7.render(scale);
/* 115 */     this.spiderLeg8.render(scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 124 */     this.spiderHead.rotateAngleY = netHeadYaw / 57.295776F;
/* 125 */     this.spiderHead.rotateAngleX = headPitch / 57.295776F;
/* 126 */     float f = 0.7853982F;
/* 127 */     this.spiderLeg1.rotateAngleZ = -f;
/* 128 */     this.spiderLeg2.rotateAngleZ = f;
/* 129 */     this.spiderLeg3.rotateAngleZ = -f * 0.74F;
/* 130 */     this.spiderLeg4.rotateAngleZ = f * 0.74F;
/* 131 */     this.spiderLeg5.rotateAngleZ = -f * 0.74F;
/* 132 */     this.spiderLeg6.rotateAngleZ = f * 0.74F;
/* 133 */     this.spiderLeg7.rotateAngleZ = -f;
/* 134 */     this.spiderLeg8.rotateAngleZ = f;
/* 135 */     float f1 = -0.0F;
/* 136 */     float f2 = 0.3926991F;
/* 137 */     this.spiderLeg1.rotateAngleY = f2 * 2.0F + f1;
/* 138 */     this.spiderLeg2.rotateAngleY = -f2 * 2.0F - f1;
/* 139 */     this.spiderLeg3.rotateAngleY = f2 * 1.0F + f1;
/* 140 */     this.spiderLeg4.rotateAngleY = -f2 * 1.0F - f1;
/* 141 */     this.spiderLeg5.rotateAngleY = -f2 * 1.0F + f1;
/* 142 */     this.spiderLeg6.rotateAngleY = f2 * 1.0F - f1;
/* 143 */     this.spiderLeg7.rotateAngleY = -f2 * 2.0F + f1;
/* 144 */     this.spiderLeg8.rotateAngleY = f2 * 2.0F - f1;
/* 145 */     float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
/* 146 */     float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * limbSwingAmount;
/* 147 */     float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * limbSwingAmount;
/* 148 */     float f6 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 4.712389F) * 0.4F) * limbSwingAmount;
/* 149 */     float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
/* 150 */     float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 3.1415927F) * 0.4F) * limbSwingAmount;
/* 151 */     float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 1.5707964F) * 0.4F) * limbSwingAmount;
/* 152 */     float f10 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 4.712389F) * 0.4F) * limbSwingAmount;
/* 153 */     this.spiderLeg1.rotateAngleY += f3;
/* 154 */     this.spiderLeg2.rotateAngleY += -f3;
/* 155 */     this.spiderLeg3.rotateAngleY += f4;
/* 156 */     this.spiderLeg4.rotateAngleY += -f4;
/* 157 */     this.spiderLeg5.rotateAngleY += f5;
/* 158 */     this.spiderLeg6.rotateAngleY += -f5;
/* 159 */     this.spiderLeg7.rotateAngleY += f6;
/* 160 */     this.spiderLeg8.rotateAngleY += -f6;
/* 161 */     this.spiderLeg1.rotateAngleZ += f7;
/* 162 */     this.spiderLeg2.rotateAngleZ += -f7;
/* 163 */     this.spiderLeg3.rotateAngleZ += f8;
/* 164 */     this.spiderLeg4.rotateAngleZ += -f8;
/* 165 */     this.spiderLeg5.rotateAngleZ += f9;
/* 166 */     this.spiderLeg6.rotateAngleZ += -f9;
/* 167 */     this.spiderLeg7.rotateAngleZ += f10;
/* 168 */     this.spiderLeg8.rotateAngleZ += -f10;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelSpider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */