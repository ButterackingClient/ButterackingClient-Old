/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class S1BPacketEntityAttach
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int leash;
/*    */   private int entityId;
/*    */   private int vehicleEntityId;
/*    */   
/*    */   public S1BPacketEntityAttach() {}
/*    */   
/*    */   public S1BPacketEntityAttach(int leashIn, Entity entityIn, Entity vehicle) {
/* 19 */     this.leash = leashIn;
/* 20 */     this.entityId = entityIn.getEntityId();
/* 21 */     this.vehicleEntityId = (vehicle != null) ? vehicle.getEntityId() : -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 28 */     this.entityId = buf.readInt();
/* 29 */     this.vehicleEntityId = buf.readInt();
/* 30 */     this.leash = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 37 */     buf.writeInt(this.entityId);
/* 38 */     buf.writeInt(this.vehicleEntityId);
/* 39 */     buf.writeByte(this.leash);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 46 */     handler.handleEntityAttach(this);
/*    */   }
/*    */   
/*    */   public int getLeash() {
/* 50 */     return this.leash;
/*    */   }
/*    */   
/*    */   public int getEntityId() {
/* 54 */     return this.entityId;
/*    */   }
/*    */   
/*    */   public int getVehicleEntityId() {
/* 58 */     return this.vehicleEntityId;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S1BPacketEntityAttach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */