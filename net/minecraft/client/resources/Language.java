/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ public class Language implements Comparable<Language> {
/*    */   private final String languageCode;
/*    */   private final String region;
/*    */   private final String name;
/*    */   private final boolean bidirectional;
/*    */   
/*    */   public Language(String languageCodeIn, String regionIn, String nameIn, boolean bidirectionalIn) {
/* 10 */     this.languageCode = languageCodeIn;
/* 11 */     this.region = regionIn;
/* 12 */     this.name = nameIn;
/* 13 */     this.bidirectional = bidirectionalIn;
/*    */   }
/*    */   
/*    */   public String getLanguageCode() {
/* 17 */     return this.languageCode;
/*    */   }
/*    */   
/*    */   public boolean isBidirectional() {
/* 21 */     return this.bidirectional;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 25 */     return String.format("%s (%s)", new Object[] { this.name, this.region });
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 29 */     return (this == p_equals_1_) ? true : (!(p_equals_1_ instanceof Language) ? false : this.languageCode.equals(((Language)p_equals_1_).languageCode));
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 33 */     return this.languageCode.hashCode();
/*    */   }
/*    */   
/*    */   public int compareTo(Language p_compareTo_1_) {
/* 37 */     return this.languageCode.compareTo(p_compareTo_1_.languageCode);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\Language.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */