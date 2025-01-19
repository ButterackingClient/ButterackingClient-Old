/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.gen.feature.WorldGenDungeons;
/*     */ import net.minecraft.world.gen.feature.WorldGenLakes;
/*     */ import net.minecraft.world.gen.structure.MapGenMineshaft;
/*     */ import net.minecraft.world.gen.structure.MapGenScatteredFeature;
/*     */ import net.minecraft.world.gen.structure.MapGenStronghold;
/*     */ import net.minecraft.world.gen.structure.MapGenStructure;
/*     */ import net.minecraft.world.gen.structure.MapGenVillage;
/*     */ import net.minecraft.world.gen.structure.StructureOceanMonument;
/*     */ 
/*     */ public class ChunkProviderFlat
/*     */   implements IChunkProvider {
/*     */   private World worldObj;
/*     */   private Random random;
/*  32 */   private final IBlockState[] cachedBlockIDs = new IBlockState[256];
/*     */   private final FlatGeneratorInfo flatWorldGenInfo;
/*  34 */   private final List<MapGenStructure> structureGenerators = Lists.newArrayList();
/*     */   private final boolean hasDecoration;
/*     */   private final boolean hasDungeons;
/*     */   private WorldGenLakes waterLakeGenerator;
/*     */   private WorldGenLakes lavaLakeGenerator;
/*     */   
/*     */   public ChunkProviderFlat(World worldIn, long seed, boolean generateStructures, String flatGeneratorSettings) {
/*  41 */     this.worldObj = worldIn;
/*  42 */     this.random = new Random(seed);
/*  43 */     this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(flatGeneratorSettings);
/*     */     
/*  45 */     if (generateStructures) {
/*  46 */       Map<String, Map<String, String>> map = this.flatWorldGenInfo.getWorldFeatures();
/*     */       
/*  48 */       if (map.containsKey("village")) {
/*  49 */         Map<String, String> map1 = map.get("village");
/*     */         
/*  51 */         if (!map1.containsKey("size")) {
/*  52 */           map1.put("size", "1");
/*     */         }
/*     */         
/*  55 */         this.structureGenerators.add(new MapGenVillage(map1));
/*     */       } 
/*     */       
/*  58 */       if (map.containsKey("biome_1")) {
/*  59 */         this.structureGenerators.add(new MapGenScatteredFeature(map.get("biome_1")));
/*     */       }
/*     */       
/*  62 */       if (map.containsKey("mineshaft")) {
/*  63 */         this.structureGenerators.add(new MapGenMineshaft(map.get("mineshaft")));
/*     */       }
/*     */       
/*  66 */       if (map.containsKey("stronghold")) {
/*  67 */         this.structureGenerators.add(new MapGenStronghold(map.get("stronghold")));
/*     */       }
/*     */       
/*  70 */       if (map.containsKey("oceanmonument")) {
/*  71 */         this.structureGenerators.add(new StructureOceanMonument(map.get("oceanmonument")));
/*     */       }
/*     */     } 
/*     */     
/*  75 */     if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lake")) {
/*  76 */       this.waterLakeGenerator = new WorldGenLakes((Block)Blocks.water);
/*     */     }
/*     */     
/*  79 */     if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lava_lake")) {
/*  80 */       this.lavaLakeGenerator = new WorldGenLakes((Block)Blocks.lava);
/*     */     }
/*     */     
/*  83 */     this.hasDungeons = this.flatWorldGenInfo.getWorldFeatures().containsKey("dungeon");
/*  84 */     int j = 0;
/*  85 */     int k = 0;
/*  86 */     boolean flag = true;
/*     */     
/*  88 */     for (FlatLayerInfo flatlayerinfo : this.flatWorldGenInfo.getFlatLayers()) {
/*  89 */       for (int i = flatlayerinfo.getMinY(); i < flatlayerinfo.getMinY() + flatlayerinfo.getLayerCount(); i++) {
/*  90 */         IBlockState iblockstate = flatlayerinfo.getLayerMaterial();
/*     */         
/*  92 */         if (iblockstate.getBlock() != Blocks.air) {
/*  93 */           flag = false;
/*  94 */           this.cachedBlockIDs[i] = iblockstate;
/*     */         } 
/*     */       } 
/*     */       
/*  98 */       if (flatlayerinfo.getLayerMaterial().getBlock() == Blocks.air) {
/*  99 */         k += flatlayerinfo.getLayerCount(); continue;
/*     */       } 
/* 101 */       j += flatlayerinfo.getLayerCount() + k;
/* 102 */       k = 0;
/*     */     } 
/*     */ 
/*     */     
/* 106 */     worldIn.setSeaLevel(j);
/* 107 */     this.hasDecoration = flag ? false : this.flatWorldGenInfo.getWorldFeatures().containsKey("decoration");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/* 115 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/*     */     
/* 117 */     for (int i = 0; i < this.cachedBlockIDs.length; i++) {
/* 118 */       IBlockState iblockstate = this.cachedBlockIDs[i];
/*     */       
/* 120 */       if (iblockstate != null) {
/* 121 */         for (int j = 0; j < 16; j++) {
/* 122 */           for (int k = 0; k < 16; k++) {
/* 123 */             chunkprimer.setBlockState(j, i, k, iblockstate);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 129 */     for (MapGenBase mapgenbase : this.structureGenerators) {
/* 130 */       mapgenbase.generate(this, this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 133 */     Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
/* 134 */     BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, x * 16, z * 16, 16, 16);
/* 135 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/* 137 */     for (int l = 0; l < abyte.length; l++) {
/* 138 */       abyte[l] = (byte)(abiomegenbase[l]).biomeID;
/*     */     }
/*     */     
/* 141 */     chunk.generateSkylightMap();
/* 142 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/* 149 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {
/* 156 */     int i = x * 16;
/* 157 */     int j = z * 16;
/* 158 */     BlockPos blockpos = new BlockPos(i, 0, j);
/* 159 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(new BlockPos(i + 16, 0, j + 16));
/* 160 */     boolean flag = false;
/* 161 */     this.random.setSeed(this.worldObj.getSeed());
/* 162 */     long k = this.random.nextLong() / 2L * 2L + 1L;
/* 163 */     long l = this.random.nextLong() / 2L * 2L + 1L;
/* 164 */     this.random.setSeed(x * k + z * l ^ this.worldObj.getSeed());
/* 165 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
/*     */     
/* 167 */     for (MapGenStructure mapgenstructure : this.structureGenerators) {
/* 168 */       boolean flag1 = mapgenstructure.generateStructure(this.worldObj, this.random, chunkcoordintpair);
/*     */       
/* 170 */       if (mapgenstructure instanceof MapGenVillage) {
/* 171 */         flag |= flag1;
/*     */       }
/*     */     } 
/*     */     
/* 175 */     if (this.waterLakeGenerator != null && !flag && this.random.nextInt(4) == 0) {
/* 176 */       this.waterLakeGenerator.generate(this.worldObj, this.random, blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
/*     */     }
/*     */     
/* 179 */     if (this.lavaLakeGenerator != null && !flag && this.random.nextInt(8) == 0) {
/* 180 */       BlockPos blockpos1 = blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(this.random.nextInt(248) + 8), this.random.nextInt(16) + 8);
/*     */       
/* 182 */       if (blockpos1.getY() < this.worldObj.getSeaLevel() || this.random.nextInt(10) == 0) {
/* 183 */         this.lavaLakeGenerator.generate(this.worldObj, this.random, blockpos1);
/*     */       }
/*     */     } 
/*     */     
/* 187 */     if (this.hasDungeons) {
/* 188 */       for (int i1 = 0; i1 < 8; i1++) {
/* 189 */         (new WorldGenDungeons()).generate(this.worldObj, this.random, blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
/*     */       }
/*     */     }
/*     */     
/* 193 */     if (this.hasDecoration) {
/* 194 */       biomegenbase.decorate(this.worldObj, this.random, blockpos);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 199 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/* 207 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unloadQueuedChunks() {
/* 221 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 228 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 235 */     return "FlatLevelSource";
/*     */   }
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 239 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(pos);
/* 240 */     return biomegenbase.getSpawnableList(creatureType);
/*     */   }
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 244 */     if ("Stronghold".equals(structureName)) {
/* 245 */       for (MapGenStructure mapgenstructure : this.structureGenerators) {
/* 246 */         if (mapgenstructure instanceof MapGenStronghold) {
/* 247 */           return mapgenstructure.getClosestStrongholdPos(worldIn, position);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 252 */     return null;
/*     */   }
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 256 */     return 0;
/*     */   }
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {
/* 260 */     for (MapGenStructure mapgenstructure : this.structureGenerators) {
/* 261 */       mapgenstructure.generate(this, this.worldObj, x, z, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 266 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\ChunkProviderFlat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */