/*    */ package net.minecraft.world.gen;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseGeneratorOctaves
/*    */   extends NoiseGenerator
/*    */ {
/*    */   private NoiseGeneratorImproved[] generatorCollection;
/*    */   private int octaves;
/*    */   
/*    */   public NoiseGeneratorOctaves(Random seed, int octavesIn) {
/* 15 */     this.octaves = octavesIn;
/* 16 */     this.generatorCollection = new NoiseGeneratorImproved[octavesIn];
/*    */     
/* 18 */     for (int i = 0; i < octavesIn; i++) {
/* 19 */       this.generatorCollection[i] = new NoiseGeneratorImproved(seed);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] generateNoiseOctaves(double[] noiseArray, int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize, double xScale, double yScale, double zScale) {
/* 28 */     if (noiseArray == null) {
/* 29 */       noiseArray = new double[xSize * ySize * zSize];
/*    */     } else {
/* 31 */       for (int i = 0; i < noiseArray.length; i++) {
/* 32 */         noiseArray[i] = 0.0D;
/*    */       }
/*    */     } 
/*    */     
/* 36 */     double d3 = 1.0D;
/*    */     
/* 38 */     for (int j = 0; j < this.octaves; j++) {
/* 39 */       double d0 = xOffset * d3 * xScale;
/* 40 */       double d1 = yOffset * d3 * yScale;
/* 41 */       double d2 = zOffset * d3 * zScale;
/* 42 */       long k = MathHelper.floor_double_long(d0);
/* 43 */       long l = MathHelper.floor_double_long(d2);
/* 44 */       d0 -= k;
/* 45 */       d2 -= l;
/* 46 */       k %= 16777216L;
/* 47 */       l %= 16777216L;
/* 48 */       d0 += k;
/* 49 */       d2 += l;
/* 50 */       this.generatorCollection[j].populateNoiseArray(noiseArray, d0, d1, d2, xSize, ySize, zSize, xScale * d3, yScale * d3, zScale * d3, d3);
/* 51 */       d3 /= 2.0D;
/*    */     } 
/*    */     
/* 54 */     return noiseArray;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] generateNoiseOctaves(double[] noiseArray, int xOffset, int zOffset, int xSize, int zSize, double xScale, double zScale, double p_76305_10_) {
/* 61 */     return generateNoiseOctaves(noiseArray, xOffset, 10, zOffset, xSize, 1, zSize, xScale, 1.0D, zScale);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\NoiseGeneratorOctaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */