/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.biome.WorldChunkManager;
/*     */ import net.minecraft.world.biome.WorldChunkManagerHell;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.SaveFormatComparator;
/*     */ import net.minecraft.world.storage.SaveFormatOld;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class AnvilSaveConverter
/*     */   extends SaveFormatOld {
/*  31 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   public AnvilSaveConverter(File savesDirectoryIn) {
/*  34 */     super(savesDirectoryIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  41 */     return "Anvil";
/*     */   }
/*     */   
/*     */   public List<SaveFormatComparator> getSaveList() throws AnvilConverterException {
/*  45 */     if (this.savesDirectory != null && this.savesDirectory.exists() && this.savesDirectory.isDirectory()) {
/*  46 */       List<SaveFormatComparator> list = Lists.newArrayList();
/*  47 */       File[] afile = this.savesDirectory.listFiles(); byte b; int i;
/*     */       File[] arrayOfFile1;
/*  49 */       for (i = (arrayOfFile1 = afile).length, b = 0; b < i; ) { File file1 = arrayOfFile1[b];
/*  50 */         if (file1.isDirectory()) {
/*  51 */           String s = file1.getName();
/*  52 */           WorldInfo worldinfo = getWorldInfo(s);
/*     */           
/*  54 */           if (worldinfo != null && (worldinfo.getSaveVersion() == 19132 || worldinfo.getSaveVersion() == 19133)) {
/*  55 */             boolean flag = (worldinfo.getSaveVersion() != getSaveVersion());
/*  56 */             String s1 = worldinfo.getWorldName();
/*     */             
/*  58 */             if (StringUtils.isEmpty(s1)) {
/*  59 */               s1 = s;
/*     */             }
/*     */             
/*  62 */             long l = 0L;
/*  63 */             list.add(new SaveFormatComparator(s, s1, worldinfo.getLastTimePlayed(), l, worldinfo.getGameType(), flag, worldinfo.isHardcoreModeEnabled(), worldinfo.areCommandsAllowed()));
/*     */           } 
/*     */         } 
/*     */         b++; }
/*     */       
/*  68 */       return list;
/*     */     } 
/*  70 */     throw new AnvilConverterException("Unable to read or access folder where game worlds are saved!");
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getSaveVersion() {
/*  75 */     return 19133;
/*     */   }
/*     */   
/*     */   public void flushCache() {
/*  79 */     RegionFileCache.clearRegionFileReferences();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISaveHandler getSaveLoader(String saveName, boolean storePlayerdata) {
/*  86 */     return (ISaveHandler)new AnvilSaveHandler(this.savesDirectory, saveName, storePlayerdata);
/*     */   }
/*     */   
/*     */   public boolean isConvertible(String saveName) {
/*  90 */     WorldInfo worldinfo = getWorldInfo(saveName);
/*  91 */     return (worldinfo != null && worldinfo.getSaveVersion() == 19132);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOldMapFormat(String saveName) {
/*  98 */     WorldInfo worldinfo = getWorldInfo(saveName);
/*  99 */     return (worldinfo != null && worldinfo.getSaveVersion() != getSaveVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean convertMapFormat(String filename, IProgressUpdate progressCallback) {
/* 106 */     progressCallback.setLoadingProgress(0);
/* 107 */     List<File> list = Lists.newArrayList();
/* 108 */     List<File> list1 = Lists.newArrayList();
/* 109 */     List<File> list2 = Lists.newArrayList();
/* 110 */     File file1 = new File(this.savesDirectory, filename);
/* 111 */     File file2 = new File(file1, "DIM-1");
/* 112 */     File file3 = new File(file1, "DIM1");
/* 113 */     logger.info("Scanning folders...");
/* 114 */     addRegionFilesToCollection(file1, list);
/*     */     
/* 116 */     if (file2.exists()) {
/* 117 */       addRegionFilesToCollection(file2, list1);
/*     */     }
/*     */     
/* 120 */     if (file3.exists()) {
/* 121 */       addRegionFilesToCollection(file3, list2);
/*     */     }
/*     */     
/* 124 */     int i = list.size() + list1.size() + list2.size();
/* 125 */     logger.info("Total conversion count is " + i);
/* 126 */     WorldInfo worldinfo = getWorldInfo(filename);
/* 127 */     WorldChunkManager worldchunkmanager = null;
/*     */     
/* 129 */     if (worldinfo.getTerrainType() == WorldType.FLAT) {
/* 130 */       WorldChunkManagerHell worldChunkManagerHell = new WorldChunkManagerHell(BiomeGenBase.plains, 0.5F);
/*     */     } else {
/* 132 */       worldchunkmanager = new WorldChunkManager(worldinfo.getSeed(), worldinfo.getTerrainType(), worldinfo.getGeneratorOptions());
/*     */     } 
/*     */     
/* 135 */     convertFile(new File(file1, "region"), list, worldchunkmanager, 0, i, progressCallback);
/* 136 */     convertFile(new File(file2, "region"), list1, (WorldChunkManager)new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F), list.size(), i, progressCallback);
/* 137 */     convertFile(new File(file3, "region"), list2, (WorldChunkManager)new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F), list.size() + list1.size(), i, progressCallback);
/* 138 */     worldinfo.setSaveVersion(19133);
/*     */     
/* 140 */     if (worldinfo.getTerrainType() == WorldType.DEFAULT_1_1) {
/* 141 */       worldinfo.setTerrainType(WorldType.DEFAULT);
/*     */     }
/*     */     
/* 144 */     createFile(filename);
/* 145 */     ISaveHandler isavehandler = getSaveLoader(filename, false);
/* 146 */     isavehandler.saveWorldInfo(worldinfo);
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createFile(String filename) {
/* 154 */     File file1 = new File(this.savesDirectory, filename);
/*     */     
/* 156 */     if (!file1.exists()) {
/* 157 */       logger.warn("Unable to create level.dat_mcr backup");
/*     */     } else {
/* 159 */       File file2 = new File(file1, "level.dat");
/*     */       
/* 161 */       if (!file2.exists()) {
/* 162 */         logger.warn("Unable to create level.dat_mcr backup");
/*     */       } else {
/* 164 */         File file3 = new File(file1, "level.dat_mcr");
/*     */         
/* 166 */         if (!file2.renameTo(file3)) {
/* 167 */           logger.warn("Unable to create level.dat_mcr backup");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void convertFile(File p_75813_1_, Iterable<File> p_75813_2_, WorldChunkManager p_75813_3_, int p_75813_4_, int p_75813_5_, IProgressUpdate p_75813_6_) {
/* 174 */     for (File file1 : p_75813_2_) {
/* 175 */       convertChunks(p_75813_1_, file1, p_75813_3_, p_75813_4_, p_75813_5_, p_75813_6_);
/* 176 */       p_75813_4_++;
/* 177 */       int i = (int)Math.round(100.0D * p_75813_4_ / p_75813_5_);
/* 178 */       p_75813_6_.setLoadingProgress(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertChunks(File p_75811_1_, File p_75811_2_, WorldChunkManager p_75811_3_, int p_75811_4_, int p_75811_5_, IProgressUpdate progressCallback) {
/*     */     try {
/* 187 */       String s = p_75811_2_.getName();
/* 188 */       RegionFile regionfile = new RegionFile(p_75811_2_);
/* 189 */       RegionFile regionfile1 = new RegionFile(new File(p_75811_1_, String.valueOf(s.substring(0, s.length() - ".mcr".length())) + ".mca"));
/*     */       
/* 191 */       for (int i = 0; i < 32; i++) {
/* 192 */         for (int j = 0; j < 32; j++) {
/* 193 */           if (regionfile.isChunkSaved(i, j) && !regionfile1.isChunkSaved(i, j)) {
/* 194 */             DataInputStream datainputstream = regionfile.getChunkDataInputStream(i, j);
/*     */             
/* 196 */             if (datainputstream == null) {
/* 197 */               logger.warn("Failed to fetch input stream");
/*     */             } else {
/* 199 */               NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
/* 200 */               datainputstream.close();
/* 201 */               NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Level");
/* 202 */               ChunkLoader.AnvilConverterData chunkloader$anvilconverterdata = ChunkLoader.load(nbttagcompound1);
/* 203 */               NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 204 */               NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 205 */               nbttagcompound2.setTag("Level", (NBTBase)nbttagcompound3);
/* 206 */               ChunkLoader.convertToAnvilFormat(chunkloader$anvilconverterdata, nbttagcompound3, p_75811_3_);
/* 207 */               DataOutputStream dataoutputstream = regionfile1.getChunkDataOutputStream(i, j);
/* 208 */               CompressedStreamTools.write(nbttagcompound2, dataoutputstream);
/* 209 */               dataoutputstream.close();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 214 */         int k = (int)Math.round(100.0D * (p_75811_4_ * 1024) / (p_75811_5_ * 1024));
/* 215 */         int l = (int)Math.round(100.0D * ((i + 1) * 32 + p_75811_4_ * 1024) / (p_75811_5_ * 1024));
/*     */         
/* 217 */         if (l > k) {
/* 218 */           progressCallback.setLoadingProgress(l);
/*     */         }
/*     */       } 
/*     */       
/* 222 */       regionfile.close();
/* 223 */       regionfile1.close();
/* 224 */     } catch (IOException ioexception) {
/* 225 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addRegionFilesToCollection(File worldDir, Collection<File> collection) {
/* 233 */     File file1 = new File(worldDir, "region");
/* 234 */     File[] afile = file1.listFiles(new FilenameFilter() {
/*     */           public boolean accept(File p_accept_1_, String p_accept_2_) {
/* 236 */             return p_accept_2_.endsWith(".mcr");
/*     */           }
/*     */         });
/*     */     
/* 240 */     if (afile != null)
/* 241 */       Collections.addAll(collection, afile); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\chunk\storage\AnvilSaveConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */