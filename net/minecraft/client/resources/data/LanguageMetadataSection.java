/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import net.minecraft.client.resources.Language;
/*    */ 
/*    */ public class LanguageMetadataSection
/*    */   implements IMetadataSection {
/*    */   private final Collection<Language> languages;
/*    */   
/*    */   public LanguageMetadataSection(Collection<Language> p_i1311_1_) {
/* 11 */     this.languages = p_i1311_1_;
/*    */   }
/*    */   
/*    */   public Collection<Language> getLanguages() {
/* 15 */     return this.languages;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\data\LanguageMetadataSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */