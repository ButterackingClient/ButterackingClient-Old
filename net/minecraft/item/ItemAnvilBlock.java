/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ 
/*    */ public class ItemAnvilBlock extends ItemMultiTexture {
/*    */   public ItemAnvilBlock(Block block) {
/*  7 */     super(block, block, new String[] { "intact", "slightlyDamaged", "veryDamaged" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetadata(int damage) {
/* 15 */     return damage << 2;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemAnvilBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */