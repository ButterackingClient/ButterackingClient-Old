/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import net.minecraft.scoreboard.ScoreDummyCriteria;
/*    */ 
/*    */ public class ObjectiveStat extends ScoreDummyCriteria {
/*    */   private final StatBase stat;
/*    */   
/*    */   public ObjectiveStat(StatBase statIn) {
/*  9 */     super(statIn.statId);
/* 10 */     this.stat = statIn;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\stats\ObjectiveStat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */