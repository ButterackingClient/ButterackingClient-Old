/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityList;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class EntityUtils
/*    */ {
/* 11 */   private static final Map<Class, Integer> mapIdByClass = (Map)new HashMap<>();
/* 12 */   private static final Map<String, Integer> mapIdByName = new HashMap<>();
/* 13 */   private static final Map<String, Class> mapClassByName = (Map)new HashMap<>();
/*    */   
/*    */   public static int getEntityIdByClass(Entity entity) {
/* 16 */     return (entity == null) ? -1 : getEntityIdByClass(entity.getClass());
/*    */   }
/*    */   
/*    */   public static int getEntityIdByClass(Class cls) {
/* 20 */     Integer integer = mapIdByClass.get(cls);
/* 21 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */   
/*    */   public static int getEntityIdByName(String name) {
/* 25 */     Integer integer = mapIdByName.get(name);
/* 26 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */   
/*    */   public static Class getEntityClassByName(String name) {
/* 30 */     Class oclass = mapClassByName.get(name);
/* 31 */     return oclass;
/*    */   }
/*    */   
/*    */   static {
/* 35 */     for (int i = 0; i < 1000; i++) {
/* 36 */       Class oclass = EntityList.getClassFromID(i);
/*    */       
/* 38 */       if (oclass != null) {
/* 39 */         String s = EntityList.getStringFromID(i);
/*    */         
/* 41 */         if (s != null) {
/* 42 */           if (mapIdByClass.containsKey(oclass)) {
/* 43 */             Config.warn("Duplicate entity class: " + oclass + ", id1: " + mapIdByClass.get(oclass) + ", id2: " + i);
/*    */           }
/*    */           
/* 46 */           if (mapIdByName.containsKey(s)) {
/* 47 */             Config.warn("Duplicate entity name: " + s + ", id1: " + mapIdByName.get(s) + ", id2: " + i);
/*    */           }
/*    */           
/* 50 */           if (mapClassByName.containsKey(s)) {
/* 51 */             Config.warn("Duplicate entity name: " + s + ", class1: " + mapClassByName.get(s) + ", class2: " + oclass);
/*    */           }
/*    */           
/* 54 */           mapIdByClass.put(oclass, Integer.valueOf(i));
/* 55 */           mapIdByName.put(s, Integer.valueOf(i));
/* 56 */           mapClassByName.put(s, oclass);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\EntityUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */