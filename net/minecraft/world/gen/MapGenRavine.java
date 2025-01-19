/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ 
/*     */ public class MapGenRavine
/*     */   extends MapGenBase {
/*  13 */   private float[] field_75046_d = new float[1024];
/*     */   
/*     */   protected void func_180707_a(long p_180707_1_, int p_180707_3_, int p_180707_4_, ChunkPrimer p_180707_5_, double p_180707_6_, double p_180707_8_, double p_180707_10_, float p_180707_12_, float p_180707_13_, float p_180707_14_, int p_180707_15_, int p_180707_16_, double p_180707_17_) {
/*  16 */     Random random = new Random(p_180707_1_);
/*  17 */     double d0 = (p_180707_3_ * 16 + 8);
/*  18 */     double d1 = (p_180707_4_ * 16 + 8);
/*  19 */     float f = 0.0F;
/*  20 */     float f1 = 0.0F;
/*     */     
/*  22 */     if (p_180707_16_ <= 0) {
/*  23 */       int i = this.range * 16 - 16;
/*  24 */       p_180707_16_ = i - random.nextInt(i / 4);
/*     */     } 
/*     */     
/*  27 */     boolean flag1 = false;
/*     */     
/*  29 */     if (p_180707_15_ == -1) {
/*  30 */       p_180707_15_ = p_180707_16_ / 2;
/*  31 */       flag1 = true;
/*     */     } 
/*     */     
/*  34 */     float f2 = 1.0F;
/*     */     
/*  36 */     for (int j = 0; j < 256; j++) {
/*  37 */       if (j == 0 || random.nextInt(3) == 0) {
/*  38 */         f2 = 1.0F + random.nextFloat() * random.nextFloat() * 1.0F;
/*     */       }
/*     */       
/*  41 */       this.field_75046_d[j] = f2 * f2;
/*     */     } 
/*     */     
/*  44 */     for (; p_180707_15_ < p_180707_16_; p_180707_15_++) {
/*  45 */       double d9 = 1.5D + (MathHelper.sin(p_180707_15_ * 3.1415927F / p_180707_16_) * p_180707_12_ * 1.0F);
/*  46 */       double d2 = d9 * p_180707_17_;
/*  47 */       d9 *= random.nextFloat() * 0.25D + 0.75D;
/*  48 */       d2 *= random.nextFloat() * 0.25D + 0.75D;
/*  49 */       float f3 = MathHelper.cos(p_180707_14_);
/*  50 */       float f4 = MathHelper.sin(p_180707_14_);
/*  51 */       p_180707_6_ += (MathHelper.cos(p_180707_13_) * f3);
/*  52 */       p_180707_8_ += f4;
/*  53 */       p_180707_10_ += (MathHelper.sin(p_180707_13_) * f3);
/*  54 */       p_180707_14_ *= 0.7F;
/*  55 */       p_180707_14_ += f1 * 0.05F;
/*  56 */       p_180707_13_ += f * 0.05F;
/*  57 */       f1 *= 0.8F;
/*  58 */       f *= 0.5F;
/*  59 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  60 */       f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */       
/*  62 */       if (flag1 || random.nextInt(4) != 0) {
/*  63 */         double d3 = p_180707_6_ - d0;
/*  64 */         double d4 = p_180707_10_ - d1;
/*  65 */         double d5 = (p_180707_16_ - p_180707_15_);
/*  66 */         double d6 = (p_180707_12_ + 2.0F + 16.0F);
/*     */         
/*  68 */         if (d3 * d3 + d4 * d4 - d5 * d5 > d6 * d6) {
/*     */           return;
/*     */         }
/*     */         
/*  72 */         if (p_180707_6_ >= d0 - 16.0D - d9 * 2.0D && p_180707_10_ >= d1 - 16.0D - d9 * 2.0D && p_180707_6_ <= d0 + 16.0D + d9 * 2.0D && p_180707_10_ <= d1 + 16.0D + d9 * 2.0D) {
/*  73 */           int k2 = MathHelper.floor_double(p_180707_6_ - d9) - p_180707_3_ * 16 - 1;
/*  74 */           int k = MathHelper.floor_double(p_180707_6_ + d9) - p_180707_3_ * 16 + 1;
/*  75 */           int l2 = MathHelper.floor_double(p_180707_8_ - d2) - 1;
/*  76 */           int l = MathHelper.floor_double(p_180707_8_ + d2) + 1;
/*  77 */           int i3 = MathHelper.floor_double(p_180707_10_ - d9) - p_180707_4_ * 16 - 1;
/*  78 */           int i1 = MathHelper.floor_double(p_180707_10_ + d9) - p_180707_4_ * 16 + 1;
/*     */           
/*  80 */           if (k2 < 0) {
/*  81 */             k2 = 0;
/*     */           }
/*     */           
/*  84 */           if (k > 16) {
/*  85 */             k = 16;
/*     */           }
/*     */           
/*  88 */           if (l2 < 1) {
/*  89 */             l2 = 1;
/*     */           }
/*     */           
/*  92 */           if (l > 248) {
/*  93 */             l = 248;
/*     */           }
/*     */           
/*  96 */           if (i3 < 0) {
/*  97 */             i3 = 0;
/*     */           }
/*     */           
/* 100 */           if (i1 > 16) {
/* 101 */             i1 = 16;
/*     */           }
/*     */           
/* 104 */           boolean flag2 = false;
/*     */           
/* 106 */           for (int j1 = k2; !flag2 && j1 < k; j1++) {
/* 107 */             for (int k1 = i3; !flag2 && k1 < i1; k1++) {
/* 108 */               for (int l1 = l + 1; !flag2 && l1 >= l2 - 1; l1--) {
/* 109 */                 if (l1 >= 0 && l1 < 256) {
/* 110 */                   IBlockState iblockstate = p_180707_5_.getBlockState(j1, l1, k1);
/*     */                   
/* 112 */                   if (iblockstate.getBlock() == Blocks.flowing_water || iblockstate.getBlock() == Blocks.water) {
/* 113 */                     flag2 = true;
/*     */                   }
/*     */                   
/* 116 */                   if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1) {
/* 117 */                     l1 = l2;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 124 */           if (!flag2) {
/* 125 */             BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */             
/* 127 */             for (int j3 = k2; j3 < k; j3++) {
/* 128 */               double d10 = ((j3 + p_180707_3_ * 16) + 0.5D - p_180707_6_) / d9;
/*     */               
/* 130 */               for (int i2 = i3; i2 < i1; i2++) {
/* 131 */                 double d7 = ((i2 + p_180707_4_ * 16) + 0.5D - p_180707_10_) / d9;
/* 132 */                 boolean flag = false;
/*     */                 
/* 134 */                 if (d10 * d10 + d7 * d7 < 1.0D) {
/* 135 */                   for (int j2 = l; j2 > l2; j2--) {
/* 136 */                     double d8 = ((j2 - 1) + 0.5D - p_180707_8_) / d2;
/*     */                     
/* 138 */                     if ((d10 * d10 + d7 * d7) * this.field_75046_d[j2 - 1] + d8 * d8 / 6.0D < 1.0D) {
/* 139 */                       IBlockState iblockstate1 = p_180707_5_.getBlockState(j3, j2, i2);
/*     */                       
/* 141 */                       if (iblockstate1.getBlock() == Blocks.grass) {
/* 142 */                         flag = true;
/*     */                       }
/*     */                       
/* 145 */                       if (iblockstate1.getBlock() == Blocks.stone || iblockstate1.getBlock() == Blocks.dirt || iblockstate1.getBlock() == Blocks.grass) {
/* 146 */                         if (j2 - 1 < 10) {
/* 147 */                           p_180707_5_.setBlockState(j3, j2, i2, Blocks.flowing_lava.getDefaultState());
/*     */                         } else {
/* 149 */                           p_180707_5_.setBlockState(j3, j2, i2, Blocks.air.getDefaultState());
/*     */                           
/* 151 */                           if (flag && p_180707_5_.getBlockState(j3, j2 - 1, i2).getBlock() == Blocks.dirt) {
/* 152 */                             blockpos$mutableblockpos.set(j3 + p_180707_3_ * 16, 0, i2 + p_180707_4_ * 16);
/* 153 */                             p_180707_5_.setBlockState(j3, j2 - 1, i2, (this.worldObj.getBiomeGenForCoords((BlockPos)blockpos$mutableblockpos)).topBlock);
/*     */                           } 
/*     */                         } 
/*     */                       }
/*     */                     } 
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 163 */             if (flag1) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {
/* 176 */     if (this.rand.nextInt(50) == 0) {
/* 177 */       double d0 = (chunkX * 16 + this.rand.nextInt(16));
/* 178 */       double d1 = (this.rand.nextInt(this.rand.nextInt(40) + 8) + 20);
/* 179 */       double d2 = (chunkZ * 16 + this.rand.nextInt(16));
/* 180 */       int i = 1;
/*     */       
/* 182 */       for (int j = 0; j < i; j++) {
/* 183 */         float f = this.rand.nextFloat() * 3.1415927F * 2.0F;
/* 184 */         float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 185 */         float f2 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
/* 186 */         func_180707_a(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2, f2, f, f1, 0, 0, 3.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\MapGenRavine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */