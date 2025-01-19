/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.MathUtils;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ 
/*     */ public class CustomPanorama
/*     */ {
/*  16 */   private static CustomPanoramaProperties customPanoramaProperties = null;
/*  17 */   private static final Random random = new Random();
/*     */   
/*     */   public static CustomPanoramaProperties getCustomPanoramaProperties() {
/*  20 */     return customPanoramaProperties;
/*     */   }
/*     */   
/*     */   public static void update() {
/*  24 */     customPanoramaProperties = null;
/*  25 */     String[] astring = getPanoramaFolders();
/*     */     
/*  27 */     if (astring.length > 1) {
/*  28 */       PropertiesOrdered propertiesOrdered; Properties[] aproperties = getPanoramaProperties(astring);
/*  29 */       int[] aint = getWeights(aproperties);
/*  30 */       int i = getRandomIndex(aint);
/*  31 */       String s = astring[i];
/*  32 */       Properties properties = aproperties[i];
/*     */       
/*  34 */       if (properties == null) {
/*  35 */         properties = aproperties[0];
/*     */       }
/*     */       
/*  38 */       if (properties == null) {
/*  39 */         propertiesOrdered = new PropertiesOrdered();
/*     */       }
/*     */       
/*  42 */       CustomPanoramaProperties custompanoramaproperties = new CustomPanoramaProperties(s, (Properties)propertiesOrdered);
/*  43 */       customPanoramaProperties = custompanoramaproperties;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String[] getPanoramaFolders() {
/*  48 */     List<String> list = new ArrayList<>();
/*  49 */     list.add("textures/gui/title/background");
/*     */     
/*  51 */     for (int i = 0; i < 100; i++) {
/*  52 */       String s = "optifine/gui/background" + i;
/*  53 */       String s1 = String.valueOf(s) + "/panorama_0.png";
/*  54 */       ResourceLocation resourcelocation = new ResourceLocation(s1);
/*     */       
/*  56 */       if (Config.hasResource(resourcelocation)) {
/*  57 */         list.add(s);
/*     */       }
/*     */     } 
/*     */     
/*  61 */     String[] astring = list.<String>toArray(new String[list.size()]);
/*  62 */     return astring;
/*     */   }
/*     */   
/*     */   private static Properties[] getPanoramaProperties(String[] folders) {
/*  66 */     Properties[] aproperties = new Properties[folders.length];
/*     */     
/*  68 */     for (int i = 0; i < folders.length; i++) {
/*  69 */       String s = folders[i];
/*     */       
/*  71 */       if (i == 0) {
/*  72 */         s = "optifine/gui";
/*     */       } else {
/*  74 */         Config.dbg("CustomPanorama: " + s);
/*     */       } 
/*     */       
/*  77 */       ResourceLocation resourcelocation = new ResourceLocation(String.valueOf(s) + "/background.properties");
/*     */       
/*     */       try {
/*  80 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */         
/*  82 */         if (inputstream != null) {
/*  83 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  84 */           propertiesOrdered.load(inputstream);
/*  85 */           Config.dbg("CustomPanorama: " + resourcelocation.getResourcePath());
/*  86 */           aproperties[i] = (Properties)propertiesOrdered;
/*  87 */           inputstream.close();
/*     */         } 
/*  89 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  94 */     return aproperties;
/*     */   }
/*     */   
/*     */   private static int[] getWeights(Properties[] properties) {
/*  98 */     int[] aint = new int[properties.length];
/*     */     
/* 100 */     for (int i = 0; i < aint.length; i++) {
/* 101 */       Properties property = properties[i];
/*     */       
/* 103 */       if (property == null) {
/* 104 */         property = properties[0];
/*     */       }
/*     */       
/* 107 */       if (property == null) {
/* 108 */         aint[i] = 1;
/*     */       } else {
/* 110 */         String s = property.getProperty("weight", null);
/* 111 */         aint[i] = Config.parseInt(s, 1);
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     return aint;
/*     */   }
/*     */   
/*     */   private static int getRandomIndex(int[] weights) {
/* 119 */     int i = MathUtils.getSum(weights);
/* 120 */     int j = random.nextInt(i);
/* 121 */     int k = 0;
/*     */     
/* 123 */     for (int l = 0; l < weights.length; l++) {
/* 124 */       k += weights[l];
/*     */       
/* 126 */       if (k > j) {
/* 127 */         return l;
/*     */       }
/*     */     } 
/*     */     
/* 131 */     return weights.length - 1;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomPanorama.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */