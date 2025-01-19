/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockLadder extends Block {
/*  18 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*     */   
/*     */   protected BlockLadder() {
/*  21 */     super(Material.circuits);
/*  22 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  23 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  27 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  28 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  32 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  33 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  37 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  39 */     if (iblockstate.getBlock() == this) {
/*  40 */       float f = 0.125F;
/*     */       
/*  42 */       switch ((EnumFacing)iblockstate.getValue((IProperty)FACING)) {
/*     */         case NORTH:
/*  44 */           setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */           return;
/*     */         
/*     */         case SOUTH:
/*  48 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */           return;
/*     */         
/*     */         case WEST:
/*  52 */           setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */           return;
/*     */       } 
/*     */ 
/*     */       
/*  57 */       setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  66 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  70 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  74 */     return worldIn.getBlockState(pos.west()).getBlock().isNormalCube() ? true : (worldIn.getBlockState(pos.east()).getBlock().isNormalCube() ? true : (worldIn.getBlockState(pos.north()).getBlock().isNormalCube() ? true : worldIn.getBlockState(pos.south()).getBlock().isNormalCube()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  82 */     if (facing.getAxis().isHorizontal() && canBlockStay(worldIn, pos, facing)) {
/*  83 */       return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing);
/*     */     }
/*  85 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*  86 */       if (canBlockStay(worldIn, pos, enumfacing)) {
/*  87 */         return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */       }
/*     */     } 
/*     */     
/*  91 */     return getDefaultState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  99 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 101 */     if (!canBlockStay(worldIn, pos, enumfacing)) {
/* 102 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 103 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */     
/* 106 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/*     */   }
/*     */   
/*     */   protected boolean canBlockStay(World worldIn, BlockPos pos, EnumFacing facing) {
/* 110 */     return worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock().isNormalCube();
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 114 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 121 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 123 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
/* 124 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 127 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 134 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 138 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockLadder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */