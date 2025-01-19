/*     */ package net.minecraft.network.play.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class C0EPacketClickWindow
/*     */   implements Packet<INetHandlerPlayServer>
/*     */ {
/*     */   private int windowId;
/*     */   private int slotId;
/*     */   private int usedButton;
/*     */   private short actionNumber;
/*     */   private ItemStack clickedItem;
/*     */   private int mode;
/*     */   
/*     */   public C0EPacketClickWindow() {}
/*     */   
/*     */   public C0EPacketClickWindow(int windowId, int slotId, int usedButton, int mode, ItemStack clickedItem, short actionNumber) {
/*  45 */     this.windowId = windowId;
/*  46 */     this.slotId = slotId;
/*  47 */     this.usedButton = usedButton;
/*  48 */     this.clickedItem = (clickedItem != null) ? clickedItem.copy() : null;
/*  49 */     this.actionNumber = actionNumber;
/*  50 */     this.mode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayServer handler) {
/*  57 */     handler.processClickWindow(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  64 */     this.windowId = buf.readByte();
/*  65 */     this.slotId = buf.readShort();
/*  66 */     this.usedButton = buf.readByte();
/*  67 */     this.actionNumber = buf.readShort();
/*  68 */     this.mode = buf.readByte();
/*  69 */     this.clickedItem = buf.readItemStackFromBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  76 */     buf.writeByte(this.windowId);
/*  77 */     buf.writeShort(this.slotId);
/*  78 */     buf.writeByte(this.usedButton);
/*  79 */     buf.writeShort(this.actionNumber);
/*  80 */     buf.writeByte(this.mode);
/*  81 */     buf.writeItemStackToBuffer(this.clickedItem);
/*     */   }
/*     */   
/*     */   public int getWindowId() {
/*  85 */     return this.windowId;
/*     */   }
/*     */   
/*     */   public int getSlotId() {
/*  89 */     return this.slotId;
/*     */   }
/*     */   
/*     */   public int getUsedButton() {
/*  93 */     return this.usedButton;
/*     */   }
/*     */   
/*     */   public short getActionNumber() {
/*  97 */     return this.actionNumber;
/*     */   }
/*     */   
/*     */   public ItemStack getClickedItem() {
/* 101 */     return this.clickedItem;
/*     */   }
/*     */   
/*     */   public int getMode() {
/* 105 */     return this.mode;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C0EPacketClickWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */