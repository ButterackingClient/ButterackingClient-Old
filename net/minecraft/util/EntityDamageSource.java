/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityDamageSource
/*    */   extends DamageSource
/*    */ {
/*    */   protected Entity damageSourceEntity;
/*    */   private boolean isThornsDamage = false;
/*    */   
/*    */   public EntityDamageSource(String damageTypeIn, Entity damageSourceEntityIn) {
/* 17 */     super(damageTypeIn);
/* 18 */     this.damageSourceEntity = damageSourceEntityIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityDamageSource setIsThornsDamage() {
/* 25 */     this.isThornsDamage = true;
/* 26 */     return this;
/*    */   }
/*    */   
/*    */   public boolean getIsThornsDamage() {
/* 30 */     return this.isThornsDamage;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 34 */     return this.damageSourceEntity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChatComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
/* 43 */     ItemStack itemstack = (this.damageSourceEntity instanceof EntityLivingBase) ? ((EntityLivingBase)this.damageSourceEntity).getHeldItem() : null;
/* 44 */     String s = "death.attack." + this.damageType;
/* 45 */     String s1 = String.valueOf(s) + ".item";
/* 46 */     return (itemstack != null && itemstack.hasDisplayName() && StatCollector.canTranslate(s1)) ? new ChatComponentTranslation(s1, new Object[] { entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName(), itemstack.getChatComponent() }) : new ChatComponentTranslation(s, new Object[] { entityLivingBaseIn.getDisplayName(), this.damageSourceEntity.getDisplayName() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDifficultyScaled() {
/* 53 */     return (this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLivingBase && !(this.damageSourceEntity instanceof net.minecraft.entity.player.EntityPlayer));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\EntityDamageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */