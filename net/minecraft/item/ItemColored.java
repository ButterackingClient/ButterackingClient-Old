/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ 
/*    */ public class ItemColored extends ItemBlock {
/*    */   private final Block coloredBlock;
/*    */   private String[] subtypeNames;
/*    */   
/*    */   public ItemColored(Block block, boolean hasSubtypes) {
/* 10 */     super(block);
/* 11 */     this.coloredBlock = block;
/*    */     
/* 13 */     if (hasSubtypes) {
/* 14 */       setMaxDamage(0);
/* 15 */       setHasSubtypes(true);
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/* 20 */     return this.coloredBlock.getRenderColor(this.coloredBlock.getStateFromMeta(stack.getMetadata()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetadata(int damage) {
/* 28 */     return damage;
/*    */   }
/*    */   
/*    */   public ItemColored setSubtypeNames(String[] names) {
/* 32 */     this.subtypeNames = names;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName(ItemStack stack) {
/* 41 */     if (this.subtypeNames == null) {
/* 42 */       return super.getUnlocalizedName(stack);
/*    */     }
/* 44 */     int i = stack.getMetadata();
/* 45 */     return (i >= 0 && i < this.subtypeNames.length) ? (String.valueOf(super.getUnlocalizedName(stack)) + "." + this.subtypeNames[i]) : super.getUnlocalizedName(stack);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemColored.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */