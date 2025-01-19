/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class Score
/*    */ {
/*  9 */   public static final Comparator<Score> scoreComparator = new Comparator<Score>() {
/*    */       public int compare(Score p_compare_1_, Score p_compare_2_) {
/* 11 */         return (p_compare_1_.getScorePoints() > p_compare_2_.getScorePoints()) ? 1 : ((p_compare_1_.getScorePoints() < p_compare_2_.getScorePoints()) ? -1 : p_compare_2_.getPlayerName().compareToIgnoreCase(p_compare_1_.getPlayerName()));
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   private final Scoreboard theScoreboard;
/*    */   
/*    */   private final ScoreObjective theScoreObjective;
/*    */   private final String scorePlayerName;
/*    */   
/*    */   public Score(Scoreboard theScoreboardIn, ScoreObjective theScoreObjectiveIn, String scorePlayerNameIn) {
/* 22 */     this.theScoreboard = theScoreboardIn;
/* 23 */     this.theScoreObjective = theScoreObjectiveIn;
/* 24 */     this.scorePlayerName = scorePlayerNameIn;
/* 25 */     this.forceUpdate = true;
/*    */   }
/*    */   private int scorePoints; private boolean locked; private boolean forceUpdate;
/*    */   public void increseScore(int amount) {
/* 29 */     if (this.theScoreObjective.getCriteria().isReadOnly()) {
/* 30 */       throw new IllegalStateException("Cannot modify read-only score");
/*    */     }
/* 32 */     setScorePoints(getScorePoints() + amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decreaseScore(int amount) {
/* 37 */     if (this.theScoreObjective.getCriteria().isReadOnly()) {
/* 38 */       throw new IllegalStateException("Cannot modify read-only score");
/*    */     }
/* 40 */     setScorePoints(getScorePoints() - amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_96648_a() {
/* 45 */     if (this.theScoreObjective.getCriteria().isReadOnly()) {
/* 46 */       throw new IllegalStateException("Cannot modify read-only score");
/*    */     }
/* 48 */     increseScore(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getScorePoints() {
/* 53 */     return this.scorePoints;
/*    */   }
/*    */   
/*    */   public void setScorePoints(int points) {
/* 57 */     int i = this.scorePoints;
/* 58 */     this.scorePoints = points;
/*    */     
/* 60 */     if (i != points || this.forceUpdate) {
/* 61 */       this.forceUpdate = false;
/* 62 */       getScoreScoreboard().func_96536_a(this);
/*    */     } 
/*    */   }
/*    */   
/*    */   public ScoreObjective getObjective() {
/* 67 */     return this.theScoreObjective;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPlayerName() {
/* 74 */     return this.scorePlayerName;
/*    */   }
/*    */   
/*    */   public Scoreboard getScoreScoreboard() {
/* 78 */     return this.theScoreboard;
/*    */   }
/*    */   
/*    */   public boolean isLocked() {
/* 82 */     return this.locked;
/*    */   }
/*    */   
/*    */   public void setLocked(boolean locked) {
/* 86 */     this.locked = locked;
/*    */   }
/*    */   
/*    */   public void func_96651_a(List<EntityPlayer> p_96651_1_) {
/* 90 */     setScorePoints(this.theScoreObjective.getCriteria().setScore(p_96651_1_));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\scoreboard\Score.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */