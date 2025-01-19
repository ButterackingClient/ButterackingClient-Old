/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShapedRecipes
/*     */   implements IRecipe
/*     */ {
/*     */   private final int recipeWidth;
/*     */   private final int recipeHeight;
/*     */   private final ItemStack[] recipeItems;
/*     */   private final ItemStack recipeOutput;
/*     */   private boolean copyIngredientNBT;
/*     */   
/*     */   public ShapedRecipes(int width, int height, ItemStack[] p_i1917_3_, ItemStack output) {
/*  31 */     this.recipeWidth = width;
/*  32 */     this.recipeHeight = height;
/*  33 */     this.recipeItems = p_i1917_3_;
/*  34 */     this.recipeOutput = output;
/*     */   }
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/*  38 */     return this.recipeOutput;
/*     */   }
/*     */   
/*     */   public ItemStack[] getRemainingItems(InventoryCrafting inv) {
/*  42 */     ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*     */     
/*  44 */     for (int i = 0; i < aitemstack.length; i++) {
/*  45 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/*  47 */       if (itemstack != null && itemstack.getItem().hasContainerItem()) {
/*  48 */         aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
/*     */       }
/*     */     } 
/*     */     
/*  52 */     return aitemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  59 */     for (int i = 0; i <= 3 - this.recipeWidth; i++) {
/*  60 */       for (int j = 0; j <= 3 - this.recipeHeight; j++) {
/*  61 */         if (checkMatch(inv, i, j, true)) {
/*  62 */           return true;
/*     */         }
/*     */         
/*  65 */         if (checkMatch(inv, i, j, false)) {
/*  66 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkMatch(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_) {
/*  78 */     for (int i = 0; i < 3; i++) {
/*  79 */       for (int j = 0; j < 3; j++) {
/*  80 */         int k = i - p_77573_2_;
/*  81 */         int l = j - p_77573_3_;
/*  82 */         ItemStack itemstack = null;
/*     */         
/*  84 */         if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
/*  85 */           if (p_77573_4_) {
/*  86 */             itemstack = this.recipeItems[this.recipeWidth - k - 1 + l * this.recipeWidth];
/*     */           } else {
/*  88 */             itemstack = this.recipeItems[k + l * this.recipeWidth];
/*     */           } 
/*     */         }
/*     */         
/*  92 */         ItemStack itemstack1 = p_77573_1_.getStackInRowAndColumn(i, j);
/*     */         
/*  94 */         if (itemstack1 != null || itemstack != null) {
/*  95 */           if ((itemstack1 == null && itemstack != null) || (itemstack1 != null && itemstack == null)) {
/*  96 */             return false;
/*     */           }
/*     */           
/*  99 */           if (itemstack.getItem() != itemstack1.getItem()) {
/* 100 */             return false;
/*     */           }
/*     */           
/* 103 */           if (itemstack.getMetadata() != 32767 && itemstack.getMetadata() != itemstack1.getMetadata()) {
/* 104 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 117 */     ItemStack itemstack = getRecipeOutput().copy();
/*     */     
/* 119 */     if (this.copyIngredientNBT) {
/* 120 */       for (int i = 0; i < inv.getSizeInventory(); i++) {
/* 121 */         ItemStack itemstack1 = inv.getStackInSlot(i);
/*     */         
/* 123 */         if (itemstack1 != null && itemstack1.hasTagCompound()) {
/* 124 */           itemstack.setTagCompound((NBTTagCompound)itemstack1.getTagCompound().copy());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 129 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeSize() {
/* 136 */     return this.recipeWidth * this.recipeHeight;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\crafting\ShapedRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */