/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import net.minecraft.world.storage.SaveFormatComparator;
/*    */ 
/*    */ public class RealmsLevelSummary implements Comparable<RealmsLevelSummary> {
/*    */   private SaveFormatComparator levelSummary;
/*    */   
/*    */   public RealmsLevelSummary(SaveFormatComparator p_i1109_1_) {
/*  9 */     this.levelSummary = p_i1109_1_;
/*    */   }
/*    */   
/*    */   public int getGameMode() {
/* 13 */     return this.levelSummary.getEnumGameType().getID();
/*    */   }
/*    */   
/*    */   public String getLevelId() {
/* 17 */     return this.levelSummary.getFileName();
/*    */   }
/*    */   
/*    */   public boolean hasCheats() {
/* 21 */     return this.levelSummary.getCheatsEnabled();
/*    */   }
/*    */   
/*    */   public boolean isHardcore() {
/* 25 */     return this.levelSummary.isHardcoreModeEnabled();
/*    */   }
/*    */   
/*    */   public boolean isRequiresConversion() {
/* 29 */     return this.levelSummary.requiresConversion();
/*    */   }
/*    */   
/*    */   public String getLevelName() {
/* 33 */     return this.levelSummary.getDisplayName();
/*    */   }
/*    */   
/*    */   public long getLastPlayed() {
/* 37 */     return this.levelSummary.getLastTimePlayed();
/*    */   }
/*    */   
/*    */   public int compareTo(SaveFormatComparator p_compareTo_1_) {
/* 41 */     return this.levelSummary.compareTo(p_compareTo_1_);
/*    */   }
/*    */   
/*    */   public long getSizeOnDisk() {
/* 45 */     return this.levelSummary.getSizeOnDisk();
/*    */   }
/*    */   
/*    */   public int compareTo(RealmsLevelSummary p_compareTo_1_) {
/* 49 */     return (this.levelSummary.getLastTimePlayed() < p_compareTo_1_.getLastPlayed()) ? 1 : ((this.levelSummary.getLastTimePlayed() > p_compareTo_1_.getLastPlayed()) ? -1 : this.levelSummary.getFileName().compareTo(p_compareTo_1_.getLevelId()));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\RealmsLevelSummary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */