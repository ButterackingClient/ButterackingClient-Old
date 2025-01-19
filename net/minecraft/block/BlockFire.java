/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockFire
/*     */   extends Block
/*     */ {
/*  26 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
/*  27 */   public static final PropertyBool FLIP = PropertyBool.create("flip");
/*  28 */   public static final PropertyBool ALT = PropertyBool.create("alt");
/*  29 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  30 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  31 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  32 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  33 */   public static final PropertyInteger UPPER = PropertyInteger.create("upper", 0, 2);
/*  34 */   private final Map<Block, Integer> encouragements = Maps.newIdentityHashMap();
/*  35 */   private final Map<Block, Integer> flammabilities = Maps.newIdentityHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  42 */     int i = pos.getX();
/*  43 */     int j = pos.getY();
/*  44 */     int k = pos.getZ();
/*     */     
/*  46 */     if (!World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) && !Blocks.fire.canCatchFire(worldIn, pos.down())) {
/*  47 */       boolean flag = ((i + j + k & 0x1) == 1);
/*  48 */       boolean flag1 = ((i / 2 + j / 2 + k / 2 & 0x1) == 1);
/*  49 */       int l = 0;
/*     */       
/*  51 */       if (canCatchFire(worldIn, pos.up())) {
/*  52 */         l = flag ? 1 : 2;
/*     */       }
/*     */       
/*  55 */       return state.withProperty((IProperty)NORTH, Boolean.valueOf(canCatchFire(worldIn, pos.north()))).withProperty((IProperty)EAST, Boolean.valueOf(canCatchFire(worldIn, pos.east()))).withProperty((IProperty)SOUTH, Boolean.valueOf(canCatchFire(worldIn, pos.south()))).withProperty((IProperty)WEST, Boolean.valueOf(canCatchFire(worldIn, pos.west()))).withProperty((IProperty)UPPER, Integer.valueOf(l)).withProperty((IProperty)FLIP, Boolean.valueOf(flag1)).withProperty((IProperty)ALT, Boolean.valueOf(flag));
/*     */     } 
/*  57 */     return getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockFire() {
/*  62 */     super(Material.fire);
/*  63 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)).withProperty((IProperty)FLIP, Boolean.valueOf(false)).withProperty((IProperty)ALT, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)).withProperty((IProperty)UPPER, Integer.valueOf(0)));
/*  64 */     setTickRandomly(true);
/*     */   }
/*     */   
/*     */   public static void init() {
/*  68 */     Blocks.fire.setFireInfo(Blocks.planks, 5, 20);
/*  69 */     Blocks.fire.setFireInfo(Blocks.double_wooden_slab, 5, 20);
/*  70 */     Blocks.fire.setFireInfo(Blocks.wooden_slab, 5, 20);
/*  71 */     Blocks.fire.setFireInfo(Blocks.oak_fence_gate, 5, 20);
/*  72 */     Blocks.fire.setFireInfo(Blocks.spruce_fence_gate, 5, 20);
/*  73 */     Blocks.fire.setFireInfo(Blocks.birch_fence_gate, 5, 20);
/*  74 */     Blocks.fire.setFireInfo(Blocks.jungle_fence_gate, 5, 20);
/*  75 */     Blocks.fire.setFireInfo(Blocks.dark_oak_fence_gate, 5, 20);
/*  76 */     Blocks.fire.setFireInfo(Blocks.acacia_fence_gate, 5, 20);
/*  77 */     Blocks.fire.setFireInfo(Blocks.oak_fence, 5, 20);
/*  78 */     Blocks.fire.setFireInfo(Blocks.spruce_fence, 5, 20);
/*  79 */     Blocks.fire.setFireInfo(Blocks.birch_fence, 5, 20);
/*  80 */     Blocks.fire.setFireInfo(Blocks.jungle_fence, 5, 20);
/*  81 */     Blocks.fire.setFireInfo(Blocks.dark_oak_fence, 5, 20);
/*  82 */     Blocks.fire.setFireInfo(Blocks.acacia_fence, 5, 20);
/*  83 */     Blocks.fire.setFireInfo(Blocks.oak_stairs, 5, 20);
/*  84 */     Blocks.fire.setFireInfo(Blocks.birch_stairs, 5, 20);
/*  85 */     Blocks.fire.setFireInfo(Blocks.spruce_stairs, 5, 20);
/*  86 */     Blocks.fire.setFireInfo(Blocks.jungle_stairs, 5, 20);
/*  87 */     Blocks.fire.setFireInfo(Blocks.log, 5, 5);
/*  88 */     Blocks.fire.setFireInfo(Blocks.log2, 5, 5);
/*  89 */     Blocks.fire.setFireInfo(Blocks.leaves, 30, 60);
/*  90 */     Blocks.fire.setFireInfo(Blocks.leaves2, 30, 60);
/*  91 */     Blocks.fire.setFireInfo(Blocks.bookshelf, 30, 20);
/*  92 */     Blocks.fire.setFireInfo(Blocks.tnt, 15, 100);
/*  93 */     Blocks.fire.setFireInfo(Blocks.tallgrass, 60, 100);
/*  94 */     Blocks.fire.setFireInfo(Blocks.double_plant, 60, 100);
/*  95 */     Blocks.fire.setFireInfo(Blocks.yellow_flower, 60, 100);
/*  96 */     Blocks.fire.setFireInfo(Blocks.red_flower, 60, 100);
/*  97 */     Blocks.fire.setFireInfo(Blocks.deadbush, 60, 100);
/*  98 */     Blocks.fire.setFireInfo(Blocks.wool, 30, 60);
/*  99 */     Blocks.fire.setFireInfo(Blocks.vine, 15, 100);
/* 100 */     Blocks.fire.setFireInfo(Blocks.coal_block, 5, 5);
/* 101 */     Blocks.fire.setFireInfo(Blocks.hay_block, 60, 20);
/* 102 */     Blocks.fire.setFireInfo(Blocks.carpet, 60, 20);
/*     */   }
/*     */   
/*     */   public void setFireInfo(Block blockIn, int encouragement, int flammability) {
/* 106 */     this.encouragements.put(blockIn, Integer.valueOf(encouragement));
/* 107 */     this.flammabilities.put(blockIn, Integer.valueOf(flammability));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 111 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 118 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 129 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/* 136 */     return 30;
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 140 */     if (worldIn.getGameRules().getBoolean("doFireTick")) {
/* 141 */       if (!canPlaceBlockAt(worldIn, pos)) {
/* 142 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */       
/* 145 */       Block block = worldIn.getBlockState(pos.down()).getBlock();
/* 146 */       boolean flag = (block == Blocks.netherrack);
/*     */       
/* 148 */       if (worldIn.provider instanceof net.minecraft.world.WorldProviderEnd && block == Blocks.bedrock) {
/* 149 */         flag = true;
/*     */       }
/*     */       
/* 152 */       if (!flag && worldIn.isRaining() && canDie(worldIn, pos)) {
/* 153 */         worldIn.setBlockToAir(pos);
/*     */       } else {
/* 155 */         int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/* 157 */         if (i < 15) {
/* 158 */           state = state.withProperty((IProperty)AGE, Integer.valueOf(i + rand.nextInt(3) / 2));
/* 159 */           worldIn.setBlockState(pos, state, 4);
/*     */         } 
/*     */         
/* 162 */         worldIn.scheduleUpdate(pos, this, tickRate(worldIn) + rand.nextInt(10));
/*     */         
/* 164 */         if (!flag) {
/* 165 */           if (!canNeighborCatchFire(worldIn, pos)) {
/* 166 */             if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) || i > 3) {
/* 167 */               worldIn.setBlockToAir(pos);
/*     */             }
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 173 */           if (!canCatchFire((IBlockAccess)worldIn, pos.down()) && i == 15 && rand.nextInt(4) == 0) {
/* 174 */             worldIn.setBlockToAir(pos);
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 179 */         boolean flag1 = worldIn.isBlockinHighHumidity(pos);
/* 180 */         int j = 0;
/*     */         
/* 182 */         if (flag1) {
/* 183 */           j = -50;
/*     */         }
/*     */         
/* 186 */         catchOnFire(worldIn, pos.east(), 300 + j, rand, i);
/* 187 */         catchOnFire(worldIn, pos.west(), 300 + j, rand, i);
/* 188 */         catchOnFire(worldIn, pos.down(), 250 + j, rand, i);
/* 189 */         catchOnFire(worldIn, pos.up(), 250 + j, rand, i);
/* 190 */         catchOnFire(worldIn, pos.north(), 300 + j, rand, i);
/* 191 */         catchOnFire(worldIn, pos.south(), 300 + j, rand, i);
/*     */         
/* 193 */         for (int k = -1; k <= 1; k++) {
/* 194 */           for (int l = -1; l <= 1; l++) {
/* 195 */             for (int i1 = -1; i1 <= 4; i1++) {
/* 196 */               if (k != 0 || i1 != 0 || l != 0) {
/* 197 */                 int j1 = 100;
/*     */                 
/* 199 */                 if (i1 > 1) {
/* 200 */                   j1 += (i1 - 1) * 100;
/*     */                 }
/*     */                 
/* 203 */                 BlockPos blockpos = pos.add(k, i1, l);
/* 204 */                 int k1 = getNeighborEncouragement(worldIn, blockpos);
/*     */                 
/* 206 */                 if (k1 > 0) {
/* 207 */                   int l1 = (k1 + 40 + worldIn.getDifficulty().getDifficultyId() * 7) / (i + 30);
/*     */                   
/* 209 */                   if (flag1) {
/* 210 */                     l1 /= 2;
/*     */                   }
/*     */                   
/* 213 */                   if (l1 > 0 && rand.nextInt(j1) <= l1 && (!worldIn.isRaining() || !canDie(worldIn, blockpos))) {
/* 214 */                     int i2 = i + rand.nextInt(5) / 4;
/*     */                     
/* 216 */                     if (i2 > 15) {
/* 217 */                       i2 = 15;
/*     */                     }
/*     */                     
/* 220 */                     worldIn.setBlockState(blockpos, state.withProperty((IProperty)AGE, Integer.valueOf(i2)), 3);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean canDie(World worldIn, BlockPos pos) {
/* 232 */     return !(!worldIn.isRainingAt(pos) && !worldIn.isRainingAt(pos.west()) && !worldIn.isRainingAt(pos.east()) && !worldIn.isRainingAt(pos.north()) && !worldIn.isRainingAt(pos.south()));
/*     */   }
/*     */   
/*     */   public boolean requiresUpdates() {
/* 236 */     return false;
/*     */   }
/*     */   
/*     */   private int getFlammability(Block blockIn) {
/* 240 */     Integer integer = this.flammabilities.get(blockIn);
/* 241 */     return (integer == null) ? 0 : integer.intValue();
/*     */   }
/*     */   
/*     */   private int getEncouragement(Block blockIn) {
/* 245 */     Integer integer = this.encouragements.get(blockIn);
/* 246 */     return (integer == null) ? 0 : integer.intValue();
/*     */   }
/*     */   
/*     */   private void catchOnFire(World worldIn, BlockPos pos, int chance, Random random, int age) {
/* 250 */     int i = getFlammability(worldIn.getBlockState(pos).getBlock());
/*     */     
/* 252 */     if (random.nextInt(chance) < i) {
/* 253 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */       
/* 255 */       if (random.nextInt(age + 10) < 5 && !worldIn.isRainingAt(pos)) {
/* 256 */         int j = age + random.nextInt(5) / 4;
/*     */         
/* 258 */         if (j > 15) {
/* 259 */           j = 15;
/*     */         }
/*     */         
/* 262 */         worldIn.setBlockState(pos, getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(j)), 3);
/*     */       } else {
/* 264 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */       
/* 267 */       if (iblockstate.getBlock() == Blocks.tnt)
/* 268 */         Blocks.tnt.onBlockDestroyedByPlayer(worldIn, pos, iblockstate.withProperty((IProperty)BlockTNT.EXPLODE, Boolean.valueOf(true))); 
/*     */     } 
/*     */   } private boolean canNeighborCatchFire(World worldIn, BlockPos pos) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 274 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 275 */       if (canCatchFire((IBlockAccess)worldIn, pos.offset(enumfacing))) {
/* 276 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/* 280 */     return false;
/*     */   }
/*     */   
/*     */   private int getNeighborEncouragement(World worldIn, BlockPos pos) {
/* 284 */     if (!worldIn.isAirBlock(pos)) {
/* 285 */       return 0;
/*     */     }
/* 287 */     int i = 0; byte b; int j;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 289 */     for (j = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 290 */       i = Math.max(getEncouragement(worldIn.getBlockState(pos.offset(enumfacing)).getBlock()), i);
/*     */       b++; }
/*     */     
/* 293 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCollidable() {
/* 301 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCatchFire(IBlockAccess worldIn, BlockPos pos) {
/* 308 */     return (getEncouragement(worldIn.getBlockState(pos).getBlock()) > 0);
/*     */   }
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 312 */     return !(!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && !canNeighborCatchFire(worldIn, pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 319 */     if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && !canNeighborCatchFire(worldIn, pos)) {
/* 320 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 325 */     if (worldIn.provider.getDimensionId() > 0 || !Blocks.portal.func_176548_d(worldIn, pos)) {
/* 326 */       if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && !canNeighborCatchFire(worldIn, pos)) {
/* 327 */         worldIn.setBlockToAir(pos);
/*     */       } else {
/* 329 */         worldIn.scheduleUpdate(pos, this, tickRate(worldIn) + worldIn.rand.nextInt(10));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 335 */     if (rand.nextInt(24) == 0) {
/* 336 */       worldIn.playSound((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), "fire.fire", 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
/*     */     }
/*     */     
/* 339 */     if (!World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && !Blocks.fire.canCatchFire((IBlockAccess)worldIn, pos.down())) {
/* 340 */       if (Blocks.fire.canCatchFire((IBlockAccess)worldIn, pos.west())) {
/* 341 */         for (int j = 0; j < 2; j++) {
/* 342 */           double d3 = pos.getX() + rand.nextDouble() * 0.10000000149011612D;
/* 343 */           double d8 = pos.getY() + rand.nextDouble();
/* 344 */           double d13 = pos.getZ() + rand.nextDouble();
/* 345 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d3, d8, d13, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */       
/* 349 */       if (Blocks.fire.canCatchFire((IBlockAccess)worldIn, pos.east())) {
/* 350 */         for (int k = 0; k < 2; k++) {
/* 351 */           double d4 = (pos.getX() + 1) - rand.nextDouble() * 0.10000000149011612D;
/* 352 */           double d9 = pos.getY() + rand.nextDouble();
/* 353 */           double d14 = pos.getZ() + rand.nextDouble();
/* 354 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d4, d9, d14, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */       
/* 358 */       if (Blocks.fire.canCatchFire((IBlockAccess)worldIn, pos.north())) {
/* 359 */         for (int l = 0; l < 2; l++) {
/* 360 */           double d5 = pos.getX() + rand.nextDouble();
/* 361 */           double d10 = pos.getY() + rand.nextDouble();
/* 362 */           double d15 = pos.getZ() + rand.nextDouble() * 0.10000000149011612D;
/* 363 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d5, d10, d15, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */       
/* 367 */       if (Blocks.fire.canCatchFire((IBlockAccess)worldIn, pos.south())) {
/* 368 */         for (int i1 = 0; i1 < 2; i1++) {
/* 369 */           double d6 = pos.getX() + rand.nextDouble();
/* 370 */           double d11 = pos.getY() + rand.nextDouble();
/* 371 */           double d16 = (pos.getZ() + 1) - rand.nextDouble() * 0.10000000149011612D;
/* 372 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d6, d11, d16, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */       
/* 376 */       if (Blocks.fire.canCatchFire((IBlockAccess)worldIn, pos.up())) {
/* 377 */         for (int j1 = 0; j1 < 2; j1++) {
/* 378 */           double d7 = pos.getX() + rand.nextDouble();
/* 379 */           double d12 = (pos.getY() + 1) - rand.nextDouble() * 0.10000000149011612D;
/* 380 */           double d17 = pos.getZ() + rand.nextDouble();
/* 381 */           worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d12, d17, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       }
/*     */     } else {
/* 385 */       for (int i = 0; i < 3; i++) {
/* 386 */         double d0 = pos.getX() + rand.nextDouble();
/* 387 */         double d1 = pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
/* 388 */         double d2 = pos.getZ() + rand.nextDouble();
/* 389 */         worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state) {
/* 398 */     return MapColor.tntColor;
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 402 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 409 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 416 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 420 */     return new BlockState(this, new IProperty[] { (IProperty)AGE, (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST, (IProperty)UPPER, (IProperty)FLIP, (IProperty)ALT });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockFire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */