/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerDispenser;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ 
/*     */ public class TileEntityDispenser extends TileEntityLockable implements IInventory {
/*  15 */   private static final Random RNG = new Random();
/*  16 */   private ItemStack[] stacks = new ItemStack[9];
/*     */ 
/*     */   
/*     */   protected String customName;
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  23 */     return 9;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  30 */     return this.stacks[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  37 */     if (this.stacks[index] != null) {
/*  38 */       if ((this.stacks[index]).stackSize <= count) {
/*  39 */         ItemStack itemstack1 = this.stacks[index];
/*  40 */         this.stacks[index] = null;
/*  41 */         markDirty();
/*  42 */         return itemstack1;
/*     */       } 
/*  44 */       ItemStack itemstack = this.stacks[index].splitStack(count);
/*     */       
/*  46 */       if ((this.stacks[index]).stackSize == 0) {
/*  47 */         this.stacks[index] = null;
/*     */       }
/*     */       
/*  50 */       markDirty();
/*  51 */       return itemstack;
/*     */     } 
/*     */     
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  62 */     if (this.stacks[index] != null) {
/*  63 */       ItemStack itemstack = this.stacks[index];
/*  64 */       this.stacks[index] = null;
/*  65 */       return itemstack;
/*     */     } 
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDispenseSlot() {
/*  72 */     int i = -1;
/*  73 */     int j = 1;
/*     */     
/*  75 */     for (int k = 0; k < this.stacks.length; k++) {
/*  76 */       if (this.stacks[k] != null && RNG.nextInt(j++) == 0) {
/*  77 */         i = k;
/*     */       }
/*     */     } 
/*     */     
/*  81 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/*  88 */     this.stacks[index] = stack;
/*     */     
/*  90 */     if (stack != null && stack.stackSize > getInventoryStackLimit()) {
/*  91 */       stack.stackSize = getInventoryStackLimit();
/*     */     }
/*     */     
/*  94 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addItemStack(ItemStack stack) {
/* 102 */     for (int i = 0; i < this.stacks.length; i++) {
/* 103 */       if (this.stacks[i] == null || this.stacks[i].getItem() == null) {
/* 104 */         setInventorySlotContents(i, stack);
/* 105 */         return i;
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 116 */     return hasCustomName() ? this.customName : "container.dispenser";
/*     */   }
/*     */   
/*     */   public void setCustomName(String customName) {
/* 120 */     this.customName = customName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 127 */     return (this.customName != null);
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 131 */     super.readFromNBT(compound);
/* 132 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/* 133 */     this.stacks = new ItemStack[getSizeInventory()];
/*     */     
/* 135 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 136 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 137 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*     */       
/* 139 */       if (j >= 0 && j < this.stacks.length) {
/* 140 */         this.stacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */     
/* 144 */     if (compound.hasKey("CustomName", 8)) {
/* 145 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 150 */     super.writeToNBT(compound);
/* 151 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 153 */     for (int i = 0; i < this.stacks.length; i++) {
/* 154 */       if (this.stacks[i] != null) {
/* 155 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 156 */         nbttagcompound.setByte("Slot", (byte)i);
/* 157 */         this.stacks[i].writeToNBT(nbttagcompound);
/* 158 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 162 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*     */     
/* 164 */     if (hasCustomName()) {
/* 165 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 173 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 180 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
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
/* 193 */     return true;
/*     */   }
/*     */   
/*     */   public String getGuiID() {
/* 197 */     return "minecraft:dispenser";
/*     */   }
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 201 */     return (Container)new ContainerDispenser((IInventory)playerInventory, this);
/*     */   }
/*     */   
/*     */   public int getField(int id) {
/* 205 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */   
/*     */   public int getFieldCount() {
/* 212 */     return 0;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 216 */     for (int i = 0; i < this.stacks.length; i++)
/* 217 */       this.stacks[i] = null; 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */