/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemLead extends Item {
/*    */   public ItemLead() {
/* 16 */     setCreativeTab(CreativeTabs.tabTools);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 23 */     Block block = worldIn.getBlockState(pos).getBlock();
/*    */     
/* 25 */     if (block instanceof net.minecraft.block.BlockFence) {
/* 26 */       if (worldIn.isRemote) {
/* 27 */         return true;
/*    */       }
/* 29 */       attachToFence(playerIn, worldIn, pos);
/* 30 */       return true;
/*    */     } 
/*    */     
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean attachToFence(EntityPlayer player, World worldIn, BlockPos fence) {
/* 38 */     EntityLeashKnot entityleashknot = EntityLeashKnot.getKnotForPosition(worldIn, fence);
/* 39 */     boolean flag = false;
/* 40 */     double d0 = 7.0D;
/* 41 */     int i = fence.getX();
/* 42 */     int j = fence.getY();
/* 43 */     int k = fence.getZ();
/*    */     
/* 45 */     for (EntityLiving entityliving : worldIn.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(i - d0, j - d0, k - d0, i + d0, j + d0, k + d0))) {
/* 46 */       if (entityliving.getLeashed() && entityliving.getLeashedToEntity() == player) {
/* 47 */         if (entityleashknot == null) {
/* 48 */           entityleashknot = EntityLeashKnot.createKnot(worldIn, fence);
/*    */         }
/*    */         
/* 51 */         entityliving.setLeashedToEntity((Entity)entityleashknot, true);
/* 52 */         flag = true;
/*    */       } 
/*    */     } 
/*    */     
/* 56 */     return flag;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemLead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */