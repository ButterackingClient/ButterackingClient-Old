/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.SortedSet;
/*    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*    */ import net.minecraft.client.resources.data.LanguageMetadataSection;
/*    */ import net.minecraft.util.StringTranslate;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class LanguageManager
/*    */   implements IResourceManagerReloadListener
/*    */ {
/* 19 */   private static final Logger logger = LogManager.getLogger();
/*    */   private final IMetadataSerializer theMetadataSerializer;
/*    */   private String currentLanguage;
/* 22 */   protected static final Locale currentLocale = new Locale();
/* 23 */   private Map<String, Language> languageMap = Maps.newHashMap();
/*    */   
/*    */   public LanguageManager(IMetadataSerializer theMetadataSerializerIn, String currentLanguageIn) {
/* 26 */     this.theMetadataSerializer = theMetadataSerializerIn;
/* 27 */     this.currentLanguage = currentLanguageIn;
/* 28 */     I18n.setLocale(currentLocale);
/*    */   }
/*    */   
/*    */   public void parseLanguageMetadata(List<IResourcePack> resourcesPacks) {
/* 32 */     this.languageMap.clear();
/*    */     
/* 34 */     for (IResourcePack iresourcepack : resourcesPacks) {
/*    */       try {
/* 36 */         LanguageMetadataSection languagemetadatasection = iresourcepack.<LanguageMetadataSection>getPackMetadata(this.theMetadataSerializer, "language");
/*    */         
/* 38 */         if (languagemetadatasection != null) {
/* 39 */           for (Language language : languagemetadatasection.getLanguages()) {
/* 40 */             if (!this.languageMap.containsKey(language.getLanguageCode())) {
/* 41 */               this.languageMap.put(language.getLanguageCode(), language);
/*    */             }
/*    */           } 
/*    */         }
/* 45 */       } catch (RuntimeException runtimeexception) {
/* 46 */         logger.warn("Unable to parse metadata section of resourcepack: " + iresourcepack.getPackName(), runtimeexception);
/* 47 */       } catch (IOException ioexception) {
/* 48 */         logger.warn("Unable to parse metadata section of resourcepack: " + iresourcepack.getPackName(), ioexception);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 54 */     List<String> list = Lists.newArrayList((Object[])new String[] { "en_US" });
/*    */     
/* 56 */     if (!"en_US".equals(this.currentLanguage)) {
/* 57 */       list.add(this.currentLanguage);
/*    */     }
/*    */     
/* 60 */     currentLocale.loadLocaleDataFiles(resourceManager, list);
/* 61 */     StringTranslate.replaceWith(currentLocale.properties);
/*    */   }
/*    */   
/*    */   public boolean isCurrentLocaleUnicode() {
/* 65 */     return currentLocale.isUnicode();
/*    */   }
/*    */   
/*    */   public boolean isCurrentLanguageBidirectional() {
/* 69 */     return (getCurrentLanguage() != null && getCurrentLanguage().isBidirectional());
/*    */   }
/*    */   
/*    */   public void setCurrentLanguage(Language currentLanguageIn) {
/* 73 */     this.currentLanguage = currentLanguageIn.getLanguageCode();
/*    */   }
/*    */   
/*    */   public Language getCurrentLanguage() {
/* 77 */     return this.languageMap.containsKey(this.currentLanguage) ? this.languageMap.get(this.currentLanguage) : this.languageMap.get("en_US");
/*    */   }
/*    */   
/*    */   public SortedSet<Language> getLanguages() {
/* 81 */     return Sets.newTreeSet(this.languageMap.values());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\LanguageManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */