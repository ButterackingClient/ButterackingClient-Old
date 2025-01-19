/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import net.minecraft.world.WorldType;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.minecraft.world.gen.ChunkProviderSettings;
/*    */ 
/*    */ public class GenLayerBiome extends GenLayer {
/*  8 */   private BiomeGenBase[] field_151623_c = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.savanna, BiomeGenBase.savanna, BiomeGenBase.plains };
/*  9 */   private BiomeGenBase[] field_151621_d = new BiomeGenBase[] { BiomeGenBase.forest, BiomeGenBase.roofedForest, BiomeGenBase.extremeHills, BiomeGenBase.plains, BiomeGenBase.birchForest, BiomeGenBase.swampland };
/* 10 */   private BiomeGenBase[] field_151622_e = new BiomeGenBase[] { BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.taiga, BiomeGenBase.plains };
/* 11 */   private BiomeGenBase[] field_151620_f = new BiomeGenBase[] { BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.icePlains, BiomeGenBase.coldTaiga };
/*    */   private final ChunkProviderSettings field_175973_g;
/*    */   
/*    */   public GenLayerBiome(long p_i45560_1_, GenLayer p_i45560_3_, WorldType p_i45560_4_, String p_i45560_5_) {
/* 15 */     super(p_i45560_1_);
/* 16 */     this.parent = p_i45560_3_;
/*    */     
/* 18 */     if (p_i45560_4_ == WorldType.DEFAULT_1_1) {
/* 19 */       this.field_151623_c = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga };
/* 20 */       this.field_175973_g = null;
/* 21 */     } else if (p_i45560_4_ == WorldType.CUSTOMIZED) {
/* 22 */       this.field_175973_g = ChunkProviderSettings.Factory.jsonToFactory(p_i45560_5_).func_177864_b();
/*    */     } else {
/* 24 */       this.field_175973_g = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/* 33 */     int[] aint = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
/* 34 */     int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
/*    */     
/* 36 */     for (int i = 0; i < areaHeight; i++) {
/* 37 */       for (int j = 0; j < areaWidth; j++) {
/* 38 */         initChunkSeed((j + areaX), (i + areaY));
/* 39 */         int k = aint[j + i * areaWidth];
/* 40 */         int l = (k & 0xF00) >> 8;
/* 41 */         k &= 0xFFFFF0FF;
/*    */         
/* 43 */         if (this.field_175973_g != null && this.field_175973_g.fixedBiome >= 0) {
/* 44 */           aint1[j + i * areaWidth] = this.field_175973_g.fixedBiome;
/* 45 */         } else if (isBiomeOceanic(k)) {
/* 46 */           aint1[j + i * areaWidth] = k;
/* 47 */         } else if (k == BiomeGenBase.mushroomIsland.biomeID) {
/* 48 */           aint1[j + i * areaWidth] = k;
/* 49 */         } else if (k == 1) {
/* 50 */           if (l > 0) {
/* 51 */             if (nextInt(3) == 0) {
/* 52 */               aint1[j + i * areaWidth] = BiomeGenBase.mesaPlateau.biomeID;
/*    */             } else {
/* 54 */               aint1[j + i * areaWidth] = BiomeGenBase.mesaPlateau_F.biomeID;
/*    */             } 
/*    */           } else {
/* 57 */             aint1[j + i * areaWidth] = (this.field_151623_c[nextInt(this.field_151623_c.length)]).biomeID;
/*    */           } 
/* 59 */         } else if (k == 2) {
/* 60 */           if (l > 0) {
/* 61 */             aint1[j + i * areaWidth] = BiomeGenBase.jungle.biomeID;
/*    */           } else {
/* 63 */             aint1[j + i * areaWidth] = (this.field_151621_d[nextInt(this.field_151621_d.length)]).biomeID;
/*    */           } 
/* 65 */         } else if (k == 3) {
/* 66 */           if (l > 0) {
/* 67 */             aint1[j + i * areaWidth] = BiomeGenBase.megaTaiga.biomeID;
/*    */           } else {
/* 69 */             aint1[j + i * areaWidth] = (this.field_151622_e[nextInt(this.field_151622_e.length)]).biomeID;
/*    */           } 
/* 71 */         } else if (k == 4) {
/* 72 */           aint1[j + i * areaWidth] = (this.field_151620_f[nextInt(this.field_151620_f.length)]).biomeID;
/*    */         } else {
/* 74 */           aint1[j + i * areaWidth] = BiomeGenBase.mushroomIsland.biomeID;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 79 */     return aint1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerBiome.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */