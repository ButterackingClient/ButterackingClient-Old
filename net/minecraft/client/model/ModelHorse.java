/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityHorse;
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
/*     */ public class ModelHorse
/*     */   extends ModelBase
/*     */ {
/*     */   private ModelRenderer head;
/*     */   private ModelRenderer field_178711_b;
/*     */   private ModelRenderer field_178712_c;
/*     */   private ModelRenderer horseLeftEar;
/*     */   private ModelRenderer horseRightEar;
/*     */   private ModelRenderer muleLeftEar;
/*     */   private ModelRenderer muleRightEar;
/*     */   private ModelRenderer neck;
/*     */   private ModelRenderer horseFaceRopes;
/*     */   private ModelRenderer mane;
/*     */   private ModelRenderer body;
/*     */   private ModelRenderer tailBase;
/*     */   private ModelRenderer tailMiddle;
/*     */   private ModelRenderer tailTip;
/*     */   private ModelRenderer backLeftLeg;
/*     */   private ModelRenderer backLeftShin;
/*     */   private ModelRenderer backLeftHoof;
/*     */   private ModelRenderer backRightLeg;
/*     */   private ModelRenderer backRightShin;
/*     */   private ModelRenderer backRightHoof;
/*     */   private ModelRenderer frontLeftLeg;
/*     */   private ModelRenderer frontLeftShin;
/*     */   private ModelRenderer frontLeftHoof;
/*     */   private ModelRenderer frontRightLeg;
/*     */   private ModelRenderer frontRightShin;
/*     */   private ModelRenderer frontRightHoof;
/*     */   private ModelRenderer muleLeftChest;
/*     */   private ModelRenderer muleRightChest;
/*     */   private ModelRenderer horseSaddleBottom;
/*     */   private ModelRenderer horseSaddleFront;
/*     */   private ModelRenderer horseSaddleBack;
/*     */   private ModelRenderer horseLeftSaddleRope;
/*     */   private ModelRenderer horseLeftSaddleMetal;
/*     */   private ModelRenderer horseRightSaddleRope;
/*     */   private ModelRenderer horseRightSaddleMetal;
/*     */   private ModelRenderer horseLeftFaceMetal;
/*     */   private ModelRenderer horseRightFaceMetal;
/*     */   private ModelRenderer horseLeftRein;
/*     */   private ModelRenderer horseRightRein;
/*     */   
/*     */   public ModelHorse() {
/*  79 */     this.textureWidth = 128;
/*  80 */     this.textureHeight = 128;
/*  81 */     this.body = new ModelRenderer(this, 0, 34);
/*  82 */     this.body.addBox(-5.0F, -8.0F, -19.0F, 10, 10, 24);
/*  83 */     this.body.setRotationPoint(0.0F, 11.0F, 9.0F);
/*  84 */     this.tailBase = new ModelRenderer(this, 44, 0);
/*  85 */     this.tailBase.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3);
/*  86 */     this.tailBase.setRotationPoint(0.0F, 3.0F, 14.0F);
/*  87 */     setBoxRotation(this.tailBase, -1.134464F, 0.0F, 0.0F);
/*  88 */     this.tailMiddle = new ModelRenderer(this, 38, 7);
/*  89 */     this.tailMiddle.addBox(-1.5F, -2.0F, 3.0F, 3, 4, 7);
/*  90 */     this.tailMiddle.setRotationPoint(0.0F, 3.0F, 14.0F);
/*  91 */     setBoxRotation(this.tailMiddle, -1.134464F, 0.0F, 0.0F);
/*  92 */     this.tailTip = new ModelRenderer(this, 24, 3);
/*  93 */     this.tailTip.addBox(-1.5F, -4.5F, 9.0F, 3, 4, 7);
/*  94 */     this.tailTip.setRotationPoint(0.0F, 3.0F, 14.0F);
/*  95 */     setBoxRotation(this.tailTip, -1.40215F, 0.0F, 0.0F);
/*  96 */     this.backLeftLeg = new ModelRenderer(this, 78, 29);
/*  97 */     this.backLeftLeg.addBox(-2.5F, -2.0F, -2.5F, 4, 9, 5);
/*  98 */     this.backLeftLeg.setRotationPoint(4.0F, 9.0F, 11.0F);
/*  99 */     this.backLeftShin = new ModelRenderer(this, 78, 43);
/* 100 */     this.backLeftShin.addBox(-2.0F, 0.0F, -1.5F, 3, 5, 3);
/* 101 */     this.backLeftShin.setRotationPoint(4.0F, 16.0F, 11.0F);
/* 102 */     this.backLeftHoof = new ModelRenderer(this, 78, 51);
/* 103 */     this.backLeftHoof.addBox(-2.5F, 5.1F, -2.0F, 4, 3, 4);
/* 104 */     this.backLeftHoof.setRotationPoint(4.0F, 16.0F, 11.0F);
/* 105 */     this.backRightLeg = new ModelRenderer(this, 96, 29);
/* 106 */     this.backRightLeg.addBox(-1.5F, -2.0F, -2.5F, 4, 9, 5);
/* 107 */     this.backRightLeg.setRotationPoint(-4.0F, 9.0F, 11.0F);
/* 108 */     this.backRightShin = new ModelRenderer(this, 96, 43);
/* 109 */     this.backRightShin.addBox(-1.0F, 0.0F, -1.5F, 3, 5, 3);
/* 110 */     this.backRightShin.setRotationPoint(-4.0F, 16.0F, 11.0F);
/* 111 */     this.backRightHoof = new ModelRenderer(this, 96, 51);
/* 112 */     this.backRightHoof.addBox(-1.5F, 5.1F, -2.0F, 4, 3, 4);
/* 113 */     this.backRightHoof.setRotationPoint(-4.0F, 16.0F, 11.0F);
/* 114 */     this.frontLeftLeg = new ModelRenderer(this, 44, 29);
/* 115 */     this.frontLeftLeg.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4);
/* 116 */     this.frontLeftLeg.setRotationPoint(4.0F, 9.0F, -8.0F);
/* 117 */     this.frontLeftShin = new ModelRenderer(this, 44, 41);
/* 118 */     this.frontLeftShin.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3);
/* 119 */     this.frontLeftShin.setRotationPoint(4.0F, 16.0F, -8.0F);
/* 120 */     this.frontLeftHoof = new ModelRenderer(this, 44, 51);
/* 121 */     this.frontLeftHoof.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4);
/* 122 */     this.frontLeftHoof.setRotationPoint(4.0F, 16.0F, -8.0F);
/* 123 */     this.frontRightLeg = new ModelRenderer(this, 60, 29);
/* 124 */     this.frontRightLeg.addBox(-1.1F, -1.0F, -2.1F, 3, 8, 4);
/* 125 */     this.frontRightLeg.setRotationPoint(-4.0F, 9.0F, -8.0F);
/* 126 */     this.frontRightShin = new ModelRenderer(this, 60, 41);
/* 127 */     this.frontRightShin.addBox(-1.1F, 0.0F, -1.6F, 3, 5, 3);
/* 128 */     this.frontRightShin.setRotationPoint(-4.0F, 16.0F, -8.0F);
/* 129 */     this.frontRightHoof = new ModelRenderer(this, 60, 51);
/* 130 */     this.frontRightHoof.addBox(-1.6F, 5.1F, -2.1F, 4, 3, 4);
/* 131 */     this.frontRightHoof.setRotationPoint(-4.0F, 16.0F, -8.0F);
/* 132 */     this.head = new ModelRenderer(this, 0, 0);
/* 133 */     this.head.addBox(-2.5F, -10.0F, -1.5F, 5, 5, 7);
/* 134 */     this.head.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 135 */     setBoxRotation(this.head, 0.5235988F, 0.0F, 0.0F);
/* 136 */     this.field_178711_b = new ModelRenderer(this, 24, 18);
/* 137 */     this.field_178711_b.addBox(-2.0F, -10.0F, -7.0F, 4, 3, 6);
/* 138 */     this.field_178711_b.setRotationPoint(0.0F, 3.95F, -10.0F);
/* 139 */     setBoxRotation(this.field_178711_b, 0.5235988F, 0.0F, 0.0F);
/* 140 */     this.field_178712_c = new ModelRenderer(this, 24, 27);
/* 141 */     this.field_178712_c.addBox(-2.0F, -7.0F, -6.5F, 4, 2, 5);
/* 142 */     this.field_178712_c.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 143 */     setBoxRotation(this.field_178712_c, 0.5235988F, 0.0F, 0.0F);
/* 144 */     this.head.addChild(this.field_178711_b);
/* 145 */     this.head.addChild(this.field_178712_c);
/* 146 */     this.horseLeftEar = new ModelRenderer(this, 0, 0);
/* 147 */     this.horseLeftEar.addBox(0.45F, -12.0F, 4.0F, 2, 3, 1);
/* 148 */     this.horseLeftEar.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 149 */     setBoxRotation(this.horseLeftEar, 0.5235988F, 0.0F, 0.0F);
/* 150 */     this.horseRightEar = new ModelRenderer(this, 0, 0);
/* 151 */     this.horseRightEar.addBox(-2.45F, -12.0F, 4.0F, 2, 3, 1);
/* 152 */     this.horseRightEar.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 153 */     setBoxRotation(this.horseRightEar, 0.5235988F, 0.0F, 0.0F);
/* 154 */     this.muleLeftEar = new ModelRenderer(this, 0, 12);
/* 155 */     this.muleLeftEar.addBox(-2.0F, -16.0F, 4.0F, 2, 7, 1);
/* 156 */     this.muleLeftEar.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 157 */     setBoxRotation(this.muleLeftEar, 0.5235988F, 0.0F, 0.2617994F);
/* 158 */     this.muleRightEar = new ModelRenderer(this, 0, 12);
/* 159 */     this.muleRightEar.addBox(0.0F, -16.0F, 4.0F, 2, 7, 1);
/* 160 */     this.muleRightEar.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 161 */     setBoxRotation(this.muleRightEar, 0.5235988F, 0.0F, -0.2617994F);
/* 162 */     this.neck = new ModelRenderer(this, 0, 12);
/* 163 */     this.neck.addBox(-2.05F, -9.8F, -2.0F, 4, 14, 8);
/* 164 */     this.neck.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 165 */     setBoxRotation(this.neck, 0.5235988F, 0.0F, 0.0F);
/* 166 */     this.muleLeftChest = new ModelRenderer(this, 0, 34);
/* 167 */     this.muleLeftChest.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
/* 168 */     this.muleLeftChest.setRotationPoint(-7.5F, 3.0F, 10.0F);
/* 169 */     setBoxRotation(this.muleLeftChest, 0.0F, 1.5707964F, 0.0F);
/* 170 */     this.muleRightChest = new ModelRenderer(this, 0, 47);
/* 171 */     this.muleRightChest.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
/* 172 */     this.muleRightChest.setRotationPoint(4.5F, 3.0F, 10.0F);
/* 173 */     setBoxRotation(this.muleRightChest, 0.0F, 1.5707964F, 0.0F);
/* 174 */     this.horseSaddleBottom = new ModelRenderer(this, 80, 0);
/* 175 */     this.horseSaddleBottom.addBox(-5.0F, 0.0F, -3.0F, 10, 1, 8);
/* 176 */     this.horseSaddleBottom.setRotationPoint(0.0F, 2.0F, 2.0F);
/* 177 */     this.horseSaddleFront = new ModelRenderer(this, 106, 9);
/* 178 */     this.horseSaddleFront.addBox(-1.5F, -1.0F, -3.0F, 3, 1, 2);
/* 179 */     this.horseSaddleFront.setRotationPoint(0.0F, 2.0F, 2.0F);
/* 180 */     this.horseSaddleBack = new ModelRenderer(this, 80, 9);
/* 181 */     this.horseSaddleBack.addBox(-4.0F, -1.0F, 3.0F, 8, 1, 2);
/* 182 */     this.horseSaddleBack.setRotationPoint(0.0F, 2.0F, 2.0F);
/* 183 */     this.horseLeftSaddleMetal = new ModelRenderer(this, 74, 0);
/* 184 */     this.horseLeftSaddleMetal.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2);
/* 185 */     this.horseLeftSaddleMetal.setRotationPoint(5.0F, 3.0F, 2.0F);
/* 186 */     this.horseLeftSaddleRope = new ModelRenderer(this, 70, 0);
/* 187 */     this.horseLeftSaddleRope.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1);
/* 188 */     this.horseLeftSaddleRope.setRotationPoint(5.0F, 3.0F, 2.0F);
/* 189 */     this.horseRightSaddleMetal = new ModelRenderer(this, 74, 4);
/* 190 */     this.horseRightSaddleMetal.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2);
/* 191 */     this.horseRightSaddleMetal.setRotationPoint(-5.0F, 3.0F, 2.0F);
/* 192 */     this.horseRightSaddleRope = new ModelRenderer(this, 80, 0);
/* 193 */     this.horseRightSaddleRope.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1);
/* 194 */     this.horseRightSaddleRope.setRotationPoint(-5.0F, 3.0F, 2.0F);
/* 195 */     this.horseLeftFaceMetal = new ModelRenderer(this, 74, 13);
/* 196 */     this.horseLeftFaceMetal.addBox(1.5F, -8.0F, -4.0F, 1, 2, 2);
/* 197 */     this.horseLeftFaceMetal.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 198 */     setBoxRotation(this.horseLeftFaceMetal, 0.5235988F, 0.0F, 0.0F);
/* 199 */     this.horseRightFaceMetal = new ModelRenderer(this, 74, 13);
/* 200 */     this.horseRightFaceMetal.addBox(-2.5F, -8.0F, -4.0F, 1, 2, 2);
/* 201 */     this.horseRightFaceMetal.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 202 */     setBoxRotation(this.horseRightFaceMetal, 0.5235988F, 0.0F, 0.0F);
/* 203 */     this.horseLeftRein = new ModelRenderer(this, 44, 10);
/* 204 */     this.horseLeftRein.addBox(2.6F, -6.0F, -6.0F, 0, 3, 16);
/* 205 */     this.horseLeftRein.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 206 */     this.horseRightRein = new ModelRenderer(this, 44, 5);
/* 207 */     this.horseRightRein.addBox(-2.6F, -6.0F, -6.0F, 0, 3, 16);
/* 208 */     this.horseRightRein.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 209 */     this.mane = new ModelRenderer(this, 58, 0);
/* 210 */     this.mane.addBox(-1.0F, -11.5F, 5.0F, 2, 16, 4);
/* 211 */     this.mane.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 212 */     setBoxRotation(this.mane, 0.5235988F, 0.0F, 0.0F);
/* 213 */     this.horseFaceRopes = new ModelRenderer(this, 80, 12);
/* 214 */     this.horseFaceRopes.addBox(-2.5F, -10.1F, -7.0F, 5, 5, 12, 0.2F);
/* 215 */     this.horseFaceRopes.setRotationPoint(0.0F, 4.0F, -10.0F);
/* 216 */     setBoxRotation(this.horseFaceRopes, 0.5235988F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/* 223 */     EntityHorse entityhorse = (EntityHorse)entityIn;
/* 224 */     int i = entityhorse.getHorseType();
/* 225 */     float f = entityhorse.getGrassEatingAmount(0.0F);
/* 226 */     boolean flag = entityhorse.isAdultHorse();
/* 227 */     boolean flag1 = (flag && entityhorse.isHorseSaddled());
/* 228 */     boolean flag2 = (flag && entityhorse.isChested());
/* 229 */     boolean flag3 = !(i != 1 && i != 2);
/* 230 */     float f1 = entityhorse.getHorseSize();
/* 231 */     boolean flag4 = (entityhorse.riddenByEntity != null);
/*     */     
/* 233 */     if (flag1) {
/* 234 */       this.horseFaceRopes.render(scale);
/* 235 */       this.horseSaddleBottom.render(scale);
/* 236 */       this.horseSaddleFront.render(scale);
/* 237 */       this.horseSaddleBack.render(scale);
/* 238 */       this.horseLeftSaddleRope.render(scale);
/* 239 */       this.horseLeftSaddleMetal.render(scale);
/* 240 */       this.horseRightSaddleRope.render(scale);
/* 241 */       this.horseRightSaddleMetal.render(scale);
/* 242 */       this.horseLeftFaceMetal.render(scale);
/* 243 */       this.horseRightFaceMetal.render(scale);
/*     */       
/* 245 */       if (flag4) {
/* 246 */         this.horseLeftRein.render(scale);
/* 247 */         this.horseRightRein.render(scale);
/*     */       } 
/*     */     } 
/*     */     
/* 251 */     if (!flag) {
/* 252 */       GlStateManager.pushMatrix();
/* 253 */       GlStateManager.scale(f1, 0.5F + f1 * 0.5F, f1);
/* 254 */       GlStateManager.translate(0.0F, 0.95F * (1.0F - f1), 0.0F);
/*     */     } 
/*     */     
/* 257 */     this.backLeftLeg.render(scale);
/* 258 */     this.backLeftShin.render(scale);
/* 259 */     this.backLeftHoof.render(scale);
/* 260 */     this.backRightLeg.render(scale);
/* 261 */     this.backRightShin.render(scale);
/* 262 */     this.backRightHoof.render(scale);
/* 263 */     this.frontLeftLeg.render(scale);
/* 264 */     this.frontLeftShin.render(scale);
/* 265 */     this.frontLeftHoof.render(scale);
/* 266 */     this.frontRightLeg.render(scale);
/* 267 */     this.frontRightShin.render(scale);
/* 268 */     this.frontRightHoof.render(scale);
/*     */     
/* 270 */     if (!flag) {
/* 271 */       GlStateManager.popMatrix();
/* 272 */       GlStateManager.pushMatrix();
/* 273 */       GlStateManager.scale(f1, f1, f1);
/* 274 */       GlStateManager.translate(0.0F, 1.35F * (1.0F - f1), 0.0F);
/*     */     } 
/*     */     
/* 277 */     this.body.render(scale);
/* 278 */     this.tailBase.render(scale);
/* 279 */     this.tailMiddle.render(scale);
/* 280 */     this.tailTip.render(scale);
/* 281 */     this.neck.render(scale);
/* 282 */     this.mane.render(scale);
/*     */     
/* 284 */     if (!flag) {
/* 285 */       GlStateManager.popMatrix();
/* 286 */       GlStateManager.pushMatrix();
/* 287 */       float f2 = 0.5F + f1 * f1 * 0.5F;
/* 288 */       GlStateManager.scale(f2, f2, f2);
/*     */       
/* 290 */       if (f <= 0.0F) {
/* 291 */         GlStateManager.translate(0.0F, 1.35F * (1.0F - f1), 0.0F);
/*     */       } else {
/* 293 */         GlStateManager.translate(0.0F, 0.9F * (1.0F - f1) * f + 1.35F * (1.0F - f1) * (1.0F - f), 0.15F * (1.0F - f1) * f);
/*     */       } 
/*     */     } 
/*     */     
/* 297 */     if (flag3) {
/* 298 */       this.muleLeftEar.render(scale);
/* 299 */       this.muleRightEar.render(scale);
/*     */     } else {
/* 301 */       this.horseLeftEar.render(scale);
/* 302 */       this.horseRightEar.render(scale);
/*     */     } 
/*     */     
/* 305 */     this.head.render(scale);
/*     */     
/* 307 */     if (!flag) {
/* 308 */       GlStateManager.popMatrix();
/*     */     }
/*     */     
/* 311 */     if (flag2) {
/* 312 */       this.muleLeftChest.render(scale);
/* 313 */       this.muleRightChest.render(scale);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setBoxRotation(ModelRenderer p_110682_1_, float p_110682_2_, float p_110682_3_, float p_110682_4_) {
/* 321 */     p_110682_1_.rotateAngleX = p_110682_2_;
/* 322 */     p_110682_1_.rotateAngleY = p_110682_3_;
/* 323 */     p_110682_1_.rotateAngleZ = p_110682_4_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float updateHorseRotation(float p_110683_1_, float p_110683_2_, float p_110683_3_) {
/*     */     float f;
/* 332 */     for (f = p_110683_2_ - p_110683_1_; f < -180.0F; f += 360.0F);
/*     */ 
/*     */ 
/*     */     
/* 336 */     while (f >= 180.0F) {
/* 337 */       f -= 360.0F;
/*     */     }
/*     */     
/* 340 */     return p_110683_1_ + p_110683_3_ * f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 348 */     super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
/* 349 */     float f = updateHorseRotation(entitylivingbaseIn.prevRenderYawOffset, entitylivingbaseIn.renderYawOffset, partialTickTime);
/* 350 */     float f1 = updateHorseRotation(entitylivingbaseIn.prevRotationYawHead, entitylivingbaseIn.rotationYawHead, partialTickTime);
/* 351 */     float f2 = entitylivingbaseIn.prevRotationPitch + (entitylivingbaseIn.rotationPitch - entitylivingbaseIn.prevRotationPitch) * partialTickTime;
/* 352 */     float f3 = f1 - f;
/* 353 */     float f4 = f2 / 57.295776F;
/*     */     
/* 355 */     if (f3 > 20.0F) {
/* 356 */       f3 = 20.0F;
/*     */     }
/*     */     
/* 359 */     if (f3 < -20.0F) {
/* 360 */       f3 = -20.0F;
/*     */     }
/*     */     
/* 363 */     if (p_78086_3_ > 0.2F) {
/* 364 */       f4 += MathHelper.cos(p_78086_2_ * 0.4F) * 0.15F * p_78086_3_;
/*     */     }
/*     */     
/* 367 */     EntityHorse entityhorse = (EntityHorse)entitylivingbaseIn;
/* 368 */     float f5 = entityhorse.getGrassEatingAmount(partialTickTime);
/* 369 */     float f6 = entityhorse.getRearingAmount(partialTickTime);
/* 370 */     float f7 = 1.0F - f6;
/* 371 */     float f8 = entityhorse.getMouthOpennessAngle(partialTickTime);
/* 372 */     boolean flag = (entityhorse.field_110278_bp != 0);
/* 373 */     boolean flag1 = entityhorse.isHorseSaddled();
/* 374 */     boolean flag2 = (entityhorse.riddenByEntity != null);
/* 375 */     float f9 = entitylivingbaseIn.ticksExisted + partialTickTime;
/* 376 */     float f10 = MathHelper.cos(p_78086_2_ * 0.6662F + 3.1415927F);
/* 377 */     float f11 = f10 * 0.8F * p_78086_3_;
/* 378 */     this.head.rotationPointY = 4.0F;
/* 379 */     this.head.rotationPointZ = -10.0F;
/* 380 */     this.tailBase.rotationPointY = 3.0F;
/* 381 */     this.tailMiddle.rotationPointZ = 14.0F;
/* 382 */     this.muleRightChest.rotationPointY = 3.0F;
/* 383 */     this.muleRightChest.rotationPointZ = 10.0F;
/* 384 */     this.body.rotateAngleX = 0.0F;
/* 385 */     this.head.rotateAngleX = 0.5235988F + f4;
/* 386 */     this.head.rotateAngleY = f3 / 57.295776F;
/* 387 */     this.head.rotateAngleX = f6 * (0.2617994F + f4) + f5 * 2.18166F + (1.0F - Math.max(f6, f5)) * this.head.rotateAngleX;
/* 388 */     this.head.rotateAngleY = f6 * f3 / 57.295776F + (1.0F - Math.max(f6, f5)) * this.head.rotateAngleY;
/* 389 */     this.head.rotationPointY = f6 * -6.0F + f5 * 11.0F + (1.0F - Math.max(f6, f5)) * this.head.rotationPointY;
/* 390 */     this.head.rotationPointZ = f6 * -1.0F + f5 * -10.0F + (1.0F - Math.max(f6, f5)) * this.head.rotationPointZ;
/* 391 */     this.tailBase.rotationPointY = f6 * 9.0F + f7 * this.tailBase.rotationPointY;
/* 392 */     this.tailMiddle.rotationPointZ = f6 * 18.0F + f7 * this.tailMiddle.rotationPointZ;
/* 393 */     this.muleRightChest.rotationPointY = f6 * 5.5F + f7 * this.muleRightChest.rotationPointY;
/* 394 */     this.muleRightChest.rotationPointZ = f6 * 15.0F + f7 * this.muleRightChest.rotationPointZ;
/* 395 */     this.body.rotateAngleX = f6 * -45.0F / 57.295776F + f7 * this.body.rotateAngleX;
/* 396 */     this.horseLeftEar.rotationPointY = this.head.rotationPointY;
/* 397 */     this.horseRightEar.rotationPointY = this.head.rotationPointY;
/* 398 */     this.muleLeftEar.rotationPointY = this.head.rotationPointY;
/* 399 */     this.muleRightEar.rotationPointY = this.head.rotationPointY;
/* 400 */     this.neck.rotationPointY = this.head.rotationPointY;
/* 401 */     this.field_178711_b.rotationPointY = 0.02F;
/* 402 */     this.field_178712_c.rotationPointY = 0.0F;
/* 403 */     this.mane.rotationPointY = this.head.rotationPointY;
/* 404 */     this.horseLeftEar.rotationPointZ = this.head.rotationPointZ;
/* 405 */     this.horseRightEar.rotationPointZ = this.head.rotationPointZ;
/* 406 */     this.muleLeftEar.rotationPointZ = this.head.rotationPointZ;
/* 407 */     this.muleRightEar.rotationPointZ = this.head.rotationPointZ;
/* 408 */     this.neck.rotationPointZ = this.head.rotationPointZ;
/* 409 */     this.field_178711_b.rotationPointZ = 0.02F - f8 * 1.0F;
/* 410 */     this.field_178712_c.rotationPointZ = 0.0F + f8 * 1.0F;
/* 411 */     this.mane.rotationPointZ = this.head.rotationPointZ;
/* 412 */     this.horseLeftEar.rotateAngleX = this.head.rotateAngleX;
/* 413 */     this.horseRightEar.rotateAngleX = this.head.rotateAngleX;
/* 414 */     this.muleLeftEar.rotateAngleX = this.head.rotateAngleX;
/* 415 */     this.muleRightEar.rotateAngleX = this.head.rotateAngleX;
/* 416 */     this.neck.rotateAngleX = this.head.rotateAngleX;
/* 417 */     this.field_178711_b.rotateAngleX = 0.0F - 0.09424778F * f8;
/* 418 */     this.field_178712_c.rotateAngleX = 0.0F + 0.15707964F * f8;
/* 419 */     this.mane.rotateAngleX = this.head.rotateAngleX;
/* 420 */     this.horseLeftEar.rotateAngleY = this.head.rotateAngleY;
/* 421 */     this.horseRightEar.rotateAngleY = this.head.rotateAngleY;
/* 422 */     this.muleLeftEar.rotateAngleY = this.head.rotateAngleY;
/* 423 */     this.muleRightEar.rotateAngleY = this.head.rotateAngleY;
/* 424 */     this.neck.rotateAngleY = this.head.rotateAngleY;
/* 425 */     this.field_178711_b.rotateAngleY = 0.0F;
/* 426 */     this.field_178712_c.rotateAngleY = 0.0F;
/* 427 */     this.mane.rotateAngleY = this.head.rotateAngleY;
/* 428 */     this.muleLeftChest.rotateAngleX = f11 / 5.0F;
/* 429 */     this.muleRightChest.rotateAngleX = -f11 / 5.0F;
/* 430 */     float f12 = 1.5707964F;
/* 431 */     float f13 = 4.712389F;
/* 432 */     float f14 = -1.0471976F;
/* 433 */     float f15 = 0.2617994F * f6;
/* 434 */     float f16 = MathHelper.cos(f9 * 0.6F + 3.1415927F);
/* 435 */     this.frontLeftLeg.rotationPointY = -2.0F * f6 + 9.0F * f7;
/* 436 */     this.frontLeftLeg.rotationPointZ = -2.0F * f6 + -8.0F * f7;
/* 437 */     this.frontRightLeg.rotationPointY = this.frontLeftLeg.rotationPointY;
/* 438 */     this.frontRightLeg.rotationPointZ = this.frontLeftLeg.rotationPointZ;
/* 439 */     this.backLeftLeg.rotationPointY += MathHelper.sin(1.5707964F + f15 + f7 * -f10 * 0.5F * p_78086_3_) * 7.0F;
/* 440 */     this.backLeftLeg.rotationPointZ += MathHelper.cos(4.712389F + f15 + f7 * -f10 * 0.5F * p_78086_3_) * 7.0F;
/* 441 */     this.backRightLeg.rotationPointY += MathHelper.sin(1.5707964F + f15 + f7 * f10 * 0.5F * p_78086_3_) * 7.0F;
/* 442 */     this.backRightLeg.rotationPointZ += MathHelper.cos(4.712389F + f15 + f7 * f10 * 0.5F * p_78086_3_) * 7.0F;
/* 443 */     float f17 = (-1.0471976F + f16) * f6 + f11 * f7;
/* 444 */     float f18 = (-1.0471976F + -f16) * f6 + -f11 * f7;
/* 445 */     this.frontLeftLeg.rotationPointY += MathHelper.sin(1.5707964F + f17) * 7.0F;
/* 446 */     this.frontLeftLeg.rotationPointZ += MathHelper.cos(4.712389F + f17) * 7.0F;
/* 447 */     this.frontRightLeg.rotationPointY += MathHelper.sin(1.5707964F + f18) * 7.0F;
/* 448 */     this.frontRightLeg.rotationPointZ += MathHelper.cos(4.712389F + f18) * 7.0F;
/* 449 */     this.backLeftLeg.rotateAngleX = f15 + -f10 * 0.5F * p_78086_3_ * f7;
/* 450 */     this.backLeftShin.rotateAngleX = -0.08726646F * f6 + (-f10 * 0.5F * p_78086_3_ - Math.max(0.0F, f10 * 0.5F * p_78086_3_)) * f7;
/* 451 */     this.backLeftHoof.rotateAngleX = this.backLeftShin.rotateAngleX;
/* 452 */     this.backRightLeg.rotateAngleX = f15 + f10 * 0.5F * p_78086_3_ * f7;
/* 453 */     this.backRightShin.rotateAngleX = -0.08726646F * f6 + (f10 * 0.5F * p_78086_3_ - Math.max(0.0F, -f10 * 0.5F * p_78086_3_)) * f7;
/* 454 */     this.backRightHoof.rotateAngleX = this.backRightShin.rotateAngleX;
/* 455 */     this.frontLeftLeg.rotateAngleX = f17;
/* 456 */     this.frontLeftShin.rotateAngleX = (this.frontLeftLeg.rotateAngleX + 3.1415927F * Math.max(0.0F, 0.2F + f16 * 0.2F)) * f6 + (f11 + Math.max(0.0F, f10 * 0.5F * p_78086_3_)) * f7;
/* 457 */     this.frontLeftHoof.rotateAngleX = this.frontLeftShin.rotateAngleX;
/* 458 */     this.frontRightLeg.rotateAngleX = f18;
/* 459 */     this.frontRightShin.rotateAngleX = (this.frontRightLeg.rotateAngleX + 3.1415927F * Math.max(0.0F, 0.2F - f16 * 0.2F)) * f6 + (-f11 + Math.max(0.0F, -f10 * 0.5F * p_78086_3_)) * f7;
/* 460 */     this.frontRightHoof.rotateAngleX = this.frontRightShin.rotateAngleX;
/* 461 */     this.backLeftHoof.rotationPointY = this.backLeftShin.rotationPointY;
/* 462 */     this.backLeftHoof.rotationPointZ = this.backLeftShin.rotationPointZ;
/* 463 */     this.backRightHoof.rotationPointY = this.backRightShin.rotationPointY;
/* 464 */     this.backRightHoof.rotationPointZ = this.backRightShin.rotationPointZ;
/* 465 */     this.frontLeftHoof.rotationPointY = this.frontLeftShin.rotationPointY;
/* 466 */     this.frontLeftHoof.rotationPointZ = this.frontLeftShin.rotationPointZ;
/* 467 */     this.frontRightHoof.rotationPointY = this.frontRightShin.rotationPointY;
/* 468 */     this.frontRightHoof.rotationPointZ = this.frontRightShin.rotationPointZ;
/*     */     
/* 470 */     if (flag1) {
/* 471 */       this.horseSaddleBottom.rotationPointY = f6 * 0.5F + f7 * 2.0F;
/* 472 */       this.horseSaddleBottom.rotationPointZ = f6 * 11.0F + f7 * 2.0F;
/* 473 */       this.horseSaddleFront.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 474 */       this.horseSaddleBack.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 475 */       this.horseLeftSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 476 */       this.horseRightSaddleRope.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 477 */       this.horseLeftSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 478 */       this.horseRightSaddleMetal.rotationPointY = this.horseSaddleBottom.rotationPointY;
/* 479 */       this.muleLeftChest.rotationPointY = this.muleRightChest.rotationPointY;
/* 480 */       this.horseSaddleFront.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 481 */       this.horseSaddleBack.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 482 */       this.horseLeftSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 483 */       this.horseRightSaddleRope.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 484 */       this.horseLeftSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 485 */       this.horseRightSaddleMetal.rotationPointZ = this.horseSaddleBottom.rotationPointZ;
/* 486 */       this.muleLeftChest.rotationPointZ = this.muleRightChest.rotationPointZ;
/* 487 */       this.horseSaddleBottom.rotateAngleX = this.body.rotateAngleX;
/* 488 */       this.horseSaddleFront.rotateAngleX = this.body.rotateAngleX;
/* 489 */       this.horseSaddleBack.rotateAngleX = this.body.rotateAngleX;
/* 490 */       this.horseLeftRein.rotationPointY = this.head.rotationPointY;
/* 491 */       this.horseRightRein.rotationPointY = this.head.rotationPointY;
/* 492 */       this.horseFaceRopes.rotationPointY = this.head.rotationPointY;
/* 493 */       this.horseLeftFaceMetal.rotationPointY = this.head.rotationPointY;
/* 494 */       this.horseRightFaceMetal.rotationPointY = this.head.rotationPointY;
/* 495 */       this.horseLeftRein.rotationPointZ = this.head.rotationPointZ;
/* 496 */       this.horseRightRein.rotationPointZ = this.head.rotationPointZ;
/* 497 */       this.horseFaceRopes.rotationPointZ = this.head.rotationPointZ;
/* 498 */       this.horseLeftFaceMetal.rotationPointZ = this.head.rotationPointZ;
/* 499 */       this.horseRightFaceMetal.rotationPointZ = this.head.rotationPointZ;
/* 500 */       this.horseLeftRein.rotateAngleX = f4;
/* 501 */       this.horseRightRein.rotateAngleX = f4;
/* 502 */       this.horseFaceRopes.rotateAngleX = this.head.rotateAngleX;
/* 503 */       this.horseLeftFaceMetal.rotateAngleX = this.head.rotateAngleX;
/* 504 */       this.horseRightFaceMetal.rotateAngleX = this.head.rotateAngleX;
/* 505 */       this.horseFaceRopes.rotateAngleY = this.head.rotateAngleY;
/* 506 */       this.horseLeftFaceMetal.rotateAngleY = this.head.rotateAngleY;
/* 507 */       this.horseLeftRein.rotateAngleY = this.head.rotateAngleY;
/* 508 */       this.horseRightFaceMetal.rotateAngleY = this.head.rotateAngleY;
/* 509 */       this.horseRightRein.rotateAngleY = this.head.rotateAngleY;
/*     */       
/* 511 */       if (flag2) {
/* 512 */         this.horseLeftSaddleRope.rotateAngleX = -1.0471976F;
/* 513 */         this.horseLeftSaddleMetal.rotateAngleX = -1.0471976F;
/* 514 */         this.horseRightSaddleRope.rotateAngleX = -1.0471976F;
/* 515 */         this.horseRightSaddleMetal.rotateAngleX = -1.0471976F;
/* 516 */         this.horseLeftSaddleRope.rotateAngleZ = 0.0F;
/* 517 */         this.horseLeftSaddleMetal.rotateAngleZ = 0.0F;
/* 518 */         this.horseRightSaddleRope.rotateAngleZ = 0.0F;
/* 519 */         this.horseRightSaddleMetal.rotateAngleZ = 0.0F;
/*     */       } else {
/* 521 */         this.horseLeftSaddleRope.rotateAngleX = f11 / 3.0F;
/* 522 */         this.horseLeftSaddleMetal.rotateAngleX = f11 / 3.0F;
/* 523 */         this.horseRightSaddleRope.rotateAngleX = f11 / 3.0F;
/* 524 */         this.horseRightSaddleMetal.rotateAngleX = f11 / 3.0F;
/* 525 */         this.horseLeftSaddleRope.rotateAngleZ = f11 / 5.0F;
/* 526 */         this.horseLeftSaddleMetal.rotateAngleZ = f11 / 5.0F;
/* 527 */         this.horseRightSaddleRope.rotateAngleZ = -f11 / 5.0F;
/* 528 */         this.horseRightSaddleMetal.rotateAngleZ = -f11 / 5.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 532 */     f12 = -1.3089F + p_78086_3_ * 1.5F;
/*     */     
/* 534 */     if (f12 > 0.0F) {
/* 535 */       f12 = 0.0F;
/*     */     }
/*     */     
/* 538 */     if (flag) {
/* 539 */       this.tailBase.rotateAngleY = MathHelper.cos(f9 * 0.7F);
/* 540 */       f12 = 0.0F;
/*     */     } else {
/* 542 */       this.tailBase.rotateAngleY = 0.0F;
/*     */     } 
/*     */     
/* 545 */     this.tailMiddle.rotateAngleY = this.tailBase.rotateAngleY;
/* 546 */     this.tailTip.rotateAngleY = this.tailBase.rotateAngleY;
/* 547 */     this.tailMiddle.rotationPointY = this.tailBase.rotationPointY;
/* 548 */     this.tailTip.rotationPointY = this.tailBase.rotationPointY;
/* 549 */     this.tailMiddle.rotationPointZ = this.tailBase.rotationPointZ;
/* 550 */     this.tailTip.rotationPointZ = this.tailBase.rotationPointZ;
/* 551 */     this.tailBase.rotateAngleX = f12;
/* 552 */     this.tailMiddle.rotateAngleX = f12;
/* 553 */     this.tailTip.rotateAngleX = -0.2618F + f12;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\model\ModelHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */