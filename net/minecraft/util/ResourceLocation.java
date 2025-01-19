/*    */ package net.minecraft.util;
/*    */ 
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class ResourceLocation {
/*    */   protected final String resourceDomain;
/*    */   
/*    */   protected ResourceLocation(int p_i45928_1_, String... resourceName) {
/* 10 */     this.resourceDomain = StringUtils.isEmpty(resourceName[0]) ? "minecraft" : resourceName[0].toLowerCase();
/* 11 */     this.resourcePath = resourceName[1];
/* 12 */     Validate.notNull(this.resourcePath);
/*    */   }
/*    */   protected final String resourcePath;
/*    */   public ResourceLocation(String resourceName) {
/* 16 */     this(0, splitObjectName(resourceName));
/*    */   }
/*    */   
/*    */   public ResourceLocation(String resourceDomainIn, String resourcePathIn) {
/* 20 */     this(0, new String[] { resourceDomainIn, resourcePathIn });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static String[] splitObjectName(String toSplit) {
/* 28 */     String[] astring = { null, toSplit };
/* 29 */     int i = toSplit.indexOf(':');
/*    */     
/* 31 */     if (i >= 0) {
/* 32 */       astring[1] = toSplit.substring(i + 1, toSplit.length());
/*    */       
/* 34 */       if (i > 1) {
/* 35 */         astring[0] = toSplit.substring(0, i);
/*    */       }
/*    */     } 
/*    */     
/* 39 */     return astring;
/*    */   }
/*    */   
/*    */   public String getResourcePath() {
/* 43 */     return this.resourcePath;
/*    */   }
/*    */   
/*    */   public String getResourceDomain() {
/* 47 */     return this.resourceDomain;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 51 */     return String.valueOf(this.resourceDomain) + ':' + this.resourcePath;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 55 */     if (this == p_equals_1_)
/* 56 */       return true; 
/* 57 */     if (!(p_equals_1_ instanceof ResourceLocation)) {
/* 58 */       return false;
/*    */     }
/* 60 */     ResourceLocation resourcelocation = (ResourceLocation)p_equals_1_;
/* 61 */     return (this.resourceDomain.equals(resourcelocation.resourceDomain) && this.resourcePath.equals(resourcelocation.resourcePath));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 66 */     return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ResourceLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */