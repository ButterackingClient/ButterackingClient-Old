/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*    */ import net.minecraft.scoreboard.ScoreObjective;
/*    */ 
/*    */ public class S3BPacketScoreboardObjective
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private String objectiveName;
/*    */   private String objectiveValue;
/*    */   private IScoreObjectiveCriteria.EnumRenderType type;
/*    */   private int field_149342_c;
/*    */   
/*    */   public S3BPacketScoreboardObjective() {}
/*    */   
/*    */   public S3BPacketScoreboardObjective(ScoreObjective p_i45224_1_, int p_i45224_2_) {
/* 21 */     this.objectiveName = p_i45224_1_.getName();
/* 22 */     this.objectiveValue = p_i45224_1_.getDisplayName();
/* 23 */     this.type = p_i45224_1_.getCriteria().getRenderType();
/* 24 */     this.field_149342_c = p_i45224_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 31 */     this.objectiveName = buf.readStringFromBuffer(16);
/* 32 */     this.field_149342_c = buf.readByte();
/*    */     
/* 34 */     if (this.field_149342_c == 0 || this.field_149342_c == 2) {
/* 35 */       this.objectiveValue = buf.readStringFromBuffer(32);
/* 36 */       this.type = IScoreObjectiveCriteria.EnumRenderType.func_178795_a(buf.readStringFromBuffer(16));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 44 */     buf.writeString(this.objectiveName);
/* 45 */     buf.writeByte(this.field_149342_c);
/*    */     
/* 47 */     if (this.field_149342_c == 0 || this.field_149342_c == 2) {
/* 48 */       buf.writeString(this.objectiveValue);
/* 49 */       buf.writeString(this.type.func_178796_a());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 57 */     handler.handleScoreboardObjective(this);
/*    */   }
/*    */   
/*    */   public String func_149339_c() {
/* 61 */     return this.objectiveName;
/*    */   }
/*    */   
/*    */   public String func_149337_d() {
/* 65 */     return this.objectiveValue;
/*    */   }
/*    */   
/*    */   public int func_149338_e() {
/* 69 */     return this.field_149342_c;
/*    */   }
/*    */   
/*    */   public IScoreObjectiveCriteria.EnumRenderType func_179817_d() {
/* 73 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S3BPacketScoreboardObjective.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */