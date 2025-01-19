/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class BlockClay
/*    */   extends Block {
/*    */   public BlockClay() {
/* 13 */     super(Material.clay);
/* 14 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 21 */     return Items.clay_ball;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(Random random) {
/* 28 */     return 4;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockClay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */