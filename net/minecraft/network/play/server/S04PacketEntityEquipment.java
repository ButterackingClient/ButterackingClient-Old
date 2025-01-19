/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S04PacketEntityEquipment
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int entityID;
/*    */   private int equipmentSlot;
/*    */   private ItemStack itemStack;
/*    */   
/*    */   public S04PacketEntityEquipment() {}
/*    */   
/*    */   public S04PacketEntityEquipment(int entityIDIn, int p_i45221_2_, ItemStack itemStackIn) {
/* 19 */     this.entityID = entityIDIn;
/* 20 */     this.equipmentSlot = p_i45221_2_;
/* 21 */     this.itemStack = (itemStackIn == null) ? null : itemStackIn.copy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 28 */     this.entityID = buf.readVarIntFromBuffer();
/* 29 */     this.equipmentSlot = buf.readShort();
/* 30 */     this.itemStack = buf.readItemStackFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 37 */     buf.writeVarIntToBuffer(this.entityID);
/* 38 */     buf.writeShort(this.equipmentSlot);
/* 39 */     buf.writeItemStackToBuffer(this.itemStack);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 46 */     handler.handleEntityEquipment(this);
/*    */   }
/*    */   
/*    */   public ItemStack getItemStack() {
/* 50 */     return this.itemStack;
/*    */   }
/*    */   
/*    */   public int getEntityID() {
/* 54 */     return this.entityID;
/*    */   }
/*    */   
/*    */   public int getEquipmentSlot() {
/* 58 */     return this.equipmentSlot;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S04PacketEntityEquipment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */