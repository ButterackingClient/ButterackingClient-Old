/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class PropertyInteger
/*    */   extends PropertyHelper<Integer> {
/*    */   private final ImmutableSet<Integer> allowedValues;
/*    */   
/*    */   protected PropertyInteger(String name, int min, int max) {
/* 13 */     super(name, Integer.class);
/*    */     
/* 15 */     if (min < 0)
/* 16 */       throw new IllegalArgumentException("Min value of " + name + " must be 0 or greater"); 
/* 17 */     if (max <= min) {
/* 18 */       throw new IllegalArgumentException("Max value of " + name + " must be greater than min (" + min + ")");
/*    */     }
/* 20 */     Set<Integer> set = Sets.newHashSet();
/*    */     
/* 22 */     for (int i = min; i <= max; i++) {
/* 23 */       set.add(Integer.valueOf(i));
/*    */     }
/*    */     
/* 26 */     this.allowedValues = ImmutableSet.copyOf(set);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<Integer> getAllowedValues() {
/* 31 */     return (Collection<Integer>)this.allowedValues;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 35 */     if (this == p_equals_1_)
/* 36 */       return true; 
/* 37 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/* 38 */       if (!super.equals(p_equals_1_)) {
/* 39 */         return false;
/*    */       }
/* 41 */       PropertyInteger propertyinteger = (PropertyInteger)p_equals_1_;
/* 42 */       return this.allowedValues.equals(propertyinteger.allowedValues);
/*    */     } 
/*    */     
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 50 */     int i = super.hashCode();
/* 51 */     i = 31 * i + this.allowedValues.hashCode();
/* 52 */     return i;
/*    */   }
/*    */   
/*    */   public static PropertyInteger create(String name, int min, int max) {
/* 56 */     return new PropertyInteger(name, min, max);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName(Integer value) {
/* 63 */     return value.toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\properties\PropertyInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */