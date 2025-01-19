/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Enumeration;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class PropertiesOrdered extends Properties {
/* 10 */   private Set<Object> keysOrdered = new LinkedHashSet();
/*    */   
/*    */   public synchronized Object put(Object key, Object value) {
/* 13 */     this.keysOrdered.add(key);
/* 14 */     return super.put(key, value);
/*    */   }
/*    */   
/*    */   public Set<Object> keySet() {
/* 18 */     Set<Object> set = super.keySet();
/* 19 */     this.keysOrdered.retainAll(set);
/* 20 */     return Collections.unmodifiableSet(this.keysOrdered);
/*    */   }
/*    */   
/*    */   public synchronized Enumeration<Object> keys() {
/* 24 */     return Collections.enumeration(keySet());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\PropertiesOrdered.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */