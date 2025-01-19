/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S46PacketSetCompressionLevel
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int threshold;
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 16 */     this.threshold = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 23 */     buf.writeVarIntToBuffer(this.threshold);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 30 */     handler.handleSetCompressionLevel(this);
/*    */   }
/*    */   
/*    */   public int getThreshold() {
/* 34 */     return this.threshold;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S46PacketSetCompressionLevel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */