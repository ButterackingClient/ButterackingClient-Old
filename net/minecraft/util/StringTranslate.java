/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringTranslate
/*     */ {
/*  20 */   private static final Pattern numericVariablePattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  25 */   private static final Splitter equalSignSplitter = Splitter.on('=').limit(2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   private static StringTranslate instance = new StringTranslate();
/*  31 */   private final Map<String, String> languageList = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private long lastUpdateTimeInMilliseconds;
/*     */ 
/*     */ 
/*     */   
/*     */   public StringTranslate() {
/*     */     try {
/*  40 */       InputStream inputstream = StringTranslate.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");
/*     */       
/*  42 */       for (String s : IOUtils.readLines(inputstream, Charsets.UTF_8)) {
/*  43 */         if (!s.isEmpty() && s.charAt(0) != '#') {
/*  44 */           String[] astring = (String[])Iterables.toArray(equalSignSplitter.split(s), String.class);
/*     */           
/*  46 */           if (astring != null && astring.length == 2) {
/*  47 */             String s1 = astring[0];
/*  48 */             String s2 = numericVariablePattern.matcher(astring[1]).replaceAll("%$1s");
/*  49 */             this.languageList.put(s1, s2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  54 */       this.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
/*  55 */     } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static StringTranslate getInstance() {
/*  64 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void replaceWith(Map<String, String> p_135063_0_) {
/*  71 */     instance.languageList.clear();
/*  72 */     instance.languageList.putAll(p_135063_0_);
/*  73 */     instance.lastUpdateTimeInMilliseconds = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String translateKey(String key) {
/*  80 */     return tryTranslateKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String translateKeyFormat(String key, Object... format) {
/*  87 */     String s = tryTranslateKey(key);
/*     */     
/*     */     try {
/*  90 */       return String.format(s, format);
/*  91 */     } catch (IllegalFormatException var5) {
/*  92 */       return "Format error: " + s;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String tryTranslateKey(String key) {
/* 100 */     String s = this.languageList.get(key);
/* 101 */     return (s == null) ? key : s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isKeyTranslated(String key) {
/* 108 */     return this.languageList.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastUpdateTimeInMilliseconds() {
/* 115 */     return this.lastUpdateTimeInMilliseconds;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\StringTranslate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */