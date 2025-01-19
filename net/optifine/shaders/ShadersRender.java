/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.ItemRenderer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntityEndPortal;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ 
/*     */ public class ShadersRender
/*     */ {
/*  31 */   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
/*     */   
/*     */   public static void setFrustrumPosition(ICamera frustum, double x, double y, double z) {
/*  34 */     frustum.setPosition(x, y, z);
/*     */   }
/*     */   
/*     */   public static void setupTerrain(RenderGlobal renderGlobal, Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
/*  38 */     renderGlobal.setupTerrain(viewEntity, partialTicks, camera, frameCount, playerSpectator);
/*     */   }
/*     */   
/*     */   public static void beginTerrainSolid() {
/*  42 */     if (Shaders.isRenderingWorld) {
/*  43 */       Shaders.fogEnabled = true;
/*  44 */       Shaders.useProgram(Shaders.ProgramTerrain);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void beginTerrainCutoutMipped() {
/*  49 */     if (Shaders.isRenderingWorld) {
/*  50 */       Shaders.useProgram(Shaders.ProgramTerrain);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void beginTerrainCutout() {
/*  55 */     if (Shaders.isRenderingWorld) {
/*  56 */       Shaders.useProgram(Shaders.ProgramTerrain);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void endTerrain() {
/*  61 */     if (Shaders.isRenderingWorld) {
/*  62 */       Shaders.useProgram(Shaders.ProgramTexturedLit);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void beginTranslucent() {
/*  67 */     if (Shaders.isRenderingWorld) {
/*  68 */       if (Shaders.usedDepthBuffers >= 2) {
/*  69 */         GlStateManager.setActiveTexture(33995);
/*  70 */         Shaders.checkGLError("pre copy depth");
/*  71 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
/*  72 */         Shaders.checkGLError("copy depth");
/*  73 */         GlStateManager.setActiveTexture(33984);
/*     */       } 
/*     */       
/*  76 */       Shaders.useProgram(Shaders.ProgramWater);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void endTranslucent() {
/*  81 */     if (Shaders.isRenderingWorld) {
/*  82 */       Shaders.useProgram(Shaders.ProgramTexturedLit);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void renderHand0(EntityRenderer er, float par1, int par2) {
/*  87 */     if (!Shaders.isShadowPass) {
/*  88 */       boolean flag = Shaders.isItemToRenderMainTranslucent();
/*  89 */       boolean flag1 = Shaders.isItemToRenderOffTranslucent();
/*     */       
/*  91 */       if (!flag || !flag1) {
/*  92 */         Shaders.readCenterDepth();
/*  93 */         Shaders.beginHand(false);
/*  94 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  95 */         Shaders.setSkipRenderHands(flag, flag1);
/*  96 */         er.renderHand(par1, par2, true, false, false);
/*  97 */         Shaders.endHand();
/*  98 */         Shaders.setHandsRendered(!flag, !flag1);
/*  99 */         Shaders.setSkipRenderHands(false, false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void renderHand1(EntityRenderer er, float par1, int par2) {
/* 105 */     if (!Shaders.isShadowPass && !Shaders.isBothHandsRendered()) {
/* 106 */       Shaders.readCenterDepth();
/* 107 */       GlStateManager.enableBlend();
/* 108 */       Shaders.beginHand(true);
/* 109 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 110 */       Shaders.setSkipRenderHands(Shaders.isHandRenderedMain(), Shaders.isHandRenderedOff());
/* 111 */       er.renderHand(par1, par2, true, false, true);
/* 112 */       Shaders.endHand();
/* 113 */       Shaders.setHandsRendered(true, true);
/* 114 */       Shaders.setSkipRenderHands(false, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void renderItemFP(ItemRenderer itemRenderer, float par1, boolean renderTranslucent) {
/* 119 */     Shaders.setRenderingFirstPersonHand(true);
/* 120 */     GlStateManager.depthMask(true);
/*     */     
/* 122 */     if (renderTranslucent) {
/* 123 */       GlStateManager.depthFunc(519);
/* 124 */       GL11.glPushMatrix();
/* 125 */       IntBuffer intbuffer = Shaders.activeDrawBuffers;
/* 126 */       Shaders.setDrawBuffers(Shaders.drawBuffersNone);
/* 127 */       Shaders.renderItemKeepDepthMask = true;
/* 128 */       itemRenderer.renderItemInFirstPerson(par1);
/* 129 */       Shaders.renderItemKeepDepthMask = false;
/* 130 */       Shaders.setDrawBuffers(intbuffer);
/* 131 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 134 */     GlStateManager.depthFunc(515);
/* 135 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 136 */     itemRenderer.renderItemInFirstPerson(par1);
/* 137 */     Shaders.setRenderingFirstPersonHand(false);
/*     */   }
/*     */   
/*     */   public static void renderFPOverlay(EntityRenderer er, float par1, int par2) {
/* 141 */     if (!Shaders.isShadowPass) {
/* 142 */       Shaders.beginFPOverlay();
/* 143 */       er.renderHand(par1, par2, false, true, false);
/* 144 */       Shaders.endFPOverlay();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void beginBlockDamage() {
/* 149 */     if (Shaders.isRenderingWorld) {
/* 150 */       Shaders.useProgram(Shaders.ProgramDamagedBlock);
/*     */       
/* 152 */       if (Shaders.ProgramDamagedBlock.getId() == Shaders.ProgramTerrain.getId()) {
/* 153 */         Shaders.setDrawBuffers(Shaders.drawBuffersColorAtt0);
/* 154 */         GlStateManager.depthMask(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void endBlockDamage() {
/* 160 */     if (Shaders.isRenderingWorld) {
/* 161 */       GlStateManager.depthMask(true);
/* 162 */       Shaders.useProgram(Shaders.ProgramTexturedLit);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void renderShadowMap(EntityRenderer entityRenderer, int pass, float partialTicks, long finishTimeNano) {
/* 167 */     if (Shaders.usedShadowDepthBuffers > 0 && --Shaders.shadowPassCounter <= 0) {
/* 168 */       Minecraft minecraft = Minecraft.getMinecraft();
/* 169 */       minecraft.mcProfiler.endStartSection("shadow pass");
/* 170 */       RenderGlobal renderglobal = minecraft.renderGlobal;
/* 171 */       Shaders.isShadowPass = true;
/* 172 */       Shaders.shadowPassCounter = Shaders.shadowPassInterval;
/* 173 */       Shaders.preShadowPassThirdPersonView = minecraft.gameSettings.showDebugInfo;
/* 174 */       minecraft.gameSettings.showDebugInfo = 1;
/* 175 */       Shaders.checkGLError("pre shadow");
/* 176 */       GL11.glMatrixMode(5889);
/* 177 */       GL11.glPushMatrix();
/* 178 */       GL11.glMatrixMode(5888);
/* 179 */       GL11.glPushMatrix();
/* 180 */       minecraft.mcProfiler.endStartSection("shadow clear");
/* 181 */       EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.sfb);
/* 182 */       Shaders.checkGLError("shadow bind sfb");
/* 183 */       minecraft.mcProfiler.endStartSection("shadow camera");
/* 184 */       entityRenderer.setupCameraTransform(partialTicks, 2);
/* 185 */       Shaders.setCameraShadow(partialTicks);
/* 186 */       Shaders.checkGLError("shadow camera");
/* 187 */       Shaders.useProgram(Shaders.ProgramShadow);
/* 188 */       GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
/* 189 */       Shaders.checkGLError("shadow drawbuffers");
/* 190 */       GL11.glReadBuffer(0);
/* 191 */       Shaders.checkGLError("shadow readbuffer");
/* 192 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, Shaders.sfbDepthTextures.get(0), 0);
/*     */       
/* 194 */       if (Shaders.usedShadowColorBuffers != 0) {
/* 195 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, Shaders.sfbColorTextures.get(0), 0);
/*     */       }
/*     */       
/* 198 */       Shaders.checkFramebufferStatus("shadow fb");
/* 199 */       GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 200 */       GL11.glClear((Shaders.usedShadowColorBuffers != 0) ? 16640 : 256);
/* 201 */       Shaders.checkGLError("shadow clear");
/* 202 */       minecraft.mcProfiler.endStartSection("shadow frustum");
/* 203 */       ClippingHelper clippinghelper = ClippingHelperShadow.getInstance();
/* 204 */       minecraft.mcProfiler.endStartSection("shadow culling");
/* 205 */       Frustum frustum = new Frustum(clippinghelper);
/* 206 */       Entity entity = minecraft.getRenderViewEntity();
/* 207 */       double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 208 */       double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 209 */       double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 210 */       frustum.setPosition(d0, d1, d2);
/* 211 */       GlStateManager.shadeModel(7425);
/* 212 */       GlStateManager.enableDepth();
/* 213 */       GlStateManager.depthFunc(515);
/* 214 */       GlStateManager.depthMask(true);
/* 215 */       GlStateManager.colorMask(true, true, true, true);
/* 216 */       GlStateManager.disableCull();
/* 217 */       minecraft.mcProfiler.endStartSection("shadow prepareterrain");
/* 218 */       minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 219 */       minecraft.mcProfiler.endStartSection("shadow setupterrain");
/* 220 */       int i = 0;
/* 221 */       i = entityRenderer.frameCount;
/* 222 */       entityRenderer.frameCount = i + 1;
/* 223 */       renderglobal.setupTerrain(entity, partialTicks, (ICamera)frustum, i, minecraft.thePlayer.isSpectator());
/* 224 */       minecraft.mcProfiler.endStartSection("shadow updatechunks");
/* 225 */       minecraft.mcProfiler.endStartSection("shadow terrain");
/* 226 */       GlStateManager.matrixMode(5888);
/* 227 */       GlStateManager.pushMatrix();
/* 228 */       GlStateManager.disableAlpha();
/* 229 */       renderglobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, partialTicks, 2, entity);
/* 230 */       Shaders.checkGLError("shadow terrain solid");
/* 231 */       GlStateManager.enableAlpha();
/* 232 */       renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, 2, entity);
/* 233 */       Shaders.checkGLError("shadow terrain cutoutmipped");
/* 234 */       minecraft.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/* 235 */       renderglobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, partialTicks, 2, entity);
/* 236 */       Shaders.checkGLError("shadow terrain cutout");
/* 237 */       minecraft.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/* 238 */       GlStateManager.shadeModel(7424);
/* 239 */       GlStateManager.alphaFunc(516, 0.1F);
/* 240 */       GlStateManager.matrixMode(5888);
/* 241 */       GlStateManager.popMatrix();
/* 242 */       GlStateManager.pushMatrix();
/* 243 */       minecraft.mcProfiler.endStartSection("shadow entities");
/*     */       
/* 245 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/* 246 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
/*     */       }
/*     */       
/* 249 */       renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 250 */       Shaders.checkGLError("shadow entities");
/* 251 */       GlStateManager.matrixMode(5888);
/* 252 */       GlStateManager.popMatrix();
/* 253 */       GlStateManager.depthMask(true);
/* 254 */       GlStateManager.disableBlend();
/* 255 */       GlStateManager.enableCull();
/* 256 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 257 */       GlStateManager.alphaFunc(516, 0.1F);
/*     */       
/* 259 */       if (Shaders.usedShadowDepthBuffers >= 2) {
/* 260 */         GlStateManager.setActiveTexture(33989);
/* 261 */         Shaders.checkGLError("pre copy shadow depth");
/* 262 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
/* 263 */         Shaders.checkGLError("copy shadow depth");
/* 264 */         GlStateManager.setActiveTexture(33984);
/*     */       } 
/*     */       
/* 267 */       GlStateManager.disableBlend();
/* 268 */       GlStateManager.depthMask(true);
/* 269 */       minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 270 */       GlStateManager.shadeModel(7425);
/* 271 */       Shaders.checkGLError("shadow pre-translucent");
/* 272 */       GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
/* 273 */       Shaders.checkGLError("shadow drawbuffers pre-translucent");
/* 274 */       Shaders.checkFramebufferStatus("shadow pre-translucent");
/*     */       
/* 276 */       if (Shaders.isRenderShadowTranslucent()) {
/* 277 */         minecraft.mcProfiler.endStartSection("shadow translucent");
/* 278 */         renderglobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, partialTicks, 2, entity);
/* 279 */         Shaders.checkGLError("shadow translucent");
/*     */       } 
/*     */       
/* 282 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/* 283 */         RenderHelper.enableStandardItemLighting();
/* 284 */         Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
/* 285 */         renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 286 */         Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 287 */         RenderHelper.disableStandardItemLighting();
/* 288 */         Shaders.checkGLError("shadow entities 1");
/*     */       } 
/*     */       
/* 291 */       GlStateManager.shadeModel(7424);
/* 292 */       GlStateManager.depthMask(true);
/* 293 */       GlStateManager.enableCull();
/* 294 */       GlStateManager.disableBlend();
/* 295 */       GL11.glFlush();
/* 296 */       Shaders.checkGLError("shadow flush");
/* 297 */       Shaders.isShadowPass = false;
/* 298 */       minecraft.gameSettings.showDebugInfo = Shaders.preShadowPassThirdPersonView;
/* 299 */       minecraft.mcProfiler.endStartSection("shadow postprocess");
/*     */       
/* 301 */       if (Shaders.hasGlGenMipmap) {
/* 302 */         if (Shaders.usedShadowDepthBuffers >= 1) {
/* 303 */           if (Shaders.shadowMipmapEnabled[0]) {
/* 304 */             GlStateManager.setActiveTexture(33988);
/* 305 */             GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(0));
/* 306 */             GL30.glGenerateMipmap(3553);
/* 307 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[0] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 310 */           if (Shaders.usedShadowDepthBuffers >= 2 && Shaders.shadowMipmapEnabled[1]) {
/* 311 */             GlStateManager.setActiveTexture(33989);
/* 312 */             GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(1));
/* 313 */             GL30.glGenerateMipmap(3553);
/* 314 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[1] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 317 */           GlStateManager.setActiveTexture(33984);
/*     */         } 
/*     */         
/* 320 */         if (Shaders.usedShadowColorBuffers >= 1) {
/* 321 */           if (Shaders.shadowColorMipmapEnabled[0]) {
/* 322 */             GlStateManager.setActiveTexture(33997);
/* 323 */             GlStateManager.bindTexture(Shaders.sfbColorTextures.get(0));
/* 324 */             GL30.glGenerateMipmap(3553);
/* 325 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[0] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 328 */           if (Shaders.usedShadowColorBuffers >= 2 && Shaders.shadowColorMipmapEnabled[1]) {
/* 329 */             GlStateManager.setActiveTexture(33998);
/* 330 */             GlStateManager.bindTexture(Shaders.sfbColorTextures.get(1));
/* 331 */             GL30.glGenerateMipmap(3553);
/* 332 */             GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[1] ? 9984 : 9987);
/*     */           } 
/*     */           
/* 335 */           GlStateManager.setActiveTexture(33984);
/*     */         } 
/*     */       } 
/*     */       
/* 339 */       Shaders.checkGLError("shadow postprocess");
/* 340 */       EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
/* 341 */       GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
/* 342 */       Shaders.activeDrawBuffers = null;
/* 343 */       minecraft.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 344 */       Shaders.useProgram(Shaders.ProgramTerrain);
/* 345 */       GL11.glMatrixMode(5888);
/* 346 */       GL11.glPopMatrix();
/* 347 */       GL11.glMatrixMode(5889);
/* 348 */       GL11.glPopMatrix();
/* 349 */       GL11.glMatrixMode(5888);
/* 350 */       Shaders.checkGLError("shadow end");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void preRenderChunkLayer(EnumWorldBlockLayer blockLayerIn) {
/* 355 */     if (Shaders.isRenderBackFace(blockLayerIn)) {
/* 356 */       GlStateManager.disableCull();
/*     */     }
/*     */     
/* 359 */     if (OpenGlHelper.useVbo()) {
/* 360 */       GL11.glEnableClientState(32885);
/* 361 */       GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 362 */       GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
/* 363 */       GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void postRenderChunkLayer(EnumWorldBlockLayer blockLayerIn) {
/* 368 */     if (OpenGlHelper.useVbo()) {
/* 369 */       GL11.glDisableClientState(32885);
/* 370 */       GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
/* 371 */       GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
/* 372 */       GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
/*     */     } 
/*     */     
/* 375 */     if (Shaders.isRenderBackFace(blockLayerIn)) {
/* 376 */       GlStateManager.enableCull();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setupArrayPointersVbo() {
/* 381 */     int i = 14;
/* 382 */     GL11.glVertexPointer(3, 5126, 56, 0L);
/* 383 */     GL11.glColorPointer(4, 5121, 56, 12L);
/* 384 */     GL11.glTexCoordPointer(2, 5126, 56, 16L);
/* 385 */     OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 386 */     GL11.glTexCoordPointer(2, 5122, 56, 24L);
/* 387 */     OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 388 */     GL11.glNormalPointer(5120, 56, 28L);
/* 389 */     GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 56, 32L);
/* 390 */     GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 56, 40L);
/* 391 */     GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 56, 48L);
/*     */   }
/*     */   
/*     */   public static void beaconBeamBegin() {
/* 395 */     Shaders.useProgram(Shaders.ProgramBeaconBeam);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void beaconBeamStartQuad1() {}
/*     */ 
/*     */   
/*     */   public static void beaconBeamStartQuad2() {}
/*     */ 
/*     */   
/*     */   public static void beaconBeamDraw1() {}
/*     */   
/*     */   public static void beaconBeamDraw2() {
/* 408 */     GlStateManager.disableBlend();
/*     */   }
/*     */   
/*     */   public static void renderEnchantedGlintBegin() {
/* 412 */     Shaders.useProgram(Shaders.ProgramArmorGlint);
/*     */   }
/*     */   
/*     */   public static void renderEnchantedGlintEnd() {
/* 416 */     if (Shaders.isRenderingWorld) {
/* 417 */       if (Shaders.isRenderingFirstPersonHand() && Shaders.isRenderBothHands()) {
/* 418 */         Shaders.useProgram(Shaders.ProgramHand);
/*     */       } else {
/* 420 */         Shaders.useProgram(Shaders.ProgramEntities);
/*     */       } 
/*     */     } else {
/* 423 */       Shaders.useProgram(Shaders.ProgramNone);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean renderEndPortal(TileEntityEndPortal te, double x, double y, double z, float partialTicks, int destroyStage, float offset) {
/* 428 */     if (!Shaders.isShadowPass && Shaders.activeProgram.getId() == 0) {
/* 429 */       return false;
/*     */     }
/* 431 */     GlStateManager.disableLighting();
/* 432 */     Config.getTextureManager().bindTexture(END_PORTAL_TEXTURE);
/* 433 */     Tessellator tessellator = Tessellator.getInstance();
/* 434 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 435 */     worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/* 436 */     float f = 0.5F;
/* 437 */     float f1 = f * 0.15F;
/* 438 */     float f2 = f * 0.3F;
/* 439 */     float f3 = f * 0.4F;
/* 440 */     float f4 = 0.0F;
/* 441 */     float f5 = 0.2F;
/* 442 */     float f6 = (float)(System.currentTimeMillis() % 100000L) / 100000.0F;
/* 443 */     int i = 240;
/* 444 */     worldrenderer.pos(x, y + offset, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/* 445 */     worldrenderer.pos(x + 1.0D, y + offset, z + 1.0D).color(f1, f2, f3, 1.0F).tex((f4 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 446 */     worldrenderer.pos(x + 1.0D, y + offset, z).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f5 + f6)).lightmap(i, i).endVertex();
/* 447 */     worldrenderer.pos(x, y + offset, z).color(f1, f2, f3, 1.0F).tex((f5 + f6), (f4 + f6)).lightmap(i, i).endVertex();
/* 448 */     tessellator.draw();
/* 449 */     GlStateManager.enableLighting();
/* 450 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\ShadersRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */