/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeColorHelper;
/*     */ 
/*     */ public class BlockDoublePlant
/*     */   extends BlockBush implements IGrowable {
/*  28 */   public static final PropertyEnum<EnumPlantType> VARIANT = PropertyEnum.create("variant", EnumPlantType.class);
/*  29 */   public static final PropertyEnum<EnumBlockHalf> HALF = PropertyEnum.create("half", EnumBlockHalf.class);
/*  30 */   public static final PropertyEnum<EnumFacing> FACING = (PropertyEnum<EnumFacing>)BlockDirectional.FACING;
/*     */   
/*     */   public BlockDoublePlant() {
/*  33 */     super(Material.vine);
/*  34 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, EnumPlantType.SUNFLOWER).withProperty((IProperty)HALF, EnumBlockHalf.LOWER).withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  35 */     setHardness(0.0F);
/*  36 */     setStepSound(soundTypeGrass);
/*  37 */     setUnlocalizedName("doublePlant");
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  41 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public EnumPlantType getVariant(IBlockAccess worldIn, BlockPos pos) {
/*  45 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  47 */     if (iblockstate.getBlock() == this) {
/*  48 */       iblockstate = getActualState(iblockstate, worldIn, pos);
/*  49 */       return (EnumPlantType)iblockstate.getValue((IProperty)VARIANT);
/*     */     } 
/*  51 */     return EnumPlantType.FERN;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  56 */     return (super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/*  63 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/*  65 */     if (iblockstate.getBlock() != this) {
/*  66 */       return true;
/*     */     }
/*  68 */     EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)getActualState(iblockstate, (IBlockAccess)worldIn, pos).getValue((IProperty)VARIANT);
/*  69 */     return !(blockdoubleplant$enumplanttype != EnumPlantType.FERN && blockdoubleplant$enumplanttype != EnumPlantType.GRASS);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  74 */     if (!canBlockStay(worldIn, pos, state)) {
/*  75 */       boolean flag = (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER);
/*  76 */       BlockPos blockpos = flag ? pos : pos.up();
/*  77 */       BlockPos blockpos1 = flag ? pos.down() : pos;
/*  78 */       Block block = flag ? this : worldIn.getBlockState(blockpos).getBlock();
/*  79 */       Block block1 = flag ? worldIn.getBlockState(blockpos1).getBlock() : this;
/*     */       
/*  81 */       if (block == this) {
/*  82 */         worldIn.setBlockState(blockpos, Blocks.air.getDefaultState(), 2);
/*     */       }
/*     */       
/*  85 */       if (block1 == this) {
/*  86 */         worldIn.setBlockState(blockpos1, Blocks.air.getDefaultState(), 3);
/*     */         
/*  88 */         if (!flag) {
/*  89 */           dropBlockAsItem(worldIn, blockpos1, state, 0);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/*  96 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) {
/*  97 */       return (worldIn.getBlockState(pos.down()).getBlock() == this);
/*     */     }
/*  99 */     IBlockState iblockstate = worldIn.getBlockState(pos.up());
/* 100 */     return (iblockstate.getBlock() == this && super.canBlockStay(worldIn, pos, iblockstate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 108 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) {
/* 109 */       return null;
/*     */     }
/* 111 */     EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)state.getValue((IProperty)VARIANT);
/* 112 */     return (blockdoubleplant$enumplanttype == EnumPlantType.FERN) ? null : ((blockdoubleplant$enumplanttype == EnumPlantType.GRASS) ? ((rand.nextInt(8) == 0) ? Items.wheat_seeds : null) : Item.getItemFromBlock(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 121 */     return (state.getValue((IProperty)HALF) != EnumBlockHalf.UPPER && state.getValue((IProperty)VARIANT) != EnumPlantType.GRASS) ? ((EnumPlantType)state.getValue((IProperty)VARIANT)).getMeta() : 0;
/*     */   }
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 125 */     EnumPlantType blockdoubleplant$enumplanttype = getVariant(worldIn, pos);
/* 126 */     return (blockdoubleplant$enumplanttype != EnumPlantType.GRASS && blockdoubleplant$enumplanttype != EnumPlantType.FERN) ? 16777215 : BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
/*     */   }
/*     */   
/*     */   public void placeAt(World worldIn, BlockPos lowerPos, EnumPlantType variant, int flags) {
/* 130 */     worldIn.setBlockState(lowerPos, getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.LOWER).withProperty((IProperty)VARIANT, variant), flags);
/* 131 */     worldIn.setBlockState(lowerPos.up(), getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.UPPER), flags);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 138 */     worldIn.setBlockState(pos.up(), getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.UPPER), 2);
/*     */   }
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 142 */     if (worldIn.isRemote || player.getCurrentEquippedItem() == null || player.getCurrentEquippedItem().getItem() != Items.shears || state.getValue((IProperty)HALF) != EnumBlockHalf.LOWER || !onHarvest(worldIn, pos, state, player)) {
/* 143 */       super.harvestBlock(worldIn, player, pos, state, te);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 148 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) {
/* 149 */       if (worldIn.getBlockState(pos.down()).getBlock() == this) {
/* 150 */         if (!player.capabilities.isCreativeMode) {
/* 151 */           IBlockState iblockstate = worldIn.getBlockState(pos.down());
/* 152 */           EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)iblockstate.getValue((IProperty)VARIANT);
/*     */           
/* 154 */           if (blockdoubleplant$enumplanttype != EnumPlantType.FERN && blockdoubleplant$enumplanttype != EnumPlantType.GRASS) {
/* 155 */             worldIn.destroyBlock(pos.down(), true);
/* 156 */           } else if (!worldIn.isRemote) {
/* 157 */             if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears) {
/* 158 */               onHarvest(worldIn, pos, iblockstate, player);
/* 159 */               worldIn.setBlockToAir(pos.down());
/*     */             } else {
/* 161 */               worldIn.destroyBlock(pos.down(), true);
/*     */             } 
/*     */           } else {
/* 164 */             worldIn.setBlockToAir(pos.down());
/*     */           } 
/*     */         } else {
/* 167 */           worldIn.setBlockToAir(pos.down());
/*     */         } 
/*     */       }
/* 170 */     } else if (player.capabilities.isCreativeMode && worldIn.getBlockState(pos.up()).getBlock() == this) {
/* 171 */       worldIn.setBlockState(pos.up(), Blocks.air.getDefaultState(), 2);
/*     */     } 
/*     */     
/* 174 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */   }
/*     */   
/*     */   private boolean onHarvest(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 178 */     EnumPlantType blockdoubleplant$enumplanttype = (EnumPlantType)state.getValue((IProperty)VARIANT);
/*     */     
/* 180 */     if (blockdoubleplant$enumplanttype != EnumPlantType.FERN && blockdoubleplant$enumplanttype != EnumPlantType.GRASS) {
/* 181 */       return false;
/*     */     }
/* 183 */     player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/* 184 */     int i = ((blockdoubleplant$enumplanttype == EnumPlantType.GRASS) ? BlockTallGrass.EnumType.GRASS : BlockTallGrass.EnumType.FERN).getMeta();
/* 185 */     spawnAsEntity(worldIn, pos, new ItemStack(Blocks.tallgrass, 2, i));
/* 186 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumPlantType[] arrayOfEnumPlantType;
/* 194 */     for (i = (arrayOfEnumPlantType = EnumPlantType.values()).length, b = 0; b < i; ) { EnumPlantType blockdoubleplant$enumplanttype = arrayOfEnumPlantType[b];
/* 195 */       list.add(new ItemStack(itemIn, 1, blockdoubleplant$enumplanttype.getMeta()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 203 */     return getVariant((IBlockAccess)worldIn, pos).getMeta();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 210 */     EnumPlantType blockdoubleplant$enumplanttype = getVariant((IBlockAccess)worldIn, pos);
/* 211 */     return (blockdoubleplant$enumplanttype != EnumPlantType.GRASS && blockdoubleplant$enumplanttype != EnumPlantType.FERN);
/*     */   }
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 215 */     return true;
/*     */   }
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 219 */     spawnAsEntity(worldIn, pos, new ItemStack(this, 1, getVariant((IBlockAccess)worldIn, pos).getMeta()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 226 */     return ((meta & 0x8) > 0) ? getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.UPPER) : getDefaultState().withProperty((IProperty)HALF, EnumBlockHalf.LOWER).withProperty((IProperty)VARIANT, EnumPlantType.byMetadata(meta & 0x7));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 234 */     if (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) {
/* 235 */       IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*     */       
/* 237 */       if (iblockstate.getBlock() == this) {
/* 238 */         state = state.withProperty((IProperty)VARIANT, iblockstate.getValue((IProperty)VARIANT));
/*     */       }
/*     */     } 
/*     */     
/* 242 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 249 */     return (state.getValue((IProperty)HALF) == EnumBlockHalf.UPPER) ? (0x8 | ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex()) : ((EnumPlantType)state.getValue((IProperty)VARIANT)).getMeta();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 253 */     return new BlockState(this, new IProperty[] { (IProperty)HALF, (IProperty)VARIANT, (IProperty)FACING });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block.EnumOffsetType getOffsetType() {
/* 260 */     return Block.EnumOffsetType.XZ;
/*     */   }
/*     */   
/*     */   public enum EnumBlockHalf implements IStringSerializable {
/* 264 */     UPPER,
/* 265 */     LOWER;
/*     */     
/*     */     public String toString() {
/* 268 */       return getName();
/*     */     }
/*     */     
/*     */     public String getName() {
/* 272 */       return (this == UPPER) ? "upper" : "lower";
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumPlantType implements IStringSerializable {
/* 277 */     SUNFLOWER(0, "sunflower"),
/* 278 */     SYRINGA(1, "syringa"),
/* 279 */     GRASS(2, "double_grass", "grass"),
/* 280 */     FERN(3, "double_fern", "fern"),
/* 281 */     ROSE(4, "double_rose", "rose"),
/* 282 */     PAEONIA(5, "paeonia");
/*     */     
/* 284 */     private static final EnumPlantType[] META_LOOKUP = new EnumPlantType[(values()).length];
/*     */ 
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
/*     */     
/*     */     private final String name;
/*     */ 
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
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumPlantType[] arrayOfEnumPlantType;
/* 324 */       for (i = (arrayOfEnumPlantType = values()).length, b = 0; b < i; ) { EnumPlantType blockdoubleplant$enumplanttype = arrayOfEnumPlantType[b];
/* 325 */         META_LOOKUP[blockdoubleplant$enumplanttype.getMeta()] = blockdoubleplant$enumplanttype;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumPlantType(int meta, String name, String unlocalizedName) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */       this.unlocalizedName = unlocalizedName;
/*     */     }
/*     */     
/*     */     public int getMeta() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumPlantType byMetadata(int meta) {
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


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockDoublePlant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */