/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSeedFood
/*    */   extends ItemFood
/*    */ {
/*    */   private Block crops;
/*    */   private Block soilId;
/*    */   
/*    */   public ItemSeedFood(int healAmount, float saturation, Block crops, Block soil) {
/* 18 */     super(healAmount, saturation, false);
/* 19 */     this.crops = crops;
/* 20 */     this.soilId = soil;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 27 */     if (side != EnumFacing.UP)
/* 28 */       return false; 
/* 29 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
/* 30 */       return false; 
/* 31 */     if (worldIn.getBlockState(pos).getBlock() == this.soilId && worldIn.isAirBlock(pos.up())) {
/* 32 */       worldIn.setBlockState(pos.up(), this.crops.getDefaultState());
/* 33 */       stack.stackSize--;
/* 34 */       return true;
/*    */     } 
/* 36 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemSeedFood.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */