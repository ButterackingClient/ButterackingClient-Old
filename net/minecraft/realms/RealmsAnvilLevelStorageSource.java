/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.AnvilConverterException;
/*    */ import net.minecraft.util.IProgressUpdate;
/*    */ import net.minecraft.world.storage.ISaveFormat;
/*    */ import net.minecraft.world.storage.SaveFormatComparator;
/*    */ 
/*    */ 
/*    */ public class RealmsAnvilLevelStorageSource
/*    */ {
/*    */   private ISaveFormat levelStorageSource;
/*    */   
/*    */   public RealmsAnvilLevelStorageSource(ISaveFormat levelStorageSourceIn) {
/* 16 */     this.levelStorageSource = levelStorageSourceIn;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 20 */     return this.levelStorageSource.getName();
/*    */   }
/*    */   
/*    */   public boolean levelExists(String p_levelExists_1_) {
/* 24 */     return this.levelStorageSource.canLoadWorld(p_levelExists_1_);
/*    */   }
/*    */   
/*    */   public boolean convertLevel(String p_convertLevel_1_, IProgressUpdate p_convertLevel_2_) {
/* 28 */     return this.levelStorageSource.convertMapFormat(p_convertLevel_1_, p_convertLevel_2_);
/*    */   }
/*    */   
/*    */   public boolean requiresConversion(String p_requiresConversion_1_) {
/* 32 */     return this.levelStorageSource.isOldMapFormat(p_requiresConversion_1_);
/*    */   }
/*    */   
/*    */   public boolean isNewLevelIdAcceptable(String p_isNewLevelIdAcceptable_1_) {
/* 36 */     return this.levelStorageSource.isNewLevelIdAcceptable(p_isNewLevelIdAcceptable_1_);
/*    */   }
/*    */   
/*    */   public boolean deleteLevel(String p_deleteLevel_1_) {
/* 40 */     return this.levelStorageSource.deleteWorldDirectory(p_deleteLevel_1_);
/*    */   }
/*    */   
/*    */   public boolean isConvertible(String p_isConvertible_1_) {
/* 44 */     return this.levelStorageSource.isConvertible(p_isConvertible_1_);
/*    */   }
/*    */   
/*    */   public void renameLevel(String p_renameLevel_1_, String p_renameLevel_2_) {
/* 48 */     this.levelStorageSource.renameWorld(p_renameLevel_1_, p_renameLevel_2_);
/*    */   }
/*    */   
/*    */   public void clearAll() {
/* 52 */     this.levelStorageSource.flushCache();
/*    */   }
/*    */   
/*    */   public List<RealmsLevelSummary> getLevelList() throws AnvilConverterException {
/* 56 */     List<RealmsLevelSummary> list = Lists.newArrayList();
/*    */     
/* 58 */     for (SaveFormatComparator saveformatcomparator : this.levelStorageSource.getSaveList()) {
/* 59 */       list.add(new RealmsLevelSummary(saveformatcomparator));
/*    */     }
/*    */     
/* 62 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\RealmsAnvilLevelStorageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */