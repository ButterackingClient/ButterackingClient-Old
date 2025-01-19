/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityFlowerPot;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFlowerPot
/*     */   extends BlockContainer
/*     */ {
/*  29 */   public static final PropertyInteger LEGACY_DATA = PropertyInteger.create("legacy_data", 0, 15);
/*  30 */   public static final PropertyEnum<EnumFlowerType> CONTENTS = PropertyEnum.create("contents", EnumFlowerType.class);
/*     */   
/*     */   public BlockFlowerPot() {
/*  33 */     super(Material.circuits);
/*  34 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)CONTENTS, EnumFlowerType.EMPTY).withProperty((IProperty)LEGACY_DATA, Integer.valueOf(0)));
/*  35 */     setBlockBoundsForItemRender();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  42 */     return StatCollector.translateToLocal("item.flowerPot.name");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  49 */     float f = 0.375F;
/*  50 */     float f1 = f / 2.0F;
/*  51 */     setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f, 0.5F + f1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/*  65 */     return 3;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  69 */     return false;
/*     */   }
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  73 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/*  75 */     if (tileentity instanceof TileEntityFlowerPot) {
/*  76 */       Item item = ((TileEntityFlowerPot)tileentity).getFlowerPotItem();
/*     */       
/*  78 */       if (item instanceof net.minecraft.item.ItemBlock) {
/*  79 */         return Block.getBlockFromItem(item).colorMultiplier(worldIn, pos, renderPass);
/*     */       }
/*     */     } 
/*     */     
/*  83 */     return 16777215;
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  87 */     ItemStack itemstack = playerIn.inventory.getCurrentItem();
/*     */     
/*  89 */     if (itemstack != null && itemstack.getItem() instanceof net.minecraft.item.ItemBlock) {
/*  90 */       TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/*     */       
/*  92 */       if (tileentityflowerpot == null)
/*  93 */         return false; 
/*  94 */       if (tileentityflowerpot.getFlowerPotItem() != null) {
/*  95 */         return false;
/*     */       }
/*  97 */       Block block = Block.getBlockFromItem(itemstack.getItem());
/*     */       
/*  99 */       if (!canNotContain(block, itemstack.getMetadata())) {
/* 100 */         return false;
/*     */       }
/* 102 */       tileentityflowerpot.setFlowerPotData(itemstack.getItem(), itemstack.getMetadata());
/* 103 */       tileentityflowerpot.markDirty();
/* 104 */       worldIn.markBlockForUpdate(pos);
/* 105 */       playerIn.triggerAchievement(StatList.field_181736_T);
/*     */       
/* 107 */       if (!playerIn.capabilities.isCreativeMode && --itemstack.stackSize <= 0) {
/* 108 */         playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, null);
/*     */       }
/*     */       
/* 111 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canNotContain(Block blockIn, int meta) {
/* 120 */     return (blockIn != Blocks.yellow_flower && blockIn != Blocks.red_flower && blockIn != Blocks.cactus && blockIn != Blocks.brown_mushroom && blockIn != Blocks.red_mushroom && blockIn != Blocks.sapling && blockIn != Blocks.deadbush) ? ((blockIn == Blocks.tallgrass && meta == BlockTallGrass.EnumType.FERN.getMeta())) : true;
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 124 */     TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/* 125 */     return (tileentityflowerpot != null && tileentityflowerpot.getFlowerPotItem() != null) ? tileentityflowerpot.getFlowerPotItem() : Items.flower_pot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 132 */     TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/* 133 */     return (tileentityflowerpot != null && tileentityflowerpot.getFlowerPotItem() != null) ? tileentityflowerpot.getFlowerPotData() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFlowerPot() {
/* 140 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 144 */     return (super.canPlaceBlockAt(worldIn, pos) && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 151 */     if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())) {
/* 152 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 153 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 158 */     TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/*     */     
/* 160 */     if (tileentityflowerpot != null && tileentityflowerpot.getFlowerPotItem() != null) {
/* 161 */       spawnAsEntity(worldIn, pos, new ItemStack(tileentityflowerpot.getFlowerPotItem(), 1, tileentityflowerpot.getFlowerPotData()));
/*     */     }
/*     */     
/* 164 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 168 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */     
/* 170 */     if (player.capabilities.isCreativeMode) {
/* 171 */       TileEntityFlowerPot tileentityflowerpot = getTileEntity(worldIn, pos);
/*     */       
/* 173 */       if (tileentityflowerpot != null) {
/* 174 */         tileentityflowerpot.setFlowerPotData(null, 0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 183 */     return Items.flower_pot;
/*     */   }
/*     */   
/*     */   private TileEntityFlowerPot getTileEntity(World worldIn, BlockPos pos) {
/* 187 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/* 188 */     return (tileentity instanceof TileEntityFlowerPot) ? (TileEntityFlowerPot)tileentity : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 195 */     Block block = null;
/* 196 */     int i = 0;
/*     */     
/* 198 */     switch (meta) {
/*     */       case 1:
/* 200 */         block = Blocks.red_flower;
/* 201 */         i = BlockFlower.EnumFlowerType.POPPY.getMeta();
/*     */         break;
/*     */       
/*     */       case 2:
/* 205 */         block = Blocks.yellow_flower;
/*     */         break;
/*     */       
/*     */       case 3:
/* 209 */         block = Blocks.sapling;
/* 210 */         i = BlockPlanks.EnumType.OAK.getMetadata();
/*     */         break;
/*     */       
/*     */       case 4:
/* 214 */         block = Blocks.sapling;
/* 215 */         i = BlockPlanks.EnumType.SPRUCE.getMetadata();
/*     */         break;
/*     */       
/*     */       case 5:
/* 219 */         block = Blocks.sapling;
/* 220 */         i = BlockPlanks.EnumType.BIRCH.getMetadata();
/*     */         break;
/*     */       
/*     */       case 6:
/* 224 */         block = Blocks.sapling;
/* 225 */         i = BlockPlanks.EnumType.JUNGLE.getMetadata();
/*     */         break;
/*     */       
/*     */       case 7:
/* 229 */         block = Blocks.red_mushroom;
/*     */         break;
/*     */       
/*     */       case 8:
/* 233 */         block = Blocks.brown_mushroom;
/*     */         break;
/*     */       
/*     */       case 9:
/* 237 */         block = Blocks.cactus;
/*     */         break;
/*     */       
/*     */       case 10:
/* 241 */         block = Blocks.deadbush;
/*     */         break;
/*     */       
/*     */       case 11:
/* 245 */         block = Blocks.tallgrass;
/* 246 */         i = BlockTallGrass.EnumType.FERN.getMeta();
/*     */         break;
/*     */       
/*     */       case 12:
/* 250 */         block = Blocks.sapling;
/* 251 */         i = BlockPlanks.EnumType.ACACIA.getMetadata();
/*     */         break;
/*     */       
/*     */       case 13:
/* 255 */         block = Blocks.sapling;
/* 256 */         i = BlockPlanks.EnumType.DARK_OAK.getMetadata();
/*     */         break;
/*     */     } 
/* 259 */     return (TileEntity)new TileEntityFlowerPot(Item.getItemFromBlock(block), i);
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 263 */     return new BlockState(this, new IProperty[] { (IProperty)CONTENTS, (IProperty)LEGACY_DATA });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 270 */     return ((Integer)state.getValue((IProperty)LEGACY_DATA)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 278 */     EnumFlowerType blockflowerpot$enumflowertype = EnumFlowerType.EMPTY;
/* 279 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 281 */     if (tileentity instanceof TileEntityFlowerPot)
/* 282 */     { TileEntityFlowerPot tileentityflowerpot = (TileEntityFlowerPot)tileentity;
/* 283 */       Item item = tileentityflowerpot.getFlowerPotItem();
/*     */       
/* 285 */       if (item instanceof net.minecraft.item.ItemBlock)
/* 286 */       { int i = tileentityflowerpot.getFlowerPotData();
/* 287 */         Block block = Block.getBlockFromItem(item);
/*     */         
/* 289 */         if (block == Blocks.sapling)
/* 290 */         { switch (BlockPlanks.EnumType.byMetadata(i))
/*     */           { case OAK:
/* 292 */               blockflowerpot$enumflowertype = EnumFlowerType.OAK_SAPLING;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 386 */               return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case SPRUCE: blockflowerpot$enumflowertype = EnumFlowerType.SPRUCE_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case BIRCH: blockflowerpot$enumflowertype = EnumFlowerType.BIRCH_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case JUNGLE: blockflowerpot$enumflowertype = EnumFlowerType.JUNGLE_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case null: blockflowerpot$enumflowertype = EnumFlowerType.ACACIA_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case DARK_OAK: blockflowerpot$enumflowertype = EnumFlowerType.DARK_OAK_SAPLING; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype); }  blockflowerpot$enumflowertype = EnumFlowerType.EMPTY; } else if (block == Blocks.tallgrass) { switch (i) { case 0: blockflowerpot$enumflowertype = EnumFlowerType.DEAD_BUSH; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case 2: blockflowerpot$enumflowertype = EnumFlowerType.FERN; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype); }  blockflowerpot$enumflowertype = EnumFlowerType.EMPTY; } else if (block == Blocks.yellow_flower) { blockflowerpot$enumflowertype = EnumFlowerType.DANDELION; } else if (block == Blocks.red_flower) { switch (BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.RED, i)) { case POPPY: blockflowerpot$enumflowertype = EnumFlowerType.POPPY; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case BLUE_ORCHID: blockflowerpot$enumflowertype = EnumFlowerType.BLUE_ORCHID; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case null: blockflowerpot$enumflowertype = EnumFlowerType.ALLIUM; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case HOUSTONIA: blockflowerpot$enumflowertype = EnumFlowerType.HOUSTONIA; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case RED_TULIP: blockflowerpot$enumflowertype = EnumFlowerType.RED_TULIP; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case ORANGE_TULIP: blockflowerpot$enumflowertype = EnumFlowerType.ORANGE_TULIP; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case WHITE_TULIP: blockflowerpot$enumflowertype = EnumFlowerType.WHITE_TULIP; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case PINK_TULIP: blockflowerpot$enumflowertype = EnumFlowerType.PINK_TULIP; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);case OXEYE_DAISY: blockflowerpot$enumflowertype = EnumFlowerType.OXEYE_DAISY; return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype); }  blockflowerpot$enumflowertype = EnumFlowerType.EMPTY; } else if (block == Blocks.red_mushroom) { blockflowerpot$enumflowertype = EnumFlowerType.MUSHROOM_RED; } else if (block == Blocks.brown_mushroom) { blockflowerpot$enumflowertype = EnumFlowerType.MUSHROOM_BROWN; } else if (block == Blocks.deadbush) { blockflowerpot$enumflowertype = EnumFlowerType.DEAD_BUSH; } else if (block == Blocks.cactus) { blockflowerpot$enumflowertype = EnumFlowerType.CACTUS; }  }  }  return state.withProperty((IProperty)CONTENTS, blockflowerpot$enumflowertype);
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 390 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */   
/*     */   public enum EnumFlowerType implements IStringSerializable {
/* 394 */     EMPTY("empty"),
/* 395 */     POPPY("rose"),
/* 396 */     BLUE_ORCHID("blue_orchid"),
/* 397 */     ALLIUM("allium"),
/* 398 */     HOUSTONIA("houstonia"),
/* 399 */     RED_TULIP("red_tulip"),
/* 400 */     ORANGE_TULIP("orange_tulip"),
/* 401 */     WHITE_TULIP("white_tulip"),
/* 402 */     PINK_TULIP("pink_tulip"),
/* 403 */     OXEYE_DAISY("oxeye_daisy"),
/* 404 */     DANDELION("dandelion"),
/* 405 */     OAK_SAPLING("oak_sapling"),
/* 406 */     SPRUCE_SAPLING("spruce_sapling"),
/* 407 */     BIRCH_SAPLING("birch_sapling"),
/* 408 */     JUNGLE_SAPLING("jungle_sapling"),
/* 409 */     ACACIA_SAPLING("acacia_sapling"),
/* 410 */     DARK_OAK_SAPLING("dark_oak_sapling"),
/* 411 */     MUSHROOM_RED("mushroom_red"),
/* 412 */     MUSHROOM_BROWN("mushroom_brown"),
/* 413 */     DEAD_BUSH("dead_bush"),
/* 414 */     FERN("fern"),
/* 415 */     CACTUS("cactus");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     EnumFlowerType(String name) {
/* 420 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 424 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 428 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockFlowerPot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */