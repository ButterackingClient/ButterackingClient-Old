/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockBarrier extends Block {
/*    */   protected BlockBarrier() {
/* 10 */     super(Material.barrier);
/* 11 */     setBlockUnbreakable();
/* 12 */     setResistance(6000001.0F);
/* 13 */     disableStats();
/* 14 */     this.translucent = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRenderType() {
/* 21 */     return -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube() {
/* 28 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getAmbientOcclusionLightValue() {
/* 35 */     return 1.0F;
/*    */   }
/*    */   
/*    */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockBarrier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */