/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.stats.StatBase;
/*    */ import net.minecraft.stats.StatList;
/*    */ 
/*    */ 
/*    */ public class S37PacketStatistics
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private Map<StatBase, Integer> field_148976_a;
/*    */   
/*    */   public S37PacketStatistics() {}
/*    */   
/*    */   public S37PacketStatistics(Map<StatBase, Integer> p_i45173_1_) {
/* 22 */     this.field_148976_a = p_i45173_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 29 */     handler.handleStatistics(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 36 */     int i = buf.readVarIntFromBuffer();
/* 37 */     this.field_148976_a = Maps.newHashMap();
/*    */     
/* 39 */     for (int j = 0; j < i; j++) {
/* 40 */       StatBase statbase = StatList.getOneShotStat(buf.readStringFromBuffer(32767));
/* 41 */       int k = buf.readVarIntFromBuffer();
/*    */       
/* 43 */       if (statbase != null) {
/* 44 */         this.field_148976_a.put(statbase, Integer.valueOf(k));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 53 */     buf.writeVarIntToBuffer(this.field_148976_a.size());
/*    */     
/* 55 */     for (Map.Entry<StatBase, Integer> entry : this.field_148976_a.entrySet()) {
/* 56 */       buf.writeString(((StatBase)entry.getKey()).statId);
/* 57 */       buf.writeVarIntToBuffer(((Integer)entry.getValue()).intValue());
/*    */     } 
/*    */   }
/*    */   
/*    */   public Map<StatBase, Integer> func_148974_c() {
/* 62 */     return this.field_148976_a;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S37PacketStatistics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */