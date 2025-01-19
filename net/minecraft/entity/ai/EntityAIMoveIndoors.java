/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.village.Village;
/*    */ import net.minecraft.village.VillageDoorInfo;
/*    */ 
/*    */ public class EntityAIMoveIndoors extends EntityAIBase {
/*    */   private EntityCreature entityObj;
/* 12 */   private int insidePosX = -1; private VillageDoorInfo doorInfo;
/* 13 */   private int insidePosZ = -1;
/*    */   
/*    */   public EntityAIMoveIndoors(EntityCreature entityObjIn) {
/* 16 */     this.entityObj = entityObjIn;
/* 17 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 24 */     BlockPos blockpos = new BlockPos((Entity)this.entityObj);
/*    */     
/* 26 */     if ((!this.entityObj.worldObj.isDaytime() || (this.entityObj.worldObj.isRaining() && !this.entityObj.worldObj.getBiomeGenForCoords(blockpos).canRain())) && !this.entityObj.worldObj.provider.getHasNoSky()) {
/* 27 */       if (this.entityObj.getRNG().nextInt(50) != 0)
/* 28 */         return false; 
/* 29 */       if (this.insidePosX != -1 && this.entityObj.getDistanceSq(this.insidePosX, this.entityObj.posY, this.insidePosZ) < 4.0D) {
/* 30 */         return false;
/*    */       }
/* 32 */       Village village = this.entityObj.worldObj.getVillageCollection().getNearestVillage(blockpos, 14);
/*    */       
/* 34 */       if (village == null) {
/* 35 */         return false;
/*    */       }
/* 37 */       this.doorInfo = village.getDoorInfo(blockpos);
/* 38 */       return (this.doorInfo != null);
/*    */     } 
/*    */ 
/*    */     
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 50 */     return !this.entityObj.getNavigator().noPath();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 57 */     this.insidePosX = -1;
/* 58 */     BlockPos blockpos = this.doorInfo.getInsideBlockPos();
/* 59 */     int i = blockpos.getX();
/* 60 */     int j = blockpos.getY();
/* 61 */     int k = blockpos.getZ();
/*    */     
/* 63 */     if (this.entityObj.getDistanceSq(blockpos) > 256.0D) {
/* 64 */       Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.entityObj, 14, 3, new Vec3(i + 0.5D, j, k + 0.5D));
/*    */       
/* 66 */       if (vec3 != null) {
/* 67 */         this.entityObj.getNavigator().tryMoveToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord, 1.0D);
/*    */       }
/*    */     } else {
/* 70 */       this.entityObj.getNavigator().tryMoveToXYZ(i + 0.5D, j, k + 0.5D, 1.0D);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 78 */     this.insidePosX = this.doorInfo.getInsideBlockPos().getX();
/* 79 */     this.insidePosZ = this.doorInfo.getInsideBlockPos().getZ();
/* 80 */     this.doorInfo = null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIMoveIndoors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */