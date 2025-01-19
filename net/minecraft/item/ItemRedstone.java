/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemRedstone
/*    */   extends Item {
/*    */   public ItemRedstone() {
/* 14 */     setCreativeTab(CreativeTabs.tabRedstone);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 21 */     boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
/* 22 */     BlockPos blockpos = flag ? pos : pos.offset(side);
/*    */     
/* 24 */     if (!playerIn.canPlayerEdit(blockpos, side, stack)) {
/* 25 */       return false;
/*    */     }
/* 27 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */     
/* 29 */     if (!worldIn.canBlockBePlaced(block, blockpos, false, side, null, stack))
/* 30 */       return false; 
/* 31 */     if (Blocks.redstone_wire.canPlaceBlockAt(worldIn, blockpos)) {
/* 32 */       stack.stackSize--;
/* 33 */       worldIn.setBlockState(blockpos, Blocks.redstone_wire.getDefaultState());
/* 34 */       return true;
/*    */     } 
/* 36 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemRedstone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */