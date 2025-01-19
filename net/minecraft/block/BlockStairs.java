/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockStairs extends Block {
/*  30 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
/*  31 */   public static final PropertyEnum<EnumHalf> HALF = PropertyEnum.create("half", EnumHalf.class);
/*  32 */   public static final PropertyEnum<EnumShape> SHAPE = PropertyEnum.create("shape", EnumShape.class);
/*  33 */   private static final int[][] field_150150_a = new int[][] { { 4, 5 }, { 5, 7 }, { 6, 7 }, { 4, 6 }, { 0, 1 }, { 1, 3 }, { 2, 3 }, { 0, 2 } };
/*     */   private final Block modelBlock;
/*     */   private final IBlockState modelState;
/*     */   private boolean hasRaytraced;
/*     */   private int rayTracePass;
/*     */   
/*     */   protected BlockStairs(IBlockState modelState) {
/*  40 */     super((modelState.getBlock()).blockMaterial);
/*  41 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)HALF, EnumHalf.BOTTOM).withProperty((IProperty)SHAPE, EnumShape.STRAIGHT));
/*  42 */     this.modelBlock = modelState.getBlock();
/*  43 */     this.modelState = modelState;
/*  44 */     setHardness(this.modelBlock.blockHardness);
/*  45 */     setResistance(this.modelBlock.blockResistance / 3.0F);
/*  46 */     setStepSound(this.modelBlock.stepSound);
/*  47 */     setLightOpacity(255);
/*  48 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  52 */     if (this.hasRaytraced) {
/*  53 */       setBlockBounds(0.5F * (this.rayTracePass % 2), 0.5F * (this.rayTracePass / 4 % 2), 0.5F * (this.rayTracePass / 2 % 2), 0.5F + 0.5F * (this.rayTracePass % 2), 0.5F + 0.5F * (this.rayTracePass / 4 % 2), 0.5F + 0.5F * (this.rayTracePass / 2 % 2));
/*     */     } else {
/*  55 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  63 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseCollisionBounds(IBlockAccess worldIn, BlockPos pos) {
/*  74 */     if (worldIn.getBlockState(pos).getValue((IProperty)HALF) == EnumHalf.TOP) {
/*  75 */       setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } else {
/*  77 */       setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isBlockStairs(Block blockIn) {
/*  85 */     return blockIn instanceof BlockStairs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSameStair(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
/*  92 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  93 */     Block block = iblockstate.getBlock();
/*  94 */     return (isBlockStairs(block) && iblockstate.getValue((IProperty)HALF) == state.getValue((IProperty)HALF) && iblockstate.getValue((IProperty)FACING) == state.getValue((IProperty)FACING));
/*     */   }
/*     */   
/*     */   public int func_176307_f(IBlockAccess blockAccess, BlockPos pos) {
/*  98 */     IBlockState iblockstate = blockAccess.getBlockState(pos);
/*  99 */     EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/* 100 */     EnumHalf blockstairs$enumhalf = (EnumHalf)iblockstate.getValue((IProperty)HALF);
/* 101 */     boolean flag = (blockstairs$enumhalf == EnumHalf.TOP);
/*     */     
/* 103 */     if (enumfacing == EnumFacing.EAST) {
/* 104 */       IBlockState iblockstate1 = blockAccess.getBlockState(pos.east());
/* 105 */       Block block = iblockstate1.getBlock();
/*     */       
/* 107 */       if (isBlockStairs(block) && blockstairs$enumhalf == iblockstate1.getValue((IProperty)HALF)) {
/* 108 */         EnumFacing enumfacing1 = (EnumFacing)iblockstate1.getValue((IProperty)FACING);
/*     */         
/* 110 */         if (enumfacing1 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.south(), iblockstate)) {
/* 111 */           return flag ? 1 : 2;
/*     */         }
/*     */         
/* 114 */         if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.north(), iblockstate)) {
/* 115 */           return flag ? 2 : 1;
/*     */         }
/*     */       } 
/* 118 */     } else if (enumfacing == EnumFacing.WEST) {
/* 119 */       IBlockState iblockstate2 = blockAccess.getBlockState(pos.west());
/* 120 */       Block block1 = iblockstate2.getBlock();
/*     */       
/* 122 */       if (isBlockStairs(block1) && blockstairs$enumhalf == iblockstate2.getValue((IProperty)HALF)) {
/* 123 */         EnumFacing enumfacing2 = (EnumFacing)iblockstate2.getValue((IProperty)FACING);
/*     */         
/* 125 */         if (enumfacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.south(), iblockstate)) {
/* 126 */           return flag ? 2 : 1;
/*     */         }
/*     */         
/* 129 */         if (enumfacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.north(), iblockstate)) {
/* 130 */           return flag ? 1 : 2;
/*     */         }
/*     */       } 
/* 133 */     } else if (enumfacing == EnumFacing.SOUTH) {
/* 134 */       IBlockState iblockstate3 = blockAccess.getBlockState(pos.south());
/* 135 */       Block block2 = iblockstate3.getBlock();
/*     */       
/* 137 */       if (isBlockStairs(block2) && blockstairs$enumhalf == iblockstate3.getValue((IProperty)HALF)) {
/* 138 */         EnumFacing enumfacing3 = (EnumFacing)iblockstate3.getValue((IProperty)FACING);
/*     */         
/* 140 */         if (enumfacing3 == EnumFacing.WEST && !isSameStair(blockAccess, pos.east(), iblockstate)) {
/* 141 */           return flag ? 2 : 1;
/*     */         }
/*     */         
/* 144 */         if (enumfacing3 == EnumFacing.EAST && !isSameStair(blockAccess, pos.west(), iblockstate)) {
/* 145 */           return flag ? 1 : 2;
/*     */         }
/*     */       } 
/* 148 */     } else if (enumfacing == EnumFacing.NORTH) {
/* 149 */       IBlockState iblockstate4 = blockAccess.getBlockState(pos.north());
/* 150 */       Block block3 = iblockstate4.getBlock();
/*     */       
/* 152 */       if (isBlockStairs(block3) && blockstairs$enumhalf == iblockstate4.getValue((IProperty)HALF)) {
/* 153 */         EnumFacing enumfacing4 = (EnumFacing)iblockstate4.getValue((IProperty)FACING);
/*     */         
/* 155 */         if (enumfacing4 == EnumFacing.WEST && !isSameStair(blockAccess, pos.east(), iblockstate)) {
/* 156 */           return flag ? 1 : 2;
/*     */         }
/*     */         
/* 159 */         if (enumfacing4 == EnumFacing.EAST && !isSameStair(blockAccess, pos.west(), iblockstate)) {
/* 160 */           return flag ? 2 : 1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     return 0;
/*     */   }
/*     */   
/*     */   public int func_176305_g(IBlockAccess blockAccess, BlockPos pos) {
/* 169 */     IBlockState iblockstate = blockAccess.getBlockState(pos);
/* 170 */     EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/* 171 */     EnumHalf blockstairs$enumhalf = (EnumHalf)iblockstate.getValue((IProperty)HALF);
/* 172 */     boolean flag = (blockstairs$enumhalf == EnumHalf.TOP);
/*     */     
/* 174 */     if (enumfacing == EnumFacing.EAST) {
/* 175 */       IBlockState iblockstate1 = blockAccess.getBlockState(pos.west());
/* 176 */       Block block = iblockstate1.getBlock();
/*     */       
/* 178 */       if (isBlockStairs(block) && blockstairs$enumhalf == iblockstate1.getValue((IProperty)HALF)) {
/* 179 */         EnumFacing enumfacing1 = (EnumFacing)iblockstate1.getValue((IProperty)FACING);
/*     */         
/* 181 */         if (enumfacing1 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.north(), iblockstate)) {
/* 182 */           return flag ? 1 : 2;
/*     */         }
/*     */         
/* 185 */         if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.south(), iblockstate)) {
/* 186 */           return flag ? 2 : 1;
/*     */         }
/*     */       } 
/* 189 */     } else if (enumfacing == EnumFacing.WEST) {
/* 190 */       IBlockState iblockstate2 = blockAccess.getBlockState(pos.east());
/* 191 */       Block block1 = iblockstate2.getBlock();
/*     */       
/* 193 */       if (isBlockStairs(block1) && blockstairs$enumhalf == iblockstate2.getValue((IProperty)HALF)) {
/* 194 */         EnumFacing enumfacing2 = (EnumFacing)iblockstate2.getValue((IProperty)FACING);
/*     */         
/* 196 */         if (enumfacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.north(), iblockstate)) {
/* 197 */           return flag ? 2 : 1;
/*     */         }
/*     */         
/* 200 */         if (enumfacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.south(), iblockstate)) {
/* 201 */           return flag ? 1 : 2;
/*     */         }
/*     */       } 
/* 204 */     } else if (enumfacing == EnumFacing.SOUTH) {
/* 205 */       IBlockState iblockstate3 = blockAccess.getBlockState(pos.north());
/* 206 */       Block block2 = iblockstate3.getBlock();
/*     */       
/* 208 */       if (isBlockStairs(block2) && blockstairs$enumhalf == iblockstate3.getValue((IProperty)HALF)) {
/* 209 */         EnumFacing enumfacing3 = (EnumFacing)iblockstate3.getValue((IProperty)FACING);
/*     */         
/* 211 */         if (enumfacing3 == EnumFacing.WEST && !isSameStair(blockAccess, pos.west(), iblockstate)) {
/* 212 */           return flag ? 2 : 1;
/*     */         }
/*     */         
/* 215 */         if (enumfacing3 == EnumFacing.EAST && !isSameStair(blockAccess, pos.east(), iblockstate)) {
/* 216 */           return flag ? 1 : 2;
/*     */         }
/*     */       } 
/* 219 */     } else if (enumfacing == EnumFacing.NORTH) {
/* 220 */       IBlockState iblockstate4 = blockAccess.getBlockState(pos.south());
/* 221 */       Block block3 = iblockstate4.getBlock();
/*     */       
/* 223 */       if (isBlockStairs(block3) && blockstairs$enumhalf == iblockstate4.getValue((IProperty)HALF)) {
/* 224 */         EnumFacing enumfacing4 = (EnumFacing)iblockstate4.getValue((IProperty)FACING);
/*     */         
/* 226 */         if (enumfacing4 == EnumFacing.WEST && !isSameStair(blockAccess, pos.west(), iblockstate)) {
/* 227 */           return flag ? 1 : 2;
/*     */         }
/*     */         
/* 230 */         if (enumfacing4 == EnumFacing.EAST && !isSameStair(blockAccess, pos.east(), iblockstate)) {
/* 231 */           return flag ? 2 : 1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 236 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean func_176306_h(IBlockAccess blockAccess, BlockPos pos) {
/* 240 */     IBlockState iblockstate = blockAccess.getBlockState(pos);
/* 241 */     EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/* 242 */     EnumHalf blockstairs$enumhalf = (EnumHalf)iblockstate.getValue((IProperty)HALF);
/* 243 */     boolean flag = (blockstairs$enumhalf == EnumHalf.TOP);
/* 244 */     float f = 0.5F;
/* 245 */     float f1 = 1.0F;
/*     */     
/* 247 */     if (flag) {
/* 248 */       f = 0.0F;
/* 249 */       f1 = 0.5F;
/*     */     } 
/*     */     
/* 252 */     float f2 = 0.0F;
/* 253 */     float f3 = 1.0F;
/* 254 */     float f4 = 0.0F;
/* 255 */     float f5 = 0.5F;
/* 256 */     boolean flag1 = true;
/*     */     
/* 258 */     if (enumfacing == EnumFacing.EAST) {
/* 259 */       f2 = 0.5F;
/* 260 */       f5 = 1.0F;
/* 261 */       IBlockState iblockstate1 = blockAccess.getBlockState(pos.east());
/* 262 */       Block block = iblockstate1.getBlock();
/*     */       
/* 264 */       if (isBlockStairs(block) && blockstairs$enumhalf == iblockstate1.getValue((IProperty)HALF)) {
/* 265 */         EnumFacing enumfacing1 = (EnumFacing)iblockstate1.getValue((IProperty)FACING);
/*     */         
/* 267 */         if (enumfacing1 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.south(), iblockstate)) {
/* 268 */           f5 = 0.5F;
/* 269 */           flag1 = false;
/* 270 */         } else if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.north(), iblockstate)) {
/* 271 */           f4 = 0.5F;
/* 272 */           flag1 = false;
/*     */         } 
/*     */       } 
/* 275 */     } else if (enumfacing == EnumFacing.WEST) {
/* 276 */       f3 = 0.5F;
/* 277 */       f5 = 1.0F;
/* 278 */       IBlockState iblockstate2 = blockAccess.getBlockState(pos.west());
/* 279 */       Block block1 = iblockstate2.getBlock();
/*     */       
/* 281 */       if (isBlockStairs(block1) && blockstairs$enumhalf == iblockstate2.getValue((IProperty)HALF)) {
/* 282 */         EnumFacing enumfacing2 = (EnumFacing)iblockstate2.getValue((IProperty)FACING);
/*     */         
/* 284 */         if (enumfacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.south(), iblockstate)) {
/* 285 */           f5 = 0.5F;
/* 286 */           flag1 = false;
/* 287 */         } else if (enumfacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.north(), iblockstate)) {
/* 288 */           f4 = 0.5F;
/* 289 */           flag1 = false;
/*     */         } 
/*     */       } 
/* 292 */     } else if (enumfacing == EnumFacing.SOUTH) {
/* 293 */       f4 = 0.5F;
/* 294 */       f5 = 1.0F;
/* 295 */       IBlockState iblockstate3 = blockAccess.getBlockState(pos.south());
/* 296 */       Block block2 = iblockstate3.getBlock();
/*     */       
/* 298 */       if (isBlockStairs(block2) && blockstairs$enumhalf == iblockstate3.getValue((IProperty)HALF)) {
/* 299 */         EnumFacing enumfacing3 = (EnumFacing)iblockstate3.getValue((IProperty)FACING);
/*     */         
/* 301 */         if (enumfacing3 == EnumFacing.WEST && !isSameStair(blockAccess, pos.east(), iblockstate)) {
/* 302 */           f3 = 0.5F;
/* 303 */           flag1 = false;
/* 304 */         } else if (enumfacing3 == EnumFacing.EAST && !isSameStair(blockAccess, pos.west(), iblockstate)) {
/* 305 */           f2 = 0.5F;
/* 306 */           flag1 = false;
/*     */         } 
/*     */       } 
/* 309 */     } else if (enumfacing == EnumFacing.NORTH) {
/* 310 */       IBlockState iblockstate4 = blockAccess.getBlockState(pos.north());
/* 311 */       Block block3 = iblockstate4.getBlock();
/*     */       
/* 313 */       if (isBlockStairs(block3) && blockstairs$enumhalf == iblockstate4.getValue((IProperty)HALF)) {
/* 314 */         EnumFacing enumfacing4 = (EnumFacing)iblockstate4.getValue((IProperty)FACING);
/*     */         
/* 316 */         if (enumfacing4 == EnumFacing.WEST && !isSameStair(blockAccess, pos.east(), iblockstate)) {
/* 317 */           f3 = 0.5F;
/* 318 */           flag1 = false;
/* 319 */         } else if (enumfacing4 == EnumFacing.EAST && !isSameStair(blockAccess, pos.west(), iblockstate)) {
/* 320 */           f2 = 0.5F;
/* 321 */           flag1 = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 326 */     setBlockBounds(f2, f, f4, f3, f1, f5);
/* 327 */     return flag1;
/*     */   }
/*     */   
/*     */   public boolean func_176304_i(IBlockAccess blockAccess, BlockPos pos) {
/* 331 */     IBlockState iblockstate = blockAccess.getBlockState(pos);
/* 332 */     EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/* 333 */     EnumHalf blockstairs$enumhalf = (EnumHalf)iblockstate.getValue((IProperty)HALF);
/* 334 */     boolean flag = (blockstairs$enumhalf == EnumHalf.TOP);
/* 335 */     float f = 0.5F;
/* 336 */     float f1 = 1.0F;
/*     */     
/* 338 */     if (flag) {
/* 339 */       f = 0.0F;
/* 340 */       f1 = 0.5F;
/*     */     } 
/*     */     
/* 343 */     float f2 = 0.0F;
/* 344 */     float f3 = 0.5F;
/* 345 */     float f4 = 0.5F;
/* 346 */     float f5 = 1.0F;
/* 347 */     boolean flag1 = false;
/*     */     
/* 349 */     if (enumfacing == EnumFacing.EAST) {
/* 350 */       IBlockState iblockstate1 = blockAccess.getBlockState(pos.west());
/* 351 */       Block block = iblockstate1.getBlock();
/*     */       
/* 353 */       if (isBlockStairs(block) && blockstairs$enumhalf == iblockstate1.getValue((IProperty)HALF)) {
/* 354 */         EnumFacing enumfacing1 = (EnumFacing)iblockstate1.getValue((IProperty)FACING);
/*     */         
/* 356 */         if (enumfacing1 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.north(), iblockstate)) {
/* 357 */           f4 = 0.0F;
/* 358 */           f5 = 0.5F;
/* 359 */           flag1 = true;
/* 360 */         } else if (enumfacing1 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.south(), iblockstate)) {
/* 361 */           f4 = 0.5F;
/* 362 */           f5 = 1.0F;
/* 363 */           flag1 = true;
/*     */         } 
/*     */       } 
/* 366 */     } else if (enumfacing == EnumFacing.WEST) {
/* 367 */       IBlockState iblockstate2 = blockAccess.getBlockState(pos.east());
/* 368 */       Block block1 = iblockstate2.getBlock();
/*     */       
/* 370 */       if (isBlockStairs(block1) && blockstairs$enumhalf == iblockstate2.getValue((IProperty)HALF)) {
/* 371 */         f2 = 0.5F;
/* 372 */         f3 = 1.0F;
/* 373 */         EnumFacing enumfacing2 = (EnumFacing)iblockstate2.getValue((IProperty)FACING);
/*     */         
/* 375 */         if (enumfacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, pos.north(), iblockstate)) {
/* 376 */           f4 = 0.0F;
/* 377 */           f5 = 0.5F;
/* 378 */           flag1 = true;
/* 379 */         } else if (enumfacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, pos.south(), iblockstate)) {
/* 380 */           f4 = 0.5F;
/* 381 */           f5 = 1.0F;
/* 382 */           flag1 = true;
/*     */         } 
/*     */       } 
/* 385 */     } else if (enumfacing == EnumFacing.SOUTH) {
/* 386 */       IBlockState iblockstate3 = blockAccess.getBlockState(pos.north());
/* 387 */       Block block2 = iblockstate3.getBlock();
/*     */       
/* 389 */       if (isBlockStairs(block2) && blockstairs$enumhalf == iblockstate3.getValue((IProperty)HALF)) {
/* 390 */         f4 = 0.0F;
/* 391 */         f5 = 0.5F;
/* 392 */         EnumFacing enumfacing3 = (EnumFacing)iblockstate3.getValue((IProperty)FACING);
/*     */         
/* 394 */         if (enumfacing3 == EnumFacing.WEST && !isSameStair(blockAccess, pos.west(), iblockstate)) {
/* 395 */           flag1 = true;
/* 396 */         } else if (enumfacing3 == EnumFacing.EAST && !isSameStair(blockAccess, pos.east(), iblockstate)) {
/* 397 */           f2 = 0.5F;
/* 398 */           f3 = 1.0F;
/* 399 */           flag1 = true;
/*     */         } 
/*     */       } 
/* 402 */     } else if (enumfacing == EnumFacing.NORTH) {
/* 403 */       IBlockState iblockstate4 = blockAccess.getBlockState(pos.south());
/* 404 */       Block block3 = iblockstate4.getBlock();
/*     */       
/* 406 */       if (isBlockStairs(block3) && blockstairs$enumhalf == iblockstate4.getValue((IProperty)HALF)) {
/* 407 */         EnumFacing enumfacing4 = (EnumFacing)iblockstate4.getValue((IProperty)FACING);
/*     */         
/* 409 */         if (enumfacing4 == EnumFacing.WEST && !isSameStair(blockAccess, pos.west(), iblockstate)) {
/* 410 */           flag1 = true;
/* 411 */         } else if (enumfacing4 == EnumFacing.EAST && !isSameStair(blockAccess, pos.east(), iblockstate)) {
/* 412 */           f2 = 0.5F;
/* 413 */           f3 = 1.0F;
/* 414 */           flag1 = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 419 */     if (flag1) {
/* 420 */       setBlockBounds(f2, f, f4, f3, f1, f5);
/*     */     }
/*     */     
/* 423 */     return flag1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/* 430 */     setBaseCollisionBounds((IBlockAccess)worldIn, pos);
/* 431 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/* 432 */     boolean flag = func_176306_h((IBlockAccess)worldIn, pos);
/* 433 */     super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     
/* 435 */     if (flag && func_176304_i((IBlockAccess)worldIn, pos)) {
/* 436 */       super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
/*     */     }
/*     */     
/* 439 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 443 */     this.modelBlock.randomDisplayTick(worldIn, pos, state, rand);
/*     */   }
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/* 447 */     this.modelBlock.onBlockClicked(worldIn, pos, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/* 454 */     this.modelBlock.onBlockDestroyedByPlayer(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos) {
/* 458 */     return this.modelBlock.getMixedBrightnessForBlock(worldIn, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExplosionResistance(Entity exploder) {
/* 465 */     return this.modelBlock.getExplosionResistance(exploder);
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 469 */     return this.modelBlock.getBlockLayer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/* 476 */     return this.modelBlock.tickRate(worldIn);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/* 480 */     return this.modelBlock.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */   
/*     */   public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion) {
/* 484 */     return this.modelBlock.modifyAcceleration(worldIn, pos, entityIn, motion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCollidable() {
/* 491 */     return this.modelBlock.isCollidable();
/*     */   }
/*     */   
/*     */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/* 495 */     return this.modelBlock.canCollideCheck(state, hitIfLiquid);
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 499 */     return this.modelBlock.canPlaceBlockAt(worldIn, pos);
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 503 */     onNeighborBlockChange(worldIn, pos, this.modelState, Blocks.air);
/* 504 */     this.modelBlock.onBlockAdded(worldIn, pos, this.modelState);
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 508 */     this.modelBlock.breakBlock(worldIn, pos, this.modelState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {
/* 515 */     this.modelBlock.onEntityCollidedWithBlock(worldIn, pos, entityIn);
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 519 */     this.modelBlock.updateTick(worldIn, pos, state, rand);
/*     */   }
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 523 */     return this.modelBlock.onBlockActivated(worldIn, pos, this.modelState, playerIn, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
/* 530 */     this.modelBlock.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state) {
/* 537 */     return this.modelBlock.getMapColor(this.modelState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 545 */     IBlockState iblockstate = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
/* 546 */     iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing()).withProperty((IProperty)SHAPE, EnumShape.STRAIGHT);
/* 547 */     return (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || hitY <= 0.5D)) ? iblockstate.withProperty((IProperty)HALF, EnumHalf.BOTTOM) : iblockstate.withProperty((IProperty)HALF, EnumHalf.TOP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/* 554 */     MovingObjectPosition[] amovingobjectposition = new MovingObjectPosition[8];
/* 555 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 556 */     int i = ((EnumFacing)iblockstate.getValue((IProperty)FACING)).getHorizontalIndex();
/* 557 */     boolean flag = (iblockstate.getValue((IProperty)HALF) == EnumHalf.TOP);
/* 558 */     int[] aint = field_150150_a[i + (flag ? 4 : 0)];
/* 559 */     this.hasRaytraced = true;
/*     */     
/* 561 */     for (int j = 0; j < 8; j++) {
/* 562 */       this.rayTracePass = j;
/*     */       
/* 564 */       if (Arrays.binarySearch(aint, j) < 0)
/* 565 */         amovingobjectposition[j] = super.collisionRayTrace(worldIn, pos, start, end); 
/*     */     } 
/*     */     byte b1;
/*     */     int k, arrayOfInt1[];
/* 569 */     for (k = (arrayOfInt1 = aint).length, b1 = 0; b1 < k; ) { int n = arrayOfInt1[b1];
/* 570 */       amovingobjectposition[n] = null;
/*     */       b1++; }
/*     */     
/* 573 */     MovingObjectPosition movingobjectposition1 = null;
/* 574 */     double d1 = 0.0D; byte b2; int m;
/*     */     MovingObjectPosition[] arrayOfMovingObjectPosition1;
/* 576 */     for (m = (arrayOfMovingObjectPosition1 = amovingobjectposition).length, b2 = 0; b2 < m; ) { MovingObjectPosition movingobjectposition = arrayOfMovingObjectPosition1[b2];
/* 577 */       if (movingobjectposition != null) {
/* 578 */         double d0 = movingobjectposition.hitVec.squareDistanceTo(end);
/*     */         
/* 580 */         if (d0 > d1) {
/* 581 */           movingobjectposition1 = movingobjectposition;
/* 582 */           d1 = d0;
/*     */         } 
/*     */       } 
/*     */       b2++; }
/*     */     
/* 587 */     return movingobjectposition1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 594 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)HALF, ((meta & 0x4) > 0) ? EnumHalf.TOP : EnumHalf.BOTTOM);
/* 595 */     iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.getFront(5 - (meta & 0x3)));
/* 596 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 603 */     int i = 0;
/*     */     
/* 605 */     if (state.getValue((IProperty)HALF) == EnumHalf.TOP) {
/* 606 */       i |= 0x4;
/*     */     }
/*     */     
/* 609 */     i |= 5 - ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/* 610 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 618 */     if (func_176306_h(worldIn, pos)) {
/* 619 */       switch (func_176305_g(worldIn, pos)) {
/*     */         case 0:
/* 621 */           state = state.withProperty((IProperty)SHAPE, EnumShape.STRAIGHT);
/*     */           break;
/*     */         
/*     */         case 1:
/* 625 */           state = state.withProperty((IProperty)SHAPE, EnumShape.INNER_RIGHT);
/*     */           break;
/*     */         
/*     */         case 2:
/* 629 */           state = state.withProperty((IProperty)SHAPE, EnumShape.INNER_LEFT); break;
/*     */       } 
/*     */     } else {
/* 632 */       switch (func_176307_f(worldIn, pos)) {
/*     */         case 0:
/* 634 */           state = state.withProperty((IProperty)SHAPE, EnumShape.STRAIGHT);
/*     */           break;
/*     */         
/*     */         case 1:
/* 638 */           state = state.withProperty((IProperty)SHAPE, EnumShape.OUTER_RIGHT);
/*     */           break;
/*     */         
/*     */         case 2:
/* 642 */           state = state.withProperty((IProperty)SHAPE, EnumShape.OUTER_LEFT);
/*     */           break;
/*     */       } 
/*     */     } 
/* 646 */     return state;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 650 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)HALF, (IProperty)SHAPE });
/*     */   }
/*     */   
/*     */   public enum EnumHalf implements IStringSerializable {
/* 654 */     TOP("top"),
/* 655 */     BOTTOM("bottom");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     EnumHalf(String name) {
/* 660 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 664 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 668 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumShape implements IStringSerializable {
/* 673 */     STRAIGHT("straight"),
/* 674 */     INNER_LEFT("inner_left"),
/* 675 */     INNER_RIGHT("inner_right"),
/* 676 */     OUTER_LEFT("outer_left"),
/* 677 */     OUTER_RIGHT("outer_right");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     EnumShape(String name) {
/* 682 */       this.name = name;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 686 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 690 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockStairs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */