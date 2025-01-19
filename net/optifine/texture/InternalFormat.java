/*    */ package net.optifine.texture;
/*    */ 
/*    */ public enum InternalFormat {
/*  4 */   R8(33321),
/*  5 */   RG8(33323),
/*  6 */   RGB8(32849),
/*  7 */   RGBA8(32856),
/*  8 */   R8_SNORM(36756),
/*  9 */   RG8_SNORM(36757),
/* 10 */   RGB8_SNORM(36758),
/* 11 */   RGBA8_SNORM(36759),
/* 12 */   R16(33322),
/* 13 */   RG16(33324),
/* 14 */   RGB16(32852),
/* 15 */   RGBA16(32859),
/* 16 */   R16_SNORM(36760),
/* 17 */   RG16_SNORM(36761),
/* 18 */   RGB16_SNORM(36762),
/* 19 */   RGBA16_SNORM(36763),
/* 20 */   R16F(33325),
/* 21 */   RG16F(33327),
/* 22 */   RGB16F(34843),
/* 23 */   RGBA16F(34842),
/* 24 */   R32F(33326),
/* 25 */   RG32F(33328),
/* 26 */   RGB32F(34837),
/* 27 */   RGBA32F(34836),
/* 28 */   R32I(33333),
/* 29 */   RG32I(33339),
/* 30 */   RGB32I(36227),
/* 31 */   RGBA32I(36226),
/* 32 */   R32UI(33334),
/* 33 */   RG32UI(33340),
/* 34 */   RGB32UI(36209),
/* 35 */   RGBA32UI(36208),
/* 36 */   R3_G3_B2(10768),
/* 37 */   RGB5_A1(32855),
/* 38 */   RGB10_A2(32857),
/* 39 */   R11F_G11F_B10F(35898),
/* 40 */   RGB9_E5(35901);
/*    */   
/*    */   private int id;
/*    */   
/*    */   InternalFormat(int id) {
/* 45 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 49 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\texture\InternalFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */