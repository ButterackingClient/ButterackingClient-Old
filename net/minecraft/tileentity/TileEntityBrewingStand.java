/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.BlockBrewingStand;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerBrewingStand;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemPotion;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.potion.PotionHelper;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ 
/*     */ public class TileEntityBrewingStand
/*     */   extends TileEntityLockable implements ITickable, ISidedInventory {
/*  28 */   private static final int[] inputSlots = new int[] { 3 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private static final int[] outputSlots = new int[] { 0, 1, 2 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private ItemStack[] brewingItemStacks = new ItemStack[4];
/*     */ 
/*     */ 
/*     */   
/*     */   private int brewTime;
/*     */ 
/*     */   
/*     */   private boolean[] filledSlots;
/*     */ 
/*     */   
/*     */   private Item ingredientID;
/*     */ 
/*     */   
/*     */   private String customName;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  56 */     return hasCustomName() ? this.customName : "container.brewing";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  63 */     return (this.customName != null && this.customName.length() > 0);
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  67 */     this.customName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  74 */     return this.brewingItemStacks.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  81 */     if (this.brewTime > 0) {
/*  82 */       this.brewTime--;
/*     */       
/*  84 */       if (this.brewTime == 0) {
/*  85 */         brewPotions();
/*  86 */         markDirty();
/*  87 */       } else if (!canBrew()) {
/*  88 */         this.brewTime = 0;
/*  89 */         markDirty();
/*  90 */       } else if (this.ingredientID != this.brewingItemStacks[3].getItem()) {
/*  91 */         this.brewTime = 0;
/*  92 */         markDirty();
/*     */       } 
/*  94 */     } else if (canBrew()) {
/*  95 */       this.brewTime = 400;
/*  96 */       this.ingredientID = this.brewingItemStacks[3].getItem();
/*     */     } 
/*     */     
/*  99 */     if (!this.worldObj.isRemote) {
/* 100 */       boolean[] aboolean = func_174902_m();
/*     */       
/* 102 */       if (!Arrays.equals(aboolean, this.filledSlots)) {
/* 103 */         this.filledSlots = aboolean;
/* 104 */         IBlockState iblockstate = this.worldObj.getBlockState(getPos());
/*     */         
/* 106 */         if (!(iblockstate.getBlock() instanceof BlockBrewingStand)) {
/*     */           return;
/*     */         }
/*     */         
/* 110 */         for (int i = 0; i < BlockBrewingStand.HAS_BOTTLE.length; i++) {
/* 111 */           iblockstate = iblockstate.withProperty((IProperty)BlockBrewingStand.HAS_BOTTLE[i], Boolean.valueOf(aboolean[i]));
/*     */         }
/*     */         
/* 114 */         this.worldObj.setBlockState(this.pos, iblockstate, 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean canBrew() {
/* 120 */     if (this.brewingItemStacks[3] != null && (this.brewingItemStacks[3]).stackSize > 0) {
/* 121 */       ItemStack itemstack = this.brewingItemStacks[3];
/*     */       
/* 123 */       if (!itemstack.getItem().isPotionIngredient(itemstack)) {
/* 124 */         return false;
/*     */       }
/* 126 */       boolean flag = false;
/*     */       
/* 128 */       for (int i = 0; i < 3; i++) {
/* 129 */         if (this.brewingItemStacks[i] != null && this.brewingItemStacks[i].getItem() == Items.potionitem) {
/* 130 */           int j = this.brewingItemStacks[i].getMetadata();
/* 131 */           int k = getPotionResult(j, itemstack);
/*     */           
/* 133 */           if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k)) {
/* 134 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/* 138 */           List<PotionEffect> list = Items.potionitem.getEffects(j);
/* 139 */           List<PotionEffect> list1 = Items.potionitem.getEffects(k);
/*     */           
/* 141 */           if ((j <= 0 || list != list1) && (list == null || (!list.equals(list1) && list1 != null)) && j != k) {
/* 142 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 148 */       return flag;
/*     */     } 
/*     */     
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void brewPotions() {
/* 156 */     if (canBrew()) {
/* 157 */       ItemStack itemstack = this.brewingItemStacks[3];
/*     */       
/* 159 */       for (int i = 0; i < 3; i++) {
/* 160 */         if (this.brewingItemStacks[i] != null && this.brewingItemStacks[i].getItem() == Items.potionitem) {
/* 161 */           int j = this.brewingItemStacks[i].getMetadata();
/* 162 */           int k = getPotionResult(j, itemstack);
/* 163 */           List<PotionEffect> list = Items.potionitem.getEffects(j);
/* 164 */           List<PotionEffect> list1 = Items.potionitem.getEffects(k);
/*     */           
/* 166 */           if ((j > 0 && list == list1) || (list != null && (list.equals(list1) || list1 == null))) {
/* 167 */             if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k)) {
/* 168 */               this.brewingItemStacks[i].setItemDamage(k);
/*     */             }
/* 170 */           } else if (j != k) {
/* 171 */             this.brewingItemStacks[i].setItemDamage(k);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 176 */       if (itemstack.getItem().hasContainerItem()) {
/* 177 */         this.brewingItemStacks[3] = new ItemStack(itemstack.getItem().getContainerItem());
/*     */       } else {
/* 179 */         (this.brewingItemStacks[3]).stackSize--;
/*     */         
/* 181 */         if ((this.brewingItemStacks[3]).stackSize <= 0) {
/* 182 */           this.brewingItemStacks[3] = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPotionResult(int meta, ItemStack stack) {
/* 192 */     return (stack == null) ? meta : (stack.getItem().isPotionIngredient(stack) ? PotionHelper.applyIngredient(meta, stack.getItem().getPotionEffect(stack)) : meta);
/*     */   }
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 196 */     super.readFromNBT(compound);
/* 197 */     NBTTagList nbttaglist = compound.getTagList("Items", 10);
/* 198 */     this.brewingItemStacks = new ItemStack[getSizeInventory()];
/*     */     
/* 200 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 201 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 202 */       int j = nbttagcompound.getByte("Slot");
/*     */       
/* 204 */       if (j >= 0 && j < this.brewingItemStacks.length) {
/* 205 */         this.brewingItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
/*     */       }
/*     */     } 
/*     */     
/* 209 */     this.brewTime = compound.getShort("BrewTime");
/*     */     
/* 211 */     if (compound.hasKey("CustomName", 8)) {
/* 212 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/* 217 */     super.writeToNBT(compound);
/* 218 */     compound.setShort("BrewTime", (short)this.brewTime);
/* 219 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 221 */     for (int i = 0; i < this.brewingItemStacks.length; i++) {
/* 222 */       if (this.brewingItemStacks[i] != null) {
/* 223 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 224 */         nbttagcompound.setByte("Slot", (byte)i);
/* 225 */         this.brewingItemStacks[i].writeToNBT(nbttagcompound);
/* 226 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 230 */     compound.setTag("Items", (NBTBase)nbttaglist);
/*     */     
/* 232 */     if (hasCustomName()) {
/* 233 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 241 */     return (index >= 0 && index < this.brewingItemStacks.length) ? this.brewingItemStacks[index] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 248 */     if (index >= 0 && index < this.brewingItemStacks.length) {
/* 249 */       ItemStack itemstack = this.brewingItemStacks[index];
/* 250 */       this.brewingItemStacks[index] = null;
/* 251 */       return itemstack;
/*     */     } 
/* 253 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 261 */     if (index >= 0 && index < this.brewingItemStacks.length) {
/* 262 */       ItemStack itemstack = this.brewingItemStacks[index];
/* 263 */       this.brewingItemStacks[index] = null;
/* 264 */       return itemstack;
/*     */     } 
/* 266 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 274 */     if (index >= 0 && index < this.brewingItemStacks.length) {
/* 275 */       this.brewingItemStacks[index] = stack;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 283 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUseableByPlayer(EntityPlayer player) {
/* 290 */     return (this.worldObj.getTileEntity(this.pos) != this) ? false : ((player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D));
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
/* 303 */     return (index == 3) ? stack.getItem().isPotionIngredient(stack) : (!(stack.getItem() != Items.potionitem && stack.getItem() != Items.glass_bottle));
/*     */   }
/*     */   
/*     */   public boolean[] func_174902_m() {
/* 307 */     boolean[] aboolean = new boolean[3];
/*     */     
/* 309 */     for (int i = 0; i < 3; i++) {
/* 310 */       if (this.brewingItemStacks[i] != null) {
/* 311 */         aboolean[i] = true;
/*     */       }
/*     */     } 
/*     */     
/* 315 */     return aboolean;
/*     */   }
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side) {
/* 319 */     return (side == EnumFacing.UP) ? inputSlots : outputSlots;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
/* 327 */     return isItemValidForSlot(index, itemStackIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
/* 335 */     return true;
/*     */   }
/*     */   
/*     */   public String getGuiID() {
/* 339 */     return "minecraft:brewing_stand";
/*     */   }
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 343 */     return (Container)new ContainerBrewingStand(playerInventory, (IInventory)this);
/*     */   }
/*     */   
/*     */   public int getField(int id) {
/* 347 */     switch (id) {
/*     */       case 0:
/* 349 */         return this.brewTime;
/*     */     } 
/*     */     
/* 352 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {
/* 357 */     switch (id) {
/*     */       case 0:
/* 359 */         this.brewTime = value;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 366 */     return 1;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 370 */     for (int i = 0; i < this.brewingItemStacks.length; i++)
/* 371 */       this.brewingItemStacks[i] = null; 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityBrewingStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */