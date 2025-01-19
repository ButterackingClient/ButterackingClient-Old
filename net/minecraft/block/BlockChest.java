/*     */ package net.minecraft.block;
/*     */ import com.google.common.base.Predicate;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.inventory.InventoryLargeChest;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockChest extends BlockContainer {
/*  30 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*     */ 
/*     */   
/*     */   public final int chestType;
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockChest(int type) {
/*  38 */     super(Material.wood);
/*  39 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  40 */     this.chestType = type;
/*  41 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  42 */     setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  49 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/*  60 */     return 2;
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  64 */     if (worldIn.getBlockState(pos.north()).getBlock() == this) {
/*  65 */       setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
/*  66 */     } else if (worldIn.getBlockState(pos.south()).getBlock() == this) {
/*  67 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
/*  68 */     } else if (worldIn.getBlockState(pos.west()).getBlock() == this) {
/*  69 */       setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*  70 */     } else if (worldIn.getBlockState(pos.east()).getBlock() == this) {
/*  71 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
/*     */     } else {
/*  73 */       setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  78 */     checkForSurroundingChests(worldIn, pos, state);
/*     */     
/*  80 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*  81 */       BlockPos blockpos = pos.offset(enumfacing);
/*  82 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/*  84 */       if (iblockstate.getBlock() == this) {
/*  85 */         checkForSurroundingChests(worldIn, blockpos, iblockstate);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  95 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 102 */     EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor_double((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3).getOpposite();
/* 103 */     state = state.withProperty((IProperty)FACING, (Comparable)enumfacing);
/* 104 */     BlockPos blockpos = pos.north();
/* 105 */     BlockPos blockpos1 = pos.south();
/* 106 */     BlockPos blockpos2 = pos.west();
/* 107 */     BlockPos blockpos3 = pos.east();
/* 108 */     boolean flag = (this == worldIn.getBlockState(blockpos).getBlock());
/* 109 */     boolean flag1 = (this == worldIn.getBlockState(blockpos1).getBlock());
/* 110 */     boolean flag2 = (this == worldIn.getBlockState(blockpos2).getBlock());
/* 111 */     boolean flag3 = (this == worldIn.getBlockState(blockpos3).getBlock());
/*     */     
/* 113 */     if (!flag && !flag1 && !flag2 && !flag3) {
/* 114 */       worldIn.setBlockState(pos, state, 3);
/* 115 */     } else if (enumfacing.getAxis() != EnumFacing.Axis.X || (!flag && !flag1)) {
/* 116 */       if (enumfacing.getAxis() == EnumFacing.Axis.Z && (flag2 || flag3)) {
/* 117 */         if (flag2) {
/* 118 */           worldIn.setBlockState(blockpos2, state, 3);
/*     */         } else {
/* 120 */           worldIn.setBlockState(blockpos3, state, 3);
/*     */         } 
/*     */         
/* 123 */         worldIn.setBlockState(pos, state, 3);
/*     */       } 
/*     */     } else {
/* 126 */       if (flag) {
/* 127 */         worldIn.setBlockState(blockpos, state, 3);
/*     */       } else {
/* 129 */         worldIn.setBlockState(blockpos1, state, 3);
/*     */       } 
/*     */       
/* 132 */       worldIn.setBlockState(pos, state, 3);
/*     */     } 
/*     */     
/* 135 */     if (stack.hasDisplayName()) {
/* 136 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 138 */       if (tileentity instanceof TileEntityChest) {
/* 139 */         ((TileEntityChest)tileentity).setCustomName(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public IBlockState checkForSurroundingChests(World worldIn, BlockPos pos, IBlockState state) {
/* 145 */     if (worldIn.isRemote) {
/* 146 */       return state;
/*     */     }
/* 148 */     IBlockState iblockstate = worldIn.getBlockState(pos.north());
/* 149 */     IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
/* 150 */     IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
/* 151 */     IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
/* 152 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 153 */     Block block = iblockstate.getBlock();
/* 154 */     Block block1 = iblockstate1.getBlock();
/* 155 */     Block block2 = iblockstate2.getBlock();
/* 156 */     Block block3 = iblockstate3.getBlock();
/*     */     
/* 158 */     if (block != this && block1 != this) {
/* 159 */       boolean flag = block.isFullBlock();
/* 160 */       boolean flag1 = block1.isFullBlock();
/*     */       
/* 162 */       if (block2 == this || block3 == this) {
/* 163 */         EnumFacing enumfacing2; BlockPos blockpos1 = (block2 == this) ? pos.west() : pos.east();
/* 164 */         IBlockState iblockstate6 = worldIn.getBlockState(blockpos1.north());
/* 165 */         IBlockState iblockstate7 = worldIn.getBlockState(blockpos1.south());
/* 166 */         enumfacing = EnumFacing.SOUTH;
/*     */ 
/*     */         
/* 169 */         if (block2 == this) {
/* 170 */           enumfacing2 = (EnumFacing)iblockstate2.getValue((IProperty)FACING);
/*     */         } else {
/* 172 */           enumfacing2 = (EnumFacing)iblockstate3.getValue((IProperty)FACING);
/*     */         } 
/*     */         
/* 175 */         if (enumfacing2 == EnumFacing.NORTH) {
/* 176 */           enumfacing = EnumFacing.NORTH;
/*     */         }
/*     */         
/* 179 */         Block block6 = iblockstate6.getBlock();
/* 180 */         Block block7 = iblockstate7.getBlock();
/*     */         
/* 182 */         if ((flag || block6.isFullBlock()) && !flag1 && !block7.isFullBlock()) {
/* 183 */           enumfacing = EnumFacing.SOUTH;
/*     */         }
/*     */         
/* 186 */         if ((flag1 || block7.isFullBlock()) && !flag && !block6.isFullBlock())
/* 187 */           enumfacing = EnumFacing.NORTH; 
/*     */       } 
/*     */     } else {
/*     */       EnumFacing enumfacing1;
/* 191 */       BlockPos blockpos = (block == this) ? pos.north() : pos.south();
/* 192 */       IBlockState iblockstate4 = worldIn.getBlockState(blockpos.west());
/* 193 */       IBlockState iblockstate5 = worldIn.getBlockState(blockpos.east());
/* 194 */       enumfacing = EnumFacing.EAST;
/*     */ 
/*     */       
/* 197 */       if (block == this) {
/* 198 */         enumfacing1 = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/*     */       } else {
/* 200 */         enumfacing1 = (EnumFacing)iblockstate1.getValue((IProperty)FACING);
/*     */       } 
/*     */       
/* 203 */       if (enumfacing1 == EnumFacing.WEST) {
/* 204 */         enumfacing = EnumFacing.WEST;
/*     */       }
/*     */       
/* 207 */       Block block4 = iblockstate4.getBlock();
/* 208 */       Block block5 = iblockstate5.getBlock();
/*     */       
/* 210 */       if ((block2.isFullBlock() || block4.isFullBlock()) && !block3.isFullBlock() && !block5.isFullBlock()) {
/* 211 */         enumfacing = EnumFacing.EAST;
/*     */       }
/*     */       
/* 214 */       if ((block3.isFullBlock() || block5.isFullBlock()) && !block2.isFullBlock() && !block4.isFullBlock()) {
/* 215 */         enumfacing = EnumFacing.WEST;
/*     */       }
/*     */     } 
/*     */     
/* 219 */     state = state.withProperty((IProperty)FACING, (Comparable)enumfacing);
/* 220 */     worldIn.setBlockState(pos, state, 3);
/* 221 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState correctFacing(World worldIn, BlockPos pos, IBlockState state) {
/* 226 */     EnumFacing enumfacing = null;
/*     */     
/* 228 */     for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
/* 229 */       IBlockState iblockstate = worldIn.getBlockState(pos.offset(enumfacing1));
/*     */       
/* 231 */       if (iblockstate.getBlock() == this) {
/* 232 */         return state;
/*     */       }
/*     */       
/* 235 */       if (iblockstate.getBlock().isFullBlock()) {
/* 236 */         if (enumfacing != null) {
/* 237 */           enumfacing = null;
/*     */           
/*     */           break;
/*     */         } 
/* 241 */         enumfacing = enumfacing1;
/*     */       } 
/*     */     } 
/*     */     
/* 245 */     if (enumfacing != null) {
/* 246 */       return state.withProperty((IProperty)FACING, (Comparable)enumfacing.getOpposite());
/*     */     }
/* 248 */     EnumFacing enumfacing2 = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 250 */     if (worldIn.getBlockState(pos.offset(enumfacing2)).getBlock().isFullBlock()) {
/* 251 */       enumfacing2 = enumfacing2.getOpposite();
/*     */     }
/*     */     
/* 254 */     if (worldIn.getBlockState(pos.offset(enumfacing2)).getBlock().isFullBlock()) {
/* 255 */       enumfacing2 = enumfacing2.rotateY();
/*     */     }
/*     */     
/* 258 */     if (worldIn.getBlockState(pos.offset(enumfacing2)).getBlock().isFullBlock()) {
/* 259 */       enumfacing2 = enumfacing2.getOpposite();
/*     */     }
/*     */     
/* 262 */     return state.withProperty((IProperty)FACING, (Comparable)enumfacing2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 267 */     int i = 0;
/* 268 */     BlockPos blockpos = pos.west();
/* 269 */     BlockPos blockpos1 = pos.east();
/* 270 */     BlockPos blockpos2 = pos.north();
/* 271 */     BlockPos blockpos3 = pos.south();
/*     */     
/* 273 */     if (worldIn.getBlockState(blockpos).getBlock() == this) {
/* 274 */       if (isDoubleChest(worldIn, blockpos)) {
/* 275 */         return false;
/*     */       }
/*     */       
/* 278 */       i++;
/*     */     } 
/*     */     
/* 281 */     if (worldIn.getBlockState(blockpos1).getBlock() == this) {
/* 282 */       if (isDoubleChest(worldIn, blockpos1)) {
/* 283 */         return false;
/*     */       }
/*     */       
/* 286 */       i++;
/*     */     } 
/*     */     
/* 289 */     if (worldIn.getBlockState(blockpos2).getBlock() == this) {
/* 290 */       if (isDoubleChest(worldIn, blockpos2)) {
/* 291 */         return false;
/*     */       }
/*     */       
/* 294 */       i++;
/*     */     } 
/*     */     
/* 297 */     if (worldIn.getBlockState(blockpos3).getBlock() == this) {
/* 298 */       if (isDoubleChest(worldIn, blockpos3)) {
/* 299 */         return false;
/*     */       }
/*     */       
/* 302 */       i++;
/*     */     } 
/*     */     
/* 305 */     return (i <= 1);
/*     */   }
/*     */   
/*     */   private boolean isDoubleChest(World worldIn, BlockPos pos) {
/* 309 */     if (worldIn.getBlockState(pos).getBlock() != this) {
/* 310 */       return false;
/*     */     }
/* 312 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 313 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this) {
/* 314 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 326 */     super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
/* 327 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 329 */     if (tileentity instanceof TileEntityChest) {
/* 330 */       tileentity.updateContainingBlockInfo();
/*     */     }
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 335 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 337 */     if (tileentity instanceof IInventory) {
/* 338 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 339 */       worldIn.updateComparatorOutputLevel(pos, this);
/*     */     } 
/*     */     
/* 342 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 346 */     if (worldIn.isRemote) {
/* 347 */       return true;
/*     */     }
/* 349 */     ILockableContainer ilockablecontainer = getLockableContainer(worldIn, pos);
/*     */     
/* 351 */     if (ilockablecontainer != null) {
/* 352 */       playerIn.displayGUIChest((IInventory)ilockablecontainer);
/*     */       
/* 354 */       if (this.chestType == 0) {
/* 355 */         playerIn.triggerAchievement(StatList.field_181723_aa);
/* 356 */       } else if (this.chestType == 1) {
/* 357 */         playerIn.triggerAchievement(StatList.field_181737_U);
/*     */       } 
/*     */     } 
/*     */     
/* 361 */     return true;
/*     */   }
/*     */   
/*     */   public ILockableContainer getLockableContainer(World worldIn, BlockPos pos) {
/*     */     InventoryLargeChest inventoryLargeChest;
/* 366 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 368 */     if (!(tileentity instanceof TileEntityChest)) {
/* 369 */       return null;
/*     */     }
/* 371 */     TileEntityChest tileEntityChest = (TileEntityChest)tileentity;
/*     */     
/* 373 */     if (isBlocked(worldIn, pos)) {
/* 374 */       return null;
/*     */     }
/* 376 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 377 */       BlockPos blockpos = pos.offset(enumfacing);
/* 378 */       Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */       
/* 380 */       if (block == this) {
/* 381 */         if (isBlocked(worldIn, blockpos)) {
/* 382 */           return null;
/*     */         }
/*     */         
/* 385 */         TileEntity tileentity1 = worldIn.getTileEntity(blockpos);
/*     */         
/* 387 */         if (tileentity1 instanceof TileEntityChest) {
/* 388 */           if (enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH) {
/* 389 */             inventoryLargeChest = new InventoryLargeChest("container.chestDouble", (ILockableContainer)tileEntityChest, (ILockableContainer)tileentity1); continue;
/*     */           } 
/* 391 */           inventoryLargeChest = new InventoryLargeChest("container.chestDouble", (ILockableContainer)tileentity1, (ILockableContainer)inventoryLargeChest);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 397 */     return (ILockableContainer)inventoryLargeChest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 406 */     return (TileEntity)new TileEntityChest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower() {
/* 413 */     return (this.chestType == 1);
/*     */   }
/*     */   
/*     */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 417 */     if (!canProvidePower()) {
/* 418 */       return 0;
/*     */     }
/* 420 */     int i = 0;
/* 421 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 423 */     if (tileentity instanceof TileEntityChest) {
/* 424 */       i = ((TileEntityChest)tileentity).numPlayersUsing;
/*     */     }
/*     */     
/* 427 */     return MathHelper.clamp_int(i, 0, 15);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/* 432 */     return (side == EnumFacing.UP) ? getWeakPower(worldIn, pos, state, side) : 0;
/*     */   }
/*     */   
/*     */   private boolean isBlocked(World worldIn, BlockPos pos) {
/* 436 */     return !(!isBelowSolidBlock(worldIn, pos) && !isOcelotSittingOnChest(worldIn, pos));
/*     */   }
/*     */   
/*     */   private boolean isBelowSolidBlock(World worldIn, BlockPos pos) {
/* 440 */     return worldIn.getBlockState(pos.up()).getBlock().isNormalCube();
/*     */   }
/*     */   
/*     */   private boolean isOcelotSittingOnChest(World worldIn, BlockPos pos) {
/* 444 */     for (Entity entity : worldIn.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB(pos.getX(), (pos.getY() + 1), pos.getZ(), (pos.getX() + 1), (pos.getY() + 2), (pos.getZ() + 1)))) {
/* 445 */       EntityOcelot entityocelot = (EntityOcelot)entity;
/*     */       
/* 447 */       if (entityocelot.isSitting()) {
/* 448 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 452 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasComparatorInputOverride() {
/* 456 */     return true;
/*     */   }
/*     */   
/*     */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 460 */     return Container.calcRedstoneFromInventory((IInventory)getLockableContainer(worldIn, pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 467 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 469 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
/* 470 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 473 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 480 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 484 */     return new BlockState(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */