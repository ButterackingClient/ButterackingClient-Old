/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class S18PacketEntityTeleport
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private int entityId;
/*     */   private int posX;
/*     */   private int posY;
/*     */   private int posZ;
/*     */   private byte yaw;
/*     */   private byte pitch;
/*     */   private boolean onGround;
/*     */   
/*     */   public S18PacketEntityTeleport() {}
/*     */   
/*     */   public S18PacketEntityTeleport(Entity entityIn) {
/*  24 */     this.entityId = entityIn.getEntityId();
/*  25 */     this.posX = MathHelper.floor_double(entityIn.posX * 32.0D);
/*  26 */     this.posY = MathHelper.floor_double(entityIn.posY * 32.0D);
/*  27 */     this.posZ = MathHelper.floor_double(entityIn.posZ * 32.0D);
/*  28 */     this.yaw = (byte)(int)(entityIn.rotationYaw * 256.0F / 360.0F);
/*  29 */     this.pitch = (byte)(int)(entityIn.rotationPitch * 256.0F / 360.0F);
/*  30 */     this.onGround = entityIn.onGround;
/*     */   }
/*     */   
/*     */   public S18PacketEntityTeleport(int entityIdIn, int posXIn, int posYIn, int posZIn, byte yawIn, byte pitchIn, boolean onGroundIn) {
/*  34 */     this.entityId = entityIdIn;
/*  35 */     this.posX = posXIn;
/*  36 */     this.posY = posYIn;
/*  37 */     this.posZ = posZIn;
/*  38 */     this.yaw = yawIn;
/*  39 */     this.pitch = pitchIn;
/*  40 */     this.onGround = onGroundIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  47 */     this.entityId = buf.readVarIntFromBuffer();
/*  48 */     this.posX = buf.readInt();
/*  49 */     this.posY = buf.readInt();
/*  50 */     this.posZ = buf.readInt();
/*  51 */     this.yaw = buf.readByte();
/*  52 */     this.pitch = buf.readByte();
/*  53 */     this.onGround = buf.readBoolean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  60 */     buf.writeVarIntToBuffer(this.entityId);
/*  61 */     buf.writeInt(this.posX);
/*  62 */     buf.writeInt(this.posY);
/*  63 */     buf.writeInt(this.posZ);
/*  64 */     buf.writeByte(this.yaw);
/*  65 */     buf.writeByte(this.pitch);
/*  66 */     buf.writeBoolean(this.onGround);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  73 */     handler.handleEntityTeleport(this);
/*     */   }
/*     */   
/*     */   public int getEntityId() {
/*  77 */     return this.entityId;
/*     */   }
/*     */   
/*     */   public int getX() {
/*  81 */     return this.posX;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  85 */     return this.posY;
/*     */   }
/*     */   
/*     */   public int getZ() {
/*  89 */     return this.posZ;
/*     */   }
/*     */   
/*     */   public byte getYaw() {
/*  93 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public byte getPitch() {
/*  97 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public boolean getOnGround() {
/* 101 */     return this.onGround;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S18PacketEntityTeleport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */