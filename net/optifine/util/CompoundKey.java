/*    */ package net.optifine.util;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class CompoundKey {
/*    */   private Object[] keys;
/*    */   private int hashcode;
/*    */   
/*    */   public CompoundKey(Object[] keys) {
/* 10 */     this.hashcode = 0;
/* 11 */     this.keys = (Object[])keys.clone();
/*    */   }
/*    */   
/*    */   public CompoundKey(Object k1, Object k2) {
/* 15 */     this(new Object[] { k1, k2 });
/*    */   }
/*    */   
/*    */   public CompoundKey(Object k1, Object k2, Object k3) {
/* 19 */     this(new Object[] { k1, k2, k3 });
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 23 */     if (this.hashcode == 0) {
/* 24 */       this.hashcode = 7;
/*    */       
/* 26 */       for (int i = 0; i < this.keys.length; i++) {
/* 27 */         Object object = this.keys[i];
/*    */         
/* 29 */         if (object != null) {
/* 30 */           this.hashcode = 31 * this.hashcode + object.hashCode();
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 35 */     return this.hashcode;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 39 */     if (obj == null)
/* 40 */       return false; 
/* 41 */     if (obj == this)
/* 42 */       return true; 
/* 43 */     if (!(obj instanceof CompoundKey)) {
/* 44 */       return false;
/*    */     }
/* 46 */     CompoundKey compoundkey = (CompoundKey)obj;
/* 47 */     Object[] aobject = compoundkey.getKeys();
/*    */     
/* 49 */     if (aobject.length != this.keys.length) {
/* 50 */       return false;
/*    */     }
/* 52 */     for (int i = 0; i < this.keys.length; i++) {
/* 53 */       if (!compareKeys(this.keys[i], aobject[i])) {
/* 54 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 58 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean compareKeys(Object key1, Object key2) {
/* 64 */     return (key1 == key2) ? true : ((key1 == null) ? false : ((key2 == null) ? false : key1.equals(key2)));
/*    */   }
/*    */   
/*    */   private Object[] getKeys() {
/* 68 */     return this.keys;
/*    */   }
/*    */   
/*    */   public Object[] getKeysCopy() {
/* 72 */     return (Object[])this.keys.clone();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 76 */     return "[" + Config.arrayToString(this.keys) + "]";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\CompoundKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */