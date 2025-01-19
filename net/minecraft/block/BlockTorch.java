/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTorch
/*     */   extends Block {
/*  25 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>() {
/*     */         public boolean apply(EnumFacing p_apply_1_) {
/*  27 */           return (p_apply_1_ != EnumFacing.DOWN);
/*     */         }
/*     */       });
/*     */   
/*     */   protected BlockTorch() {
/*  32 */     super(Material.circuits);
/*  33 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.UP));
/*  34 */     setTickRandomly(true);
/*  35 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  39 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  46 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  50 */     return false;
/*     */   }
/*     */   
/*     */   private boolean canPlaceOn(World worldIn, BlockPos pos) {
/*  54 */     if (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos)) {
/*  55 */       return true;
/*     */     }
/*  57 */     Block block = worldIn.getBlockState(pos).getBlock();
/*  58 */     return !(!(block instanceof BlockFence) && block != Blocks.glass && block != Blocks.cobblestone_wall && block != Blocks.stained_glass);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  63 */     for (EnumFacing enumfacing : FACING.getAllowedValues()) {
/*  64 */       if (canPlaceAt(worldIn, pos, enumfacing)) {
/*  65 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  69 */     return false;
/*     */   }
/*     */   
/*     */   private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
/*  73 */     BlockPos blockpos = pos.offset(facing.getOpposite());
/*  74 */     boolean flag = facing.getAxis().isHorizontal();
/*  75 */     return !((!flag || !worldIn.isBlockNormalCube(blockpos, true)) && (!facing.equals(EnumFacing.UP) || !canPlaceOn(worldIn, blockpos)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  83 */     if (canPlaceAt(worldIn, pos, facing)) {
/*  84 */       return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing);
/*     */     }
/*  86 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*  87 */       if (worldIn.isBlockNormalCube(pos.offset(enumfacing.getOpposite()), true)) {
/*  88 */         return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */       }
/*     */     } 
/*     */     
/*  92 */     return getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  97 */     checkForDrop(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 104 */     onNeighborChangeInternal(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   protected boolean onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
/* 108 */     if (!checkForDrop(worldIn, pos, state)) {
/* 109 */       return true;
/*     */     }
/* 111 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 112 */     EnumFacing.Axis enumfacing$axis = enumfacing.getAxis();
/* 113 */     EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 114 */     boolean flag = false;
/*     */     
/* 116 */     if (enumfacing$axis.isHorizontal() && !worldIn.isBlockNormalCube(pos.offset(enumfacing1), true)) {
/* 117 */       flag = true;
/* 118 */     } else if (enumfacing$axis.isVertical() && !canPlaceOn(worldIn, pos.offset(enumfacing1))) {
/* 119 */       flag = true;
/*     */     } 
/*     */     
/* 122 */     if (flag) {
/* 123 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 124 */       worldIn.setBlockToAir(pos);
/* 125 */       return true;
/*     */     } 
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 133 */     if (state.getBlock() == this && canPlaceAt(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING))) {
/* 134 */       return true;
/*     */     }
/* 136 */     if (worldIn.getBlockState(pos).getBlock() == this) {
/* 137 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 138 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */     
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 149 */     EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/* 150 */     float f = 0.15F;
/*     */     
/* 152 */     if (enumfacing == EnumFacing.EAST) {
/* 153 */       setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/* 154 */     } else if (enumfacing == EnumFacing.WEST) {
/* 155 */       setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/* 156 */     } else if (enumfacing == EnumFacing.SOUTH) {
/* 157 */       setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/* 158 */     } else if (enumfacing == EnumFacing.NORTH) {
/* 159 */       setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */     } else {
/* 161 */       f = 0.1F;
/* 162 */       setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
/*     */     } 
/*     */     
/* 165 */     return super.collisionRayTrace(worldIn, pos, start, end);
/*     */   }
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 169 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 170 */     double d0 = pos.getX() + 0.5D;
/* 171 */     double d1 = pos.getY() + 0.7D;
/* 172 */     double d2 = pos.getZ() + 0.5D;
/* 173 */     double d3 = 0.22D;
/* 174 */     double d4 = 0.27D;
/*     */     
/* 176 */     if (enumfacing.getAxis().isHorizontal()) {
/* 177 */       EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 178 */       worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4 * enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
/* 179 */       worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4 * enumfacing1.getFrontOffsetX(), d1 + d3, d2 + d4 * enumfacing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } else {
/* 181 */       worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/* 182 */       worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 187 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 194 */     IBlockState iblockstate = getDefaultState();
/*     */     
/* 196 */     switch (meta)
/*     */     { case 1:
/* 198 */         iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.EAST);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 218 */         return iblockstate;case 2: iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.WEST); return iblockstate;case 3: iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.SOUTH); return iblockstate;case 4: iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH); return iblockstate; }  iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.UP); return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 225 */     int i = 0;
/*     */     
/* 227 */     switch ((EnumFacing)state.getValue((IProperty)FACING))
/*     */     { case EAST:
/* 229 */         i |= 0x1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 250 */         return i;case WEST: i |= 0x2; return i;case SOUTH: i |= 0x3; return i;case NORTH: i |= 0x4; return i; }  i |= 0x5; return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 254 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockTorch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */