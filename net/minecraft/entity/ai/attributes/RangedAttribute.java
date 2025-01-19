/*    */ package net.minecraft.entity.ai.attributes;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class RangedAttribute extends BaseAttribute {
/*    */   private final double minimumValue;
/*    */   private final double maximumValue;
/*    */   private String description;
/*    */   
/*    */   public RangedAttribute(IAttribute p_i45891_1_, String unlocalizedNameIn, double defaultValue, double minimumValueIn, double maximumValueIn) {
/* 11 */     super(p_i45891_1_, unlocalizedNameIn, defaultValue);
/* 12 */     this.minimumValue = minimumValueIn;
/* 13 */     this.maximumValue = maximumValueIn;
/*    */     
/* 15 */     if (minimumValueIn > maximumValueIn)
/* 16 */       throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!"); 
/* 17 */     if (defaultValue < minimumValueIn)
/* 18 */       throw new IllegalArgumentException("Default value cannot be lower than minimum value!"); 
/* 19 */     if (defaultValue > maximumValueIn) {
/* 20 */       throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
/*    */     }
/*    */   }
/*    */   
/*    */   public RangedAttribute setDescription(String descriptionIn) {
/* 25 */     this.description = descriptionIn;
/* 26 */     return this;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 30 */     return this.description;
/*    */   }
/*    */   
/*    */   public double clampValue(double p_111109_1_) {
/* 34 */     p_111109_1_ = MathHelper.clamp_double(p_111109_1_, this.minimumValue, this.maximumValue);
/* 35 */     return p_111109_1_;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\attributes\RangedAttribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */