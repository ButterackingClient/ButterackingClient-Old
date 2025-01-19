/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.passive.EntityHorse;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ public class EntityAIRunAroundLikeCrazy
/*    */   extends EntityAIBase
/*    */ {
/*    */   private EntityHorse horseHost;
/*    */   private double speed;
/*    */   
/*    */   public EntityAIRunAroundLikeCrazy(EntityHorse horse, double speedIn) {
/* 16 */     this.horseHost = horse;
/* 17 */     this.speed = speedIn;
/* 18 */     setMutexBits(1);
/*    */   }
/*    */   private double targetX;
/*    */   private double targetY;
/*    */   private double targetZ;
/*    */   
/*    */   public boolean shouldExecute() {
/* 25 */     if (!this.horseHost.isTame() && this.horseHost.riddenByEntity != null) {
/* 26 */       Vec3 vec3 = RandomPositionGenerator.findRandomTarget((EntityCreature)this.horseHost, 5, 4);
/*    */       
/* 28 */       if (vec3 == null) {
/* 29 */         return false;
/*    */       }
/* 31 */       this.targetX = vec3.xCoord;
/* 32 */       this.targetY = vec3.yCoord;
/* 33 */       this.targetZ = vec3.zCoord;
/* 34 */       return true;
/*    */     } 
/*    */     
/* 37 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 45 */     this.horseHost.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 52 */     return (!this.horseHost.getNavigator().noPath() && this.horseHost.riddenByEntity != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 59 */     if (this.horseHost.getRNG().nextInt(50) == 0) {
/* 60 */       if (this.horseHost.riddenByEntity instanceof EntityPlayer) {
/* 61 */         int i = this.horseHost.getTemper();
/* 62 */         int j = this.horseHost.getMaxTemper();
/*    */         
/* 64 */         if (j > 0 && this.horseHost.getRNG().nextInt(j) < i) {
/* 65 */           this.horseHost.setTamedBy((EntityPlayer)this.horseHost.riddenByEntity);
/* 66 */           this.horseHost.worldObj.setEntityState((Entity)this.horseHost, (byte)7);
/*    */           
/*    */           return;
/*    */         } 
/* 70 */         this.horseHost.increaseTemper(5);
/*    */       } 
/*    */       
/* 73 */       this.horseHost.riddenByEntity.mountEntity(null);
/* 74 */       this.horseHost.riddenByEntity = null;
/* 75 */       this.horseHost.makeHorseRearWithSound();
/* 76 */       this.horseHost.worldObj.setEntityState((Entity)this.horseHost, (byte)6);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIRunAroundLikeCrazy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */