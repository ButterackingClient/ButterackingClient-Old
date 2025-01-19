/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockPistonStructureHelper;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityPiston;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPistonBase
/*     */   extends Block {
/*  27 */   public static final PropertyDirection FACING = PropertyDirection.create("facing");
/*  28 */   public static final PropertyBool EXTENDED = PropertyBool.create("extended");
/*     */ 
/*     */   
/*     */   private final boolean isSticky;
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPistonBase(boolean isSticky) {
/*  36 */     super(Material.piston);
/*  37 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)EXTENDED, Boolean.valueOf(false)));
/*  38 */     this.isSticky = isSticky;
/*  39 */     setStepSound(soundTypePiston);
/*  40 */     setHardness(0.5F);
/*  41 */     setCreativeTab(CreativeTabs.tabRedstone);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  55 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)getFacingFromEntity(worldIn, pos, placer)), 2);
/*     */     
/*  57 */     if (!worldIn.isRemote) {
/*  58 */       checkForMove(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  66 */     if (!worldIn.isRemote) {
/*  67 */       checkForMove(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  72 */     if (!worldIn.isRemote && worldIn.getTileEntity(pos) == null) {
/*  73 */       checkForMove(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  82 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacingFromEntity(worldIn, pos, placer)).withProperty((IProperty)EXTENDED, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   private void checkForMove(World worldIn, BlockPos pos, IBlockState state) {
/*  86 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  87 */     boolean flag = shouldBeExtended(worldIn, pos, enumfacing);
/*     */     
/*  89 */     if (flag && !((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue()) {
/*  90 */       if ((new BlockPistonStructureHelper(worldIn, pos, enumfacing, true)).canMove()) {
/*  91 */         worldIn.addBlockEvent(pos, this, 0, enumfacing.getIndex());
/*     */       }
/*  93 */     } else if (!flag && ((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue()) {
/*  94 */       worldIn.setBlockState(pos, state.withProperty((IProperty)EXTENDED, Boolean.valueOf(false)), 2);
/*  95 */       worldIn.addBlockEvent(pos, this, 1, enumfacing.getIndex());
/*     */     }  } private boolean shouldBeExtended(World worldIn, BlockPos pos, EnumFacing facing) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing1;
/* 100 */     for (i = (arrayOfEnumFacing1 = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing1[b];
/* 101 */       if (enumfacing != facing && worldIn.isSidePowered(pos.offset(enumfacing), enumfacing)) {
/* 102 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/* 106 */     if (worldIn.isSidePowered(pos, EnumFacing.DOWN)) {
/* 107 */       return true;
/*     */     }
/* 109 */     BlockPos blockpos = pos.up();
/*     */     EnumFacing[] arrayOfEnumFacing2;
/* 111 */     for (int j = (arrayOfEnumFacing2 = EnumFacing.values()).length; i < j; ) { EnumFacing enumfacing1 = arrayOfEnumFacing2[i];
/* 112 */       if (enumfacing1 != EnumFacing.DOWN && worldIn.isSidePowered(blockpos.offset(enumfacing1), enumfacing1)) {
/* 113 */         return true;
/*     */       }
/*     */       i++; }
/*     */     
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
/* 125 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 127 */     if (!worldIn.isRemote) {
/* 128 */       boolean flag = shouldBeExtended(worldIn, pos, enumfacing);
/*     */       
/* 130 */       if (flag && eventID == 1) {
/* 131 */         worldIn.setBlockState(pos, state.withProperty((IProperty)EXTENDED, Boolean.valueOf(true)), 2);
/* 132 */         return false;
/*     */       } 
/*     */       
/* 135 */       if (!flag && eventID == 0) {
/* 136 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 140 */     if (eventID == 0) {
/* 141 */       if (!doMove(worldIn, pos, enumfacing, true)) {
/* 142 */         return false;
/*     */       }
/*     */       
/* 145 */       worldIn.setBlockState(pos, state.withProperty((IProperty)EXTENDED, Boolean.valueOf(true)), 2);
/* 146 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "tile.piston.out", 0.5F, worldIn.rand.nextFloat() * 0.25F + 0.6F);
/* 147 */     } else if (eventID == 1) {
/* 148 */       TileEntity tileentity1 = worldIn.getTileEntity(pos.offset(enumfacing));
/*     */       
/* 150 */       if (tileentity1 instanceof TileEntityPiston) {
/* 151 */         ((TileEntityPiston)tileentity1).clearPistonTileEntity();
/*     */       }
/*     */       
/* 154 */       worldIn.setBlockState(pos, Blocks.piston_extension.getDefaultState().withProperty((IProperty)BlockPistonMoving.FACING, (Comparable)enumfacing).withProperty((IProperty)BlockPistonMoving.TYPE, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT), 3);
/* 155 */       worldIn.setTileEntity(pos, BlockPistonMoving.newTileEntity(getStateFromMeta(eventParam), enumfacing, false, true));
/*     */       
/* 157 */       if (this.isSticky) {
/* 158 */         BlockPos blockpos = pos.add(enumfacing.getFrontOffsetX() * 2, enumfacing.getFrontOffsetY() * 2, enumfacing.getFrontOffsetZ() * 2);
/* 159 */         Block block = worldIn.getBlockState(blockpos).getBlock();
/* 160 */         boolean flag1 = false;
/*     */         
/* 162 */         if (block == Blocks.piston_extension) {
/* 163 */           TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */           
/* 165 */           if (tileentity instanceof TileEntityPiston) {
/* 166 */             TileEntityPiston tileentitypiston = (TileEntityPiston)tileentity;
/*     */             
/* 168 */             if (tileentitypiston.getFacing() == enumfacing && tileentitypiston.isExtending()) {
/* 169 */               tileentitypiston.clearPistonTileEntity();
/* 170 */               flag1 = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 175 */         if (!flag1 && block.getMaterial() != Material.air && canPush(block, worldIn, blockpos, enumfacing.getOpposite(), false) && (block.getMobilityFlag() == 0 || block == Blocks.piston || block == Blocks.sticky_piston)) {
/* 176 */           doMove(worldIn, pos, enumfacing, false);
/*     */         }
/*     */       } else {
/* 179 */         worldIn.setBlockToAir(pos.offset(enumfacing));
/*     */       } 
/*     */       
/* 182 */       worldIn.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "tile.piston.in", 0.5F, worldIn.rand.nextFloat() * 0.15F + 0.6F);
/*     */     } 
/*     */     
/* 185 */     return true;
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 189 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 191 */     if (iblockstate.getBlock() == this && ((Boolean)iblockstate.getValue((IProperty)EXTENDED)).booleanValue()) {
/* 192 */       float f = 0.25F;
/* 193 */       EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/*     */       
/* 195 */       if (enumfacing != null)
/* 196 */         switch (enumfacing) {
/*     */           case null:
/* 198 */             setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */             break;
/*     */           
/*     */           case UP:
/* 202 */             setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
/*     */             break;
/*     */           
/*     */           case NORTH:
/* 206 */             setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
/*     */             break;
/*     */           
/*     */           case SOUTH:
/* 210 */             setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
/*     */             break;
/*     */           
/*     */           case WEST:
/* 214 */             setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */             break;
/*     */           
/*     */           case EAST:
/* 218 */             setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
/*     */             break;
/*     */         }  
/*     */     } else {
/* 222 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 230 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/* 237 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/* 238 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 242 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/* 243 */     return super.getCollisionBoundingBox(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/* 247 */     return false;
/*     */   }
/*     */   
/*     */   public static EnumFacing getFacing(int meta) {
/* 251 */     int i = meta & 0x7;
/* 252 */     return (i > 5) ? null : EnumFacing.getFront(i);
/*     */   }
/*     */   
/*     */   public static EnumFacing getFacingFromEntity(World worldIn, BlockPos clickedBlock, EntityLivingBase entityIn) {
/* 256 */     if (MathHelper.abs((float)entityIn.posX - clickedBlock.getX()) < 2.0F && MathHelper.abs((float)entityIn.posZ - clickedBlock.getZ()) < 2.0F) {
/* 257 */       double d0 = entityIn.posY + entityIn.getEyeHeight();
/*     */       
/* 259 */       if (d0 - clickedBlock.getY() > 2.0D) {
/* 260 */         return EnumFacing.UP;
/*     */       }
/*     */       
/* 263 */       if (clickedBlock.getY() - d0 > 0.0D) {
/* 264 */         return EnumFacing.DOWN;
/*     */       }
/*     */     } 
/*     */     
/* 268 */     return entityIn.getHorizontalFacing().getOpposite();
/*     */   }
/*     */   
/*     */   public static boolean canPush(Block blockIn, World worldIn, BlockPos pos, EnumFacing direction, boolean allowDestroy) {
/* 272 */     if (blockIn == Blocks.obsidian)
/* 273 */       return false; 
/* 274 */     if (!worldIn.getWorldBorder().contains(pos))
/* 275 */       return false; 
/* 276 */     if (pos.getY() >= 0 && (direction != EnumFacing.DOWN || pos.getY() != 0)) {
/* 277 */       if (pos.getY() <= worldIn.getHeight() - 1 && (direction != EnumFacing.UP || pos.getY() != worldIn.getHeight() - 1)) {
/* 278 */         if (blockIn != Blocks.piston && blockIn != Blocks.sticky_piston) {
/* 279 */           if (blockIn.getBlockHardness(worldIn, pos) == -1.0F) {
/* 280 */             return false;
/*     */           }
/*     */           
/* 283 */           if (blockIn.getMobilityFlag() == 2) {
/* 284 */             return false;
/*     */           }
/*     */           
/* 287 */           if (blockIn.getMobilityFlag() == 1) {
/* 288 */             if (!allowDestroy) {
/* 289 */               return false;
/*     */             }
/*     */             
/* 292 */             return true;
/*     */           } 
/* 294 */         } else if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)EXTENDED)).booleanValue()) {
/* 295 */           return false;
/*     */         } 
/*     */         
/* 298 */         return !(blockIn instanceof ITileEntityProvider);
/*     */       } 
/* 300 */       return false;
/*     */     } 
/*     */     
/* 303 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean doMove(World worldIn, BlockPos pos, EnumFacing direction, boolean extending) {
/* 308 */     if (!extending) {
/* 309 */       worldIn.setBlockToAir(pos.offset(direction));
/*     */     }
/*     */     
/* 312 */     BlockPistonStructureHelper blockpistonstructurehelper = new BlockPistonStructureHelper(worldIn, pos, direction, extending);
/* 313 */     List<BlockPos> list = blockpistonstructurehelper.getBlocksToMove();
/* 314 */     List<BlockPos> list1 = blockpistonstructurehelper.getBlocksToDestroy();
/*     */     
/* 316 */     if (!blockpistonstructurehelper.canMove()) {
/* 317 */       return false;
/*     */     }
/* 319 */     int i = list.size() + list1.size();
/* 320 */     Block[] ablock = new Block[i];
/* 321 */     EnumFacing enumfacing = extending ? direction : direction.getOpposite();
/*     */     
/* 323 */     for (int j = list1.size() - 1; j >= 0; j--) {
/* 324 */       BlockPos blockpos = list1.get(j);
/* 325 */       Block block = worldIn.getBlockState(blockpos).getBlock();
/* 326 */       block.dropBlockAsItem(worldIn, blockpos, worldIn.getBlockState(blockpos), 0);
/* 327 */       worldIn.setBlockToAir(blockpos);
/* 328 */       i--;
/* 329 */       ablock[i] = block;
/*     */     } 
/*     */     
/* 332 */     for (int k = list.size() - 1; k >= 0; k--) {
/* 333 */       BlockPos blockpos2 = list.get(k);
/* 334 */       IBlockState iblockstate = worldIn.getBlockState(blockpos2);
/* 335 */       Block block1 = iblockstate.getBlock();
/* 336 */       block1.getMetaFromState(iblockstate);
/* 337 */       worldIn.setBlockToAir(blockpos2);
/* 338 */       blockpos2 = blockpos2.offset(enumfacing);
/* 339 */       worldIn.setBlockState(blockpos2, Blocks.piston_extension.getDefaultState().withProperty((IProperty)FACING, (Comparable)direction), 4);
/* 340 */       worldIn.setTileEntity(blockpos2, BlockPistonMoving.newTileEntity(iblockstate, direction, extending, false));
/* 341 */       i--;
/* 342 */       ablock[i] = block1;
/*     */     } 
/*     */     
/* 345 */     BlockPos blockpos1 = pos.offset(direction);
/*     */     
/* 347 */     if (extending) {
/* 348 */       BlockPistonExtension.EnumPistonType blockpistonextension$enumpistontype = this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
/* 349 */       IBlockState iblockstate1 = Blocks.piston_head.getDefaultState().withProperty((IProperty)BlockPistonExtension.FACING, (Comparable)direction).withProperty((IProperty)BlockPistonExtension.TYPE, blockpistonextension$enumpistontype);
/* 350 */       IBlockState iblockstate2 = Blocks.piston_extension.getDefaultState().withProperty((IProperty)BlockPistonMoving.FACING, (Comparable)direction).withProperty((IProperty)BlockPistonMoving.TYPE, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
/* 351 */       worldIn.setBlockState(blockpos1, iblockstate2, 4);
/* 352 */       worldIn.setTileEntity(blockpos1, BlockPistonMoving.newTileEntity(iblockstate1, direction, true, false));
/*     */     } 
/*     */     
/* 355 */     for (int l = list1.size() - 1; l >= 0; l--) {
/* 356 */       worldIn.notifyNeighborsOfStateChange(list1.get(l), ablock[i++]);
/*     */     }
/*     */     
/* 359 */     for (int i1 = list.size() - 1; i1 >= 0; i1--) {
/* 360 */       worldIn.notifyNeighborsOfStateChange(list.get(i1), ablock[i++]);
/*     */     }
/*     */     
/* 363 */     if (extending) {
/* 364 */       worldIn.notifyNeighborsOfStateChange(blockpos1, Blocks.piston_head);
/* 365 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/*     */     } 
/*     */     
/* 368 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 376 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.UP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 383 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)EXTENDED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 390 */     int i = 0;
/* 391 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 393 */     if (((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue()) {
/* 394 */       i |= 0x8;
/*     */     }
/*     */     
/* 397 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 401 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)EXTENDED });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockPistonBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */