/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import net.minecraft.client.resources.AbstractResourcePack;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class ResUtils
/*     */ {
/*     */   public static String[] collectFiles(String prefix, String suffix) {
/*  26 */     return collectFiles(new String[] { prefix }, new String[] { suffix });
/*     */   }
/*     */   
/*     */   public static String[] collectFiles(String[] prefixes, String[] suffixes) {
/*  30 */     Set<String> set = new LinkedHashSet<>();
/*  31 */     IResourcePack[] airesourcepack = Config.getResourcePacks();
/*     */     
/*  33 */     for (int i = 0; i < airesourcepack.length; i++) {
/*  34 */       IResourcePack iresourcepack = airesourcepack[i];
/*  35 */       String[] astring = collectFiles(iresourcepack, prefixes, suffixes, (String[])null);
/*  36 */       set.addAll(Arrays.asList(astring));
/*     */     } 
/*     */     
/*  39 */     String[] astring1 = set.<String>toArray(new String[set.size()]);
/*  40 */     return astring1;
/*     */   }
/*     */   
/*     */   public static String[] collectFiles(IResourcePack rp, String prefix, String suffix, String[] defaultPaths) {
/*  44 */     return collectFiles(rp, new String[] { prefix }, new String[] { suffix }, defaultPaths);
/*     */   }
/*     */   
/*     */   public static String[] collectFiles(IResourcePack rp, String[] prefixes, String[] suffixes) {
/*  48 */     return collectFiles(rp, prefixes, suffixes, (String[])null);
/*     */   }
/*     */   
/*     */   public static String[] collectFiles(IResourcePack rp, String[] prefixes, String[] suffixes, String[] defaultPaths) {
/*  52 */     if (rp instanceof net.minecraft.client.resources.DefaultResourcePack)
/*  53 */       return collectFilesFixed(rp, defaultPaths); 
/*  54 */     if (!(rp instanceof AbstractResourcePack)) {
/*  55 */       Config.warn("Unknown resource pack type: " + rp);
/*  56 */       return new String[0];
/*     */     } 
/*  58 */     AbstractResourcePack abstractresourcepack = (AbstractResourcePack)rp;
/*  59 */     File file1 = abstractresourcepack.resourcePackFile;
/*     */     
/*  61 */     if (file1 == null)
/*  62 */       return new String[0]; 
/*  63 */     if (file1.isDirectory())
/*  64 */       return collectFilesFolder(file1, "", prefixes, suffixes); 
/*  65 */     if (file1.isFile()) {
/*  66 */       return collectFilesZIP(file1, prefixes, suffixes);
/*     */     }
/*  68 */     Config.warn("Unknown resource pack file: " + file1);
/*  69 */     return new String[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] collectFilesFixed(IResourcePack rp, String[] paths) {
/*  75 */     if (paths == null) {
/*  76 */       return new String[0];
/*     */     }
/*  78 */     List<String> list = new ArrayList();
/*     */     
/*  80 */     for (int i = 0; i < paths.length; i++) {
/*  81 */       String s = paths[i];
/*  82 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */       
/*  84 */       if (rp.resourceExists(resourcelocation)) {
/*  85 */         list.add(s);
/*     */       }
/*     */     } 
/*     */     
/*  89 */     String[] astring = list.<String>toArray(new String[list.size()]);
/*  90 */     return astring;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] collectFilesFolder(File tpFile, String basePath, String[] prefixes, String[] suffixes) {
/*  95 */     List<String> list = new ArrayList();
/*  96 */     String s = "assets/minecraft/";
/*  97 */     File[] afile = tpFile.listFiles();
/*     */     
/*  99 */     if (afile == null) {
/* 100 */       return new String[0];
/*     */     }
/* 102 */     for (int i = 0; i < afile.length; i++) {
/* 103 */       File file1 = afile[i];
/*     */       
/* 105 */       if (file1.isFile()) {
/* 106 */         String s3 = String.valueOf(basePath) + file1.getName();
/*     */         
/* 108 */         if (s3.startsWith(s)) {
/* 109 */           s3 = s3.substring(s.length());
/*     */           
/* 111 */           if (StrUtils.startsWith(s3, prefixes) && StrUtils.endsWith(s3, suffixes)) {
/* 112 */             list.add(s3);
/*     */           }
/*     */         } 
/* 115 */       } else if (file1.isDirectory()) {
/* 116 */         String s1 = String.valueOf(basePath) + file1.getName() + "/";
/* 117 */         String[] astring = collectFilesFolder(file1, s1, prefixes, suffixes);
/*     */         
/* 119 */         for (int j = 0; j < astring.length; j++) {
/* 120 */           String s2 = astring[j];
/* 121 */           list.add(s2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     String[] astring1 = list.<String>toArray(new String[list.size()]);
/* 127 */     return astring1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] collectFilesZIP(File tpFile, String[] prefixes, String[] suffixes) {
/* 132 */     List<String> list = new ArrayList();
/* 133 */     String s = "assets/minecraft/";
/*     */     
/*     */     try {
/* 136 */       ZipFile zipfile = new ZipFile(tpFile);
/* 137 */       Enumeration<? extends ZipEntry> enumeration = zipfile.entries();
/*     */       
/* 139 */       while (enumeration.hasMoreElements()) {
/* 140 */         ZipEntry zipentry = enumeration.nextElement();
/* 141 */         String s1 = zipentry.getName();
/*     */         
/* 143 */         if (s1.startsWith(s)) {
/* 144 */           s1 = s1.substring(s.length());
/*     */           
/* 146 */           if (StrUtils.startsWith(s1, prefixes) && StrUtils.endsWith(s1, suffixes)) {
/* 147 */             list.add(s1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 152 */       zipfile.close();
/* 153 */       String[] astring = list.<String>toArray(new String[list.size()]);
/* 154 */       return astring;
/* 155 */     } catch (IOException ioexception) {
/* 156 */       ioexception.printStackTrace();
/* 157 */       return new String[0];
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isLowercase(String str) {
/* 162 */     return str.equals(str.toLowerCase(Locale.ROOT));
/*     */   }
/*     */   
/*     */   public static Properties readProperties(String path, String module) {
/* 166 */     ResourceLocation resourcelocation = new ResourceLocation(path);
/*     */     
/*     */     try {
/* 169 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 171 */       if (inputstream == null) {
/* 172 */         return null;
/*     */       }
/* 174 */       Properties properties = new PropertiesOrdered();
/* 175 */       properties.load(inputstream);
/* 176 */       inputstream.close();
/* 177 */       Config.dbg(module + ": Loading " + path);
/* 178 */       return properties;
/*     */     }
/* 180 */     catch (FileNotFoundException var5) {
/* 181 */       return null;
/* 182 */     } catch (IOException var6) {
/* 183 */       Config.warn(module + ": Error reading " + path);
/* 184 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Properties readProperties(InputStream in, String module) {
/* 189 */     if (in == null) {
/* 190 */       return null;
/*     */     }
/*     */     try {
/* 193 */       Properties properties = new PropertiesOrdered();
/* 194 */       properties.load(in);
/* 195 */       in.close();
/* 196 */       return properties;
/* 197 */     } catch (FileNotFoundException var3) {
/* 198 */       return null;
/* 199 */     } catch (IOException var4) {
/* 200 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\ResUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */