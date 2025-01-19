/*     */ package net.minecraft.network.play.client;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*     */ 
/*     */ public class C03PacketPlayer
/*     */   implements Packet<INetHandlerPlayServer> {
/*     */   protected double x;
/*     */   protected double y;
/*     */   protected double z;
/*     */   protected float yaw;
/*     */   protected float pitch;
/*     */   protected boolean onGround;
/*     */   protected boolean moving;
/*     */   protected boolean rotating;
/*     */   
/*     */   public C03PacketPlayer() {}
/*     */   
/*     */   public C03PacketPlayer(boolean isOnGround) {
/*  23 */     this.onGround = isOnGround;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayServer handler) {
/*  30 */     handler.processPlayer(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  37 */     this.onGround = (buf.readUnsignedByte() != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  44 */     buf.writeByte(this.onGround ? 1 : 0);
/*     */   }
/*     */   
/*     */   public double getPositionX() {
/*  48 */     return this.x;
/*     */   }
/*     */   
/*     */   public double getPositionY() {
/*  52 */     return this.y;
/*     */   }
/*     */   
/*     */   public double getPositionZ() {
/*  56 */     return this.z;
/*     */   }
/*     */   
/*     */   public float getYaw() {
/*  60 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/*  64 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public boolean isOnGround() {
/*  68 */     return this.onGround;
/*     */   }
/*     */   
/*     */   public boolean isMoving() {
/*  72 */     return this.moving;
/*     */   }
/*     */   
/*     */   public boolean getRotating() {
/*  76 */     return this.rotating;
/*     */   }
/*     */   
/*     */   public void setMoving(boolean isMoving) {
/*  80 */     this.moving = isMoving;
/*     */   }
/*     */   
/*     */   public static class C04PacketPlayerPosition extends C03PacketPlayer {
/*     */     public C04PacketPlayerPosition() {
/*  85 */       this.moving = true;
/*     */     }
/*     */     
/*     */     public C04PacketPlayerPosition(double posX, double posY, double posZ, boolean isOnGround) {
/*  89 */       this.x = posX;
/*  90 */       this.y = posY;
/*  91 */       this.z = posZ;
/*  92 */       this.onGround = isOnGround;
/*  93 */       this.moving = true;
/*     */     }
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/*  97 */       this.x = buf.readDouble();
/*  98 */       this.y = buf.readDouble();
/*  99 */       this.z = buf.readDouble();
/* 100 */       super.readPacketData(buf);
/*     */     }
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 104 */       buf.writeDouble(this.x);
/* 105 */       buf.writeDouble(this.y);
/* 106 */       buf.writeDouble(this.z);
/* 107 */       super.writePacketData(buf);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class C05PacketPlayerLook extends C03PacketPlayer {
/*     */     public C05PacketPlayerLook() {
/* 113 */       this.rotating = true;
/*     */     }
/*     */     
/*     */     public C05PacketPlayerLook(float playerYaw, float playerPitch, boolean isOnGround) {
/* 117 */       this.yaw = playerYaw;
/* 118 */       this.pitch = playerPitch;
/* 119 */       this.onGround = isOnGround;
/* 120 */       this.rotating = true;
/*     */     }
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 124 */       this.yaw = buf.readFloat();
/* 125 */       this.pitch = buf.readFloat();
/* 126 */       super.readPacketData(buf);
/*     */     }
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 130 */       buf.writeFloat(this.yaw);
/* 131 */       buf.writeFloat(this.pitch);
/* 132 */       super.writePacketData(buf);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class C06PacketPlayerPosLook extends C03PacketPlayer {
/*     */     public C06PacketPlayerPosLook() {
/* 138 */       this.moving = true;
/* 139 */       this.rotating = true;
/*     */     }
/*     */     
/*     */     public C06PacketPlayerPosLook(double playerX, double playerY, double playerZ, float playerYaw, float playerPitch, boolean playerIsOnGround) {
/* 143 */       this.x = playerX;
/* 144 */       this.y = playerY;
/* 145 */       this.z = playerZ;
/* 146 */       this.yaw = playerYaw;
/* 147 */       this.pitch = playerPitch;
/* 148 */       this.onGround = playerIsOnGround;
/* 149 */       this.rotating = true;
/* 150 */       this.moving = true;
/*     */     }
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 154 */       this.x = buf.readDouble();
/* 155 */       this.y = buf.readDouble();
/* 156 */       this.z = buf.readDouble();
/* 157 */       this.yaw = buf.readFloat();
/* 158 */       this.pitch = buf.readFloat();
/* 159 */       super.readPacketData(buf);
/*     */     }
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 163 */       buf.writeDouble(this.x);
/* 164 */       buf.writeDouble(this.y);
/* 165 */       buf.writeDouble(this.z);
/* 166 */       buf.writeFloat(this.yaw);
/* 167 */       buf.writeFloat(this.pitch);
/* 168 */       super.writePacketData(buf);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\client\C03PacketPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */