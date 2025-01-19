/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockButton
/*     */   extends Block {
/*  24 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  25 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*     */   private final boolean wooden;
/*     */   
/*     */   protected BlockButton(boolean wooden) {
/*  29 */     super(Material.circuits);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/*  31 */     setTickRandomly(true);
/*  32 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  33 */     this.wooden = wooden;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  37 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  44 */     return this.wooden ? 30 : 20;
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
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  62 */     return func_181088_a(worldIn, pos, side.getOpposite()); } public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/*  66 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*  67 */       if (func_181088_a(worldIn, pos, enumfacing)) {
/*  68 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean func_181088_a(World p_181088_0_, BlockPos p_181088_1_, EnumFacing p_181088_2_) {
/*  76 */     BlockPos blockpos = p_181088_1_.offset(p_181088_2_);
/*  77 */     return (p_181088_2_ == EnumFacing.DOWN) ? World.doesBlockHaveSolidTopSurface((IBlockAccess)p_181088_0_, blockpos) : p_181088_0_.getBlockState(blockpos).getBlock().isNormalCube();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  85 */     return func_181088_a(worldIn, pos, facing.getOpposite()) ? getDefaultState().withProperty((IProperty)FACING, (Comparable)facing).withProperty((IProperty)POWERED, Boolean.valueOf(false)) : getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.DOWN).withProperty((IProperty)POWERED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  92 */     if (checkForDrop(worldIn, pos, state) && !func_181088_a(worldIn, pos, ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite())) {
/*  93 */       dropBlockAsItem(worldIn, pos, state, 0);
/*  94 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/*  99 */     if (canPlaceBlockAt(worldIn, pos)) {
/* 100 */       return true;
/*     */     }
/* 102 */     dropBlockAsItem(worldIn, pos, state, 0);
/* 103 */     worldIn.setBlockToAir(pos);
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 109 */     updateBlockBounds(worldIn.getBlockState(pos));
/*     */   }
/*     */   
/*     */   private void updateBlockBounds(IBlockState state) {
/* 113 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 114 */     boolean flag = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/* 115 */     float f = 0.25F;
/* 116 */     float f1 = 0.375F;
/* 117 */     float f2 = (flag ? true : 2) / 16.0F;
/* 118 */     float f3 = 0.125F;
/* 119 */     float f4 = 0.1875F;
/*     */     
/* 121 */     switch (enumfacing) {
/*     */       case EAST:
/* 123 */         setBlockBounds(0.0F, 0.375F, 0.3125F, f2, 0.625F, 0.6875F);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 127 */         setBlockBounds(1.0F - f2, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 131 */         setBlockBounds(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, f2);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 135 */         setBlockBounds(0.3125F, 0.375F, 1.0F - f2, 0.6875F, 0.625F, 1.0F);
/*     */         break;
/*     */       
/*     */       case UP:
/* 139 */         setBlockBounds(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + f2, 0.625F);
/*     */         break;
/*     */       
/*     */       case null:
/* 143 */         setBlockBounds(0.3125F, 1.0F - f2, 0.375F, 0.6875F, 1.0F, 0.625F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 148 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 149 */       return true;
/*     */     }
/* 151 */     worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)), 3);
/* 152 */     worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 153 */     worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
/* 154 */     notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 155 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/* 156 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 161 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 162 */       notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/*     */     }
/*     */     
/* 165 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 169 */     return ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 173 */     return !((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0 : ((state.getValue((IProperty)FACING) == side) ? 15 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 190 */     if (!worldIn.isRemote && (
/* 191 */       (Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 192 */       if (this.wooden) {
/* 193 */         checkForArrows(worldIn, pos, state);
/*     */       } else {
/* 195 */         worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/* 196 */         notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 197 */         worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
/* 198 */         worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 208 */     float f = 0.1875F;
/* 209 */     float f1 = 0.125F;
/* 210 */     float f2 = 0.125F;
/* 211 */     setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 218 */     if (!worldIn.isRemote && 
/* 219 */       this.wooden && 
/* 220 */       !((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 221 */       checkForArrows(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkForArrows(World worldIn, BlockPos pos, IBlockState state) {
/* 228 */     updateBlockBounds(state);
/* 229 */     List<? extends Entity> list = worldIn.getEntitiesWithinAABB(EntityArrow.class, new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ));
/* 230 */     boolean flag = !list.isEmpty();
/* 231 */     boolean flag1 = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/*     */     
/* 233 */     if (flag && !flag1) {
/* 234 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)));
/* 235 */       notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 236 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 237 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
/*     */     } 
/*     */     
/* 240 */     if (!flag && flag1) {
/* 241 */       worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/* 242 */       notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 243 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 244 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
/*     */     } 
/*     */     
/* 247 */     if (flag) {
/* 248 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */   
/*     */   private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing) {
/* 253 */     worldIn.notifyNeighborsOfStateChange(pos, this);
/* 254 */     worldIn.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*     */     EnumFacing enumfacing;
/* 263 */     switch (meta & 0x7) {
/*     */       case 0:
/* 265 */         enumfacing = EnumFacing.DOWN;
/*     */         break;
/*     */       
/*     */       case 1:
/* 269 */         enumfacing = EnumFacing.EAST;
/*     */         break;
/*     */       
/*     */       case 2:
/* 273 */         enumfacing = EnumFacing.WEST;
/*     */         break;
/*     */       
/*     */       case 3:
/* 277 */         enumfacing = EnumFacing.SOUTH;
/*     */         break;
/*     */       
/*     */       case 4:
/* 281 */         enumfacing = EnumFacing.NORTH;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 286 */         enumfacing = EnumFacing.UP;
/*     */         break;
/*     */     } 
/* 289 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*     */     int i;
/* 298 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */       case EAST:
/* 300 */         i = 1;
/*     */         break;
/*     */       
/*     */       case WEST:
/* 304 */         i = 2;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 308 */         i = 3;
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 312 */         i = 4;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 317 */         i = 5;
/*     */         break;
/*     */       
/*     */       case null:
/* 321 */         i = 0;
/*     */         break;
/*     */     } 
/* 324 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/* 325 */       i |= 0x8;
/*     */     }
/*     */     
/* 328 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 332 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */