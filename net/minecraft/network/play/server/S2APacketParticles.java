/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S2APacketParticles
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private EnumParticleTypes particleType;
/*     */   private float xCoord;
/*     */   private float yCoord;
/*     */   private float zCoord;
/*     */   private float xOffset;
/*     */   private float yOffset;
/*     */   private float zOffset;
/*     */   private float particleSpeed;
/*     */   private int particleCount;
/*     */   private boolean longDistance;
/*     */   private int[] particleArguments;
/*     */   
/*     */   public S2APacketParticles() {}
/*     */   
/*     */   public S2APacketParticles(EnumParticleTypes particleTypeIn, boolean longDistanceIn, float x, float y, float z, float xOffsetIn, float yOffset, float zOffset, float particleSpeedIn, int particleCountIn, int... particleArgumentsIn) {
/*  31 */     this.particleType = particleTypeIn;
/*  32 */     this.longDistance = longDistanceIn;
/*  33 */     this.xCoord = x;
/*  34 */     this.yCoord = y;
/*  35 */     this.zCoord = z;
/*  36 */     this.xOffset = xOffsetIn;
/*  37 */     this.yOffset = yOffset;
/*  38 */     this.zOffset = zOffset;
/*  39 */     this.particleSpeed = particleSpeedIn;
/*  40 */     this.particleCount = particleCountIn;
/*  41 */     this.particleArguments = particleArgumentsIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  48 */     this.particleType = EnumParticleTypes.getParticleFromId(buf.readInt());
/*     */     
/*  50 */     if (this.particleType == null) {
/*  51 */       this.particleType = EnumParticleTypes.BARRIER;
/*     */     }
/*     */     
/*  54 */     this.longDistance = buf.readBoolean();
/*  55 */     this.xCoord = buf.readFloat();
/*  56 */     this.yCoord = buf.readFloat();
/*  57 */     this.zCoord = buf.readFloat();
/*  58 */     this.xOffset = buf.readFloat();
/*  59 */     this.yOffset = buf.readFloat();
/*  60 */     this.zOffset = buf.readFloat();
/*  61 */     this.particleSpeed = buf.readFloat();
/*  62 */     this.particleCount = buf.readInt();
/*  63 */     int i = this.particleType.getArgumentCount();
/*  64 */     this.particleArguments = new int[i];
/*     */     
/*  66 */     for (int j = 0; j < i; j++) {
/*  67 */       this.particleArguments[j] = buf.readVarIntFromBuffer();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  75 */     buf.writeInt(this.particleType.getParticleID());
/*  76 */     buf.writeBoolean(this.longDistance);
/*  77 */     buf.writeFloat(this.xCoord);
/*  78 */     buf.writeFloat(this.yCoord);
/*  79 */     buf.writeFloat(this.zCoord);
/*  80 */     buf.writeFloat(this.xOffset);
/*  81 */     buf.writeFloat(this.yOffset);
/*  82 */     buf.writeFloat(this.zOffset);
/*  83 */     buf.writeFloat(this.particleSpeed);
/*  84 */     buf.writeInt(this.particleCount);
/*  85 */     int i = this.particleType.getArgumentCount();
/*     */     
/*  87 */     for (int j = 0; j < i; j++) {
/*  88 */       buf.writeVarIntToBuffer(this.particleArguments[j]);
/*     */     }
/*     */   }
/*     */   
/*     */   public EnumParticleTypes getParticleType() {
/*  93 */     return this.particleType;
/*     */   }
/*     */   
/*     */   public boolean isLongDistance() {
/*  97 */     return this.longDistance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXCoordinate() {
/* 104 */     return this.xCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYCoordinate() {
/* 111 */     return this.yCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZCoordinate() {
/* 118 */     return this.zCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getXOffset() {
/* 125 */     return this.xOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getYOffset() {
/* 132 */     return this.yOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getZOffset() {
/* 139 */     return this.zOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getParticleSpeed() {
/* 146 */     return this.particleSpeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParticleCount() {
/* 153 */     return this.particleCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getParticleArgs() {
/* 161 */     return this.particleArguments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 168 */     handler.handleParticles(this);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S2APacketParticles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */