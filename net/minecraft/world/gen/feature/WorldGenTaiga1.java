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
/*     */ public class WorldGenTaiga1 extends WorldGenAbstractTree {
/*  17 */   private static final IBlockState field_181636_a = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE);
/*  18 */   private static final IBlockState field_181637_b = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */   
/*     */   public WorldGenTaiga1() {
/*  21 */     super(false);
/*     */   }
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  25 */     int i = rand.nextInt(5) + 7;
/*  26 */     int j = i - rand.nextInt(2) - 3;
/*  27 */     int k = i - j;
/*  28 */     int l = 1 + rand.nextInt(k + 1);
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
/*  46 */               if (!func_150523_a(worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, i1, l1)).getBlock())) {
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
/*  63 */         int k2 = 0;
/*     */         
/*  65 */         for (int l2 = position.getY() + i; l2 >= position.getY() + j; l2--) {
/*  66 */           for (int j3 = position.getX() - k2; j3 <= position.getX() + k2; j3++) {
/*  67 */             int k3 = j3 - position.getX();
/*     */             
/*  69 */             for (int i2 = position.getZ() - k2; i2 <= position.getZ() + k2; i2++) {
/*  70 */               int j2 = i2 - position.getZ();
/*     */               
/*  72 */               if (Math.abs(k3) != k2 || Math.abs(j2) != k2 || k2 <= 0) {
/*  73 */                 BlockPos blockpos = new BlockPos(j3, l2, i2);
/*     */                 
/*  75 */                 if (!worldIn.getBlockState(blockpos).getBlock().isFullBlock()) {
/*  76 */                   setBlockAndNotifyAdequately(worldIn, blockpos, field_181637_b);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/*  82 */           if (k2 >= 1 && l2 == position.getY() + j + 1) {
/*  83 */             k2--;
/*  84 */           } else if (k2 < l) {
/*  85 */             k2++;
/*     */           } 
/*     */         } 
/*     */         
/*  89 */         for (int i3 = 0; i3 < i - 1; i3++) {
/*  90 */           Block block1 = worldIn.getBlockState(position.up(i3)).getBlock();
/*     */           
/*  92 */           if (block1.getMaterial() == Material.air || block1.getMaterial() == Material.leaves) {
/*  93 */             setBlockAndNotifyAdequately(worldIn, position.up(i3), field_181636_a);
/*     */           }
/*     */         } 
/*     */         
/*  97 */         return true;
/*     */       } 
/*  99 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenTaiga1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */