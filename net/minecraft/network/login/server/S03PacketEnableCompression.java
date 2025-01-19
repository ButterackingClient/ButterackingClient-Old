/*    */ package net.minecraft.network.login.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginClient;
/*    */ 
/*    */ public class S03PacketEnableCompression
/*    */   implements Packet<INetHandlerLoginClient> {
/*    */   private int compressionTreshold;
/*    */   
/*    */   public S03PacketEnableCompression() {}
/*    */   
/*    */   public S03PacketEnableCompression(int compressionTresholdIn) {
/* 16 */     this.compressionTreshold = compressionTresholdIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 23 */     this.compressionTreshold = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 30 */     buf.writeVarIntToBuffer(this.compressionTreshold);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerLoginClient handler) {
/* 37 */     handler.handleEnableCompression(this);
/*    */   }
/*    */   
/*    */   public int getCompressionTreshold() {
/* 41 */     return this.compressionTreshold;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\login\server\S03PacketEnableCompression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */