/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ public abstract class Container
/*     */ {
/*  17 */   public List<ItemStack> inventoryItemStacks = Lists.newArrayList();
/*  18 */   public List<Slot> inventorySlots = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public int windowId;
/*     */   
/*     */   private short transactionID;
/*     */   
/*  25 */   private int dragMode = -1;
/*     */ 
/*     */   
/*     */   private int dragEvent;
/*     */ 
/*     */   
/*  31 */   private final Set<Slot> dragSlots = Sets.newHashSet();
/*  32 */   protected List<ICrafting> crafters = Lists.newArrayList();
/*  33 */   private Set<EntityPlayer> playerList = Sets.newHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Slot addSlotToContainer(Slot slotIn) {
/*  39 */     slotIn.slotNumber = this.inventorySlots.size();
/*  40 */     this.inventorySlots.add(slotIn);
/*  41 */     this.inventoryItemStacks.add(null);
/*  42 */     return slotIn;
/*     */   }
/*     */   
/*     */   public void onCraftGuiOpened(ICrafting listener) {
/*  46 */     if (this.crafters.contains(listener)) {
/*  47 */       throw new IllegalArgumentException("Listener already listening");
/*     */     }
/*  49 */     this.crafters.add(listener);
/*  50 */     listener.updateCraftingInventory(this, getInventory());
/*  51 */     detectAndSendChanges();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeCraftingFromCrafters(ICrafting listeners) {
/*  59 */     this.crafters.remove(listeners);
/*     */   }
/*     */   
/*     */   public List<ItemStack> getInventory() {
/*  63 */     List<ItemStack> list = Lists.newArrayList();
/*     */     
/*  65 */     for (int i = 0; i < this.inventorySlots.size(); i++) {
/*  66 */       list.add(((Slot)this.inventorySlots.get(i)).getStack());
/*     */     }
/*     */     
/*  69 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/*  76 */     for (int i = 0; i < this.inventorySlots.size(); i++) {
/*  77 */       ItemStack itemstack = ((Slot)this.inventorySlots.get(i)).getStack();
/*  78 */       ItemStack itemstack1 = this.inventoryItemStacks.get(i);
/*     */       
/*  80 */       if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
/*  81 */         itemstack1 = (itemstack == null) ? null : itemstack.copy();
/*  82 */         this.inventoryItemStacks.set(i, itemstack1);
/*     */         
/*  84 */         for (int j = 0; j < this.crafters.size(); j++) {
/*  85 */           ((ICrafting)this.crafters.get(j)).sendSlotContents(this, i, itemstack1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enchantItem(EntityPlayer playerIn, int id) {
/*  95 */     return false;
/*     */   }
/*     */   
/*     */   public Slot getSlotFromInventory(IInventory inv, int slotIn) {
/*  99 */     for (int i = 0; i < this.inventorySlots.size(); i++) {
/* 100 */       Slot slot = this.inventorySlots.get(i);
/*     */       
/* 102 */       if (slot.isHere(inv, slotIn)) {
/* 103 */         return slot;
/*     */       }
/*     */     } 
/*     */     
/* 107 */     return null;
/*     */   }
/*     */   
/*     */   public Slot getSlot(int slotId) {
/* 111 */     return this.inventorySlots.get(slotId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 118 */     Slot slot = this.inventorySlots.get(index);
/* 119 */     return (slot != null) ? slot.getStack() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn) {
/* 126 */     ItemStack itemstack = null;
/* 127 */     InventoryPlayer inventoryplayer = playerIn.inventory;
/*     */     
/* 129 */     if (mode == 5) {
/* 130 */       int i = this.dragEvent;
/* 131 */       this.dragEvent = getDragEvent(clickedButton);
/*     */       
/* 133 */       if ((i != 1 || this.dragEvent != 2) && i != this.dragEvent) {
/* 134 */         resetDrag();
/* 135 */       } else if (inventoryplayer.getItemStack() == null) {
/* 136 */         resetDrag();
/* 137 */       } else if (this.dragEvent == 0) {
/* 138 */         this.dragMode = extractDragMode(clickedButton);
/*     */         
/* 140 */         if (isValidDragMode(this.dragMode, playerIn)) {
/* 141 */           this.dragEvent = 1;
/* 142 */           this.dragSlots.clear();
/*     */         } else {
/* 144 */           resetDrag();
/*     */         } 
/* 146 */       } else if (this.dragEvent == 1) {
/* 147 */         Slot slot = this.inventorySlots.get(slotId);
/*     */         
/* 149 */         if (slot != null && canAddItemToSlot(slot, inventoryplayer.getItemStack(), true) && slot.isItemValid(inventoryplayer.getItemStack()) && (inventoryplayer.getItemStack()).stackSize > this.dragSlots.size() && canDragIntoSlot(slot)) {
/* 150 */           this.dragSlots.add(slot);
/*     */         }
/* 152 */       } else if (this.dragEvent == 2) {
/* 153 */         if (!this.dragSlots.isEmpty()) {
/* 154 */           ItemStack itemstack3 = inventoryplayer.getItemStack().copy();
/* 155 */           int j = (inventoryplayer.getItemStack()).stackSize;
/*     */           
/* 157 */           for (Slot slot1 : this.dragSlots) {
/* 158 */             if (slot1 != null && canAddItemToSlot(slot1, inventoryplayer.getItemStack(), true) && slot1.isItemValid(inventoryplayer.getItemStack()) && (inventoryplayer.getItemStack()).stackSize >= this.dragSlots.size() && canDragIntoSlot(slot1)) {
/* 159 */               ItemStack itemstack1 = itemstack3.copy();
/* 160 */               int k = slot1.getHasStack() ? (slot1.getStack()).stackSize : 0;
/* 161 */               computeStackSize(this.dragSlots, this.dragMode, itemstack1, k);
/*     */               
/* 163 */               if (itemstack1.stackSize > itemstack1.getMaxStackSize()) {
/* 164 */                 itemstack1.stackSize = itemstack1.getMaxStackSize();
/*     */               }
/*     */               
/* 167 */               if (itemstack1.stackSize > slot1.getItemStackLimit(itemstack1)) {
/* 168 */                 itemstack1.stackSize = slot1.getItemStackLimit(itemstack1);
/*     */               }
/*     */               
/* 171 */               j -= itemstack1.stackSize - k;
/* 172 */               slot1.putStack(itemstack1);
/*     */             } 
/*     */           } 
/*     */           
/* 176 */           itemstack3.stackSize = j;
/*     */           
/* 178 */           if (itemstack3.stackSize <= 0) {
/* 179 */             itemstack3 = null;
/*     */           }
/*     */           
/* 182 */           inventoryplayer.setItemStack(itemstack3);
/*     */         } 
/*     */         
/* 185 */         resetDrag();
/*     */       } else {
/* 187 */         resetDrag();
/*     */       } 
/* 189 */     } else if (this.dragEvent != 0) {
/* 190 */       resetDrag();
/* 191 */     } else if ((mode == 0 || mode == 1) && (clickedButton == 0 || clickedButton == 1)) {
/* 192 */       if (slotId == -999) {
/* 193 */         if (inventoryplayer.getItemStack() != null) {
/* 194 */           if (clickedButton == 0) {
/* 195 */             playerIn.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), true);
/* 196 */             inventoryplayer.setItemStack(null);
/*     */           } 
/*     */           
/* 199 */           if (clickedButton == 1) {
/* 200 */             playerIn.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack().splitStack(1), true);
/*     */             
/* 202 */             if ((inventoryplayer.getItemStack()).stackSize == 0) {
/* 203 */               inventoryplayer.setItemStack(null);
/*     */             }
/*     */           } 
/*     */         } 
/* 207 */       } else if (mode == 1) {
/* 208 */         if (slotId < 0) {
/* 209 */           return null;
/*     */         }
/*     */         
/* 212 */         Slot slot6 = this.inventorySlots.get(slotId);
/*     */         
/* 214 */         if (slot6 != null && slot6.canTakeStack(playerIn)) {
/* 215 */           ItemStack itemstack8 = transferStackInSlot(playerIn, slotId);
/*     */           
/* 217 */           if (itemstack8 != null) {
/* 218 */             Item item = itemstack8.getItem();
/* 219 */             itemstack = itemstack8.copy();
/*     */             
/* 221 */             if (slot6.getStack() != null && slot6.getStack().getItem() == item) {
/* 222 */               retrySlotClick(slotId, clickedButton, true, playerIn);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } else {
/* 227 */         if (slotId < 0) {
/* 228 */           return null;
/*     */         }
/*     */         
/* 231 */         Slot slot7 = this.inventorySlots.get(slotId);
/*     */         
/* 233 */         if (slot7 != null) {
/* 234 */           ItemStack itemstack9 = slot7.getStack();
/* 235 */           ItemStack itemstack10 = inventoryplayer.getItemStack();
/*     */           
/* 237 */           if (itemstack9 != null) {
/* 238 */             itemstack = itemstack9.copy();
/*     */           }
/*     */           
/* 241 */           if (itemstack9 == null) {
/* 242 */             if (itemstack10 != null && slot7.isItemValid(itemstack10)) {
/* 243 */               int k2 = (clickedButton == 0) ? itemstack10.stackSize : 1;
/*     */               
/* 245 */               if (k2 > slot7.getItemStackLimit(itemstack10)) {
/* 246 */                 k2 = slot7.getItemStackLimit(itemstack10);
/*     */               }
/*     */               
/* 249 */               if (itemstack10.stackSize >= k2) {
/* 250 */                 slot7.putStack(itemstack10.splitStack(k2));
/*     */               }
/*     */               
/* 253 */               if (itemstack10.stackSize == 0) {
/* 254 */                 inventoryplayer.setItemStack(null);
/*     */               }
/*     */             } 
/* 257 */           } else if (slot7.canTakeStack(playerIn)) {
/* 258 */             if (itemstack10 == null) {
/* 259 */               int j2 = (clickedButton == 0) ? itemstack9.stackSize : ((itemstack9.stackSize + 1) / 2);
/* 260 */               ItemStack itemstack12 = slot7.decrStackSize(j2);
/* 261 */               inventoryplayer.setItemStack(itemstack12);
/*     */               
/* 263 */               if (itemstack9.stackSize == 0) {
/* 264 */                 slot7.putStack(null);
/*     */               }
/*     */               
/* 267 */               slot7.onPickupFromSlot(playerIn, inventoryplayer.getItemStack());
/* 268 */             } else if (slot7.isItemValid(itemstack10)) {
/* 269 */               if (itemstack9.getItem() == itemstack10.getItem() && itemstack9.getMetadata() == itemstack10.getMetadata() && ItemStack.areItemStackTagsEqual(itemstack9, itemstack10)) {
/* 270 */                 int i2 = (clickedButton == 0) ? itemstack10.stackSize : 1;
/*     */                 
/* 272 */                 if (i2 > slot7.getItemStackLimit(itemstack10) - itemstack9.stackSize) {
/* 273 */                   i2 = slot7.getItemStackLimit(itemstack10) - itemstack9.stackSize;
/*     */                 }
/*     */                 
/* 276 */                 if (i2 > itemstack10.getMaxStackSize() - itemstack9.stackSize) {
/* 277 */                   i2 = itemstack10.getMaxStackSize() - itemstack9.stackSize;
/*     */                 }
/*     */                 
/* 280 */                 itemstack10.splitStack(i2);
/*     */                 
/* 282 */                 if (itemstack10.stackSize == 0) {
/* 283 */                   inventoryplayer.setItemStack(null);
/*     */                 }
/*     */                 
/* 286 */                 itemstack9.stackSize += i2;
/* 287 */               } else if (itemstack10.stackSize <= slot7.getItemStackLimit(itemstack10)) {
/* 288 */                 slot7.putStack(itemstack10);
/* 289 */                 inventoryplayer.setItemStack(itemstack9);
/*     */               } 
/* 291 */             } else if (itemstack9.getItem() == itemstack10.getItem() && itemstack10.getMaxStackSize() > 1 && (!itemstack9.getHasSubtypes() || itemstack9.getMetadata() == itemstack10.getMetadata()) && ItemStack.areItemStackTagsEqual(itemstack9, itemstack10)) {
/* 292 */               int l1 = itemstack9.stackSize;
/*     */               
/* 294 */               if (l1 > 0 && l1 + itemstack10.stackSize <= itemstack10.getMaxStackSize()) {
/* 295 */                 itemstack10.stackSize += l1;
/* 296 */                 itemstack9 = slot7.decrStackSize(l1);
/*     */                 
/* 298 */                 if (itemstack9.stackSize == 0) {
/* 299 */                   slot7.putStack(null);
/*     */                 }
/*     */                 
/* 302 */                 slot7.onPickupFromSlot(playerIn, inventoryplayer.getItemStack());
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 307 */           slot7.onSlotChanged();
/*     */         } 
/*     */       } 
/* 310 */     } else if (mode == 2 && clickedButton >= 0 && clickedButton < 9) {
/* 311 */       Slot slot5 = this.inventorySlots.get(slotId);
/*     */       
/* 313 */       if (slot5.canTakeStack(playerIn)) {
/* 314 */         int i; ItemStack itemstack7 = inventoryplayer.getStackInSlot(clickedButton);
/* 315 */         boolean flag = !(itemstack7 != null && (slot5.inventory != inventoryplayer || !slot5.isItemValid(itemstack7)));
/* 316 */         int k1 = -1;
/*     */         
/* 318 */         if (!flag) {
/* 319 */           k1 = inventoryplayer.getFirstEmptyStack();
/* 320 */           i = flag | ((k1 > -1) ? 1 : 0);
/*     */         } 
/*     */         
/* 323 */         if (slot5.getHasStack() && i != 0) {
/* 324 */           ItemStack itemstack11 = slot5.getStack();
/* 325 */           inventoryplayer.setInventorySlotContents(clickedButton, itemstack11.copy());
/*     */           
/* 327 */           if ((slot5.inventory != inventoryplayer || !slot5.isItemValid(itemstack7)) && itemstack7 != null) {
/* 328 */             if (k1 > -1) {
/* 329 */               inventoryplayer.addItemStackToInventory(itemstack7);
/* 330 */               slot5.decrStackSize(itemstack11.stackSize);
/* 331 */               slot5.putStack(null);
/* 332 */               slot5.onPickupFromSlot(playerIn, itemstack11);
/*     */             } 
/*     */           } else {
/* 335 */             slot5.decrStackSize(itemstack11.stackSize);
/* 336 */             slot5.putStack(itemstack7);
/* 337 */             slot5.onPickupFromSlot(playerIn, itemstack11);
/*     */           } 
/* 339 */         } else if (!slot5.getHasStack() && itemstack7 != null && slot5.isItemValid(itemstack7)) {
/* 340 */           inventoryplayer.setInventorySlotContents(clickedButton, null);
/* 341 */           slot5.putStack(itemstack7);
/*     */         } 
/*     */       } 
/* 344 */     } else if (mode == 3 && playerIn.capabilities.isCreativeMode && inventoryplayer.getItemStack() == null && slotId >= 0) {
/* 345 */       Slot slot4 = this.inventorySlots.get(slotId);
/*     */       
/* 347 */       if (slot4 != null && slot4.getHasStack()) {
/* 348 */         ItemStack itemstack6 = slot4.getStack().copy();
/* 349 */         itemstack6.stackSize = itemstack6.getMaxStackSize();
/* 350 */         inventoryplayer.setItemStack(itemstack6);
/*     */       } 
/* 352 */     } else if (mode == 4 && inventoryplayer.getItemStack() == null && slotId >= 0) {
/* 353 */       Slot slot3 = this.inventorySlots.get(slotId);
/*     */       
/* 355 */       if (slot3 != null && slot3.getHasStack() && slot3.canTakeStack(playerIn)) {
/* 356 */         ItemStack itemstack5 = slot3.decrStackSize((clickedButton == 0) ? 1 : (slot3.getStack()).stackSize);
/* 357 */         slot3.onPickupFromSlot(playerIn, itemstack5);
/* 358 */         playerIn.dropPlayerItemWithRandomChoice(itemstack5, true);
/*     */       } 
/* 360 */     } else if (mode == 6 && slotId >= 0) {
/* 361 */       Slot slot2 = this.inventorySlots.get(slotId);
/* 362 */       ItemStack itemstack4 = inventoryplayer.getItemStack();
/*     */       
/* 364 */       if (itemstack4 != null && (slot2 == null || !slot2.getHasStack() || !slot2.canTakeStack(playerIn))) {
/* 365 */         int i1 = (clickedButton == 0) ? 0 : (this.inventorySlots.size() - 1);
/* 366 */         int j1 = (clickedButton == 0) ? 1 : -1;
/*     */         
/* 368 */         for (int l2 = 0; l2 < 2; l2++) {
/* 369 */           for (int i3 = i1; i3 >= 0 && i3 < this.inventorySlots.size() && itemstack4.stackSize < itemstack4.getMaxStackSize(); i3 += j1) {
/* 370 */             Slot slot8 = this.inventorySlots.get(i3);
/*     */             
/* 372 */             if (slot8.getHasStack() && canAddItemToSlot(slot8, itemstack4, true) && slot8.canTakeStack(playerIn) && canMergeSlot(itemstack4, slot8) && (l2 != 0 || (slot8.getStack()).stackSize != slot8.getStack().getMaxStackSize())) {
/* 373 */               int l = Math.min(itemstack4.getMaxStackSize() - itemstack4.stackSize, (slot8.getStack()).stackSize);
/* 374 */               ItemStack itemstack2 = slot8.decrStackSize(l);
/* 375 */               itemstack4.stackSize += l;
/*     */               
/* 377 */               if (itemstack2.stackSize <= 0) {
/* 378 */                 slot8.putStack(null);
/*     */               }
/*     */               
/* 381 */               slot8.onPickupFromSlot(playerIn, itemstack2);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 387 */       detectAndSendChanges();
/*     */     } 
/*     */     
/* 390 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
/* 398 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void retrySlotClick(int slotId, int clickedButton, boolean mode, EntityPlayer playerIn) {
/* 405 */     slotClick(slotId, clickedButton, 1, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 412 */     InventoryPlayer inventoryplayer = playerIn.inventory;
/*     */     
/* 414 */     if (inventoryplayer.getItemStack() != null) {
/* 415 */       playerIn.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), false);
/* 416 */       inventoryplayer.setItemStack(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/* 424 */     detectAndSendChanges();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putStackInSlot(int slotID, ItemStack stack) {
/* 431 */     getSlot(slotID).putStack(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putStacksInSlots(ItemStack[] p_75131_1_) {
/* 438 */     for (int i = 0; i < p_75131_1_.length; i++) {
/* 439 */       getSlot(i).putStack(p_75131_1_[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public short getNextTransactionID(InventoryPlayer p_75136_1_) {
/* 450 */     this.transactionID = (short)(this.transactionID + 1);
/* 451 */     return this.transactionID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanCraft(EntityPlayer p_75129_1_) {
/* 458 */     return !this.playerList.contains(p_75129_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCanCraft(EntityPlayer p_75128_1_, boolean p_75128_2_) {
/* 465 */     if (p_75128_2_) {
/* 466 */       this.playerList.remove(p_75128_1_);
/*     */     } else {
/* 468 */       this.playerList.add(p_75128_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean canInteractWith(EntityPlayer paramEntityPlayer);
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
/* 480 */     boolean flag = false;
/* 481 */     int i = startIndex;
/*     */     
/* 483 */     if (reverseDirection) {
/* 484 */       i = endIndex - 1;
/*     */     }
/*     */     
/* 487 */     if (stack.isStackable()) {
/* 488 */       while (stack.stackSize > 0 && ((!reverseDirection && i < endIndex) || (reverseDirection && i >= startIndex))) {
/* 489 */         Slot slot = this.inventorySlots.get(i);
/* 490 */         ItemStack itemstack = slot.getStack();
/*     */         
/* 492 */         if (itemstack != null && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack)) {
/* 493 */           int j = itemstack.stackSize + stack.stackSize;
/*     */           
/* 495 */           if (j <= stack.getMaxStackSize()) {
/* 496 */             stack.stackSize = 0;
/* 497 */             itemstack.stackSize = j;
/* 498 */             slot.onSlotChanged();
/* 499 */             flag = true;
/* 500 */           } else if (itemstack.stackSize < stack.getMaxStackSize()) {
/* 501 */             stack.stackSize -= stack.getMaxStackSize() - itemstack.stackSize;
/* 502 */             itemstack.stackSize = stack.getMaxStackSize();
/* 503 */             slot.onSlotChanged();
/* 504 */             flag = true;
/*     */           } 
/*     */         } 
/*     */         
/* 508 */         if (reverseDirection) {
/* 509 */           i--; continue;
/*     */         } 
/* 511 */         i++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 516 */     if (stack.stackSize > 0) {
/* 517 */       if (reverseDirection) {
/* 518 */         i = endIndex - 1;
/*     */       } else {
/* 520 */         i = startIndex;
/*     */       } 
/*     */       
/* 523 */       while ((!reverseDirection && i < endIndex) || (reverseDirection && i >= startIndex)) {
/* 524 */         Slot slot1 = this.inventorySlots.get(i);
/* 525 */         ItemStack itemstack1 = slot1.getStack();
/*     */         
/* 527 */         if (itemstack1 == null) {
/* 528 */           slot1.putStack(stack.copy());
/* 529 */           slot1.onSlotChanged();
/* 530 */           stack.stackSize = 0;
/* 531 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/* 535 */         if (reverseDirection) {
/* 536 */           i--; continue;
/*     */         } 
/* 538 */         i++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 543 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int extractDragMode(int p_94529_0_) {
/* 550 */     return p_94529_0_ >> 2 & 0x3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDragEvent(int p_94532_0_) {
/* 557 */     return p_94532_0_ & 0x3;
/*     */   }
/*     */   
/*     */   public static int func_94534_d(int p_94534_0_, int p_94534_1_) {
/* 561 */     return p_94534_0_ & 0x3 | (p_94534_1_ & 0x3) << 2;
/*     */   }
/*     */   
/*     */   public static boolean isValidDragMode(int dragModeIn, EntityPlayer player) {
/* 565 */     return (dragModeIn == 0) ? true : ((dragModeIn == 1) ? true : ((dragModeIn == 2 && player.capabilities.isCreativeMode)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetDrag() {
/* 572 */     this.dragEvent = 0;
/* 573 */     this.dragSlots.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canAddItemToSlot(Slot slotIn, ItemStack stack, boolean stackSizeMatters) {
/*     */     int i;
/* 580 */     boolean flag = !(slotIn != null && slotIn.getHasStack());
/*     */     
/* 582 */     if (slotIn != null && slotIn.getHasStack() && stack != null && stack.isItemEqual(slotIn.getStack()) && ItemStack.areItemStackTagsEqual(slotIn.getStack(), stack)) {
/* 583 */       i = flag | (((slotIn.getStack()).stackSize + (stackSizeMatters ? 0 : stack.stackSize) <= stack.getMaxStackSize()) ? 1 : 0);
/*     */     }
/*     */     
/* 586 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void computeStackSize(Set<Slot> p_94525_0_, int p_94525_1_, ItemStack p_94525_2_, int p_94525_3_) {
/* 594 */     switch (p_94525_1_) {
/*     */       case 0:
/* 596 */         p_94525_2_.stackSize = MathHelper.floor_float(p_94525_2_.stackSize / p_94525_0_.size());
/*     */         break;
/*     */       
/*     */       case 1:
/* 600 */         p_94525_2_.stackSize = 1;
/*     */         break;
/*     */       
/*     */       case 2:
/* 604 */         p_94525_2_.stackSize = p_94525_2_.getItem().getItemStackLimit(); break;
/* 605 */     }  p_94525_2_.stackSize += 
/*     */       
/* 607 */       p_94525_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDragIntoSlot(Slot p_94531_1_) {
/* 615 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calcRedstone(TileEntity te) {
/* 622 */     return (te instanceof IInventory) ? calcRedstoneFromInventory((IInventory)te) : 0;
/*     */   }
/*     */   
/*     */   public static int calcRedstoneFromInventory(IInventory inv) {
/* 626 */     if (inv == null) {
/* 627 */       return 0;
/*     */     }
/* 629 */     int i = 0;
/* 630 */     float f = 0.0F;
/*     */     
/* 632 */     for (int j = 0; j < inv.getSizeInventory(); j++) {
/* 633 */       ItemStack itemstack = inv.getStackInSlot(j);
/*     */       
/* 635 */       if (itemstack != null) {
/* 636 */         f += itemstack.stackSize / Math.min(inv.getInventoryStackLimit(), itemstack.getMaxStackSize());
/* 637 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 641 */     f /= inv.getSizeInventory();
/* 642 */     return MathHelper.floor_float(f * 14.0F) + ((i > 0) ? 1 : 0);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\Container.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */