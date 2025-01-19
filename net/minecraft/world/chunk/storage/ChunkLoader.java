/*     */ package net.minecraft.world.chunk.storage;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.biome.WorldChunkManager;
/*     */ import net.minecraft.world.chunk.NibbleArray;
/*     */ 
/*     */ public class ChunkLoader {
/*     */   public static AnvilConverterData load(NBTTagCompound nbt) {
/*  12 */     int i = nbt.getInteger("xPos");
/*  13 */     int j = nbt.getInteger("zPos");
/*  14 */     AnvilConverterData chunkloader$anvilconverterdata = new AnvilConverterData(i, j);
/*  15 */     chunkloader$anvilconverterdata.blocks = nbt.getByteArray("Blocks");
/*  16 */     chunkloader$anvilconverterdata.data = new NibbleArrayReader(nbt.getByteArray("Data"), 7);
/*  17 */     chunkloader$anvilconverterdata.skyLight = new NibbleArrayReader(nbt.getByteArray("SkyLight"), 7);
/*  18 */     chunkloader$anvilconverterdata.blockLight = new NibbleArrayReader(nbt.getByteArray("BlockLight"), 7);
/*  19 */     chunkloader$anvilconverterdata.heightmap = nbt.getByteArray("HeightMap");
/*  20 */     chunkloader$anvilconverterdata.terrainPopulated = nbt.getBoolean("TerrainPopulated");
/*  21 */     chunkloader$anvilconverterdata.entities = nbt.getTagList("Entities", 10);
/*  22 */     chunkloader$anvilconverterdata.tileEntities = nbt.getTagList("TileEntities", 10);
/*  23 */     chunkloader$anvilconverterdata.tileTicks = nbt.getTagList("TileTicks", 10);
/*     */     
/*     */     try {
/*  26 */       chunkloader$anvilconverterdata.lastUpdated = nbt.getLong("LastUpdate");
/*  27 */     } catch (ClassCastException var5) {
/*  28 */       chunkloader$anvilconverterdata.lastUpdated = nbt.getInteger("LastUpdate");
/*     */     } 
/*     */     
/*  31 */     return chunkloader$anvilconverterdata;
/*     */   }
/*     */   
/*     */   public static void convertToAnvilFormat(AnvilConverterData p_76690_0_, NBTTagCompound compound, WorldChunkManager p_76690_2_) {
/*  35 */     compound.setInteger("xPos", p_76690_0_.x);
/*  36 */     compound.setInteger("zPos", p_76690_0_.z);
/*  37 */     compound.setLong("LastUpdate", p_76690_0_.lastUpdated);
/*  38 */     int[] aint = new int[p_76690_0_.heightmap.length];
/*     */     
/*  40 */     for (int i = 0; i < p_76690_0_.heightmap.length; i++) {
/*  41 */       aint[i] = p_76690_0_.heightmap[i];
/*     */     }
/*     */     
/*  44 */     compound.setIntArray("HeightMap", aint);
/*  45 */     compound.setBoolean("TerrainPopulated", p_76690_0_.terrainPopulated);
/*  46 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  48 */     for (int j = 0; j < 8; j++) {
/*  49 */       boolean flag = true;
/*     */       
/*  51 */       for (int k = 0; k < 16 && flag; k++) {
/*  52 */         for (int l = 0; l < 16 && flag; l++) {
/*  53 */           for (int i1 = 0; i1 < 16; i1++) {
/*  54 */             int j1 = k << 11 | i1 << 7 | l + (j << 4);
/*  55 */             int k1 = p_76690_0_.blocks[j1];
/*     */             
/*  57 */             if (k1 != 0) {
/*  58 */               flag = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*  65 */       if (!flag) {
/*  66 */         byte[] abyte1 = new byte[4096];
/*  67 */         NibbleArray nibblearray = new NibbleArray();
/*  68 */         NibbleArray nibblearray1 = new NibbleArray();
/*  69 */         NibbleArray nibblearray2 = new NibbleArray();
/*     */         
/*  71 */         for (int j3 = 0; j3 < 16; j3++) {
/*  72 */           for (int l1 = 0; l1 < 16; l1++) {
/*  73 */             for (int i2 = 0; i2 < 16; i2++) {
/*  74 */               int j2 = j3 << 11 | i2 << 7 | l1 + (j << 4);
/*  75 */               int k2 = p_76690_0_.blocks[j2];
/*  76 */               abyte1[l1 << 8 | i2 << 4 | j3] = (byte)(k2 & 0xFF);
/*  77 */               nibblearray.set(j3, l1, i2, p_76690_0_.data.get(j3, l1 + (j << 4), i2));
/*  78 */               nibblearray1.set(j3, l1, i2, p_76690_0_.skyLight.get(j3, l1 + (j << 4), i2));
/*  79 */               nibblearray2.set(j3, l1, i2, p_76690_0_.blockLight.get(j3, l1 + (j << 4), i2));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  84 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  85 */         nbttagcompound.setByte("Y", (byte)(j & 0xFF));
/*  86 */         nbttagcompound.setByteArray("Blocks", abyte1);
/*  87 */         nbttagcompound.setByteArray("Data", nibblearray.getData());
/*  88 */         nbttagcompound.setByteArray("SkyLight", nibblearray1.getData());
/*  89 */         nbttagcompound.setByteArray("BlockLight", nibblearray2.getData());
/*  90 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     compound.setTag("Sections", (NBTBase)nbttaglist);
/*  95 */     byte[] abyte = new byte[256];
/*  96 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/*  98 */     for (int l2 = 0; l2 < 16; l2++) {
/*  99 */       for (int i3 = 0; i3 < 16; i3++) {
/* 100 */         blockpos$mutableblockpos.set(p_76690_0_.x << 4 | l2, 0, p_76690_0_.z << 4 | i3);
/* 101 */         abyte[i3 << 4 | l2] = (byte)((p_76690_2_.getBiomeGenerator((BlockPos)blockpos$mutableblockpos, BiomeGenBase.field_180279_ad)).biomeID & 0xFF);
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     compound.setByteArray("Biomes", abyte);
/* 106 */     compound.setTag("Entities", (NBTBase)p_76690_0_.entities);
/* 107 */     compound.setTag("TileEntities", (NBTBase)p_76690_0_.tileEntities);
/*     */     
/* 109 */     if (p_76690_0_.tileTicks != null)
/* 110 */       compound.setTag("TileTicks", (NBTBase)p_76690_0_.tileTicks); 
/*     */   }
/*     */   
/*     */   public static class AnvilConverterData
/*     */   {
/*     */     public long lastUpdated;
/*     */     public boolean terrainPopulated;
/*     */     public byte[] heightmap;
/*     */     public NibbleArrayReader blockLight;
/*     */     public NibbleArrayReader skyLight;
/*     */     public NibbleArrayReader data;
/*     */     public byte[] blocks;
/*     */     public NBTTagList entities;
/*     */     public NBTTagList tileEntities;
/*     */     public NBTTagList tileTicks;
/*     */     public final int x;
/*     */     public final int z;
/*     */     
/*     */     public AnvilConverterData(int xIn, int zIn) {
/* 129 */       this.x = xIn;
/* 130 */       this.z = zIn;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\chunk\storage\ChunkLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */