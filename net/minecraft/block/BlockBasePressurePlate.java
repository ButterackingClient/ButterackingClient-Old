/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockBasePressurePlate
/*     */   extends Block {
/*     */   protected BlockBasePressurePlate(Material materialIn) {
/*  18 */     this(materialIn, materialIn.getMaterialMapColor());
/*     */   }
/*     */   
/*     */   protected BlockBasePressurePlate(Material p_i46401_1_, MapColor p_i46401_2_) {
/*  22 */     super(p_i46401_1_, p_i46401_2_);
/*  23 */     setCreativeTab(CreativeTabs.tabRedstone);
/*  24 */     setTickRandomly(true);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  28 */     setBlockBoundsBasedOnState0(worldIn.getBlockState(pos));
/*     */   }
/*     */   
/*     */   protected void setBlockBoundsBasedOnState0(IBlockState state) {
/*  32 */     boolean flag = (getRedstoneStrength(state) > 0);
/*  33 */     float f = 0.0625F;
/*     */     
/*  35 */     if (flag) {
/*  36 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.03125F, 0.9375F);
/*     */     } else {
/*  38 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  46 */     return 20;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  50 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  57 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  61 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  65 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSpawnInBlock() {
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  76 */     return canBePlacedOn(worldIn, pos.down());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  83 */     if (!canBePlacedOn(worldIn, pos.down())) {
/*  84 */       dropBlockAsItem(worldIn, pos, state, 0);
/*  85 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean canBePlacedOn(World worldIn, BlockPos pos) {
/*  90 */     return !(!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos) && !(worldIn.getBlockState(pos).getBlock() instanceof BlockFence));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 100 */     if (!worldIn.isRemote) {
/* 101 */       int i = getRedstoneStrength(state);
/*     */       
/* 103 */       if (i > 0) {
/* 104 */         updateState(worldIn, pos, state, i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 113 */     if (!worldIn.isRemote) {
/* 114 */       int i = getRedstoneStrength(state);
/*     */       
/* 116 */       if (i == 0) {
/* 117 */         updateState(worldIn, pos, state, i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateState(World worldIn, BlockPos pos, IBlockState state, int oldRedstoneStrength) {
/* 126 */     int i = computeRedstoneStrength(worldIn, pos);
/* 127 */     boolean flag = (oldRedstoneStrength > 0);
/* 128 */     boolean flag1 = (i > 0);
/*     */     
/* 130 */     if (oldRedstoneStrength != i) {
/* 131 */       state = setRedstoneStrength(state, i);
/* 132 */       worldIn.setBlockState(pos, state, 2);
/* 133 */       updateNeighbors(worldIn, pos);
/* 134 */       worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */     } 
/*     */     
/* 137 */     if (!flag1 && flag) {
/* 138 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
/* 139 */     } else if (flag1 && !flag) {
/* 140 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.1D, pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
/*     */     } 
/*     */     
/* 143 */     if (flag1) {
/* 144 */       worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AxisAlignedBB getSensitiveAABB(BlockPos pos) {
/* 152 */     float f = 0.125F;
/* 153 */     return new AxisAlignedBB((pos.getX() + 0.125F), pos.getY(), (pos.getZ() + 0.125F), ((pos.getX() + 1) - 0.125F), pos.getY() + 0.25D, ((pos.getZ() + 1) - 0.125F));
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 157 */     if (getRedstoneStrength(state) > 0) {
/* 158 */       updateNeighbors(worldIn, pos);
/*     */     }
/*     */     
/* 161 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateNeighbors(World worldIn, BlockPos pos) {
/* 168 */     worldIn.notifyNeighborsOfStateChange(pos, this);
/* 169 */     worldIn.notifyNeighborsOfStateChange(pos.down(), this);
/*     */   }
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 173 */     return getRedstoneStrength(state);
/*     */   }
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 177 */     return (side == EnumFacing.UP) ? getRedstoneStrength(state) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 191 */     float f = 0.5F;
/* 192 */     float f1 = 0.125F;
/* 193 */     float f2 = 0.5F;
/* 194 */     setBlockBounds(0.0F, 0.375F, 0.0F, 1.0F, 0.625F, 1.0F);
/*     */   }
/*     */   
/*     */   public int getMobilityFlag() {
/* 198 */     return 1;
/*     */   }
/*     */   
/*     */   protected abstract int computeRedstoneStrength(World paramWorld, BlockPos paramBlockPos);
/*     */   
/*     */   protected abstract int getRedstoneStrength(IBlockState paramIBlockState);
/*     */   
/*     */   protected abstract IBlockState setRedstoneStrength(IBlockState paramIBlockState, int paramInt);
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockBasePressurePlate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */