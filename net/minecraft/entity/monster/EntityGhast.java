/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityFlying;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityGhast
/*     */   extends EntityFlying implements IMob {
/*  29 */   private int explosionStrength = 1;
/*     */   
/*     */   public EntityGhast(World worldIn) {
/*  32 */     super(worldIn);
/*  33 */     setSize(4.0F, 4.0F);
/*  34 */     this.isImmuneToFire = true;
/*  35 */     this.experienceValue = 5;
/*  36 */     this.moveHelper = new GhastMoveHelper(this);
/*  37 */     this.tasks.addTask(5, new AIRandomFly(this));
/*  38 */     this.tasks.addTask(7, new AILookAround(this));
/*  39 */     this.tasks.addTask(7, new AIFireballAttack(this));
/*  40 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIFindEntityNearestPlayer((EntityLiving)this));
/*     */   }
/*     */   
/*     */   public boolean isAttacking() {
/*  44 */     return (this.dataWatcher.getWatchableObjectByte(16) != 0);
/*     */   }
/*     */   
/*     */   public void setAttacking(boolean attacking) {
/*  48 */     this.dataWatcher.updateObject(16, Byte.valueOf((byte)(attacking ? 1 : 0)));
/*     */   }
/*     */   
/*     */   public int getFireballStrength() {
/*  52 */     return this.explosionStrength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  59 */     super.onUpdate();
/*     */     
/*  61 */     if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
/*  62 */       setDead();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  70 */     if (isEntityInvulnerable(source))
/*  71 */       return false; 
/*  72 */     if ("fireball".equals(source.getDamageType()) && source.getEntity() instanceof EntityPlayer) {
/*  73 */       super.attackEntityFrom(source, 1000.0F);
/*  74 */       ((EntityPlayer)source.getEntity()).triggerAchievement((StatBase)AchievementList.ghast);
/*  75 */       return true;
/*     */     } 
/*  77 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  82 */     super.entityInit();
/*  83 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  87 */     super.applyEntityAttributes();
/*  88 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  89 */     getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  96 */     return "mob.ghast.moan";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/* 103 */     return "mob.ghast.scream";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/* 110 */     return "mob.ghast.death";
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/* 114 */     return Items.gunpowder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
/* 125 */     int i = this.rand.nextInt(2) + this.rand.nextInt(1 + lootingModifier);
/*     */     
/* 127 */     for (int j = 0; j < i; j++) {
/* 128 */       dropItem(Items.ghast_tear, 1);
/*     */     }
/*     */     
/* 131 */     i = this.rand.nextInt(3) + this.rand.nextInt(1 + lootingModifier);
/*     */     
/* 133 */     for (int k = 0; k < i; k++) {
/* 134 */       dropItem(Items.gunpowder, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 142 */     return 10.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 149 */     return (this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxSpawnedInChunk() {
/* 156 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 163 */     super.writeEntityToNBT(tagCompound);
/* 164 */     tagCompound.setInteger("ExplosionPower", this.explosionStrength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 171 */     super.readEntityFromNBT(tagCompund);
/*     */     
/* 173 */     if (tagCompund.hasKey("ExplosionPower", 99)) {
/* 174 */       this.explosionStrength = tagCompund.getInteger("ExplosionPower");
/*     */     }
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 179 */     return 2.6F;
/*     */   }
/*     */   
/*     */   static class AIFireballAttack extends EntityAIBase {
/*     */     private EntityGhast parentEntity;
/*     */     public int attackTimer;
/*     */     
/*     */     public AIFireballAttack(EntityGhast ghast) {
/* 187 */       this.parentEntity = ghast;
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 191 */       return (this.parentEntity.getAttackTarget() != null);
/*     */     }
/*     */     
/*     */     public void startExecuting() {
/* 195 */       this.attackTimer = 0;
/*     */     }
/*     */     
/*     */     public void resetTask() {
/* 199 */       this.parentEntity.setAttacking(false);
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 203 */       EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
/* 204 */       double d0 = 64.0D;
/*     */       
/* 206 */       if (entitylivingbase.getDistanceSqToEntity((Entity)this.parentEntity) < d0 * d0 && this.parentEntity.canEntityBeSeen((Entity)entitylivingbase)) {
/* 207 */         World world = this.parentEntity.worldObj;
/* 208 */         this.attackTimer++;
/*     */         
/* 210 */         if (this.attackTimer == 10) {
/* 211 */           world.playAuxSFXAtEntity(null, 1007, new BlockPos((Entity)this.parentEntity), 0);
/*     */         }
/*     */         
/* 214 */         if (this.attackTimer == 20) {
/* 215 */           double d1 = 4.0D;
/* 216 */           Vec3 vec3 = this.parentEntity.getLook(1.0F);
/* 217 */           double d2 = entitylivingbase.posX - this.parentEntity.posX + vec3.xCoord * d1;
/* 218 */           double d3 = (entitylivingbase.getEntityBoundingBox()).minY + (entitylivingbase.height / 2.0F) - 0.5D + this.parentEntity.posY + (this.parentEntity.height / 2.0F);
/* 219 */           double d4 = entitylivingbase.posZ - this.parentEntity.posZ + vec3.zCoord * d1;
/* 220 */           world.playAuxSFXAtEntity(null, 1008, new BlockPos((Entity)this.parentEntity), 0);
/* 221 */           EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, (EntityLivingBase)this.parentEntity, d2, d3, d4);
/* 222 */           entitylargefireball.explosionPower = this.parentEntity.getFireballStrength();
/* 223 */           entitylargefireball.posX = this.parentEntity.posX + vec3.xCoord * d1;
/* 224 */           entitylargefireball.posY = this.parentEntity.posY + (this.parentEntity.height / 2.0F) + 0.5D;
/* 225 */           entitylargefireball.posZ = this.parentEntity.posZ + vec3.zCoord * d1;
/* 226 */           world.spawnEntityInWorld((Entity)entitylargefireball);
/* 227 */           this.attackTimer = -40;
/*     */         } 
/* 229 */       } else if (this.attackTimer > 0) {
/* 230 */         this.attackTimer--;
/*     */       } 
/*     */       
/* 233 */       this.parentEntity.setAttacking((this.attackTimer > 10));
/*     */     }
/*     */   }
/*     */   
/*     */   static class AILookAround extends EntityAIBase {
/*     */     private EntityGhast parentEntity;
/*     */     
/*     */     public AILookAround(EntityGhast ghast) {
/* 241 */       this.parentEntity = ghast;
/* 242 */       setMutexBits(2);
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 246 */       return true;
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 250 */       if (this.parentEntity.getAttackTarget() == null) {
/* 251 */         this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = -((float)MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * 180.0F / 3.1415927F;
/*     */       } else {
/* 253 */         EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
/* 254 */         double d0 = 64.0D;
/*     */         
/* 256 */         if (entitylivingbase.getDistanceSqToEntity((Entity)this.parentEntity) < d0 * d0) {
/* 257 */           double d1 = entitylivingbase.posX - this.parentEntity.posX;
/* 258 */           double d2 = entitylivingbase.posZ - this.parentEntity.posZ;
/* 259 */           this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * 180.0F / 3.1415927F;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIRandomFly extends EntityAIBase {
/*     */     private EntityGhast parentEntity;
/*     */     
/*     */     public AIRandomFly(EntityGhast ghast) {
/* 269 */       this.parentEntity = ghast;
/* 270 */       setMutexBits(1);
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 274 */       EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();
/*     */       
/* 276 */       if (!entitymovehelper.isUpdating()) {
/* 277 */         return true;
/*     */       }
/* 279 */       double d0 = entitymovehelper.getX() - this.parentEntity.posX;
/* 280 */       double d1 = entitymovehelper.getY() - this.parentEntity.posY;
/* 281 */       double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
/* 282 */       double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 283 */       return !(d3 >= 1.0D && d3 <= 3600.0D);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 288 */       return false;
/*     */     }
/*     */     
/*     */     public void startExecuting() {
/* 292 */       Random random = this.parentEntity.getRNG();
/* 293 */       double d0 = this.parentEntity.posX + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/* 294 */       double d1 = this.parentEntity.posY + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/* 295 */       double d2 = this.parentEntity.posZ + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/* 296 */       this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class GhastMoveHelper extends EntityMoveHelper {
/*     */     private EntityGhast parentEntity;
/*     */     private int courseChangeCooldown;
/*     */     
/*     */     public GhastMoveHelper(EntityGhast ghast) {
/* 305 */       super((EntityLiving)ghast);
/* 306 */       this.parentEntity = ghast;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 311 */       double d0 = this.posX - this.parentEntity.posX;
/* 312 */       double d1 = this.posY - this.parentEntity.posY;
/* 313 */       double d2 = this.posZ - this.parentEntity.posZ;
/* 314 */       double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */       
/* 316 */       if (this.update && this.courseChangeCooldown-- <= 0) {
/* 317 */         this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
/* 318 */         d3 = MathHelper.sqrt_double(d3);
/*     */         
/* 320 */         if (isNotColliding(this.posX, this.posY, this.posZ, d3)) {
/* 321 */           this.parentEntity.motionX += d0 / d3 * 0.1D;
/* 322 */           this.parentEntity.motionY += d1 / d3 * 0.1D;
/* 323 */           this.parentEntity.motionZ += d2 / d3 * 0.1D;
/*     */         } else {
/* 325 */           this.update = false;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
/* 332 */       double d0 = (x - this.parentEntity.posX) / p_179926_7_;
/* 333 */       double d1 = (y - this.parentEntity.posY) / p_179926_7_;
/* 334 */       double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
/* 335 */       AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();
/*     */       
/* 337 */       for (int i = 1; i < p_179926_7_; i++) {
/* 338 */         axisalignedbb = axisalignedbb.offset(d0, d1, d2);
/*     */         
/* 340 */         if (!this.parentEntity.worldObj.getCollidingBoundingBoxes((Entity)this.parentEntity, axisalignedbb).isEmpty()) {
/* 341 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 345 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityGhast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */