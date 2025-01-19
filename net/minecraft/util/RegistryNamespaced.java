/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.BiMap;
/*    */ import com.google.common.collect.HashBiMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class RegistryNamespaced<K, V>
/*    */   extends RegistrySimple<K, V> implements IObjectIntIterable<V> {
/* 10 */   protected final ObjectIntIdentityMap<V> underlyingIntegerMap = new ObjectIntIdentityMap<>();
/*    */   protected final Map<V, K> inverseObjectRegistry;
/*    */   
/*    */   public RegistryNamespaced() {
/* 14 */     this.inverseObjectRegistry = (Map<V, K>)((BiMap)this.registryObjects).inverse();
/*    */   }
/*    */   
/*    */   public void register(int id, K key, V value) {
/* 18 */     this.underlyingIntegerMap.put(value, id);
/* 19 */     putObject(key, value);
/*    */   }
/*    */   
/*    */   protected Map<K, V> createUnderlyingMap() {
/* 23 */     return (Map<K, V>)HashBiMap.create();
/*    */   }
/*    */   
/*    */   public V getObject(K name) {
/* 27 */     return super.getObject(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public K getNameForObject(V value) {
/* 34 */     return this.inverseObjectRegistry.get(value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean containsKey(K key) {
/* 41 */     return super.containsKey(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIDForObject(V value) {
/* 48 */     return this.underlyingIntegerMap.get(value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public V getObjectById(int id) {
/* 55 */     return this.underlyingIntegerMap.getByValue(id);
/*    */   }
/*    */   
/*    */   public Iterator<V> iterator() {
/* 59 */     return this.underlyingIntegerMap.iterator();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\RegistryNamespaced.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */