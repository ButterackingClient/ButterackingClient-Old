/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityEndPortal;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockEndPortal
/*    */   extends BlockContainer {
/*    */   protected BlockEndPortal(Material materialIn) {
/* 22 */     super(materialIn);
/* 23 */     setLightLevel(1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 30 */     return (TileEntity)new TileEntityEndPortal();
/*    */   }
/*    */   
/*    */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 34 */     float f = 0.0625F;
/* 35 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
/*    */   }
/*    */   
/*    */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 39 */     return (side == EnumFacing.DOWN) ? super.shouldSideBeRendered(worldIn, pos, side) : false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube() {
/* 52 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isFullCube() {
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int quantityDropped(Random random) {
/* 63 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 70 */     if (entityIn.ridingEntity == null && entityIn.riddenByEntity == null && !worldIn.isRemote) {
/* 71 */       entityIn.travelToDimension(1);
/*    */     }
/*    */   }
/*    */   
/*    */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 76 */     double d0 = (pos.getX() + rand.nextFloat());
/* 77 */     double d1 = (pos.getY() + 0.8F);
/* 78 */     double d2 = (pos.getZ() + rand.nextFloat());
/* 79 */     double d3 = 0.0D;
/* 80 */     double d4 = 0.0D;
/* 81 */     double d5 = 0.0D;
/* 82 */     worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*    */   }
/*    */   
/*    */   public Item getItem(World worldIn, BlockPos pos) {
/* 86 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MapColor getMapColor(IBlockState state) {
/* 93 */     return MapColor.blackColor;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockEndPortal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */