/*   */ package net.minecraft.dispenser;
/*   */ 
/*   */ import net.minecraft.item.ItemStack;
/*   */ 
/*   */ public interface IBehaviorDispenseItem {
/* 6 */   public static final IBehaviorDispenseItem itemDispenseBehaviorProvider = new IBehaviorDispenseItem() {
/*   */       public ItemStack dispense(IBlockSource source, ItemStack stack) {
/* 8 */         return stack;
/*   */       }
/*   */     };
/*   */   
/*   */   ItemStack dispense(IBlockSource paramIBlockSource, ItemStack paramItemStack);
/*   */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\dispenser\IBehaviorDispenseItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */