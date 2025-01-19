/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.optifine.util.CounterInt;
/*    */ import net.optifine.util.SmoothFloat;
/*    */ 
/*    */ public class Smoother
/*    */ {
/* 10 */   private static Map<Integer, SmoothFloat> mapSmoothValues = new HashMap<>();
/* 11 */   private static CounterInt counterIds = new CounterInt(1);
/*    */   
/*    */   public static float getSmoothValue(int id, float value, float timeFadeUpSec, float timeFadeDownSec) {
/* 14 */     synchronized (mapSmoothValues) {
/* 15 */       Integer integer = Integer.valueOf(id);
/* 16 */       SmoothFloat smoothfloat = mapSmoothValues.get(integer);
/*    */       
/* 18 */       if (smoothfloat == null) {
/* 19 */         smoothfloat = new SmoothFloat(value, timeFadeUpSec, timeFadeDownSec);
/* 20 */         mapSmoothValues.put(integer, smoothfloat);
/*    */       } 
/*    */       
/* 23 */       float f = smoothfloat.getSmoothValue(value, timeFadeUpSec, timeFadeDownSec);
/* 24 */       return f;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static int getNextId() {
/* 29 */     synchronized (counterIds) {
/* 30 */       return counterIds.nextValue();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void resetValues() {
/* 35 */     synchronized (mapSmoothValues) {
/* 36 */       mapSmoothValues.clear();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\Smoother.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */