/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCake
/*     */   extends Block {
/*  22 */   public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 6);
/*     */   
/*     */   protected BlockCake() {
/*  25 */     super(Material.cake);
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)BITES, Integer.valueOf(0)));
/*  27 */     setTickRandomly(true);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  31 */     float f = 0.0625F;
/*  32 */     float f1 = (1 + ((Integer)worldIn.getBlockState(pos).getValue((IProperty)BITES)).intValue() * 2) / 16.0F;
/*  33 */     float f2 = 0.5F;
/*  34 */     setBlockBounds(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  41 */     float f = 0.0625F;
/*  42 */     float f1 = 0.5F;
/*  43 */     setBlockBounds(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  47 */     float f = 0.0625F;
/*  48 */     float f1 = (1 + ((Integer)state.getValue((IProperty)BITES)).intValue() * 2) / 16.0F;
/*  49 */     float f2 = 0.5F;
/*  50 */     return new AxisAlignedBB((pos.getX() + f1), pos.getY(), (pos.getZ() + f), ((pos.getX() + 1) - f), (pos.getY() + f2), ((pos.getZ() + 1) - f));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  54 */     return getCollisionBoundingBox(worldIn, pos, worldIn.getBlockState(pos));
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  65 */     return false;
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  69 */     eatCake(worldIn, pos, state, playerIn);
/*  70 */     return true;
/*     */   }
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/*  74 */     eatCake(worldIn, pos, worldIn.getBlockState(pos), playerIn);
/*     */   }
/*     */   
/*     */   private void eatCake(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/*  78 */     if (player.canEat(false)) {
/*  79 */       player.triggerAchievement(StatList.field_181724_H);
/*  80 */       player.getFoodStats().addStats(2, 0.1F);
/*  81 */       int i = ((Integer)state.getValue((IProperty)BITES)).intValue();
/*     */       
/*  83 */       if (i < 6) {
/*  84 */         worldIn.setBlockState(pos, state.withProperty((IProperty)BITES, Integer.valueOf(i + 1)), 3);
/*     */       } else {
/*  86 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  92 */     return super.canPlaceBlockAt(worldIn, pos) ? canBlockStay(worldIn, pos) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  99 */     if (!canBlockStay(worldIn, pos)) {
/* 100 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean canBlockStay(World worldIn, BlockPos pos) {
/* 105 */     return worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 112 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 119 */     return null;
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 123 */     return Items.cake;
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 127 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 134 */     return getDefaultState().withProperty((IProperty)BITES, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 141 */     return ((Integer)state.getValue((IProperty)BITES)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 145 */     return new BlockState(this, new IProperty[] { (IProperty)BITES });
/*     */   }
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 149 */     return (7 - ((Integer)worldIn.getBlockState(pos).getValue((IProperty)BITES)).intValue()) * 2;
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 153 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockCake.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */