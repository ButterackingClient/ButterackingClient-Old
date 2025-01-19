/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ public class InventoryBasic
/*     */   implements IInventory
/*     */ {
/*     */   private String inventoryTitle;
/*     */   private int slotsCount;
/*     */   private ItemStack[] inventoryContents;
/*     */   private List<IInvBasic> changeListeners;
/*     */   private boolean hasCustomName;
/*     */   
/*     */   public InventoryBasic(String title, boolean customName, int slotCount) {
/*  21 */     this.inventoryTitle = title;
/*  22 */     this.hasCustomName = customName;
/*  23 */     this.slotsCount = slotCount;
/*  24 */     this.inventoryContents = new ItemStack[slotCount];
/*     */   }
/*     */   
/*     */   public InventoryBasic(IChatComponent title, int slotCount) {
/*  28 */     this(title.getUnformattedText(), true, slotCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInventoryChangeListener(IInvBasic listener) {
/*  37 */     if (this.changeListeners == null) {
/*  38 */       this.changeListeners = Lists.newArrayList();
/*     */     }
/*     */     
/*  41 */     this.changeListeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeInventoryChangeListener(IInvBasic listener) {
/*  50 */     this.changeListeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  57 */     return (index >= 0 && index < this.inventoryContents.length) ? this.inventoryContents[index] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  64 */     if (this.inventoryContents[index] != null) {
/*  65 */       if ((this.inventoryContents[index]).stackSize <= count) {
/*  66 */         ItemStack itemstack1 = this.inventoryContents[index];
/*  67 */         this.inventoryContents[index] = null;
/*  68 */         markDirty();
/*  69 */         return itemstack1;
/*     */       } 
/*  71 */       ItemStack itemstack = this.inventoryContents[index].splitStack(count);
/*     */       
/*  73 */       if ((this.inventoryContents[index]).stackSize == 0) {
/*  74 */         this.inventoryContents[index] = null;
/*     */       }
/*     */       
/*  77 */       markDirty();
/*  78 */       return itemstack;
/*     */     } 
/*     */     
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack func_174894_a(ItemStack stack) {
/*  86 */     ItemStack itemstack = stack.copy();
/*     */     
/*  88 */     for (int i = 0; i < this.slotsCount; i++) {
/*  89 */       ItemStack itemstack1 = getStackInSlot(i);
/*     */       
/*  91 */       if (itemstack1 == null) {
/*  92 */         setInventorySlotContents(i, itemstack);
/*  93 */         markDirty();
/*  94 */         return null;
/*     */       } 
/*     */       
/*  97 */       if (ItemStack.areItemsEqual(itemstack1, itemstack)) {
/*  98 */         int j = Math.min(getInventoryStackLimit(), itemstack1.getMaxStackSize());
/*  99 */         int k = Math.min(itemstack.stackSize, j - itemstack1.stackSize);
/*     */         
/* 101 */         if (k > 0) {
/* 102 */           itemstack1.stackSize += k;
/* 103 */           itemstack.stackSize -= k;
/*     */           
/* 105 */           if (itemstack.stackSize <= 0) {
/* 106 */             markDirty();
/* 107 */             return null;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     if (itemstack.stackSize != stack.stackSize) {
/* 114 */       markDirty();
/*     */     }
/*     */     
/* 117 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 124 */     if (this.inventoryContents[index] != null) {
/* 125 */       ItemStack itemstack = this.inventoryContents[index];
/* 126 */       this.inventoryContents[index] = null;
/* 127 */       return itemstack;
/*     */     } 
/* 129 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 137 */     this.inventoryContents[index] = stack;
/*     */     
/* 139 */     if (stack != null && stack.stackSize > getInventoryStackLimit()) {
/* 140 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/* 143 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 150 */     return this.slotsCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 157 */     return this.inventoryTitle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 164 */     return this.hasCustomName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomName(String inventoryTitleIn) {
/* 171 */     this.hasCustomName = true;
/* 172 */     this.inventoryTitle = inventoryTitleIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDisplayName() {
/* 179 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getName()) : (IChatComponent)new ChatComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 186 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 194 */     if (this.changeListeners != null) {
/* 195 */       for (int i = 0; i < this.changeListeners.size(); i++) {
/* 196 */         ((IInvBasic)this.changeListeners.get(i)).onInventoryChanged(this);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 205 */     return true;
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
/* 218 */     return true;
/*     */   }
/*     */   
/*     */   public int getField(int id) {
/* 222 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount() {
/* 229 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 233 */     for (int i = 0; i < this.inventoryContents.length; i++)
/* 234 */       this.inventoryContents[i] = null; 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\inventory\InventoryBasic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */