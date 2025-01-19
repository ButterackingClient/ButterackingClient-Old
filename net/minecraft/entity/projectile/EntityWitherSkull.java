/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityWitherSkull extends EntityFireball {
/*     */   public EntityWitherSkull(World worldIn) {
/*  18 */     super(worldIn);
/*  19 */     setSize(0.3125F, 0.3125F);
/*     */   }
/*     */   
/*     */   public EntityWitherSkull(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
/*  23 */     super(worldIn, shooter, accelX, accelY, accelZ);
/*  24 */     setSize(0.3125F, 0.3125F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getMotionFactor() {
/*  31 */     return isInvulnerable() ? 0.73F : super.getMotionFactor();
/*     */   }
/*     */   
/*     */   public EntityWitherSkull(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
/*  35 */     super(worldIn, x, y, z, accelX, accelY, accelZ);
/*  36 */     setSize(0.3125F, 0.3125F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
/*  50 */     float f = super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
/*  51 */     Block block = blockStateIn.getBlock();
/*     */     
/*  53 */     if (isInvulnerable() && EntityWither.canDestroyBlock(block)) {
/*  54 */       f = Math.min(0.8F, f);
/*     */     }
/*     */     
/*  57 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onImpact(MovingObjectPosition movingObject) {
/*  64 */     if (!this.worldObj.isRemote) {
/*  65 */       if (movingObject.entityHit != null) {
/*  66 */         if (this.shootingEntity != null) {
/*  67 */           if (movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 8.0F)) {
/*  68 */             if (!movingObject.entityHit.isEntityAlive()) {
/*  69 */               this.shootingEntity.heal(5.0F);
/*     */             } else {
/*  71 */               applyEnchantments(this.shootingEntity, movingObject.entityHit);
/*     */             } 
/*     */           }
/*     */         } else {
/*  75 */           movingObject.entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
/*     */         } 
/*     */         
/*  78 */         if (movingObject.entityHit instanceof EntityLivingBase) {
/*  79 */           int i = 0;
/*     */           
/*  81 */           if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
/*  82 */             i = 10;
/*  83 */           } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
/*  84 */             i = 40;
/*     */           } 
/*     */           
/*  87 */           if (i > 0) {
/*  88 */             ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * i, 1));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  93 */       this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, false, this.worldObj.getGameRules().getBoolean("mobGriefing"));
/*  94 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 109 */     return false;
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/* 113 */     this.dataWatcher.addObject(10, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInvulnerable() {
/* 120 */     return (this.dataWatcher.getWatchableObjectByte(10) == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInvulnerable(boolean invulnerable) {
/* 127 */     this.dataWatcher.updateObject(10, Byte.valueOf((byte)(invulnerable ? 1 : 0)));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\projectile\EntityWitherSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */