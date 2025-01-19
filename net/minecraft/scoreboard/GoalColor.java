/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ 
/*    */ public class GoalColor
/*    */   implements IScoreObjectiveCriteria {
/*    */   private final String goalName;
/*    */   
/*    */   public GoalColor(String p_i45549_1_, EnumChatFormatting p_i45549_2_) {
/* 12 */     this.goalName = String.valueOf(p_i45549_1_) + p_i45549_2_.getFriendlyName();
/* 13 */     IScoreObjectiveCriteria.INSTANCES.put(this.goalName, this);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 17 */     return this.goalName;
/*    */   }
/*    */   
/*    */   public int setScore(List<EntityPlayer> p_96635_1_) {
/* 21 */     return 0;
/*    */   }
/*    */   
/*    */   public boolean isReadOnly() {
/* 25 */     return false;
/*    */   }
/*    */   
/*    */   public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
/* 29 */     return IScoreObjectiveCriteria.EnumRenderType.INTEGER;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\scoreboard\GoalColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */