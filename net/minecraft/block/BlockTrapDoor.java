/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTrapDoor extends Block {
/*  25 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  26 */   public static final PropertyBool OPEN = PropertyBool.create("open");
/*  27 */   public static final PropertyEnum<DoorHalf> HALF = PropertyEnum.create("half", DoorHalf.class);
/*     */   
/*     */   protected BlockTrapDoor(Material materialIn) {
/*  30 */     super(materialIn);
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)OPEN, Boolean.valueOf(false)).withProperty((IProperty)HALF, DoorHalf.BOTTOM));
/*  32 */     float f = 0.5F;
/*  33 */     float f1 = 1.0F;
/*  34 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  35 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  42 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  46 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  50 */     return !((Boolean)worldIn.getBlockState(pos).getValue((IProperty)OPEN)).booleanValue();
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  54 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  55 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  59 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  60 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  64 */     setBounds(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  71 */     float f = 0.1875F;
/*  72 */     setBlockBounds(0.0F, 0.40625F, 0.0F, 1.0F, 0.59375F, 1.0F);
/*     */   }
/*     */   
/*     */   public void setBounds(IBlockState state) {
/*  76 */     if (state.getBlock() == this) {
/*  77 */       boolean flag = (state.getValue((IProperty)HALF) == DoorHalf.TOP);
/*  78 */       Boolean obool = (Boolean)state.getValue((IProperty)OPEN);
/*  79 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  80 */       float f = 0.1875F;
/*     */       
/*  82 */       if (flag) {
/*  83 */         setBlockBounds(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */       } else {
/*  85 */         setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
/*     */       } 
/*     */       
/*  88 */       if (obool.booleanValue()) {
/*  89 */         if (enumfacing == EnumFacing.NORTH) {
/*  90 */           setBlockBounds(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         
/*  93 */         if (enumfacing == EnumFacing.SOUTH) {
/*  94 */           setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
/*     */         }
/*     */         
/*  97 */         if (enumfacing == EnumFacing.WEST) {
/*  98 */           setBlockBounds(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */         }
/*     */         
/* 101 */         if (enumfacing == EnumFacing.EAST) {
/* 102 */           setBlockBounds(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 109 */     if (this.blockMaterial == Material.iron) {
/* 110 */       return true;
/*     */     }
/* 112 */     state = state.cycleProperty((IProperty)OPEN);
/* 113 */     worldIn.setBlockState(pos, state, 2);
/* 114 */     worldIn.playAuxSFXAtEntity(playerIn, ((Boolean)state.getValue((IProperty)OPEN)).booleanValue() ? 1003 : 1006, pos, 0);
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 123 */     if (!worldIn.isRemote) {
/* 124 */       BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*     */       
/* 126 */       if (!isValidSupportBlock(worldIn.getBlockState(blockpos).getBlock())) {
/* 127 */         worldIn.setBlockToAir(pos);
/* 128 */         dropBlockAsItem(worldIn, pos, state, 0);
/*     */       } else {
/* 130 */         boolean flag = worldIn.isBlockPowered(pos);
/*     */         
/* 132 */         if (flag || neighborBlock.canProvidePower()) {
/* 133 */           boolean flag1 = ((Boolean)state.getValue((IProperty)OPEN)).booleanValue();
/*     */           
/* 135 */           if (flag1 != flag) {
/* 136 */             worldIn.setBlockState(pos, state.withProperty((IProperty)OPEN, Boolean.valueOf(flag)), 2);
/* 137 */             worldIn.playAuxSFXAtEntity(null, flag ? 1003 : 1006, pos, 0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 148 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 149 */     return super.collisionRayTrace(worldIn, pos, start, end);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 157 */     IBlockState iblockstate = getDefaultState();
/*     */     
/* 159 */     if (facing.getAxis().isHorizontal()) {
/* 160 */       iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)facing).withProperty((IProperty)OPEN, Boolean.valueOf(false));
/* 161 */       iblockstate = iblockstate.withProperty((IProperty)HALF, (hitY > 0.5F) ? DoorHalf.TOP : DoorHalf.BOTTOM);
/*     */     } 
/*     */     
/* 164 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/* 171 */     return (!side.getAxis().isVertical() && isValidSupportBlock(worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock()));
/*     */   }
/*     */   
/*     */   protected static EnumFacing getFacing(int meta) {
/* 175 */     switch (meta & 0x3) {
/*     */       case 0:
/* 177 */         return EnumFacing.NORTH;
/*     */       
/*     */       case 1:
/* 180 */         return EnumFacing.SOUTH;
/*     */       
/*     */       case 2:
/* 183 */         return EnumFacing.WEST;
/*     */     } 
/*     */ 
/*     */     
/* 187 */     return EnumFacing.EAST;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int getMetaForFacing(EnumFacing facing) {
/* 192 */     switch (facing) {
/*     */       case NORTH:
/* 194 */         return 0;
/*     */       
/*     */       case SOUTH:
/* 197 */         return 1;
/*     */       
/*     */       case WEST:
/* 200 */         return 2;
/*     */     } 
/*     */ 
/*     */     
/* 204 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isValidSupportBlock(Block blockIn) {
/* 209 */     return !((!blockIn.blockMaterial.isOpaque() || !blockIn.isFullCube()) && blockIn != Blocks.glowstone && !(blockIn instanceof BlockSlab) && !(blockIn instanceof BlockStairs));
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 213 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 220 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)OPEN, Boolean.valueOf(((meta & 0x4) != 0))).withProperty((IProperty)HALF, ((meta & 0x8) == 0) ? DoorHalf.BOTTOM : DoorHalf.TOP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 227 */     int i = 0;
/* 228 */     i |= getMetaForFacing((EnumFacing)state.getValue((IProperty)FACING));
/*     */     
/* 230 */     if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue()) {
/* 231 */       i |= 0x4;
/*     */     }
/*     */     
/* 234 */     if (state.getValue((IProperty)HALF) == DoorHalf.TOP) {
/* 235 */       i |= 0x8;
/*     */     }
/*     */     
/* 238 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 242 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)OPEN, (IProperty)HALF });
/*     */   }
/*     */   
/*     */   public enum DoorHalf implements IStringSerializable {
/* 246 */     TOP("top"),
/* 247 */     BOTTOM("bottom");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     DoorHalf(String name) {
/* 252 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 256 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 260 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockTrapDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */