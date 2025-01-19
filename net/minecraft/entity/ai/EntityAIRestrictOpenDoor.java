/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.pathfinding.PathNavigateGround;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.village.Village;
/*    */ import net.minecraft.village.VillageDoorInfo;
/*    */ 
/*    */ public class EntityAIRestrictOpenDoor extends EntityAIBase {
/*    */   private EntityCreature entityObj;
/*    */   
/*    */   public EntityAIRestrictOpenDoor(EntityCreature creatureIn) {
/* 14 */     this.entityObj = creatureIn;
/*    */     
/* 16 */     if (!(creatureIn.getNavigator() instanceof PathNavigateGround)) {
/* 17 */       throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private VillageDoorInfo frontDoor;
/*    */   
/*    */   public boolean shouldExecute() {
/* 25 */     if (this.entityObj.worldObj.isDaytime()) {
/* 26 */       return false;
/*    */     }
/* 28 */     BlockPos blockpos = new BlockPos((Entity)this.entityObj);
/* 29 */     Village village = this.entityObj.worldObj.getVillageCollection().getNearestVillage(blockpos, 16);
/*    */     
/* 31 */     if (village == null) {
/* 32 */       return false;
/*    */     }
/* 34 */     this.frontDoor = village.getNearestDoor(blockpos);
/* 35 */     return (this.frontDoor == null) ? false : ((this.frontDoor.getDistanceToInsideBlockSq(blockpos) < 2.25D));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 44 */     return this.entityObj.worldObj.isDaytime() ? false : ((!this.frontDoor.getIsDetachedFromVillageFlag() && this.frontDoor.func_179850_c(new BlockPos((Entity)this.entityObj))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 51 */     ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(false);
/* 52 */     ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 59 */     ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(true);
/* 60 */     ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(true);
/* 61 */     this.frontDoor = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 68 */     this.frontDoor.incrementDoorOpeningRestrictionCounter();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIRestrictOpenDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */