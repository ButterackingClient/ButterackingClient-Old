/*    */ package net.minecraft.potion;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class PotionHealthBoost extends Potion {
/*    */   public PotionHealthBoost(int potionID, ResourceLocation location, boolean badEffect, int potionColor) {
/*  9 */     super(potionID, location, badEffect, potionColor);
/*    */   }
/*    */   
/*    */   public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, BaseAttributeMap p_111187_2_, int amplifier) {
/* 13 */     super.removeAttributesModifiersFromEntity(entityLivingBaseIn, p_111187_2_, amplifier);
/*    */     
/* 15 */     if (entityLivingBaseIn.getHealth() > entityLivingBaseIn.getMaxHealth())
/* 16 */       entityLivingBaseIn.setHealth(entityLivingBaseIn.getMaxHealth()); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\potion\PotionHealthBoost.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */