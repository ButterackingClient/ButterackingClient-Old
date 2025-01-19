/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S30PacketWindowItems
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int windowId;
/*    */   private ItemStack[] itemStacks;
/*    */   
/*    */   public S30PacketWindowItems() {}
/*    */   
/*    */   public S30PacketWindowItems(int windowIdIn, List<ItemStack> p_i45186_2_) {
/* 19 */     this.windowId = windowIdIn;
/* 20 */     this.itemStacks = new ItemStack[p_i45186_2_.size()];
/*    */     
/* 22 */     for (int i = 0; i < this.itemStacks.length; i++) {
/* 23 */       ItemStack itemstack = p_i45186_2_.get(i);
/* 24 */       this.itemStacks[i] = (itemstack == null) ? null : itemstack.copy();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.windowId = buf.readUnsignedByte();
/* 33 */     int i = buf.readShort();
/* 34 */     this.itemStacks = new ItemStack[i];
/*    */     
/* 36 */     for (int j = 0; j < i; j++) {
/* 37 */       this.itemStacks[j] = buf.readItemStackFromBuffer();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 45 */     buf.writeByte(this.windowId);
/* 46 */     buf.writeShort(this.itemStacks.length); byte b; int i;
/*    */     ItemStack[] arrayOfItemStack;
/* 48 */     for (i = (arrayOfItemStack = this.itemStacks).length, b = 0; b < i; ) { ItemStack itemstack = arrayOfItemStack[b];
/* 49 */       buf.writeItemStackToBuffer(itemstack);
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 57 */     handler.handleWindowItems(this);
/*    */   }
/*    */   
/*    */   public int func_148911_c() {
/* 61 */     return this.windowId;
/*    */   }
/*    */   
/*    */   public ItemStack[] getItemStacks() {
/* 65 */     return this.itemStacks;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S30PacketWindowItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */