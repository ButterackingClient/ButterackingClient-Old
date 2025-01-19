/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockRailBase
/*     */   extends Block
/*     */ {
/*     */   protected final boolean isPowered;
/*     */   
/*     */   public static boolean isRailBlock(World worldIn, BlockPos pos) {
/*  26 */     return isRailBlock(worldIn.getBlockState(pos));
/*     */   }
/*     */   
/*     */   public static boolean isRailBlock(IBlockState state) {
/*  30 */     Block block = state.getBlock();
/*  31 */     return !(block != Blocks.rail && block != Blocks.golden_rail && block != Blocks.detector_rail && block != Blocks.activator_rail);
/*     */   }
/*     */   
/*     */   protected BlockRailBase(boolean isPowered) {
/*  35 */     super(Material.circuits);
/*  36 */     this.isPowered = isPowered;
/*  37 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  38 */     setCreativeTab(CreativeTabs.tabTransport);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  42 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/*  56 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  57 */     return super.collisionRayTrace(worldIn, pos, start, end);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  61 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  62 */     EnumRailDirection blockrailbase$enumraildirection = (iblockstate.getBlock() == this) ? (EnumRailDirection)iblockstate.getValue(getShapeProperty()) : null;
/*     */     
/*  64 */     if (blockrailbase$enumraildirection != null && blockrailbase$enumraildirection.isAscending()) {
/*  65 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*     */     } else {
/*  67 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  76 */     return World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down());
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  80 */     if (!worldIn.isRemote) {
/*  81 */       state = func_176564_a(worldIn, pos, state, true);
/*     */       
/*  83 */       if (this.isPowered) {
/*  84 */         onNeighborBlockChange(worldIn, pos, state, this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/*  93 */     if (!worldIn.isRemote) {
/*  94 */       EnumRailDirection blockrailbase$enumraildirection = (EnumRailDirection)state.getValue(getShapeProperty());
/*  95 */       boolean flag = false;
/*     */       
/*  97 */       if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())) {
/*  98 */         flag = true;
/*     */       }
/*     */       
/* 101 */       if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_EAST && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.east())) {
/* 102 */         flag = true;
/* 103 */       } else if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_WEST && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.west())) {
/* 104 */         flag = true;
/* 105 */       } else if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_NORTH && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.north())) {
/* 106 */         flag = true;
/* 107 */       } else if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_SOUTH && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.south())) {
/* 108 */         flag = true;
/*     */       } 
/*     */       
/* 111 */       if (flag) {
/* 112 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 113 */         worldIn.setBlockToAir(pos);
/*     */       } else {
/* 115 */         onNeighborChangedInternal(worldIn, pos, state, neighborBlock);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onNeighborChangedInternal(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {}
/*     */   
/*     */   protected IBlockState func_176564_a(World worldIn, BlockPos p_176564_2_, IBlockState p_176564_3_, boolean p_176564_4_) {
/* 124 */     return worldIn.isRemote ? p_176564_3_ : (new Rail(worldIn, p_176564_2_, p_176564_3_)).func_180364_a(worldIn.isBlockPowered(p_176564_2_), p_176564_4_).getBlockState();
/*     */   }
/*     */   
/*     */   public int getMobilityFlag() {
/* 128 */     return 0;
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 132 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 136 */     super.breakBlock(worldIn, pos, state);
/*     */     
/* 138 */     if (((EnumRailDirection)state.getValue(getShapeProperty())).isAscending()) {
/* 139 */       worldIn.notifyNeighborsOfStateChange(pos.up(), this);
/*     */     }
/*     */     
/* 142 */     if (this.isPowered) {
/* 143 */       worldIn.notifyNeighborsOfStateChange(pos, this);
/* 144 */       worldIn.notifyNeighborsOfStateChange(pos.down(), this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract IProperty<EnumRailDirection> getShapeProperty();
/*     */   
/*     */   public enum EnumRailDirection implements IStringSerializable {
/* 151 */     NORTH_SOUTH(0, "north_south"),
/* 152 */     EAST_WEST(1, "east_west"),
/* 153 */     ASCENDING_EAST(2, "ascending_east"),
/* 154 */     ASCENDING_WEST(3, "ascending_west"),
/* 155 */     ASCENDING_NORTH(4, "ascending_north"),
/* 156 */     ASCENDING_SOUTH(5, "ascending_south"),
/* 157 */     SOUTH_EAST(6, "south_east"),
/* 158 */     SOUTH_WEST(7, "south_west"),
/* 159 */     NORTH_WEST(8, "north_west"),
/* 160 */     NORTH_EAST(9, "north_east");
/*     */     
/* 162 */     private static final EnumRailDirection[] META_LOOKUP = new EnumRailDirection[(values()).length];
/*     */ 
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
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumRailDirection[] arrayOfEnumRailDirection;
/* 196 */       for (i = (arrayOfEnumRailDirection = values()).length, b = 0; b < i; ) { EnumRailDirection blockrailbase$enumraildirection = arrayOfEnumRailDirection[b];
/* 197 */         META_LOOKUP[blockrailbase$enumraildirection.getMetadata()] = blockrailbase$enumraildirection;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     EnumRailDirection(int meta, String name) { this.meta = meta;
/*     */       this.name = name; }
/*     */     public int getMetadata() { return this.meta; } public String toString() { return this.name; } public boolean isAscending() { return !(this != ASCENDING_NORTH && this != ASCENDING_EAST && this != ASCENDING_SOUTH && this != ASCENDING_WEST); } public static EnumRailDirection byMetadata(int meta) { if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       return META_LOOKUP[meta]; } public String getName() { return this.name; }
/*     */   } public class Rail
/*     */   {
/* 208 */     private final World world; private final BlockPos pos; private final List<BlockPos> field_150657_g = Lists.newArrayList(); private final BlockRailBase block; private IBlockState state; private final boolean isPowered;
/*     */     
/*     */     public Rail(World worldIn, BlockPos pos, IBlockState state) {
/* 211 */       this.world = worldIn;
/* 212 */       this.pos = pos;
/* 213 */       this.state = state;
/* 214 */       this.block = (BlockRailBase)state.getBlock();
/* 215 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)state.getValue(BlockRailBase.this.getShapeProperty());
/* 216 */       this.isPowered = this.block.isPowered;
/* 217 */       func_180360_a(blockrailbase$enumraildirection);
/*     */     }
/*     */     
/*     */     private void func_180360_a(BlockRailBase.EnumRailDirection p_180360_1_) {
/* 221 */       this.field_150657_g.clear();
/*     */       
/* 223 */       switch (p_180360_1_) {
/*     */         case NORTH_SOUTH:
/* 225 */           this.field_150657_g.add(this.pos.north());
/* 226 */           this.field_150657_g.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case EAST_WEST:
/* 230 */           this.field_150657_g.add(this.pos.west());
/* 231 */           this.field_150657_g.add(this.pos.east());
/*     */           break;
/*     */         
/*     */         case null:
/* 235 */           this.field_150657_g.add(this.pos.west());
/* 236 */           this.field_150657_g.add(this.pos.east().up());
/*     */           break;
/*     */         
/*     */         case ASCENDING_WEST:
/* 240 */           this.field_150657_g.add(this.pos.west().up());
/*     */           this.field_150657_g.add(this.pos.east());
/*     */           break;
/*     */         
/*     */         case ASCENDING_NORTH:
/* 245 */           this.field_150657_g.add(this.pos.north().up());
/* 246 */           this.field_150657_g.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case ASCENDING_SOUTH:
/* 250 */           this.field_150657_g.add(this.pos.north());
/* 251 */           this.field_150657_g.add(this.pos.south().up());
/*     */           break;
/*     */         
/*     */         case SOUTH_EAST:
/* 255 */           this.field_150657_g.add(this.pos.east());
/* 256 */           this.field_150657_g.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case SOUTH_WEST:
/* 260 */           this.field_150657_g.add(this.pos.west());
/* 261 */           this.field_150657_g.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case NORTH_WEST:
/* 265 */           this.field_150657_g.add(this.pos.west());
/* 266 */           this.field_150657_g.add(this.pos.north());
/*     */           break;
/*     */         
/*     */         case NORTH_EAST:
/* 270 */           this.field_150657_g.add(this.pos.east());
/* 271 */           this.field_150657_g.add(this.pos.north());
/*     */           break;
/*     */       } 
/*     */     }
/*     */     private void func_150651_b() {
/* 276 */       for (int i = 0; i < this.field_150657_g.size(); i++) {
/* 277 */         Rail blockrailbase$rail = findRailAt(this.field_150657_g.get(i));
/*     */         
/* 279 */         if (blockrailbase$rail != null && blockrailbase$rail.func_150653_a(this)) {
/* 280 */           this.field_150657_g.set(i, blockrailbase$rail.pos);
/*     */         } else {
/* 282 */           this.field_150657_g.remove(i--);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean hasRailAt(BlockPos pos) {
/* 288 */       return !(!BlockRailBase.isRailBlock(this.world, pos) && !BlockRailBase.isRailBlock(this.world, pos.up()) && !BlockRailBase.isRailBlock(this.world, pos.down()));
/*     */     }
/*     */     
/*     */     private Rail findRailAt(BlockPos pos) {
/* 292 */       IBlockState iblockstate = this.world.getBlockState(pos);
/*     */       
/* 294 */       if (BlockRailBase.isRailBlock(iblockstate)) {
/* 295 */         BlockRailBase.this.getClass(); return new Rail(this.world, pos, iblockstate);
/*     */       } 
/* 297 */       BlockPos lvt_2_1_ = pos.up();
/* 298 */       iblockstate = this.world.getBlockState(lvt_2_1_);
/*     */       
/* 300 */       if (BlockRailBase.isRailBlock(iblockstate)) {
/* 301 */         BlockRailBase.this.getClass(); return new Rail(this.world, lvt_2_1_, iblockstate);
/*     */       } 
/* 303 */       lvt_2_1_ = pos.down();
/* 304 */       iblockstate = this.world.getBlockState(lvt_2_1_);
/* 305 */       BlockRailBase.this.getClass(); return BlockRailBase.isRailBlock(iblockstate) ? new Rail(this.world, lvt_2_1_, iblockstate) : null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean func_150653_a(Rail p_150653_1_) {
/* 311 */       return func_180363_c(p_150653_1_.pos);
/*     */     }
/*     */     
/*     */     private boolean func_180363_c(BlockPos p_180363_1_) {
/* 315 */       for (int i = 0; i < this.field_150657_g.size(); i++) {
/* 316 */         BlockPos blockpos = this.field_150657_g.get(i);
/*     */         
/* 318 */         if (blockpos.getX() == p_180363_1_.getX() && blockpos.getZ() == p_180363_1_.getZ()) {
/* 319 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 323 */       return false;
/*     */     }
/*     */     
/*     */     protected int countAdjacentRails() {
/* 327 */       int i = 0;
/*     */       
/* 329 */       for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 330 */         if (hasRailAt(this.pos.offset(enumfacing))) {
/* 331 */           i++;
/*     */         }
/*     */       } 
/*     */       
/* 335 */       return i;
/*     */     }
/*     */     
/*     */     private boolean func_150649_b(Rail rail) {
/* 339 */       return !(!func_150653_a(rail) && this.field_150657_g.size() == 2);
/*     */     }
/*     */     
/*     */     private void func_150645_c(Rail p_150645_1_) {
/* 343 */       this.field_150657_g.add(p_150645_1_.pos);
/* 344 */       BlockPos blockpos = this.pos.north();
/* 345 */       BlockPos blockpos1 = this.pos.south();
/* 346 */       BlockPos blockpos2 = this.pos.west();
/* 347 */       BlockPos blockpos3 = this.pos.east();
/* 348 */       boolean flag = func_180363_c(blockpos);
/* 349 */       boolean flag1 = func_180363_c(blockpos1);
/* 350 */       boolean flag2 = func_180363_c(blockpos2);
/* 351 */       boolean flag3 = func_180363_c(blockpos3);
/* 352 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = null;
/*     */       
/* 354 */       if (flag || flag1) {
/* 355 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 358 */       if (flag2 || flag3) {
/* 359 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */       }
/*     */       
/* 362 */       if (!this.isPowered) {
/* 363 */         if (flag1 && flag3 && !flag && !flag2) {
/* 364 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */         }
/*     */         
/* 367 */         if (flag1 && flag2 && !flag && !flag3) {
/* 368 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */         }
/*     */         
/* 371 */         if (flag && flag2 && !flag1 && !flag3) {
/* 372 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */         }
/*     */         
/* 375 */         if (flag && flag3 && !flag1 && !flag2) {
/* 376 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */         }
/*     */       } 
/*     */       
/* 380 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
/* 381 */         if (BlockRailBase.isRailBlock(this.world, blockpos.up())) {
/* 382 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
/*     */         }
/*     */         
/* 385 */         if (BlockRailBase.isRailBlock(this.world, blockpos1.up())) {
/* 386 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
/*     */         }
/*     */       } 
/*     */       
/* 390 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
/* 391 */         if (BlockRailBase.isRailBlock(this.world, blockpos3.up())) {
/* 392 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
/*     */         }
/*     */         
/* 395 */         if (BlockRailBase.isRailBlock(this.world, blockpos2.up())) {
/* 396 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
/*     */         }
/*     */       } 
/*     */       
/* 400 */       if (blockrailbase$enumraildirection == null) {
/* 401 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 404 */       this.state = this.state.withProperty(this.block.getShapeProperty(), blockrailbase$enumraildirection);
/* 405 */       this.world.setBlockState(this.pos, this.state, 3);
/*     */     }
/*     */     
/*     */     private boolean func_180361_d(BlockPos p_180361_1_) {
/* 409 */       Rail blockrailbase$rail = findRailAt(p_180361_1_);
/*     */       
/* 411 */       if (blockrailbase$rail == null) {
/* 412 */         return false;
/*     */       }
/* 414 */       blockrailbase$rail.func_150651_b();
/* 415 */       return blockrailbase$rail.func_150649_b(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public Rail func_180364_a(boolean p_180364_1_, boolean p_180364_2_) {
/* 420 */       BlockPos blockpos = this.pos.north();
/* 421 */       BlockPos blockpos1 = this.pos.south();
/* 422 */       BlockPos blockpos2 = this.pos.west();
/* 423 */       BlockPos blockpos3 = this.pos.east();
/* 424 */       boolean flag = func_180361_d(blockpos);
/* 425 */       boolean flag1 = func_180361_d(blockpos1);
/* 426 */       boolean flag2 = func_180361_d(blockpos2);
/* 427 */       boolean flag3 = func_180361_d(blockpos3);
/* 428 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = null;
/*     */       
/* 430 */       if ((flag || flag1) && !flag2 && !flag3) {
/* 431 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 434 */       if ((flag2 || flag3) && !flag && !flag1) {
/* 435 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */       }
/*     */       
/* 438 */       if (!this.isPowered) {
/* 439 */         if (flag1 && flag3 && !flag && !flag2) {
/* 440 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */         }
/*     */         
/* 443 */         if (flag1 && flag2 && !flag && !flag3) {
/* 444 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */         }
/*     */         
/* 447 */         if (flag && flag2 && !flag1 && !flag3) {
/* 448 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */         }
/*     */         
/* 451 */         if (flag && flag3 && !flag1 && !flag2) {
/* 452 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */         }
/*     */       } 
/*     */       
/* 456 */       if (blockrailbase$enumraildirection == null) {
/* 457 */         if (flag || flag1) {
/* 458 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */         }
/*     */         
/* 461 */         if (flag2 || flag3) {
/* 462 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */         }
/*     */         
/* 465 */         if (!this.isPowered) {
/* 466 */           if (p_180364_1_) {
/* 467 */             if (flag1 && flag3) {
/* 468 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */             }
/*     */             
/* 471 */             if (flag2 && flag1) {
/* 472 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */             }
/*     */             
/* 475 */             if (flag3 && flag) {
/* 476 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */             }
/*     */             
/* 479 */             if (flag && flag2) {
/* 480 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */             }
/*     */           } else {
/* 483 */             if (flag && flag2) {
/* 484 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */             }
/*     */             
/* 487 */             if (flag3 && flag) {
/* 488 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */             }
/*     */             
/* 491 */             if (flag2 && flag1) {
/* 492 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */             }
/*     */             
/* 495 */             if (flag1 && flag3) {
/* 496 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 502 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
/* 503 */         if (BlockRailBase.isRailBlock(this.world, blockpos.up())) {
/* 504 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
/*     */         }
/*     */         
/* 507 */         if (BlockRailBase.isRailBlock(this.world, blockpos1.up())) {
/* 508 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
/*     */         }
/*     */       } 
/*     */       
/* 512 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
/* 513 */         if (BlockRailBase.isRailBlock(this.world, blockpos3.up())) {
/* 514 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
/*     */         }
/*     */         
/* 517 */         if (BlockRailBase.isRailBlock(this.world, blockpos2.up())) {
/* 518 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
/*     */         }
/*     */       } 
/*     */       
/* 522 */       if (blockrailbase$enumraildirection == null) {
/* 523 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 526 */       func_180360_a(blockrailbase$enumraildirection);
/* 527 */       this.state = this.state.withProperty(this.block.getShapeProperty(), blockrailbase$enumraildirection);
/*     */       
/* 529 */       if (p_180364_2_ || this.world.getBlockState(this.pos) != this.state) {
/* 530 */         this.world.setBlockState(this.pos, this.state, 3);
/*     */         
/* 532 */         for (int i = 0; i < this.field_150657_g.size(); i++) {
/* 533 */           Rail blockrailbase$rail = findRailAt(this.field_150657_g.get(i));
/*     */           
/* 535 */           if (blockrailbase$rail != null) {
/* 536 */             blockrailbase$rail.func_150651_b();
/*     */             
/* 538 */             if (blockrailbase$rail.func_150649_b(this)) {
/* 539 */               blockrailbase$rail.func_150645_c(this);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 545 */       return this;
/*     */     }
/*     */     
/*     */     public IBlockState getBlockState() {
/* 549 */       return this.state;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockRailBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */