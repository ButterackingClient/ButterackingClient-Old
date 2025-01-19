/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockLever extends Block {
/*  20 */   public static final PropertyEnum<EnumOrientation> FACING = PropertyEnum.create("facing", EnumOrientation.class);
/*  21 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */   
/*     */   protected BlockLever() {
/*  24 */     super(Material.circuits);
/*  25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, EnumOrientation.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/*  26 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  30 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  37 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  41 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  48 */     return func_181090_a(worldIn, pos, side.getOpposite()); } public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/*  52 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*  53 */       if (func_181090_a(worldIn, pos, enumfacing)) {
/*  54 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/*  58 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean func_181090_a(World p_181090_0_, BlockPos p_181090_1_, EnumFacing p_181090_2_) {
/*  62 */     return BlockButton.func_181088_a(p_181090_0_, p_181090_1_, p_181090_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  70 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf(false));
/*     */     
/*  72 */     if (func_181090_a(worldIn, pos, facing.getOpposite())) {
/*  73 */       return iblockstate.withProperty((IProperty)FACING, EnumOrientation.forFacings(facing, placer.getHorizontalFacing()));
/*     */     }
/*  75 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*  76 */       if (enumfacing != facing && func_181090_a(worldIn, pos, enumfacing.getOpposite())) {
/*  77 */         return iblockstate.withProperty((IProperty)FACING, EnumOrientation.forFacings(enumfacing, placer.getHorizontalFacing()));
/*     */       }
/*     */     } 
/*     */     
/*  81 */     if (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())) {
/*  82 */       return iblockstate.withProperty((IProperty)FACING, EnumOrientation.forFacings(EnumFacing.UP, placer.getHorizontalFacing()));
/*     */     }
/*  84 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMetadataForFacing(EnumFacing facing) {
/*  90 */     switch (facing) {
/*     */       case null:
/*  92 */         return 0;
/*     */       
/*     */       case UP:
/*  95 */         return 5;
/*     */       
/*     */       case NORTH:
/*  98 */         return 4;
/*     */       
/*     */       case SOUTH:
/* 101 */         return 3;
/*     */       
/*     */       case WEST:
/* 104 */         return 2;
/*     */       
/*     */       case EAST:
/* 107 */         return 1;
/*     */     } 
/*     */     
/* 110 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 118 */     if (func_181091_e(worldIn, pos, state) && !func_181090_a(worldIn, pos, ((EnumOrientation)state.getValue((IProperty)FACING)).getFacing().getOpposite())) {
/* 119 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 120 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean func_181091_e(World p_181091_1_, BlockPos p_181091_2_, IBlockState p_181091_3_) {
/* 125 */     if (canPlaceBlockAt(p_181091_1_, p_181091_2_)) {
/* 126 */       return true;
/*     */     }
/* 128 */     dropBlockAsItem(p_181091_1_, p_181091_2_, p_181091_3_, 0);
/* 129 */     p_181091_1_.setBlockToAir(p_181091_2_);
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 135 */     float f = 0.1875F;
/*     */     
/* 137 */     switch ((EnumOrientation)worldIn.getBlockState(pos).getValue((IProperty)FACING)) {
/*     */       case EAST:
/* 139 */         setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 143 */         setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 147 */         setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 151 */         setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */         break;
/*     */       
/*     */       case UP_Z:
/*     */       case UP_X:
/* 156 */         f = 0.25F;
/* 157 */         setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case null:
/*     */       case DOWN_Z:
/* 162 */         f = 0.25F;
/* 163 */         setBlockBounds(0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 168 */     if (worldIn.isRemote) {
/* 169 */       return true;
/*     */     }
/* 171 */     state = state.cycleProperty((IProperty)POWERED);
/* 172 */     worldIn.setBlockState(pos, state, 3);
/* 173 */     worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0.6F : 0.5F);
/* 174 */     worldIn.notifyNeighborsOfStateChange(pos, this);
/* 175 */     EnumFacing enumfacing = ((EnumOrientation)state.getValue((IProperty)FACING)).getFacing();
/* 176 */     worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this);
/* 177 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 182 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 183 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 184 */       EnumFacing enumfacing = ((EnumOrientation)state.getValue((IProperty)FACING)).getFacing();
/* 185 */       worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this);
/*     */     } 
/*     */     
/* 188 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 192 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 196 */     return !((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0 : ((((EnumOrientation)state.getValue((IProperty)FACING)).getFacing() == side) ? 15 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 203 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 210 */     return getDefaultState().withProperty((IProperty)FACING, EnumOrientation.byMetadata(meta & 0x7)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 217 */     int i = 0;
/* 218 */     i |= ((EnumOrientation)state.getValue((IProperty)FACING)).getMetadata();
/*     */     
/* 220 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 221 */       i |= 0x8;
/*     */     }
/*     */     
/* 224 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 228 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED });
/*     */   }
/*     */   
/*     */   public enum EnumOrientation implements IStringSerializable {
/* 232 */     DOWN_X(0, "down_x", EnumFacing.DOWN),
/* 233 */     EAST(1, "east", EnumFacing.EAST),
/* 234 */     WEST(2, "west", EnumFacing.WEST),
/* 235 */     SOUTH(3, "south", EnumFacing.SOUTH),
/* 236 */     NORTH(4, "north", EnumFacing.NORTH),
/* 237 */     UP_Z(5, "up_z", EnumFacing.UP),
/* 238 */     UP_X(6, "up_x", EnumFacing.UP),
/* 239 */     DOWN_Z(7, "down_z", EnumFacing.DOWN);
/*     */     
/* 241 */     private static final EnumOrientation[] META_LOOKUP = new EnumOrientation[(values()).length];
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
/*     */     private final int meta;
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
/*     */     private final String name;
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
/*     */     private final EnumFacing facing;
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
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumOrientation[] arrayOfEnumOrientation;
/* 320 */       for (i = (arrayOfEnumOrientation = values()).length, b = 0; b < i; ) { EnumOrientation blocklever$enumorientation = arrayOfEnumOrientation[b];
/* 321 */         META_LOOKUP[blocklever$enumorientation.getMetadata()] = blocklever$enumorientation;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumOrientation(int meta, String name, EnumFacing facing) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */       this.facing = facing;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public EnumFacing getFacing() {
/*     */       return this.facing;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumOrientation byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     public static EnumOrientation forFacings(EnumFacing clickedSide, EnumFacing entityFacing) {
/*     */       switch (clickedSide) {
/*     */         case null:
/*     */           switch (entityFacing.getAxis()) {
/*     */             case null:
/*     */               return DOWN_X;
/*     */             case Z:
/*     */               return DOWN_Z;
/*     */           } 
/*     */           throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
/*     */         case UP:
/*     */           switch (entityFacing.getAxis()) {
/*     */             case null:
/*     */               return UP_X;
/*     */             case Z:
/*     */               return UP_Z;
/*     */           } 
/*     */           throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
/*     */         case NORTH:
/*     */           return NORTH;
/*     */         case SOUTH:
/*     */           return SOUTH;
/*     */         case WEST:
/*     */           return WEST;
/*     */         case EAST:
/*     */           return EAST;
/*     */       } 
/*     */       throw new IllegalArgumentException("Invalid facing: " + clickedSide);
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockLever.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */