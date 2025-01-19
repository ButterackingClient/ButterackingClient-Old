/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ public class MapPopulator
/*    */ {
/*    */   public static <K, V> Map<K, V> createMap(Iterable<K> keys, Iterable<V> values) {
/* 11 */     return populateMap(keys, values, Maps.newLinkedHashMap());
/*    */   }
/*    */   
/*    */   public static <K, V> Map<K, V> populateMap(Iterable<K> keys, Iterable<V> values, Map<K, V> map) {
/* 15 */     Iterator<V> iterator = values.iterator();
/*    */     
/* 17 */     for (K k : keys) {
/* 18 */       map.put(k, iterator.next());
/*    */     }
/*    */     
/* 21 */     if (iterator.hasNext()) {
/* 22 */       throw new NoSuchElementException();
/*    */     }
/* 24 */     return map;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\MapPopulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */