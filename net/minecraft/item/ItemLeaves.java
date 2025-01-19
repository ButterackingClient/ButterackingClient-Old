/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockLeaves;
/*    */ 
/*    */ public class ItemLeaves
/*    */   extends ItemBlock {
/*    */   public ItemLeaves(BlockLeaves block) {
/*  9 */     super((Block)block);
/* 10 */     this.leaves = block;
/* 11 */     setMaxDamage(0);
/* 12 */     setHasSubtypes(true);
/*    */   }
/*    */ 
/*    */   
/*    */   private final BlockLeaves leaves;
/*    */ 
/*    */   
/*    */   public int getMetadata(int damage) {
/* 20 */     return damage | 0x4;
/*    */   }
/*    */   
/*    */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/* 24 */     return this.leaves.getRenderColor(this.leaves.getStateFromMeta(stack.getMetadata()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName(ItemStack stack) {
/* 32 */     return String.valueOf(getUnlocalizedName()) + "." + this.leaves.getWoodType(stack.getMetadata()).getUnlocalizedName();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemLeaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */