/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ public class ModelResourceLocation extends ResourceLocation {
/*    */   private final String variant;
/*    */   
/*    */   protected ModelResourceLocation(int p_i46078_1_, String... p_i46078_2_) {
/* 10 */     super(0, new String[] { p_i46078_2_[0], p_i46078_2_[1] });
/* 11 */     this.variant = StringUtils.isEmpty(p_i46078_2_[2]) ? "normal" : p_i46078_2_[2].toLowerCase();
/*    */   }
/*    */   
/*    */   public ModelResourceLocation(String p_i46079_1_) {
/* 15 */     this(0, parsePathString(p_i46079_1_));
/*    */   }
/*    */   
/*    */   public ModelResourceLocation(ResourceLocation p_i46080_1_, String p_i46080_2_) {
/* 19 */     this(p_i46080_1_.toString(), p_i46080_2_);
/*    */   }
/*    */   
/*    */   public ModelResourceLocation(String p_i46081_1_, String p_i46081_2_) {
/* 23 */     this(0, parsePathString(String.valueOf(p_i46081_1_) + '#' + ((p_i46081_2_ == null) ? "normal" : p_i46081_2_)));
/*    */   }
/*    */   
/*    */   protected static String[] parsePathString(String p_177517_0_) {
/* 27 */     String[] astring = { null, p_177517_0_ };
/* 28 */     int i = p_177517_0_.indexOf('#');
/* 29 */     String s = p_177517_0_;
/*    */     
/* 31 */     if (i >= 0) {
/* 32 */       astring[2] = p_177517_0_.substring(i + 1, p_177517_0_.length());
/*    */       
/* 34 */       if (i > 1) {
/* 35 */         s = p_177517_0_.substring(0, i);
/*    */       }
/*    */     } 
/*    */     
/* 39 */     System.arraycopy(ResourceLocation.splitObjectName(s), 0, astring, 0, 2);
/* 40 */     return astring;
/*    */   }
/*    */   
/*    */   public String getVariant() {
/* 44 */     return this.variant;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 48 */     if (this == p_equals_1_)
/* 49 */       return true; 
/* 50 */     if (p_equals_1_ instanceof ModelResourceLocation && super.equals(p_equals_1_)) {
/* 51 */       ModelResourceLocation modelresourcelocation = (ModelResourceLocation)p_equals_1_;
/* 52 */       return this.variant.equals(modelresourcelocation.variant);
/*    */     } 
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 59 */     return 31 * super.hashCode() + this.variant.hashCode();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 63 */     return String.valueOf(super.toString()) + '#' + this.variant;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\model\ModelResourceLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */