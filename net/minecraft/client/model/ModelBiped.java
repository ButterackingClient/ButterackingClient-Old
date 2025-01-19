/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
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
/*     */ public class ModelBiped
/*     */   extends ModelBase
/*     */ {
/*     */   public ModelRenderer bipedHead;
/*     */   public ModelRenderer bipedHeadwear;
/*     */   public ModelRenderer bipedBody;
/*     */   public ModelRenderer bipedRightArm;
/*     */   public ModelRenderer bipedLeftArm;
/*     */   public ModelRenderer bipedRightLeg;
/*     */   public ModelRenderer bipedLeftLeg;
/*     */   public int heldItemLeft;
/*     */   public int heldItemRight;
/*     */   public boolean isSneak;
/*     */   public boolean aimedBow;
/*     */   
/*     */   public ModelBiped() {
/*  53 */     this(0.0F);
/*     */   }
/*     */   
/*     */   public ModelBiped(float modelSize) {
/*  57 */     this(modelSize, 0.0F, 64, 32);
/*     */   }
/*     */   
/*     */   public ModelBiped(float modelSize, float p_i1149_2_, int textureWidthIn, int textureHeightIn) {
/*  61 */     this.textureWidth = textureWidthIn;
/*  62 */     this.textureHeight = textureHeightIn;
/*  63 */     this.bipedHead = new ModelRenderer(this, 0, 0);
/*  64 */     this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
/*  65 */     this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
/*  66 */     this.bipedHeadwear = new ModelRenderer(this, 32, 0);
/*  67 */     this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize + 0.5F);
/*  68 */     this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
/*  69 */     this.bipedBody = new ModelRenderer(this, 16, 16);
/*  70 */     this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
/*  71 */     this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
/*  72 */     this.bipedRightArm = new ModelRenderer(this, 40, 16);
/*  73 */     this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
/*  74 */     this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i1149_2_, 0.0F);
/*  75 */     this.bipedLeftArm = new ModelRenderer(this, 40, 16);
/*  76 */     this.bipedLeftArm.mirror = true;
/*  77 */     this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
/*  78 */     this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i1149_2_, 0.0F);
/*  79 */     this.bipedRightLeg = new ModelRenderer(this, 0, 16);
/*  80 */     this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
/*  81 */     this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + p_i1149_2_, 0.0F);
/*  82 */     this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
/*  83 */     this.bipedLeftLeg.mirror = true;
/*  84 */     this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
/*  85 */     this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + p_i1149_2_, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/*  92 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/*  93 */     GlStateManager.pushMatrix();
/*     */     
/*  95 */     if (this.isChild) {
/*  96 */       float f = 2.0F;
/*  97 */       GlStateManager.scale(1.5F / f, 1.5F / f, 1.5F / f);
/*  98 */       GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
/*  99 */       this.bipedHead.render(scale);
/* 100 */       GlStateManager.popMatrix();
/* 101 */       GlStateManager.pushMatrix();
/* 102 */       GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
/* 103 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/* 104 */       this.bipedBody.render(scale);
/* 105 */       this.bipedRightArm.render(scale);
/* 106 */       this.bipedLeftArm.render(scale);
/* 107 */       this.bipedRightLeg.render(scale);
/* 108 */       this.bipedLeftLeg.render(scale);
/* 109 */       this.bipedHeadwear.render(scale);
/*     */     } else {
/* 111 */       if (entityIn.isSneaking()) {
/* 112 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/* 115 */       this.bipedHead.render(scale);
/* 116 */       this.bipedBody.render(scale);
/* 117 */       this.bipedRightArm.render(scale);
/* 118 */       this.bipedLeftArm.render(scale);
/* 119 */       this.bipedRightLeg.render(scale);
/* 120 */       this.bipedLeftLeg.render(scale);
/* 121 */       this.bipedHeadwear.render(scale);
/*     */     } 
/*     */     
/* 124 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 133 */     this.bipedHead.rotateAngleY = netHeadYaw / 57.295776F;
/* 134 */     this.bipedHead.rotateAngleX = headPitch / 57.295776F;
/* 135 */     this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F;
/* 136 */     this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
/* 137 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/* 138 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 139 */     this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
/* 140 */     this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
/* 141 */     this.bipedRightLeg.rotateAngleY = 0.0F;
/* 142 */     this.bipedLeftLeg.rotateAngleY = 0.0F;
/*     */     
/* 144 */     if (this.isRiding) {
/* 145 */       this.bipedRightArm.rotateAngleX += -0.62831855F;
/* 146 */       this.bipedLeftArm.rotateAngleX += -0.62831855F;
/* 147 */       this.bipedRightLeg.rotateAngleX = -1.2566371F;
/* 148 */       this.bipedLeftLeg.rotateAngleX = -1.2566371F;
/* 149 */       this.bipedRightLeg.rotateAngleY = 0.31415927F;
/* 150 */       this.bipedLeftLeg.rotateAngleY = -0.31415927F;
/*     */     } 
/*     */     
/* 153 */     if (this.heldItemLeft != 0) {
/* 154 */       this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.31415927F * this.heldItemLeft;
/*     */     }
/*     */     
/* 157 */     this.bipedRightArm.rotateAngleY = 0.0F;
/* 158 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/*     */     
/* 160 */     switch (this.heldItemRight) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 167 */         this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.31415927F * this.heldItemRight;
/*     */         break;
/*     */       
/*     */       case 3:
/* 171 */         this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.31415927F * this.heldItemRight;
/* 172 */         this.bipedRightArm.rotateAngleY = -0.5235988F;
/*     */         break;
/*     */     } 
/* 175 */     this.bipedLeftArm.rotateAngleY = 0.0F;
/*     */     
/* 177 */     if (this.swingProgress > -9990.0F) {
/* 178 */       float f = this.swingProgress;
/* 179 */       this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f) * 3.1415927F * 2.0F) * 0.2F;
/* 180 */       this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
/* 181 */       this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
/* 182 */       this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
/* 183 */       this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
/* 184 */       this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 185 */       this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 186 */       this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
/* 187 */       f = 1.0F - this.swingProgress;
/* 188 */       f *= f;
/* 189 */       f *= f;
/* 190 */       f = 1.0F - f;
/* 191 */       float f1 = MathHelper.sin(f * 3.1415927F);
/* 192 */       float f2 = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
/* 193 */       this.bipedRightArm.rotateAngleX = (float)(this.bipedRightArm.rotateAngleX - f1 * 1.2D + f2);
/* 194 */       this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
/* 195 */       this.bipedRightArm.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
/*     */     } 
/*     */     
/* 198 */     if (this.isSneak) {
/* 199 */       this.bipedBody.rotateAngleX = 0.5F;
/* 200 */       this.bipedRightArm.rotateAngleX += 0.4F;
/* 201 */       this.bipedLeftArm.rotateAngleX += 0.4F;
/* 202 */       this.bipedRightLeg.rotationPointZ = 4.0F;
/* 203 */       this.bipedLeftLeg.rotationPointZ = 4.0F;
/* 204 */       this.bipedRightLeg.rotationPointY = 9.0F;
/* 205 */       this.bipedLeftLeg.rotationPointY = 9.0F;
/* 206 */       this.bipedHead.rotationPointY = 1.0F;
/*     */     } else {
/* 208 */       this.bipedBody.rotateAngleX = 0.0F;
/* 209 */       this.bipedRightLeg.rotationPointZ = 0.1F;
/* 210 */       this.bipedLeftLeg.rotationPointZ = 0.1F;
/* 211 */       this.bipedRightLeg.rotationPointY = 12.0F;
/* 212 */       this.bipedLeftLeg.rotationPointY = 12.0F;
/* 213 */       this.bipedHead.rotationPointY = 0.0F;
/*     */     } 
/*     */     
/* 216 */     this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 217 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 218 */     this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/* 219 */     this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/*     */     
/* 221 */     if (this.aimedBow) {
/* 222 */       float f3 = 0.0F;
/* 223 */       float f4 = 0.0F;
/* 224 */       this.bipedRightArm.rotateAngleZ = 0.0F;
/* 225 */       this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 226 */       this.bipedRightArm.rotateAngleY = -(0.1F - f3 * 0.6F) + this.bipedHead.rotateAngleY;
/* 227 */       this.bipedLeftArm.rotateAngleY = 0.1F - f3 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
/* 228 */       this.bipedRightArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/* 229 */       this.bipedLeftArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/* 230 */       this.bipedRightArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
/* 231 */       this.bipedLeftArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
/* 232 */       this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 233 */       this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 234 */       this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/* 235 */       this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/*     */     } 
/*     */     
/* 238 */     copyModelAngles(this.bipedHead, this.bipedHeadwear);
/*     */   }
/*     */   
/*     */   public void setModelAttributes(ModelBase model) {
/* 242 */     super.setModelAttributes(model);
/*     */     
/* 244 */     if (model instanceof ModelBiped) {
/* 245 */       ModelBiped modelbiped = (ModelBiped)model;
/* 246 */       this.heldItemLeft = modelbiped.heldItemLeft;
/* 247 */       this.heldItemRight = modelbiped.heldItemRight;
/* 248 */       this.isSneak = modelbiped.isSneak;
/* 249 */       this.aimedBow = modelbiped.aimedBow;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setInvisible(boolean invisible) {
/* 254 */     this.bipedHead.showModel = invisible;
/* 255 */     this.bipedHeadwear.showModel = invisible;
/* 256 */     this.bipedBody.showModel = invisible;
/* 257 */     this.bipedRightArm.showModel = invisible;
/* 258 */     this.bipedLeftArm.showModel = invisible;
/* 259 */     this.bipedRightLeg.showModel = invisible;
/* 260 */     this.bipedLeftLeg.showModel = invisible;
/*     */   }
/*     */   
/*     */   public void postRenderArm(float scale) {
/* 264 */     this.bipedRightArm.postRender(scale);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelBiped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */