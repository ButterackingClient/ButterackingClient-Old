/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemShears extends Item {
/*    */   public ItemShears() {
/* 13 */     setMaxStackSize(1);
/* 14 */     setMaxDamage(238);
/* 15 */     setCreativeTab(CreativeTabs.tabTools);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
/* 22 */     if (blockIn.getMaterial() != Material.leaves && blockIn != Blocks.web && blockIn != Blocks.tallgrass && blockIn != Blocks.vine && blockIn != Blocks.tripwire && blockIn != Blocks.wool) {
/* 23 */       return super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
/*    */     }
/* 25 */     stack.damageItem(1, playerIn);
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canHarvestBlock(Block blockIn) {
/* 34 */     return !(blockIn != Blocks.web && blockIn != Blocks.redstone_wire && blockIn != Blocks.tripwire);
/*    */   }
/*    */   
/*    */   public float getStrVsBlock(ItemStack stack, Block state) {
/* 38 */     return (state != Blocks.web && state.getMaterial() != Material.leaves) ? ((state == Blocks.wool) ? 5.0F : super.getStrVsBlock(stack, state)) : 15.0F;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemShears.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */