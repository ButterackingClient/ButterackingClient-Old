/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class S02PacketChat
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private IChatComponent chatComponent;
/*    */   private byte type;
/*    */   
/*    */   public S02PacketChat() {}
/*    */   
/*    */   public S02PacketChat(IChatComponent component) {
/* 18 */     this(component, (byte)1);
/*    */   }
/*    */   
/*    */   public S02PacketChat(IChatComponent message, byte typeIn) {
/* 22 */     this.chatComponent = message;
/* 23 */     this.type = typeIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 30 */     this.chatComponent = buf.readChatComponent();
/* 31 */     this.type = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 38 */     buf.writeChatComponent(this.chatComponent);
/* 39 */     buf.writeByte(this.type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 46 */     handler.handleChat(this);
/*    */   }
/*    */   
/*    */   public IChatComponent getChatComponent() {
/* 50 */     return this.chatComponent;
/*    */   }
/*    */   
/*    */   public boolean isChat() {
/* 54 */     return !(this.type != 1 && this.type != 2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getType() {
/* 62 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S02PacketChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */