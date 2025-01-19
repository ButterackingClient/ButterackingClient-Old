/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityDragon;
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
/*     */ public class ModelDragon
/*     */   extends ModelBase
/*     */ {
/*     */   private ModelRenderer head;
/*     */   private ModelRenderer spine;
/*     */   private ModelRenderer jaw;
/*     */   private ModelRenderer body;
/*     */   private ModelRenderer rearLeg;
/*     */   private ModelRenderer frontLeg;
/*     */   private ModelRenderer rearLegTip;
/*     */   private ModelRenderer frontLegTip;
/*     */   private ModelRenderer rearFoot;
/*     */   private ModelRenderer frontFoot;
/*     */   private ModelRenderer wing;
/*     */   private ModelRenderer wingTip;
/*     */   private float partialTicks;
/*     */   
/*     */   public ModelDragon(float p_i46360_1_) {
/*  71 */     this.textureWidth = 256;
/*  72 */     this.textureHeight = 256;
/*  73 */     setTextureOffset("body.body", 0, 0);
/*  74 */     setTextureOffset("wing.skin", -56, 88);
/*  75 */     setTextureOffset("wingtip.skin", -56, 144);
/*  76 */     setTextureOffset("rearleg.main", 0, 0);
/*  77 */     setTextureOffset("rearfoot.main", 112, 0);
/*  78 */     setTextureOffset("rearlegtip.main", 196, 0);
/*  79 */     setTextureOffset("head.upperhead", 112, 30);
/*  80 */     setTextureOffset("wing.bone", 112, 88);
/*  81 */     setTextureOffset("head.upperlip", 176, 44);
/*  82 */     setTextureOffset("jaw.jaw", 176, 65);
/*  83 */     setTextureOffset("frontleg.main", 112, 104);
/*  84 */     setTextureOffset("wingtip.bone", 112, 136);
/*  85 */     setTextureOffset("frontfoot.main", 144, 104);
/*  86 */     setTextureOffset("neck.box", 192, 104);
/*  87 */     setTextureOffset("frontlegtip.main", 226, 138);
/*  88 */     setTextureOffset("body.scale", 220, 53);
/*  89 */     setTextureOffset("head.scale", 0, 0);
/*  90 */     setTextureOffset("neck.scale", 48, 0);
/*  91 */     setTextureOffset("head.nostril", 112, 0);
/*  92 */     float f = -16.0F;
/*  93 */     this.head = new ModelRenderer(this, "head");
/*  94 */     this.head.addBox("upperlip", -6.0F, -1.0F, -8.0F + f, 12, 5, 16);
/*  95 */     this.head.addBox("upperhead", -8.0F, -8.0F, 6.0F + f, 16, 16, 16);
/*  96 */     this.head.mirror = true;
/*  97 */     this.head.addBox("scale", -5.0F, -12.0F, 12.0F + f, 2, 4, 6);
/*  98 */     this.head.addBox("nostril", -5.0F, -3.0F, -6.0F + f, 2, 2, 4);
/*  99 */     this.head.mirror = false;
/* 100 */     this.head.addBox("scale", 3.0F, -12.0F, 12.0F + f, 2, 4, 6);
/* 101 */     this.head.addBox("nostril", 3.0F, -3.0F, -6.0F + f, 2, 2, 4);
/* 102 */     this.jaw = new ModelRenderer(this, "jaw");
/* 103 */     this.jaw.setRotationPoint(0.0F, 4.0F, 8.0F + f);
/* 104 */     this.jaw.addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
/* 105 */     this.head.addChild(this.jaw);
/* 106 */     this.spine = new ModelRenderer(this, "neck");
/* 107 */     this.spine.addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
/* 108 */     this.spine.addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
/* 109 */     this.body = new ModelRenderer(this, "body");
/* 110 */     this.body.setRotationPoint(0.0F, 4.0F, 8.0F);
/* 111 */     this.body.addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
/* 112 */     this.body.addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
/* 113 */     this.body.addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
/* 114 */     this.body.addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
/* 115 */     this.wing = new ModelRenderer(this, "wing");
/* 116 */     this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
/* 117 */     this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
/* 118 */     this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
/* 119 */     this.wingTip = new ModelRenderer(this, "wingtip");
/* 120 */     this.wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
/* 121 */     this.wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
/* 122 */     this.wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
/* 123 */     this.wing.addChild(this.wingTip);
/* 124 */     this.frontLeg = new ModelRenderer(this, "frontleg");
/* 125 */     this.frontLeg.setRotationPoint(-12.0F, 20.0F, 2.0F);
/* 126 */     this.frontLeg.addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
/* 127 */     this.frontLegTip = new ModelRenderer(this, "frontlegtip");
/* 128 */     this.frontLegTip.setRotationPoint(0.0F, 20.0F, -1.0F);
/* 129 */     this.frontLegTip.addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
/* 130 */     this.frontLeg.addChild(this.frontLegTip);
/* 131 */     this.frontFoot = new ModelRenderer(this, "frontfoot");
/* 132 */     this.frontFoot.setRotationPoint(0.0F, 23.0F, 0.0F);
/* 133 */     this.frontFoot.addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
/* 134 */     this.frontLegTip.addChild(this.frontFoot);
/* 135 */     this.rearLeg = new ModelRenderer(this, "rearleg");
/* 136 */     this.rearLeg.setRotationPoint(-16.0F, 16.0F, 42.0F);
/* 137 */     this.rearLeg.addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
/* 138 */     this.rearLegTip = new ModelRenderer(this, "rearlegtip");
/* 139 */     this.rearLegTip.setRotationPoint(0.0F, 32.0F, -4.0F);
/* 140 */     this.rearLegTip.addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
/* 141 */     this.rearLeg.addChild(this.rearLegTip);
/* 142 */     this.rearFoot = new ModelRenderer(this, "rearfoot");
/* 143 */     this.rearFoot.setRotationPoint(0.0F, 31.0F, 4.0F);
/* 144 */     this.rearFoot.addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
/* 145 */     this.rearLegTip.addChild(this.rearFoot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 153 */     this.partialTicks = partialTickTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 160 */     GlStateManager.pushMatrix();
/* 161 */     EntityDragon entitydragon = (EntityDragon)entityIn;
/* 162 */     float f = entitydragon.prevAnimTime + (entitydragon.animTime - entitydragon.prevAnimTime) * this.partialTicks;
/* 163 */     this.jaw.rotateAngleX = (float)(Math.sin((f * 3.1415927F * 2.0F)) + 1.0D) * 0.2F;
/* 164 */     float f1 = (float)(Math.sin((f * 3.1415927F * 2.0F - 1.0F)) + 1.0D);
/* 165 */     f1 = (f1 * f1 * 1.0F + f1 * 2.0F) * 0.05F;
/* 166 */     GlStateManager.translate(0.0F, f1 - 2.0F, -3.0F);
/* 167 */     GlStateManager.rotate(f1 * 2.0F, 1.0F, 0.0F, 0.0F);
/* 168 */     float f2 = -30.0F;
/* 169 */     float f4 = 0.0F;
/* 170 */     float f5 = 1.5F;
/* 171 */     double[] adouble = entitydragon.getMovementOffsets(6, this.partialTicks);
/* 172 */     float f6 = updateRotations(entitydragon.getMovementOffsets(5, this.partialTicks)[0] - entitydragon.getMovementOffsets(10, this.partialTicks)[0]);
/* 173 */     float f7 = updateRotations(entitydragon.getMovementOffsets(5, this.partialTicks)[0] + (f6 / 2.0F));
/* 174 */     f2 += 2.0F;
/* 175 */     float f8 = f * 3.1415927F * 2.0F;
/* 176 */     f2 = 20.0F;
/* 177 */     float f3 = -12.0F;
/*     */     
/* 179 */     for (int i = 0; i < 5; i++) {
/* 180 */       double[] adouble1 = entitydragon.getMovementOffsets(5 - i, this.partialTicks);
/* 181 */       float f9 = (float)Math.cos((i * 0.45F + f8)) * 0.15F;
/* 182 */       this.spine.rotateAngleY = updateRotations(adouble1[0] - adouble[0]) * 3.1415927F / 180.0F * f5;
/* 183 */       this.spine.rotateAngleX = f9 + (float)(adouble1[1] - adouble[1]) * 3.1415927F / 180.0F * f5 * 5.0F;
/* 184 */       this.spine.rotateAngleZ = -updateRotations(adouble1[0] - f7) * 3.1415927F / 180.0F * f5;
/* 185 */       this.spine.rotationPointY = f2;
/* 186 */       this.spine.rotationPointZ = f3;
/* 187 */       this.spine.rotationPointX = f4;
/* 188 */       f2 = (float)(f2 + Math.sin(this.spine.rotateAngleX) * 10.0D);
/* 189 */       f3 = (float)(f3 - Math.cos(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0D);
/* 190 */       f4 = (float)(f4 - Math.sin(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0D);
/* 191 */       this.spine.render(scale);
/*     */     } 
/*     */     
/* 194 */     this.head.rotationPointY = f2;
/* 195 */     this.head.rotationPointZ = f3;
/* 196 */     this.head.rotationPointX = f4;
/* 197 */     double[] adouble2 = entitydragon.getMovementOffsets(0, this.partialTicks);
/* 198 */     this.head.rotateAngleY = updateRotations(adouble2[0] - adouble[0]) * 3.1415927F / 180.0F * 1.0F;
/* 199 */     this.head.rotateAngleZ = -updateRotations(adouble2[0] - f7) * 3.1415927F / 180.0F * 1.0F;
/* 200 */     this.head.render(scale);
/* 201 */     GlStateManager.pushMatrix();
/* 202 */     GlStateManager.translate(0.0F, 1.0F, 0.0F);
/* 203 */     GlStateManager.rotate(-f6 * f5 * 1.0F, 0.0F, 0.0F, 1.0F);
/* 204 */     GlStateManager.translate(0.0F, -1.0F, 0.0F);
/* 205 */     this.body.rotateAngleZ = 0.0F;
/* 206 */     this.body.render(scale);
/*     */     
/* 208 */     for (int j = 0; j < 2; j++) {
/* 209 */       GlStateManager.enableCull();
/* 210 */       float f11 = f * 3.1415927F * 2.0F;
/* 211 */       this.wing.rotateAngleX = 0.125F - (float)Math.cos(f11) * 0.2F;
/* 212 */       this.wing.rotateAngleY = 0.25F;
/* 213 */       this.wing.rotateAngleZ = (float)(Math.sin(f11) + 0.125D) * 0.8F;
/* 214 */       this.wingTip.rotateAngleZ = -((float)(Math.sin((f11 + 2.0F)) + 0.5D)) * 0.75F;
/* 215 */       this.rearLeg.rotateAngleX = 1.0F + f1 * 0.1F;
/* 216 */       this.rearLegTip.rotateAngleX = 0.5F + f1 * 0.1F;
/* 217 */       this.rearFoot.rotateAngleX = 0.75F + f1 * 0.1F;
/* 218 */       this.frontLeg.rotateAngleX = 1.3F + f1 * 0.1F;
/* 219 */       this.frontLegTip.rotateAngleX = -0.5F - f1 * 0.1F;
/* 220 */       this.frontFoot.rotateAngleX = 0.75F + f1 * 0.1F;
/* 221 */       this.wing.render(scale);
/* 222 */       this.frontLeg.render(scale);
/* 223 */       this.rearLeg.render(scale);
/* 224 */       GlStateManager.scale(-1.0F, 1.0F, 1.0F);
/*     */       
/* 226 */       if (j == 0) {
/* 227 */         GlStateManager.cullFace(1028);
/*     */       }
/*     */     } 
/*     */     
/* 231 */     GlStateManager.popMatrix();
/* 232 */     GlStateManager.cullFace(1029);
/* 233 */     GlStateManager.disableCull();
/* 234 */     float f10 = -((float)Math.sin((f * 3.1415927F * 2.0F))) * 0.0F;
/* 235 */     f8 = f * 3.1415927F * 2.0F;
/* 236 */     f2 = 10.0F;
/* 237 */     f3 = 60.0F;
/* 238 */     f4 = 0.0F;
/* 239 */     adouble = entitydragon.getMovementOffsets(11, this.partialTicks);
/*     */     
/* 241 */     for (int k = 0; k < 12; k++) {
/* 242 */       adouble2 = entitydragon.getMovementOffsets(12 + k, this.partialTicks);
/* 243 */       f10 = (float)(f10 + Math.sin((k * 0.45F + f8)) * 0.05000000074505806D);
/* 244 */       this.spine.rotateAngleY = (updateRotations(adouble2[0] - adouble[0]) * f5 + 180.0F) * 3.1415927F / 180.0F;
/* 245 */       this.spine.rotateAngleX = f10 + (float)(adouble2[1] - adouble[1]) * 3.1415927F / 180.0F * f5 * 5.0F;
/* 246 */       this.spine.rotateAngleZ = updateRotations(adouble2[0] - f7) * 3.1415927F / 180.0F * f5;
/* 247 */       this.spine.rotationPointY = f2;
/* 248 */       this.spine.rotationPointZ = f3;
/* 249 */       this.spine.rotationPointX = f4;
/* 250 */       f2 = (float)(f2 + Math.sin(this.spine.rotateAngleX) * 10.0D);
/* 251 */       f3 = (float)(f3 - Math.cos(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0D);
/* 252 */       f4 = (float)(f4 - Math.sin(this.spine.rotateAngleY) * Math.cos(this.spine.rotateAngleX) * 10.0D);
/* 253 */       this.spine.render(scale);
/*     */     } 
/*     */     
/* 256 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float updateRotations(double p_78214_1_) {
/* 265 */     while (p_78214_1_ >= 180.0D) {
/* 266 */       p_78214_1_ -= 360.0D;
/*     */     }
/*     */     
/* 269 */     while (p_78214_1_ < -180.0D) {
/* 270 */       p_78214_1_ += 360.0D;
/*     */     }
/*     */     
/* 273 */     return (float)p_78214_1_;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelDragon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */