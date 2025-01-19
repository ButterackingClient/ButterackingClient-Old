/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S48PacketResourcePackSend
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private String url;
/*    */   private String hash;
/*    */   
/*    */   public S48PacketResourcePackSend() {}
/*    */   
/*    */   public S48PacketResourcePackSend(String url, String hash) {
/* 17 */     this.url = url;
/* 18 */     this.hash = hash;
/*    */     
/* 20 */     if (hash.length() > 40) {
/* 21 */       throw new IllegalArgumentException("Hash is too long (max 40, was " + hash.length() + ")");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 29 */     this.url = buf.readStringFromBuffer(32767);
/* 30 */     this.hash = buf.readStringFromBuffer(40);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 37 */     buf.writeString(this.url);
/* 38 */     buf.writeString(this.hash);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 45 */     handler.handleResourcePack(this);
/*    */   }
/*    */   
/*    */   public String getURL() {
/* 49 */     return this.url;
/*    */   }
/*    */   
/*    */   public String getHash() {
/* 53 */     return this.hash;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S48PacketResourcePackSend.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */