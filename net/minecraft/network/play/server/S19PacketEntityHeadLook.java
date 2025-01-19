/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class S19PacketEntityHeadLook
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int entityId;
/*    */   private byte yaw;
/*    */   
/*    */   public S19PacketEntityHeadLook() {}
/*    */   
/*    */   public S19PacketEntityHeadLook(Entity entityIn, byte p_i45214_2_) {
/* 19 */     this.entityId = entityIn.getEntityId();
/* 20 */     this.yaw = p_i45214_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 27 */     this.entityId = buf.readVarIntFromBuffer();
/* 28 */     this.yaw = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 35 */     buf.writeVarIntToBuffer(this.entityId);
/* 36 */     buf.writeByte(this.yaw);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 43 */     handler.handleEntityHeadLook(this);
/*    */   }
/*    */   
/*    */   public Entity getEntity(World worldIn) {
/* 47 */     return worldIn.getEntityByID(this.entityId);
/*    */   }
/*    */   
/*    */   public byte getYaw() {
/* 51 */     return this.yaw;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S19PacketEntityHeadLook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */