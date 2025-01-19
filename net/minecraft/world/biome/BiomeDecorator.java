/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.block.BlockStone;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.ChunkProviderSettings;
/*     */ import net.minecraft.world.gen.GeneratorBushFeature;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*     */ import net.minecraft.world.gen.feature.WorldGenCactus;
/*     */ import net.minecraft.world.gen.feature.WorldGenClay;
/*     */ import net.minecraft.world.gen.feature.WorldGenDeadBush;
/*     */ import net.minecraft.world.gen.feature.WorldGenFlowers;
/*     */ import net.minecraft.world.gen.feature.WorldGenLiquids;
/*     */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*     */ import net.minecraft.world.gen.feature.WorldGenPumpkin;
/*     */ import net.minecraft.world.gen.feature.WorldGenReed;
/*     */ import net.minecraft.world.gen.feature.WorldGenSand;
/*     */ import net.minecraft.world.gen.feature.WorldGenWaterlily;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BiomeDecorator
/*     */ {
/*     */   protected World currentWorld;
/*     */   protected Random randomGenerator;
/*     */   protected BlockPos field_180294_c;
/*     */   protected ChunkProviderSettings chunkProviderSettings;
/*  43 */   protected WorldGenerator clayGen = (WorldGenerator)new WorldGenClay(4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   protected WorldGenerator sandGen = (WorldGenerator)new WorldGenSand((Block)Blocks.sand, 7);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   protected WorldGenerator gravelAsSandGen = (WorldGenerator)new WorldGenSand(Blocks.gravel, 6);
/*     */   
/*     */   protected WorldGenerator dirtGen;
/*     */   
/*     */   protected WorldGenerator gravelGen;
/*     */   
/*     */   protected WorldGenerator graniteGen;
/*     */   
/*     */   protected WorldGenerator dioriteGen;
/*     */   
/*     */   protected WorldGenerator andesiteGen;
/*     */   
/*     */   protected WorldGenerator coalGen;
/*     */   
/*     */   protected WorldGenerator ironGen;
/*     */   
/*     */   protected WorldGenerator goldGen;
/*     */   
/*     */   protected WorldGenerator redstoneGen;
/*     */   
/*     */   protected WorldGenerator diamondGen;
/*     */   
/*     */   protected WorldGenerator lapisGen;
/*     */   
/*  77 */   protected WorldGenFlowers yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   protected WorldGenerator mushroomBrownGen = (WorldGenerator)new GeneratorBushFeature(Blocks.brown_mushroom);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   protected WorldGenerator mushroomRedGen = (WorldGenerator)new GeneratorBushFeature(Blocks.red_mushroom);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   protected WorldGenerator bigMushroomGen = (WorldGenerator)new WorldGenBigMushroom();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   protected WorldGenerator reedGen = (WorldGenerator)new WorldGenReed();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   protected WorldGenerator cactusGen = (WorldGenerator)new WorldGenCactus();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   protected WorldGenerator waterlilyGen = (WorldGenerator)new WorldGenWaterlily();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int waterlilyPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int treesPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   protected int flowersPerChunk = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   protected int grassPerChunk = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int deadBushPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int mushroomsPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int reedsPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int cactiPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 154 */   protected int sandPerChunk = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   protected int sandPerChunk2 = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   protected int clayPerChunk = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int bigMushroomsPerChunk;
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generateLakes = true;
/*     */ 
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random random, BiomeGenBase biome, BlockPos p_180292_4_) {
/* 178 */     if (this.currentWorld != null) {
/* 179 */       throw new RuntimeException("Already decorating");
/*     */     }
/* 181 */     this.currentWorld = worldIn;
/* 182 */     String s = worldIn.getWorldInfo().getGeneratorOptions();
/*     */     
/* 184 */     if (s != null) {
/* 185 */       this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(s).func_177864_b();
/*     */     } else {
/* 187 */       this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory("").func_177864_b();
/*     */     } 
/*     */     
/* 190 */     this.randomGenerator = random;
/* 191 */     this.field_180294_c = p_180292_4_;
/* 192 */     this.dirtGen = (WorldGenerator)new WorldGenMinable(Blocks.dirt.getDefaultState(), this.chunkProviderSettings.dirtSize);
/* 193 */     this.gravelGen = (WorldGenerator)new WorldGenMinable(Blocks.gravel.getDefaultState(), this.chunkProviderSettings.gravelSize);
/* 194 */     this.graniteGen = (WorldGenerator)new WorldGenMinable(Blocks.stone.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, (Comparable)BlockStone.EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
/* 195 */     this.dioriteGen = (WorldGenerator)new WorldGenMinable(Blocks.stone.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, (Comparable)BlockStone.EnumType.DIORITE), this.chunkProviderSettings.dioriteSize);
/* 196 */     this.andesiteGen = (WorldGenerator)new WorldGenMinable(Blocks.stone.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, (Comparable)BlockStone.EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize);
/* 197 */     this.coalGen = (WorldGenerator)new WorldGenMinable(Blocks.coal_ore.getDefaultState(), this.chunkProviderSettings.coalSize);
/* 198 */     this.ironGen = (WorldGenerator)new WorldGenMinable(Blocks.iron_ore.getDefaultState(), this.chunkProviderSettings.ironSize);
/* 199 */     this.goldGen = (WorldGenerator)new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.chunkProviderSettings.goldSize);
/* 200 */     this.redstoneGen = (WorldGenerator)new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), this.chunkProviderSettings.redstoneSize);
/* 201 */     this.diamondGen = (WorldGenerator)new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), this.chunkProviderSettings.diamondSize);
/* 202 */     this.lapisGen = (WorldGenerator)new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), this.chunkProviderSettings.lapisSize);
/* 203 */     genDecorations(biome);
/* 204 */     this.currentWorld = null;
/* 205 */     this.randomGenerator = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void genDecorations(BiomeGenBase biomeGenBaseIn) {
/* 210 */     generateOres();
/*     */     
/* 212 */     for (int i = 0; i < this.sandPerChunk2; i++) {
/* 213 */       int j = this.randomGenerator.nextInt(16) + 8;
/* 214 */       int k = this.randomGenerator.nextInt(16) + 8;
/* 215 */       this.sandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(j, 0, k)));
/*     */     } 
/*     */     
/* 218 */     for (int i1 = 0; i1 < this.clayPerChunk; i1++) {
/* 219 */       int l1 = this.randomGenerator.nextInt(16) + 8;
/* 220 */       int i6 = this.randomGenerator.nextInt(16) + 8;
/* 221 */       this.clayGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(l1, 0, i6)));
/*     */     } 
/*     */     
/* 224 */     for (int j1 = 0; j1 < this.sandPerChunk; j1++) {
/* 225 */       int i2 = this.randomGenerator.nextInt(16) + 8;
/* 226 */       int j6 = this.randomGenerator.nextInt(16) + 8;
/* 227 */       this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(i2, 0, j6)));
/*     */     } 
/*     */     
/* 230 */     int k1 = this.treesPerChunk;
/*     */     
/* 232 */     if (this.randomGenerator.nextInt(10) == 0) {
/* 233 */       k1++;
/*     */     }
/*     */     
/* 236 */     for (int j2 = 0; j2 < k1; j2++) {
/* 237 */       int k6 = this.randomGenerator.nextInt(16) + 8;
/* 238 */       int l = this.randomGenerator.nextInt(16) + 8;
/* 239 */       WorldGenAbstractTree worldgenabstracttree = biomeGenBaseIn.genBigTreeChance(this.randomGenerator);
/* 240 */       worldgenabstracttree.func_175904_e();
/* 241 */       BlockPos blockpos = this.currentWorld.getHeight(this.field_180294_c.add(k6, 0, l));
/*     */       
/* 243 */       if (worldgenabstracttree.generate(this.currentWorld, this.randomGenerator, blockpos)) {
/* 244 */         worldgenabstracttree.func_180711_a(this.currentWorld, this.randomGenerator, blockpos);
/*     */       }
/*     */     } 
/*     */     
/* 248 */     for (int k2 = 0; k2 < this.bigMushroomsPerChunk; k2++) {
/* 249 */       int l6 = this.randomGenerator.nextInt(16) + 8;
/* 250 */       int k10 = this.randomGenerator.nextInt(16) + 8;
/* 251 */       this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getHeight(this.field_180294_c.add(l6, 0, k10)));
/*     */     } 
/*     */     
/* 254 */     for (int l2 = 0; l2 < this.flowersPerChunk; l2++) {
/* 255 */       int i7 = this.randomGenerator.nextInt(16) + 8;
/* 256 */       int l10 = this.randomGenerator.nextInt(16) + 8;
/* 257 */       int j14 = this.currentWorld.getHeight(this.field_180294_c.add(i7, 0, l10)).getY() + 32;
/*     */       
/* 259 */       if (j14 > 0) {
/* 260 */         int k17 = this.randomGenerator.nextInt(j14);
/* 261 */         BlockPos blockpos1 = this.field_180294_c.add(i7, k17, l10);
/* 262 */         BlockFlower.EnumFlowerType blockflower$enumflowertype = biomeGenBaseIn.pickRandomFlower(this.randomGenerator, blockpos1);
/* 263 */         BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();
/*     */         
/* 265 */         if (blockflower.getMaterial() != Material.air) {
/* 266 */           this.yellowFlowerGen.setGeneratedBlock(blockflower, blockflower$enumflowertype);
/* 267 */           this.yellowFlowerGen.generate(this.currentWorld, this.randomGenerator, blockpos1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 272 */     for (int i3 = 0; i3 < this.grassPerChunk; i3++) {
/* 273 */       int j7 = this.randomGenerator.nextInt(16) + 8;
/* 274 */       int i11 = this.randomGenerator.nextInt(16) + 8;
/* 275 */       int k14 = this.currentWorld.getHeight(this.field_180294_c.add(j7, 0, i11)).getY() * 2;
/*     */       
/* 277 */       if (k14 > 0) {
/* 278 */         int l17 = this.randomGenerator.nextInt(k14);
/* 279 */         biomeGenBaseIn.getRandomWorldGenForGrass(this.randomGenerator).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(j7, l17, i11));
/*     */       } 
/*     */     } 
/*     */     
/* 283 */     for (int j3 = 0; j3 < this.deadBushPerChunk; j3++) {
/* 284 */       int k7 = this.randomGenerator.nextInt(16) + 8;
/* 285 */       int j11 = this.randomGenerator.nextInt(16) + 8;
/* 286 */       int l14 = this.currentWorld.getHeight(this.field_180294_c.add(k7, 0, j11)).getY() * 2;
/*     */       
/* 288 */       if (l14 > 0) {
/* 289 */         int i18 = this.randomGenerator.nextInt(l14);
/* 290 */         (new WorldGenDeadBush()).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(k7, i18, j11));
/*     */       } 
/*     */     } 
/*     */     
/* 294 */     for (int k3 = 0; k3 < this.waterlilyPerChunk; k3++) {
/* 295 */       int l7 = this.randomGenerator.nextInt(16) + 8;
/* 296 */       int k11 = this.randomGenerator.nextInt(16) + 8;
/* 297 */       int i15 = this.currentWorld.getHeight(this.field_180294_c.add(l7, 0, k11)).getY() * 2;
/*     */       
/* 299 */       if (i15 > 0) {
/* 300 */         int j18 = this.randomGenerator.nextInt(i15);
/*     */         
/*     */         BlockPos blockpos4;
/*     */         
/* 304 */         for (blockpos4 = this.field_180294_c.add(l7, j18, k11); blockpos4.getY() > 0; blockpos4 = blockpos7) {
/* 305 */           BlockPos blockpos7 = blockpos4.down();
/*     */           
/* 307 */           if (!this.currentWorld.isAirBlock(blockpos7)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         
/* 312 */         this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, blockpos4);
/*     */       } 
/*     */     } 
/*     */     
/* 316 */     for (int l3 = 0; l3 < this.mushroomsPerChunk; l3++) {
/* 317 */       if (this.randomGenerator.nextInt(4) == 0) {
/* 318 */         int i8 = this.randomGenerator.nextInt(16) + 8;
/* 319 */         int l11 = this.randomGenerator.nextInt(16) + 8;
/* 320 */         BlockPos blockpos2 = this.currentWorld.getHeight(this.field_180294_c.add(i8, 0, l11));
/* 321 */         this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, blockpos2);
/*     */       } 
/*     */       
/* 324 */       if (this.randomGenerator.nextInt(8) == 0) {
/* 325 */         int j8 = this.randomGenerator.nextInt(16) + 8;
/* 326 */         int i12 = this.randomGenerator.nextInt(16) + 8;
/* 327 */         int j15 = this.currentWorld.getHeight(this.field_180294_c.add(j8, 0, i12)).getY() * 2;
/*     */         
/* 329 */         if (j15 > 0) {
/* 330 */           int k18 = this.randomGenerator.nextInt(j15);
/* 331 */           BlockPos blockpos5 = this.field_180294_c.add(j8, k18, i12);
/* 332 */           this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, blockpos5);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 337 */     if (this.randomGenerator.nextInt(4) == 0) {
/* 338 */       int i4 = this.randomGenerator.nextInt(16) + 8;
/* 339 */       int k8 = this.randomGenerator.nextInt(16) + 8;
/* 340 */       int j12 = this.currentWorld.getHeight(this.field_180294_c.add(i4, 0, k8)).getY() * 2;
/*     */       
/* 342 */       if (j12 > 0) {
/* 343 */         int k15 = this.randomGenerator.nextInt(j12);
/* 344 */         this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(i4, k15, k8));
/*     */       } 
/*     */     } 
/*     */     
/* 348 */     if (this.randomGenerator.nextInt(8) == 0) {
/* 349 */       int j4 = this.randomGenerator.nextInt(16) + 8;
/* 350 */       int l8 = this.randomGenerator.nextInt(16) + 8;
/* 351 */       int k12 = this.currentWorld.getHeight(this.field_180294_c.add(j4, 0, l8)).getY() * 2;
/*     */       
/* 353 */       if (k12 > 0) {
/* 354 */         int l15 = this.randomGenerator.nextInt(k12);
/* 355 */         this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(j4, l15, l8));
/*     */       } 
/*     */     } 
/*     */     
/* 359 */     for (int k4 = 0; k4 < this.reedsPerChunk; k4++) {
/* 360 */       int i9 = this.randomGenerator.nextInt(16) + 8;
/* 361 */       int l12 = this.randomGenerator.nextInt(16) + 8;
/* 362 */       int i16 = this.currentWorld.getHeight(this.field_180294_c.add(i9, 0, l12)).getY() * 2;
/*     */       
/* 364 */       if (i16 > 0) {
/* 365 */         int l18 = this.randomGenerator.nextInt(i16);
/* 366 */         this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(i9, l18, l12));
/*     */       } 
/*     */     } 
/*     */     
/* 370 */     for (int l4 = 0; l4 < 10; l4++) {
/* 371 */       int j9 = this.randomGenerator.nextInt(16) + 8;
/* 372 */       int i13 = this.randomGenerator.nextInt(16) + 8;
/* 373 */       int j16 = this.currentWorld.getHeight(this.field_180294_c.add(j9, 0, i13)).getY() * 2;
/*     */       
/* 375 */       if (j16 > 0) {
/* 376 */         int i19 = this.randomGenerator.nextInt(j16);
/* 377 */         this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(j9, i19, i13));
/*     */       } 
/*     */     } 
/*     */     
/* 381 */     if (this.randomGenerator.nextInt(32) == 0) {
/* 382 */       int i5 = this.randomGenerator.nextInt(16) + 8;
/* 383 */       int k9 = this.randomGenerator.nextInt(16) + 8;
/* 384 */       int j13 = this.currentWorld.getHeight(this.field_180294_c.add(i5, 0, k9)).getY() * 2;
/*     */       
/* 386 */       if (j13 > 0) {
/* 387 */         int k16 = this.randomGenerator.nextInt(j13);
/* 388 */         (new WorldGenPumpkin()).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(i5, k16, k9));
/*     */       } 
/*     */     } 
/*     */     
/* 392 */     for (int j5 = 0; j5 < this.cactiPerChunk; j5++) {
/* 393 */       int l9 = this.randomGenerator.nextInt(16) + 8;
/* 394 */       int k13 = this.randomGenerator.nextInt(16) + 8;
/* 395 */       int l16 = this.currentWorld.getHeight(this.field_180294_c.add(l9, 0, k13)).getY() * 2;
/*     */       
/* 397 */       if (l16 > 0) {
/* 398 */         int j19 = this.randomGenerator.nextInt(l16);
/* 399 */         this.cactusGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(l9, j19, k13));
/*     */       } 
/*     */     } 
/*     */     
/* 403 */     if (this.generateLakes) {
/* 404 */       for (int k5 = 0; k5 < 50; k5++) {
/* 405 */         int i10 = this.randomGenerator.nextInt(16) + 8;
/* 406 */         int l13 = this.randomGenerator.nextInt(16) + 8;
/* 407 */         int i17 = this.randomGenerator.nextInt(248) + 8;
/*     */         
/* 409 */         if (i17 > 0) {
/* 410 */           int k19 = this.randomGenerator.nextInt(i17);
/* 411 */           BlockPos blockpos6 = this.field_180294_c.add(i10, k19, l13);
/* 412 */           (new WorldGenLiquids((Block)Blocks.flowing_water)).generate(this.currentWorld, this.randomGenerator, blockpos6);
/*     */         } 
/*     */       } 
/*     */       
/* 416 */       for (int l5 = 0; l5 < 20; l5++) {
/* 417 */         int j10 = this.randomGenerator.nextInt(16) + 8;
/* 418 */         int i14 = this.randomGenerator.nextInt(16) + 8;
/* 419 */         int j17 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);
/* 420 */         BlockPos blockpos3 = this.field_180294_c.add(j10, j17, i14);
/* 421 */         (new WorldGenLiquids((Block)Blocks.flowing_lava)).generate(this.currentWorld, this.randomGenerator, blockpos3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void genStandardOre1(int blockCount, WorldGenerator generator, int minHeight, int maxHeight) {
/* 430 */     if (maxHeight < minHeight) {
/* 431 */       int i = minHeight;
/* 432 */       minHeight = maxHeight;
/* 433 */       maxHeight = i;
/* 434 */     } else if (maxHeight == minHeight) {
/* 435 */       if (minHeight < 255) {
/* 436 */         maxHeight++;
/*     */       } else {
/* 438 */         minHeight--;
/*     */       } 
/*     */     } 
/*     */     
/* 442 */     for (int j = 0; j < blockCount; j++) {
/* 443 */       BlockPos blockpos = this.field_180294_c.add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(maxHeight - minHeight) + minHeight, this.randomGenerator.nextInt(16));
/* 444 */       generator.generate(this.currentWorld, this.randomGenerator, blockpos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void genStandardOre2(int blockCount, WorldGenerator generator, int centerHeight, int spread) {
/* 452 */     for (int i = 0; i < blockCount; i++) {
/* 453 */       BlockPos blockpos = this.field_180294_c.add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(spread) + this.randomGenerator.nextInt(spread) + centerHeight - spread, this.randomGenerator.nextInt(16));
/* 454 */       generator.generate(this.currentWorld, this.randomGenerator, blockpos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void generateOres() {
/* 462 */     genStandardOre1(this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight);
/* 463 */     genStandardOre1(this.chunkProviderSettings.gravelCount, this.gravelGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight);
/* 464 */     genStandardOre1(this.chunkProviderSettings.dioriteCount, this.dioriteGen, this.chunkProviderSettings.dioriteMinHeight, this.chunkProviderSettings.dioriteMaxHeight);
/* 465 */     genStandardOre1(this.chunkProviderSettings.graniteCount, this.graniteGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight);
/* 466 */     genStandardOre1(this.chunkProviderSettings.andesiteCount, this.andesiteGen, this.chunkProviderSettings.andesiteMinHeight, this.chunkProviderSettings.andesiteMaxHeight);
/* 467 */     genStandardOre1(this.chunkProviderSettings.coalCount, this.coalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);
/* 468 */     genStandardOre1(this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);
/* 469 */     genStandardOre1(this.chunkProviderSettings.goldCount, this.goldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);
/* 470 */     genStandardOre1(this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight);
/* 471 */     genStandardOre1(this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight);
/* 472 */     genStandardOre2(this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\biome\BiomeDecorator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */