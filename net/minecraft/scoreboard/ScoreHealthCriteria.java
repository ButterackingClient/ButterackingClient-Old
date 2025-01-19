/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ScoreHealthCriteria
/*    */   extends ScoreDummyCriteria {
/*    */   public ScoreHealthCriteria(String name) {
/* 10 */     super(name);
/*    */   }
/*    */   
/*    */   public int setScore(List<EntityPlayer> p_96635_1_) {
/* 14 */     float f = 0.0F;
/*    */     
/* 16 */     for (EntityPlayer entityplayer : p_96635_1_) {
/* 17 */       f += entityplayer.getHealth() + entityplayer.getAbsorptionAmount();
/*    */     }
/*    */     
/* 20 */     if (p_96635_1_.size() > 0) {
/* 21 */       f /= p_96635_1_.size();
/*    */     }
/*    */     
/* 24 */     return MathHelper.ceiling_float_int(f);
/*    */   }
/*    */   
/*    */   public boolean isReadOnly() {
/* 28 */     return true;
/*    */   }
/*    */   
/*    */   public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
/* 32 */     return IScoreObjectiveCriteria.EnumRenderType.HEARTS;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\scoreboard\ScoreHealthCriteria.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */