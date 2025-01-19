/*     */ package net.minecraft.entity.item;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class EntityItem extends Entity {
/*  21 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private int age;
/*     */ 
/*     */   
/*     */   private int delayBeforeCanPickup;
/*     */ 
/*     */   
/*     */   private int health;
/*     */ 
/*     */   
/*     */   private String thrower;
/*     */ 
/*     */   
/*     */   private String owner;
/*     */   
/*     */   public float hoverStart;
/*     */ 
/*     */   
/*     */   public EntityItem(World worldIn, double x, double y, double z) {
/*  42 */     super(worldIn);
/*  43 */     this.health = 5;
/*  44 */     this.hoverStart = (float)(Math.random() * Math.PI * 2.0D);
/*  45 */     setSize(0.25F, 0.25F);
/*  46 */     setPosition(x, y, z);
/*  47 */     this.rotationYaw = (float)(Math.random() * 360.0D);
/*  48 */     this.motionX = (float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D);
/*  49 */     this.motionY = 0.20000000298023224D;
/*  50 */     this.motionZ = (float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D);
/*     */   }
/*     */   
/*     */   public EntityItem(World worldIn, double x, double y, double z, ItemStack stack) {
/*  54 */     this(worldIn, x, y, z);
/*  55 */     setEntityItemStack(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  63 */     return false;
/*     */   }
/*     */   
/*     */   public EntityItem(World worldIn) {
/*  67 */     super(worldIn);
/*  68 */     this.health = 5;
/*  69 */     this.hoverStart = (float)(Math.random() * Math.PI * 2.0D);
/*  70 */     setSize(0.25F, 0.25F);
/*  71 */     setEntityItemStack(new ItemStack(Blocks.air, 0));
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  75 */     getDataWatcher().addObjectByDataType(10, 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  82 */     if (getEntityItem() == null) {
/*  83 */       setDead();
/*     */     } else {
/*  85 */       super.onUpdate();
/*     */       
/*  87 */       if (this.delayBeforeCanPickup > 0 && this.delayBeforeCanPickup != 32767) {
/*  88 */         this.delayBeforeCanPickup--;
/*     */       }
/*     */       
/*  91 */       this.prevPosX = this.posX;
/*  92 */       this.prevPosY = this.posY;
/*  93 */       this.prevPosZ = this.posZ;
/*  94 */       this.motionY -= 0.03999999910593033D;
/*  95 */       this.noClip = pushOutOfBlocks(this.posX, ((getEntityBoundingBox()).minY + (getEntityBoundingBox()).maxY) / 2.0D, this.posZ);
/*  96 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/*  97 */       boolean flag = !((int)this.prevPosX == (int)this.posX && (int)this.prevPosY == (int)this.posY && (int)this.prevPosZ == (int)this.posZ);
/*     */       
/*  99 */       if (flag || this.ticksExisted % 25 == 0) {
/* 100 */         if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() == Material.lava) {
/* 101 */           this.motionY = 0.20000000298023224D;
/* 102 */           this.motionX = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 103 */           this.motionZ = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/* 104 */           playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
/*     */         } 
/*     */         
/* 107 */         if (!this.worldObj.isRemote) {
/* 108 */           searchForOtherItemsNearby();
/*     */         }
/*     */       } 
/*     */       
/* 112 */       float f = 0.98F;
/*     */       
/* 114 */       if (this.onGround) {
/* 115 */         f = (this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double((getEntityBoundingBox()).minY) - 1, MathHelper.floor_double(this.posZ))).getBlock()).slipperiness * 0.98F;
/*     */       }
/*     */       
/* 118 */       this.motionX *= f;
/* 119 */       this.motionY *= 0.9800000190734863D;
/* 120 */       this.motionZ *= f;
/*     */       
/* 122 */       if (this.onGround) {
/* 123 */         this.motionY *= -0.5D;
/*     */       }
/*     */       
/* 126 */       if (this.age != -32768) {
/* 127 */         this.age++;
/*     */       }
/*     */       
/* 130 */       handleWaterMovement();
/*     */       
/* 132 */       if (!this.worldObj.isRemote && this.age >= 6000) {
/* 133 */         setDead();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void searchForOtherItemsNearby() {
/* 142 */     for (EntityItem entityitem : this.worldObj.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().expand(0.5D, 0.0D, 0.5D))) {
/* 143 */       combineItems(entityitem);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean combineItems(EntityItem other) {
/* 152 */     if (other == this)
/* 153 */       return false; 
/* 154 */     if (other.isEntityAlive() && isEntityAlive()) {
/* 155 */       ItemStack itemstack = getEntityItem();
/* 156 */       ItemStack itemstack1 = other.getEntityItem();
/*     */       
/* 158 */       if (this.delayBeforeCanPickup != 32767 && other.delayBeforeCanPickup != 32767) {
/* 159 */         if (this.age != -32768 && other.age != -32768) {
/* 160 */           if (itemstack1.getItem() != itemstack.getItem())
/* 161 */             return false; 
/* 162 */           if ((itemstack1.hasTagCompound() ^ itemstack.hasTagCompound()) != 0)
/* 163 */             return false; 
/* 164 */           if (itemstack1.hasTagCompound() && !itemstack1.getTagCompound().equals(itemstack.getTagCompound()))
/* 165 */             return false; 
/* 166 */           if (itemstack1.getItem() == null)
/* 167 */             return false; 
/* 168 */           if (itemstack1.getItem().getHasSubtypes() && itemstack1.getMetadata() != itemstack.getMetadata())
/* 169 */             return false; 
/* 170 */           if (itemstack1.stackSize < itemstack.stackSize)
/* 171 */             return other.combineItems(this); 
/* 172 */           if (itemstack1.stackSize + itemstack.stackSize > itemstack1.getMaxStackSize()) {
/* 173 */             return false;
/*     */           }
/* 175 */           itemstack1.stackSize += itemstack.stackSize;
/* 176 */           other.delayBeforeCanPickup = Math.max(other.delayBeforeCanPickup, this.delayBeforeCanPickup);
/* 177 */           other.age = Math.min(other.age, this.age);
/* 178 */           other.setEntityItemStack(itemstack1);
/* 179 */           setDead();
/* 180 */           return true;
/*     */         } 
/*     */         
/* 183 */         return false;
/*     */       } 
/*     */       
/* 186 */       return false;
/*     */     } 
/*     */     
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAgeToCreativeDespawnTime() {
/* 198 */     this.age = 4800;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleWaterMovement() {
/* 205 */     if (this.worldObj.handleMaterialAcceleration(getEntityBoundingBox(), Material.water, this)) {
/* 206 */       if (!this.inWater && !this.firstUpdate) {
/* 207 */         resetHeight();
/*     */       }
/*     */       
/* 210 */       this.inWater = true;
/*     */     } else {
/* 212 */       this.inWater = false;
/*     */     } 
/*     */     
/* 215 */     return this.inWater;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dealFireDamage(int amount) {
/* 223 */     attackEntityFrom(DamageSource.inFire, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 230 */     if (isEntityInvulnerable(source))
/* 231 */       return false; 
/* 232 */     if (getEntityItem() != null && getEntityItem().getItem() == Items.nether_star && source.isExplosion()) {
/* 233 */       return false;
/*     */     }
/* 235 */     setBeenAttacked();
/* 236 */     this.health = (int)(this.health - amount);
/*     */     
/* 238 */     if (this.health <= 0) {
/* 239 */       setDead();
/*     */     }
/*     */     
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 250 */     tagCompound.setShort("Health", (short)(byte)this.health);
/* 251 */     tagCompound.setShort("Age", (short)this.age);
/* 252 */     tagCompound.setShort("PickupDelay", (short)this.delayBeforeCanPickup);
/*     */     
/* 254 */     if (getThrower() != null) {
/* 255 */       tagCompound.setString("Thrower", this.thrower);
/*     */     }
/*     */     
/* 258 */     if (getOwner() != null) {
/* 259 */       tagCompound.setString("Owner", this.owner);
/*     */     }
/*     */     
/* 262 */     if (getEntityItem() != null) {
/* 263 */       tagCompound.setTag("Item", (NBTBase)getEntityItem().writeToNBT(new NBTTagCompound()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 271 */     this.health = tagCompund.getShort("Health") & 0xFF;
/* 272 */     this.age = tagCompund.getShort("Age");
/*     */     
/* 274 */     if (tagCompund.hasKey("PickupDelay")) {
/* 275 */       this.delayBeforeCanPickup = tagCompund.getShort("PickupDelay");
/*     */     }
/*     */     
/* 278 */     if (tagCompund.hasKey("Owner")) {
/* 279 */       this.owner = tagCompund.getString("Owner");
/*     */     }
/*     */     
/* 282 */     if (tagCompund.hasKey("Thrower")) {
/* 283 */       this.thrower = tagCompund.getString("Thrower");
/*     */     }
/*     */     
/* 286 */     NBTTagCompound nbttagcompound = tagCompund.getCompoundTag("Item");
/* 287 */     setEntityItemStack(ItemStack.loadItemStackFromNBT(nbttagcompound));
/*     */     
/* 289 */     if (getEntityItem() == null) {
/* 290 */       setDead();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer entityIn) {
/* 298 */     if (!this.worldObj.isRemote) {
/* 299 */       ItemStack itemstack = getEntityItem();
/* 300 */       int i = itemstack.stackSize;
/*     */       
/* 302 */       if (this.delayBeforeCanPickup == 0 && (this.owner == null || 6000 - this.age <= 200 || this.owner.equals(entityIn.getName())) && entityIn.inventory.addItemStackToInventory(itemstack)) {
/* 303 */         if (itemstack.getItem() == Item.getItemFromBlock(Blocks.log)) {
/* 304 */           entityIn.triggerAchievement((StatBase)AchievementList.mineWood);
/*     */         }
/*     */         
/* 307 */         if (itemstack.getItem() == Item.getItemFromBlock(Blocks.log2)) {
/* 308 */           entityIn.triggerAchievement((StatBase)AchievementList.mineWood);
/*     */         }
/*     */         
/* 311 */         if (itemstack.getItem() == Items.leather) {
/* 312 */           entityIn.triggerAchievement((StatBase)AchievementList.killCow);
/*     */         }
/*     */         
/* 315 */         if (itemstack.getItem() == Items.diamond) {
/* 316 */           entityIn.triggerAchievement((StatBase)AchievementList.diamonds);
/*     */         }
/*     */         
/* 319 */         if (itemstack.getItem() == Items.blaze_rod) {
/* 320 */           entityIn.triggerAchievement((StatBase)AchievementList.blazeRod);
/*     */         }
/*     */         
/* 323 */         if (itemstack.getItem() == Items.diamond && getThrower() != null) {
/* 324 */           EntityPlayer entityplayer = this.worldObj.getPlayerEntityByName(getThrower());
/*     */           
/* 326 */           if (entityplayer != null && entityplayer != entityIn) {
/* 327 */             entityplayer.triggerAchievement((StatBase)AchievementList.diamondsToYou);
/*     */           }
/*     */         } 
/*     */         
/* 331 */         if (!isSilent()) {
/* 332 */           this.worldObj.playSoundAtEntity((Entity)entityIn, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*     */         }
/*     */         
/* 335 */         entityIn.onItemPickup(this, i);
/*     */         
/* 337 */         if (itemstack.stackSize <= 0) {
/* 338 */           setDead();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 348 */     return hasCustomName() ? getCustomNameTag() : StatCollector.translateToLocal("item." + getEntityItem().getUnlocalizedName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttackWithItem() {
/* 355 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void travelToDimension(int dimensionId) {
/* 362 */     super.travelToDimension(dimensionId);
/*     */     
/* 364 */     if (!this.worldObj.isRemote) {
/* 365 */       searchForOtherItemsNearby();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getEntityItem() {
/* 374 */     ItemStack itemstack = getDataWatcher().getWatchableObjectItemStack(10);
/*     */     
/* 376 */     if (itemstack == null) {
/* 377 */       if (this.worldObj != null) {
/* 378 */         logger.error("Item entity " + getEntityId() + " has no item?!");
/*     */       }
/*     */       
/* 381 */       return new ItemStack(Blocks.stone);
/*     */     } 
/* 383 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEntityItemStack(ItemStack stack) {
/* 391 */     getDataWatcher().updateObject(10, stack);
/* 392 */     getDataWatcher().setObjectWatched(10);
/*     */   }
/*     */   
/*     */   public String getOwner() {
/* 396 */     return this.owner;
/*     */   }
/*     */   
/*     */   public void setOwner(String owner) {
/* 400 */     this.owner = owner;
/*     */   }
/*     */   
/*     */   public String getThrower() {
/* 404 */     return this.thrower;
/*     */   }
/*     */   
/*     */   public void setThrower(String thrower) {
/* 408 */     this.thrower = thrower;
/*     */   }
/*     */   
/*     */   public int getAge() {
/* 412 */     return this.age;
/*     */   }
/*     */   
/*     */   public void setDefaultPickupDelay() {
/* 416 */     this.delayBeforeCanPickup = 10;
/*     */   }
/*     */   
/*     */   public void setNoPickupDelay() {
/* 420 */     this.delayBeforeCanPickup = 0;
/*     */   }
/*     */   
/*     */   public void setInfinitePickupDelay() {
/* 424 */     this.delayBeforeCanPickup = 32767;
/*     */   }
/*     */   
/*     */   public void setPickupDelay(int ticks) {
/* 428 */     this.delayBeforeCanPickup = ticks;
/*     */   }
/*     */   
/*     */   public boolean cannotPickup() {
/* 432 */     return (this.delayBeforeCanPickup > 0);
/*     */   }
/*     */   
/*     */   public void setNoDespawn() {
/* 436 */     this.age = -6000;
/*     */   }
/*     */   
/*     */   public void func_174870_v() {
/* 440 */     setInfinitePickupDelay();
/* 441 */     this.age = 5999;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */