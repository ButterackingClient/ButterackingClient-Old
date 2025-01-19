/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ 
/*    */ public abstract class PropertyHelper<T extends Comparable<T>> implements IProperty<T> {
/*    */   private final Class<T> valueClass;
/*    */   private final String name;
/*    */   
/*    */   protected PropertyHelper(String name, Class<T> valueClass) {
/* 10 */     this.valueClass = valueClass;
/* 11 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 15 */     return this.name;
/*    */   }
/*    */   
/*    */   public Class<T> getValueClass() {
/* 19 */     return this.valueClass;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 23 */     return Objects.toStringHelper(this).add("name", this.name).add("clazz", this.valueClass).add("values", getAllowedValues()).toString();
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 27 */     if (this == p_equals_1_)
/* 28 */       return true; 
/* 29 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/* 30 */       PropertyHelper propertyhelper = (PropertyHelper)p_equals_1_;
/* 31 */       return (this.valueClass.equals(propertyhelper.valueClass) && this.name.equals(propertyhelper.name));
/*    */     } 
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 38 */     return 31 * this.valueClass.hashCode() + this.name.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\properties\PropertyHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */