/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ public class C01PacketChatMessage
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private String message;
/*    */   
/*    */   public C01PacketChatMessage() {}
/*    */   
/*    */   public C01PacketChatMessage(String messageIn) {
/* 16 */     if (messageIn.length() > 100) {
/* 17 */       messageIn = messageIn.substring(0, 100);
/*    */     }
/*    */     
/* 20 */     this.message = messageIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 27 */     this.message = buf.readStringFromBuffer(100);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeString(this.message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 41 */     handler.processChatMessage(this);
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 45 */     return this.message;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C01PacketChatMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */