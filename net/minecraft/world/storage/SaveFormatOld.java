/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SaveFormatOld
/*     */   implements ISaveFormat
/*     */ {
/*  18 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   protected final File savesDirectory;
/*     */ 
/*     */ 
/*     */   
/*     */   public SaveFormatOld(File savesDirectoryIn) {
/*  26 */     if (!savesDirectoryIn.exists()) {
/*  27 */       savesDirectoryIn.mkdirs();
/*     */     }
/*     */     
/*  30 */     this.savesDirectory = savesDirectoryIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  37 */     return "Old Format";
/*     */   }
/*     */   
/*     */   public List<SaveFormatComparator> getSaveList() throws AnvilConverterException {
/*  41 */     List<SaveFormatComparator> list = Lists.newArrayList();
/*     */     
/*  43 */     for (int i = 0; i < 5; i++) {
/*  44 */       String s = "World" + (i + 1);
/*  45 */       WorldInfo worldinfo = getWorldInfo(s);
/*     */       
/*  47 */       if (worldinfo != null) {
/*  48 */         list.add(new SaveFormatComparator(s, "", worldinfo.getLastTimePlayed(), worldinfo.getSizeOnDisk(), worldinfo.getGameType(), false, worldinfo.isHardcoreModeEnabled(), worldinfo.areCommandsAllowed()));
/*     */       }
/*     */     } 
/*     */     
/*  52 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushCache() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldInfo getWorldInfo(String saveName) {
/*  62 */     File file1 = new File(this.savesDirectory, saveName);
/*     */     
/*  64 */     if (!file1.exists()) {
/*  65 */       return null;
/*     */     }
/*  67 */     File file2 = new File(file1, "level.dat");
/*     */     
/*  69 */     if (file2.exists()) {
/*     */       try {
/*  71 */         NBTTagCompound nbttagcompound2 = CompressedStreamTools.readCompressed(new FileInputStream(file2));
/*  72 */         NBTTagCompound nbttagcompound3 = nbttagcompound2.getCompoundTag("Data");
/*  73 */         return new WorldInfo(nbttagcompound3);
/*  74 */       } catch (Exception exception1) {
/*  75 */         logger.error("Exception reading " + file2, exception1);
/*     */       } 
/*     */     }
/*     */     
/*  79 */     file2 = new File(file1, "level.dat_old");
/*     */     
/*  81 */     if (file2.exists()) {
/*     */       try {
/*  83 */         NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file2));
/*  84 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/*  85 */         return new WorldInfo(nbttagcompound1);
/*  86 */       } catch (Exception exception) {
/*  87 */         logger.error("Exception reading " + file2, exception);
/*     */       } 
/*     */     }
/*     */     
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renameWorld(String dirName, String newName) {
/* 100 */     File file1 = new File(this.savesDirectory, dirName);
/*     */     
/* 102 */     if (file1.exists()) {
/* 103 */       File file2 = new File(file1, "level.dat");
/*     */       
/* 105 */       if (file2.exists()) {
/*     */         try {
/* 107 */           NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file2));
/* 108 */           NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/* 109 */           nbttagcompound1.setString("LevelName", newName);
/* 110 */           CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(file2));
/* 111 */         } catch (Exception exception) {
/* 112 */           exception.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isNewLevelIdAcceptable(String saveName) {
/* 119 */     File file1 = new File(this.savesDirectory, saveName);
/*     */     
/* 121 */     if (file1.exists()) {
/* 122 */       return false;
/*     */     }
/*     */     try {
/* 125 */       file1.mkdir();
/* 126 */       file1.delete();
/* 127 */       return true;
/* 128 */     } catch (Throwable throwable) {
/* 129 */       logger.warn("Couldn't make new level", throwable);
/* 130 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deleteWorldDirectory(String saveName) {
/* 141 */     File file1 = new File(this.savesDirectory, saveName);
/*     */     
/* 143 */     if (!file1.exists()) {
/* 144 */       return true;
/*     */     }
/* 146 */     logger.info("Deleting level " + saveName);
/*     */     
/* 148 */     for (int i = 1; i <= 5; i++) {
/* 149 */       logger.info("Attempt " + i + "...");
/*     */       
/* 151 */       if (deleteFiles(file1.listFiles())) {
/*     */         break;
/*     */       }
/*     */       
/* 155 */       logger.warn("Unsuccessful in deleting contents.");
/*     */       
/* 157 */       if (i < 5) {
/*     */         try {
/* 159 */           Thread.sleep(500L);
/* 160 */         } catch (InterruptedException interruptedException) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 166 */     return file1.delete();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean deleteFiles(File[] files) {
/* 175 */     for (int i = 0; i < files.length; i++) {
/* 176 */       File file1 = files[i];
/* 177 */       logger.debug("Deleting " + file1);
/*     */       
/* 179 */       if (file1.isDirectory() && !deleteFiles(file1.listFiles())) {
/* 180 */         logger.warn("Couldn't delete directory " + file1);
/* 181 */         return false;
/*     */       } 
/*     */       
/* 184 */       if (!file1.delete()) {
/* 185 */         logger.warn("Couldn't delete file " + file1);
/* 186 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISaveHandler getSaveLoader(String saveName, boolean storePlayerdata) {
/* 197 */     return new SaveHandler(this.savesDirectory, saveName, storePlayerdata);
/*     */   }
/*     */   
/*     */   public boolean isConvertible(String saveName) {
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOldMapFormat(String saveName) {
/* 208 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean convertMapFormat(String filename, IProgressUpdate progressCallback) {
/* 215 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canLoadWorld(String saveName) {
/* 224 */     File file1 = new File(this.savesDirectory, saveName);
/* 225 */     return file1.isDirectory();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\storage\SaveFormatOld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */