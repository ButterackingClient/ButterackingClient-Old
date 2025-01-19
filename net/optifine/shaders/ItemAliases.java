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
/*     */ public class ItemAliases
/*     */ {
/*  19 */   private static int[] itemAliases = null;
/*     */   private static boolean updateOnResourcesReloaded;
/*     */   private static final int NO_ALIAS = -2147483648;
/*     */   
/*     */   public static int getItemAliasId(int itemId) {
/*  24 */     if (itemAliases == null)
/*  25 */       return itemId; 
/*  26 */     if (itemId >= 0 && itemId < itemAliases.length) {
/*  27 */       int i = itemAliases[itemId];
/*  28 */       return (i == Integer.MIN_VALUE) ? itemId : i;
/*     */     } 
/*  30 */     return itemId;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded() {
/*  35 */     if (updateOnResourcesReloaded) {
/*  36 */       updateOnResourcesReloaded = false;
/*  37 */       update(Shaders.getShaderPack());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void update(IShaderPack shaderPack) {
/*  42 */     reset();
/*     */     
/*  44 */     if (shaderPack != null) {
/*  45 */       if (Reflector.Loader_getActiveModList.exists() && Config.getResourceManager() == null) {
/*  46 */         Config.dbg("[Shaders] Delayed loading of item mappings after resources are loaded");
/*  47 */         updateOnResourcesReloaded = true;
/*     */       } else {
/*  49 */         List<Integer> list = new ArrayList<>();
/*  50 */         String s = "/shaders/item.properties";
/*  51 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*     */         
/*  53 */         if (inputstream != null) {
/*  54 */           loadItemAliases(inputstream, s, list);
/*     */         }
/*     */         
/*  57 */         loadModItemAliases(list);
/*     */         
/*  59 */         if (list.size() > 0) {
/*  60 */           itemAliases = toArray(list);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void loadModItemAliases(List<Integer> listItemAliases) {
/*  67 */     String[] astring = ReflectorForge.getForgeModIds();
/*     */     
/*  69 */     for (int i = 0; i < astring.length; i++) {
/*  70 */       String s = astring[i];
/*     */       
/*     */       try {
/*  73 */         ResourceLocation resourcelocation = new ResourceLocation(s, "shaders/item.properties");
/*  74 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*  75 */         loadItemAliases(inputstream, resourcelocation.toString(), listItemAliases);
/*  76 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadItemAliases(InputStream in, String path, List<Integer> listItemAliases) {
/*  83 */     if (in != null) {
/*     */       try {
/*  85 */         in = MacroProcessor.process(in, path);
/*  86 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  87 */         propertiesOrdered.load(in);
/*  88 */         in.close();
/*  89 */         Config.dbg("[Shaders] Parsing item mappings: " + path);
/*  90 */         ConnectedParser connectedparser = new ConnectedParser("Shaders");
/*     */         
/*  92 */         for (Object s0 : propertiesOrdered.keySet()) {
/*  93 */           String s = (String)s0;
/*  94 */           String s1 = propertiesOrdered.getProperty(s);
/*  95 */           String s2 = "item.";
/*     */           
/*  97 */           if (!s.startsWith(s2)) {
/*  98 */             Config.warn("[Shaders] Invalid item ID: " + s); continue;
/*     */           } 
/* 100 */           String s3 = StrUtils.removePrefix(s, s2);
/* 101 */           int i = Config.parseInt(s3, -1);
/*     */           
/* 103 */           if (i < 0) {
/* 104 */             Config.warn("[Shaders] Invalid item alias ID: " + i); continue;
/*     */           } 
/* 106 */           int[] aint = connectedparser.parseItems(s1);
/*     */           
/* 108 */           if (aint != null && aint.length >= 1) {
/* 109 */             for (int j = 0; j < aint.length; j++) {
/* 110 */               int k = aint[j];
/* 111 */               addToList(listItemAliases, k, i);
/*     */             }  continue;
/*     */           } 
/* 114 */           Config.warn("[Shaders] Invalid item ID mapping: " + s + "=" + s1);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 119 */       catch (IOException var15) {
/* 120 */         Config.warn("[Shaders] Error reading: " + path);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void addToList(List<Integer> list, int index, int val) {
/* 126 */     while (list.size() <= index) {
/* 127 */       list.add(Integer.valueOf(-2147483648));
/*     */     }
/*     */     
/* 130 */     list.set(index, Integer.valueOf(val));
/*     */   }
/*     */   
/*     */   private static int[] toArray(List<Integer> list) {
/* 134 */     int[] aint = new int[list.size()];
/*     */     
/* 136 */     for (int i = 0; i < aint.length; i++) {
/* 137 */       aint[i] = ((Integer)list.get(i)).intValue();
/*     */     }
/*     */     
/* 140 */     return aint;
/*     */   }
/*     */   
/*     */   public static void reset() {
/* 144 */     itemAliases = null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\ItemAliases.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */