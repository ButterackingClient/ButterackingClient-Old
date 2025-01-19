/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*     */ import net.minecraft.tileentity.TileEntityEndPortal;
/*     */ import net.minecraft.tileentity.TileEntityEnderChest;
/*     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*     */ import net.minecraft.tileentity.TileEntityPiston;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.EmissiveTextures;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ 
/*     */ public class TileEntityRendererDispatcher
/*     */ {
/*  37 */   public Map<Class, TileEntitySpecialRenderer> mapSpecialRenderers = Maps.newHashMap();
/*  38 */   public static TileEntityRendererDispatcher instance = new TileEntityRendererDispatcher();
/*     */   
/*     */   public FontRenderer fontRenderer;
/*     */   
/*     */   public static double staticPlayerX;
/*     */   
/*     */   public static double staticPlayerY;
/*     */   
/*     */   public static double staticPlayerZ;
/*     */   
/*     */   public TextureManager renderEngine;
/*     */   
/*     */   public World worldObj;
/*     */   
/*     */   public Entity entity;
/*     */   
/*     */   public float entityYaw;
/*     */   
/*     */   public float entityPitch;
/*     */   
/*     */   public double entityX;
/*     */   
/*     */   public double entityY;
/*     */   
/*     */   public double entityZ;
/*     */   public TileEntity tileEntityRendered;
/*  64 */   private Tessellator batchBuffer = new Tessellator(2097152);
/*     */   private boolean drawingBatch = false;
/*     */   
/*     */   private TileEntityRendererDispatcher() {
/*  68 */     this.mapSpecialRenderers.put(TileEntitySign.class, new TileEntitySignRenderer());
/*  69 */     this.mapSpecialRenderers.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
/*  70 */     this.mapSpecialRenderers.put(TileEntityPiston.class, new TileEntityPistonRenderer());
/*  71 */     this.mapSpecialRenderers.put(TileEntityChest.class, new TileEntityChestRenderer());
/*  72 */     this.mapSpecialRenderers.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
/*  73 */     this.mapSpecialRenderers.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
/*  74 */     this.mapSpecialRenderers.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
/*  75 */     this.mapSpecialRenderers.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
/*  76 */     this.mapSpecialRenderers.put(TileEntitySkull.class, new TileEntitySkullRenderer());
/*  77 */     this.mapSpecialRenderers.put(TileEntityBanner.class, new TileEntityBannerRenderer());
/*     */     
/*  79 */     for (TileEntitySpecialRenderer<?> tileentityspecialrenderer : this.mapSpecialRenderers.values()) {
/*  80 */       tileentityspecialrenderer.setRendererDispatcher(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public <T extends TileEntity> TileEntitySpecialRenderer<T> getSpecialRendererByClass(Class<? extends TileEntity> teClass) {
/*  85 */     TileEntitySpecialRenderer<? extends TileEntity> tileentityspecialrenderer = this.mapSpecialRenderers.get(teClass);
/*     */     
/*  87 */     if (tileentityspecialrenderer == null && teClass != TileEntity.class) {
/*  88 */       tileentityspecialrenderer = getSpecialRendererByClass((Class)teClass.getSuperclass());
/*  89 */       this.mapSpecialRenderers.put(teClass, tileentityspecialrenderer);
/*     */     } 
/*     */     
/*  92 */     return (TileEntitySpecialRenderer)tileentityspecialrenderer;
/*     */   }
/*     */   
/*     */   public <T extends TileEntity> TileEntitySpecialRenderer<T> getSpecialRenderer(TileEntity tileEntityIn) {
/*  96 */     return (tileEntityIn != null && !tileEntityIn.isInvalid()) ? getSpecialRendererByClass((Class)tileEntityIn.getClass()) : null;
/*     */   }
/*     */   
/*     */   public void cacheActiveRenderInfo(World worldIn, TextureManager textureManagerIn, FontRenderer fontrendererIn, Entity entityIn, float partialTicks) {
/* 100 */     if (this.worldObj != worldIn) {
/* 101 */       setWorld(worldIn);
/*     */     }
/*     */     
/* 104 */     this.renderEngine = textureManagerIn;
/* 105 */     this.entity = entityIn;
/* 106 */     this.fontRenderer = fontrendererIn;
/* 107 */     this.entityYaw = entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks;
/* 108 */     this.entityPitch = entityIn.prevRotationPitch + (entityIn.rotationPitch - entityIn.prevRotationPitch) * partialTicks;
/* 109 */     this.entityX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 110 */     this.entityY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 111 */     this.entityZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/*     */   }
/*     */   
/*     */   public void renderTileEntity(TileEntity tileentityIn, float partialTicks, int destroyStage) {
/* 115 */     if (tileentityIn.getDistanceSq(this.entityX, this.entityY, this.entityZ) < tileentityIn.getMaxRenderDistanceSquared()) {
/* 116 */       boolean flag = true;
/*     */       
/* 118 */       if (Reflector.ForgeTileEntity_hasFastRenderer.exists()) {
/* 119 */         flag = !(this.drawingBatch && Reflector.callBoolean(tileentityIn, Reflector.ForgeTileEntity_hasFastRenderer, new Object[0]));
/*     */       }
/*     */       
/* 122 */       if (flag) {
/* 123 */         RenderHelper.enableStandardItemLighting();
/* 124 */         int i = this.worldObj.getCombinedLight(tileentityIn.getPos(), 0);
/* 125 */         int j = i % 65536;
/* 126 */         int k = i / 65536;
/* 127 */         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 128 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       } 
/*     */       
/* 131 */       BlockPos blockpos = tileentityIn.getPos();
/*     */       
/* 133 */       if (!this.worldObj.isBlockLoaded(blockpos, false)) {
/*     */         return;
/*     */       }
/*     */       
/* 137 */       if (EmissiveTextures.isActive()) {
/* 138 */         EmissiveTextures.beginRender();
/*     */       }
/*     */       
/* 141 */       renderTileEntityAt(tileentityIn, blockpos.getX() - staticPlayerX, blockpos.getY() - staticPlayerY, blockpos.getZ() - staticPlayerZ, partialTicks, destroyStage);
/*     */       
/* 143 */       if (EmissiveTextures.isActive()) {
/* 144 */         if (EmissiveTextures.hasEmissive()) {
/* 145 */           EmissiveTextures.beginRenderEmissive();
/* 146 */           renderTileEntityAt(tileentityIn, blockpos.getX() - staticPlayerX, blockpos.getY() - staticPlayerY, blockpos.getZ() - staticPlayerZ, partialTicks, destroyStage);
/* 147 */           EmissiveTextures.endRenderEmissive();
/*     */         } 
/*     */         
/* 150 */         EmissiveTextures.endRender();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntity tileEntityIn, double x, double y, double z, float partialTicks) {
/* 159 */     renderTileEntityAt(tileEntityIn, x, y, z, partialTicks, -1);
/*     */   }
/*     */   
/*     */   public void renderTileEntityAt(TileEntity tileEntityIn, double x, double y, double z, float partialTicks, int destroyStage) {
/* 163 */     TileEntitySpecialRenderer<TileEntity> tileentityspecialrenderer = getSpecialRenderer(tileEntityIn);
/*     */     
/* 165 */     if (tileentityspecialrenderer != null) {
/*     */       try {
/* 167 */         this.tileEntityRendered = tileEntityIn;
/*     */         
/* 169 */         if (this.drawingBatch && Reflector.callBoolean(tileEntityIn, Reflector.ForgeTileEntity_hasFastRenderer, new Object[0])) {
/* 170 */           tileentityspecialrenderer.renderTileEntityFast(tileEntityIn, x, y, z, partialTicks, destroyStage, this.batchBuffer.getWorldRenderer());
/*     */         } else {
/* 172 */           tileentityspecialrenderer.renderTileEntityAt(tileEntityIn, x, y, z, partialTicks, destroyStage);
/*     */         } 
/*     */         
/* 175 */         this.tileEntityRendered = null;
/* 176 */       } catch (Throwable throwable) {
/* 177 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Block Entity");
/* 178 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block Entity Details");
/* 179 */         tileEntityIn.addInfoToCrashReport(crashreportcategory);
/* 180 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void setWorld(World worldIn) {
/* 186 */     this.worldObj = worldIn;
/*     */   }
/*     */   
/*     */   public FontRenderer getFontRenderer() {
/* 190 */     return this.fontRenderer;
/*     */   }
/*     */   
/*     */   public void preDrawBatch() {
/* 194 */     this.batchBuffer.getWorldRenderer().begin(7, DefaultVertexFormats.BLOCK);
/* 195 */     this.drawingBatch = true;
/*     */   }
/*     */   
/*     */   public void drawBatch(int p_drawBatch_1_) {
/* 199 */     this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/* 200 */     RenderHelper.disableStandardItemLighting();
/* 201 */     GlStateManager.blendFunc(770, 771);
/* 202 */     GlStateManager.enableBlend();
/* 203 */     GlStateManager.disableCull();
/*     */     
/* 205 */     if (Minecraft.isAmbientOcclusionEnabled()) {
/* 206 */       GlStateManager.shadeModel(7425);
/*     */     } else {
/* 208 */       GlStateManager.shadeModel(7424);
/*     */     } 
/*     */     
/* 211 */     if (p_drawBatch_1_ > 0) {
/* 212 */       this.batchBuffer.getWorldRenderer().sortVertexData((float)staticPlayerX, (float)staticPlayerY, (float)staticPlayerZ);
/*     */     }
/*     */     
/* 215 */     this.batchBuffer.draw();
/* 216 */     RenderHelper.enableStandardItemLighting();
/* 217 */     this.drawingBatch = false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntityRendererDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */