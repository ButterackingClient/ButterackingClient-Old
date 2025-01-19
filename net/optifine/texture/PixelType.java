/*    */ package net.optifine.texture;
/*    */ 
/*    */ public enum PixelType {
/*  4 */   BYTE(5120),
/*  5 */   SHORT(5122),
/*  6 */   INT(5124),
/*  7 */   HALF_FLOAT(5131),
/*  8 */   FLOAT(5126),
/*  9 */   UNSIGNED_BYTE(5121),
/* 10 */   UNSIGNED_BYTE_3_3_2(32818),
/* 11 */   UNSIGNED_BYTE_2_3_3_REV(33634),
/* 12 */   UNSIGNED_SHORT(5123),
/* 13 */   UNSIGNED_SHORT_5_6_5(33635),
/* 14 */   UNSIGNED_SHORT_5_6_5_REV(33636),
/* 15 */   UNSIGNED_SHORT_4_4_4_4(32819),
/* 16 */   UNSIGNED_SHORT_4_4_4_4_REV(33637),
/* 17 */   UNSIGNED_SHORT_5_5_5_1(32820),
/* 18 */   UNSIGNED_SHORT_1_5_5_5_REV(33638),
/* 19 */   UNSIGNED_INT(5125),
/* 20 */   UNSIGNED_INT_8_8_8_8(32821),
/* 21 */   UNSIGNED_INT_8_8_8_8_REV(33639),
/* 22 */   UNSIGNED_INT_10_10_10_2(32822),
/* 23 */   UNSIGNED_INT_2_10_10_10_REV(33640);
/*    */   
/*    */   private int id;
/*    */   
/*    */   PixelType(int id) {
/* 28 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 32 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\texture\PixelType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */