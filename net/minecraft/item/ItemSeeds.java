/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSeeds
/*    */   extends Item
/*    */ {
/*    */   private Block crops;
/*    */   private Block soilBlockID;
/*    */   
/*    */   public ItemSeeds(Block crops, Block soil) {
/* 19 */     this.crops = crops;
/* 20 */     this.soilBlockID = soil;
/* 21 */     setCreativeTab(CreativeTabs.tabMaterials);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 28 */     if (side != EnumFacing.UP)
/* 29 */       return false; 
/* 30 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
/* 31 */       return false; 
/* 32 */     if (worldIn.getBlockState(pos).getBlock() == this.soilBlockID && worldIn.isAirBlock(pos.up())) {
/* 33 */       worldIn.setBlockState(pos.up(), this.crops.getDefaultState());
/* 34 */       stack.stackSize--;
/* 35 */       return true;
/*    */     } 
/* 37 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemSeeds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */