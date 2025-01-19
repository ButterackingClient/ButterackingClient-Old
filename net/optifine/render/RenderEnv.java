/*     */ package net.optifine.render;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.BlockModelRenderer;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.optifine.BlockPosM;
/*     */ import net.optifine.model.ListQuadsOverlay;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderEnv
/*     */ {
/*     */   private IBlockState blockState;
/*     */   private BlockPos blockPos;
/*  25 */   private int blockId = -1;
/*  26 */   private int metadata = -1;
/*  27 */   private int breakingAnimation = -1;
/*  28 */   private int smartLeaves = -1;
/*  29 */   private float[] quadBounds = new float[EnumFacing.VALUES.length * 2];
/*  30 */   private BitSet boundsFlags = new BitSet(3);
/*  31 */   private BlockModelRenderer.AmbientOcclusionFace aoFace = new BlockModelRenderer.AmbientOcclusionFace();
/*  32 */   private BlockPosM colorizerBlockPosM = null;
/*  33 */   private boolean[] borderFlags = null;
/*  34 */   private boolean[] borderFlags2 = null;
/*  35 */   private boolean[] borderFlags3 = null;
/*  36 */   private EnumFacing[] borderDirections = null;
/*  37 */   private List<BakedQuad> listQuadsCustomizer = new ArrayList<>();
/*  38 */   private List<BakedQuad> listQuadsCtmMultipass = new ArrayList<>();
/*  39 */   private BakedQuad[] arrayQuadsCtm1 = new BakedQuad[1];
/*  40 */   private BakedQuad[] arrayQuadsCtm2 = new BakedQuad[2];
/*  41 */   private BakedQuad[] arrayQuadsCtm3 = new BakedQuad[3];
/*  42 */   private BakedQuad[] arrayQuadsCtm4 = new BakedQuad[4];
/*  43 */   private RegionRenderCacheBuilder regionRenderCacheBuilder = null;
/*  44 */   private ListQuadsOverlay[] listsQuadsOverlay = new ListQuadsOverlay[(EnumWorldBlockLayer.values()).length];
/*     */   private boolean overlaysRendered = false;
/*     */   private static final int UNKNOWN = -1;
/*     */   private static final int FALSE = 0;
/*     */   private static final int TRUE = 1;
/*     */   
/*     */   public RenderEnv(IBlockState blockState, BlockPos blockPos) {
/*  51 */     this.blockState = blockState;
/*  52 */     this.blockPos = blockPos;
/*     */   }
/*     */   
/*     */   public void reset(IBlockState blockStateIn, BlockPos blockPosIn) {
/*  56 */     if (this.blockState != blockStateIn || this.blockPos != blockPosIn) {
/*  57 */       this.blockState = blockStateIn;
/*  58 */       this.blockPos = blockPosIn;
/*  59 */       this.blockId = -1;
/*  60 */       this.metadata = -1;
/*  61 */       this.breakingAnimation = -1;
/*  62 */       this.smartLeaves = -1;
/*  63 */       this.boundsFlags.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getBlockId() {
/*  68 */     if (this.blockId < 0) {
/*  69 */       if (this.blockState instanceof BlockStateBase) {
/*  70 */         BlockStateBase blockstatebase = (BlockStateBase)this.blockState;
/*  71 */         this.blockId = blockstatebase.getBlockId();
/*     */       } else {
/*  73 */         this.blockId = Block.getIdFromBlock(this.blockState.getBlock());
/*     */       } 
/*     */     }
/*     */     
/*  77 */     return this.blockId;
/*     */   }
/*     */   
/*     */   public int getMetadata() {
/*  81 */     if (this.metadata < 0) {
/*  82 */       if (this.blockState instanceof BlockStateBase) {
/*  83 */         BlockStateBase blockstatebase = (BlockStateBase)this.blockState;
/*  84 */         this.metadata = blockstatebase.getMetadata();
/*     */       } else {
/*  86 */         this.metadata = this.blockState.getBlock().getMetaFromState(this.blockState);
/*     */       } 
/*     */     }
/*     */     
/*  90 */     return this.metadata;
/*     */   }
/*     */   
/*     */   public float[] getQuadBounds() {
/*  94 */     return this.quadBounds;
/*     */   }
/*     */   
/*     */   public BitSet getBoundsFlags() {
/*  98 */     return this.boundsFlags;
/*     */   }
/*     */   
/*     */   public BlockModelRenderer.AmbientOcclusionFace getAoFace() {
/* 102 */     return this.aoFace;
/*     */   }
/*     */   
/*     */   public boolean isBreakingAnimation(List listQuads) {
/* 106 */     if (this.breakingAnimation == -1 && listQuads.size() > 0) {
/* 107 */       if (listQuads.get(0) instanceof net.minecraft.client.renderer.block.model.BreakingFour) {
/* 108 */         this.breakingAnimation = 1;
/*     */       } else {
/* 110 */         this.breakingAnimation = 0;
/*     */       } 
/*     */     }
/*     */     
/* 114 */     return (this.breakingAnimation == 1);
/*     */   }
/*     */   
/*     */   public boolean isBreakingAnimation(BakedQuad quad) {
/* 118 */     if (this.breakingAnimation < 0) {
/* 119 */       if (quad instanceof net.minecraft.client.renderer.block.model.BreakingFour) {
/* 120 */         this.breakingAnimation = 1;
/*     */       } else {
/* 122 */         this.breakingAnimation = 0;
/*     */       } 
/*     */     }
/*     */     
/* 126 */     return (this.breakingAnimation == 1);
/*     */   }
/*     */   
/*     */   public boolean isBreakingAnimation() {
/* 130 */     return (this.breakingAnimation == 1);
/*     */   }
/*     */   
/*     */   public IBlockState getBlockState() {
/* 134 */     return this.blockState;
/*     */   }
/*     */   
/*     */   public BlockPosM getColorizerBlockPosM() {
/* 138 */     if (this.colorizerBlockPosM == null) {
/* 139 */       this.colorizerBlockPosM = new BlockPosM(0, 0, 0);
/*     */     }
/*     */     
/* 142 */     return this.colorizerBlockPosM;
/*     */   }
/*     */   
/*     */   public boolean[] getBorderFlags() {
/* 146 */     if (this.borderFlags == null) {
/* 147 */       this.borderFlags = new boolean[4];
/*     */     }
/*     */     
/* 150 */     return this.borderFlags;
/*     */   }
/*     */   
/*     */   public boolean[] getBorderFlags2() {
/* 154 */     if (this.borderFlags2 == null) {
/* 155 */       this.borderFlags2 = new boolean[4];
/*     */     }
/*     */     
/* 158 */     return this.borderFlags2;
/*     */   }
/*     */   
/*     */   public boolean[] getBorderFlags3() {
/* 162 */     if (this.borderFlags3 == null) {
/* 163 */       this.borderFlags3 = new boolean[4];
/*     */     }
/*     */     
/* 166 */     return this.borderFlags3;
/*     */   }
/*     */   
/*     */   public EnumFacing[] getBorderDirections() {
/* 170 */     if (this.borderDirections == null) {
/* 171 */       this.borderDirections = new EnumFacing[4];
/*     */     }
/*     */     
/* 174 */     return this.borderDirections;
/*     */   }
/*     */   
/*     */   public EnumFacing[] getBorderDirections(EnumFacing dir0, EnumFacing dir1, EnumFacing dir2, EnumFacing dir3) {
/* 178 */     EnumFacing[] aenumfacing = getBorderDirections();
/* 179 */     aenumfacing[0] = dir0;
/* 180 */     aenumfacing[1] = dir1;
/* 181 */     aenumfacing[2] = dir2;
/* 182 */     aenumfacing[3] = dir3;
/* 183 */     return aenumfacing;
/*     */   }
/*     */   
/*     */   public boolean isSmartLeaves() {
/* 187 */     if (this.smartLeaves == -1) {
/* 188 */       if (Config.isTreesSmart() && this.blockState.getBlock() instanceof net.minecraft.block.BlockLeaves) {
/* 189 */         this.smartLeaves = 1;
/*     */       } else {
/* 191 */         this.smartLeaves = 0;
/*     */       } 
/*     */     }
/*     */     
/* 195 */     return (this.smartLeaves == 1);
/*     */   }
/*     */   
/*     */   public List<BakedQuad> getListQuadsCustomizer() {
/* 199 */     return this.listQuadsCustomizer;
/*     */   }
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad) {
/* 203 */     this.arrayQuadsCtm1[0] = quad;
/* 204 */     return this.arrayQuadsCtm1;
/*     */   }
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad0, BakedQuad quad1) {
/* 208 */     this.arrayQuadsCtm2[0] = quad0;
/* 209 */     this.arrayQuadsCtm2[1] = quad1;
/* 210 */     return this.arrayQuadsCtm2;
/*     */   }
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad0, BakedQuad quad1, BakedQuad quad2) {
/* 214 */     this.arrayQuadsCtm3[0] = quad0;
/* 215 */     this.arrayQuadsCtm3[1] = quad1;
/* 216 */     this.arrayQuadsCtm3[2] = quad2;
/* 217 */     return this.arrayQuadsCtm3;
/*     */   }
/*     */   
/*     */   public BakedQuad[] getArrayQuadsCtm(BakedQuad quad0, BakedQuad quad1, BakedQuad quad2, BakedQuad quad3) {
/* 221 */     this.arrayQuadsCtm4[0] = quad0;
/* 222 */     this.arrayQuadsCtm4[1] = quad1;
/* 223 */     this.arrayQuadsCtm4[2] = quad2;
/* 224 */     this.arrayQuadsCtm4[3] = quad3;
/* 225 */     return this.arrayQuadsCtm4;
/*     */   }
/*     */   
/*     */   public List<BakedQuad> getListQuadsCtmMultipass(BakedQuad[] quads) {
/* 229 */     this.listQuadsCtmMultipass.clear();
/*     */     
/* 231 */     if (quads != null) {
/* 232 */       for (int i = 0; i < quads.length; i++) {
/* 233 */         BakedQuad bakedquad = quads[i];
/* 234 */         this.listQuadsCtmMultipass.add(bakedquad);
/*     */       } 
/*     */     }
/*     */     
/* 238 */     return this.listQuadsCtmMultipass;
/*     */   }
/*     */   
/*     */   public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
/* 242 */     return this.regionRenderCacheBuilder;
/*     */   }
/*     */   
/*     */   public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder regionRenderCacheBuilder) {
/* 246 */     this.regionRenderCacheBuilder = regionRenderCacheBuilder;
/*     */   }
/*     */   
/*     */   public ListQuadsOverlay getListQuadsOverlay(EnumWorldBlockLayer layer) {
/* 250 */     ListQuadsOverlay listquadsoverlay = this.listsQuadsOverlay[layer.ordinal()];
/*     */     
/* 252 */     if (listquadsoverlay == null) {
/* 253 */       listquadsoverlay = new ListQuadsOverlay();
/* 254 */       this.listsQuadsOverlay[layer.ordinal()] = listquadsoverlay;
/*     */     } 
/*     */     
/* 257 */     return listquadsoverlay;
/*     */   }
/*     */   
/*     */   public boolean isOverlaysRendered() {
/* 261 */     return this.overlaysRendered;
/*     */   }
/*     */   
/*     */   public void setOverlaysRendered(boolean overlaysRendered) {
/* 265 */     this.overlaysRendered = overlaysRendered;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\render\RenderEnv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */