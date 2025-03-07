/*    */ package net.minecraft.item.crafting;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class RecipesIngots {
/* 10 */   private Object[][] recipeItems = new Object[][] { { Blocks.gold_block, new ItemStack(Items.gold_ingot, 9) }, { Blocks.iron_block, new ItemStack(Items.iron_ingot, 9) }, { Blocks.diamond_block, new ItemStack(Items.diamond, 9) }, { Blocks.emerald_block, new ItemStack(Items.emerald, 9) }, { Blocks.lapis_block, new ItemStack(Items.dye, 9, EnumDyeColor.BLUE.getDyeDamage()) }, { Blocks.redstone_block, new ItemStack(Items.redstone, 9) }, { Blocks.coal_block, new ItemStack(Items.coal, 9, 0) }, { Blocks.hay_block, new ItemStack(Items.wheat, 9) }, { Blocks.slime_block, new ItemStack(Items.slime_ball, 9) } };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addRecipes(CraftingManager p_77590_1_) {
/* 16 */     for (int i = 0; i < this.recipeItems.length; i++) {
/* 17 */       Block block = (Block)this.recipeItems[i][0];
/* 18 */       ItemStack itemstack = (ItemStack)this.recipeItems[i][1];
/* 19 */       p_77590_1_.addRecipe(new ItemStack(block), new Object[] { "###", "###", "###", Character.valueOf('#'), itemstack });
/* 20 */       p_77590_1_.addRecipe(itemstack, new Object[] { "#", Character.valueOf('#'), block });
/*    */     } 
/*    */     
/* 23 */     p_77590_1_.addRecipe(new ItemStack(Items.gold_ingot), new Object[] { "###", "###", "###", Character.valueOf('#'), Items.gold_nugget });
/* 24 */     p_77590_1_.addRecipe(new ItemStack(Items.gold_nugget, 9), new Object[] { "#", Character.valueOf('#'), Items.gold_ingot });
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\crafting\RecipesIngots.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */