/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ import java.util.concurrent.locks.ReadWriteLock;
/*    */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*    */ 
/*    */ public class ThreadSafeBoundList<T> {
/*    */   private final T[] field_152759_a;
/*    */   private final Class<? extends T> field_152760_b;
/* 10 */   private final ReadWriteLock field_152761_c = new ReentrantReadWriteLock();
/*    */   private int field_152762_d;
/*    */   private int field_152763_e;
/*    */   
/*    */   public ThreadSafeBoundList(Class<? extends T> p_i1126_1_, int p_i1126_2_) {
/* 15 */     this.field_152760_b = p_i1126_1_;
/* 16 */     this.field_152759_a = (T[])Array.newInstance(p_i1126_1_, p_i1126_2_);
/*    */   }
/*    */   
/*    */   public T func_152757_a(T p_152757_1_) {
/* 20 */     this.field_152761_c.writeLock().lock();
/* 21 */     this.field_152759_a[this.field_152763_e] = p_152757_1_;
/* 22 */     this.field_152763_e = (this.field_152763_e + 1) % func_152758_b();
/*    */     
/* 24 */     if (this.field_152762_d < func_152758_b()) {
/* 25 */       this.field_152762_d++;
/*    */     }
/*    */     
/* 28 */     this.field_152761_c.writeLock().unlock();
/* 29 */     return p_152757_1_;
/*    */   }
/*    */   
/*    */   public int func_152758_b() {
/* 33 */     this.field_152761_c.readLock().lock();
/* 34 */     int i = this.field_152759_a.length;
/* 35 */     this.field_152761_c.readLock().unlock();
/* 36 */     return i;
/*    */   }
/*    */   
/*    */   public T[] func_152756_c() {
/* 40 */     Object[] at = (Object[])Array.newInstance(this.field_152760_b, this.field_152762_d);
/* 41 */     this.field_152761_c.readLock().lock();
/*    */     
/* 43 */     for (int i = 0; i < this.field_152762_d; i++) {
/* 44 */       int j = (this.field_152763_e - this.field_152762_d + i) % func_152758_b();
/*    */       
/* 46 */       if (j < 0) {
/* 47 */         j += func_152758_b();
/*    */       }
/*    */       
/* 50 */       at[i] = this.field_152759_a[j];
/*    */     } 
/*    */     
/* 53 */     this.field_152761_c.readLock().unlock();
/* 54 */     return (T[])at;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ThreadSafeBoundList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */