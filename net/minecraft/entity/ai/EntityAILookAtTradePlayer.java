/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class EntityAILookAtTradePlayer extends EntityAIWatchClosest {
/*    */   public EntityAILookAtTradePlayer(EntityVillager theMerchantIn) {
/* 10 */     super((EntityLiving)theMerchantIn, (Class)EntityPlayer.class, 8.0F);
/* 11 */     this.theMerchant = theMerchantIn;
/*    */   }
/*    */ 
/*    */   
/*    */   private final EntityVillager theMerchant;
/*    */   
/*    */   public boolean shouldExecute() {
/* 18 */     if (this.theMerchant.isTrading()) {
/* 19 */       this.closestEntity = (Entity)this.theMerchant.getCustomer();
/* 20 */       return true;
/*    */     } 
/* 22 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAILookAtTradePlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */