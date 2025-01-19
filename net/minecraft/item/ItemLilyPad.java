/*    */ package net.minecraft.item;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockLiquid;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.stats.StatList;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemLilyPad extends ItemColored {
/*    */   public ItemLilyPad(Block block) {
/* 16 */     super(block, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/* 23 */     MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
/*    */     
/* 25 */     if (movingobjectposition == null) {
/* 26 */       return itemStackIn;
/*    */     }
/* 28 */     if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 29 */       BlockPos blockpos = movingobjectposition.getBlockPos();
/*    */       
/* 31 */       if (!worldIn.isBlockModifiable(playerIn, blockpos)) {
/* 32 */         return itemStackIn;
/*    */       }
/*    */       
/* 35 */       if (!playerIn.canPlayerEdit(blockpos.offset(movingobjectposition.sideHit), movingobjectposition.sideHit, itemStackIn)) {
/* 36 */         return itemStackIn;
/*    */       }
/*    */       
/* 39 */       BlockPos blockpos1 = blockpos.up();
/* 40 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*    */       
/* 42 */       if (iblockstate.getBlock().getMaterial() == Material.water && ((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0 && worldIn.isAirBlock(blockpos1)) {
/* 43 */         worldIn.setBlockState(blockpos1, Blocks.waterlily.getDefaultState());
/*    */         
/* 45 */         if (!playerIn.capabilities.isCreativeMode) {
/* 46 */           itemStackIn.stackSize--;
/*    */         }
/*    */         
/* 49 */         playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
/*    */       } 
/*    */     } 
/*    */     
/* 53 */     return itemStackIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/* 58 */     return Blocks.waterlily.getRenderColor(Blocks.waterlily.getStateFromMeta(stack.getMetadata()));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemLilyPad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */