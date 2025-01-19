/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.IJsonSerializable;
/*    */ import net.minecraft.util.TupleIntJsonSerializable;
/*    */ 
/*    */ 
/*    */ public class StatFileWriter
/*    */ {
/* 12 */   protected final Map<StatBase, TupleIntJsonSerializable> statsData = Maps.newConcurrentMap();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasAchievementUnlocked(Achievement achievementIn) {
/* 18 */     return (readStat(achievementIn) > 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canUnlockAchievement(Achievement achievementIn) {
/* 25 */     return !(achievementIn.parentAchievement != null && !hasAchievementUnlocked(achievementIn.parentAchievement));
/*    */   }
/*    */   
/*    */   public int func_150874_c(Achievement p_150874_1_) {
/* 29 */     if (hasAchievementUnlocked(p_150874_1_)) {
/* 30 */       return 0;
/*    */     }
/* 32 */     int i = 0;
/*    */     
/* 34 */     for (Achievement achievement = p_150874_1_.parentAchievement; achievement != null && !hasAchievementUnlocked(achievement); i++) {
/* 35 */       achievement = achievement.parentAchievement;
/*    */     }
/*    */     
/* 38 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public void increaseStat(EntityPlayer player, StatBase stat, int amount) {
/* 43 */     if (!stat.isAchievement() || canUnlockAchievement((Achievement)stat)) {
/* 44 */       unlockAchievement(player, stat, readStat(stat) + amount);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void unlockAchievement(EntityPlayer playerIn, StatBase statIn, int p_150873_3_) {
/* 52 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(statIn);
/*    */     
/* 54 */     if (tupleintjsonserializable == null) {
/* 55 */       tupleintjsonserializable = new TupleIntJsonSerializable();
/* 56 */       this.statsData.put(statIn, tupleintjsonserializable);
/*    */     } 
/*    */     
/* 59 */     tupleintjsonserializable.setIntegerValue(p_150873_3_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int readStat(StatBase stat) {
/* 66 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(stat);
/* 67 */     return (tupleintjsonserializable == null) ? 0 : tupleintjsonserializable.getIntegerValue();
/*    */   }
/*    */   
/*    */   public <T extends IJsonSerializable> T func_150870_b(StatBase p_150870_1_) {
/* 71 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(p_150870_1_);
/* 72 */     return (tupleintjsonserializable != null) ? (T)tupleintjsonserializable.getJsonSerializableValue() : null;
/*    */   }
/*    */   
/*    */   public <T extends IJsonSerializable> T func_150872_a(StatBase p_150872_1_, T p_150872_2_) {
/* 76 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(p_150872_1_);
/*    */     
/* 78 */     if (tupleintjsonserializable == null) {
/* 79 */       tupleintjsonserializable = new TupleIntJsonSerializable();
/* 80 */       this.statsData.put(p_150872_1_, tupleintjsonserializable);
/*    */     } 
/*    */     
/* 83 */     tupleintjsonserializable.setJsonSerializableValue((IJsonSerializable)p_150872_2_);
/* 84 */     return p_150872_2_;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\stats\StatFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */