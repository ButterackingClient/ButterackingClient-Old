/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ public class GenLayerSmooth extends GenLayer {
/*    */   public GenLayerSmooth(long p_i2131_1_, GenLayer p_i2131_3_) {
/*  5 */     super(p_i2131_1_);
/*  6 */     this.parent = p_i2131_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/* 14 */     int i = areaX - 1;
/* 15 */     int j = areaY - 1;
/* 16 */     int k = areaWidth + 2;
/* 17 */     int l = areaHeight + 2;
/* 18 */     int[] aint = this.parent.getInts(i, j, k, l);
/* 19 */     int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
/*    */     
/* 21 */     for (int i1 = 0; i1 < areaHeight; i1++) {
/* 22 */       for (int j1 = 0; j1 < areaWidth; j1++) {
/* 23 */         int k1 = aint[j1 + 0 + (i1 + 1) * k];
/* 24 */         int l1 = aint[j1 + 2 + (i1 + 1) * k];
/* 25 */         int i2 = aint[j1 + 1 + (i1 + 0) * k];
/* 26 */         int j2 = aint[j1 + 1 + (i1 + 2) * k];
/* 27 */         int k2 = aint[j1 + 1 + (i1 + 1) * k];
/*    */         
/* 29 */         if (k1 == l1 && i2 == j2) {
/* 30 */           initChunkSeed((j1 + areaX), (i1 + areaY));
/*    */           
/* 32 */           if (nextInt(2) == 0) {
/* 33 */             k2 = k1;
/*    */           } else {
/* 35 */             k2 = i2;
/*    */           } 
/*    */         } else {
/* 38 */           if (k1 == l1) {
/* 39 */             k2 = k1;
/*    */           }
/*    */           
/* 42 */           if (i2 == j2) {
/* 43 */             k2 = i2;
/*    */           }
/*    */         } 
/*    */         
/* 47 */         aint1[j1 + i1 * areaWidth] = k2;
/*    */       } 
/*    */     } 
/*    */     
/* 51 */     return aint1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerSmooth.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */