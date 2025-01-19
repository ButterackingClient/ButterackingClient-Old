/*    */ package net.minecraft.world.border;
/*    */ 
/*    */ public enum EnumBorderStatus {
/*  4 */   GROWING(4259712),
/*  5 */   SHRINKING(16724016),
/*  6 */   STATIONARY(2138367);
/*    */   
/*    */   private final int id;
/*    */   
/*    */   EnumBorderStatus(int id) {
/* 11 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getID() {
/* 19 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\border\EnumBorderStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */