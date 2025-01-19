/*    */ package net.minecraft.potion;
/*    */ 
/*    */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class PotionAttackDamage extends Potion {
/*    */   protected PotionAttackDamage(int potionID, ResourceLocation location, boolean badEffect, int potionColor) {
/*  8 */     super(potionID, location, badEffect, potionColor);
/*    */   }
/*    */   
/*    */   public double getAttributeModifierAmount(int p_111183_1_, AttributeModifier modifier) {
/* 12 */     return (this.id == Potion.weakness.id) ? (-0.5F * (p_111183_1_ + 1)) : (1.3D * (p_111183_1_ + 1));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\potion\PotionAttackDamage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */