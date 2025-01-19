/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockNewLeaf;
/*     */ import net.minecraft.block.BlockNewLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenSavannaTree extends WorldGenAbstractTree {
/*  18 */   private static final IBlockState field_181643_a = Blocks.log2.getDefaultState().withProperty((IProperty)BlockNewLog.VARIANT, (Comparable)BlockPlanks.EnumType.ACACIA);
/*  19 */   private static final IBlockState field_181644_b = Blocks.leaves2.getDefaultState().withProperty((IProperty)BlockNewLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.ACACIA).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */   
/*     */   public WorldGenSavannaTree(boolean p_i45463_1_) {
/*  22 */     super(p_i45463_1_);
/*     */   }
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  26 */     int i = rand.nextInt(3) + rand.nextInt(3) + 5;
/*  27 */     boolean flag = true;
/*     */     
/*  29 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*  30 */       for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
/*  31 */         int k = 1;
/*     */         
/*  33 */         if (j == position.getY()) {
/*  34 */           k = 0;
/*     */         }
/*     */         
/*  37 */         if (j >= position.getY() + 1 + i - 2) {
/*  38 */           k = 2;
/*     */         }
/*     */         
/*  41 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  43 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*  44 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*  45 */             if (j >= 0 && j < 256) {
/*  46 */               if (!func_150523_a(worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(l, j, i1)).getBlock())) {
/*  47 */                 flag = false;
/*     */               }
/*     */             } else {
/*  50 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  56 */       if (!flag) {
/*  57 */         return false;
/*     */       }
/*  59 */       Block block = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  61 */       if ((block == Blocks.grass || block == Blocks.dirt) && position.getY() < 256 - i - 1) {
/*  62 */         func_175921_a(worldIn, position.down());
/*  63 */         EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
/*  64 */         int k2 = i - rand.nextInt(4) - 1;
/*  65 */         int l2 = 3 - rand.nextInt(3);
/*  66 */         int i3 = position.getX();
/*  67 */         int j1 = position.getZ();
/*  68 */         int k1 = 0;
/*     */         
/*  70 */         for (int l1 = 0; l1 < i; l1++) {
/*  71 */           int i2 = position.getY() + l1;
/*     */           
/*  73 */           if (l1 >= k2 && l2 > 0) {
/*  74 */             i3 += enumfacing.getFrontOffsetX();
/*  75 */             j1 += enumfacing.getFrontOffsetZ();
/*  76 */             l2--;
/*     */           } 
/*     */           
/*  79 */           BlockPos blockpos = new BlockPos(i3, i2, j1);
/*  80 */           Material material = worldIn.getBlockState(blockpos).getBlock().getMaterial();
/*     */           
/*  82 */           if (material == Material.air || material == Material.leaves) {
/*  83 */             func_181642_b(worldIn, blockpos);
/*  84 */             k1 = i2;
/*     */           } 
/*     */         } 
/*     */         
/*  88 */         BlockPos blockpos2 = new BlockPos(i3, k1, j1);
/*     */         
/*  90 */         for (int j3 = -3; j3 <= 3; j3++) {
/*  91 */           for (int i4 = -3; i4 <= 3; i4++) {
/*  92 */             if (Math.abs(j3) != 3 || Math.abs(i4) != 3) {
/*  93 */               func_175924_b(worldIn, blockpos2.add(j3, 0, i4));
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/*  98 */         blockpos2 = blockpos2.up();
/*     */         
/* 100 */         for (int k3 = -1; k3 <= 1; k3++) {
/* 101 */           for (int j4 = -1; j4 <= 1; j4++) {
/* 102 */             func_175924_b(worldIn, blockpos2.add(k3, 0, j4));
/*     */           }
/*     */         } 
/*     */         
/* 106 */         func_175924_b(worldIn, blockpos2.east(2));
/* 107 */         func_175924_b(worldIn, blockpos2.west(2));
/* 108 */         func_175924_b(worldIn, blockpos2.south(2));
/* 109 */         func_175924_b(worldIn, blockpos2.north(2));
/* 110 */         i3 = position.getX();
/* 111 */         j1 = position.getZ();
/* 112 */         EnumFacing enumfacing1 = EnumFacing.Plane.HORIZONTAL.random(rand);
/*     */         
/* 114 */         if (enumfacing1 != enumfacing) {
/* 115 */           int l3 = k2 - rand.nextInt(2) - 1;
/* 116 */           int k4 = 1 + rand.nextInt(3);
/* 117 */           k1 = 0;
/*     */           
/* 119 */           for (int l4 = l3; l4 < i && k4 > 0; k4--) {
/* 120 */             if (l4 >= 1) {
/* 121 */               int j2 = position.getY() + l4;
/* 122 */               i3 += enumfacing1.getFrontOffsetX();
/* 123 */               j1 += enumfacing1.getFrontOffsetZ();
/* 124 */               BlockPos blockpos1 = new BlockPos(i3, j2, j1);
/* 125 */               Material material1 = worldIn.getBlockState(blockpos1).getBlock().getMaterial();
/*     */               
/* 127 */               if (material1 == Material.air || material1 == Material.leaves) {
/* 128 */                 func_181642_b(worldIn, blockpos1);
/* 129 */                 k1 = j2;
/*     */               } 
/*     */             } 
/*     */             
/* 133 */             l4++;
/*     */           } 
/*     */           
/* 136 */           if (k1 > 0) {
/* 137 */             BlockPos blockpos3 = new BlockPos(i3, k1, j1);
/*     */             
/* 139 */             for (int i5 = -2; i5 <= 2; i5++) {
/* 140 */               for (int k5 = -2; k5 <= 2; k5++) {
/* 141 */                 if (Math.abs(i5) != 2 || Math.abs(k5) != 2) {
/* 142 */                   func_175924_b(worldIn, blockpos3.add(i5, 0, k5));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 147 */             blockpos3 = blockpos3.up();
/*     */             
/* 149 */             for (int j5 = -1; j5 <= 1; j5++) {
/* 150 */               for (int l5 = -1; l5 <= 1; l5++) {
/* 151 */                 func_175924_b(worldIn, blockpos3.add(j5, 0, l5));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 157 */         return true;
/*     */       } 
/* 159 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181642_b(World p_181642_1_, BlockPos p_181642_2_) {
/* 168 */     setBlockAndNotifyAdequately(p_181642_1_, p_181642_2_, field_181643_a);
/*     */   }
/*     */   
/*     */   private void func_175924_b(World worldIn, BlockPos p_175924_2_) {
/* 172 */     Material material = worldIn.getBlockState(p_175924_2_).getBlock().getMaterial();
/*     */     
/* 174 */     if (material == Material.air || material == Material.leaves)
/* 175 */       setBlockAndNotifyAdequately(worldIn, p_175924_2_, field_181644_b); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenSavannaTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */