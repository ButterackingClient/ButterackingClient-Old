/*    */ package net.minecraft.entity.ai.attributes;
/*    */ 
/*    */ public abstract class BaseAttribute implements IAttribute {
/*    */   private final IAttribute field_180373_a;
/*    */   private final String unlocalizedName;
/*    */   private final double defaultValue;
/*    */   private boolean shouldWatch;
/*    */   
/*    */   protected BaseAttribute(IAttribute p_i45892_1_, String unlocalizedNameIn, double defaultValueIn) {
/* 10 */     this.field_180373_a = p_i45892_1_;
/* 11 */     this.unlocalizedName = unlocalizedNameIn;
/* 12 */     this.defaultValue = defaultValueIn;
/*    */     
/* 14 */     if (unlocalizedNameIn == null) {
/* 15 */       throw new IllegalArgumentException("Name cannot be null!");
/*    */     }
/*    */   }
/*    */   
/*    */   public String getAttributeUnlocalizedName() {
/* 20 */     return this.unlocalizedName;
/*    */   }
/*    */   
/*    */   public double getDefaultValue() {
/* 24 */     return this.defaultValue;
/*    */   }
/*    */   
/*    */   public boolean getShouldWatch() {
/* 28 */     return this.shouldWatch;
/*    */   }
/*    */   
/*    */   public BaseAttribute setShouldWatch(boolean shouldWatchIn) {
/* 32 */     this.shouldWatch = shouldWatchIn;
/* 33 */     return this;
/*    */   }
/*    */   
/*    */   public IAttribute func_180372_d() {
/* 37 */     return this.field_180373_a;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 41 */     return this.unlocalizedName.hashCode();
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 45 */     return (p_equals_1_ instanceof IAttribute && this.unlocalizedName.equals(((IAttribute)p_equals_1_).getAttributeUnlocalizedName()));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\attributes\BaseAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */