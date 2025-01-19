/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockDirt;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemHoe extends Item {
/*    */   public ItemHoe(Item.ToolMaterial material) {
/* 18 */     this.theToolMaterial = material;
/* 19 */     this.maxStackSize = 1;
/* 20 */     setMaxDamage(material.getMaxUses());
/* 21 */     setCreativeTab(CreativeTabs.tabTools);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Item.ToolMaterial theToolMaterial;
/*    */ 
/*    */   
/*    */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 30 */     if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
/* 31 */       return false;
/*    */     }
/* 33 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 34 */     Block block = iblockstate.getBlock();
/*    */     
/* 36 */     if (side != EnumFacing.DOWN && worldIn.getBlockState(pos.up()).getBlock().getMaterial() == Material.air) {
/* 37 */       if (block == Blocks.grass) {
/* 38 */         return useHoe(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
/*    */       }
/*    */       
/* 41 */       if (block == Blocks.dirt) {
/* 42 */         switch ((BlockDirt.DirtType)iblockstate.getValue((IProperty)BlockDirt.VARIANT)) {
/*    */           case DIRT:
/* 44 */             return useHoe(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
/*    */           
/*    */           case null:
/* 47 */             return useHoe(stack, playerIn, worldIn, pos, Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.DIRT));
/*    */         } 
/*    */       
/*    */       }
/*    */     } 
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean useHoe(ItemStack stack, EntityPlayer player, World worldIn, BlockPos target, IBlockState newState) {
/* 57 */     worldIn.playSoundEffect((target.getX() + 0.5F), (target.getY() + 0.5F), (target.getZ() + 0.5F), (newState.getBlock()).stepSound.getStepSound(), ((newState.getBlock()).stepSound.getVolume() + 1.0F) / 2.0F, (newState.getBlock()).stepSound.getFrequency() * 0.8F);
/*    */     
/* 59 */     if (worldIn.isRemote) {
/* 60 */       return true;
/*    */     }
/* 62 */     worldIn.setBlockState(target, newState);
/* 63 */     stack.damageItem(1, (EntityLivingBase)player);
/* 64 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFull3D() {
/* 72 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMaterialName() {
/* 80 */     return this.theToolMaterial.toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\item\ItemHoe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */