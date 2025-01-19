/*    */ package net.optifine.texture;
/*    */ 
/*    */ public enum TextureType {
/*  4 */   TEXTURE_1D(3552),
/*  5 */   TEXTURE_2D(3553),
/*  6 */   TEXTURE_3D(32879),
/*  7 */   TEXTURE_RECTANGLE(34037);
/*    */   
/*    */   private int id;
/*    */   
/*    */   TextureType(int id) {
/* 12 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 16 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\texture\TextureType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */