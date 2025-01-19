/*     */ package net.minecraft.entity.monster;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMagmaCube extends EntitySlime {
/*     */   public EntityMagmaCube(World worldIn) {
/*  12 */     super(worldIn);
/*  13 */     this.isImmuneToFire = true;
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  17 */     super.applyEntityAttributes();
/*  18 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/*  25 */     return (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/*  32 */     return (this.worldObj.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.worldObj.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(getEntityBoundingBox()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalArmorValue() {
/*  39 */     return getSlimeSize() * 3;
/*     */   }
/*     */   
/*     */   public int getBrightnessForRender(float partialTicks) {
/*  43 */     return 15728880;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness(float partialTicks) {
/*  50 */     return 1.0F;
/*     */   }
/*     */   
/*     */   protected EnumParticleTypes getParticleType() {
/*  54 */     return EnumParticleTypes.FLAME;
/*     */   }
/*     */   
/*     */   protected EntitySlime createInstance() {
/*  58 */     return new EntityMagmaCube(this.worldObj);
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/*  62 */     return Items.magma_cream;
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
/*  73 */     Item item = getDropItem();
/*     */     
/*  75 */     if (item != null && getSlimeSize() > 1) {
/*  76 */       int i = this.rand.nextInt(4) - 2;
/*     */       
/*  78 */       if (lootingModifier > 0) {
/*  79 */         i += this.rand.nextInt(lootingModifier + 1);
/*     */       }
/*     */       
/*  82 */       for (int j = 0; j < i; j++) {
/*  83 */         dropItem(item, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getJumpDelay() {
/*  99 */     return super.getJumpDelay() * 4;
/*     */   }
/*     */   
/*     */   protected void alterSquishAmount() {
/* 103 */     this.squishAmount *= 0.9F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jump() {
/* 110 */     this.motionY = (0.42F + getSlimeSize() * 0.1F);
/* 111 */     this.isAirBorne = true;
/*     */   }
/*     */   
/*     */   protected void handleJumpLava() {
/* 115 */     this.motionY = (0.22F + getSlimeSize() * 0.05F);
/* 116 */     this.isAirBorne = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDamagePlayer() {
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getAttackStrength() {
/* 133 */     return super.getAttackStrength() + 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getJumpSound() {
/* 140 */     return (getSlimeSize() > 1) ? "mob.magmacube.big" : "mob.magmacube.small";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean makesSoundOnLand() {
/* 147 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\monster\EntityMagmaCube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */