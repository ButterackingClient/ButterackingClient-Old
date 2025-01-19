/*    */ package net.minecraft.network.status.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.status.INetHandlerStatusServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class C00PacketServerQuery
/*    */   implements Packet<INetHandlerStatusServer>
/*    */ {
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {}
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {}
/*    */   
/*    */   public void processPacket(INetHandlerStatusServer handler) {
/* 26 */     handler.processServerQuery(this);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\status\client\C00PacketServerQuery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */