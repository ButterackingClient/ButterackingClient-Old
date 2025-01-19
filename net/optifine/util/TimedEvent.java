/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class TimedEvent {
/*  7 */   private static Map<String, Long> mapEventTimes = new HashMap<>();
/*    */   
/*    */   public static boolean isActive(String name, long timeIntervalMs) {
/* 10 */     synchronized (mapEventTimes) {
/* 11 */       long i = System.currentTimeMillis();
/* 12 */       Long olong = mapEventTimes.get(name);
/*    */       
/* 14 */       if (olong == null) {
/* 15 */         olong = new Long(i);
/* 16 */         mapEventTimes.put(name, olong);
/*    */       } 
/*    */       
/* 19 */       long j = olong.longValue();
/*    */       
/* 21 */       if (i < j + timeIntervalMs) {
/* 22 */         return false;
/*    */       }
/* 24 */       mapEventTimes.put(name, new Long(i));
/* 25 */       return true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\TimedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */