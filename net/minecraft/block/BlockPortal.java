/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemMonsterPlacer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPortal
/*     */   extends BlockBreakable
/*     */ {
/*  27 */   public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class, (Enum[])new EnumFacing.Axis[] { EnumFacing.Axis.X, EnumFacing.Axis.Z });
/*     */   
/*     */   public BlockPortal() {
/*  30 */     super(Material.portal, false);
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AXIS, (Comparable)EnumFacing.Axis.X));
/*  32 */     setTickRandomly(true);
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  36 */     super.updateTick(worldIn, pos, state, rand);
/*     */     
/*  38 */     if (worldIn.provider.isSurfaceWorld() && worldIn.getGameRules().getBoolean("doMobSpawning") && rand.nextInt(2000) < worldIn.getDifficulty().getDifficultyId()) {
/*  39 */       int i = pos.getY();
/*     */       
/*     */       BlockPos blockpos;
/*  42 */       for (blockpos = pos; !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, blockpos) && blockpos.getY() > 0; blockpos = blockpos.down());
/*     */ 
/*     */ 
/*     */       
/*  46 */       if (i > 0 && !worldIn.getBlockState(blockpos.up()).getBlock().isNormalCube()) {
/*  47 */         Entity entity = ItemMonsterPlacer.spawnCreature(worldIn, 57, blockpos.getX() + 0.5D, blockpos.getY() + 1.1D, blockpos.getZ() + 0.5D);
/*     */         
/*  49 */         if (entity != null) {
/*  50 */           entity.timeUntilPortal = entity.getPortalCooldown();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  57 */     return null;
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/*  61 */     EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)worldIn.getBlockState(pos).getValue((IProperty)AXIS);
/*  62 */     float f = 0.125F;
/*  63 */     float f1 = 0.125F;
/*     */     
/*  65 */     if (enumfacing$axis == EnumFacing.Axis.X) {
/*  66 */       f = 0.5F;
/*     */     }
/*     */     
/*  69 */     if (enumfacing$axis == EnumFacing.Axis.Z) {
/*  70 */       f1 = 0.5F;
/*     */     }
/*     */     
/*  73 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
/*     */   }
/*     */   
/*     */   public static int getMetaForAxis(EnumFacing.Axis axis) {
/*  77 */     return (axis == EnumFacing.Axis.X) ? 1 : ((axis == EnumFacing.Axis.Z) ? 2 : 0);
/*     */   }
/*     */   
/*     */   public boolean isFullCube() {
/*  81 */     return false;
/*     */   }
/*     */   
/*     */   public boolean func_176548_d(World worldIn, BlockPos p_176548_2_) {
/*  85 */     Size blockportal$size = new Size(worldIn, p_176548_2_, EnumFacing.Axis.X);
/*     */     
/*  87 */     if (blockportal$size.func_150860_b() && blockportal$size.field_150864_e == 0) {
/*  88 */       blockportal$size.func_150859_c();
/*  89 */       return true;
/*     */     } 
/*  91 */     Size blockportal$size1 = new Size(worldIn, p_176548_2_, EnumFacing.Axis.Z);
/*     */     
/*  93 */     if (blockportal$size1.func_150860_b() && blockportal$size1.field_150864_e == 0) {
/*  94 */       blockportal$size1.func_150859_c();
/*  95 */       return true;
/*     */     } 
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 106 */     EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)state.getValue((IProperty)AXIS);
/*     */     
/* 108 */     if (enumfacing$axis == EnumFacing.Axis.X) {
/* 109 */       Size blockportal$size = new Size(worldIn, pos, EnumFacing.Axis.X);
/*     */       
/* 111 */       if (!blockportal$size.func_150860_b() || blockportal$size.field_150864_e < blockportal$size.field_150868_h * blockportal$size.field_150862_g) {
/* 112 */         worldIn.setBlockState(pos, Blocks.air.getDefaultState());
/*     */       }
/* 114 */     } else if (enumfacing$axis == EnumFacing.Axis.Z) {
/* 115 */       Size blockportal$size1 = new Size(worldIn, pos, EnumFacing.Axis.Z);
/*     */       
/* 117 */       if (!blockportal$size1.func_150860_b() || blockportal$size1.field_150864_e < blockportal$size1.field_150868_h * blockportal$size1.field_150862_g) {
/* 118 */         worldIn.setBlockState(pos, Blocks.air.getDefaultState());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 124 */     EnumFacing.Axis enumfacing$axis = null;
/* 125 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 127 */     if (worldIn.getBlockState(pos).getBlock() == this) {
/* 128 */       enumfacing$axis = (EnumFacing.Axis)iblockstate.getValue((IProperty)AXIS);
/*     */       
/* 130 */       if (enumfacing$axis == null) {
/* 131 */         return false;
/*     */       }
/*     */       
/* 134 */       if (enumfacing$axis == EnumFacing.Axis.Z && side != EnumFacing.EAST && side != EnumFacing.WEST) {
/* 135 */         return false;
/*     */       }
/*     */       
/* 138 */       if (enumfacing$axis == EnumFacing.Axis.X && side != EnumFacing.SOUTH && side != EnumFacing.NORTH) {
/* 139 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 143 */     boolean flag = (worldIn.getBlockState(pos.west()).getBlock() == this && worldIn.getBlockState(pos.west(2)).getBlock() != this);
/* 144 */     boolean flag1 = (worldIn.getBlockState(pos.east()).getBlock() == this && worldIn.getBlockState(pos.east(2)).getBlock() != this);
/* 145 */     boolean flag2 = (worldIn.getBlockState(pos.north()).getBlock() == this && worldIn.getBlockState(pos.north(2)).getBlock() != this);
/* 146 */     boolean flag3 = (worldIn.getBlockState(pos.south()).getBlock() == this && worldIn.getBlockState(pos.south(2)).getBlock() != this);
/* 147 */     boolean flag4 = !(!flag && !flag1 && enumfacing$axis != EnumFacing.Axis.X);
/* 148 */     boolean flag5 = !(!flag2 && !flag3 && enumfacing$axis != EnumFacing.Axis.Z);
/* 149 */     return (flag4 && side == EnumFacing.WEST) ? true : ((flag4 && side == EnumFacing.EAST) ? true : ((flag5 && side == EnumFacing.NORTH) ? true : ((flag5 && side == EnumFacing.SOUTH))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 156 */     return 0;
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 160 */     return EnumWorldBlockLayer.TRANSLUCENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 167 */     if (entityIn.ridingEntity == null && entityIn.riddenByEntity == null) {
/* 168 */       entityIn.setPortal(pos);
/*     */     }
/*     */   }
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 173 */     if (rand.nextInt(100) == 0) {
/* 174 */       worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "portal.portal", 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
/*     */     }
/*     */     
/* 177 */     for (int i = 0; i < 4; i++) {
/* 178 */       double d0 = (pos.getX() + rand.nextFloat());
/* 179 */       double d1 = (pos.getY() + rand.nextFloat());
/* 180 */       double d2 = (pos.getZ() + rand.nextFloat());
/* 181 */       double d3 = (rand.nextFloat() - 0.5D) * 0.5D;
/* 182 */       double d4 = (rand.nextFloat() - 0.5D) * 0.5D;
/* 183 */       double d5 = (rand.nextFloat() - 0.5D) * 0.5D;
/* 184 */       int j = rand.nextInt(2) * 2 - 1;
/*     */       
/* 186 */       if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this) {
/* 187 */         d0 = pos.getX() + 0.5D + 0.25D * j;
/* 188 */         d3 = (rand.nextFloat() * 2.0F * j);
/*     */       } else {
/* 190 */         d2 = pos.getZ() + 0.5D + 0.25D * j;
/* 191 */         d5 = (rand.nextFloat() * 2.0F * j);
/*     */       } 
/*     */       
/* 194 */       worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 199 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 206 */     return getDefaultState().withProperty((IProperty)AXIS, ((meta & 0x3) == 2) ? (Comparable)EnumFacing.Axis.Z : (Comparable)EnumFacing.Axis.X);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 213 */     return getMetaForAxis((EnumFacing.Axis)state.getValue((IProperty)AXIS));
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 217 */     return new BlockState(this, new IProperty[] { (IProperty)AXIS });
/*     */   }
/*     */   
/*     */   public BlockPattern.PatternHelper func_181089_f(World p_181089_1_, BlockPos p_181089_2_) {
/* 221 */     EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Z;
/* 222 */     Size blockportal$size = new Size(p_181089_1_, p_181089_2_, EnumFacing.Axis.X);
/* 223 */     LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.func_181627_a(p_181089_1_, true);
/*     */     
/* 225 */     if (!blockportal$size.func_150860_b()) {
/* 226 */       enumfacing$axis = EnumFacing.Axis.X;
/* 227 */       blockportal$size = new Size(p_181089_1_, p_181089_2_, EnumFacing.Axis.Z);
/*     */     } 
/*     */     
/* 230 */     if (!blockportal$size.func_150860_b()) {
/* 231 */       return new BlockPattern.PatternHelper(p_181089_2_, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
/*     */     }
/* 233 */     int[] aint = new int[(EnumFacing.AxisDirection.values()).length];
/* 234 */     EnumFacing enumfacing = blockportal$size.field_150866_c.rotateYCCW();
/* 235 */     BlockPos blockpos = blockportal$size.field_150861_f.up(blockportal$size.func_181100_a() - 1); byte b; int i;
/*     */     EnumFacing.AxisDirection[] arrayOfAxisDirection1;
/* 237 */     for (i = (arrayOfAxisDirection1 = EnumFacing.AxisDirection.values()).length, b = 0; b < i; ) { EnumFacing.AxisDirection enumfacing$axisdirection = arrayOfAxisDirection1[b];
/* 238 */       BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper((enumfacing.getAxisDirection() == enumfacing$axisdirection) ? blockpos : blockpos.offset(blockportal$size.field_150866_c, blockportal$size.func_181101_b() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.func_181101_b(), blockportal$size.func_181100_a(), 1);
/*     */       
/* 240 */       for (int k = 0; k < blockportal$size.func_181101_b(); k++) {
/* 241 */         for (int m = 0; m < blockportal$size.func_181100_a(); m++) {
/* 242 */           BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(k, m, 1);
/*     */           
/* 244 */           if (blockworldstate.getBlockState() != null && blockworldstate.getBlockState().getBlock().getMaterial() != Material.air) {
/* 245 */             aint[enumfacing$axisdirection.ordinal()] = aint[enumfacing$axisdirection.ordinal()] + 1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */     
/* 251 */     EnumFacing.AxisDirection enumfacing$axisdirection1 = EnumFacing.AxisDirection.POSITIVE;
/*     */     EnumFacing.AxisDirection[] arrayOfAxisDirection2;
/* 253 */     for (int j = (arrayOfAxisDirection2 = EnumFacing.AxisDirection.values()).length; i < j; ) { EnumFacing.AxisDirection enumfacing$axisdirection2 = arrayOfAxisDirection2[i];
/* 254 */       if (aint[enumfacing$axisdirection2.ordinal()] < aint[enumfacing$axisdirection1.ordinal()]) {
/* 255 */         enumfacing$axisdirection1 = enumfacing$axisdirection2;
/*     */       }
/*     */       i++; }
/*     */     
/* 259 */     return new BlockPattern.PatternHelper((enumfacing.getAxisDirection() == enumfacing$axisdirection1) ? blockpos : blockpos.offset(blockportal$size.field_150866_c, blockportal$size.func_181101_b() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection1, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.func_181101_b(), blockportal$size.func_181100_a(), 1);
/*     */   }
/*     */   
/*     */   public static class Size
/*     */   {
/*     */     private final World world;
/*     */     private final EnumFacing.Axis axis;
/*     */     private final EnumFacing field_150866_c;
/*     */     private final EnumFacing field_150863_d;
/* 268 */     private int field_150864_e = 0;
/*     */     private BlockPos field_150861_f;
/*     */     private int field_150862_g;
/*     */     private int field_150868_h;
/*     */     
/*     */     public Size(World worldIn, BlockPos p_i45694_2_, EnumFacing.Axis p_i45694_3_) {
/* 274 */       this.world = worldIn;
/* 275 */       this.axis = p_i45694_3_;
/*     */       
/* 277 */       if (p_i45694_3_ == EnumFacing.Axis.X) {
/* 278 */         this.field_150863_d = EnumFacing.EAST;
/* 279 */         this.field_150866_c = EnumFacing.WEST;
/*     */       } else {
/* 281 */         this.field_150863_d = EnumFacing.NORTH;
/* 282 */         this.field_150866_c = EnumFacing.SOUTH;
/*     */       } 
/*     */       
/* 285 */       for (BlockPos blockpos = p_i45694_2_; p_i45694_2_.getY() > blockpos.getY() - 21 && p_i45694_2_.getY() > 0 && func_150857_a(worldIn.getBlockState(p_i45694_2_.down()).getBlock()); p_i45694_2_ = p_i45694_2_.down());
/*     */ 
/*     */ 
/*     */       
/* 289 */       int i = func_180120_a(p_i45694_2_, this.field_150863_d) - 1;
/*     */       
/* 291 */       if (i >= 0) {
/* 292 */         this.field_150861_f = p_i45694_2_.offset(this.field_150863_d, i);
/* 293 */         this.field_150868_h = func_180120_a(this.field_150861_f, this.field_150866_c);
/*     */         
/* 295 */         if (this.field_150868_h < 2 || this.field_150868_h > 21) {
/* 296 */           this.field_150861_f = null;
/* 297 */           this.field_150868_h = 0;
/*     */         } 
/*     */       } 
/*     */       
/* 301 */       if (this.field_150861_f != null) {
/* 302 */         this.field_150862_g = func_150858_a();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected int func_180120_a(BlockPos p_180120_1_, EnumFacing p_180120_2_) {
/*     */       int i;
/* 309 */       for (i = 0; i < 22; i++) {
/* 310 */         BlockPos blockpos = p_180120_1_.offset(p_180120_2_, i);
/*     */         
/* 312 */         if (!func_150857_a(this.world.getBlockState(blockpos).getBlock()) || this.world.getBlockState(blockpos.down()).getBlock() != Blocks.obsidian) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 317 */       Block block = this.world.getBlockState(p_180120_1_.offset(p_180120_2_, i)).getBlock();
/* 318 */       return (block == Blocks.obsidian) ? i : 0;
/*     */     }
/*     */     
/*     */     public int func_181100_a() {
/* 322 */       return this.field_150862_g;
/*     */     }
/*     */     
/*     */     public int func_181101_b() {
/* 326 */       return this.field_150868_h;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected int func_150858_a() {
/* 332 */       label38: for (this.field_150862_g = 0; this.field_150862_g < 21; this.field_150862_g++) {
/* 333 */         for (int i = 0; i < this.field_150868_h; i++) {
/* 334 */           BlockPos blockpos = this.field_150861_f.offset(this.field_150866_c, i).up(this.field_150862_g);
/* 335 */           Block block = this.world.getBlockState(blockpos).getBlock();
/*     */           
/* 337 */           if (!func_150857_a(block)) {
/*     */             break label38;
/*     */           }
/*     */           
/* 341 */           if (block == Blocks.portal) {
/* 342 */             this.field_150864_e++;
/*     */           }
/*     */           
/* 345 */           if (i == 0) {
/* 346 */             block = this.world.getBlockState(blockpos.offset(this.field_150863_d)).getBlock();
/*     */             
/* 348 */             if (block != Blocks.obsidian) {
/*     */               break label38;
/*     */             }
/* 351 */           } else if (i == this.field_150868_h - 1) {
/* 352 */             block = this.world.getBlockState(blockpos.offset(this.field_150866_c)).getBlock();
/*     */             
/* 354 */             if (block != Blocks.obsidian) {
/*     */               break label38;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 361 */       for (int j = 0; j < this.field_150868_h; j++) {
/* 362 */         if (this.world.getBlockState(this.field_150861_f.offset(this.field_150866_c, j).up(this.field_150862_g)).getBlock() != Blocks.obsidian) {
/* 363 */           this.field_150862_g = 0;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 368 */       if (this.field_150862_g <= 21 && this.field_150862_g >= 3) {
/* 369 */         return this.field_150862_g;
/*     */       }
/* 371 */       this.field_150861_f = null;
/* 372 */       this.field_150868_h = 0;
/* 373 */       this.field_150862_g = 0;
/* 374 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean func_150857_a(Block p_150857_1_) {
/* 379 */       return !(p_150857_1_.blockMaterial != Material.air && p_150857_1_ != Blocks.fire && p_150857_1_ != Blocks.portal);
/*     */     }
/*     */     
/*     */     public boolean func_150860_b() {
/* 383 */       return (this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3 && this.field_150862_g <= 21);
/*     */     }
/*     */     
/*     */     public void func_150859_c() {
/* 387 */       for (int i = 0; i < this.field_150868_h; i++) {
/* 388 */         BlockPos blockpos = this.field_150861_f.offset(this.field_150866_c, i);
/*     */         
/* 390 */         for (int j = 0; j < this.field_150862_g; j++)
/* 391 */           this.world.setBlockState(blockpos.up(j), Blocks.portal.getDefaultState().withProperty((IProperty)BlockPortal.AXIS, (Comparable)this.axis), 2); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockPortal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */