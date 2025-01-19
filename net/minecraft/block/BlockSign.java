/*    */ package net.minecraft.block;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntitySign;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockSign
/*    */   extends BlockContainer {
/*    */   protected BlockSign() {
/* 20 */     super(Material.wood);
/* 21 */     float f = 0.25F;
/* 22 */     float f1 = 1.0F;
/* 23 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
/*    */   }
/*    */   
/*    */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 27 */     return null;
/*    */   }
/*    */   
/*    */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/* 31 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 32 */     return super.getSelectedBoundingBox(worldIn, pos);
/*    */   }
/*    */   
/*    */   public boolean isFullCube() {
/* 36 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube() {
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canSpawnInBlock() {
/* 54 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 61 */     return (TileEntity)new TileEntitySign();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 68 */     return Items.sign;
/*    */   }
/*    */   
/*    */   public Item getItem(World worldIn, BlockPos pos) {
/* 72 */     return Items.sign;
/*    */   }
/*    */   
/*    */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 76 */     if (worldIn.isRemote) {
/* 77 */       return true;
/*    */     }
/* 79 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 80 */     return (tileentity instanceof TileEntitySign) ? ((TileEntitySign)tileentity).executeCommand(playerIn) : false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 85 */     return (!hasInvalidNeighbor(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */