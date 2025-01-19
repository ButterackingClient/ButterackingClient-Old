/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class S0FPacketSpawnMob implements Packet<INetHandlerPlayClient> {
/*     */   private int entityId;
/*     */   private int type;
/*     */   private int x;
/*     */   private int y;
/*     */   private int z;
/*     */   private int velocityX;
/*     */   private int velocityY;
/*     */   private int velocityZ;
/*     */   private byte yaw;
/*     */   private byte pitch;
/*     */   private byte headPitch;
/*     */   private DataWatcher field_149043_l;
/*     */   private List<DataWatcher.WatchableObject> watcher;
/*     */   
/*     */   public S0FPacketSpawnMob() {}
/*     */   
/*     */   public S0FPacketSpawnMob(EntityLivingBase entityIn) {
/*  33 */     this.entityId = entityIn.getEntityId();
/*  34 */     this.type = (byte)EntityList.getEntityID((Entity)entityIn);
/*  35 */     this.x = MathHelper.floor_double(entityIn.posX * 32.0D);
/*  36 */     this.y = MathHelper.floor_double(entityIn.posY * 32.0D);
/*  37 */     this.z = MathHelper.floor_double(entityIn.posZ * 32.0D);
/*  38 */     this.yaw = (byte)(int)(entityIn.rotationYaw * 256.0F / 360.0F);
/*  39 */     this.pitch = (byte)(int)(entityIn.rotationPitch * 256.0F / 360.0F);
/*  40 */     this.headPitch = (byte)(int)(entityIn.rotationYawHead * 256.0F / 360.0F);
/*  41 */     double d0 = 3.9D;
/*  42 */     double d1 = entityIn.motionX;
/*  43 */     double d2 = entityIn.motionY;
/*  44 */     double d3 = entityIn.motionZ;
/*     */     
/*  46 */     if (d1 < -d0) {
/*  47 */       d1 = -d0;
/*     */     }
/*     */     
/*  50 */     if (d2 < -d0) {
/*  51 */       d2 = -d0;
/*     */     }
/*     */     
/*  54 */     if (d3 < -d0) {
/*  55 */       d3 = -d0;
/*     */     }
/*     */     
/*  58 */     if (d1 > d0) {
/*  59 */       d1 = d0;
/*     */     }
/*     */     
/*  62 */     if (d2 > d0) {
/*  63 */       d2 = d0;
/*     */     }
/*     */     
/*  66 */     if (d3 > d0) {
/*  67 */       d3 = d0;
/*     */     }
/*     */     
/*  70 */     this.velocityX = (int)(d1 * 8000.0D);
/*  71 */     this.velocityY = (int)(d2 * 8000.0D);
/*  72 */     this.velocityZ = (int)(d3 * 8000.0D);
/*  73 */     this.field_149043_l = entityIn.getDataWatcher();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  80 */     this.entityId = buf.readVarIntFromBuffer();
/*  81 */     this.type = buf.readByte() & 0xFF;
/*  82 */     this.x = buf.readInt();
/*  83 */     this.y = buf.readInt();
/*  84 */     this.z = buf.readInt();
/*  85 */     this.yaw = buf.readByte();
/*  86 */     this.pitch = buf.readByte();
/*  87 */     this.headPitch = buf.readByte();
/*  88 */     this.velocityX = buf.readShort();
/*  89 */     this.velocityY = buf.readShort();
/*  90 */     this.velocityZ = buf.readShort();
/*  91 */     this.watcher = DataWatcher.readWatchedListFromPacketBuffer(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  98 */     buf.writeVarIntToBuffer(this.entityId);
/*  99 */     buf.writeByte(this.type & 0xFF);
/* 100 */     buf.writeInt(this.x);
/* 101 */     buf.writeInt(this.y);
/* 102 */     buf.writeInt(this.z);
/* 103 */     buf.writeByte(this.yaw);
/* 104 */     buf.writeByte(this.pitch);
/* 105 */     buf.writeByte(this.headPitch);
/* 106 */     buf.writeShort(this.velocityX);
/* 107 */     buf.writeShort(this.velocityY);
/* 108 */     buf.writeShort(this.velocityZ);
/* 109 */     this.field_149043_l.writeTo(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 116 */     handler.handleSpawnMob(this);
/*     */   }
/*     */   
/*     */   public List<DataWatcher.WatchableObject> func_149027_c() {
/* 120 */     if (this.watcher == null) {
/* 121 */       this.watcher = this.field_149043_l.getAllWatched();
/*     */     }
/*     */     
/* 124 */     return this.watcher;
/*     */   }
/*     */   
/*     */   public int getEntityID() {
/* 128 */     return this.entityId;
/*     */   }
/*     */   
/*     */   public int getEntityType() {
/* 132 */     return this.type;
/*     */   }
/*     */   
/*     */   public int getX() {
/* 136 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/* 140 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getZ() {
/* 144 */     return this.z;
/*     */   }
/*     */   
/*     */   public int getVelocityX() {
/* 148 */     return this.velocityX;
/*     */   }
/*     */   
/*     */   public int getVelocityY() {
/* 152 */     return this.velocityY;
/*     */   }
/*     */   
/*     */   public int getVelocityZ() {
/* 156 */     return this.velocityZ;
/*     */   }
/*     */   
/*     */   public byte getYaw() {
/* 160 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public byte getPitch() {
/* 164 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public byte getHeadPitch() {
/* 168 */     return this.headPitch;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S0FPacketSpawnMob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */