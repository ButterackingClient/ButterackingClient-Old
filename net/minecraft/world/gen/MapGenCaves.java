/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ 
/*     */ public class MapGenCaves
/*     */   extends MapGenBase {
/*     */   protected void func_180703_a(long p_180703_1_, int p_180703_3_, int p_180703_4_, ChunkPrimer p_180703_5_, double p_180703_6_, double p_180703_8_, double p_180703_10_) {
/*  18 */     func_180702_a(p_180703_1_, p_180703_3_, p_180703_4_, p_180703_5_, p_180703_6_, p_180703_8_, p_180703_10_, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
/*     */   }
/*     */   
/*     */   protected void func_180702_a(long p_180702_1_, int p_180702_3_, int p_180702_4_, ChunkPrimer p_180702_5_, double p_180702_6_, double p_180702_8_, double p_180702_10_, float p_180702_12_, float p_180702_13_, float p_180702_14_, int p_180702_15_, int p_180702_16_, double p_180702_17_) {
/*  22 */     double d0 = (p_180702_3_ * 16 + 8);
/*  23 */     double d1 = (p_180702_4_ * 16 + 8);
/*  24 */     float f = 0.0F;
/*  25 */     float f1 = 0.0F;
/*  26 */     Random random = new Random(p_180702_1_);
/*     */     
/*  28 */     if (p_180702_16_ <= 0) {
/*  29 */       int i = this.range * 16 - 16;
/*  30 */       p_180702_16_ = i - random.nextInt(i / 4);
/*     */     } 
/*     */     
/*  33 */     boolean flag2 = false;
/*     */     
/*  35 */     if (p_180702_15_ == -1) {
/*  36 */       p_180702_15_ = p_180702_16_ / 2;
/*  37 */       flag2 = true;
/*     */     } 
/*     */     
/*  40 */     int j = random.nextInt(p_180702_16_ / 2) + p_180702_16_ / 4;
/*     */     
/*  42 */     for (boolean flag = (random.nextInt(6) == 0); p_180702_15_ < p_180702_16_; p_180702_15_++) {
/*  43 */       double d2 = 1.5D + (MathHelper.sin(p_180702_15_ * 3.1415927F / p_180702_16_) * p_180702_12_ * 1.0F);
/*  44 */       double d3 = d2 * p_180702_17_;
/*  45 */       float f2 = MathHelper.cos(p_180702_14_);
/*  46 */       float f3 = MathHelper.sin(p_180702_14_);
/*  47 */       p_180702_6_ += (MathHelper.cos(p_180702_13_) * f2);
/*  48 */       p_180702_8_ += f3;
/*  49 */       p_180702_10_ += (MathHelper.sin(p_180702_13_) * f2);
/*     */       
/*  51 */       if (flag) {
/*  52 */         p_180702_14_ *= 0.92F;
/*     */       } else {
/*  54 */         p_180702_14_ *= 0.7F;
/*     */       } 
/*     */       
/*  57 */       p_180702_14_ += f1 * 0.1F;
/*  58 */       p_180702_13_ += f * 0.1F;
/*  59 */       f1 *= 0.9F;
/*  60 */       f *= 0.75F;
/*  61 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  62 */       f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */       
/*  64 */       if (!flag2 && p_180702_15_ == j && p_180702_12_ > 1.0F && p_180702_16_ > 0) {
/*  65 */         func_180702_a(random.nextLong(), p_180702_3_, p_180702_4_, p_180702_5_, p_180702_6_, p_180702_8_, p_180702_10_, random.nextFloat() * 0.5F + 0.5F, p_180702_13_ - 1.5707964F, p_180702_14_ / 3.0F, p_180702_15_, p_180702_16_, 1.0D);
/*  66 */         func_180702_a(random.nextLong(), p_180702_3_, p_180702_4_, p_180702_5_, p_180702_6_, p_180702_8_, p_180702_10_, random.nextFloat() * 0.5F + 0.5F, p_180702_13_ + 1.5707964F, p_180702_14_ / 3.0F, p_180702_15_, p_180702_16_, 1.0D);
/*     */         
/*     */         return;
/*     */       } 
/*  70 */       if (flag2 || random.nextInt(4) != 0) {
/*  71 */         double d4 = p_180702_6_ - d0;
/*  72 */         double d5 = p_180702_10_ - d1;
/*  73 */         double d6 = (p_180702_16_ - p_180702_15_);
/*  74 */         double d7 = (p_180702_12_ + 2.0F + 16.0F);
/*     */         
/*  76 */         if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7) {
/*     */           return;
/*     */         }
/*     */         
/*  80 */         if (p_180702_6_ >= d0 - 16.0D - d2 * 2.0D && p_180702_10_ >= d1 - 16.0D - d2 * 2.0D && p_180702_6_ <= d0 + 16.0D + d2 * 2.0D && p_180702_10_ <= d1 + 16.0D + d2 * 2.0D) {
/*  81 */           int k2 = MathHelper.floor_double(p_180702_6_ - d2) - p_180702_3_ * 16 - 1;
/*  82 */           int k = MathHelper.floor_double(p_180702_6_ + d2) - p_180702_3_ * 16 + 1;
/*  83 */           int l2 = MathHelper.floor_double(p_180702_8_ - d3) - 1;
/*  84 */           int l = MathHelper.floor_double(p_180702_8_ + d3) + 1;
/*  85 */           int i3 = MathHelper.floor_double(p_180702_10_ - d2) - p_180702_4_ * 16 - 1;
/*  86 */           int i1 = MathHelper.floor_double(p_180702_10_ + d2) - p_180702_4_ * 16 + 1;
/*     */           
/*  88 */           if (k2 < 0) {
/*  89 */             k2 = 0;
/*     */           }
/*     */           
/*  92 */           if (k > 16) {
/*  93 */             k = 16;
/*     */           }
/*     */           
/*  96 */           if (l2 < 1) {
/*  97 */             l2 = 1;
/*     */           }
/*     */           
/* 100 */           if (l > 248) {
/* 101 */             l = 248;
/*     */           }
/*     */           
/* 104 */           if (i3 < 0) {
/* 105 */             i3 = 0;
/*     */           }
/*     */           
/* 108 */           if (i1 > 16) {
/* 109 */             i1 = 16;
/*     */           }
/*     */           
/* 112 */           boolean flag3 = false;
/*     */           
/* 114 */           for (int j1 = k2; !flag3 && j1 < k; j1++) {
/* 115 */             for (int k1 = i3; !flag3 && k1 < i1; k1++) {
/* 116 */               for (int l1 = l + 1; !flag3 && l1 >= l2 - 1; l1--) {
/* 117 */                 if (l1 >= 0 && l1 < 256) {
/* 118 */                   IBlockState iblockstate = p_180702_5_.getBlockState(j1, l1, k1);
/*     */                   
/* 120 */                   if (iblockstate.getBlock() == Blocks.flowing_water || iblockstate.getBlock() == Blocks.water) {
/* 121 */                     flag3 = true;
/*     */                   }
/*     */                   
/* 124 */                   if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1) {
/* 125 */                     l1 = l2;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 132 */           if (!flag3) {
/* 133 */             BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */             
/* 135 */             for (int j3 = k2; j3 < k; j3++) {
/* 136 */               double d10 = ((j3 + p_180702_3_ * 16) + 0.5D - p_180702_6_) / d2;
/*     */               
/* 138 */               for (int i2 = i3; i2 < i1; i2++) {
/* 139 */                 double d8 = ((i2 + p_180702_4_ * 16) + 0.5D - p_180702_10_) / d2;
/* 140 */                 boolean flag1 = false;
/*     */                 
/* 142 */                 if (d10 * d10 + d8 * d8 < 1.0D) {
/* 143 */                   for (int j2 = l; j2 > l2; j2--) {
/* 144 */                     double d9 = ((j2 - 1) + 0.5D - p_180702_8_) / d3;
/*     */                     
/* 146 */                     if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D) {
/* 147 */                       IBlockState iblockstate1 = p_180702_5_.getBlockState(j3, j2, i2);
/* 148 */                       IBlockState iblockstate2 = (IBlockState)Objects.firstNonNull(p_180702_5_.getBlockState(j3, j2 + 1, i2), Blocks.air.getDefaultState());
/*     */                       
/* 150 */                       if (iblockstate1.getBlock() == Blocks.grass || iblockstate1.getBlock() == Blocks.mycelium) {
/* 151 */                         flag1 = true;
/*     */                       }
/*     */                       
/* 154 */                       if (func_175793_a(iblockstate1, iblockstate2)) {
/* 155 */                         if (j2 - 1 < 10) {
/* 156 */                           p_180702_5_.setBlockState(j3, j2, i2, Blocks.lava.getDefaultState());
/*     */                         } else {
/* 158 */                           p_180702_5_.setBlockState(j3, j2, i2, Blocks.air.getDefaultState());
/*     */                           
/* 160 */                           if (iblockstate2.getBlock() == Blocks.sand) {
/* 161 */                             p_180702_5_.setBlockState(j3, j2 + 1, i2, (iblockstate2.getValue((IProperty)BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND) ? Blocks.red_sandstone.getDefaultState() : Blocks.sandstone.getDefaultState());
/*     */                           }
/*     */                           
/* 164 */                           if (flag1 && p_180702_5_.getBlockState(j3, j2 - 1, i2).getBlock() == Blocks.dirt) {
/* 165 */                             blockpos$mutableblockpos.set(j3 + p_180702_3_ * 16, 0, i2 + p_180702_4_ * 16);
/* 166 */                             p_180702_5_.setBlockState(j3, j2 - 1, i2, (this.worldObj.getBiomeGenForCoords((BlockPos)blockpos$mutableblockpos)).topBlock.getBlock().getDefaultState());
/*     */                           } 
/*     */                         } 
/*     */                       }
/*     */                     } 
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 176 */             if (flag2) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean func_175793_a(IBlockState p_175793_1_, IBlockState p_175793_2_) {
/* 186 */     return (p_175793_1_.getBlock() == Blocks.stone) ? true : ((p_175793_1_.getBlock() == Blocks.dirt) ? true : ((p_175793_1_.getBlock() == Blocks.grass) ? true : ((p_175793_1_.getBlock() == Blocks.hardened_clay) ? true : ((p_175793_1_.getBlock() == Blocks.stained_hardened_clay) ? true : ((p_175793_1_.getBlock() == Blocks.sandstone) ? true : ((p_175793_1_.getBlock() == Blocks.red_sandstone) ? true : ((p_175793_1_.getBlock() == Blocks.mycelium) ? true : ((p_175793_1_.getBlock() == Blocks.snow_layer) ? true : (((p_175793_1_.getBlock() == Blocks.sand || p_175793_1_.getBlock() == Blocks.gravel) && p_175793_2_.getBlock().getMaterial() != Material.water))))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {
/* 193 */     int i = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);
/*     */     
/* 195 */     if (this.rand.nextInt(7) != 0) {
/* 196 */       i = 0;
/*     */     }
/*     */     
/* 199 */     for (int j = 0; j < i; j++) {
/* 200 */       double d0 = (chunkX * 16 + this.rand.nextInt(16));
/* 201 */       double d1 = this.rand.nextInt(this.rand.nextInt(120) + 8);
/* 202 */       double d2 = (chunkZ * 16 + this.rand.nextInt(16));
/* 203 */       int k = 1;
/*     */       
/* 205 */       if (this.rand.nextInt(4) == 0) {
/* 206 */         func_180703_a(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2);
/* 207 */         k += this.rand.nextInt(4);
/*     */       } 
/*     */       
/* 210 */       for (int l = 0; l < k; l++) {
/* 211 */         float f = this.rand.nextFloat() * 3.1415927F * 2.0F;
/* 212 */         float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 213 */         float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
/*     */         
/* 215 */         if (this.rand.nextInt(10) == 0) {
/* 216 */           f2 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
/*     */         }
/*     */         
/* 219 */         func_180702_a(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2, f2, f, f1, 0, 0, 1.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\MapGenCaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */