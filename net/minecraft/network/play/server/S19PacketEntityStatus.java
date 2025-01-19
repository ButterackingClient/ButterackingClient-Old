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
/*    */ public class S19PacketEntityStatus
/*    */   implements Packet<INetHandlerPlayClient> {
/*    */   private int entityId;
/*    */   private byte logicOpcode;
/*    */   
/*    */   public S19PacketEntityStatus() {}
/*    */   
/*    */   public S19PacketEntityStatus(Entity entityIn, byte opCodeIn) {
/* 19 */     this.entityId = entityIn.getEntityId();
/* 20 */     this.logicOpcode = opCodeIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 27 */     this.entityId = buf.readInt();
/* 28 */     this.logicOpcode = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 35 */     buf.writeInt(this.entityId);
/* 36 */     buf.writeByte(this.logicOpcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 43 */     handler.handleEntityStatus(this);
/*    */   }
/*    */   
/*    */   public Entity getEntity(World worldIn) {
/* 47 */     return worldIn.getEntityByID(this.entityId);
/*    */   }
/*    */   
/*    */   public byte getOpCode() {
/* 51 */     return this.logicOpcode;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S19PacketEntityStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */