/*    */ package net.minecraft.world;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColorizerGrass
/*    */ {
/*  7 */   private static int[] grassBuffer = new int[65536];
/*    */   
/*    */   public static void setGrassBiomeColorizer(int[] p_77479_0_) {
/* 10 */     grassBuffer = p_77479_0_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getGrassColor(double p_77480_0_, double p_77480_2_) {
/* 17 */     p_77480_2_ *= p_77480_0_;
/* 18 */     int i = (int)((1.0D - p_77480_0_) * 255.0D);
/* 19 */     int j = (int)((1.0D - p_77480_2_) * 255.0D);
/* 20 */     int k = j << 8 | i;
/* 21 */     return (k > grassBuffer.length) ? -65281 : grassBuffer[k];
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\ColorizerGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */