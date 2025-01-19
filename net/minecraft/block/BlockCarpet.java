/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCarpet
/*     */   extends Block {
/*  21 */   public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);
/*     */   
/*     */   protected BlockCarpet() {
/*  24 */     super(Material.carpet);
/*  25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)COLOR, (Comparable)EnumDyeColor.WHITE));
/*  26 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
/*  27 */     setTickRandomly(true);
/*  28 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  29 */     setBlockBoundsFromMeta(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state) {
/*  36 */     return ((EnumDyeColor)state.getValue((IProperty)COLOR)).getMapColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  43 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  47 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  54 */     setBlockBoundsFromMeta(0);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  58 */     setBlockBoundsFromMeta(0);
/*     */   }
/*     */   
/*     */   protected void setBlockBoundsFromMeta(int meta) {
/*  62 */     int i = 0;
/*  63 */     float f = (1 * (1 + i)) / 16.0F;
/*  64 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  68 */     return (super.canPlaceBlockAt(worldIn, pos) && canBlockStay(worldIn, pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  75 */     checkForDrop(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/*  79 */     if (!canBlockStay(worldIn, pos)) {
/*  80 */       dropBlockAsItem(worldIn, pos, state, 0);
/*  81 */       worldIn.setBlockToAir(pos);
/*  82 */       return false;
/*     */     } 
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canBlockStay(World worldIn, BlockPos pos) {
/*  89 */     return !worldIn.isAirBlock(pos.down());
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  93 */     return (side == EnumFacing.UP) ? true : super.shouldSideBeRendered(worldIn, pos, side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 101 */     return ((EnumDyeColor)state.getValue((IProperty)COLOR)).getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/* 108 */     for (int i = 0; i < 16; i++) {
/* 109 */       list.add(new ItemStack(itemIn, 1, i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 117 */     return getDefaultState().withProperty((IProperty)COLOR, (Comparable)EnumDyeColor.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 124 */     return ((EnumDyeColor)state.getValue((IProperty)COLOR)).getMetadata();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 128 */     return new BlockState(this, new IProperty[] { (IProperty)COLOR });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockCarpet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */