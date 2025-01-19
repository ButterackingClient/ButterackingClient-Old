/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemDoor;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.util.WeightedRandomChestContent;
/*     */ import net.minecraft.world.World;
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
/*     */ public abstract class StructureComponent
/*     */ {
/*     */   protected StructureBoundingBox boundingBox;
/*     */   protected EnumFacing coordBaseMode;
/*     */   protected int componentType;
/*     */   
/*     */   public StructureComponent() {}
/*     */   
/*     */   protected StructureComponent(int type) {
/*  39 */     this.componentType = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound createStructureBaseNBT() {
/*  49 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  50 */     nbttagcompound.setString("id", MapGenStructureIO.getStructureComponentName(this));
/*  51 */     nbttagcompound.setTag("BB", (NBTBase)this.boundingBox.toNBTTagIntArray());
/*  52 */     nbttagcompound.setInteger("O", (this.coordBaseMode == null) ? -1 : this.coordBaseMode.getHorizontalIndex());
/*  53 */     nbttagcompound.setInteger("GD", this.componentType);
/*  54 */     writeStructureToNBT(nbttagcompound);
/*  55 */     return nbttagcompound;
/*     */   }
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
/*     */   public void readStructureBaseNBT(World worldIn, NBTTagCompound tagCompound) {
/*  69 */     if (tagCompound.hasKey("BB")) {
/*  70 */       this.boundingBox = new StructureBoundingBox(tagCompound.getIntArray("BB"));
/*     */     }
/*     */     
/*  73 */     int i = tagCompound.getInteger("O");
/*  74 */     this.coordBaseMode = (i == -1) ? null : EnumFacing.getHorizontal(i);
/*  75 */     this.componentType = tagCompound.getInteger("GD");
/*  76 */     readStructureFromNBT(tagCompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureBoundingBox getBoundingBox() {
/*  97 */     return this.boundingBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getComponentType() {
/* 104 */     return this.componentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructureComponent findIntersecting(List<StructureComponent> listIn, StructureBoundingBox boundingboxIn) {
/* 111 */     for (StructureComponent structurecomponent : listIn) {
/* 112 */       if (structurecomponent.getBoundingBox() != null && structurecomponent.getBoundingBox().intersectsWith(boundingboxIn)) {
/* 113 */         return structurecomponent;
/*     */       }
/*     */     } 
/*     */     
/* 117 */     return null;
/*     */   }
/*     */   
/*     */   public BlockPos getBoundingBoxCenter() {
/* 121 */     return new BlockPos(this.boundingBox.getCenter());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isLiquidInStructureBoundingBox(World worldIn, StructureBoundingBox boundingboxIn) {
/* 128 */     int i = Math.max(this.boundingBox.minX - 1, boundingboxIn.minX);
/* 129 */     int j = Math.max(this.boundingBox.minY - 1, boundingboxIn.minY);
/* 130 */     int k = Math.max(this.boundingBox.minZ - 1, boundingboxIn.minZ);
/* 131 */     int l = Math.min(this.boundingBox.maxX + 1, boundingboxIn.maxX);
/* 132 */     int i1 = Math.min(this.boundingBox.maxY + 1, boundingboxIn.maxY);
/* 133 */     int j1 = Math.min(this.boundingBox.maxZ + 1, boundingboxIn.maxZ);
/* 134 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 136 */     for (int k1 = i; k1 <= l; k1++) {
/* 137 */       for (int l1 = k; l1 <= j1; l1++) {
/* 138 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, j, l1)).getBlock().getMaterial().isLiquid()) {
/* 139 */           return true;
/*     */         }
/*     */         
/* 142 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(k1, i1, l1)).getBlock().getMaterial().isLiquid()) {
/* 143 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     for (int i2 = i; i2 <= l; i2++) {
/* 149 */       for (int k2 = j; k2 <= i1; k2++) {
/* 150 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(i2, k2, k)).getBlock().getMaterial().isLiquid()) {
/* 151 */           return true;
/*     */         }
/*     */         
/* 154 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(i2, k2, j1)).getBlock().getMaterial().isLiquid()) {
/* 155 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     for (int j2 = k; j2 <= j1; j2++) {
/* 161 */       for (int l2 = j; l2 <= i1; l2++) {
/* 162 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(i, l2, j2)).getBlock().getMaterial().isLiquid()) {
/* 163 */           return true;
/*     */         }
/*     */         
/* 166 */         if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(l, l2, j2)).getBlock().getMaterial().isLiquid()) {
/* 167 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 172 */     return false;
/*     */   }
/*     */   
/*     */   protected int getXWithOffset(int x, int z) {
/* 176 */     if (this.coordBaseMode == null) {
/* 177 */       return x;
/*     */     }
/* 179 */     switch (this.coordBaseMode) {
/*     */       case NORTH:
/*     */       case SOUTH:
/* 182 */         return this.boundingBox.minX + x;
/*     */       
/*     */       case WEST:
/* 185 */         return this.boundingBox.maxX - z;
/*     */       
/*     */       case EAST:
/* 188 */         return this.boundingBox.minX + z;
/*     */     } 
/*     */     
/* 191 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getYWithOffset(int y) {
/* 197 */     return (this.coordBaseMode == null) ? y : (y + this.boundingBox.minY);
/*     */   }
/*     */   
/*     */   protected int getZWithOffset(int x, int z) {
/* 201 */     if (this.coordBaseMode == null) {
/* 202 */       return z;
/*     */     }
/* 204 */     switch (this.coordBaseMode) {
/*     */       case NORTH:
/* 206 */         return this.boundingBox.maxZ - z;
/*     */       
/*     */       case SOUTH:
/* 209 */         return this.boundingBox.minZ + z;
/*     */       
/*     */       case WEST:
/*     */       case EAST:
/* 213 */         return this.boundingBox.minZ + x;
/*     */     } 
/*     */     
/* 216 */     return z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getMetadataWithOffset(Block blockIn, int meta) {
/* 225 */     if (blockIn == Blocks.rail) {
/* 226 */       if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.EAST) {
/* 227 */         if (meta == 1) {
/* 228 */           return 0;
/*     */         }
/*     */         
/* 231 */         return 1;
/*     */       } 
/* 233 */     } else if (blockIn instanceof net.minecraft.block.BlockDoor) {
/* 234 */       if (this.coordBaseMode == EnumFacing.SOUTH) {
/* 235 */         if (meta == 0) {
/* 236 */           return 2;
/*     */         }
/*     */         
/* 239 */         if (meta == 2) {
/* 240 */           return 0;
/*     */         }
/*     */       } else {
/* 243 */         if (this.coordBaseMode == EnumFacing.WEST) {
/* 244 */           return meta + 1 & 0x3;
/*     */         }
/*     */         
/* 247 */         if (this.coordBaseMode == EnumFacing.EAST) {
/* 248 */           return meta + 3 & 0x3;
/*     */         }
/*     */       } 
/* 251 */     } else if (blockIn != Blocks.stone_stairs && blockIn != Blocks.oak_stairs && blockIn != Blocks.nether_brick_stairs && blockIn != Blocks.stone_brick_stairs && blockIn != Blocks.sandstone_stairs) {
/* 252 */       if (blockIn == Blocks.ladder) {
/* 253 */         if (this.coordBaseMode == EnumFacing.SOUTH) {
/* 254 */           if (meta == EnumFacing.NORTH.getIndex()) {
/* 255 */             return EnumFacing.SOUTH.getIndex();
/*     */           }
/*     */           
/* 258 */           if (meta == EnumFacing.SOUTH.getIndex()) {
/* 259 */             return EnumFacing.NORTH.getIndex();
/*     */           }
/* 261 */         } else if (this.coordBaseMode == EnumFacing.WEST) {
/* 262 */           if (meta == EnumFacing.NORTH.getIndex()) {
/* 263 */             return EnumFacing.WEST.getIndex();
/*     */           }
/*     */           
/* 266 */           if (meta == EnumFacing.SOUTH.getIndex()) {
/* 267 */             return EnumFacing.EAST.getIndex();
/*     */           }
/*     */           
/* 270 */           if (meta == EnumFacing.WEST.getIndex()) {
/* 271 */             return EnumFacing.NORTH.getIndex();
/*     */           }
/*     */           
/* 274 */           if (meta == EnumFacing.EAST.getIndex()) {
/* 275 */             return EnumFacing.SOUTH.getIndex();
/*     */           }
/* 277 */         } else if (this.coordBaseMode == EnumFacing.EAST) {
/* 278 */           if (meta == EnumFacing.NORTH.getIndex()) {
/* 279 */             return EnumFacing.EAST.getIndex();
/*     */           }
/*     */           
/* 282 */           if (meta == EnumFacing.SOUTH.getIndex()) {
/* 283 */             return EnumFacing.WEST.getIndex();
/*     */           }
/*     */           
/* 286 */           if (meta == EnumFacing.WEST.getIndex()) {
/* 287 */             return EnumFacing.NORTH.getIndex();
/*     */           }
/*     */           
/* 290 */           if (meta == EnumFacing.EAST.getIndex()) {
/* 291 */             return EnumFacing.SOUTH.getIndex();
/*     */           }
/*     */         } 
/* 294 */       } else if (blockIn == Blocks.stone_button) {
/* 295 */         if (this.coordBaseMode == EnumFacing.SOUTH) {
/* 296 */           if (meta == 3) {
/* 297 */             return 4;
/*     */           }
/*     */           
/* 300 */           if (meta == 4) {
/* 301 */             return 3;
/*     */           }
/* 303 */         } else if (this.coordBaseMode == EnumFacing.WEST) {
/* 304 */           if (meta == 3) {
/* 305 */             return 1;
/*     */           }
/*     */           
/* 308 */           if (meta == 4) {
/* 309 */             return 2;
/*     */           }
/*     */           
/* 312 */           if (meta == 2) {
/* 313 */             return 3;
/*     */           }
/*     */           
/* 316 */           if (meta == 1) {
/* 317 */             return 4;
/*     */           }
/* 319 */         } else if (this.coordBaseMode == EnumFacing.EAST) {
/* 320 */           if (meta == 3) {
/* 321 */             return 2;
/*     */           }
/*     */           
/* 324 */           if (meta == 4) {
/* 325 */             return 1;
/*     */           }
/*     */           
/* 328 */           if (meta == 2) {
/* 329 */             return 3;
/*     */           }
/*     */           
/* 332 */           if (meta == 1) {
/* 333 */             return 4;
/*     */           }
/*     */         } 
/* 336 */       } else if (blockIn != Blocks.tripwire_hook && !(blockIn instanceof net.minecraft.block.BlockDirectional)) {
/* 337 */         if (blockIn == Blocks.piston || blockIn == Blocks.sticky_piston || blockIn == Blocks.lever || blockIn == Blocks.dispenser) {
/* 338 */           if (this.coordBaseMode == EnumFacing.SOUTH) {
/* 339 */             if (meta == EnumFacing.NORTH.getIndex() || meta == EnumFacing.SOUTH.getIndex()) {
/* 340 */               return EnumFacing.getFront(meta).getOpposite().getIndex();
/*     */             }
/* 342 */           } else if (this.coordBaseMode == EnumFacing.WEST) {
/* 343 */             if (meta == EnumFacing.NORTH.getIndex()) {
/* 344 */               return EnumFacing.WEST.getIndex();
/*     */             }
/*     */             
/* 347 */             if (meta == EnumFacing.SOUTH.getIndex()) {
/* 348 */               return EnumFacing.EAST.getIndex();
/*     */             }
/*     */             
/* 351 */             if (meta == EnumFacing.WEST.getIndex()) {
/* 352 */               return EnumFacing.NORTH.getIndex();
/*     */             }
/*     */             
/* 355 */             if (meta == EnumFacing.EAST.getIndex()) {
/* 356 */               return EnumFacing.SOUTH.getIndex();
/*     */             }
/* 358 */           } else if (this.coordBaseMode == EnumFacing.EAST) {
/* 359 */             if (meta == EnumFacing.NORTH.getIndex()) {
/* 360 */               return EnumFacing.EAST.getIndex();
/*     */             }
/*     */             
/* 363 */             if (meta == EnumFacing.SOUTH.getIndex()) {
/* 364 */               return EnumFacing.WEST.getIndex();
/*     */             }
/*     */             
/* 367 */             if (meta == EnumFacing.WEST.getIndex()) {
/* 368 */               return EnumFacing.NORTH.getIndex();
/*     */             }
/*     */             
/* 371 */             if (meta == EnumFacing.EAST.getIndex()) {
/* 372 */               return EnumFacing.SOUTH.getIndex();
/*     */             }
/*     */           } 
/*     */         }
/*     */       } else {
/* 377 */         EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
/*     */         
/* 379 */         if (this.coordBaseMode == EnumFacing.SOUTH) {
/* 380 */           if (enumfacing == EnumFacing.SOUTH || enumfacing == EnumFacing.NORTH) {
/* 381 */             return enumfacing.getOpposite().getHorizontalIndex();
/*     */           }
/* 383 */         } else if (this.coordBaseMode == EnumFacing.WEST) {
/* 384 */           if (enumfacing == EnumFacing.NORTH) {
/* 385 */             return EnumFacing.WEST.getHorizontalIndex();
/*     */           }
/*     */           
/* 388 */           if (enumfacing == EnumFacing.SOUTH) {
/* 389 */             return EnumFacing.EAST.getHorizontalIndex();
/*     */           }
/*     */           
/* 392 */           if (enumfacing == EnumFacing.WEST) {
/* 393 */             return EnumFacing.NORTH.getHorizontalIndex();
/*     */           }
/*     */           
/* 396 */           if (enumfacing == EnumFacing.EAST) {
/* 397 */             return EnumFacing.SOUTH.getHorizontalIndex();
/*     */           }
/* 399 */         } else if (this.coordBaseMode == EnumFacing.EAST) {
/* 400 */           if (enumfacing == EnumFacing.NORTH) {
/* 401 */             return EnumFacing.EAST.getHorizontalIndex();
/*     */           }
/*     */           
/* 404 */           if (enumfacing == EnumFacing.SOUTH) {
/* 405 */             return EnumFacing.WEST.getHorizontalIndex();
/*     */           }
/*     */           
/* 408 */           if (enumfacing == EnumFacing.WEST) {
/* 409 */             return EnumFacing.NORTH.getHorizontalIndex();
/*     */           }
/*     */           
/* 412 */           if (enumfacing == EnumFacing.EAST) {
/* 413 */             return EnumFacing.SOUTH.getHorizontalIndex();
/*     */           }
/*     */         } 
/*     */       } 
/* 417 */     } else if (this.coordBaseMode == EnumFacing.SOUTH) {
/* 418 */       if (meta == 2) {
/* 419 */         return 3;
/*     */       }
/*     */       
/* 422 */       if (meta == 3) {
/* 423 */         return 2;
/*     */       }
/* 425 */     } else if (this.coordBaseMode == EnumFacing.WEST) {
/* 426 */       if (meta == 0) {
/* 427 */         return 2;
/*     */       }
/*     */       
/* 430 */       if (meta == 1) {
/* 431 */         return 3;
/*     */       }
/*     */       
/* 434 */       if (meta == 2) {
/* 435 */         return 0;
/*     */       }
/*     */       
/* 438 */       if (meta == 3) {
/* 439 */         return 1;
/*     */       }
/* 441 */     } else if (this.coordBaseMode == EnumFacing.EAST) {
/* 442 */       if (meta == 0) {
/* 443 */         return 2;
/*     */       }
/*     */       
/* 446 */       if (meta == 1) {
/* 447 */         return 3;
/*     */       }
/*     */       
/* 450 */       if (meta == 2) {
/* 451 */         return 1;
/*     */       }
/*     */       
/* 454 */       if (meta == 3) {
/* 455 */         return 0;
/*     */       }
/*     */     } 
/*     */     
/* 459 */     return meta;
/*     */   }
/*     */   
/*     */   protected void setBlockState(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 463 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 465 */     if (boundingboxIn.isVecInside((Vec3i)blockpos)) {
/* 466 */       worldIn.setBlockState(blockpos, blockstateIn, 2);
/*     */     }
/*     */   }
/*     */   
/*     */   protected IBlockState getBlockStateFromPos(World worldIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 471 */     int i = getXWithOffset(x, z);
/* 472 */     int j = getYWithOffset(y);
/* 473 */     int k = getZWithOffset(x, z);
/* 474 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 475 */     return !boundingboxIn.isVecInside((Vec3i)blockpos) ? Blocks.air.getDefaultState() : worldIn.getBlockState(blockpos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fillWithAir(World worldIn, StructureBoundingBox structurebb, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
/* 483 */     for (int i = minY; i <= maxY; i++) {
/* 484 */       for (int j = minX; j <= maxX; j++) {
/* 485 */         for (int k = minZ; k <= maxZ; k++) {
/* 486 */           setBlockState(worldIn, Blocks.air.getDefaultState(), j, i, k, structurebb);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly) {
/* 496 */     for (int i = yMin; i <= yMax; i++) {
/* 497 */       for (int j = xMin; j <= xMax; j++) {
/* 498 */         for (int k = zMin; k <= zMax; k++) {
/* 499 */           if (!existingOnly || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getBlock().getMaterial() != Material.air) {
/* 500 */             if (i != yMin && i != yMax && j != xMin && j != xMax && k != zMin && k != zMax) {
/* 501 */               setBlockState(worldIn, insideBlockState, j, i, k, boundingboxIn);
/*     */             } else {
/* 503 */               setBlockState(worldIn, boundaryBlockState, j, i, k, boundingboxIn);
/*     */             } 
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
/*     */   protected void fillWithRandomizedBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean alwaysReplace, Random rand, BlockSelector blockselector) {
/* 516 */     for (int i = minY; i <= maxY; i++) {
/* 517 */       for (int j = minX; j <= maxX; j++) {
/* 518 */         for (int k = minZ; k <= maxZ; k++) {
/* 519 */           if (!alwaysReplace || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getBlock().getMaterial() != Material.air) {
/* 520 */             blockselector.selectBlocks(rand, j, i, k, !(i != minY && i != maxY && j != minX && j != maxX && k != minZ && k != maxZ));
/* 521 */             setBlockState(worldIn, blockselector.getBlockState(), j, i, k, boundingboxIn);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void func_175805_a(World worldIn, StructureBoundingBox boundingboxIn, Random rand, float chance, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, IBlockState blockstate1, IBlockState blockstate2, boolean p_175805_13_) {
/* 529 */     for (int i = minY; i <= maxY; i++) {
/* 530 */       for (int j = minX; j <= maxX; j++) {
/* 531 */         for (int k = minZ; k <= maxZ; k++) {
/* 532 */           if (rand.nextFloat() <= chance && (!p_175805_13_ || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getBlock().getMaterial() != Material.air)) {
/* 533 */             if (i != minY && i != maxY && j != minX && j != maxX && k != minZ && k != maxZ) {
/* 534 */               setBlockState(worldIn, blockstate2, j, i, k, boundingboxIn);
/*     */             } else {
/* 536 */               setBlockState(worldIn, blockstate1, j, i, k, boundingboxIn);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void randomlyPlaceBlock(World worldIn, StructureBoundingBox boundingboxIn, Random rand, float chance, int x, int y, int z, IBlockState blockstateIn) {
/* 545 */     if (rand.nextFloat() < chance) {
/* 546 */       setBlockState(worldIn, blockstateIn, x, y, z, boundingboxIn);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void randomlyRareFillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, IBlockState blockstateIn, boolean p_180777_10_) {
/* 551 */     float f = (maxX - minX + 1);
/* 552 */     float f1 = (maxY - minY + 1);
/* 553 */     float f2 = (maxZ - minZ + 1);
/* 554 */     float f3 = minX + f / 2.0F;
/* 555 */     float f4 = minZ + f2 / 2.0F;
/*     */     
/* 557 */     for (int i = minY; i <= maxY; i++) {
/* 558 */       float f5 = (i - minY) / f1;
/*     */       
/* 560 */       for (int j = minX; j <= maxX; j++) {
/* 561 */         float f6 = (j - f3) / f * 0.5F;
/*     */         
/* 563 */         for (int k = minZ; k <= maxZ; k++) {
/* 564 */           float f7 = (k - f4) / f2 * 0.5F;
/*     */           
/* 566 */           if (!p_180777_10_ || getBlockStateFromPos(worldIn, j, i, k, boundingboxIn).getBlock().getMaterial() != Material.air) {
/* 567 */             float f8 = f6 * f6 + f5 * f5 + f7 * f7;
/*     */             
/* 569 */             if (f8 <= 1.05F) {
/* 570 */               setBlockState(worldIn, blockstateIn, j, i, k, boundingboxIn);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearCurrentPositionBlocksUpwards(World worldIn, int x, int y, int z, StructureBoundingBox structurebb) {
/* 582 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 584 */     if (structurebb.isVecInside((Vec3i)blockpos)) {
/* 585 */       while (!worldIn.isAirBlock(blockpos) && blockpos.getY() < 255) {
/* 586 */         worldIn.setBlockState(blockpos, Blocks.air.getDefaultState(), 2);
/* 587 */         blockpos = blockpos.up();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn) {
/* 596 */     int i = getXWithOffset(x, z);
/* 597 */     int j = getYWithOffset(y);
/* 598 */     int k = getZWithOffset(x, z);
/*     */     
/* 600 */     if (boundingboxIn.isVecInside((Vec3i)new BlockPos(i, j, k))) {
/* 601 */       while ((worldIn.isAirBlock(new BlockPos(i, j, k)) || worldIn.getBlockState(new BlockPos(i, j, k)).getBlock().getMaterial().isLiquid()) && j > 1) {
/* 602 */         worldIn.setBlockState(new BlockPos(i, j, k), blockstateIn, 2);
/* 603 */         j--;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean generateChestContents(World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, List<WeightedRandomChestContent> listIn, int max) {
/* 609 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 611 */     if (boundingBoxIn.isVecInside((Vec3i)blockpos) && worldIn.getBlockState(blockpos).getBlock() != Blocks.chest) {
/* 612 */       IBlockState iblockstate = Blocks.chest.getDefaultState();
/* 613 */       worldIn.setBlockState(blockpos, Blocks.chest.correctFacing(worldIn, blockpos, iblockstate), 2);
/* 614 */       TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */       
/* 616 */       if (tileentity instanceof net.minecraft.tileentity.TileEntityChest) {
/* 617 */         WeightedRandomChestContent.generateChestContents(rand, listIn, (IInventory)tileentity, max);
/*     */       }
/*     */       
/* 620 */       return true;
/*     */     } 
/* 622 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean generateDispenserContents(World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, int meta, List<WeightedRandomChestContent> listIn, int max) {
/* 627 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 629 */     if (boundingBoxIn.isVecInside((Vec3i)blockpos) && worldIn.getBlockState(blockpos).getBlock() != Blocks.dispenser) {
/* 630 */       worldIn.setBlockState(blockpos, Blocks.dispenser.getStateFromMeta(getMetadataWithOffset(Blocks.dispenser, meta)), 2);
/* 631 */       TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */       
/* 633 */       if (tileentity instanceof TileEntityDispenser) {
/* 634 */         WeightedRandomChestContent.generateDispenserContents(rand, listIn, (TileEntityDispenser)tileentity, max);
/*     */       }
/*     */       
/* 637 */       return true;
/*     */     } 
/* 639 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void writeStructureToNBT(NBTTagCompound paramNBTTagCompound);
/*     */ 
/*     */   
/*     */   protected void placeDoorCurrentPosition(World worldIn, StructureBoundingBox boundingBoxIn, Random rand, int x, int y, int z, EnumFacing facing) {
/* 647 */     BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));
/*     */     
/* 649 */     if (boundingBoxIn.isVecInside((Vec3i)blockpos))
/* 650 */       ItemDoor.placeDoor(worldIn, blockpos, facing.rotateYCCW(), Blocks.oak_door); 
/*     */   }
/*     */   protected abstract void readStructureFromNBT(NBTTagCompound paramNBTTagCompound);
/*     */   public abstract boolean addComponentParts(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox);
/*     */   public void func_181138_a(int p_181138_1_, int p_181138_2_, int p_181138_3_) {
/* 655 */     this.boundingBox.offset(p_181138_1_, p_181138_2_, p_181138_3_);
/*     */   }
/*     */   
/*     */   public static abstract class BlockSelector {
/* 659 */     protected IBlockState blockstate = Blocks.air.getDefaultState();
/*     */     
/*     */     public abstract void selectBlocks(Random param1Random, int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean);
/*     */     
/*     */     public IBlockState getBlockState() {
/* 664 */       return this.blockstate;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\StructureComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */