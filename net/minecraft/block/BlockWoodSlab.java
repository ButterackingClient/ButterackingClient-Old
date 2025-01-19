/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockWoodSlab
/*     */   extends BlockSlab {
/*  20 */   public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class);
/*     */   
/*     */   public BlockWoodSlab() {
/*  23 */     super(Material.wood);
/*  24 */     IBlockState iblockstate = this.blockState.getBaseState();
/*     */     
/*  26 */     if (!isDouble()) {
/*  27 */       iblockstate = iblockstate.withProperty((IProperty)HALF, BlockSlab.EnumBlockHalf.BOTTOM);
/*     */     }
/*     */     
/*  30 */     setDefaultState(iblockstate.withProperty((IProperty)VARIANT, BlockPlanks.EnumType.OAK));
/*  31 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state) {
/*  38 */     return ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMapColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  45 */     return Item.getItemFromBlock(Blocks.wooden_slab);
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/*  49 */     return Item.getItemFromBlock(Blocks.wooden_slab);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(int meta) {
/*  56 */     return String.valueOf(getUnlocalizedName()) + "." + BlockPlanks.EnumType.byMetadata(meta).getUnlocalizedName();
/*     */   }
/*     */   
/*     */   public IProperty<?> getVariantProperty() {
/*  60 */     return (IProperty<?>)VARIANT;
/*     */   }
/*     */   
/*     */   public Object getVariant(ItemStack stack) {
/*  64 */     return BlockPlanks.EnumType.byMetadata(stack.getMetadata() & 0x7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*  71 */     if (itemIn != Item.getItemFromBlock(Blocks.double_wooden_slab)) {
/*  72 */       byte b; int i; BlockPlanks.EnumType[] arrayOfEnumType; for (i = (arrayOfEnumType = BlockPlanks.EnumType.values()).length, b = 0; b < i; ) { BlockPlanks.EnumType blockplanks$enumtype = arrayOfEnumType[b];
/*  73 */         list.add(new ItemStack(itemIn, 1, blockplanks$enumtype.getMetadata()));
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  82 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)VARIANT, BlockPlanks.EnumType.byMetadata(meta & 0x7));
/*     */     
/*  84 */     if (!isDouble()) {
/*  85 */       iblockstate = iblockstate.withProperty((IProperty)HALF, ((meta & 0x8) == 0) ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
/*     */     }
/*     */     
/*  88 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  95 */     int i = 0;
/*  96 */     i |= ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */     
/*  98 */     if (!isDouble() && state.getValue((IProperty)HALF) == BlockSlab.EnumBlockHalf.TOP) {
/*  99 */       i |= 0x8;
/*     */     }
/*     */     
/* 102 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 106 */     return isDouble() ? new BlockState(this, new IProperty[] { (IProperty)VARIANT }) : new BlockState(this, new IProperty[] { (IProperty)HALF, (IProperty)VARIANT });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 114 */     return ((BlockPlanks.EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockWoodSlab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */