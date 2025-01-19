/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.optifine.BetterSnow;
/*     */ import net.optifine.CustomColors;
/*     */ import net.optifine.model.BlockModelCustomizer;
/*     */ import net.optifine.model.ListQuadsOverlay;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.render.RenderEnv;
/*     */ import net.optifine.shaders.SVertexBuilder;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public class BlockModelRenderer
/*     */ {
/*  33 */   private static float aoLightValueOpaque = 0.2F;
/*     */   private static boolean separateAoLightValue = false;
/*  35 */   private static final EnumWorldBlockLayer[] OVERLAY_LAYERS = new EnumWorldBlockLayer[] { EnumWorldBlockLayer.CUTOUT, EnumWorldBlockLayer.CUTOUT_MIPPED, EnumWorldBlockLayer.TRANSLUCENT };
/*     */   
/*     */   public BlockModelRenderer() {
/*  38 */     if (Reflector.ForgeModContainer_forgeLightPipelineEnabled.exists()) {
/*  39 */       Reflector.setFieldValue(Reflector.ForgeModContainer_forgeLightPipelineEnabled, Boolean.valueOf(false));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn) {
/*  44 */     Block block = blockStateIn.getBlock();
/*  45 */     block.setBlockBoundsBasedOnState(blockAccessIn, blockPosIn);
/*  46 */     return renderModel(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, true);
/*     */   }
/*     */   
/*     */   public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides) {
/*  50 */     boolean flag = (Minecraft.isAmbientOcclusionEnabled() && blockStateIn.getBlock().getLightValue() == 0 && modelIn.isAmbientOcclusion());
/*     */     
/*     */     try {
/*  53 */       if (Config.isShaders()) {
/*  54 */         SVertexBuilder.pushEntity(blockStateIn, blockPosIn, blockAccessIn, worldRendererIn);
/*     */       }
/*     */       
/*  57 */       RenderEnv renderenv = worldRendererIn.getRenderEnv(blockStateIn, blockPosIn);
/*  58 */       modelIn = BlockModelCustomizer.getRenderModel(modelIn, blockStateIn, renderenv);
/*  59 */       boolean flag1 = flag ? renderModelSmooth(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, checkSides) : renderModelFlat(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, checkSides);
/*     */       
/*  61 */       if (flag1) {
/*  62 */         renderOverlayModels(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, checkSides, 0L, renderenv, flag);
/*     */       }
/*     */       
/*  65 */       if (Config.isShaders()) {
/*  66 */         SVertexBuilder.popEntity(worldRendererIn);
/*     */       }
/*     */       
/*  69 */       return flag1;
/*  70 */     } catch (Throwable throwable) {
/*  71 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating block model");
/*  72 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Block model being tesselated");
/*  73 */       CrashReportCategory.addBlockInfo(crashreportcategory, blockPosIn, blockStateIn);
/*  74 */       crashreportcategory.addCrashSection("Using AO", Boolean.valueOf(flag));
/*  75 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean renderModelAmbientOcclusion(IBlockAccess blockAccessIn, IBakedModel modelIn, Block blockIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides) {
/*  80 */     IBlockState iblockstate = blockAccessIn.getBlockState(blockPosIn);
/*  81 */     return renderModelSmooth(blockAccessIn, modelIn, iblockstate, blockPosIn, worldRendererIn, checkSides);
/*     */   }
/*     */   
/*     */   private boolean renderModelSmooth(IBlockAccess p_renderModelSmooth_1_, IBakedModel p_renderModelSmooth_2_, IBlockState p_renderModelSmooth_3_, BlockPos p_renderModelSmooth_4_, WorldRenderer p_renderModelSmooth_5_, boolean p_renderModelSmooth_6_) {
/*  85 */     boolean flag = false;
/*  86 */     Block block = p_renderModelSmooth_3_.getBlock();
/*  87 */     RenderEnv renderenv = p_renderModelSmooth_5_.getRenderEnv(p_renderModelSmooth_3_, p_renderModelSmooth_4_);
/*  88 */     EnumWorldBlockLayer enumworldblocklayer = p_renderModelSmooth_5_.getBlockLayer(); byte b; int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/*  90 */     for (i = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*  91 */       List<BakedQuad> list = p_renderModelSmooth_2_.getFaceQuads(enumfacing);
/*     */       
/*  93 */       if (!list.isEmpty()) {
/*  94 */         BlockPos blockpos = p_renderModelSmooth_4_.offset(enumfacing);
/*     */         
/*  96 */         if (!p_renderModelSmooth_6_ || block.shouldSideBeRendered(p_renderModelSmooth_1_, blockpos, enumfacing)) {
/*  97 */           list = BlockModelCustomizer.getRenderQuads(list, p_renderModelSmooth_1_, p_renderModelSmooth_3_, p_renderModelSmooth_4_, enumfacing, enumworldblocklayer, 0L, renderenv);
/*  98 */           renderQuadsSmooth(p_renderModelSmooth_1_, p_renderModelSmooth_3_, p_renderModelSmooth_4_, p_renderModelSmooth_5_, list, renderenv);
/*  99 */           flag = true;
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */     
/* 104 */     List<BakedQuad> list1 = p_renderModelSmooth_2_.getGeneralQuads();
/*     */     
/* 106 */     if (list1.size() > 0) {
/* 107 */       list1 = BlockModelCustomizer.getRenderQuads(list1, p_renderModelSmooth_1_, p_renderModelSmooth_3_, p_renderModelSmooth_4_, null, enumworldblocklayer, 0L, renderenv);
/* 108 */       renderQuadsSmooth(p_renderModelSmooth_1_, p_renderModelSmooth_3_, p_renderModelSmooth_4_, p_renderModelSmooth_5_, list1, renderenv);
/* 109 */       flag = true;
/*     */     } 
/*     */     
/* 112 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean renderModelStandard(IBlockAccess blockAccessIn, IBakedModel modelIn, Block blockIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides) {
/* 116 */     IBlockState iblockstate = blockAccessIn.getBlockState(blockPosIn);
/* 117 */     return renderModelFlat(blockAccessIn, modelIn, iblockstate, blockPosIn, worldRendererIn, checkSides);
/*     */   }
/*     */   
/*     */   public boolean renderModelFlat(IBlockAccess p_renderModelFlat_1_, IBakedModel p_renderModelFlat_2_, IBlockState p_renderModelFlat_3_, BlockPos p_renderModelFlat_4_, WorldRenderer p_renderModelFlat_5_, boolean p_renderModelFlat_6_) {
/* 121 */     boolean flag = false;
/* 122 */     Block block = p_renderModelFlat_3_.getBlock();
/* 123 */     RenderEnv renderenv = p_renderModelFlat_5_.getRenderEnv(p_renderModelFlat_3_, p_renderModelFlat_4_);
/* 124 */     EnumWorldBlockLayer enumworldblocklayer = p_renderModelFlat_5_.getBlockLayer(); byte b; int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 126 */     for (i = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 127 */       List<BakedQuad> list = p_renderModelFlat_2_.getFaceQuads(enumfacing);
/*     */       
/* 129 */       if (!list.isEmpty()) {
/* 130 */         BlockPos blockpos = p_renderModelFlat_4_.offset(enumfacing);
/*     */         
/* 132 */         if (!p_renderModelFlat_6_ || block.shouldSideBeRendered(p_renderModelFlat_1_, blockpos, enumfacing)) {
/* 133 */           int j = block.getMixedBrightnessForBlock(p_renderModelFlat_1_, blockpos);
/* 134 */           list = BlockModelCustomizer.getRenderQuads(list, p_renderModelFlat_1_, p_renderModelFlat_3_, p_renderModelFlat_4_, enumfacing, enumworldblocklayer, 0L, renderenv);
/* 135 */           renderQuadsFlat(p_renderModelFlat_1_, p_renderModelFlat_3_, p_renderModelFlat_4_, enumfacing, j, false, p_renderModelFlat_5_, list, renderenv);
/* 136 */           flag = true;
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */     
/* 141 */     List<BakedQuad> list1 = p_renderModelFlat_2_.getGeneralQuads();
/*     */     
/* 143 */     if (list1.size() > 0) {
/* 144 */       list1 = BlockModelCustomizer.getRenderQuads(list1, p_renderModelFlat_1_, p_renderModelFlat_3_, p_renderModelFlat_4_, null, enumworldblocklayer, 0L, renderenv);
/* 145 */       renderQuadsFlat(p_renderModelFlat_1_, p_renderModelFlat_3_, p_renderModelFlat_4_, null, -1, true, p_renderModelFlat_5_, list1, renderenv);
/* 146 */       flag = true;
/*     */     } 
/*     */     
/* 149 */     return flag;
/*     */   }
/*     */   
/*     */   private void renderQuadsSmooth(IBlockAccess p_renderQuadsSmooth_1_, IBlockState p_renderQuadsSmooth_2_, BlockPos p_renderQuadsSmooth_3_, WorldRenderer p_renderQuadsSmooth_4_, List<BakedQuad> p_renderQuadsSmooth_5_, RenderEnv p_renderQuadsSmooth_6_) {
/* 153 */     Block block = p_renderQuadsSmooth_2_.getBlock();
/* 154 */     float[] afloat = p_renderQuadsSmooth_6_.getQuadBounds();
/* 155 */     BitSet bitset = p_renderQuadsSmooth_6_.getBoundsFlags();
/* 156 */     AmbientOcclusionFace blockmodelrenderer$ambientocclusionface = p_renderQuadsSmooth_6_.getAoFace();
/* 157 */     double d0 = p_renderQuadsSmooth_3_.getX();
/* 158 */     double d1 = p_renderQuadsSmooth_3_.getY();
/* 159 */     double d2 = p_renderQuadsSmooth_3_.getZ();
/* 160 */     Block.EnumOffsetType block$enumoffsettype = block.getOffsetType();
/*     */     
/* 162 */     if (block$enumoffsettype != Block.EnumOffsetType.NONE) {
/* 163 */       long i = MathHelper.getPositionRandom((Vec3i)p_renderQuadsSmooth_3_);
/* 164 */       d0 += (((float)(i >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 165 */       d2 += (((float)(i >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/*     */       
/* 167 */       if (block$enumoffsettype == Block.EnumOffsetType.XYZ) {
/* 168 */         d1 += (((float)(i >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D;
/*     */       }
/*     */     } 
/*     */     
/* 172 */     for (BakedQuad bakedquad : p_renderQuadsSmooth_5_) {
/* 173 */       fillQuadBounds(block, bakedquad.getVertexData(), bakedquad.getFace(), afloat, bitset);
/* 174 */       blockmodelrenderer$ambientocclusionface.updateVertexBrightness(p_renderQuadsSmooth_1_, block, p_renderQuadsSmooth_3_, bakedquad.getFace(), afloat, bitset);
/*     */       
/* 176 */       if ((bakedquad.getSprite()).isEmissive) {
/* 177 */         blockmodelrenderer$ambientocclusionface.setMaxBlockLight();
/*     */       }
/*     */       
/* 180 */       if (p_renderQuadsSmooth_4_.isMultiTexture()) {
/* 181 */         p_renderQuadsSmooth_4_.addVertexData(bakedquad.getVertexDataSingle());
/*     */       } else {
/* 183 */         p_renderQuadsSmooth_4_.addVertexData(bakedquad.getVertexData());
/*     */       } 
/*     */       
/* 186 */       p_renderQuadsSmooth_4_.putSprite(bakedquad.getSprite());
/* 187 */       p_renderQuadsSmooth_4_.putBrightness4(blockmodelrenderer$ambientocclusionface.vertexBrightness[0], blockmodelrenderer$ambientocclusionface.vertexBrightness[1], blockmodelrenderer$ambientocclusionface.vertexBrightness[2], blockmodelrenderer$ambientocclusionface.vertexBrightness[3]);
/* 188 */       int j = CustomColors.getColorMultiplier(bakedquad, p_renderQuadsSmooth_2_, p_renderQuadsSmooth_1_, p_renderQuadsSmooth_3_, p_renderQuadsSmooth_6_);
/*     */       
/* 190 */       if (!bakedquad.hasTintIndex() && j == -1) {
/* 191 */         if (separateAoLightValue) {
/* 192 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], 4);
/* 193 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], 3);
/* 194 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], 2);
/* 195 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], 1);
/*     */         } else {
/* 197 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], 4);
/* 198 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], 3);
/* 199 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], 2);
/* 200 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], 1);
/*     */         } 
/*     */       } else {
/*     */         int k;
/*     */         
/* 205 */         if (j != -1) {
/* 206 */           k = j;
/*     */         } else {
/* 208 */           k = block.colorMultiplier(p_renderQuadsSmooth_1_, p_renderQuadsSmooth_3_, bakedquad.getTintIndex());
/*     */         } 
/*     */         
/* 211 */         if (EntityRenderer.anaglyphEnable) {
/* 212 */           k = TextureUtil.anaglyphColor(k);
/*     */         }
/*     */         
/* 215 */         float f = (k >> 16 & 0xFF) / 255.0F;
/* 216 */         float f1 = (k >> 8 & 0xFF) / 255.0F;
/* 217 */         float f2 = (k & 0xFF) / 255.0F;
/*     */         
/* 219 */         if (separateAoLightValue) {
/* 220 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(f, f1, f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0], 4);
/* 221 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(f, f1, f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1], 3);
/* 222 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(f, f1, f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2], 2);
/* 223 */           p_renderQuadsSmooth_4_.putColorMultiplierRgba(f, f1, f2, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3], 1);
/*     */         } else {
/* 225 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[0] * f2, 4);
/* 226 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[1] * f2, 3);
/* 227 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[2] * f2, 2);
/* 228 */           p_renderQuadsSmooth_4_.putColorMultiplier(blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f1, blockmodelrenderer$ambientocclusionface.vertexColorMultiplier[3] * f2, 1);
/*     */         } 
/*     */       } 
/*     */       
/* 232 */       p_renderQuadsSmooth_4_.putPosition(d0, d1, d2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fillQuadBounds(Block blockIn, int[] vertexData, EnumFacing facingIn, float[] quadBounds, BitSet boundsFlags) {
/* 237 */     float f = 32.0F;
/* 238 */     float f1 = 32.0F;
/* 239 */     float f2 = 32.0F;
/* 240 */     float f3 = -32.0F;
/* 241 */     float f4 = -32.0F;
/* 242 */     float f5 = -32.0F;
/* 243 */     int i = vertexData.length / 4;
/*     */     
/* 245 */     for (int j = 0; j < 4; j++) {
/* 246 */       float f6 = Float.intBitsToFloat(vertexData[j * i]);
/* 247 */       float f7 = Float.intBitsToFloat(vertexData[j * i + 1]);
/* 248 */       float f8 = Float.intBitsToFloat(vertexData[j * i + 2]);
/* 249 */       f = Math.min(f, f6);
/* 250 */       f1 = Math.min(f1, f7);
/* 251 */       f2 = Math.min(f2, f8);
/* 252 */       f3 = Math.max(f3, f6);
/* 253 */       f4 = Math.max(f4, f7);
/* 254 */       f5 = Math.max(f5, f8);
/*     */     } 
/*     */     
/* 257 */     if (quadBounds != null) {
/* 258 */       quadBounds[EnumFacing.WEST.getIndex()] = f;
/* 259 */       quadBounds[EnumFacing.EAST.getIndex()] = f3;
/* 260 */       quadBounds[EnumFacing.DOWN.getIndex()] = f1;
/* 261 */       quadBounds[EnumFacing.UP.getIndex()] = f4;
/* 262 */       quadBounds[EnumFacing.NORTH.getIndex()] = f2;
/* 263 */       quadBounds[EnumFacing.SOUTH.getIndex()] = f5;
/* 264 */       int k = EnumFacing.VALUES.length;
/* 265 */       quadBounds[EnumFacing.WEST.getIndex() + k] = 1.0F - f;
/* 266 */       quadBounds[EnumFacing.EAST.getIndex() + k] = 1.0F - f3;
/* 267 */       quadBounds[EnumFacing.DOWN.getIndex() + k] = 1.0F - f1;
/* 268 */       quadBounds[EnumFacing.UP.getIndex() + k] = 1.0F - f4;
/* 269 */       quadBounds[EnumFacing.NORTH.getIndex() + k] = 1.0F - f2;
/* 270 */       quadBounds[EnumFacing.SOUTH.getIndex() + k] = 1.0F - f5;
/*     */     } 
/*     */     
/* 273 */     float f9 = 1.0E-4F;
/* 274 */     float f10 = 0.9999F;
/*     */     
/* 276 */     switch (facingIn) {
/*     */       case null:
/* 278 */         boundsFlags.set(1, !(f < 1.0E-4F && f2 < 1.0E-4F && f3 > 0.9999F && f5 > 0.9999F));
/* 279 */         boundsFlags.set(0, ((f1 < 1.0E-4F || blockIn.isFullCube()) && f1 == f4));
/*     */         break;
/*     */       
/*     */       case UP:
/* 283 */         boundsFlags.set(1, !(f < 1.0E-4F && f2 < 1.0E-4F && f3 > 0.9999F && f5 > 0.9999F));
/* 284 */         boundsFlags.set(0, ((f4 > 0.9999F || blockIn.isFullCube()) && f1 == f4));
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 288 */         boundsFlags.set(1, !(f < 1.0E-4F && f1 < 1.0E-4F && f3 > 0.9999F && f4 > 0.9999F));
/* 289 */         boundsFlags.set(0, ((f2 < 1.0E-4F || blockIn.isFullCube()) && f2 == f5));
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 293 */         boundsFlags.set(1, !(f < 1.0E-4F && f1 < 1.0E-4F && f3 > 0.9999F && f4 > 0.9999F));
/* 294 */         boundsFlags.set(0, ((f5 > 0.9999F || blockIn.isFullCube()) && f2 == f5));
/*     */         break;
/*     */       
/*     */       case WEST:
/* 298 */         boundsFlags.set(1, !(f1 < 1.0E-4F && f2 < 1.0E-4F && f4 > 0.9999F && f5 > 0.9999F));
/* 299 */         boundsFlags.set(0, ((f < 1.0E-4F || blockIn.isFullCube()) && f == f3));
/*     */         break;
/*     */       
/*     */       case EAST:
/* 303 */         boundsFlags.set(1, !(f1 < 1.0E-4F && f2 < 1.0E-4F && f4 > 0.9999F && f5 > 0.9999F));
/* 304 */         boundsFlags.set(0, ((f3 > 0.9999F || blockIn.isFullCube()) && f == f3));
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/* 309 */   private void renderQuadsFlat(IBlockAccess p_renderQuadsFlat_1_, IBlockState p_renderQuadsFlat_2_, BlockPos p_renderQuadsFlat_3_, EnumFacing p_renderQuadsFlat_4_, int p_renderQuadsFlat_5_, boolean p_renderQuadsFlat_6_, WorldRenderer p_renderQuadsFlat_7_, List<BakedQuad> p_renderQuadsFlat_8_, RenderEnv p_renderQuadsFlat_9_) { Block block = p_renderQuadsFlat_2_.getBlock();
/* 310 */     BitSet bitset = p_renderQuadsFlat_9_.getBoundsFlags();
/* 311 */     double d0 = p_renderQuadsFlat_3_.getX();
/* 312 */     double d1 = p_renderQuadsFlat_3_.getY();
/* 313 */     double d2 = p_renderQuadsFlat_3_.getZ();
/* 314 */     Block.EnumOffsetType block$enumoffsettype = block.getOffsetType();
/*     */     
/* 316 */     if (block$enumoffsettype != Block.EnumOffsetType.NONE) {
/* 317 */       int i = p_renderQuadsFlat_3_.getX();
/* 318 */       int j = p_renderQuadsFlat_3_.getZ();
/* 319 */       long k = (i * 3129871) ^ j * 116129781L;
/* 320 */       k = k * k * 42317861L + k * 11L;
/* 321 */       d0 += (((float)(k >> 16L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/* 322 */       d2 += (((float)(k >> 24L & 0xFL) / 15.0F) - 0.5D) * 0.5D;
/*     */       
/* 324 */       if (block$enumoffsettype == Block.EnumOffsetType.XYZ) {
/* 325 */         d1 += (((float)(k >> 20L & 0xFL) / 15.0F) - 1.0D) * 0.2D;
/*     */       }
/*     */     } 
/*     */     
/* 329 */     for (BakedQuad bakedquad : p_renderQuadsFlat_8_) {
/* 330 */       if (p_renderQuadsFlat_6_) {
/* 331 */         fillQuadBounds(block, bakedquad.getVertexData(), bakedquad.getFace(), null, bitset);
/* 332 */         p_renderQuadsFlat_5_ = bitset.get(0) ? block.getMixedBrightnessForBlock(p_renderQuadsFlat_1_, p_renderQuadsFlat_3_.offset(bakedquad.getFace())) : block.getMixedBrightnessForBlock(p_renderQuadsFlat_1_, p_renderQuadsFlat_3_);
/*     */       } 
/*     */       
/* 335 */       if ((bakedquad.getSprite()).isEmissive) {
/* 336 */         p_renderQuadsFlat_5_ |= 0xF0;
/*     */       }
/*     */       
/* 339 */       if (p_renderQuadsFlat_7_.isMultiTexture()) {
/* 340 */         p_renderQuadsFlat_7_.addVertexData(bakedquad.getVertexDataSingle());
/*     */       } else {
/* 342 */         p_renderQuadsFlat_7_.addVertexData(bakedquad.getVertexData());
/*     */       } 
/*     */       
/* 345 */       p_renderQuadsFlat_7_.putSprite(bakedquad.getSprite());
/* 346 */       p_renderQuadsFlat_7_.putBrightness4(p_renderQuadsFlat_5_, p_renderQuadsFlat_5_, p_renderQuadsFlat_5_, p_renderQuadsFlat_5_);
/* 347 */       int i1 = CustomColors.getColorMultiplier(bakedquad, p_renderQuadsFlat_2_, p_renderQuadsFlat_1_, p_renderQuadsFlat_3_, p_renderQuadsFlat_9_);
/*     */       
/* 349 */       if (bakedquad.hasTintIndex() || i1 != -1) {
/*     */         int l;
/*     */         
/* 352 */         if (i1 != -1) {
/* 353 */           l = i1;
/*     */         } else {
/* 355 */           l = block.colorMultiplier(p_renderQuadsFlat_1_, p_renderQuadsFlat_3_, bakedquad.getTintIndex());
/*     */         } 
/*     */         
/* 358 */         if (EntityRenderer.anaglyphEnable) {
/* 359 */           l = TextureUtil.anaglyphColor(l);
/*     */         }
/*     */         
/* 362 */         float f = (l >> 16 & 0xFF) / 255.0F;
/* 363 */         float f1 = (l >> 8 & 0xFF) / 255.0F;
/* 364 */         float f2 = (l & 0xFF) / 255.0F;
/* 365 */         p_renderQuadsFlat_7_.putColorMultiplier(f, f1, f2, 4);
/* 366 */         p_renderQuadsFlat_7_.putColorMultiplier(f, f1, f2, 3);
/* 367 */         p_renderQuadsFlat_7_.putColorMultiplier(f, f1, f2, 2);
/* 368 */         p_renderQuadsFlat_7_.putColorMultiplier(f, f1, f2, 1);
/*     */       } 
/*     */       
/* 371 */       p_renderQuadsFlat_7_.putPosition(d0, d1, d2);
/*     */     }  } public void renderModelBrightnessColor(IBakedModel bakedModel, float p_178262_2_, float red, float green, float blue) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 376 */     for (i = (arrayOfEnumFacing = EnumFacing.VALUES).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 377 */       renderModelBrightnessColorQuads(p_178262_2_, red, green, blue, bakedModel.getFaceQuads(enumfacing));
/*     */       b++; }
/*     */     
/* 380 */     renderModelBrightnessColorQuads(p_178262_2_, red, green, blue, bakedModel.getGeneralQuads());
/*     */   }
/*     */   
/*     */   public void renderModelBrightness(IBakedModel model, IBlockState p_178266_2_, float brightness, boolean p_178266_4_) {
/* 384 */     Block block = p_178266_2_.getBlock();
/* 385 */     block.setBlockBoundsForItemRender();
/* 386 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 387 */     int i = block.getRenderColor(block.getStateForEntityRender(p_178266_2_));
/*     */     
/* 389 */     if (EntityRenderer.anaglyphEnable) {
/* 390 */       i = TextureUtil.anaglyphColor(i);
/*     */     }
/*     */     
/* 393 */     float f = (i >> 16 & 0xFF) / 255.0F;
/* 394 */     float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 395 */     float f2 = (i & 0xFF) / 255.0F;
/*     */     
/* 397 */     if (!p_178266_4_) {
/* 398 */       GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */     }
/*     */     
/* 401 */     renderModelBrightnessColor(model, brightness, f, f1, f2);
/*     */   }
/*     */   
/*     */   private void renderModelBrightnessColorQuads(float brightness, float red, float green, float blue, List<BakedQuad> listQuads) {
/* 405 */     Tessellator tessellator = Tessellator.getInstance();
/* 406 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */     
/* 408 */     for (BakedQuad bakedquad : listQuads) {
/* 409 */       worldrenderer.begin(7, DefaultVertexFormats.ITEM);
/* 410 */       worldrenderer.addVertexData(bakedquad.getVertexData());
/* 411 */       worldrenderer.putSprite(bakedquad.getSprite());
/*     */       
/* 413 */       if (bakedquad.hasTintIndex()) {
/* 414 */         worldrenderer.putColorRGB_F4(red * brightness, green * brightness, blue * brightness);
/*     */       } else {
/* 416 */         worldrenderer.putColorRGB_F4(brightness, brightness, brightness);
/*     */       } 
/*     */       
/* 419 */       Vec3i vec3i = bakedquad.getFace().getDirectionVec();
/* 420 */       worldrenderer.putNormal(vec3i.getX(), vec3i.getY(), vec3i.getZ());
/* 421 */       tessellator.draw();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float fixAoLightValue(float p_fixAoLightValue_0_) {
/* 426 */     return (p_fixAoLightValue_0_ == 0.2F) ? aoLightValueOpaque : p_fixAoLightValue_0_;
/*     */   }
/*     */   
/*     */   public static void updateAoLightValue() {
/* 430 */     aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
/* 431 */     separateAoLightValue = (Config.isShaders() && Shaders.isSeparateAo());
/*     */   }
/*     */   
/*     */   private void renderOverlayModels(IBlockAccess p_renderOverlayModels_1_, IBakedModel p_renderOverlayModels_2_, IBlockState p_renderOverlayModels_3_, BlockPos p_renderOverlayModels_4_, WorldRenderer p_renderOverlayModels_5_, boolean p_renderOverlayModels_6_, long p_renderOverlayModels_7_, RenderEnv p_renderOverlayModels_9_, boolean p_renderOverlayModels_10_) {
/* 435 */     if (p_renderOverlayModels_9_.isOverlaysRendered()) {
/* 436 */       for (int i = 0; i < OVERLAY_LAYERS.length; i++) {
/* 437 */         EnumWorldBlockLayer enumworldblocklayer = OVERLAY_LAYERS[i];
/* 438 */         ListQuadsOverlay listquadsoverlay = p_renderOverlayModels_9_.getListQuadsOverlay(enumworldblocklayer);
/*     */         
/* 440 */         if (listquadsoverlay.size() > 0) {
/* 441 */           RegionRenderCacheBuilder regionrendercachebuilder = p_renderOverlayModels_9_.getRegionRenderCacheBuilder();
/*     */           
/* 443 */           if (regionrendercachebuilder != null) {
/* 444 */             WorldRenderer worldrenderer = regionrendercachebuilder.getWorldRendererByLayer(enumworldblocklayer);
/*     */             
/* 446 */             if (!worldrenderer.isDrawing()) {
/* 447 */               worldrenderer.begin(7, DefaultVertexFormats.BLOCK);
/* 448 */               worldrenderer.setTranslation(p_renderOverlayModels_5_.getXOffset(), p_renderOverlayModels_5_.getYOffset(), p_renderOverlayModels_5_.getZOffset());
/*     */             } 
/*     */             
/* 451 */             for (int j = 0; j < listquadsoverlay.size(); j++) {
/* 452 */               BakedQuad bakedquad = listquadsoverlay.getQuad(j);
/* 453 */               List<BakedQuad> list = listquadsoverlay.getListQuadsSingle(bakedquad);
/* 454 */               IBlockState iblockstate = listquadsoverlay.getBlockState(j);
/*     */               
/* 456 */               if (bakedquad.getQuadEmissive() != null) {
/* 457 */                 listquadsoverlay.addQuad(bakedquad.getQuadEmissive(), iblockstate);
/*     */               }
/*     */               
/* 460 */               p_renderOverlayModels_9_.reset(iblockstate, p_renderOverlayModels_4_);
/*     */               
/* 462 */               if (p_renderOverlayModels_10_) {
/* 463 */                 renderQuadsSmooth(p_renderOverlayModels_1_, iblockstate, p_renderOverlayModels_4_, worldrenderer, list, p_renderOverlayModels_9_);
/*     */               } else {
/* 465 */                 int k = iblockstate.getBlock().getMixedBrightnessForBlock(p_renderOverlayModels_1_, p_renderOverlayModels_4_.offset(bakedquad.getFace()));
/* 466 */                 renderQuadsFlat(p_renderOverlayModels_1_, iblockstate, p_renderOverlayModels_4_, bakedquad.getFace(), k, false, worldrenderer, list, p_renderOverlayModels_9_);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 471 */           listquadsoverlay.clear();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 476 */     if (Config.isBetterSnow() && !p_renderOverlayModels_9_.isBreakingAnimation() && BetterSnow.shouldRender(p_renderOverlayModels_1_, p_renderOverlayModels_3_, p_renderOverlayModels_4_)) {
/* 477 */       IBakedModel ibakedmodel = BetterSnow.getModelSnowLayer();
/* 478 */       IBlockState iblockstate1 = BetterSnow.getStateSnowLayer();
/* 479 */       renderModel(p_renderOverlayModels_1_, ibakedmodel, iblockstate1, p_renderOverlayModels_4_, p_renderOverlayModels_5_, p_renderOverlayModels_6_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AmbientOcclusionFace
/*     */   {
/*     */     public AmbientOcclusionFace() {
/* 488 */       this(null);
/*     */     }
/*     */ 
/*     */     
/* 492 */     private final float[] vertexColorMultiplier = new float[4];
/* 493 */     private final int[] vertexBrightness = new int[4];
/*     */     
/*     */     public void setMaxBlockLight()
/*     */     {
/* 497 */       int i = 240;
/* 498 */       this.vertexBrightness[0] = this.vertexBrightness[0] | i;
/* 499 */       this.vertexBrightness[1] = this.vertexBrightness[1] | i;
/* 500 */       this.vertexBrightness[2] = this.vertexBrightness[2] | i;
/* 501 */       this.vertexBrightness[3] = this.vertexBrightness[3] | i;
/* 502 */       this.vertexColorMultiplier[0] = 1.0F;
/* 503 */       this.vertexColorMultiplier[1] = 1.0F;
/* 504 */       this.vertexColorMultiplier[2] = 1.0F;
/* 505 */       this.vertexColorMultiplier[3] = 1.0F; } public void updateVertexBrightness(IBlockAccess blockAccessIn, Block blockIn, BlockPos blockPosIn, EnumFacing facingIn, float[] quadBounds, BitSet boundsFlags) { float f4; int i1, j1; float f26; int k1;
/*     */       float f27;
/*     */       int l1;
/*     */       float f28;
/* 509 */       BlockPos blockpos = boundsFlags.get(0) ? blockPosIn.offset(facingIn) : blockPosIn;
/* 510 */       BlockModelRenderer.EnumNeighborInfo blockmodelrenderer$enumneighborinfo = BlockModelRenderer.EnumNeighborInfo.getNeighbourInfo(facingIn);
/* 511 */       BlockPos blockpos1 = blockpos.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[0]);
/* 512 */       BlockPos blockpos2 = blockpos.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[1]);
/* 513 */       BlockPos blockpos3 = blockpos.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[2]);
/* 514 */       BlockPos blockpos4 = blockpos.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[3]);
/* 515 */       int i = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos1);
/* 516 */       int j = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos2);
/* 517 */       int k = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos3);
/* 518 */       int l = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos4);
/* 519 */       float f = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos1).getBlock().getAmbientOcclusionLightValue());
/* 520 */       float f1 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos2).getBlock().getAmbientOcclusionLightValue());
/* 521 */       float f2 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos3).getBlock().getAmbientOcclusionLightValue());
/* 522 */       float f3 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos4).getBlock().getAmbientOcclusionLightValue());
/* 523 */       boolean flag = blockAccessIn.getBlockState(blockpos1.offset(facingIn)).getBlock().isTranslucent();
/* 524 */       boolean flag1 = blockAccessIn.getBlockState(blockpos2.offset(facingIn)).getBlock().isTranslucent();
/* 525 */       boolean flag2 = blockAccessIn.getBlockState(blockpos3.offset(facingIn)).getBlock().isTranslucent();
/* 526 */       boolean flag3 = blockAccessIn.getBlockState(blockpos4.offset(facingIn)).getBlock().isTranslucent();
/*     */ 
/*     */ 
/*     */       
/* 530 */       if (!flag2 && !flag) {
/* 531 */         f4 = f;
/* 532 */         i1 = i;
/*     */       } else {
/* 534 */         BlockPos blockpos5 = blockpos1.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[2]);
/* 535 */         f4 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos5).getBlock().getAmbientOcclusionLightValue());
/* 536 */         i1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos5);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 542 */       if (!flag3 && !flag) {
/* 543 */         f26 = f;
/* 544 */         j1 = i;
/*     */       } else {
/* 546 */         BlockPos blockpos6 = blockpos1.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[3]);
/* 547 */         f26 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos6).getBlock().getAmbientOcclusionLightValue());
/* 548 */         j1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos6);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 554 */       if (!flag2 && !flag1) {
/* 555 */         f27 = f1;
/* 556 */         k1 = j;
/*     */       } else {
/* 558 */         BlockPos blockpos7 = blockpos2.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[2]);
/* 559 */         f27 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos7).getBlock().getAmbientOcclusionLightValue());
/* 560 */         k1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos7);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 566 */       if (!flag3 && !flag1) {
/* 567 */         f28 = f1;
/* 568 */         l1 = j;
/*     */       } else {
/* 570 */         BlockPos blockpos8 = blockpos2.offset(blockmodelrenderer$enumneighborinfo.field_178276_g[3]);
/* 571 */         f28 = BlockModelRenderer.fixAoLightValue(blockAccessIn.getBlockState(blockpos8).getBlock().getAmbientOcclusionLightValue());
/* 572 */         l1 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockpos8);
/*     */       } 
/*     */       
/* 575 */       int i3 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn);
/*     */       
/* 577 */       if (boundsFlags.get(0) || !blockAccessIn.getBlockState(blockPosIn.offset(facingIn)).getBlock().isOpaqueCube()) {
/* 578 */         i3 = blockIn.getMixedBrightnessForBlock(blockAccessIn, blockPosIn.offset(facingIn));
/*     */       }
/*     */       
/* 581 */       float f5 = boundsFlags.get(0) ? blockAccessIn.getBlockState(blockpos).getBlock().getAmbientOcclusionLightValue() : blockAccessIn.getBlockState(blockPosIn).getBlock().getAmbientOcclusionLightValue();
/* 582 */       f5 = BlockModelRenderer.fixAoLightValue(f5);
/* 583 */       BlockModelRenderer.VertexTranslations blockmodelrenderer$vertextranslations = BlockModelRenderer.VertexTranslations.getVertexTranslations(facingIn);
/*     */       
/* 585 */       if (boundsFlags.get(1) && blockmodelrenderer$enumneighborinfo.field_178289_i) {
/* 586 */         float f29 = (f3 + f + f26 + f5) * 0.25F;
/* 587 */         float f30 = (f2 + f + f4 + f5) * 0.25F;
/* 588 */         float f31 = (f2 + f1 + f27 + f5) * 0.25F;
/* 589 */         float f32 = (f3 + f1 + f28 + f5) * 0.25F;
/* 590 */         float f10 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[0]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[1]).field_178229_m];
/* 591 */         float f11 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[2]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[3]).field_178229_m];
/* 592 */         float f12 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[4]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[5]).field_178229_m];
/* 593 */         float f13 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[6]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178286_j[7]).field_178229_m];
/* 594 */         float f14 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[0]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[1]).field_178229_m];
/* 595 */         float f15 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[2]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[3]).field_178229_m];
/* 596 */         float f16 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[4]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[5]).field_178229_m];
/* 597 */         float f17 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[6]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178287_k[7]).field_178229_m];
/* 598 */         float f18 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[0]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[1]).field_178229_m];
/* 599 */         float f19 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[2]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[3]).field_178229_m];
/* 600 */         float f20 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[4]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[5]).field_178229_m];
/* 601 */         float f21 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[6]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178284_l[7]).field_178229_m];
/* 602 */         float f22 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[0]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[1]).field_178229_m];
/* 603 */         float f23 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[2]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[3]).field_178229_m];
/* 604 */         float f24 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[4]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[5]).field_178229_m];
/* 605 */         float f25 = quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[6]).field_178229_m] * quadBounds[(blockmodelrenderer$enumneighborinfo.field_178285_m[7]).field_178229_m];
/* 606 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178191_g] = f29 * f10 + f30 * f11 + f31 * f12 + f32 * f13;
/* 607 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178200_h] = f29 * f14 + f30 * f15 + f31 * f16 + f32 * f17;
/* 608 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178201_i] = f29 * f18 + f30 * f19 + f31 * f20 + f32 * f21;
/* 609 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178198_j] = f29 * f22 + f30 * f23 + f31 * f24 + f32 * f25;
/* 610 */         int i2 = getAoBrightness(l, i, j1, i3);
/* 611 */         int j2 = getAoBrightness(k, i, i1, i3);
/* 612 */         int k2 = getAoBrightness(k, j, k1, i3);
/* 613 */         int l2 = getAoBrightness(l, j, l1, i3);
/* 614 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178191_g] = getVertexBrightness(i2, j2, k2, l2, f10, f11, f12, f13);
/* 615 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178200_h] = getVertexBrightness(i2, j2, k2, l2, f14, f15, f16, f17);
/* 616 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178201_i] = getVertexBrightness(i2, j2, k2, l2, f18, f19, f20, f21);
/* 617 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178198_j] = getVertexBrightness(i2, j2, k2, l2, f22, f23, f24, f25);
/*     */       } else {
/* 619 */         float f6 = (f3 + f + f26 + f5) * 0.25F;
/* 620 */         float f7 = (f2 + f + f4 + f5) * 0.25F;
/* 621 */         float f8 = (f2 + f1 + f27 + f5) * 0.25F;
/* 622 */         float f9 = (f3 + f1 + f28 + f5) * 0.25F;
/* 623 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178191_g] = getAoBrightness(l, i, j1, i3);
/* 624 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178200_h] = getAoBrightness(k, i, i1, i3);
/* 625 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178201_i] = getAoBrightness(k, j, k1, i3);
/* 626 */         this.vertexBrightness[blockmodelrenderer$vertextranslations.field_178198_j] = getAoBrightness(l, j, l1, i3);
/* 627 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178191_g] = f6;
/* 628 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178200_h] = f7;
/* 629 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178201_i] = f8;
/* 630 */         this.vertexColorMultiplier[blockmodelrenderer$vertextranslations.field_178198_j] = f9;
/*     */       }  }
/*     */ 
/*     */     
/*     */     private int getAoBrightness(int br1, int br2, int br3, int br4) {
/* 635 */       if (br1 == 0) {
/* 636 */         br1 = br4;
/*     */       }
/*     */       
/* 639 */       if (br2 == 0) {
/* 640 */         br2 = br4;
/*     */       }
/*     */       
/* 643 */       if (br3 == 0) {
/* 644 */         br3 = br4;
/*     */       }
/*     */       
/* 647 */       return br1 + br2 + br3 + br4 >> 2 & 0xFF00FF;
/*     */     }
/*     */     
/*     */     private int getVertexBrightness(int p_178203_1_, int p_178203_2_, int p_178203_3_, int p_178203_4_, float p_178203_5_, float p_178203_6_, float p_178203_7_, float p_178203_8_) {
/* 651 */       int i = (int)((p_178203_1_ >> 16 & 0xFF) * p_178203_5_ + (p_178203_2_ >> 16 & 0xFF) * p_178203_6_ + (p_178203_3_ >> 16 & 0xFF) * p_178203_7_ + (p_178203_4_ >> 16 & 0xFF) * p_178203_8_) & 0xFF;
/* 652 */       int j = (int)((p_178203_1_ & 0xFF) * p_178203_5_ + (p_178203_2_ & 0xFF) * p_178203_6_ + (p_178203_3_ & 0xFF) * p_178203_7_ + (p_178203_4_ & 0xFF) * p_178203_8_) & 0xFF;
/* 653 */       return i << 16 | j;
/*     */     }
/*     */     
/*     */     public AmbientOcclusionFace(BlockModelRenderer p_i46235_1_) {} }
/*     */   
/* 658 */   public enum EnumNeighborInfo { DOWN((String)new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.5F, false, new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0]),
/* 659 */     UP((String)new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH }, 1.0F, false, new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0], new BlockModelRenderer.Orientation[0]),
/* 660 */     NORTH((String)new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST }, 0.8F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST }),
/* 661 */     SOUTH((String)new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP }, 0.8F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.WEST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_WEST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.WEST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.WEST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.EAST }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_EAST, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.EAST, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.EAST }),
/* 662 */     WEST((String)new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.6F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH }),
/* 663 */     EAST((String)new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH }, 0.6F, true, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.SOUTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.DOWN, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.NORTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_NORTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.NORTH }, new BlockModelRenderer.Orientation[] { BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.SOUTH, BlockModelRenderer.Orientation.FLIP_UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.FLIP_SOUTH, BlockModelRenderer.Orientation.UP, BlockModelRenderer.Orientation.SOUTH });
/*     */     
/*     */     protected final EnumFacing[] field_178276_g;
/*     */     protected final float field_178288_h;
/*     */     protected final boolean field_178289_i;
/*     */     protected final BlockModelRenderer.Orientation[] field_178286_j;
/*     */     protected final BlockModelRenderer.Orientation[] field_178287_k;
/*     */     protected final BlockModelRenderer.Orientation[] field_178284_l;
/*     */     protected final BlockModelRenderer.Orientation[] field_178285_m;
/* 672 */     private static final EnumNeighborInfo[] VALUES = new EnumNeighborInfo[6];
/*     */ 
/*     */ 
/*     */     
/*     */     EnumNeighborInfo(EnumFacing[] p_i46236_3_, float p_i46236_4_, boolean p_i46236_5_, BlockModelRenderer.Orientation[] p_i46236_6_, BlockModelRenderer.Orientation[] p_i46236_7_, BlockModelRenderer.Orientation[] p_i46236_8_, BlockModelRenderer.Orientation[] p_i46236_9_) {
/*     */       this.field_178276_g = p_i46236_3_;
/*     */       this.field_178288_h = p_i46236_4_;
/*     */       this.field_178289_i = p_i46236_5_;
/*     */       this.field_178286_j = p_i46236_6_;
/*     */       this.field_178287_k = p_i46236_7_;
/*     */       this.field_178284_l = p_i46236_8_;
/*     */       this.field_178285_m = p_i46236_9_;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 689 */       VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
/* 690 */       VALUES[EnumFacing.UP.getIndex()] = UP;
/* 691 */       VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
/* 692 */       VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
/* 693 */       VALUES[EnumFacing.WEST.getIndex()] = WEST;
/* 694 */       VALUES[EnumFacing.EAST.getIndex()] = EAST;
/*     */     }
/*     */     public static EnumNeighborInfo getNeighbourInfo(EnumFacing p_178273_0_) {
/*     */       return VALUES[p_178273_0_.getIndex()];
/*     */     } }
/* 699 */   public enum Orientation { DOWN((String)EnumFacing.DOWN, false),
/* 700 */     UP((String)EnumFacing.UP, false),
/* 701 */     NORTH((String)EnumFacing.NORTH, false),
/* 702 */     SOUTH((String)EnumFacing.SOUTH, false),
/* 703 */     WEST((String)EnumFacing.WEST, false),
/* 704 */     EAST((String)EnumFacing.EAST, false),
/* 705 */     FLIP_DOWN((String)EnumFacing.DOWN, true),
/* 706 */     FLIP_UP((String)EnumFacing.UP, true),
/* 707 */     FLIP_NORTH((String)EnumFacing.NORTH, true),
/* 708 */     FLIP_SOUTH((String)EnumFacing.SOUTH, true),
/* 709 */     FLIP_WEST((String)EnumFacing.WEST, true),
/* 710 */     FLIP_EAST((String)EnumFacing.EAST, true);
/*     */     
/*     */     protected final int field_178229_m;
/*     */     
/*     */     Orientation(EnumFacing p_i46233_3_, boolean p_i46233_4_) {
/* 715 */       this.field_178229_m = p_i46233_3_.getIndex() + (p_i46233_4_ ? (EnumFacing.values()).length : 0);
/*     */     } }
/*     */ 
/*     */   
/*     */   enum VertexTranslations {
/* 720 */     DOWN(0, 1, 2, 3),
/* 721 */     UP(2, 3, 0, 1),
/* 722 */     NORTH(3, 0, 1, 2),
/* 723 */     SOUTH(0, 1, 2, 3),
/* 724 */     WEST(3, 0, 1, 2),
/* 725 */     EAST(1, 2, 3, 0);
/*     */     
/*     */     private final int field_178191_g;
/*     */     private final int field_178200_h;
/*     */     private final int field_178201_i;
/*     */     private final int field_178198_j;
/* 731 */     private static final VertexTranslations[] VALUES = new VertexTranslations[6];
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
/*     */     static {
/* 745 */       VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
/* 746 */       VALUES[EnumFacing.UP.getIndex()] = UP;
/* 747 */       VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
/* 748 */       VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
/* 749 */       VALUES[EnumFacing.WEST.getIndex()] = WEST;
/* 750 */       VALUES[EnumFacing.EAST.getIndex()] = EAST;
/*     */     }
/*     */     
/*     */     VertexTranslations(int p_i46234_3_, int p_i46234_4_, int p_i46234_5_, int p_i46234_6_) {
/*     */       this.field_178191_g = p_i46234_3_;
/*     */       this.field_178200_h = p_i46234_4_;
/*     */       this.field_178201_i = p_i46234_5_;
/*     */       this.field_178198_j = p_i46234_6_;
/*     */     }
/*     */     
/*     */     public static VertexTranslations getVertexTranslations(EnumFacing p_178184_0_) {
/*     */       return VALUES[p_178184_0_.getIndex()];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\BlockModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */