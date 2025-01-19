/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Rotations;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class EntityArmorStand
/*     */   extends EntityLivingBase
/*     */ {
/*  28 */   private static final Rotations DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
/*  29 */   private static final Rotations DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
/*  30 */   private static final Rotations DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0F, 0.0F, -10.0F);
/*  31 */   private static final Rotations DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0F, 0.0F, 10.0F);
/*  32 */   private static final Rotations DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0F, 0.0F, -1.0F);
/*  33 */   private static final Rotations DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);
/*     */   
/*     */   private final ItemStack[] contents;
/*     */   
/*     */   private boolean canInteract;
/*     */   
/*     */   private long punchCooldown;
/*     */   
/*     */   private int disabledSlots;
/*     */   private boolean field_181028_bj;
/*     */   private Rotations headRotation;
/*     */   private Rotations bodyRotation;
/*     */   private Rotations leftArmRotation;
/*     */   private Rotations rightArmRotation;
/*     */   private Rotations leftLegRotation;
/*     */   private Rotations rightLegRotation;
/*     */   
/*     */   public EntityArmorStand(World worldIn) {
/*  51 */     super(worldIn);
/*  52 */     this.contents = new ItemStack[5];
/*  53 */     this.headRotation = DEFAULT_HEAD_ROTATION;
/*  54 */     this.bodyRotation = DEFAULT_BODY_ROTATION;
/*  55 */     this.leftArmRotation = DEFAULT_LEFTARM_ROTATION;
/*  56 */     this.rightArmRotation = DEFAULT_RIGHTARM_ROTATION;
/*  57 */     this.leftLegRotation = DEFAULT_LEFTLEG_ROTATION;
/*  58 */     this.rightLegRotation = DEFAULT_RIGHTLEG_ROTATION;
/*  59 */     setSilent(true);
/*  60 */     this.noClip = hasNoGravity();
/*  61 */     setSize(0.5F, 1.975F);
/*     */   }
/*     */   
/*     */   public EntityArmorStand(World worldIn, double posX, double posY, double posZ) {
/*  65 */     this(worldIn);
/*  66 */     setPosition(posX, posY, posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isServerWorld() {
/*  73 */     return (super.isServerWorld() && !hasNoGravity());
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  77 */     super.entityInit();
/*  78 */     this.dataWatcher.addObject(10, Byte.valueOf((byte)0));
/*  79 */     this.dataWatcher.addObject(11, DEFAULT_HEAD_ROTATION);
/*  80 */     this.dataWatcher.addObject(12, DEFAULT_BODY_ROTATION);
/*  81 */     this.dataWatcher.addObject(13, DEFAULT_LEFTARM_ROTATION);
/*  82 */     this.dataWatcher.addObject(14, DEFAULT_RIGHTARM_ROTATION);
/*  83 */     this.dataWatcher.addObject(15, DEFAULT_LEFTLEG_ROTATION);
/*  84 */     this.dataWatcher.addObject(16, DEFAULT_RIGHTLEG_ROTATION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getHeldItem() {
/*  91 */     return this.contents[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getEquipmentInSlot(int slotIn) {
/*  98 */     return this.contents[slotIn];
/*     */   }
/*     */   
/*     */   public ItemStack getCurrentArmor(int slotIn) {
/* 102 */     return this.contents[slotIn + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
/* 109 */     this.contents[slotIn] = stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getInventory() {
/* 116 */     return this.contents;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/*     */     int i;
/* 122 */     if (inventorySlot == 99) {
/* 123 */       i = 0;
/*     */     } else {
/* 125 */       i = inventorySlot - 100 + 1;
/*     */       
/* 127 */       if (i < 0 || i >= this.contents.length) {
/* 128 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 132 */     if (itemStackIn != null && EntityLiving.getArmorPosition(itemStackIn) != i && (i != 4 || !(itemStackIn.getItem() instanceof net.minecraft.item.ItemBlock))) {
/* 133 */       return false;
/*     */     }
/* 135 */     setCurrentItemOrArmor(i, itemStackIn);
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/* 144 */     super.writeEntityToNBT(tagCompound);
/* 145 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 147 */     for (int i = 0; i < this.contents.length; i++) {
/* 148 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 150 */       if (this.contents[i] != null) {
/* 151 */         this.contents[i].writeToNBT(nbttagcompound);
/*     */       }
/*     */       
/* 154 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/* 157 */     tagCompound.setTag("Equipment", (NBTBase)nbttaglist);
/*     */     
/* 159 */     if (getAlwaysRenderNameTag() && (getCustomNameTag() == null || getCustomNameTag().length() == 0)) {
/* 160 */       tagCompound.setBoolean("CustomNameVisible", getAlwaysRenderNameTag());
/*     */     }
/*     */     
/* 163 */     tagCompound.setBoolean("Invisible", isInvisible());
/* 164 */     tagCompound.setBoolean("Small", isSmall());
/* 165 */     tagCompound.setBoolean("ShowArms", getShowArms());
/* 166 */     tagCompound.setInteger("DisabledSlots", this.disabledSlots);
/* 167 */     tagCompound.setBoolean("NoGravity", hasNoGravity());
/* 168 */     tagCompound.setBoolean("NoBasePlate", hasNoBasePlate());
/*     */     
/* 170 */     if (hasMarker()) {
/* 171 */       tagCompound.setBoolean("Marker", hasMarker());
/*     */     }
/*     */     
/* 174 */     tagCompound.setTag("Pose", (NBTBase)readPoseFromNBT());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/* 181 */     super.readEntityFromNBT(tagCompund);
/*     */     
/* 183 */     if (tagCompund.hasKey("Equipment", 9)) {
/* 184 */       NBTTagList nbttaglist = tagCompund.getTagList("Equipment", 10);
/*     */       
/* 186 */       for (int i = 0; i < this.contents.length; i++) {
/* 187 */         this.contents[i] = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
/*     */       }
/*     */     } 
/*     */     
/* 191 */     setInvisible(tagCompund.getBoolean("Invisible"));
/* 192 */     setSmall(tagCompund.getBoolean("Small"));
/* 193 */     setShowArms(tagCompund.getBoolean("ShowArms"));
/* 194 */     this.disabledSlots = tagCompund.getInteger("DisabledSlots");
/* 195 */     setNoGravity(tagCompund.getBoolean("NoGravity"));
/* 196 */     setNoBasePlate(tagCompund.getBoolean("NoBasePlate"));
/* 197 */     setMarker(tagCompund.getBoolean("Marker"));
/* 198 */     this.field_181028_bj = !hasMarker();
/* 199 */     this.noClip = hasNoGravity();
/* 200 */     NBTTagCompound nbttagcompound = tagCompund.getCompoundTag("Pose");
/* 201 */     writePoseToNBT(nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writePoseToNBT(NBTTagCompound tagCompound) {
/* 208 */     NBTTagList nbttaglist = tagCompound.getTagList("Head", 5);
/*     */     
/* 210 */     if (nbttaglist.tagCount() > 0) {
/* 211 */       setHeadRotation(new Rotations(nbttaglist));
/*     */     } else {
/* 213 */       setHeadRotation(DEFAULT_HEAD_ROTATION);
/*     */     } 
/*     */     
/* 216 */     NBTTagList nbttaglist1 = tagCompound.getTagList("Body", 5);
/*     */     
/* 218 */     if (nbttaglist1.tagCount() > 0) {
/* 219 */       setBodyRotation(new Rotations(nbttaglist1));
/*     */     } else {
/* 221 */       setBodyRotation(DEFAULT_BODY_ROTATION);
/*     */     } 
/*     */     
/* 224 */     NBTTagList nbttaglist2 = tagCompound.getTagList("LeftArm", 5);
/*     */     
/* 226 */     if (nbttaglist2.tagCount() > 0) {
/* 227 */       setLeftArmRotation(new Rotations(nbttaglist2));
/*     */     } else {
/* 229 */       setLeftArmRotation(DEFAULT_LEFTARM_ROTATION);
/*     */     } 
/*     */     
/* 232 */     NBTTagList nbttaglist3 = tagCompound.getTagList("RightArm", 5);
/*     */     
/* 234 */     if (nbttaglist3.tagCount() > 0) {
/* 235 */       setRightArmRotation(new Rotations(nbttaglist3));
/*     */     } else {
/* 237 */       setRightArmRotation(DEFAULT_RIGHTARM_ROTATION);
/*     */     } 
/*     */     
/* 240 */     NBTTagList nbttaglist4 = tagCompound.getTagList("LeftLeg", 5);
/*     */     
/* 242 */     if (nbttaglist4.tagCount() > 0) {
/* 243 */       setLeftLegRotation(new Rotations(nbttaglist4));
/*     */     } else {
/* 245 */       setLeftLegRotation(DEFAULT_LEFTLEG_ROTATION);
/*     */     } 
/*     */     
/* 248 */     NBTTagList nbttaglist5 = tagCompound.getTagList("RightLeg", 5);
/*     */     
/* 250 */     if (nbttaglist5.tagCount() > 0) {
/* 251 */       setRightLegRotation(new Rotations(nbttaglist5));
/*     */     } else {
/* 253 */       setRightLegRotation(DEFAULT_RIGHTLEG_ROTATION);
/*     */     } 
/*     */   }
/*     */   
/*     */   private NBTTagCompound readPoseFromNBT() {
/* 258 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 260 */     if (!DEFAULT_HEAD_ROTATION.equals(this.headRotation)) {
/* 261 */       nbttagcompound.setTag("Head", (NBTBase)this.headRotation.writeToNBT());
/*     */     }
/*     */     
/* 264 */     if (!DEFAULT_BODY_ROTATION.equals(this.bodyRotation)) {
/* 265 */       nbttagcompound.setTag("Body", (NBTBase)this.bodyRotation.writeToNBT());
/*     */     }
/*     */     
/* 268 */     if (!DEFAULT_LEFTARM_ROTATION.equals(this.leftArmRotation)) {
/* 269 */       nbttagcompound.setTag("LeftArm", (NBTBase)this.leftArmRotation.writeToNBT());
/*     */     }
/*     */     
/* 272 */     if (!DEFAULT_RIGHTARM_ROTATION.equals(this.rightArmRotation)) {
/* 273 */       nbttagcompound.setTag("RightArm", (NBTBase)this.rightArmRotation.writeToNBT());
/*     */     }
/*     */     
/* 276 */     if (!DEFAULT_LEFTLEG_ROTATION.equals(this.leftLegRotation)) {
/* 277 */       nbttagcompound.setTag("LeftLeg", (NBTBase)this.leftLegRotation.writeToNBT());
/*     */     }
/*     */     
/* 280 */     if (!DEFAULT_RIGHTLEG_ROTATION.equals(this.rightLegRotation)) {
/* 281 */       nbttagcompound.setTag("RightLeg", (NBTBase)this.rightLegRotation.writeToNBT());
/*     */     }
/*     */     
/* 284 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePushed() {
/* 291 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void collideWithEntity(Entity entityIn) {}
/*     */   
/*     */   protected void collideWithNearbyEntities() {
/* 298 */     List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox());
/*     */     
/* 300 */     if (list != null && !list.isEmpty()) {
/* 301 */       for (int i = 0; i < list.size(); i++) {
/* 302 */         Entity entity = list.get(i);
/*     */         
/* 304 */         if (entity instanceof EntityMinecart && ((EntityMinecart)entity).getMinecartType() == EntityMinecart.EnumMinecartType.RIDEABLE && getDistanceSqToEntity(entity) <= 0.2D) {
/* 305 */           entity.applyEntityCollision((Entity)this);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interactAt(EntityPlayer player, Vec3 targetVec3) {
/* 315 */     if (hasMarker())
/* 316 */       return false; 
/* 317 */     if (!this.worldObj.isRemote && !player.isSpectator()) {
/* 318 */       int i = 0;
/* 319 */       ItemStack itemstack = player.getCurrentEquippedItem();
/* 320 */       boolean flag = (itemstack != null);
/*     */       
/* 322 */       if (flag && itemstack.getItem() instanceof ItemArmor) {
/* 323 */         ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/*     */         
/* 325 */         if (itemarmor.armorType == 3) {
/* 326 */           i = 1;
/* 327 */         } else if (itemarmor.armorType == 2) {
/* 328 */           i = 2;
/* 329 */         } else if (itemarmor.armorType == 1) {
/* 330 */           i = 3;
/* 331 */         } else if (itemarmor.armorType == 0) {
/* 332 */           i = 4;
/*     */         } 
/*     */       } 
/*     */       
/* 336 */       if (flag && (itemstack.getItem() == Items.skull || itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin))) {
/* 337 */         i = 4;
/*     */       }
/*     */       
/* 340 */       double d4 = 0.1D;
/* 341 */       double d0 = 0.9D;
/* 342 */       double d1 = 0.4D;
/* 343 */       double d2 = 1.6D;
/* 344 */       int j = 0;
/* 345 */       boolean flag1 = isSmall();
/* 346 */       double d3 = flag1 ? (targetVec3.yCoord * 2.0D) : targetVec3.yCoord;
/*     */       
/* 348 */       if (d3 >= 0.1D && d3 < 0.1D + (flag1 ? 0.8D : 0.45D) && this.contents[1] != null) {
/* 349 */         j = 1;
/* 350 */       } else if (d3 >= 0.9D + (flag1 ? 0.3D : 0.0D) && d3 < 0.9D + (flag1 ? 1.0D : 0.7D) && this.contents[3] != null) {
/* 351 */         j = 3;
/* 352 */       } else if (d3 >= 0.4D && d3 < 0.4D + (flag1 ? 1.0D : 0.8D) && this.contents[2] != null) {
/* 353 */         j = 2;
/* 354 */       } else if (d3 >= 1.6D && this.contents[4] != null) {
/* 355 */         j = 4;
/*     */       } 
/*     */       
/* 358 */       boolean flag2 = (this.contents[j] != null);
/*     */       
/* 360 */       if ((this.disabledSlots & 1 << j) != 0 || (this.disabledSlots & 1 << i) != 0) {
/* 361 */         j = i;
/*     */         
/* 363 */         if ((this.disabledSlots & 1 << i) != 0) {
/* 364 */           if ((this.disabledSlots & 0x1) != 0) {
/* 365 */             return true;
/*     */           }
/*     */           
/* 368 */           j = 0;
/*     */         } 
/*     */       } 
/*     */       
/* 372 */       if (flag && i == 0 && !getShowArms()) {
/* 373 */         return true;
/*     */       }
/* 375 */       if (flag) {
/* 376 */         func_175422_a(player, i);
/* 377 */       } else if (flag2) {
/* 378 */         func_175422_a(player, j);
/*     */       } 
/*     */       
/* 381 */       return true;
/*     */     } 
/*     */     
/* 384 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_175422_a(EntityPlayer p_175422_1_, int p_175422_2_) {
/* 389 */     ItemStack itemstack = this.contents[p_175422_2_];
/*     */     
/* 391 */     if ((itemstack == null || (this.disabledSlots & 1 << p_175422_2_ + 8) == 0) && (
/* 392 */       itemstack != null || (this.disabledSlots & 1 << p_175422_2_ + 16) == 0)) {
/* 393 */       int i = p_175422_1_.inventory.currentItem;
/* 394 */       ItemStack itemstack1 = p_175422_1_.inventory.getStackInSlot(i);
/*     */       
/* 396 */       if (p_175422_1_.capabilities.isCreativeMode && (itemstack == null || itemstack.getItem() == Item.getItemFromBlock(Blocks.air)) && itemstack1 != null) {
/* 397 */         ItemStack itemstack3 = itemstack1.copy();
/* 398 */         itemstack3.stackSize = 1;
/* 399 */         setCurrentItemOrArmor(p_175422_2_, itemstack3);
/* 400 */       } else if (itemstack1 != null && itemstack1.stackSize > 1) {
/* 401 */         if (itemstack == null) {
/* 402 */           ItemStack itemstack2 = itemstack1.copy();
/* 403 */           itemstack2.stackSize = 1;
/* 404 */           setCurrentItemOrArmor(p_175422_2_, itemstack2);
/* 405 */           itemstack1.stackSize--;
/*     */         } 
/*     */       } else {
/* 408 */         setCurrentItemOrArmor(p_175422_2_, itemstack1);
/* 409 */         p_175422_1_.inventory.setInventorySlotContents(i, itemstack);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 419 */     if (this.worldObj.isRemote)
/* 420 */       return false; 
/* 421 */     if (DamageSource.outOfWorld.equals(source)) {
/* 422 */       setDead();
/* 423 */       return false;
/* 424 */     }  if (!isEntityInvulnerable(source) && !this.canInteract && !hasMarker()) {
/* 425 */       if (source.isExplosion()) {
/* 426 */         dropContents();
/* 427 */         setDead();
/* 428 */         return false;
/* 429 */       }  if (DamageSource.inFire.equals(source)) {
/* 430 */         if (!isBurning()) {
/* 431 */           setFire(5);
/*     */         } else {
/* 433 */           damageArmorStand(0.15F);
/*     */         } 
/*     */         
/* 436 */         return false;
/* 437 */       }  if (DamageSource.onFire.equals(source) && getHealth() > 0.5F) {
/* 438 */         damageArmorStand(4.0F);
/* 439 */         return false;
/*     */       } 
/* 441 */       boolean flag = "arrow".equals(source.getDamageType());
/* 442 */       boolean flag1 = "player".equals(source.getDamageType());
/*     */       
/* 444 */       if (!flag1 && !flag) {
/* 445 */         return false;
/*     */       }
/* 447 */       if (source.getSourceOfDamage() instanceof net.minecraft.entity.projectile.EntityArrow) {
/* 448 */         source.getSourceOfDamage().setDead();
/*     */       }
/*     */       
/* 451 */       if (source.getEntity() instanceof EntityPlayer && !((EntityPlayer)source.getEntity()).capabilities.allowEdit)
/* 452 */         return false; 
/* 453 */       if (source.isCreativePlayer()) {
/* 454 */         playParticles();
/* 455 */         setDead();
/* 456 */         return false;
/*     */       } 
/* 458 */       long i = this.worldObj.getTotalWorldTime();
/*     */       
/* 460 */       if (i - this.punchCooldown > 5L && !flag) {
/* 461 */         this.punchCooldown = i;
/*     */       } else {
/* 463 */         dropBlock();
/* 464 */         playParticles();
/* 465 */         setDead();
/*     */       } 
/*     */       
/* 468 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 473 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/* 482 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/* 484 */     if (Double.isNaN(d0) || d0 == 0.0D) {
/* 485 */       d0 = 4.0D;
/*     */     }
/*     */     
/* 488 */     d0 *= 64.0D;
/* 489 */     return (distance < d0 * d0);
/*     */   }
/*     */   
/*     */   private void playParticles() {
/* 493 */     if (this.worldObj instanceof WorldServer) {
/* 494 */       ((WorldServer)this.worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY + this.height / 1.5D, this.posZ, 10, (this.width / 4.0F), (this.height / 4.0F), (this.width / 4.0F), 0.05D, new int[] { Block.getStateId(Blocks.planks.getDefaultState()) });
/*     */     }
/*     */   }
/*     */   
/*     */   private void damageArmorStand(float p_175406_1_) {
/* 499 */     float f = getHealth();
/* 500 */     f -= p_175406_1_;
/*     */     
/* 502 */     if (f <= 0.5F) {
/* 503 */       dropContents();
/* 504 */       setDead();
/*     */     } else {
/* 506 */       setHealth(f);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void dropBlock() {
/* 511 */     Block.spawnAsEntity(this.worldObj, new BlockPos((Entity)this), new ItemStack((Item)Items.armor_stand));
/* 512 */     dropContents();
/*     */   }
/*     */   
/*     */   private void dropContents() {
/* 516 */     for (int i = 0; i < this.contents.length; i++) {
/* 517 */       if (this.contents[i] != null && (this.contents[i]).stackSize > 0) {
/* 518 */         if (this.contents[i] != null) {
/* 519 */           Block.spawnAsEntity(this.worldObj, (new BlockPos((Entity)this)).up(), this.contents[i]);
/*     */         }
/*     */         
/* 522 */         this.contents[i] = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected float updateDistance(float p_110146_1_, float p_110146_2_) {
/* 528 */     this.prevRenderYawOffset = this.prevRotationYaw;
/* 529 */     this.renderYawOffset = this.rotationYaw;
/* 530 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/* 534 */     return isChild() ? (this.height * 0.5F) : (this.height * 0.9F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntityWithHeading(float strafe, float forward) {
/* 541 */     if (!hasNoGravity()) {
/* 542 */       super.moveEntityWithHeading(strafe, forward);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 550 */     super.onUpdate();
/* 551 */     Rotations rotations = this.dataWatcher.getWatchableObjectRotations(11);
/*     */     
/* 553 */     if (!this.headRotation.equals(rotations)) {
/* 554 */       setHeadRotation(rotations);
/*     */     }
/*     */     
/* 557 */     Rotations rotations1 = this.dataWatcher.getWatchableObjectRotations(12);
/*     */     
/* 559 */     if (!this.bodyRotation.equals(rotations1)) {
/* 560 */       setBodyRotation(rotations1);
/*     */     }
/*     */     
/* 563 */     Rotations rotations2 = this.dataWatcher.getWatchableObjectRotations(13);
/*     */     
/* 565 */     if (!this.leftArmRotation.equals(rotations2)) {
/* 566 */       setLeftArmRotation(rotations2);
/*     */     }
/*     */     
/* 569 */     Rotations rotations3 = this.dataWatcher.getWatchableObjectRotations(14);
/*     */     
/* 571 */     if (!this.rightArmRotation.equals(rotations3)) {
/* 572 */       setRightArmRotation(rotations3);
/*     */     }
/*     */     
/* 575 */     Rotations rotations4 = this.dataWatcher.getWatchableObjectRotations(15);
/*     */     
/* 577 */     if (!this.leftLegRotation.equals(rotations4)) {
/* 578 */       setLeftLegRotation(rotations4);
/*     */     }
/*     */     
/* 581 */     Rotations rotations5 = this.dataWatcher.getWatchableObjectRotations(16);
/*     */     
/* 583 */     if (!this.rightLegRotation.equals(rotations5)) {
/* 584 */       setRightLegRotation(rotations5);
/*     */     }
/*     */     
/* 587 */     boolean flag = hasMarker();
/*     */     
/* 589 */     if (!this.field_181028_bj && flag) {
/* 590 */       func_181550_a(false);
/*     */     } else {
/* 592 */       if (!this.field_181028_bj || flag) {
/*     */         return;
/*     */       }
/*     */       
/* 596 */       func_181550_a(true);
/*     */     } 
/*     */     
/* 599 */     this.field_181028_bj = flag;
/*     */   }
/*     */   
/*     */   private void func_181550_a(boolean p_181550_1_) {
/* 603 */     double d0 = this.posX;
/* 604 */     double d1 = this.posY;
/* 605 */     double d2 = this.posZ;
/*     */     
/* 607 */     if (p_181550_1_) {
/* 608 */       setSize(0.5F, 1.975F);
/*     */     } else {
/* 610 */       setSize(0.0F, 0.0F);
/*     */     } 
/*     */     
/* 613 */     setPosition(d0, d1, d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updatePotionMetadata() {
/* 621 */     setInvisible(this.canInteract);
/*     */   }
/*     */   
/*     */   public void setInvisible(boolean invisible) {
/* 625 */     this.canInteract = invisible;
/* 626 */     super.setInvisible(invisible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChild() {
/* 633 */     return isSmall();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onKillCommand() {
/* 640 */     setDead();
/*     */   }
/*     */   
/*     */   public boolean isImmuneToExplosions() {
/* 644 */     return isInvisible();
/*     */   }
/*     */   
/*     */   private void setSmall(boolean p_175420_1_) {
/* 648 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 650 */     if (p_175420_1_) {
/* 651 */       b0 = (byte)(b0 | 0x1);
/*     */     } else {
/* 653 */       b0 = (byte)(b0 & 0xFFFFFFFE);
/*     */     } 
/*     */     
/* 656 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */   
/*     */   public boolean isSmall() {
/* 660 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   private void setNoGravity(boolean p_175425_1_) {
/* 664 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 666 */     if (p_175425_1_) {
/* 667 */       b0 = (byte)(b0 | 0x2);
/*     */     } else {
/* 669 */       b0 = (byte)(b0 & 0xFFFFFFFD);
/*     */     } 
/*     */     
/* 672 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */   
/*     */   public boolean hasNoGravity() {
/* 676 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x2) != 0);
/*     */   }
/*     */   
/*     */   private void setShowArms(boolean p_175413_1_) {
/* 680 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 682 */     if (p_175413_1_) {
/* 683 */       b0 = (byte)(b0 | 0x4);
/*     */     } else {
/* 685 */       b0 = (byte)(b0 & 0xFFFFFFFB);
/*     */     } 
/*     */     
/* 688 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */   
/*     */   public boolean getShowArms() {
/* 692 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x4) != 0);
/*     */   }
/*     */   
/*     */   private void setNoBasePlate(boolean p_175426_1_) {
/* 696 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 698 */     if (p_175426_1_) {
/* 699 */       b0 = (byte)(b0 | 0x8);
/*     */     } else {
/* 701 */       b0 = (byte)(b0 & 0xFFFFFFF7);
/*     */     } 
/*     */     
/* 704 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */   
/*     */   public boolean hasNoBasePlate() {
/* 708 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x8) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setMarker(boolean p_181027_1_) {
/* 715 */     byte b0 = this.dataWatcher.getWatchableObjectByte(10);
/*     */     
/* 717 */     if (p_181027_1_) {
/* 718 */       b0 = (byte)(b0 | 0x10);
/*     */     } else {
/* 720 */       b0 = (byte)(b0 & 0xFFFFFFEF);
/*     */     } 
/*     */     
/* 723 */     this.dataWatcher.updateObject(10, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMarker() {
/* 731 */     return ((this.dataWatcher.getWatchableObjectByte(10) & 0x10) != 0);
/*     */   }
/*     */   
/*     */   public void setHeadRotation(Rotations p_175415_1_) {
/* 735 */     this.headRotation = p_175415_1_;
/* 736 */     this.dataWatcher.updateObject(11, p_175415_1_);
/*     */   }
/*     */   
/*     */   public void setBodyRotation(Rotations p_175424_1_) {
/* 740 */     this.bodyRotation = p_175424_1_;
/* 741 */     this.dataWatcher.updateObject(12, p_175424_1_);
/*     */   }
/*     */   
/*     */   public void setLeftArmRotation(Rotations p_175405_1_) {
/* 745 */     this.leftArmRotation = p_175405_1_;
/* 746 */     this.dataWatcher.updateObject(13, p_175405_1_);
/*     */   }
/*     */   
/*     */   public void setRightArmRotation(Rotations p_175428_1_) {
/* 750 */     this.rightArmRotation = p_175428_1_;
/* 751 */     this.dataWatcher.updateObject(14, p_175428_1_);
/*     */   }
/*     */   
/*     */   public void setLeftLegRotation(Rotations p_175417_1_) {
/* 755 */     this.leftLegRotation = p_175417_1_;
/* 756 */     this.dataWatcher.updateObject(15, p_175417_1_);
/*     */   }
/*     */   
/*     */   public void setRightLegRotation(Rotations p_175427_1_) {
/* 760 */     this.rightLegRotation = p_175427_1_;
/* 761 */     this.dataWatcher.updateObject(16, p_175427_1_);
/*     */   }
/*     */   
/*     */   public Rotations getHeadRotation() {
/* 765 */     return this.headRotation;
/*     */   }
/*     */   
/*     */   public Rotations getBodyRotation() {
/* 769 */     return this.bodyRotation;
/*     */   }
/*     */   
/*     */   public Rotations getLeftArmRotation() {
/* 773 */     return this.leftArmRotation;
/*     */   }
/*     */   
/*     */   public Rotations getRightArmRotation() {
/* 777 */     return this.rightArmRotation;
/*     */   }
/*     */   
/*     */   public Rotations getLeftLegRotation() {
/* 781 */     return this.leftLegRotation;
/*     */   }
/*     */   
/*     */   public Rotations getRightLegRotation() {
/* 785 */     return this.rightLegRotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 792 */     return (super.canBeCollidedWith() && !hasMarker());
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\item\EntityArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */