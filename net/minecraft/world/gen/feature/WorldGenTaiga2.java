/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockOldLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenTaiga2 extends WorldGenAbstractTree {
/*  17 */   private static final IBlockState field_181645_a = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE);
/*  18 */   private static final IBlockState field_181646_b = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */   
/*     */   public WorldGenTaiga2(boolean p_i2025_1_) {
/*  21 */     super(p_i2025_1_);
/*     */   }
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  25 */     int i = rand.nextInt(4) + 6;
/*  26 */     int j = 1 + rand.nextInt(2);
/*  27 */     int k = i - j;
/*  28 */     int l = 2 + rand.nextInt(2);
/*  29 */     boolean flag = true;
/*     */     
/*  31 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*  32 */       for (int i1 = position.getY(); i1 <= position.getY() + 1 + i && flag; i1++) {
/*  33 */         int j1 = 1;
/*     */         
/*  35 */         if (i1 - position.getY() < j) {
/*  36 */           j1 = 0;
/*     */         } else {
/*  38 */           j1 = l;
/*     */         } 
/*     */         
/*  41 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  43 */         for (int k1 = position.getX() - j1; k1 <= position.getX() + j1 && flag; k1++) {
/*  44 */           for (int l1 = position.getZ() - j1; l1 <= position.getZ() + j1 && flag; l1++) {
/*  45 */             if (i1 >= 0 && i1 < 256) {
/*  46 */               Block block = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, i1, l1)).getBlock();
/*     */               
/*  48 */               if (block.getMaterial() != Material.air && block.getMaterial() != Material.leaves) {
/*  49 */                 flag = false;
/*     */               }
/*     */             } else {
/*  52 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  58 */       if (!flag) {
/*  59 */         return false;
/*     */       }
/*  61 */       Block block1 = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  63 */       if ((block1 == Blocks.grass || block1 == Blocks.dirt || block1 == Blocks.farmland) && position.getY() < 256 - i - 1) {
/*  64 */         func_175921_a(worldIn, position.down());
/*  65 */         int i3 = rand.nextInt(2);
/*  66 */         int j3 = 1;
/*  67 */         int k3 = 0;
/*     */         
/*  69 */         for (int l3 = 0; l3 <= k; l3++) {
/*  70 */           int j4 = position.getY() + i - l3;
/*     */           
/*  72 */           for (int i2 = position.getX() - i3; i2 <= position.getX() + i3; i2++) {
/*  73 */             int j2 = i2 - position.getX();
/*     */             
/*  75 */             for (int k2 = position.getZ() - i3; k2 <= position.getZ() + i3; k2++) {
/*  76 */               int l2 = k2 - position.getZ();
/*     */               
/*  78 */               if (Math.abs(j2) != i3 || Math.abs(l2) != i3 || i3 <= 0) {
/*  79 */                 BlockPos blockpos = new BlockPos(i2, j4, k2);
/*     */                 
/*  81 */                 if (!worldIn.getBlockState(blockpos).getBlock().isFullBlock()) {
/*  82 */                   setBlockAndNotifyAdequately(worldIn, blockpos, field_181646_b);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/*  88 */           if (i3 >= j3) {
/*  89 */             i3 = k3;
/*  90 */             k3 = 1;
/*  91 */             j3++;
/*     */             
/*  93 */             if (j3 > l) {
/*  94 */               j3 = l;
/*     */             }
/*     */           } else {
/*  97 */             i3++;
/*     */           } 
/*     */         } 
/*     */         
/* 101 */         int i4 = rand.nextInt(3);
/*     */         
/* 103 */         for (int k4 = 0; k4 < i - i4; k4++) {
/* 104 */           Block block2 = worldIn.getBlockState(position.up(k4)).getBlock();
/*     */           
/* 106 */           if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves) {
/* 107 */             setBlockAndNotifyAdequately(worldIn, position.up(k4), field_181645_a);
/*     */           }
/*     */         } 
/*     */         
/* 111 */         return true;
/*     */       } 
/* 113 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 117 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenTaiga2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */