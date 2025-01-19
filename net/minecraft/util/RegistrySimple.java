/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class RegistrySimple<K, V>
/*    */   implements IRegistry<K, V>
/*    */ {
/* 15 */   private static final Logger logger = LogManager.getLogger();
/* 16 */   protected final Map<K, V> registryObjects = createUnderlyingMap();
/*    */   
/*    */   protected Map<K, V> createUnderlyingMap() {
/* 19 */     return Maps.newHashMap();
/*    */   }
/*    */   
/*    */   public V getObject(K name) {
/* 23 */     return this.registryObjects.get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void putObject(K key, V value) {
/* 30 */     Validate.notNull(key);
/* 31 */     Validate.notNull(value);
/*    */     
/* 33 */     if (this.registryObjects.containsKey(key)) {
/* 34 */       logger.debug("Adding duplicate key '" + key + "' to registry");
/*    */     }
/*    */     
/* 37 */     this.registryObjects.put(key, value);
/*    */   }
/*    */   
/*    */   public Set<K> getKeys() {
/* 41 */     return Collections.unmodifiableSet(this.registryObjects.keySet());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean containsKey(K key) {
/* 48 */     return this.registryObjects.containsKey(key);
/*    */   }
/*    */   
/*    */   public Iterator<V> iterator() {
/* 52 */     return this.registryObjects.values().iterator();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\RegistrySimple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */