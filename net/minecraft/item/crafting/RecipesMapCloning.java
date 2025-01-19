/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class RecipesMapCloning
/*     */   implements IRecipe
/*     */ {
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  13 */     int i = 0;
/*  14 */     ItemStack itemstack = null;
/*     */     
/*  16 */     for (int j = 0; j < inv.getSizeInventory(); j++) {
/*  17 */       ItemStack itemstack1 = inv.getStackInSlot(j);
/*     */       
/*  19 */       if (itemstack1 != null) {
/*  20 */         if (itemstack1.getItem() == Items.filled_map) {
/*  21 */           if (itemstack != null) {
/*  22 */             return false;
/*     */           }
/*     */           
/*  25 */           itemstack = itemstack1;
/*     */         } else {
/*  27 */           if (itemstack1.getItem() != Items.map) {
/*  28 */             return false;
/*     */           }
/*     */           
/*  31 */           i++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  36 */     return (itemstack != null && i > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  43 */     int i = 0;
/*  44 */     ItemStack itemstack = null;
/*     */     
/*  46 */     for (int j = 0; j < inv.getSizeInventory(); j++) {
/*  47 */       ItemStack itemstack1 = inv.getStackInSlot(j);
/*     */       
/*  49 */       if (itemstack1 != null) {
/*  50 */         if (itemstack1.getItem() == Items.filled_map) {
/*  51 */           if (itemstack != null) {
/*  52 */             return null;
/*     */           }
/*     */           
/*  55 */           itemstack = itemstack1;
/*     */         } else {
/*  57 */           if (itemstack1.getItem() != Items.map) {
/*  58 */             return null;
/*     */           }
/*     */           
/*  61 */           i++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  66 */     if (itemstack != null && i >= 1) {
/*  67 */       ItemStack itemstack2 = new ItemStack((Item)Items.filled_map, i + 1, itemstack.getMetadata());
/*     */       
/*  69 */       if (itemstack.hasDisplayName()) {
/*  70 */         itemstack2.setStackDisplayName(itemstack.getDisplayName());
/*     */       }
/*     */       
/*  73 */       return itemstack2;
/*     */     } 
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeSize() {
/*  83 */     return 9;
/*     */   }
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/*  87 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack[] getRemainingItems(InventoryCrafting inv) {
/*  91 */     ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*     */     
/*  93 */     for (int i = 0; i < aitemstack.length; i++) {
/*  94 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/*  96 */       if (itemstack != null && itemstack.getItem().hasContainerItem()) {
/*  97 */         aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
/*     */       }
/*     */     } 
/*     */     
/* 101 */     return aitemstack;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\crafting\RecipesMapCloning.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */