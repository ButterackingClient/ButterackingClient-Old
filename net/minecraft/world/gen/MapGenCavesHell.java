/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ 
/*     */ public class MapGenCavesHell
/*     */   extends MapGenBase {
/*     */   protected void func_180705_a(long p_180705_1_, int p_180705_3_, int p_180705_4_, ChunkPrimer p_180705_5_, double p_180705_6_, double p_180705_8_, double p_180705_10_) {
/*  13 */     func_180704_a(p_180705_1_, p_180705_3_, p_180705_4_, p_180705_5_, p_180705_6_, p_180705_8_, p_180705_10_, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
/*     */   }
/*     */   
/*     */   protected void func_180704_a(long p_180704_1_, int p_180704_3_, int p_180704_4_, ChunkPrimer p_180704_5_, double p_180704_6_, double p_180704_8_, double p_180704_10_, float p_180704_12_, float p_180704_13_, float p_180704_14_, int p_180704_15_, int p_180704_16_, double p_180704_17_) {
/*  17 */     double d0 = (p_180704_3_ * 16 + 8);
/*  18 */     double d1 = (p_180704_4_ * 16 + 8);
/*  19 */     float f = 0.0F;
/*  20 */     float f1 = 0.0F;
/*  21 */     Random random = new Random(p_180704_1_);
/*     */     
/*  23 */     if (p_180704_16_ <= 0) {
/*  24 */       int i = this.range * 16 - 16;
/*  25 */       p_180704_16_ = i - random.nextInt(i / 4);
/*     */     } 
/*     */     
/*  28 */     boolean flag1 = false;
/*     */     
/*  30 */     if (p_180704_15_ == -1) {
/*  31 */       p_180704_15_ = p_180704_16_ / 2;
/*  32 */       flag1 = true;
/*     */     } 
/*     */     
/*  35 */     int j = random.nextInt(p_180704_16_ / 2) + p_180704_16_ / 4;
/*     */     
/*  37 */     for (boolean flag = (random.nextInt(6) == 0); p_180704_15_ < p_180704_16_; p_180704_15_++) {
/*  38 */       double d2 = 1.5D + (MathHelper.sin(p_180704_15_ * 3.1415927F / p_180704_16_) * p_180704_12_ * 1.0F);
/*  39 */       double d3 = d2 * p_180704_17_;
/*  40 */       float f2 = MathHelper.cos(p_180704_14_);
/*  41 */       float f3 = MathHelper.sin(p_180704_14_);
/*  42 */       p_180704_6_ += (MathHelper.cos(p_180704_13_) * f2);
/*  43 */       p_180704_8_ += f3;
/*  44 */       p_180704_10_ += (MathHelper.sin(p_180704_13_) * f2);
/*     */       
/*  46 */       if (flag) {
/*  47 */         p_180704_14_ *= 0.92F;
/*     */       } else {
/*  49 */         p_180704_14_ *= 0.7F;
/*     */       } 
/*     */       
/*  52 */       p_180704_14_ += f1 * 0.1F;
/*  53 */       p_180704_13_ += f * 0.1F;
/*  54 */       f1 *= 0.9F;
/*  55 */       f *= 0.75F;
/*  56 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  57 */       f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */       
/*  59 */       if (!flag1 && p_180704_15_ == j && p_180704_12_ > 1.0F) {
/*  60 */         func_180704_a(random.nextLong(), p_180704_3_, p_180704_4_, p_180704_5_, p_180704_6_, p_180704_8_, p_180704_10_, random.nextFloat() * 0.5F + 0.5F, p_180704_13_ - 1.5707964F, p_180704_14_ / 3.0F, p_180704_15_, p_180704_16_, 1.0D);
/*  61 */         func_180704_a(random.nextLong(), p_180704_3_, p_180704_4_, p_180704_5_, p_180704_6_, p_180704_8_, p_180704_10_, random.nextFloat() * 0.5F + 0.5F, p_180704_13_ + 1.5707964F, p_180704_14_ / 3.0F, p_180704_15_, p_180704_16_, 1.0D);
/*     */         
/*     */         return;
/*     */       } 
/*  65 */       if (flag1 || random.nextInt(4) != 0) {
/*  66 */         double d4 = p_180704_6_ - d0;
/*  67 */         double d5 = p_180704_10_ - d1;
/*  68 */         double d6 = (p_180704_16_ - p_180704_15_);
/*  69 */         double d7 = (p_180704_12_ + 2.0F + 16.0F);
/*     */         
/*  71 */         if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7) {
/*     */           return;
/*     */         }
/*     */         
/*  75 */         if (p_180704_6_ >= d0 - 16.0D - d2 * 2.0D && p_180704_10_ >= d1 - 16.0D - d2 * 2.0D && p_180704_6_ <= d0 + 16.0D + d2 * 2.0D && p_180704_10_ <= d1 + 16.0D + d2 * 2.0D) {
/*  76 */           int j2 = MathHelper.floor_double(p_180704_6_ - d2) - p_180704_3_ * 16 - 1;
/*  77 */           int k = MathHelper.floor_double(p_180704_6_ + d2) - p_180704_3_ * 16 + 1;
/*  78 */           int k2 = MathHelper.floor_double(p_180704_8_ - d3) - 1;
/*  79 */           int l = MathHelper.floor_double(p_180704_8_ + d3) + 1;
/*  80 */           int l2 = MathHelper.floor_double(p_180704_10_ - d2) - p_180704_4_ * 16 - 1;
/*  81 */           int i1 = MathHelper.floor_double(p_180704_10_ + d2) - p_180704_4_ * 16 + 1;
/*     */           
/*  83 */           if (j2 < 0) {
/*  84 */             j2 = 0;
/*     */           }
/*     */           
/*  87 */           if (k > 16) {
/*  88 */             k = 16;
/*     */           }
/*     */           
/*  91 */           if (k2 < 1) {
/*  92 */             k2 = 1;
/*     */           }
/*     */           
/*  95 */           if (l > 120) {
/*  96 */             l = 120;
/*     */           }
/*     */           
/*  99 */           if (l2 < 0) {
/* 100 */             l2 = 0;
/*     */           }
/*     */           
/* 103 */           if (i1 > 16) {
/* 104 */             i1 = 16;
/*     */           }
/*     */           
/* 107 */           boolean flag2 = false;
/*     */           
/* 109 */           for (int j1 = j2; !flag2 && j1 < k; j1++) {
/* 110 */             for (int k1 = l2; !flag2 && k1 < i1; k1++) {
/* 111 */               for (int l1 = l + 1; !flag2 && l1 >= k2 - 1; l1--) {
/* 112 */                 if (l1 >= 0 && l1 < 128) {
/* 113 */                   IBlockState iblockstate = p_180704_5_.getBlockState(j1, l1, k1);
/*     */                   
/* 115 */                   if (iblockstate.getBlock() == Blocks.flowing_lava || iblockstate.getBlock() == Blocks.lava) {
/* 116 */                     flag2 = true;
/*     */                   }
/*     */                   
/* 119 */                   if (l1 != k2 - 1 && j1 != j2 && j1 != k - 1 && k1 != l2 && k1 != i1 - 1) {
/* 120 */                     l1 = k2;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 127 */           if (!flag2) {
/* 128 */             for (int i3 = j2; i3 < k; i3++) {
/* 129 */               double d10 = ((i3 + p_180704_3_ * 16) + 0.5D - p_180704_6_) / d2;
/*     */               
/* 131 */               for (int j3 = l2; j3 < i1; j3++) {
/* 132 */                 double d8 = ((j3 + p_180704_4_ * 16) + 0.5D - p_180704_10_) / d2;
/*     */                 
/* 134 */                 for (int i2 = l; i2 > k2; i2--) {
/* 135 */                   double d9 = ((i2 - 1) + 0.5D - p_180704_8_) / d3;
/*     */                   
/* 137 */                   if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D) {
/* 138 */                     IBlockState iblockstate1 = p_180704_5_.getBlockState(i3, i2, j3);
/*     */                     
/* 140 */                     if (iblockstate1.getBlock() == Blocks.netherrack || iblockstate1.getBlock() == Blocks.dirt || iblockstate1.getBlock() == Blocks.grass) {
/* 141 */                       p_180704_5_.setBlockState(i3, i2, j3, Blocks.air.getDefaultState());
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 148 */             if (flag1) {
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
/* 161 */     int i = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);
/*     */     
/* 163 */     if (this.rand.nextInt(5) != 0) {
/* 164 */       i = 0;
/*     */     }
/*     */     
/* 167 */     for (int j = 0; j < i; j++) {
/* 168 */       double d0 = (chunkX * 16 + this.rand.nextInt(16));
/* 169 */       double d1 = this.rand.nextInt(128);
/* 170 */       double d2 = (chunkZ * 16 + this.rand.nextInt(16));
/* 171 */       int k = 1;
/*     */       
/* 173 */       if (this.rand.nextInt(4) == 0) {
/* 174 */         func_180705_a(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2);
/* 175 */         k += this.rand.nextInt(4);
/*     */       } 
/*     */       
/* 178 */       for (int l = 0; l < k; l++) {
/* 179 */         float f = this.rand.nextFloat() * 3.1415927F * 2.0F;
/* 180 */         float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 181 */         float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
/* 182 */         func_180704_a(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2, f2 * 2.0F, f, f1, 0, 0, 0.5D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\MapGenCavesHell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */