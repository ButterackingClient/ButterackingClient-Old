/*    */ package net.minecraft.entity.item;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityEndermite;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import net.minecraft.entity.projectile.EntityThrowable;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityEnderPearl
/*    */   extends EntityThrowable {
/*    */   private EntityLivingBase field_181555_c;
/*    */   
/*    */   public EntityEnderPearl(World worldIn) {
/* 18 */     super(worldIn);
/*    */   }
/*    */   
/*    */   public EntityEnderPearl(World worldIn, EntityLivingBase p_i1783_2_) {
/* 22 */     super(worldIn, p_i1783_2_);
/* 23 */     this.field_181555_c = p_i1783_2_;
/*    */   }
/*    */   
/*    */   public EntityEnderPearl(World worldIn, double x, double y, double z) {
/* 27 */     super(worldIn, x, y, z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onImpact(MovingObjectPosition p_70184_1_) {
/* 34 */     EntityLivingBase entitylivingbase = getThrower();
/*    */     
/* 36 */     if (p_70184_1_.entityHit != null) {
/* 37 */       if (p_70184_1_.entityHit == this.field_181555_c) {
/*    */         return;
/*    */       }
/*    */       
/* 41 */       p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)entitylivingbase), 0.0F);
/*    */     } 
/*    */     
/* 44 */     for (int i = 0; i < 32; i++) {
/* 45 */       this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian(), new int[0]);
/*    */     }
/*    */     
/* 48 */     if (!this.worldObj.isRemote) {
/* 49 */       if (entitylivingbase instanceof EntityPlayerMP) {
/* 50 */         EntityPlayerMP entityplayermp = (EntityPlayerMP)entitylivingbase;
/*    */         
/* 52 */         if (entityplayermp.playerNetServerHandler.getNetworkManager().isChannelOpen() && entityplayermp.worldObj == this.worldObj && !entityplayermp.isPlayerSleeping()) {
/* 53 */           if (this.rand.nextFloat() < 0.05F && this.worldObj.getGameRules().getBoolean("doMobSpawning")) {
/* 54 */             EntityEndermite entityendermite = new EntityEndermite(this.worldObj);
/* 55 */             entityendermite.setSpawnedByPlayer(true);
/* 56 */             entityendermite.setLocationAndAngles(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, entitylivingbase.rotationYaw, entitylivingbase.rotationPitch);
/* 57 */             this.worldObj.spawnEntityInWorld((Entity)entityendermite);
/*    */           } 
/*    */           
/* 60 */           if (entitylivingbase.isRiding()) {
/* 61 */             entitylivingbase.mountEntity(null);
/*    */           }
/*    */           
/* 64 */           entitylivingbase.setPositionAndUpdate(this.posX, this.posY, this.posZ);
/* 65 */           entitylivingbase.fallDistance = 0.0F;
/* 66 */           entitylivingbase.attackEntityFrom(DamageSource.fall, 5.0F);
/*    */         } 
/* 68 */       } else if (entitylivingbase != null) {
/* 69 */         entitylivingbase.setPositionAndUpdate(this.posX, this.posY, this.posZ);
/* 70 */         entitylivingbase.fallDistance = 0.0F;
/*    */       } 
/*    */       
/* 73 */       setDead();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 81 */     EntityLivingBase entitylivingbase = getThrower();
/*    */     
/* 83 */     if (entitylivingbase != null && entitylivingbase instanceof net.minecraft.entity.player.EntityPlayer && !entitylivingbase.isEntityAlive()) {
/* 84 */       setDead();
/*    */     } else {
/* 86 */       super.onUpdate();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityEnderPearl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */