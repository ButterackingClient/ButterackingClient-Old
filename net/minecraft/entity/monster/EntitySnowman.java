/*     */ package net.minecraft.entity.monster;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIArrowAttack;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntitySnowman extends EntityGolem implements IRangedAttackMob {
/*     */   public EntitySnowman(World worldIn) {
/*  26 */     super(worldIn);
/*  27 */     setSize(0.7F, 1.9F);
/*  28 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  29 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIArrowAttack(this, 1.25D, 20, 10.0F));
/*  30 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIWander(this, 1.0D));
/*  31 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  32 */     this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  33 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, true, false, IMob.mobSelector));
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  37 */     super.applyEntityAttributes();
/*  38 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
/*  39 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  47 */     super.onLivingUpdate();
/*     */     
/*  49 */     if (!this.worldObj.isRemote) {
/*  50 */       int i = MathHelper.floor_double(this.posX);
/*  51 */       int j = MathHelper.floor_double(this.posY);
/*  52 */       int k = MathHelper.floor_double(this.posZ);
/*     */       
/*  54 */       if (isWet()) {
/*  55 */         attackEntityFrom(DamageSource.drown, 1.0F);
/*     */       }
/*     */       
/*  58 */       if (this.worldObj.getBiomeGenForCoords(new BlockPos(i, 0, k)).getFloatTemperature(new BlockPos(i, j, k)) > 1.0F) {
/*  59 */         attackEntityFrom(DamageSource.onFire, 1.0F);
/*     */       }
/*     */       
/*  62 */       for (int l = 0; l < 4; l++) {
/*  63 */         i = MathHelper.floor_double(this.posX + ((l % 2 * 2 - 1) * 0.25F));
/*  64 */         j = MathHelper.floor_double(this.posY);
/*  65 */         k = MathHelper.floor_double(this.posZ + ((l / 2 % 2 * 2 - 1) * 0.25F));
/*  66 */         BlockPos blockpos = new BlockPos(i, j, k);
/*     */         
/*  68 */         if (this.worldObj.getBlockState(blockpos).getBlock().getMaterial() == Material.air && this.worldObj.getBiomeGenForCoords(new BlockPos(i, 0, k)).getFloatTemperature(blockpos) < 0.8F && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, blockpos)) {
/*  69 */           this.worldObj.setBlockState(blockpos, Blocks.snow_layer.getDefaultState());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/*  76 */     return Items.snowball;
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
/*  87 */     int i = this.rand.nextInt(16);
/*     */     
/*  89 */     for (int j = 0; j < i; j++) {
/*  90 */       dropItem(Items.snowball, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
/*  98 */     EntitySnowball entitysnowball = new EntitySnowball(this.worldObj, (EntityLivingBase)this);
/*  99 */     double d0 = target.posY + target.getEyeHeight() - 1.100000023841858D;
/* 100 */     double d1 = target.posX - this.posX;
/* 101 */     double d2 = d0 - entitysnowball.posY;
/* 102 */     double d3 = target.posZ - this.posZ;
/* 103 */     float f = MathHelper.sqrt_double(d1 * d1 + d3 * d3) * 0.2F;
/* 104 */     entitysnowball.setThrowableHeading(d1, d2 + f, d3, 1.6F, 12.0F);
/* 105 */     playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
/* 106 */     this.worldObj.spawnEntityInWorld((Entity)entitysnowball);
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 110 */     return 1.7F;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntitySnowman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */