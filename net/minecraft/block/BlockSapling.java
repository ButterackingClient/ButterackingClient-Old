/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.StatCollector;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenCanopyTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenForest;
/*     */ import net.minecraft.world.gen.feature.WorldGenMegaJungle;
/*     */ import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenSavannaTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/*     */ import net.minecraft.world.gen.feature.WorldGenTrees;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public class BlockSapling
/*     */   extends BlockBush implements IGrowable {
/*  29 */   public static final PropertyEnum<BlockPlanks.EnumType> TYPE = PropertyEnum.create("type", BlockPlanks.EnumType.class);
/*  30 */   public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
/*     */   
/*     */   protected BlockSapling() {
/*  33 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)TYPE, BlockPlanks.EnumType.OAK).withProperty((IProperty)STAGE, Integer.valueOf(0)));
/*  34 */     float f = 0.4F;
/*  35 */     setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
/*  36 */     setCreativeTab(CreativeTabs.tabDecorations);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  43 */     return StatCollector.translateToLocal(String.valueOf(getUnlocalizedName()) + "." + BlockPlanks.EnumType.OAK.getUnlocalizedName() + ".name");
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  47 */     if (!worldIn.isRemote) {
/*  48 */       super.updateTick(worldIn, pos, state, rand);
/*     */       
/*  50 */       if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
/*  51 */         grow(worldIn, pos, state, rand);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void grow(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  57 */     if (((Integer)state.getValue((IProperty)STAGE)).intValue() == 0) {
/*  58 */       worldIn.setBlockState(pos, state.cycleProperty((IProperty)STAGE), 4);
/*     */     } else {
/*  60 */       generateTree(worldIn, pos, state, rand);
/*     */     }  } public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand) { WorldGenForest worldGenForest;
/*     */     WorldGenSavannaTree worldGenSavannaTree;
/*     */     WorldGenCanopyTree worldGenCanopyTree;
/*     */     IBlockState iblockstate, iblockstate1;
/*  65 */     WorldGenerator worldgenerator = (rand.nextInt(10) == 0) ? (WorldGenerator)new WorldGenBigTree(true) : (WorldGenerator)new WorldGenTrees(true);
/*  66 */     int i = 0;
/*  67 */     int j = 0;
/*  68 */     boolean flag = false;
/*     */     
/*  70 */     switch ((BlockPlanks.EnumType)state.getValue((IProperty)TYPE)) {
/*     */       
/*     */       case SPRUCE:
/*  73 */         label68: for (i = 0; i >= -1; i--) {
/*  74 */           for (j = 0; j >= -1; j--) {
/*  75 */             if (func_181624_a(worldIn, pos, i, j, BlockPlanks.EnumType.SPRUCE)) {
/*  76 */               WorldGenMegaPineTree worldGenMegaPineTree = new WorldGenMegaPineTree(false, rand.nextBoolean());
/*  77 */               flag = true;
/*     */               
/*     */               break label68;
/*     */             } 
/*     */           } 
/*     */         } 
/*  83 */         if (!flag) {
/*  84 */           j = 0;
/*  85 */           i = 0;
/*  86 */           WorldGenTaiga2 worldGenTaiga2 = new WorldGenTaiga2(true);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case BIRCH:
/*  92 */         worldGenForest = new WorldGenForest(true, false);
/*     */         break;
/*     */       
/*     */       case JUNGLE:
/*  96 */         iblockstate = Blocks.log.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
/*  97 */         iblockstate1 = Blocks.leaves.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */         
/* 100 */         label69: for (i = 0; i >= -1; i--) {
/* 101 */           for (j = 0; j >= -1; j--) {
/* 102 */             if (func_181624_a(worldIn, pos, i, j, BlockPlanks.EnumType.JUNGLE)) {
/* 103 */               WorldGenMegaJungle worldGenMegaJungle = new WorldGenMegaJungle(true, 10, 20, iblockstate, iblockstate1);
/* 104 */               flag = true;
/*     */               
/*     */               break label69;
/*     */             } 
/*     */           } 
/*     */         } 
/* 110 */         if (!flag) {
/* 111 */           j = 0;
/* 112 */           i = 0;
/* 113 */           WorldGenTrees worldGenTrees = new WorldGenTrees(true, 4 + rand.nextInt(7), iblockstate, iblockstate1, false);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case null:
/* 119 */         worldGenSavannaTree = new WorldGenSavannaTree(true);
/*     */         break;
/*     */ 
/*     */       
/*     */       case DARK_OAK:
/* 124 */         label70: for (i = 0; i >= -1; i--) {
/* 125 */           for (j = 0; j >= -1; j--) {
/* 126 */             if (func_181624_a(worldIn, pos, i, j, BlockPlanks.EnumType.DARK_OAK)) {
/* 127 */               worldGenCanopyTree = new WorldGenCanopyTree(true);
/* 128 */               flag = true;
/*     */               
/*     */               break label70;
/*     */             } 
/*     */           } 
/*     */         } 
/* 134 */         if (!flag) {
/*     */           return;
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 141 */     IBlockState iblockstate2 = Blocks.air.getDefaultState();
/*     */     
/* 143 */     if (flag) {
/* 144 */       worldIn.setBlockState(pos.add(i, 0, j), iblockstate2, 4);
/* 145 */       worldIn.setBlockState(pos.add(i + 1, 0, j), iblockstate2, 4);
/* 146 */       worldIn.setBlockState(pos.add(i, 0, j + 1), iblockstate2, 4);
/* 147 */       worldIn.setBlockState(pos.add(i + 1, 0, j + 1), iblockstate2, 4);
/*     */     } else {
/* 149 */       worldIn.setBlockState(pos, iblockstate2, 4);
/*     */     } 
/*     */     
/* 152 */     if (!worldGenCanopyTree.generate(worldIn, rand, pos.add(i, 0, j))) {
/* 153 */       if (flag) {
/* 154 */         worldIn.setBlockState(pos.add(i, 0, j), state, 4);
/* 155 */         worldIn.setBlockState(pos.add(i + 1, 0, j), state, 4);
/* 156 */         worldIn.setBlockState(pos.add(i, 0, j + 1), state, 4);
/* 157 */         worldIn.setBlockState(pos.add(i + 1, 0, j + 1), state, 4);
/*     */       } else {
/* 159 */         worldIn.setBlockState(pos, state, 4);
/*     */       } 
/*     */     } }
/*     */ 
/*     */   
/*     */   private boolean func_181624_a(World p_181624_1_, BlockPos p_181624_2_, int p_181624_3_, int p_181624_4_, BlockPlanks.EnumType p_181624_5_) {
/* 165 */     return (isTypeAt(p_181624_1_, p_181624_2_.add(p_181624_3_, 0, p_181624_4_), p_181624_5_) && isTypeAt(p_181624_1_, p_181624_2_.add(p_181624_3_ + 1, 0, p_181624_4_), p_181624_5_) && isTypeAt(p_181624_1_, p_181624_2_.add(p_181624_3_, 0, p_181624_4_ + 1), p_181624_5_) && isTypeAt(p_181624_1_, p_181624_2_.add(p_181624_3_ + 1, 0, p_181624_4_ + 1), p_181624_5_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTypeAt(World worldIn, BlockPos pos, BlockPlanks.EnumType type) {
/* 172 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 173 */     return (iblockstate.getBlock() == this && iblockstate.getValue((IProperty)TYPE) == type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 181 */     return ((BlockPlanks.EnumType)state.getValue((IProperty)TYPE)).getMetadata();
/*     */   }
/*     */   
/*     */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/*     */     byte b;
/*     */     int i;
/*     */     BlockPlanks.EnumType[] arrayOfEnumType;
/* 188 */     for (i = (arrayOfEnumType = BlockPlanks.EnumType.values()).length, b = 0; b < i; ) { BlockPlanks.EnumType blockplanks$enumtype = arrayOfEnumType[b];
/* 189 */       list.add(new ItemStack(itemIn, 1, blockplanks$enumtype.getMetadata()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 197 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 201 */     return (worldIn.rand.nextFloat() < 0.45D);
/*     */   }
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 205 */     grow(worldIn, pos, state, rand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 212 */     return getDefaultState().withProperty((IProperty)TYPE, BlockPlanks.EnumType.byMetadata(meta & 0x7)).withProperty((IProperty)STAGE, Integer.valueOf((meta & 0x8) >> 3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 219 */     int i = 0;
/* 220 */     i |= ((BlockPlanks.EnumType)state.getValue((IProperty)TYPE)).getMetadata();
/* 221 */     i |= ((Integer)state.getValue((IProperty)STAGE)).intValue() << 3;
/* 222 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockState createBlockState() {
/* 226 */     return new BlockState(this, new IProperty[] { (IProperty)TYPE, (IProperty)STAGE });
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockSapling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */