/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class S47PacketPlayerListHeaderFooter
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private IChatComponent header;
/*    */   private IChatComponent footer;
/*    */   
/*    */   public S47PacketPlayerListHeaderFooter() {}
/*    */   
/*    */   public S47PacketPlayerListHeaderFooter(IChatComponent headerIn) {
/* 18 */     this.header = headerIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 25 */     this.header = buf.readChatComponent();
/* 26 */     this.footer = buf.readChatComponent();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 33 */     buf.writeChatComponent(this.header);
/* 34 */     buf.writeChatComponent(this.footer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 41 */     handler.handlePlayerListHeaderFooter(this);
/*    */   }
/*    */   
/*    */   public IChatComponent getHeader() {
/* 45 */     return this.header;
/*    */   }
/*    */   
/*    */   public IChatComponent getFooter() {
/* 49 */     return this.footer;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S47PacketPlayerListHeaderFooter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */