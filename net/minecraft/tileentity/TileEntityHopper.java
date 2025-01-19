/*     */ package net.minecraft.tileentity;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.block.BlockHopper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerHopper;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntityHopper extends TileEntityLockable implements IHopper, ITickable {
/*  28 */   private ItemStack[] inventory = new ItemStack[5];
/*     */   private String customName;
/*  30 */   private int transferCooldown = -1;
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  33 */     super.readFromNBT(compound);
/*  34 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/*  35 */     this.inventory = new ItemStack[getSizeInventory()];
/*     */     
/*  37 */     if (compound.hasKey("CustomName", 8)) {
/*  38 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */     
/*  41 */     this.transferCooldown = compound.getInteger("TransferCooldown");
/*     */     
/*  43 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*  44 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  45 */       int j = nbttagcompound.getByte("Slot");
/*     */       
/*  47 */       if (j >= 0 && j < this.inventory.length) {
/*  48 */         this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  54 */     super.writeToNBT(compound);
/*  55 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  57 */     for (int i = 0; i < this.inventory.length; i++) {
/*  58 */       if (this.inventory[i] != null) {
/*  59 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  60 */         nbttagcompound.setByte("Slot", (byte)i);
/*  61 */         this.inventory[i].writeToNBT(nbttagcompound);
/*  62 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*  67 */     compound.setInteger("TransferCooldown", this.transferCooldown);
/*     */     
/*  69 */     if (hasCustomName()) {
/*  70 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/*  79 */     super.markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  86 */     return this.inventory.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  93 */     return this.inventory[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 100 */     if (this.inventory[index] != null) {
/* 101 */       if ((this.inventory[index]).stackSize <= count) {
/* 102 */         ItemStack itemstack1 = this.inventory[index];
/* 103 */         this.inventory[index] = null;
/* 104 */         return itemstack1;
/*     */       } 
/* 106 */       ItemStack itemstack = this.inventory[index].splitStack(count);
/*     */       
/* 108 */       if ((this.inventory[index]).stackSize == 0) {
/* 109 */         this.inventory[index] = null;
/*     */       }
/*     */       
/* 112 */       return itemstack;
/*     */     } 
/*     */     
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 123 */     if (this.inventory[index] != null) {
/* 124 */       ItemStack itemstack = this.inventory[index];
/* 125 */       this.inventory[index] = null;
/* 126 */       return itemstack;
/*     */     } 
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 136 */     this.inventory[index] = stack;
/*     */     
/* 138 */     if (stack != null && stack.stackSize > getInventoryStackLimit()) {
/* 139 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 147 */     return hasCustomName() ? this.customName : "container.hopper";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 154 */     return (this.customName != null && this.customName.length() > 0);
/*     */   }
/*     */   
/*     */   public void setCustomName(String customNameIn) {
/* 158 */     this.customName = customNameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 165 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 172 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 192 */     if (this.worldObj != null && !this.worldObj.isRemote) {
/* 193 */       this.transferCooldown--;
/*     */       
/* 195 */       if (!isOnTransferCooldown()) {
/* 196 */         setTransferCooldown(0);
/* 197 */         updateHopper();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean updateHopper() {
/* 203 */     if (this.worldObj != null && !this.worldObj.isRemote) {
/* 204 */       if (!isOnTransferCooldown() && BlockHopper.isEnabled(getBlockMetadata())) {
/* 205 */         boolean flag = false;
/*     */         
/* 207 */         if (!isEmpty()) {
/* 208 */           flag = transferItemsOut();
/*     */         }
/*     */         
/* 211 */         if (!isFull()) {
/* 212 */           flag = !(!captureDroppedItems(this) && !flag);
/*     */         }
/*     */         
/* 215 */         if (flag) {
/* 216 */           setTransferCooldown(8);
/* 217 */           markDirty();
/* 218 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 222 */       return false;
/*     */     } 
/* 224 */     return false;
/*     */   }
/*     */   private boolean isEmpty() { byte b;
/*     */     int i;
/*     */     ItemStack[] arrayOfItemStack;
/* 229 */     for (i = (arrayOfItemStack = this.inventory).length, b = 0; b < i; ) { ItemStack itemstack = arrayOfItemStack[b];
/* 230 */       if (itemstack != null) {
/* 231 */         return false;
/*     */       }
/*     */       b++; }
/*     */     
/* 235 */     return true; } private boolean isFull() {
/*     */     byte b;
/*     */     int i;
/*     */     ItemStack[] arrayOfItemStack;
/* 239 */     for (i = (arrayOfItemStack = this.inventory).length, b = 0; b < i; ) { ItemStack itemstack = arrayOfItemStack[b];
/* 240 */       if (itemstack == null || itemstack.stackSize != itemstack.getMaxStackSize()) {
/* 241 */         return false;
/*     */       }
/*     */       b++; }
/*     */     
/* 245 */     return true;
/*     */   }
/*     */   
/*     */   private boolean transferItemsOut() {
/* 249 */     IInventory iinventory = getInventoryForHopperTransfer();
/*     */     
/* 251 */     if (iinventory == null) {
/* 252 */       return false;
/*     */     }
/* 254 */     EnumFacing enumfacing = BlockHopper.getFacing(getBlockMetadata()).getOpposite();
/*     */     
/* 256 */     if (isInventoryFull(iinventory, enumfacing)) {
/* 257 */       return false;
/*     */     }
/* 259 */     for (int i = 0; i < getSizeInventory(); i++) {
/* 260 */       if (getStackInSlot(i) != null) {
/* 261 */         ItemStack itemstack = getStackInSlot(i).copy();
/* 262 */         ItemStack itemstack1 = putStackInInventoryAllSlots(iinventory, decrStackSize(i, 1), enumfacing);
/*     */         
/* 264 */         if (itemstack1 == null || itemstack1.stackSize == 0) {
/* 265 */           iinventory.markDirty();
/* 266 */           return true;
/*     */         } 
/*     */         
/* 269 */         setInventorySlotContents(i, itemstack);
/*     */       } 
/*     */     } 
/*     */     
/* 273 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isInventoryFull(IInventory inventoryIn, EnumFacing side) {
/* 282 */     if (inventoryIn instanceof ISidedInventory) {
/* 283 */       ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
/* 284 */       int[] aint = isidedinventory.getSlotsForFace(side);
/*     */       
/* 286 */       for (int k = 0; k < aint.length; k++) {
/* 287 */         ItemStack itemstack1 = isidedinventory.getStackInSlot(aint[k]);
/*     */         
/* 289 */         if (itemstack1 == null || itemstack1.stackSize != itemstack1.getMaxStackSize()) {
/* 290 */           return false;
/*     */         }
/*     */       } 
/*     */     } else {
/* 294 */       int i = inventoryIn.getSizeInventory();
/*     */       
/* 296 */       for (int j = 0; j < i; j++) {
/* 297 */         ItemStack itemstack = inventoryIn.getStackInSlot(j);
/*     */         
/* 299 */         if (itemstack == null || itemstack.stackSize != itemstack.getMaxStackSize()) {
/* 300 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 305 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isInventoryEmpty(IInventory inventoryIn, EnumFacing side) {
/* 312 */     if (inventoryIn instanceof ISidedInventory) {
/* 313 */       ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
/* 314 */       int[] aint = isidedinventory.getSlotsForFace(side);
/*     */       
/* 316 */       for (int i = 0; i < aint.length; i++) {
/* 317 */         if (isidedinventory.getStackInSlot(aint[i]) != null) {
/* 318 */           return false;
/*     */         }
/*     */       } 
/*     */     } else {
/* 322 */       int j = inventoryIn.getSizeInventory();
/*     */       
/* 324 */       for (int k = 0; k < j; k++) {
/* 325 */         if (inventoryIn.getStackInSlot(k) != null) {
/* 326 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 331 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean captureDroppedItems(IHopper p_145891_0_) {
/* 335 */     IInventory iinventory = getHopperInventory(p_145891_0_);
/*     */     
/* 337 */     if (iinventory != null) {
/* 338 */       EnumFacing enumfacing = EnumFacing.DOWN;
/*     */       
/* 340 */       if (isInventoryEmpty(iinventory, enumfacing)) {
/* 341 */         return false;
/*     */       }
/*     */       
/* 344 */       if (iinventory instanceof ISidedInventory) {
/* 345 */         ISidedInventory isidedinventory = (ISidedInventory)iinventory;
/* 346 */         int[] aint = isidedinventory.getSlotsForFace(enumfacing);
/*     */         
/* 348 */         for (int i = 0; i < aint.length; i++) {
/* 349 */           if (pullItemFromSlot(p_145891_0_, iinventory, aint[i], enumfacing)) {
/* 350 */             return true;
/*     */           }
/*     */         } 
/*     */       } else {
/* 354 */         int j = iinventory.getSizeInventory();
/*     */         
/* 356 */         for (int k = 0; k < j; k++) {
/* 357 */           if (pullItemFromSlot(p_145891_0_, iinventory, k, enumfacing)) {
/* 358 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/* 363 */       for (EntityItem entityitem : func_181556_a(p_145891_0_.getWorld(), p_145891_0_.getXPos(), p_145891_0_.getYPos() + 1.0D, p_145891_0_.getZPos())) {
/* 364 */         if (putDropInInventoryAllSlots(p_145891_0_, entityitem)) {
/* 365 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 370 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean pullItemFromSlot(IHopper hopper, IInventory inventoryIn, int index, EnumFacing direction) {
/* 378 */     ItemStack itemstack = inventoryIn.getStackInSlot(index);
/*     */     
/* 380 */     if (itemstack != null && canExtractItemFromSlot(inventoryIn, itemstack, index, direction)) {
/* 381 */       ItemStack itemstack1 = itemstack.copy();
/* 382 */       ItemStack itemstack2 = putStackInInventoryAllSlots(hopper, inventoryIn.decrStackSize(index, 1), (EnumFacing)null);
/*     */       
/* 384 */       if (itemstack2 == null || itemstack2.stackSize == 0) {
/* 385 */         inventoryIn.markDirty();
/* 386 */         return true;
/*     */       } 
/*     */       
/* 389 */       inventoryIn.setInventorySlotContents(index, itemstack1);
/*     */     } 
/*     */     
/* 392 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean putDropInInventoryAllSlots(IInventory p_145898_0_, EntityItem itemIn) {
/* 400 */     boolean flag = false;
/*     */     
/* 402 */     if (itemIn == null) {
/* 403 */       return false;
/*     */     }
/* 405 */     ItemStack itemstack = itemIn.getEntityItem().copy();
/* 406 */     ItemStack itemstack1 = putStackInInventoryAllSlots(p_145898_0_, itemstack, (EnumFacing)null);
/*     */     
/* 408 */     if (itemstack1 != null && itemstack1.stackSize != 0) {
/* 409 */       itemIn.setEntityItemStack(itemstack1);
/*     */     } else {
/* 411 */       flag = true;
/* 412 */       itemIn.setDead();
/*     */     } 
/*     */     
/* 415 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack putStackInInventoryAllSlots(IInventory inventoryIn, ItemStack stack, EnumFacing side) {
/* 423 */     if (inventoryIn instanceof ISidedInventory && side != null) {
/* 424 */       ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
/* 425 */       int[] aint = isidedinventory.getSlotsForFace(side);
/*     */       
/* 427 */       for (int k = 0; k < aint.length && stack != null && stack.stackSize > 0; k++) {
/* 428 */         stack = insertStack(inventoryIn, stack, aint[k], side);
/*     */       }
/*     */     } else {
/* 431 */       int i = inventoryIn.getSizeInventory();
/*     */       
/* 433 */       for (int j = 0; j < i && stack != null && stack.stackSize > 0; j++) {
/* 434 */         stack = insertStack(inventoryIn, stack, j, side);
/*     */       }
/*     */     } 
/*     */     
/* 438 */     if (stack != null && stack.stackSize == 0) {
/* 439 */       stack = null;
/*     */     }
/*     */     
/* 442 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canInsertItemInSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
/* 449 */     return !inventoryIn.isItemValidForSlot(index, stack) ? false : (!(inventoryIn instanceof ISidedInventory && !((ISidedInventory)inventoryIn).canInsertItem(index, stack, side)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canExtractItemFromSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
/* 456 */     return !(inventoryIn instanceof ISidedInventory && !((ISidedInventory)inventoryIn).canExtractItem(index, stack, side));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ItemStack insertStack(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
/* 463 */     ItemStack itemstack = inventoryIn.getStackInSlot(index);
/*     */     
/* 465 */     if (canInsertItemInSlot(inventoryIn, stack, index, side)) {
/* 466 */       boolean flag = false;
/*     */       
/* 468 */       if (itemstack == null) {
/* 469 */         inventoryIn.setInventorySlotContents(index, stack);
/* 470 */         stack = null;
/* 471 */         flag = true;
/* 472 */       } else if (canCombine(itemstack, stack)) {
/* 473 */         int i = stack.getMaxStackSize() - itemstack.stackSize;
/* 474 */         int j = Math.min(stack.stackSize, i);
/* 475 */         stack.stackSize -= j;
/* 476 */         itemstack.stackSize += j;
/* 477 */         flag = (j > 0);
/*     */       } 
/*     */       
/* 480 */       if (flag) {
/* 481 */         if (inventoryIn instanceof TileEntityHopper) {
/* 482 */           TileEntityHopper tileentityhopper = (TileEntityHopper)inventoryIn;
/*     */           
/* 484 */           if (tileentityhopper.mayTransfer()) {
/* 485 */             tileentityhopper.setTransferCooldown(8);
/*     */           }
/*     */           
/* 488 */           inventoryIn.markDirty();
/*     */         } 
/*     */         
/* 491 */         inventoryIn.markDirty();
/*     */       } 
/*     */     } 
/*     */     
/* 495 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IInventory getInventoryForHopperTransfer() {
/* 502 */     EnumFacing enumfacing = BlockHopper.getFacing(getBlockMetadata());
/* 503 */     return getInventoryAtPosition(getWorld(), (this.pos.getX() + enumfacing.getFrontOffsetX()), (this.pos.getY() + enumfacing.getFrontOffsetY()), (this.pos.getZ() + enumfacing.getFrontOffsetZ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IInventory getHopperInventory(IHopper hopper) {
/* 510 */     return getInventoryAtPosition(hopper.getWorld(), hopper.getXPos(), hopper.getYPos() + 1.0D, hopper.getZPos());
/*     */   }
/*     */   
/*     */   public static List<EntityItem> func_181556_a(World p_181556_0_, double p_181556_1_, double p_181556_3_, double p_181556_5_) {
/* 514 */     return p_181556_0_.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(p_181556_1_ - 0.5D, p_181556_3_ - 0.5D, p_181556_5_ - 0.5D, p_181556_1_ + 0.5D, p_181556_3_ + 0.5D, p_181556_5_ + 0.5D), EntitySelectors.selectAnything);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static IInventory getInventoryAtPosition(World worldIn, double x, double y, double z) {
/*     */     ILockableContainer iLockableContainer;
/* 521 */     IInventory iInventory1, iinventory = null;
/* 522 */     int i = MathHelper.floor_double(x);
/* 523 */     int j = MathHelper.floor_double(y);
/* 524 */     int k = MathHelper.floor_double(z);
/* 525 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 526 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */     
/* 528 */     if (block.hasTileEntity()) {
/* 529 */       TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */       
/* 531 */       if (tileentity instanceof IInventory) {
/* 532 */         iinventory = (IInventory)tileentity;
/*     */         
/* 534 */         if (iinventory instanceof TileEntityChest && block instanceof BlockChest) {
/* 535 */           iLockableContainer = ((BlockChest)block).getLockableContainer(worldIn, blockpos);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 540 */     if (iLockableContainer == null) {
/* 541 */       List<Entity> list = worldIn.getEntitiesInAABBexcluding(null, new AxisAlignedBB(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), EntitySelectors.selectInventories);
/*     */       
/* 543 */       if (list.size() > 0) {
/* 544 */         iInventory1 = (IInventory)list.get(worldIn.rand.nextInt(list.size()));
/*     */       }
/*     */     } 
/*     */     
/* 548 */     return iInventory1;
/*     */   }
/*     */   
/*     */   private static boolean canCombine(ItemStack stack1, ItemStack stack2) {
/* 552 */     return (stack1.getItem() != stack2.getItem()) ? false : ((stack1.getMetadata() != stack2.getMetadata()) ? false : ((stack1.stackSize > stack1.getMaxStackSize()) ? false : ItemStack.areItemStackTagsEqual(stack1, stack2)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXPos() {
/* 559 */     return this.pos.getX() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYPos() {
/* 566 */     return this.pos.getY() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZPos() {
/* 573 */     return this.pos.getZ() + 0.5D;
/*     */   }
/*     */   
/*     */   public void setTransferCooldown(int ticks) {
/* 577 */     this.transferCooldown = ticks;
/*     */   }
/*     */   
/*     */   public boolean isOnTransferCooldown() {
/* 581 */     return (this.transferCooldown > 0);
/*     */   }
/*     */   
/*     */   public boolean mayTransfer() {
/* 585 */     return (this.transferCooldown <= 1);
/*     */   }
/*     */   
/*     */   public String getGuiID() {
/* 589 */     return "minecraft:hopper";
/*     */   }
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 593 */     return (Container)new ContainerHopper(playerInventory, this, playerIn);
/*     */   }
/*     */   
/*     */   public int getField(int id) {
/* 597 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount() {
/* 604 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 608 */     for (int i = 0; i < this.inventory.length; i++)
/* 609 */       this.inventory[i] = null; 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */