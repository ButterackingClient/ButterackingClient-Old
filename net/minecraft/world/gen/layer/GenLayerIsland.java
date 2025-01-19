/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ public class GenLayerIsland extends GenLayer {
/*    */   public GenLayerIsland(long p_i2124_1_) {
/*  5 */     super(p_i2124_1_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/* 13 */     int[] aint = IntCache.getIntCache(areaWidth * areaHeight);
/*    */     
/* 15 */     for (int i = 0; i < areaHeight; i++) {
/* 16 */       for (int j = 0; j < areaWidth; j++) {
/* 17 */         initChunkSeed((areaX + j), (areaY + i));
/* 18 */         aint[j + i * areaWidth] = (nextInt(10) == 0) ? 1 : 0;
/*    */       } 
/*    */     } 
/*    */     
/* 22 */     if (areaX > -areaWidth && areaX <= 0 && areaY > -areaHeight && areaY <= 0) {
/* 23 */       aint[-areaX + -areaY * areaWidth] = 1;
/*    */     }
/*    */     
/* 26 */     return aint;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerIsland.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */