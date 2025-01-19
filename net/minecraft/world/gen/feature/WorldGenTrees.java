/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockCocoa;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockOldLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.BlockVine;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenTrees extends WorldGenAbstractTree {
/*  21 */   private static final IBlockState field_181653_a = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.OAK);
/*  22 */   private static final IBlockState field_181654_b = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.OAK).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int minTreeHeight;
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean vinesGrow;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IBlockState metaWood;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IBlockState metaLeaves;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenTrees(boolean p_i2027_1_) {
/*  45 */     this(p_i2027_1_, 4, field_181653_a, field_181654_b, false);
/*     */   }
/*     */   
/*     */   public WorldGenTrees(boolean p_i46446_1_, int p_i46446_2_, IBlockState p_i46446_3_, IBlockState p_i46446_4_, boolean p_i46446_5_) {
/*  49 */     super(p_i46446_1_);
/*  50 */     this.minTreeHeight = p_i46446_2_;
/*  51 */     this.metaWood = p_i46446_3_;
/*  52 */     this.metaLeaves = p_i46446_4_;
/*  53 */     this.vinesGrow = p_i46446_5_;
/*     */   }
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  57 */     int i = rand.nextInt(3) + this.minTreeHeight;
/*  58 */     boolean flag = true;
/*     */     
/*  60 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*  61 */       for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
/*  62 */         int k = 1;
/*     */         
/*  64 */         if (j == position.getY()) {
/*  65 */           k = 0;
/*     */         }
/*     */         
/*  68 */         if (j >= position.getY() + 1 + i - 2) {
/*  69 */           k = 2;
/*     */         }
/*     */         
/*  72 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  74 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*  75 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*  76 */             if (j >= 0 && j < 256) {
/*  77 */               if (!func_150523_a(worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(l, j, i1)).getBlock())) {
/*  78 */                 flag = false;
/*     */               }
/*     */             } else {
/*  81 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  87 */       if (!flag) {
/*  88 */         return false;
/*     */       }
/*  90 */       Block block1 = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  92 */       if ((block1 == Blocks.grass || block1 == Blocks.dirt || block1 == Blocks.farmland) && position.getY() < 256 - i - 1) {
/*  93 */         func_175921_a(worldIn, position.down());
/*  94 */         int k2 = 3;
/*  95 */         int l2 = 0;
/*     */         
/*  97 */         for (int i3 = position.getY() - k2 + i; i3 <= position.getY() + i; i3++) {
/*  98 */           int i4 = i3 - position.getY() + i;
/*  99 */           int j1 = l2 + 1 - i4 / 2;
/*     */           
/* 101 */           for (int k1 = position.getX() - j1; k1 <= position.getX() + j1; k1++) {
/* 102 */             int l1 = k1 - position.getX();
/*     */             
/* 104 */             for (int i2 = position.getZ() - j1; i2 <= position.getZ() + j1; i2++) {
/* 105 */               int j2 = i2 - position.getZ();
/*     */               
/* 107 */               if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || (rand.nextInt(2) != 0 && i4 != 0)) {
/* 108 */                 BlockPos blockpos = new BlockPos(k1, i3, i2);
/* 109 */                 Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */                 
/* 111 */                 if (block.getMaterial() == Material.air || block.getMaterial() == Material.leaves || block.getMaterial() == Material.vine) {
/* 112 */                   setBlockAndNotifyAdequately(worldIn, blockpos, this.metaLeaves);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 119 */         for (int j3 = 0; j3 < i; j3++) {
/* 120 */           Block block2 = worldIn.getBlockState(position.up(j3)).getBlock();
/*     */           
/* 122 */           if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves || block2.getMaterial() == Material.vine) {
/* 123 */             setBlockAndNotifyAdequately(worldIn, position.up(j3), this.metaWood);
/*     */             
/* 125 */             if (this.vinesGrow && j3 > 0) {
/* 126 */               if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(-1, j3, 0))) {
/* 127 */                 func_181651_a(worldIn, position.add(-1, j3, 0), BlockVine.EAST);
/*     */               }
/*     */               
/* 130 */               if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(1, j3, 0))) {
/* 131 */                 func_181651_a(worldIn, position.add(1, j3, 0), BlockVine.WEST);
/*     */               }
/*     */               
/* 134 */               if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(0, j3, -1))) {
/* 135 */                 func_181651_a(worldIn, position.add(0, j3, -1), BlockVine.SOUTH);
/*     */               }
/*     */               
/* 138 */               if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(0, j3, 1))) {
/* 139 */                 func_181651_a(worldIn, position.add(0, j3, 1), BlockVine.NORTH);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 145 */         if (this.vinesGrow) {
/* 146 */           for (int k3 = position.getY() - 3 + i; k3 <= position.getY() + i; k3++) {
/* 147 */             int j4 = k3 - position.getY() + i;
/* 148 */             int k4 = 2 - j4 / 2;
/* 149 */             BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
/*     */             
/* 151 */             for (int l4 = position.getX() - k4; l4 <= position.getX() + k4; l4++) {
/* 152 */               for (int i5 = position.getZ() - k4; i5 <= position.getZ() + k4; i5++) {
/* 153 */                 blockpos$mutableblockpos1.set(l4, k3, i5);
/*     */                 
/* 155 */                 if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos1).getBlock().getMaterial() == Material.leaves) {
/* 156 */                   BlockPos blockpos2 = blockpos$mutableblockpos1.west();
/* 157 */                   BlockPos blockpos3 = blockpos$mutableblockpos1.east();
/* 158 */                   BlockPos blockpos4 = blockpos$mutableblockpos1.north();
/* 159 */                   BlockPos blockpos1 = blockpos$mutableblockpos1.south();
/*     */                   
/* 161 */                   if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos2).getBlock().getMaterial() == Material.air) {
/* 162 */                     func_181650_b(worldIn, blockpos2, BlockVine.EAST);
/*     */                   }
/*     */                   
/* 165 */                   if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos3).getBlock().getMaterial() == Material.air) {
/* 166 */                     func_181650_b(worldIn, blockpos3, BlockVine.WEST);
/*     */                   }
/*     */                   
/* 169 */                   if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos4).getBlock().getMaterial() == Material.air) {
/* 170 */                     func_181650_b(worldIn, blockpos4, BlockVine.SOUTH);
/*     */                   }
/*     */                   
/* 173 */                   if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos1).getBlock().getMaterial() == Material.air) {
/* 174 */                     func_181650_b(worldIn, blockpos1, BlockVine.NORTH);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 181 */           if (rand.nextInt(5) == 0 && i > 5) {
/* 182 */             for (int l3 = 0; l3 < 2; l3++) {
/* 183 */               for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 184 */                 if (rand.nextInt(4 - l3) == 0) {
/* 185 */                   EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 186 */                   func_181652_a(worldIn, rand.nextInt(3), position.add(enumfacing1.getFrontOffsetX(), i - 5 + l3, enumfacing1.getFrontOffsetZ()), enumfacing);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 193 */         return true;
/*     */       } 
/* 195 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 199 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181652_a(World p_181652_1_, int p_181652_2_, BlockPos p_181652_3_, EnumFacing p_181652_4_) {
/* 204 */     setBlockAndNotifyAdequately(p_181652_1_, p_181652_3_, Blocks.cocoa.getDefaultState().withProperty((IProperty)BlockCocoa.AGE, Integer.valueOf(p_181652_2_)).withProperty((IProperty)BlockCocoa.FACING, (Comparable)p_181652_4_));
/*     */   }
/*     */   
/*     */   private void func_181651_a(World p_181651_1_, BlockPos p_181651_2_, PropertyBool p_181651_3_) {
/* 208 */     setBlockAndNotifyAdequately(p_181651_1_, p_181651_2_, Blocks.vine.getDefaultState().withProperty((IProperty)p_181651_3_, Boolean.valueOf(true)));
/*     */   }
/*     */   
/*     */   private void func_181650_b(World p_181650_1_, BlockPos p_181650_2_, PropertyBool p_181650_3_) {
/* 212 */     func_181651_a(p_181650_1_, p_181650_2_, p_181650_3_);
/* 213 */     int i = 4;
/*     */     
/* 215 */     for (p_181650_2_ = p_181650_2_.down(); p_181650_1_.getBlockState(p_181650_2_).getBlock().getMaterial() == Material.air && i > 0; i--) {
/* 216 */       func_181651_a(p_181650_1_, p_181650_2_, p_181650_3_);
/* 217 */       p_181650_2_ = p_181650_2_.down();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenTrees.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */