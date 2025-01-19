/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class LockCode {
/*  6 */   public static final LockCode EMPTY_CODE = new LockCode("");
/*    */   private final String lock;
/*    */   
/*    */   public LockCode(String code) {
/* 10 */     this.lock = code;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 14 */     return !(this.lock != null && !this.lock.isEmpty());
/*    */   }
/*    */   
/*    */   public String getLock() {
/* 18 */     return this.lock;
/*    */   }
/*    */   
/*    */   public void toNBT(NBTTagCompound nbt) {
/* 22 */     nbt.setString("Lock", this.lock);
/*    */   }
/*    */   
/*    */   public static LockCode fromNBT(NBTTagCompound nbt) {
/* 26 */     if (nbt.hasKey("Lock", 8)) {
/* 27 */       String s = nbt.getString("Lock");
/* 28 */       return new LockCode(s);
/*    */     } 
/* 30 */     return EMPTY_CODE;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\LockCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */