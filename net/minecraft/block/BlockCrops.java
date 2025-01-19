/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCrops
/*     */   extends BlockBush
/*     */   implements IGrowable {
/*  19 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
/*     */   
/*     */   protected BlockCrops() {
/*  22 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  23 */     setTickRandomly(true);
/*  24 */     float f = 0.5F;
/*  25 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*  26 */     setCreativeTab(null);
/*  27 */     setHardness(0.0F);
/*  28 */     setStepSound(soundTypeGrass);
/*  29 */     disableStats();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canPlaceBlockOn(Block ground) {
/*  36 */     return (ground == Blocks.farmland);
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  40 */     super.updateTick(worldIn, pos, state, rand);
/*     */     
/*  42 */     if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*  43 */       int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */       
/*  45 */       if (i < 7) {
/*  46 */         float f = getGrowthChance(this, worldIn, pos);
/*     */         
/*  48 */         if (rand.nextInt((int)(25.0F / f) + 1) == 0) {
/*  49 */           worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(i + 1)), 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void grow(World worldIn, BlockPos pos, IBlockState state) {
/*  56 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue() + MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
/*     */     
/*  58 */     if (i > 7) {
/*  59 */       i = 7;
/*     */     }
/*     */     
/*  62 */     worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(i)), 2);
/*     */   }
/*     */   
/*     */   protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos) {
/*  66 */     float f = 1.0F;
/*  67 */     BlockPos blockpos = pos.down();
/*     */     
/*  69 */     for (int i = -1; i <= 1; i++) {
/*  70 */       for (int j = -1; j <= 1; j++) {
/*  71 */         float f1 = 0.0F;
/*  72 */         IBlockState iblockstate = worldIn.getBlockState(blockpos.add(i, 0, j));
/*     */         
/*  74 */         if (iblockstate.getBlock() == Blocks.farmland) {
/*  75 */           f1 = 1.0F;
/*     */           
/*  77 */           if (((Integer)iblockstate.getValue((IProperty)BlockFarmland.MOISTURE)).intValue() > 0) {
/*  78 */             f1 = 3.0F;
/*     */           }
/*     */         } 
/*     */         
/*  82 */         if (i != 0 || j != 0) {
/*  83 */           f1 /= 4.0F;
/*     */         }
/*     */         
/*  86 */         f += f1;
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     BlockPos blockpos1 = pos.north();
/*  91 */     BlockPos blockpos2 = pos.south();
/*  92 */     BlockPos blockpos3 = pos.west();
/*  93 */     BlockPos blockpos4 = pos.east();
/*  94 */     boolean flag = !(blockIn != worldIn.getBlockState(blockpos3).getBlock() && blockIn != worldIn.getBlockState(blockpos4).getBlock());
/*  95 */     boolean flag1 = !(blockIn != worldIn.getBlockState(blockpos1).getBlock() && blockIn != worldIn.getBlockState(blockpos2).getBlock());
/*     */     
/*  97 */     if (flag && flag1) {
/*  98 */       f /= 2.0F;
/*     */     } else {
/* 100 */       boolean flag2 = !(blockIn != worldIn.getBlockState(blockpos3.north()).getBlock() && blockIn != worldIn.getBlockState(blockpos4.north()).getBlock() && blockIn != worldIn.getBlockState(blockpos4.south()).getBlock() && blockIn != worldIn.getBlockState(blockpos3.south()).getBlock());
/*     */       
/* 102 */       if (flag2) {
/* 103 */         f /= 2.0F;
/*     */       }
/*     */     } 
/*     */     
/* 107 */     return f;
/*     */   }
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/* 111 */     return ((worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && canPlaceBlockOn(worldIn.getBlockState(pos.down()).getBlock()));
/*     */   }
/*     */   
/*     */   protected Item getSeed() {
/* 115 */     return Items.wheat_seeds;
/*     */   }
/*     */   
/*     */   protected Item getCrop() {
/* 119 */     return Items.wheat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 126 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
/*     */     
/* 128 */     if (!worldIn.isRemote) {
/* 129 */       int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */       
/* 131 */       if (i >= 7) {
/* 132 */         int j = 3 + fortune;
/*     */         
/* 134 */         for (int k = 0; k < j; k++) {
/* 135 */           if (worldIn.rand.nextInt(15) <= i) {
/* 136 */             spawnAsEntity(worldIn, pos, new ItemStack(getSeed(), 1, 0));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 147 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() == 7) ? getCrop() : getSeed();
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 151 */     return getSeed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 158 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() < 7);
/*     */   }
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 162 */     return true;
/*     */   }
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 166 */     grow(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 173 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 180 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 184 */     return new BlockState(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockCrops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */