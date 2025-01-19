/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.Enumeration;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public class ShaderPackZip
/*     */   implements IShaderPack
/*     */ {
/*     */   protected File packFile;
/*     */   protected ZipFile packZipFile;
/*     */   protected String baseFolder;
/*     */   
/*     */   public ShaderPackZip(String name, File file) {
/*  25 */     this.packFile = file;
/*  26 */     this.packZipFile = null;
/*  27 */     this.baseFolder = "";
/*     */   }
/*     */   
/*     */   public void close() {
/*  31 */     if (this.packZipFile != null) {
/*     */       try {
/*  33 */         this.packZipFile.close();
/*  34 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */       
/*  38 */       this.packZipFile = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public InputStream getResourceAsStream(String resName) {
/*     */     try {
/*  44 */       if (this.packZipFile == null) {
/*  45 */         this.packZipFile = new ZipFile(this.packFile);
/*  46 */         this.baseFolder = detectBaseFolder(this.packZipFile);
/*     */       } 
/*     */       
/*  49 */       String s = StrUtils.removePrefix(resName, "/");
/*     */       
/*  51 */       if (s.contains("..")) {
/*  52 */         s = resolveRelative(s);
/*     */       }
/*     */       
/*  55 */       ZipEntry zipentry = this.packZipFile.getEntry(String.valueOf(this.baseFolder) + s);
/*  56 */       return (zipentry == null) ? null : this.packZipFile.getInputStream(zipentry);
/*  57 */     } catch (Exception var4) {
/*  58 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String resolveRelative(String name) {
/*  63 */     Deque<String> deque = new ArrayDeque<>();
/*  64 */     String[] astring = Config.tokenize(name, "/");
/*     */     
/*  66 */     for (int i = 0; i < astring.length; i++) {
/*  67 */       String s = astring[i];
/*     */       
/*  69 */       if (s.equals("..")) {
/*  70 */         if (deque.isEmpty()) {
/*  71 */           return "";
/*     */         }
/*     */         
/*  74 */         deque.removeLast();
/*     */       } else {
/*  76 */         deque.add(s);
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     String s1 = Joiner.on('/').join(deque);
/*  81 */     return s1;
/*     */   }
/*     */   
/*     */   private String detectBaseFolder(ZipFile zip) {
/*  85 */     ZipEntry zipentry = zip.getEntry("shaders/");
/*     */     
/*  87 */     if (zipentry != null && zipentry.isDirectory()) {
/*  88 */       return "";
/*     */     }
/*  90 */     Pattern pattern = Pattern.compile("([^/]+/)shaders/");
/*  91 */     Enumeration<? extends ZipEntry> enumeration = zip.entries();
/*     */     
/*  93 */     while (enumeration.hasMoreElements()) {
/*  94 */       ZipEntry zipentry1 = enumeration.nextElement();
/*  95 */       String s = zipentry1.getName();
/*  96 */       Matcher matcher = pattern.matcher(s);
/*     */       
/*  98 */       if (matcher.matches()) {
/*  99 */         String s1 = matcher.group(1);
/*     */         
/* 101 */         if (s1 != null) {
/* 102 */           if (s1.equals("shaders/")) {
/* 103 */             return "";
/*     */           }
/*     */           
/* 106 */           return s1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasDirectory(String resName) {
/*     */     try {
/* 117 */       if (this.packZipFile == null) {
/* 118 */         this.packZipFile = new ZipFile(this.packFile);
/* 119 */         this.baseFolder = detectBaseFolder(this.packZipFile);
/*     */       } 
/*     */       
/* 122 */       String s = StrUtils.removePrefix(resName, "/");
/* 123 */       ZipEntry zipentry = this.packZipFile.getEntry(String.valueOf(this.baseFolder) + s);
/* 124 */       return (zipentry != null);
/* 125 */     } catch (IOException var4) {
/* 126 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getName() {
/* 131 */     return this.packFile.getName();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\ShaderPackZip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */