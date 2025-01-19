/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneWire
/*     */   extends Block
/*     */ {
/*  31 */   public static final PropertyEnum<EnumAttachPosition> NORTH = PropertyEnum.create("north", EnumAttachPosition.class);
/*  32 */   public static final PropertyEnum<EnumAttachPosition> EAST = PropertyEnum.create("east", EnumAttachPosition.class);
/*  33 */   public static final PropertyEnum<EnumAttachPosition> SOUTH = PropertyEnum.create("south", EnumAttachPosition.class);
/*  34 */   public static final PropertyEnum<EnumAttachPosition> WEST = PropertyEnum.create("west", EnumAttachPosition.class);
/*  35 */   public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
/*     */   private boolean canProvidePower = true;
/*  37 */   private final Set<BlockPos> blocksNeedingUpdate = Sets.newHashSet();
/*     */   
/*     */   public BlockRedstoneWire() {
/*  40 */     super(Material.circuits);
/*  41 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, EnumAttachPosition.NONE).withProperty((IProperty)EAST, EnumAttachPosition.NONE).withProperty((IProperty)SOUTH, EnumAttachPosition.NONE).withProperty((IProperty)WEST, EnumAttachPosition.NONE).withProperty((IProperty)POWER, Integer.valueOf(0)));
/*  42 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  50 */     state = state.withProperty((IProperty)WEST, getAttachPosition(worldIn, pos, EnumFacing.WEST));
/*  51 */     state = state.withProperty((IProperty)EAST, getAttachPosition(worldIn, pos, EnumFacing.EAST));
/*  52 */     state = state.withProperty((IProperty)NORTH, getAttachPosition(worldIn, pos, EnumFacing.NORTH));
/*  53 */     state = state.withProperty((IProperty)SOUTH, getAttachPosition(worldIn, pos, EnumFacing.SOUTH));
/*  54 */     return state;
/*     */   }
/*     */   
/*     */   private EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
/*  58 */     BlockPos blockpos = pos.offset(direction);
/*  59 */     Block block = worldIn.getBlockState(pos.offset(direction)).getBlock();
/*     */     
/*  61 */     if (!canConnectTo(worldIn.getBlockState(blockpos), direction) && (block.isBlockNormalCube() || !canConnectUpwardsTo(worldIn.getBlockState(blockpos.down())))) {
/*  62 */       Block block1 = worldIn.getBlockState(pos.up()).getBlock();
/*  63 */       return (!block1.isBlockNormalCube() && block.isBlockNormalCube() && canConnectUpwardsTo(worldIn.getBlockState(blockpos.up()))) ? EnumAttachPosition.UP : EnumAttachPosition.NONE;
/*     */     } 
/*  65 */     return EnumAttachPosition.SIDE;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  77 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  81 */     return false;
/*     */   }
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  85 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  86 */     return (iblockstate.getBlock() != this) ? super.colorMultiplier(worldIn, pos, renderPass) : colorMultiplier(((Integer)iblockstate.getValue((IProperty)POWER)).intValue());
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  90 */     return !(!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && worldIn.getBlockState(pos.down()).getBlock() != Blocks.glowstone);
/*     */   }
/*     */   
/*     */   private IBlockState updateSurroundingRedstone(World worldIn, BlockPos pos, IBlockState state) {
/*  94 */     state = calculateCurrentChanges(worldIn, pos, pos, state);
/*  95 */     List<BlockPos> list = Lists.newArrayList(this.blocksNeedingUpdate);
/*  96 */     this.blocksNeedingUpdate.clear();
/*     */     
/*  98 */     for (BlockPos blockpos : list) {
/*  99 */       worldIn.notifyNeighborsOfStateChange(blockpos, this);
/*     */     }
/*     */     
/* 102 */     return state;
/*     */   }
/*     */   
/*     */   private IBlockState calculateCurrentChanges(World worldIn, BlockPos pos1, BlockPos pos2, IBlockState state) {
/* 106 */     IBlockState iblockstate = state;
/* 107 */     int i = ((Integer)state.getValue((IProperty)POWER)).intValue();
/* 108 */     int j = 0;
/* 109 */     j = getMaxCurrentStrength(worldIn, pos2, j);
/* 110 */     this.canProvidePower = false;
/* 111 */     int k = worldIn.isBlockIndirectlyGettingPowered(pos1);
/* 112 */     this.canProvidePower = true;
/*     */     
/* 114 */     if (k > 0 && k > j - 1) {
/* 115 */       j = k;
/*     */     }
/*     */     
/* 118 */     int l = 0;
/*     */     
/* 120 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 121 */       BlockPos blockpos = pos1.offset(enumfacing);
/* 122 */       boolean flag = !(blockpos.getX() == pos2.getX() && blockpos.getZ() == pos2.getZ());
/*     */       
/* 124 */       if (flag) {
/* 125 */         l = getMaxCurrentStrength(worldIn, blockpos, l);
/*     */       }
/*     */       
/* 128 */       if (worldIn.getBlockState(blockpos).getBlock().isNormalCube() && !worldIn.getBlockState(pos1.up()).getBlock().isNormalCube()) {
/* 129 */         if (flag && pos1.getY() >= pos2.getY())
/* 130 */           l = getMaxCurrentStrength(worldIn, blockpos.up(), l);  continue;
/*     */       } 
/* 132 */       if (!worldIn.getBlockState(blockpos).getBlock().isNormalCube() && flag && pos1.getY() <= pos2.getY()) {
/* 133 */         l = getMaxCurrentStrength(worldIn, blockpos.down(), l);
/*     */       }
/*     */     } 
/*     */     
/* 137 */     if (l > j) {
/* 138 */       j = l - 1;
/* 139 */     } else if (j > 0) {
/* 140 */       j--;
/*     */     } else {
/* 142 */       j = 0;
/*     */     } 
/*     */     
/* 145 */     if (k > j - 1) {
/* 146 */       j = k;
/*     */     }
/*     */     
/* 149 */     if (i != j) {
/* 150 */       state = state.withProperty((IProperty)POWER, Integer.valueOf(j));
/*     */       
/* 152 */       if (worldIn.getBlockState(pos1) == iblockstate) {
/* 153 */         worldIn.setBlockState(pos1, state, 2);
/*     */       }
/*     */       
/* 156 */       this.blocksNeedingUpdate.add(pos1); byte b; int m;
/*     */       EnumFacing[] arrayOfEnumFacing;
/* 158 */       for (m = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < m; ) { EnumFacing enumfacing1 = arrayOfEnumFacing[b];
/* 159 */         this.blocksNeedingUpdate.add(pos1.offset(enumfacing1));
/*     */         b++; }
/*     */     
/*     */     } 
/* 163 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void notifyWireNeighborsOfStateChange(World worldIn, BlockPos pos) {
/* 171 */     if (worldIn.getBlockState(pos).getBlock() == this) {
/* 172 */       worldIn.notifyNeighborsOfStateChange(pos, this); byte b; int i;
/*     */       EnumFacing[] arrayOfEnumFacing;
/* 174 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 175 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 181 */     if (!worldIn.isRemote) {
/* 182 */       updateSurroundingRedstone(worldIn, pos, state);
/*     */       
/* 184 */       for (EnumFacing enumfacing : EnumFacing.Plane.VERTICAL) {
/* 185 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */       }
/*     */       
/* 188 */       for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
/* 189 */         notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
/*     */       }
/*     */       
/* 192 */       for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
/* 193 */         BlockPos blockpos = pos.offset(enumfacing2);
/*     */         
/* 195 */         if (worldIn.getBlockState(blockpos).getBlock().isNormalCube()) {
/* 196 */           notifyWireNeighborsOfStateChange(worldIn, blockpos.up()); continue;
/*     */         } 
/* 198 */         notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 205 */     super.breakBlock(worldIn, pos, state);
/*     */     
/* 207 */     if (!worldIn.isRemote) {
/* 208 */       byte b; int i; EnumFacing[] arrayOfEnumFacing; for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 209 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */         b++; }
/*     */       
/* 212 */       updateSurroundingRedstone(worldIn, pos, state);
/*     */       
/* 214 */       for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
/* 215 */         notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
/*     */       }
/*     */       
/* 218 */       for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
/* 219 */         BlockPos blockpos = pos.offset(enumfacing2);
/*     */         
/* 221 */         if (worldIn.getBlockState(blockpos).getBlock().isNormalCube()) {
/* 222 */           notifyWireNeighborsOfStateChange(worldIn, blockpos.up()); continue;
/*     */         } 
/* 224 */         notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int getMaxCurrentStrength(World worldIn, BlockPos pos, int strength) {
/* 231 */     if (worldIn.getBlockState(pos).getBlock() != this) {
/* 232 */       return strength;
/*     */     }
/* 234 */     int i = ((Integer)worldIn.getBlockState(pos).getValue((IProperty)POWER)).intValue();
/* 235 */     return (i > strength) ? i : strength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 243 */     if (!worldIn.isRemote) {
/* 244 */       if (canPlaceBlockAt(worldIn, pos)) {
/* 245 */         updateSurroundingRedstone(worldIn, pos, state);
/*     */       } else {
/* 247 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 248 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 257 */     return Items.redstone;
/*     */   }
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 261 */     return !this.canProvidePower ? 0 : getWeakPower(worldIn, pos, state, side);
/*     */   }
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 265 */     if (!this.canProvidePower) {
/* 266 */       return 0;
/*     */     }
/* 268 */     int i = ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */     
/* 270 */     if (i == 0)
/* 271 */       return 0; 
/* 272 */     if (side == EnumFacing.UP) {
/* 273 */       return i;
/*     */     }
/* 275 */     EnumSet<EnumFacing> enumset = EnumSet.noneOf(EnumFacing.class);
/*     */     
/* 277 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 278 */       if (func_176339_d(worldIn, pos, enumfacing)) {
/* 279 */         enumset.add(enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 283 */     if (side.getAxis().isHorizontal() && enumset.isEmpty())
/* 284 */       return i; 
/* 285 */     if (enumset.contains(side) && !enumset.contains(side.rotateYCCW()) && !enumset.contains(side.rotateY())) {
/* 286 */       return i;
/*     */     }
/* 288 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean func_176339_d(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 295 */     BlockPos blockpos = pos.offset(side);
/* 296 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 297 */     Block block = iblockstate.getBlock();
/* 298 */     boolean flag = block.isNormalCube();
/* 299 */     boolean flag1 = worldIn.getBlockState(pos.up()).getBlock().isNormalCube();
/* 300 */     return (!flag1 && flag && canConnectUpwardsTo(worldIn, blockpos.up())) ? true : (canConnectTo(iblockstate, side) ? true : ((block == Blocks.powered_repeater && iblockstate.getValue((IProperty)BlockRedstoneDiode.FACING) == side) ? true : ((!flag && canConnectUpwardsTo(worldIn, blockpos.down())))));
/*     */   }
/*     */   
/*     */   protected static boolean canConnectUpwardsTo(IBlockAccess worldIn, BlockPos pos) {
/* 304 */     return canConnectUpwardsTo(worldIn.getBlockState(pos));
/*     */   }
/*     */   
/*     */   protected static boolean canConnectUpwardsTo(IBlockState state) {
/* 308 */     return canConnectTo(state, (EnumFacing)null);
/*     */   }
/*     */   
/*     */   protected static boolean canConnectTo(IBlockState blockState, EnumFacing side) {
/* 312 */     Block block = blockState.getBlock();
/*     */     
/* 314 */     if (block == Blocks.redstone_wire)
/* 315 */       return true; 
/* 316 */     if (Blocks.unpowered_repeater.isAssociated(block)) {
/* 317 */       EnumFacing enumfacing = (EnumFacing)blockState.getValue((IProperty)BlockRedstoneRepeater.FACING);
/* 318 */       return !(enumfacing != side && enumfacing.getOpposite() != side);
/*     */     } 
/* 320 */     return (block.canProvidePower() && side != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 328 */     return this.canProvidePower;
/*     */   }
/*     */   
/*     */   private int colorMultiplier(int powerLevel) {
/* 332 */     float f = powerLevel / 15.0F;
/* 333 */     float f1 = f * 0.6F + 0.4F;
/*     */     
/* 335 */     if (powerLevel == 0) {
/* 336 */       f1 = 0.3F;
/*     */     }
/*     */     
/* 339 */     float f2 = f * f * 0.7F - 0.5F;
/* 340 */     float f3 = f * f * 0.6F - 0.7F;
/*     */     
/* 342 */     if (f2 < 0.0F) {
/* 343 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 346 */     if (f3 < 0.0F) {
/* 347 */       f3 = 0.0F;
/*     */     }
/*     */     
/* 350 */     int i = MathHelper.clamp_int((int)(f1 * 255.0F), 0, 255);
/* 351 */     int j = MathHelper.clamp_int((int)(f2 * 255.0F), 0, 255);
/* 352 */     int k = MathHelper.clamp_int((int)(f3 * 255.0F), 0, 255);
/* 353 */     return 0xFF000000 | i << 16 | j << 8 | k;
/*     */   }
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 357 */     int i = ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */     
/* 359 */     if (i != 0) {
/* 360 */       double d0 = pos.getX() + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
/* 361 */       double d1 = (pos.getY() + 0.0625F);
/* 362 */       double d2 = pos.getZ() + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
/* 363 */       float f = i / 15.0F;
/* 364 */       float f1 = f * 0.6F + 0.4F;
/* 365 */       float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
/* 366 */       float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
/* 367 */       worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, f1, f2, f3, new int[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 372 */     return Items.redstone;
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 376 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 383 */     return getDefaultState().withProperty((IProperty)POWER, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 390 */     return ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 394 */     return new BlockState(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST, (IProperty)POWER });
/*     */   }
/*     */   
/*     */   enum EnumAttachPosition implements IStringSerializable {
/* 398 */     UP("up"),
/* 399 */     SIDE("side"),
/* 400 */     NONE("none");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     EnumAttachPosition(String name) {
/* 405 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 409 */       return getName();
/*     */     }
/*     */     
/*     */     public String getName() {
/* 413 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockRedstoneWire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */