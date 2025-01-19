/*    */ package net.minecraft.entity.projectile;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityLargeFireball extends EntityFireball {
/* 11 */   public int explosionPower = 1;
/*    */   
/*    */   public EntityLargeFireball(World worldIn) {
/* 14 */     super(worldIn);
/*    */   }
/*    */   
/*    */   public EntityLargeFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
/* 18 */     super(worldIn, x, y, z, accelX, accelY, accelZ);
/*    */   }
/*    */   
/*    */   public EntityLargeFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
/* 22 */     super(worldIn, shooter, accelX, accelY, accelZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onImpact(MovingObjectPosition movingObject) {
/* 29 */     if (!this.worldObj.isRemote) {
/* 30 */       if (movingObject.entityHit != null) {
/* 31 */         movingObject.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, (Entity)this.shootingEntity), 6.0F);
/* 32 */         applyEnchantments(this.shootingEntity, movingObject.entityHit);
/*    */       } 
/*    */       
/* 35 */       boolean flag = this.worldObj.getGameRules().getBoolean("mobGriefing");
/* 36 */       this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, this.explosionPower, flag, flag);
/* 37 */       setDead();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 45 */     super.writeEntityToNBT(tagCompound);
/* 46 */     tagCompound.setInteger("ExplosionPower", this.explosionPower);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 53 */     super.readEntityFromNBT(tagCompund);
/*    */     
/* 55 */     if (tagCompund.hasKey("ExplosionPower", 99))
/* 56 */       this.explosionPower = tagCompund.getInteger("ExplosionPower"); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\projectile\EntityLargeFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */