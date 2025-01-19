/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ public class GenLayerVoronoiZoom extends GenLayer {
/*    */   public GenLayerVoronoiZoom(long p_i2133_1_, GenLayer p_i2133_3_) {
/*  5 */     super(p_i2133_1_);
/*  6 */     this.parent = p_i2133_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/* 14 */     areaX -= 2;
/* 15 */     areaY -= 2;
/* 16 */     int i = areaX >> 2;
/* 17 */     int j = areaY >> 2;
/* 18 */     int k = (areaWidth >> 2) + 2;
/* 19 */     int l = (areaHeight >> 2) + 2;
/* 20 */     int[] aint = this.parent.getInts(i, j, k, l);
/* 21 */     int i1 = k - 1 << 2;
/* 22 */     int j1 = l - 1 << 2;
/* 23 */     int[] aint1 = IntCache.getIntCache(i1 * j1);
/*    */     
/* 25 */     for (int k1 = 0; k1 < l - 1; k1++) {
/* 26 */       int l1 = 0;
/* 27 */       int i2 = aint[l1 + 0 + (k1 + 0) * k];
/*    */       
/* 29 */       for (int j2 = aint[l1 + 0 + (k1 + 1) * k]; l1 < k - 1; l1++) {
/* 30 */         double d0 = 3.6D;
/* 31 */         initChunkSeed((l1 + i << 2), (k1 + j << 2));
/* 32 */         double d1 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
/* 33 */         double d2 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
/* 34 */         initChunkSeed((l1 + i + 1 << 2), (k1 + j << 2));
/* 35 */         double d3 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/* 36 */         double d4 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
/* 37 */         initChunkSeed((l1 + i << 2), (k1 + j + 1 << 2));
/* 38 */         double d5 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D;
/* 39 */         double d6 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/* 40 */         initChunkSeed((l1 + i + 1 << 2), (k1 + j + 1 << 2));
/* 41 */         double d7 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/* 42 */         double d8 = (nextInt(1024) / 1024.0D - 0.5D) * 3.6D + 4.0D;
/* 43 */         int k2 = aint[l1 + 1 + (k1 + 0) * k] & 0xFF;
/* 44 */         int l2 = aint[l1 + 1 + (k1 + 1) * k] & 0xFF;
/*    */         
/* 46 */         for (int i3 = 0; i3 < 4; i3++) {
/* 47 */           int j3 = ((k1 << 2) + i3) * i1 + (l1 << 2);
/*    */           
/* 49 */           for (int k3 = 0; k3 < 4; k3++) {
/* 50 */             double d9 = (i3 - d2) * (i3 - d2) + (k3 - d1) * (k3 - d1);
/* 51 */             double d10 = (i3 - d4) * (i3 - d4) + (k3 - d3) * (k3 - d3);
/* 52 */             double d11 = (i3 - d6) * (i3 - d6) + (k3 - d5) * (k3 - d5);
/* 53 */             double d12 = (i3 - d8) * (i3 - d8) + (k3 - d7) * (k3 - d7);
/*    */             
/* 55 */             if (d9 < d10 && d9 < d11 && d9 < d12) {
/* 56 */               aint1[j3++] = i2;
/* 57 */             } else if (d10 < d9 && d10 < d11 && d10 < d12) {
/* 58 */               aint1[j3++] = k2;
/* 59 */             } else if (d11 < d9 && d11 < d10 && d11 < d12) {
/* 60 */               aint1[j3++] = j2;
/*    */             } else {
/* 62 */               aint1[j3++] = l2;
/*    */             } 
/*    */           } 
/*    */         } 
/*    */         
/* 67 */         i2 = k2;
/* 68 */         j2 = l2;
/*    */       } 
/*    */     } 
/*    */     
/* 72 */     int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);
/*    */     
/* 74 */     for (int l3 = 0; l3 < areaHeight; l3++) {
/* 75 */       System.arraycopy(aint1, (l3 + (areaY & 0x3)) * i1 + (areaX & 0x3), aint2, l3 * areaWidth, areaWidth);
/*    */     }
/*    */     
/* 78 */     return aint2;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerVoronoiZoom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */