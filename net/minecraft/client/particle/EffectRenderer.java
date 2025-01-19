/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class EffectRenderer
/*     */ {
/*  37 */   private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
/*     */ 
/*     */   
/*     */   protected World worldObj;
/*     */ 
/*     */   
/*  43 */   private List<EntityFX>[][] fxLayers = (List<EntityFX>[][])new List[4][];
/*  44 */   private List<EntityParticleEmitter> particleEmitters = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   private TextureManager renderer;
/*     */ 
/*     */   
/*  50 */   private Random rand = new Random();
/*  51 */   private Map<Integer, IParticleFactory> particleTypes = Maps.newHashMap();
/*     */   
/*     */   public EffectRenderer(World worldIn, TextureManager rendererIn) {
/*  54 */     this.worldObj = worldIn;
/*  55 */     this.renderer = rendererIn;
/*     */     
/*  57 */     for (int i = 0; i < 4; i++) {
/*  58 */       this.fxLayers[i] = (List<EntityFX>[])new List[2];
/*     */       
/*  60 */       for (int j = 0; j < 2; j++) {
/*  61 */         this.fxLayers[i][j] = Lists.newArrayList();
/*     */       }
/*     */     } 
/*     */     
/*  65 */     registerVanillaParticles();
/*     */   }
/*     */   
/*     */   private void registerVanillaParticles() {
/*  69 */     registerParticle(EnumParticleTypes.EXPLOSION_NORMAL.getParticleID(), new EntityExplodeFX.Factory());
/*  70 */     registerParticle(EnumParticleTypes.WATER_BUBBLE.getParticleID(), new EntityBubbleFX.Factory());
/*  71 */     registerParticle(EnumParticleTypes.WATER_SPLASH.getParticleID(), new EntitySplashFX.Factory());
/*  72 */     registerParticle(EnumParticleTypes.WATER_WAKE.getParticleID(), new EntityFishWakeFX.Factory());
/*  73 */     registerParticle(EnumParticleTypes.WATER_DROP.getParticleID(), new EntityRainFX.Factory());
/*  74 */     registerParticle(EnumParticleTypes.SUSPENDED.getParticleID(), new EntitySuspendFX.Factory());
/*  75 */     registerParticle(EnumParticleTypes.SUSPENDED_DEPTH.getParticleID(), new EntityAuraFX.Factory());
/*  76 */     registerParticle(EnumParticleTypes.CRIT.getParticleID(), new EntityCrit2FX.Factory());
/*  77 */     registerParticle(EnumParticleTypes.CRIT_MAGIC.getParticleID(), new EntityCrit2FX.MagicFactory());
/*  78 */     registerParticle(EnumParticleTypes.SMOKE_NORMAL.getParticleID(), new EntitySmokeFX.Factory());
/*  79 */     registerParticle(EnumParticleTypes.SMOKE_LARGE.getParticleID(), new EntityCritFX.Factory());
/*  80 */     registerParticle(EnumParticleTypes.SPELL.getParticleID(), new EntitySpellParticleFX.Factory());
/*  81 */     registerParticle(EnumParticleTypes.SPELL_INSTANT.getParticleID(), new EntitySpellParticleFX.InstantFactory());
/*  82 */     registerParticle(EnumParticleTypes.SPELL_MOB.getParticleID(), new EntitySpellParticleFX.MobFactory());
/*  83 */     registerParticle(EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID(), new EntitySpellParticleFX.AmbientMobFactory());
/*  84 */     registerParticle(EnumParticleTypes.SPELL_WITCH.getParticleID(), new EntitySpellParticleFX.WitchFactory());
/*  85 */     registerParticle(EnumParticleTypes.DRIP_WATER.getParticleID(), new EntityDropParticleFX.WaterFactory());
/*  86 */     registerParticle(EnumParticleTypes.DRIP_LAVA.getParticleID(), new EntityDropParticleFX.LavaFactory());
/*  87 */     registerParticle(EnumParticleTypes.VILLAGER_ANGRY.getParticleID(), new EntityHeartFX.AngryVillagerFactory());
/*  88 */     registerParticle(EnumParticleTypes.VILLAGER_HAPPY.getParticleID(), new EntityAuraFX.HappyVillagerFactory());
/*  89 */     registerParticle(EnumParticleTypes.TOWN_AURA.getParticleID(), new EntityAuraFX.Factory());
/*  90 */     registerParticle(EnumParticleTypes.NOTE.getParticleID(), new EntityNoteFX.Factory());
/*  91 */     registerParticle(EnumParticleTypes.PORTAL.getParticleID(), new EntityPortalFX.Factory());
/*  92 */     registerParticle(EnumParticleTypes.ENCHANTMENT_TABLE.getParticleID(), new EntityEnchantmentTableParticleFX.EnchantmentTable());
/*  93 */     registerParticle(EnumParticleTypes.FLAME.getParticleID(), new EntityFlameFX.Factory());
/*  94 */     registerParticle(EnumParticleTypes.LAVA.getParticleID(), new EntityLavaFX.Factory());
/*  95 */     registerParticle(EnumParticleTypes.FOOTSTEP.getParticleID(), new EntityFootStepFX.Factory());
/*  96 */     registerParticle(EnumParticleTypes.CLOUD.getParticleID(), new EntityCloudFX.Factory());
/*  97 */     registerParticle(EnumParticleTypes.REDSTONE.getParticleID(), new EntityReddustFX.Factory());
/*  98 */     registerParticle(EnumParticleTypes.SNOWBALL.getParticleID(), new EntityBreakingFX.SnowballFactory());
/*  99 */     registerParticle(EnumParticleTypes.SNOW_SHOVEL.getParticleID(), new EntitySnowShovelFX.Factory());
/* 100 */     registerParticle(EnumParticleTypes.SLIME.getParticleID(), new EntityBreakingFX.SlimeFactory());
/* 101 */     registerParticle(EnumParticleTypes.HEART.getParticleID(), new EntityHeartFX.Factory());
/* 102 */     registerParticle(EnumParticleTypes.BARRIER.getParticleID(), new Barrier.Factory());
/* 103 */     registerParticle(EnumParticleTypes.ITEM_CRACK.getParticleID(), new EntityBreakingFX.Factory());
/* 104 */     registerParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), new EntityDiggingFX.Factory());
/* 105 */     registerParticle(EnumParticleTypes.BLOCK_DUST.getParticleID(), new EntityBlockDustFX.Factory());
/* 106 */     registerParticle(EnumParticleTypes.EXPLOSION_HUGE.getParticleID(), new EntityHugeExplodeFX.Factory());
/* 107 */     registerParticle(EnumParticleTypes.EXPLOSION_LARGE.getParticleID(), new EntityLargeExplodeFX.Factory());
/* 108 */     registerParticle(EnumParticleTypes.FIREWORKS_SPARK.getParticleID(), new EntityFirework.Factory());
/* 109 */     registerParticle(EnumParticleTypes.MOB_APPEARANCE.getParticleID(), new MobAppearance.Factory());
/*     */   }
/*     */   
/*     */   public void registerParticle(int id, IParticleFactory particleFactory) {
/* 113 */     this.particleTypes.put(Integer.valueOf(id), particleFactory);
/*     */   }
/*     */   
/*     */   public void emitParticleAtEntity(Entity entityIn, EnumParticleTypes particleTypes) {
/* 117 */     this.particleEmitters.add(new EntityParticleEmitter(this.worldObj, entityIn, particleTypes));
/*     */   }
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
/*     */   public EntityFX spawnEffectParticle(int particleId, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
/* 132 */     IParticleFactory iparticlefactory = this.particleTypes.get(Integer.valueOf(particleId));
/*     */     
/* 134 */     if (iparticlefactory != null) {
/* 135 */       EntityFX entityfx = iparticlefactory.getEntityFX(particleId, this.worldObj, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
/*     */       
/* 137 */       if (entityfx != null) {
/* 138 */         addEffect(entityfx);
/* 139 */         return entityfx;
/*     */       } 
/*     */     } 
/*     */     
/* 143 */     return null;
/*     */   }
/*     */   
/*     */   public void addEffect(EntityFX effect) {
/* 147 */     if (effect != null && (
/* 148 */       !(effect instanceof EntityFirework.SparkFX) || Config.isFireworkParticles())) {
/* 149 */       int i = effect.getFXLayer();
/* 150 */       int j = (effect.getAlpha() != 1.0F) ? 0 : 1;
/*     */       
/* 152 */       if (this.fxLayers[i][j].size() >= 4000) {
/* 153 */         this.fxLayers[i][j].remove(0);
/*     */       }
/*     */       
/* 156 */       this.fxLayers[i][j].add(effect);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEffects() {
/* 162 */     for (int i = 0; i < 4; i++) {
/* 163 */       updateEffectLayer(i);
/*     */     }
/*     */     
/* 166 */     List<EntityParticleEmitter> list = Lists.newArrayList();
/*     */     
/* 168 */     for (EntityParticleEmitter entityparticleemitter : this.particleEmitters) {
/* 169 */       entityparticleemitter.onUpdate();
/*     */       
/* 171 */       if (entityparticleemitter.isDead) {
/* 172 */         list.add(entityparticleemitter);
/*     */       }
/*     */     } 
/*     */     
/* 176 */     this.particleEmitters.removeAll(list);
/*     */   }
/*     */   
/*     */   private void updateEffectLayer(int layer) {
/* 180 */     for (int i = 0; i < 2; i++) {
/* 181 */       updateEffectAlphaLayer(this.fxLayers[layer][i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateEffectAlphaLayer(List<EntityFX> entitiesFX) {
/* 186 */     List<EntityFX> list = Lists.newArrayList();
/* 187 */     long i = System.currentTimeMillis();
/* 188 */     int j = entitiesFX.size();
/*     */     
/* 190 */     for (int k = 0; k < entitiesFX.size(); k++) {
/* 191 */       EntityFX entityfx = entitiesFX.get(k);
/* 192 */       tickParticle(entityfx);
/*     */       
/* 194 */       if (entityfx.isDead) {
/* 195 */         list.add(entityfx);
/*     */       }
/*     */       
/* 198 */       j--;
/*     */       
/* 200 */       if (System.currentTimeMillis() > i + 20L) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 205 */     if (j > 0) {
/* 206 */       int l = j;
/*     */       
/* 208 */       for (Iterator<EntityFX> iterator = entitiesFX.iterator(); iterator.hasNext() && l > 0; l--) {
/* 209 */         EntityFX entityfx1 = iterator.next();
/* 210 */         entityfx1.setDead();
/* 211 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     entitiesFX.removeAll(list);
/*     */   }
/*     */   
/*     */   private void tickParticle(final EntityFX particle) {
/*     */     try {
/* 220 */       particle.onUpdate();
/* 221 */     } catch (Throwable throwable) {
/* 222 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking Particle");
/* 223 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being ticked");
/* 224 */       final int i = particle.getFXLayer();
/* 225 */       crashreportcategory.addCrashSectionCallable("Particle", new Callable<String>() {
/*     */             public String call() throws Exception {
/* 227 */               return particle.toString();
/*     */             }
/*     */           });
/* 230 */       crashreportcategory.addCrashSectionCallable("Particle Type", new Callable<String>() {
/*     */             public String call() throws Exception {
/* 232 */               return (i == 0) ? "MISC_TEXTURE" : ((i == 1) ? "TERRAIN_TEXTURE" : ((i == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + i)));
/*     */             }
/*     */           });
/* 235 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticles(Entity entityIn, float partialTicks) {
/* 243 */     float f = ActiveRenderInfo.getRotationX();
/* 244 */     float f1 = ActiveRenderInfo.getRotationZ();
/* 245 */     float f2 = ActiveRenderInfo.getRotationYZ();
/* 246 */     float f3 = ActiveRenderInfo.getRotationXY();
/* 247 */     float f4 = ActiveRenderInfo.getRotationXZ();
/* 248 */     EntityFX.interpPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 249 */     EntityFX.interpPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 250 */     EntityFX.interpPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 251 */     GlStateManager.enableBlend();
/* 252 */     GlStateManager.blendFunc(770, 771);
/* 253 */     GlStateManager.alphaFunc(516, 0.003921569F);
/* 254 */     Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(this.worldObj, entityIn, partialTicks);
/* 255 */     boolean flag = (block.getMaterial() == Material.water);
/*     */     
/* 257 */     for (int i = 0; i < 3; i++) {
/* 258 */       for (int j = 0; j < 2; j++) {
/* 259 */         final int i_f = i;
/*     */         
/* 261 */         if (!this.fxLayers[i][j].isEmpty()) {
/* 262 */           switch (j) {
/*     */             case 0:
/* 264 */               GlStateManager.depthMask(false);
/*     */               break;
/*     */             
/*     */             case 1:
/* 268 */               GlStateManager.depthMask(true);
/*     */               break;
/*     */           } 
/* 271 */           switch (i) {
/*     */             
/*     */             default:
/* 274 */               this.renderer.bindTexture(particleTextures);
/*     */               break;
/*     */             
/*     */             case 1:
/* 278 */               this.renderer.bindTexture(TextureMap.locationBlocksTexture);
/*     */               break;
/*     */           } 
/* 281 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 282 */           Tessellator tessellator = Tessellator.getInstance();
/* 283 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 284 */           worldrenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*     */           
/* 286 */           for (int k = 0; k < this.fxLayers[i][j].size(); k++) {
/* 287 */             final EntityFX entityfx = this.fxLayers[i][j].get(k);
/*     */             
/*     */             try {
/* 290 */               if (flag || !(entityfx instanceof EntitySuspendFX)) {
/* 291 */                 entityfx.renderParticle(worldrenderer, entityIn, partialTicks, f, f4, f1, f2, f3);
/*     */               }
/* 293 */             } catch (Throwable throwable) {
/* 294 */               CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Particle");
/* 295 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being rendered");
/* 296 */               crashreportcategory.addCrashSectionCallable("Particle", new Callable<String>() {
/*     */                     public String call() throws Exception {
/* 298 */                       return entityfx.toString();
/*     */                     }
/*     */                   });
/* 301 */               crashreportcategory.addCrashSectionCallable("Particle Type", new Callable<String>() {
/*     */                     public String call() throws Exception {
/* 303 */                       return (i_f == 0) ? "MISC_TEXTURE" : ((i_f == 1) ? "TERRAIN_TEXTURE" : ((i_f == 3) ? "ENTITY_PARTICLE_TEXTURE" : ("Unknown - " + i_f)));
/*     */                     }
/*     */                   });
/* 306 */               throw new ReportedException(crashreport);
/*     */             } 
/*     */           } 
/*     */           
/* 310 */           tessellator.draw();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 315 */     GlStateManager.depthMask(true);
/* 316 */     GlStateManager.disableBlend();
/* 317 */     GlStateManager.alphaFunc(516, 0.1F);
/*     */   }
/*     */   
/*     */   public void renderLitParticles(Entity entityIn, float partialTick) {
/* 321 */     float f = 0.017453292F;
/* 322 */     float f1 = MathHelper.cos(entityIn.rotationYaw * 0.017453292F);
/* 323 */     float f2 = MathHelper.sin(entityIn.rotationYaw * 0.017453292F);
/* 324 */     float f3 = -f2 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
/* 325 */     float f4 = f1 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
/* 326 */     float f5 = MathHelper.cos(entityIn.rotationPitch * 0.017453292F);
/*     */     
/* 328 */     for (int i = 0; i < 2; i++) {
/* 329 */       List<EntityFX> list = this.fxLayers[3][i];
/*     */       
/* 331 */       if (!list.isEmpty()) {
/* 332 */         Tessellator tessellator = Tessellator.getInstance();
/* 333 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */         
/* 335 */         for (int j = 0; j < list.size(); j++) {
/* 336 */           EntityFX entityfx = list.get(j);
/* 337 */           entityfx.renderParticle(worldrenderer, entityIn, partialTick, f1, f5, f2, f3, f4);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearEffects(World worldIn) {
/* 344 */     this.worldObj = worldIn;
/*     */     
/* 346 */     for (int i = 0; i < 4; i++) {
/* 347 */       for (int j = 0; j < 2; j++) {
/* 348 */         this.fxLayers[i][j].clear();
/*     */       }
/*     */     } 
/*     */     
/* 352 */     this.particleEmitters.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBlockDestroyEffects(BlockPos pos, IBlockState state) {
/*     */     boolean flag;
/* 358 */     if (Reflector.ForgeBlock_addDestroyEffects.exists() && Reflector.ForgeBlock_isAir.exists()) {
/* 359 */       Block block = state.getBlock();
/* 360 */       flag = (!Reflector.callBoolean(block, Reflector.ForgeBlock_isAir, new Object[] { this.worldObj, pos }) && !Reflector.callBoolean(block, Reflector.ForgeBlock_addDestroyEffects, new Object[] { this.worldObj, pos, this }));
/*     */     } else {
/* 362 */       flag = (state.getBlock().getMaterial() != Material.air);
/*     */     } 
/*     */     
/* 365 */     if (flag) {
/* 366 */       state = state.getBlock().getActualState(state, (IBlockAccess)this.worldObj, pos);
/* 367 */       int l = 4;
/*     */       
/* 369 */       for (int i = 0; i < l; i++) {
/* 370 */         for (int j = 0; j < l; j++) {
/* 371 */           for (int k = 0; k < l; k++) {
/* 372 */             double d0 = pos.getX() + (i + 0.5D) / l;
/* 373 */             double d1 = pos.getY() + (j + 0.5D) / l;
/* 374 */             double d2 = pos.getZ() + (k + 0.5D) / l;
/* 375 */             addEffect((new EntityDiggingFX(this.worldObj, d0, d1, d2, d0 - pos.getX() - 0.5D, d1 - pos.getY() - 0.5D, d2 - pos.getZ() - 0.5D, state)).setBlockPos(pos));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBlockHitEffects(BlockPos pos, EnumFacing side) {
/* 386 */     IBlockState iblockstate = this.worldObj.getBlockState(pos);
/* 387 */     Block block = iblockstate.getBlock();
/*     */     
/* 389 */     if (block.getRenderType() != -1) {
/* 390 */       int i = pos.getX();
/* 391 */       int j = pos.getY();
/* 392 */       int k = pos.getZ();
/* 393 */       float f = 0.1F;
/* 394 */       double d0 = i + this.rand.nextDouble() * (block.getBlockBoundsMaxX() - block.getBlockBoundsMinX() - (f * 2.0F)) + f + block.getBlockBoundsMinX();
/* 395 */       double d1 = j + this.rand.nextDouble() * (block.getBlockBoundsMaxY() - block.getBlockBoundsMinY() - (f * 2.0F)) + f + block.getBlockBoundsMinY();
/* 396 */       double d2 = k + this.rand.nextDouble() * (block.getBlockBoundsMaxZ() - block.getBlockBoundsMinZ() - (f * 2.0F)) + f + block.getBlockBoundsMinZ();
/*     */       
/* 398 */       if (side == EnumFacing.DOWN) {
/* 399 */         d1 = j + block.getBlockBoundsMinY() - f;
/*     */       }
/*     */       
/* 402 */       if (side == EnumFacing.UP) {
/* 403 */         d1 = j + block.getBlockBoundsMaxY() + f;
/*     */       }
/*     */       
/* 406 */       if (side == EnumFacing.NORTH) {
/* 407 */         d2 = k + block.getBlockBoundsMinZ() - f;
/*     */       }
/*     */       
/* 410 */       if (side == EnumFacing.SOUTH) {
/* 411 */         d2 = k + block.getBlockBoundsMaxZ() + f;
/*     */       }
/*     */       
/* 414 */       if (side == EnumFacing.WEST) {
/* 415 */         d0 = i + block.getBlockBoundsMinX() - f;
/*     */       }
/*     */       
/* 418 */       if (side == EnumFacing.EAST) {
/* 419 */         d0 = i + block.getBlockBoundsMaxX() + f;
/*     */       }
/*     */       
/* 422 */       addEffect((new EntityDiggingFX(this.worldObj, d0, d1, d2, 0.0D, 0.0D, 0.0D, iblockstate)).setBlockPos(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void moveToAlphaLayer(EntityFX effect) {
/* 427 */     moveToLayer(effect, 1, 0);
/*     */   }
/*     */   
/*     */   public void moveToNoAlphaLayer(EntityFX effect) {
/* 431 */     moveToLayer(effect, 0, 1);
/*     */   }
/*     */   
/*     */   private void moveToLayer(EntityFX effect, int layerFrom, int layerTo) {
/* 435 */     for (int i = 0; i < 4; i++) {
/* 436 */       if (this.fxLayers[i][layerFrom].contains(effect)) {
/* 437 */         this.fxLayers[i][layerFrom].remove(effect);
/* 438 */         this.fxLayers[i][layerTo].add(effect);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getStatistics() {
/* 444 */     int i = 0;
/*     */     
/* 446 */     for (int j = 0; j < 4; j++) {
/* 447 */       for (int k = 0; k < 2; k++) {
/* 448 */         i += this.fxLayers[j][k].size();
/*     */       }
/*     */     } 
/*     */     
/* 452 */     return i;
/*     */   }
/*     */   
/*     */   public void addBlockHitEffects(BlockPos p_addBlockHitEffects_1_, MovingObjectPosition p_addBlockHitEffects_2_) {
/* 456 */     IBlockState iblockstate = this.worldObj.getBlockState(p_addBlockHitEffects_1_);
/*     */     
/* 458 */     if (iblockstate != null) {
/* 459 */       boolean flag = Reflector.callBoolean(iblockstate.getBlock(), Reflector.ForgeBlock_addHitEffects, new Object[] { this.worldObj, p_addBlockHitEffects_2_, this });
/*     */       
/* 461 */       if (iblockstate != null && !flag)
/* 462 */         addBlockHitEffects(p_addBlockHitEffects_1_, p_addBlockHitEffects_2_.sideHit); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\particle\EffectRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */