/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ public class C11PacketEnchantItem
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private int windowId;
/*    */   private int button;
/*    */   
/*    */   public C11PacketEnchantItem() {}
/*    */   
/*    */   public C11PacketEnchantItem(int windowId, int button) {
/* 17 */     this.windowId = windowId;
/* 18 */     this.button = button;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 25 */     handler.processEnchantItem(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.windowId = buf.readByte();
/* 33 */     this.button = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 40 */     buf.writeByte(this.windowId);
/* 41 */     buf.writeByte(this.button);
/*    */   }
/*    */   
/*    */   public int getWindowId() {
/* 45 */     return this.windowId;
/*    */   }
/*    */   
/*    */   public int getButton() {
/* 49 */     return this.button;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C11PacketEnchantItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */