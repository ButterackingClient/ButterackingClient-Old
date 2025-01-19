/*     */ package net.minecraft.world.gen.layer;
/*     */ 
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GenLayerHills extends GenLayer {
/*   8 */   private static final Logger logger = LogManager.getLogger();
/*     */   private GenLayer field_151628_d;
/*     */   
/*     */   public GenLayerHills(long p_i45479_1_, GenLayer p_i45479_3_, GenLayer p_i45479_4_) {
/*  12 */     super(p_i45479_1_);
/*  13 */     this.parent = p_i45479_3_;
/*  14 */     this.field_151628_d = p_i45479_4_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/*  22 */     int[] aint = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
/*  23 */     int[] aint1 = this.field_151628_d.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
/*  24 */     int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);
/*     */     
/*  26 */     for (int i = 0; i < areaHeight; i++) {
/*  27 */       for (int j = 0; j < areaWidth; j++) {
/*  28 */         initChunkSeed((j + areaX), (i + areaY));
/*  29 */         int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
/*  30 */         int l = aint1[j + 1 + (i + 1) * (areaWidth + 2)];
/*  31 */         boolean flag = ((l - 2) % 29 == 0);
/*     */         
/*  33 */         if (k > 255) {
/*  34 */           logger.debug("old! " + k);
/*     */         }
/*     */         
/*  37 */         if (k != 0 && l >= 2 && (l - 2) % 29 == 1 && k < 128) {
/*  38 */           if (BiomeGenBase.getBiome(k + 128) != null) {
/*  39 */             aint2[j + i * areaWidth] = k + 128;
/*     */           } else {
/*  41 */             aint2[j + i * areaWidth] = k;
/*     */           } 
/*  43 */         } else if (nextInt(3) != 0 && !flag) {
/*  44 */           aint2[j + i * areaWidth] = k;
/*     */         } else {
/*  46 */           int i1 = k;
/*     */           
/*  48 */           if (k == BiomeGenBase.desert.biomeID) {
/*  49 */             i1 = BiomeGenBase.desertHills.biomeID;
/*  50 */           } else if (k == BiomeGenBase.forest.biomeID) {
/*  51 */             i1 = BiomeGenBase.forestHills.biomeID;
/*  52 */           } else if (k == BiomeGenBase.birchForest.biomeID) {
/*  53 */             i1 = BiomeGenBase.birchForestHills.biomeID;
/*  54 */           } else if (k == BiomeGenBase.roofedForest.biomeID) {
/*  55 */             i1 = BiomeGenBase.plains.biomeID;
/*  56 */           } else if (k == BiomeGenBase.taiga.biomeID) {
/*  57 */             i1 = BiomeGenBase.taigaHills.biomeID;
/*  58 */           } else if (k == BiomeGenBase.megaTaiga.biomeID) {
/*  59 */             i1 = BiomeGenBase.megaTaigaHills.biomeID;
/*  60 */           } else if (k == BiomeGenBase.coldTaiga.biomeID) {
/*  61 */             i1 = BiomeGenBase.coldTaigaHills.biomeID;
/*  62 */           } else if (k == BiomeGenBase.plains.biomeID) {
/*  63 */             if (nextInt(3) == 0) {
/*  64 */               i1 = BiomeGenBase.forestHills.biomeID;
/*     */             } else {
/*  66 */               i1 = BiomeGenBase.forest.biomeID;
/*     */             } 
/*  68 */           } else if (k == BiomeGenBase.icePlains.biomeID) {
/*  69 */             i1 = BiomeGenBase.iceMountains.biomeID;
/*  70 */           } else if (k == BiomeGenBase.jungle.biomeID) {
/*  71 */             i1 = BiomeGenBase.jungleHills.biomeID;
/*  72 */           } else if (k == BiomeGenBase.ocean.biomeID) {
/*  73 */             i1 = BiomeGenBase.deepOcean.biomeID;
/*  74 */           } else if (k == BiomeGenBase.extremeHills.biomeID) {
/*  75 */             i1 = BiomeGenBase.extremeHillsPlus.biomeID;
/*  76 */           } else if (k == BiomeGenBase.savanna.biomeID) {
/*  77 */             i1 = BiomeGenBase.savannaPlateau.biomeID;
/*  78 */           } else if (biomesEqualOrMesaPlateau(k, BiomeGenBase.mesaPlateau_F.biomeID)) {
/*  79 */             i1 = BiomeGenBase.mesa.biomeID;
/*  80 */           } else if (k == BiomeGenBase.deepOcean.biomeID && nextInt(3) == 0) {
/*  81 */             int j1 = nextInt(2);
/*     */             
/*  83 */             if (j1 == 0) {
/*  84 */               i1 = BiomeGenBase.plains.biomeID;
/*     */             } else {
/*  86 */               i1 = BiomeGenBase.forest.biomeID;
/*     */             } 
/*     */           } 
/*     */           
/*  90 */           if (flag && i1 != k) {
/*  91 */             if (BiomeGenBase.getBiome(i1 + 128) != null) {
/*  92 */               i1 += 128;
/*     */             } else {
/*  94 */               i1 = k;
/*     */             } 
/*     */           }
/*     */           
/*  98 */           if (i1 == k) {
/*  99 */             aint2[j + i * areaWidth] = k;
/*     */           } else {
/* 101 */             int k2 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/* 102 */             int k1 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/* 103 */             int l1 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/* 104 */             int i2 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/* 105 */             int j2 = 0;
/*     */             
/* 107 */             if (biomesEqualOrMesaPlateau(k2, k)) {
/* 108 */               j2++;
/*     */             }
/*     */             
/* 111 */             if (biomesEqualOrMesaPlateau(k1, k)) {
/* 112 */               j2++;
/*     */             }
/*     */             
/* 115 */             if (biomesEqualOrMesaPlateau(l1, k)) {
/* 116 */               j2++;
/*     */             }
/*     */             
/* 119 */             if (biomesEqualOrMesaPlateau(i2, k)) {
/* 120 */               j2++;
/*     */             }
/*     */             
/* 123 */             if (j2 >= 3) {
/* 124 */               aint2[j + i * areaWidth] = i1;
/*     */             } else {
/* 126 */               aint2[j + i * areaWidth] = k;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 133 */     return aint2;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerHills.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */