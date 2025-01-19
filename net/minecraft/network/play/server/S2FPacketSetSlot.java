/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S2FPacketSetSlot
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int windowId;
/*    */   private int slot;
/*    */   private ItemStack item;
/*    */   
/*    */   public S2FPacketSetSlot() {}
/*    */   
/*    */   public S2FPacketSetSlot(int windowIdIn, int slotIn, ItemStack itemIn) {
/* 19 */     this.windowId = windowIdIn;
/* 20 */     this.slot = slotIn;
/* 21 */     this.item = (itemIn == null) ? null : itemIn.copy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 28 */     handler.handleSetSlot(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 35 */     this.windowId = buf.readByte();
/* 36 */     this.slot = buf.readShort();
/* 37 */     this.item = buf.readItemStackFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 44 */     buf.writeByte(this.windowId);
/* 45 */     buf.writeShort(this.slot);
/* 46 */     buf.writeItemStackToBuffer(this.item);
/*    */   }
/*    */   
/*    */   public int func_149175_c() {
/* 50 */     return this.windowId;
/*    */   }
/*    */   
/*    */   public int func_149173_d() {
/* 54 */     return this.slot;
/*    */   }
/*    */   
/*    */   public ItemStack func_149174_e() {
/* 58 */     return this.item;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S2FPacketSetSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */