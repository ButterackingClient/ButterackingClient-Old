/*     */ package net.minecraft.world.gen.layer;
/*     */ 
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ public class GenLayerBiomeEdge extends GenLayer {
/*     */   public GenLayerBiomeEdge(long p_i45475_1_, GenLayer p_i45475_3_) {
/*   7 */     super(p_i45475_1_);
/*   8 */     this.parent = p_i45475_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/*  16 */     int[] aint = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
/*  17 */     int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
/*     */     
/*  19 */     for (int i = 0; i < areaHeight; i++) {
/*  20 */       for (int j = 0; j < areaWidth; j++) {
/*  21 */         initChunkSeed((j + areaX), (i + areaY));
/*  22 */         int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
/*     */         
/*  24 */         if (!replaceBiomeEdgeIfNecessary(aint, aint1, j, i, areaWidth, k, BiomeGenBase.extremeHills.biomeID, BiomeGenBase.extremeHillsEdge.biomeID) && !replaceBiomeEdge(aint, aint1, j, i, areaWidth, k, BiomeGenBase.mesaPlateau_F.biomeID, BiomeGenBase.mesa.biomeID) && !replaceBiomeEdge(aint, aint1, j, i, areaWidth, k, BiomeGenBase.mesaPlateau.biomeID, BiomeGenBase.mesa.biomeID) && !replaceBiomeEdge(aint, aint1, j, i, areaWidth, k, BiomeGenBase.megaTaiga.biomeID, BiomeGenBase.taiga.biomeID)) {
/*  25 */           if (k == BiomeGenBase.desert.biomeID) {
/*  26 */             int l1 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/*  27 */             int i2 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/*  28 */             int j2 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/*  29 */             int k2 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/*     */             
/*  31 */             if (l1 != BiomeGenBase.icePlains.biomeID && i2 != BiomeGenBase.icePlains.biomeID && j2 != BiomeGenBase.icePlains.biomeID && k2 != BiomeGenBase.icePlains.biomeID) {
/*  32 */               aint1[j + i * areaWidth] = k;
/*     */             } else {
/*  34 */               aint1[j + i * areaWidth] = BiomeGenBase.extremeHillsPlus.biomeID;
/*     */             } 
/*  36 */           } else if (k == BiomeGenBase.swampland.biomeID) {
/*  37 */             int l = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/*  38 */             int i1 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/*  39 */             int j1 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/*  40 */             int k1 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/*     */             
/*  42 */             if (l != BiomeGenBase.desert.biomeID && i1 != BiomeGenBase.desert.biomeID && j1 != BiomeGenBase.desert.biomeID && k1 != BiomeGenBase.desert.biomeID && l != BiomeGenBase.coldTaiga.biomeID && i1 != BiomeGenBase.coldTaiga.biomeID && j1 != BiomeGenBase.coldTaiga.biomeID && k1 != BiomeGenBase.coldTaiga.biomeID && l != BiomeGenBase.icePlains.biomeID && i1 != BiomeGenBase.icePlains.biomeID && j1 != BiomeGenBase.icePlains.biomeID && k1 != BiomeGenBase.icePlains.biomeID) {
/*  43 */               if (l != BiomeGenBase.jungle.biomeID && k1 != BiomeGenBase.jungle.biomeID && i1 != BiomeGenBase.jungle.biomeID && j1 != BiomeGenBase.jungle.biomeID) {
/*  44 */                 aint1[j + i * areaWidth] = k;
/*     */               } else {
/*  46 */                 aint1[j + i * areaWidth] = BiomeGenBase.jungleEdge.biomeID;
/*     */               } 
/*     */             } else {
/*  49 */               aint1[j + i * areaWidth] = BiomeGenBase.plains.biomeID;
/*     */             } 
/*     */           } else {
/*  52 */             aint1[j + i * areaWidth] = k;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     return aint1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean replaceBiomeEdgeIfNecessary(int[] p_151636_1_, int[] p_151636_2_, int p_151636_3_, int p_151636_4_, int p_151636_5_, int p_151636_6_, int p_151636_7_, int p_151636_8_) {
/*  65 */     if (!biomesEqualOrMesaPlateau(p_151636_6_, p_151636_7_)) {
/*  66 */       return false;
/*     */     }
/*  68 */     int i = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 - 1) * (p_151636_5_ + 2)];
/*  69 */     int j = p_151636_1_[p_151636_3_ + 1 + 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
/*  70 */     int k = p_151636_1_[p_151636_3_ + 1 - 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
/*  71 */     int l = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 + 1) * (p_151636_5_ + 2)];
/*     */     
/*  73 */     if (canBiomesBeNeighbors(i, p_151636_7_) && canBiomesBeNeighbors(j, p_151636_7_) && canBiomesBeNeighbors(k, p_151636_7_) && canBiomesBeNeighbors(l, p_151636_7_)) {
/*  74 */       p_151636_2_[p_151636_3_ + p_151636_4_ * p_151636_5_] = p_151636_6_;
/*     */     } else {
/*  76 */       p_151636_2_[p_151636_3_ + p_151636_4_ * p_151636_5_] = p_151636_8_;
/*     */     } 
/*     */     
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean replaceBiomeEdge(int[] p_151635_1_, int[] p_151635_2_, int p_151635_3_, int p_151635_4_, int p_151635_5_, int p_151635_6_, int p_151635_7_, int p_151635_8_) {
/*  87 */     if (p_151635_6_ != p_151635_7_) {
/*  88 */       return false;
/*     */     }
/*  90 */     int i = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 - 1) * (p_151635_5_ + 2)];
/*  91 */     int j = p_151635_1_[p_151635_3_ + 1 + 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
/*  92 */     int k = p_151635_1_[p_151635_3_ + 1 - 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
/*  93 */     int l = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 + 1) * (p_151635_5_ + 2)];
/*     */     
/*  95 */     if (biomesEqualOrMesaPlateau(i, p_151635_7_) && biomesEqualOrMesaPlateau(j, p_151635_7_) && biomesEqualOrMesaPlateau(k, p_151635_7_) && biomesEqualOrMesaPlateau(l, p_151635_7_)) {
/*  96 */       p_151635_2_[p_151635_3_ + p_151635_4_ * p_151635_5_] = p_151635_6_;
/*     */     } else {
/*  98 */       p_151635_2_[p_151635_3_ + p_151635_4_ * p_151635_5_] = p_151635_8_;
/*     */     } 
/*     */     
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canBiomesBeNeighbors(int p_151634_1_, int p_151634_2_) {
/* 110 */     if (biomesEqualOrMesaPlateau(p_151634_1_, p_151634_2_)) {
/* 111 */       return true;
/*     */     }
/* 113 */     BiomeGenBase biomegenbase = BiomeGenBase.getBiome(p_151634_1_);
/* 114 */     BiomeGenBase biomegenbase1 = BiomeGenBase.getBiome(p_151634_2_);
/*     */     
/* 116 */     if (biomegenbase != null && biomegenbase1 != null) {
/* 117 */       BiomeGenBase.TempCategory biomegenbase$tempcategory = biomegenbase.getTempCategory();
/* 118 */       BiomeGenBase.TempCategory biomegenbase$tempcategory1 = biomegenbase1.getTempCategory();
/* 119 */       return !(biomegenbase$tempcategory != biomegenbase$tempcategory1 && biomegenbase$tempcategory != BiomeGenBase.TempCategory.MEDIUM && biomegenbase$tempcategory1 != BiomeGenBase.TempCategory.MEDIUM);
/*     */     } 
/* 121 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerBiomeEdge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */