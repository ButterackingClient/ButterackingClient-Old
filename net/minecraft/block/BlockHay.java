/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.BlockState;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockHay extends BlockRotatedPillar {
/*    */   public BlockHay() {
/* 18 */     super(Material.grass, MapColor.yellowColor);
/* 19 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AXIS, (Comparable)EnumFacing.Axis.Y));
/* 20 */     setCreativeTab(CreativeTabs.tabBlock);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getStateFromMeta(int meta) {
/* 27 */     EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Y;
/* 28 */     int i = meta & 0xC;
/*    */     
/* 30 */     if (i == 4) {
/* 31 */       enumfacing$axis = EnumFacing.Axis.X;
/* 32 */     } else if (i == 8) {
/* 33 */       enumfacing$axis = EnumFacing.Axis.Z;
/*    */     } 
/*    */     
/* 36 */     return getDefaultState().withProperty((IProperty)AXIS, (Comparable)enumfacing$axis);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 43 */     int i = 0;
/* 44 */     EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)state.getValue((IProperty)AXIS);
/*    */     
/* 46 */     if (enumfacing$axis == EnumFacing.Axis.X) {
/* 47 */       i |= 0x4;
/* 48 */     } else if (enumfacing$axis == EnumFacing.Axis.Z) {
/* 49 */       i |= 0x8;
/*    */     } 
/*    */     
/* 52 */     return i;
/*    */   }
/*    */   
/*    */   protected BlockState createBlockState() {
/* 56 */     return new BlockState(this, new IProperty[] { (IProperty)AXIS });
/*    */   }
/*    */   
/*    */   protected ItemStack createStackedBlock(IBlockState state) {
/* 60 */     return new ItemStack(Item.getItemFromBlock(this), 1, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 68 */     return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty((IProperty)AXIS, (Comparable)facing.getAxis());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockHay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */