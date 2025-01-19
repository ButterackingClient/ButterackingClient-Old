/*    */ package net.minecraft.util;
/*    */ 
/*    */ public abstract class LazyLoadBase<T> {
/*    */   private T value;
/*    */   private boolean isLoaded = false;
/*    */   
/*    */   public T getValue() {
/*  8 */     if (!this.isLoaded) {
/*  9 */       this.isLoaded = true;
/* 10 */       this.value = load();
/*    */     } 
/*    */     
/* 13 */     return this.value;
/*    */   }
/*    */   
/*    */   protected abstract T load();
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\LazyLoadBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */