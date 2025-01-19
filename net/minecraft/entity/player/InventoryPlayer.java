/*     */ package net.minecraft.entity.player;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ReportedException;
/*     */ 
/*     */ public class InventoryPlayer
/*     */   implements IInventory
/*     */ {
/*  24 */   public ItemStack[] mainInventory = new ItemStack[36];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  29 */   public ItemStack[] armorInventory = new ItemStack[4];
/*     */ 
/*     */ 
/*     */   
/*     */   public int currentItem;
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayer player;
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemStack itemStack;
/*     */ 
/*     */   
/*     */   public boolean inventoryChanged;
/*     */ 
/*     */ 
/*     */   
/*     */   public InventoryPlayer(EntityPlayer playerIn) {
/*  49 */     this.player = playerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCurrentItem() {
/*  56 */     return (this.currentItem < 9 && this.currentItem >= 0) ? this.mainInventory[this.currentItem] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getHotbarSize() {
/*  63 */     return 9;
/*     */   }
/*     */   
/*     */   private int getInventorySlotContainItem(Item itemIn) {
/*  67 */     for (int i = 0; i < this.mainInventory.length; i++) {
/*  68 */       if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == itemIn) {
/*  69 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  73 */     return -1;
/*     */   }
/*     */   
/*     */   private int getInventorySlotContainItemAndDamage(Item itemIn, int metadataIn) {
/*  77 */     for (int i = 0; i < this.mainInventory.length; i++) {
/*  78 */       if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == itemIn && this.mainInventory[i].getMetadata() == metadataIn) {
/*  79 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  83 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int storeItemStack(ItemStack itemStackIn) {
/*  90 */     for (int i = 0; i < this.mainInventory.length; i++) {
/*  91 */       if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == itemStackIn.getItem() && this.mainInventory[i].isStackable() && (this.mainInventory[i]).stackSize < this.mainInventory[i].getMaxStackSize() && (this.mainInventory[i]).stackSize < getInventoryStackLimit() && (!this.mainInventory[i].getHasSubtypes() || this.mainInventory[i].getMetadata() == itemStackIn.getMetadata()) && ItemStack.areItemStackTagsEqual(this.mainInventory[i], itemStackIn)) {
/*  92 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFirstEmptyStack() {
/* 103 */     for (int i = 0; i < this.mainInventory.length; i++) {
/* 104 */       if (this.mainInventory[i] == null) {
/* 105 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 109 */     return -1;
/*     */   }
/*     */   
/*     */   public void setCurrentItem(Item itemIn, int metadataIn, boolean isMetaSpecific, boolean p_146030_4_) {
/* 113 */     ItemStack itemstack = getCurrentItem();
/* 114 */     int i = isMetaSpecific ? getInventorySlotContainItemAndDamage(itemIn, metadataIn) : getInventorySlotContainItem(itemIn);
/*     */     
/* 116 */     if (i >= 0 && i < 9) {
/* 117 */       this.currentItem = i;
/* 118 */     } else if (p_146030_4_ && itemIn != null) {
/* 119 */       int j = getFirstEmptyStack();
/*     */       
/* 121 */       if (j >= 0 && j < 9) {
/* 122 */         this.currentItem = j;
/*     */       }
/*     */       
/* 125 */       if (itemstack == null || !itemstack.isItemEnchantable() || getInventorySlotContainItemAndDamage(itemstack.getItem(), itemstack.getItemDamage()) != this.currentItem) {
/* 126 */         int l, k = getInventorySlotContainItemAndDamage(itemIn, metadataIn);
/*     */ 
/*     */         
/* 129 */         if (k >= 0) {
/* 130 */           l = (this.mainInventory[k]).stackSize;
/* 131 */           this.mainInventory[k] = this.mainInventory[this.currentItem];
/*     */         } else {
/* 133 */           l = 1;
/*     */         } 
/*     */         
/* 136 */         this.mainInventory[this.currentItem] = new ItemStack(itemIn, l, metadataIn);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changeCurrentItem(int direction) {
/* 148 */     if (direction > 0) {
/* 149 */       direction = 1;
/*     */     }
/*     */     
/* 152 */     if (direction < 0) {
/* 153 */       direction = -1;
/*     */     }
/*     */     
/* 156 */     for (this.currentItem -= direction; this.currentItem < 0; this.currentItem += 9);
/*     */ 
/*     */ 
/*     */     
/* 160 */     while (this.currentItem >= 9) {
/* 161 */       this.currentItem -= 9;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int clearMatchingItems(Item itemIn, int metadataIn, int removeCount, NBTTagCompound itemNBT) {
/* 175 */     int i = 0;
/*     */     
/* 177 */     for (int j = 0; j < this.mainInventory.length; j++) {
/* 178 */       ItemStack itemstack = this.mainInventory[j];
/*     */       
/* 180 */       if (itemstack != null && (itemIn == null || itemstack.getItem() == itemIn) && (metadataIn <= -1 || itemstack.getMetadata() == metadataIn) && (itemNBT == null || NBTUtil.func_181123_a((NBTBase)itemNBT, (NBTBase)itemstack.getTagCompound(), true))) {
/* 181 */         int k = (removeCount <= 0) ? itemstack.stackSize : Math.min(removeCount - i, itemstack.stackSize);
/* 182 */         i += k;
/*     */         
/* 184 */         if (removeCount != 0) {
/* 185 */           (this.mainInventory[j]).stackSize -= k;
/*     */           
/* 187 */           if ((this.mainInventory[j]).stackSize == 0) {
/* 188 */             this.mainInventory[j] = null;
/*     */           }
/*     */           
/* 191 */           if (removeCount > 0 && i >= removeCount) {
/* 192 */             return i;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     for (int l = 0; l < this.armorInventory.length; l++) {
/* 199 */       ItemStack itemstack1 = this.armorInventory[l];
/*     */       
/* 201 */       if (itemstack1 != null && (itemIn == null || itemstack1.getItem() == itemIn) && (metadataIn <= -1 || itemstack1.getMetadata() == metadataIn) && (itemNBT == null || NBTUtil.func_181123_a((NBTBase)itemNBT, (NBTBase)itemstack1.getTagCompound(), false))) {
/* 202 */         int j1 = (removeCount <= 0) ? itemstack1.stackSize : Math.min(removeCount - i, itemstack1.stackSize);
/* 203 */         i += j1;
/*     */         
/* 205 */         if (removeCount != 0) {
/* 206 */           (this.armorInventory[l]).stackSize -= j1;
/*     */           
/* 208 */           if ((this.armorInventory[l]).stackSize == 0) {
/* 209 */             this.armorInventory[l] = null;
/*     */           }
/*     */           
/* 212 */           if (removeCount > 0 && i >= removeCount) {
/* 213 */             return i;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 219 */     if (this.itemStack != null) {
/* 220 */       if (itemIn != null && this.itemStack.getItem() != itemIn) {
/* 221 */         return i;
/*     */       }
/*     */       
/* 224 */       if (metadataIn > -1 && this.itemStack.getMetadata() != metadataIn) {
/* 225 */         return i;
/*     */       }
/*     */       
/* 228 */       if (itemNBT != null && !NBTUtil.func_181123_a((NBTBase)itemNBT, (NBTBase)this.itemStack.getTagCompound(), false)) {
/* 229 */         return i;
/*     */       }
/*     */       
/* 232 */       int i1 = (removeCount <= 0) ? this.itemStack.stackSize : Math.min(removeCount - i, this.itemStack.stackSize);
/* 233 */       i += i1;
/*     */       
/* 235 */       if (removeCount != 0) {
/* 236 */         this.itemStack.stackSize -= i1;
/*     */         
/* 238 */         if (this.itemStack.stackSize == 0) {
/* 239 */           this.itemStack = null;
/*     */         }
/*     */         
/* 242 */         if (removeCount > 0 && i >= removeCount) {
/* 243 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int storePartialItemStack(ItemStack itemStackIn) {
/* 256 */     Item item = itemStackIn.getItem();
/* 257 */     int i = itemStackIn.stackSize;
/* 258 */     int j = storeItemStack(itemStackIn);
/*     */     
/* 260 */     if (j < 0) {
/* 261 */       j = getFirstEmptyStack();
/*     */     }
/*     */     
/* 264 */     if (j < 0) {
/* 265 */       return i;
/*     */     }
/* 267 */     if (this.mainInventory[j] == null) {
/* 268 */       this.mainInventory[j] = new ItemStack(item, 0, itemStackIn.getMetadata());
/*     */       
/* 270 */       if (itemStackIn.hasTagCompound()) {
/* 271 */         this.mainInventory[j].setTagCompound((NBTTagCompound)itemStackIn.getTagCompound().copy());
/*     */       }
/*     */     } 
/*     */     
/* 275 */     int k = i;
/*     */     
/* 277 */     if (i > this.mainInventory[j].getMaxStackSize() - (this.mainInventory[j]).stackSize) {
/* 278 */       k = this.mainInventory[j].getMaxStackSize() - (this.mainInventory[j]).stackSize;
/*     */     }
/*     */     
/* 281 */     if (k > getInventoryStackLimit() - (this.mainInventory[j]).stackSize) {
/* 282 */       k = getInventoryStackLimit() - (this.mainInventory[j]).stackSize;
/*     */     }
/*     */     
/* 285 */     if (k == 0) {
/* 286 */       return i;
/*     */     }
/* 288 */     i -= k;
/* 289 */     (this.mainInventory[j]).stackSize += k;
/* 290 */     (this.mainInventory[j]).animationsToGo = 5;
/* 291 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decrementAnimations() {
/* 301 */     for (int i = 0; i < this.mainInventory.length; i++) {
/* 302 */       if (this.mainInventory[i] != null) {
/* 303 */         this.mainInventory[i].updateAnimation(this.player.worldObj, (Entity)this.player, i, (this.currentItem == i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean consumeInventoryItem(Item itemIn) {
/* 312 */     int i = getInventorySlotContainItem(itemIn);
/*     */     
/* 314 */     if (i < 0) {
/* 315 */       return false;
/*     */     }
/* 317 */     if (--(this.mainInventory[i]).stackSize <= 0) {
/* 318 */       this.mainInventory[i] = null;
/*     */     }
/*     */     
/* 321 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasItem(Item itemIn) {
/* 329 */     int i = getInventorySlotContainItem(itemIn);
/* 330 */     return (i >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addItemStackToInventory(final ItemStack itemStackIn) {
/* 337 */     if (itemStackIn != null && itemStackIn.stackSize != 0 && itemStackIn.getItem() != null) {
/*     */       try {
/* 339 */         int i; if (itemStackIn.isItemDamaged()) {
/* 340 */           int j = getFirstEmptyStack();
/*     */           
/* 342 */           if (j >= 0) {
/* 343 */             this.mainInventory[j] = ItemStack.copyItemStack(itemStackIn);
/* 344 */             (this.mainInventory[j]).animationsToGo = 5;
/* 345 */             itemStackIn.stackSize = 0;
/* 346 */             return true;
/* 347 */           }  if (this.player.capabilities.isCreativeMode) {
/* 348 */             itemStackIn.stackSize = 0;
/* 349 */             return true;
/*     */           } 
/* 351 */           return false;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         do {
/* 357 */           i = itemStackIn.stackSize;
/* 358 */           itemStackIn.stackSize = storePartialItemStack(itemStackIn);
/*     */         }
/* 360 */         while (itemStackIn.stackSize > 0 && itemStackIn.stackSize < i);
/* 361 */         if (itemStackIn
/*     */ 
/*     */ 
/*     */           
/* 365 */           .stackSize == i && this.player.capabilities.isCreativeMode) {
/* 366 */           itemStackIn.stackSize = 0;
/* 367 */           return true;
/*     */         } 
/* 369 */         return (itemStackIn.stackSize < i);
/*     */       
/*     */       }
/* 372 */       catch (Throwable throwable) {
/* 373 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
/* 374 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
/* 375 */         crashreportcategory.addCrashSection("Item ID", Integer.valueOf(Item.getIdFromItem(itemStackIn.getItem())));
/* 376 */         crashreportcategory.addCrashSection("Item data", Integer.valueOf(itemStackIn.getMetadata()));
/* 377 */         crashreportcategory.addCrashSectionCallable("Item name", new Callable<String>() {
/*     */               public String call() throws Exception {
/* 379 */                 return itemStackIn.getDisplayName();
/*     */               }
/*     */             });
/* 382 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     }
/* 385 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 393 */     ItemStack[] aitemstack = this.mainInventory;
/*     */     
/* 395 */     if (index >= this.mainInventory.length) {
/* 396 */       aitemstack = this.armorInventory;
/* 397 */       index -= this.mainInventory.length;
/*     */     } 
/*     */     
/* 400 */     if (aitemstack[index] != null) {
/* 401 */       if ((aitemstack[index]).stackSize <= count) {
/* 402 */         ItemStack itemstack1 = aitemstack[index];
/* 403 */         aitemstack[index] = null;
/* 404 */         return itemstack1;
/*     */       } 
/* 406 */       ItemStack itemstack = aitemstack[index].splitStack(count);
/*     */       
/* 408 */       if ((aitemstack[index]).stackSize == 0) {
/* 409 */         aitemstack[index] = null;
/*     */       }
/*     */       
/* 412 */       return itemstack;
/*     */     } 
/*     */     
/* 415 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 423 */     ItemStack[] aitemstack = this.mainInventory;
/*     */     
/* 425 */     if (index >= this.mainInventory.length) {
/* 426 */       aitemstack = this.armorInventory;
/* 427 */       index -= this.mainInventory.length;
/*     */     } 
/*     */     
/* 430 */     if (aitemstack[index] != null) {
/* 431 */       ItemStack itemstack = aitemstack[index];
/* 432 */       aitemstack[index] = null;
/* 433 */       return itemstack;
/*     */     } 
/* 435 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 443 */     ItemStack[] aitemstack = this.mainInventory;
/*     */     
/* 445 */     if (index >= aitemstack.length) {
/* 446 */       index -= aitemstack.length;
/* 447 */       aitemstack = this.armorInventory;
/*     */     } 
/*     */     
/* 450 */     aitemstack[index] = stack;
/*     */   }
/*     */   
/*     */   public float getStrVsBlock(Block blockIn) {
/* 454 */     float f = 1.0F;
/*     */     
/* 456 */     if (this.mainInventory[this.currentItem] != null) {
/* 457 */       f *= this.mainInventory[this.currentItem].getStrVsBlock(blockIn);
/*     */     }
/*     */     
/* 460 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagList writeToNBT(NBTTagList nbtTagListIn) {
/* 470 */     for (int i = 0; i < this.mainInventory.length; i++) {
/* 471 */       if (this.mainInventory[i] != null) {
/* 472 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 473 */         nbttagcompound.setByte("Slot", (byte)i);
/* 474 */         this.mainInventory[i].writeToNBT(nbttagcompound);
/* 475 */         nbtTagListIn.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 479 */     for (int j = 0; j < this.armorInventory.length; j++) {
/* 480 */       if (this.armorInventory[j] != null) {
/* 481 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 482 */         nbttagcompound1.setByte("Slot", (byte)(j + 100));
/* 483 */         this.armorInventory[j].writeToNBT(nbttagcompound1);
/* 484 */         nbtTagListIn.appendTag((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 488 */     return nbtTagListIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagList nbtTagListIn) {
/* 497 */     this.mainInventory = new ItemStack[36];
/* 498 */     this.armorInventory = new ItemStack[4];
/*     */     
/* 500 */     for (int i = 0; i < nbtTagListIn.tagCount(); i++) {
/* 501 */       NBTTagCompound nbttagcompound = nbtTagListIn.getCompoundTagAt(i);
/* 502 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/* 503 */       ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       
/* 505 */       if (itemstack != null) {
/* 506 */         if (j >= 0 && j < this.mainInventory.length) {
/* 507 */           this.mainInventory[j] = itemstack;
/*     */         }
/*     */         
/* 510 */         if (j >= 100 && j < this.armorInventory.length + 100) {
/* 511 */           this.armorInventory[j - 100] = itemstack;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 521 */     return this.mainInventory.length + 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 528 */     ItemStack[] aitemstack = this.mainInventory;
/*     */     
/* 530 */     if (index >= aitemstack.length) {
/* 531 */       index -= aitemstack.length;
/* 532 */       aitemstack = this.armorInventory;
/*     */     } 
/*     */     
/* 535 */     return aitemstack[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 542 */     return "container.inventory";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 549 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 556 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getName()) : (IChatComponent)new ChatComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 563 */     return 64;
/*     */   }
/*     */   
/*     */   public boolean canHeldItemHarvest(Block blockIn) {
/* 567 */     if (blockIn.getMaterial().isToolNotRequired()) {
/* 568 */       return true;
/*     */     }
/* 570 */     ItemStack itemstack = getStackInSlot(this.currentItem);
/* 571 */     return (itemstack != null) ? itemstack.canHarvestBlock(blockIn) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack armorItemInSlot(int slotIn) {
/* 581 */     return this.armorInventory[slotIn];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalArmorValue() {
/* 588 */     int i = 0;
/*     */     
/* 590 */     for (int j = 0; j < this.armorInventory.length; j++) {
/* 591 */       if (this.armorInventory[j] != null && this.armorInventory[j].getItem() instanceof ItemArmor) {
/* 592 */         int k = ((ItemArmor)this.armorInventory[j].getItem()).damageReduceAmount;
/* 593 */         i += k;
/*     */       } 
/*     */     } 
/*     */     
/* 597 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageArmor(float damage) {
/* 604 */     damage /= 4.0F;
/*     */     
/* 606 */     if (damage < 1.0F) {
/* 607 */       damage = 1.0F;
/*     */     }
/*     */     
/* 610 */     for (int i = 0; i < this.armorInventory.length; i++) {
/* 611 */       if (this.armorInventory[i] != null && this.armorInventory[i].getItem() instanceof ItemArmor) {
/* 612 */         this.armorInventory[i].damageItem((int)damage, this.player);
/*     */         
/* 614 */         if ((this.armorInventory[i]).stackSize == 0) {
/* 615 */           this.armorInventory[i] = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropAllItems() {
/* 625 */     for (int i = 0; i < this.mainInventory.length; i++) {
/* 626 */       if (this.mainInventory[i] != null) {
/* 627 */         this.player.dropItem(this.mainInventory[i], true, false);
/* 628 */         this.mainInventory[i] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 632 */     for (int j = 0; j < this.armorInventory.length; j++) {
/* 633 */       if (this.armorInventory[j] != null) {
/* 634 */         this.player.dropItem(this.armorInventory[j], true, false);
/* 635 */         this.armorInventory[j] = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 645 */     this.inventoryChanged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemStack(ItemStack itemStackIn) {
/* 652 */     this.itemStack = itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack() {
/* 659 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 666 */     return this.player.isDead ? false : ((player.getDistanceSqToEntity((Entity)this.player) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasItemStack(ItemStack itemStackIn) {
/* 673 */     for (int i = 0; i < this.armorInventory.length; i++) {
/* 674 */       if (this.armorInventory[i] != null && this.armorInventory[i].isItemEqual(itemStackIn)) {
/* 675 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 679 */     for (int j = 0; j < this.mainInventory.length; j++) {
/* 680 */       if (this.mainInventory[j] != null && this.mainInventory[j].isItemEqual(itemStackIn)) {
/* 681 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 685 */     return false;
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
/* 698 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyInventory(InventoryPlayer playerInventory) {
/* 705 */     for (int i = 0; i < this.mainInventory.length; i++) {
/* 706 */       this.mainInventory[i] = ItemStack.copyItemStack(playerInventory.mainInventory[i]);
/*     */     }
/*     */     
/* 709 */     for (int j = 0; j < this.armorInventory.length; j++) {
/* 710 */       this.armorInventory[j] = ItemStack.copyItemStack(playerInventory.armorInventory[j]);
/*     */     }
/*     */     
/* 713 */     this.currentItem = playerInventory.currentItem;
/*     */   }
/*     */   
/*     */   public int getField(int id) {
/* 717 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount() {
/* 724 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 728 */     for (int i = 0; i < this.mainInventory.length; i++) {
/* 729 */       this.mainInventory[i] = null;
/*     */     }
/*     */     
/* 732 */     for (int j = 0; j < this.armorInventory.length; j++)
/* 733 */       this.armorInventory[j] = null; 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\player\InventoryPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */