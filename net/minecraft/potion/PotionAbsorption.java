/*    */ package net.minecraft.potion;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class PotionAbsorption extends Potion {
/*    */   protected PotionAbsorption(int potionID, ResourceLocation location, boolean badEffect, int potionColor) {
/*  9 */     super(potionID, location, badEffect, potionColor);
/*    */   }
/*    */   
/*    */   public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, BaseAttributeMap p_111187_2_, int amplifier) {
/* 13 */     entityLivingBaseIn.setAbsorptionAmount(entityLivingBaseIn.getAbsorptionAmount() - (4 * (amplifier + 1)));
/* 14 */     super.removeAttributesModifiersFromEntity(entityLivingBaseIn, p_111187_2_, amplifier);
/*    */   }
/*    */   
/*    */   public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, BaseAttributeMap p_111185_2_, int amplifier) {
/* 18 */     entityLivingBaseIn.setAbsorptionAmount(entityLivingBaseIn.getAbsorptionAmount() + (4 * (amplifier + 1)));
/* 19 */     super.applyAttributesModifiersToEntity(entityLivingBaseIn, p_111185_2_, amplifier);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\potion\PotionAbsorption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */