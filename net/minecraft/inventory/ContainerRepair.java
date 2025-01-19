/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.BlockAnvil;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ContainerRepair extends Container {
/*  22 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   private IInventory outputSlot;
/*     */ 
/*     */ 
/*     */   
/*     */   private IInventory inputSlots;
/*     */ 
/*     */   
/*     */   private World theWorld;
/*     */ 
/*     */   
/*     */   private BlockPos selfPosition;
/*     */ 
/*     */   
/*     */   public int maximumCost;
/*     */ 
/*     */   
/*     */   private int materialCost;
/*     */ 
/*     */   
/*     */   private String repairedItemName;
/*     */ 
/*     */   
/*     */   private final EntityPlayer thePlayer;
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerRepair(InventoryPlayer playerInventory, World worldIn, EntityPlayer player) {
/*  53 */     this(playerInventory, worldIn, BlockPos.ORIGIN, player);
/*     */   }
/*     */   
/*     */   public ContainerRepair(InventoryPlayer playerInventory, final World worldIn, final BlockPos blockPosIn, EntityPlayer player) {
/*  57 */     this.outputSlot = new InventoryCraftResult();
/*  58 */     this.inputSlots = new InventoryBasic("Repair", true, 2) {
/*     */         public void markDirty() {
/*  60 */           super.markDirty();
/*  61 */           ContainerRepair.this.onCraftMatrixChanged(this);
/*     */         }
/*     */       };
/*  64 */     this.selfPosition = blockPosIn;
/*  65 */     this.theWorld = worldIn;
/*  66 */     this.thePlayer = player;
/*  67 */     addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
/*  68 */     addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
/*  69 */     addSlotToContainer(new Slot(this.outputSlot, 2, 134, 47) {
/*     */           public boolean isItemValid(ItemStack stack) {
/*  71 */             return false;
/*     */           }
/*     */           
/*     */           public boolean canTakeStack(EntityPlayer playerIn) {
/*  75 */             return ((playerIn.capabilities.isCreativeMode || playerIn.experienceLevel >= ContainerRepair.this.maximumCost) && ContainerRepair.this.maximumCost > 0 && getHasStack());
/*     */           }
/*     */           
/*     */           public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
/*  79 */             if (!playerIn.capabilities.isCreativeMode) {
/*  80 */               playerIn.addExperienceLevel(-ContainerRepair.this.maximumCost);
/*     */             }
/*     */             
/*  83 */             ContainerRepair.this.inputSlots.setInventorySlotContents(0, null);
/*     */             
/*  85 */             if (ContainerRepair.this.materialCost > 0) {
/*  86 */               ItemStack itemstack = ContainerRepair.this.inputSlots.getStackInSlot(1);
/*     */               
/*  88 */               if (itemstack != null && itemstack.stackSize > ContainerRepair.this.materialCost) {
/*  89 */                 itemstack.stackSize -= ContainerRepair.this.materialCost;
/*  90 */                 ContainerRepair.this.inputSlots.setInventorySlotContents(1, itemstack);
/*     */               } else {
/*  92 */                 ContainerRepair.this.inputSlots.setInventorySlotContents(1, null);
/*     */               } 
/*     */             } else {
/*  95 */               ContainerRepair.this.inputSlots.setInventorySlotContents(1, null);
/*     */             } 
/*     */             
/*  98 */             ContainerRepair.this.maximumCost = 0;
/*  99 */             IBlockState iblockstate = worldIn.getBlockState(blockPosIn);
/*     */             
/* 101 */             if (!playerIn.capabilities.isCreativeMode && !worldIn.isRemote && iblockstate.getBlock() == Blocks.anvil && playerIn.getRNG().nextFloat() < 0.12F) {
/* 102 */               int l = ((Integer)iblockstate.getValue((IProperty)BlockAnvil.DAMAGE)).intValue();
/* 103 */               l++;
/*     */               
/* 105 */               if (l > 2) {
/* 106 */                 worldIn.setBlockToAir(blockPosIn);
/* 107 */                 worldIn.playAuxSFX(1020, blockPosIn, 0);
/*     */               } else {
/* 109 */                 worldIn.setBlockState(blockPosIn, iblockstate.withProperty((IProperty)BlockAnvil.DAMAGE, Integer.valueOf(l)), 2);
/* 110 */                 worldIn.playAuxSFX(1021, blockPosIn, 0);
/*     */               } 
/* 112 */             } else if (!worldIn.isRemote) {
/* 113 */               worldIn.playAuxSFX(1021, blockPosIn, 0);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 118 */     for (int i = 0; i < 3; i++) {
/* 119 */       for (int j = 0; j < 9; j++) {
/* 120 */         addSlotToContainer(new Slot((IInventory)playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/* 124 */     for (int k = 0; k < 9; k++) {
/* 125 */       addSlotToContainer(new Slot((IInventory)playerInventory, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/* 133 */     super.onCraftMatrixChanged(inventoryIn);
/*     */     
/* 135 */     if (inventoryIn == this.inputSlots) {
/* 136 */       updateRepairOutput();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRepairOutput() {
/* 144 */     int i = 0;
/* 145 */     int j = 1;
/* 146 */     int k = 1;
/* 147 */     int l = 1;
/* 148 */     int i1 = 2;
/* 149 */     int j1 = 1;
/* 150 */     int k1 = 1;
/* 151 */     ItemStack itemstack = this.inputSlots.getStackInSlot(0);
/* 152 */     this.maximumCost = 1;
/* 153 */     int l1 = 0;
/* 154 */     int i2 = 0;
/* 155 */     int j2 = 0;
/*     */     
/* 157 */     if (itemstack == null) {
/* 158 */       this.outputSlot.setInventorySlotContents(0, null);
/* 159 */       this.maximumCost = 0;
/*     */     } else {
/* 161 */       ItemStack itemstack1 = itemstack.copy();
/* 162 */       ItemStack itemstack2 = this.inputSlots.getStackInSlot(1);
/* 163 */       Map<Integer, Integer> map = EnchantmentHelper.getEnchantments(itemstack1);
/* 164 */       boolean flag = false;
/* 165 */       i2 = i2 + itemstack.getRepairCost() + ((itemstack2 == null) ? 0 : itemstack2.getRepairCost());
/* 166 */       this.materialCost = 0;
/*     */       
/* 168 */       if (itemstack2 != null) {
/* 169 */         flag = (itemstack2.getItem() == Items.enchanted_book && Items.enchanted_book.getEnchantments(itemstack2).tagCount() > 0);
/*     */         
/* 171 */         if (itemstack1.isItemStackDamageable() && itemstack1.getItem().getIsRepairable(itemstack, itemstack2)) {
/* 172 */           int j4 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);
/*     */           
/* 174 */           if (j4 <= 0) {
/* 175 */             this.outputSlot.setInventorySlotContents(0, null);
/* 176 */             this.maximumCost = 0;
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           int l4;
/* 182 */           for (l4 = 0; j4 > 0 && l4 < itemstack2.stackSize; l4++) {
/* 183 */             int j5 = itemstack1.getItemDamage() - j4;
/* 184 */             itemstack1.setItemDamage(j5);
/* 185 */             l1++;
/* 186 */             j4 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);
/*     */           } 
/*     */           
/* 189 */           this.materialCost = l4;
/*     */         } else {
/* 191 */           if (!flag && (itemstack1.getItem() != itemstack2.getItem() || !itemstack1.isItemStackDamageable())) {
/* 192 */             this.outputSlot.setInventorySlotContents(0, null);
/* 193 */             this.maximumCost = 0;
/*     */             
/*     */             return;
/*     */           } 
/* 197 */           if (itemstack1.isItemStackDamageable() && !flag) {
/* 198 */             int k2 = itemstack.getMaxDamage() - itemstack.getItemDamage();
/* 199 */             int l2 = itemstack2.getMaxDamage() - itemstack2.getItemDamage();
/* 200 */             int i3 = l2 + itemstack1.getMaxDamage() * 12 / 100;
/* 201 */             int j3 = k2 + i3;
/* 202 */             int k3 = itemstack1.getMaxDamage() - j3;
/*     */             
/* 204 */             if (k3 < 0) {
/* 205 */               k3 = 0;
/*     */             }
/*     */             
/* 208 */             if (k3 < itemstack1.getMetadata()) {
/* 209 */               itemstack1.setItemDamage(k3);
/* 210 */               l1 += 2;
/*     */             } 
/*     */           } 
/*     */           
/* 214 */           Map<Integer, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack2);
/* 215 */           Iterator<Integer> iterator1 = map1.keySet().iterator();
/*     */           
/* 217 */           while (iterator1.hasNext()) {
/* 218 */             int i5 = ((Integer)iterator1.next()).intValue();
/* 219 */             Enchantment enchantment = Enchantment.getEnchantmentById(i5);
/*     */             
/* 221 */             if (enchantment != null) {
/* 222 */               int i6, k5 = map.containsKey(Integer.valueOf(i5)) ? ((Integer)map.get(Integer.valueOf(i5))).intValue() : 0;
/* 223 */               int l3 = ((Integer)map1.get(Integer.valueOf(i5))).intValue();
/*     */ 
/*     */               
/* 226 */               if (k5 == l3) {
/*     */                 
/* 228 */                 i6 = ++l3;
/*     */               } else {
/* 230 */                 i6 = Math.max(l3, k5);
/*     */               } 
/*     */               
/* 233 */               l3 = i6;
/* 234 */               boolean flag1 = enchantment.canApply(itemstack);
/*     */               
/* 236 */               if (this.thePlayer.capabilities.isCreativeMode || itemstack.getItem() == Items.enchanted_book) {
/* 237 */                 flag1 = true;
/*     */               }
/*     */               
/* 240 */               Iterator<Integer> iterator = map.keySet().iterator();
/*     */               
/* 242 */               while (iterator.hasNext()) {
/* 243 */                 int i4 = ((Integer)iterator.next()).intValue();
/*     */                 
/* 245 */                 if (i4 != i5 && !enchantment.canApplyTogether(Enchantment.getEnchantmentById(i4))) {
/* 246 */                   flag1 = false;
/* 247 */                   l1++;
/*     */                 } 
/*     */               } 
/*     */               
/* 251 */               if (flag1) {
/* 252 */                 if (l3 > enchantment.getMaxLevel()) {
/* 253 */                   l3 = enchantment.getMaxLevel();
/*     */                 }
/*     */                 
/* 256 */                 map.put(Integer.valueOf(i5), Integer.valueOf(l3));
/* 257 */                 int l5 = 0;
/*     */                 
/* 259 */                 switch (enchantment.getWeight()) {
/*     */                   case 1:
/* 261 */                     l5 = 8;
/*     */                     break;
/*     */                   
/*     */                   case 2:
/* 265 */                     l5 = 4;
/*     */                     break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   case 5:
/* 277 */                     l5 = 2;
/*     */                     break;
/*     */                   
/*     */                   case 10:
/* 281 */                     l5 = 1;
/*     */                     break;
/*     */                 } 
/* 284 */                 if (flag) {
/* 285 */                   l5 = Math.max(1, l5 / 2);
/*     */                 }
/*     */                 
/* 288 */                 l1 += l5 * l3;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 295 */       if (StringUtils.isBlank(this.repairedItemName)) {
/* 296 */         if (itemstack.hasDisplayName()) {
/* 297 */           j2 = 1;
/* 298 */           l1 += j2;
/* 299 */           itemstack1.clearCustomName();
/*     */         } 
/* 301 */       } else if (!this.repairedItemName.equals(itemstack.getDisplayName())) {
/* 302 */         j2 = 1;
/* 303 */         l1 += j2;
/* 304 */         itemstack1.setStackDisplayName(this.repairedItemName);
/*     */       } 
/*     */       
/* 307 */       this.maximumCost = i2 + l1;
/*     */       
/* 309 */       if (l1 <= 0) {
/* 310 */         itemstack1 = null;
/*     */       }
/*     */       
/* 313 */       if (j2 == l1 && j2 > 0 && this.maximumCost >= 40) {
/* 314 */         this.maximumCost = 39;
/*     */       }
/*     */       
/* 317 */       if (this.maximumCost >= 40 && !this.thePlayer.capabilities.isCreativeMode) {
/* 318 */         itemstack1 = null;
/*     */       }
/*     */       
/* 321 */       if (itemstack1 != null) {
/* 322 */         int k4 = itemstack1.getRepairCost();
/*     */         
/* 324 */         if (itemstack2 != null && k4 < itemstack2.getRepairCost()) {
/* 325 */           k4 = itemstack2.getRepairCost();
/*     */         }
/*     */         
/* 328 */         k4 = k4 * 2 + 1;
/* 329 */         itemstack1.setRepairCost(k4);
/* 330 */         EnchantmentHelper.setEnchantments(map, itemstack1);
/*     */       } 
/*     */       
/* 333 */       this.outputSlot.setInventorySlotContents(0, itemstack1);
/* 334 */       detectAndSendChanges();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onCraftGuiOpened(ICrafting listener) {
/* 339 */     super.onCraftGuiOpened(listener);
/* 340 */     listener.sendProgressBarUpdate(this, 0, this.maximumCost);
/*     */   }
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/* 344 */     if (id == 0) {
/* 345 */       this.maximumCost = data;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 353 */     super.onContainerClosed(playerIn);
/*     */     
/* 355 */     if (!this.theWorld.isRemote) {
/* 356 */       for (int i = 0; i < this.inputSlots.getSizeInventory(); i++) {
/* 357 */         ItemStack itemstack = this.inputSlots.removeStackFromSlot(i);
/*     */         
/* 359 */         if (itemstack != null) {
/* 360 */           playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 367 */     return (this.theWorld.getBlockState(this.selfPosition).getBlock() != Blocks.anvil) ? false : ((playerIn.getDistanceSq(this.selfPosition.getX() + 0.5D, this.selfPosition.getY() + 0.5D, this.selfPosition.getZ() + 0.5D) <= 64.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 374 */     ItemStack itemstack = null;
/* 375 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/* 377 */     if (slot != null && slot.getHasStack()) {
/* 378 */       ItemStack itemstack1 = slot.getStack();
/* 379 */       itemstack = itemstack1.copy();
/*     */       
/* 381 */       if (index == 2) {
/* 382 */         if (!mergeItemStack(itemstack1, 3, 39, true)) {
/* 383 */           return null;
/*     */         }
/*     */         
/* 386 */         slot.onSlotChange(itemstack1, itemstack);
/* 387 */       } else if (index != 0 && index != 1) {
/* 388 */         if (index >= 3 && index < 39 && !mergeItemStack(itemstack1, 0, 2, false)) {
/* 389 */           return null;
/*     */         }
/* 391 */       } else if (!mergeItemStack(itemstack1, 3, 39, false)) {
/* 392 */         return null;
/*     */       } 
/*     */       
/* 395 */       if (itemstack1.stackSize == 0) {
/* 396 */         slot.putStack(null);
/*     */       } else {
/* 398 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 401 */       if (itemstack1.stackSize == itemstack.stackSize) {
/* 402 */         return null;
/*     */       }
/*     */       
/* 405 */       slot.onPickupFromSlot(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 408 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateItemName(String newName) {
/* 415 */     this.repairedItemName = newName;
/*     */     
/* 417 */     if (getSlot(2).getHasStack()) {
/* 418 */       ItemStack itemstack = getSlot(2).getStack();
/*     */       
/* 420 */       if (StringUtils.isBlank(newName)) {
/* 421 */         itemstack.clearCustomName();
/*     */       } else {
/* 423 */         itemstack.setStackDisplayName(this.repairedItemName);
/*     */       } 
/*     */     } 
/*     */     
/* 427 */     updateRepairOutput();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\ContainerRepair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */