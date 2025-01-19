/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class ScoreDummyCriteria
/*    */   implements IScoreObjectiveCriteria {
/*    */   private final String dummyName;
/*    */   
/*    */   public ScoreDummyCriteria(String name) {
/* 11 */     this.dummyName = name;
/* 12 */     IScoreObjectiveCriteria.INSTANCES.put(name, this);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 16 */     return this.dummyName;
/*    */   }
/*    */   
/*    */   public int setScore(List<EntityPlayer> p_96635_1_) {
/* 20 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean isReadOnly() {
/* 24 */     return false;
/*    */   }
/*    */   
/*    */   public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
/* 28 */     return IScoreObjectiveCriteria.EnumRenderType.INTEGER;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\scoreboard\ScoreDummyCriteria.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */