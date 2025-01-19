/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
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
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.ColorizerFoliage;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockVine
/*     */   extends Block {
/*  28 */   public static final PropertyBool UP = PropertyBool.create("up");
/*  29 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  30 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  31 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  32 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  33 */   public static final PropertyBool[] ALL_FACES = new PropertyBool[] { UP, NORTH, SOUTH, WEST, EAST };
/*     */   
/*     */   public BlockVine() {
/*  36 */     super(Material.vine);
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)UP, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  38 */     setTickRandomly(true);
/*  39 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  47 */     return state.withProperty((IProperty)UP, Boolean.valueOf(worldIn.getBlockState(pos.up()).getBlock().isBlockNormalCube()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/*  54 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  61 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  76 */     float f = 0.0625F;
/*  77 */     float f1 = 1.0F;
/*  78 */     float f2 = 1.0F;
/*  79 */     float f3 = 1.0F;
/*  80 */     float f4 = 0.0F;
/*  81 */     float f5 = 0.0F;
/*  82 */     float f6 = 0.0F;
/*  83 */     boolean flag = false;
/*     */     
/*  85 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)WEST)).booleanValue()) {
/*  86 */       f4 = Math.max(f4, 0.0625F);
/*  87 */       f1 = 0.0F;
/*  88 */       f2 = 0.0F;
/*  89 */       f5 = 1.0F;
/*  90 */       f3 = 0.0F;
/*  91 */       f6 = 1.0F;
/*  92 */       flag = true;
/*     */     } 
/*     */     
/*  95 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)EAST)).booleanValue()) {
/*  96 */       f1 = Math.min(f1, 0.9375F);
/*  97 */       f4 = 1.0F;
/*  98 */       f2 = 0.0F;
/*  99 */       f5 = 1.0F;
/* 100 */       f3 = 0.0F;
/* 101 */       f6 = 1.0F;
/* 102 */       flag = true;
/*     */     } 
/*     */     
/* 105 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)NORTH)).booleanValue()) {
/* 106 */       f6 = Math.max(f6, 0.0625F);
/* 107 */       f3 = 0.0F;
/* 108 */       f1 = 0.0F;
/* 109 */       f4 = 1.0F;
/* 110 */       f2 = 0.0F;
/* 111 */       f5 = 1.0F;
/* 112 */       flag = true;
/*     */     } 
/*     */     
/* 115 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)SOUTH)).booleanValue()) {
/* 116 */       f3 = Math.min(f3, 0.9375F);
/* 117 */       f6 = 1.0F;
/* 118 */       f1 = 0.0F;
/* 119 */       f4 = 1.0F;
/* 120 */       f2 = 0.0F;
/* 121 */       f5 = 1.0F;
/* 122 */       flag = true;
/*     */     } 
/*     */     
/* 125 */     if (!flag && canPlaceOn(worldIn.getBlockState(pos.up()).getBlock())) {
/* 126 */       f2 = Math.min(f2, 0.9375F);
/* 127 */       f5 = 1.0F;
/* 128 */       f1 = 0.0F;
/* 129 */       f4 = 1.0F;
/* 130 */       f3 = 0.0F;
/* 131 */       f6 = 1.0F;
/*     */     } 
/*     */     
/* 134 */     setBlockBounds(f1, f2, f3, f4, f5, f6);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 138 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/* 145 */     switch (side) {
/*     */       case UP:
/* 147 */         return canPlaceOn(worldIn.getBlockState(pos.up()).getBlock());
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/*     */       case WEST:
/*     */       case EAST:
/* 153 */         return canPlaceOn(worldIn.getBlockState(pos.offset(side.getOpposite())).getBlock());
/*     */     } 
/*     */     
/* 156 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canPlaceOn(Block blockIn) {
/* 161 */     return (blockIn.isFullCube() && blockIn.blockMaterial.blocksMovement());
/*     */   }
/*     */   
/*     */   private boolean recheckGrownSides(World worldIn, BlockPos pos, IBlockState state) {
/* 165 */     IBlockState iblockstate = state;
/*     */     
/* 167 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 168 */       PropertyBool propertybool = getPropertyFor(enumfacing);
/*     */       
/* 170 */       if (((Boolean)state.getValue((IProperty)propertybool)).booleanValue() && !canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing)).getBlock())) {
/* 171 */         IBlockState iblockstate1 = worldIn.getBlockState(pos.up());
/*     */         
/* 173 */         if (iblockstate1.getBlock() != this || !((Boolean)iblockstate1.getValue((IProperty)propertybool)).booleanValue()) {
/* 174 */           state = state.withProperty((IProperty)propertybool, Boolean.valueOf(false));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 179 */     if (getNumGrownFaces(state) == 0) {
/* 180 */       return false;
/*     */     }
/* 182 */     if (iblockstate != state) {
/* 183 */       worldIn.setBlockState(pos, state, 2);
/*     */     }
/*     */     
/* 186 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockColor() {
/* 191 */     return ColorizerFoliage.getFoliageColorBasic();
/*     */   }
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/* 195 */     return ColorizerFoliage.getFoliageColorBasic();
/*     */   }
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 199 */     return worldIn.getBiomeGenForCoords(pos).getFoliageColorAtPos(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 206 */     if (!worldIn.isRemote && !recheckGrownSides(worldIn, pos, state)) {
/* 207 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 208 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 213 */     if (!worldIn.isRemote && 
/* 214 */       worldIn.rand.nextInt(4) == 0) {
/* 215 */       int i = 4;
/* 216 */       int j = 5;
/* 217 */       boolean flag = false;
/*     */       
/*     */       int k;
/* 220 */       label103: for (k = -i; k <= i; k++) {
/* 221 */         for (int l = -i; l <= i; l++) {
/* 222 */           for (int i1 = -1; i1 <= 1; i1++) {
/*     */             
/* 224 */             j--;
/*     */             
/* 226 */             if (worldIn.getBlockState(pos.add(k, i1, l)).getBlock() == this && j <= 0) {
/* 227 */               flag = true;
/*     */               
/*     */               break label103;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 235 */       EnumFacing enumfacing1 = EnumFacing.random(rand);
/* 236 */       BlockPos blockpos1 = pos.up();
/*     */       
/* 238 */       if (enumfacing1 == EnumFacing.UP && pos.getY() < 255 && worldIn.isAirBlock(blockpos1)) {
/* 239 */         if (!flag) {
/* 240 */           IBlockState iblockstate2 = state;
/*     */           
/* 242 */           for (EnumFacing enumfacing3 : EnumFacing.Plane.HORIZONTAL) {
/* 243 */             if (rand.nextBoolean() || !canPlaceOn(worldIn.getBlockState(blockpos1.offset(enumfacing3)).getBlock())) {
/* 244 */               iblockstate2 = iblockstate2.withProperty((IProperty)getPropertyFor(enumfacing3), Boolean.valueOf(false));
/*     */             }
/*     */           } 
/*     */           
/* 248 */           if (((Boolean)iblockstate2.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)WEST)).booleanValue()) {
/* 249 */             worldIn.setBlockState(blockpos1, iblockstate2, 2);
/*     */           }
/*     */         } 
/* 252 */       } else if (enumfacing1.getAxis().isHorizontal() && !((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing1))).booleanValue()) {
/* 253 */         if (!flag) {
/* 254 */           BlockPos blockpos3 = pos.offset(enumfacing1);
/* 255 */           Block block1 = worldIn.getBlockState(blockpos3).getBlock();
/*     */           
/* 257 */           if (block1.blockMaterial == Material.air) {
/* 258 */             EnumFacing enumfacing2 = enumfacing1.rotateY();
/* 259 */             EnumFacing enumfacing4 = enumfacing1.rotateYCCW();
/* 260 */             boolean flag1 = ((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing2))).booleanValue();
/* 261 */             boolean flag2 = ((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing4))).booleanValue();
/* 262 */             BlockPos blockpos4 = blockpos3.offset(enumfacing2);
/* 263 */             BlockPos blockpos = blockpos3.offset(enumfacing4);
/*     */             
/* 265 */             if (flag1 && canPlaceOn(worldIn.getBlockState(blockpos4).getBlock())) {
/* 266 */               worldIn.setBlockState(blockpos3, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing2), Boolean.valueOf(true)), 2);
/* 267 */             } else if (flag2 && canPlaceOn(worldIn.getBlockState(blockpos).getBlock())) {
/* 268 */               worldIn.setBlockState(blockpos3, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing4), Boolean.valueOf(true)), 2);
/* 269 */             } else if (flag1 && worldIn.isAirBlock(blockpos4) && canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing2)).getBlock())) {
/* 270 */               worldIn.setBlockState(blockpos4, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing1.getOpposite()), Boolean.valueOf(true)), 2);
/* 271 */             } else if (flag2 && worldIn.isAirBlock(blockpos) && canPlaceOn(worldIn.getBlockState(pos.offset(enumfacing4)).getBlock())) {
/* 272 */               worldIn.setBlockState(blockpos, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing1.getOpposite()), Boolean.valueOf(true)), 2);
/* 273 */             } else if (canPlaceOn(worldIn.getBlockState(blockpos3.up()).getBlock())) {
/* 274 */               worldIn.setBlockState(blockpos3, getDefaultState(), 2);
/*     */             } 
/* 276 */           } else if (block1.blockMaterial.isOpaque() && block1.isFullCube()) {
/* 277 */             worldIn.setBlockState(pos, state.withProperty((IProperty)getPropertyFor(enumfacing1), Boolean.valueOf(true)), 2);
/*     */           }
/*     */         
/*     */         } 
/* 281 */       } else if (pos.getY() > 1) {
/* 282 */         BlockPos blockpos2 = pos.down();
/* 283 */         IBlockState iblockstate = worldIn.getBlockState(blockpos2);
/* 284 */         Block block = iblockstate.getBlock();
/*     */         
/* 286 */         if (block.blockMaterial == Material.air) {
/* 287 */           IBlockState iblockstate1 = state;
/*     */           
/* 289 */           for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 290 */             if (rand.nextBoolean()) {
/* 291 */               iblockstate1 = iblockstate1.withProperty((IProperty)getPropertyFor(enumfacing), Boolean.valueOf(false));
/*     */             }
/*     */           } 
/*     */           
/* 295 */           if (((Boolean)iblockstate1.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)WEST)).booleanValue()) {
/* 296 */             worldIn.setBlockState(blockpos2, iblockstate1, 2);
/*     */           }
/* 298 */         } else if (block == this) {
/* 299 */           IBlockState iblockstate3 = iblockstate;
/*     */           
/* 301 */           for (EnumFacing enumfacing5 : EnumFacing.Plane.HORIZONTAL) {
/* 302 */             PropertyBool propertybool = getPropertyFor(enumfacing5);
/*     */             
/* 304 */             if (rand.nextBoolean() && ((Boolean)state.getValue((IProperty)propertybool)).booleanValue()) {
/* 305 */               iblockstate3 = iblockstate3.withProperty((IProperty)propertybool, Boolean.valueOf(true));
/*     */             }
/*     */           } 
/*     */           
/* 309 */           if (((Boolean)iblockstate3.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate3.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate3.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate3.getValue((IProperty)WEST)).booleanValue()) {
/* 310 */             worldIn.setBlockState(blockpos2, iblockstate3, 2);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 324 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)UP, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false));
/* 325 */     return facing.getAxis().isHorizontal() ? iblockstate.withProperty((IProperty)getPropertyFor(facing.getOpposite()), Boolean.valueOf(true)) : iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 332 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 339 */     return 0;
/*     */   }
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 343 */     if (!worldIn.isRemote && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.shears) {
/* 344 */       player.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
/* 345 */       spawnAsEntity(worldIn, pos, new ItemStack(Blocks.vine, 1, 0));
/*     */     } else {
/* 347 */       super.harvestBlock(worldIn, player, pos, state, te);
/*     */     } 
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 352 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 359 */     return getDefaultState().withProperty((IProperty)SOUTH, Boolean.valueOf(((meta & 0x1) > 0))).withProperty((IProperty)WEST, Boolean.valueOf(((meta & 0x2) > 0))).withProperty((IProperty)NORTH, Boolean.valueOf(((meta & 0x4) > 0))).withProperty((IProperty)EAST, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 366 */     int i = 0;
/*     */     
/* 368 */     if (((Boolean)state.getValue((IProperty)SOUTH)).booleanValue()) {
/* 369 */       i |= 0x1;
/*     */     }
/*     */     
/* 372 */     if (((Boolean)state.getValue((IProperty)WEST)).booleanValue()) {
/* 373 */       i |= 0x2;
/*     */     }
/*     */     
/* 376 */     if (((Boolean)state.getValue((IProperty)NORTH)).booleanValue()) {
/* 377 */       i |= 0x4;
/*     */     }
/*     */     
/* 380 */     if (((Boolean)state.getValue((IProperty)EAST)).booleanValue()) {
/* 381 */       i |= 0x8;
/*     */     }
/*     */     
/* 384 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 388 */     return new BlockState(this, new IProperty[] { (IProperty)UP, (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST });
/*     */   }
/*     */   
/*     */   public static PropertyBool getPropertyFor(EnumFacing side) {
/* 392 */     switch (side) {
/*     */       case UP:
/* 394 */         return UP;
/*     */       
/*     */       case NORTH:
/* 397 */         return NORTH;
/*     */       
/*     */       case SOUTH:
/* 400 */         return SOUTH;
/*     */       
/*     */       case EAST:
/* 403 */         return EAST;
/*     */       
/*     */       case WEST:
/* 406 */         return WEST;
/*     */     } 
/*     */     
/* 409 */     throw new IllegalArgumentException(side + " is an invalid choice");
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getNumGrownFaces(IBlockState state) {
/* 414 */     int i = 0; byte b; int j;
/*     */     PropertyBool[] arrayOfPropertyBool;
/* 416 */     for (j = (arrayOfPropertyBool = ALL_FACES).length, b = 0; b < j; ) { PropertyBool propertybool = arrayOfPropertyBool[b];
/* 417 */       if (((Boolean)state.getValue((IProperty)propertybool)).booleanValue()) {
/* 418 */         i++;
/*     */       }
/*     */       b++; }
/*     */     
/* 422 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockVine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */