/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import client.Client;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBed;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelChicken;
/*     */ import net.minecraft.client.model.ModelCow;
/*     */ import net.minecraft.client.model.ModelHorse;
/*     */ import net.minecraft.client.model.ModelOcelot;
/*     */ import net.minecraft.client.model.ModelPig;
/*     */ import net.minecraft.client.model.ModelRabbit;
/*     */ import net.minecraft.client.model.ModelSheep2;
/*     */ import net.minecraft.client.model.ModelSlime;
/*     */ import net.minecraft.client.model.ModelSquid;
/*     */ import net.minecraft.client.model.ModelWolf;
/*     */ import net.minecraft.client.model.ModelZombie;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
/*     */ import net.minecraft.client.renderer.tileentity.RenderItemFrame;
/*     */ import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.EntityMinecartMobSpawner;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.item.EntityEnderEye;
/*     */ import net.minecraft.entity.item.EntityEnderPearl;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.item.EntityMinecartTNT;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCaveSpider;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityEndermite;
/*     */ import net.minecraft.entity.monster.EntityGhast;
/*     */ import net.minecraft.entity.monster.EntityGiantZombie;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntitySnowman;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityMooshroom;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.passive.EntitySquid;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityEgg;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.entity.model.CustomEntityModels;
/*     */ import net.optifine.player.PlayerItemsLayer;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class RenderManager
/*     */ {
/* 116 */   private Map<Class, Render> entityRenderMap = Maps.newHashMap();
/* 117 */   private Map<String, RenderPlayer> skinMap = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private RenderPlayer playerRenderer;
/*     */   
/*     */   private FontRenderer textRenderer;
/*     */   
/*     */   private double renderPosX;
/*     */   
/*     */   private double renderPosY;
/*     */   
/*     */   private double renderPosZ;
/*     */   
/*     */   public TextureManager renderEngine;
/*     */   
/*     */   public World worldObj;
/*     */   
/*     */   public Entity livingPlayer;
/*     */   
/*     */   public Entity pointedEntity;
/*     */   
/*     */   public float playerViewY;
/*     */   
/*     */   public float playerViewX;
/*     */   
/*     */   public GameSettings options;
/*     */   
/*     */   public double viewerPosX;
/*     */   
/*     */   public double viewerPosY;
/*     */   
/*     */   public double viewerPosZ;
/*     */   
/*     */   private boolean renderOutlines = false;
/*     */   
/*     */   private boolean renderShadow = true;
/*     */   
/*     */   private boolean debugBoundingBox = false;
/*     */   
/* 156 */   public Render renderRender = null;
/*     */   
/*     */   public RenderManager(TextureManager renderEngineIn, RenderItem itemRendererIn) {
/* 159 */     this.renderEngine = renderEngineIn;
/* 160 */     this.entityRenderMap.put(EntityCaveSpider.class, new RenderCaveSpider(this));
/* 161 */     this.entityRenderMap.put(EntitySpider.class, new RenderSpider<>(this));
/* 162 */     this.entityRenderMap.put(EntityPig.class, new RenderPig(this, (ModelBase)new ModelPig(), 0.7F));
/* 163 */     this.entityRenderMap.put(EntitySheep.class, new RenderSheep(this, (ModelBase)new ModelSheep2(), 0.7F));
/* 164 */     this.entityRenderMap.put(EntityCow.class, new RenderCow(this, (ModelBase)new ModelCow(), 0.7F));
/* 165 */     this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(this, (ModelBase)new ModelCow(), 0.7F));
/* 166 */     this.entityRenderMap.put(EntityWolf.class, new RenderWolf(this, (ModelBase)new ModelWolf(), 0.5F));
/* 167 */     this.entityRenderMap.put(EntityChicken.class, new RenderChicken(this, (ModelBase)new ModelChicken(), 0.3F));
/* 168 */     this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(this, (ModelBase)new ModelOcelot(), 0.4F));
/* 169 */     this.entityRenderMap.put(EntityRabbit.class, new RenderRabbit(this, (ModelBase)new ModelRabbit(), 0.3F));
/* 170 */     this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish(this));
/* 171 */     this.entityRenderMap.put(EntityEndermite.class, new RenderEndermite(this));
/* 172 */     this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper(this));
/* 173 */     this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman(this));
/* 174 */     this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan(this));
/* 175 */     this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton(this));
/* 176 */     this.entityRenderMap.put(EntityWitch.class, new RenderWitch(this));
/* 177 */     this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze(this));
/* 178 */     this.entityRenderMap.put(EntityPigZombie.class, new RenderPigZombie(this));
/* 179 */     this.entityRenderMap.put(EntityZombie.class, new RenderZombie(this));
/* 180 */     this.entityRenderMap.put(EntitySlime.class, new RenderSlime(this, (ModelBase)new ModelSlime(16), 0.25F));
/* 181 */     this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube(this));
/* 182 */     this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(this, (ModelBase)new ModelZombie(), 0.5F, 6.0F));
/* 183 */     this.entityRenderMap.put(EntityGhast.class, new RenderGhast(this));
/* 184 */     this.entityRenderMap.put(EntitySquid.class, new RenderSquid(this, (ModelBase)new ModelSquid(), 0.7F));
/* 185 */     this.entityRenderMap.put(EntityVillager.class, new RenderVillager(this));
/* 186 */     this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem(this));
/* 187 */     this.entityRenderMap.put(EntityBat.class, new RenderBat(this));
/* 188 */     this.entityRenderMap.put(EntityGuardian.class, new RenderGuardian(this));
/* 189 */     this.entityRenderMap.put(EntityDragon.class, new RenderDragon(this));
/* 190 */     this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal(this));
/* 191 */     this.entityRenderMap.put(EntityWither.class, new RenderWither(this));
/* 192 */     this.entityRenderMap.put(Entity.class, new RenderEntity(this));
/* 193 */     this.entityRenderMap.put(EntityPainting.class, new RenderPainting(this));
/* 194 */     this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame(this, itemRendererIn));
/* 195 */     this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot(this));
/* 196 */     this.entityRenderMap.put(EntityArrow.class, new RenderArrow(this));
/* 197 */     this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball<>(this, Items.snowball, itemRendererIn));
/* 198 */     this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball<>(this, Items.ender_pearl, itemRendererIn));
/* 199 */     this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball<>(this, Items.ender_eye, itemRendererIn));
/* 200 */     this.entityRenderMap.put(EntityEgg.class, new RenderSnowball<>(this, Items.egg, itemRendererIn));
/* 201 */     this.entityRenderMap.put(EntityPotion.class, new RenderPotion(this, itemRendererIn));
/* 202 */     this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball<>(this, Items.experience_bottle, itemRendererIn));
/* 203 */     this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball<>(this, Items.fireworks, itemRendererIn));
/* 204 */     this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(this, 2.0F));
/* 205 */     this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(this, 0.5F));
/* 206 */     this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull(this));
/* 207 */     this.entityRenderMap.put(EntityItem.class, new RenderEntityItem(this, itemRendererIn));
/* 208 */     this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb(this));
/* 209 */     this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed(this));
/* 210 */     this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock(this));
/* 211 */     this.entityRenderMap.put(EntityArmorStand.class, new ArmorStandRenderer(this));
/* 212 */     this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart(this));
/* 213 */     this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner(this));
/* 214 */     this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart<>(this));
/* 215 */     this.entityRenderMap.put(EntityBoat.class, new RenderBoat(this));
/* 216 */     this.entityRenderMap.put(EntityFishHook.class, new RenderFish(this));
/* 217 */     this.entityRenderMap.put(EntityHorse.class, new RenderHorse(this, new ModelHorse(), 0.75F));
/* 218 */     this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt(this));
/* 219 */     this.playerRenderer = new RenderPlayer(this);
/* 220 */     this.skinMap.put("default", this.playerRenderer);
/* 221 */     this.skinMap.put("slim", new RenderPlayer(this, true));
/* 222 */     PlayerItemsLayer.register(this.skinMap);
/*     */     
/* 224 */     if (Reflector.RenderingRegistry_loadEntityRenderers.exists()) {
/* 225 */       Reflector.call(Reflector.RenderingRegistry_loadEntityRenderers, new Object[] { this, this.entityRenderMap });
/*     */     }
/*     */   }
/*     */   
/*     */   public void setRenderPosition(double renderPosXIn, double renderPosYIn, double renderPosZIn) {
/* 230 */     this.renderPosX = renderPosXIn;
/* 231 */     this.renderPosY = renderPosYIn;
/* 232 */     this.renderPosZ = renderPosZIn;
/*     */   }
/*     */   
/*     */   public <T extends Entity> Render<T> getEntityClassRenderObject(Class<? extends Entity> entityClass) {
/* 236 */     Render<? extends Entity> render = this.entityRenderMap.get(entityClass);
/*     */     
/* 238 */     if (render == null && entityClass != Entity.class) {
/* 239 */       render = getEntityClassRenderObject((Class)entityClass.getSuperclass());
/* 240 */       this.entityRenderMap.put(entityClass, render);
/*     */     } 
/*     */     
/* 243 */     return (Render)render;
/*     */   }
/*     */   
/*     */   public <T extends Entity> Render<T> getEntityRenderObject(Entity entityIn) {
/* 247 */     if (entityIn instanceof AbstractClientPlayer) {
/* 248 */       String s = ((AbstractClientPlayer)entityIn).getSkinType();
/* 249 */       RenderPlayer renderplayer = this.skinMap.get(s);
/* 250 */       return (renderplayer != null) ? renderplayer : this.playerRenderer;
/*     */     } 
/* 252 */     return getEntityClassRenderObject((Class)entityIn.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public void cacheActiveRenderInfo(World worldIn, FontRenderer textRendererIn, Entity livingPlayerIn, Entity pointedEntityIn, GameSettings optionsIn, float partialTicks) {
/* 257 */     this.worldObj = worldIn;
/* 258 */     this.options = optionsIn;
/* 259 */     this.livingPlayer = livingPlayerIn;
/* 260 */     this.pointedEntity = pointedEntityIn;
/* 261 */     this.textRenderer = textRendererIn;
/*     */     
/* 263 */     if (livingPlayerIn instanceof EntityLivingBase && ((EntityLivingBase)livingPlayerIn).isPlayerSleeping()) {
/* 264 */       IBlockState iblockstate = worldIn.getBlockState(new BlockPos(livingPlayerIn));
/* 265 */       Block block = iblockstate.getBlock();
/*     */       
/* 267 */       if (Reflector.callBoolean(block, Reflector.ForgeBlock_isBed, new Object[] { iblockstate, worldIn, new BlockPos(livingPlayerIn), livingPlayerIn })) {
/* 268 */         EnumFacing enumfacing = (EnumFacing)Reflector.call(block, Reflector.ForgeBlock_getBedDirection, new Object[] { iblockstate, worldIn, new BlockPos(livingPlayerIn) });
/* 269 */         int i = enumfacing.getHorizontalIndex();
/* 270 */         this.playerViewY = (i * 90 + 180);
/* 271 */         this.playerViewX = 0.0F;
/* 272 */       } else if (block == Blocks.bed) {
/* 273 */         int j = ((EnumFacing)iblockstate.getValue((IProperty)BlockBed.FACING)).getHorizontalIndex();
/* 274 */         this.playerViewY = (j * 90 + 180);
/* 275 */         this.playerViewX = 0.0F;
/*     */       } 
/*     */     } else {
/* 278 */       this.playerViewY = livingPlayerIn.prevRotationYaw + (livingPlayerIn.rotationYaw - livingPlayerIn.prevRotationYaw) * partialTicks;
/* 279 */       this.playerViewX = livingPlayerIn.prevRotationPitch + (livingPlayerIn.rotationPitch - livingPlayerIn.prevRotationPitch) * partialTicks;
/*     */     } 
/*     */     
/* 282 */     if (optionsIn.showDebugInfo == 2) {
/* 283 */       this.playerViewY += 180.0F;
/*     */     }
/*     */     
/* 286 */     this.viewerPosX = livingPlayerIn.lastTickPosX + (livingPlayerIn.posX - livingPlayerIn.lastTickPosX) * partialTicks;
/* 287 */     this.viewerPosY = livingPlayerIn.lastTickPosY + (livingPlayerIn.posY - livingPlayerIn.lastTickPosY) * partialTicks;
/* 288 */     this.viewerPosZ = livingPlayerIn.lastTickPosZ + (livingPlayerIn.posZ - livingPlayerIn.lastTickPosZ) * partialTicks;
/*     */   }
/*     */   
/*     */   public void setPlayerViewY(float playerViewYIn) {
/* 292 */     this.playerViewY = playerViewYIn;
/*     */   }
/*     */   
/*     */   public boolean isRenderShadow() {
/* 296 */     return this.renderShadow;
/*     */   }
/*     */   
/*     */   public void setRenderShadow(boolean renderShadowIn) {
/* 300 */     this.renderShadow = renderShadowIn;
/*     */   }
/*     */   
/*     */   public void setDebugBoundingBox(boolean debugBoundingBoxIn) {
/* 304 */     this.debugBoundingBox = debugBoundingBoxIn;
/*     */   }
/*     */   
/*     */   public boolean isDebugBoundingBox() {
/* 308 */     return this.debugBoundingBox;
/*     */   }
/*     */   
/*     */   public boolean renderEntitySimple(Entity entityIn, float partialTicks) {
/* 312 */     return renderEntityStatic(entityIn, partialTicks, false);
/*     */   }
/*     */   
/*     */   public boolean shouldRender(Entity entityIn, ICamera camera, double camX, double camY, double camZ) {
/* 316 */     Render<Entity> render = getEntityRenderObject(entityIn);
/* 317 */     return (render != null && render.shouldRender(entityIn, camera, camX, camY, camZ));
/*     */   }
/*     */   
/*     */   public boolean renderEntityStatic(Entity entity, float partialTicks, boolean hideDebugBox) {
/* 321 */     if (entity.ticksExisted == 0) {
/* 322 */       entity.lastTickPosX = entity.posX;
/* 323 */       entity.lastTickPosY = entity.posY;
/* 324 */       entity.lastTickPosZ = entity.posZ;
/*     */     } 
/*     */     
/* 327 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 328 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 329 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 330 */     float f = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
/* 331 */     int i = entity.getBrightnessForRender(partialTicks);
/*     */     
/* 333 */     if (entity.isBurning()) {
/* 334 */       i = 15728880;
/*     */     }
/*     */     
/* 337 */     int j = i % 65536;
/* 338 */     int k = i / 65536;
/* 339 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 340 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 341 */     return doRenderEntity(entity, d0 - this.renderPosX, d1 - this.renderPosY, d2 - this.renderPosZ, f, partialTicks, hideDebugBox);
/*     */   }
/*     */   
/*     */   public void renderWitherSkull(Entity entityIn, float partialTicks) {
/* 345 */     double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 346 */     double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 347 */     double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 348 */     Render<Entity> render = getEntityRenderObject(entityIn);
/*     */     
/* 350 */     if (render != null && this.renderEngine != null) {
/* 351 */       int i = entityIn.getBrightnessForRender(partialTicks);
/* 352 */       int j = i % 65536;
/* 353 */       int k = i / 65536;
/* 354 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 355 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 356 */       render.renderName(entityIn, d0 - this.renderPosX, d1 - this.renderPosY, d2 - this.renderPosZ);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean renderEntityWithPosYaw(Entity entityIn, double x, double y, double z, float entityYaw, float partialTicks) {
/* 361 */     return doRenderEntity(entityIn, x, y, z, entityYaw, partialTicks, false);
/*     */   }
/*     */   
/*     */   public boolean doRenderEntity(Entity entity, double x, double y, double z, float entityYaw, float partialTicks, boolean hideDebugBox) {
/* 365 */     Render<Entity> render = null;
/*     */     
/*     */     try {
/* 368 */       render = getEntityRenderObject(entity);
/*     */       
/* 370 */       if (render != null && this.renderEngine != null) {
/*     */         try {
/* 372 */           if (render instanceof RendererLivingEntity) {
/* 373 */             ((RendererLivingEntity)render).setRenderOutlines(this.renderOutlines);
/*     */           }
/*     */           
/* 376 */           if (CustomEntityModels.isActive()) {
/* 377 */             this.renderRender = render;
/*     */           }
/*     */           
/* 380 */           render.doRender(entity, x, y, z, entityYaw, partialTicks);
/* 381 */         } catch (Throwable throwable2) {
/* 382 */           throw new ReportedException(CrashReport.makeCrashReport(throwable2, "Rendering entity in world"));
/*     */         } 
/*     */         
/*     */         try {
/* 386 */           if (!this.renderOutlines) {
/* 387 */             render.doRenderShadowAndFire(entity, x, y, z, entityYaw, partialTicks);
/*     */           }
/* 389 */         } catch (Throwable throwable1) {
/* 390 */           throw new ReportedException(CrashReport.makeCrashReport(throwable1, "Post-rendering entity in world"));
/*     */         } 
/*     */         
/* 393 */         if (this.debugBoundingBox && !entity.isInvisible() && !hideDebugBox) {
/*     */           try {
/* 395 */             renderDebugBoundingBox(entity, x, y, z, entityYaw, partialTicks);
/* 396 */           } catch (Throwable throwable) {
/* 397 */             throw new ReportedException(CrashReport.makeCrashReport(throwable, "Rendering entity hitbox in world"));
/*     */           } 
/*     */         }
/* 400 */       } else if (this.renderEngine != null) {
/* 401 */         return false;
/*     */       } 
/*     */       
/* 404 */       return true;
/* 405 */     } catch (Throwable throwable3) {
/* 406 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable3, "Rendering entity in world");
/* 407 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being rendered");
/* 408 */       entity.addEntityCrashInfo(crashreportcategory);
/* 409 */       CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Renderer details");
/* 410 */       crashreportcategory1.addCrashSection("Assigned renderer", render);
/* 411 */       crashreportcategory1.addCrashSection("Location", CrashReportCategory.getCoordinateInfo(x, y, z));
/* 412 */       crashreportcategory1.addCrashSection("Rotation", Float.valueOf(entityYaw));
/* 413 */       crashreportcategory1.addCrashSection("Delta", Float.valueOf(partialTicks));
/* 414 */       throw new ReportedException(crashreport);
/*     */     } 
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
/*     */   private void renderDebugBoundingBox(Entity entityIn, double x, double y, double z, float entityYaw, float partialTicks) {
/* 428 */     if (!Shaders.isShadowPass) {
/* 429 */       GlStateManager.depthMask(false);
/* 430 */       GlStateManager.disableTexture2D();
/* 431 */       GlStateManager.disableLighting();
/* 432 */       GlStateManager.disableCull();
/* 433 */       GlStateManager.disableBlend();
/* 434 */       float f = entityIn.width / 2.0F;
/* 435 */       AxisAlignedBB axisalignedbb = entityIn.getEntityBoundingBox();
/* 436 */       AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(axisalignedbb.minX - entityIn.posX + x, axisalignedbb.minY - entityIn.posY + y, axisalignedbb.minZ - entityIn.posZ + z, axisalignedbb.maxX - entityIn.posX + x, axisalignedbb.maxY - entityIn.posY + y, axisalignedbb.maxZ - entityIn.posZ + z);
/* 437 */       if ((Client.getInstance()).hudManager.hitbox.isEnabled()) {
/* 438 */         GL11.glLineWidth(2.7F);
/* 439 */         RenderGlobal.drawOutlinedBoundingBox(axisalignedbb2, (Client.getInstance()).splashRed, (Client.getInstance()).splashGreen, (Client.getInstance()).splashBlue, 255);
/*     */       } else {
/*     */         
/* 442 */         GL11.glLineWidth(2.0F);
/* 443 */         RenderGlobal.drawOutlinedBoundingBox(axisalignedbb2, 255, 255, 255, 255);
/*     */       } 
/* 445 */       if (entityIn instanceof EntityLivingBase && 
/* 446 */         !(Client.getInstance()).hudManager.hitbox.isEnabled()) {
/* 447 */         float f2 = 0.01F;
/* 448 */         RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(x - f, y + entityIn.getEyeHeight() - 0.009999999776482582D, z - f, x + f, y + entityIn.getEyeHeight() + 0.009999999776482582D, z + f), 255, 0, 0, 255);
/*     */       } 
/*     */       
/* 451 */       Tessellator tessellator = Tessellator.getInstance();
/* 452 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 453 */       Vec3 vec3 = entityIn.getLook(partialTicks);
/* 454 */       if (!(Client.getInstance()).hudManager.hitbox.isEnabled()) {
/* 455 */         worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 456 */         worldrenderer.pos(x, y + entityIn.getEyeHeight(), z).color(0, 0, 255, 255).endVertex();
/* 457 */         worldrenderer.pos(x + vec3.xCoord * 2.0D, y + entityIn.getEyeHeight() + vec3.yCoord * 2.0D, z + vec3.zCoord * 2.0D).color(0, 0, 255, 255).endVertex();
/* 458 */         tessellator.draw();
/*     */       } 
/* 460 */       GlStateManager.enableTexture2D();
/* 461 */       GlStateManager.enableLighting();
/* 462 */       GlStateManager.enableCull();
/* 463 */       GlStateManager.disableBlend();
/* 464 */       GlStateManager.depthMask(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(World worldIn) {
/* 472 */     this.worldObj = worldIn;
/*     */   }
/*     */   
/*     */   public double getDistanceToCamera(double x, double y, double z) {
/* 476 */     double d0 = x - this.viewerPosX;
/* 477 */     double d1 = y - this.viewerPosY;
/* 478 */     double d2 = z - this.viewerPosZ;
/* 479 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRenderer() {
/* 486 */     return this.textRenderer;
/*     */   }
/*     */   
/*     */   public void setRenderOutlines(boolean renderOutlinesIn) {
/* 490 */     this.renderOutlines = renderOutlinesIn;
/*     */   }
/*     */   
/*     */   public Map<Class, Render> getEntityRenderMap() {
/* 494 */     return this.entityRenderMap;
/*     */   }
/*     */   
/*     */   public void setEntityRenderMap(Map<Class, Render> p_setEntityRenderMap_1_) {
/* 498 */     this.entityRenderMap = p_setEntityRenderMap_1_;
/*     */   }
/*     */   
/*     */   public Map<String, RenderPlayer> getSkinMap() {
/* 502 */     return Collections.unmodifiableMap(this.skinMap);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */