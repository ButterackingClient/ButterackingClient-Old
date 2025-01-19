/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
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
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenSwamp extends WorldGenAbstractTree {
/*  18 */   private static final IBlockState field_181648_a = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.OAK);
/*  19 */   private static final IBlockState field_181649_b = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.OAK).withProperty((IProperty)BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false));
/*     */   
/*     */   public WorldGenSwamp() {
/*  22 */     super(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*     */     int i;
/*  28 */     for (i = rand.nextInt(4) + 5; worldIn.getBlockState(position.down()).getBlock().getMaterial() == Material.water; position = position.down());
/*     */ 
/*     */ 
/*     */     
/*  32 */     boolean flag = true;
/*     */     
/*  34 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*  35 */       for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
/*  36 */         int k = 1;
/*     */         
/*  38 */         if (j == position.getY()) {
/*  39 */           k = 0;
/*     */         }
/*     */         
/*  42 */         if (j >= position.getY() + 1 + i - 2) {
/*  43 */           k = 3;
/*     */         }
/*     */         
/*  46 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  48 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*  49 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*  50 */             if (j >= 0 && j < 256) {
/*  51 */               Block block = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(l, j, i1)).getBlock();
/*     */               
/*  53 */               if (block.getMaterial() != Material.air && block.getMaterial() != Material.leaves) {
/*  54 */                 if (block != Blocks.water && block != Blocks.flowing_water) {
/*  55 */                   flag = false;
/*  56 */                 } else if (j > position.getY()) {
/*  57 */                   flag = false;
/*     */                 } 
/*     */               }
/*     */             } else {
/*  61 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  67 */       if (!flag) {
/*  68 */         return false;
/*     */       }
/*  70 */       Block block1 = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  72 */       if ((block1 == Blocks.grass || block1 == Blocks.dirt) && position.getY() < 256 - i - 1) {
/*  73 */         func_175921_a(worldIn, position.down());
/*     */         
/*  75 */         for (int l1 = position.getY() - 3 + i; l1 <= position.getY() + i; l1++) {
/*  76 */           int k2 = l1 - position.getY() + i;
/*  77 */           int i3 = 2 - k2 / 2;
/*     */           
/*  79 */           for (int k3 = position.getX() - i3; k3 <= position.getX() + i3; k3++) {
/*  80 */             int l3 = k3 - position.getX();
/*     */             
/*  82 */             for (int j1 = position.getZ() - i3; j1 <= position.getZ() + i3; j1++) {
/*  83 */               int k1 = j1 - position.getZ();
/*     */               
/*  85 */               if (Math.abs(l3) != i3 || Math.abs(k1) != i3 || (rand.nextInt(2) != 0 && k2 != 0)) {
/*  86 */                 BlockPos blockpos = new BlockPos(k3, l1, j1);
/*     */                 
/*  88 */                 if (!worldIn.getBlockState(blockpos).getBlock().isFullBlock()) {
/*  89 */                   setBlockAndNotifyAdequately(worldIn, blockpos, field_181649_b);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  96 */         for (int i2 = 0; i2 < i; i2++) {
/*  97 */           Block block2 = worldIn.getBlockState(position.up(i2)).getBlock();
/*     */           
/*  99 */           if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves || block2 == Blocks.flowing_water || block2 == Blocks.water) {
/* 100 */             setBlockAndNotifyAdequately(worldIn, position.up(i2), field_181648_a);
/*     */           }
/*     */         } 
/*     */         
/* 104 */         for (int j2 = position.getY() - 3 + i; j2 <= position.getY() + i; j2++) {
/* 105 */           int l2 = j2 - position.getY() + i;
/* 106 */           int j3 = 2 - l2 / 2;
/* 107 */           BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
/*     */           
/* 109 */           for (int i4 = position.getX() - j3; i4 <= position.getX() + j3; i4++) {
/* 110 */             for (int j4 = position.getZ() - j3; j4 <= position.getZ() + j3; j4++) {
/* 111 */               blockpos$mutableblockpos1.set(i4, j2, j4);
/*     */               
/* 113 */               if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos1).getBlock().getMaterial() == Material.leaves) {
/* 114 */                 BlockPos blockpos3 = blockpos$mutableblockpos1.west();
/* 115 */                 BlockPos blockpos4 = blockpos$mutableblockpos1.east();
/* 116 */                 BlockPos blockpos1 = blockpos$mutableblockpos1.north();
/* 117 */                 BlockPos blockpos2 = blockpos$mutableblockpos1.south();
/*     */                 
/* 119 */                 if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos3).getBlock().getMaterial() == Material.air) {
/* 120 */                   func_181647_a(worldIn, blockpos3, BlockVine.EAST);
/*     */                 }
/*     */                 
/* 123 */                 if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos4).getBlock().getMaterial() == Material.air) {
/* 124 */                   func_181647_a(worldIn, blockpos4, BlockVine.WEST);
/*     */                 }
/*     */                 
/* 127 */                 if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos1).getBlock().getMaterial() == Material.air) {
/* 128 */                   func_181647_a(worldIn, blockpos1, BlockVine.SOUTH);
/*     */                 }
/*     */                 
/* 131 */                 if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos2).getBlock().getMaterial() == Material.air) {
/* 132 */                   func_181647_a(worldIn, blockpos2, BlockVine.NORTH);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 139 */         return true;
/*     */       } 
/* 141 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181647_a(World p_181647_1_, BlockPos p_181647_2_, PropertyBool p_181647_3_) {
/* 150 */     IBlockState iblockstate = Blocks.vine.getDefaultState().withProperty((IProperty)p_181647_3_, Boolean.valueOf(true));
/* 151 */     setBlockAndNotifyAdequately(p_181647_1_, p_181647_2_, iblockstate);
/* 152 */     int i = 4;
/*     */     
/* 154 */     for (p_181647_2_ = p_181647_2_.down(); p_181647_1_.getBlockState(p_181647_2_).getBlock().getMaterial() == Material.air && i > 0; i--) {
/* 155 */       setBlockAndNotifyAdequately(p_181647_1_, p_181647_2_, iblockstate);
/* 156 */       p_181647_2_ = p_181647_2_.down();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenSwamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */