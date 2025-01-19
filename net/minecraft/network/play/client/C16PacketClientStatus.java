/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ public class C16PacketClientStatus
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private EnumState status;
/*    */   
/*    */   public C16PacketClientStatus() {}
/*    */   
/*    */   public C16PacketClientStatus(EnumState statusIn) {
/* 16 */     this.status = statusIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 23 */     this.status = (EnumState)buf.readEnumValue(EnumState.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 30 */     buf.writeEnumValue(this.status);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 37 */     handler.processClientStatus(this);
/*    */   }
/*    */   
/*    */   public EnumState getStatus() {
/* 41 */     return this.status;
/*    */   }
/*    */   
/*    */   public enum EnumState {
/* 45 */     PERFORM_RESPAWN,
/* 46 */     REQUEST_STATS,
/* 47 */     OPEN_INVENTORY_ACHIEVEMENT;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C16PacketClientStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */