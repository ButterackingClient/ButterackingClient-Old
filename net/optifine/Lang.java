/*     */ package net.optifine;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Iterables;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ public class Lang
/*     */ {
/*  22 */   private static final Splitter splitter = Splitter.on('=').limit(2);
/*  23 */   private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
/*     */   
/*     */   public static void resourcesReloaded() {
/*  26 */     Map map = I18n.getLocaleProperties();
/*  27 */     List<String> list = new ArrayList<>();
/*  28 */     String s = "optifine/lang/";
/*  29 */     String s1 = "en_US";
/*  30 */     String s2 = ".lang";
/*  31 */     list.add(String.valueOf(s) + s1 + s2);
/*     */     
/*  33 */     if (!(Config.getGameSettings()).forceUnicodeFont.equals(s1)) {
/*  34 */       list.add(String.valueOf(s) + (Config.getGameSettings()).forceUnicodeFont + s2);
/*     */     }
/*     */     
/*  37 */     String[] astring = list.<String>toArray(new String[list.size()]);
/*  38 */     loadResources((IResourcePack)Config.getDefaultResourcePack(), astring, map);
/*  39 */     IResourcePack[] airesourcepack = Config.getResourcePacks();
/*     */     
/*  41 */     for (int i = 0; i < airesourcepack.length; i++) {
/*  42 */       IResourcePack iresourcepack = airesourcepack[i];
/*  43 */       loadResources(iresourcepack, astring, map);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadResources(IResourcePack rp, String[] files, Map localeProperties) {
/*     */     try {
/*  49 */       for (int i = 0; i < files.length; i++) {
/*  50 */         String s = files[i];
/*  51 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */         
/*  53 */         if (rp.resourceExists(resourcelocation)) {
/*  54 */           InputStream inputstream = rp.getInputStream(resourcelocation);
/*     */           
/*  56 */           if (inputstream != null) {
/*  57 */             loadLocaleData(inputstream, localeProperties);
/*     */           }
/*     */         } 
/*     */       } 
/*  61 */     } catch (IOException ioexception) {
/*  62 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void loadLocaleData(InputStream is, Map<String, String> localeProperties) throws IOException {
/*  67 */     Iterator<String> iterator = IOUtils.readLines(is, Charsets.UTF_8).iterator();
/*  68 */     is.close();
/*     */     
/*  70 */     while (iterator.hasNext()) {
/*  71 */       String s = iterator.next();
/*     */       
/*  73 */       if (!s.isEmpty() && s.charAt(0) != '#') {
/*  74 */         String[] astring = (String[])Iterables.toArray(splitter.split(s), String.class);
/*     */         
/*  76 */         if (astring != null && astring.length == 2) {
/*  77 */           String s1 = astring[0];
/*  78 */           String s2 = pattern.matcher(astring[1]).replaceAll("%$1s");
/*  79 */           localeProperties.put(s1, s2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String get(String key) {
/*  86 */     return I18n.format(key, new Object[0]);
/*     */   }
/*     */   
/*     */   public static String get(String key, String def) {
/*  90 */     String s = I18n.format(key, new Object[0]);
/*  91 */     return (s != null && !s.equals(key)) ? s : def;
/*     */   }
/*     */   
/*     */   public static String getOn() {
/*  95 */     return I18n.format("options.on", new Object[0]);
/*     */   }
/*     */   
/*     */   public static String getOff() {
/*  99 */     return I18n.format("options.off", new Object[0]);
/*     */   }
/*     */   
/*     */   public static String getFast() {
/* 103 */     return I18n.format("options.graphics.fast", new Object[0]);
/*     */   }
/*     */   
/*     */   public static String getFancy() {
/* 107 */     return I18n.format("options.graphics.fancy", new Object[0]);
/*     */   }
/*     */   
/*     */   public static String getDefault() {
/* 111 */     return I18n.format("generator.default", new Object[0]);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\Lang.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */