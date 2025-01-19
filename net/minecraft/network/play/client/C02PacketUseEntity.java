/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.Vec3;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class C02PacketUseEntity
/*    */   implements Packet<INetHandlerPlayServer> {
/*    */   private int entityId;
/*    */   private Action action;
/*    */   private Vec3 hitVec;
/*    */   
/*    */   public C02PacketUseEntity() {}
/*    */   
/*    */   public C02PacketUseEntity(Entity entity, Action action) {
/* 21 */     this.entityId = entity.getEntityId();
/* 22 */     this.action = action;
/*    */   }
/*    */   
/*    */   public C02PacketUseEntity(Entity entity, Vec3 hitVec) {
/* 26 */     this(entity, Action.INTERACT_AT);
/* 27 */     this.hitVec = hitVec;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.entityId = buf.readVarIntFromBuffer();
/* 35 */     this.action = (Action)buf.readEnumValue(Action.class);
/*    */     
/* 37 */     if (this.action == Action.INTERACT_AT) {
/* 38 */       this.hitVec = new Vec3(buf.readFloat(), buf.readFloat(), buf.readFloat());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 46 */     buf.writeVarIntToBuffer(this.entityId);
/* 47 */     buf.writeEnumValue(this.action);
/*    */     
/* 49 */     if (this.action == Action.INTERACT_AT) {
/* 50 */       buf.writeFloat((float)this.hitVec.xCoord);
/* 51 */       buf.writeFloat((float)this.hitVec.yCoord);
/* 52 */       buf.writeFloat((float)this.hitVec.zCoord);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 60 */     handler.processUseEntity(this);
/*    */   }
/*    */   
/*    */   public Entity getEntityFromWorld(World worldIn) {
/* 64 */     return worldIn.getEntityByID(this.entityId);
/*    */   }
/*    */   
/*    */   public Action getAction() {
/* 68 */     return this.action;
/*    */   }
/*    */   
/*    */   public Vec3 getHitVec() {
/* 72 */     return this.hitVec;
/*    */   }
/*    */   
/*    */   public enum Action {
/* 76 */     INTERACT,
/* 77 */     ATTACK,
/* 78 */     INTERACT_AT;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C02PacketUseEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */