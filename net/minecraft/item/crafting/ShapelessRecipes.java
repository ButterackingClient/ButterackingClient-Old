/*    */ package net.minecraft.item.crafting;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.inventory.InventoryCrafting;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShapelessRecipes
/*    */   implements IRecipe
/*    */ {
/*    */   private final ItemStack recipeOutput;
/*    */   private final List<ItemStack> recipeItems;
/*    */   
/*    */   public ShapelessRecipes(ItemStack output, List<ItemStack> inputList) {
/* 19 */     this.recipeOutput = output;
/* 20 */     this.recipeItems = inputList;
/*    */   }
/*    */   
/*    */   public ItemStack getRecipeOutput() {
/* 24 */     return this.recipeOutput;
/*    */   }
/*    */   
/*    */   public ItemStack[] getRemainingItems(InventoryCrafting inv) {
/* 28 */     ItemStack[] aitemstack = new ItemStack[inv.getSizeInventory()];
/*    */     
/* 30 */     for (int i = 0; i < aitemstack.length; i++) {
/* 31 */       ItemStack itemstack = inv.getStackInSlot(i);
/*    */       
/* 33 */       if (itemstack != null && itemstack.getItem().hasContainerItem()) {
/* 34 */         aitemstack[i] = new ItemStack(itemstack.getItem().getContainerItem());
/*    */       }
/*    */     } 
/*    */     
/* 38 */     return aitemstack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(InventoryCrafting inv, World worldIn) {
/* 45 */     List<ItemStack> list = Lists.newArrayList(this.recipeItems);
/*    */     
/* 47 */     for (int i = 0; i < inv.getHeight(); i++) {
/* 48 */       for (int j = 0; j < inv.getWidth(); j++) {
/* 49 */         ItemStack itemstack = inv.getStackInRowAndColumn(j, i);
/*    */         
/* 51 */         if (itemstack != null) {
/* 52 */           boolean flag = false;
/*    */           
/* 54 */           for (ItemStack itemstack1 : list) {
/* 55 */             if (itemstack.getItem() == itemstack1.getItem() && (itemstack1.getMetadata() == 32767 || itemstack.getMetadata() == itemstack1.getMetadata())) {
/* 56 */               flag = true;
/* 57 */               list.remove(itemstack1);
/*    */               
/*    */               break;
/*    */             } 
/*    */           } 
/* 62 */           if (!flag) {
/* 63 */             return false;
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 69 */     return list.isEmpty();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getCraftingResult(InventoryCrafting inv) {
/* 76 */     return this.recipeOutput.copy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRecipeSize() {
/* 83 */     return this.recipeItems.size();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\crafting\ShapelessRecipes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */