/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockSnow
/*     */   extends Block {
/*  26 */   public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 8);
/*     */   
/*     */   protected BlockSnow() {
/*  29 */     super(Material.snow);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)LAYERS, Integer.valueOf(1)));
/*  31 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  32 */     setTickRandomly(true);
/*  33 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  34 */     setBlockBoundsForItemRender();
/*     */   }
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  38 */     return (((Integer)worldIn.getBlockState(pos).getValue((IProperty)LAYERS)).intValue() < 5);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  42 */     int i = ((Integer)state.getValue((IProperty)LAYERS)).intValue() - 1;
/*  43 */     float f = 0.125F;
/*  44 */     return new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, (pos.getY() + i * f), pos.getZ() + this.maxZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  62 */     getBoundsForLayers(0);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  66 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  67 */     getBoundsForLayers(((Integer)iblockstate.getValue((IProperty)LAYERS)).intValue());
/*     */   }
/*     */   
/*     */   protected void getBoundsForLayers(int p_150154_1_) {
/*  71 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, p_150154_1_ / 8.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  75 */     IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*  76 */     Block block = iblockstate.getBlock();
/*  77 */     return (block != Blocks.ice && block != Blocks.packed_ice) ? ((block.getMaterial() == Material.leaves) ? true : ((block == this && ((Integer)iblockstate.getValue((IProperty)LAYERS)).intValue() >= 7) ? true : ((block.isOpaqueCube() && block.blockMaterial.blocksMovement())))) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  84 */     checkAndDropBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   private boolean checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  88 */     if (!canPlaceBlockAt(worldIn, pos)) {
/*  89 */       dropBlockAsItem(worldIn, pos, state, 0);
/*  90 */       worldIn.setBlockToAir(pos);
/*  91 */       return false;
/*     */     } 
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/*  98 */     spawnAsEntity(worldIn, pos, new ItemStack(Items.snowball, ((Integer)state.getValue((IProperty)LAYERS)).intValue() + 1, 0));
/*  99 */     worldIn.setBlockToAir(pos);
/* 100 */     player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 107 */     return Items.snowball;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 114 */     return 0;
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 118 */     if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11) {
/* 119 */       dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
/* 120 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 125 */     return (side == EnumFacing.UP) ? true : super.shouldSideBeRendered(worldIn, pos, side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 132 */     return getDefaultState().withProperty((IProperty)LAYERS, Integer.valueOf((meta & 0x7) + 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/* 139 */     return (((Integer)worldIn.getBlockState(pos).getValue((IProperty)LAYERS)).intValue() == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 146 */     return ((Integer)state.getValue((IProperty)LAYERS)).intValue() - 1;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 150 */     return new BlockState(this, new IProperty[] { (IProperty)LAYERS });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */