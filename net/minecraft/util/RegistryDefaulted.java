/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ public class RegistryDefaulted<K, V>
/*    */   extends RegistrySimple<K, V>
/*    */ {
/*    */   private final V defaultObject;
/*    */   
/*    */   public RegistryDefaulted(V defaultObjectIn) {
/* 10 */     this.defaultObject = defaultObjectIn;
/*    */   }
/*    */   
/*    */   public V getObject(K name) {
/* 14 */     V v = super.getObject(name);
/* 15 */     return (v == null) ? this.defaultObject : v;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\RegistryDefaulted.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */