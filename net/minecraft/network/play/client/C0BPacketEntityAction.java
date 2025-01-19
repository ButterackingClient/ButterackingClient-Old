/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ public class C0BPacketEntityAction
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private int entityID;
/*    */   private Action action;
/*    */   private int auxData;
/*    */   
/*    */   public C0BPacketEntityAction() {}
/*    */   
/*    */   public C0BPacketEntityAction(Entity entity, Action action) {
/* 19 */     this(entity, action, 0);
/*    */   }
/*    */   
/*    */   public C0BPacketEntityAction(Entity entity, Action action, int auxData) {
/* 23 */     this.entityID = entity.getEntityId();
/* 24 */     this.action = action;
/* 25 */     this.auxData = auxData;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.entityID = buf.readVarIntFromBuffer();
/* 33 */     this.action = (Action)buf.readEnumValue(Action.class);
/* 34 */     this.auxData = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 41 */     buf.writeVarIntToBuffer(this.entityID);
/* 42 */     buf.writeEnumValue(this.action);
/* 43 */     buf.writeVarIntToBuffer(this.auxData);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 50 */     handler.processEntityAction(this);
/*    */   }
/*    */   
/*    */   public Action getAction() {
/* 54 */     return this.action;
/*    */   }
/*    */   
/*    */   public int getAuxData() {
/* 58 */     return this.auxData;
/*    */   }
/*    */   
/*    */   public enum Action {
/* 62 */     START_SNEAKING,
/* 63 */     STOP_SNEAKING,
/* 64 */     STOP_SLEEPING,
/* 65 */     START_SPRINTING,
/* 66 */     STOP_SPRINTING,
/* 67 */     RIDING_JUMP,
/* 68 */     OPEN_INVENTORY;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C0BPacketEntityAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */