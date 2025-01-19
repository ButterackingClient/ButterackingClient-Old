/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeColorHelper;
/*     */ 
/*     */ public class BlockGrass
/*     */   extends Block implements IGrowable {
/*  21 */   public static final PropertyBool SNOWY = PropertyBool.create("snowy");
/*     */   
/*     */   protected BlockGrass() {
/*  24 */     super(Material.grass);
/*  25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)SNOWY, Boolean.valueOf(false)));
/*  26 */     setTickRandomly(true);
/*  27 */     setCreativeTab(CreativeTabs.tabBlock);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  35 */     Block block = worldIn.getBlockState(pos.up()).getBlock();
/*  36 */     return state.withProperty((IProperty)SNOWY, Boolean.valueOf(!(block != Blocks.snow && block != Blocks.snow_layer)));
/*     */   }
/*     */   
/*     */   public int getBlockColor() {
/*  40 */     return ColorizerGrass.getGrassColor(0.5D, 1.0D);
/*     */   }
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/*  44 */     return getBlockColor();
/*     */   }
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  48 */     return BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  52 */     if (!worldIn.isRemote) {
/*  53 */       if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getBlock().getLightOpacity() > 2) {
/*  54 */         worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
/*     */       }
/*  56 */       else if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*  57 */         for (int i = 0; i < 4; i++) {
/*  58 */           BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
/*  59 */           Block block = worldIn.getBlockState(blockpos.up()).getBlock();
/*  60 */           IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */           
/*  62 */           if (iblockstate.getBlock() == Blocks.dirt && iblockstate.getValue((IProperty)BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && block.getLightOpacity() <= 2) {
/*  63 */             worldIn.setBlockState(blockpos, Blocks.grass.getDefaultState());
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
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  75 */     return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/*  82 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/*  86 */     return true;
/*     */   }
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/*  90 */     BlockPos blockpos = pos.up();
/*     */     
/*  92 */     for (int i = 0; i < 128; i++) {
/*  93 */       BlockPos blockpos1 = blockpos;
/*  94 */       int j = 0;
/*     */       
/*     */       while (true) {
/*  97 */         if (j >= i / 16) {
/*  98 */           if ((worldIn.getBlockState(blockpos1).getBlock()).blockMaterial == Material.air) {
/*  99 */             if (rand.nextInt(8) == 0) {
/* 100 */               BlockFlower.EnumFlowerType blockflower$enumflowertype = worldIn.getBiomeGenForCoords(blockpos1).pickRandomFlower(rand, blockpos1);
/* 101 */               BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();
/* 102 */               IBlockState iblockstate = blockflower.getDefaultState().withProperty(blockflower.getTypeProperty(), blockflower$enumflowertype);
/*     */               
/* 104 */               if (blockflower.canBlockStay(worldIn, blockpos1, iblockstate))
/* 105 */                 worldIn.setBlockState(blockpos1, iblockstate, 3); 
/*     */               break;
/*     */             } 
/* 108 */             IBlockState iblockstate1 = Blocks.tallgrass.getDefaultState().withProperty((IProperty)BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);
/*     */             
/* 110 */             if (Blocks.tallgrass.canBlockStay(worldIn, blockpos1, iblockstate1)) {
/* 111 */               worldIn.setBlockState(blockpos1, iblockstate1, 3);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 119 */         blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
/*     */         
/* 121 */         if (worldIn.getBlockState(blockpos1.down()).getBlock() != Blocks.grass || worldIn.getBlockState(blockpos1).getBlock().isNormalCube()) {
/*     */           break;
/*     */         }
/*     */         
/* 125 */         j++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 131 */     return EnumWorldBlockLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 138 */     return 0;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 142 */     return new BlockState(this, new IProperty[] { (IProperty)SNOWY });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */