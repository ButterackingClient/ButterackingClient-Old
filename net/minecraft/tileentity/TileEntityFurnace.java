/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFurnace;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerFurnace;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.inventory.SlotFurnaceFuel;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class TileEntityFurnace extends TileEntityLockable implements ITickable, ISidedInventory {
/*  29 */   private static final int[] slotsTop = new int[1];
/*  30 */   private static final int[] slotsBottom = new int[] { 2, 1 };
/*  31 */   private static final int[] slotsSides = new int[] { 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   private ItemStack[] furnaceItemStacks = new ItemStack[3];
/*     */ 
/*     */   
/*     */   private int furnaceBurnTime;
/*     */ 
/*     */   
/*     */   private int currentItemBurnTime;
/*     */ 
/*     */   
/*     */   private int cookTime;
/*     */ 
/*     */   
/*     */   private int totalCookTime;
/*     */ 
/*     */   
/*     */   private String furnaceCustomName;
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  55 */     return this.furnaceItemStacks.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  62 */     return this.furnaceItemStacks[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  69 */     if (this.furnaceItemStacks[index] != null) {
/*  70 */       if ((this.furnaceItemStacks[index]).stackSize <= count) {
/*  71 */         ItemStack itemstack1 = this.furnaceItemStacks[index];
/*  72 */         this.furnaceItemStacks[index] = null;
/*  73 */         return itemstack1;
/*     */       } 
/*  75 */       ItemStack itemstack = this.furnaceItemStacks[index].splitStack(count);
/*     */       
/*  77 */       if ((this.furnaceItemStacks[index]).stackSize == 0) {
/*  78 */         this.furnaceItemStacks[index] = null;
/*     */       }
/*     */       
/*  81 */       return itemstack;
/*     */     } 
/*     */     
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  92 */     if (this.furnaceItemStacks[index] != null) {
/*  93 */       ItemStack itemstack = this.furnaceItemStacks[index];
/*  94 */       this.furnaceItemStacks[index] = null;
/*  95 */       return itemstack;
/*     */     } 
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 105 */     boolean flag = (stack != null && stack.isItemEqual(this.furnaceItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.furnaceItemStacks[index]));
/* 106 */     this.furnaceItemStacks[index] = stack;
/*     */     
/* 108 */     if (stack != null && stack.stackSize > getInventoryStackLimit()) {
/* 109 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 112 */     if (index == 0 && !flag) {
/* 113 */       this.totalCookTime = getCookTime(stack);
/* 114 */       this.cookTime = 0;
/* 115 */       markDirty();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 123 */     return hasCustomName() ? this.furnaceCustomName : "container.furnace";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 130 */     return (this.furnaceCustomName != null && this.furnaceCustomName.length() > 0);
/*     */   }
/*     */   
/*     */   public void setCustomInventoryName(String p_145951_1_) {
/* 134 */     this.furnaceCustomName = p_145951_1_;
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 138 */     super.readFromNBT(compound);
/* 139 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/* 140 */     this.furnaceItemStacks = new ItemStack[getSizeInventory()];
/*     */     
/* 142 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 143 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 144 */       int j = nbttagcompound.getByte("Slot");
/*     */       
/* 146 */       if (j >= 0 && j < this.furnaceItemStacks.length) {
/* 147 */         this.furnaceItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */     
/* 151 */     this.furnaceBurnTime = compound.getShort("BurnTime");
/* 152 */     this.cookTime = compound.getShort("CookTime");
/* 153 */     this.totalCookTime = compound.getShort("CookTimeTotal");
/* 154 */     this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
/*     */     
/* 156 */     if (compound.hasKey("CustomName", 8)) {
/* 157 */       this.furnaceCustomName = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 162 */     super.writeToNBT(compound);
/* 163 */     compound.setShort("BurnTime", (short)this.furnaceBurnTime);
/* 164 */     compound.setShort("CookTime", (short)this.cookTime);
/* 165 */     compound.setShort("CookTimeTotal", (short)this.totalCookTime);
/* 166 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 168 */     for (int i = 0; i < this.furnaceItemStacks.length; i++) {
/* 169 */       if (this.furnaceItemStacks[i] != null) {
/* 170 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 171 */         nbttagcompound.setByte("Slot", (byte)i);
/* 172 */         this.furnaceItemStacks[i].writeToNBT(nbttagcompound);
/* 173 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*     */     
/* 179 */     if (hasCustomName()) {
/* 180 */       compound.setString("CustomName", this.furnaceCustomName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 188 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 195 */     return (this.furnaceBurnTime > 0);
/*     */   }
/*     */   
/*     */   public static boolean isBurning(IInventory p_174903_0_) {
/* 199 */     return (p_174903_0_.getField(0) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 206 */     boolean flag = isBurning();
/* 207 */     boolean flag1 = false;
/*     */     
/* 209 */     if (isBurning()) {
/* 210 */       this.furnaceBurnTime--;
/*     */     }
/*     */     
/* 213 */     if (!this.worldObj.isRemote) {
/* 214 */       if (isBurning() || (this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] != null)) {
/* 215 */         if (!isBurning() && canSmelt()) {
/* 216 */           this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
/*     */           
/* 218 */           if (isBurning()) {
/* 219 */             flag1 = true;
/*     */             
/* 221 */             if (this.furnaceItemStacks[1] != null) {
/* 222 */               (this.furnaceItemStacks[1]).stackSize--;
/*     */               
/* 224 */               if ((this.furnaceItemStacks[1]).stackSize == 0) {
/* 225 */                 Item item = this.furnaceItemStacks[1].getItem().getContainerItem();
/* 226 */                 this.furnaceItemStacks[1] = (item != null) ? new ItemStack(item) : null;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 232 */         if (isBurning() && canSmelt()) {
/* 233 */           this.cookTime++;
/*     */           
/* 235 */           if (this.cookTime == this.totalCookTime) {
/* 236 */             this.cookTime = 0;
/* 237 */             this.totalCookTime = getCookTime(this.furnaceItemStacks[0]);
/* 238 */             smeltItem();
/* 239 */             flag1 = true;
/*     */           } 
/*     */         } else {
/* 242 */           this.cookTime = 0;
/*     */         } 
/* 244 */       } else if (!isBurning() && this.cookTime > 0) {
/* 245 */         this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
/*     */       } 
/*     */       
/* 248 */       if (flag != isBurning()) {
/* 249 */         flag1 = true;
/* 250 */         BlockFurnace.setState(isBurning(), this.worldObj, this.pos);
/*     */       } 
/*     */     } 
/*     */     
/* 254 */     if (flag1) {
/* 255 */       markDirty();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getCookTime(ItemStack stack) {
/* 260 */     return 200;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canSmelt() {
/* 267 */     if (this.furnaceItemStacks[0] == null) {
/* 268 */       return false;
/*     */     }
/* 270 */     ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
/* 271 */     return (itemstack == null) ? false : ((this.furnaceItemStacks[2] == null) ? true : (!this.furnaceItemStacks[2].isItemEqual(itemstack) ? false : (((this.furnaceItemStacks[2]).stackSize < getInventoryStackLimit() && (this.furnaceItemStacks[2]).stackSize < this.furnaceItemStacks[2].getMaxStackSize()) ? true : (((this.furnaceItemStacks[2]).stackSize < itemstack.getMaxStackSize())))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void smeltItem() {
/* 279 */     if (canSmelt()) {
/* 280 */       ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
/*     */       
/* 282 */       if (this.furnaceItemStacks[2] == null) {
/* 283 */         this.furnaceItemStacks[2] = itemstack.copy();
/* 284 */       } else if (this.furnaceItemStacks[2].getItem() == itemstack.getItem()) {
/* 285 */         (this.furnaceItemStacks[2]).stackSize++;
/*     */       } 
/*     */       
/* 288 */       if (this.furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge) && this.furnaceItemStacks[0].getMetadata() == 1 && this.furnaceItemStacks[1] != null && this.furnaceItemStacks[1].getItem() == Items.bucket) {
/* 289 */         this.furnaceItemStacks[1] = new ItemStack(Items.water_bucket);
/*     */       }
/*     */       
/* 292 */       (this.furnaceItemStacks[0]).stackSize--;
/*     */       
/* 294 */       if ((this.furnaceItemStacks[0]).stackSize <= 0) {
/* 295 */         this.furnaceItemStacks[0] = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getItemBurnTime(ItemStack p_145952_0_) {
/* 305 */     if (p_145952_0_ == null) {
/* 306 */       return 0;
/*     */     }
/* 308 */     Item item = p_145952_0_.getItem();
/*     */     
/* 310 */     if (item instanceof net.minecraft.item.ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
/* 311 */       Block block = Block.getBlockFromItem(item);
/*     */       
/* 313 */       if (block == Blocks.wooden_slab) {
/* 314 */         return 150;
/*     */       }
/*     */       
/* 317 */       if (block.getMaterial() == Material.wood) {
/* 318 */         return 300;
/*     */       }
/*     */       
/* 321 */       if (block == Blocks.coal_block) {
/* 322 */         return 16000;
/*     */       }
/*     */     } 
/*     */     
/* 326 */     return (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) ? 200 : ((item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) ? 200 : ((item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals("WOOD")) ? 200 : ((item == Items.stick) ? 100 : ((item == Items.coal) ? 1600 : ((item == Items.lava_bucket) ? 20000 : ((item == Item.getItemFromBlock(Blocks.sapling)) ? 100 : ((item == Items.blaze_rod) ? 2400 : 0)))))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isItemFuel(ItemStack p_145954_0_) {
/* 331 */     return (getItemBurnTime(p_145954_0_) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 338 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
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
/* 351 */     return (index == 2) ? false : ((index != 1) ? true : (!(!isItemFuel(stack) && !SlotFurnaceFuel.isBucket(stack))));
/*     */   }
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side) {
/* 355 */     return (side == EnumFacing.DOWN) ? slotsBottom : ((side == EnumFacing.UP) ? slotsTop : slotsSides);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
/* 363 */     return isItemValidForSlot(index, itemStackIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
/* 371 */     if (direction == EnumFacing.DOWN && index == 1) {
/* 372 */       Item item = stack.getItem();
/*     */       
/* 374 */       if (item != Items.water_bucket && item != Items.bucket) {
/* 375 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 379 */     return true;
/*     */   }
/*     */   
/*     */   public String getGuiID() {
/* 383 */     return "minecraft:furnace";
/*     */   }
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 387 */     return (Container)new ContainerFurnace(playerInventory, (IInventory)this);
/*     */   }
/*     */   
/*     */   public int getField(int id) {
/* 391 */     switch (id) {
/*     */       case 0:
/* 393 */         return this.furnaceBurnTime;
/*     */       
/*     */       case 1:
/* 396 */         return this.currentItemBurnTime;
/*     */       
/*     */       case 2:
/* 399 */         return this.cookTime;
/*     */       
/*     */       case 3:
/* 402 */         return this.totalCookTime;
/*     */     } 
/*     */     
/* 405 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {
/* 410 */     switch (id) {
/*     */       case 0:
/* 412 */         this.furnaceBurnTime = value;
/*     */         break;
/*     */       
/*     */       case 1:
/* 416 */         this.currentItemBurnTime = value;
/*     */         break;
/*     */       
/*     */       case 2:
/* 420 */         this.cookTime = value;
/*     */         break;
/*     */       
/*     */       case 3:
/* 424 */         this.totalCookTime = value;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public int getFieldCount() {
/* 429 */     return 4;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 433 */     for (int i = 0; i < this.furnaceItemStacks.length; i++)
/* 434 */       this.furnaceItemStacks[i] = null; 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */