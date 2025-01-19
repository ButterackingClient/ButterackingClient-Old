/*    */ package net.minecraft.world.chunk.storage;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraft.world.storage.SaveHandler;
/*    */ import net.minecraft.world.storage.ThreadedFileIOBase;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ 
/*    */ public class AnvilSaveHandler
/*    */   extends SaveHandler
/*    */ {
/*    */   public AnvilSaveHandler(File savesDirectory, String directoryName, boolean storePlayerdata) {
/* 15 */     super(savesDirectory, directoryName, storePlayerdata);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChunkLoader getChunkLoader(WorldProvider provider) {
/* 22 */     File file1 = getWorldDirectory();
/*    */     
/* 24 */     if (provider instanceof net.minecraft.world.WorldProviderHell) {
/* 25 */       File file3 = new File(file1, "DIM-1");
/* 26 */       file3.mkdirs();
/* 27 */       return new AnvilChunkLoader(file3);
/* 28 */     }  if (provider instanceof net.minecraft.world.WorldProviderEnd) {
/* 29 */       File file2 = new File(file1, "DIM1");
/* 30 */       file2.mkdirs();
/* 31 */       return new AnvilChunkLoader(file2);
/*    */     } 
/* 33 */     return new AnvilChunkLoader(file1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {
/* 41 */     worldInformation.setSaveVersion(19133);
/* 42 */     super.saveWorldInfoWithPlayer(worldInformation, tagCompound);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() {
/*    */     try {
/* 50 */       ThreadedFileIOBase.getThreadedIOInstance().waitForFinish();
/* 51 */     } catch (InterruptedException interruptedexception) {
/* 52 */       interruptedexception.printStackTrace();
/*    */     } 
/*    */     
/* 55 */     RegionFileCache.clearRegionFileReferences();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\chunk\storage\AnvilSaveHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */