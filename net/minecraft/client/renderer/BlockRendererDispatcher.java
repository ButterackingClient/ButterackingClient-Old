/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.SimpleBakedModel;
/*     */ import net.minecraft.client.resources.model.WeightedBakedModel;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.WorldType;
/*     */ 
/*     */ public class BlockRendererDispatcher implements IResourceManagerReloadListener {
/*     */   private BlockModelShapes blockModelShapes;
/*  23 */   private final BlockModelRenderer blockModelRenderer = new BlockModelRenderer(); private final GameSettings gameSettings;
/*  24 */   private final ChestRenderer chestRenderer = new ChestRenderer();
/*  25 */   private final BlockFluidRenderer fluidRenderer = new BlockFluidRenderer();
/*     */   
/*     */   public BlockRendererDispatcher(BlockModelShapes blockModelShapesIn, GameSettings gameSettingsIn) {
/*  28 */     this.blockModelShapes = blockModelShapesIn;
/*  29 */     this.gameSettings = gameSettingsIn;
/*     */   }
/*     */   
/*     */   public BlockModelShapes getBlockModelShapes() {
/*  33 */     return this.blockModelShapes;
/*     */   }
/*     */   
/*     */   public void renderBlockDamage(IBlockState state, BlockPos pos, TextureAtlasSprite texture, IBlockAccess blockAccess) {
/*  37 */     Block block = state.getBlock();
/*  38 */     int i = block.getRenderType();
/*     */     
/*  40 */     if (i == 3) {
/*  41 */       state = block.getActualState(state, blockAccess, pos);
/*  42 */       IBakedModel ibakedmodel = this.blockModelShapes.getModelForState(state);
/*  43 */       IBakedModel ibakedmodel1 = (new SimpleBakedModel.Builder(ibakedmodel, texture)).makeBakedModel();
/*  44 */       this.blockModelRenderer.renderModel(blockAccess, ibakedmodel1, state, pos, Tessellator.getInstance().getWorldRenderer());
/*     */     } 
/*     */   }
/*     */   public boolean renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, WorldRenderer worldRendererIn) {
/*     */     try {
/*     */       IBakedModel ibakedmodel;
/*  50 */       int i = state.getBlock().getRenderType();
/*     */       
/*  52 */       if (i == -1) {
/*  53 */         return false;
/*     */       }
/*  55 */       switch (i) {
/*     */         case 1:
/*  57 */           return this.fluidRenderer.renderFluid(blockAccess, state, pos, worldRendererIn);
/*     */         
/*     */         case 2:
/*  60 */           return false;
/*     */         
/*     */         case 3:
/*  63 */           ibakedmodel = getModelFromBlockState(state, blockAccess, pos);
/*  64 */           return this.blockModelRenderer.renderModel(blockAccess, ibakedmodel, state, pos, worldRendererIn);
/*     */       } 
/*     */       
/*  67 */       return false;
/*     */     
/*     */     }
/*  70 */     catch (Throwable throwable) {
/*  71 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating block in world");
/*  72 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being tesselated");
/*  73 */       CrashReportCategory.addBlockInfo(crashreportcategory, pos, state.getBlock(), state.getBlock().getMetaFromState(state));
/*  74 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */   
/*     */   public BlockModelRenderer getBlockModelRenderer() {
/*  79 */     return this.blockModelRenderer;
/*     */   }
/*     */   
/*     */   private IBakedModel getBakedModel(IBlockState state, BlockPos pos) {
/*  83 */     IBakedModel ibakedmodel = this.blockModelShapes.getModelForState(state);
/*     */     
/*  85 */     if (pos != null && this.gameSettings.allowBlockAlternatives && ibakedmodel instanceof WeightedBakedModel) {
/*  86 */       ibakedmodel = ((WeightedBakedModel)ibakedmodel).getAlternativeModel(MathHelper.getPositionRandom((Vec3i)pos));
/*     */     }
/*     */     
/*  89 */     return ibakedmodel;
/*     */   }
/*     */   
/*     */   public IBakedModel getModelFromBlockState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  93 */     Block block = state.getBlock();
/*     */     
/*  95 */     if (worldIn.getWorldType() != WorldType.DEBUG_WORLD) {
/*     */       try {
/*  97 */         state = block.getActualState(state, worldIn, pos);
/*  98 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 103 */     IBakedModel ibakedmodel = this.blockModelShapes.getModelForState(state);
/*     */     
/* 105 */     if (pos != null && this.gameSettings.allowBlockAlternatives && ibakedmodel instanceof WeightedBakedModel) {
/* 106 */       ibakedmodel = ((WeightedBakedModel)ibakedmodel).getAlternativeModel(MathHelper.getPositionRandom((Vec3i)pos));
/*     */     }
/*     */     
/* 109 */     return ibakedmodel;
/*     */   }
/*     */   
/*     */   public void renderBlockBrightness(IBlockState state, float brightness) {
/* 113 */     int i = state.getBlock().getRenderType();
/*     */     
/* 115 */     if (i != -1) {
/* 116 */       switch (i) {
/*     */         default:
/*     */           return;
/*     */ 
/*     */         
/*     */         case 2:
/* 122 */           this.chestRenderer.renderChestBrightness(state.getBlock(), brightness);
/*     */         case 3:
/*     */           break;
/*     */       } 
/* 126 */       IBakedModel ibakedmodel = getBakedModel(state, null);
/* 127 */       this.blockModelRenderer.renderModelBrightness(ibakedmodel, state, brightness, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRenderTypeChest(Block p_175021_1_, int p_175021_2_) {
/* 133 */     if (p_175021_1_ == null) {
/* 134 */       return false;
/*     */     }
/* 136 */     int i = p_175021_1_.getRenderType();
/* 137 */     return (i == 3) ? false : ((i == 2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 142 */     this.fluidRenderer.initAtlasSprites();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\BlockRendererDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */