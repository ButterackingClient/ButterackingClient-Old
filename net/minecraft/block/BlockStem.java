/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockStem
/*     */   extends BlockBush
/*     */   implements IGrowable
/*     */ {
/*  25 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
/*  26 */   public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>() {
/*     */         public boolean apply(EnumFacing p_apply_1_) {
/*  28 */           return (p_apply_1_ != EnumFacing.DOWN);
/*     */         }
/*     */       });
/*     */   private final Block crop;
/*     */   
/*     */   protected BlockStem(Block crop) {
/*  34 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)).withProperty((IProperty)FACING, (Comparable)EnumFacing.UP));
/*  35 */     this.crop = crop;
/*  36 */     setTickRandomly(true);
/*  37 */     float f = 0.125F;
/*  38 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*  39 */     setCreativeTab(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  47 */     state = state.withProperty((IProperty)FACING, (Comparable)EnumFacing.UP);
/*     */     
/*  49 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*  50 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this.crop) {
/*  51 */         state = state.withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  56 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canPlaceBlockOn(Block ground) {
/*  63 */     return (ground == Blocks.farmland);
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  67 */     super.updateTick(worldIn, pos, state, rand);
/*     */     
/*  69 */     if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*  70 */       float f = BlockCrops.getGrowthChance(this, worldIn, pos);
/*     */       
/*  72 */       if (rand.nextInt((int)(25.0F / f) + 1) == 0) {
/*  73 */         int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/*  75 */         if (i < 7) {
/*  76 */           state = state.withProperty((IProperty)AGE, Integer.valueOf(i + 1));
/*  77 */           worldIn.setBlockState(pos, state, 2);
/*     */         } else {
/*  79 */           for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*  80 */             if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this.crop) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */           
/*  85 */           pos = pos.offset(EnumFacing.Plane.HORIZONTAL.random(rand));
/*  86 */           Block block = worldIn.getBlockState(pos.down()).getBlock();
/*     */           
/*  88 */           if ((worldIn.getBlockState(pos).getBlock()).blockMaterial == Material.air && (block == Blocks.farmland || block == Blocks.dirt || block == Blocks.grass)) {
/*  89 */             worldIn.setBlockState(pos, this.crop.getDefaultState());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void growStem(World worldIn, BlockPos pos, IBlockState state) {
/*  97 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue() + MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
/*  98 */     worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(Math.min(7, i))), 2);
/*     */   }
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/* 102 */     if (state.getBlock() != this) {
/* 103 */       return super.getRenderColor(state);
/*     */     }
/* 105 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/* 106 */     int j = i * 32;
/* 107 */     int k = 255 - i * 8;
/* 108 */     int l = i * 4;
/* 109 */     return j << 16 | k << 8 | l;
/*     */   }
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/* 114 */     return getRenderColor(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlockBoundsForItemRender() {
/* 121 */     float f = 0.125F;
/* 122 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*     */   }
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 126 */     this.maxY = ((((Integer)worldIn.getBlockState(pos).getValue((IProperty)AGE)).intValue() * 2 + 2) / 16.0F);
/* 127 */     float f = 0.125F;
/* 128 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, (float)this.maxY, 0.5F + f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 135 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     
/* 137 */     if (!worldIn.isRemote) {
/* 138 */       Item item = getSeedItem();
/*     */       
/* 140 */       if (item != null) {
/* 141 */         int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/* 143 */         for (int j = 0; j < 3; j++) {
/* 144 */           if (worldIn.rand.nextInt(15) <= i) {
/* 145 */             spawnAsEntity(worldIn, pos, new ItemStack(item));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Item getSeedItem() {
/* 153 */     return (this.crop == Blocks.pumpkin) ? Items.pumpkin_seeds : ((this.crop == Blocks.melon_block) ? Items.melon_seeds : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 160 */     return null;
/*     */   }
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 164 */     Item item = getSeedItem();
/* 165 */     return (item != null) ? item : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 172 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() != 7);
/*     */   }
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 176 */     return true;
/*     */   }
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 180 */     growStem(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 187 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 194 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 198 */     return new BlockState(this, new IProperty[] { (IProperty)AGE, (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockStem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */