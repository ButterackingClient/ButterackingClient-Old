/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ public class C19PacketResourcePackStatus
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private String hash;
/*    */   private Action status;
/*    */   
/*    */   public C19PacketResourcePackStatus() {}
/*    */   
/*    */   public C19PacketResourcePackStatus(String hashIn, Action statusIn) {
/* 17 */     if (hashIn.length() > 40) {
/* 18 */       hashIn = hashIn.substring(0, 40);
/*    */     }
/*    */     
/* 21 */     this.hash = hashIn;
/* 22 */     this.status = statusIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 29 */     this.hash = buf.readStringFromBuffer(40);
/* 30 */     this.status = (Action)buf.readEnumValue(Action.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 37 */     buf.writeString(this.hash);
/* 38 */     buf.writeEnumValue(this.status);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 45 */     handler.handleResourcePackStatus(this);
/*    */   }
/*    */   
/*    */   public enum Action {
/* 49 */     SUCCESSFULLY_LOADED,
/* 50 */     DECLINED,
/* 51 */     FAILED_DOWNLOAD,
/* 52 */     ACCEPTED;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C19PacketResourcePackStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */