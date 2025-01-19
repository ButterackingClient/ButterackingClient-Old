/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ 
/*    */ public class GenLayerDeepOcean extends GenLayer {
/*    */   public GenLayerDeepOcean(long p_i45472_1_, GenLayer p_i45472_3_) {
/*  7 */     super(p_i45472_1_);
/*  8 */     this.parent = p_i45472_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/* 16 */     int i = areaX - 1;
/* 17 */     int j = areaY - 1;
/* 18 */     int k = areaWidth + 2;
/* 19 */     int l = areaHeight + 2;
/* 20 */     int[] aint = this.parent.getInts(i, j, k, l);
/* 21 */     int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
/*    */     
/* 23 */     for (int i1 = 0; i1 < areaHeight; i1++) {
/* 24 */       for (int j1 = 0; j1 < areaWidth; j1++) {
/* 25 */         int k1 = aint[j1 + 1 + (i1 + 1 - 1) * (areaWidth + 2)];
/* 26 */         int l1 = aint[j1 + 1 + 1 + (i1 + 1) * (areaWidth + 2)];
/* 27 */         int i2 = aint[j1 + 1 - 1 + (i1 + 1) * (areaWidth + 2)];
/* 28 */         int j2 = aint[j1 + 1 + (i1 + 1 + 1) * (areaWidth + 2)];
/* 29 */         int k2 = aint[j1 + 1 + (i1 + 1) * k];
/* 30 */         int l2 = 0;
/*    */         
/* 32 */         if (k1 == 0) {
/* 33 */           l2++;
/*    */         }
/*    */         
/* 36 */         if (l1 == 0) {
/* 37 */           l2++;
/*    */         }
/*    */         
/* 40 */         if (i2 == 0) {
/* 41 */           l2++;
/*    */         }
/*    */         
/* 44 */         if (j2 == 0) {
/* 45 */           l2++;
/*    */         }
/*    */         
/* 48 */         if (k2 == 0 && l2 > 3) {
/* 49 */           aint1[j1 + i1 * areaWidth] = BiomeGenBase.deepOcean.biomeID;
/*    */         } else {
/* 51 */           aint1[j1 + i1 * areaWidth] = k2;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 56 */     return aint1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerDeepOcean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */