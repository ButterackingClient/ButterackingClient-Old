/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.scoreboard.Score;
/*    */ import net.minecraft.scoreboard.ScoreObjective;
/*    */ 
/*    */ public class S3CPacketUpdateScore implements Packet<INetHandlerPlayClient> {
/* 12 */   private String name = "";
/* 13 */   private String objective = "";
/*    */   
/*    */   private int value;
/*    */   
/*    */   private Action action;
/*    */ 
/*    */   
/*    */   public S3CPacketUpdateScore(Score scoreIn) {
/* 21 */     this.name = scoreIn.getPlayerName();
/* 22 */     this.objective = scoreIn.getObjective().getName();
/* 23 */     this.value = scoreIn.getScorePoints();
/* 24 */     this.action = Action.CHANGE;
/*    */   }
/*    */   
/*    */   public S3CPacketUpdateScore(String nameIn) {
/* 28 */     this.name = nameIn;
/* 29 */     this.objective = "";
/* 30 */     this.value = 0;
/* 31 */     this.action = Action.REMOVE;
/*    */   }
/*    */   
/*    */   public S3CPacketUpdateScore(String nameIn, ScoreObjective objectiveIn) {
/* 35 */     this.name = nameIn;
/* 36 */     this.objective = objectiveIn.getName();
/* 37 */     this.value = 0;
/* 38 */     this.action = Action.REMOVE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 45 */     this.name = buf.readStringFromBuffer(40);
/* 46 */     this.action = (Action)buf.readEnumValue(Action.class);
/* 47 */     this.objective = buf.readStringFromBuffer(16);
/*    */     
/* 49 */     if (this.action != Action.REMOVE) {
/* 50 */       this.value = buf.readVarIntFromBuffer();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 58 */     buf.writeString(this.name);
/* 59 */     buf.writeEnumValue(this.action);
/* 60 */     buf.writeString(this.objective);
/*    */     
/* 62 */     if (this.action != Action.REMOVE) {
/* 63 */       buf.writeVarIntToBuffer(this.value);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 71 */     handler.handleUpdateScore(this);
/*    */   }
/*    */   
/*    */   public String getPlayerName() {
/* 75 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getObjectiveName() {
/* 79 */     return this.objective;
/*    */   }
/*    */   
/*    */   public int getScoreValue() {
/* 83 */     return this.value;
/*    */   }
/*    */   public S3CPacketUpdateScore() {}
/*    */   public Action getScoreAction() {
/* 87 */     return this.action;
/*    */   }
/*    */   
/*    */   public enum Action {
/* 91 */     CHANGE,
/* 92 */     REMOVE;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S3CPacketUpdateScore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */