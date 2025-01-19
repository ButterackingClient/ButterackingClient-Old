/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
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
/*     */ public class ContainerEnchantment
/*     */   extends Container
/*     */ {
/*     */   public IInventory tableInventory;
/*     */   private World worldPointer;
/*     */   private BlockPos position;
/*     */   private Random rand;
/*     */   public int xpSeed;
/*     */   public int[] enchantLevels;
/*     */   public int[] enchantmentIds;
/*     */   
/*     */   public ContainerEnchantment(InventoryPlayer playerInv, World worldIn) {
/*  39 */     this(playerInv, worldIn, BlockPos.ORIGIN);
/*     */   }
/*     */   
/*     */   public ContainerEnchantment(InventoryPlayer playerInv, World worldIn, BlockPos pos) {
/*  43 */     this.tableInventory = new InventoryBasic("Enchant", true, 2) {
/*     */         public int getInventoryStackLimit() {
/*  45 */           return 64;
/*     */         }
/*     */         
/*     */         public void markDirty() {
/*  49 */           super.markDirty();
/*  50 */           ContainerEnchantment.this.onCraftMatrixChanged(this);
/*     */         }
/*     */       };
/*  53 */     this.rand = new Random();
/*  54 */     this.enchantLevels = new int[3];
/*  55 */     this.enchantmentIds = new int[] { -1, -1, -1 };
/*  56 */     this.worldPointer = worldIn;
/*  57 */     this.position = pos;
/*  58 */     this.xpSeed = playerInv.player.getXPSeed();
/*  59 */     addSlotToContainer(new Slot(this.tableInventory, 0, 15, 47) {
/*     */           public boolean isItemValid(ItemStack stack) {
/*  61 */             return true;
/*     */           }
/*     */           
/*     */           public int getSlotStackLimit() {
/*  65 */             return 1;
/*     */           }
/*     */         });
/*  68 */     addSlotToContainer(new Slot(this.tableInventory, 1, 35, 47) {
/*     */           public boolean isItemValid(ItemStack stack) {
/*  70 */             return (stack.getItem() == Items.dye && EnumDyeColor.byDyeDamage(stack.getMetadata()) == EnumDyeColor.BLUE);
/*     */           }
/*     */         });
/*     */     
/*  74 */     for (int i = 0; i < 3; i++) {
/*  75 */       for (int j = 0; j < 9; j++) {
/*  76 */         addSlotToContainer(new Slot((IInventory)playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  80 */     for (int k = 0; k < 9; k++) {
/*  81 */       addSlotToContainer(new Slot((IInventory)playerInv, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public void onCraftGuiOpened(ICrafting listener) {
/*  86 */     super.onCraftGuiOpened(listener);
/*  87 */     listener.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
/*  88 */     listener.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
/*  89 */     listener.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
/*  90 */     listener.sendProgressBarUpdate(this, 3, this.xpSeed & 0xFFFFFFF0);
/*  91 */     listener.sendProgressBarUpdate(this, 4, this.enchantmentIds[0]);
/*  92 */     listener.sendProgressBarUpdate(this, 5, this.enchantmentIds[1]);
/*  93 */     listener.sendProgressBarUpdate(this, 6, this.enchantmentIds[2]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/* 100 */     super.detectAndSendChanges();
/*     */     
/* 102 */     for (int i = 0; i < this.crafters.size(); i++) {
/* 103 */       ICrafting icrafting = this.crafters.get(i);
/* 104 */       icrafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
/* 105 */       icrafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
/* 106 */       icrafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
/* 107 */       icrafting.sendProgressBarUpdate(this, 3, this.xpSeed & 0xFFFFFFF0);
/* 108 */       icrafting.sendProgressBarUpdate(this, 4, this.enchantmentIds[0]);
/* 109 */       icrafting.sendProgressBarUpdate(this, 5, this.enchantmentIds[1]);
/* 110 */       icrafting.sendProgressBarUpdate(this, 6, this.enchantmentIds[2]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/* 115 */     if (id >= 0 && id <= 2) {
/* 116 */       this.enchantLevels[id] = data;
/* 117 */     } else if (id == 3) {
/* 118 */       this.xpSeed = data;
/* 119 */     } else if (id >= 4 && id <= 6) {
/* 120 */       this.enchantmentIds[id - 4] = data;
/*     */     } else {
/* 122 */       super.updateProgressBar(id, data);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/* 130 */     if (inventoryIn == this.tableInventory) {
/* 131 */       ItemStack itemstack = inventoryIn.getStackInSlot(0);
/*     */       
/* 133 */       if (itemstack != null && itemstack.isItemEnchantable()) {
/* 134 */         if (!this.worldPointer.isRemote) {
/* 135 */           int l = 0;
/*     */           
/* 137 */           for (int j = -1; j <= 1; j++) {
/* 138 */             for (int k = -1; k <= 1; k++) {
/* 139 */               if ((j != 0 || k != 0) && this.worldPointer.isAirBlock(this.position.add(k, 0, j)) && this.worldPointer.isAirBlock(this.position.add(k, 1, j))) {
/* 140 */                 if (this.worldPointer.getBlockState(this.position.add(k * 2, 0, j * 2)).getBlock() == Blocks.bookshelf) {
/* 141 */                   l++;
/*     */                 }
/*     */                 
/* 144 */                 if (this.worldPointer.getBlockState(this.position.add(k * 2, 1, j * 2)).getBlock() == Blocks.bookshelf) {
/* 145 */                   l++;
/*     */                 }
/*     */                 
/* 148 */                 if (k != 0 && j != 0) {
/* 149 */                   if (this.worldPointer.getBlockState(this.position.add(k * 2, 0, j)).getBlock() == Blocks.bookshelf) {
/* 150 */                     l++;
/*     */                   }
/*     */                   
/* 153 */                   if (this.worldPointer.getBlockState(this.position.add(k * 2, 1, j)).getBlock() == Blocks.bookshelf) {
/* 154 */                     l++;
/*     */                   }
/*     */                   
/* 157 */                   if (this.worldPointer.getBlockState(this.position.add(k, 0, j * 2)).getBlock() == Blocks.bookshelf) {
/* 158 */                     l++;
/*     */                   }
/*     */                   
/* 161 */                   if (this.worldPointer.getBlockState(this.position.add(k, 1, j * 2)).getBlock() == Blocks.bookshelf) {
/* 162 */                     l++;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 169 */           this.rand.setSeed(this.xpSeed);
/*     */           
/* 171 */           for (int i1 = 0; i1 < 3; i1++) {
/* 172 */             this.enchantLevels[i1] = EnchantmentHelper.calcItemStackEnchantability(this.rand, i1, l, itemstack);
/* 173 */             this.enchantmentIds[i1] = -1;
/*     */             
/* 175 */             if (this.enchantLevels[i1] < i1 + 1) {
/* 176 */               this.enchantLevels[i1] = 0;
/*     */             }
/*     */           } 
/*     */           
/* 180 */           for (int j1 = 0; j1 < 3; j1++) {
/* 181 */             if (this.enchantLevels[j1] > 0) {
/* 182 */               List<EnchantmentData> list = func_178148_a(itemstack, j1, this.enchantLevels[j1]);
/*     */               
/* 184 */               if (list != null && !list.isEmpty()) {
/* 185 */                 EnchantmentData enchantmentdata = list.get(this.rand.nextInt(list.size()));
/* 186 */                 this.enchantmentIds[j1] = enchantmentdata.enchantmentobj.effectId | enchantmentdata.enchantmentLevel << 8;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 191 */           detectAndSendChanges();
/*     */         } 
/*     */       } else {
/* 194 */         for (int i = 0; i < 3; i++) {
/* 195 */           this.enchantLevels[i] = 0;
/* 196 */           this.enchantmentIds[i] = -1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enchantItem(EntityPlayer playerIn, int id) {
/* 206 */     ItemStack itemstack = this.tableInventory.getStackInSlot(0);
/* 207 */     ItemStack itemstack1 = this.tableInventory.getStackInSlot(1);
/* 208 */     int i = id + 1;
/*     */     
/* 210 */     if ((itemstack1 == null || itemstack1.stackSize < i) && !playerIn.capabilities.isCreativeMode)
/* 211 */       return false; 
/* 212 */     if (this.enchantLevels[id] > 0 && itemstack != null && ((playerIn.experienceLevel >= i && playerIn.experienceLevel >= this.enchantLevels[id]) || playerIn.capabilities.isCreativeMode)) {
/* 213 */       if (!this.worldPointer.isRemote) {
/* 214 */         List<EnchantmentData> list = func_178148_a(itemstack, id, this.enchantLevels[id]);
/* 215 */         boolean flag = (itemstack.getItem() == Items.book);
/*     */         
/* 217 */         if (list != null) {
/* 218 */           playerIn.removeExperienceLevel(i);
/*     */           
/* 220 */           if (flag) {
/* 221 */             itemstack.setItem((Item)Items.enchanted_book);
/*     */           }
/*     */           
/* 224 */           for (int j = 0; j < list.size(); j++) {
/* 225 */             EnchantmentData enchantmentdata = list.get(j);
/*     */             
/* 227 */             if (flag) {
/* 228 */               Items.enchanted_book.addEnchantment(itemstack, enchantmentdata);
/*     */             } else {
/* 230 */               itemstack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
/*     */             } 
/*     */           } 
/*     */           
/* 234 */           if (!playerIn.capabilities.isCreativeMode) {
/* 235 */             itemstack1.stackSize -= i;
/*     */             
/* 237 */             if (itemstack1.stackSize <= 0) {
/* 238 */               this.tableInventory.setInventorySlotContents(1, null);
/*     */             }
/*     */           } 
/*     */           
/* 242 */           playerIn.triggerAchievement(StatList.field_181739_W);
/* 243 */           this.tableInventory.markDirty();
/* 244 */           this.xpSeed = playerIn.getXPSeed();
/* 245 */           onCraftMatrixChanged(this.tableInventory);
/*     */         } 
/*     */       } 
/*     */       
/* 249 */       return true;
/*     */     } 
/* 251 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<EnchantmentData> func_178148_a(ItemStack stack, int p_178148_2_, int p_178148_3_) {
/* 256 */     this.rand.setSeed((this.xpSeed + p_178148_2_));
/* 257 */     List<EnchantmentData> list = EnchantmentHelper.buildEnchantmentList(this.rand, stack, p_178148_3_);
/*     */     
/* 259 */     if (stack.getItem() == Items.book && list != null && list.size() > 1) {
/* 260 */       list.remove(this.rand.nextInt(list.size()));
/*     */     }
/*     */     
/* 263 */     return list;
/*     */   }
/*     */   
/*     */   public int getLapisAmount() {
/* 267 */     ItemStack itemstack = this.tableInventory.getStackInSlot(1);
/* 268 */     return (itemstack == null) ? 0 : itemstack.stackSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 275 */     super.onContainerClosed(playerIn);
/*     */     
/* 277 */     if (!this.worldPointer.isRemote) {
/* 278 */       for (int i = 0; i < this.tableInventory.getSizeInventory(); i++) {
/* 279 */         ItemStack itemstack = this.tableInventory.removeStackFromSlot(i);
/*     */         
/* 281 */         if (itemstack != null) {
/* 282 */           playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 289 */     return (this.worldPointer.getBlockState(this.position).getBlock() != Blocks.enchanting_table) ? false : ((playerIn.getDistanceSq(this.position.getX() + 0.5D, this.position.getY() + 0.5D, this.position.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 296 */     ItemStack itemstack = null;
/* 297 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/* 299 */     if (slot != null && slot.getHasStack()) {
/* 300 */       ItemStack itemstack1 = slot.getStack();
/* 301 */       itemstack = itemstack1.copy();
/*     */       
/* 303 */       if (index == 0) {
/* 304 */         if (!mergeItemStack(itemstack1, 2, 38, true)) {
/* 305 */           return null;
/*     */         }
/* 307 */       } else if (index == 1) {
/* 308 */         if (!mergeItemStack(itemstack1, 2, 38, true)) {
/* 309 */           return null;
/*     */         }
/* 311 */       } else if (itemstack1.getItem() == Items.dye && EnumDyeColor.byDyeDamage(itemstack1.getMetadata()) == EnumDyeColor.BLUE) {
/* 312 */         if (!mergeItemStack(itemstack1, 1, 2, true)) {
/* 313 */           return null;
/*     */         }
/*     */       } else {
/* 316 */         if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(itemstack1)) {
/* 317 */           return null;
/*     */         }
/*     */         
/* 320 */         if (itemstack1.hasTagCompound() && itemstack1.stackSize == 1) {
/* 321 */           ((Slot)this.inventorySlots.get(0)).putStack(itemstack1.copy());
/* 322 */           itemstack1.stackSize = 0;
/* 323 */         } else if (itemstack1.stackSize >= 1) {
/* 324 */           ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemstack1.getItem(), 1, itemstack1.getMetadata()));
/* 325 */           itemstack1.stackSize--;
/*     */         } 
/*     */       } 
/*     */       
/* 329 */       if (itemstack1.stackSize == 0) {
/* 330 */         slot.putStack(null);
/*     */       } else {
/* 332 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 335 */       if (itemstack1.stackSize == itemstack.stackSize) {
/* 336 */         return null;
/*     */       }
/*     */       
/* 339 */       slot.onPickupFromSlot(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 342 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ContainerEnchantment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */