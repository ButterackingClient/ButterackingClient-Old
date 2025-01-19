/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class S2CPacketSpawnGlobalEntity
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityId;
/*    */   private int x;
/*    */   private int y;
/*    */   private int z;
/*    */   private int type;
/*    */   
/*    */   public S2CPacketSpawnGlobalEntity() {}
/*    */   
/*    */   public S2CPacketSpawnGlobalEntity(Entity entityIn) {
/* 23 */     this.entityId = entityIn.getEntityId();
/* 24 */     this.x = MathHelper.floor_double(entityIn.posX * 32.0D);
/* 25 */     this.y = MathHelper.floor_double(entityIn.posY * 32.0D);
/* 26 */     this.z = MathHelper.floor_double(entityIn.posZ * 32.0D);
/*    */     
/* 28 */     if (entityIn instanceof net.minecraft.entity.effect.EntityLightningBolt) {
/* 29 */       this.type = 1;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 37 */     this.entityId = buf.readVarIntFromBuffer();
/* 38 */     this.type = buf.readByte();
/* 39 */     this.x = buf.readInt();
/* 40 */     this.y = buf.readInt();
/* 41 */     this.z = buf.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 48 */     buf.writeVarIntToBuffer(this.entityId);
/* 49 */     buf.writeByte(this.type);
/* 50 */     buf.writeInt(this.x);
/* 51 */     buf.writeInt(this.y);
/* 52 */     buf.writeInt(this.z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 59 */     handler.handleSpawnGlobalEntity(this);
/*    */   }
/*    */   
/*    */   public int func_149052_c() {
/* 63 */     return this.entityId;
/*    */   }
/*    */   
/*    */   public int func_149051_d() {
/* 67 */     return this.x;
/*    */   }
/*    */   
/*    */   public int func_149050_e() {
/* 71 */     return this.y;
/*    */   }
/*    */   
/*    */   public int func_149049_f() {
/* 75 */     return this.z;
/*    */   }
/*    */   
/*    */   public int func_149053_g() {
/* 79 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S2CPacketSpawnGlobalEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */