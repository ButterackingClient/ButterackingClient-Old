/*     */ package net.minecraft.item.crafting;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntityBanner;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class RecipesBanners {
/*     */   void addRecipes(CraftingManager p_179534_1_) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumDyeColor[] arrayOfEnumDyeColor;
/*  18 */     for (i = (arrayOfEnumDyeColor = EnumDyeColor.values()).length, b = 0; b < i; ) { EnumDyeColor enumdyecolor = arrayOfEnumDyeColor[b];
/*  19 */       p_179534_1_.addRecipe(new ItemStack(Items.banner, 1, enumdyecolor.getDyeDamage()), new Object[] { "###", "###", " | ", Character.valueOf('#'), new ItemStack(Blocks.wool, 1, enumdyecolor.getMetadata()), Character.valueOf('|'), Items.stick });
/*     */       b++; }
/*     */     
/*  22 */     p_179534_1_.addRecipe(new RecipeDuplicatePattern(null));
/*  23 */     p_179534_1_.addRecipe(new RecipeAddPattern(null));
/*     */   }
/*     */   
/*     */   static class RecipeAddPattern
/*     */     implements IRecipe {
/*     */     private RecipeAddPattern() {}
/*     */     
/*     */     public boolean matches(InventoryCrafting inv, World worldIn) {
/*  31 */       boolean flag = false;
/*     */       
/*  33 */       for (int i = 0; i < inv.getSizeInventory(); i++) {
/*  34 */         ItemStack itemstack = inv.getStackInSlot(i);
/*     */         
/*  36 */         if (itemstack != null && itemstack.getItem() == Items.banner) {
/*  37 */           if (flag) {
/*  38 */             return false;
/*     */           }
/*     */           
/*  41 */           if (TileEntityBanner.getPatterns(itemstack) >= 6) {
/*  42 */             return false;
/*     */           }
/*     */           
/*  45 */           flag = true;
/*     */         } 
/*     */       } 
/*     */       
/*  49 */       if (!flag) {
/*  50 */         return false;
/*     */       }
/*  52 */       return (func_179533_c(inv) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  57 */       ItemStack itemstack = null;
/*     */       
/*  59 */       for (int i = 0; i < inv.getSizeInventory(); i++) {
/*  60 */         ItemStack itemstack1 = inv.getStackInSlot(i);
/*     */         
/*  62 */         if (itemstack1 != null && itemstack1.getItem() == Items.banner) {
/*  63 */           itemstack = itemstack1.copy();
/*  64 */           itemstack.stackSize = 1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  69 */       TileEntityBanner.EnumBannerPattern tileentitybanner$enumbannerpattern = func_179533_c(inv);
/*     */       
/*  71 */       if (tileentitybanner$enumbannerpattern != null) {
/*  72 */         int k = 0;
/*     */         
/*  74 */         for (int j = 0; j < inv.getSizeInventory(); j++) {
/*  75 */           ItemStack itemstack2 = inv.getStackInSlot(j);
/*     */           
/*  77 */           if (itemstack2 != null && itemstack2.getItem() == Items.dye) {
/*  78 */             k = itemstack2.getMetadata();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*  83 */         NBTTagCompound nbttagcompound1 = itemstack.getSubCompound("BlockEntityTag", true);
/*  84 */         NBTTagList nbttaglist = null;
/*     */         
/*  86 */         if (nbttagcompound1.hasKey("Patterns", 9)) {
/*  87 */           nbttaglist = nbttagcompound1.getTagList("Patterns", 10);
/*     */         } else {
/*  89 */           nbttaglist = new NBTTagList();
/*  90 */           nbttagcompound1.setTag("Patterns", (NBTBase)nbttaglist);
/*     */         } 
/*     */         
/*  93 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  94 */         nbttagcompound.setString("Pattern", tileentitybanner$enumbannerpattern.getPatternID());
/*  95 */         nbttagcompound.setInteger("Color", k);
/*  96 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */       
/*  99 */       return itemstack;
/*     */     }
/*     */     
/*     */     public int getRecipeSize() {
/* 103 */       return 10;
/*     */     }
/*     */     
/*     */     public ItemStack getRecipeOutput() {
/* 107 */       return null;
/*     */     }
/*     */     
/*     */     public ItemStack[] getRemainingItems(InventoryCrafting inv) {
/* 111 */       ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*     */       
/* 113 */       for (int i = 0; i < aitemstack.length; i++) {
/* 114 */         ItemStack itemstack = inv.getStackInSlot(i);
/*     */         
/* 116 */         if (itemstack != null && itemstack.getItem().hasContainerItem()) {
/* 117 */           aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
/*     */         }
/*     */       } 
/*     */       
/* 121 */       return aitemstack; } private TileEntityBanner.EnumBannerPattern func_179533_c(InventoryCrafting p_179533_1_) {
/*     */       byte b;
/*     */       int i;
/*     */       TileEntityBanner.EnumBannerPattern[] arrayOfEnumBannerPattern;
/* 125 */       for (i = (arrayOfEnumBannerPattern = TileEntityBanner.EnumBannerPattern.values()).length, b = 0; b < i; ) { TileEntityBanner.EnumBannerPattern tileentitybanner$enumbannerpattern = arrayOfEnumBannerPattern[b];
/* 126 */         if (tileentitybanner$enumbannerpattern.hasValidCrafting()) {
/* 127 */           boolean flag = true;
/*     */           
/* 129 */           if (tileentitybanner$enumbannerpattern.hasCraftingStack()) {
/* 130 */             boolean flag1 = false;
/* 131 */             boolean flag2 = false;
/*     */             
/* 133 */             for (int j = 0; j < p_179533_1_.getSizeInventory() && flag; j++) {
/* 134 */               ItemStack itemstack = p_179533_1_.getStackInSlot(j);
/*     */               
/* 136 */               if (itemstack != null && itemstack.getItem() != Items.banner) {
/* 137 */                 if (itemstack.getItem() == Items.dye) {
/* 138 */                   if (flag2) {
/* 139 */                     flag = false;
/*     */                     
/*     */                     break;
/*     */                   } 
/* 143 */                   flag2 = true;
/*     */                 } else {
/* 145 */                   if (flag1 || !itemstack.isItemEqual(tileentitybanner$enumbannerpattern.getCraftingStack())) {
/* 146 */                     flag = false;
/*     */                     
/*     */                     break;
/*     */                   } 
/* 150 */                   flag1 = true;
/*     */                 } 
/*     */               }
/*     */             } 
/*     */             
/* 155 */             if (!flag1) {
/* 156 */               flag = false;
/*     */             }
/* 158 */           } else if (p_179533_1_.getSizeInventory() == (tileentitybanner$enumbannerpattern.getCraftingLayers()).length * tileentitybanner$enumbannerpattern.getCraftingLayers()[0].length()) {
/* 159 */             int j = -1;
/*     */             
/* 161 */             for (int k = 0; k < p_179533_1_.getSizeInventory() && flag; k++) {
/* 162 */               int l = k / 3;
/* 163 */               int i1 = k % 3;
/* 164 */               ItemStack itemstack1 = p_179533_1_.getStackInSlot(k);
/*     */               
/* 166 */               if (itemstack1 != null && itemstack1.getItem() != Items.banner) {
/* 167 */                 if (itemstack1.getItem() != Items.dye) {
/* 168 */                   flag = false;
/*     */                   
/*     */                   break;
/*     */                 } 
/* 172 */                 if (j != -1 && j != itemstack1.getMetadata()) {
/* 173 */                   flag = false;
/*     */                   
/*     */                   break;
/*     */                 } 
/* 177 */                 if (tileentitybanner$enumbannerpattern.getCraftingLayers()[l].charAt(i1) == ' ') {
/* 178 */                   flag = false;
/*     */                   
/*     */                   break;
/*     */                 } 
/* 182 */                 j = itemstack1.getMetadata();
/* 183 */               } else if (tileentitybanner$enumbannerpattern.getCraftingLayers()[l].charAt(i1) != ' ') {
/* 184 */                 flag = false;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } else {
/* 189 */             flag = false;
/*     */           } 
/*     */           
/* 192 */           if (flag) {
/* 193 */             return tileentitybanner$enumbannerpattern;
/*     */           }
/*     */         } 
/*     */         b++; }
/*     */       
/* 198 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   static class RecipeDuplicatePattern
/*     */     implements IRecipe {
/*     */     private RecipeDuplicatePattern() {}
/*     */     
/*     */     public boolean matches(InventoryCrafting inv, World worldIn) {
/* 207 */       ItemStack itemstack = null;
/* 208 */       ItemStack itemstack1 = null;
/*     */       
/* 210 */       for (int i = 0; i < inv.getSizeInventory(); i++) {
/* 211 */         ItemStack itemstack2 = inv.getStackInSlot(i);
/*     */         
/* 213 */         if (itemstack2 != null) {
/* 214 */           if (itemstack2.getItem() != Items.banner) {
/* 215 */             return false;
/*     */           }
/*     */           
/* 218 */           if (itemstack != null && itemstack1 != null) {
/* 219 */             return false;
/*     */           }
/*     */           
/* 222 */           int j = TileEntityBanner.getBaseColor(itemstack2);
/* 223 */           boolean flag = (TileEntityBanner.getPatterns(itemstack2) > 0);
/*     */           
/* 225 */           if (itemstack != null) {
/* 226 */             if (flag) {
/* 227 */               return false;
/*     */             }
/*     */             
/* 230 */             if (j != TileEntityBanner.getBaseColor(itemstack)) {
/* 231 */               return false;
/*     */             }
/*     */             
/* 234 */             itemstack1 = itemstack2;
/* 235 */           } else if (itemstack1 != null) {
/* 236 */             if (!flag) {
/* 237 */               return false;
/*     */             }
/*     */             
/* 240 */             if (j != TileEntityBanner.getBaseColor(itemstack1)) {
/* 241 */               return false;
/*     */             }
/*     */             
/* 244 */             itemstack = itemstack2;
/* 245 */           } else if (flag) {
/* 246 */             itemstack = itemstack2;
/*     */           } else {
/* 248 */             itemstack1 = itemstack2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 253 */       return (itemstack != null && itemstack1 != null);
/*     */     }
/*     */     
/*     */     public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 257 */       for (int i = 0; i < inv.getSizeInventory(); i++) {
/* 258 */         ItemStack itemstack = inv.getStackInSlot(i);
/*     */         
/* 260 */         if (itemstack != null && TileEntityBanner.getPatterns(itemstack) > 0) {
/* 261 */           ItemStack itemstack1 = itemstack.copy();
/* 262 */           itemstack1.stackSize = 1;
/* 263 */           return itemstack1;
/*     */         } 
/*     */       } 
/*     */       
/* 267 */       return null;
/*     */     }
/*     */     
/*     */     public int getRecipeSize() {
/* 271 */       return 2;
/*     */     }
/*     */     
/*     */     public ItemStack getRecipeOutput() {
/* 275 */       return null;
/*     */     }
/*     */     
/*     */     public ItemStack[] getRemainingItems(InventoryCrafting inv) {
/* 279 */       ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*     */       
/* 281 */       for (int i = 0; i < aitemstack.length; i++) {
/* 282 */         ItemStack itemstack = inv.getStackInSlot(i);
/*     */         
/* 284 */         if (itemstack != null) {
/* 285 */           if (itemstack.getItem().hasContainerItem()) {
/* 286 */             aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
/* 287 */           } else if (itemstack.hasTagCompound() && TileEntityBanner.getPatterns(itemstack) > 0) {
/* 288 */             aitemstack[i] = itemstack.copy();
/* 289 */             (aitemstack[i]).stackSize = 1;
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 294 */       return aitemstack;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\crafting\RecipesBanners.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */