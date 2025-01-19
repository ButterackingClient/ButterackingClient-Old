/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCocoa
/*     */   extends BlockDirectional implements IGrowable {
/*  24 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);
/*     */   
/*     */   public BlockCocoa() {
/*  27 */     super(Material.plants);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  29 */     setTickRandomly(true);
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  33 */     if (!canBlockStay(worldIn, pos, state)) {
/*  34 */       dropBlock(worldIn, pos, state);
/*  35 */     } else if (worldIn.rand.nextInt(5) == 0) {
/*  36 */       int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */       
/*  38 */       if (i < 2) {
/*  39 */         worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(i + 1)), 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/*  45 */     pos = pos.offset((EnumFacing)state.getValue((IProperty)FACING));
/*  46 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  47 */     return (iblockstate.getBlock() == Blocks.log && iblockstate.getValue((IProperty)BlockPlanks.VARIANT) == BlockPlanks.EnumType.JUNGLE);
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  58 */     return false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  62 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  63 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  67 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  68 */     return super.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  73 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  74 */     EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/*  75 */     int i = ((Integer)iblockstate.getValue((IProperty)AGE)).intValue();
/*  76 */     int j = 4 + i * 2;
/*  77 */     int k = 5 + i * 2;
/*  78 */     float f = j / 2.0F;
/*     */     
/*  80 */     switch (enumfacing) {
/*     */       case SOUTH:
/*  82 */         setBlockBounds((8.0F - f) / 16.0F, (12.0F - k) / 16.0F, (15.0F - j) / 16.0F, (8.0F + f) / 16.0F, 0.75F, 0.9375F);
/*     */         break;
/*     */       
/*     */       case NORTH:
/*  86 */         setBlockBounds((8.0F - f) / 16.0F, (12.0F - k) / 16.0F, 0.0625F, (8.0F + f) / 16.0F, 0.75F, (1.0F + j) / 16.0F);
/*     */         break;
/*     */       
/*     */       case WEST:
/*  90 */         setBlockBounds(0.0625F, (12.0F - k) / 16.0F, (8.0F - f) / 16.0F, (1.0F + j) / 16.0F, 0.75F, (8.0F + f) / 16.0F);
/*     */         break;
/*     */       
/*     */       case EAST:
/*  94 */         setBlockBounds((15.0F - j) / 16.0F, (12.0F - k) / 16.0F, (8.0F - f) / 16.0F, 0.9375F, 0.75F, (8.0F + f) / 16.0F);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 102 */     EnumFacing enumfacing = EnumFacing.fromAngle(placer.rotationYaw);
/* 103 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)enumfacing), 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 111 */     if (!facing.getAxis().isHorizontal()) {
/* 112 */       facing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 115 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing.getOpposite()).withProperty((IProperty)AGE, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 122 */     if (!canBlockStay(worldIn, pos, state)) {
/* 123 */       dropBlock(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */   
/*     */   private void dropBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 128 */     worldIn.setBlockState(pos, Blocks.air.getDefaultState(), 3);
/* 129 */     dropBlockAsItem(worldIn, pos, state, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 136 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/* 137 */     int j = 1;
/*     */     
/* 139 */     if (i >= 2) {
/* 140 */       j = 3;
/*     */     }
/*     */     
/* 143 */     for (int k = 0; k < j; k++) {
/* 144 */       spawnAsEntity(worldIn, pos, new ItemStack(Items.dye, 1, EnumDyeColor.BROWN.getDyeDamage()));
/*     */     }
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 149 */     return Items.dye;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 156 */     return EnumDyeColor.BROWN.getDyeDamage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 163 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() < 2);
/*     */   }
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 167 */     return true;
/*     */   }
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 171 */     worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(((Integer)state.getValue((IProperty)AGE)).intValue() + 1)), 2);
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 175 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 182 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta)).withProperty((IProperty)AGE, Integer.valueOf((meta & 0xF) >> 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 189 */     int i = 0;
/* 190 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/* 191 */     i |= ((Integer)state.getValue((IProperty)AGE)).intValue() << 2;
/* 192 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 196 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockCocoa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */