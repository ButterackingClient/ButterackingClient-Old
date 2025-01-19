/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneRepeater
/*     */   extends BlockRedstoneDiode {
/*  22 */   public static final PropertyBool LOCKED = PropertyBool.create("locked");
/*  23 */   public static final PropertyInteger DELAY = PropertyInteger.create("delay", 1, 4);
/*     */   
/*     */   protected BlockRedstoneRepeater(boolean powered) {
/*  26 */     super(powered);
/*  27 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)DELAY, Integer.valueOf(1)).withProperty((IProperty)LOCKED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  34 */     return StatCollector.translateToLocal("item.diode.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  42 */     return state.withProperty((IProperty)LOCKED, Boolean.valueOf(isLocked(worldIn, pos, state)));
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  46 */     if (!playerIn.capabilities.allowEdit) {
/*  47 */       return false;
/*     */     }
/*  49 */     worldIn.setBlockState(pos, state.cycleProperty((IProperty)DELAY), 3);
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getDelay(IBlockState state) {
/*  55 */     return ((Integer)state.getValue((IProperty)DELAY)).intValue() * 2;
/*     */   }
/*     */   
/*     */   protected IBlockState getPoweredState(IBlockState unpoweredState) {
/*  59 */     Integer integer = (Integer)unpoweredState.getValue((IProperty)DELAY);
/*  60 */     Boolean obool = (Boolean)unpoweredState.getValue((IProperty)LOCKED);
/*  61 */     EnumFacing enumfacing = (EnumFacing)unpoweredState.getValue((IProperty)FACING);
/*  62 */     return Blocks.powered_repeater.getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)DELAY, integer).withProperty((IProperty)LOCKED, obool);
/*     */   }
/*     */   
/*     */   protected IBlockState getUnpoweredState(IBlockState poweredState) {
/*  66 */     Integer integer = (Integer)poweredState.getValue((IProperty)DELAY);
/*  67 */     Boolean obool = (Boolean)poweredState.getValue((IProperty)LOCKED);
/*  68 */     EnumFacing enumfacing = (EnumFacing)poweredState.getValue((IProperty)FACING);
/*  69 */     return Blocks.unpowered_repeater.getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)DELAY, integer).withProperty((IProperty)LOCKED, obool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  76 */     return Items.repeater;
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  80 */     return Items.repeater;
/*     */   }
/*     */   
/*     */   public boolean isLocked(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/*  84 */     return (getPowerOnSides(worldIn, pos, state) > 0);
/*     */   }
/*     */   
/*     */   protected boolean canPowerSide(Block blockIn) {
/*  88 */     return isRedstoneRepeaterBlockID(blockIn);
/*     */   }
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  92 */     if (this.isRepeaterPowered) {
/*  93 */       EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  94 */       double d0 = (pos.getX() + 0.5F) + (rand.nextFloat() - 0.5F) * 0.2D;
/*  95 */       double d1 = (pos.getY() + 0.4F) + (rand.nextFloat() - 0.5F) * 0.2D;
/*  96 */       double d2 = (pos.getZ() + 0.5F) + (rand.nextFloat() - 0.5F) * 0.2D;
/*  97 */       float f = -5.0F;
/*     */       
/*  99 */       if (rand.nextBoolean()) {
/* 100 */         f = (((Integer)state.getValue((IProperty)DELAY)).intValue() * 2 - 1);
/*     */       }
/*     */       
/* 103 */       f /= 16.0F;
/* 104 */       double d3 = (f * enumfacing.getFrontOffsetX());
/* 105 */       double d4 = (f * enumfacing.getFrontOffsetZ());
/* 106 */       worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 111 */     super.breakBlock(worldIn, pos, state);
/* 112 */     notifyNeighbors(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 119 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta)).withProperty((IProperty)LOCKED, Boolean.valueOf(false)).withProperty((IProperty)DELAY, Integer.valueOf(1 + (meta >> 2)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 126 */     int i = 0;
/* 127 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/* 128 */     i |= ((Integer)state.getValue((IProperty)DELAY)).intValue() - 1 << 2;
/* 129 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 133 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)DELAY, (IProperty)LOCKED });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockRedstoneRepeater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */