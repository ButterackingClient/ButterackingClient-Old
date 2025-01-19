/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.ItemEditableBook;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class RecipeBookCloning
/*     */   implements IRecipe
/*     */ {
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  15 */     int i = 0;
/*  16 */     ItemStack itemstack = null;
/*     */     
/*  18 */     for (int j = 0; j < inv.getSizeInventory(); j++) {
/*  19 */       ItemStack itemstack1 = inv.getStackInSlot(j);
/*     */       
/*  21 */       if (itemstack1 != null) {
/*  22 */         if (itemstack1.getItem() == Items.written_book) {
/*  23 */           if (itemstack != null) {
/*  24 */             return false;
/*     */           }
/*     */           
/*  27 */           itemstack = itemstack1;
/*     */         } else {
/*  29 */           if (itemstack1.getItem() != Items.writable_book) {
/*  30 */             return false;
/*     */           }
/*     */           
/*  33 */           i++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  38 */     return (itemstack != null && i > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  45 */     int i = 0;
/*  46 */     ItemStack itemstack = null;
/*     */     
/*  48 */     for (int j = 0; j < inv.getSizeInventory(); j++) {
/*  49 */       ItemStack itemstack1 = inv.getStackInSlot(j);
/*     */       
/*  51 */       if (itemstack1 != null) {
/*  52 */         if (itemstack1.getItem() == Items.written_book) {
/*  53 */           if (itemstack != null) {
/*  54 */             return null;
/*     */           }
/*     */           
/*  57 */           itemstack = itemstack1;
/*     */         } else {
/*  59 */           if (itemstack1.getItem() != Items.writable_book) {
/*  60 */             return null;
/*     */           }
/*     */           
/*  63 */           i++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  68 */     if (itemstack != null && i >= 1 && ItemEditableBook.getGeneration(itemstack) < 2) {
/*  69 */       ItemStack itemstack2 = new ItemStack(Items.written_book, i);
/*  70 */       itemstack2.setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
/*  71 */       itemstack2.getTagCompound().setInteger("generation", ItemEditableBook.getGeneration(itemstack) + 1);
/*     */       
/*  73 */       if (itemstack.hasDisplayName()) {
/*  74 */         itemstack2.setStackDisplayName(itemstack.getDisplayName());
/*     */       }
/*     */       
/*  77 */       return itemstack2;
/*     */     } 
/*  79 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeSize() {
/*  87 */     return 9;
/*     */   }
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack[] getRemainingItems(InventoryCrafting inv) {
/*  95 */     ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*     */     
/*  97 */     for (int i = 0; i < aitemstack.length; i++) {
/*  98 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/* 100 */       if (itemstack != null && itemstack.getItem() instanceof ItemEditableBook) {
/* 101 */         aitemstack[i] = itemstack;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 106 */     return aitemstack;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\crafting\RecipeBookCloning.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */