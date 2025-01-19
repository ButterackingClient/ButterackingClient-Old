/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityMob extends EntityCreature implements IMob {
/*     */   public EntityMob(World worldIn) {
/*  17 */     super(worldIn);
/*  18 */     this.experienceValue = 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  26 */     updateArmSwingProgress();
/*  27 */     float f = getBrightness(1.0F);
/*     */     
/*  29 */     if (f > 0.5F) {
/*  30 */       this.entityAge += 2;
/*     */     }
/*     */     
/*  33 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  40 */     super.onUpdate();
/*     */     
/*  42 */     if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
/*  43 */       setDead();
/*     */     }
/*     */   }
/*     */   
/*     */   protected String getSwimSound() {
/*  48 */     return "game.hostile.swim";
/*     */   }
/*     */   
/*     */   protected String getSplashSound() {
/*  52 */     return "game.hostile.swim.splash";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  59 */     if (isEntityInvulnerable(source))
/*  60 */       return false; 
/*  61 */     if (super.attackEntityFrom(source, amount)) {
/*  62 */       Entity entity = source.getEntity();
/*  63 */       return (this.riddenByEntity != entity && this.ridingEntity != entity) ? true : true;
/*     */     } 
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  73 */     return "game.hostile.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  80 */     return "game.hostile.die";
/*     */   }
/*     */   
/*     */   protected String getFallSoundString(int damageValue) {
/*  84 */     return (damageValue > 4) ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
/*     */   }
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/*  88 */     float f = (float)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
/*  89 */     int i = 0;
/*     */     
/*  91 */     if (entityIn instanceof EntityLivingBase) {
/*  92 */       f += EnchantmentHelper.getModifierForCreature(getHeldItem(), ((EntityLivingBase)entityIn).getCreatureAttribute());
/*  93 */       i += EnchantmentHelper.getKnockbackModifier((EntityLivingBase)this);
/*     */     } 
/*     */     
/*  96 */     boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), f);
/*     */     
/*  98 */     if (flag) {
/*  99 */       if (i > 0) {
/* 100 */         entityIn.addVelocity((-MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F), 0.1D, (MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F) * i * 0.5F));
/* 101 */         this.motionX *= 0.6D;
/* 102 */         this.motionZ *= 0.6D;
/*     */       } 
/*     */       
/* 105 */       int j = EnchantmentHelper.getFireAspectModifier((EntityLivingBase)this);
/*     */       
/* 107 */       if (j > 0) {
/* 108 */         entityIn.setFire(j * 4);
/*     */       }
/*     */       
/* 111 */       applyEnchantments((EntityLivingBase)this, entityIn);
/*     */     } 
/*     */     
/* 114 */     return flag;
/*     */   }
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/* 118 */     return 0.5F - this.worldObj.getLightBrightness(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 125 */     BlockPos blockpos = new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ);
/*     */     
/* 127 */     if (this.worldObj.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32)) {
/* 128 */       return false;
/*     */     }
/* 130 */     int i = this.worldObj.getLightFromNeighbors(blockpos);
/*     */     
/* 132 */     if (this.worldObj.isThundering()) {
/* 133 */       int j = this.worldObj.getSkylightSubtracted();
/* 134 */       this.worldObj.setSkylightSubtracted(10);
/* 135 */       i = this.worldObj.getLightFromNeighbors(blockpos);
/* 136 */       this.worldObj.setSkylightSubtracted(j);
/*     */     } 
/*     */     
/* 139 */     return (i <= this.rand.nextInt(8));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 147 */     return (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL && isValidLightLevel() && super.getCanSpawnHere());
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 151 */     super.applyEntityAttributes();
/* 152 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDropLoot() {
/* 159 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityMob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */