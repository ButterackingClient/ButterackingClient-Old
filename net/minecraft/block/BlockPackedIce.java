/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ 
/*    */ public class BlockPackedIce
/*    */   extends Block {
/*    */   public BlockPackedIce() {
/* 10 */     super(Material.packedIce);
/* 11 */     this.slipperiness = 0.98F;
/* 12 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(Random random) {
/* 19 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockPackedIce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */