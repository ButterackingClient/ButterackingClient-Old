/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ public class WorldGenLakes
/*     */   extends WorldGenerator {
/*     */   private Block block;
/*     */   
/*     */   public WorldGenLakes(Block blockIn) {
/*  17 */     this.block = blockIn;
/*     */   }
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  21 */     for (position = position.add(-8, 0, -8); position.getY() > 5 && worldIn.isAirBlock(position); position = position.down());
/*     */ 
/*     */ 
/*     */     
/*  25 */     if (position.getY() <= 4) {
/*  26 */       return false;
/*     */     }
/*  28 */     position = position.down(4);
/*  29 */     boolean[] aboolean = new boolean[2048];
/*  30 */     int i = rand.nextInt(4) + 4;
/*     */     
/*  32 */     for (int j = 0; j < i; j++) {
/*  33 */       double d0 = rand.nextDouble() * 6.0D + 3.0D;
/*  34 */       double d1 = rand.nextDouble() * 4.0D + 2.0D;
/*  35 */       double d2 = rand.nextDouble() * 6.0D + 3.0D;
/*  36 */       double d3 = rand.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
/*  37 */       double d4 = rand.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
/*  38 */       double d5 = rand.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;
/*     */       
/*  40 */       for (int l = 1; l < 15; l++) {
/*  41 */         for (int i1 = 1; i1 < 15; i1++) {
/*  42 */           for (int j1 = 1; j1 < 7; j1++) {
/*  43 */             double d6 = (l - d3) / d0 / 2.0D;
/*  44 */             double d7 = (j1 - d4) / d1 / 2.0D;
/*  45 */             double d8 = (i1 - d5) / d2 / 2.0D;
/*  46 */             double d9 = d6 * d6 + d7 * d7 + d8 * d8;
/*     */             
/*  48 */             if (d9 < 1.0D) {
/*  49 */               aboolean[(l * 16 + i1) * 8 + j1] = true;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  56 */     for (int k1 = 0; k1 < 16; k1++) {
/*  57 */       for (int l2 = 0; l2 < 16; l2++) {
/*  58 */         for (int k = 0; k < 8; k++) {
/*  59 */           boolean flag = (!aboolean[(k1 * 16 + l2) * 8 + k] && ((k1 < 15 && aboolean[((k1 + 1) * 16 + l2) * 8 + k]) || (k1 > 0 && aboolean[((k1 - 1) * 16 + l2) * 8 + k]) || (l2 < 15 && aboolean[(k1 * 16 + l2 + 1) * 8 + k]) || (l2 > 0 && aboolean[(k1 * 16 + l2 - 1) * 8 + k]) || (k < 7 && aboolean[(k1 * 16 + l2) * 8 + k + 1]) || (k > 0 && aboolean[(k1 * 16 + l2) * 8 + k - 1])));
/*     */           
/*  61 */           if (flag) {
/*  62 */             Material material = worldIn.getBlockState(position.add(k1, k, l2)).getBlock().getMaterial();
/*     */             
/*  64 */             if (k >= 4 && material.isLiquid()) {
/*  65 */               return false;
/*     */             }
/*     */             
/*  68 */             if (k < 4 && !material.isSolid() && worldIn.getBlockState(position.add(k1, k, l2)).getBlock() != this.block) {
/*  69 */               return false;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     for (int l1 = 0; l1 < 16; l1++) {
/*  77 */       for (int i3 = 0; i3 < 16; i3++) {
/*  78 */         for (int i4 = 0; i4 < 8; i4++) {
/*  79 */           if (aboolean[(l1 * 16 + i3) * 8 + i4]) {
/*  80 */             worldIn.setBlockState(position.add(l1, i4, i3), (i4 >= 4) ? Blocks.air.getDefaultState() : this.block.getDefaultState(), 2);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     for (int i2 = 0; i2 < 16; i2++) {
/*  87 */       for (int j3 = 0; j3 < 16; j3++) {
/*  88 */         for (int j4 = 4; j4 < 8; j4++) {
/*  89 */           if (aboolean[(i2 * 16 + j3) * 8 + j4]) {
/*  90 */             BlockPos blockpos = position.add(i2, j4 - 1, j3);
/*     */             
/*  92 */             if (worldIn.getBlockState(blockpos).getBlock() == Blocks.dirt && worldIn.getLightFor(EnumSkyBlock.SKY, position.add(i2, j4, j3)) > 0) {
/*  93 */               BiomeGenBase biomegenbase = worldIn.getBiomeGenForCoords(blockpos);
/*     */               
/*  95 */               if (biomegenbase.topBlock.getBlock() == Blocks.mycelium) {
/*  96 */                 worldIn.setBlockState(blockpos, Blocks.mycelium.getDefaultState(), 2);
/*     */               } else {
/*  98 */                 worldIn.setBlockState(blockpos, Blocks.grass.getDefaultState(), 2);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     if (this.block.getMaterial() == Material.lava) {
/* 107 */       for (int j2 = 0; j2 < 16; j2++) {
/* 108 */         for (int k3 = 0; k3 < 16; k3++) {
/* 109 */           for (int k4 = 0; k4 < 8; k4++) {
/* 110 */             boolean flag1 = (!aboolean[(j2 * 16 + k3) * 8 + k4] && ((j2 < 15 && aboolean[((j2 + 1) * 16 + k3) * 8 + k4]) || (j2 > 0 && aboolean[((j2 - 1) * 16 + k3) * 8 + k4]) || (k3 < 15 && aboolean[(j2 * 16 + k3 + 1) * 8 + k4]) || (k3 > 0 && aboolean[(j2 * 16 + k3 - 1) * 8 + k4]) || (k4 < 7 && aboolean[(j2 * 16 + k3) * 8 + k4 + 1]) || (k4 > 0 && aboolean[(j2 * 16 + k3) * 8 + k4 - 1])));
/*     */             
/* 112 */             if (flag1 && (k4 < 4 || rand.nextInt(2) != 0) && worldIn.getBlockState(position.add(j2, k4, k3)).getBlock().getMaterial().isSolid()) {
/* 113 */               worldIn.setBlockState(position.add(j2, k4, k3), Blocks.stone.getDefaultState(), 2);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 120 */     if (this.block.getMaterial() == Material.water) {
/* 121 */       for (int k2 = 0; k2 < 16; k2++) {
/* 122 */         for (int l3 = 0; l3 < 16; l3++) {
/* 123 */           int l4 = 4;
/*     */           
/* 125 */           if (worldIn.canBlockFreezeWater(position.add(k2, l4, l3))) {
/* 126 */             worldIn.setBlockState(position.add(k2, l4, l3), Blocks.ice.getDefaultState(), 2);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 132 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\feature\WorldGenLakes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */