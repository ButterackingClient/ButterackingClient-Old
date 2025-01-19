/*     */ package net.minecraft.world;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntitySpawnPlacementRegistry;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.BlockPosM;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ 
/*     */ public final class SpawnerAnimals
/*     */ {
/*  33 */   private static final int MOB_COUNT_DIV = (int)Math.pow(17.0D, 2.0D);
/*  34 */   private final Set<ChunkCoordIntPair> eligibleChunksForSpawning = Sets.newHashSet();
/*  35 */   private Map<Class, EntityLiving> mapSampleEntitiesByClass = (Map)new HashMap<>();
/*  36 */   private int lastPlayerChunkX = Integer.MAX_VALUE;
/*  37 */   private int lastPlayerChunkZ = Integer.MAX_VALUE;
/*     */ 
/*     */   
/*     */   private int countChunkPos;
/*     */ 
/*     */ 
/*     */   
/*     */   public int findChunksForSpawning(WorldServer worldServerIn, boolean spawnHostileMobs, boolean spawnPeacefulMobs, boolean p_77192_4_) {
/*  45 */     if (!spawnHostileMobs && !spawnPeacefulMobs) {
/*  46 */       return 0;
/*     */     }
/*  48 */     boolean flag = true;
/*  49 */     EntityPlayer entityplayer = null;
/*     */     
/*  51 */     if (worldServerIn.playerEntities.size() == 1) {
/*  52 */       entityplayer = worldServerIn.playerEntities.get(0);
/*     */       
/*  54 */       if (this.eligibleChunksForSpawning.size() > 0 && entityplayer != null && entityplayer.chunkCoordX == this.lastPlayerChunkX && entityplayer.chunkCoordZ == this.lastPlayerChunkZ) {
/*  55 */         flag = false;
/*     */       }
/*     */     } 
/*     */     
/*  59 */     if (flag) {
/*  60 */       this.eligibleChunksForSpawning.clear();
/*  61 */       int j = 0;
/*     */       
/*  63 */       for (EntityPlayer entityplayer1 : worldServerIn.playerEntities) {
/*  64 */         if (!entityplayer1.isSpectator()) {
/*  65 */           int m = MathHelper.floor_double(entityplayer1.posX / 16.0D);
/*  66 */           int k = MathHelper.floor_double(entityplayer1.posZ / 16.0D);
/*  67 */           int l = 8;
/*     */           
/*  69 */           for (int i1 = -l; i1 <= l; i1++) {
/*  70 */             for (int j1 = -l; j1 <= l; j1++) {
/*  71 */               boolean flag1 = !(i1 != -l && i1 != l && j1 != -l && j1 != l);
/*  72 */               ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i1 + m, j1 + k);
/*     */               
/*  74 */               if (!this.eligibleChunksForSpawning.contains(chunkcoordintpair)) {
/*  75 */                 j++;
/*     */                 
/*  77 */                 if (!flag1 && worldServerIn.getWorldBorder().contains(chunkcoordintpair)) {
/*  78 */                   this.eligibleChunksForSpawning.add(chunkcoordintpair);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  86 */       this.countChunkPos = j;
/*     */       
/*  88 */       if (entityplayer != null) {
/*  89 */         this.lastPlayerChunkX = entityplayer.chunkCoordX;
/*  90 */         this.lastPlayerChunkZ = entityplayer.chunkCoordZ;
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     int j4 = 0;
/*  95 */     BlockPos blockpos2 = worldServerIn.getSpawnPoint();
/*  96 */     BlockPosM blockposm = new BlockPosM(0, 0, 0); byte b;
/*     */     int i;
/*     */     EnumCreatureType[] arrayOfEnumCreatureType;
/*  99 */     for (i = (arrayOfEnumCreatureType = EnumCreatureType.values()).length, b = 0; b < i; ) { EnumCreatureType enumcreaturetype = arrayOfEnumCreatureType[b];
/* 100 */       if ((!enumcreaturetype.getPeacefulCreature() || spawnPeacefulMobs) && (enumcreaturetype.getPeacefulCreature() || spawnHostileMobs) && (!enumcreaturetype.getAnimal() || p_77192_4_)) {
/* 101 */         int k4 = Reflector.ForgeWorld_countEntities.exists() ? Reflector.callInt(worldServerIn, Reflector.ForgeWorld_countEntities, new Object[] { enumcreaturetype, Boolean.valueOf(true) }) : worldServerIn.countEntities(enumcreaturetype.getCreatureClass());
/* 102 */         int l4 = enumcreaturetype.getMaxNumberOfCreature() * this.countChunkPos / MOB_COUNT_DIV;
/*     */         
/* 104 */         if (k4 <= l4) {
/* 105 */           Collection<ChunkCoordIntPair> collection = this.eligibleChunksForSpawning;
/*     */           
/* 107 */           if (Reflector.ForgeHooksClient.exists()) {
/* 108 */             ArrayList<ChunkCoordIntPair> arraylist = Lists.newArrayList(collection);
/* 109 */             Collections.shuffle(arraylist);
/* 110 */             collection = arraylist;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 115 */           for (ChunkCoordIntPair chunkcoordintpair1 : collection) {
/* 116 */             BlockPosM blockPosM = getRandomChunkPosition(worldServerIn, chunkcoordintpair1.chunkXPos, chunkcoordintpair1.chunkZPos, blockposm);
/* 117 */             int k1 = blockPosM.getX();
/* 118 */             int l1 = blockPosM.getY();
/* 119 */             int i2 = blockPosM.getZ();
/* 120 */             Block block = worldServerIn.getBlockState((BlockPos)blockPosM).getBlock();
/*     */             
/* 122 */             if (!block.isNormalCube()) {
/* 123 */               int j2 = 0;
/*     */               int k2;
/* 125 */               label117: for (k2 = 0; k2 < 3; k2++) {
/* 126 */                 int l2 = k1;
/* 127 */                 int i3 = l1;
/* 128 */                 int j3 = i2;
/* 129 */                 int k3 = 6;
/* 130 */                 BiomeGenBase.SpawnListEntry biomegenbase$spawnlistentry = null;
/* 131 */                 IEntityLivingData ientitylivingdata = null;
/*     */                 
/* 133 */                 for (int l3 = 0; l3 < 4; l3++) {
/* 134 */                   l2 += worldServerIn.rand.nextInt(k3) - worldServerIn.rand.nextInt(k3);
/* 135 */                   i3 += worldServerIn.rand.nextInt(1) - worldServerIn.rand.nextInt(1);
/* 136 */                   j3 += worldServerIn.rand.nextInt(k3) - worldServerIn.rand.nextInt(k3);
/* 137 */                   BlockPos blockpos1 = new BlockPos(l2, i3, j3);
/* 138 */                   float f = l2 + 0.5F;
/* 139 */                   float f1 = j3 + 0.5F;
/*     */                   
/* 141 */                   if (!worldServerIn.isAnyPlayerWithinRangeAt(f, i3, f1, 24.0D) && blockpos2.distanceSq(f, i3, f1) >= 576.0D) {
/* 142 */                     if (biomegenbase$spawnlistentry == null) {
/* 143 */                       biomegenbase$spawnlistentry = worldServerIn.getSpawnListEntryForTypeAt(enumcreaturetype, blockpos1);
/*     */                       
/* 145 */                       if (biomegenbase$spawnlistentry == null) {
/*     */                         break;
/*     */                       }
/*     */                     } 
/*     */                     
/* 150 */                     if (worldServerIn.canCreatureTypeSpawnHere(enumcreaturetype, biomegenbase$spawnlistentry, blockpos1) && canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.getPlacementForEntity(biomegenbase$spawnlistentry.entityClass), worldServerIn, blockpos1)) {
/*     */                       EntityLiving entityliving;
/*     */                       
/*     */                       try {
/* 154 */                         entityliving = this.mapSampleEntitiesByClass.get(biomegenbase$spawnlistentry.entityClass);
/*     */                         
/* 156 */                         if (entityliving == null) {
/* 157 */                           entityliving = biomegenbase$spawnlistentry.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldServerIn });
/* 158 */                           this.mapSampleEntitiesByClass.put(biomegenbase$spawnlistentry.entityClass, entityliving);
/*     */                         } 
/* 160 */                       } catch (Exception exception1) {
/* 161 */                         exception1.printStackTrace();
/* 162 */                         return j4;
/*     */                       } 
/*     */                       
/* 165 */                       entityliving.setLocationAndAngles(f, i3, f1, worldServerIn.rand.nextFloat() * 360.0F, 0.0F);
/* 166 */                       boolean flag2 = Reflector.ForgeEventFactory_canEntitySpawn.exists() ? ReflectorForge.canEntitySpawn(entityliving, worldServerIn, f, i3, f1) : ((entityliving.getCanSpawnHere() && entityliving.isNotColliding()));
/*     */                       
/* 168 */                       if (flag2) {
/* 169 */                         this.mapSampleEntitiesByClass.remove(biomegenbase$spawnlistentry.entityClass);
/*     */                         
/* 171 */                         if (!ReflectorForge.doSpecialSpawn(entityliving, worldServerIn, f, i3, f1)) {
/* 172 */                           ientitylivingdata = entityliving.onInitialSpawn(worldServerIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), ientitylivingdata);
/*     */                         }
/*     */                         
/* 175 */                         if (entityliving.isNotColliding()) {
/* 176 */                           j2++;
/* 177 */                           worldServerIn.spawnEntityInWorld((Entity)entityliving);
/*     */                         } 
/*     */                         
/* 180 */                         int i4 = Reflector.ForgeEventFactory_getMaxSpawnPackSize.exists() ? Reflector.callInt(Reflector.ForgeEventFactory_getMaxSpawnPackSize, new Object[] { entityliving }) : entityliving.getMaxSpawnedInChunk();
/*     */                         
/* 182 */                         if (j2 >= i4) {
/*     */                           break label117;
/*     */                         }
/*     */                       } 
/*     */                       
/* 187 */                       j4 += j2;
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */     
/* 198 */     return j4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static BlockPos getRandomChunkPosition(World worldIn, int x, int z) {
/* 203 */     Chunk chunk = worldIn.getChunkFromChunkCoords(x, z);
/* 204 */     int i = x * 16 + worldIn.rand.nextInt(16);
/* 205 */     int j = z * 16 + worldIn.rand.nextInt(16);
/* 206 */     int k = MathHelper.roundUp(chunk.getHeight(new BlockPos(i, 0, j)) + 1, 16);
/* 207 */     int l = worldIn.rand.nextInt((k > 0) ? k : (chunk.getTopFilledSegment() + 16 - 1));
/* 208 */     return new BlockPos(i, l, j);
/*     */   }
/*     */   
/*     */   private static BlockPosM getRandomChunkPosition(World p_getRandomChunkPosition_0_, int p_getRandomChunkPosition_1_, int p_getRandomChunkPosition_2_, BlockPosM p_getRandomChunkPosition_3_) {
/* 212 */     Chunk chunk = p_getRandomChunkPosition_0_.getChunkFromChunkCoords(p_getRandomChunkPosition_1_, p_getRandomChunkPosition_2_);
/* 213 */     int i = p_getRandomChunkPosition_1_ * 16 + p_getRandomChunkPosition_0_.rand.nextInt(16);
/* 214 */     int j = p_getRandomChunkPosition_2_ * 16 + p_getRandomChunkPosition_0_.rand.nextInt(16);
/* 215 */     int k = MathHelper.roundUp(chunk.getHeightValue(i & 0xF, j & 0xF) + 1, 16);
/* 216 */     int l = p_getRandomChunkPosition_0_.rand.nextInt((k > 0) ? k : (chunk.getTopFilledSegment() + 16 - 1));
/* 217 */     p_getRandomChunkPosition_3_.setXyz(i, l, j);
/* 218 */     return p_getRandomChunkPosition_3_;
/*     */   }
/*     */   
/*     */   public static boolean canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType spawnPlacementTypeIn, World worldIn, BlockPos pos) {
/* 222 */     if (!worldIn.getWorldBorder().contains(pos))
/* 223 */       return false; 
/* 224 */     if (spawnPlacementTypeIn == null) {
/* 225 */       return false;
/*     */     }
/* 227 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 229 */     if (spawnPlacementTypeIn == EntityLiving.SpawnPlacementType.IN_WATER) {
/* 230 */       return (block.getMaterial().isLiquid() && worldIn.getBlockState(pos.down()).getBlock().getMaterial().isLiquid() && !worldIn.getBlockState(pos.up()).getBlock().isNormalCube());
/*     */     }
/* 232 */     BlockPos blockpos = pos.down();
/* 233 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 234 */     boolean flag = Reflector.ForgeBlock_canCreatureSpawn.exists() ? Reflector.callBoolean(iblockstate.getBlock(), Reflector.ForgeBlock_canCreatureSpawn, new Object[] { worldIn, blockpos, spawnPlacementTypeIn }) : World.doesBlockHaveSolidTopSurface(worldIn, blockpos);
/*     */     
/* 236 */     if (!flag) {
/* 237 */       return false;
/*     */     }
/* 239 */     Block block1 = worldIn.getBlockState(blockpos).getBlock();
/* 240 */     boolean flag1 = (block1 != Blocks.bedrock && block1 != Blocks.barrier);
/* 241 */     return (flag1 && !block.isNormalCube() && !block.getMaterial().isLiquid() && !worldIn.getBlockState(pos.up()).getBlock().isNormalCube());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void performWorldGenSpawning(World worldIn, BiomeGenBase biomeIn, int p_77191_2_, int p_77191_3_, int p_77191_4_, int p_77191_5_, Random randomIn) {
/* 251 */     List<BiomeGenBase.SpawnListEntry> list = biomeIn.getSpawnableList(EnumCreatureType.CREATURE);
/*     */     
/* 253 */     if (!list.isEmpty())
/* 254 */       while (randomIn.nextFloat() < biomeIn.getSpawningChance()) {
/* 255 */         BiomeGenBase.SpawnListEntry biomegenbase$spawnlistentry = (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(worldIn.rand, list);
/* 256 */         int i = biomegenbase$spawnlistentry.minGroupCount + randomIn.nextInt(1 + biomegenbase$spawnlistentry.maxGroupCount - biomegenbase$spawnlistentry.minGroupCount);
/* 257 */         IEntityLivingData ientitylivingdata = null;
/* 258 */         int j = p_77191_2_ + randomIn.nextInt(p_77191_4_);
/* 259 */         int k = p_77191_3_ + randomIn.nextInt(p_77191_5_);
/* 260 */         int l = j;
/* 261 */         int i1 = k;
/*     */         
/* 263 */         for (int j1 = 0; j1 < i; j1++) {
/* 264 */           boolean flag = false;
/*     */           
/* 266 */           for (int k1 = 0; !flag && k1 < 4; k1++) {
/* 267 */             BlockPos blockpos = worldIn.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k));
/*     */             
/* 269 */             if (canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, worldIn, blockpos)) {
/*     */               EntityLiving entityliving;
/*     */               
/*     */               try {
/* 273 */                 entityliving = biomegenbase$spawnlistentry.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/* 274 */               } catch (Exception exception1) {
/* 275 */                 exception1.printStackTrace();
/*     */                 
/*     */                 continue;
/*     */               } 
/* 279 */               if (Reflector.ForgeEventFactory_canEntitySpawn.exists()) {
/* 280 */                 Object object = Reflector.call(Reflector.ForgeEventFactory_canEntitySpawn, new Object[] { entityliving, worldIn, Float.valueOf(j + 0.5F), Integer.valueOf(blockpos.getY()), Float.valueOf(k + 0.5F) });
/*     */                 
/* 282 */                 if (object == ReflectorForge.EVENT_RESULT_DENY) {
/*     */                   continue;
/*     */                 }
/*     */               } 
/*     */               
/* 287 */               entityliving.setLocationAndAngles((j + 0.5F), blockpos.getY(), (k + 0.5F), randomIn.nextFloat() * 360.0F, 0.0F);
/* 288 */               worldIn.spawnEntityInWorld((Entity)entityliving);
/* 289 */               ientitylivingdata = entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), ientitylivingdata);
/* 290 */               flag = true;
/*     */             } 
/*     */             
/* 293 */             j += randomIn.nextInt(5) - randomIn.nextInt(5);
/*     */             
/* 295 */             for (k += randomIn.nextInt(5) - randomIn.nextInt(5); j < p_77191_2_ || j >= p_77191_2_ + p_77191_4_ || k < p_77191_3_ || k >= p_77191_3_ + p_77191_4_; k = i1 + randomIn.nextInt(5) - randomIn.nextInt(5))
/* 296 */               j = l + randomIn.nextInt(5) - randomIn.nextInt(5); 
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\SpawnerAnimals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */