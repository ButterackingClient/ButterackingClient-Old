/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockSoulSand extends Block {
/*    */   public BlockSoulSand() {
/* 14 */     super(Material.sand, MapColor.brownColor);
/* 15 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */   
/*    */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 19 */     float f = 0.125F;
/* 20 */     return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), ((pos.getY() + 1) - f), (pos.getZ() + 1));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 27 */     entityIn.motionX *= 0.4D;
/* 28 */     entityIn.motionZ *= 0.4D;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockSoulSand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */