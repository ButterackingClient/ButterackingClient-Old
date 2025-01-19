/*    */ package net.minecraft.world.chunk.storage;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class RegionFileCache
/*    */ {
/* 12 */   private static final Map<File, RegionFile> regionsByFilename = Maps.newHashMap();
/*    */   
/*    */   public static synchronized RegionFile createOrLoadRegionFile(File worldDir, int chunkX, int chunkZ) {
/* 15 */     File file1 = new File(worldDir, "region");
/* 16 */     File file2 = new File(file1, "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + ".mca");
/* 17 */     RegionFile regionfile = regionsByFilename.get(file2);
/*    */     
/* 19 */     if (regionfile != null) {
/* 20 */       return regionfile;
/*    */     }
/* 22 */     if (!file1.exists()) {
/* 23 */       file1.mkdirs();
/*    */     }
/*    */     
/* 26 */     if (regionsByFilename.size() >= 256) {
/* 27 */       clearRegionFileReferences();
/*    */     }
/*    */     
/* 30 */     RegionFile regionfile1 = new RegionFile(file2);
/* 31 */     regionsByFilename.put(file2, regionfile1);
/* 32 */     return regionfile1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized void clearRegionFileReferences() {
/* 40 */     for (RegionFile regionfile : regionsByFilename.values()) {
/*    */       try {
/* 42 */         if (regionfile != null) {
/* 43 */           regionfile.close();
/*    */         }
/* 45 */       } catch (IOException ioexception) {
/* 46 */         ioexception.printStackTrace();
/*    */       } 
/*    */     } 
/*    */     
/* 50 */     regionsByFilename.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DataInputStream getChunkInputStream(File worldDir, int chunkX, int chunkZ) {
/* 57 */     RegionFile regionfile = createOrLoadRegionFile(worldDir, chunkX, chunkZ);
/* 58 */     return regionfile.getChunkDataInputStream(chunkX & 0x1F, chunkZ & 0x1F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DataOutputStream getChunkOutputStream(File worldDir, int chunkX, int chunkZ) {
/* 65 */     RegionFile regionfile = createOrLoadRegionFile(worldDir, chunkX, chunkZ);
/* 66 */     return regionfile.getChunkDataOutputStream(chunkX & 0x1F, chunkZ & 0x1F);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\chunk\storage\RegionFileCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */