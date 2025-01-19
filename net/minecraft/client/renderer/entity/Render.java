/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.entity.model.IEntityRenderer;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public abstract class Render<T extends Entity> implements IEntityRenderer {
/*  29 */   private static final ResourceLocation shadowTextures = new ResourceLocation("textures/misc/shadow.png");
/*     */ 
/*     */   
/*     */   protected final RenderManager renderManager;
/*     */   
/*     */   public float shadowSize;
/*     */   
/*  36 */   protected float shadowOpaque = 1.0F;
/*  37 */   private Class entityClass = null;
/*  38 */   private ResourceLocation locationTextureCustom = null;
/*     */   
/*     */   protected Render(RenderManager renderManager) {
/*  41 */     this.renderManager = renderManager;
/*     */   }
/*     */   
/*     */   public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  45 */     AxisAlignedBB axisalignedbb = livingEntity.getEntityBoundingBox();
/*     */     
/*  47 */     if (axisalignedbb.hasNaN() || axisalignedbb.getAverageEdgeLength() == 0.0D) {
/*  48 */       axisalignedbb = new AxisAlignedBB(((Entity)livingEntity).posX - 2.0D, ((Entity)livingEntity).posY - 2.0D, ((Entity)livingEntity).posZ - 2.0D, ((Entity)livingEntity).posX + 2.0D, ((Entity)livingEntity).posY + 2.0D, ((Entity)livingEntity).posZ + 2.0D);
/*     */     }
/*     */     
/*  51 */     return (livingEntity.isInRangeToRender3d(camX, camY, camZ) && (((Entity)livingEntity).ignoreFrustumCheck || camera.isBoundingBoxInFrustum(axisalignedbb)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  58 */     renderName(entity, x, y, z);
/*     */   }
/*     */   
/*     */   protected void renderName(T entity, double x, double y, double z) {
/*  62 */     if (canRenderName(entity)) {
/*  63 */       renderLivingLabel(entity, entity.getDisplayName().getFormattedText(), x, y, z, 64);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/*  68 */     return (entity.getAlwaysRenderNameTagForRender() && entity.hasCustomName());
/*     */   }
/*     */   
/*     */   protected void renderOffsetLivingLabel(T entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_) {
/*  72 */     renderLivingLabel(entityIn, str, x, y, z, 64);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ResourceLocation getEntityTexture(T paramT);
/*     */ 
/*     */   
/*     */   protected boolean bindEntityTexture(T entity) {
/*  81 */     ResourceLocation resourcelocation = getEntityTexture(entity);
/*     */     
/*  83 */     if (this.locationTextureCustom != null) {
/*  84 */       resourcelocation = this.locationTextureCustom;
/*     */     }
/*     */     
/*  87 */     if (resourcelocation == null) {
/*  88 */       return false;
/*     */     }
/*  90 */     bindTexture(resourcelocation);
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindTexture(ResourceLocation location) {
/*  96 */     this.renderManager.renderEngine.bindTexture(location);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderEntityOnFire(Entity entity, double x, double y, double z, float partialTicks) {
/* 103 */     GlStateManager.disableLighting();
/* 104 */     TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/* 105 */     TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_0");
/* 106 */     TextureAtlasSprite textureatlassprite1 = texturemap.getAtlasSprite("minecraft:blocks/fire_layer_1");
/* 107 */     GlStateManager.pushMatrix();
/* 108 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 109 */     float f = entity.width * 1.4F;
/* 110 */     GlStateManager.scale(f, f, f);
/* 111 */     Tessellator tessellator = Tessellator.getInstance();
/* 112 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 113 */     float f1 = 0.5F;
/* 114 */     float f2 = 0.0F;
/* 115 */     float f3 = entity.height / f;
/* 116 */     float f4 = (float)(entity.posY - (entity.getEntityBoundingBox()).minY);
/* 117 */     GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 118 */     GlStateManager.translate(0.0F, 0.0F, -0.3F + (int)f3 * 0.02F);
/* 119 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 120 */     float f5 = 0.0F;
/* 121 */     int i = 0;
/* 122 */     boolean flag = Config.isMultiTexture();
/*     */     
/* 124 */     if (flag) {
/* 125 */       worldrenderer.setBlockLayer(EnumWorldBlockLayer.SOLID);
/*     */     }
/*     */     
/* 128 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/* 130 */     while (f3 > 0.0F) {
/* 131 */       TextureAtlasSprite textureatlassprite2 = (i % 2 == 0) ? textureatlassprite : textureatlassprite1;
/* 132 */       worldrenderer.setSprite(textureatlassprite2);
/* 133 */       bindTexture(TextureMap.locationBlocksTexture);
/* 134 */       float f6 = textureatlassprite2.getMinU();
/* 135 */       float f7 = textureatlassprite2.getMinV();
/* 136 */       float f8 = textureatlassprite2.getMaxU();
/* 137 */       float f9 = textureatlassprite2.getMaxV();
/*     */       
/* 139 */       if (i / 2 % 2 == 0) {
/* 140 */         float f10 = f8;
/* 141 */         f8 = f6;
/* 142 */         f6 = f10;
/*     */       } 
/*     */       
/* 145 */       worldrenderer.pos((f1 - f2), (0.0F - f4), f5).tex(f8, f9).endVertex();
/* 146 */       worldrenderer.pos((-f1 - f2), (0.0F - f4), f5).tex(f6, f9).endVertex();
/* 147 */       worldrenderer.pos((-f1 - f2), (1.4F - f4), f5).tex(f6, f7).endVertex();
/* 148 */       worldrenderer.pos((f1 - f2), (1.4F - f4), f5).tex(f8, f7).endVertex();
/* 149 */       f3 -= 0.45F;
/* 150 */       f4 -= 0.45F;
/* 151 */       f1 *= 0.9F;
/* 152 */       f5 += 0.03F;
/* 153 */       i++;
/*     */     } 
/*     */     
/* 156 */     tessellator.draw();
/*     */     
/* 158 */     if (flag) {
/* 159 */       worldrenderer.setBlockLayer(null);
/* 160 */       GlStateManager.bindCurrentTexture();
/*     */     } 
/*     */     
/* 163 */     GlStateManager.popMatrix();
/* 164 */     GlStateManager.enableLighting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderShadow(Entity entityIn, double x, double y, double z, float shadowAlpha, float partialTicks) {
/* 172 */     if (!Config.isShaders() || !Shaders.shouldSkipDefaultShadow) {
/* 173 */       GlStateManager.enableBlend();
/* 174 */       GlStateManager.blendFunc(770, 771);
/* 175 */       this.renderManager.renderEngine.bindTexture(shadowTextures);
/* 176 */       World world = getWorldFromRenderManager();
/* 177 */       GlStateManager.depthMask(false);
/* 178 */       float f = this.shadowSize;
/*     */       
/* 180 */       if (entityIn instanceof EntityLiving) {
/* 181 */         EntityLiving entityliving = (EntityLiving)entityIn;
/* 182 */         f *= entityliving.getRenderSizeModifier();
/*     */         
/* 184 */         if (entityliving.isChild()) {
/* 185 */           f *= 0.5F;
/*     */         }
/*     */       } 
/*     */       
/* 189 */       double d5 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 190 */       double d0 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 191 */       double d1 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 192 */       int i = MathHelper.floor_double(d5 - f);
/* 193 */       int j = MathHelper.floor_double(d5 + f);
/* 194 */       int k = MathHelper.floor_double(d0 - f);
/* 195 */       int l = MathHelper.floor_double(d0);
/* 196 */       int i1 = MathHelper.floor_double(d1 - f);
/* 197 */       int j1 = MathHelper.floor_double(d1 + f);
/* 198 */       double d2 = x - d5;
/* 199 */       double d3 = y - d0;
/* 200 */       double d4 = z - d1;
/* 201 */       Tessellator tessellator = Tessellator.getInstance();
/* 202 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 203 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*     */       
/* 205 */       for (BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(i, k, i1), new BlockPos(j, l, j1))) {
/* 206 */         Block block = world.getBlockState(blockpos.down()).getBlock();
/*     */         
/* 208 */         if (block.getRenderType() != -1 && world.getLightFromNeighbors(blockpos) > 3) {
/* 209 */           renderShadowBlock(block, x, y, z, blockpos, shadowAlpha, f, d2, d3, d4);
/*     */         }
/*     */       } 
/*     */       
/* 213 */       tessellator.draw();
/* 214 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 215 */       GlStateManager.disableBlend();
/* 216 */       GlStateManager.depthMask(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private World getWorldFromRenderManager() {
/* 224 */     return this.renderManager.worldObj;
/*     */   }
/*     */   
/*     */   private void renderShadowBlock(Block blockIn, double p_180549_2_, double p_180549_4_, double p_180549_6_, BlockPos pos, float p_180549_9_, float p_180549_10_, double p_180549_11_, double p_180549_13_, double p_180549_15_) {
/* 228 */     if (blockIn.isFullCube()) {
/* 229 */       Tessellator tessellator = Tessellator.getInstance();
/* 230 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 231 */       double d0 = (p_180549_9_ - (p_180549_4_ - pos.getY() + p_180549_13_) / 2.0D) * 0.5D * getWorldFromRenderManager().getLightBrightness(pos);
/*     */       
/* 233 */       if (d0 >= 0.0D) {
/* 234 */         if (d0 > 1.0D) {
/* 235 */           d0 = 1.0D;
/*     */         }
/*     */         
/* 238 */         double d1 = pos.getX() + blockIn.getBlockBoundsMinX() + p_180549_11_;
/* 239 */         double d2 = pos.getX() + blockIn.getBlockBoundsMaxX() + p_180549_11_;
/* 240 */         double d3 = pos.getY() + blockIn.getBlockBoundsMinY() + p_180549_13_ + 0.015625D;
/* 241 */         double d4 = pos.getZ() + blockIn.getBlockBoundsMinZ() + p_180549_15_;
/* 242 */         double d5 = pos.getZ() + blockIn.getBlockBoundsMaxZ() + p_180549_15_;
/* 243 */         float f = (float)((p_180549_2_ - d1) / 2.0D / p_180549_10_ + 0.5D);
/* 244 */         float f1 = (float)((p_180549_2_ - d2) / 2.0D / p_180549_10_ + 0.5D);
/* 245 */         float f2 = (float)((p_180549_6_ - d4) / 2.0D / p_180549_10_ + 0.5D);
/* 246 */         float f3 = (float)((p_180549_6_ - d5) / 2.0D / p_180549_10_ + 0.5D);
/* 247 */         worldrenderer.pos(d1, d3, d4).tex(f, f2).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 248 */         worldrenderer.pos(d1, d3, d5).tex(f, f3).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 249 */         worldrenderer.pos(d2, d3, d5).tex(f1, f3).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/* 250 */         worldrenderer.pos(d2, d3, d4).tex(f1, f2).color(1.0F, 1.0F, 1.0F, (float)d0).endVertex();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderOffsetAABB(AxisAlignedBB boundingBox, double x, double y, double z) {
/* 259 */     GlStateManager.disableTexture2D();
/* 260 */     Tessellator tessellator = Tessellator.getInstance();
/* 261 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 262 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 263 */     worldrenderer.setTranslation(x, y, z);
/* 264 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_NORMAL);
/* 265 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 266 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 267 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 268 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 269 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 270 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 271 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 272 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 273 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 274 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 275 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 276 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 277 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 278 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 279 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 280 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 281 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 282 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 283 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 284 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 285 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 286 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 287 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 288 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 289 */     tessellator.draw();
/* 290 */     worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 291 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
/* 298 */     if (this.renderManager.options != null) {
/* 299 */       if (this.renderManager.options.entityShadows && this.shadowSize > 0.0F && !entityIn.isInvisible() && this.renderManager.isRenderShadow()) {
/* 300 */         double d0 = this.renderManager.getDistanceToCamera(entityIn.posX, entityIn.posY, entityIn.posZ);
/* 301 */         float f = (float)((1.0D - d0 / 256.0D) * this.shadowOpaque);
/*     */         
/* 303 */         if (f > 0.0F) {
/* 304 */           renderShadow(entityIn, x, y, z, f, partialTicks);
/*     */         }
/*     */       } 
/*     */       
/* 308 */       if (entityIn.canRenderOnFire() && (!(entityIn instanceof EntityPlayer) || !((EntityPlayer)entityIn).isSpectator())) {
/* 309 */         renderEntityOnFire(entityIn, x, y, z, partialTicks);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontRenderer getFontRendererFromRenderManager() {
/* 318 */     return this.renderManager.getFontRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingLabel(T entityIn, String str, double x, double y, double z, int maxDistance) {
/* 325 */     double d0 = entityIn.getDistanceSqToEntity(this.renderManager.livingPlayer);
/*     */     
/* 327 */     if (d0 <= (maxDistance * maxDistance)) {
/* 328 */       FontRenderer fontrenderer = getFontRendererFromRenderManager();
/* 329 */       float f = 1.6F;
/* 330 */       float f1 = 0.016666668F * f;
/* 331 */       GlStateManager.pushMatrix();
/* 332 */       GlStateManager.translate((float)x + 0.0F, (float)y + ((Entity)entityIn).height + 0.5F, (float)z);
/* 333 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 334 */       GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 335 */       GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 336 */       GlStateManager.scale(-f1, -f1, f1);
/* 337 */       GlStateManager.disableLighting();
/* 338 */       GlStateManager.depthMask(false);
/* 339 */       GlStateManager.disableDepth();
/* 340 */       GlStateManager.enableBlend();
/* 341 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 342 */       Tessellator tessellator = Tessellator.getInstance();
/* 343 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 344 */       int i = 0;
/*     */       
/* 346 */       if (str.equals("deadmau5")) {
/* 347 */         i = -10;
/*     */       }
/*     */       
/* 350 */       int j = fontrenderer.getStringWidth(str) / 2;
/* 351 */       GlStateManager.disableTexture2D();
/* 352 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 353 */       worldrenderer.pos((-j - 1), (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 354 */       worldrenderer.pos((-j - 1), (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 355 */       worldrenderer.pos((j + 1), (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 356 */       worldrenderer.pos((j + 1), (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
/* 357 */       tessellator.draw();
/* 358 */       GlStateManager.enableTexture2D();
/* 359 */       fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127);
/* 360 */       GlStateManager.enableDepth();
/* 361 */       GlStateManager.depthMask(true);
/* 362 */       fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
/* 363 */       GlStateManager.enableLighting();
/* 364 */       GlStateManager.disableBlend();
/* 365 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 366 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   public RenderManager getRenderManager() {
/* 371 */     return this.renderManager;
/*     */   }
/*     */   
/*     */   public boolean isMultipass() {
/* 375 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderMultipass(T p_renderMultipass_1_, double p_renderMultipass_2_, double p_renderMultipass_4_, double p_renderMultipass_6_, float p_renderMultipass_8_, float p_renderMultipass_9_) {}
/*     */   
/*     */   public Class getEntityClass() {
/* 382 */     return this.entityClass;
/*     */   }
/*     */   
/*     */   public void setEntityClass(Class p_setEntityClass_1_) {
/* 386 */     this.entityClass = p_setEntityClass_1_;
/*     */   }
/*     */   
/*     */   public ResourceLocation getLocationTextureCustom() {
/* 390 */     return this.locationTextureCustom;
/*     */   }
/*     */   
/*     */   public void setLocationTextureCustom(ResourceLocation p_setLocationTextureCustom_1_) {
/* 394 */     this.locationTextureCustom = p_setLocationTextureCustom_1_;
/*     */   }
/*     */   
/*     */   public static void setModelBipedMain(RenderBiped p_setModelBipedMain_0_, ModelBiped p_setModelBipedMain_1_) {
/* 398 */     p_setModelBipedMain_0_.modelBipedMain = p_setModelBipedMain_1_;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\Render.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */