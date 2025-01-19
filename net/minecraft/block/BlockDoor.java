/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDoor extends Block {
/*  28 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  29 */   public static final PropertyBool OPEN = PropertyBool.create("open");
/*  30 */   public static final PropertyEnum<EnumHingePosition> HINGE = PropertyEnum.create("hinge", EnumHingePosition.class);
/*  31 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  32 */   public static final PropertyEnum<EnumDoorHalf> HALF = PropertyEnum.create("half", EnumDoorHalf.class);
/*     */   
/*     */   protected BlockDoor(Material materialIn) {
/*  35 */     super(materialIn);
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)OPEN, Boolean.valueOf(false)).withProperty((IProperty)HINGE, EnumHingePosition.LEFT).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)HALF, EnumDoorHalf.LOWER));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  43 */     return StatCollector.translateToLocal((String.valueOf(getUnlocalizedName()) + ".name").replaceAll("tile", "item"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  50 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  54 */     return isOpen(combineMetadata(worldIn, pos));
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  58 */     return false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  62 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  63 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  67 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  68 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  72 */     setBoundBasedOnMeta(combineMetadata(worldIn, pos));
/*     */   }
/*     */   
/*     */   private void setBoundBasedOnMeta(int combinedMeta) {
/*  76 */     float f = 0.1875F;
/*  77 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
/*  78 */     EnumFacing enumfacing = getFacing(combinedMeta);
/*  79 */     boolean flag = isOpen(combinedMeta);
/*  80 */     boolean flag1 = isHingeLeft(combinedMeta);
/*     */     
/*  82 */     if (flag) {
/*  83 */       if (enumfacing == EnumFacing.EAST) {
/*  84 */         if (!flag1) {
/*  85 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */         } else {
/*  87 */           setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */         } 
/*  89 */       } else if (enumfacing == EnumFacing.SOUTH) {
/*  90 */         if (!flag1) {
/*  91 */           setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         } else {
/*  93 */           setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */         } 
/*  95 */       } else if (enumfacing == EnumFacing.WEST) {
/*  96 */         if (!flag1) {
/*  97 */           setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */         } else {
/*  99 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*     */         } 
/* 101 */       } else if (enumfacing == EnumFacing.NORTH) {
/* 102 */         if (!flag1) {
/* 103 */           setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*     */         } else {
/* 105 */           setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         } 
/*     */       } 
/* 108 */     } else if (enumfacing == EnumFacing.EAST) {
/* 109 */       setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/* 110 */     } else if (enumfacing == EnumFacing.SOUTH) {
/* 111 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/* 112 */     } else if (enumfacing == EnumFacing.WEST) {
/* 113 */       setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 114 */     } else if (enumfacing == EnumFacing.NORTH) {
/* 115 */       setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 120 */     if (this.blockMaterial == Material.iron) {
/* 121 */       return true;
/*     */     }
/* 123 */     BlockPos blockpos = (state.getValue((IProperty)HALF) == EnumDoorHalf.LOWER) ? pos : pos.down();
/* 124 */     IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);
/*     */     
/* 126 */     if (iblockstate.getBlock() != this) {
/* 127 */       return false;
/*     */     }
/* 129 */     state = iblockstate.cycleProperty((IProperty)OPEN);
/* 130 */     worldIn.setBlockState(blockpos, state, 2);
/* 131 */     worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
/* 132 */     worldIn.playAuxSFXAtEntity(playerIn, ((Boolean)state.getValue((IProperty)OPEN)).booleanValue() ? 1003 : 1006, pos, 0);
/* 133 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleDoor(World worldIn, BlockPos pos, boolean open) {
/* 139 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 141 */     if (iblockstate.getBlock() == this) {
/* 142 */       BlockPos blockpos = (iblockstate.getValue((IProperty)HALF) == EnumDoorHalf.LOWER) ? pos : pos.down();
/* 143 */       IBlockState iblockstate1 = (pos == blockpos) ? iblockstate : worldIn.getBlockState(blockpos);
/*     */       
/* 145 */       if (iblockstate1.getBlock() == this && ((Boolean)iblockstate1.getValue((IProperty)OPEN)).booleanValue() != open) {
/* 146 */         worldIn.setBlockState(blockpos, iblockstate1.withProperty((IProperty)OPEN, Boolean.valueOf(open)), 2);
/* 147 */         worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
/* 148 */         worldIn.playAuxSFXAtEntity(null, open ? 1003 : 1006, pos, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 157 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER) {
/* 158 */       BlockPos blockpos = pos.down();
/* 159 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 161 */       if (iblockstate.getBlock() != this) {
/* 162 */         worldIn.setBlockToAir(pos);
/* 163 */       } else if (neighborBlock != this) {
/* 164 */         onNeighborBlockChange(worldIn, blockpos, iblockstate, neighborBlock);
/*     */       } 
/*     */     } else {
/* 167 */       boolean flag1 = false;
/* 168 */       BlockPos blockpos1 = pos.up();
/* 169 */       IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
/*     */       
/* 171 */       if (iblockstate1.getBlock() != this) {
/* 172 */         worldIn.setBlockToAir(pos);
/* 173 */         flag1 = true;
/*     */       } 
/*     */       
/* 176 */       if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())) {
/* 177 */         worldIn.setBlockToAir(pos);
/* 178 */         flag1 = true;
/*     */         
/* 180 */         if (iblockstate1.getBlock() == this) {
/* 181 */           worldIn.setBlockToAir(blockpos1);
/*     */         }
/*     */       } 
/*     */       
/* 185 */       if (flag1) {
/* 186 */         if (!worldIn.isRemote) {
/* 187 */           dropBlockAsItem(worldIn, pos, state, 0);
/*     */         }
/*     */       } else {
/* 190 */         boolean flag = !(!worldIn.isBlockPowered(pos) && !worldIn.isBlockPowered(blockpos1));
/*     */         
/* 192 */         if ((flag || neighborBlock.canProvidePower()) && neighborBlock != this && flag != ((Boolean)iblockstate1.getValue((IProperty)POWERED)).booleanValue()) {
/* 193 */           worldIn.setBlockState(blockpos1, iblockstate1.withProperty((IProperty)POWERED, Boolean.valueOf(flag)), 2);
/*     */           
/* 195 */           if (flag != ((Boolean)state.getValue((IProperty)OPEN)).booleanValue()) {
/* 196 */             worldIn.setBlockState(pos, state.withProperty((IProperty)OPEN, Boolean.valueOf(flag)), 2);
/* 197 */             worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 198 */             worldIn.playAuxSFXAtEntity(null, flag ? 1003 : 1006, pos, 0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 209 */     return (state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER) ? null : getItem();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 216 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 217 */     return super.collisionRayTrace(worldIn, pos, start, end);
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 221 */     return (pos.getY() >= 255) ? false : ((World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up())));
/*     */   }
/*     */   
/*     */   public int getMobilityFlag() {
/* 225 */     return 1;
/*     */   }
/*     */   
/*     */   public static int combineMetadata(IBlockAccess worldIn, BlockPos pos) {
/* 229 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 230 */     int i = iblockstate.getBlock().getMetaFromState(iblockstate);
/* 231 */     boolean flag = isTop(i);
/* 232 */     IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
/* 233 */     int j = iblockstate1.getBlock().getMetaFromState(iblockstate1);
/* 234 */     int k = flag ? j : i;
/* 235 */     IBlockState iblockstate2 = worldIn.getBlockState(pos.up());
/* 236 */     int l = iblockstate2.getBlock().getMetaFromState(iblockstate2);
/* 237 */     int i1 = flag ? i : l;
/* 238 */     boolean flag1 = ((i1 & 0x1) != 0);
/* 239 */     boolean flag2 = ((i1 & 0x2) != 0);
/* 240 */     return removeHalfBit(k) | (flag ? 8 : 0) | (flag1 ? 16 : 0) | (flag2 ? 32 : 0);
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 244 */     return getItem();
/*     */   }
/*     */   
/*     */   private Item getItem() {
/* 248 */     return (this == Blocks.iron_door) ? Items.iron_door : ((this == Blocks.spruce_door) ? Items.spruce_door : ((this == Blocks.birch_door) ? Items.birch_door : ((this == Blocks.jungle_door) ? Items.jungle_door : ((this == Blocks.acacia_door) ? Items.acacia_door : ((this == Blocks.dark_oak_door) ? Items.dark_oak_door : Items.oak_door)))));
/*     */   }
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 252 */     BlockPos blockpos = pos.down();
/*     */     
/* 254 */     if (player.capabilities.isCreativeMode && state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER && worldIn.getBlockState(blockpos).getBlock() == this) {
/* 255 */       worldIn.setBlockToAir(blockpos);
/*     */     }
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 260 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 268 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.LOWER) {
/* 269 */       IBlockState iblockstate = worldIn.getBlockState(pos.up());
/*     */       
/* 271 */       if (iblockstate.getBlock() == this) {
/* 272 */         state = state.withProperty((IProperty)HINGE, iblockstate.getValue((IProperty)HINGE)).withProperty((IProperty)POWERED, iblockstate.getValue((IProperty)POWERED));
/*     */       }
/*     */     } else {
/* 275 */       IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
/*     */       
/* 277 */       if (iblockstate1.getBlock() == this) {
/* 278 */         state = state.withProperty((IProperty)FACING, iblockstate1.getValue((IProperty)FACING)).withProperty((IProperty)OPEN, iblockstate1.getValue((IProperty)OPEN));
/*     */       }
/*     */     } 
/*     */     
/* 282 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 289 */     return ((meta & 0x8) > 0) ? getDefaultState().withProperty((IProperty)HALF, EnumDoorHalf.UPPER).withProperty((IProperty)HINGE, ((meta & 0x1) > 0) ? EnumHingePosition.RIGHT : EnumHingePosition.LEFT).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x2) > 0))) : getDefaultState().withProperty((IProperty)HALF, EnumDoorHalf.LOWER).withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3).rotateYCCW()).withProperty((IProperty)OPEN, Boolean.valueOf(((meta & 0x4) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 296 */     int i = 0;
/*     */     
/* 298 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER) {
/* 299 */       i |= 0x8;
/*     */       
/* 301 */       if (state.getValue((IProperty)HINGE) == EnumHingePosition.RIGHT) {
/* 302 */         i |= 0x1;
/*     */       }
/*     */       
/* 305 */       if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 306 */         i |= 0x2;
/*     */       }
/*     */     } else {
/* 309 */       i |= ((EnumFacing)state.getValue((IProperty)FACING)).rotateY().getHorizontalIndex();
/*     */       
/* 311 */       if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue()) {
/* 312 */         i |= 0x4;
/*     */       }
/*     */     } 
/*     */     
/* 316 */     return i;
/*     */   }
/*     */   
/*     */   protected static int removeHalfBit(int meta) {
/* 320 */     return meta & 0x7;
/*     */   }
/*     */   
/*     */   public static boolean isOpen(IBlockAccess worldIn, BlockPos pos) {
/* 324 */     return isOpen(combineMetadata(worldIn, pos));
/*     */   }
/*     */   
/*     */   public static EnumFacing getFacing(IBlockAccess worldIn, BlockPos pos) {
/* 328 */     return getFacing(combineMetadata(worldIn, pos));
/*     */   }
/*     */   
/*     */   public static EnumFacing getFacing(int combinedMeta) {
/* 332 */     return EnumFacing.getHorizontal(combinedMeta & 0x3).rotateYCCW();
/*     */   }
/*     */   
/*     */   protected static boolean isOpen(int combinedMeta) {
/* 336 */     return ((combinedMeta & 0x4) != 0);
/*     */   }
/*     */   
/*     */   protected static boolean isTop(int meta) {
/* 340 */     return ((meta & 0x8) != 0);
/*     */   }
/*     */   
/*     */   protected static boolean isHingeLeft(int combinedMeta) {
/* 344 */     return ((combinedMeta & 0x10) != 0);
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 348 */     return new BlockState(this, new IProperty[] { (IProperty)HALF, (IProperty)FACING, (IProperty)OPEN, (IProperty)HINGE, (IProperty)POWERED });
/*     */   }
/*     */   
/*     */   public enum EnumDoorHalf implements IStringSerializable {
/* 352 */     UPPER,
/* 353 */     LOWER;
/*     */     
/*     */     public String toString() {
/* 356 */       return getName();
/*     */     }
/*     */     
/*     */     public String getName() {
/* 360 */       return (this == UPPER) ? "upper" : "lower";
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumHingePosition implements IStringSerializable {
/* 365 */     LEFT,
/* 366 */     RIGHT;
/*     */     
/*     */     public String toString() {
/* 369 */       return getName();
/*     */     }
/*     */     
/*     */     public String getName() {
/* 373 */       return (this == LEFT) ? "left" : "right";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */