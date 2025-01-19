/*     */ package client.event;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventManager
/*     */ {
/*     */   public static void register(Object o) {
/*     */     byte b;
/*     */     int i;
/*     */     Method[] arrayOfMethod;
/*  20 */     for (i = (arrayOfMethod = o.getClass().getDeclaredMethods()).length, b = 0; b < i; ) { Method method = arrayOfMethod[b];
/*  21 */       if (!isMethodBad(method))
/*  22 */         register(method, o); 
/*     */       b++; }
/*     */   
/*     */   } public static void register(Object o, Class<? extends Event> clazz) {
/*     */     byte b;
/*     */     int i;
/*     */     Method[] arrayOfMethod;
/*  29 */     for (i = (arrayOfMethod = o.getClass().getDeclaredMethods()).length, b = 0; b < i; ) { Method method = arrayOfMethod[b];
/*  30 */       if (!isMethodBad(method, clazz)) {
/*  31 */         register(method, o);
/*     */       }
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   private static void register(Method method, Object o) {
/*  38 */     Class<?> clazz = method.getParameterTypes()[0];
/*  39 */     Data methodData = new Data(o, method, ((SubscribeEvent)method.<SubscribeEvent>getAnnotation(SubscribeEvent.class)).value());
/*     */     
/*  41 */     if (!methodData.target.isAccessible()) {
/*  42 */       methodData.target.setAccessible(true);
/*     */     }
/*     */     
/*  45 */     if (REGISTRY_MAP.containsKey(clazz)) {
/*  46 */       if (!((ArrayHelper<Data>)REGISTRY_MAP.get(clazz)).contains(methodData)) {
/*  47 */         ((ArrayHelper<Data>)REGISTRY_MAP.get(clazz)).add(methodData);
/*  48 */         sortListValue((Class)clazz);
/*     */       } 
/*     */     } else {
/*  51 */       REGISTRY_MAP.put(clazz, new ArrayHelper<Data>(methodData)
/*     */           {
/*     */           
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unregister(Object o) {
/*  62 */     for (ArrayHelper<Data> flexibalArray : REGISTRY_MAP.values()) {
/*  63 */       for (Data methodData : flexibalArray) {
/*  64 */         if (methodData.source.equals(o)) {
/*  65 */           flexibalArray.remove(methodData);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     cleanMap(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void unregister(Object o, Class<? extends Event> clazz) {
/*  75 */     if (REGISTRY_MAP.containsKey(clazz)) {
/*  76 */       for (Data methodData : REGISTRY_MAP.get(clazz)) {
/*  77 */         if (methodData.source.equals(o)) {
/*  78 */           ((ArrayHelper<Data>)REGISTRY_MAP.get(clazz)).remove(methodData);
/*     */         }
/*     */       } 
/*     */       
/*  82 */       cleanMap(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void post(Event event) {
/*  87 */     event.call();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void cleanMap(boolean b) {
/*  93 */     Iterator<Map.Entry<Class<? extends Event>, ArrayHelper<Data>>> iterator = REGISTRY_MAP.entrySet().iterator();
/*     */     
/*  95 */     while (iterator.hasNext()) {
/*  96 */       if (!b || ((ArrayHelper)((Map.Entry)iterator.next()).getValue()).isEmpty()) {
/*  97 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void removeEnty(Class<? extends Event> clazz) {
/* 104 */     Iterator<Map.Entry<Class<? extends Event>, ArrayHelper<Data>>> iterator = REGISTRY_MAP.entrySet().iterator();
/*     */     
/* 106 */     while (iterator.hasNext()) {
/* 107 */       if (((Class)((Map.Entry)iterator.next()).getKey()).equals(clazz)) {
/* 108 */         iterator.remove();
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void sortListValue(Class<? extends Event> clazz) {
/* 116 */     ArrayHelper<Data> flexibleArray = new ArrayHelper<>(); byte b; int i;
/*     */     byte[] arrayOfByte;
/* 118 */     for (i = (arrayOfByte = Priority.VALUE_ARRAY).length, b = 0; b < i; ) { byte b1 = arrayOfByte[b];
/* 119 */       for (Data methodData : REGISTRY_MAP.get(clazz)) {
/* 120 */         if (methodData.priority == b1) {
/* 121 */           flexibleArray.add(methodData);
/*     */         }
/*     */       } 
/*     */       b++; }
/*     */     
/* 126 */     REGISTRY_MAP.put(clazz, flexibleArray);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isMethodBad(Method method) {
/* 131 */     return !((method.getParameterTypes()).length == 1 && method.isAnnotationPresent((Class)SubscribeEvent.class));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isMethodBad(Method method, Class<? extends Event> clazz) {
/* 136 */     return !(!isMethodBad(method) && !method.getParameterTypes()[0].equals(clazz));
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayHelper<Data> get(Class<? extends Event> clazz) {
/* 141 */     return REGISTRY_MAP.get(clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void shutdown() {
/* 146 */     REGISTRY_MAP.clear();
/*     */   }
/*     */ 
/*     */   
/* 150 */   private static final Map<Class<? extends Event>, ArrayHelper<Data>> REGISTRY_MAP = new HashMap<>();
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\event\EventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */