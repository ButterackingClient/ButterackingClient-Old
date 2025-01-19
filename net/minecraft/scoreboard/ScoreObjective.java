/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScoreObjective
/*    */ {
/*    */   private final Scoreboard theScoreboard;
/*    */   private final String name;
/*    */   private final IScoreObjectiveCriteria objectiveCriteria;
/*    */   private IScoreObjectiveCriteria.EnumRenderType renderType;
/*    */   private String displayName;
/*    */   
/*    */   public ScoreObjective(Scoreboard theScoreboardIn, String nameIn, IScoreObjectiveCriteria objectiveCriteriaIn) {
/* 15 */     this.theScoreboard = theScoreboardIn;
/* 16 */     this.name = nameIn;
/* 17 */     this.objectiveCriteria = objectiveCriteriaIn;
/* 18 */     this.displayName = nameIn;
/* 19 */     this.renderType = objectiveCriteriaIn.getRenderType();
/*    */   }
/*    */   
/*    */   public Scoreboard getScoreboard() {
/* 23 */     return this.theScoreboard;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 27 */     return this.name;
/*    */   }
/*    */   
/*    */   public IScoreObjectiveCriteria getCriteria() {
/* 31 */     return this.objectiveCriteria;
/*    */   }
/*    */   
/*    */   public String getDisplayName() {
/* 35 */     return this.displayName;
/*    */   }
/*    */   
/*    */   public void setDisplayName(String nameIn) {
/* 39 */     this.displayName = nameIn;
/* 40 */     this.theScoreboard.onObjectiveDisplayNameChanged(this);
/*    */   }
/*    */   
/*    */   public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
/* 44 */     return this.renderType;
/*    */   }
/*    */   
/*    */   public void setRenderType(IScoreObjectiveCriteria.EnumRenderType type) {
/* 48 */     this.renderType = type;
/* 49 */     this.theScoreboard.onObjectiveDisplayNameChanged(this);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\scoreboard\ScoreObjective.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */