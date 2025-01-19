/*    */ package net.minecraft.item;
/*    */ 
/*    */ 
/*    */ public class ItemBook
/*    */   extends Item
/*    */ {
/*    */   public boolean isItemTool(ItemStack stack) {
/*  8 */     return (stack.stackSize == 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getItemEnchantability() {
/* 15 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemBook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */