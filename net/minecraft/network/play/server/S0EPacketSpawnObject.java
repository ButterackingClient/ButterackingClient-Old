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
/*     */ public class S0EPacketSpawnObject
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private int entityId;
/*     */   private int x;
/*     */   private int y;
/*     */   private int z;
/*     */   private int speedX;
/*     */   private int speedY;
/*     */   private int speedZ;
/*     */   private int pitch;
/*     */   private int yaw;
/*     */   private int type;
/*     */   private int field_149020_k;
/*     */   
/*     */   public S0EPacketSpawnObject() {}
/*     */   
/*     */   public S0EPacketSpawnObject(Entity entityIn, int typeIn) {
/*  28 */     this(entityIn, typeIn, 0);
/*     */   }
/*     */   
/*     */   public S0EPacketSpawnObject(Entity entityIn, int typeIn, int p_i45166_3_) {
/*  32 */     this.entityId = entityIn.getEntityId();
/*  33 */     this.x = MathHelper.floor_double(entityIn.posX * 32.0D);
/*  34 */     this.y = MathHelper.floor_double(entityIn.posY * 32.0D);
/*  35 */     this.z = MathHelper.floor_double(entityIn.posZ * 32.0D);
/*  36 */     this.pitch = MathHelper.floor_float(entityIn.rotationPitch * 256.0F / 360.0F);
/*  37 */     this.yaw = MathHelper.floor_float(entityIn.rotationYaw * 256.0F / 360.0F);
/*  38 */     this.type = typeIn;
/*  39 */     this.field_149020_k = p_i45166_3_;
/*     */     
/*  41 */     if (p_i45166_3_ > 0) {
/*  42 */       double d0 = entityIn.motionX;
/*  43 */       double d1 = entityIn.motionY;
/*  44 */       double d2 = entityIn.motionZ;
/*  45 */       double d3 = 3.9D;
/*     */       
/*  47 */       if (d0 < -d3) {
/*  48 */         d0 = -d3;
/*     */       }
/*     */       
/*  51 */       if (d1 < -d3) {
/*  52 */         d1 = -d3;
/*     */       }
/*     */       
/*  55 */       if (d2 < -d3) {
/*  56 */         d2 = -d3;
/*     */       }
/*     */       
/*  59 */       if (d0 > d3) {
/*  60 */         d0 = d3;
/*     */       }
/*     */       
/*  63 */       if (d1 > d3) {
/*  64 */         d1 = d3;
/*     */       }
/*     */       
/*  67 */       if (d2 > d3) {
/*  68 */         d2 = d3;
/*     */       }
/*     */       
/*  71 */       this.speedX = (int)(d0 * 8000.0D);
/*  72 */       this.speedY = (int)(d1 * 8000.0D);
/*  73 */       this.speedZ = (int)(d2 * 8000.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  81 */     this.entityId = buf.readVarIntFromBuffer();
/*  82 */     this.type = buf.readByte();
/*  83 */     this.x = buf.readInt();
/*  84 */     this.y = buf.readInt();
/*  85 */     this.z = buf.readInt();
/*  86 */     this.pitch = buf.readByte();
/*  87 */     this.yaw = buf.readByte();
/*  88 */     this.field_149020_k = buf.readInt();
/*     */     
/*  90 */     if (this.field_149020_k > 0) {
/*  91 */       this.speedX = buf.readShort();
/*  92 */       this.speedY = buf.readShort();
/*  93 */       this.speedZ = buf.readShort();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 101 */     buf.writeVarIntToBuffer(this.entityId);
/* 102 */     buf.writeByte(this.type);
/* 103 */     buf.writeInt(this.x);
/* 104 */     buf.writeInt(this.y);
/* 105 */     buf.writeInt(this.z);
/* 106 */     buf.writeByte(this.pitch);
/* 107 */     buf.writeByte(this.yaw);
/* 108 */     buf.writeInt(this.field_149020_k);
/*     */     
/* 110 */     if (this.field_149020_k > 0) {
/* 111 */       buf.writeShort(this.speedX);
/* 112 */       buf.writeShort(this.speedY);
/* 113 */       buf.writeShort(this.speedZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 121 */     handler.handleSpawnObject(this);
/*     */   }
/*     */   
/*     */   public int getEntityID() {
/* 125 */     return this.entityId;
/*     */   }
/*     */   
/*     */   public int getX() {
/* 129 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/* 133 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getZ() {
/* 137 */     return this.z;
/*     */   }
/*     */   
/*     */   public int getSpeedX() {
/* 141 */     return this.speedX;
/*     */   }
/*     */   
/*     */   public int getSpeedY() {
/* 145 */     return this.speedY;
/*     */   }
/*     */   
/*     */   public int getSpeedZ() {
/* 149 */     return this.speedZ;
/*     */   }
/*     */   
/*     */   public int getPitch() {
/* 153 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public int getYaw() {
/* 157 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public int getType() {
/* 161 */     return this.type;
/*     */   }
/*     */   
/*     */   public int func_149009_m() {
/* 165 */     return this.field_149020_k;
/*     */   }
/*     */   
/*     */   public void setX(int newX) {
/* 169 */     this.x = newX;
/*     */   }
/*     */   
/*     */   public void setY(int newY) {
/* 173 */     this.y = newY;
/*     */   }
/*     */   
/*     */   public void setZ(int newZ) {
/* 177 */     this.z = newZ;
/*     */   }
/*     */   
/*     */   public void setSpeedX(int newSpeedX) {
/* 181 */     this.speedX = newSpeedX;
/*     */   }
/*     */   
/*     */   public void setSpeedY(int newSpeedY) {
/* 185 */     this.speedY = newSpeedY;
/*     */   }
/*     */   
/*     */   public void setSpeedZ(int newSpeedZ) {
/* 189 */     this.speedZ = newSpeedZ;
/*     */   }
/*     */   
/*     */   public void func_149002_g(int p_149002_1_) {
/* 193 */     this.field_149020_k = p_149002_1_;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S0EPacketSpawnObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */