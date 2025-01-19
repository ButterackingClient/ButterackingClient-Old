/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.world.ColorizerFoliage;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeColorHelper;
/*     */ 
/*     */ public abstract class BlockLeaves extends BlockLeavesBase {
/*  21 */   public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
/*  22 */   public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
/*     */   int[] surroundings;
/*     */   protected int iconIndex;
/*     */   protected boolean isTransparent;
/*     */   
/*     */   public BlockLeaves() {
/*  28 */     super(Material.leaves, false);
/*  29 */     setTickRandomly(true);
/*  30 */     setCreativeTab(CreativeTabs.tabDecorations);
/*  31 */     setHardness(0.2F);
/*  32 */     setLightOpacity(1);
/*  33 */     setStepSound(soundTypeGrass);
/*     */   }
/*     */   
/*     */   public int getBlockColor() {
/*  37 */     return ColorizerFoliage.getFoliageColor(0.5D, 1.0D);
/*     */   }
/*     */   
/*     */   public int getRenderColor(IBlockState state) {
/*  41 */     return ColorizerFoliage.getFoliageColorBasic();
/*     */   }
/*     */   
/*     */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  45 */     return BiomeColorHelper.getFoliageColorAtPos(worldIn, pos);
/*     */   }
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/*  49 */     int i = 1;
/*  50 */     int j = i + 1;
/*  51 */     int k = pos.getX();
/*  52 */     int l = pos.getY();
/*  53 */     int i1 = pos.getZ();
/*     */     
/*  55 */     if (worldIn.isAreaLoaded(new BlockPos(k - j, l - j, i1 - j), new BlockPos(k + j, l + j, i1 + j))) {
/*  56 */       for (int j1 = -i; j1 <= i; j1++) {
/*  57 */         for (int k1 = -i; k1 <= i; k1++) {
/*  58 */           for (int l1 = -i; l1 <= i; l1++) {
/*  59 */             BlockPos blockpos = pos.add(j1, k1, l1);
/*  60 */             IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */             
/*  62 */             if (iblockstate.getBlock().getMaterial() == Material.leaves && !((Boolean)iblockstate.getValue((IProperty)CHECK_DECAY)).booleanValue()) {
/*  63 */               worldIn.setBlockState(blockpos, iblockstate.withProperty((IProperty)CHECK_DECAY, Boolean.valueOf(true)), 4);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  72 */     if (!worldIn.isRemote && (
/*  73 */       (Boolean)state.getValue((IProperty)CHECK_DECAY)).booleanValue() && ((Boolean)state.getValue((IProperty)DECAYABLE)).booleanValue()) {
/*  74 */       int i = 4;
/*  75 */       int j = i + 1;
/*  76 */       int k = pos.getX();
/*  77 */       int l = pos.getY();
/*  78 */       int i1 = pos.getZ();
/*  79 */       int j1 = 32;
/*  80 */       int k1 = j1 * j1;
/*  81 */       int l1 = j1 / 2;
/*     */       
/*  83 */       if (this.surroundings == null) {
/*  84 */         this.surroundings = new int[j1 * j1 * j1];
/*     */       }
/*     */       
/*  87 */       if (worldIn.isAreaLoaded(new BlockPos(k - j, l - j, i1 - j), new BlockPos(k + j, l + j, i1 + j))) {
/*  88 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  90 */         for (int i2 = -i; i2 <= i; i2++) {
/*  91 */           for (int j2 = -i; j2 <= i; j2++) {
/*  92 */             for (int k2 = -i; k2 <= i; k2++) {
/*  93 */               Block block = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.set(k + i2, l + j2, i1 + k2)).getBlock();
/*     */               
/*  95 */               if (block != Blocks.log && block != Blocks.log2) {
/*  96 */                 if (block.getMaterial() == Material.leaves) {
/*  97 */                   this.surroundings[(i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1] = -2;
/*     */                 } else {
/*  99 */                   this.surroundings[(i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1] = -1;
/*     */                 } 
/*     */               } else {
/* 102 */                 this.surroundings[(i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1] = 0;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 108 */         for (int i3 = 1; i3 <= 4; i3++) {
/* 109 */           for (int j3 = -i; j3 <= i; j3++) {
/* 110 */             for (int k3 = -i; k3 <= i; k3++) {
/* 111 */               for (int l3 = -i; l3 <= i; l3++) {
/* 112 */                 if (this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1] == i3 - 1) {
/* 113 */                   if (this.surroundings[(j3 + l1 - 1) * k1 + (k3 + l1) * j1 + l3 + l1] == -2) {
/* 114 */                     this.surroundings[(j3 + l1 - 1) * k1 + (k3 + l1) * j1 + l3 + l1] = i3;
/*     */                   }
/*     */                   
/* 117 */                   if (this.surroundings[(j3 + l1 + 1) * k1 + (k3 + l1) * j1 + l3 + l1] == -2) {
/* 118 */                     this.surroundings[(j3 + l1 + 1) * k1 + (k3 + l1) * j1 + l3 + l1] = i3;
/*     */                   }
/*     */                   
/* 121 */                   if (this.surroundings[(j3 + l1) * k1 + (k3 + l1 - 1) * j1 + l3 + l1] == -2) {
/* 122 */                     this.surroundings[(j3 + l1) * k1 + (k3 + l1 - 1) * j1 + l3 + l1] = i3;
/*     */                   }
/*     */                   
/* 125 */                   if (this.surroundings[(j3 + l1) * k1 + (k3 + l1 + 1) * j1 + l3 + l1] == -2) {
/* 126 */                     this.surroundings[(j3 + l1) * k1 + (k3 + l1 + 1) * j1 + l3 + l1] = i3;
/*     */                   }
/*     */                   
/* 129 */                   if (this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 - 1] == -2) {
/* 130 */                     this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 - 1] = i3;
/*     */                   }
/*     */                   
/* 133 */                   if (this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 + 1] == -2) {
/* 134 */                     this.surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 + 1] = i3;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 143 */       int l2 = this.surroundings[l1 * k1 + l1 * j1 + l1];
/*     */       
/* 145 */       if (l2 >= 0) {
/* 146 */         worldIn.setBlockState(pos, state.withProperty((IProperty)CHECK_DECAY, Boolean.valueOf(false)), 4);
/*     */       } else {
/* 148 */         destroy(worldIn, pos);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 155 */     if (worldIn.isRainingAt(pos.up()) && !World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && rand.nextInt(15) == 1) {
/* 156 */       double d0 = (pos.getX() + rand.nextFloat());
/* 157 */       double d1 = pos.getY() - 0.05D;
/* 158 */       double d2 = (pos.getZ() + rand.nextFloat());
/* 159 */       worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void destroy(World worldIn, BlockPos pos) {
/* 164 */     dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
/* 165 */     worldIn.setBlockToAir(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 172 */     return (random.nextInt(20) == 0) ? 1 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 179 */     return Item.getItemFromBlock(Blocks.sapling);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 186 */     if (!worldIn.isRemote) {
/* 187 */       int i = getSaplingDropChance(state);
/*     */       
/* 189 */       if (fortune > 0) {
/* 190 */         i -= 2 << fortune;
/*     */         
/* 192 */         if (i < 10) {
/* 193 */           i = 10;
/*     */         }
/*     */       } 
/*     */       
/* 197 */       if (worldIn.rand.nextInt(i) == 0) {
/* 198 */         Item item = getItemDropped(state, worldIn.rand, fortune);
/* 199 */         spawnAsEntity(worldIn, pos, new ItemStack(item, 1, damageDropped(state)));
/*     */       } 
/*     */       
/* 202 */       i = 200;
/*     */       
/* 204 */       if (fortune > 0) {
/* 205 */         i -= 10 << fortune;
/*     */         
/* 207 */         if (i < 40) {
/* 208 */           i = 40;
/*     */         }
/*     */       } 
/*     */       
/* 212 */       dropApple(worldIn, pos, state, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {}
/*     */   
/*     */   protected int getSaplingDropChance(IBlockState state) {
/* 220 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 227 */     return !this.fancyGraphics;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGraphicsLevel(boolean fancy) {
/* 234 */     this.isTransparent = fancy;
/* 235 */     this.fancyGraphics = fancy;
/* 236 */     this.iconIndex = fancy ? 0 : 1;
/*     */   }
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 240 */     return this.isTransparent ? EnumWorldBlockLayer.CUTOUT_MIPPED : EnumWorldBlockLayer.SOLID;
/*     */   }
/*     */   
/*     */   public boolean isVisuallyOpaque() {
/* 244 */     return false;
/*     */   }
/*     */   
/*     */   public abstract BlockPlanks.EnumType getWoodType(int paramInt);
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\BlockLeaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */