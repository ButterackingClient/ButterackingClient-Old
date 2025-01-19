/*    */ package net.minecraft.world;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ColorizerFoliage
/*    */ {
/*  7 */   private static int[] foliageBuffer = new int[65536];
/*    */   
/*    */   public static void setFoliageBiomeColorizer(int[] p_77467_0_) {
/* 10 */     foliageBuffer = p_77467_0_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getFoliageColor(double p_77470_0_, double p_77470_2_) {
/* 17 */     p_77470_2_ *= p_77470_0_;
/* 18 */     int i = (int)((1.0D - p_77470_0_) * 255.0D);
/* 19 */     int j = (int)((1.0D - p_77470_2_) * 255.0D);
/* 20 */     return foliageBuffer[j << 8 | i];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getFoliageColorPine() {
/* 27 */     return 6396257;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getFoliageColorBirch() {
/* 34 */     return 8431445;
/*    */   }
/*    */   
/*    */   public static int getFoliageColorBasic() {
/* 38 */     return 4764952;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\ColorizerFoliage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */