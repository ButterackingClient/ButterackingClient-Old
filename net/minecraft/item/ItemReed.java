/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockSnow;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemReed
/*    */   extends Item {
/*    */   public ItemReed(Block block) {
/* 17 */     this.block = block;
/*    */   }
/*    */ 
/*    */   
/*    */   private Block block;
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 24 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 25 */     Block block = iblockstate.getBlock();
/*    */     
/* 27 */     if (block == Blocks.snow_layer && ((Integer)iblockstate.getValue((IProperty)BlockSnow.LAYERS)).intValue() < 1) {
/* 28 */       side = EnumFacing.UP;
/* 29 */     } else if (!block.isReplaceable(worldIn, pos)) {
/* 30 */       pos = pos.offset(side);
/*    */     } 
/*    */     
/* 33 */     if (!playerIn.canPlayerEdit(pos, side, stack))
/* 34 */       return false; 
/* 35 */     if (stack.stackSize == 0) {
/* 36 */       return false;
/*    */     }
/* 38 */     if (worldIn.canBlockBePlaced(this.block, pos, false, side, null, stack)) {
/* 39 */       IBlockState iblockstate1 = this.block.onBlockPlaced(worldIn, pos, side, hitX, hitY, hitZ, 0, (EntityLivingBase)playerIn);
/*    */       
/* 41 */       if (worldIn.setBlockState(pos, iblockstate1, 3)) {
/* 42 */         iblockstate1 = worldIn.getBlockState(pos);
/*    */         
/* 44 */         if (iblockstate1.getBlock() == this.block) {
/* 45 */           ItemBlock.setTileEntityNBT(worldIn, playerIn, pos, stack);
/* 46 */           iblockstate1.getBlock().onBlockPlacedBy(worldIn, pos, iblockstate1, (EntityLivingBase)playerIn, stack);
/*    */         } 
/*    */         
/* 49 */         worldIn.playSoundEffect((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getFrequency() * 0.8F);
/* 50 */         stack.stackSize--;
/* 51 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemReed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */