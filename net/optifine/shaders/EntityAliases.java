/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.shaders.config.MacroProcessor;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ 
/*     */ public class EntityAliases
/*     */ {
/*  19 */   private static int[] entityAliases = null;
/*     */   private static boolean updateOnResourcesReloaded;
/*     */   
/*     */   public static int getEntityAliasId(int entityId) {
/*  23 */     if (entityAliases == null)
/*  24 */       return -1; 
/*  25 */     if (entityId >= 0 && entityId < entityAliases.length) {
/*  26 */       int i = entityAliases[entityId];
/*  27 */       return i;
/*     */     } 
/*  29 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  34 */     if (updateOnResourcesReloaded) {
/*  35 */       updateOnResourcesReloaded = false;
/*  36 */       update(Shaders.getShaderPack());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void update(IShaderPack shaderPack) {
/*  41 */     reset();
/*     */     
/*  43 */     if (shaderPack != null) {
/*  44 */       if (Reflector.Loader_getActiveModList.exists() && Config.getResourceManager() == null) {
/*  45 */         Config.dbg("[Shaders] Delayed loading of entity mappings after resources are loaded");
/*  46 */         updateOnResourcesReloaded = true;
/*     */       } else {
/*  48 */         List<Integer> list = new ArrayList<>();
/*  49 */         String s = "/shaders/entity.properties";
/*  50 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*     */         
/*  52 */         if (inputstream != null) {
/*  53 */           loadEntityAliases(inputstream, s, list);
/*     */         }
/*     */         
/*  56 */         loadModEntityAliases(list);
/*     */         
/*  58 */         if (list.size() > 0) {
/*  59 */           entityAliases = toArray(list);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void loadModEntityAliases(List<Integer> listEntityAliases) {
/*  66 */     String[] astring = ReflectorForge.getForgeModIds();
/*     */     
/*  68 */     for (int i = 0; i < astring.length; i++) {
/*  69 */       String s = astring[i];
/*     */       
/*     */       try {
/*  72 */         ResourceLocation resourcelocation = new ResourceLocation(s, "shaders/entity.properties");
/*  73 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*  74 */         loadEntityAliases(inputstream, resourcelocation.toString(), listEntityAliases);
/*  75 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadEntityAliases(InputStream in, String path, List<Integer> listEntityAliases) {
/*  82 */     if (in != null) {
/*     */       try {
/*  84 */         in = MacroProcessor.process(in, path);
/*  85 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  86 */         propertiesOrdered.load(in);
/*  87 */         in.close();
/*  88 */         Config.dbg("[Shaders] Parsing entity mappings: " + path);
/*  89 */         ConnectedParser connectedparser = new ConnectedParser("Shaders");
/*     */         
/*  91 */         for (Object s0 : propertiesOrdered.keySet()) {
/*  92 */           String s = (String)s0;
/*  93 */           String s1 = propertiesOrdered.getProperty(s);
/*  94 */           String s2 = "entity.";
/*     */           
/*  96 */           if (!s.startsWith(s2)) {
/*  97 */             Config.warn("[Shaders] Invalid entity ID: " + s); continue;
/*     */           } 
/*  99 */           String s3 = StrUtils.removePrefix(s, s2);
/* 100 */           int i = Config.parseInt(s3, -1);
/*     */           
/* 102 */           if (i < 0) {
/* 103 */             Config.warn("[Shaders] Invalid entity alias ID: " + i); continue;
/*     */           } 
/* 105 */           int[] aint = connectedparser.parseEntities(s1);
/*     */           
/* 107 */           if (aint != null && aint.length >= 1) {
/* 108 */             for (int j = 0; j < aint.length; j++) {
/* 109 */               int k = aint[j];
/* 110 */               addToList(listEntityAliases, k, i);
/*     */             }  continue;
/*     */           } 
/* 113 */           Config.warn("[Shaders] Invalid entity ID mapping: " + s + "=" + s1);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 118 */       catch (IOException var15) {
/* 119 */         Config.warn("[Shaders] Error reading: " + path);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void addToList(List<Integer> list, int index, int val) {
/* 125 */     while (list.size() <= index) {
/* 126 */       list.add(Integer.valueOf(-1));
/*     */     }
/*     */     
/* 129 */     list.set(index, Integer.valueOf(val));
/*     */   }
/*     */   
/*     */   private static int[] toArray(List<Integer> list) {
/* 133 */     int[] aint = new int[list.size()];
/*     */     
/* 135 */     for (int i = 0; i < aint.length; i++) {
/* 136 */       aint[i] = ((Integer)list.get(i)).intValue();
/*     */     }
/*     */     
/* 139 */     return aint;
/*     */   }
/*     */   
/*     */   public static void reset() {
/* 143 */     entityAliases = null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\EntityAliases.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */