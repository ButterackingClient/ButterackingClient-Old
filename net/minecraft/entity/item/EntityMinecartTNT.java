/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.block.BlockRailBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityMinecartTNT extends EntityMinecart {
/*  17 */   private int minecartTNTFuse = -1;
/*     */   
/*     */   public EntityMinecartTNT(World worldIn) {
/*  20 */     super(worldIn);
/*     */   }
/*     */   
/*     */   public EntityMinecartTNT(World worldIn, double x, double y, double z) {
/*  24 */     super(worldIn, x, y, z);
/*     */   }
/*     */   
/*     */   public EntityMinecart.EnumMinecartType getMinecartType() {
/*  28 */     return EntityMinecart.EnumMinecartType.TNT;
/*     */   }
/*     */   
/*     */   public IBlockState getDefaultDisplayTile() {
/*  32 */     return Blocks.tnt.getDefaultState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  39 */     super.onUpdate();
/*     */     
/*  41 */     if (this.minecartTNTFuse > 0) {
/*  42 */       this.minecartTNTFuse--;
/*  43 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*  44 */     } else if (this.minecartTNTFuse == 0) {
/*  45 */       explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */     } 
/*     */     
/*  48 */     if (this.isCollidedHorizontally) {
/*  49 */       double d0 = this.motionX * this.motionX + this.motionZ * this.motionZ;
/*     */       
/*  51 */       if (d0 >= 0.009999999776482582D) {
/*  52 */         explodeCart(d0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  61 */     Entity entity = source.getSourceOfDamage();
/*     */     
/*  63 */     if (entity instanceof EntityArrow) {
/*  64 */       EntityArrow entityarrow = (EntityArrow)entity;
/*     */       
/*  66 */       if (entityarrow.isBurning()) {
/*  67 */         explodeCart(entityarrow.motionX * entityarrow.motionX + entityarrow.motionY * entityarrow.motionY + entityarrow.motionZ * entityarrow.motionZ);
/*     */       }
/*     */     } 
/*     */     
/*  71 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */   
/*     */   public void killMinecart(DamageSource source) {
/*  75 */     super.killMinecart(source);
/*  76 */     double d0 = this.motionX * this.motionX + this.motionZ * this.motionZ;
/*     */     
/*  78 */     if (!source.isExplosion() && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/*  79 */       entityDropItem(new ItemStack(Blocks.tnt, 1), 0.0F);
/*     */     }
/*     */     
/*  82 */     if (source.isFireDamage() || source.isExplosion() || d0 >= 0.009999999776482582D) {
/*  83 */       explodeCart(d0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void explodeCart(double p_94103_1_) {
/*  91 */     if (!this.worldObj.isRemote) {
/*  92 */       double d0 = Math.sqrt(p_94103_1_);
/*     */       
/*  94 */       if (d0 > 5.0D) {
/*  95 */         d0 = 5.0D;
/*     */       }
/*     */       
/*  98 */       this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(4.0D + this.rand.nextDouble() * 1.5D * d0), true);
/*  99 */       setDead();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {
/* 104 */     if (distance >= 3.0F) {
/* 105 */       float f = distance / 10.0F;
/* 106 */       explodeCart((f * f));
/*     */     } 
/*     */     
/* 109 */     super.fall(distance, damageMultiplier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
/* 116 */     if (receivingPower && this.minecartTNTFuse < 0) {
/* 117 */       ignite();
/*     */     }
/*     */   }
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 122 */     if (id == 10) {
/* 123 */       ignite();
/*     */     } else {
/* 125 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignite() {
/* 133 */     this.minecartTNTFuse = 80;
/*     */     
/* 135 */     if (!this.worldObj.isRemote) {
/* 136 */       this.worldObj.setEntityState(this, (byte)10);
/*     */       
/* 138 */       if (!isSilent()) {
/* 139 */         this.worldObj.playSoundAtEntity(this, "game.tnt.primed", 1.0F, 1.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFuseTicks() {
/* 148 */     return this.minecartTNTFuse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIgnited() {
/* 155 */     return (this.minecartTNTFuse > -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
/* 162 */     return (!isIgnited() || (!BlockRailBase.isRailBlock(blockStateIn) && !BlockRailBase.isRailBlock(worldIn, pos.up()))) ? super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn) : 0.0F;
/*     */   }
/*     */   
/*     */   public boolean verifyExplosion(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn, float p_174816_5_) {
/* 166 */     return (!isIgnited() || (!BlockRailBase.isRailBlock(blockStateIn) && !BlockRailBase.isRailBlock(worldIn, pos.up()))) ? super.verifyExplosion(explosionIn, worldIn, pos, blockStateIn, p_174816_5_) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 173 */     super.readEntityFromNBT(tagCompund);
/*     */     
/* 175 */     if (tagCompund.hasKey("TNTFuse", 99)) {
/* 176 */       this.minecartTNTFuse = tagCompund.getInteger("TNTFuse");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 184 */     super.writeEntityToNBT(tagCompound);
/* 185 */     tagCompound.setInteger("TNTFuse", this.minecartTNTFuse);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityMinecartTNT.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */