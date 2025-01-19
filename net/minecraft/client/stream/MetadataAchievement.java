/*    */ package net.minecraft.client.stream;
/*    */ 
/*    */ import net.minecraft.stats.Achievement;
/*    */ 
/*    */ public class MetadataAchievement extends Metadata {
/*    */   public MetadataAchievement(Achievement p_i1032_1_) {
/*  7 */     super("achievement");
/*  8 */     func_152808_a("achievement_id", p_i1032_1_.statId);
/*  9 */     func_152808_a("achievement_name", p_i1032_1_.getStatName().getUnformattedText());
/* 10 */     func_152808_a("achievement_description", p_i1032_1_.getDescription());
/* 11 */     func_152807_a("Achievement '" + p_i1032_1_.getStatName().getUnformattedText() + "' obtained!");
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\stream\MetadataAchievement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */