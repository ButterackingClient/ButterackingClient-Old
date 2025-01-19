/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.pathfinder.WalkNodeProcessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAIControlledByPlayer
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityLiving thisEntity;
/*     */   private final float maxSpeed;
/*     */   private float currentSpeed;
/*     */   private boolean speedBoosted;
/*     */   private int speedBoostTime;
/*     */   private int maxSpeedBoostTime;
/*     */   
/*     */   public EntityAIControlledByPlayer(EntityLiving entitylivingIn, float maxspeed) {
/*  37 */     this.thisEntity = entitylivingIn;
/*  38 */     this.maxSpeed = maxspeed;
/*  39 */     setMutexBits(7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  46 */     this.currentSpeed = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  53 */     this.speedBoosted = false;
/*  54 */     this.currentSpeed = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  61 */     return (this.thisEntity.isEntityAlive() && this.thisEntity.riddenByEntity != null && this.thisEntity.riddenByEntity instanceof EntityPlayer && (this.speedBoosted || this.thisEntity.canBeSteered()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  68 */     EntityPlayer entityplayer = (EntityPlayer)this.thisEntity.riddenByEntity;
/*  69 */     EntityCreature entitycreature = (EntityCreature)this.thisEntity;
/*  70 */     float f = MathHelper.wrapAngleTo180_float(entityplayer.rotationYaw - this.thisEntity.rotationYaw) * 0.5F;
/*     */     
/*  72 */     if (f > 5.0F) {
/*  73 */       f = 5.0F;
/*     */     }
/*     */     
/*  76 */     if (f < -5.0F) {
/*  77 */       f = -5.0F;
/*     */     }
/*     */     
/*  80 */     this.thisEntity.rotationYaw = MathHelper.wrapAngleTo180_float(this.thisEntity.rotationYaw + f);
/*     */     
/*  82 */     if (this.currentSpeed < this.maxSpeed) {
/*  83 */       this.currentSpeed += (this.maxSpeed - this.currentSpeed) * 0.01F;
/*     */     }
/*     */     
/*  86 */     if (this.currentSpeed > this.maxSpeed) {
/*  87 */       this.currentSpeed = this.maxSpeed;
/*     */     }
/*     */     
/*  90 */     int i = MathHelper.floor_double(this.thisEntity.posX);
/*  91 */     int j = MathHelper.floor_double(this.thisEntity.posY);
/*  92 */     int k = MathHelper.floor_double(this.thisEntity.posZ);
/*  93 */     float f1 = this.currentSpeed;
/*     */     
/*  95 */     if (this.speedBoosted) {
/*  96 */       if (this.speedBoostTime++ > this.maxSpeedBoostTime) {
/*  97 */         this.speedBoosted = false;
/*     */       }
/*     */       
/* 100 */       f1 += f1 * 1.15F * MathHelper.sin(this.speedBoostTime / this.maxSpeedBoostTime * 3.1415927F);
/*     */     } 
/*     */     
/* 103 */     float f2 = 0.91F;
/*     */     
/* 105 */     if (this.thisEntity.onGround) {
/* 106 */       f2 = (this.thisEntity.worldObj.getBlockState(new BlockPos(MathHelper.floor_float(i), MathHelper.floor_float(j) - 1, MathHelper.floor_float(k))).getBlock()).slipperiness * 0.91F;
/*     */     }
/*     */     
/* 109 */     float f3 = 0.16277136F / f2 * f2 * f2;
/* 110 */     float f4 = MathHelper.sin(entitycreature.rotationYaw * 3.1415927F / 180.0F);
/* 111 */     float f5 = MathHelper.cos(entitycreature.rotationYaw * 3.1415927F / 180.0F);
/* 112 */     float f6 = entitycreature.getAIMoveSpeed() * f3;
/* 113 */     float f7 = Math.max(f1, 1.0F);
/* 114 */     f7 = f6 / f7;
/* 115 */     float f8 = f1 * f7;
/* 116 */     float f9 = -(f8 * f4);
/* 117 */     float f10 = f8 * f5;
/*     */     
/* 119 */     if (MathHelper.abs(f9) > MathHelper.abs(f10)) {
/* 120 */       if (f9 < 0.0F) {
/* 121 */         f9 -= this.thisEntity.width / 2.0F;
/*     */       }
/*     */       
/* 124 */       if (f9 > 0.0F) {
/* 125 */         f9 += this.thisEntity.width / 2.0F;
/*     */       }
/*     */       
/* 128 */       f10 = 0.0F;
/*     */     } else {
/* 130 */       f9 = 0.0F;
/*     */       
/* 132 */       if (f10 < 0.0F) {
/* 133 */         f10 -= this.thisEntity.width / 2.0F;
/*     */       }
/*     */       
/* 136 */       if (f10 > 0.0F) {
/* 137 */         f10 += this.thisEntity.width / 2.0F;
/*     */       }
/*     */     } 
/*     */     
/* 141 */     int l = MathHelper.floor_double(this.thisEntity.posX + f9);
/* 142 */     int i1 = MathHelper.floor_double(this.thisEntity.posZ + f10);
/* 143 */     int j1 = MathHelper.floor_float(this.thisEntity.width + 1.0F);
/* 144 */     int k1 = MathHelper.floor_float(this.thisEntity.height + entityplayer.height + 1.0F);
/* 145 */     int l1 = MathHelper.floor_float(this.thisEntity.width + 1.0F);
/*     */     
/* 147 */     if (i != l || k != i1) {
/* 148 */       Block block = this.thisEntity.worldObj.getBlockState(new BlockPos(i, j, k)).getBlock();
/* 149 */       boolean flag = (!isStairOrSlab(block) && (block.getMaterial() != Material.air || !isStairOrSlab(this.thisEntity.worldObj.getBlockState(new BlockPos(i, j - 1, k)).getBlock())));
/*     */       
/* 151 */       if (flag && WalkNodeProcessor.func_176170_a((IBlockAccess)this.thisEntity.worldObj, (Entity)this.thisEntity, l, j, i1, j1, k1, l1, false, false, true) == 0 && 1 == WalkNodeProcessor.func_176170_a((IBlockAccess)this.thisEntity.worldObj, (Entity)this.thisEntity, i, j + 1, k, j1, k1, l1, false, false, true) && 1 == WalkNodeProcessor.func_176170_a((IBlockAccess)this.thisEntity.worldObj, (Entity)this.thisEntity, l, j + 1, i1, j1, k1, l1, false, false, true)) {
/* 152 */         entitycreature.getJumpHelper().setJumping();
/*     */       }
/*     */     } 
/*     */     
/* 156 */     if (!entityplayer.capabilities.isCreativeMode && this.currentSpeed >= this.maxSpeed * 0.5F && this.thisEntity.getRNG().nextFloat() < 0.006F && !this.speedBoosted) {
/* 157 */       ItemStack itemstack = entityplayer.getHeldItem();
/*     */       
/* 159 */       if (itemstack != null && itemstack.getItem() == Items.carrot_on_a_stick) {
/* 160 */         itemstack.damageItem(1, (EntityLivingBase)entityplayer);
/*     */         
/* 162 */         if (itemstack.stackSize == 0) {
/* 163 */           ItemStack itemstack1 = new ItemStack((Item)Items.fishing_rod);
/* 164 */           itemstack1.setTagCompound(itemstack.getTagCompound());
/* 165 */           entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = itemstack1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     this.thisEntity.moveEntityWithHeading(0.0F, f1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isStairOrSlab(Block blockIn) {
/* 177 */     return !(!(blockIn instanceof net.minecraft.block.BlockStairs) && !(blockIn instanceof net.minecraft.block.BlockSlab));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpeedBoosted() {
/* 184 */     return this.speedBoosted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void boostSpeed() {
/* 191 */     this.speedBoosted = true;
/* 192 */     this.speedBoostTime = 0;
/* 193 */     this.maxSpeedBoostTime = this.thisEntity.getRNG().nextInt(841) + 140;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isControlledByPlayer() {
/* 200 */     return (!isSpeedBoosted() && this.currentSpeed > this.maxSpeed * 0.3F);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIControlledByPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */