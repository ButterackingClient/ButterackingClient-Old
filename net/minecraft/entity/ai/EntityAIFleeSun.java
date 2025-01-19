/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityAIFleeSun
/*    */   extends EntityAIBase {
/*    */   private EntityCreature theCreature;
/*    */   private double shelterX;
/*    */   private double shelterY;
/*    */   private double shelterZ;
/*    */   private double movementSpeed;
/*    */   private World theWorld;
/*    */   
/*    */   public EntityAIFleeSun(EntityCreature theCreatureIn, double movementSpeedIn) {
/* 19 */     this.theCreature = theCreatureIn;
/* 20 */     this.movementSpeed = movementSpeedIn;
/* 21 */     this.theWorld = theCreatureIn.worldObj;
/* 22 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 29 */     if (!this.theWorld.isDaytime())
/* 30 */       return false; 
/* 31 */     if (!this.theCreature.isBurning())
/* 32 */       return false; 
/* 33 */     if (!this.theWorld.canSeeSky(new BlockPos(this.theCreature.posX, (this.theCreature.getEntityBoundingBox()).minY, this.theCreature.posZ))) {
/* 34 */       return false;
/*    */     }
/* 36 */     Vec3 vec3 = findPossibleShelter();
/*    */     
/* 38 */     if (vec3 == null) {
/* 39 */       return false;
/*    */     }
/* 41 */     this.shelterX = vec3.xCoord;
/* 42 */     this.shelterY = vec3.yCoord;
/* 43 */     this.shelterZ = vec3.zCoord;
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 53 */     return !this.theCreature.getNavigator().noPath();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 60 */     this.theCreature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
/*    */   }
/*    */   
/*    */   private Vec3 findPossibleShelter() {
/* 64 */     Random random = this.theCreature.getRNG();
/* 65 */     BlockPos blockpos = new BlockPos(this.theCreature.posX, (this.theCreature.getEntityBoundingBox()).minY, this.theCreature.posZ);
/*    */     
/* 67 */     for (int i = 0; i < 10; i++) {
/* 68 */       BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
/*    */       
/* 70 */       if (!this.theWorld.canSeeSky(blockpos1) && this.theCreature.getBlockPathWeight(blockpos1) < 0.0F) {
/* 71 */         return new Vec3(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
/*    */       }
/*    */     } 
/*    */     
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIFleeSun.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */