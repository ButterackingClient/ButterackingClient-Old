/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockReed
/*     */   extends Block {
/*  21 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
/*     */   
/*     */   protected BlockReed() {
/*  24 */     super(Material.plants);
/*  25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  26 */     float f = 0.375F;
/*  27 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
/*  28 */     setTickRandomly(true);
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  32 */     if ((worldIn.getBlockState(pos.down()).getBlock() == Blocks.reeds || checkForDrop(worldIn, pos, state)) && 
/*  33 */       worldIn.isAirBlock(pos.up())) {
/*     */       int i;
/*     */       
/*  36 */       for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; i++);
/*     */ 
/*     */ 
/*     */       
/*  40 */       if (i < 3) {
/*  41 */         int j = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/*  43 */         if (j == 15) {
/*  44 */           worldIn.setBlockState(pos.up(), getDefaultState());
/*  45 */           worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(0)), 4);
/*     */         } else {
/*  47 */           worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(j + 1)), 4);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  55 */     Block block = worldIn.getBlockState(pos.down()).getBlock();
/*     */     
/*  57 */     if (block == this)
/*  58 */       return true; 
/*  59 */     if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.sand) {
/*  60 */       return false;
/*     */     }
/*  62 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*  63 */       if (worldIn.getBlockState(pos.offset(enumfacing).down()).getBlock().getMaterial() == Material.water) {
/*  64 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  76 */     checkForDrop(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   protected final boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/*  80 */     if (canBlockStay(worldIn, pos)) {
/*  81 */       return true;
/*     */     }
/*  83 */     dropBlockAsItem(worldIn, pos, state, 0);
/*  84 */     worldIn.setBlockToAir(pos);
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos) {
/*  90 */     return canPlaceBlockAt(worldIn, pos);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  94 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 101 */     return Items.reeds;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/* 112 */     return false;
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 116 */     return Items.reeds;
/*     */   }
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 120 */     return worldIn.getBiomeGenForCoords(pos).getGrassColorAtPos(pos);
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 124 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 131 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 138 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 142 */     return new BlockState(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockReed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */