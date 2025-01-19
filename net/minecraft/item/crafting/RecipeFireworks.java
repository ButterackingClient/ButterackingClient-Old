/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemDye;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipeFireworks
/*     */   implements IRecipe
/*     */ {
/*     */   private ItemStack field_92102_a;
/*     */   
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  22 */     this.field_92102_a = null;
/*  23 */     int i = 0;
/*  24 */     int j = 0;
/*  25 */     int k = 0;
/*  26 */     int l = 0;
/*  27 */     int i1 = 0;
/*  28 */     int j1 = 0;
/*     */     
/*  30 */     for (int k1 = 0; k1 < inv.getSizeInventory(); k1++) {
/*  31 */       ItemStack itemstack = inv.getStackInSlot(k1);
/*     */       
/*  33 */       if (itemstack != null) {
/*  34 */         if (itemstack.getItem() == Items.gunpowder) {
/*  35 */           j++;
/*  36 */         } else if (itemstack.getItem() == Items.firework_charge) {
/*  37 */           l++;
/*  38 */         } else if (itemstack.getItem() == Items.dye) {
/*  39 */           k++;
/*  40 */         } else if (itemstack.getItem() == Items.paper) {
/*  41 */           i++;
/*  42 */         } else if (itemstack.getItem() == Items.glowstone_dust) {
/*  43 */           i1++;
/*  44 */         } else if (itemstack.getItem() == Items.diamond) {
/*  45 */           i1++;
/*  46 */         } else if (itemstack.getItem() == Items.fire_charge) {
/*  47 */           j1++;
/*  48 */         } else if (itemstack.getItem() == Items.feather) {
/*  49 */           j1++;
/*  50 */         } else if (itemstack.getItem() == Items.gold_nugget) {
/*  51 */           j1++;
/*     */         } else {
/*  53 */           if (itemstack.getItem() != Items.skull) {
/*  54 */             return false;
/*     */           }
/*     */           
/*  57 */           j1++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  62 */     i1 = i1 + k + j1;
/*     */     
/*  64 */     if (j <= 3 && i <= 1) {
/*  65 */       if (j >= 1 && i == 1 && i1 == 0) {
/*  66 */         this.field_92102_a = new ItemStack(Items.fireworks);
/*     */         
/*  68 */         if (l > 0) {
/*  69 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  70 */           NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/*  71 */           NBTTagList nbttaglist = new NBTTagList();
/*     */           
/*  73 */           for (int k2 = 0; k2 < inv.getSizeInventory(); k2++) {
/*  74 */             ItemStack itemstack3 = inv.getStackInSlot(k2);
/*     */             
/*  76 */             if (itemstack3 != null && itemstack3.getItem() == Items.firework_charge && itemstack3.hasTagCompound() && itemstack3.getTagCompound().hasKey("Explosion", 10)) {
/*  77 */               nbttaglist.appendTag((NBTBase)itemstack3.getTagCompound().getCompoundTag("Explosion"));
/*     */             }
/*     */           } 
/*     */           
/*  81 */           nbttagcompound3.setTag("Explosions", (NBTBase)nbttaglist);
/*  82 */           nbttagcompound3.setByte("Flight", (byte)j);
/*  83 */           nbttagcompound1.setTag("Fireworks", (NBTBase)nbttagcompound3);
/*  84 */           this.field_92102_a.setTagCompound(nbttagcompound1);
/*     */         } 
/*     */         
/*  87 */         return true;
/*  88 */       }  if (j == 1 && i == 0 && l == 0 && k > 0 && j1 <= 1) {
/*  89 */         this.field_92102_a = new ItemStack(Items.firework_charge);
/*  90 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  91 */         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/*  92 */         byte b0 = 0;
/*  93 */         List<Integer> list = Lists.newArrayList();
/*     */         
/*  95 */         for (int l1 = 0; l1 < inv.getSizeInventory(); l1++) {
/*  96 */           ItemStack itemstack2 = inv.getStackInSlot(l1);
/*     */           
/*  98 */           if (itemstack2 != null) {
/*  99 */             if (itemstack2.getItem() == Items.dye) {
/* 100 */               list.add(Integer.valueOf(ItemDye.dyeColors[itemstack2.getMetadata() & 0xF]));
/* 101 */             } else if (itemstack2.getItem() == Items.glowstone_dust) {
/* 102 */               nbttagcompound2.setBoolean("Flicker", true);
/* 103 */             } else if (itemstack2.getItem() == Items.diamond) {
/* 104 */               nbttagcompound2.setBoolean("Trail", true);
/* 105 */             } else if (itemstack2.getItem() == Items.fire_charge) {
/* 106 */               b0 = 1;
/* 107 */             } else if (itemstack2.getItem() == Items.feather) {
/* 108 */               b0 = 4;
/* 109 */             } else if (itemstack2.getItem() == Items.gold_nugget) {
/* 110 */               b0 = 2;
/* 111 */             } else if (itemstack2.getItem() == Items.skull) {
/* 112 */               b0 = 3;
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 117 */         int[] aint1 = new int[list.size()];
/*     */         
/* 119 */         for (int l2 = 0; l2 < aint1.length; l2++) {
/* 120 */           aint1[l2] = ((Integer)list.get(l2)).intValue();
/*     */         }
/*     */         
/* 123 */         nbttagcompound2.setIntArray("Colors", aint1);
/* 124 */         nbttagcompound2.setByte("Type", b0);
/* 125 */         nbttagcompound.setTag("Explosion", (NBTBase)nbttagcompound2);
/* 126 */         this.field_92102_a.setTagCompound(nbttagcompound);
/* 127 */         return true;
/* 128 */       }  if (j == 0 && i == 0 && l == 1 && k > 0 && k == i1) {
/* 129 */         List<Integer> list1 = Lists.newArrayList();
/*     */         
/* 131 */         for (int i2 = 0; i2 < inv.getSizeInventory(); i2++) {
/* 132 */           ItemStack itemstack1 = inv.getStackInSlot(i2);
/*     */           
/* 134 */           if (itemstack1 != null) {
/* 135 */             if (itemstack1.getItem() == Items.dye) {
/* 136 */               list1.add(Integer.valueOf(ItemDye.dyeColors[itemstack1.getMetadata() & 0xF]));
/* 137 */             } else if (itemstack1.getItem() == Items.firework_charge) {
/* 138 */               this.field_92102_a = itemstack1.copy();
/* 139 */               this.field_92102_a.stackSize = 1;
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 144 */         int[] aint = new int[list1.size()];
/*     */         
/* 146 */         for (int j2 = 0; j2 < aint.length; j2++) {
/* 147 */           aint[j2] = ((Integer)list1.get(j2)).intValue();
/*     */         }
/*     */         
/* 150 */         if (this.field_92102_a != null && this.field_92102_a.hasTagCompound()) {
/* 151 */           NBTTagCompound nbttagcompound4 = this.field_92102_a.getTagCompound().getCompoundTag("Explosion");
/*     */           
/* 153 */           if (nbttagcompound4 == null) {
/* 154 */             return false;
/*     */           }
/* 156 */           nbttagcompound4.setIntArray("FadeColors", aint);
/* 157 */           return true;
/*     */         } 
/*     */         
/* 160 */         return false;
/*     */       } 
/*     */       
/* 163 */       return false;
/*     */     } 
/*     */     
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 174 */     return this.field_92102_a.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeSize() {
/* 181 */     return 10;
/*     */   }
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/* 185 */     return this.field_92102_a;
/*     */   }
/*     */   
/*     */   public ItemStack[] getRemainingItems(InventoryCrafting inv) {
/* 189 */     ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*     */     
/* 191 */     for (int i = 0; i < aitemstack.length; i++) {
/* 192 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/* 194 */       if (itemstack != null && itemstack.getItem().hasContainerItem()) {
/* 195 */         aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
/*     */       }
/*     */     } 
/*     */     
/* 199 */     return aitemstack;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\crafting\RecipeFireworks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */