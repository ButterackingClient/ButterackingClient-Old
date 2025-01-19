/*     */ package net.minecraft.entity.passive;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityAnimal extends EntityAgeable implements IAnimals {
/*  17 */   protected Block spawnableBlock = (Block)Blocks.grass;
/*     */   private int inLove;
/*     */   private EntityPlayer playerInLove;
/*     */   
/*     */   public EntityAnimal(World worldIn) {
/*  22 */     super(worldIn);
/*     */   }
/*     */   
/*     */   protected void updateAITasks() {
/*  26 */     if (getGrowingAge() != 0) {
/*  27 */       this.inLove = 0;
/*     */     }
/*     */     
/*  30 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  38 */     super.onLivingUpdate();
/*     */     
/*  40 */     if (getGrowingAge() != 0) {
/*  41 */       this.inLove = 0;
/*     */     }
/*     */     
/*  44 */     if (this.inLove > 0) {
/*  45 */       this.inLove--;
/*     */       
/*  47 */       if (this.inLove % 10 == 0) {
/*  48 */         double d0 = this.rand.nextGaussian() * 0.02D;
/*  49 */         double d1 = this.rand.nextGaussian() * 0.02D;
/*  50 */         double d2 = this.rand.nextGaussian() * 0.02D;
/*  51 */         this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  60 */     if (isEntityInvulnerable(source)) {
/*  61 */       return false;
/*     */     }
/*  63 */     this.inLove = 0;
/*  64 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/*  69 */     return (this.worldObj.getBlockState(pos.down()).getBlock() == Blocks.grass) ? 10.0F : (this.worldObj.getLightBrightness(pos) - 0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  76 */     super.writeEntityToNBT(tagCompound);
/*  77 */     tagCompound.setInteger("InLove", this.inLove);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  84 */     super.readEntityFromNBT(tagCompund);
/*  85 */     this.inLove = tagCompund.getInteger("InLove");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/*  92 */     int i = MathHelper.floor_double(this.posX);
/*  93 */     int j = MathHelper.floor_double((getEntityBoundingBox()).minY);
/*  94 */     int k = MathHelper.floor_double(this.posZ);
/*  95 */     BlockPos blockpos = new BlockPos(i, j, k);
/*  96 */     return (this.worldObj.getBlockState(blockpos.down()).getBlock() == this.spawnableBlock && this.worldObj.getLight(blockpos) > 8 && super.getCanSpawnHere());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTalkInterval() {
/* 103 */     return 120;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getExperiencePoints(EntityPlayer player) {
/* 117 */     return 1 + this.worldObj.rand.nextInt(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 125 */     return (stack == null) ? false : ((stack.getItem() == Items.wheat));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(EntityPlayer player) {
/* 132 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*     */     
/* 134 */     if (itemstack != null) {
/* 135 */       if (isBreedingItem(itemstack) && getGrowingAge() == 0 && this.inLove <= 0) {
/* 136 */         consumeItemFromStack(player, itemstack);
/* 137 */         setInLove(player);
/* 138 */         return true;
/*     */       } 
/*     */       
/* 141 */       if (isChild() && isBreedingItem(itemstack)) {
/* 142 */         consumeItemFromStack(player, itemstack);
/* 143 */         func_175501_a((int)((-getGrowingAge() / 20) * 0.1F), true);
/* 144 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     return super.interact(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void consumeItemFromStack(EntityPlayer player, ItemStack stack) {
/* 155 */     if (!player.capabilities.isCreativeMode) {
/* 156 */       stack.stackSize--;
/*     */       
/* 158 */       if (stack.stackSize <= 0) {
/* 159 */         player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setInLove(EntityPlayer player) {
/* 165 */     this.inLove = 600;
/* 166 */     this.playerInLove = player;
/* 167 */     this.worldObj.setEntityState((Entity)this, (byte)18);
/*     */   }
/*     */   
/*     */   public EntityPlayer getPlayerInLove() {
/* 171 */     return this.playerInLove;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInLove() {
/* 178 */     return (this.inLove > 0);
/*     */   }
/*     */   
/*     */   public void resetInLove() {
/* 182 */     this.inLove = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 189 */     return (otherAnimal == this) ? false : ((otherAnimal.getClass() != getClass()) ? false : ((isInLove() && otherAnimal.isInLove())));
/*     */   }
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 193 */     if (id == 18) {
/* 194 */       for (int i = 0; i < 7; i++) {
/* 195 */         double d0 = this.rand.nextGaussian() * 0.02D;
/* 196 */         double d1 = this.rand.nextGaussian() * 0.02D;
/* 197 */         double d2 = this.rand.nextGaussian() * 0.02D;
/* 198 */         this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*     */       } 
/*     */     } else {
/* 201 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityAnimal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */