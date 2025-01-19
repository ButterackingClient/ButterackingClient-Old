/*     */ package net.minecraft.entity.passive;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityCow extends EntityAnimal {
/*     */   public EntityCow(World worldIn) {
/*  24 */     super(worldIn);
/*  25 */     setSize(0.9F, 1.3F);
/*  26 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*  27 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  28 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 2.0D));
/*  29 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  30 */     this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.25D, Items.wheat, false));
/*  31 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.25D));
/*  32 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  33 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  34 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  38 */     super.applyEntityAttributes();
/*  39 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*  40 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  47 */     return "mob.cow.say";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  54 */     return "mob.cow.hurt";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  61 */     return "mob.cow.hurt";
/*     */   }
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  65 */     playSound("mob.cow.step", 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/*  72 */     return 0.4F;
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/*  76 */     return Items.leather;
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
/*  87 */     int i = this.rand.nextInt(3) + this.rand.nextInt(1 + lootingModifier);
/*     */     
/*  89 */     for (int j = 0; j < i; j++) {
/*  90 */       dropItem(Items.leather, 1);
/*     */     }
/*     */     
/*  93 */     i = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + lootingModifier);
/*     */     
/*  95 */     for (int k = 0; k < i; k++) {
/*  96 */       if (isBurning()) {
/*  97 */         dropItem(Items.cooked_beef, 1);
/*     */       } else {
/*  99 */         dropItem(Items.beef, 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 108 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*     */     
/* 110 */     if (itemstack != null && itemstack.getItem() == Items.bucket && !player.capabilities.isCreativeMode && !isChild()) {
/* 111 */       if (itemstack.stackSize-- == 1) {
/* 112 */         player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.milk_bucket));
/* 113 */       } else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket))) {
/* 114 */         player.dropPlayerItemWithRandomChoice(new ItemStack(Items.milk_bucket, 1, 0), false);
/*     */       } 
/*     */       
/* 117 */       return true;
/*     */     } 
/* 119 */     return super.interact(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityCow createChild(EntityAgeable ageable) {
/* 124 */     return new EntityCow(this.worldObj);
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 128 */     return this.height;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityCow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */