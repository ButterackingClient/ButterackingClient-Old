/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemFireball extends Item {
/*    */   public ItemFireball() {
/* 13 */     setCreativeTab(CreativeTabs.tabMisc);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 20 */     if (worldIn.isRemote) {
/* 21 */       return true;
/*    */     }
/* 23 */     pos = pos.offset(side);
/*    */     
/* 25 */     if (!playerIn.canPlayerEdit(pos, side, stack)) {
/* 26 */       return false;
/*    */     }
/* 28 */     if (worldIn.getBlockState(pos).getBlock().getMaterial() == Material.air) {
/* 29 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "item.fireCharge.use", 1.0F, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2F + 1.0F);
/* 30 */       worldIn.setBlockState(pos, Blocks.fire.getDefaultState());
/*    */     } 
/*    */     
/* 33 */     if (!playerIn.capabilities.isCreativeMode) {
/* 34 */       stack.stackSize--;
/*    */     }
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */