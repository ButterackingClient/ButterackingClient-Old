/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class S2DPacketOpenWindow
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int windowId;
/*    */   private String inventoryType;
/*    */   private IChatComponent windowTitle;
/*    */   private int slotCount;
/*    */   private int entityId;
/*    */   
/*    */   public S2DPacketOpenWindow() {}
/*    */   
/*    */   public S2DPacketOpenWindow(int incomingWindowId, String incomingWindowTitle, IChatComponent windowTitleIn) {
/* 21 */     this(incomingWindowId, incomingWindowTitle, windowTitleIn, 0);
/*    */   }
/*    */   
/*    */   public S2DPacketOpenWindow(int windowIdIn, String guiId, IChatComponent windowTitleIn, int slotCountIn) {
/* 25 */     this.windowId = windowIdIn;
/* 26 */     this.inventoryType = guiId;
/* 27 */     this.windowTitle = windowTitleIn;
/* 28 */     this.slotCount = slotCountIn;
/*    */   }
/*    */   
/*    */   public S2DPacketOpenWindow(int windowIdIn, String guiId, IChatComponent windowTitleIn, int slotCountIn, int incomingEntityId) {
/* 32 */     this(windowIdIn, guiId, windowTitleIn, slotCountIn);
/* 33 */     this.entityId = incomingEntityId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 40 */     handler.handleOpenWindow(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 47 */     this.windowId = buf.readUnsignedByte();
/* 48 */     this.inventoryType = buf.readStringFromBuffer(32);
/* 49 */     this.windowTitle = buf.readChatComponent();
/* 50 */     this.slotCount = buf.readUnsignedByte();
/*    */     
/* 52 */     if (this.inventoryType.equals("EntityHorse")) {
/* 53 */       this.entityId = buf.readInt();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 61 */     buf.writeByte(this.windowId);
/* 62 */     buf.writeString(this.inventoryType);
/* 63 */     buf.writeChatComponent(this.windowTitle);
/* 64 */     buf.writeByte(this.slotCount);
/*    */     
/* 66 */     if (this.inventoryType.equals("EntityHorse")) {
/* 67 */       buf.writeInt(this.entityId);
/*    */     }
/*    */   }
/*    */   
/*    */   public int getWindowId() {
/* 72 */     return this.windowId;
/*    */   }
/*    */   
/*    */   public String getGuiId() {
/* 76 */     return this.inventoryType;
/*    */   }
/*    */   
/*    */   public IChatComponent getWindowTitle() {
/* 80 */     return this.windowTitle;
/*    */   }
/*    */   
/*    */   public int getSlotCount() {
/* 84 */     return this.slotCount;
/*    */   }
/*    */   
/*    */   public int getEntityId() {
/* 88 */     return this.entityId;
/*    */   }
/*    */   
/*    */   public boolean hasSlots() {
/* 92 */     return (this.slotCount > 0);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S2DPacketOpenWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */