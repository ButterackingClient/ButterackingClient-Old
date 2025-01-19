/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Collections2;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class PropertyDirection
/*    */   extends PropertyEnum<EnumFacing>
/*    */ {
/*    */   protected PropertyDirection(String name, Collection<EnumFacing> values) {
/* 14 */     super(name, EnumFacing.class, values);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PropertyDirection create(String name) {
/* 21 */     return create(name, Predicates.alwaysTrue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PropertyDirection create(String name, Predicate<EnumFacing> filter) {
/* 28 */     return create(name, Collections2.filter(Lists.newArrayList((Object[])EnumFacing.values()), filter));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PropertyDirection create(String name, Collection<EnumFacing> values) {
/* 35 */     return new PropertyDirection(name, values);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\properties\PropertyDirection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */