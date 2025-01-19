/*    */ package net.minecraft.entity.passive;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public abstract class EntityWaterMob extends EntityLiving implements IAnimals {
/*    */   public EntityWaterMob(World worldIn) {
/* 10 */     super(worldIn);
/*    */   }
/*    */   
/*    */   public boolean canBreatheUnderwater() {
/* 14 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getCanSpawnHere() {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isNotColliding() {
/* 28 */     return this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTalkInterval() {
/* 35 */     return 120;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canDespawn() {
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getExperiencePoints(EntityPlayer player) {
/* 49 */     return 1 + this.worldObj.rand.nextInt(3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityUpdate() {
/* 56 */     int i = getAir();
/* 57 */     super.onEntityUpdate();
/*    */     
/* 59 */     if (isEntityAlive() && !isInWater()) {
/* 60 */       i--;
/* 61 */       setAir(i);
/*    */       
/* 63 */       if (getAir() == -20) {
/* 64 */         setAir(0);
/* 65 */         attackEntityFrom(DamageSource.drown, 2.0F);
/*    */       } 
/*    */     } else {
/* 68 */       setAir(300);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isPushedByWater() {
/* 73 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityWaterMob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */