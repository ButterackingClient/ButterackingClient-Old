/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
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
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockWall
/*     */   extends Block {
/*  24 */   public static final PropertyBool UP = PropertyBool.create("up");
/*  25 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  26 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  27 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  28 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  29 */   public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
/*     */   
/*     */   public BlockWall(Block modelBlock) {
/*  32 */     super(modelBlock.blockMaterial);
/*  33 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)UP, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)).withProperty((IProperty)VARIANT, EnumType.NORMAL));
/*  34 */     setHardness(modelBlock.blockHardness);
/*  35 */     setResistance(modelBlock.blockResistance / 3.0F);
/*  36 */     setStepSound(modelBlock.stepSound);
/*  37 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  44 */     return StatCollector.translateToLocal(String.valueOf(getUnlocalizedName()) + "." + EnumType.NORMAL.getUnlocalizedName() + ".name");
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  48 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  52 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  59 */     return false;
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  63 */     boolean flag = canConnectTo(worldIn, pos.north());
/*  64 */     boolean flag1 = canConnectTo(worldIn, pos.south());
/*  65 */     boolean flag2 = canConnectTo(worldIn, pos.west());
/*  66 */     boolean flag3 = canConnectTo(worldIn, pos.east());
/*  67 */     float f = 0.25F;
/*  68 */     float f1 = 0.75F;
/*  69 */     float f2 = 0.25F;
/*  70 */     float f3 = 0.75F;
/*  71 */     float f4 = 1.0F;
/*     */     
/*  73 */     if (flag) {
/*  74 */       f2 = 0.0F;
/*     */     }
/*     */     
/*  77 */     if (flag1) {
/*  78 */       f3 = 1.0F;
/*     */     }
/*     */     
/*  81 */     if (flag2) {
/*  82 */       f = 0.0F;
/*     */     }
/*     */     
/*  85 */     if (flag3) {
/*  86 */       f1 = 1.0F;
/*     */     }
/*     */     
/*  89 */     if (flag && flag1 && !flag2 && !flag3) {
/*  90 */       f4 = 0.8125F;
/*  91 */       f = 0.3125F;
/*  92 */       f1 = 0.6875F;
/*  93 */     } else if (!flag && !flag1 && flag2 && flag3) {
/*  94 */       f4 = 0.8125F;
/*  95 */       f2 = 0.3125F;
/*  96 */       f3 = 0.6875F;
/*     */     } 
/*     */     
/*  99 */     setBlockBounds(f, 0.0F, f2, f1, f4, f3);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 103 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 104 */     this.maxY = 1.5D;
/* 105 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
/* 109 */     Block block = worldIn.getBlockState(pos).getBlock();
/* 110 */     return (block == Blocks.barrier) ? false : ((block != this && !(block instanceof BlockFenceGate)) ? ((block.blockMaterial.isOpaque() && block.isFullCube()) ? ((block.blockMaterial != Material.gourd)) : false) : true);
/*     */   }
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumType[] arrayOfEnumType;
/* 117 */     for (i = (arrayOfEnumType = EnumType.values()).length, b = 0; b < i; ) { EnumType blockwall$enumtype = arrayOfEnumType[b];
/* 118 */       list.add(new ItemStack(itemIn, 1, blockwall$enumtype.getMetadata()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 127 */     return ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 131 */     return (side == EnumFacing.DOWN) ? super.shouldSideBeRendered(worldIn, pos, side) : true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 138 */     return getDefaultState().withProperty((IProperty)VARIANT, EnumType.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 145 */     return ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 153 */     return state.withProperty((IProperty)UP, Boolean.valueOf(!worldIn.isAirBlock(pos.up()))).withProperty((IProperty)NORTH, Boolean.valueOf(canConnectTo(worldIn, pos.north()))).withProperty((IProperty)EAST, Boolean.valueOf(canConnectTo(worldIn, pos.east()))).withProperty((IProperty)SOUTH, Boolean.valueOf(canConnectTo(worldIn, pos.south()))).withProperty((IProperty)WEST, Boolean.valueOf(canConnectTo(worldIn, pos.west())));
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 157 */     return new BlockState(this, new IProperty[] { (IProperty)UP, (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH, (IProperty)VARIANT });
/*     */   }
/*     */   
/*     */   public enum EnumType implements IStringSerializable {
/* 161 */     NORMAL(0, "cobblestone", "normal"),
/* 162 */     MOSSY(1, "mossy_cobblestone", "mossy");
/*     */     
/* 164 */     private static final EnumType[] META_LOOKUP = new EnumType[(values()).length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int meta;
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
/*     */     private String unlocalizedName;
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
/*     */       EnumType[] arrayOfEnumType;
/* 200 */       for (i = (arrayOfEnumType = values()).length, b = 0; b < i; ) { EnumType blockwall$enumtype = arrayOfEnumType[b];
/* 201 */         META_LOOKUP[blockwall$enumtype.getMetadata()] = blockwall$enumtype;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumType(int meta, String name, String unlocalizedName) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */       this.unlocalizedName = unlocalizedName;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumType byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public String getUnlocalizedName() {
/*     */       return this.unlocalizedName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockWall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */