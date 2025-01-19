/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*     */ import net.minecraft.world.gen.feature.WorldGenCanopyTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenForest;
/*     */ 
/*     */ public class BiomeGenForest
/*     */   extends BiomeGenBase {
/*     */   private int field_150632_aF;
/*  18 */   protected static final WorldGenForest field_150629_aC = new WorldGenForest(false, true);
/*  19 */   protected static final WorldGenForest field_150630_aD = new WorldGenForest(false, false);
/*  20 */   protected static final WorldGenCanopyTree field_150631_aE = new WorldGenCanopyTree(false);
/*     */   
/*     */   public BiomeGenForest(int id, int p_i45377_2_) {
/*  23 */     super(id);
/*  24 */     this.field_150632_aF = p_i45377_2_;
/*  25 */     this.theBiomeDecorator.treesPerChunk = 10;
/*  26 */     this.theBiomeDecorator.grassPerChunk = 2;
/*     */     
/*  28 */     if (this.field_150632_aF == 1) {
/*  29 */       this.theBiomeDecorator.treesPerChunk = 6;
/*  30 */       this.theBiomeDecorator.flowersPerChunk = 100;
/*  31 */       this.theBiomeDecorator.grassPerChunk = 1;
/*     */     } 
/*     */     
/*  34 */     setFillerBlockMetadata(5159473);
/*  35 */     setTemperatureRainfall(0.7F, 0.8F);
/*     */     
/*  37 */     if (this.field_150632_aF == 2) {
/*  38 */       this.field_150609_ah = 353825;
/*  39 */       this.color = 3175492;
/*  40 */       setTemperatureRainfall(0.6F, 0.6F);
/*     */     } 
/*     */     
/*  43 */     if (this.field_150632_aF == 0) {
/*  44 */       this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 5, 4, 4));
/*     */     }
/*     */     
/*  47 */     if (this.field_150632_aF == 3) {
/*  48 */       this.theBiomeDecorator.treesPerChunk = -999;
/*     */     }
/*     */   }
/*     */   
/*     */   protected BiomeGenBase func_150557_a(int colorIn, boolean p_150557_2_) {
/*  53 */     if (this.field_150632_aF == 2) {
/*  54 */       this.field_150609_ah = 353825;
/*  55 */       this.color = colorIn;
/*     */       
/*  57 */       if (p_150557_2_) {
/*  58 */         this.field_150609_ah = (this.field_150609_ah & 0xFEFEFE) >> 1;
/*     */       }
/*     */       
/*  61 */       return this;
/*     */     } 
/*  63 */     return super.func_150557_a(colorIn, p_150557_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/*  68 */     return (this.field_150632_aF == 3 && rand.nextInt(3) > 0) ? (WorldGenAbstractTree)field_150631_aE : ((this.field_150632_aF != 2 && rand.nextInt(5) != 0) ? (WorldGenAbstractTree)this.worldGeneratorTrees : (WorldGenAbstractTree)field_150630_aD);
/*     */   }
/*     */   
/*     */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/*  72 */     if (this.field_150632_aF == 1) {
/*  73 */       double d0 = MathHelper.clamp_double((1.0D + GRASS_COLOR_NOISE.func_151601_a(pos.getX() / 48.0D, pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
/*  74 */       BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType.values()[(int)(d0 * (BlockFlower.EnumFlowerType.values()).length)];
/*  75 */       return (blockflower$enumflowertype == BlockFlower.EnumFlowerType.BLUE_ORCHID) ? BlockFlower.EnumFlowerType.POPPY : blockflower$enumflowertype;
/*     */     } 
/*  77 */     return super.pickRandomFlower(rand, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/*  82 */     if (this.field_150632_aF == 3) {
/*  83 */       for (int i = 0; i < 4; i++) {
/*  84 */         for (int j = 0; j < 4; j++) {
/*  85 */           int k = i * 4 + 1 + 8 + rand.nextInt(3);
/*  86 */           int l = j * 4 + 1 + 8 + rand.nextInt(3);
/*  87 */           BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
/*     */           
/*  89 */           if (rand.nextInt(20) == 0) {
/*  90 */             WorldGenBigMushroom worldgenbigmushroom = new WorldGenBigMushroom();
/*  91 */             worldgenbigmushroom.generate(worldIn, rand, blockpos);
/*     */           } else {
/*  93 */             WorldGenAbstractTree worldgenabstracttree = genBigTreeChance(rand);
/*  94 */             worldgenabstracttree.func_175904_e();
/*     */             
/*  96 */             if (worldgenabstracttree.generate(worldIn, rand, blockpos)) {
/*  97 */               worldgenabstracttree.func_180711_a(worldIn, rand, blockpos);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 104 */     int j1 = rand.nextInt(5) - 3;
/*     */     
/* 106 */     if (this.field_150632_aF == 1) {
/* 107 */       j1 += 2;
/*     */     }
/*     */     
/* 110 */     for (int k1 = 0; k1 < j1; k1++) {
/* 111 */       int l1 = rand.nextInt(3);
/*     */       
/* 113 */       if (l1 == 0) {
/* 114 */         DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
/* 115 */       } else if (l1 == 1) {
/* 116 */         DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.ROSE);
/* 117 */       } else if (l1 == 2) {
/* 118 */         DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
/*     */       } 
/*     */       
/* 121 */       int i2 = 0;
/* 122 */       int j2 = rand.nextInt(16) + 8;
/* 123 */       int k2 = rand.nextInt(16) + 8;
/* 124 */       int i1 = rand.nextInt(worldIn.getHeight(pos.add(j2, 0, k2)).getY() + 32);
/*     */       
/* 126 */       for (; i2 < 5 && !DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, new BlockPos(pos.getX() + j2, i1, pos.getZ() + k2)); i2++);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     super.decorate(worldIn, rand, pos);
/*     */   }
/*     */   
/*     */   public int getGrassColorAtPos(BlockPos pos) {
/* 136 */     int i = super.getGrassColorAtPos(pos);
/* 137 */     return (this.field_150632_aF == 3) ? ((i & 0xFEFEFE) + 2634762 >> 1) : i;
/*     */   }
/*     */   
/*     */   protected BiomeGenBase createMutatedBiome(int p_180277_1_) {
/* 141 */     if (this.biomeID == BiomeGenBase.forest.biomeID) {
/* 142 */       BiomeGenForest biomegenforest = new BiomeGenForest(p_180277_1_, 1);
/* 143 */       biomegenforest.setHeight(new BiomeGenBase.Height(this.minHeight, this.maxHeight + 0.2F));
/* 144 */       biomegenforest.setBiomeName("Flower Forest");
/* 145 */       biomegenforest.func_150557_a(6976549, true);
/* 146 */       biomegenforest.setFillerBlockMetadata(8233509);
/* 147 */       return biomegenforest;
/*     */     } 
/* 149 */     return (this.biomeID != BiomeGenBase.birchForest.biomeID && this.biomeID != BiomeGenBase.birchForestHills.biomeID) ? new BiomeGenMutated(p_180277_1_, this) {
/*     */         public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 151 */           this.baseBiome.decorate(worldIn, rand, pos);
/*     */         }
/* 153 */       } : new BiomeGenMutated(p_180277_1_, this) {
/*     */         public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 155 */           return rand.nextBoolean() ? (WorldGenAbstractTree)BiomeGenForest.field_150629_aC : (WorldGenAbstractTree)BiomeGenForest.field_150630_aD;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeGenForest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */