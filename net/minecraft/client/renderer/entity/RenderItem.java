/*      */ package net.minecraft.client.renderer.entity;
/*      */ 
/*      */ import java.util.List;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockDirt;
/*      */ import net.minecraft.block.BlockDoublePlant;
/*      */ import net.minecraft.block.BlockFlower;
/*      */ import net.minecraft.block.BlockHugeMushroom;
/*      */ import net.minecraft.block.BlockPlanks;
/*      */ import net.minecraft.block.BlockPrismarine;
/*      */ import net.minecraft.block.BlockQuartz;
/*      */ import net.minecraft.block.BlockRedSandstone;
/*      */ import net.minecraft.block.BlockSand;
/*      */ import net.minecraft.block.BlockSandStone;
/*      */ import net.minecraft.block.BlockSilverfish;
/*      */ import net.minecraft.block.BlockStone;
/*      */ import net.minecraft.block.BlockStoneBrick;
/*      */ import net.minecraft.block.BlockStoneSlab;
/*      */ import net.minecraft.block.BlockStoneSlabNew;
/*      */ import net.minecraft.block.BlockTallGrass;
/*      */ import net.minecraft.block.BlockWall;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.ItemMeshDefinition;
/*      */ import net.minecraft.client.renderer.ItemModelMesher;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*      */ import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.texture.TextureUtil;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.resources.model.IBakedModel;
/*      */ import net.minecraft.client.resources.model.ModelManager;
/*      */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemFishFood;
/*      */ import net.minecraft.item.ItemPotion;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3i;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.CustomItems;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.reflect.ReflectorForge;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.shaders.ShadersRender;
/*      */ 
/*      */ public class RenderItem
/*      */   implements IResourceManagerReloadListener {
/*   72 */   private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*      */ 
/*      */   
/*      */   private boolean notRenderingEffectsInGUI = true;
/*      */ 
/*      */   
/*      */   public float zLevel;
/*      */ 
/*      */   
/*      */   private final ItemModelMesher itemModelMesher;
/*      */   
/*      */   private final TextureManager textureManager;
/*      */   
/*   85 */   private ModelResourceLocation modelLocation = null;
/*      */   private boolean renderItemGui = false;
/*   87 */   public ModelManager modelManager = null;
/*      */   private boolean renderModelHasEmissive = false;
/*      */   private boolean renderModelEmissive = false;
/*      */   
/*      */   public RenderItem(TextureManager textureManager, ModelManager modelManager) {
/*   92 */     this.textureManager = textureManager;
/*   93 */     this.modelManager = modelManager;
/*      */     
/*   95 */     if (Reflector.ItemModelMesherForge_Constructor.exists()) {
/*   96 */       this.itemModelMesher = (ItemModelMesher)Reflector.newInstance(Reflector.ItemModelMesherForge_Constructor, new Object[] { modelManager });
/*      */     } else {
/*   98 */       this.itemModelMesher = new ItemModelMesher(modelManager);
/*      */     } 
/*      */     
/*  101 */     registerItems();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void isNotRenderingEffectsInGUI(boolean isNot) {
/*  110 */     this.notRenderingEffectsInGUI = isNot;
/*      */   }
/*      */   
/*      */   public ItemModelMesher getItemModelMesher() {
/*  114 */     return this.itemModelMesher;
/*      */   }
/*      */   
/*      */   protected void registerItem(Item itm, int subType, String identifier) {
/*  118 */     this.itemModelMesher.register(itm, subType, new ModelResourceLocation(identifier, "inventory"));
/*      */   }
/*      */   
/*      */   protected void registerBlock(Block blk, int subType, String identifier) {
/*  122 */     registerItem(Item.getItemFromBlock(blk), subType, identifier);
/*      */   }
/*      */   
/*      */   private void registerBlock(Block blk, String identifier) {
/*  126 */     registerBlock(blk, 0, identifier);
/*      */   }
/*      */   
/*      */   private void registerItem(Item itm, String identifier) {
/*  130 */     registerItem(itm, 0, identifier);
/*      */   }
/*      */   
/*      */   private void renderModel(IBakedModel model, ItemStack stack) {
/*  134 */     renderModel(model, -1, stack);
/*      */   }
/*      */   
/*      */   public void renderModel(IBakedModel model, int color) {
/*  138 */     renderModel(model, color, null);
/*      */   }
/*      */   
/*      */   private void renderModel(IBakedModel model, int color, ItemStack stack) {
/*  142 */     Tessellator tessellator = Tessellator.getInstance();
/*  143 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  144 */     boolean flag = Minecraft.getMinecraft().getTextureMapBlocks().isTextureBound();
/*  145 */     boolean flag1 = (Config.isMultiTexture() && flag);
/*      */     
/*  147 */     if (flag1) {
/*  148 */       worldrenderer.setBlockLayer(EnumWorldBlockLayer.SOLID);
/*      */     }
/*      */     
/*  151 */     worldrenderer.begin(7, DefaultVertexFormats.ITEM); byte b; int i;
/*      */     EnumFacing[] arrayOfEnumFacing;
/*  153 */     for (i = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*  154 */       renderQuads(worldrenderer, model.getFaceQuads(enumfacing), color, stack);
/*      */       b++; }
/*      */     
/*  157 */     renderQuads(worldrenderer, model.getGeneralQuads(), color, stack);
/*  158 */     tessellator.draw();
/*      */     
/*  160 */     if (flag1) {
/*  161 */       worldrenderer.setBlockLayer(null);
/*  162 */       GlStateManager.bindCurrentTexture();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderItem(ItemStack stack, IBakedModel model) {
/*  167 */     if (stack != null) {
/*  168 */       GlStateManager.pushMatrix();
/*  169 */       GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*      */       
/*  171 */       if (model.isBuiltInRenderer()) {
/*  172 */         GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*  173 */         GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/*  174 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  175 */         GlStateManager.enableRescaleNormal();
/*  176 */         TileEntityItemStackRenderer.instance.renderByItem(stack);
/*      */       } else {
/*  178 */         GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/*      */         
/*  180 */         if (Config.isCustomItems()) {
/*  181 */           model = CustomItems.getCustomItemModel(stack, model, (ResourceLocation)this.modelLocation, false);
/*      */         }
/*      */         
/*  184 */         this.renderModelHasEmissive = false;
/*  185 */         renderModel(model, stack);
/*      */         
/*  187 */         if (this.renderModelHasEmissive) {
/*  188 */           float f = OpenGlHelper.lastBrightnessX;
/*  189 */           float f1 = OpenGlHelper.lastBrightnessY;
/*  190 */           OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, f1);
/*  191 */           this.renderModelEmissive = true;
/*  192 */           renderModel(model, stack);
/*  193 */           this.renderModelEmissive = false;
/*  194 */           OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
/*      */         } 
/*      */         
/*  197 */         if (stack.hasEffect() && (!Config.isCustomItems() || !CustomItems.renderCustomEffect(this, stack, model))) {
/*  198 */           renderEffect(model);
/*      */         }
/*      */       } 
/*      */       
/*  202 */       GlStateManager.popMatrix();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderEffect(IBakedModel model) {
/*  207 */     if ((!Config.isCustomItems() || CustomItems.isUseGlint()) && (
/*  208 */       !Config.isShaders() || !Shaders.isShadowPass)) {
/*  209 */       GlStateManager.depthMask(false);
/*  210 */       GlStateManager.depthFunc(514);
/*  211 */       GlStateManager.disableLighting();
/*  212 */       GlStateManager.blendFunc(768, 1);
/*  213 */       this.textureManager.bindTexture(RES_ITEM_GLINT);
/*      */       
/*  215 */       if (Config.isShaders() && !this.renderItemGui) {
/*  216 */         ShadersRender.renderEnchantedGlintBegin();
/*      */       }
/*      */       
/*  219 */       GlStateManager.matrixMode(5890);
/*  220 */       GlStateManager.pushMatrix();
/*  221 */       GlStateManager.scale(8.0F, 8.0F, 8.0F);
/*  222 */       float f = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/*  223 */       GlStateManager.translate(f, 0.0F, 0.0F);
/*  224 */       GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
/*  225 */       renderModel(model, -8372020);
/*  226 */       GlStateManager.popMatrix();
/*  227 */       GlStateManager.pushMatrix();
/*  228 */       GlStateManager.scale(8.0F, 8.0F, 8.0F);
/*  229 */       float f1 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
/*  230 */       GlStateManager.translate(-f1, 0.0F, 0.0F);
/*  231 */       GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
/*  232 */       renderModel(model, -8372020);
/*  233 */       GlStateManager.popMatrix();
/*  234 */       GlStateManager.matrixMode(5888);
/*  235 */       GlStateManager.blendFunc(770, 771);
/*  236 */       GlStateManager.enableLighting();
/*  237 */       GlStateManager.depthFunc(515);
/*  238 */       GlStateManager.depthMask(true);
/*  239 */       this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*      */       
/*  241 */       if (Config.isShaders() && !this.renderItemGui) {
/*  242 */         ShadersRender.renderEnchantedGlintEnd();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void putQuadNormal(WorldRenderer renderer, BakedQuad quad) {
/*  249 */     Vec3i vec3i = quad.getFace().getDirectionVec();
/*  250 */     renderer.putNormal(vec3i.getX(), vec3i.getY(), vec3i.getZ());
/*      */   }
/*      */   
/*      */   private void renderQuad(WorldRenderer renderer, BakedQuad quad, int color) {
/*  254 */     if (this.renderModelEmissive) {
/*  255 */       if (quad.getQuadEmissive() == null) {
/*      */         return;
/*      */       }
/*      */       
/*  259 */       quad = quad.getQuadEmissive();
/*  260 */     } else if (quad.getQuadEmissive() != null) {
/*  261 */       this.renderModelHasEmissive = true;
/*      */     } 
/*      */     
/*  264 */     if (renderer.isMultiTexture()) {
/*  265 */       renderer.addVertexData(quad.getVertexDataSingle());
/*      */     } else {
/*  267 */       renderer.addVertexData(quad.getVertexData());
/*      */     } 
/*      */     
/*  270 */     renderer.putSprite(quad.getSprite());
/*      */     
/*  272 */     if (Reflector.IColoredBakedQuad.exists() && Reflector.IColoredBakedQuad.isInstance(quad)) {
/*  273 */       forgeHooksClient_putQuadColor(renderer, quad, color);
/*      */     } else {
/*  275 */       renderer.putColor4(color);
/*      */     } 
/*      */     
/*  278 */     putQuadNormal(renderer, quad);
/*      */   }
/*      */   
/*      */   private void renderQuads(WorldRenderer renderer, List<BakedQuad> quads, int color, ItemStack stack) {
/*  282 */     boolean flag = (color == -1 && stack != null);
/*  283 */     int i = 0;
/*      */     
/*  285 */     for (int j = quads.size(); i < j; i++) {
/*  286 */       BakedQuad bakedquad = quads.get(i);
/*  287 */       int k = color;
/*      */       
/*  289 */       if (flag && bakedquad.hasTintIndex()) {
/*  290 */         k = stack.getItem().getColorFromItemStack(stack, bakedquad.getTintIndex());
/*      */         
/*  292 */         if (Config.isCustomColors()) {
/*  293 */           k = CustomColors.getColorFromItemStack(stack, bakedquad.getTintIndex(), k);
/*      */         }
/*      */         
/*  296 */         if (EntityRenderer.anaglyphEnable) {
/*  297 */           k = TextureUtil.anaglyphColor(k);
/*      */         }
/*      */         
/*  300 */         k |= 0xFF000000;
/*      */       } 
/*      */       
/*  303 */       renderQuad(renderer, bakedquad, k);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean shouldRenderItemIn3D(ItemStack stack) {
/*  308 */     IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  309 */     return (ibakedmodel == null) ? false : ibakedmodel.isGui3d();
/*      */   }
/*      */   
/*      */   private void preTransform(ItemStack stack) {
/*  313 */     IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  314 */     Item item = stack.getItem();
/*      */     
/*  316 */     if (item != null) {
/*  317 */       boolean flag = ibakedmodel.isGui3d();
/*      */       
/*  319 */       if (!flag) {
/*  320 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*      */       }
/*      */       
/*  323 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType cameraTransformType) {
/*  328 */     if (stack != null) {
/*  329 */       IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  330 */       renderItemModelTransform(stack, ibakedmodel, cameraTransformType);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderItemModelForEntity(ItemStack stack, EntityLivingBase entityToRenderFor, ItemCameraTransforms.TransformType cameraTransformType) {
/*  335 */     if (stack != null && entityToRenderFor != null) {
/*  336 */       IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*      */       
/*  338 */       if (entityToRenderFor instanceof EntityPlayer) {
/*  339 */         EntityPlayer entityplayer = (EntityPlayer)entityToRenderFor;
/*  340 */         Item item = stack.getItem();
/*  341 */         ModelResourceLocation modelresourcelocation = null;
/*      */         
/*  343 */         if (item == Items.fishing_rod && entityplayer.fishEntity != null) {
/*  344 */           modelresourcelocation = new ModelResourceLocation("fishing_rod_cast", "inventory");
/*  345 */         } else if (item == Items.bow && entityplayer.getItemInUse() != null) {
/*  346 */           int i = stack.getMaxItemUseDuration() - entityplayer.getItemInUseCount();
/*      */           
/*  348 */           if (i >= 18) {
/*  349 */             modelresourcelocation = new ModelResourceLocation("bow_pulling_2", "inventory");
/*  350 */           } else if (i > 13) {
/*  351 */             modelresourcelocation = new ModelResourceLocation("bow_pulling_1", "inventory");
/*  352 */           } else if (i > 0) {
/*  353 */             modelresourcelocation = new ModelResourceLocation("bow_pulling_0", "inventory");
/*      */           } 
/*  355 */         } else if (Reflector.ForgeItem_getModel.exists()) {
/*  356 */           modelresourcelocation = (ModelResourceLocation)Reflector.call(item, Reflector.ForgeItem_getModel, new Object[] { stack, entityplayer, Integer.valueOf(entityplayer.getItemInUseCount()) });
/*      */         } 
/*      */         
/*  359 */         if (modelresourcelocation != null) {
/*  360 */           ibakedmodel = this.itemModelMesher.getModelManager().getModel(modelresourcelocation);
/*  361 */           this.modelLocation = modelresourcelocation;
/*      */         } 
/*      */       } 
/*      */       
/*  365 */       renderItemModelTransform(stack, ibakedmodel, cameraTransformType);
/*  366 */       this.modelLocation = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void renderItemModelTransform(ItemStack stack, IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType) {
/*  371 */     this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  372 */     this.textureManager.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/*  373 */     preTransform(stack);
/*  374 */     GlStateManager.enableRescaleNormal();
/*  375 */     GlStateManager.alphaFunc(516, 0.1F);
/*  376 */     GlStateManager.enableBlend();
/*  377 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  378 */     GlStateManager.pushMatrix();
/*      */     
/*  380 */     if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
/*  381 */       model = (IBakedModel)Reflector.call(Reflector.ForgeHooksClient_handleCameraTransforms, new Object[] { model, cameraTransformType });
/*      */     } else {
/*  383 */       ItemCameraTransforms itemcameratransforms = model.getItemCameraTransforms();
/*  384 */       itemcameratransforms.applyTransform(cameraTransformType);
/*      */       
/*  386 */       if (isThereOneNegativeScale(itemcameratransforms.getTransform(cameraTransformType))) {
/*  387 */         GlStateManager.cullFace(1028);
/*      */       }
/*      */     } 
/*      */     
/*  391 */     renderItem(stack, model);
/*  392 */     GlStateManager.cullFace(1029);
/*  393 */     GlStateManager.popMatrix();
/*  394 */     GlStateManager.disableRescaleNormal();
/*  395 */     GlStateManager.disableBlend();
/*  396 */     this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  397 */     this.textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isThereOneNegativeScale(ItemTransformVec3f itemTranformVec) {
/*  406 */     return ((itemTranformVec.scale.x < 0.0F)) ^ ((itemTranformVec.scale.y < 0.0F)) ^ ((itemTranformVec.scale.z < 0.0F) ? 1 : 0);
/*      */   }
/*      */   
/*      */   public void renderItemIntoGUI(ItemStack stack, int x, int y) {
/*  410 */     this.renderItemGui = true;
/*  411 */     IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);
/*  412 */     GlStateManager.pushMatrix();
/*  413 */     this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  414 */     this.textureManager.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
/*  415 */     GlStateManager.enableRescaleNormal();
/*  416 */     GlStateManager.enableAlpha();
/*  417 */     GlStateManager.alphaFunc(516, 0.1F);
/*  418 */     GlStateManager.enableBlend();
/*  419 */     GlStateManager.blendFunc(770, 771);
/*  420 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  421 */     setupGuiTransform(x, y, ibakedmodel.isGui3d());
/*      */     
/*  423 */     if (Reflector.ForgeHooksClient_handleCameraTransforms.exists()) {
/*  424 */       ibakedmodel = (IBakedModel)Reflector.call(Reflector.ForgeHooksClient_handleCameraTransforms, new Object[] { ibakedmodel, ItemCameraTransforms.TransformType.GUI });
/*      */     } else {
/*  426 */       ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GUI);
/*      */     } 
/*      */     
/*  429 */     renderItem(stack, ibakedmodel);
/*  430 */     GlStateManager.disableAlpha();
/*  431 */     GlStateManager.disableRescaleNormal();
/*  432 */     GlStateManager.disableLighting();
/*  433 */     GlStateManager.popMatrix();
/*  434 */     this.textureManager.bindTexture(TextureMap.locationBlocksTexture);
/*  435 */     this.textureManager.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
/*  436 */     this.renderItemGui = false;
/*      */   }
/*      */   
/*      */   private void setupGuiTransform(int xPosition, int yPosition, boolean isGui3d) {
/*  440 */     GlStateManager.translate(xPosition, yPosition, 100.0F + this.zLevel);
/*  441 */     GlStateManager.translate(8.0F, 8.0F, 0.0F);
/*  442 */     GlStateManager.scale(1.0F, 1.0F, -1.0F);
/*  443 */     GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*      */     
/*  445 */     if (isGui3d) {
/*  446 */       GlStateManager.scale(40.0F, 40.0F, 40.0F);
/*  447 */       GlStateManager.rotate(210.0F, 1.0F, 0.0F, 0.0F);
/*  448 */       GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/*  449 */       GlStateManager.enableLighting();
/*      */     } else {
/*  451 */       GlStateManager.scale(64.0F, 64.0F, 64.0F);
/*  452 */       GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/*  453 */       GlStateManager.disableLighting();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderItemAndEffectIntoGUI(final ItemStack stack, int xPosition, int yPosition) {
/*  458 */     if (stack != null && stack.getItem() != null) {
/*  459 */       this.zLevel += 50.0F;
/*      */       
/*      */       try {
/*  462 */         renderItemIntoGUI(stack, xPosition, yPosition);
/*  463 */       } catch (Throwable throwable) {
/*  464 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering item");
/*  465 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being rendered");
/*  466 */         crashreportcategory.addCrashSectionCallable("Item Type", new Callable<String>() {
/*      */               public String call() throws Exception {
/*  468 */                 return String.valueOf(stack.getItem());
/*      */               }
/*      */             });
/*  471 */         crashreportcategory.addCrashSectionCallable("Item Aux", new Callable<String>() {
/*      */               public String call() throws Exception {
/*  473 */                 return String.valueOf(stack.getMetadata());
/*      */               }
/*      */             });
/*  476 */         crashreportcategory.addCrashSectionCallable("Item NBT", new Callable<String>() {
/*      */               public String call() throws Exception {
/*  478 */                 return String.valueOf(stack.getTagCompound());
/*      */               }
/*      */             });
/*  481 */         crashreportcategory.addCrashSectionCallable("Item Foil", new Callable<String>() {
/*      */               public String call() throws Exception {
/*  483 */                 return String.valueOf(stack.hasEffect());
/*      */               }
/*      */             });
/*  486 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*  489 */       this.zLevel -= 50.0F;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderItemOverlays(FontRenderer fr, ItemStack stack, int xPosition, int yPosition) {
/*  494 */     renderItemOverlayIntoGUI(fr, stack, xPosition, yPosition, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text) {
/*  501 */     if (stack != null) {
/*  502 */       if (stack.stackSize != 1 || text != null) {
/*  503 */         String s = (text == null) ? String.valueOf(stack.stackSize) : text;
/*      */         
/*  505 */         if (text == null && stack.stackSize < 1) {
/*  506 */           s = EnumChatFormatting.RED + String.valueOf(stack.stackSize);
/*      */         }
/*      */         
/*  509 */         GlStateManager.disableLighting();
/*  510 */         GlStateManager.disableDepth();
/*  511 */         GlStateManager.disableBlend();
/*  512 */         fr.drawStringWithShadow(s, (xPosition + 19 - 2 - fr.getStringWidth(s)), (yPosition + 6 + 3), 16777215);
/*  513 */         GlStateManager.enableLighting();
/*  514 */         GlStateManager.enableDepth();
/*  515 */         GlStateManager.enableBlend();
/*      */       } 
/*      */       
/*  518 */       if (ReflectorForge.isItemDamaged(stack)) {
/*  519 */         int j1 = (int)Math.round(13.0D - stack.getItemDamage() * 13.0D / stack.getMaxDamage());
/*  520 */         int i = (int)Math.round(255.0D - stack.getItemDamage() * 255.0D / stack.getMaxDamage());
/*      */         
/*  522 */         if (Reflector.ForgeItem_getDurabilityForDisplay.exists()) {
/*  523 */           double d0 = Reflector.callDouble(stack.getItem(), Reflector.ForgeItem_getDurabilityForDisplay, new Object[] { stack });
/*  524 */           j1 = (int)Math.round(13.0D - d0 * 13.0D);
/*  525 */           i = (int)Math.round(255.0D - d0 * 255.0D);
/*      */         } 
/*      */         
/*  528 */         GlStateManager.disableLighting();
/*  529 */         GlStateManager.disableDepth();
/*  530 */         GlStateManager.disableTexture2D();
/*  531 */         GlStateManager.disableAlpha();
/*  532 */         GlStateManager.disableBlend();
/*  533 */         Tessellator tessellator = Tessellator.getInstance();
/*  534 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  535 */         draw(worldrenderer, xPosition + 2, yPosition + 13, 13, 2, 0, 0, 0, 255);
/*  536 */         draw(worldrenderer, xPosition + 2, yPosition + 13, 12, 1, (255 - i) / 4, 64, 0, 255);
/*  537 */         int j = 255 - i;
/*  538 */         int k = i;
/*  539 */         int l = 0;
/*      */         
/*  541 */         if (Config.isCustomColors()) {
/*  542 */           int i1 = CustomColors.getDurabilityColor(i);
/*      */           
/*  544 */           if (i1 >= 0) {
/*  545 */             j = i1 >> 16 & 0xFF;
/*  546 */             k = i1 >> 8 & 0xFF;
/*  547 */             l = i1 >> 0 & 0xFF;
/*      */           } 
/*      */         } 
/*      */         
/*  551 */         draw(worldrenderer, xPosition + 2, yPosition + 13, j1, 1, j, k, l, 255);
/*  552 */         GlStateManager.enableBlend();
/*  553 */         GlStateManager.enableAlpha();
/*  554 */         GlStateManager.enableTexture2D();
/*  555 */         GlStateManager.enableLighting();
/*  556 */         GlStateManager.enableDepth();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void draw(WorldRenderer renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
/*  575 */     renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  576 */     renderer.pos((x + 0), (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
/*  577 */     renderer.pos((x + 0), (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
/*  578 */     renderer.pos((x + width), (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
/*  579 */     renderer.pos((x + width), (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
/*  580 */     Tessellator.getInstance().draw();
/*      */   }
/*      */   
/*      */   private void registerItems() {
/*  584 */     registerBlock(Blocks.anvil, "anvil_intact");
/*  585 */     registerBlock(Blocks.anvil, 1, "anvil_slightly_damaged");
/*  586 */     registerBlock(Blocks.anvil, 2, "anvil_very_damaged");
/*  587 */     registerBlock(Blocks.carpet, EnumDyeColor.BLACK.getMetadata(), "black_carpet");
/*  588 */     registerBlock(Blocks.carpet, EnumDyeColor.BLUE.getMetadata(), "blue_carpet");
/*  589 */     registerBlock(Blocks.carpet, EnumDyeColor.BROWN.getMetadata(), "brown_carpet");
/*  590 */     registerBlock(Blocks.carpet, EnumDyeColor.CYAN.getMetadata(), "cyan_carpet");
/*  591 */     registerBlock(Blocks.carpet, EnumDyeColor.GRAY.getMetadata(), "gray_carpet");
/*  592 */     registerBlock(Blocks.carpet, EnumDyeColor.GREEN.getMetadata(), "green_carpet");
/*  593 */     registerBlock(Blocks.carpet, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_carpet");
/*  594 */     registerBlock(Blocks.carpet, EnumDyeColor.LIME.getMetadata(), "lime_carpet");
/*  595 */     registerBlock(Blocks.carpet, EnumDyeColor.MAGENTA.getMetadata(), "magenta_carpet");
/*  596 */     registerBlock(Blocks.carpet, EnumDyeColor.ORANGE.getMetadata(), "orange_carpet");
/*  597 */     registerBlock(Blocks.carpet, EnumDyeColor.PINK.getMetadata(), "pink_carpet");
/*  598 */     registerBlock(Blocks.carpet, EnumDyeColor.PURPLE.getMetadata(), "purple_carpet");
/*  599 */     registerBlock(Blocks.carpet, EnumDyeColor.RED.getMetadata(), "red_carpet");
/*  600 */     registerBlock(Blocks.carpet, EnumDyeColor.SILVER.getMetadata(), "silver_carpet");
/*  601 */     registerBlock(Blocks.carpet, EnumDyeColor.WHITE.getMetadata(), "white_carpet");
/*  602 */     registerBlock(Blocks.carpet, EnumDyeColor.YELLOW.getMetadata(), "yellow_carpet");
/*  603 */     registerBlock(Blocks.cobblestone_wall, BlockWall.EnumType.MOSSY.getMetadata(), "mossy_cobblestone_wall");
/*  604 */     registerBlock(Blocks.cobblestone_wall, BlockWall.EnumType.NORMAL.getMetadata(), "cobblestone_wall");
/*  605 */     registerBlock(Blocks.dirt, BlockDirt.DirtType.COARSE_DIRT.getMetadata(), "coarse_dirt");
/*  606 */     registerBlock(Blocks.dirt, BlockDirt.DirtType.DIRT.getMetadata(), "dirt");
/*  607 */     registerBlock(Blocks.dirt, BlockDirt.DirtType.PODZOL.getMetadata(), "podzol");
/*  608 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.FERN.getMeta(), "double_fern");
/*  609 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.GRASS.getMeta(), "double_grass");
/*  610 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.PAEONIA.getMeta(), "paeonia");
/*  611 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.ROSE.getMeta(), "double_rose");
/*  612 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.SUNFLOWER.getMeta(), "sunflower");
/*  613 */     registerBlock((Block)Blocks.double_plant, BlockDoublePlant.EnumPlantType.SYRINGA.getMeta(), "syringa");
/*  614 */     registerBlock((Block)Blocks.leaves, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_leaves");
/*  615 */     registerBlock((Block)Blocks.leaves, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_leaves");
/*  616 */     registerBlock((Block)Blocks.leaves, BlockPlanks.EnumType.OAK.getMetadata(), "oak_leaves");
/*  617 */     registerBlock((Block)Blocks.leaves, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_leaves");
/*  618 */     registerBlock((Block)Blocks.leaves2, BlockPlanks.EnumType.ACACIA.getMetadata() - 4, "acacia_leaves");
/*  619 */     registerBlock((Block)Blocks.leaves2, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4, "dark_oak_leaves");
/*  620 */     registerBlock(Blocks.log, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_log");
/*  621 */     registerBlock(Blocks.log, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_log");
/*  622 */     registerBlock(Blocks.log, BlockPlanks.EnumType.OAK.getMetadata(), "oak_log");
/*  623 */     registerBlock(Blocks.log, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_log");
/*  624 */     registerBlock(Blocks.log2, BlockPlanks.EnumType.ACACIA.getMetadata() - 4, "acacia_log");
/*  625 */     registerBlock(Blocks.log2, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4, "dark_oak_log");
/*  626 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.CHISELED_STONEBRICK.getMetadata(), "chiseled_brick_monster_egg");
/*  627 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.COBBLESTONE.getMetadata(), "cobblestone_monster_egg");
/*  628 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.CRACKED_STONEBRICK.getMetadata(), "cracked_brick_monster_egg");
/*  629 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.MOSSY_STONEBRICK.getMetadata(), "mossy_brick_monster_egg");
/*  630 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.STONE.getMetadata(), "stone_monster_egg");
/*  631 */     registerBlock(Blocks.monster_egg, BlockSilverfish.EnumType.STONEBRICK.getMetadata(), "stone_brick_monster_egg");
/*  632 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_planks");
/*  633 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_planks");
/*  634 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_planks");
/*  635 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_planks");
/*  636 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.OAK.getMetadata(), "oak_planks");
/*  637 */     registerBlock(Blocks.planks, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_planks");
/*  638 */     registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.BRICKS.getMetadata(), "prismarine_bricks");
/*  639 */     registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.DARK.getMetadata(), "dark_prismarine");
/*  640 */     registerBlock(Blocks.prismarine, BlockPrismarine.EnumType.ROUGH.getMetadata(), "prismarine");
/*  641 */     registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.CHISELED.getMetadata(), "chiseled_quartz_block");
/*  642 */     registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.DEFAULT.getMetadata(), "quartz_block");
/*  643 */     registerBlock(Blocks.quartz_block, BlockQuartz.EnumType.LINES_Y.getMetadata(), "quartz_column");
/*  644 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.ALLIUM.getMeta(), "allium");
/*  645 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.BLUE_ORCHID.getMeta(), "blue_orchid");
/*  646 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.HOUSTONIA.getMeta(), "houstonia");
/*  647 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.ORANGE_TULIP.getMeta(), "orange_tulip");
/*  648 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta(), "oxeye_daisy");
/*  649 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.PINK_TULIP.getMeta(), "pink_tulip");
/*  650 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.POPPY.getMeta(), "poppy");
/*  651 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.RED_TULIP.getMeta(), "red_tulip");
/*  652 */     registerBlock((Block)Blocks.red_flower, BlockFlower.EnumFlowerType.WHITE_TULIP.getMeta(), "white_tulip");
/*  653 */     registerBlock((Block)Blocks.sand, BlockSand.EnumType.RED_SAND.getMetadata(), "red_sand");
/*  654 */     registerBlock((Block)Blocks.sand, BlockSand.EnumType.SAND.getMetadata(), "sand");
/*  655 */     registerBlock(Blocks.sandstone, BlockSandStone.EnumType.CHISELED.getMetadata(), "chiseled_sandstone");
/*  656 */     registerBlock(Blocks.sandstone, BlockSandStone.EnumType.DEFAULT.getMetadata(), "sandstone");
/*  657 */     registerBlock(Blocks.sandstone, BlockSandStone.EnumType.SMOOTH.getMetadata(), "smooth_sandstone");
/*  658 */     registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.CHISELED.getMetadata(), "chiseled_red_sandstone");
/*  659 */     registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.DEFAULT.getMetadata(), "red_sandstone");
/*  660 */     registerBlock(Blocks.red_sandstone, BlockRedSandstone.EnumType.SMOOTH.getMetadata(), "smooth_red_sandstone");
/*  661 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_sapling");
/*  662 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_sapling");
/*  663 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_sapling");
/*  664 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_sapling");
/*  665 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.OAK.getMetadata(), "oak_sapling");
/*  666 */     registerBlock(Blocks.sapling, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_sapling");
/*  667 */     registerBlock(Blocks.sponge, 0, "sponge");
/*  668 */     registerBlock(Blocks.sponge, 1, "sponge_wet");
/*  669 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.BLACK.getMetadata(), "black_stained_glass");
/*  670 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.BLUE.getMetadata(), "blue_stained_glass");
/*  671 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.BROWN.getMetadata(), "brown_stained_glass");
/*  672 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_glass");
/*  673 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.GRAY.getMetadata(), "gray_stained_glass");
/*  674 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.GREEN.getMetadata(), "green_stained_glass");
/*  675 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_glass");
/*  676 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.LIME.getMetadata(), "lime_stained_glass");
/*  677 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_glass");
/*  678 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_glass");
/*  679 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.PINK.getMetadata(), "pink_stained_glass");
/*  680 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_glass");
/*  681 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.RED.getMetadata(), "red_stained_glass");
/*  682 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.SILVER.getMetadata(), "silver_stained_glass");
/*  683 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.WHITE.getMetadata(), "white_stained_glass");
/*  684 */     registerBlock((Block)Blocks.stained_glass, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_glass");
/*  685 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.BLACK.getMetadata(), "black_stained_glass_pane");
/*  686 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.BLUE.getMetadata(), "blue_stained_glass_pane");
/*  687 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.BROWN.getMetadata(), "brown_stained_glass_pane");
/*  688 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_glass_pane");
/*  689 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.GRAY.getMetadata(), "gray_stained_glass_pane");
/*  690 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.GREEN.getMetadata(), "green_stained_glass_pane");
/*  691 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_glass_pane");
/*  692 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.LIME.getMetadata(), "lime_stained_glass_pane");
/*  693 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_glass_pane");
/*  694 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_glass_pane");
/*  695 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.PINK.getMetadata(), "pink_stained_glass_pane");
/*  696 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_glass_pane");
/*  697 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.RED.getMetadata(), "red_stained_glass_pane");
/*  698 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.SILVER.getMetadata(), "silver_stained_glass_pane");
/*  699 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.WHITE.getMetadata(), "white_stained_glass_pane");
/*  700 */     registerBlock((Block)Blocks.stained_glass_pane, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_glass_pane");
/*  701 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BLACK.getMetadata(), "black_stained_hardened_clay");
/*  702 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BLUE.getMetadata(), "blue_stained_hardened_clay");
/*  703 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.BROWN.getMetadata(), "brown_stained_hardened_clay");
/*  704 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.CYAN.getMetadata(), "cyan_stained_hardened_clay");
/*  705 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.GRAY.getMetadata(), "gray_stained_hardened_clay");
/*  706 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.GREEN.getMetadata(), "green_stained_hardened_clay");
/*  707 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_stained_hardened_clay");
/*  708 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.LIME.getMetadata(), "lime_stained_hardened_clay");
/*  709 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.MAGENTA.getMetadata(), "magenta_stained_hardened_clay");
/*  710 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.ORANGE.getMetadata(), "orange_stained_hardened_clay");
/*  711 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.PINK.getMetadata(), "pink_stained_hardened_clay");
/*  712 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.PURPLE.getMetadata(), "purple_stained_hardened_clay");
/*  713 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.RED.getMetadata(), "red_stained_hardened_clay");
/*  714 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.SILVER.getMetadata(), "silver_stained_hardened_clay");
/*  715 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.WHITE.getMetadata(), "white_stained_hardened_clay");
/*  716 */     registerBlock(Blocks.stained_hardened_clay, EnumDyeColor.YELLOW.getMetadata(), "yellow_stained_hardened_clay");
/*  717 */     registerBlock(Blocks.stone, BlockStone.EnumType.ANDESITE.getMetadata(), "andesite");
/*  718 */     registerBlock(Blocks.stone, BlockStone.EnumType.ANDESITE_SMOOTH.getMetadata(), "andesite_smooth");
/*  719 */     registerBlock(Blocks.stone, BlockStone.EnumType.DIORITE.getMetadata(), "diorite");
/*  720 */     registerBlock(Blocks.stone, BlockStone.EnumType.DIORITE_SMOOTH.getMetadata(), "diorite_smooth");
/*  721 */     registerBlock(Blocks.stone, BlockStone.EnumType.GRANITE.getMetadata(), "granite");
/*  722 */     registerBlock(Blocks.stone, BlockStone.EnumType.GRANITE_SMOOTH.getMetadata(), "granite_smooth");
/*  723 */     registerBlock(Blocks.stone, BlockStone.EnumType.STONE.getMetadata(), "stone");
/*  724 */     registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.CRACKED.getMetadata(), "cracked_stonebrick");
/*  725 */     registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.DEFAULT.getMetadata(), "stonebrick");
/*  726 */     registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.CHISELED.getMetadata(), "chiseled_stonebrick");
/*  727 */     registerBlock(Blocks.stonebrick, BlockStoneBrick.EnumType.MOSSY.getMetadata(), "mossy_stonebrick");
/*  728 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.BRICK.getMetadata(), "brick_slab");
/*  729 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.COBBLESTONE.getMetadata(), "cobblestone_slab");
/*  730 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.WOOD.getMetadata(), "old_wood_slab");
/*  731 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.NETHERBRICK.getMetadata(), "nether_brick_slab");
/*  732 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.QUARTZ.getMetadata(), "quartz_slab");
/*  733 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.SAND.getMetadata(), "sandstone_slab");
/*  734 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata(), "stone_brick_slab");
/*  735 */     registerBlock((Block)Blocks.stone_slab, BlockStoneSlab.EnumType.STONE.getMetadata(), "stone_slab");
/*  736 */     registerBlock((Block)Blocks.stone_slab2, BlockStoneSlabNew.EnumType.RED_SANDSTONE.getMetadata(), "red_sandstone_slab");
/*  737 */     registerBlock((Block)Blocks.tallgrass, BlockTallGrass.EnumType.DEAD_BUSH.getMeta(), "dead_bush");
/*  738 */     registerBlock((Block)Blocks.tallgrass, BlockTallGrass.EnumType.FERN.getMeta(), "fern");
/*  739 */     registerBlock((Block)Blocks.tallgrass, BlockTallGrass.EnumType.GRASS.getMeta(), "tall_grass");
/*  740 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia_slab");
/*  741 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.BIRCH.getMetadata(), "birch_slab");
/*  742 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak_slab");
/*  743 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle_slab");
/*  744 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.OAK.getMetadata(), "oak_slab");
/*  745 */     registerBlock((Block)Blocks.wooden_slab, BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce_slab");
/*  746 */     registerBlock(Blocks.wool, EnumDyeColor.BLACK.getMetadata(), "black_wool");
/*  747 */     registerBlock(Blocks.wool, EnumDyeColor.BLUE.getMetadata(), "blue_wool");
/*  748 */     registerBlock(Blocks.wool, EnumDyeColor.BROWN.getMetadata(), "brown_wool");
/*  749 */     registerBlock(Blocks.wool, EnumDyeColor.CYAN.getMetadata(), "cyan_wool");
/*  750 */     registerBlock(Blocks.wool, EnumDyeColor.GRAY.getMetadata(), "gray_wool");
/*  751 */     registerBlock(Blocks.wool, EnumDyeColor.GREEN.getMetadata(), "green_wool");
/*  752 */     registerBlock(Blocks.wool, EnumDyeColor.LIGHT_BLUE.getMetadata(), "light_blue_wool");
/*  753 */     registerBlock(Blocks.wool, EnumDyeColor.LIME.getMetadata(), "lime_wool");
/*  754 */     registerBlock(Blocks.wool, EnumDyeColor.MAGENTA.getMetadata(), "magenta_wool");
/*  755 */     registerBlock(Blocks.wool, EnumDyeColor.ORANGE.getMetadata(), "orange_wool");
/*  756 */     registerBlock(Blocks.wool, EnumDyeColor.PINK.getMetadata(), "pink_wool");
/*  757 */     registerBlock(Blocks.wool, EnumDyeColor.PURPLE.getMetadata(), "purple_wool");
/*  758 */     registerBlock(Blocks.wool, EnumDyeColor.RED.getMetadata(), "red_wool");
/*  759 */     registerBlock(Blocks.wool, EnumDyeColor.SILVER.getMetadata(), "silver_wool");
/*  760 */     registerBlock(Blocks.wool, EnumDyeColor.WHITE.getMetadata(), "white_wool");
/*  761 */     registerBlock(Blocks.wool, EnumDyeColor.YELLOW.getMetadata(), "yellow_wool");
/*  762 */     registerBlock(Blocks.acacia_stairs, "acacia_stairs");
/*  763 */     registerBlock(Blocks.activator_rail, "activator_rail");
/*  764 */     registerBlock((Block)Blocks.beacon, "beacon");
/*  765 */     registerBlock(Blocks.bedrock, "bedrock");
/*  766 */     registerBlock(Blocks.birch_stairs, "birch_stairs");
/*  767 */     registerBlock(Blocks.bookshelf, "bookshelf");
/*  768 */     registerBlock(Blocks.brick_block, "brick_block");
/*  769 */     registerBlock(Blocks.brick_block, "brick_block");
/*  770 */     registerBlock(Blocks.brick_stairs, "brick_stairs");
/*  771 */     registerBlock((Block)Blocks.brown_mushroom, "brown_mushroom");
/*  772 */     registerBlock((Block)Blocks.cactus, "cactus");
/*  773 */     registerBlock(Blocks.clay, "clay");
/*  774 */     registerBlock(Blocks.coal_block, "coal_block");
/*  775 */     registerBlock(Blocks.coal_ore, "coal_ore");
/*  776 */     registerBlock(Blocks.cobblestone, "cobblestone");
/*  777 */     registerBlock(Blocks.crafting_table, "crafting_table");
/*  778 */     registerBlock(Blocks.dark_oak_stairs, "dark_oak_stairs");
/*  779 */     registerBlock((Block)Blocks.daylight_detector, "daylight_detector");
/*  780 */     registerBlock((Block)Blocks.deadbush, "dead_bush");
/*  781 */     registerBlock(Blocks.detector_rail, "detector_rail");
/*  782 */     registerBlock(Blocks.diamond_block, "diamond_block");
/*  783 */     registerBlock(Blocks.diamond_ore, "diamond_ore");
/*  784 */     registerBlock(Blocks.dispenser, "dispenser");
/*  785 */     registerBlock(Blocks.dropper, "dropper");
/*  786 */     registerBlock(Blocks.emerald_block, "emerald_block");
/*  787 */     registerBlock(Blocks.emerald_ore, "emerald_ore");
/*  788 */     registerBlock(Blocks.enchanting_table, "enchanting_table");
/*  789 */     registerBlock(Blocks.end_portal_frame, "end_portal_frame");
/*  790 */     registerBlock(Blocks.end_stone, "end_stone");
/*  791 */     registerBlock(Blocks.oak_fence, "oak_fence");
/*  792 */     registerBlock(Blocks.spruce_fence, "spruce_fence");
/*  793 */     registerBlock(Blocks.birch_fence, "birch_fence");
/*  794 */     registerBlock(Blocks.jungle_fence, "jungle_fence");
/*  795 */     registerBlock(Blocks.dark_oak_fence, "dark_oak_fence");
/*  796 */     registerBlock(Blocks.acacia_fence, "acacia_fence");
/*  797 */     registerBlock(Blocks.oak_fence_gate, "oak_fence_gate");
/*  798 */     registerBlock(Blocks.spruce_fence_gate, "spruce_fence_gate");
/*  799 */     registerBlock(Blocks.birch_fence_gate, "birch_fence_gate");
/*  800 */     registerBlock(Blocks.jungle_fence_gate, "jungle_fence_gate");
/*  801 */     registerBlock(Blocks.dark_oak_fence_gate, "dark_oak_fence_gate");
/*  802 */     registerBlock(Blocks.acacia_fence_gate, "acacia_fence_gate");
/*  803 */     registerBlock(Blocks.furnace, "furnace");
/*  804 */     registerBlock(Blocks.glass, "glass");
/*  805 */     registerBlock(Blocks.glass_pane, "glass_pane");
/*  806 */     registerBlock(Blocks.glowstone, "glowstone");
/*  807 */     registerBlock(Blocks.golden_rail, "golden_rail");
/*  808 */     registerBlock(Blocks.gold_block, "gold_block");
/*  809 */     registerBlock(Blocks.gold_ore, "gold_ore");
/*  810 */     registerBlock((Block)Blocks.grass, "grass");
/*  811 */     registerBlock(Blocks.gravel, "gravel");
/*  812 */     registerBlock(Blocks.hardened_clay, "hardened_clay");
/*  813 */     registerBlock(Blocks.hay_block, "hay_block");
/*  814 */     registerBlock(Blocks.heavy_weighted_pressure_plate, "heavy_weighted_pressure_plate");
/*  815 */     registerBlock((Block)Blocks.hopper, "hopper");
/*  816 */     registerBlock(Blocks.ice, "ice");
/*  817 */     registerBlock(Blocks.iron_bars, "iron_bars");
/*  818 */     registerBlock(Blocks.iron_block, "iron_block");
/*  819 */     registerBlock(Blocks.iron_ore, "iron_ore");
/*  820 */     registerBlock(Blocks.iron_trapdoor, "iron_trapdoor");
/*  821 */     registerBlock(Blocks.jukebox, "jukebox");
/*  822 */     registerBlock(Blocks.jungle_stairs, "jungle_stairs");
/*  823 */     registerBlock(Blocks.ladder, "ladder");
/*  824 */     registerBlock(Blocks.lapis_block, "lapis_block");
/*  825 */     registerBlock(Blocks.lapis_ore, "lapis_ore");
/*  826 */     registerBlock(Blocks.lever, "lever");
/*  827 */     registerBlock(Blocks.light_weighted_pressure_plate, "light_weighted_pressure_plate");
/*  828 */     registerBlock(Blocks.lit_pumpkin, "lit_pumpkin");
/*  829 */     registerBlock(Blocks.melon_block, "melon_block");
/*  830 */     registerBlock(Blocks.mossy_cobblestone, "mossy_cobblestone");
/*  831 */     registerBlock((Block)Blocks.mycelium, "mycelium");
/*  832 */     registerBlock(Blocks.netherrack, "netherrack");
/*  833 */     registerBlock(Blocks.nether_brick, "nether_brick");
/*  834 */     registerBlock(Blocks.nether_brick_fence, "nether_brick_fence");
/*  835 */     registerBlock(Blocks.nether_brick_stairs, "nether_brick_stairs");
/*  836 */     registerBlock(Blocks.noteblock, "noteblock");
/*  837 */     registerBlock(Blocks.oak_stairs, "oak_stairs");
/*  838 */     registerBlock(Blocks.obsidian, "obsidian");
/*  839 */     registerBlock(Blocks.packed_ice, "packed_ice");
/*  840 */     registerBlock((Block)Blocks.piston, "piston");
/*  841 */     registerBlock(Blocks.pumpkin, "pumpkin");
/*  842 */     registerBlock(Blocks.quartz_ore, "quartz_ore");
/*  843 */     registerBlock(Blocks.quartz_stairs, "quartz_stairs");
/*  844 */     registerBlock(Blocks.rail, "rail");
/*  845 */     registerBlock(Blocks.redstone_block, "redstone_block");
/*  846 */     registerBlock(Blocks.redstone_lamp, "redstone_lamp");
/*  847 */     registerBlock(Blocks.redstone_ore, "redstone_ore");
/*  848 */     registerBlock(Blocks.redstone_torch, "redstone_torch");
/*  849 */     registerBlock((Block)Blocks.red_mushroom, "red_mushroom");
/*  850 */     registerBlock(Blocks.sandstone_stairs, "sandstone_stairs");
/*  851 */     registerBlock(Blocks.red_sandstone_stairs, "red_sandstone_stairs");
/*  852 */     registerBlock(Blocks.sea_lantern, "sea_lantern");
/*  853 */     registerBlock(Blocks.slime_block, "slime");
/*  854 */     registerBlock(Blocks.snow, "snow");
/*  855 */     registerBlock(Blocks.snow_layer, "snow_layer");
/*  856 */     registerBlock(Blocks.soul_sand, "soul_sand");
/*  857 */     registerBlock(Blocks.spruce_stairs, "spruce_stairs");
/*  858 */     registerBlock((Block)Blocks.sticky_piston, "sticky_piston");
/*  859 */     registerBlock(Blocks.stone_brick_stairs, "stone_brick_stairs");
/*  860 */     registerBlock(Blocks.stone_button, "stone_button");
/*  861 */     registerBlock(Blocks.stone_pressure_plate, "stone_pressure_plate");
/*  862 */     registerBlock(Blocks.stone_stairs, "stone_stairs");
/*  863 */     registerBlock(Blocks.tnt, "tnt");
/*  864 */     registerBlock(Blocks.torch, "torch");
/*  865 */     registerBlock(Blocks.trapdoor, "trapdoor");
/*  866 */     registerBlock((Block)Blocks.tripwire_hook, "tripwire_hook");
/*  867 */     registerBlock(Blocks.vine, "vine");
/*  868 */     registerBlock(Blocks.waterlily, "waterlily");
/*  869 */     registerBlock(Blocks.web, "web");
/*  870 */     registerBlock(Blocks.wooden_button, "wooden_button");
/*  871 */     registerBlock(Blocks.wooden_pressure_plate, "wooden_pressure_plate");
/*  872 */     registerBlock((Block)Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION.getMeta(), "dandelion");
/*  873 */     registerBlock((Block)Blocks.chest, "chest");
/*  874 */     registerBlock(Blocks.trapped_chest, "trapped_chest");
/*  875 */     registerBlock(Blocks.ender_chest, "ender_chest");
/*  876 */     registerItem(Items.iron_shovel, "iron_shovel");
/*  877 */     registerItem(Items.iron_pickaxe, "iron_pickaxe");
/*  878 */     registerItem(Items.iron_axe, "iron_axe");
/*  879 */     registerItem(Items.flint_and_steel, "flint_and_steel");
/*  880 */     registerItem(Items.apple, "apple");
/*  881 */     registerItem((Item)Items.bow, 0, "bow");
/*  882 */     registerItem((Item)Items.bow, 1, "bow_pulling_0");
/*  883 */     registerItem((Item)Items.bow, 2, "bow_pulling_1");
/*  884 */     registerItem((Item)Items.bow, 3, "bow_pulling_2");
/*  885 */     registerItem(Items.arrow, "arrow");
/*  886 */     registerItem(Items.coal, 0, "coal");
/*  887 */     registerItem(Items.coal, 1, "charcoal");
/*  888 */     registerItem(Items.diamond, "diamond");
/*  889 */     registerItem(Items.iron_ingot, "iron_ingot");
/*  890 */     registerItem(Items.gold_ingot, "gold_ingot");
/*  891 */     registerItem(Items.iron_sword, "iron_sword");
/*  892 */     registerItem(Items.wooden_sword, "wooden_sword");
/*  893 */     registerItem(Items.wooden_shovel, "wooden_shovel");
/*  894 */     registerItem(Items.wooden_pickaxe, "wooden_pickaxe");
/*  895 */     registerItem(Items.wooden_axe, "wooden_axe");
/*  896 */     registerItem(Items.stone_sword, "stone_sword");
/*  897 */     registerItem(Items.stone_shovel, "stone_shovel");
/*  898 */     registerItem(Items.stone_pickaxe, "stone_pickaxe");
/*  899 */     registerItem(Items.stone_axe, "stone_axe");
/*  900 */     registerItem(Items.diamond_sword, "diamond_sword");
/*  901 */     registerItem(Items.diamond_shovel, "diamond_shovel");
/*  902 */     registerItem(Items.diamond_pickaxe, "diamond_pickaxe");
/*  903 */     registerItem(Items.diamond_axe, "diamond_axe");
/*  904 */     registerItem(Items.stick, "stick");
/*  905 */     registerItem(Items.bowl, "bowl");
/*  906 */     registerItem(Items.mushroom_stew, "mushroom_stew");
/*  907 */     registerItem(Items.golden_sword, "golden_sword");
/*  908 */     registerItem(Items.golden_shovel, "golden_shovel");
/*  909 */     registerItem(Items.golden_pickaxe, "golden_pickaxe");
/*  910 */     registerItem(Items.golden_axe, "golden_axe");
/*  911 */     registerItem(Items.string, "string");
/*  912 */     registerItem(Items.feather, "feather");
/*  913 */     registerItem(Items.gunpowder, "gunpowder");
/*  914 */     registerItem(Items.wooden_hoe, "wooden_hoe");
/*  915 */     registerItem(Items.stone_hoe, "stone_hoe");
/*  916 */     registerItem(Items.iron_hoe, "iron_hoe");
/*  917 */     registerItem(Items.diamond_hoe, "diamond_hoe");
/*  918 */     registerItem(Items.golden_hoe, "golden_hoe");
/*  919 */     registerItem(Items.wheat_seeds, "wheat_seeds");
/*  920 */     registerItem(Items.wheat, "wheat");
/*  921 */     registerItem(Items.bread, "bread");
/*  922 */     registerItem((Item)Items.leather_helmet, "leather_helmet");
/*  923 */     registerItem((Item)Items.leather_chestplate, "leather_chestplate");
/*  924 */     registerItem((Item)Items.leather_leggings, "leather_leggings");
/*  925 */     registerItem((Item)Items.leather_boots, "leather_boots");
/*  926 */     registerItem((Item)Items.chainmail_helmet, "chainmail_helmet");
/*  927 */     registerItem((Item)Items.chainmail_chestplate, "chainmail_chestplate");
/*  928 */     registerItem((Item)Items.chainmail_leggings, "chainmail_leggings");
/*  929 */     registerItem((Item)Items.chainmail_boots, "chainmail_boots");
/*  930 */     registerItem((Item)Items.iron_helmet, "iron_helmet");
/*  931 */     registerItem((Item)Items.iron_chestplate, "iron_chestplate");
/*  932 */     registerItem((Item)Items.iron_leggings, "iron_leggings");
/*  933 */     registerItem((Item)Items.iron_boots, "iron_boots");
/*  934 */     registerItem((Item)Items.diamond_helmet, "diamond_helmet");
/*  935 */     registerItem((Item)Items.diamond_chestplate, "diamond_chestplate");
/*  936 */     registerItem((Item)Items.diamond_leggings, "diamond_leggings");
/*  937 */     registerItem((Item)Items.diamond_boots, "diamond_boots");
/*  938 */     registerItem((Item)Items.golden_helmet, "golden_helmet");
/*  939 */     registerItem((Item)Items.golden_chestplate, "golden_chestplate");
/*  940 */     registerItem((Item)Items.golden_leggings, "golden_leggings");
/*  941 */     registerItem((Item)Items.golden_boots, "golden_boots");
/*  942 */     registerItem(Items.flint, "flint");
/*  943 */     registerItem(Items.porkchop, "porkchop");
/*  944 */     registerItem(Items.cooked_porkchop, "cooked_porkchop");
/*  945 */     registerItem(Items.painting, "painting");
/*  946 */     registerItem(Items.golden_apple, "golden_apple");
/*  947 */     registerItem(Items.golden_apple, 1, "golden_apple");
/*  948 */     registerItem(Items.sign, "sign");
/*  949 */     registerItem(Items.oak_door, "oak_door");
/*  950 */     registerItem(Items.spruce_door, "spruce_door");
/*  951 */     registerItem(Items.birch_door, "birch_door");
/*  952 */     registerItem(Items.jungle_door, "jungle_door");
/*  953 */     registerItem(Items.acacia_door, "acacia_door");
/*  954 */     registerItem(Items.dark_oak_door, "dark_oak_door");
/*  955 */     registerItem(Items.bucket, "bucket");
/*  956 */     registerItem(Items.water_bucket, "water_bucket");
/*  957 */     registerItem(Items.lava_bucket, "lava_bucket");
/*  958 */     registerItem(Items.minecart, "minecart");
/*  959 */     registerItem(Items.saddle, "saddle");
/*  960 */     registerItem(Items.iron_door, "iron_door");
/*  961 */     registerItem(Items.redstone, "redstone");
/*  962 */     registerItem(Items.snowball, "snowball");
/*  963 */     registerItem(Items.boat, "boat");
/*  964 */     registerItem(Items.leather, "leather");
/*  965 */     registerItem(Items.milk_bucket, "milk_bucket");
/*  966 */     registerItem(Items.brick, "brick");
/*  967 */     registerItem(Items.clay_ball, "clay_ball");
/*  968 */     registerItem(Items.reeds, "reeds");
/*  969 */     registerItem(Items.paper, "paper");
/*  970 */     registerItem(Items.book, "book");
/*  971 */     registerItem(Items.slime_ball, "slime_ball");
/*  972 */     registerItem(Items.chest_minecart, "chest_minecart");
/*  973 */     registerItem(Items.furnace_minecart, "furnace_minecart");
/*  974 */     registerItem(Items.egg, "egg");
/*  975 */     registerItem(Items.compass, "compass");
/*  976 */     registerItem((Item)Items.fishing_rod, "fishing_rod");
/*  977 */     registerItem((Item)Items.fishing_rod, 1, "fishing_rod_cast");
/*  978 */     registerItem(Items.clock, "clock");
/*  979 */     registerItem(Items.glowstone_dust, "glowstone_dust");
/*  980 */     registerItem(Items.fish, ItemFishFood.FishType.COD.getMetadata(), "cod");
/*  981 */     registerItem(Items.fish, ItemFishFood.FishType.SALMON.getMetadata(), "salmon");
/*  982 */     registerItem(Items.fish, ItemFishFood.FishType.CLOWNFISH.getMetadata(), "clownfish");
/*  983 */     registerItem(Items.fish, ItemFishFood.FishType.PUFFERFISH.getMetadata(), "pufferfish");
/*  984 */     registerItem(Items.cooked_fish, ItemFishFood.FishType.COD.getMetadata(), "cooked_cod");
/*  985 */     registerItem(Items.cooked_fish, ItemFishFood.FishType.SALMON.getMetadata(), "cooked_salmon");
/*  986 */     registerItem(Items.dye, EnumDyeColor.BLACK.getDyeDamage(), "dye_black");
/*  987 */     registerItem(Items.dye, EnumDyeColor.RED.getDyeDamage(), "dye_red");
/*  988 */     registerItem(Items.dye, EnumDyeColor.GREEN.getDyeDamage(), "dye_green");
/*  989 */     registerItem(Items.dye, EnumDyeColor.BROWN.getDyeDamage(), "dye_brown");
/*  990 */     registerItem(Items.dye, EnumDyeColor.BLUE.getDyeDamage(), "dye_blue");
/*  991 */     registerItem(Items.dye, EnumDyeColor.PURPLE.getDyeDamage(), "dye_purple");
/*  992 */     registerItem(Items.dye, EnumDyeColor.CYAN.getDyeDamage(), "dye_cyan");
/*  993 */     registerItem(Items.dye, EnumDyeColor.SILVER.getDyeDamage(), "dye_silver");
/*  994 */     registerItem(Items.dye, EnumDyeColor.GRAY.getDyeDamage(), "dye_gray");
/*  995 */     registerItem(Items.dye, EnumDyeColor.PINK.getDyeDamage(), "dye_pink");
/*  996 */     registerItem(Items.dye, EnumDyeColor.LIME.getDyeDamage(), "dye_lime");
/*  997 */     registerItem(Items.dye, EnumDyeColor.YELLOW.getDyeDamage(), "dye_yellow");
/*  998 */     registerItem(Items.dye, EnumDyeColor.LIGHT_BLUE.getDyeDamage(), "dye_light_blue");
/*  999 */     registerItem(Items.dye, EnumDyeColor.MAGENTA.getDyeDamage(), "dye_magenta");
/* 1000 */     registerItem(Items.dye, EnumDyeColor.ORANGE.getDyeDamage(), "dye_orange");
/* 1001 */     registerItem(Items.dye, EnumDyeColor.WHITE.getDyeDamage(), "dye_white");
/* 1002 */     registerItem(Items.bone, "bone");
/* 1003 */     registerItem(Items.sugar, "sugar");
/* 1004 */     registerItem(Items.cake, "cake");
/* 1005 */     registerItem(Items.bed, "bed");
/* 1006 */     registerItem(Items.repeater, "repeater");
/* 1007 */     registerItem(Items.cookie, "cookie");
/* 1008 */     registerItem((Item)Items.shears, "shears");
/* 1009 */     registerItem(Items.melon, "melon");
/* 1010 */     registerItem(Items.pumpkin_seeds, "pumpkin_seeds");
/* 1011 */     registerItem(Items.melon_seeds, "melon_seeds");
/* 1012 */     registerItem(Items.beef, "beef");
/* 1013 */     registerItem(Items.cooked_beef, "cooked_beef");
/* 1014 */     registerItem(Items.chicken, "chicken");
/* 1015 */     registerItem(Items.cooked_chicken, "cooked_chicken");
/* 1016 */     registerItem(Items.rabbit, "rabbit");
/* 1017 */     registerItem(Items.cooked_rabbit, "cooked_rabbit");
/* 1018 */     registerItem(Items.mutton, "mutton");
/* 1019 */     registerItem(Items.cooked_mutton, "cooked_mutton");
/* 1020 */     registerItem(Items.rabbit_foot, "rabbit_foot");
/* 1021 */     registerItem(Items.rabbit_hide, "rabbit_hide");
/* 1022 */     registerItem(Items.rabbit_stew, "rabbit_stew");
/* 1023 */     registerItem(Items.rotten_flesh, "rotten_flesh");
/* 1024 */     registerItem(Items.ender_pearl, "ender_pearl");
/* 1025 */     registerItem(Items.blaze_rod, "blaze_rod");
/* 1026 */     registerItem(Items.ghast_tear, "ghast_tear");
/* 1027 */     registerItem(Items.gold_nugget, "gold_nugget");
/* 1028 */     registerItem(Items.nether_wart, "nether_wart");
/* 1029 */     this.itemModelMesher.register((Item)Items.potionitem, new ItemMeshDefinition() {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack) {
/* 1031 */             return ItemPotion.isSplash(stack.getMetadata()) ? new ModelResourceLocation("bottle_splash", "inventory") : new ModelResourceLocation("bottle_drinkable", "inventory");
/*      */           }
/*      */         });
/* 1034 */     registerItem(Items.glass_bottle, "glass_bottle");
/* 1035 */     registerItem(Items.spider_eye, "spider_eye");
/* 1036 */     registerItem(Items.fermented_spider_eye, "fermented_spider_eye");
/* 1037 */     registerItem(Items.blaze_powder, "blaze_powder");
/* 1038 */     registerItem(Items.magma_cream, "magma_cream");
/* 1039 */     registerItem(Items.brewing_stand, "brewing_stand");
/* 1040 */     registerItem(Items.cauldron, "cauldron");
/* 1041 */     registerItem(Items.ender_eye, "ender_eye");
/* 1042 */     registerItem(Items.speckled_melon, "speckled_melon");
/* 1043 */     this.itemModelMesher.register(Items.spawn_egg, new ItemMeshDefinition() {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack) {
/* 1045 */             return new ModelResourceLocation("spawn_egg", "inventory");
/*      */           }
/*      */         });
/* 1048 */     registerItem(Items.experience_bottle, "experience_bottle");
/* 1049 */     registerItem(Items.fire_charge, "fire_charge");
/* 1050 */     registerItem(Items.writable_book, "writable_book");
/* 1051 */     registerItem(Items.emerald, "emerald");
/* 1052 */     registerItem(Items.item_frame, "item_frame");
/* 1053 */     registerItem(Items.flower_pot, "flower_pot");
/* 1054 */     registerItem(Items.carrot, "carrot");
/* 1055 */     registerItem(Items.potato, "potato");
/* 1056 */     registerItem(Items.baked_potato, "baked_potato");
/* 1057 */     registerItem(Items.poisonous_potato, "poisonous_potato");
/* 1058 */     registerItem((Item)Items.map, "map");
/* 1059 */     registerItem(Items.golden_carrot, "golden_carrot");
/* 1060 */     registerItem(Items.skull, 0, "skull_skeleton");
/* 1061 */     registerItem(Items.skull, 1, "skull_wither");
/* 1062 */     registerItem(Items.skull, 2, "skull_zombie");
/* 1063 */     registerItem(Items.skull, 3, "skull_char");
/* 1064 */     registerItem(Items.skull, 4, "skull_creeper");
/* 1065 */     registerItem(Items.carrot_on_a_stick, "carrot_on_a_stick");
/* 1066 */     registerItem(Items.nether_star, "nether_star");
/* 1067 */     registerItem(Items.pumpkin_pie, "pumpkin_pie");
/* 1068 */     registerItem(Items.firework_charge, "firework_charge");
/* 1069 */     registerItem(Items.comparator, "comparator");
/* 1070 */     registerItem(Items.netherbrick, "netherbrick");
/* 1071 */     registerItem(Items.quartz, "quartz");
/* 1072 */     registerItem(Items.tnt_minecart, "tnt_minecart");
/* 1073 */     registerItem(Items.hopper_minecart, "hopper_minecart");
/* 1074 */     registerItem((Item)Items.armor_stand, "armor_stand");
/* 1075 */     registerItem(Items.iron_horse_armor, "iron_horse_armor");
/* 1076 */     registerItem(Items.golden_horse_armor, "golden_horse_armor");
/* 1077 */     registerItem(Items.diamond_horse_armor, "diamond_horse_armor");
/* 1078 */     registerItem(Items.lead, "lead");
/* 1079 */     registerItem(Items.name_tag, "name_tag");
/* 1080 */     this.itemModelMesher.register(Items.banner, new ItemMeshDefinition() {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack) {
/* 1082 */             return new ModelResourceLocation("banner", "inventory");
/*      */           }
/*      */         });
/* 1085 */     registerItem(Items.record_13, "record_13");
/* 1086 */     registerItem(Items.record_cat, "record_cat");
/* 1087 */     registerItem(Items.record_blocks, "record_blocks");
/* 1088 */     registerItem(Items.record_chirp, "record_chirp");
/* 1089 */     registerItem(Items.record_far, "record_far");
/* 1090 */     registerItem(Items.record_mall, "record_mall");
/* 1091 */     registerItem(Items.record_mellohi, "record_mellohi");
/* 1092 */     registerItem(Items.record_stal, "record_stal");
/* 1093 */     registerItem(Items.record_strad, "record_strad");
/* 1094 */     registerItem(Items.record_ward, "record_ward");
/* 1095 */     registerItem(Items.record_11, "record_11");
/* 1096 */     registerItem(Items.record_wait, "record_wait");
/* 1097 */     registerItem(Items.prismarine_shard, "prismarine_shard");
/* 1098 */     registerItem(Items.prismarine_crystals, "prismarine_crystals");
/* 1099 */     this.itemModelMesher.register((Item)Items.enchanted_book, new ItemMeshDefinition() {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack) {
/* 1101 */             return new ModelResourceLocation("enchanted_book", "inventory");
/*      */           }
/*      */         });
/* 1104 */     this.itemModelMesher.register((Item)Items.filled_map, new ItemMeshDefinition() {
/*      */           public ModelResourceLocation getModelLocation(ItemStack stack) {
/* 1106 */             return new ModelResourceLocation("filled_map", "inventory");
/*      */           }
/*      */         });
/* 1109 */     registerBlock(Blocks.command_block, "command_block");
/* 1110 */     registerItem(Items.fireworks, "fireworks");
/* 1111 */     registerItem(Items.command_block_minecart, "command_block_minecart");
/* 1112 */     registerBlock(Blocks.barrier, "barrier");
/* 1113 */     registerBlock(Blocks.mob_spawner, "mob_spawner");
/* 1114 */     registerItem(Items.written_book, "written_book");
/* 1115 */     registerBlock(Blocks.brown_mushroom_block, BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), "brown_mushroom_block");
/* 1116 */     registerBlock(Blocks.red_mushroom_block, BlockHugeMushroom.EnumType.ALL_INSIDE.getMetadata(), "red_mushroom_block");
/* 1117 */     registerBlock(Blocks.dragon_egg, "dragon_egg");
/*      */     
/* 1119 */     if (Reflector.ModelLoader_onRegisterItems.exists()) {
/* 1120 */       Reflector.call(Reflector.ModelLoader_onRegisterItems, new Object[] { this.itemModelMesher });
/*      */     }
/*      */   }
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 1125 */     this.itemModelMesher.rebuildCache();
/*      */   }
/*      */   
/*      */   public static void forgeHooksClient_putQuadColor(WorldRenderer p_forgeHooksClient_putQuadColor_0_, BakedQuad p_forgeHooksClient_putQuadColor_1_, int p_forgeHooksClient_putQuadColor_2_) {
/* 1129 */     float f = (p_forgeHooksClient_putQuadColor_2_ & 0xFF);
/* 1130 */     float f1 = (p_forgeHooksClient_putQuadColor_2_ >>> 8 & 0xFF);
/* 1131 */     float f2 = (p_forgeHooksClient_putQuadColor_2_ >>> 16 & 0xFF);
/* 1132 */     float f3 = (p_forgeHooksClient_putQuadColor_2_ >>> 24 & 0xFF);
/* 1133 */     int[] aint = p_forgeHooksClient_putQuadColor_1_.getVertexData();
/* 1134 */     int i = aint.length / 4;
/*      */     
/* 1136 */     for (int j = 0; j < 4; j++) {
/* 1137 */       int k = aint[3 + i * j];
/* 1138 */       float f4 = (k & 0xFF);
/* 1139 */       float f5 = (k >>> 8 & 0xFF);
/* 1140 */       float f6 = (k >>> 16 & 0xFF);
/* 1141 */       float f7 = (k >>> 24 & 0xFF);
/* 1142 */       int l = Math.min(255, (int)(f * f4 / 255.0F));
/* 1143 */       int i1 = Math.min(255, (int)(f1 * f5 / 255.0F));
/* 1144 */       int j1 = Math.min(255, (int)(f2 * f6 / 255.0F));
/* 1145 */       int k1 = Math.min(255, (int)(f3 * f7 / 255.0F));
/* 1146 */       p_forgeHooksClient_putQuadColor_0_.putColorRGBA(p_forgeHooksClient_putQuadColor_0_.getColorIndex(4 - j), l, i1, j1, k1);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */