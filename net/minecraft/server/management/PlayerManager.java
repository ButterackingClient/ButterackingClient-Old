/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S21PacketChunkData;
/*     */ import net.minecraft.network.play.server.S22PacketMultiBlockChange;
/*     */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.LongHashMap;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.ChunkPosComparator;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class PlayerManager
/*     */ {
/*  35 */   private static final Logger pmLogger = LogManager.getLogger();
/*     */   private final WorldServer theWorldServer;
/*  37 */   private final List<EntityPlayerMP> players = Lists.newArrayList();
/*  38 */   private final LongHashMap<PlayerInstance> playerInstances = new LongHashMap();
/*  39 */   private final List<PlayerInstance> playerInstancesToUpdate = Lists.newArrayList();
/*  40 */   private final List<PlayerInstance> playerInstanceList = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int playerViewRadius;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long previousTotalWorldTime;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private final int[][] xzDirectionsConst = new int[][] { { 1 }, { 0, 1 }, { -1 }, { 0, -1 } };
/*  56 */   private final Map<EntityPlayerMP, Set<ChunkCoordIntPair>> mapPlayerPendingEntries = new HashMap<>();
/*     */   
/*     */   public PlayerManager(WorldServer serverWorld) {
/*  59 */     this.theWorldServer = serverWorld;
/*  60 */     setPlayerViewRadius(serverWorld.getMinecraftServer().getConfigurationManager().getViewDistance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldServer getWorldServer() {
/*  67 */     return this.theWorldServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePlayerInstances() {
/*  74 */     Set<Map.Entry<EntityPlayerMP, Set<ChunkCoordIntPair>>> set = this.mapPlayerPendingEntries.entrySet();
/*  75 */     Iterator<Map.Entry<EntityPlayerMP, Set<ChunkCoordIntPair>>> iterator = set.iterator();
/*     */     
/*  77 */     while (iterator.hasNext()) {
/*  78 */       Map.Entry<EntityPlayerMP, Set<ChunkCoordIntPair>> entry = iterator.next();
/*  79 */       Set<ChunkCoordIntPair> set1 = entry.getValue();
/*     */       
/*  81 */       if (!set1.isEmpty()) {
/*  82 */         EntityPlayerMP entityplayermp = entry.getKey();
/*     */         
/*  84 */         if (entityplayermp.worldObj != this.theWorldServer) {
/*  85 */           iterator.remove(); continue;
/*     */         } 
/*  87 */         int i = this.playerViewRadius / 3 + 1;
/*     */         
/*  89 */         if (!Config.isLazyChunkLoading()) {
/*  90 */           i = this.playerViewRadius * 2 + 1;
/*     */         }
/*     */         
/*  93 */         for (ChunkCoordIntPair chunkcoordintpair : getNearest(set1, entityplayermp, i)) {
/*  94 */           PlayerInstance playermanager$playerinstance = getPlayerInstance(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos, true);
/*  95 */           playermanager$playerinstance.addPlayer(entityplayermp);
/*  96 */           set1.remove(chunkcoordintpair);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 102 */     long j = this.theWorldServer.getTotalWorldTime();
/*     */     
/* 104 */     if (j - this.previousTotalWorldTime > 8000L) {
/* 105 */       this.previousTotalWorldTime = j;
/*     */       
/* 107 */       for (int k = 0; k < this.playerInstanceList.size(); k++) {
/* 108 */         PlayerInstance playermanager$playerinstance1 = this.playerInstanceList.get(k);
/* 109 */         playermanager$playerinstance1.onUpdate();
/* 110 */         playermanager$playerinstance1.processChunk();
/*     */       } 
/*     */     } else {
/* 113 */       for (int l = 0; l < this.playerInstancesToUpdate.size(); l++) {
/* 114 */         PlayerInstance playermanager$playerinstance2 = this.playerInstancesToUpdate.get(l);
/* 115 */         playermanager$playerinstance2.onUpdate();
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     this.playerInstancesToUpdate.clear();
/*     */     
/* 121 */     if (this.players.isEmpty()) {
/* 122 */       WorldProvider worldprovider = this.theWorldServer.provider;
/*     */       
/* 124 */       if (!worldprovider.canRespawnHere()) {
/* 125 */         this.theWorldServer.theChunkProviderServer.unloadAllChunks();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasPlayerInstance(int chunkX, int chunkZ) {
/* 131 */     long i = chunkX + 2147483647L | chunkZ + 2147483647L << 32L;
/* 132 */     return (this.playerInstances.getValueByKey(i) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PlayerInstance getPlayerInstance(int chunkX, int chunkZ, boolean createIfAbsent) {
/* 139 */     long i = chunkX + 2147483647L | chunkZ + 2147483647L << 32L;
/* 140 */     PlayerInstance playermanager$playerinstance = (PlayerInstance)this.playerInstances.getValueByKey(i);
/*     */     
/* 142 */     if (playermanager$playerinstance == null && createIfAbsent) {
/* 143 */       playermanager$playerinstance = new PlayerInstance(chunkX, chunkZ);
/* 144 */       this.playerInstances.add(i, playermanager$playerinstance);
/* 145 */       this.playerInstanceList.add(playermanager$playerinstance);
/*     */     } 
/*     */     
/* 148 */     return playermanager$playerinstance;
/*     */   }
/*     */   
/*     */   public void markBlockForUpdate(BlockPos pos) {
/* 152 */     int i = pos.getX() >> 4;
/* 153 */     int j = pos.getZ() >> 4;
/* 154 */     PlayerInstance playermanager$playerinstance = getPlayerInstance(i, j, false);
/*     */     
/* 156 */     if (playermanager$playerinstance != null) {
/* 157 */       playermanager$playerinstance.flagChunkForUpdate(pos.getX() & 0xF, pos.getY(), pos.getZ() & 0xF);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayer(EntityPlayerMP player) {
/* 165 */     int i = (int)player.posX >> 4;
/* 166 */     int j = (int)player.posZ >> 4;
/* 167 */     player.managedPosX = player.posX;
/* 168 */     player.managedPosZ = player.posZ;
/* 169 */     int k = Math.min(this.playerViewRadius, 8);
/* 170 */     int l = i - k;
/* 171 */     int i1 = i + k;
/* 172 */     int j1 = j - k;
/* 173 */     int k1 = j + k;
/* 174 */     Set<ChunkCoordIntPair> set = getPendingEntriesSafe(player);
/*     */     
/* 176 */     for (int l1 = i - this.playerViewRadius; l1 <= i + this.playerViewRadius; l1++) {
/* 177 */       for (int i2 = j - this.playerViewRadius; i2 <= j + this.playerViewRadius; i2++) {
/* 178 */         if (l1 >= l && l1 <= i1 && i2 >= j1 && i2 <= k1) {
/* 179 */           getPlayerInstance(l1, i2, true).addPlayer(player);
/*     */         } else {
/* 181 */           set.add(new ChunkCoordIntPair(l1, i2));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 186 */     this.players.add(player);
/* 187 */     filterChunkLoadQueue(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void filterChunkLoadQueue(EntityPlayerMP player) {
/* 194 */     List<ChunkCoordIntPair> list = Lists.newArrayList(player.loadedChunks);
/* 195 */     int i = 0;
/* 196 */     int j = this.playerViewRadius;
/* 197 */     int k = (int)player.posX >> 4;
/* 198 */     int l = (int)player.posZ >> 4;
/* 199 */     int i1 = 0;
/* 200 */     int j1 = 0;
/* 201 */     ChunkCoordIntPair chunkcoordintpair = (getPlayerInstance(k, l, true)).chunkCoords;
/* 202 */     player.loadedChunks.clear();
/*     */     
/* 204 */     if (list.contains(chunkcoordintpair)) {
/* 205 */       player.loadedChunks.add(chunkcoordintpair);
/*     */     }
/*     */     
/* 208 */     for (int k1 = 1; k1 <= j * 2; k1++) {
/* 209 */       for (int l1 = 0; l1 < 2; l1++) {
/* 210 */         int[] aint = this.xzDirectionsConst[i++ % 4];
/*     */         
/* 212 */         for (int i2 = 0; i2 < k1; i2++) {
/* 213 */           i1 += aint[0];
/* 214 */           j1 += aint[1];
/* 215 */           chunkcoordintpair = (getPlayerInstance(k + i1, l + j1, true)).chunkCoords;
/*     */           
/* 217 */           if (list.contains(chunkcoordintpair)) {
/* 218 */             player.loadedChunks.add(chunkcoordintpair);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     i %= 4;
/*     */     
/* 226 */     for (int j2 = 0; j2 < j * 2; j2++) {
/* 227 */       i1 += this.xzDirectionsConst[i][0];
/* 228 */       j1 += this.xzDirectionsConst[i][1];
/* 229 */       chunkcoordintpair = (getPlayerInstance(k + i1, l + j1, true)).chunkCoords;
/*     */       
/* 231 */       if (list.contains(chunkcoordintpair)) {
/* 232 */         player.loadedChunks.add(chunkcoordintpair);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayer(EntityPlayerMP player) {
/* 241 */     this.mapPlayerPendingEntries.remove(player);
/* 242 */     int i = (int)player.managedPosX >> 4;
/* 243 */     int j = (int)player.managedPosZ >> 4;
/*     */     
/* 245 */     for (int k = i - this.playerViewRadius; k <= i + this.playerViewRadius; k++) {
/* 246 */       for (int l = j - this.playerViewRadius; l <= j + this.playerViewRadius; l++) {
/* 247 */         PlayerInstance playermanager$playerinstance = getPlayerInstance(k, l, false);
/*     */         
/* 249 */         if (playermanager$playerinstance != null) {
/* 250 */           playermanager$playerinstance.removePlayer(player);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 255 */     this.players.remove(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean overlaps(int x1, int z1, int x2, int z2, int radius) {
/* 263 */     int i = x1 - x2;
/* 264 */     int j = z1 - z2;
/* 265 */     return (i >= -radius && i <= radius) ? ((j >= -radius && j <= radius)) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMountedMovingPlayer(EntityPlayerMP player) {
/* 272 */     int i = (int)player.posX >> 4;
/* 273 */     int j = (int)player.posZ >> 4;
/* 274 */     double d0 = player.managedPosX - player.posX;
/* 275 */     double d1 = player.managedPosZ - player.posZ;
/* 276 */     double d2 = d0 * d0 + d1 * d1;
/*     */     
/* 278 */     if (d2 >= 64.0D) {
/* 279 */       int k = (int)player.managedPosX >> 4;
/* 280 */       int l = (int)player.managedPosZ >> 4;
/* 281 */       int i1 = this.playerViewRadius;
/* 282 */       int j1 = i - k;
/* 283 */       int k1 = j - l;
/*     */       
/* 285 */       if (j1 != 0 || k1 != 0) {
/* 286 */         Set<ChunkCoordIntPair> set = getPendingEntriesSafe(player);
/*     */         
/* 288 */         for (int l1 = i - i1; l1 <= i + i1; l1++) {
/* 289 */           for (int i2 = j - i1; i2 <= j + i1; i2++) {
/* 290 */             if (!overlaps(l1, i2, k, l, i1)) {
/* 291 */               if (Config.isLazyChunkLoading()) {
/* 292 */                 set.add(new ChunkCoordIntPair(l1, i2));
/*     */               } else {
/* 294 */                 getPlayerInstance(l1, i2, true).addPlayer(player);
/*     */               } 
/*     */             }
/*     */             
/* 298 */             if (!overlaps(l1 - j1, i2 - k1, i, j, i1)) {
/* 299 */               set.remove(new ChunkCoordIntPair(l1 - j1, i2 - k1));
/* 300 */               PlayerInstance playermanager$playerinstance = getPlayerInstance(l1 - j1, i2 - k1, false);
/*     */               
/* 302 */               if (playermanager$playerinstance != null) {
/* 303 */                 playermanager$playerinstance.removePlayer(player);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 309 */         filterChunkLoadQueue(player);
/* 310 */         player.managedPosX = player.posX;
/* 311 */         player.managedPosZ = player.posZ;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPlayerWatchingChunk(EntityPlayerMP player, int chunkX, int chunkZ) {
/* 317 */     PlayerInstance playermanager$playerinstance = getPlayerInstance(chunkX, chunkZ, false);
/* 318 */     return (playermanager$playerinstance != null && playermanager$playerinstance.playersWatchingChunk.contains(player) && !player.loadedChunks.contains(playermanager$playerinstance.chunkCoords));
/*     */   }
/*     */   
/*     */   public void setPlayerViewRadius(int radius) {
/* 322 */     radius = MathHelper.clamp_int(radius, 3, 64);
/*     */     
/* 324 */     if (radius != this.playerViewRadius) {
/* 325 */       int i = radius - this.playerViewRadius;
/*     */       
/* 327 */       for (EntityPlayerMP entityplayermp : Lists.newArrayList(this.players)) {
/* 328 */         int j = (int)entityplayermp.posX >> 4;
/* 329 */         int k = (int)entityplayermp.posZ >> 4;
/* 330 */         Set<ChunkCoordIntPair> set = getPendingEntriesSafe(entityplayermp);
/*     */         
/* 332 */         if (i > 0) {
/* 333 */           for (int j1 = j - radius; j1 <= j + radius; j1++) {
/* 334 */             for (int k1 = k - radius; k1 <= k + radius; k1++) {
/* 335 */               if (Config.isLazyChunkLoading()) {
/* 336 */                 set.add(new ChunkCoordIntPair(j1, k1));
/*     */               } else {
/* 338 */                 PlayerInstance playermanager$playerinstance1 = getPlayerInstance(j1, k1, true);
/*     */                 
/* 340 */                 if (!playermanager$playerinstance1.playersWatchingChunk.contains(entityplayermp))
/* 341 */                   playermanager$playerinstance1.addPlayer(entityplayermp); 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           continue;
/*     */         } 
/* 347 */         for (int l = j - this.playerViewRadius; l <= j + this.playerViewRadius; l++) {
/* 348 */           for (int i1 = k - this.playerViewRadius; i1 <= k + this.playerViewRadius; i1++) {
/* 349 */             if (!overlaps(l, i1, j, k, radius)) {
/* 350 */               set.remove(new ChunkCoordIntPair(l, i1));
/* 351 */               PlayerInstance playermanager$playerinstance = getPlayerInstance(l, i1, true);
/*     */               
/* 353 */               if (playermanager$playerinstance != null) {
/* 354 */                 playermanager$playerinstance.removePlayer(entityplayermp);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 362 */       this.playerViewRadius = radius;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFurthestViewableBlock(int distance) {
/* 370 */     return distance * 16 - 16;
/*     */   }
/*     */ 
/*     */   
/*     */   private PriorityQueue<ChunkCoordIntPair> getNearest(Set<ChunkCoordIntPair> p_getNearest_1_, EntityPlayerMP p_getNearest_2_, int p_getNearest_3_) {
/*     */     float f;
/* 376 */     for (f = p_getNearest_2_.rotationYaw + 90.0F; f <= -180.0F; f += 360.0F);
/*     */ 
/*     */ 
/*     */     
/* 380 */     while (f > 180.0F) {
/* 381 */       f -= 360.0F;
/*     */     }
/*     */     
/* 384 */     double d0 = f * 0.017453292519943295D;
/* 385 */     double d1 = p_getNearest_2_.rotationPitch;
/* 386 */     double d2 = d1 * 0.017453292519943295D;
/* 387 */     ChunkPosComparator chunkposcomparator = new ChunkPosComparator(p_getNearest_2_.chunkCoordX, p_getNearest_2_.chunkCoordZ, d0, d2);
/* 388 */     Comparator<ChunkCoordIntPair> comparator = Collections.reverseOrder((Comparator<ChunkCoordIntPair>)chunkposcomparator);
/* 389 */     PriorityQueue<ChunkCoordIntPair> priorityqueue = new PriorityQueue<>(p_getNearest_3_, comparator);
/*     */     
/* 391 */     for (ChunkCoordIntPair chunkcoordintpair : p_getNearest_1_) {
/* 392 */       if (priorityqueue.size() < p_getNearest_3_) {
/* 393 */         priorityqueue.add(chunkcoordintpair); continue;
/*     */       } 
/* 395 */       ChunkCoordIntPair chunkcoordintpair1 = priorityqueue.peek();
/*     */       
/* 397 */       if (chunkposcomparator.compare(chunkcoordintpair, chunkcoordintpair1) < 0) {
/* 398 */         priorityqueue.remove();
/* 399 */         priorityqueue.add(chunkcoordintpair);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 404 */     return priorityqueue;
/*     */   }
/*     */   
/*     */   private Set<ChunkCoordIntPair> getPendingEntriesSafe(EntityPlayerMP p_getPendingEntriesSafe_1_) {
/* 408 */     Set<ChunkCoordIntPair> set = this.mapPlayerPendingEntries.get(p_getPendingEntriesSafe_1_);
/*     */     
/* 410 */     if (set != null) {
/* 411 */       return set;
/*     */     }
/* 413 */     int i = Math.min(this.playerViewRadius, 8);
/* 414 */     int j = this.playerViewRadius * 2 + 1;
/* 415 */     int k = i * 2 + 1;
/* 416 */     int l = j * j - k * k;
/* 417 */     l = Math.max(l, 16);
/* 418 */     HashSet<ChunkCoordIntPair> hashset = new HashSet(l);
/* 419 */     this.mapPlayerPendingEntries.put(p_getPendingEntriesSafe_1_, hashset);
/* 420 */     return hashset;
/*     */   }
/*     */   
/*     */   class PlayerInstance
/*     */   {
/* 425 */     private final List<EntityPlayerMP> playersWatchingChunk = Lists.newArrayList();
/*     */     private final ChunkCoordIntPair chunkCoords;
/* 427 */     private short[] locationOfBlockChange = new short[64];
/*     */     private int numBlocksToUpdate;
/*     */     private int flagsYAreasToUpdate;
/*     */     private long previousWorldTime;
/*     */     
/*     */     public PlayerInstance(int chunkX, int chunkZ) {
/* 433 */       this.chunkCoords = new ChunkCoordIntPair(chunkX, chunkZ);
/* 434 */       (PlayerManager.this.getWorldServer()).theChunkProviderServer.loadChunk(chunkX, chunkZ);
/*     */     }
/*     */     
/*     */     public void addPlayer(EntityPlayerMP player) {
/* 438 */       if (this.playersWatchingChunk.contains(player)) {
/* 439 */         PlayerManager.pmLogger.debug("Failed to add player. {} already is in chunk {}, {}", new Object[] { player, Integer.valueOf(this.chunkCoords.chunkXPos), Integer.valueOf(this.chunkCoords.chunkZPos) });
/*     */       } else {
/* 441 */         if (this.playersWatchingChunk.isEmpty()) {
/* 442 */           this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
/*     */         }
/*     */         
/* 445 */         this.playersWatchingChunk.add(player);
/* 446 */         player.loadedChunks.add(this.chunkCoords);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void removePlayer(EntityPlayerMP player) {
/* 451 */       if (this.playersWatchingChunk.contains(player)) {
/* 452 */         Chunk chunk = PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
/*     */         
/* 454 */         if (chunk.isPopulated()) {
/* 455 */           player.playerNetServerHandler.sendPacket((Packet)new S21PacketChunkData(chunk, true, 0));
/*     */         }
/*     */         
/* 458 */         this.playersWatchingChunk.remove(player);
/* 459 */         player.loadedChunks.remove(this.chunkCoords);
/*     */         
/* 461 */         if (this.playersWatchingChunk.isEmpty()) {
/* 462 */           long i = this.chunkCoords.chunkXPos + 2147483647L | this.chunkCoords.chunkZPos + 2147483647L << 32L;
/* 463 */           increaseInhabitedTime(chunk);
/* 464 */           PlayerManager.this.playerInstances.remove(i);
/* 465 */           PlayerManager.this.playerInstanceList.remove(this);
/*     */           
/* 467 */           if (this.numBlocksToUpdate > 0) {
/* 468 */             PlayerManager.this.playerInstancesToUpdate.remove(this);
/*     */           }
/*     */           
/* 471 */           (PlayerManager.this.getWorldServer()).theChunkProviderServer.dropChunk(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void processChunk() {
/* 477 */       increaseInhabitedTime(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos));
/*     */     }
/*     */     
/*     */     private void increaseInhabitedTime(Chunk theChunk) {
/* 481 */       theChunk.setInhabitedTime(theChunk.getInhabitedTime() + PlayerManager.this.theWorldServer.getTotalWorldTime() - this.previousWorldTime);
/* 482 */       this.previousWorldTime = PlayerManager.this.theWorldServer.getTotalWorldTime();
/*     */     }
/*     */     
/*     */     public void flagChunkForUpdate(int x, int y, int z) {
/* 486 */       if (this.numBlocksToUpdate == 0) {
/* 487 */         PlayerManager.this.playerInstancesToUpdate.add(this);
/*     */       }
/*     */       
/* 490 */       this.flagsYAreasToUpdate |= 1 << y >> 4;
/*     */       
/* 492 */       if (this.numBlocksToUpdate < 64) {
/* 493 */         short short1 = (short)(x << 12 | z << 8 | y);
/*     */         
/* 495 */         for (int i = 0; i < this.numBlocksToUpdate; i++) {
/* 496 */           if (this.locationOfBlockChange[i] == short1) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */         
/* 501 */         this.locationOfBlockChange[this.numBlocksToUpdate++] = short1;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void sendToAllPlayersWatchingChunk(Packet thePacket) {
/* 506 */       for (int i = 0; i < this.playersWatchingChunk.size(); i++) {
/* 507 */         EntityPlayerMP entityplayermp = this.playersWatchingChunk.get(i);
/*     */         
/* 509 */         if (!entityplayermp.loadedChunks.contains(this.chunkCoords)) {
/* 510 */           entityplayermp.playerNetServerHandler.sendPacket(thePacket);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public void onUpdate() {
/* 516 */       if (this.numBlocksToUpdate != 0) {
/* 517 */         if (this.numBlocksToUpdate == 1) {
/* 518 */           int k1 = (this.locationOfBlockChange[0] >> 12 & 0xF) + this.chunkCoords.chunkXPos * 16;
/* 519 */           int i2 = this.locationOfBlockChange[0] & 0xFF;
/* 520 */           int k2 = (this.locationOfBlockChange[0] >> 8 & 0xF) + this.chunkCoords.chunkZPos * 16;
/* 521 */           BlockPos blockpos = new BlockPos(k1, i2, k2);
/* 522 */           sendToAllPlayersWatchingChunk((Packet)new S23PacketBlockChange((World)PlayerManager.this.theWorldServer, blockpos));
/*     */           
/* 524 */           if (PlayerManager.this.theWorldServer.getBlockState(blockpos).getBlock().hasTileEntity()) {
/* 525 */             sendTileToAllPlayersWatchingChunk(PlayerManager.this.theWorldServer.getTileEntity(blockpos));
/*     */           }
/* 527 */         } else if (this.numBlocksToUpdate != 64) {
/* 528 */           sendToAllPlayersWatchingChunk((Packet)new S22PacketMultiBlockChange(this.numBlocksToUpdate, this.locationOfBlockChange, PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos)));
/*     */           
/* 530 */           for (int j1 = 0; j1 < this.numBlocksToUpdate; j1++) {
/* 531 */             int l1 = (this.locationOfBlockChange[j1] >> 12 & 0xF) + this.chunkCoords.chunkXPos * 16;
/* 532 */             int j2 = this.locationOfBlockChange[j1] & 0xFF;
/* 533 */             int l2 = (this.locationOfBlockChange[j1] >> 8 & 0xF) + this.chunkCoords.chunkZPos * 16;
/* 534 */             BlockPos blockpos1 = new BlockPos(l1, j2, l2);
/*     */             
/* 536 */             if (PlayerManager.this.theWorldServer.getBlockState(blockpos1).getBlock().hasTileEntity()) {
/* 537 */               sendTileToAllPlayersWatchingChunk(PlayerManager.this.theWorldServer.getTileEntity(blockpos1));
/*     */             }
/*     */           } 
/*     */         } else {
/* 541 */           int i = this.chunkCoords.chunkXPos * 16;
/* 542 */           int j = this.chunkCoords.chunkZPos * 16;
/* 543 */           sendToAllPlayersWatchingChunk((Packet)new S21PacketChunkData(PlayerManager.this.theWorldServer.getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos), false, this.flagsYAreasToUpdate));
/*     */           
/* 545 */           for (int k = 0; k < 16; k++) {
/* 546 */             if ((this.flagsYAreasToUpdate & 1 << k) != 0) {
/* 547 */               int l = k << 4;
/* 548 */               List<TileEntity> list = PlayerManager.this.theWorldServer.getTileEntitiesIn(i, l, j, i + 16, l + 16, j + 16);
/*     */               
/* 550 */               for (int i1 = 0; i1 < list.size(); i1++) {
/* 551 */                 sendTileToAllPlayersWatchingChunk(list.get(i1));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 557 */         this.numBlocksToUpdate = 0;
/* 558 */         this.flagsYAreasToUpdate = 0;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void sendTileToAllPlayersWatchingChunk(TileEntity theTileEntity) {
/* 563 */       if (theTileEntity != null) {
/* 564 */         Packet packet = theTileEntity.getDescriptionPacket();
/*     */         
/* 566 */         if (packet != null)
/* 567 */           sendToAllPlayersWatchingChunk(packet); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\PlayerManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */