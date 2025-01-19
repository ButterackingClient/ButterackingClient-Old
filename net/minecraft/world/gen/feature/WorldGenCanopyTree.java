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
/*     */ public class WorldGenCanopyTree extends WorldGenAbstractTree {
/*  18 */   private static final IBlockState field_181640_a = Blocks.log2.getDefaultState().withProperty((IProperty)BlockNewLog.VARIANT, (Comparable)BlockPlanks.EnumType.DARK_OAK);
/*  19 */   private static final IBlockState field_181641_b = Blocks.leaves2.getDefaultState().withProperty((IProperty)BlockNewLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.DARK_OAK).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */   
/*     */   public WorldGenCanopyTree(boolean p_i45461_1_) {
/*  22 */     super(p_i45461_1_);
/*     */   }
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  26 */     int i = rand.nextInt(3) + rand.nextInt(2) + 6;
/*  27 */     int j = position.getX();
/*  28 */     int k = position.getY();
/*  29 */     int l = position.getZ();
/*     */     
/*  31 */     if (k >= 1 && k + i + 1 < 256) {
/*  32 */       BlockPos blockpos = position.down();
/*  33 */       Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */       
/*  35 */       if (block != Blocks.grass && block != Blocks.dirt)
/*  36 */         return false; 
/*  37 */       if (!func_181638_a(worldIn, position, i)) {
/*  38 */         return false;
/*     */       }
/*  40 */       func_175921_a(worldIn, blockpos);
/*  41 */       func_175921_a(worldIn, blockpos.east());
/*  42 */       func_175921_a(worldIn, blockpos.south());
/*  43 */       func_175921_a(worldIn, blockpos.south().east());
/*  44 */       EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
/*  45 */       int i1 = i - rand.nextInt(4);
/*  46 */       int j1 = 2 - rand.nextInt(3);
/*  47 */       int k1 = j;
/*  48 */       int l1 = l;
/*  49 */       int i2 = k + i - 1;
/*     */       
/*  51 */       for (int j2 = 0; j2 < i; j2++) {
/*  52 */         if (j2 >= i1 && j1 > 0) {
/*  53 */           k1 += enumfacing.getFrontOffsetX();
/*  54 */           l1 += enumfacing.getFrontOffsetZ();
/*  55 */           j1--;
/*     */         } 
/*     */         
/*  58 */         int k2 = k + j2;
/*  59 */         BlockPos blockpos1 = new BlockPos(k1, k2, l1);
/*  60 */         Material material = worldIn.getBlockState(blockpos1).getBlock().getMaterial();
/*     */         
/*  62 */         if (material == Material.air || material == Material.leaves) {
/*  63 */           func_181639_b(worldIn, blockpos1);
/*  64 */           func_181639_b(worldIn, blockpos1.east());
/*  65 */           func_181639_b(worldIn, blockpos1.south());
/*  66 */           func_181639_b(worldIn, blockpos1.east().south());
/*     */         } 
/*     */       } 
/*     */       
/*  70 */       for (int i3 = -2; i3 <= 0; i3++) {
/*  71 */         for (int l3 = -2; l3 <= 0; l3++) {
/*  72 */           int k4 = -1;
/*  73 */           func_150526_a(worldIn, k1 + i3, i2 + k4, l1 + l3);
/*  74 */           func_150526_a(worldIn, 1 + k1 - i3, i2 + k4, l1 + l3);
/*  75 */           func_150526_a(worldIn, k1 + i3, i2 + k4, 1 + l1 - l3);
/*  76 */           func_150526_a(worldIn, 1 + k1 - i3, i2 + k4, 1 + l1 - l3);
/*     */           
/*  78 */           if ((i3 > -2 || l3 > -1) && (i3 != -1 || l3 != -2)) {
/*  79 */             k4 = 1;
/*  80 */             func_150526_a(worldIn, k1 + i3, i2 + k4, l1 + l3);
/*  81 */             func_150526_a(worldIn, 1 + k1 - i3, i2 + k4, l1 + l3);
/*  82 */             func_150526_a(worldIn, k1 + i3, i2 + k4, 1 + l1 - l3);
/*  83 */             func_150526_a(worldIn, 1 + k1 - i3, i2 + k4, 1 + l1 - l3);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  88 */       if (rand.nextBoolean()) {
/*  89 */         func_150526_a(worldIn, k1, i2 + 2, l1);
/*  90 */         func_150526_a(worldIn, k1 + 1, i2 + 2, l1);
/*  91 */         func_150526_a(worldIn, k1 + 1, i2 + 2, l1 + 1);
/*  92 */         func_150526_a(worldIn, k1, i2 + 2, l1 + 1);
/*     */       } 
/*     */       
/*  95 */       for (int j3 = -3; j3 <= 4; j3++) {
/*  96 */         for (int i4 = -3; i4 <= 4; i4++) {
/*  97 */           if ((j3 != -3 || i4 != -3) && (j3 != -3 || i4 != 4) && (j3 != 4 || i4 != -3) && (j3 != 4 || i4 != 4) && (Math.abs(j3) < 3 || Math.abs(i4) < 3)) {
/*  98 */             func_150526_a(worldIn, k1 + j3, i2, l1 + i4);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 103 */       for (int k3 = -1; k3 <= 2; k3++) {
/* 104 */         for (int j4 = -1; j4 <= 2; j4++) {
/* 105 */           if ((k3 < 0 || k3 > 1 || j4 < 0 || j4 > 1) && rand.nextInt(3) <= 0) {
/* 106 */             int l4 = rand.nextInt(3) + 2;
/*     */             
/* 108 */             for (int i5 = 0; i5 < l4; i5++) {
/* 109 */               func_181639_b(worldIn, new BlockPos(j + k3, i2 - i5 - 1, l + j4));
/*     */             }
/*     */             
/* 112 */             for (int j5 = -1; j5 <= 1; j5++) {
/* 113 */               for (int l2 = -1; l2 <= 1; l2++) {
/* 114 */                 func_150526_a(worldIn, k1 + k3 + j5, i2, l1 + j4 + l2);
/*     */               }
/*     */             } 
/*     */             
/* 118 */             for (int k5 = -2; k5 <= 2; k5++) {
/* 119 */               for (int l5 = -2; l5 <= 2; l5++) {
/* 120 */                 if (Math.abs(k5) != 2 || Math.abs(l5) != 2) {
/* 121 */                   func_150526_a(worldIn, k1 + k3 + k5, i2 - 1, l1 + j4 + l5);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 129 */       return true;
/*     */     } 
/*     */     
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_181638_a(World p_181638_1_, BlockPos p_181638_2_, int p_181638_3_) {
/* 137 */     int i = p_181638_2_.getX();
/* 138 */     int j = p_181638_2_.getY();
/* 139 */     int k = p_181638_2_.getZ();
/* 140 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 142 */     for (int l = 0; l <= p_181638_3_ + 1; l++) {
/* 143 */       int i1 = 1;
/*     */       
/* 145 */       if (l == 0) {
/* 146 */         i1 = 0;
/*     */       }
/*     */       
/* 149 */       if (l >= p_181638_3_ - 1) {
/* 150 */         i1 = 2;
/*     */       }
/*     */       
/* 153 */       for (int j1 = -i1; j1 <= i1; j1++) {
/* 154 */         for (int k1 = -i1; k1 <= i1; k1++) {
/* 155 */           if (!func_150523_a(p_181638_1_.getBlockState((BlockPos)blockpos$mutableblockpos.set(i + j1, j + l, k + k1)).getBlock())) {
/* 156 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 162 */     return true;
/*     */   }
/*     */   
/*     */   private void func_181639_b(World p_181639_1_, BlockPos p_181639_2_) {
/* 166 */     if (func_150523_a(p_181639_1_.getBlockState(p_181639_2_).getBlock())) {
/* 167 */       setBlockAndNotifyAdequately(p_181639_1_, p_181639_2_, field_181640_a);
/*     */     }
/*     */   }
/*     */   
/*     */   private void func_150526_a(World worldIn, int p_150526_2_, int p_150526_3_, int p_150526_4_) {
/* 172 */     BlockPos blockpos = new BlockPos(p_150526_2_, p_150526_3_, p_150526_4_);
/* 173 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */     
/* 175 */     if (block.getMaterial() == Material.air)
/* 176 */       setBlockAndNotifyAdequately(worldIn, blockpos, field_181641_b); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenCanopyTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */