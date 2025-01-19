/*     */ package net.minecraft.item.crafting;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.inventory.InventoryCrafting;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipeRepairItem
/*     */   implements IRecipe
/*     */ {
/*     */   public boolean matches(InventoryCrafting inv, World worldIn) {
/*  17 */     List<ItemStack> list = Lists.newArrayList();
/*     */     
/*  19 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/*  20 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/*  22 */       if (itemstack != null) {
/*  23 */         list.add(itemstack);
/*     */         
/*  25 */         if (list.size() > 1) {
/*  26 */           ItemStack itemstack1 = list.get(0);
/*     */           
/*  28 */           if (itemstack.getItem() != itemstack1.getItem() || itemstack1.stackSize != 1 || itemstack.stackSize != 1 || !itemstack1.getItem().isDamageable()) {
/*  29 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  35 */     return (list.size() == 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/*  42 */     List<ItemStack> list = Lists.newArrayList();
/*     */     
/*  44 */     for (int i = 0; i < inv.getSizeInventory(); i++) {
/*  45 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/*  47 */       if (itemstack != null) {
/*  48 */         list.add(itemstack);
/*     */         
/*  50 */         if (list.size() > 1) {
/*  51 */           ItemStack itemstack1 = list.get(0);
/*     */           
/*  53 */           if (itemstack.getItem() != itemstack1.getItem() || itemstack1.stackSize != 1 || itemstack.stackSize != 1 || !itemstack1.getItem().isDamageable()) {
/*  54 */             return null;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  60 */     if (list.size() == 2) {
/*  61 */       ItemStack itemstack2 = list.get(0);
/*  62 */       ItemStack itemstack3 = list.get(1);
/*     */       
/*  64 */       if (itemstack2.getItem() == itemstack3.getItem() && itemstack2.stackSize == 1 && itemstack3.stackSize == 1 && itemstack2.getItem().isDamageable()) {
/*  65 */         Item item = itemstack2.getItem();
/*  66 */         int j = item.getMaxDamage() - itemstack2.getItemDamage();
/*  67 */         int k = item.getMaxDamage() - itemstack3.getItemDamage();
/*  68 */         int l = j + k + item.getMaxDamage() * 5 / 100;
/*  69 */         int i1 = item.getMaxDamage() - l;
/*     */         
/*  71 */         if (i1 < 0) {
/*  72 */           i1 = 0;
/*     */         }
/*     */         
/*  75 */         return new ItemStack(itemstack2.getItem(), 1, i1);
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRecipeSize() {
/*  86 */     return 4;
/*     */   }
/*     */   
/*     */   public ItemStack getRecipeOutput() {
/*  90 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack[] getRemainingItems(InventoryCrafting inv) {
/*  94 */     ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*     */     
/*  96 */     for (int i = 0; i < aitemstack.length; i++) {
/*  97 */       ItemStack itemstack = inv.getStackInSlot(i);
/*     */       
/*  99 */       if (itemstack != null && itemstack.getItem().hasContainerItem()) {
/* 100 */         aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
/*     */       }
/*     */     } 
/*     */     
/* 104 */     return aitemstack;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\crafting\RecipeRepairItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */