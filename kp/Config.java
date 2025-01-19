/*     */ package kp;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import kp.input.InputMethod;
/*     */ import kp.keyboards.KeyLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Config
/*     */ {
/*  15 */   public static File configFile = new File(InputMethod.getMinecraftInterface().getMinecraftDir(), "koreanpatch.cfg");
/*     */   public static boolean DELETE_JASO = true;
/*  17 */   public static final KeyLoader keyloader = new KeyLoader(); static {
/*  18 */     load();
/*     */   }
/*     */   
/*     */   public static void load() {
/*  22 */     FileInputStream fin = null;
/*     */     try {
/*  24 */       if (!configFile.exists()) {
/*  25 */         configFile.getParentFile().mkdirs();
/*  26 */         save();
/*     */       } else {
/*     */         
/*  29 */         fin = new FileInputStream(configFile);
/*  30 */         Properties p = new Properties();
/*  31 */         p.load(fin);
/*  32 */         DELETE_JASO = p.getProperty("DELETE_JASO").equals("true");
/*  33 */         keyloader.setKeyboard(Integer.parseInt(p.getProperty("KEYBOARD")));
/*     */       }
/*     */     
/*  36 */     } catch (IOException e) {
/*  37 */       e.printStackTrace();
/*     */       try {
/*  39 */         if (fin != null) {
/*  40 */           fin.close();
/*     */         }
/*     */       }
/*  43 */       catch (IOException e2) {
/*  44 */         e2.printStackTrace();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } finally {
/*     */       try {
/*  50 */         if (fin != null) {
/*  51 */           fin.close();
/*     */         }
/*     */       }
/*  54 */       catch (IOException e2) {
/*  55 */         e2.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     try {
/*  59 */       if (fin != null) {
/*  60 */         fin.close();
/*     */       }
/*     */     }
/*  63 */     catch (IOException e2) {
/*  64 */       e2.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void save() {
/*  69 */     FileWriter writer = null;
/*     */     try {
/*  71 */       if (!configFile.getParentFile().exists()) {
/*  72 */         configFile.getParentFile().mkdirs();
/*     */       }
/*  74 */       writer = new FileWriter(configFile);
/*  75 */       Properties p = new Properties();
/*  76 */       p.setProperty("DELETE_JASO", String.valueOf(DELETE_JASO));
/*  77 */       p.setProperty("KEYBOARD", String.valueOf(keyloader.getKeyboardArrayIndex()));
/*  78 */       p.store(writer, "Korean Patch Configuration File.");
/*     */     }
/*  80 */     catch (IOException e) {
/*  81 */       e.printStackTrace();
/*     */       try {
/*  83 */         if (writer != null) {
/*  84 */           writer.close();
/*     */         }
/*     */       }
/*  87 */       catch (IOException e2) {
/*  88 */         e2.printStackTrace();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } finally {
/*     */       try {
/*  94 */         if (writer != null) {
/*  95 */           writer.close();
/*     */         }
/*     */       }
/*  98 */       catch (IOException e2) {
/*  99 */         e2.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     try {
/* 103 */       if (writer != null) {
/* 104 */         writer.close();
/*     */       }
/*     */     }
/* 107 */     catch (IOException e2) {
/* 108 */       e2.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\kp\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */