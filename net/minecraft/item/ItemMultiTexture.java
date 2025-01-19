/*    */ package net.minecraft.item;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import net.minecraft.block.Block;
/*    */ 
/*    */ public class ItemMultiTexture extends ItemBlock {
/*    */   protected final Block theBlock;
/*    */   protected final Function<ItemStack, String> nameFunction;
/*    */   
/*    */   public ItemMultiTexture(Block block, Block block2, Function<ItemStack, String> nameFunction) {
/* 11 */     super(block);
/* 12 */     this.theBlock = block2;
/* 13 */     this.nameFunction = nameFunction;
/* 14 */     setMaxDamage(0);
/* 15 */     setHasSubtypes(true);
/*    */   }
/*    */   
/*    */   public ItemMultiTexture(Block block, Block block2, String[] namesByMeta) {
/* 19 */     this(block, block2, new Function<ItemStack, String>(namesByMeta) {
/*    */           public String apply(ItemStack p_apply_1_) {
/* 21 */             int i = p_apply_1_.getMetadata();
/*    */             
/* 23 */             if (i < 0 || i >= namesByMeta.length) {
/* 24 */               i = 0;
/*    */             }
/*    */             
/* 27 */             return namesByMeta[i];
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetadata(int damage) {
/* 37 */     return damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnlocalizedName(ItemStack stack) {
/* 45 */     return String.valueOf(getUnlocalizedName()) + "." + (String)this.nameFunction.apply(stack);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemMultiTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */