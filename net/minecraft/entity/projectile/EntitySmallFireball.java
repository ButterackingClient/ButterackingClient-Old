/*    */ package net.minecraft.entity.projectile;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntitySmallFireball extends EntityFireball {
/*    */   public EntitySmallFireball(World worldIn) {
/* 13 */     super(worldIn);
/* 14 */     setSize(0.3125F, 0.3125F);
/*    */   }
/*    */   
/*    */   public EntitySmallFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
/* 18 */     super(worldIn, shooter, accelX, accelY, accelZ);
/* 19 */     setSize(0.3125F, 0.3125F);
/*    */   }
/*    */   
/*    */   public EntitySmallFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
/* 23 */     super(worldIn, x, y, z, accelX, accelY, accelZ);
/* 24 */     setSize(0.3125F, 0.3125F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onImpact(MovingObjectPosition movingObject) {
/* 31 */     if (!this.worldObj.isRemote) {
/* 32 */       if (movingObject.entityHit != null) {
/* 33 */         boolean flag = movingObject.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, (Entity)this.shootingEntity), 5.0F);
/*    */         
/* 35 */         if (flag) {
/* 36 */           applyEnchantments(this.shootingEntity, movingObject.entityHit);
/*    */           
/* 38 */           if (!movingObject.entityHit.isImmuneToFire()) {
/* 39 */             movingObject.entityHit.setFire(5);
/*    */           }
/*    */         } 
/*    */       } else {
/* 43 */         boolean flag1 = true;
/*    */         
/* 45 */         if (this.shootingEntity != null && this.shootingEntity instanceof net.minecraft.entity.EntityLiving) {
/* 46 */           flag1 = this.worldObj.getGameRules().getBoolean("mobGriefing");
/*    */         }
/*    */         
/* 49 */         if (flag1) {
/* 50 */           BlockPos blockpos = movingObject.getBlockPos().offset(movingObject.sideHit);
/*    */           
/* 52 */           if (this.worldObj.isAirBlock(blockpos)) {
/* 53 */             this.worldObj.setBlockState(blockpos, Blocks.fire.getDefaultState());
/*    */           }
/*    */         } 
/*    */       } 
/*    */       
/* 58 */       setDead();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canBeCollidedWith() {
/* 66 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 73 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\projectile\EntitySmallFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */