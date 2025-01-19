/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDirt
/*     */   extends Block {
/*  22 */   public static final PropertyEnum<DirtType> VARIANT = PropertyEnum.create("variant", DirtType.class);
/*  23 */   public static final PropertyBool SNOWY = PropertyBool.create("snowy");
/*     */   
/*     */   protected BlockDirt() {
/*  26 */     super(Material.ground);
/*  27 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, DirtType.DIRT).withProperty((IProperty)SNOWY, Boolean.valueOf(false)));
/*  28 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state) {
/*  35 */     return ((DirtType)state.getValue((IProperty)VARIANT)).func_181066_d();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  43 */     if (state.getValue((IProperty)VARIANT) == DirtType.PODZOL) {
/*  44 */       Block block = worldIn.getBlockState(pos.up()).getBlock();
/*  45 */       state = state.withProperty((IProperty)SNOWY, Boolean.valueOf(!(block != Blocks.snow && block != Blocks.snow_layer)));
/*     */     } 
/*     */     
/*  48 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*  55 */     list.add(new ItemStack(this, 1, DirtType.DIRT.getMetadata()));
/*  56 */     list.add(new ItemStack(this, 1, DirtType.COARSE_DIRT.getMetadata()));
/*  57 */     list.add(new ItemStack(this, 1, DirtType.PODZOL.getMetadata()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/*  64 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  65 */     return (iblockstate.getBlock() != this) ? 0 : ((DirtType)iblockstate.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  72 */     return getDefaultState().withProperty((IProperty)VARIANT, DirtType.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  79 */     return ((DirtType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/*  83 */     return new BlockState(this, new IProperty[] { (IProperty)VARIANT, (IProperty)SNOWY });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/*  91 */     DirtType blockdirt$dirttype = (DirtType)state.getValue((IProperty)VARIANT);
/*     */     
/*  93 */     if (blockdirt$dirttype == DirtType.PODZOL) {
/*  94 */       blockdirt$dirttype = DirtType.DIRT;
/*     */     }
/*     */     
/*  97 */     return blockdirt$dirttype.getMetadata();
/*     */   }
/*     */   
/*     */   public enum DirtType implements IStringSerializable {
/* 101 */     DIRT(0, "dirt", "default", (String)MapColor.dirtColor),
/* 102 */     COARSE_DIRT(1, "coarse_dirt", "coarse", (String)MapColor.dirtColor),
/* 103 */     PODZOL(2, "podzol", MapColor.obsidianColor);
/*     */     
/* 105 */     private static final DirtType[] METADATA_LOOKUP = new DirtType[(values()).length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int metadata;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String unlocalizedName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final MapColor field_181067_h;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       DirtType[] arrayOfDirtType;
/* 151 */       for (i = (arrayOfDirtType = values()).length, b = 0; b < i; ) { DirtType blockdirt$dirttype = arrayOfDirtType[b];
/* 152 */         METADATA_LOOKUP[blockdirt$dirttype.getMetadata()] = blockdirt$dirttype;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     DirtType(int p_i46397_3_, String p_i46397_4_, String p_i46397_5_, MapColor p_i46397_6_) {
/*     */       this.metadata = p_i46397_3_;
/*     */       this.name = p_i46397_4_;
/*     */       this.unlocalizedName = p_i46397_5_;
/*     */       this.field_181067_h = p_i46397_6_;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.metadata;
/*     */     }
/*     */     
/*     */     public String getUnlocalizedName() {
/*     */       return this.unlocalizedName;
/*     */     }
/*     */     
/*     */     public MapColor func_181066_d() {
/*     */       return this.field_181067_h;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static DirtType byMetadata(int metadata) {
/*     */       if (metadata < 0 || metadata >= METADATA_LOOKUP.length)
/*     */         metadata = 0; 
/*     */       return METADATA_LOOKUP[metadata];
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockDirt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */