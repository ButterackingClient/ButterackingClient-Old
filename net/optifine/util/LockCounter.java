/*    */ package net.optifine.util;
/*    */ 
/*    */ public class LockCounter {
/*    */   private int lockCount;
/*    */   
/*    */   public boolean lock() {
/*  7 */     this.lockCount++;
/*  8 */     return (this.lockCount == 1);
/*    */   }
/*    */   
/*    */   public boolean unlock() {
/* 12 */     if (this.lockCount <= 0) {
/* 13 */       return false;
/*    */     }
/* 15 */     this.lockCount--;
/* 16 */     return (this.lockCount == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLocked() {
/* 21 */     return (this.lockCount > 0);
/*    */   }
/*    */   
/*    */   public int getLockCount() {
/* 25 */     return this.lockCount;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 29 */     return "lockCount: " + this.lockCount;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\LockCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */