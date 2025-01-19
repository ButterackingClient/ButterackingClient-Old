/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public abstract class BlockRedstoneDiode
/*     */   extends BlockDirectional
/*     */ {
/*     */   protected final boolean isRepeaterPowered;
/*     */   
/*     */   protected BlockRedstoneDiode(boolean powered) {
/*  23 */     super(Material.circuits);
/*  24 */     this.isRepeaterPowered = powered;
/*  25 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  29 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  33 */     return World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) ? super.canPlaceBlockAt(worldIn, pos) : false;
/*     */   }
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos) {
/*  37 */     return World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  47 */     if (!isLocked((IBlockAccess)worldIn, pos, state)) {
/*  48 */       boolean flag = shouldBePowered(worldIn, pos, state);
/*     */       
/*  50 */       if (this.isRepeaterPowered && !flag) {
/*  51 */         worldIn.setBlockState(pos, getUnpoweredState(state), 2);
/*  52 */       } else if (!this.isRepeaterPowered) {
/*  53 */         worldIn.setBlockState(pos, getPoweredState(state), 2);
/*     */         
/*  55 */         if (!flag) {
/*  56 */           worldIn.updateBlockTick(pos, getPoweredState(state).getBlock(), getTickDelay(state), -1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  63 */     return (side.getAxis() != EnumFacing.Axis.Y);
/*     */   }
/*     */   
/*     */   protected boolean isPowered(IBlockState state) {
/*  67 */     return this.isRepeaterPowered;
/*     */   }
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  71 */     return getWeakPower(worldIn, pos, state, side);
/*     */   }
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  75 */     return !isPowered(state) ? 0 : ((state.getValue((IProperty)FACING) == side) ? getActiveSignal(worldIn, pos, state) : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  82 */     if (canBlockStay(worldIn, pos)) {
/*  83 */       updateState(worldIn, pos, state);
/*     */     } else {
/*  85 */       dropBlockAsItem(worldIn, pos, state, 0);
/*  86 */       worldIn.setBlockToAir(pos); byte b; int i;
/*     */       EnumFacing[] arrayOfEnumFacing;
/*  88 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*  89 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
/*  95 */     if (!isLocked((IBlockAccess)worldIn, pos, state)) {
/*  96 */       boolean flag = shouldBePowered(worldIn, pos, state);
/*     */       
/*  98 */       if (((this.isRepeaterPowered && !flag) || (!this.isRepeaterPowered && flag)) && !worldIn.isBlockTickPending(pos, this)) {
/*  99 */         int i = -1;
/*     */         
/* 101 */         if (isFacingTowardsRepeater(worldIn, pos, state)) {
/* 102 */           i = -3;
/* 103 */         } else if (this.isRepeaterPowered) {
/* 104 */           i = -2;
/*     */         } 
/*     */         
/* 107 */         worldIn.updateBlockTick(pos, this, getDelay(state), i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isLocked(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/* 113 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
/* 117 */     return (calculateInputStrength(worldIn, pos, state) > 0);
/*     */   }
/*     */   
/*     */   protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state) {
/* 121 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 122 */     BlockPos blockpos = pos.offset(enumfacing);
/* 123 */     int i = worldIn.getRedstonePower(blockpos, enumfacing);
/*     */     
/* 125 */     if (i >= 15) {
/* 126 */       return i;
/*     */     }
/* 128 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 129 */     return Math.max(i, (iblockstate.getBlock() == Blocks.redstone_wire) ? ((Integer)iblockstate.getValue((IProperty)BlockRedstoneWire.POWER)).intValue() : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getPowerOnSides(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/* 134 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 135 */     EnumFacing enumfacing1 = enumfacing.rotateY();
/* 136 */     EnumFacing enumfacing2 = enumfacing.rotateYCCW();
/* 137 */     return Math.max(getPowerOnSide(worldIn, pos.offset(enumfacing1), enumfacing1), getPowerOnSide(worldIn, pos.offset(enumfacing2), enumfacing2));
/*     */   }
/*     */   
/*     */   protected int getPowerOnSide(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 141 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 142 */     Block block = iblockstate.getBlock();
/* 143 */     return canPowerSide(block) ? ((block == Blocks.redstone_wire) ? ((Integer)iblockstate.getValue((IProperty)BlockRedstoneWire.POWER)).intValue() : worldIn.getStrongPower(pos, side)) : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 158 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 165 */     if (shouldBePowered(worldIn, pos, state)) {
/* 166 */       worldIn.scheduleUpdate(pos, this, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 171 */     notifyNeighbors(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   protected void notifyNeighbors(World worldIn, BlockPos pos, IBlockState state) {
/* 175 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 176 */     BlockPos blockpos = pos.offset(enumfacing.getOpposite());
/* 177 */     worldIn.notifyBlockOfStateChange(blockpos, this);
/* 178 */     worldIn.notifyNeighborsOfStateExcept(blockpos, this, enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/* 185 */     if (this.isRepeaterPowered) {
/* 186 */       byte b; int i; EnumFacing[] arrayOfEnumFacing; for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 187 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
/*     */         b++; }
/*     */     
/*     */     } 
/* 191 */     super.onBlockDestroyedByPlayer(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 198 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean canPowerSide(Block blockIn) {
/* 202 */     return blockIn.canProvidePower();
/*     */   }
/*     */   
/*     */   protected int getActiveSignal(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/* 206 */     return 15;
/*     */   }
/*     */   
/*     */   public static boolean isRedstoneRepeaterBlockID(Block blockIn) {
/* 210 */     return !(!Blocks.unpowered_repeater.isAssociated(blockIn) && !Blocks.unpowered_comparator.isAssociated(blockIn));
/*     */   }
/*     */   
/*     */   public boolean isAssociated(Block other) {
/* 214 */     return !(other != getPoweredState(getDefaultState()).getBlock() && other != getUnpoweredState(getDefaultState()).getBlock());
/*     */   }
/*     */   
/*     */   public boolean isFacingTowardsRepeater(World worldIn, BlockPos pos, IBlockState state) {
/* 218 */     EnumFacing enumfacing = ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite();
/* 219 */     BlockPos blockpos = pos.offset(enumfacing);
/* 220 */     return isRedstoneRepeaterBlockID(worldIn.getBlockState(blockpos).getBlock()) ? ((worldIn.getBlockState(blockpos).getValue((IProperty)FACING) != enumfacing)) : false;
/*     */   }
/*     */   
/*     */   protected int getTickDelay(IBlockState state) {
/* 224 */     return getDelay(state);
/*     */   }
/*     */   
/*     */   protected abstract int getDelay(IBlockState paramIBlockState);
/*     */   
/*     */   protected abstract IBlockState getPoweredState(IBlockState paramIBlockState);
/*     */   
/*     */   protected abstract IBlockState getUnpoweredState(IBlockState paramIBlockState);
/*     */   
/*     */   public boolean isAssociatedBlock(Block other) {
/* 234 */     return isAssociated(other);
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 238 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockRedstoneDiode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */