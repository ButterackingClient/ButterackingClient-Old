/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Iterators;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ObjectIntIdentityMap<T>
/*    */   implements IObjectIntIterable<T> {
/* 12 */   private final IdentityHashMap<T, Integer> identityMap = new IdentityHashMap<>(512);
/* 13 */   private final List<T> objectList = Lists.newArrayList();
/*    */   
/*    */   public void put(T key, int value) {
/* 16 */     this.identityMap.put(key, Integer.valueOf(value));
/*    */     
/* 18 */     while (this.objectList.size() <= value) {
/* 19 */       this.objectList.add(null);
/*    */     }
/*    */     
/* 22 */     this.objectList.set(value, key);
/*    */   }
/*    */   
/*    */   public int get(T key) {
/* 26 */     Integer integer = this.identityMap.get(key);
/* 27 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */   
/*    */   public final T getByValue(int value) {
/* 31 */     return (value >= 0 && value < this.objectList.size()) ? this.objectList.get(value) : null;
/*    */   }
/*    */   
/*    */   public Iterator<T> iterator() {
/* 35 */     return (Iterator<T>)Iterators.filter(this.objectList.iterator(), Predicates.notNull());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ObjectIntIdentityMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */