/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAIHurtByTarget
/*    */   extends EntityAITarget
/*    */ {
/*    */   private boolean entityCallsForHelp;
/*    */   private int revengeTimerOld;
/*    */   private final Class[] targetClasses;
/*    */   
/*    */   public EntityAIHurtByTarget(EntityCreature creatureIn, boolean entityCallsForHelpIn, Class... targetClassesIn) {
/* 17 */     super(creatureIn, false);
/* 18 */     this.entityCallsForHelp = entityCallsForHelpIn;
/* 19 */     this.targetClasses = targetClassesIn;
/* 20 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 27 */     int i = this.taskOwner.getRevengeTimer();
/* 28 */     return (i != this.revengeTimerOld && isSuitableTarget(this.taskOwner.getAITarget(), false));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 35 */     this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
/* 36 */     this.revengeTimerOld = this.taskOwner.getRevengeTimer();
/*    */     
/* 38 */     if (this.entityCallsForHelp) {
/* 39 */       double d0 = getTargetDistance();
/*    */       
/* 41 */       for (EntityCreature entitycreature : this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), (new AxisAlignedBB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D)).expand(d0, 10.0D, d0))) {
/* 42 */         if (this.taskOwner != entitycreature && entitycreature.getAttackTarget() == null && !entitycreature.isOnSameTeam(this.taskOwner.getAITarget())) {
/* 43 */           boolean flag = false; byte b; int i;
/*    */           Class[] arrayOfClass;
/* 45 */           for (i = (arrayOfClass = this.targetClasses).length, b = 0; b < i; ) { Class<?> oclass = arrayOfClass[b];
/* 46 */             if (entitycreature.getClass() == oclass) {
/* 47 */               flag = true;
/*    */               break;
/*    */             } 
/*    */             b++; }
/*    */           
/* 52 */           if (!flag) {
/* 53 */             setEntityAttackTarget(entitycreature, this.taskOwner.getAITarget());
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 59 */     super.startExecuting();
/*    */   }
/*    */   
/*    */   protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
/* 63 */     creatureIn.setAttackTarget(entityLivingBaseIn);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIHurtByTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */