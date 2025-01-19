/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.scoreboard.Score;
/*    */ import net.minecraft.scoreboard.ScoreObjective;
/*    */ import net.minecraft.scoreboard.Scoreboard;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatComponentScore
/*    */   extends ChatComponentStyle
/*    */ {
/*    */   private final String name;
/*    */   private final String objective;
/* 15 */   private String value = "";
/*    */   
/*    */   public ChatComponentScore(String nameIn, String objectiveIn) {
/* 18 */     this.name = nameIn;
/* 19 */     this.objective = objectiveIn;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 23 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getObjective() {
/* 27 */     return this.objective;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValue(String valueIn) {
/* 34 */     this.value = valueIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnformattedTextForChat() {
/* 42 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*    */     
/* 44 */     if (minecraftserver != null && minecraftserver.isAnvilFileSet() && StringUtils.isNullOrEmpty(this.value)) {
/* 45 */       Scoreboard scoreboard = minecraftserver.worldServerForDimension(0).getScoreboard();
/* 46 */       ScoreObjective scoreobjective = scoreboard.getObjective(this.objective);
/*    */       
/* 48 */       if (scoreboard.entityHasObjective(this.name, scoreobjective)) {
/* 49 */         Score score = scoreboard.getValueFromObjective(this.name, scoreobjective);
/* 50 */         setValue(String.format("%d", new Object[] { Integer.valueOf(score.getScorePoints()) }));
/*    */       } else {
/* 52 */         this.value = "";
/*    */       } 
/*    */     } 
/*    */     
/* 56 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChatComponentScore createCopy() {
/* 63 */     ChatComponentScore chatcomponentscore = new ChatComponentScore(this.name, this.objective);
/* 64 */     chatcomponentscore.setValue(this.value);
/* 65 */     chatcomponentscore.setChatStyle(getChatStyle().createShallowCopy());
/*    */     
/* 67 */     for (IChatComponent ichatcomponent : getSiblings()) {
/* 68 */       chatcomponentscore.appendSibling(ichatcomponent.createCopy());
/*    */     }
/*    */     
/* 71 */     return chatcomponentscore;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 75 */     if (this == p_equals_1_)
/* 76 */       return true; 
/* 77 */     if (!(p_equals_1_ instanceof ChatComponentScore)) {
/* 78 */       return false;
/*    */     }
/* 80 */     ChatComponentScore chatcomponentscore = (ChatComponentScore)p_equals_1_;
/* 81 */     return (this.name.equals(chatcomponentscore.name) && this.objective.equals(chatcomponentscore.objective) && super.equals(p_equals_1_));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 86 */     return "ScoreComponent{name='" + this.name + '\'' + "objective='" + this.objective + '\'' + ", siblings=" + this.siblings + ", style=" + getChatStyle() + '}';
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ChatComponentScore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */