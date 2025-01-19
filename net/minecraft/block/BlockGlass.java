/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.util.EnumWorldBlockLayer;
/*    */ 
/*    */ public class BlockGlass
/*    */   extends BlockBreakable {
/*    */   public BlockGlass(Material materialIn, boolean ignoreSimilarity) {
/* 11 */     super(materialIn, ignoreSimilarity);
/* 12 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(Random random) {
/* 19 */     return 0;
/*    */   }
/*    */   
/*    */   public EnumWorldBlockLayer getBlockLayer() {
/* 23 */     return EnumWorldBlockLayer.CUTOUT;
/*    */   }
/*    */   
/*    */   public boolean isFullCube() {
/* 27 */     return false;
/*    */   }
/*    */   
/*    */   protected boolean canSilkHarvest() {
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockGlass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */