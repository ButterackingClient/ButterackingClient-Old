/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class NativeMemory
/*    */ {
/* 10 */   private static LongSupplier bufferAllocatedSupplier = makeLongSupplier(new String[][] { { "sun.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed" }, { "jdk.internal.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed" } });
/* 11 */   private static LongSupplier bufferMaximumSupplier = makeLongSupplier(new String[][] { { "sun.misc.VM", "maxDirectMemory" }, { "jdk.internal.misc.VM", "maxDirectMemory" } });
/*    */   
/*    */   public static long getBufferAllocated() {
/* 14 */     return (bufferAllocatedSupplier == null) ? -1L : bufferAllocatedSupplier.getAsLong();
/*    */   }
/*    */   
/*    */   public static long getBufferMaximum() {
/* 18 */     return (bufferMaximumSupplier == null) ? -1L : bufferMaximumSupplier.getAsLong();
/*    */   }
/*    */   
/*    */   private static LongSupplier makeLongSupplier(String[][] paths) {
/* 22 */     List<Throwable> list = new ArrayList<>();
/*    */     
/* 24 */     for (int i = 0; i < paths.length; i++) {
/* 25 */       String[] astring = paths[i];
/*    */       
/*    */       try {
/* 28 */         LongSupplier longsupplier = makeLongSupplier(astring);
/* 29 */         return longsupplier;
/* 30 */       } catch (Throwable throwable) {
/* 31 */         list.add(throwable);
/*    */       } 
/*    */     } 
/*    */     
/* 35 */     for (Throwable throwable1 : list) {
/* 36 */       Config.warn(throwable1.getClass().getName() + ": " + throwable1.getMessage());
/*    */     }
/*    */     
/* 39 */     return null;
/*    */   }
/*    */   
/*    */   private static LongSupplier makeLongSupplier(String[] path) throws Exception {
/* 43 */     if (path.length < 2) {
/* 44 */       return null;
/*    */     }
/* 46 */     Class<?> oclass = Class.forName(path[0]);
/* 47 */     Method method = oclass.getMethod(path[1], new Class[0]);
/* 48 */     method.setAccessible(true);
/* 49 */     Object object = null;
/*    */     
/* 51 */     for (int i = 2; i < path.length; i++) {
/* 52 */       String s = path[i];
/* 53 */       object = method.invoke(object, new Object[0]);
/* 54 */       method = object.getClass().getMethod(s, new Class[0]);
/* 55 */       method.setAccessible(true);
/*    */     } 
/*    */     
/* 58 */     final Method method1 = method;
/* 59 */     final Object o = object;
/* 60 */     LongSupplier longsupplier = new LongSupplier() {
/*    */         private boolean disabled = false;
/*    */         
/*    */         public long getAsLong() {
/* 64 */           if (this.disabled) {
/* 65 */             return -1L;
/*    */           }
/*    */           try {
/* 68 */             return ((Long)method1.invoke(o, new Object[0])).longValue();
/* 69 */           } catch (Throwable throwable) {
/* 70 */             Config.warn(throwable.getClass().getName() + ": " + throwable.getMessage());
/* 71 */             this.disabled = true;
/* 72 */             return -1L;
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 77 */     return longsupplier;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\NativeMemory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */