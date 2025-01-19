/*    */ package net.minecraft.util;
/*    */ 
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistryNamespacedDefaultedByKey<K, V>
/*    */   extends RegistryNamespaced<K, V>
/*    */ {
/*    */   private final K defaultValueKey;
/*    */   private V defaultValue;
/*    */   
/*    */   public RegistryNamespacedDefaultedByKey(K defaultValueKeyIn) {
/* 17 */     this.defaultValueKey = defaultValueKeyIn;
/*    */   }
/*    */   
/*    */   public void register(int id, K key, V value) {
/* 21 */     if (this.defaultValueKey.equals(key)) {
/* 22 */       this.defaultValue = value;
/*    */     }
/*    */     
/* 25 */     super.register(id, key, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void validateKey() {
/* 32 */     Validate.notNull(this.defaultValueKey);
/*    */   }
/*    */   
/*    */   public V getObject(K name) {
/* 36 */     V v = super.getObject(name);
/* 37 */     return (v == null) ? this.defaultValue : v;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public V getObjectById(int id) {
/* 44 */     V v = super.getObjectById(id);
/* 45 */     return (v == null) ? this.defaultValue : v;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\RegistryNamespacedDefaultedByKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */