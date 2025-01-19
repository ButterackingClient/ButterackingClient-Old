/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class BlockGlowstone
/*    */   extends Block {
/*    */   public BlockGlowstone(Material materialIn) {
/* 15 */     super(materialIn);
/* 16 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDroppedWithBonus(int fortune, Random random) {
/* 23 */     return MathHelper.clamp_int(quantityDropped(random) + random.nextInt(fortune + 1), 1, 4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(Random random) {
/* 30 */     return 2 + random.nextInt(3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 37 */     return Items.glowstone_dust;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MapColor getMapColor(IBlockState state) {
/* 44 */     return MapColor.sandColor;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockGlowstone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */