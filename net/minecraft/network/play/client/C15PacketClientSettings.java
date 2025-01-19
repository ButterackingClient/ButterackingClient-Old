/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ public class C15PacketClientSettings
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private String lang;
/*    */   private int view;
/*    */   private EntityPlayer.EnumChatVisibility chatVisibility;
/*    */   private boolean enableColors;
/*    */   private int modelPartFlags;
/*    */   
/*    */   public C15PacketClientSettings() {}
/*    */   
/*    */   public C15PacketClientSettings(String langIn, int viewIn, EntityPlayer.EnumChatVisibility chatVisibilityIn, boolean enableColorsIn, int modelPartFlagsIn) {
/* 21 */     this.lang = langIn;
/* 22 */     this.view = viewIn;
/* 23 */     this.chatVisibility = chatVisibilityIn;
/* 24 */     this.enableColors = enableColorsIn;
/* 25 */     this.modelPartFlags = modelPartFlagsIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.lang = buf.readStringFromBuffer(7);
/* 33 */     this.view = buf.readByte();
/* 34 */     this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(buf.readByte());
/* 35 */     this.enableColors = buf.readBoolean();
/* 36 */     this.modelPartFlags = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 43 */     buf.writeString(this.lang);
/* 44 */     buf.writeByte(this.view);
/* 45 */     buf.writeByte(this.chatVisibility.getChatVisibility());
/* 46 */     buf.writeBoolean(this.enableColors);
/* 47 */     buf.writeByte(this.modelPartFlags);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 54 */     handler.processClientSettings(this);
/*    */   }
/*    */   
/*    */   public String getLang() {
/* 58 */     return this.lang;
/*    */   }
/*    */   
/*    */   public EntityPlayer.EnumChatVisibility getChatVisibility() {
/* 62 */     return this.chatVisibility;
/*    */   }
/*    */   
/*    */   public boolean isColorsEnabled() {
/* 66 */     return this.enableColors;
/*    */   }
/*    */   
/*    */   public int getModelPartFlags() {
/* 70 */     return this.modelPartFlags;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C15PacketClientSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */