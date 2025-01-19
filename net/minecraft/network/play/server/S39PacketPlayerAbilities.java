/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ public class S39PacketPlayerAbilities
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private boolean invulnerable;
/*     */   private boolean flying;
/*     */   private boolean allowFlying;
/*     */   private boolean creativeMode;
/*     */   private float flySpeed;
/*     */   private float walkSpeed;
/*     */   
/*     */   public S39PacketPlayerAbilities() {}
/*     */   
/*     */   public S39PacketPlayerAbilities(PlayerCapabilities capabilities) {
/*  22 */     setInvulnerable(capabilities.disableDamage);
/*  23 */     setFlying(capabilities.isFlying);
/*  24 */     setAllowFlying(capabilities.allowFlying);
/*  25 */     setCreativeMode(capabilities.isCreativeMode);
/*  26 */     setFlySpeed(capabilities.getFlySpeed());
/*  27 */     setWalkSpeed(capabilities.getWalkSpeed());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  34 */     byte b0 = buf.readByte();
/*  35 */     setInvulnerable(((b0 & 0x1) > 0));
/*  36 */     setFlying(((b0 & 0x2) > 0));
/*  37 */     setAllowFlying(((b0 & 0x4) > 0));
/*  38 */     setCreativeMode(((b0 & 0x8) > 0));
/*  39 */     setFlySpeed(buf.readFloat());
/*  40 */     setWalkSpeed(buf.readFloat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  47 */     byte b0 = 0;
/*     */     
/*  49 */     if (isInvulnerable()) {
/*  50 */       b0 = (byte)(b0 | 0x1);
/*     */     }
/*     */     
/*  53 */     if (isFlying()) {
/*  54 */       b0 = (byte)(b0 | 0x2);
/*     */     }
/*     */     
/*  57 */     if (isAllowFlying()) {
/*  58 */       b0 = (byte)(b0 | 0x4);
/*     */     }
/*     */     
/*  61 */     if (isCreativeMode()) {
/*  62 */       b0 = (byte)(b0 | 0x8);
/*     */     }
/*     */     
/*  65 */     buf.writeByte(b0);
/*  66 */     buf.writeFloat(this.flySpeed);
/*  67 */     buf.writeFloat(this.walkSpeed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  74 */     handler.handlePlayerAbilities(this);
/*     */   }
/*     */   
/*     */   public boolean isInvulnerable() {
/*  78 */     return this.invulnerable;
/*     */   }
/*     */   
/*     */   public void setInvulnerable(boolean isInvulnerable) {
/*  82 */     this.invulnerable = isInvulnerable;
/*     */   }
/*     */   
/*     */   public boolean isFlying() {
/*  86 */     return this.flying;
/*     */   }
/*     */   
/*     */   public void setFlying(boolean isFlying) {
/*  90 */     this.flying = isFlying;
/*     */   }
/*     */   
/*     */   public boolean isAllowFlying() {
/*  94 */     return this.allowFlying;
/*     */   }
/*     */   
/*     */   public void setAllowFlying(boolean isAllowFlying) {
/*  98 */     this.allowFlying = isAllowFlying;
/*     */   }
/*     */   
/*     */   public boolean isCreativeMode() {
/* 102 */     return this.creativeMode;
/*     */   }
/*     */   
/*     */   public void setCreativeMode(boolean isCreativeMode) {
/* 106 */     this.creativeMode = isCreativeMode;
/*     */   }
/*     */   
/*     */   public float getFlySpeed() {
/* 110 */     return this.flySpeed;
/*     */   }
/*     */   
/*     */   public void setFlySpeed(float flySpeedIn) {
/* 114 */     this.flySpeed = flySpeedIn;
/*     */   }
/*     */   
/*     */   public float getWalkSpeed() {
/* 118 */     return this.walkSpeed;
/*     */   }
/*     */   
/*     */   public void setWalkSpeed(float walkSpeedIn) {
/* 122 */     this.walkSpeed = walkSpeedIn;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S39PacketPlayerAbilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */