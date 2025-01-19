/*    */ package net.minecraft.item;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockSnow;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemSnow extends ItemBlock {
/*    */   public ItemSnow(Block block) {
/* 14 */     super(block);
/* 15 */     setMaxDamage(0);
/* 16 */     setHasSubtypes(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 23 */     if (stack.stackSize == 0)
/* 24 */       return false; 
/* 25 */     if (!playerIn.canPlayerEdit(pos, side, stack)) {
/* 26 */       return false;
/*    */     }
/* 28 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 29 */     Block block = iblockstate.getBlock();
/* 30 */     BlockPos blockpos = pos;
/*    */     
/* 32 */     if ((side != EnumFacing.UP || block != this.block) && !block.isReplaceable(worldIn, pos)) {
/* 33 */       blockpos = pos.offset(side);
/* 34 */       iblockstate = worldIn.getBlockState(blockpos);
/* 35 */       block = iblockstate.getBlock();
/*    */     } 
/*    */     
/* 38 */     if (block == this.block) {
/* 39 */       int i = ((Integer)iblockstate.getValue((IProperty)BlockSnow.LAYERS)).intValue();
/*    */       
/* 41 */       if (i <= 7) {
/* 42 */         IBlockState iblockstate1 = iblockstate.withProperty((IProperty)BlockSnow.LAYERS, Integer.valueOf(i + 1));
/* 43 */         AxisAlignedBB axisalignedbb = this.block.getCollisionBoundingBox(worldIn, blockpos, iblockstate1);
/*    */         
/* 45 */         if (axisalignedbb != null && worldIn.checkNoEntityCollision(axisalignedbb) && worldIn.setBlockState(blockpos, iblockstate1, 2)) {
/* 46 */           worldIn.playSoundEffect((blockpos.getX() + 0.5F), (blockpos.getY() + 0.5F), (blockpos.getZ() + 0.5F), this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0F) / 2.0F, this.block.stepSound.getFrequency() * 0.8F);
/* 47 */           stack.stackSize--;
/* 48 */           return true;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 53 */     return super.onItemUse(stack, playerIn, worldIn, blockpos, side, hitX, hitY, hitZ);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetadata(int damage) {
/* 62 */     return damage;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */