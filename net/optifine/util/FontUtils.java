/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class FontUtils
/*     */ {
/*     */   public static Properties readFontProperties(ResourceLocation locationFontTexture) {
/*  13 */     String s = locationFontTexture.getResourcePath();
/*  14 */     Properties properties = new PropertiesOrdered();
/*  15 */     String s1 = ".png";
/*     */     
/*  17 */     if (!s.endsWith(s1)) {
/*  18 */       return properties;
/*     */     }
/*  20 */     String s2 = String.valueOf(s.substring(0, s.length() - s1.length())) + ".properties";
/*     */     
/*     */     try {
/*  23 */       ResourceLocation resourcelocation = new ResourceLocation(locationFontTexture.getResourceDomain(), s2);
/*  24 */       InputStream inputstream = Config.getResourceStream(Config.getResourceManager(), resourcelocation);
/*     */       
/*  26 */       if (inputstream == null) {
/*  27 */         return properties;
/*     */       }
/*     */       
/*  30 */       Config.log("Loading " + s2);
/*  31 */       properties.load(inputstream);
/*  32 */       inputstream.close();
/*  33 */     } catch (FileNotFoundException fileNotFoundException) {
/*     */     
/*  35 */     } catch (IOException ioexception) {
/*  36 */       ioexception.printStackTrace();
/*     */     } 
/*     */     
/*  39 */     return properties;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void readCustomCharWidths(Properties props, float[] charWidth) {
/*  44 */     for (Object s0 : props.keySet()) {
/*  45 */       String s = (String)s0;
/*  46 */       String s1 = "width.";
/*     */       
/*  48 */       if (s.startsWith(s1)) {
/*  49 */         String s2 = s.substring(s1.length());
/*  50 */         int i = Config.parseInt(s2, -1);
/*     */         
/*  52 */         if (i >= 0 && i < charWidth.length) {
/*  53 */           String s3 = props.getProperty(s);
/*  54 */           float f = Config.parseFloat(s3, -1.0F);
/*     */           
/*  56 */           if (f >= 0.0F) {
/*  57 */             charWidth[i] = f;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float readFloat(Properties props, String key, float defOffset) {
/*  65 */     String s = props.getProperty(key);
/*     */     
/*  67 */     if (s == null) {
/*  68 */       return defOffset;
/*     */     }
/*  70 */     float f = Config.parseFloat(s, Float.MIN_VALUE);
/*     */     
/*  72 */     if (f == Float.MIN_VALUE) {
/*  73 */       Config.warn("Invalid value for " + key + ": " + s);
/*  74 */       return defOffset;
/*     */     } 
/*  76 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean readBoolean(Properties props, String key, boolean defVal) {
/*  82 */     String s = props.getProperty(key);
/*     */     
/*  84 */     if (s == null) {
/*  85 */       return defVal;
/*     */     }
/*  87 */     String s1 = s.toLowerCase().trim();
/*     */     
/*  89 */     if (!s1.equals("true") && !s1.equals("on")) {
/*  90 */       if (!s1.equals("false") && !s1.equals("off")) {
/*  91 */         Config.warn("Invalid value for " + key + ": " + s);
/*  92 */         return defVal;
/*     */       } 
/*  94 */       return false;
/*     */     } 
/*     */     
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceLocation getHdFontLocation(ResourceLocation fontLoc) {
/* 103 */     if (!Config.isCustomFonts())
/* 104 */       return fontLoc; 
/* 105 */     if (fontLoc == null)
/* 106 */       return fontLoc; 
/* 107 */     if (!Config.isMinecraftThread()) {
/* 108 */       return fontLoc;
/*     */     }
/* 110 */     String s = fontLoc.getResourcePath();
/* 111 */     String s1 = "textures/";
/* 112 */     String s2 = "mcpatcher/";
/*     */     
/* 114 */     if (!s.startsWith(s1)) {
/* 115 */       return fontLoc;
/*     */     }
/* 117 */     s = s.substring(s1.length());
/* 118 */     s = String.valueOf(s2) + s;
/* 119 */     ResourceLocation resourcelocation = new ResourceLocation(fontLoc.getResourceDomain(), s);
/* 120 */     return Config.hasResource(Config.getResourceManager(), resourcelocation) ? resourcelocation : fontLoc;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\FontUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */