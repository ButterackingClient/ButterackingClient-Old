/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ 
/*    */ public class GenLayerRiver extends GenLayer {
/*    */   public GenLayerRiver(long p_i2128_1_, GenLayer p_i2128_3_) {
/*  7 */     super(p_i2128_1_);
/*  8 */     this.parent = p_i2128_3_;
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
/* 25 */         int k1 = func_151630_c(aint[j1 + 0 + (i1 + 1) * k]);
/* 26 */         int l1 = func_151630_c(aint[j1 + 2 + (i1 + 1) * k]);
/* 27 */         int i2 = func_151630_c(aint[j1 + 1 + (i1 + 0) * k]);
/* 28 */         int j2 = func_151630_c(aint[j1 + 1 + (i1 + 2) * k]);
/* 29 */         int k2 = func_151630_c(aint[j1 + 1 + (i1 + 1) * k]);
/*    */         
/* 31 */         if (k2 == k1 && k2 == i2 && k2 == l1 && k2 == j2) {
/* 32 */           aint1[j1 + i1 * areaWidth] = -1;
/*    */         } else {
/* 34 */           aint1[j1 + i1 * areaWidth] = BiomeGenBase.river.biomeID;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 39 */     return aint1;
/*    */   }
/*    */   
/*    */   private int func_151630_c(int p_151630_1_) {
/* 43 */     return (p_151630_1_ >= 2) ? (2 + (p_151630_1_ & 0x1)) : p_151630_1_;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerRiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */