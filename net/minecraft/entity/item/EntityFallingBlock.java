/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockAnvil;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityFallingBlock
/*     */   extends Entity
/*     */ {
/*     */   private IBlockState fallTile;
/*     */   public int fallTime;
/*     */   public boolean shouldDropItem = true;
/*     */   private boolean canSetAsBlock;
/*     */   private boolean hurtEntities;
/*  33 */   private int fallHurtMax = 40;
/*  34 */   private float fallHurtAmount = 2.0F;
/*     */   public NBTTagCompound tileEntityData;
/*     */   
/*     */   public EntityFallingBlock(World worldIn) {
/*  38 */     super(worldIn);
/*     */   }
/*     */   
/*     */   public EntityFallingBlock(World worldIn, double x, double y, double z, IBlockState fallingBlockState) {
/*  42 */     super(worldIn);
/*  43 */     this.fallTile = fallingBlockState;
/*  44 */     this.preventEntitySpawning = true;
/*  45 */     setSize(0.98F, 0.98F);
/*  46 */     setPosition(x, y, z);
/*  47 */     this.motionX = 0.0D;
/*  48 */     this.motionY = 0.0D;
/*  49 */     this.motionZ = 0.0D;
/*  50 */     this.prevPosX = x;
/*  51 */     this.prevPosY = y;
/*  52 */     this.prevPosZ = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/*  70 */     return !this.isDead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  77 */     Block block = this.fallTile.getBlock();
/*     */     
/*  79 */     if (block.getMaterial() == Material.air) {
/*  80 */       setDead();
/*     */     } else {
/*  82 */       this.prevPosX = this.posX;
/*  83 */       this.prevPosY = this.posY;
/*  84 */       this.prevPosZ = this.posZ;
/*     */       
/*  86 */       if (this.fallTime++ == 0) {
/*  87 */         BlockPos blockpos = new BlockPos(this);
/*     */         
/*  89 */         if (this.worldObj.getBlockState(blockpos).getBlock() == block) {
/*  90 */           this.worldObj.setBlockToAir(blockpos);
/*  91 */         } else if (!this.worldObj.isRemote) {
/*  92 */           setDead();
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*  97 */       this.motionY -= 0.03999999910593033D;
/*  98 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/*  99 */       this.motionX *= 0.9800000190734863D;
/* 100 */       this.motionY *= 0.9800000190734863D;
/* 101 */       this.motionZ *= 0.9800000190734863D;
/*     */       
/* 103 */       if (!this.worldObj.isRemote) {
/* 104 */         BlockPos blockpos1 = new BlockPos(this);
/*     */         
/* 106 */         if (this.onGround) {
/* 107 */           this.motionX *= 0.699999988079071D;
/* 108 */           this.motionZ *= 0.699999988079071D;
/* 109 */           this.motionY *= -0.5D;
/*     */           
/* 111 */           if (this.worldObj.getBlockState(blockpos1).getBlock() != Blocks.piston_extension) {
/* 112 */             setDead();
/*     */             
/* 114 */             if (!this.canSetAsBlock) {
/* 115 */               if (this.worldObj.canBlockBePlaced(block, blockpos1, true, EnumFacing.UP, null, null) && !BlockFalling.canFallInto(this.worldObj, blockpos1.down()) && this.worldObj.setBlockState(blockpos1, this.fallTile, 3)) {
/* 116 */                 if (block instanceof BlockFalling) {
/* 117 */                   ((BlockFalling)block).onEndFalling(this.worldObj, blockpos1);
/*     */                 }
/*     */                 
/* 120 */                 if (this.tileEntityData != null && block instanceof net.minecraft.block.ITileEntityProvider) {
/* 121 */                   TileEntity tileentity = this.worldObj.getTileEntity(blockpos1);
/*     */                   
/* 123 */                   if (tileentity != null) {
/* 124 */                     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 125 */                     tileentity.writeToNBT(nbttagcompound);
/*     */                     
/* 127 */                     for (String s : this.tileEntityData.getKeySet()) {
/* 128 */                       NBTBase nbtbase = this.tileEntityData.getTag(s);
/*     */                       
/* 130 */                       if (!s.equals("x") && !s.equals("y") && !s.equals("z")) {
/* 131 */                         nbttagcompound.setTag(s, nbtbase.copy());
/*     */                       }
/*     */                     } 
/*     */                     
/* 135 */                     tileentity.readFromNBT(nbttagcompound);
/* 136 */                     tileentity.markDirty();
/*     */                   } 
/*     */                 } 
/* 139 */               } else if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/* 140 */                 entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
/*     */               } 
/*     */             }
/*     */           } 
/* 144 */         } else if ((this.fallTime > 100 && !this.worldObj.isRemote && (blockpos1.getY() < 1 || blockpos1.getY() > 256)) || this.fallTime > 600) {
/* 145 */           if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
/* 146 */             entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
/*     */           }
/*     */           
/* 149 */           setDead();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {
/* 156 */     Block block = this.fallTile.getBlock();
/*     */     
/* 158 */     if (this.hurtEntities) {
/* 159 */       int i = MathHelper.ceiling_float_int(distance - 1.0F);
/*     */       
/* 161 */       if (i > 0) {
/* 162 */         List<Entity> list = Lists.newArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox()));
/* 163 */         boolean flag = (block == Blocks.anvil);
/* 164 */         DamageSource damagesource = flag ? DamageSource.anvil : DamageSource.fallingBlock;
/*     */         
/* 166 */         for (Entity entity : list) {
/* 167 */           entity.attackEntityFrom(damagesource, Math.min(MathHelper.floor_float(i * this.fallHurtAmount), this.fallHurtMax));
/*     */         }
/*     */         
/* 170 */         if (flag && this.rand.nextFloat() < 0.05000000074505806D + i * 0.05D) {
/* 171 */           int j = ((Integer)this.fallTile.getValue((IProperty)BlockAnvil.DAMAGE)).intValue();
/* 172 */           j++;
/*     */           
/* 174 */           if (j > 2) {
/* 175 */             this.canSetAsBlock = true;
/*     */           } else {
/* 177 */             this.fallTile = this.fallTile.withProperty((IProperty)BlockAnvil.DAMAGE, Integer.valueOf(j));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 188 */     Block block = (this.fallTile != null) ? this.fallTile.getBlock() : Blocks.air;
/* 189 */     ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(block);
/* 190 */     tagCompound.setString("Block", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 191 */     tagCompound.setByte("Data", (byte)block.getMetaFromState(this.fallTile));
/* 192 */     tagCompound.setByte("Time", (byte)this.fallTime);
/* 193 */     tagCompound.setBoolean("DropItem", this.shouldDropItem);
/* 194 */     tagCompound.setBoolean("HurtEntities", this.hurtEntities);
/* 195 */     tagCompound.setFloat("FallHurtAmount", this.fallHurtAmount);
/* 196 */     tagCompound.setInteger("FallHurtMax", this.fallHurtMax);
/*     */     
/* 198 */     if (this.tileEntityData != null) {
/* 199 */       tagCompound.setTag("TileEntityData", (NBTBase)this.tileEntityData);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 207 */     int i = tagCompund.getByte("Data") & 0xFF;
/*     */     
/* 209 */     if (tagCompund.hasKey("Block", 8)) {
/* 210 */       this.fallTile = Block.getBlockFromName(tagCompund.getString("Block")).getStateFromMeta(i);
/* 211 */     } else if (tagCompund.hasKey("TileID", 99)) {
/* 212 */       this.fallTile = Block.getBlockById(tagCompund.getInteger("TileID")).getStateFromMeta(i);
/*     */     } else {
/* 214 */       this.fallTile = Block.getBlockById(tagCompund.getByte("Tile") & 0xFF).getStateFromMeta(i);
/*     */     } 
/*     */     
/* 217 */     this.fallTime = tagCompund.getByte("Time") & 0xFF;
/* 218 */     Block block = this.fallTile.getBlock();
/*     */     
/* 220 */     if (tagCompund.hasKey("HurtEntities", 99)) {
/* 221 */       this.hurtEntities = tagCompund.getBoolean("HurtEntities");
/* 222 */       this.fallHurtAmount = tagCompund.getFloat("FallHurtAmount");
/* 223 */       this.fallHurtMax = tagCompund.getInteger("FallHurtMax");
/* 224 */     } else if (block == Blocks.anvil) {
/* 225 */       this.hurtEntities = true;
/*     */     } 
/*     */     
/* 228 */     if (tagCompund.hasKey("DropItem", 99)) {
/* 229 */       this.shouldDropItem = tagCompund.getBoolean("DropItem");
/*     */     }
/*     */     
/* 232 */     if (tagCompund.hasKey("TileEntityData", 10)) {
/* 233 */       this.tileEntityData = tagCompund.getCompoundTag("TileEntityData");
/*     */     }
/*     */     
/* 236 */     if (block == null || block.getMaterial() == Material.air) {
/* 237 */       this.fallTile = Blocks.sand.getDefaultState();
/*     */     }
/*     */   }
/*     */   
/*     */   public World getWorldObj() {
/* 242 */     return this.worldObj;
/*     */   }
/*     */   
/*     */   public void setHurtEntities(boolean p_145806_1_) {
/* 246 */     this.hurtEntities = p_145806_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRenderOnFire() {
/* 253 */     return false;
/*     */   }
/*     */   
/*     */   public void addEntityCrashInfo(CrashReportCategory category) {
/* 257 */     super.addEntityCrashInfo(category);
/*     */     
/* 259 */     if (this.fallTile != null) {
/* 260 */       Block block = this.fallTile.getBlock();
/* 261 */       category.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(block)));
/* 262 */       category.addCrashSection("Immitating block data", Integer.valueOf(block.getMetaFromState(this.fallTile)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public IBlockState getBlock() {
/* 267 */     return this.fallTile;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityFallingBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */