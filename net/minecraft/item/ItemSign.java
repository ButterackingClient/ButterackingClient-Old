/*    */ package net.minecraft.item;
/*    */ import net.minecraft.block.BlockStandingSign;
/*    */ import net.minecraft.block.BlockWallSign;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntitySign;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSign extends Item {
/*    */   public ItemSign() {
/* 17 */     this.maxStackSize = 16;
/* 18 */     setCreativeTab(CreativeTabs.tabDecorations);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 25 */     if (side == EnumFacing.DOWN)
/* 26 */       return false; 
/* 27 */     if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid()) {
/* 28 */       return false;
/*    */     }
/* 30 */     pos = pos.offset(side);
/*    */     
/* 32 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/* 33 */       return false; 
/* 34 */     if (!Blocks.standing_sign.canPlaceBlockAt(worldIn, pos))
/* 35 */       return false; 
/* 36 */     if (worldIn.isRemote) {
/* 37 */       return true;
/*    */     }
/* 39 */     if (side == EnumFacing.UP) {
/* 40 */       int i = MathHelper.floor_double(((playerIn.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 0xF;
/* 41 */       worldIn.setBlockState(pos, Blocks.standing_sign.getDefaultState().withProperty((IProperty)BlockStandingSign.ROTATION, Integer.valueOf(i)), 3);
/*    */     } else {
/* 43 */       worldIn.setBlockState(pos, Blocks.wall_sign.getDefaultState().withProperty((IProperty)BlockWallSign.FACING, (Comparable)side), 3);
/*    */     } 
/*    */     
/* 46 */     stack.stackSize--;
/* 47 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*    */     
/* 49 */     if (tileentity instanceof TileEntitySign && !ItemBlock.setTileEntityNBT(worldIn, playerIn, pos, stack)) {
/* 50 */       playerIn.openEditSign((TileEntitySign)tileentity);
/*    */     }
/*    */     
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */