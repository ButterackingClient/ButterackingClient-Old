/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ 
/*    */ public class ItemCloth extends ItemBlock {
/*    */   public ItemCloth(Block block) {
/*  7 */     super(block);
/*  8 */     setMaxDamage(0);
/*  9 */     setHasSubtypes(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetadata(int damage) {
/* 17 */     return damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName(ItemStack stack) {
/* 25 */     return String.valueOf(getUnlocalizedName()) + "." + EnumDyeColor.byMetadata(stack.getMetadata()).getUnlocalizedName();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemCloth.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */