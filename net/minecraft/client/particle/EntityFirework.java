/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.item.ItemDye;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityFirework {
/*     */   public static class Factory implements IParticleFactory {
/*     */     public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  16 */       EntityFirework.SparkFX entityfirework$sparkfx = new EntityFirework.SparkFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, (Minecraft.getMinecraft()).effectRenderer);
/*  17 */       entityfirework$sparkfx.setAlphaF(0.99F);
/*  18 */       return entityfirework$sparkfx;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class OverlayFX extends EntityFX {
/*     */     protected OverlayFX(World p_i46466_1_, double p_i46466_2_, double p_i46466_4_, double p_i46466_6_) {
/*  24 */       super(p_i46466_1_, p_i46466_2_, p_i46466_4_, p_i46466_6_);
/*  25 */       this.particleMaxAge = 4;
/*     */     }
/*     */     
/*     */     public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/*  29 */       float f = 0.25F;
/*  30 */       float f1 = 0.5F;
/*  31 */       float f2 = 0.125F;
/*  32 */       float f3 = 0.375F;
/*  33 */       float f4 = 7.1F * MathHelper.sin((this.particleAge + partialTicks - 1.0F) * 0.25F * 3.1415927F);
/*  34 */       this.particleAlpha = 0.6F - (this.particleAge + partialTicks - 1.0F) * 0.25F * 0.5F;
/*  35 */       float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/*  36 */       float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/*  37 */       float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/*  38 */       int i = getBrightnessForRender(partialTicks);
/*  39 */       int j = i >> 16 & 0xFFFF;
/*  40 */       int k = i & 0xFFFF;
/*  41 */       worldRendererIn.pos((f5 - rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 - rotationYZ * f4 - rotationXZ * f4)).tex(0.5D, 0.375D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*  42 */       worldRendererIn.pos((f5 - rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 - rotationYZ * f4 + rotationXZ * f4)).tex(0.5D, 0.125D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*  43 */       worldRendererIn.pos((f5 + rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 + rotationYZ * f4 + rotationXZ * f4)).tex(0.25D, 0.125D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*  44 */       worldRendererIn.pos((f5 + rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 + rotationYZ * f4 - rotationXZ * f4)).tex(0.25D, 0.375D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SparkFX extends EntityFX {
/*  49 */     private int baseTextureIndex = 160;
/*     */     private boolean trail;
/*     */     private boolean twinkle;
/*     */     private final EffectRenderer field_92047_az;
/*     */     private float fadeColourRed;
/*     */     private float fadeColourGreen;
/*     */     private float fadeColourBlue;
/*     */     private boolean hasFadeColour;
/*     */     
/*     */     public SparkFX(World p_i46465_1_, double p_i46465_2_, double p_i46465_4_, double p_i46465_6_, double p_i46465_8_, double p_i46465_10_, double p_i46465_12_, EffectRenderer p_i46465_14_) {
/*  59 */       super(p_i46465_1_, p_i46465_2_, p_i46465_4_, p_i46465_6_);
/*  60 */       this.motionX = p_i46465_8_;
/*  61 */       this.motionY = p_i46465_10_;
/*  62 */       this.motionZ = p_i46465_12_;
/*  63 */       this.field_92047_az = p_i46465_14_;
/*  64 */       this.particleScale *= 0.75F;
/*  65 */       this.particleMaxAge = 48 + this.rand.nextInt(12);
/*  66 */       this.noClip = false;
/*     */     }
/*     */     
/*     */     public void setTrail(boolean trailIn) {
/*  70 */       this.trail = trailIn;
/*     */     }
/*     */     
/*     */     public void setTwinkle(boolean twinkleIn) {
/*  74 */       this.twinkle = twinkleIn;
/*     */     }
/*     */     
/*     */     public void setColour(int colour) {
/*  78 */       float f = ((colour & 0xFF0000) >> 16) / 255.0F;
/*  79 */       float f1 = ((colour & 0xFF00) >> 8) / 255.0F;
/*  80 */       float f2 = ((colour & 0xFF) >> 0) / 255.0F;
/*  81 */       float f3 = 1.0F;
/*  82 */       setRBGColorF(f * f3, f1 * f3, f2 * f3);
/*     */     }
/*     */     
/*     */     public void setFadeColour(int faceColour) {
/*  86 */       this.fadeColourRed = ((faceColour & 0xFF0000) >> 16) / 255.0F;
/*  87 */       this.fadeColourGreen = ((faceColour & 0xFF00) >> 8) / 255.0F;
/*  88 */       this.fadeColourBlue = ((faceColour & 0xFF) >> 0) / 255.0F;
/*  89 */       this.hasFadeColour = true;
/*     */     }
/*     */     
/*     */     public AxisAlignedBB getCollisionBoundingBox() {
/*  93 */       return null;
/*     */     }
/*     */     
/*     */     public boolean canBePushed() {
/*  97 */       return false;
/*     */     }
/*     */     
/*     */     public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 101 */       if (!this.twinkle || this.particleAge < this.particleMaxAge / 3 || (this.particleAge + this.particleMaxAge) / 3 % 2 == 0) {
/* 102 */         super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*     */       }
/*     */     }
/*     */     
/*     */     public void onUpdate() {
/* 107 */       this.prevPosX = this.posX;
/* 108 */       this.prevPosY = this.posY;
/* 109 */       this.prevPosZ = this.posZ;
/*     */       
/* 111 */       if (this.particleAge++ >= this.particleMaxAge) {
/* 112 */         setDead();
/*     */       }
/*     */       
/* 115 */       if (this.particleAge > this.particleMaxAge / 2) {
/* 116 */         setAlphaF(1.0F - (this.particleAge - (this.particleMaxAge / 2)) / this.particleMaxAge);
/*     */         
/* 118 */         if (this.hasFadeColour) {
/* 119 */           this.particleRed += (this.fadeColourRed - this.particleRed) * 0.2F;
/* 120 */           this.particleGreen += (this.fadeColourGreen - this.particleGreen) * 0.2F;
/* 121 */           this.particleBlue += (this.fadeColourBlue - this.particleBlue) * 0.2F;
/*     */         } 
/*     */       } 
/*     */       
/* 125 */       setParticleTextureIndex(this.baseTextureIndex + 7 - this.particleAge * 8 / this.particleMaxAge);
/* 126 */       this.motionY -= 0.004D;
/* 127 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 128 */       this.motionX *= 0.9100000262260437D;
/* 129 */       this.motionY *= 0.9100000262260437D;
/* 130 */       this.motionZ *= 0.9100000262260437D;
/*     */       
/* 132 */       if (this.onGround) {
/* 133 */         this.motionX *= 0.699999988079071D;
/* 134 */         this.motionZ *= 0.699999988079071D;
/*     */       } 
/*     */       
/* 137 */       if (this.trail && this.particleAge < this.particleMaxAge / 2 && (this.particleAge + this.particleMaxAge) % 2 == 0) {
/* 138 */         SparkFX entityfirework$sparkfx = new SparkFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, this.field_92047_az);
/* 139 */         entityfirework$sparkfx.setAlphaF(0.99F);
/* 140 */         entityfirework$sparkfx.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
/* 141 */         entityfirework$sparkfx.particleAge = entityfirework$sparkfx.particleMaxAge / 2;
/*     */         
/* 143 */         if (this.hasFadeColour) {
/* 144 */           entityfirework$sparkfx.hasFadeColour = true;
/* 145 */           entityfirework$sparkfx.fadeColourRed = this.fadeColourRed;
/* 146 */           entityfirework$sparkfx.fadeColourGreen = this.fadeColourGreen;
/* 147 */           entityfirework$sparkfx.fadeColourBlue = this.fadeColourBlue;
/*     */         } 
/*     */         
/* 150 */         entityfirework$sparkfx.twinkle = this.twinkle;
/* 151 */         this.field_92047_az.addEffect(entityfirework$sparkfx);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getBrightnessForRender(float partialTicks) {
/* 156 */       return 15728880;
/*     */     }
/*     */     
/*     */     public float getBrightness(float partialTicks) {
/* 160 */       return 1.0F;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class StarterFX extends EntityFX {
/*     */     private int fireworkAge;
/*     */     private final EffectRenderer theEffectRenderer;
/*     */     private NBTTagList fireworkExplosions;
/*     */     boolean twinkle;
/*     */     
/*     */     public StarterFX(World p_i46464_1_, double p_i46464_2_, double p_i46464_4_, double p_i46464_6_, double p_i46464_8_, double p_i46464_10_, double p_i46464_12_, EffectRenderer p_i46464_14_, NBTTagCompound p_i46464_15_) {
/* 171 */       super(p_i46464_1_, p_i46464_2_, p_i46464_4_, p_i46464_6_, 0.0D, 0.0D, 0.0D);
/* 172 */       this.motionX = p_i46464_8_;
/* 173 */       this.motionY = p_i46464_10_;
/* 174 */       this.motionZ = p_i46464_12_;
/* 175 */       this.theEffectRenderer = p_i46464_14_;
/* 176 */       this.particleMaxAge = 8;
/*     */       
/* 178 */       if (p_i46464_15_ != null) {
/* 179 */         this.fireworkExplosions = p_i46464_15_.getTagList("Explosions", 10);
/*     */         
/* 181 */         if (this.fireworkExplosions.tagCount() == 0) {
/* 182 */           this.fireworkExplosions = null;
/*     */         } else {
/* 184 */           this.particleMaxAge = this.fireworkExplosions.tagCount() * 2 - 1;
/*     */           
/* 186 */           for (int i = 0; i < this.fireworkExplosions.tagCount(); i++) {
/* 187 */             NBTTagCompound nbttagcompound = this.fireworkExplosions.getCompoundTagAt(i);
/*     */             
/* 189 */             if (nbttagcompound.getBoolean("Flicker")) {
/* 190 */               this.twinkle = true;
/* 191 */               this.particleMaxAge += 15;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {}
/*     */     
/*     */     public void onUpdate() {
/* 203 */       if (this.fireworkAge == 0 && this.fireworkExplosions != null) {
/* 204 */         boolean flag = func_92037_i();
/* 205 */         boolean flag1 = false;
/*     */         
/* 207 */         if (this.fireworkExplosions.tagCount() >= 3) {
/* 208 */           flag1 = true;
/*     */         } else {
/* 210 */           for (int i = 0; i < this.fireworkExplosions.tagCount(); i++) {
/* 211 */             NBTTagCompound nbttagcompound = this.fireworkExplosions.getCompoundTagAt(i);
/*     */             
/* 213 */             if (nbttagcompound.getByte("Type") == 1) {
/* 214 */               flag1 = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 220 */         String s1 = "fireworks." + (flag1 ? "largeBlast" : "blast") + (flag ? "_far" : "");
/* 221 */         this.worldObj.playSound(this.posX, this.posY, this.posZ, s1, 20.0F, 0.95F + this.rand.nextFloat() * 0.1F, true);
/*     */       } 
/*     */       
/* 224 */       if (this.fireworkAge % 2 == 0 && this.fireworkExplosions != null && this.fireworkAge / 2 < this.fireworkExplosions.tagCount()) {
/* 225 */         int k = this.fireworkAge / 2;
/* 226 */         NBTTagCompound nbttagcompound1 = this.fireworkExplosions.getCompoundTagAt(k);
/* 227 */         int l = nbttagcompound1.getByte("Type");
/* 228 */         boolean flag4 = nbttagcompound1.getBoolean("Trail");
/* 229 */         boolean flag2 = nbttagcompound1.getBoolean("Flicker");
/* 230 */         int[] aint = nbttagcompound1.getIntArray("Colors");
/* 231 */         int[] aint1 = nbttagcompound1.getIntArray("FadeColors");
/*     */         
/* 233 */         if (aint.length == 0) {
/* 234 */           aint = new int[] { ItemDye.dyeColors[0] };
/*     */         }
/*     */         
/* 237 */         if (l == 1) {
/* 238 */           createBall(0.5D, 4, aint, aint1, flag4, flag2);
/* 239 */         } else if (l == 2) {
/* 240 */           createShaped(0.5D, new double[][] { { 0.0D, 1.0D }, , { 0.3455D, 0.309D }, , { 0.9511D, 0.309D }, , { 0.3795918367346939D, -0.12653061224489795D }, , { 0.6122448979591837D, -0.8040816326530612D }, , { 0.0D, -0.35918367346938773D },  }, aint, aint1, flag4, flag2, false);
/* 241 */         } else if (l == 3) {
/* 242 */           createShaped(0.5D, new double[][] { { 0.0D, 0.2D }, , { 0.2D, 0.2D }, , { 0.2D, 0.6D }, , { 0.6D, 0.6D }, , { 0.6D, 0.2D }, , { 0.2D, 0.2D }, , { 0.2D, 0.0D }, , { 0.4D, 0.0D }, , { 0.4D, -0.6D }, , { 0.2D, -0.6D }, , { 0.2D, -0.4D }, , { 0.0D, -0.4D },  }, aint, aint1, flag4, flag2, true);
/* 243 */         } else if (l == 4) {
/* 244 */           createBurst(aint, aint1, flag4, flag2);
/*     */         } else {
/* 246 */           createBall(0.25D, 2, aint, aint1, flag4, flag2);
/*     */         } 
/*     */         
/* 249 */         int j = aint[0];
/* 250 */         float f = ((j & 0xFF0000) >> 16) / 255.0F;
/* 251 */         float f1 = ((j & 0xFF00) >> 8) / 255.0F;
/* 252 */         float f2 = ((j & 0xFF) >> 0) / 255.0F;
/* 253 */         EntityFirework.OverlayFX entityfirework$overlayfx = new EntityFirework.OverlayFX(this.worldObj, this.posX, this.posY, this.posZ);
/* 254 */         entityfirework$overlayfx.setRBGColorF(f, f1, f2);
/* 255 */         this.theEffectRenderer.addEffect(entityfirework$overlayfx);
/*     */       } 
/*     */       
/* 258 */       this.fireworkAge++;
/*     */       
/* 260 */       if (this.fireworkAge > this.particleMaxAge) {
/* 261 */         if (this.twinkle) {
/* 262 */           boolean flag3 = func_92037_i();
/* 263 */           String s = "fireworks." + (flag3 ? "twinkle_far" : "twinkle");
/* 264 */           this.worldObj.playSound(this.posX, this.posY, this.posZ, s, 20.0F, 0.9F + this.rand.nextFloat() * 0.15F, true);
/*     */         } 
/*     */         
/* 267 */         setDead();
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean func_92037_i() {
/* 272 */       Minecraft minecraft = Minecraft.getMinecraft();
/* 273 */       return !(minecraft != null && minecraft.getRenderViewEntity() != null && minecraft.getRenderViewEntity().getDistanceSq(this.posX, this.posY, this.posZ) < 256.0D);
/*     */     }
/*     */     
/*     */     private void createParticle(double p_92034_1_, double p_92034_3_, double p_92034_5_, double p_92034_7_, double p_92034_9_, double p_92034_11_, int[] p_92034_13_, int[] p_92034_14_, boolean p_92034_15_, boolean p_92034_16_) {
/* 277 */       EntityFirework.SparkFX entityfirework$sparkfx = new EntityFirework.SparkFX(this.worldObj, p_92034_1_, p_92034_3_, p_92034_5_, p_92034_7_, p_92034_9_, p_92034_11_, this.theEffectRenderer);
/* 278 */       entityfirework$sparkfx.setAlphaF(0.99F);
/* 279 */       entityfirework$sparkfx.setTrail(p_92034_15_);
/* 280 */       entityfirework$sparkfx.setTwinkle(p_92034_16_);
/* 281 */       int i = this.rand.nextInt(p_92034_13_.length);
/* 282 */       entityfirework$sparkfx.setColour(p_92034_13_[i]);
/*     */       
/* 284 */       if (p_92034_14_ != null && p_92034_14_.length > 0) {
/* 285 */         entityfirework$sparkfx.setFadeColour(p_92034_14_[this.rand.nextInt(p_92034_14_.length)]);
/*     */       }
/*     */       
/* 288 */       this.theEffectRenderer.addEffect(entityfirework$sparkfx);
/*     */     }
/*     */     
/*     */     private void createBall(double speed, int size, int[] colours, int[] fadeColours, boolean trail, boolean twinkleIn) {
/* 292 */       double d0 = this.posX;
/* 293 */       double d1 = this.posY;
/* 294 */       double d2 = this.posZ;
/*     */       
/* 296 */       for (int i = -size; i <= size; i++) {
/* 297 */         for (int j = -size; j <= size; j++) {
/* 298 */           for (int k = -size; k <= size; k++) {
/* 299 */             double d3 = j + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
/* 300 */             double d4 = i + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
/* 301 */             double d5 = k + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
/* 302 */             double d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5) / speed + this.rand.nextGaussian() * 0.05D;
/* 303 */             createParticle(d0, d1, d2, d3 / d6, d4 / d6, d5 / d6, colours, fadeColours, trail, twinkleIn);
/*     */             
/* 305 */             if (i != -size && i != size && j != -size && j != size) {
/* 306 */               k += size * 2 - 1;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void createShaped(double speed, double[][] shape, int[] colours, int[] fadeColours, boolean trail, boolean twinkleIn, boolean p_92038_8_) {
/* 314 */       double d0 = shape[0][0];
/* 315 */       double d1 = shape[0][1];
/* 316 */       createParticle(this.posX, this.posY, this.posZ, d0 * speed, d1 * speed, 0.0D, colours, fadeColours, trail, twinkleIn);
/* 317 */       float f = this.rand.nextFloat() * 3.1415927F;
/* 318 */       double d2 = p_92038_8_ ? 0.034D : 0.34D;
/*     */       
/* 320 */       for (int i = 0; i < 3; i++) {
/* 321 */         double d3 = f + (i * 3.1415927F) * d2;
/* 322 */         double d4 = d0;
/* 323 */         double d5 = d1;
/*     */         
/* 325 */         for (int j = 1; j < shape.length; j++) {
/* 326 */           double d6 = shape[j][0];
/* 327 */           double d7 = shape[j][1];
/*     */           
/* 329 */           for (double d8 = 0.25D; d8 <= 1.0D; d8 += 0.25D) {
/* 330 */             double d9 = (d4 + (d6 - d4) * d8) * speed;
/* 331 */             double d10 = (d5 + (d7 - d5) * d8) * speed;
/* 332 */             double d11 = d9 * Math.sin(d3);
/* 333 */             d9 *= Math.cos(d3);
/*     */             
/* 335 */             for (double d12 = -1.0D; d12 <= 1.0D; d12 += 2.0D) {
/* 336 */               createParticle(this.posX, this.posY, this.posZ, d9 * d12, d10, d11 * d12, colours, fadeColours, trail, twinkleIn);
/*     */             }
/*     */           } 
/*     */           
/* 340 */           d4 = d6;
/* 341 */           d5 = d7;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void createBurst(int[] colours, int[] fadeColours, boolean trail, boolean twinkleIn) {
/* 347 */       double d0 = this.rand.nextGaussian() * 0.05D;
/* 348 */       double d1 = this.rand.nextGaussian() * 0.05D;
/*     */       
/* 350 */       for (int i = 0; i < 70; i++) {
/* 351 */         double d2 = this.motionX * 0.5D + this.rand.nextGaussian() * 0.15D + d0;
/* 352 */         double d3 = this.motionZ * 0.5D + this.rand.nextGaussian() * 0.15D + d1;
/* 353 */         double d4 = this.motionY * 0.5D + this.rand.nextDouble() * 0.5D;
/* 354 */         createParticle(this.posX, this.posY, this.posZ, d2, d4, d3, colours, fadeColours, trail, twinkleIn);
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getFXLayer() {
/* 359 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EntityFirework.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */