/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityPiston;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPistonMoving
/*     */   extends BlockContainer {
/*  25 */   public static final PropertyDirection FACING = BlockPistonExtension.FACING;
/*  26 */   public static final PropertyEnum<BlockPistonExtension.EnumPistonType> TYPE = BlockPistonExtension.TYPE;
/*     */   
/*     */   public BlockPistonMoving() {
/*  29 */     super(Material.piston);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)TYPE, BlockPistonExtension.EnumPistonType.DEFAULT));
/*  31 */     setHardness(-1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/*  38 */     return null;
/*     */   }
/*     */   
/*     */   public static TileEntity newTileEntity(IBlockState state, EnumFacing facing, boolean extending, boolean renderHead) {
/*  42 */     return (TileEntity)new TileEntityPiston(state, facing, extending, renderHead);
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  46 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  48 */     if (tileentity instanceof TileEntityPiston) {
/*  49 */       ((TileEntityPiston)tileentity).clearPistonTileEntity();
/*     */     } else {
/*  51 */       super.breakBlock(worldIn, pos, state);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/*  70 */     BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*  71 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */     
/*  73 */     if (iblockstate.getBlock() instanceof BlockPistonBase && ((Boolean)iblockstate.getValue((IProperty)BlockPistonBase.EXTENDED)).booleanValue()) {
/*  74 */       worldIn.setBlockToAir(blockpos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  86 */     return false;
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  90 */     if (!worldIn.isRemote && worldIn.getTileEntity(pos) == null) {
/*  91 */       worldIn.setBlockToAir(pos);
/*  92 */       return true;
/*     */     } 
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 102 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 109 */     if (!worldIn.isRemote) {
/* 110 */       TileEntityPiston tileentitypiston = getTileEntity((IBlockAccess)worldIn, pos);
/*     */       
/* 112 */       if (tileentitypiston != null) {
/* 113 */         IBlockState iblockstate = tileentitypiston.getPistonState();
/* 114 */         iblockstate.getBlock().dropBlockAsItem(worldIn, pos, iblockstate, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 130 */     if (!worldIn.isRemote) {
/* 131 */       worldIn.getTileEntity(pos);
/*     */     }
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 136 */     TileEntityPiston tileentitypiston = getTileEntity((IBlockAccess)worldIn, pos);
/*     */     
/* 138 */     if (tileentitypiston == null) {
/* 139 */       return null;
/*     */     }
/* 141 */     float f = tileentitypiston.getProgress(0.0F);
/*     */     
/* 143 */     if (tileentitypiston.isExtending()) {
/* 144 */       f = 1.0F - f;
/*     */     }
/*     */     
/* 147 */     return getBoundingBox(worldIn, pos, tileentitypiston.getPistonState(), f, tileentitypiston.getFacing());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 152 */     TileEntityPiston tileentitypiston = getTileEntity(worldIn, pos);
/*     */     
/* 154 */     if (tileentitypiston != null) {
/* 155 */       IBlockState iblockstate = tileentitypiston.getPistonState();
/* 156 */       Block block = iblockstate.getBlock();
/*     */       
/* 158 */       if (block == this || block.getMaterial() == Material.air) {
/*     */         return;
/*     */       }
/*     */       
/* 162 */       float f = tileentitypiston.getProgress(0.0F);
/*     */       
/* 164 */       if (tileentitypiston.isExtending()) {
/* 165 */         f = 1.0F - f;
/*     */       }
/*     */       
/* 168 */       block.setBlockBoundsBasedOnState(worldIn, pos);
/*     */       
/* 170 */       if (block == Blocks.piston || block == Blocks.sticky_piston) {
/* 171 */         f = 0.0F;
/*     */       }
/*     */       
/* 174 */       EnumFacing enumfacing = tileentitypiston.getFacing();
/* 175 */       this.minX = block.getBlockBoundsMinX() - (enumfacing.getFrontOffsetX() * f);
/* 176 */       this.minY = block.getBlockBoundsMinY() - (enumfacing.getFrontOffsetY() * f);
/* 177 */       this.minZ = block.getBlockBoundsMinZ() - (enumfacing.getFrontOffsetZ() * f);
/* 178 */       this.maxX = block.getBlockBoundsMaxX() - (enumfacing.getFrontOffsetX() * f);
/* 179 */       this.maxY = block.getBlockBoundsMaxY() - (enumfacing.getFrontOffsetY() * f);
/* 180 */       this.maxZ = block.getBlockBoundsMaxZ() - (enumfacing.getFrontOffsetZ() * f);
/*     */     } 
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(World worldIn, BlockPos pos, IBlockState extendingBlock, float progress, EnumFacing direction) {
/* 185 */     if (extendingBlock.getBlock() != this && extendingBlock.getBlock().getMaterial() != Material.air) {
/* 186 */       AxisAlignedBB axisalignedbb = extendingBlock.getBlock().getCollisionBoundingBox(worldIn, pos, extendingBlock);
/*     */       
/* 188 */       if (axisalignedbb == null) {
/* 189 */         return null;
/*     */       }
/* 191 */       double d0 = axisalignedbb.minX;
/* 192 */       double d1 = axisalignedbb.minY;
/* 193 */       double d2 = axisalignedbb.minZ;
/* 194 */       double d3 = axisalignedbb.maxX;
/* 195 */       double d4 = axisalignedbb.maxY;
/* 196 */       double d5 = axisalignedbb.maxZ;
/*     */       
/* 198 */       if (direction.getFrontOffsetX() < 0) {
/* 199 */         d0 -= (direction.getFrontOffsetX() * progress);
/*     */       } else {
/* 201 */         d3 -= (direction.getFrontOffsetX() * progress);
/*     */       } 
/*     */       
/* 204 */       if (direction.getFrontOffsetY() < 0) {
/* 205 */         d1 -= (direction.getFrontOffsetY() * progress);
/*     */       } else {
/* 207 */         d4 -= (direction.getFrontOffsetY() * progress);
/*     */       } 
/*     */       
/* 210 */       if (direction.getFrontOffsetZ() < 0) {
/* 211 */         d2 -= (direction.getFrontOffsetZ() * progress);
/*     */       } else {
/* 213 */         d5 -= (direction.getFrontOffsetZ() * progress);
/*     */       } 
/*     */       
/* 216 */       return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */     } 
/*     */     
/* 219 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private TileEntityPiston getTileEntity(IBlockAccess worldIn, BlockPos pos) {
/* 224 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 225 */     return (tileentity instanceof TileEntityPiston) ? (TileEntityPiston)tileentity : null;
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 229 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 236 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)BlockPistonExtension.getFacing(meta)).withProperty((IProperty)TYPE, ((meta & 0x8) > 0) ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 243 */     int i = 0;
/* 244 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 246 */     if (state.getValue((IProperty)TYPE) == BlockPistonExtension.EnumPistonType.STICKY) {
/* 247 */       i |= 0x8;
/*     */     }
/*     */     
/* 250 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 254 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)TYPE });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockPistonMoving.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */