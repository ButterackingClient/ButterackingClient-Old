/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ public class GenLayerAddIsland extends GenLayer {
/*    */   public GenLayerAddIsland(long p_i2119_1_, GenLayer p_i2119_3_) {
/*  5 */     super(p_i2119_1_);
/*  6 */     this.parent = p_i2119_3_;
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
/* 23 */         int k1 = aint[j1 + 0 + (i1 + 0) * k];
/* 24 */         int l1 = aint[j1 + 2 + (i1 + 0) * k];
/* 25 */         int i2 = aint[j1 + 0 + (i1 + 2) * k];
/* 26 */         int j2 = aint[j1 + 2 + (i1 + 2) * k];
/* 27 */         int k2 = aint[j1 + 1 + (i1 + 1) * k];
/* 28 */         initChunkSeed((j1 + areaX), (i1 + areaY));
/*    */         
/* 30 */         if (k2 != 0 || (k1 == 0 && l1 == 0 && i2 == 0 && j2 == 0)) {
/* 31 */           if (k2 > 0 && (k1 == 0 || l1 == 0 || i2 == 0 || j2 == 0)) {
/* 32 */             if (nextInt(5) == 0) {
/* 33 */               if (k2 == 4) {
/* 34 */                 aint1[j1 + i1 * areaWidth] = 4;
/*    */               } else {
/* 36 */                 aint1[j1 + i1 * areaWidth] = 0;
/*    */               } 
/*    */             } else {
/* 39 */               aint1[j1 + i1 * areaWidth] = k2;
/*    */             } 
/*    */           } else {
/* 42 */             aint1[j1 + i1 * areaWidth] = k2;
/*    */           } 
/*    */         } else {
/* 45 */           int l2 = 1;
/* 46 */           int i3 = 1;
/*    */           
/* 48 */           if (k1 != 0 && nextInt(l2++) == 0) {
/* 49 */             i3 = k1;
/*    */           }
/*    */           
/* 52 */           if (l1 != 0 && nextInt(l2++) == 0) {
/* 53 */             i3 = l1;
/*    */           }
/*    */           
/* 56 */           if (i2 != 0 && nextInt(l2++) == 0) {
/* 57 */             i3 = i2;
/*    */           }
/*    */           
/* 60 */           if (j2 != 0 && nextInt(l2++) == 0) {
/* 61 */             i3 = j2;
/*    */           }
/*    */           
/* 64 */           if (nextInt(3) == 0) {
/* 65 */             aint1[j1 + i1 * areaWidth] = i3;
/* 66 */           } else if (i3 == 4) {
/* 67 */             aint1[j1 + i1 * areaWidth] = 4;
/*    */           } else {
/* 69 */             aint1[j1 + i1 * areaWidth] = 0;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 75 */     return aint1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerAddIsland.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */