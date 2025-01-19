/*    */ package net.minecraft.item;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockBed;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemBed extends Item {
/*    */   public ItemBed() {
/* 16 */     setCreativeTab(CreativeTabs.tabDecorations);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 23 */     if (worldIn.isRemote)
/* 24 */       return true; 
/* 25 */     if (side != EnumFacing.UP) {
/* 26 */       return false;
/*    */     }
/* 28 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 29 */     Block block = iblockstate.getBlock();
/* 30 */     boolean flag = block.isReplaceable(worldIn, pos);
/*    */     
/* 32 */     if (!flag) {
/* 33 */       pos = pos.up();
/*    */     }
/*    */     
/* 36 */     int i = MathHelper.floor_double((playerIn.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 37 */     EnumFacing enumfacing = EnumFacing.getHorizontal(i);
/* 38 */     BlockPos blockpos = pos.offset(enumfacing);
/*    */     
/* 40 */     if (playerIn.canPlayerEdit(pos, side, stack) && playerIn.canPlayerEdit(blockpos, side, stack)) {
/* 41 */       boolean flag1 = worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
/* 42 */       boolean flag2 = !(!flag && !worldIn.isAirBlock(pos));
/* 43 */       boolean flag3 = !(!flag1 && !worldIn.isAirBlock(blockpos));
/*    */       
/* 45 */       if (flag2 && flag3 && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos.down())) {
/* 46 */         IBlockState iblockstate1 = Blocks.bed.getDefaultState().withProperty((IProperty)BlockBed.OCCUPIED, Boolean.valueOf(false)).withProperty((IProperty)BlockBed.FACING, (Comparable)enumfacing).withProperty((IProperty)BlockBed.PART, (Comparable)BlockBed.EnumPartType.FOOT);
/*    */         
/* 48 */         if (worldIn.setBlockState(pos, iblockstate1, 3)) {
/* 49 */           IBlockState iblockstate2 = iblockstate1.withProperty((IProperty)BlockBed.PART, (Comparable)BlockBed.EnumPartType.HEAD);
/* 50 */           worldIn.setBlockState(blockpos, iblockstate2, 3);
/*    */         } 
/*    */         
/* 53 */         stack.stackSize--;
/* 54 */         return true;
/*    */       } 
/* 56 */       return false;
/*    */     } 
/*    */     
/* 59 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemBed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */