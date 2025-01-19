/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFenceGate extends BlockDirectional {
/*  19 */   public static final PropertyBool OPEN = PropertyBool.create("open");
/*  20 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  21 */   public static final PropertyBool IN_WALL = PropertyBool.create("in_wall");
/*     */   
/*     */   public BlockFenceGate(BlockPlanks.EnumType p_i46394_1_) {
/*  24 */     super(Material.wood, p_i46394_1_.getMapColor());
/*  25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)OPEN, Boolean.valueOf(false)).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)IN_WALL, Boolean.valueOf(false)));
/*  26 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  34 */     EnumFacing.Axis enumfacing$axis = ((EnumFacing)state.getValue((IProperty)FACING)).getAxis();
/*     */     
/*  36 */     if ((enumfacing$axis == EnumFacing.Axis.Z && (worldIn.getBlockState(pos.west()).getBlock() == Blocks.cobblestone_wall || worldIn.getBlockState(pos.east()).getBlock() == Blocks.cobblestone_wall)) || (enumfacing$axis == EnumFacing.Axis.X && (worldIn.getBlockState(pos.north()).getBlock() == Blocks.cobblestone_wall || worldIn.getBlockState(pos.south()).getBlock() == Blocks.cobblestone_wall))) {
/*  37 */       state = state.withProperty((IProperty)IN_WALL, Boolean.valueOf(true));
/*     */     }
/*     */     
/*  40 */     return state;
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  44 */     return worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid() ? super.canPlaceBlockAt(worldIn, pos) : false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  48 */     if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue()) {
/*  49 */       return null;
/*     */     }
/*  51 */     EnumFacing.Axis enumfacing$axis = ((EnumFacing)state.getValue((IProperty)FACING)).getAxis();
/*  52 */     return (enumfacing$axis == EnumFacing.Axis.Z) ? new AxisAlignedBB(pos.getX(), pos.getY(), (pos.getZ() + 0.375F), (pos.getX() + 1), (pos.getY() + 1.5F), (pos.getZ() + 0.625F)) : new AxisAlignedBB((pos.getX() + 0.375F), pos.getY(), pos.getZ(), (pos.getX() + 0.625F), (pos.getY() + 1.5F), (pos.getZ() + 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  57 */     EnumFacing.Axis enumfacing$axis = ((EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING)).getAxis();
/*     */     
/*  59 */     if (enumfacing$axis == EnumFacing.Axis.Z) {
/*  60 */       setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
/*     */     } else {
/*  62 */       setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  70 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  74 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  78 */     return ((Boolean)worldIn.getBlockState(pos).getValue((IProperty)OPEN)).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  86 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing()).withProperty((IProperty)OPEN, Boolean.valueOf(false)).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)IN_WALL, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  90 */     if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue()) {
/*  91 */       state = state.withProperty((IProperty)OPEN, Boolean.valueOf(false));
/*  92 */       worldIn.setBlockState(pos, state, 2);
/*     */     } else {
/*  94 */       EnumFacing enumfacing = EnumFacing.fromAngle(playerIn.rotationYaw);
/*     */       
/*  96 */       if (state.getValue((IProperty)FACING) == enumfacing.getOpposite()) {
/*  97 */         state = state.withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */       }
/*     */       
/* 100 */       state = state.withProperty((IProperty)OPEN, Boolean.valueOf(true));
/* 101 */       worldIn.setBlockState(pos, state, 2);
/*     */     } 
/*     */     
/* 104 */     worldIn.playAuxSFXAtEntity(playerIn, ((Boolean)state.getValue((IProperty)OPEN)).booleanValue() ? 1003 : 1006, pos, 0);
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 112 */     if (!worldIn.isRemote) {
/* 113 */       boolean flag = worldIn.isBlockPowered(pos);
/*     */       
/* 115 */       if (flag || neighborBlock.canProvidePower()) {
/* 116 */         if (flag && !((Boolean)state.getValue((IProperty)OPEN)).booleanValue() && !((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 117 */           worldIn.setBlockState(pos, state.withProperty((IProperty)OPEN, Boolean.valueOf(true)).withProperty((IProperty)POWERED, Boolean.valueOf(true)), 2);
/* 118 */           worldIn.playAuxSFXAtEntity(null, 1003, pos, 0);
/* 119 */         } else if (!flag && ((Boolean)state.getValue((IProperty)OPEN)).booleanValue() && ((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 120 */           worldIn.setBlockState(pos, state.withProperty((IProperty)OPEN, Boolean.valueOf(false)).withProperty((IProperty)POWERED, Boolean.valueOf(false)), 2);
/* 121 */           worldIn.playAuxSFXAtEntity(null, 1006, pos, 0);
/* 122 */         } else if (flag != ((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 123 */           worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(flag)), 2);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 137 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta)).withProperty((IProperty)OPEN, Boolean.valueOf(((meta & 0x4) != 0))).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) != 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 144 */     int i = 0;
/* 145 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 147 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 148 */       i |= 0x8;
/*     */     }
/*     */     
/* 151 */     if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue()) {
/* 152 */       i |= 0x4;
/*     */     }
/*     */     
/* 155 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 159 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)OPEN, (IProperty)POWERED, (IProperty)IN_WALL });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockFenceGate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */