/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class FrameEvent
/*    */ {
/*  9 */   private static Map<String, Integer> mapEventFrames = new HashMap<>();
/*    */   
/*    */   public static boolean isActive(String name, int frameInterval) {
/* 12 */     synchronized (mapEventFrames) {
/* 13 */       int i = (Minecraft.getMinecraft()).entityRenderer.frameCount;
/* 14 */       Integer integer = mapEventFrames.get(name);
/*    */       
/* 16 */       if (integer == null) {
/* 17 */         integer = new Integer(i);
/* 18 */         mapEventFrames.put(name, integer);
/*    */       } 
/*    */       
/* 21 */       int j = integer.intValue();
/*    */       
/* 23 */       if (i > j && i < j + frameInterval) {
/* 24 */         return false;
/*    */       }
/* 26 */       mapEventFrames.put(name, new Integer(i));
/* 27 */       return true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\FrameEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */