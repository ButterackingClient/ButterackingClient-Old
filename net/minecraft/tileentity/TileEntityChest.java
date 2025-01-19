/*     */ package net.minecraft.tileentity;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerChest;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryLargeChest;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ 
/*     */ public class TileEntityChest extends TileEntityLockable implements ITickable, IInventory {
/*  20 */   private ItemStack[] chestContents = new ItemStack[27];
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean adjacentChestChecked;
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestZNeg;
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestXPos;
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestXNeg;
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestZPos;
/*     */ 
/*     */ 
/*     */   
/*     */   public float lidAngle;
/*     */ 
/*     */ 
/*     */   
/*     */   public float prevLidAngle;
/*     */ 
/*     */ 
/*     */   
/*     */   public int numPlayersUsing;
/*     */ 
/*     */ 
/*     */   
/*     */   private int ticksSinceSync;
/*     */ 
/*     */ 
/*     */   
/*     */   private int cachedChestType;
/*     */ 
/*     */ 
/*     */   
/*     */   private String customName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityChest() {
/*  70 */     this.cachedChestType = -1;
/*     */   }
/*     */   
/*     */   public TileEntityChest(int chestType) {
/*  74 */     this.cachedChestType = chestType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  81 */     return 27;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  88 */     return this.chestContents[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  95 */     if (this.chestContents[index] != null) {
/*  96 */       if ((this.chestContents[index]).stackSize <= count) {
/*  97 */         ItemStack itemstack1 = this.chestContents[index];
/*  98 */         this.chestContents[index] = null;
/*  99 */         markDirty();
/* 100 */         return itemstack1;
/*     */       } 
/* 102 */       ItemStack itemstack = this.chestContents[index].splitStack(count);
/*     */       
/* 104 */       if ((this.chestContents[index]).stackSize == 0) {
/* 105 */         this.chestContents[index] = null;
/*     */       }
/*     */       
/* 108 */       markDirty();
/* 109 */       return itemstack;
/*     */     } 
/*     */     
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 120 */     if (this.chestContents[index] != null) {
/* 121 */       ItemStack itemstack = this.chestContents[index];
/* 122 */       this.chestContents[index] = null;
/* 123 */       return itemstack;
/*     */     } 
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 133 */     this.chestContents[index] = stack;
/*     */     
/* 135 */     if (stack != null && stack.stackSize > getInventoryStackLimit()) {
/* 136 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 139 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 146 */     return hasCustomName() ? this.customName : "container.chest";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 153 */     return (this.customName != null && this.customName.length() > 0);
/*     */   }
/*     */   
/*     */   public void setCustomName(String name) {
/* 157 */     this.customName = name;
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 161 */     super.readFromNBT(compound);
/* 162 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/* 163 */     this.chestContents = new ItemStack[getSizeInventory()];
/*     */     
/* 165 */     if (compound.hasKey("CustomName", 8)) {
/* 166 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */     
/* 169 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 170 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 171 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*     */       
/* 173 */       if (j >= 0 && j < this.chestContents.length) {
/* 174 */         this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 180 */     super.writeToNBT(compound);
/* 181 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 183 */     for (int i = 0; i < this.chestContents.length; i++) {
/* 184 */       if (this.chestContents[i] != null) {
/* 185 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 186 */         nbttagcompound.setByte("Slot", (byte)i);
/* 187 */         this.chestContents[i].writeToNBT(nbttagcompound);
/* 188 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*     */     
/* 194 */     if (hasCustomName()) {
/* 195 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 203 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 210 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */   
/*     */   public void updateContainingBlockInfo() {
/* 214 */     super.updateContainingBlockInfo();
/* 215 */     this.adjacentChestChecked = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_174910_a(TileEntityChest chestTe, EnumFacing side) {
/* 220 */     if (chestTe.isInvalid()) {
/* 221 */       this.adjacentChestChecked = false;
/* 222 */     } else if (this.adjacentChestChecked) {
/* 223 */       switch (side) {
/*     */         case NORTH:
/* 225 */           if (this.adjacentChestZNeg != chestTe) {
/* 226 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case SOUTH:
/* 232 */           if (this.adjacentChestZPos != chestTe) {
/* 233 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case EAST:
/* 239 */           if (this.adjacentChestXPos != chestTe) {
/* 240 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case WEST:
/* 246 */           if (this.adjacentChestXNeg != chestTe) {
/* 247 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkForAdjacentChests() {
/* 257 */     if (!this.adjacentChestChecked) {
/* 258 */       this.adjacentChestChecked = true;
/* 259 */       this.adjacentChestXNeg = getAdjacentChest(EnumFacing.WEST);
/* 260 */       this.adjacentChestXPos = getAdjacentChest(EnumFacing.EAST);
/* 261 */       this.adjacentChestZNeg = getAdjacentChest(EnumFacing.NORTH);
/* 262 */       this.adjacentChestZPos = getAdjacentChest(EnumFacing.SOUTH);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected TileEntityChest getAdjacentChest(EnumFacing side) {
/* 267 */     BlockPos blockpos = this.pos.offset(side);
/*     */     
/* 269 */     if (isChestAt(blockpos)) {
/* 270 */       TileEntity tileentity = this.worldObj.getTileEntity(blockpos);
/*     */       
/* 272 */       if (tileentity instanceof TileEntityChest) {
/* 273 */         TileEntityChest tileentitychest = (TileEntityChest)tileentity;
/* 274 */         tileentitychest.func_174910_a(this, side.getOpposite());
/* 275 */         return tileentitychest;
/*     */       } 
/*     */     } 
/*     */     
/* 279 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isChestAt(BlockPos posIn) {
/* 283 */     if (this.worldObj == null) {
/* 284 */       return false;
/*     */     }
/* 286 */     Block block = this.worldObj.getBlockState(posIn).getBlock();
/* 287 */     return (block instanceof BlockChest && ((BlockChest)block).chestType == getChestType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 295 */     checkForAdjacentChests();
/* 296 */     int i = this.pos.getX();
/* 297 */     int j = this.pos.getY();
/* 298 */     int k = this.pos.getZ();
/* 299 */     this.ticksSinceSync++;
/*     */     
/* 301 */     if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0) {
/* 302 */       this.numPlayersUsing = 0;
/* 303 */       float f = 5.0F;
/*     */       
/* 305 */       for (EntityPlayer entityplayer : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((i - f), (j - f), (k - f), ((i + 1) + f), ((j + 1) + f), ((k + 1) + f)))) {
/* 306 */         if (entityplayer.openContainer instanceof ContainerChest) {
/* 307 */           IInventory iinventory = ((ContainerChest)entityplayer.openContainer).getLowerChestInventory();
/*     */           
/* 309 */           if (iinventory == this || (iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest(this))) {
/* 310 */             this.numPlayersUsing++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 316 */     this.prevLidAngle = this.lidAngle;
/* 317 */     float f1 = 0.1F;
/*     */     
/* 319 */     if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
/* 320 */       double d1 = i + 0.5D;
/* 321 */       double d2 = k + 0.5D;
/*     */       
/* 323 */       if (this.adjacentChestZPos != null) {
/* 324 */         d2 += 0.5D;
/*     */       }
/*     */       
/* 327 */       if (this.adjacentChestXPos != null) {
/* 328 */         d1 += 0.5D;
/*     */       }
/*     */       
/* 331 */       this.worldObj.playSoundEffect(d1, j + 0.5D, d2, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*     */     } 
/*     */     
/* 334 */     if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0F) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0F)) {
/* 335 */       float f2 = this.lidAngle;
/*     */       
/* 337 */       if (this.numPlayersUsing > 0) {
/* 338 */         this.lidAngle += f1;
/*     */       } else {
/* 340 */         this.lidAngle -= f1;
/*     */       } 
/*     */       
/* 343 */       if (this.lidAngle > 1.0F) {
/* 344 */         this.lidAngle = 1.0F;
/*     */       }
/*     */       
/* 347 */       float f3 = 0.5F;
/*     */       
/* 349 */       if (this.lidAngle < f3 && f2 >= f3 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
/* 350 */         double d3 = i + 0.5D;
/* 351 */         double d0 = k + 0.5D;
/*     */         
/* 353 */         if (this.adjacentChestZPos != null) {
/* 354 */           d0 += 0.5D;
/*     */         }
/*     */         
/* 357 */         if (this.adjacentChestXPos != null) {
/* 358 */           d3 += 0.5D;
/*     */         }
/*     */         
/* 361 */         this.worldObj.playSoundEffect(d3, j + 0.5D, d0, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
/*     */       } 
/*     */       
/* 364 */       if (this.lidAngle < 0.0F) {
/* 365 */         this.lidAngle = 0.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 371 */     if (id == 1) {
/* 372 */       this.numPlayersUsing = type;
/* 373 */       return true;
/*     */     } 
/* 375 */     return super.receiveClientEvent(id, type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {
/* 380 */     if (!player.isSpectator()) {
/* 381 */       if (this.numPlayersUsing < 0) {
/* 382 */         this.numPlayersUsing = 0;
/*     */       }
/*     */       
/* 385 */       this.numPlayersUsing++;
/* 386 */       this.worldObj.addBlockEvent(this.pos, getBlockType(), 1, this.numPlayersUsing);
/* 387 */       this.worldObj.notifyNeighborsOfStateChange(this.pos, getBlockType());
/* 388 */       this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), getBlockType());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {
/* 393 */     if (!player.isSpectator() && getBlockType() instanceof BlockChest) {
/* 394 */       this.numPlayersUsing--;
/* 395 */       this.worldObj.addBlockEvent(this.pos, getBlockType(), 1, this.numPlayersUsing);
/* 396 */       this.worldObj.notifyNeighborsOfStateChange(this.pos, getBlockType());
/* 397 */       this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), getBlockType());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 405 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 412 */     super.invalidate();
/* 413 */     updateContainingBlockInfo();
/* 414 */     checkForAdjacentChests();
/*     */   }
/*     */   
/*     */   public int getChestType() {
/* 418 */     if (this.cachedChestType == -1) {
/* 419 */       if (this.worldObj == null || !(getBlockType() instanceof BlockChest)) {
/* 420 */         return 0;
/*     */       }
/*     */       
/* 423 */       this.cachedChestType = ((BlockChest)getBlockType()).chestType;
/*     */     } 
/*     */     
/* 426 */     return this.cachedChestType;
/*     */   }
/*     */   
/*     */   public String getGuiID() {
/* 430 */     return "minecraft:chest";
/*     */   }
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 434 */     return (Container)new ContainerChest((IInventory)playerInventory, this, playerIn);
/*     */   }
/*     */   
/*     */   public int getField(int id) {
/* 438 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount() {
/* 445 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 449 */     for (int i = 0; i < this.chestContents.length; i++)
/* 450 */       this.chestContents[i] = null; 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */