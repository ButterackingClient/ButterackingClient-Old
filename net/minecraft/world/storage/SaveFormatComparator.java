/*    */ package net.minecraft.world.storage;
/*    */ 
/*    */ import net.minecraft.world.WorldSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SaveFormatComparator
/*    */   implements Comparable<SaveFormatComparator>
/*    */ {
/*    */   private final String fileName;
/*    */   private final String displayName;
/*    */   private final long lastTimePlayed;
/*    */   private final long sizeOnDisk;
/*    */   private final boolean requiresConversion;
/*    */   private final WorldSettings.GameType theEnumGameType;
/*    */   private final boolean hardcore;
/*    */   private final boolean cheatsEnabled;
/*    */   
/*    */   public SaveFormatComparator(String fileNameIn, String displayNameIn, long lastTimePlayedIn, long sizeOnDiskIn, WorldSettings.GameType theEnumGameTypeIn, boolean requiresConversionIn, boolean hardcoreIn, boolean cheatsEnabledIn) {
/* 27 */     this.fileName = fileNameIn;
/* 28 */     this.displayName = displayNameIn;
/* 29 */     this.lastTimePlayed = lastTimePlayedIn;
/* 30 */     this.sizeOnDisk = sizeOnDiskIn;
/* 31 */     this.theEnumGameType = theEnumGameTypeIn;
/* 32 */     this.requiresConversion = requiresConversionIn;
/* 33 */     this.hardcore = hardcoreIn;
/* 34 */     this.cheatsEnabled = cheatsEnabledIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFileName() {
/* 41 */     return this.fileName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDisplayName() {
/* 48 */     return this.displayName;
/*    */   }
/*    */   
/*    */   public long getSizeOnDisk() {
/* 52 */     return this.sizeOnDisk;
/*    */   }
/*    */   
/*    */   public boolean requiresConversion() {
/* 56 */     return this.requiresConversion;
/*    */   }
/*    */   
/*    */   public long getLastTimePlayed() {
/* 60 */     return this.lastTimePlayed;
/*    */   }
/*    */   
/*    */   public int compareTo(SaveFormatComparator p_compareTo_1_) {
/* 64 */     return (this.lastTimePlayed < p_compareTo_1_.lastTimePlayed) ? 1 : ((this.lastTimePlayed > p_compareTo_1_.lastTimePlayed) ? -1 : this.fileName.compareTo(p_compareTo_1_.fileName));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldSettings.GameType getEnumGameType() {
/* 71 */     return this.theEnumGameType;
/*    */   }
/*    */   
/*    */   public boolean isHardcoreModeEnabled() {
/* 75 */     return this.hardcore;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getCheatsEnabled() {
/* 82 */     return this.cheatsEnabled;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\storage\SaveFormatComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */