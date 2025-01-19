/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.scoreboard.ScoreObjective;
/*    */ 
/*    */ public class S3DPacketDisplayScoreboard
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int position;
/*    */   private String scoreName;
/*    */   
/*    */   public S3DPacketDisplayScoreboard() {}
/*    */   
/*    */   public S3DPacketDisplayScoreboard(int positionIn, ScoreObjective scoreIn) {
/* 18 */     this.position = positionIn;
/*    */     
/* 20 */     if (scoreIn == null) {
/* 21 */       this.scoreName = "";
/*    */     } else {
/* 23 */       this.scoreName = scoreIn.getName();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 31 */     this.position = buf.readByte();
/* 32 */     this.scoreName = buf.readStringFromBuffer(16);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 39 */     buf.writeByte(this.position);
/* 40 */     buf.writeString(this.scoreName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 47 */     handler.handleDisplayScoreboard(this);
/*    */   }
/*    */   
/*    */   public int func_149371_c() {
/* 51 */     return this.position;
/*    */   }
/*    */   
/*    */   public String func_149370_d() {
/* 55 */     return this.scoreName;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S3DPacketDisplayScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */