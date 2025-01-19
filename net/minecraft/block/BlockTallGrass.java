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
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTallGrass
/*     */   extends BlockBush implements IGrowable {
/*  26 */   public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);
/*     */   
/*     */   protected BlockTallGrass() {
/*  29 */     super(Material.vine);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)TYPE, EnumType.DEAD_BUSH));
/*  31 */     float f = 0.4F;
/*  32 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
/*     */   }
/*     */   
/*     */   public int getBlockColor() {
/*  36 */     return ColorizerGrass.getGrassColor(0.5D, 1.0D);
/*     */   }
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/*  40 */     return canPlaceBlockOn(worldIn.getBlockState(pos.down()).getBlock());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/*  47 */     return true;
/*     */   }
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/*  51 */     if (state.getBlock() != this) {
/*  52 */       return super.getRenderColor(state);
/*     */     }
/*  54 */     EnumType blocktallgrass$enumtype = (EnumType)state.getValue((IProperty)TYPE);
/*  55 */     return (blocktallgrass$enumtype == EnumType.DEAD_BUSH) ? 16777215 : ColorizerGrass.getGrassColor(0.5D, 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  60 */     return worldIn.getBiomeGenForCoords(pos).getGrassColorAtPos(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  67 */     return (rand.nextInt(8) == 0) ? Items.wheat_seeds : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDroppedWithBonus(int fortune, Random random) {
/*  74 */     return 1 + random.nextInt(fortune * 2 + 1);
/*     */   }
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/*  78 */     if (!worldIn.isRemote && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears) {
/*  79 */       player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/*  80 */       spawnAsEntity(worldIn, pos, new ItemStack(Blocks.tallgrass, 1, ((EnumType)state.getValue((IProperty)TYPE)).getMeta()));
/*     */     } else {
/*  82 */       super.harvestBlock(worldIn, player, pos, state, te);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/*  90 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  91 */     return iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*  98 */     for (int i = 1; i < 3; i++) {
/*  99 */       list.add(new ItemStack(itemIn, 1, i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 107 */     return (state.getValue((IProperty)TYPE) != EnumType.DEAD_BUSH);
/*     */   }
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 111 */     return true;
/*     */   }
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 115 */     BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = BlockDoublePlant.EnumPlantType.GRASS;
/*     */     
/* 117 */     if (state.getValue((IProperty)TYPE) == EnumType.FERN) {
/* 118 */       blockdoubleplant$enumplanttype = BlockDoublePlant.EnumPlantType.FERN;
/*     */     }
/*     */     
/* 121 */     if (Blocks.double_plant.canPlaceBlockAt(worldIn, pos)) {
/* 122 */       Blocks.double_plant.placeAt(worldIn, pos, blockdoubleplant$enumplanttype, 2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 130 */     return getDefaultState().withProperty((IProperty)TYPE, EnumType.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 137 */     return ((EnumType)state.getValue((IProperty)TYPE)).getMeta();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 141 */     return new BlockState(this, new IProperty[] { (IProperty)TYPE });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block.EnumOffsetType getOffsetType() {
/* 148 */     return Block.EnumOffsetType.XYZ;
/*     */   }
/*     */   
/*     */   public enum EnumType implements IStringSerializable {
/* 152 */     DEAD_BUSH(0, "dead_bush"),
/* 153 */     GRASS(1, "tall_grass"),
/* 154 */     FERN(2, "fern");
/*     */     
/* 156 */     private static final EnumType[] META_LOOKUP = new EnumType[(values()).length];
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
/*     */     private final String name;
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
/*     */       EnumType[] arrayOfEnumType;
/* 186 */       for (i = (arrayOfEnumType = values()).length, b = 0; b < i; ) { EnumType blocktallgrass$enumtype = arrayOfEnumType[b];
/* 187 */         META_LOOKUP[blocktallgrass$enumtype.getMeta()] = blocktallgrass$enumtype;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumType(int meta, String name) {
/*     */       this.meta = meta;
/*     */       this.name = name;
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
/*     */     public static EnumType byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockTallGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */