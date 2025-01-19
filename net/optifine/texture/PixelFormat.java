/*    */ package net.optifine.texture;
/*    */ 
/*    */ public enum PixelFormat {
/*  4 */   RED(6403),
/*  5 */   RG(33319),
/*  6 */   RGB(6407),
/*  7 */   BGR(32992),
/*  8 */   RGBA(6408),
/*  9 */   BGRA(32993),
/* 10 */   RED_INTEGER(36244),
/* 11 */   RG_INTEGER(33320),
/* 12 */   RGB_INTEGER(36248),
/* 13 */   BGR_INTEGER(36250),
/* 14 */   RGBA_INTEGER(36249),
/* 15 */   BGRA_INTEGER(36251);
/*    */   
/*    */   private int id;
/*    */   
/*    */   PixelFormat(int id) {
/* 20 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 24 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\texture\PixelFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */