/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeColorHelper;
/*     */ 
/*     */ public abstract class BlockLiquid
/*     */   extends Block {
/*  25 */   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
/*     */   
/*     */   protected BlockLiquid(Material materialIn) {
/*  28 */     super(materialIn);
/*  29 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)LEVEL, Integer.valueOf(0)));
/*  30 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  31 */     setTickRandomly(true);
/*     */   }
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  35 */     return (this.blockMaterial != Material.lava);
/*     */   }
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  39 */     return (this.blockMaterial == Material.water) ? BiomeColorHelper.getWaterColorAtPos(worldIn, pos) : 16777215;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getLiquidHeightPercent(int meta) {
/*  46 */     if (meta >= 8) {
/*  47 */       meta = 0;
/*     */     }
/*     */     
/*  50 */     return (meta + 1) / 9.0F;
/*     */   }
/*     */   
/*     */   protected int getLevel(IBlockAccess worldIn, BlockPos pos) {
/*  54 */     return (worldIn.getBlockState(pos).getBlock().getMaterial() == this.blockMaterial) ? ((Integer)worldIn.getBlockState(pos).getValue((IProperty)LEVEL)).intValue() : -1;
/*     */   }
/*     */   
/*     */   protected int getEffectiveFlowDecay(IBlockAccess worldIn, BlockPos pos) {
/*  58 */     int i = getLevel(worldIn, pos);
/*  59 */     return (i >= 8) ? 0 : i;
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/*  70 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/*  74 */     return (hitIfLiquid && ((Integer)state.getValue((IProperty)LEVEL)).intValue() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  81 */     Material material = worldIn.getBlockState(pos).getBlock().getMaterial();
/*  82 */     return (material == this.blockMaterial) ? false : ((side == EnumFacing.UP) ? true : ((material == Material.ice) ? false : super.isBlockSolid(worldIn, pos, side)));
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  86 */     return (worldIn.getBlockState(pos).getBlock().getMaterial() == this.blockMaterial) ? false : ((side == EnumFacing.UP) ? true : super.shouldSideBeRendered(worldIn, pos, side));
/*     */   }
/*     */   
/*     */   public boolean shouldRenderSides(IBlockAccess blockAccess, BlockPos pos) {
/*  90 */     for (int i = -1; i <= 1; i++) {
/*  91 */       for (int j = -1; j <= 1; j++) {
/*  92 */         IBlockState iblockstate = blockAccess.getBlockState(pos.add(i, 0, j));
/*  93 */         Block block = iblockstate.getBlock();
/*  94 */         Material material = block.getMaterial();
/*     */         
/*  96 */         if (material != this.blockMaterial && !block.isFullBlock()) {
/*  97 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/* 106 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRenderType() {
/* 113 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 120 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 127 */     return 0;
/*     */   }
/*     */   
/*     */   protected Vec3 getFlowVector(IBlockAccess worldIn, BlockPos pos) {
/* 131 */     Vec3 vec3 = new Vec3(0.0D, 0.0D, 0.0D);
/* 132 */     int i = getEffectiveFlowDecay(worldIn, pos);
/*     */     
/* 134 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/* 135 */       BlockPos blockpos = pos.offset(enumfacing);
/* 136 */       int j = getEffectiveFlowDecay(worldIn, blockpos);
/*     */       
/* 138 */       if (j < 0) {
/* 139 */         if (!worldIn.getBlockState(blockpos).getBlock().getMaterial().blocksMovement()) {
/* 140 */           j = getEffectiveFlowDecay(worldIn, blockpos.down());
/*     */           
/* 142 */           if (j >= 0) {
/* 143 */             int k = j - i - 8;
/* 144 */             vec3 = vec3.addVector(((blockpos.getX() - pos.getX()) * k), ((blockpos.getY() - pos.getY()) * k), ((blockpos.getZ() - pos.getZ()) * k));
/*     */           } 
/*     */         }  continue;
/* 147 */       }  if (j >= 0) {
/* 148 */         int l = j - i;
/* 149 */         vec3 = vec3.addVector(((blockpos.getX() - pos.getX()) * l), ((blockpos.getY() - pos.getY()) * l), ((blockpos.getZ() - pos.getZ()) * l));
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     if (((Integer)worldIn.getBlockState(pos).getValue((IProperty)LEVEL)).intValue() >= 8) {
/* 154 */       for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
/* 155 */         BlockPos blockpos1 = pos.offset(enumfacing1);
/*     */         
/* 157 */         if (isBlockSolid(worldIn, blockpos1, enumfacing1) || isBlockSolid(worldIn, blockpos1.up(), enumfacing1)) {
/* 158 */           vec3 = vec3.normalize().addVector(0.0D, -6.0D, 0.0D);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 164 */     return vec3.normalize();
/*     */   }
/*     */   
/*     */   public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion) {
/* 168 */     return motion.add(getFlowVector((IBlockAccess)worldIn, pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/* 175 */     return (this.blockMaterial == Material.water) ? 5 : ((this.blockMaterial == Material.lava) ? (worldIn.provider.getHasNoSky() ? 10 : 30) : 0);
/*     */   }
/*     */   
/*     */   public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos) {
/* 179 */     int i = worldIn.getCombinedLight(pos, 0);
/* 180 */     int j = worldIn.getCombinedLight(pos.up(), 0);
/* 181 */     int k = i & 0xFF;
/* 182 */     int l = j & 0xFF;
/* 183 */     int i1 = i >> 16 & 0xFF;
/* 184 */     int j1 = j >> 16 & 0xFF;
/* 185 */     return ((k > l) ? k : l) | ((i1 > j1) ? i1 : j1) << 16;
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 189 */     return (this.blockMaterial == Material.water) ? EnumWorldBlockLayer.TRANSLUCENT : EnumWorldBlockLayer.SOLID;
/*     */   }
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 193 */     double d0 = pos.getX();
/* 194 */     double d1 = pos.getY();
/* 195 */     double d2 = pos.getZ();
/*     */     
/* 197 */     if (this.blockMaterial == Material.water) {
/* 198 */       int i = ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*     */       
/* 200 */       if (i > 0 && i < 8) {
/* 201 */         if (rand.nextInt(64) == 0) {
/* 202 */           worldIn.playSound(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, "liquid.water", rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() * 1.0F + 0.5F, false);
/*     */         }
/* 204 */       } else if (rand.nextInt(10) == 0) {
/* 205 */         worldIn.spawnParticle(EnumParticleTypes.SUSPENDED, d0 + rand.nextFloat(), d1 + rand.nextFloat(), d2 + rand.nextFloat(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     if (this.blockMaterial == Material.lava && worldIn.getBlockState(pos.up()).getBlock().getMaterial() == Material.air && !worldIn.getBlockState(pos.up()).getBlock().isOpaqueCube()) {
/* 210 */       if (rand.nextInt(100) == 0) {
/* 211 */         double d8 = d0 + rand.nextFloat();
/* 212 */         double d4 = d1 + this.maxY;
/* 213 */         double d6 = d2 + rand.nextFloat();
/* 214 */         worldIn.spawnParticle(EnumParticleTypes.LAVA, d8, d4, d6, 0.0D, 0.0D, 0.0D, new int[0]);
/* 215 */         worldIn.playSound(d8, d4, d6, "liquid.lavapop", 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
/*     */       } 
/*     */       
/* 218 */       if (rand.nextInt(200) == 0) {
/* 219 */         worldIn.playSound(d0, d1, d2, "liquid.lava", 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
/*     */       }
/*     */     } 
/*     */     
/* 223 */     if (rand.nextInt(10) == 0 && World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down())) {
/* 224 */       Material material = worldIn.getBlockState(pos.down(2)).getBlock().getMaterial();
/*     */       
/* 226 */       if (!material.blocksMovement() && !material.isLiquid()) {
/* 227 */         double d3 = d0 + rand.nextFloat();
/* 228 */         double d5 = d1 - 1.05D;
/* 229 */         double d7 = d2 + rand.nextFloat();
/*     */         
/* 231 */         if (this.blockMaterial == Material.water) {
/* 232 */           worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } else {
/* 234 */           worldIn.spawnParticle(EnumParticleTypes.DRIP_LAVA, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double getFlowDirection(IBlockAccess worldIn, BlockPos pos, Material materialIn) {
/* 241 */     Vec3 vec3 = getFlowingBlock(materialIn).getFlowVector(worldIn, pos);
/* 242 */     return (vec3.xCoord == 0.0D && vec3.zCoord == 0.0D) ? -1000.0D : (MathHelper.atan2(vec3.zCoord, vec3.xCoord) - 1.5707963267948966D);
/*     */   }
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 246 */     checkForMixing(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 253 */     checkForMixing(worldIn, pos, state);
/*     */   }
/*     */   
/*     */   public boolean checkForMixing(World worldIn, BlockPos pos, IBlockState state) {
/* 257 */     if (this.blockMaterial == Material.lava) {
/* 258 */       boolean flag = false; byte b; int i;
/*     */       EnumFacing[] arrayOfEnumFacing;
/* 260 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/* 261 */         if (enumfacing != EnumFacing.DOWN && worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getMaterial() == Material.water) {
/* 262 */           flag = true;
/*     */           break;
/*     */         } 
/*     */         b++; }
/*     */       
/* 267 */       if (flag) {
/* 268 */         Integer integer = (Integer)state.getValue((IProperty)LEVEL);
/*     */         
/* 270 */         if (integer.intValue() == 0) {
/* 271 */           worldIn.setBlockState(pos, Blocks.obsidian.getDefaultState());
/* 272 */           triggerMixEffects(worldIn, pos);
/* 273 */           return true;
/*     */         } 
/*     */         
/* 276 */         if (integer.intValue() <= 4) {
/* 277 */           worldIn.setBlockState(pos, Blocks.cobblestone.getDefaultState());
/* 278 */           triggerMixEffects(worldIn, pos);
/* 279 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 284 */     return false;
/*     */   }
/*     */   
/*     */   protected void triggerMixEffects(World worldIn, BlockPos pos) {
/* 288 */     double d0 = pos.getX();
/* 289 */     double d1 = pos.getY();
/* 290 */     double d2 = pos.getZ();
/* 291 */     worldIn.playSoundEffect(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, "random.fizz", 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
/*     */     
/* 293 */     for (int i = 0; i < 8; i++) {
/* 294 */       worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0 + Math.random(), d1 + 1.2D, d2 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 302 */     return getDefaultState().withProperty((IProperty)LEVEL, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 309 */     return ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 313 */     return new BlockState(this, new IProperty[] { (IProperty)LEVEL });
/*     */   }
/*     */   
/*     */   public static BlockDynamicLiquid getFlowingBlock(Material materialIn) {
/* 317 */     if (materialIn == Material.water)
/* 318 */       return Blocks.flowing_water; 
/* 319 */     if (materialIn == Material.lava) {
/* 320 */       return Blocks.flowing_lava;
/*     */     }
/* 322 */     throw new IllegalArgumentException("Invalid material");
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockStaticLiquid getStaticBlock(Material materialIn) {
/* 327 */     if (materialIn == Material.water)
/* 328 */       return Blocks.water; 
/* 329 */     if (materialIn == Material.lava) {
/* 330 */       return Blocks.lava;
/*     */     }
/* 332 */     throw new IllegalArgumentException("Invalid material");
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */