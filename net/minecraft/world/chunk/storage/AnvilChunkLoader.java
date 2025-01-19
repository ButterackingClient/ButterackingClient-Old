/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.NibbleArray;
/*     */ import net.minecraft.world.storage.IThreadedFileIO;
/*     */ import net.minecraft.world.storage.ThreadedFileIOBase;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO {
/*  34 */   private static final Logger logger = LogManager.getLogger();
/*  35 */   private Map<ChunkCoordIntPair, NBTTagCompound> chunksToRemove = new ConcurrentHashMap<>();
/*  36 */   private Set<ChunkCoordIntPair> pendingAnvilChunksCoordinates = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*     */ 
/*     */   
/*     */   private final File chunkSaveLocation;
/*     */   
/*     */   private boolean field_183014_e = false;
/*     */ 
/*     */   
/*     */   public AnvilChunkLoader(File chunkSaveLocationIn) {
/*  45 */     this.chunkSaveLocation = chunkSaveLocationIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk loadChunk(World worldIn, int x, int z) throws IOException {
/*  52 */     ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(x, z);
/*  53 */     NBTTagCompound nbttagcompound = this.chunksToRemove.get(chunkcoordintpair);
/*     */     
/*  55 */     if (nbttagcompound == null) {
/*  56 */       DataInputStream datainputstream = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, x, z);
/*     */       
/*  58 */       if (datainputstream == null) {
/*  59 */         return null;
/*     */       }
/*     */       
/*  62 */       nbttagcompound = CompressedStreamTools.read(datainputstream);
/*     */     } 
/*     */     
/*  65 */     return checkedReadChunkFromNBT(worldIn, x, z, nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Chunk checkedReadChunkFromNBT(World worldIn, int x, int z, NBTTagCompound p_75822_4_) {
/*  72 */     if (!p_75822_4_.hasKey("Level", 10)) {
/*  73 */       logger.error("Chunk file at " + x + "," + z + " is missing level data, skipping");
/*  74 */       return null;
/*     */     } 
/*  76 */     NBTTagCompound nbttagcompound = p_75822_4_.getCompoundTag("Level");
/*     */     
/*  78 */     if (!nbttagcompound.hasKey("Sections", 9)) {
/*  79 */       logger.error("Chunk file at " + x + "," + z + " is missing block data, skipping");
/*  80 */       return null;
/*     */     } 
/*  82 */     Chunk chunk = readChunkFromNBT(worldIn, nbttagcompound);
/*     */     
/*  84 */     if (!chunk.isAtLocation(x, z)) {
/*  85 */       logger.error("Chunk file at " + x + "," + z + " is in the wrong location; relocating. (Expected " + x + ", " + z + ", got " + chunk.xPosition + ", " + chunk.zPosition + ")");
/*  86 */       nbttagcompound.setInteger("xPos", x);
/*  87 */       nbttagcompound.setInteger("zPos", z);
/*  88 */       chunk = readChunkFromNBT(worldIn, nbttagcompound);
/*     */     } 
/*     */     
/*  91 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveChunk(World worldIn, Chunk chunkIn) throws MinecraftException, IOException {
/*  97 */     worldIn.checkSessionLock();
/*     */     
/*     */     try {
/* 100 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 101 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 102 */       nbttagcompound.setTag("Level", (NBTBase)nbttagcompound1);
/* 103 */       writeChunkToNBT(chunkIn, worldIn, nbttagcompound1);
/* 104 */       addChunkToPending(chunkIn.getChunkCoordIntPair(), nbttagcompound);
/* 105 */     } catch (Exception exception) {
/* 106 */       logger.error("Failed to save chunk", exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void addChunkToPending(ChunkCoordIntPair p_75824_1_, NBTTagCompound p_75824_2_) {
/* 111 */     if (!this.pendingAnvilChunksCoordinates.contains(p_75824_1_)) {
/* 112 */       this.chunksToRemove.put(p_75824_1_, p_75824_2_);
/*     */     }
/*     */     
/* 115 */     ThreadedFileIOBase.getThreadedIOInstance().queueIO(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeNextIO() {
/*     */     boolean lvt_3_1_;
/* 122 */     if (this.chunksToRemove.isEmpty()) {
/* 123 */       if (this.field_183014_e) {
/* 124 */         logger.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", new Object[] { this.chunkSaveLocation.getName() });
/*     */       }
/*     */       
/* 127 */       return false;
/*     */     } 
/* 129 */     ChunkCoordIntPair chunkcoordintpair = this.chunksToRemove.keySet().iterator().next();
/*     */ 
/*     */     
/*     */     try {
/* 133 */       this.pendingAnvilChunksCoordinates.add(chunkcoordintpair);
/* 134 */       NBTTagCompound nbttagcompound = this.chunksToRemove.remove(chunkcoordintpair);
/*     */       
/* 136 */       if (nbttagcompound != null) {
/*     */         try {
/* 138 */           func_183013_b(chunkcoordintpair, nbttagcompound);
/* 139 */         } catch (Exception exception) {
/* 140 */           logger.error("Failed to save chunk", exception);
/*     */         } 
/*     */       }
/*     */       
/* 144 */       lvt_3_1_ = true;
/*     */     } finally {
/* 146 */       this.pendingAnvilChunksCoordinates.remove(chunkcoordintpair);
/*     */     } 
/*     */     
/* 149 */     return lvt_3_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_183013_b(ChunkCoordIntPair p_183013_1_, NBTTagCompound p_183013_2_) throws IOException {
/* 154 */     DataOutputStream dataoutputstream = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, p_183013_1_.chunkXPos, p_183013_1_.chunkZPos);
/* 155 */     CompressedStreamTools.write(p_183013_2_, dataoutputstream);
/* 156 */     dataoutputstream.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraChunkData(World worldIn, Chunk chunkIn) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void chunkTick() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {
/*     */     try {
/* 178 */       this.field_183014_e = true;
/*     */       
/*     */       while (true) {
/* 181 */         if (writeNextIO());
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 186 */       this.field_183014_e = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeChunkToNBT(Chunk chunkIn, World worldIn, NBTTagCompound p_75820_3_) {
/* 195 */     p_75820_3_.setByte("V", (byte)1);
/* 196 */     p_75820_3_.setInteger("xPos", chunkIn.xPosition);
/* 197 */     p_75820_3_.setInteger("zPos", chunkIn.zPosition);
/* 198 */     p_75820_3_.setLong("LastUpdate", worldIn.getTotalWorldTime());
/* 199 */     p_75820_3_.setIntArray("HeightMap", chunkIn.getHeightMap());
/* 200 */     p_75820_3_.setBoolean("TerrainPopulated", chunkIn.isTerrainPopulated());
/* 201 */     p_75820_3_.setBoolean("LightPopulated", chunkIn.isLightPopulated());
/* 202 */     p_75820_3_.setLong("InhabitedTime", chunkIn.getInhabitedTime());
/* 203 */     ExtendedBlockStorage[] aextendedblockstorage = chunkIn.getBlockStorageArray();
/* 204 */     NBTTagList nbttaglist = new NBTTagList();
/* 205 */     boolean flag = !worldIn.provider.getHasNoSky(); byte b; int i;
/*     */     ExtendedBlockStorage[] arrayOfExtendedBlockStorage1;
/* 207 */     for (i = (arrayOfExtendedBlockStorage1 = aextendedblockstorage).length, b = 0; b < i; ) { ExtendedBlockStorage extendedblockstorage = arrayOfExtendedBlockStorage1[b];
/* 208 */       if (extendedblockstorage != null) {
/* 209 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 210 */         nbttagcompound.setByte("Y", (byte)(extendedblockstorage.getYLocation() >> 4 & 0xFF));
/* 211 */         byte[] abyte = new byte[(extendedblockstorage.getData()).length];
/* 212 */         NibbleArray nibblearray = new NibbleArray();
/* 213 */         NibbleArray nibblearray1 = null;
/*     */         
/* 215 */         for (int j = 0; j < (extendedblockstorage.getData()).length; j++) {
/* 216 */           char c0 = extendedblockstorage.getData()[j];
/* 217 */           int m = j & 0xF;
/* 218 */           int k = j >> 8 & 0xF;
/* 219 */           int l = j >> 4 & 0xF;
/*     */           
/* 221 */           if (c0 >> 12 != 0) {
/* 222 */             if (nibblearray1 == null) {
/* 223 */               nibblearray1 = new NibbleArray();
/*     */             }
/*     */             
/* 226 */             nibblearray1.set(m, k, l, c0 >> 12);
/*     */           } 
/*     */           
/* 229 */           abyte[j] = (byte)(c0 >> 4 & 0xFF);
/* 230 */           nibblearray.set(m, k, l, c0 & 0xF);
/*     */         } 
/*     */         
/* 233 */         nbttagcompound.setByteArray("Blocks", abyte);
/* 234 */         nbttagcompound.setByteArray("Data", nibblearray.getData());
/*     */         
/* 236 */         if (nibblearray1 != null) {
/* 237 */           nbttagcompound.setByteArray("Add", nibblearray1.getData());
/*     */         }
/*     */         
/* 240 */         nbttagcompound.setByteArray("BlockLight", extendedblockstorage.getBlocklightArray().getData());
/*     */         
/* 242 */         if (flag) {
/* 243 */           nbttagcompound.setByteArray("SkyLight", extendedblockstorage.getSkylightArray().getData());
/*     */         } else {
/* 245 */           nbttagcompound.setByteArray("SkyLight", new byte[(extendedblockstorage.getBlocklightArray().getData()).length]);
/*     */         } 
/*     */         
/* 248 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */       b++; }
/*     */     
/* 252 */     p_75820_3_.setTag("Sections", (NBTBase)nbttaglist);
/* 253 */     p_75820_3_.setByteArray("Biomes", chunkIn.getBiomeArray());
/* 254 */     chunkIn.setHasEntities(false);
/* 255 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/* 257 */     for (int i1 = 0; i1 < (chunkIn.getEntityLists()).length; i1++) {
/* 258 */       for (Entity entity : chunkIn.getEntityLists()[i1]) {
/* 259 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 261 */         if (entity.writeToNBTOptional(nbttagcompound1)) {
/* 262 */           chunkIn.setHasEntities(true);
/* 263 */           nbttaglist1.appendTag((NBTBase)nbttagcompound1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     p_75820_3_.setTag("Entities", (NBTBase)nbttaglist1);
/* 269 */     NBTTagList nbttaglist2 = new NBTTagList();
/*     */     
/* 271 */     for (TileEntity tileentity : chunkIn.getTileEntityMap().values()) {
/* 272 */       NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 273 */       tileentity.writeToNBT(nbttagcompound2);
/* 274 */       nbttaglist2.appendTag((NBTBase)nbttagcompound2);
/*     */     } 
/*     */     
/* 277 */     p_75820_3_.setTag("TileEntities", (NBTBase)nbttaglist2);
/* 278 */     List<NextTickListEntry> list = worldIn.getPendingBlockUpdates(chunkIn, false);
/*     */     
/* 280 */     if (list != null) {
/* 281 */       long j1 = worldIn.getTotalWorldTime();
/* 282 */       NBTTagList nbttaglist3 = new NBTTagList();
/*     */       
/* 284 */       for (NextTickListEntry nextticklistentry : list) {
/* 285 */         NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 286 */         ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(nextticklistentry.getBlock());
/* 287 */         nbttagcompound3.setString("i", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 288 */         nbttagcompound3.setInteger("x", nextticklistentry.position.getX());
/* 289 */         nbttagcompound3.setInteger("y", nextticklistentry.position.getY());
/* 290 */         nbttagcompound3.setInteger("z", nextticklistentry.position.getZ());
/* 291 */         nbttagcompound3.setInteger("t", (int)(nextticklistentry.scheduledTime - j1));
/* 292 */         nbttagcompound3.setInteger("p", nextticklistentry.priority);
/* 293 */         nbttaglist3.appendTag((NBTBase)nbttagcompound3);
/*     */       } 
/*     */       
/* 296 */       p_75820_3_.setTag("TileTicks", (NBTBase)nbttaglist3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Chunk readChunkFromNBT(World worldIn, NBTTagCompound p_75823_2_) {
/* 305 */     int i = p_75823_2_.getInteger("xPos");
/* 306 */     int j = p_75823_2_.getInteger("zPos");
/* 307 */     Chunk chunk = new Chunk(worldIn, i, j);
/* 308 */     chunk.setHeightMap(p_75823_2_.getIntArray("HeightMap"));
/* 309 */     chunk.setTerrainPopulated(p_75823_2_.getBoolean("TerrainPopulated"));
/* 310 */     chunk.setLightPopulated(p_75823_2_.getBoolean("LightPopulated"));
/* 311 */     chunk.setInhabitedTime(p_75823_2_.getLong("InhabitedTime"));
/* 312 */     NBTTagList nbttaglist = p_75823_2_.getTagList("Sections", 10);
/* 313 */     int k = 16;
/* 314 */     ExtendedBlockStorage[] aextendedblockstorage = new ExtendedBlockStorage[k];
/* 315 */     boolean flag = !worldIn.provider.getHasNoSky();
/*     */     
/* 317 */     for (int l = 0; l < nbttaglist.tagCount(); l++) {
/* 318 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(l);
/* 319 */       int i1 = nbttagcompound.getByte("Y");
/* 320 */       ExtendedBlockStorage extendedblockstorage = new ExtendedBlockStorage(i1 << 4, flag);
/* 321 */       byte[] abyte = nbttagcompound.getByteArray("Blocks");
/* 322 */       NibbleArray nibblearray = new NibbleArray(nbttagcompound.getByteArray("Data"));
/* 323 */       NibbleArray nibblearray1 = nbttagcompound.hasKey("Add", 7) ? new NibbleArray(nbttagcompound.getByteArray("Add")) : null;
/* 324 */       char[] achar = new char[abyte.length];
/*     */       
/* 326 */       for (int j1 = 0; j1 < achar.length; j1++) {
/* 327 */         int k1 = j1 & 0xF;
/* 328 */         int l1 = j1 >> 8 & 0xF;
/* 329 */         int i2 = j1 >> 4 & 0xF;
/* 330 */         int j2 = (nibblearray1 != null) ? nibblearray1.get(k1, l1, i2) : 0;
/* 331 */         achar[j1] = (char)(j2 << 12 | (abyte[j1] & 0xFF) << 4 | nibblearray.get(k1, l1, i2));
/*     */       } 
/*     */       
/* 334 */       extendedblockstorage.setData(achar);
/* 335 */       extendedblockstorage.setBlocklightArray(new NibbleArray(nbttagcompound.getByteArray("BlockLight")));
/*     */       
/* 337 */       if (flag) {
/* 338 */         extendedblockstorage.setSkylightArray(new NibbleArray(nbttagcompound.getByteArray("SkyLight")));
/*     */       }
/*     */       
/* 341 */       extendedblockstorage.removeInvalidBlocks();
/* 342 */       aextendedblockstorage[i1] = extendedblockstorage;
/*     */     } 
/*     */     
/* 345 */     chunk.setStorageArrays(aextendedblockstorage);
/*     */     
/* 347 */     if (p_75823_2_.hasKey("Biomes", 7)) {
/* 348 */       chunk.setBiomeArray(p_75823_2_.getByteArray("Biomes"));
/*     */     }
/*     */     
/* 351 */     NBTTagList nbttaglist1 = p_75823_2_.getTagList("Entities", 10);
/*     */     
/* 353 */     if (nbttaglist1 != null) {
/* 354 */       for (int k2 = 0; k2 < nbttaglist1.tagCount(); k2++) {
/* 355 */         NBTTagCompound nbttagcompound1 = nbttaglist1.getCompoundTagAt(k2);
/* 356 */         Entity entity = EntityList.createEntityFromNBT(nbttagcompound1, worldIn);
/* 357 */         chunk.setHasEntities(true);
/*     */         
/* 359 */         if (entity != null) {
/* 360 */           chunk.addEntity(entity);
/* 361 */           Entity entity1 = entity;
/*     */           
/* 363 */           for (NBTTagCompound nbttagcompound4 = nbttagcompound1; nbttagcompound4.hasKey("Riding", 10); nbttagcompound4 = nbttagcompound4.getCompoundTag("Riding")) {
/* 364 */             Entity entity2 = EntityList.createEntityFromNBT(nbttagcompound4.getCompoundTag("Riding"), worldIn);
/*     */             
/* 366 */             if (entity2 != null) {
/* 367 */               chunk.addEntity(entity2);
/* 368 */               entity1.mountEntity(entity2);
/*     */             } 
/*     */             
/* 371 */             entity1 = entity2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 377 */     NBTTagList nbttaglist2 = p_75823_2_.getTagList("TileEntities", 10);
/*     */     
/* 379 */     if (nbttaglist2 != null) {
/* 380 */       for (int l2 = 0; l2 < nbttaglist2.tagCount(); l2++) {
/* 381 */         NBTTagCompound nbttagcompound2 = nbttaglist2.getCompoundTagAt(l2);
/* 382 */         TileEntity tileentity = TileEntity.createAndLoadEntity(nbttagcompound2);
/*     */         
/* 384 */         if (tileentity != null) {
/* 385 */           chunk.addTileEntity(tileentity);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 390 */     if (p_75823_2_.hasKey("TileTicks", 9)) {
/* 391 */       NBTTagList nbttaglist3 = p_75823_2_.getTagList("TileTicks", 10);
/*     */       
/* 393 */       if (nbttaglist3 != null) {
/* 394 */         for (int i3 = 0; i3 < nbttaglist3.tagCount(); i3++) {
/* 395 */           Block block; NBTTagCompound nbttagcompound3 = nbttaglist3.getCompoundTagAt(i3);
/*     */ 
/*     */           
/* 398 */           if (nbttagcompound3.hasKey("i", 8)) {
/* 399 */             block = Block.getBlockFromName(nbttagcompound3.getString("i"));
/*     */           } else {
/* 401 */             block = Block.getBlockById(nbttagcompound3.getInteger("i"));
/*     */           } 
/*     */           
/* 404 */           worldIn.scheduleBlockUpdate(new BlockPos(nbttagcompound3.getInteger("x"), nbttagcompound3.getInteger("y"), nbttagcompound3.getInteger("z")), block, nbttagcompound3.getInteger("t"), nbttagcompound3.getInteger("p"));
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 409 */     return chunk;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\chunk\storage\AnvilChunkLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */