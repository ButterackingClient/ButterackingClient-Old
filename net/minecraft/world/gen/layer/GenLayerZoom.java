/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ public class GenLayerZoom extends GenLayer {
/*    */   public GenLayerZoom(long p_i2134_1_, GenLayer p_i2134_3_) {
/*  5 */     super(p_i2134_1_);
/*  6 */     this.parent = p_i2134_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/* 14 */     int i = areaX >> 1;
/* 15 */     int j = areaY >> 1;
/* 16 */     int k = (areaWidth >> 1) + 2;
/* 17 */     int l = (areaHeight >> 1) + 2;
/* 18 */     int[] aint = this.parent.getInts(i, j, k, l);
/* 19 */     int i1 = k - 1 << 1;
/* 20 */     int j1 = l - 1 << 1;
/* 21 */     int[] aint1 = IntCache.getIntCache(i1 * j1);
/*    */     
/* 23 */     for (int k1 = 0; k1 < l - 1; k1++) {
/* 24 */       int l1 = (k1 << 1) * i1;
/* 25 */       int i2 = 0;
/* 26 */       int j2 = aint[i2 + 0 + (k1 + 0) * k];
/*    */       
/* 28 */       for (int k2 = aint[i2 + 0 + (k1 + 1) * k]; i2 < k - 1; i2++) {
/* 29 */         initChunkSeed((i2 + i << 1), (k1 + j << 1));
/* 30 */         int l2 = aint[i2 + 1 + (k1 + 0) * k];
/* 31 */         int i3 = aint[i2 + 1 + (k1 + 1) * k];
/* 32 */         aint1[l1] = j2;
/* 33 */         aint1[l1++ + i1] = selectRandom2(j2, k2);
/* 34 */         aint1[l1] = selectRandom2(j2, l2);
/* 35 */         aint1[l1++ + i1] = selectModeOrRandom(j2, l2, k2, i3);
/* 36 */         j2 = l2;
/* 37 */         k2 = i3;
/*    */       } 
/*    */     } 
/*    */     
/* 41 */     int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);
/*    */     
/* 43 */     for (int j3 = 0; j3 < areaHeight; j3++) {
/* 44 */       System.arraycopy(aint1, (j3 + (areaY & 0x1)) * i1 + (areaX & 0x1), aint2, j3 * areaWidth, areaWidth);
/*    */     }
/*    */     
/* 47 */     return aint2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static GenLayer magnify(long p_75915_0_, GenLayer p_75915_2_, int p_75915_3_) {
/* 54 */     GenLayer genlayer = p_75915_2_;
/*    */     
/* 56 */     for (int i = 0; i < p_75915_3_; i++) {
/* 57 */       genlayer = new GenLayerZoom(p_75915_0_ + i, genlayer);
/*    */     }
/*    */     
/* 60 */     return genlayer;
/*    */   }
/*    */   
/*    */   protected int selectRandom2(int p_selectRandom2_1_, int p_selectRandom2_2_) {
/* 64 */     int i = nextInt(2);
/* 65 */     return (i == 0) ? p_selectRandom2_1_ : p_selectRandom2_2_;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerZoom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */