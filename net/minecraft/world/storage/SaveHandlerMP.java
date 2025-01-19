/*    */ package net.minecraft.world.storage;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.MinecraftException;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SaveHandlerMP
/*    */   implements ISaveHandler
/*    */ {
/*    */   public WorldInfo loadWorldInfo() {
/* 15 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void checkSessionLock() throws MinecraftException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChunkLoader getChunkLoader(WorldProvider provider) {
/* 28 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveWorldInfo(WorldInfo worldInformation) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public IPlayerFileData getPlayerNBTManager() {
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public File getMapFileFromName(String mapName) {
/* 57 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getWorldDirectoryName() {
/* 64 */     return "none";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public File getWorldDirectory() {
/* 71 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\storage\SaveHandlerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */