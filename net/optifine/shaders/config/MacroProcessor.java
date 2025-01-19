/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public class MacroProcessor
/*     */ {
/*     */   public static InputStream process(InputStream in, String path) throws IOException {
/*  18 */     String s = Config.readInputStream(in, "ASCII");
/*  19 */     String s1 = getMacroHeader(s);
/*     */     
/*  21 */     if (!s1.isEmpty()) {
/*  22 */       s = String.valueOf(s1) + s;
/*     */       
/*  24 */       if (Shaders.saveFinalShaders) {
/*  25 */         String s2 = String.valueOf(path.replace(':', '/')) + ".pre";
/*  26 */         Shaders.saveShader(s2, s);
/*     */       } 
/*     */       
/*  29 */       s = process(s);
/*     */     } 
/*     */     
/*  32 */     if (Shaders.saveFinalShaders) {
/*  33 */       String s3 = path.replace(':', '/');
/*  34 */       Shaders.saveShader(s3, s);
/*     */     } 
/*     */     
/*  37 */     byte[] abyte = s.getBytes("ASCII");
/*  38 */     ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte);
/*  39 */     return bytearrayinputstream;
/*     */   }
/*     */   
/*     */   public static String process(String strIn) throws IOException {
/*  43 */     StringReader stringreader = new StringReader(strIn);
/*  44 */     BufferedReader bufferedreader = new BufferedReader(stringreader);
/*  45 */     MacroState macrostate = new MacroState();
/*  46 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*     */     while (true) {
/*  49 */       String s = bufferedreader.readLine();
/*     */       
/*  51 */       if (s == null) {
/*  52 */         s = stringbuilder.toString();
/*  53 */         return s;
/*     */       } 
/*     */       
/*  56 */       if (macrostate.processLine(s) && !MacroState.isMacroLine(s)) {
/*  57 */         stringbuilder.append(s);
/*  58 */         stringbuilder.append("\n");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getMacroHeader(String str) throws IOException {
/*  64 */     StringBuilder stringbuilder = new StringBuilder();
/*  65 */     List<ShaderOption> list = null;
/*  66 */     List<ShaderMacro> list1 = null;
/*  67 */     StringReader stringreader = new StringReader(str);
/*  68 */     BufferedReader bufferedreader = new BufferedReader(stringreader);
/*     */     
/*     */     while (true) {
/*  71 */       String s = bufferedreader.readLine();
/*     */       
/*  73 */       if (s == null) {
/*  74 */         return stringbuilder.toString();
/*     */       }
/*     */       
/*  77 */       if (MacroState.isMacroLine(s)) {
/*  78 */         if (stringbuilder.length() == 0) {
/*  79 */           stringbuilder.append(ShaderMacros.getFixedMacroLines());
/*     */         }
/*     */         
/*  82 */         if (list1 == null) {
/*  83 */           list1 = new ArrayList<>(Arrays.asList(ShaderMacros.getExtensions()));
/*     */         }
/*     */         
/*  86 */         Iterator<ShaderMacro> iterator = list1.iterator();
/*     */         
/*  88 */         while (iterator.hasNext()) {
/*  89 */           ShaderMacro shadermacro = iterator.next();
/*     */           
/*  91 */           if (s.contains(shadermacro.getName())) {
/*  92 */             stringbuilder.append(shadermacro.getSourceLine());
/*  93 */             stringbuilder.append("\n");
/*  94 */             iterator.remove();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static List<ShaderOption> getMacroOptions() {
/* 102 */     List<ShaderOption> list = new ArrayList<>();
/* 103 */     ShaderOption[] ashaderoption = Shaders.getShaderPackOptions();
/*     */     
/* 105 */     for (int i = 0; i < ashaderoption.length; i++) {
/* 106 */       ShaderOption shaderoption = ashaderoption[i];
/* 107 */       String s = shaderoption.getSourceLine();
/*     */       
/* 109 */       if (s != null && s.startsWith("#")) {
/* 110 */         list.add(shaderoption);
/*     */       }
/*     */     } 
/*     */     
/* 114 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\config\MacroProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */