/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTripWireHook
/*     */   extends Block {
/*  25 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  26 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  27 */   public static final PropertyBool ATTACHED = PropertyBool.create("attached");
/*  28 */   public static final PropertyBool SUSPENDED = PropertyBool.create("suspended");
/*     */   
/*     */   public BlockTripWireHook() {
/*  31 */     super(Material.circuits);
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)ATTACHED, Boolean.valueOf(false)).withProperty((IProperty)SUSPENDED, Boolean.valueOf(false)));
/*  33 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  34 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  42 */     return state.withProperty((IProperty)SUSPENDED, Boolean.valueOf(!World.doesBlockHaveSolidTopSurface(worldIn, pos.down())));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  46 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  53 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  57 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  64 */     return (side.getAxis().isHorizontal() && worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock().isNormalCube());
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  68 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*  69 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock().isNormalCube()) {
/*  70 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  82 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)ATTACHED, Boolean.valueOf(false)).withProperty((IProperty)SUSPENDED, Boolean.valueOf(false));
/*     */     
/*  84 */     if (facing.getAxis().isHorizontal()) {
/*  85 */       iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)facing);
/*     */     }
/*     */     
/*  88 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  95 */     func_176260_a(worldIn, pos, state, false, false, -1, (IBlockState)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 102 */     if (neighborBlock != this && 
/* 103 */       checkForDrop(worldIn, pos, state)) {
/* 104 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */       
/* 106 */       if (!worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock().isNormalCube()) {
/* 107 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 108 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_176260_a(World worldIn, BlockPos pos, IBlockState hookState, boolean p_176260_4_, boolean p_176260_5_, int p_176260_6_, IBlockState p_176260_7_) {
/*     */     int k, m;
/* 115 */     EnumFacing enumfacing = (EnumFacing)hookState.getValue((IProperty)FACING);
/* 116 */     int flag = ((Boolean)hookState.getValue((IProperty)ATTACHED)).booleanValue();
/* 117 */     boolean flag1 = ((Boolean)hookState.getValue((IProperty)POWERED)).booleanValue();
/* 118 */     boolean flag2 = !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down());
/* 119 */     boolean flag3 = !p_176260_4_;
/* 120 */     boolean flag4 = false;
/* 121 */     int i = 0;
/* 122 */     IBlockState[] aiblockstate = new IBlockState[42];
/*     */     
/* 124 */     for (int j = 1; j < 42; j++) {
/* 125 */       BlockPos blockpos = pos.offset(enumfacing, j);
/* 126 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 128 */       if (iblockstate.getBlock() == Blocks.tripwire_hook) {
/* 129 */         if (iblockstate.getValue((IProperty)FACING) == enumfacing.getOpposite()) {
/* 130 */           i = j;
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 136 */       if (iblockstate.getBlock() != Blocks.tripwire && j != p_176260_6_) {
/* 137 */         aiblockstate[j] = null;
/* 138 */         flag3 = false;
/*     */       } else {
/* 140 */         if (j == p_176260_6_) {
/* 141 */           iblockstate = (IBlockState)Objects.firstNonNull(p_176260_7_, iblockstate);
/*     */         }
/*     */         
/* 144 */         int flag5 = ((Boolean)iblockstate.getValue((IProperty)BlockTripWire.DISARMED)).booleanValue() ? 0 : 1;
/* 145 */         boolean flag6 = ((Boolean)iblockstate.getValue((IProperty)BlockTripWire.POWERED)).booleanValue();
/* 146 */         boolean flag7 = ((Boolean)iblockstate.getValue((IProperty)BlockTripWire.SUSPENDED)).booleanValue();
/* 147 */         int n = flag3 & ((flag7 == flag2) ? 1 : 0);
/* 148 */         m = flag4 | ((flag5 && flag6) ? 1 : 0);
/* 149 */         aiblockstate[j] = iblockstate;
/*     */         
/* 151 */         if (j == p_176260_6_) {
/* 152 */           worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/* 153 */           k = n & flag5;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     k &= (i > 1) ? 1 : 0;
/* 159 */     m &= k;
/* 160 */     IBlockState iblockstate1 = getDefaultState().withProperty((IProperty)ATTACHED, Boolean.valueOf(k)).withProperty((IProperty)POWERED, Boolean.valueOf(m));
/*     */     
/* 162 */     if (i > 0) {
/* 163 */       BlockPos blockpos1 = pos.offset(enumfacing, i);
/* 164 */       EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 165 */       worldIn.setBlockState(blockpos1, iblockstate1.withProperty((IProperty)FACING, (Comparable)enumfacing1), 3);
/* 166 */       func_176262_b(worldIn, blockpos1, enumfacing1);
/* 167 */       func_180694_a(worldIn, blockpos1, k, m, flag, flag1);
/*     */     } 
/*     */     
/* 170 */     func_180694_a(worldIn, pos, k, m, flag, flag1);
/*     */     
/* 172 */     if (!p_176260_4_) {
/* 173 */       worldIn.setBlockState(pos, iblockstate1.withProperty((IProperty)FACING, (Comparable)enumfacing), 3);
/*     */       
/* 175 */       if (p_176260_5_) {
/* 176 */         func_176262_b(worldIn, pos, enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 180 */     if (flag != k) {
/* 181 */       for (int n = 1; n < i; n++) {
/* 182 */         BlockPos blockpos2 = pos.offset(enumfacing, n);
/* 183 */         IBlockState iblockstate2 = aiblockstate[n];
/*     */         
/* 185 */         if (iblockstate2 != null && worldIn.getBlockState(blockpos2).getBlock() != Blocks.air) {
/* 186 */           worldIn.setBlockState(blockpos2, iblockstate2.withProperty((IProperty)ATTACHED, Boolean.valueOf(k)), 3);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 199 */     func_176260_a(worldIn, pos, state, false, true, -1, (IBlockState)null);
/*     */   }
/*     */   
/*     */   private void func_180694_a(World worldIn, BlockPos pos, boolean p_180694_3_, boolean p_180694_4_, boolean p_180694_5_, boolean p_180694_6_) {
/* 203 */     if (p_180694_4_ && !p_180694_6_) {
/* 204 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.4F, 0.6F);
/* 205 */     } else if (!p_180694_4_ && p_180694_6_) {
/* 206 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.4F, 0.5F);
/* 207 */     } else if (p_180694_3_ && !p_180694_5_) {
/* 208 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.4F, 0.7F);
/* 209 */     } else if (!p_180694_3_ && p_180694_5_) {
/* 210 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.bowhit", 0.4F, 1.2F / (worldIn.rand.nextFloat() * 0.2F + 0.9F));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void func_176262_b(World worldIn, BlockPos p_176262_2_, EnumFacing p_176262_3_) {
/* 215 */     worldIn.notifyNeighborsOfStateChange(p_176262_2_, this);
/* 216 */     worldIn.notifyNeighborsOfStateChange(p_176262_2_.offset(p_176262_3_.getOpposite()), this);
/*     */   }
/*     */   
/*     */   private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 220 */     if (!canPlaceBlockAt(worldIn, pos)) {
/* 221 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 222 */       worldIn.setBlockToAir(pos);
/* 223 */       return false;
/*     */     } 
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 231 */     float f = 0.1875F;
/*     */     
/* 233 */     switch ((EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING)) {
/*     */       case EAST:
/* 235 */         setBlockBounds(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 239 */         setBlockBounds(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 243 */         setBlockBounds(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 247 */         setBlockBounds(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 252 */     boolean flag = ((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue();
/* 253 */     boolean flag1 = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/*     */     
/* 255 */     if (flag || flag1) {
/* 256 */       func_176260_a(worldIn, pos, state, true, false, -1, (IBlockState)null);
/*     */     }
/*     */     
/* 259 */     if (flag1) {
/* 260 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 261 */       worldIn.notifyNeighborsOfStateChange(pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite()), this);
/*     */     } 
/*     */     
/* 264 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 268 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 272 */     return !((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0 : ((state.getValue((IProperty)FACING) == side) ? 15 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 279 */     return true;
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 283 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 290 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0))).withProperty((IProperty)ATTACHED, Boolean.valueOf(((meta & 0x4) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 297 */     int i = 0;
/* 298 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 300 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 301 */       i |= 0x8;
/*     */     }
/*     */     
/* 304 */     if (((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue()) {
/* 305 */       i |= 0x4;
/*     */     }
/*     */     
/* 308 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 312 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED, (IProperty)ATTACHED, (IProperty)SUSPENDED });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockTripWireHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */