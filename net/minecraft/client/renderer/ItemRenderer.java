/*     */ package net.minecraft.client.renderer;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import net.optifine.DynamicLights;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ItemRenderer {
/*  36 */   private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
/*  37 */   private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
/*     */ 
/*     */   
/*     */   private final Minecraft mc;
/*     */ 
/*     */   
/*     */   private ItemStack itemToRender;
/*     */ 
/*     */   
/*     */   private float equippedProgress;
/*     */ 
/*     */   
/*     */   private float prevEquippedProgress;
/*     */ 
/*     */   
/*     */   private final RenderManager renderManager;
/*     */   
/*     */   private final RenderItem itemRenderer;
/*     */   
/*  56 */   private int equippedItemSlot = -1;
/*     */   
/*     */   public ItemRenderer(Minecraft mcIn) {
/*  59 */     this.mc = mcIn;
/*  60 */     this.renderManager = mcIn.getRenderManager();
/*  61 */     this.itemRenderer = mcIn.getRenderItem();
/*     */   }
/*     */   
/*     */   public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
/*  65 */     if (heldStack != null) {
/*  66 */       Item item = heldStack.getItem();
/*  67 */       Block block = Block.getBlockFromItem(item);
/*  68 */       GlStateManager.pushMatrix();
/*     */       
/*  70 */       if (this.itemRenderer.shouldRenderItemIn3D(heldStack)) {
/*  71 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*     */         
/*  73 */         if (isBlockTranslucent(block) && (!Config.isShaders() || !Shaders.renderItemKeepDepthMask)) {
/*  74 */           GlStateManager.depthMask(false);
/*     */         }
/*     */       } 
/*     */       
/*  78 */       this.itemRenderer.renderItemModelForEntity(heldStack, entityIn, transform);
/*     */       
/*  80 */       if (isBlockTranslucent(block)) {
/*  81 */         GlStateManager.depthMask(true);
/*     */       }
/*     */       
/*  84 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBlockTranslucent(Block blockIn) {
/*  92 */     return (blockIn != null && blockIn.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateArroundXAndY(float angle, float angleY) {
/* 101 */     GlStateManager.pushMatrix();
/* 102 */     GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
/* 103 */     GlStateManager.rotate(angleY, 0.0F, 1.0F, 0.0F);
/* 104 */     RenderHelper.enableStandardItemLighting();
/* 105 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setLightMapFromPlayer(AbstractClientPlayer clientPlayer) {
/* 112 */     int i = this.mc.theWorld.getCombinedLight(new BlockPos(clientPlayer.posX, clientPlayer.posY + clientPlayer.getEyeHeight(), clientPlayer.posZ), 0);
/*     */     
/* 114 */     if (Config.isDynamicLights()) {
/* 115 */       i = DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), i);
/*     */     }
/*     */     
/* 118 */     float f = (i & 0xFFFF);
/* 119 */     float f1 = (i >> 16);
/* 120 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateWithPlayerRotations(EntityPlayerSP entityplayerspIn, float partialTicks) {
/* 127 */     float f = entityplayerspIn.prevRenderArmPitch + (entityplayerspIn.renderArmPitch - entityplayerspIn.prevRenderArmPitch) * partialTicks;
/* 128 */     float f1 = entityplayerspIn.prevRenderArmYaw + (entityplayerspIn.renderArmYaw - entityplayerspIn.prevRenderArmYaw) * partialTicks;
/* 129 */     GlStateManager.rotate((entityplayerspIn.rotationPitch - f) * 0.1F, 1.0F, 0.0F, 0.0F);
/* 130 */     GlStateManager.rotate((entityplayerspIn.rotationYaw - f1) * 0.1F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float getMapAngleFromPitch(float pitch) {
/* 139 */     float f = 1.0F - pitch / 45.0F + 0.1F;
/* 140 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 141 */     f = -MathHelper.cos(f * 3.1415927F) * 0.5F + 0.5F;
/* 142 */     return f;
/*     */   }
/*     */   
/*     */   private void renderRightArm(RenderPlayer renderPlayerIn) {
/* 146 */     GlStateManager.pushMatrix();
/* 147 */     GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
/* 148 */     GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
/* 149 */     GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
/* 150 */     GlStateManager.translate(0.25F, -0.85F, 0.75F);
/* 151 */     renderPlayerIn.renderRightArm((AbstractClientPlayer)this.mc.thePlayer);
/* 152 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   private void renderLeftArm(RenderPlayer renderPlayerIn) {
/* 156 */     GlStateManager.pushMatrix();
/* 157 */     GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
/* 158 */     GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
/* 159 */     GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
/* 160 */     GlStateManager.translate(-0.3F, -1.1F, 0.45F);
/* 161 */     renderPlayerIn.renderLeftArm((AbstractClientPlayer)this.mc.thePlayer);
/* 162 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   private void renderPlayerArms(AbstractClientPlayer clientPlayer) {
/* 166 */     this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
/* 167 */     Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject((Entity)this.mc.thePlayer);
/* 168 */     RenderPlayer renderplayer = (RenderPlayer)render;
/*     */     
/* 170 */     if (!clientPlayer.isInvisible()) {
/* 171 */       GlStateManager.disableCull();
/* 172 */       renderRightArm(renderplayer);
/* 173 */       renderLeftArm(renderplayer);
/* 174 */       GlStateManager.enableCull();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderItemMap(AbstractClientPlayer clientPlayer, float pitch, float equipmentProgress, float swingProgress) {
/* 179 */     float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 180 */     float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F * 2.0F);
/* 181 */     float f2 = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
/* 182 */     GlStateManager.translate(f, f1, f2);
/* 183 */     float f3 = getMapAngleFromPitch(pitch);
/* 184 */     GlStateManager.translate(0.0F, 0.04F, -0.72F);
/* 185 */     GlStateManager.translate(0.0F, equipmentProgress * -1.2F, 0.0F);
/* 186 */     GlStateManager.translate(0.0F, f3 * -0.5F, 0.0F);
/* 187 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 188 */     GlStateManager.rotate(f3 * -85.0F, 0.0F, 0.0F, 1.0F);
/* 189 */     GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
/* 190 */     renderPlayerArms(clientPlayer);
/* 191 */     float f4 = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
/* 192 */     float f5 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 193 */     GlStateManager.rotate(f4 * -20.0F, 0.0F, 1.0F, 0.0F);
/* 194 */     GlStateManager.rotate(f5 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 195 */     GlStateManager.rotate(f5 * -80.0F, 1.0F, 0.0F, 0.0F);
/* 196 */     GlStateManager.scale(0.38F, 0.38F, 0.38F);
/* 197 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 198 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 199 */     GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
/* 200 */     GlStateManager.translate(-1.0F, -1.0F, 0.0F);
/* 201 */     GlStateManager.scale(0.015625F, 0.015625F, 0.015625F);
/* 202 */     this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
/* 203 */     Tessellator tessellator = Tessellator.getInstance();
/* 204 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 205 */     GL11.glNormal3f(0.0F, 0.0F, -1.0F);
/* 206 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 207 */     worldrenderer.pos(-7.0D, 135.0D, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 208 */     worldrenderer.pos(135.0D, 135.0D, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 209 */     worldrenderer.pos(135.0D, -7.0D, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 210 */     worldrenderer.pos(-7.0D, -7.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
/* 211 */     tessellator.draw();
/* 212 */     MapData mapdata = Items.filled_map.getMapData(this.itemToRender, (World)this.mc.theWorld);
/*     */     
/* 214 */     if (mapdata != null) {
/* 215 */       this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderPlayerArm(AbstractClientPlayer clientPlayer, float equipProgress, float swingProgress) {
/* 226 */     float f = -0.3F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 227 */     float f1 = 0.4F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F * 2.0F);
/* 228 */     float f2 = -0.4F * MathHelper.sin(swingProgress * 3.1415927F);
/* 229 */     GlStateManager.translate(f, f1, f2);
/* 230 */     GlStateManager.translate(0.64000005F, -0.6F, -0.71999997F);
/* 231 */     GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
/* 232 */     GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 233 */     float f3 = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
/* 234 */     float f4 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 235 */     GlStateManager.rotate(f4 * 70.0F, 0.0F, 1.0F, 0.0F);
/* 236 */     GlStateManager.rotate(f3 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 237 */     this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
/* 238 */     GlStateManager.translate(-1.0F, 3.6F, 3.5F);
/* 239 */     GlStateManager.rotate(120.0F, 0.0F, 0.0F, 1.0F);
/* 240 */     GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
/* 241 */     GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/* 242 */     GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 243 */     GlStateManager.translate(5.6F, 0.0F, 0.0F);
/* 244 */     Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject((Entity)this.mc.thePlayer);
/* 245 */     GlStateManager.disableCull();
/* 246 */     RenderPlayer renderplayer = (RenderPlayer)render;
/* 247 */     renderplayer.renderRightArm((AbstractClientPlayer)this.mc.thePlayer);
/* 248 */     GlStateManager.enableCull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doItemUsedTransformations(float swingProgress) {
/* 257 */     float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 258 */     float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F * 2.0F);
/* 259 */     float f2 = -0.2F * MathHelper.sin(swingProgress * 3.1415927F);
/* 260 */     GlStateManager.translate(f, f1, f2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void performDrinking(AbstractClientPlayer clientPlayer, float partialTicks) {
/* 269 */     float f = clientPlayer.getItemInUseCount() - partialTicks + 1.0F;
/* 270 */     float f1 = f / this.itemToRender.getMaxItemUseDuration();
/* 271 */     float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * 3.1415927F) * 0.1F);
/*     */     
/* 273 */     if (f1 >= 0.8F) {
/* 274 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 277 */     GlStateManager.translate(0.0F, f2, 0.0F);
/* 278 */     float f3 = 1.0F - (float)Math.pow(f1, 27.0D);
/* 279 */     GlStateManager.translate(f3 * 0.6F, f3 * -0.5F, f3 * 0.0F);
/* 280 */     GlStateManager.rotate(f3 * 90.0F, 0.0F, 1.0F, 0.0F);
/* 281 */     GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
/* 282 */     GlStateManager.rotate(f3 * 30.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transformFirstPersonItem(float equipProgress, float swingProgress) {
/* 289 */     GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
/* 290 */     GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
/* 291 */     GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 292 */     float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
/* 293 */     float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 294 */     GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
/* 295 */     GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 296 */     GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
/* 297 */     GlStateManager.scale(0.4F, 0.4F, 0.4F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doBowTransformations(float partialTicks, AbstractClientPlayer clientPlayer) {
/* 306 */     GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
/* 307 */     GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
/* 308 */     GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
/* 309 */     GlStateManager.translate(-0.9F, 0.2F, 0.0F);
/* 310 */     float f = this.itemToRender.getMaxItemUseDuration() - clientPlayer.getItemInUseCount() - partialTicks + 1.0F;
/* 311 */     float f1 = f / 20.0F;
/* 312 */     f1 = (f1 * f1 + f1 * 2.0F) / 3.0F;
/*     */     
/* 314 */     if (f1 > 1.0F) {
/* 315 */       f1 = 1.0F;
/*     */     }
/*     */     
/* 318 */     if (f1 > 0.1F) {
/* 319 */       float f2 = MathHelper.sin((f - 0.1F) * 1.3F);
/* 320 */       float f3 = f1 - 0.1F;
/* 321 */       float f4 = f2 * f3;
/* 322 */       GlStateManager.translate(f4 * 0.0F, f4 * 0.01F, f4 * 0.0F);
/*     */     } 
/*     */     
/* 325 */     GlStateManager.translate(f1 * 0.0F, f1 * 0.0F, f1 * 0.1F);
/* 326 */     GlStateManager.scale(1.0F, 1.0F, 1.0F + f1 * 0.2F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doBlockTransformations() {
/* 333 */     GlStateManager.translate(-0.5F, 0.2F, 0.0F);
/* 334 */     GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
/* 335 */     GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
/* 336 */     GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderItemInFirstPerson(float partialTicks) {
/* 343 */     if (!Config.isShaders() || !Shaders.isSkipRenderHand()) {
/* 344 */       float f = 1.0F - this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks;
/* 345 */       EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/* 346 */       float f1 = entityPlayerSP.getSwingProgress(partialTicks);
/* 347 */       float f2 = ((AbstractClientPlayer)entityPlayerSP).prevRotationPitch + (((AbstractClientPlayer)entityPlayerSP).rotationPitch - ((AbstractClientPlayer)entityPlayerSP).prevRotationPitch) * partialTicks;
/* 348 */       float f3 = ((AbstractClientPlayer)entityPlayerSP).prevRotationYaw + (((AbstractClientPlayer)entityPlayerSP).rotationYaw - ((AbstractClientPlayer)entityPlayerSP).prevRotationYaw) * partialTicks;
/* 349 */       rotateArroundXAndY(f2, f3);
/* 350 */       setLightMapFromPlayer((AbstractClientPlayer)entityPlayerSP);
/* 351 */       rotateWithPlayerRotations(entityPlayerSP, partialTicks);
/* 352 */       GlStateManager.enableRescaleNormal();
/* 353 */       GlStateManager.pushMatrix();
/*     */       
/* 355 */       if (this.itemToRender != null) {
/* 356 */         if (this.itemToRender.getItem() instanceof net.minecraft.item.ItemMap) {
/* 357 */           renderItemMap((AbstractClientPlayer)entityPlayerSP, f2, f, f1);
/* 358 */         } else if (entityPlayerSP.getItemInUseCount() > 0) {
/* 359 */           EnumAction enumaction = this.itemToRender.getItemUseAction();
/*     */           
/* 361 */           switch (enumaction) {
/*     */             case NONE:
/* 363 */               transformFirstPersonItem(f, f1);
/*     */               break;
/*     */             
/*     */             case EAT:
/*     */             case DRINK:
/* 368 */               performDrinking((AbstractClientPlayer)entityPlayerSP, partialTicks);
/* 369 */               transformFirstPersonItem(f, f1);
/*     */               break;
/*     */             
/*     */             case null:
/* 373 */               transformFirstPersonItem(0.2F, f1);
/* 374 */               doBlockTransformations();
/* 375 */               GlStateManager.translate(-0.5F, 0.2F, 0.0F);
/*     */               break;
/*     */             
/*     */             case BOW:
/* 379 */               transformFirstPersonItem(f, f1);
/* 380 */               doBowTransformations(partialTicks, (AbstractClientPlayer)entityPlayerSP); break;
/*     */           } 
/*     */         } else {
/* 383 */           doItemUsedTransformations(f1);
/* 384 */           transformFirstPersonItem(f, f1);
/*     */         } 
/*     */         
/* 387 */         renderItem((EntityLivingBase)entityPlayerSP, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
/* 388 */       } else if (!entityPlayerSP.isInvisible()) {
/* 389 */         renderPlayerArm((AbstractClientPlayer)entityPlayerSP, f, f1);
/*     */       } 
/*     */       
/* 392 */       GlStateManager.popMatrix();
/* 393 */       GlStateManager.disableRescaleNormal();
/* 394 */       RenderHelper.disableStandardItemLighting();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderOverlays(float partialTicks) {
/* 402 */     GlStateManager.disableAlpha();
/*     */     
/* 404 */     if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
/* 405 */       IBlockState iblockstate = this.mc.theWorld.getBlockState(new BlockPos((Entity)this.mc.thePlayer));
/* 406 */       BlockPos blockpos = new BlockPos((Entity)this.mc.thePlayer);
/* 407 */       EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/*     */       
/* 409 */       for (int i = 0; i < 8; i++) {
/* 410 */         double d0 = ((EntityPlayer)entityPlayerSP).posX + ((((i >> 0) % 2) - 0.5F) * ((EntityPlayer)entityPlayerSP).width * 0.8F);
/* 411 */         double d1 = ((EntityPlayer)entityPlayerSP).posY + ((((i >> 1) % 2) - 0.5F) * 0.1F);
/* 412 */         double d2 = ((EntityPlayer)entityPlayerSP).posZ + ((((i >> 2) % 2) - 0.5F) * ((EntityPlayer)entityPlayerSP).width * 0.8F);
/* 413 */         BlockPos blockpos1 = new BlockPos(d0, d1 + entityPlayerSP.getEyeHeight(), d2);
/* 414 */         IBlockState iblockstate1 = this.mc.theWorld.getBlockState(blockpos1);
/*     */         
/* 416 */         if (iblockstate1.getBlock().isVisuallyOpaque()) {
/* 417 */           iblockstate = iblockstate1;
/* 418 */           blockpos = blockpos1;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 423 */       Object object = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);
/*     */       
/* 425 */       if (iblockstate.getBlock().getRenderType() != -1 && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, new Object[] { this.mc.thePlayer, Float.valueOf(partialTicks), object, iblockstate, blockpos })) {
/* 426 */         renderBlockInHand(partialTicks, this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 431 */     if (!this.mc.thePlayer.isSpectator()) {
/* 432 */       if (this.mc.thePlayer.isInsideOfMaterial(Material.water) && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay, new Object[] { this.mc.thePlayer, Float.valueOf(partialTicks) })) {
/* 433 */         renderWaterOverlayTexture(partialTicks);
/*     */       }
/*     */       
/* 436 */       if (this.mc.thePlayer.isBurning() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay, new Object[] { this.mc.thePlayer, Float.valueOf(partialTicks) })) {
/* 437 */         renderFireInFirstPerson(partialTicks);
/*     */       }
/*     */     } 
/*     */     
/* 441 */     GlStateManager.enableAlpha();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderBlockInHand(float partialTicks, TextureAtlasSprite atlas) {
/* 451 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 452 */     Tessellator tessellator = Tessellator.getInstance();
/* 453 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 454 */     float f = 0.1F;
/* 455 */     GlStateManager.color(0.1F, 0.1F, 0.1F, 0.5F);
/* 456 */     GlStateManager.pushMatrix();
/* 457 */     float f1 = -1.0F;
/* 458 */     float f2 = 1.0F;
/* 459 */     float f3 = -1.0F;
/* 460 */     float f4 = 1.0F;
/* 461 */     float f5 = -0.5F;
/* 462 */     float f6 = atlas.getMinU();
/* 463 */     float f7 = atlas.getMaxU();
/* 464 */     float f8 = atlas.getMinV();
/* 465 */     float f9 = atlas.getMaxV();
/* 466 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 467 */     worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex(f7, f9).endVertex();
/* 468 */     worldrenderer.pos(1.0D, -1.0D, -0.5D).tex(f6, f9).endVertex();
/* 469 */     worldrenderer.pos(1.0D, 1.0D, -0.5D).tex(f6, f8).endVertex();
/* 470 */     worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex(f7, f8).endVertex();
/* 471 */     tessellator.draw();
/* 472 */     GlStateManager.popMatrix();
/* 473 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderWaterOverlayTexture(float partialTicks) {
/* 483 */     if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
/* 484 */       this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
/* 485 */       Tessellator tessellator = Tessellator.getInstance();
/* 486 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 487 */       float f = this.mc.thePlayer.getBrightness(partialTicks);
/* 488 */       GlStateManager.color(f, f, f, 0.5F);
/* 489 */       GlStateManager.enableBlend();
/* 490 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 491 */       GlStateManager.pushMatrix();
/* 492 */       float f1 = 4.0F;
/* 493 */       float f2 = -1.0F;
/* 494 */       float f3 = 1.0F;
/* 495 */       float f4 = -1.0F;
/* 496 */       float f5 = 1.0F;
/* 497 */       float f6 = -0.5F;
/* 498 */       float f7 = -this.mc.thePlayer.rotationYaw / 64.0F;
/* 499 */       float f8 = this.mc.thePlayer.rotationPitch / 64.0F;
/* 500 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 501 */       worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex((4.0F + f7), (4.0F + f8)).endVertex();
/* 502 */       worldrenderer.pos(1.0D, -1.0D, -0.5D).tex((0.0F + f7), (4.0F + f8)).endVertex();
/* 503 */       worldrenderer.pos(1.0D, 1.0D, -0.5D).tex((0.0F + f7), (0.0F + f8)).endVertex();
/* 504 */       worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex((4.0F + f7), (0.0F + f8)).endVertex();
/* 505 */       tessellator.draw();
/* 506 */       GlStateManager.popMatrix();
/* 507 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 508 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderFireInFirstPerson(float partialTicks) {
/* 518 */     Tessellator tessellator = Tessellator.getInstance();
/* 519 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 520 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
/* 521 */     GlStateManager.depthFunc(519);
/* 522 */     GlStateManager.depthMask(false);
/* 523 */     GlStateManager.enableBlend();
/* 524 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 525 */     float f = 1.0F;
/*     */     
/* 527 */     for (int i = 0; i < 2; i++) {
/* 528 */       GlStateManager.pushMatrix();
/* 529 */       TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
/* 530 */       this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 531 */       float f1 = textureatlassprite.getMinU();
/* 532 */       float f2 = textureatlassprite.getMaxU();
/* 533 */       float f3 = textureatlassprite.getMinV();
/* 534 */       float f4 = textureatlassprite.getMaxV();
/* 535 */       float f5 = (0.0F - f) / 2.0F;
/* 536 */       float f6 = f5 + f;
/* 537 */       float f7 = 0.0F - f / 2.0F;
/* 538 */       float f8 = f7 + f;
/* 539 */       float f9 = -0.5F;
/* 540 */       GlStateManager.translate(-(i * 2 - 1) * 0.24F, -0.3F, 0.0F);
/* 541 */       GlStateManager.rotate((i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
/* 542 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 543 */       worldrenderer.setSprite(textureatlassprite);
/* 544 */       worldrenderer.pos(f5, f7, f9).tex(f2, f4).endVertex();
/* 545 */       worldrenderer.pos(f6, f7, f9).tex(f1, f4).endVertex();
/* 546 */       worldrenderer.pos(f6, f8, f9).tex(f1, f3).endVertex();
/* 547 */       worldrenderer.pos(f5, f8, f9).tex(f2, f3).endVertex();
/* 548 */       tessellator.draw();
/* 549 */       GlStateManager.popMatrix();
/*     */     } 
/*     */     
/* 552 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 553 */     GlStateManager.disableBlend();
/* 554 */     GlStateManager.depthMask(true);
/* 555 */     GlStateManager.depthFunc(515);
/*     */   }
/*     */   
/*     */   public void updateEquippedItem() {
/* 559 */     this.prevEquippedProgress = this.equippedProgress;
/* 560 */     EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/* 561 */     ItemStack itemstack = ((EntityPlayer)entityPlayerSP).inventory.getCurrentItem();
/* 562 */     boolean flag = false;
/*     */     
/* 564 */     if (this.itemToRender != null && itemstack != null) {
/* 565 */       if (!this.itemToRender.getIsItemStackEqual(itemstack)) {
/* 566 */         if (Reflector.ForgeItem_shouldCauseReequipAnimation.exists()) {
/* 567 */           boolean flag1 = Reflector.callBoolean(this.itemToRender.getItem(), Reflector.ForgeItem_shouldCauseReequipAnimation, new Object[] { this.itemToRender, itemstack, Boolean.valueOf((this.equippedItemSlot != ((EntityPlayer)entityPlayerSP).inventory.currentItem)) });
/*     */           
/* 569 */           if (!flag1) {
/* 570 */             this.itemToRender = itemstack;
/* 571 */             this.equippedItemSlot = ((EntityPlayer)entityPlayerSP).inventory.currentItem;
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 576 */         flag = true;
/*     */       } 
/* 578 */     } else if (this.itemToRender == null && itemstack == null) {
/* 579 */       flag = false;
/*     */     } else {
/* 581 */       flag = true;
/*     */     } 
/*     */     
/* 584 */     float f2 = 0.4F;
/* 585 */     float f = flag ? 0.0F : 1.0F;
/* 586 */     float f1 = MathHelper.clamp_float(f - this.equippedProgress, -f2, f2);
/* 587 */     this.equippedProgress += f1;
/*     */     
/* 589 */     if (this.equippedProgress < 0.1F) {
/* 590 */       this.itemToRender = itemstack;
/* 591 */       this.equippedItemSlot = ((EntityPlayer)entityPlayerSP).inventory.currentItem;
/*     */       
/* 593 */       if (Config.isShaders()) {
/* 594 */         Shaders.setItemToRenderMain(itemstack);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetEquippedProgress() {
/* 603 */     this.equippedProgress = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetEquippedProgress2() {
/* 610 */     this.equippedProgress = 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\ItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */