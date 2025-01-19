/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.MovingSoundMinecart;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.client.particle.EntityFX;
/*     */ import net.minecraft.client.particle.EntityFirework;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.MapStorage;
/*     */ import net.minecraft.world.storage.SaveDataMemoryStorage;
/*     */ import net.minecraft.world.storage.SaveHandlerMP;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import net.optifine.CustomGuis;
/*     */ import net.optifine.DynamicLights;
/*     */ import net.optifine.override.PlayerControllerOF;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldClient
/*     */   extends World
/*     */ {
/*     */   private NetHandlerPlayClient sendQueue;
/*     */   private ChunkProviderClient clientChunkProvider;
/*  55 */   private final Set<Entity> entityList = Sets.newHashSet();
/*  56 */   private final Set<Entity> entitySpawnQueue = Sets.newHashSet();
/*  57 */   private final Minecraft mc = Minecraft.getMinecraft();
/*  58 */   private final Set<ChunkCoordIntPair> previousActiveChunkSet = Sets.newHashSet();
/*     */   private boolean playerUpdate = false;
/*     */   
/*     */   public WorldClient(NetHandlerPlayClient netHandler, WorldSettings settings, int dimension, EnumDifficulty difficulty, Profiler profilerIn) {
/*  62 */     super((ISaveHandler)new SaveHandlerMP(), new WorldInfo(settings, "MpServer"), WorldProvider.getProviderForDimension(dimension), profilerIn, true);
/*  63 */     this.sendQueue = netHandler;
/*  64 */     getWorldInfo().setDifficulty(difficulty);
/*  65 */     this.provider.registerWorld(this);
/*  66 */     setSpawnPoint(new BlockPos(8, 64, 8));
/*  67 */     this.chunkProvider = createChunkProvider();
/*  68 */     this.mapStorage = (MapStorage)new SaveDataMemoryStorage();
/*  69 */     calculateInitialSkylight();
/*  70 */     calculateInitialWeather();
/*  71 */     Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { this });
/*     */     
/*  73 */     if (this.mc.playerController != null && this.mc.playerController.getClass() == PlayerControllerMP.class) {
/*  74 */       this.mc.playerController = (PlayerControllerMP)new PlayerControllerOF(this.mc, netHandler);
/*  75 */       CustomGuis.setPlayerControllerOF((PlayerControllerOF)this.mc.playerController);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/*  83 */     super.tick();
/*  84 */     setTotalWorldTime(getTotalWorldTime() + 1L);
/*     */     
/*  86 */     if (getGameRules().getBoolean("doDaylightCycle")) {
/*  87 */       setWorldTime(getWorldTime() + 1L);
/*     */     }
/*     */     
/*  90 */     this.theProfiler.startSection("reEntryProcessing");
/*     */     
/*  92 */     for (int i = 0; i < 10 && !this.entitySpawnQueue.isEmpty(); i++) {
/*  93 */       Entity entity = this.entitySpawnQueue.iterator().next();
/*  94 */       this.entitySpawnQueue.remove(entity);
/*     */       
/*  96 */       if (!this.loadedEntityList.contains(entity)) {
/*  97 */         spawnEntityInWorld(entity);
/*     */       }
/*     */     } 
/*     */     
/* 101 */     this.theProfiler.endStartSection("chunkCache");
/* 102 */     this.clientChunkProvider.unloadQueuedChunks();
/* 103 */     this.theProfiler.endStartSection("blocks");
/* 104 */     updateBlocks();
/* 105 */     this.theProfiler.endSection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidateBlockReceiveRegion(int x1, int y1, int z1, int x2, int y2, int z2) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IChunkProvider createChunkProvider() {
/* 126 */     this.clientChunkProvider = new ChunkProviderClient(this);
/* 127 */     return this.clientChunkProvider;
/*     */   }
/*     */   
/*     */   protected void updateBlocks() {
/* 131 */     super.updateBlocks();
/* 132 */     this.previousActiveChunkSet.retainAll(this.activeChunkSet);
/*     */     
/* 134 */     if (this.previousActiveChunkSet.size() == this.activeChunkSet.size()) {
/* 135 */       this.previousActiveChunkSet.clear();
/*     */     }
/*     */     
/* 138 */     int i = 0;
/*     */     
/* 140 */     for (ChunkCoordIntPair chunkcoordintpair : this.activeChunkSet) {
/* 141 */       if (!this.previousActiveChunkSet.contains(chunkcoordintpair)) {
/* 142 */         int j = chunkcoordintpair.chunkXPos * 16;
/* 143 */         int k = chunkcoordintpair.chunkZPos * 16;
/* 144 */         this.theProfiler.startSection("getChunk");
/* 145 */         Chunk chunk = getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
/* 146 */         playMoodSoundAndCheckLight(j, k, chunk);
/* 147 */         this.theProfiler.endSection();
/* 148 */         this.previousActiveChunkSet.add(chunkcoordintpair);
/* 149 */         i++;
/*     */         
/* 151 */         if (i >= 10) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doPreChunk(int chuncX, int chuncZ, boolean loadChunk) {
/* 159 */     if (loadChunk) {
/* 160 */       this.clientChunkProvider.loadChunk(chuncX, chuncZ);
/*     */     } else {
/* 162 */       this.clientChunkProvider.unloadChunk(chuncX, chuncZ);
/*     */     } 
/*     */     
/* 165 */     if (!loadChunk) {
/* 166 */       markBlockRangeForRenderUpdate(chuncX * 16, 0, chuncZ * 16, chuncX * 16 + 15, 256, chuncZ * 16 + 15);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean spawnEntityInWorld(Entity entityIn) {
/* 174 */     boolean flag = super.spawnEntityInWorld(entityIn);
/* 175 */     this.entityList.add(entityIn);
/*     */     
/* 177 */     if (!flag) {
/* 178 */       this.entitySpawnQueue.add(entityIn);
/* 179 */     } else if (entityIn instanceof EntityMinecart) {
/* 180 */       this.mc.getSoundHandler().playSound((ISound)new MovingSoundMinecart((EntityMinecart)entityIn));
/*     */     } 
/*     */     
/* 183 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEntity(Entity entityIn) {
/* 190 */     super.removeEntity(entityIn);
/* 191 */     this.entityList.remove(entityIn);
/*     */   }
/*     */   
/*     */   protected void onEntityAdded(Entity entityIn) {
/* 195 */     super.onEntityAdded(entityIn);
/*     */     
/* 197 */     if (this.entitySpawnQueue.contains(entityIn)) {
/* 198 */       this.entitySpawnQueue.remove(entityIn);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void onEntityRemoved(Entity entityIn) {
/* 203 */     super.onEntityRemoved(entityIn);
/* 204 */     boolean flag = false;
/*     */     
/* 206 */     if (this.entityList.contains(entityIn)) {
/* 207 */       if (entityIn.isEntityAlive()) {
/* 208 */         this.entitySpawnQueue.add(entityIn);
/* 209 */         flag = true;
/*     */       } else {
/* 211 */         this.entityList.remove(entityIn);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntityToWorld(int entityID, Entity entityToSpawn) {
/* 223 */     Entity entity = getEntityByID(entityID);
/*     */     
/* 225 */     if (entity != null) {
/* 226 */       removeEntity(entity);
/*     */     }
/*     */     
/* 229 */     this.entityList.add(entityToSpawn);
/* 230 */     entityToSpawn.setEntityId(entityID);
/*     */     
/* 232 */     if (!spawnEntityInWorld(entityToSpawn)) {
/* 233 */       this.entitySpawnQueue.add(entityToSpawn);
/*     */     }
/*     */     
/* 236 */     this.entitiesById.addKey(entityID, entityToSpawn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity getEntityByID(int id) {
/* 243 */     return (id == this.mc.thePlayer.getEntityId()) ? (Entity)this.mc.thePlayer : super.getEntityByID(id);
/*     */   }
/*     */   
/*     */   public Entity removeEntityFromWorld(int entityID) {
/* 247 */     Entity entity = (Entity)this.entitiesById.removeObject(entityID);
/*     */     
/* 249 */     if (entity != null) {
/* 250 */       this.entityList.remove(entity);
/* 251 */       removeEntity(entity);
/*     */     } 
/*     */     
/* 254 */     return entity;
/*     */   }
/*     */   
/*     */   public boolean invalidateRegionAndSetBlock(BlockPos pos, IBlockState state) {
/* 258 */     int i = pos.getX();
/* 259 */     int j = pos.getY();
/* 260 */     int k = pos.getZ();
/* 261 */     invalidateBlockReceiveRegion(i, j, k, i, j, k);
/* 262 */     return super.setBlockState(pos, state, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendQuittingDisconnectingPacket() {
/* 269 */     this.sendQueue.getNetworkManager().closeChannel((IChatComponent)new ChatComponentText("Quitting"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateWeather() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getRenderDistanceChunks() {
/* 279 */     return this.mc.gameSettings.renderDistanceChunks;
/*     */   }
/*     */   
/*     */   public void doVoidFogParticles(int posX, int posY, int posZ) {
/* 283 */     int i = 16;
/* 284 */     Random random = new Random();
/* 285 */     ItemStack itemstack = this.mc.thePlayer.getHeldItem();
/* 286 */     boolean flag = (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE && itemstack != null && Block.getBlockFromItem(itemstack.getItem()) == Blocks.barrier);
/* 287 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 289 */     for (int j = 0; j < 1000; j++) {
/* 290 */       int k = posX + this.rand.nextInt(i) - this.rand.nextInt(i);
/* 291 */       int l = posY + this.rand.nextInt(i) - this.rand.nextInt(i);
/* 292 */       int i1 = posZ + this.rand.nextInt(i) - this.rand.nextInt(i);
/* 293 */       blockpos$mutableblockpos.set(k, l, i1);
/* 294 */       IBlockState iblockstate = getBlockState((BlockPos)blockpos$mutableblockpos);
/* 295 */       iblockstate.getBlock().randomDisplayTick(this, (BlockPos)blockpos$mutableblockpos, iblockstate, random);
/*     */       
/* 297 */       if (flag && iblockstate.getBlock() == Blocks.barrier) {
/* 298 */         spawnParticle(EnumParticleTypes.BARRIER, (k + 0.5F), (l + 0.5F), (i1 + 0.5F), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllEntities() {
/* 307 */     this.loadedEntityList.removeAll(this.unloadedEntityList);
/*     */     
/* 309 */     for (int i = 0; i < this.unloadedEntityList.size(); i++) {
/* 310 */       Entity entity = this.unloadedEntityList.get(i);
/* 311 */       int j = entity.chunkCoordX;
/* 312 */       int k = entity.chunkCoordZ;
/*     */       
/* 314 */       if (entity.addedToChunk && isChunkLoaded(j, k, true)) {
/* 315 */         getChunkFromChunkCoords(j, k).removeEntity(entity);
/*     */       }
/*     */     } 
/*     */     
/* 319 */     for (int l = 0; l < this.unloadedEntityList.size(); l++) {
/* 320 */       onEntityRemoved(this.unloadedEntityList.get(l));
/*     */     }
/*     */     
/* 323 */     this.unloadedEntityList.clear();
/*     */     
/* 325 */     for (int i1 = 0; i1 < this.loadedEntityList.size(); i1++) {
/* 326 */       Entity entity1 = this.loadedEntityList.get(i1);
/*     */       
/* 328 */       if (entity1.ridingEntity != null) {
/* 329 */         if (!entity1.ridingEntity.isDead && entity1.ridingEntity.riddenByEntity == entity1) {
/*     */           continue;
/*     */         }
/*     */         
/* 333 */         entity1.ridingEntity.riddenByEntity = null;
/* 334 */         entity1.ridingEntity = null;
/*     */       } 
/*     */       
/* 337 */       if (entity1.isDead) {
/* 338 */         int j1 = entity1.chunkCoordX;
/* 339 */         int k1 = entity1.chunkCoordZ;
/*     */         
/* 341 */         if (entity1.addedToChunk && isChunkLoaded(j1, k1, true)) {
/* 342 */           getChunkFromChunkCoords(j1, k1).removeEntity(entity1);
/*     */         }
/*     */         
/* 345 */         this.loadedEntityList.remove(i1--);
/* 346 */         onEntityRemoved(entity1);
/*     */       } 
/*     */       continue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory addWorldInfoToCrashReport(CrashReport report) {
/* 355 */     CrashReportCategory crashreportcategory = super.addWorldInfoToCrashReport(report);
/* 356 */     crashreportcategory.addCrashSectionCallable("Forced entities", new Callable<String>() {
/*     */           public String call() {
/* 358 */             return String.valueOf(WorldClient.this.entityList.size()) + " total; " + WorldClient.this.entityList.toString();
/*     */           }
/*     */         });
/* 361 */     crashreportcategory.addCrashSectionCallable("Retry entities", new Callable<String>() {
/*     */           public String call() {
/* 363 */             return String.valueOf(WorldClient.this.entitySpawnQueue.size()) + " total; " + WorldClient.this.entitySpawnQueue.toString();
/*     */           }
/*     */         });
/* 366 */     crashreportcategory.addCrashSectionCallable("Server brand", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 368 */             return WorldClient.this.mc.thePlayer.getClientBrand();
/*     */           }
/*     */         });
/* 371 */     crashreportcategory.addCrashSectionCallable("Server type", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 373 */             return (WorldClient.this.mc.getIntegratedServer() == null) ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
/*     */           }
/*     */         });
/* 376 */     return crashreportcategory;
/*     */   }
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
/*     */   public void playSoundAtPos(BlockPos pos, String soundName, float volume, float pitch, boolean distanceDelay) {
/* 389 */     playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, soundName, volume, pitch, distanceDelay);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playSound(double x, double y, double z, String soundName, float volume, float pitch, boolean distanceDelay) {
/* 396 */     double d0 = this.mc.getRenderViewEntity().getDistanceSq(x, y, z);
/* 397 */     PositionedSoundRecord positionedsoundrecord = new PositionedSoundRecord(new ResourceLocation(soundName), volume, pitch, (float)x, (float)y, (float)z);
/*     */     
/* 399 */     if (distanceDelay && d0 > 100.0D) {
/* 400 */       double d1 = Math.sqrt(d0) / 40.0D;
/* 401 */       this.mc.getSoundHandler().playDelayedSound((ISound)positionedsoundrecord, (int)(d1 * 20.0D));
/*     */     } else {
/* 403 */       this.mc.getSoundHandler().playSound((ISound)positionedsoundrecord);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, NBTTagCompound compund) {
/* 408 */     this.mc.effectRenderer.addEffect((EntityFX)new EntityFirework.StarterFX(this, x, y, z, motionX, motionY, motionZ, this.mc.effectRenderer, compund));
/*     */   }
/*     */   
/*     */   public void setWorldScoreboard(Scoreboard scoreboardIn) {
/* 412 */     this.worldScoreboard = scoreboardIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldTime(long time) {
/* 419 */     if (time < 0L) {
/* 420 */       time = -time;
/* 421 */       getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
/*     */     } else {
/* 423 */       getGameRules().setOrCreateGameRule("doDaylightCycle", "true");
/*     */     } 
/*     */     
/* 426 */     super.setWorldTime(time);
/*     */   }
/*     */   
/*     */   public int getCombinedLight(BlockPos pos, int lightValue) {
/* 430 */     int i = super.getCombinedLight(pos, lightValue);
/*     */     
/* 432 */     if (Config.isDynamicLights()) {
/* 433 */       i = DynamicLights.getCombinedLight(pos, i);
/*     */     }
/*     */     
/* 436 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
/* 445 */     this.playerUpdate = isPlayerActing();
/* 446 */     boolean flag = super.setBlockState(pos, newState, flags);
/* 447 */     this.playerUpdate = false;
/* 448 */     return flag;
/*     */   }
/*     */   
/*     */   private boolean isPlayerActing() {
/* 452 */     if (this.mc.playerController instanceof PlayerControllerOF) {
/* 453 */       PlayerControllerOF playercontrollerof = (PlayerControllerOF)this.mc.playerController;
/* 454 */       return playercontrollerof.isActing();
/*     */     } 
/* 456 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPlayerUpdate() {
/* 461 */     return this.playerUpdate;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\multiplayer\WorldClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */