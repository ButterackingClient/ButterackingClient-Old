/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCactus
/*     */   extends Block {
/*  21 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
/*     */   
/*     */   protected BlockCactus() {
/*  24 */     super(Material.cactus);
/*  25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  26 */     setTickRandomly(true);
/*  27 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  31 */     BlockPos blockpos = pos.up();
/*     */     
/*  33 */     if (worldIn.isAirBlock(blockpos)) {
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
/*  44 */           worldIn.setBlockState(blockpos, getDefaultState());
/*  45 */           IBlockState iblockstate = state.withProperty((IProperty)AGE, Integer.valueOf(0));
/*  46 */           worldIn.setBlockState(pos, iblockstate, 4);
/*  47 */           onNeighborBlockChange(worldIn, blockpos, iblockstate, this);
/*     */         } else {
/*  49 */           worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(j + 1)), 4);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  56 */     float f = 0.0625F;
/*  57 */     return new AxisAlignedBB((pos.getX() + f), pos.getY(), (pos.getZ() + f), ((pos.getX() + 1) - f), ((pos.getY() + 1) - f), ((pos.getZ() + 1) - f));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  61 */     float f = 0.0625F;
/*  62 */     return new AxisAlignedBB((pos.getX() + f), pos.getY(), (pos.getZ() + f), ((pos.getX() + 1) - f), (pos.getY() + 1), ((pos.getZ() + 1) - f));
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  73 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  77 */     return super.canPlaceBlockAt(worldIn, pos) ? canBlockStay(worldIn, pos) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  84 */     if (!canBlockStay(worldIn, pos)) {
/*  85 */       worldIn.destroyBlock(pos, true);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos) {
/*  90 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*  91 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getMaterial().isSolid()) {
/*  92 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     Block block = worldIn.getBlockState(pos.down()).getBlock();
/*  97 */     return !(block != Blocks.cactus && block != Blocks.sand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 104 */     entityIn.attackEntityFrom(DamageSource.cactus, 1.0F);
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 108 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 115 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 122 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 126 */     return new BlockState(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockCactus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */