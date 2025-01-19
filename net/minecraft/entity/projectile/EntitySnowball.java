/*    */ package net.minecraft.entity.projectile;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntitySnowball extends EntityThrowable {
/*    */   public EntitySnowball(World worldIn) {
/* 12 */     super(worldIn);
/*    */   }
/*    */   
/*    */   public EntitySnowball(World worldIn, EntityLivingBase throwerIn) {
/* 16 */     super(worldIn, throwerIn);
/*    */   }
/*    */   
/*    */   public EntitySnowball(World worldIn, double x, double y, double z) {
/* 20 */     super(worldIn, x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onImpact(MovingObjectPosition p_70184_1_) {
/* 27 */     if (p_70184_1_.entityHit != null) {
/* 28 */       int i = 0;
/*    */       
/* 30 */       if (p_70184_1_.entityHit instanceof net.minecraft.entity.monster.EntityBlaze) {
/* 31 */         i = 3;
/*    */       }
/*    */       
/* 34 */       p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, (Entity)getThrower()), i);
/*    */     } 
/*    */     
/* 37 */     for (int j = 0; j < 8; j++) {
/* 38 */       this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*    */     }
/*    */     
/* 41 */     if (!this.worldObj.isRemote)
/* 42 */       setDead(); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\projectile\EntitySnowball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */