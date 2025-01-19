/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class S14PacketEntity
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   protected int entityId;
/*     */   protected byte posX;
/*     */   protected byte posY;
/*     */   protected byte posZ;
/*     */   protected byte yaw;
/*     */   protected byte pitch;
/*     */   protected boolean onGround;
/*     */   protected boolean field_149069_g;
/*     */   
/*     */   public S14PacketEntity() {}
/*     */   
/*     */   public S14PacketEntity(int entityIdIn) {
/*  25 */     this.entityId = entityIdIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  32 */     this.entityId = buf.readVarIntFromBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  39 */     buf.writeVarIntToBuffer(this.entityId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  46 */     handler.handleEntityMovement(this);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  50 */     return "Entity_" + super.toString();
/*     */   }
/*     */   
/*     */   public Entity getEntity(World worldIn) {
/*  54 */     return worldIn.getEntityByID(this.entityId);
/*     */   }
/*     */   
/*     */   public byte func_149062_c() {
/*  58 */     return this.posX;
/*     */   }
/*     */   
/*     */   public byte func_149061_d() {
/*  62 */     return this.posY;
/*     */   }
/*     */   
/*     */   public byte func_149064_e() {
/*  66 */     return this.posZ;
/*     */   }
/*     */   
/*     */   public byte func_149066_f() {
/*  70 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public byte func_149063_g() {
/*  74 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public boolean func_149060_h() {
/*  78 */     return this.field_149069_g;
/*     */   }
/*     */   
/*     */   public boolean getOnGround() {
/*  82 */     return this.onGround;
/*     */   }
/*     */   
/*     */   public static class S15PacketEntityRelMove
/*     */     extends S14PacketEntity {
/*     */     public S15PacketEntityRelMove() {}
/*     */     
/*     */     public S15PacketEntityRelMove(int entityIdIn, byte x, byte y, byte z, boolean onGroundIn) {
/*  90 */       super(entityIdIn);
/*  91 */       this.posX = x;
/*  92 */       this.posY = y;
/*  93 */       this.posZ = z;
/*  94 */       this.onGround = onGroundIn;
/*     */     }
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/*  98 */       super.readPacketData(buf);
/*  99 */       this.posX = buf.readByte();
/* 100 */       this.posY = buf.readByte();
/* 101 */       this.posZ = buf.readByte();
/* 102 */       this.onGround = buf.readBoolean();
/*     */     }
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 106 */       super.writePacketData(buf);
/* 107 */       buf.writeByte(this.posX);
/* 108 */       buf.writeByte(this.posY);
/* 109 */       buf.writeByte(this.posZ);
/* 110 */       buf.writeBoolean(this.onGround);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class S16PacketEntityLook extends S14PacketEntity {
/*     */     public S16PacketEntityLook() {
/* 116 */       this.field_149069_g = true;
/*     */     }
/*     */     
/*     */     public S16PacketEntityLook(int entityIdIn, byte yawIn, byte pitchIn, boolean onGroundIn) {
/* 120 */       super(entityIdIn);
/* 121 */       this.yaw = yawIn;
/* 122 */       this.pitch = pitchIn;
/* 123 */       this.field_149069_g = true;
/* 124 */       this.onGround = onGroundIn;
/*     */     }
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 128 */       super.readPacketData(buf);
/* 129 */       this.yaw = buf.readByte();
/* 130 */       this.pitch = buf.readByte();
/* 131 */       this.onGround = buf.readBoolean();
/*     */     }
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 135 */       super.writePacketData(buf);
/* 136 */       buf.writeByte(this.yaw);
/* 137 */       buf.writeByte(this.pitch);
/* 138 */       buf.writeBoolean(this.onGround);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class S17PacketEntityLookMove extends S14PacketEntity {
/*     */     public S17PacketEntityLookMove() {
/* 144 */       this.field_149069_g = true;
/*     */     }
/*     */     
/*     */     public S17PacketEntityLookMove(int p_i45973_1_, byte p_i45973_2_, byte p_i45973_3_, byte p_i45973_4_, byte p_i45973_5_, byte p_i45973_6_, boolean p_i45973_7_) {
/* 148 */       super(p_i45973_1_);
/* 149 */       this.posX = p_i45973_2_;
/* 150 */       this.posY = p_i45973_3_;
/* 151 */       this.posZ = p_i45973_4_;
/* 152 */       this.yaw = p_i45973_5_;
/* 153 */       this.pitch = p_i45973_6_;
/* 154 */       this.onGround = p_i45973_7_;
/* 155 */       this.field_149069_g = true;
/*     */     }
/*     */     
/*     */     public void readPacketData(PacketBuffer buf) throws IOException {
/* 159 */       super.readPacketData(buf);
/* 160 */       this.posX = buf.readByte();
/* 161 */       this.posY = buf.readByte();
/* 162 */       this.posZ = buf.readByte();
/* 163 */       this.yaw = buf.readByte();
/* 164 */       this.pitch = buf.readByte();
/* 165 */       this.onGround = buf.readBoolean();
/*     */     }
/*     */     
/*     */     public void writePacketData(PacketBuffer buf) throws IOException {
/* 169 */       super.writePacketData(buf);
/* 170 */       buf.writeByte(this.posX);
/* 171 */       buf.writeByte(this.posY);
/* 172 */       buf.writeByte(this.posZ);
/* 173 */       buf.writeByte(this.yaw);
/* 174 */       buf.writeByte(this.pitch);
/* 175 */       buf.writeBoolean(this.onGround);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S14PacketEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */