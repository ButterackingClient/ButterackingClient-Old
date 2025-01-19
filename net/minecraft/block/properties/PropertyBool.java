/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class PropertyBool
/*    */   extends PropertyHelper<Boolean> {
/*  8 */   private final ImmutableSet<Boolean> allowedValues = ImmutableSet.of(Boolean.valueOf(true), Boolean.valueOf(false));
/*    */   
/*    */   protected PropertyBool(String name) {
/* 11 */     super(name, Boolean.class);
/*    */   }
/*    */   
/*    */   public Collection<Boolean> getAllowedValues() {
/* 15 */     return (Collection<Boolean>)this.allowedValues;
/*    */   }
/*    */   
/*    */   public static PropertyBool create(String name) {
/* 19 */     return new PropertyBool(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName(Boolean value) {
/* 26 */     return value.toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\properties\PropertyBool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */