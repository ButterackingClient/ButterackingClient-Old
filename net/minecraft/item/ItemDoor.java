/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockDoor;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemDoor
/*    */   extends Item {
/*    */   public ItemDoor(Block block) {
/* 16 */     this.block = block;
/* 17 */     setCreativeTab(CreativeTabs.tabRedstone);
/*    */   }
/*    */ 
/*    */   
/*    */   private Block block;
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 24 */     if (side != EnumFacing.UP) {
/* 25 */       return false;
/*    */     }
/* 27 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 28 */     Block block = iblockstate.getBlock();
/*    */     
/* 30 */     if (!block.isReplaceable(worldIn, pos)) {
/* 31 */       pos = pos.offset(side);
/*    */     }
/*    */     
/* 34 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/* 35 */       return false; 
/* 36 */     if (!this.block.canPlaceBlockAt(worldIn, pos)) {
/* 37 */       return false;
/*    */     }
/* 39 */     placeDoor(worldIn, pos, EnumFacing.fromAngle(playerIn.rotationYaw), this.block);
/* 40 */     stack.stackSize--;
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door) {
/* 47 */     BlockPos blockpos = pos.offset(facing.rotateY());
/* 48 */     BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
/* 49 */     int i = (worldIn.getBlockState(blockpos1).getBlock().isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos1.up()).getBlock().isNormalCube() ? 1 : 0);
/* 50 */     int j = (worldIn.getBlockState(blockpos).getBlock().isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos.up()).getBlock().isNormalCube() ? 1 : 0);
/* 51 */     boolean flag = !(worldIn.getBlockState(blockpos1).getBlock() != door && worldIn.getBlockState(blockpos1.up()).getBlock() != door);
/* 52 */     boolean flag1 = !(worldIn.getBlockState(blockpos).getBlock() != door && worldIn.getBlockState(blockpos.up()).getBlock() != door);
/* 53 */     boolean flag2 = false;
/*    */     
/* 55 */     if ((flag && !flag1) || j > i) {
/* 56 */       flag2 = true;
/*    */     }
/*    */     
/* 59 */     BlockPos blockpos2 = pos.up();
/* 60 */     IBlockState iblockstate = door.getDefaultState().withProperty((IProperty)BlockDoor.FACING, (Comparable)facing).withProperty((IProperty)BlockDoor.HINGE, flag2 ? (Comparable)BlockDoor.EnumHingePosition.RIGHT : (Comparable)BlockDoor.EnumHingePosition.LEFT);
/* 61 */     worldIn.setBlockState(pos, iblockstate.withProperty((IProperty)BlockDoor.HALF, (Comparable)BlockDoor.EnumDoorHalf.LOWER), 2);
/* 62 */     worldIn.setBlockState(blockpos2, iblockstate.withProperty((IProperty)BlockDoor.HALF, (Comparable)BlockDoor.EnumDoorHalf.UPPER), 2);
/* 63 */     worldIn.notifyNeighborsOfStateChange(pos, door);
/* 64 */     worldIn.notifyNeighborsOfStateChange(blockpos2, door);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */