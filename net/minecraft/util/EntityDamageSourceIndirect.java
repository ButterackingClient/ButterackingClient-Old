/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class EntityDamageSourceIndirect extends EntityDamageSource {
/*    */   private Entity indirectEntity;
/*    */   
/*    */   public EntityDamageSourceIndirect(String damageTypeIn, Entity source, Entity indirectEntityIn) {
/* 11 */     super(damageTypeIn, source);
/* 12 */     this.indirectEntity = indirectEntityIn;
/*    */   }
/*    */   
/*    */   public Entity getSourceOfDamage() {
/* 16 */     return this.damageSourceEntity;
/*    */   }
/*    */   
/*    */   public Entity getEntity() {
/* 20 */     return this.indirectEntity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChatComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
/* 29 */     IChatComponent ichatcomponent = (this.indirectEntity == null) ? this.damageSourceEntity.getDisplayName() : this.indirectEntity.getDisplayName();
/* 30 */     ItemStack itemstack = (this.indirectEntity instanceof EntityLivingBase) ? ((EntityLivingBase)this.indirectEntity).getHeldItem() : null;
/* 31 */     String s = "death.attack." + this.damageType;
/* 32 */     String s1 = String.valueOf(s) + ".item";
/* 33 */     return (itemstack != null && itemstack.hasDisplayName() && StatCollector.canTranslate(s1)) ? new ChatComponentTranslation(s1, new Object[] { entityLivingBaseIn.getDisplayName(), ichatcomponent, itemstack.getChatComponent() }) : new ChatComponentTranslation(s, new Object[] { entityLivingBaseIn.getDisplayName(), ichatcomponent });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\EntityDamageSourceIndirect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */