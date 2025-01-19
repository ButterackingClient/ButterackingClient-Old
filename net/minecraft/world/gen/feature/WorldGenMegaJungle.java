/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockVine;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenMegaJungle extends WorldGenHugeTrees {
/*     */   public WorldGenMegaJungle(boolean p_i46448_1_, int p_i46448_2_, int p_i46448_3_, IBlockState p_i46448_4_, IBlockState p_i46448_5_) {
/*  15 */     super(p_i46448_1_, p_i46448_2_, p_i46448_3_, p_i46448_4_, p_i46448_5_);
/*     */   }
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  19 */     int i = func_150533_a(rand);
/*     */     
/*  21 */     if (!func_175929_a(worldIn, rand, position, i)) {
/*  22 */       return false;
/*     */     }
/*  24 */     func_175930_c(worldIn, position.up(i), 2);
/*     */     
/*  26 */     for (int j = position.getY() + i - 2 - rand.nextInt(4); j > position.getY() + i / 2; j -= 2 + rand.nextInt(4)) {
/*  27 */       float f = rand.nextFloat() * 3.1415927F * 2.0F;
/*  28 */       int k = position.getX() + (int)(0.5F + MathHelper.cos(f) * 4.0F);
/*  29 */       int l = position.getZ() + (int)(0.5F + MathHelper.sin(f) * 4.0F);
/*     */       
/*  31 */       for (int i1 = 0; i1 < 5; i1++) {
/*  32 */         k = position.getX() + (int)(1.5F + MathHelper.cos(f) * i1);
/*  33 */         l = position.getZ() + (int)(1.5F + MathHelper.sin(f) * i1);
/*  34 */         setBlockAndNotifyAdequately(worldIn, new BlockPos(k, j - 3 + i1 / 2, l), this.woodMetadata);
/*     */       } 
/*     */       
/*  37 */       int j2 = 1 + rand.nextInt(2);
/*  38 */       int j1 = j;
/*     */       
/*  40 */       for (int k1 = j - j2; k1 <= j1; k1++) {
/*  41 */         int l1 = k1 - j1;
/*  42 */         func_175928_b(worldIn, new BlockPos(k, k1, l), 1 - l1);
/*     */       } 
/*     */     } 
/*     */     
/*  46 */     for (int i2 = 0; i2 < i; i2++) {
/*  47 */       BlockPos blockpos = position.up(i2);
/*     */       
/*  49 */       if (func_150523_a(worldIn.getBlockState(blockpos).getBlock())) {
/*  50 */         setBlockAndNotifyAdequately(worldIn, blockpos, this.woodMetadata);
/*     */         
/*  52 */         if (i2 > 0) {
/*  53 */           func_181632_a(worldIn, rand, blockpos.west(), BlockVine.EAST);
/*  54 */           func_181632_a(worldIn, rand, blockpos.north(), BlockVine.SOUTH);
/*     */         } 
/*     */       } 
/*     */       
/*  58 */       if (i2 < i - 1) {
/*  59 */         BlockPos blockpos1 = blockpos.east();
/*     */         
/*  61 */         if (func_150523_a(worldIn.getBlockState(blockpos1).getBlock())) {
/*  62 */           setBlockAndNotifyAdequately(worldIn, blockpos1, this.woodMetadata);
/*     */           
/*  64 */           if (i2 > 0) {
/*  65 */             func_181632_a(worldIn, rand, blockpos1.east(), BlockVine.WEST);
/*  66 */             func_181632_a(worldIn, rand, blockpos1.north(), BlockVine.SOUTH);
/*     */           } 
/*     */         } 
/*     */         
/*  70 */         BlockPos blockpos2 = blockpos.south().east();
/*     */         
/*  72 */         if (func_150523_a(worldIn.getBlockState(blockpos2).getBlock())) {
/*  73 */           setBlockAndNotifyAdequately(worldIn, blockpos2, this.woodMetadata);
/*     */           
/*  75 */           if (i2 > 0) {
/*  76 */             func_181632_a(worldIn, rand, blockpos2.east(), BlockVine.WEST);
/*  77 */             func_181632_a(worldIn, rand, blockpos2.south(), BlockVine.NORTH);
/*     */           } 
/*     */         } 
/*     */         
/*  81 */         BlockPos blockpos3 = blockpos.south();
/*     */         
/*  83 */         if (func_150523_a(worldIn.getBlockState(blockpos3).getBlock())) {
/*  84 */           setBlockAndNotifyAdequately(worldIn, blockpos3, this.woodMetadata);
/*     */           
/*  86 */           if (i2 > 0) {
/*  87 */             func_181632_a(worldIn, rand, blockpos3.west(), BlockVine.EAST);
/*  88 */             func_181632_a(worldIn, rand, blockpos3.south(), BlockVine.NORTH);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181632_a(World p_181632_1_, Random p_181632_2_, BlockPos p_181632_3_, PropertyBool p_181632_4_) {
/*  99 */     if (p_181632_2_.nextInt(3) > 0 && p_181632_1_.isAirBlock(p_181632_3_)) {
/* 100 */       setBlockAndNotifyAdequately(p_181632_1_, p_181632_3_, Blocks.vine.getDefaultState().withProperty((IProperty)p_181632_4_, Boolean.valueOf(true)));
/*     */     }
/*     */   }
/*     */   
/*     */   private void func_175930_c(World worldIn, BlockPos p_175930_2_, int p_175930_3_) {
/* 105 */     int i = 2;
/*     */     
/* 107 */     for (int j = -i; j <= 0; j++)
/* 108 */       func_175925_a(worldIn, p_175930_2_.up(j), p_175930_3_ + 1 - j); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenMegaJungle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */