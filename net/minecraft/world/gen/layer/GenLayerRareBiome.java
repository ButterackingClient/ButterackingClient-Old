/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ 
/*    */ public class GenLayerRareBiome extends GenLayer {
/*    */   public GenLayerRareBiome(long p_i45478_1_, GenLayer p_i45478_3_) {
/*  7 */     super(p_i45478_1_);
/*  8 */     this.parent = p_i45478_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/* 16 */     int[] aint = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
/* 17 */     int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
/*    */     
/* 19 */     for (int i = 0; i < areaHeight; i++) {
/* 20 */       for (int j = 0; j < areaWidth; j++) {
/* 21 */         initChunkSeed((j + areaX), (i + areaY));
/* 22 */         int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
/*    */         
/* 24 */         if (nextInt(57) == 0) {
/* 25 */           if (k == BiomeGenBase.plains.biomeID) {
/* 26 */             aint1[j + i * areaWidth] = BiomeGenBase.plains.biomeID + 128;
/*    */           } else {
/* 28 */             aint1[j + i * areaWidth] = k;
/*    */           } 
/*    */         } else {
/* 31 */           aint1[j + i * areaWidth] = k;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 36 */     return aint1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerRareBiome.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */