/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockLog;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenBigTree
/*     */   extends WorldGenAbstractTree {
/*     */   private Random rand;
/*     */   private World world;
/*  21 */   private BlockPos basePos = BlockPos.ORIGIN;
/*     */   int heightLimit;
/*     */   int height;
/*  24 */   double heightAttenuation = 0.618D;
/*  25 */   double branchSlope = 0.381D;
/*  26 */   double scaleWidth = 1.0D;
/*  27 */   double leafDensity = 1.0D;
/*  28 */   int trunkSize = 1;
/*  29 */   int heightLimitLimit = 12;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  34 */   int leafDistanceLimit = 4;
/*     */   List<FoliageCoordinates> field_175948_j;
/*     */   
/*     */   public WorldGenBigTree(boolean p_i2008_1_) {
/*  38 */     super(p_i2008_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateLeafNodeList() {
/*  45 */     this.height = (int)(this.heightLimit * this.heightAttenuation);
/*     */     
/*  47 */     if (this.height >= this.heightLimit) {
/*  48 */       this.height = this.heightLimit - 1;
/*     */     }
/*     */     
/*  51 */     int i = (int)(1.382D + Math.pow(this.leafDensity * this.heightLimit / 13.0D, 2.0D));
/*     */     
/*  53 */     if (i < 1) {
/*  54 */       i = 1;
/*     */     }
/*     */     
/*  57 */     int j = this.basePos.getY() + this.height;
/*  58 */     int k = this.heightLimit - this.leafDistanceLimit;
/*  59 */     this.field_175948_j = Lists.newArrayList();
/*  60 */     this.field_175948_j.add(new FoliageCoordinates(this.basePos.up(k), j));
/*     */     
/*  62 */     for (; k >= 0; k--) {
/*  63 */       float f = layerSize(k);
/*     */       
/*  65 */       if (f >= 0.0F) {
/*  66 */         for (int l = 0; l < i; l++) {
/*  67 */           double d0 = this.scaleWidth * f * (this.rand.nextFloat() + 0.328D);
/*  68 */           double d1 = (this.rand.nextFloat() * 2.0F) * Math.PI;
/*  69 */           double d2 = d0 * Math.sin(d1) + 0.5D;
/*  70 */           double d3 = d0 * Math.cos(d1) + 0.5D;
/*  71 */           BlockPos blockpos = this.basePos.add(d2, (k - 1), d3);
/*  72 */           BlockPos blockpos1 = blockpos.up(this.leafDistanceLimit);
/*     */           
/*  74 */           if (checkBlockLine(blockpos, blockpos1) == -1) {
/*  75 */             int i1 = this.basePos.getX() - blockpos.getX();
/*  76 */             int j1 = this.basePos.getZ() - blockpos.getZ();
/*  77 */             double d4 = blockpos.getY() - Math.sqrt((i1 * i1 + j1 * j1)) * this.branchSlope;
/*  78 */             int k1 = (d4 > j) ? j : (int)d4;
/*  79 */             BlockPos blockpos2 = new BlockPos(this.basePos.getX(), k1, this.basePos.getZ());
/*     */             
/*  81 */             if (checkBlockLine(blockpos2, blockpos) == -1) {
/*  82 */               this.field_175948_j.add(new FoliageCoordinates(blockpos, blockpos2.getY()));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void func_181631_a(BlockPos p_181631_1_, float p_181631_2_, IBlockState p_181631_3_) {
/*  91 */     int i = (int)(p_181631_2_ + 0.618D);
/*     */     
/*  93 */     for (int j = -i; j <= i; j++) {
/*  94 */       for (int k = -i; k <= i; k++) {
/*  95 */         if (Math.pow(Math.abs(j) + 0.5D, 2.0D) + Math.pow(Math.abs(k) + 0.5D, 2.0D) <= (p_181631_2_ * p_181631_2_)) {
/*  96 */           BlockPos blockpos = p_181631_1_.add(j, 0, k);
/*  97 */           Material material = this.world.getBlockState(blockpos).getBlock().getMaterial();
/*     */           
/*  99 */           if (material == Material.air || material == Material.leaves) {
/* 100 */             setBlockAndNotifyAdequately(this.world, blockpos, p_181631_3_);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float layerSize(int p_76490_1_) {
/* 111 */     if (p_76490_1_ < this.heightLimit * 0.3F) {
/* 112 */       return -1.0F;
/*     */     }
/* 114 */     float f = this.heightLimit / 2.0F;
/* 115 */     float f1 = f - p_76490_1_;
/* 116 */     float f2 = MathHelper.sqrt_float(f * f - f1 * f1);
/*     */     
/* 118 */     if (f1 == 0.0F) {
/* 119 */       f2 = f;
/* 120 */     } else if (Math.abs(f1) >= f) {
/* 121 */       return 0.0F;
/*     */     } 
/*     */     
/* 124 */     return f2 * 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   float leafSize(int p_76495_1_) {
/* 129 */     return (p_76495_1_ >= 0 && p_76495_1_ < this.leafDistanceLimit) ? ((p_76495_1_ != 0 && p_76495_1_ != this.leafDistanceLimit - 1) ? 3.0F : 2.0F) : -1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateLeafNode(BlockPos pos) {
/* 136 */     for (int i = 0; i < this.leafDistanceLimit; i++) {
/* 137 */       func_181631_a(pos.up(i), leafSize(i), Blocks.leaves.getDefaultState().withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false)));
/*     */     }
/*     */   }
/*     */   
/*     */   void func_175937_a(BlockPos p_175937_1_, BlockPos p_175937_2_, Block p_175937_3_) {
/* 142 */     BlockPos blockpos = p_175937_2_.add(-p_175937_1_.getX(), -p_175937_1_.getY(), -p_175937_1_.getZ());
/* 143 */     int i = getGreatestDistance(blockpos);
/* 144 */     float f = blockpos.getX() / i;
/* 145 */     float f1 = blockpos.getY() / i;
/* 146 */     float f2 = blockpos.getZ() / i;
/*     */     
/* 148 */     for (int j = 0; j <= i; j++) {
/* 149 */       BlockPos blockpos1 = p_175937_1_.add((0.5F + j * f), (0.5F + j * f1), (0.5F + j * f2));
/* 150 */       BlockLog.EnumAxis blocklog$enumaxis = func_175938_b(p_175937_1_, blockpos1);
/* 151 */       setBlockAndNotifyAdequately(this.world, blockpos1, p_175937_3_.getDefaultState().withProperty((IProperty)BlockLog.LOG_AXIS, (Comparable)blocklog$enumaxis));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getGreatestDistance(BlockPos posIn) {
/* 159 */     int i = MathHelper.abs_int(posIn.getX());
/* 160 */     int j = MathHelper.abs_int(posIn.getY());
/* 161 */     int k = MathHelper.abs_int(posIn.getZ());
/* 162 */     return (k > i && k > j) ? k : ((j > i) ? j : i);
/*     */   }
/*     */   
/*     */   private BlockLog.EnumAxis func_175938_b(BlockPos p_175938_1_, BlockPos p_175938_2_) {
/* 166 */     BlockLog.EnumAxis blocklog$enumaxis = BlockLog.EnumAxis.Y;
/* 167 */     int i = Math.abs(p_175938_2_.getX() - p_175938_1_.getX());
/* 168 */     int j = Math.abs(p_175938_2_.getZ() - p_175938_1_.getZ());
/* 169 */     int k = Math.max(i, j);
/*     */     
/* 171 */     if (k > 0) {
/* 172 */       if (i == k) {
/* 173 */         blocklog$enumaxis = BlockLog.EnumAxis.X;
/* 174 */       } else if (j == k) {
/* 175 */         blocklog$enumaxis = BlockLog.EnumAxis.Z;
/*     */       } 
/*     */     }
/*     */     
/* 179 */     return blocklog$enumaxis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateLeaves() {
/* 186 */     for (FoliageCoordinates worldgenbigtree$foliagecoordinates : this.field_175948_j) {
/* 187 */       generateLeafNode(worldgenbigtree$foliagecoordinates);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean leafNodeNeedsBase(int p_76493_1_) {
/* 195 */     return (p_76493_1_ >= this.heightLimit * 0.2D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateTrunk() {
/* 203 */     BlockPos blockpos = this.basePos;
/* 204 */     BlockPos blockpos1 = this.basePos.up(this.height);
/* 205 */     Block block = Blocks.log;
/* 206 */     func_175937_a(blockpos, blockpos1, block);
/*     */     
/* 208 */     if (this.trunkSize == 2) {
/* 209 */       func_175937_a(blockpos.east(), blockpos1.east(), block);
/* 210 */       func_175937_a(blockpos.east().south(), blockpos1.east().south(), block);
/* 211 */       func_175937_a(blockpos.south(), blockpos1.south(), block);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void generateLeafNodeBases() {
/* 219 */     for (FoliageCoordinates worldgenbigtree$foliagecoordinates : this.field_175948_j) {
/* 220 */       int i = worldgenbigtree$foliagecoordinates.func_177999_q();
/* 221 */       BlockPos blockpos = new BlockPos(this.basePos.getX(), i, this.basePos.getZ());
/*     */       
/* 223 */       if (!blockpos.equals(worldgenbigtree$foliagecoordinates) && leafNodeNeedsBase(i - this.basePos.getY())) {
/* 224 */         func_175937_a(blockpos, worldgenbigtree$foliagecoordinates, Blocks.log);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int checkBlockLine(BlockPos posOne, BlockPos posTwo) {
/* 234 */     BlockPos blockpos = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
/* 235 */     int i = getGreatestDistance(blockpos);
/* 236 */     float f = blockpos.getX() / i;
/* 237 */     float f1 = blockpos.getY() / i;
/* 238 */     float f2 = blockpos.getZ() / i;
/*     */     
/* 240 */     if (i == 0) {
/* 241 */       return -1;
/*     */     }
/* 243 */     for (int j = 0; j <= i; j++) {
/* 244 */       BlockPos blockpos1 = posOne.add((0.5F + j * f), (0.5F + j * f1), (0.5F + j * f2));
/*     */       
/* 246 */       if (!func_150523_a(this.world.getBlockState(blockpos1).getBlock())) {
/* 247 */         return j;
/*     */       }
/*     */     } 
/*     */     
/* 251 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175904_e() {
/* 256 */     this.leafDistanceLimit = 5;
/*     */   }
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 260 */     this.world = worldIn;
/* 261 */     this.basePos = position;
/* 262 */     this.rand = new Random(rand.nextLong());
/*     */     
/* 264 */     if (this.heightLimit == 0) {
/* 265 */       this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
/*     */     }
/*     */     
/* 268 */     if (!validTreeLocation()) {
/* 269 */       return false;
/*     */     }
/* 271 */     generateLeafNodeList();
/* 272 */     generateLeaves();
/* 273 */     generateTrunk();
/* 274 */     generateLeafNodeBases();
/* 275 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean validTreeLocation() {
/* 284 */     Block block = this.world.getBlockState(this.basePos.down()).getBlock();
/*     */     
/* 286 */     if (block != Blocks.dirt && block != Blocks.grass && block != Blocks.farmland) {
/* 287 */       return false;
/*     */     }
/* 289 */     int i = checkBlockLine(this.basePos, this.basePos.up(this.heightLimit - 1));
/*     */     
/* 291 */     if (i == -1)
/* 292 */       return true; 
/* 293 */     if (i < 6) {
/* 294 */       return false;
/*     */     }
/* 296 */     this.heightLimit = i;
/* 297 */     return true;
/*     */   }
/*     */   
/*     */   static class FoliageCoordinates
/*     */     extends BlockPos
/*     */   {
/*     */     private final int field_178000_b;
/*     */     
/*     */     public FoliageCoordinates(BlockPos p_i45635_1_, int p_i45635_2_) {
/* 306 */       super(p_i45635_1_.getX(), p_i45635_1_.getY(), p_i45635_1_.getZ());
/* 307 */       this.field_178000_b = p_i45635_2_;
/*     */     }
/*     */     
/*     */     public int func_177999_q() {
/* 311 */       return this.field_178000_b;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenBigTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */