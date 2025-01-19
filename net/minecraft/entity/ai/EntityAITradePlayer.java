/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class EntityAITradePlayer extends EntityAIBase {
/*    */   private EntityVillager villager;
/*    */   
/*    */   public EntityAITradePlayer(EntityVillager villagerIn) {
/* 11 */     this.villager = villagerIn;
/* 12 */     setMutexBits(5);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 19 */     if (!this.villager.isEntityAlive())
/* 20 */       return false; 
/* 21 */     if (this.villager.isInWater())
/* 22 */       return false; 
/* 23 */     if (!this.villager.onGround)
/* 24 */       return false; 
/* 25 */     if (this.villager.velocityChanged) {
/* 26 */       return false;
/*    */     }
/* 28 */     EntityPlayer entityplayer = this.villager.getCustomer();
/* 29 */     return (entityplayer == null) ? false : ((this.villager.getDistanceSqToEntity((Entity)entityplayer) > 16.0D) ? false : (entityplayer.openContainer instanceof net.minecraft.inventory.Container));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 37 */     this.villager.getNavigator().clearPathEntity();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 44 */     this.villager.setCustomer(null);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAITradePlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */