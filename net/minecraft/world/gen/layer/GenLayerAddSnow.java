/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ public class GenLayerAddSnow extends GenLayer {
/*    */   public GenLayerAddSnow(long p_i2121_1_, GenLayer p_i2121_3_) {
/*  5 */     super(p_i2121_1_);
/*  6 */     this.parent = p_i2121_3_;
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
/* 23 */         int k1 = aint[j1 + 1 + (i1 + 1) * k];
/* 24 */         initChunkSeed((j1 + areaX), (i1 + areaY));
/*    */         
/* 26 */         if (k1 == 0) {
/* 27 */           aint1[j1 + i1 * areaWidth] = 0;
/*    */         } else {
/* 29 */           int l1 = nextInt(6);
/*    */           
/* 31 */           if (l1 == 0) {
/* 32 */             l1 = 4;
/* 33 */           } else if (l1 <= 1) {
/* 34 */             l1 = 3;
/*    */           } else {
/* 36 */             l1 = 1;
/*    */           } 
/*    */           
/* 39 */           aint1[j1 + i1 * areaWidth] = l1;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     return aint1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerAddSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */