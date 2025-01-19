/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Locale
/*     */ {
/*  22 */   private static final Splitter splitter = Splitter.on('=').limit(2);
/*  23 */   private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*  24 */   Map<String, String> properties = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   private boolean unicode;
/*     */ 
/*     */   
/*     */   public synchronized void loadLocaleDataFiles(IResourceManager resourceManager, List<String> languageList) {
/*  31 */     this.properties.clear();
/*     */     
/*  33 */     for (String s : languageList) {
/*  34 */       String s1 = String.format("lang/%s.lang", new Object[] { s });
/*     */       
/*  36 */       for (String s2 : resourceManager.getResourceDomains()) {
/*     */         try {
/*  38 */           loadLocaleData(resourceManager.getAllResources(new ResourceLocation(s2, s1)));
/*  39 */         } catch (IOException iOException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  45 */     checkUnicode();
/*     */   }
/*     */   
/*     */   public boolean isUnicode() {
/*  49 */     return this.unicode;
/*     */   }
/*     */   
/*     */   private void checkUnicode() {
/*  53 */     this.unicode = false;
/*  54 */     int i = 0;
/*  55 */     int j = 0;
/*     */     
/*  57 */     for (String s : this.properties.values()) {
/*  58 */       int k = s.length();
/*  59 */       j += k;
/*     */       
/*  61 */       for (int l = 0; l < k; l++) {
/*  62 */         if (s.charAt(l) >= 'Ā') {
/*  63 */           i++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     float f = i / j;
/*  69 */     this.unicode = (f > 0.1D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadLocaleData(List<IResource> resourcesList) throws IOException {
/*  76 */     for (IResource iresource : resourcesList) {
/*  77 */       InputStream inputstream = iresource.getInputStream();
/*     */       
/*     */       try {
/*  80 */         loadLocaleData(inputstream);
/*     */       } finally {
/*  82 */         IOUtils.closeQuietly(inputstream);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadLocaleData(InputStream inputStreamIn) throws IOException {
/*  88 */     for (String s : IOUtils.readLines(inputStreamIn, Charsets.UTF_8)) {
/*  89 */       if (!s.isEmpty() && s.charAt(0) != '#') {
/*  90 */         String[] astring = (String[])Iterables.toArray(splitter.split(s), String.class);
/*     */         
/*  92 */         if (astring != null && astring.length == 2) {
/*  93 */           String s1 = astring[0];
/*  94 */           String s2 = pattern.matcher(astring[1]).replaceAll("%$1s");
/*  95 */           this.properties.put(s1, s2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String translateKeyPrivate(String translateKey) {
/* 105 */     String s = this.properties.get(translateKey);
/* 106 */     return (s == null) ? translateKey : s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String formatMessage(String translateKey, Object[] parameters) {
/* 113 */     String s = translateKeyPrivate(translateKey);
/*     */     
/*     */     try {
/* 116 */       return String.format(s, parameters);
/* 117 */     } catch (IllegalFormatException var5) {
/* 118 */       return "Format error: " + s;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\Locale.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */