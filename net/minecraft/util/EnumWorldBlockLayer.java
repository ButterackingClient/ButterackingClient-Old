/*    */ package net.minecraft.util;
/*    */ 
/*    */ public enum EnumWorldBlockLayer {
/*  4 */   SOLID("Solid"),
/*  5 */   CUTOUT_MIPPED("Mipped Cutout"),
/*  6 */   CUTOUT("Cutout"),
/*  7 */   TRANSLUCENT("Translucent");
/*    */   
/*    */   private final String layerName;
/*    */   
/*    */   EnumWorldBlockLayer(String layerNameIn) {
/* 12 */     this.layerName = layerNameIn;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 16 */     return this.layerName;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\EnumWorldBlockLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */