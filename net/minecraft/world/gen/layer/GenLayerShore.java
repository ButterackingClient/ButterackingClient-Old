/*     */ package net.minecraft.world.gen.layer;
/*     */ 
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.biome.BiomeGenJungle;
/*     */ 
/*     */ public class GenLayerShore
/*     */   extends GenLayer {
/*     */   public GenLayerShore(long p_i2130_1_, GenLayer p_i2130_3_) {
/*   9 */     super(p_i2130_1_);
/*  10 */     this.parent = p_i2130_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/*  18 */     int[] aint = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
/*  19 */     int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
/*     */     
/*  21 */     for (int i = 0; i < areaHeight; i++) {
/*  22 */       for (int j = 0; j < areaWidth; j++) {
/*  23 */         initChunkSeed((j + areaX), (i + areaY));
/*  24 */         int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
/*  25 */         BiomeGenBase biomegenbase = BiomeGenBase.getBiome(k);
/*     */         
/*  27 */         if (k == BiomeGenBase.mushroomIsland.biomeID) {
/*  28 */           int j2 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/*  29 */           int i3 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/*  30 */           int l3 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/*  31 */           int k4 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/*     */           
/*  33 */           if (j2 != BiomeGenBase.ocean.biomeID && i3 != BiomeGenBase.ocean.biomeID && l3 != BiomeGenBase.ocean.biomeID && k4 != BiomeGenBase.ocean.biomeID) {
/*  34 */             aint1[j + i * areaWidth] = k;
/*     */           } else {
/*  36 */             aint1[j + i * areaWidth] = BiomeGenBase.mushroomIslandShore.biomeID;
/*     */           } 
/*  38 */         } else if (biomegenbase != null && biomegenbase.getBiomeClass() == BiomeGenJungle.class) {
/*  39 */           int i2 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/*  40 */           int l2 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/*  41 */           int k3 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/*  42 */           int j4 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/*     */           
/*  44 */           if (func_151631_c(i2) && func_151631_c(l2) && func_151631_c(k3) && func_151631_c(j4)) {
/*  45 */             if (!isBiomeOceanic(i2) && !isBiomeOceanic(l2) && !isBiomeOceanic(k3) && !isBiomeOceanic(j4)) {
/*  46 */               aint1[j + i * areaWidth] = k;
/*     */             } else {
/*  48 */               aint1[j + i * areaWidth] = BiomeGenBase.beach.biomeID;
/*     */             } 
/*     */           } else {
/*  51 */             aint1[j + i * areaWidth] = BiomeGenBase.jungleEdge.biomeID;
/*     */           } 
/*  53 */         } else if (k != BiomeGenBase.extremeHills.biomeID && k != BiomeGenBase.extremeHillsPlus.biomeID && k != BiomeGenBase.extremeHillsEdge.biomeID) {
/*  54 */           if (biomegenbase != null && biomegenbase.isSnowyBiome()) {
/*  55 */             func_151632_a(aint, aint1, j, i, areaWidth, k, BiomeGenBase.coldBeach.biomeID);
/*  56 */           } else if (k != BiomeGenBase.mesa.biomeID && k != BiomeGenBase.mesaPlateau_F.biomeID) {
/*  57 */             if (k != BiomeGenBase.ocean.biomeID && k != BiomeGenBase.deepOcean.biomeID && k != BiomeGenBase.river.biomeID && k != BiomeGenBase.swampland.biomeID) {
/*  58 */               int l1 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/*  59 */               int k2 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/*  60 */               int j3 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/*  61 */               int i4 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/*     */               
/*  63 */               if (!isBiomeOceanic(l1) && !isBiomeOceanic(k2) && !isBiomeOceanic(j3) && !isBiomeOceanic(i4)) {
/*  64 */                 aint1[j + i * areaWidth] = k;
/*     */               } else {
/*  66 */                 aint1[j + i * areaWidth] = BiomeGenBase.beach.biomeID;
/*     */               } 
/*     */             } else {
/*  69 */               aint1[j + i * areaWidth] = k;
/*     */             } 
/*     */           } else {
/*  72 */             int l = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/*  73 */             int i1 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/*  74 */             int j1 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/*  75 */             int k1 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/*     */             
/*  77 */             if (!isBiomeOceanic(l) && !isBiomeOceanic(i1) && !isBiomeOceanic(j1) && !isBiomeOceanic(k1)) {
/*  78 */               if (func_151633_d(l) && func_151633_d(i1) && func_151633_d(j1) && func_151633_d(k1)) {
/*  79 */                 aint1[j + i * areaWidth] = k;
/*     */               } else {
/*  81 */                 aint1[j + i * areaWidth] = BiomeGenBase.desert.biomeID;
/*     */               } 
/*     */             } else {
/*  84 */               aint1[j + i * areaWidth] = k;
/*     */             } 
/*     */           } 
/*     */         } else {
/*  88 */           func_151632_a(aint, aint1, j, i, areaWidth, k, BiomeGenBase.stoneBeach.biomeID);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     return aint1;
/*     */   }
/*     */   
/*     */   private void func_151632_a(int[] p_151632_1_, int[] p_151632_2_, int p_151632_3_, int p_151632_4_, int p_151632_5_, int p_151632_6_, int p_151632_7_) {
/*  97 */     if (isBiomeOceanic(p_151632_6_)) {
/*  98 */       p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
/*     */     } else {
/* 100 */       int i = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 - 1) * (p_151632_5_ + 2)];
/* 101 */       int j = p_151632_1_[p_151632_3_ + 1 + 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
/* 102 */       int k = p_151632_1_[p_151632_3_ + 1 - 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
/* 103 */       int l = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 + 1) * (p_151632_5_ + 2)];
/*     */       
/* 105 */       if (!isBiomeOceanic(i) && !isBiomeOceanic(j) && !isBiomeOceanic(k) && !isBiomeOceanic(l)) {
/* 106 */         p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
/*     */       } else {
/* 108 */         p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_7_;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean func_151631_c(int p_151631_1_) {
/* 114 */     return (BiomeGenBase.getBiome(p_151631_1_) != null && BiomeGenBase.getBiome(p_151631_1_).getBiomeClass() == BiomeGenJungle.class) ? true : (!(p_151631_1_ != BiomeGenBase.jungleEdge.biomeID && p_151631_1_ != BiomeGenBase.jungle.biomeID && p_151631_1_ != BiomeGenBase.jungleHills.biomeID && p_151631_1_ != BiomeGenBase.forest.biomeID && p_151631_1_ != BiomeGenBase.taiga.biomeID && !isBiomeOceanic(p_151631_1_)));
/*     */   }
/*     */   
/*     */   private boolean func_151633_d(int p_151633_1_) {
/* 118 */     return BiomeGenBase.getBiome(p_151633_1_) instanceof net.minecraft.world.biome.BiomeGenMesa;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerShore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */