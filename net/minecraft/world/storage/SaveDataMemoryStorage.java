/*    */ package net.minecraft.world.storage;
/*    */ 
/*    */ import net.minecraft.world.WorldSavedData;
/*    */ 
/*    */ public class SaveDataMemoryStorage extends MapStorage {
/*    */   public SaveDataMemoryStorage() {
/*  7 */     super(null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldSavedData loadData(Class<? extends WorldSavedData> clazz, String dataIdentifier) {
/* 15 */     return this.loadedDataMap.get(dataIdentifier);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setData(String dataIdentifier, WorldSavedData data) {
/* 22 */     this.loadedDataMap.put(dataIdentifier, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveAllData() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getUniqueDataId(String key) {
/* 35 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\storage\SaveDataMemoryStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */