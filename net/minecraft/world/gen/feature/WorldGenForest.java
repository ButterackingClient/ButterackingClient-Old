/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
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
/*     */ public class WorldGenForest extends WorldGenAbstractTree {
/*  16 */   private static final IBlockState field_181629_a = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.BIRCH);
/*  17 */   private static final IBlockState field_181630_b = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.BIRCH).withProperty((IProperty)BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false));
/*     */   private boolean useExtraRandomHeight;
/*     */   
/*     */   public WorldGenForest(boolean p_i45449_1_, boolean p_i45449_2_) {
/*  21 */     super(p_i45449_1_);
/*  22 */     this.useExtraRandomHeight = p_i45449_2_;
/*     */   }
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  26 */     int i = rand.nextInt(3) + 5;
/*     */     
/*  28 */     if (this.useExtraRandomHeight) {
/*  29 */       i += rand.nextInt(7);
/*     */     }
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
/*  43 */           k = 2;
/*     */         }
/*     */         
/*  46 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  48 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*  49 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*  50 */             if (j >= 0 && j < 256) {
/*  51 */               if (!func_150523_a(worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(l, j, i1)).getBlock())) {
/*  52 */                 flag = false;
/*     */               }
/*     */             } else {
/*  55 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  61 */       if (!flag) {
/*  62 */         return false;
/*     */       }
/*  64 */       Block block1 = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  66 */       if ((block1 == Blocks.grass || block1 == Blocks.dirt || block1 == Blocks.farmland) && position.getY() < 256 - i - 1) {
/*  67 */         func_175921_a(worldIn, position.down());
/*     */         
/*  69 */         for (int i2 = position.getY() - 3 + i; i2 <= position.getY() + i; i2++) {
/*  70 */           int k2 = i2 - position.getY() + i;
/*  71 */           int l2 = 1 - k2 / 2;
/*     */           
/*  73 */           for (int i3 = position.getX() - l2; i3 <= position.getX() + l2; i3++) {
/*  74 */             int j1 = i3 - position.getX();
/*     */             
/*  76 */             for (int k1 = position.getZ() - l2; k1 <= position.getZ() + l2; k1++) {
/*  77 */               int l1 = k1 - position.getZ();
/*     */               
/*  79 */               if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || (rand.nextInt(2) != 0 && k2 != 0)) {
/*  80 */                 BlockPos blockpos = new BlockPos(i3, i2, k1);
/*  81 */                 Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */                 
/*  83 */                 if (block.getMaterial() == Material.air || block.getMaterial() == Material.leaves) {
/*  84 */                   setBlockAndNotifyAdequately(worldIn, blockpos, field_181630_b);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  91 */         for (int j2 = 0; j2 < i; j2++) {
/*  92 */           Block block2 = worldIn.getBlockState(position.up(j2)).getBlock();
/*     */           
/*  94 */           if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves) {
/*  95 */             setBlockAndNotifyAdequately(worldIn, position.up(j2), field_181629_a);
/*     */           }
/*     */         } 
/*     */         
/*  99 */         return true;
/*     */       } 
/* 101 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 105 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenForest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */