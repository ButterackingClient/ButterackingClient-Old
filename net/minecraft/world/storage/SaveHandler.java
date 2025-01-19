/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SaveHandler implements ISaveHandler, IPlayerFileData {
/*  21 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   private final File worldDirectory;
/*     */ 
/*     */ 
/*     */   
/*     */   private final File playersDirectory;
/*     */ 
/*     */ 
/*     */   
/*     */   private final File mapDataDir;
/*     */ 
/*     */ 
/*     */   
/*  37 */   private final long initializationTime = MinecraftServer.getCurrentTimeMillis();
/*     */ 
/*     */   
/*     */   private final String saveDirectoryName;
/*     */ 
/*     */ 
/*     */   
/*     */   public SaveHandler(File savesDirectory, String directoryName, boolean playersDirectoryIn) {
/*  45 */     this.worldDirectory = new File(savesDirectory, directoryName);
/*  46 */     this.worldDirectory.mkdirs();
/*  47 */     this.playersDirectory = new File(this.worldDirectory, "playerdata");
/*  48 */     this.mapDataDir = new File(this.worldDirectory, "data");
/*  49 */     this.mapDataDir.mkdirs();
/*  50 */     this.saveDirectoryName = directoryName;
/*     */     
/*  52 */     if (playersDirectoryIn) {
/*  53 */       this.playersDirectory.mkdirs();
/*     */     }
/*     */     
/*  56 */     setSessionLock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setSessionLock() {
/*     */     try {
/*  64 */       File file1 = new File(this.worldDirectory, "session.lock");
/*  65 */       DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
/*     */       
/*     */       try {
/*  68 */         dataoutputstream.writeLong(this.initializationTime);
/*     */       } finally {
/*  70 */         dataoutputstream.close();
/*     */       } 
/*  72 */     } catch (IOException ioexception) {
/*  73 */       ioexception.printStackTrace();
/*  74 */       throw new RuntimeException("Failed to check session lock, aborting");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getWorldDirectory() {
/*  82 */     return this.worldDirectory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkSessionLock() throws MinecraftException {
/*     */     try {
/*  90 */       File file1 = new File(this.worldDirectory, "session.lock");
/*  91 */       DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
/*     */       
/*     */       try {
/*  94 */         if (datainputstream.readLong() != this.initializationTime) {
/*  95 */           throw new MinecraftException("The save is being accessed from another location, aborting");
/*     */         }
/*     */       } finally {
/*  98 */         datainputstream.close();
/*     */       } 
/* 100 */     } catch (IOException var7) {
/* 101 */       throw new MinecraftException("Failed to check session lock, aborting");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChunkLoader getChunkLoader(WorldProvider provider) {
/* 109 */     throw new RuntimeException("Old Chunk Storage is no longer supported.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldInfo loadWorldInfo() {
/* 116 */     File file1 = new File(this.worldDirectory, "level.dat");
/*     */     
/* 118 */     if (file1.exists()) {
/*     */       try {
/* 120 */         NBTTagCompound nbttagcompound2 = CompressedStreamTools.readCompressed(new FileInputStream(file1));
/* 121 */         NBTTagCompound nbttagcompound3 = nbttagcompound2.getCompoundTag("Data");
/* 122 */         return new WorldInfo(nbttagcompound3);
/* 123 */       } catch (Exception exception1) {
/* 124 */         exception1.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 128 */     file1 = new File(this.worldDirectory, "level.dat_old");
/*     */     
/* 130 */     if (file1.exists()) {
/*     */       try {
/* 132 */         NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
/* 133 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/* 134 */         return new WorldInfo(nbttagcompound1);
/* 135 */       } catch (Exception exception) {
/* 136 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {
/* 147 */     NBTTagCompound nbttagcompound = worldInformation.cloneNBTCompound(tagCompound);
/* 148 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 149 */     nbttagcompound1.setTag("Data", (NBTBase)nbttagcompound);
/*     */     
/*     */     try {
/* 152 */       File file1 = new File(this.worldDirectory, "level.dat_new");
/* 153 */       File file2 = new File(this.worldDirectory, "level.dat_old");
/* 154 */       File file3 = new File(this.worldDirectory, "level.dat");
/* 155 */       CompressedStreamTools.writeCompressed(nbttagcompound1, new FileOutputStream(file1));
/*     */       
/* 157 */       if (file2.exists()) {
/* 158 */         file2.delete();
/*     */       }
/*     */       
/* 161 */       file3.renameTo(file2);
/*     */       
/* 163 */       if (file3.exists()) {
/* 164 */         file3.delete();
/*     */       }
/*     */       
/* 167 */       file1.renameTo(file3);
/*     */       
/* 169 */       if (file1.exists()) {
/* 170 */         file1.delete();
/*     */       }
/* 172 */     } catch (Exception exception) {
/* 173 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveWorldInfo(WorldInfo worldInformation) {
/* 181 */     NBTTagCompound nbttagcompound = worldInformation.getNBTTagCompound();
/* 182 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 183 */     nbttagcompound1.setTag("Data", (NBTBase)nbttagcompound);
/*     */     
/*     */     try {
/* 186 */       File file1 = new File(this.worldDirectory, "level.dat_new");
/* 187 */       File file2 = new File(this.worldDirectory, "level.dat_old");
/* 188 */       File file3 = new File(this.worldDirectory, "level.dat");
/* 189 */       CompressedStreamTools.writeCompressed(nbttagcompound1, new FileOutputStream(file1));
/*     */       
/* 191 */       if (file2.exists()) {
/* 192 */         file2.delete();
/*     */       }
/*     */       
/* 195 */       file3.renameTo(file2);
/*     */       
/* 197 */       if (file3.exists()) {
/* 198 */         file3.delete();
/*     */       }
/*     */       
/* 201 */       file1.renameTo(file3);
/*     */       
/* 203 */       if (file1.exists()) {
/* 204 */         file1.delete();
/*     */       }
/* 206 */     } catch (Exception exception) {
/* 207 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePlayerData(EntityPlayer player) {
/*     */     try {
/* 216 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 217 */       player.writeToNBT(nbttagcompound);
/* 218 */       File file1 = new File(this.playersDirectory, String.valueOf(player.getUniqueID().toString()) + ".dat.tmp");
/* 219 */       File file2 = new File(this.playersDirectory, String.valueOf(player.getUniqueID().toString()) + ".dat");
/* 220 */       CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(file1));
/*     */       
/* 222 */       if (file2.exists()) {
/* 223 */         file2.delete();
/*     */       }
/*     */       
/* 226 */       file1.renameTo(file2);
/* 227 */     } catch (Exception var5) {
/* 228 */       logger.warn("Failed to save player data for " + player.getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound readPlayerData(EntityPlayer player) {
/* 236 */     NBTTagCompound nbttagcompound = null;
/*     */     
/*     */     try {
/* 239 */       File file1 = new File(this.playersDirectory, String.valueOf(player.getUniqueID().toString()) + ".dat");
/*     */       
/* 241 */       if (file1.exists() && file1.isFile()) {
/* 242 */         nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
/*     */       }
/* 244 */     } catch (Exception var4) {
/* 245 */       logger.warn("Failed to load player data for " + player.getName());
/*     */     } 
/*     */     
/* 248 */     if (nbttagcompound != null) {
/* 249 */       player.readFromNBT(nbttagcompound);
/*     */     }
/*     */     
/* 252 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public IPlayerFileData getPlayerNBTManager() {
/* 256 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAvailablePlayerDat() {
/* 263 */     String[] astring = this.playersDirectory.list();
/*     */     
/* 265 */     if (astring == null) {
/* 266 */       astring = new String[0];
/*     */     }
/*     */     
/* 269 */     for (int i = 0; i < astring.length; i++) {
/* 270 */       if (astring[i].endsWith(".dat")) {
/* 271 */         astring[i] = astring[i].substring(0, astring[i].length() - 4);
/*     */       }
/*     */     } 
/*     */     
/* 275 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getMapFileFromName(String mapName) {
/* 288 */     return new File(this.mapDataDir, String.valueOf(mapName) + ".dat");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWorldDirectoryName() {
/* 295 */     return this.saveDirectoryName;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\storage\SaveHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */