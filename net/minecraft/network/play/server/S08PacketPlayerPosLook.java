/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ 
/*     */ public class S08PacketPlayerPosLook
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*     */   private float yaw;
/*     */   private float pitch;
/*     */   private Set<EnumFlags> field_179835_f;
/*     */   
/*     */   public S08PacketPlayerPosLook() {}
/*     */   
/*     */   public S08PacketPlayerPosLook(double xIn, double yIn, double zIn, float yawIn, float pitchIn, Set<EnumFlags> p_i45993_9_) {
/*  23 */     this.x = xIn;
/*  24 */     this.y = yIn;
/*  25 */     this.z = zIn;
/*  26 */     this.yaw = yawIn;
/*  27 */     this.pitch = pitchIn;
/*  28 */     this.field_179835_f = p_i45993_9_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  35 */     this.x = buf.readDouble();
/*  36 */     this.y = buf.readDouble();
/*  37 */     this.z = buf.readDouble();
/*  38 */     this.yaw = buf.readFloat();
/*  39 */     this.pitch = buf.readFloat();
/*  40 */     this.field_179835_f = EnumFlags.func_180053_a(buf.readUnsignedByte());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  47 */     buf.writeDouble(this.x);
/*  48 */     buf.writeDouble(this.y);
/*  49 */     buf.writeDouble(this.z);
/*  50 */     buf.writeFloat(this.yaw);
/*  51 */     buf.writeFloat(this.pitch);
/*  52 */     buf.writeByte(EnumFlags.func_180056_a(this.field_179835_f));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  59 */     handler.handlePlayerPosLook(this);
/*     */   }
/*     */   
/*     */   public double getX() {
/*  63 */     return this.x;
/*     */   }
/*     */   
/*     */   public double getY() {
/*  67 */     return this.y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/*  71 */     return this.z;
/*     */   }
/*     */   
/*     */   public float getYaw() {
/*  75 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/*  79 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public Set<EnumFlags> func_179834_f() {
/*  83 */     return this.field_179835_f;
/*     */   }
/*     */   
/*     */   public enum EnumFlags {
/*  87 */     X(0),
/*  88 */     Y(1),
/*  89 */     Z(2),
/*  90 */     Y_ROT(3),
/*  91 */     X_ROT(4);
/*     */     
/*     */     private int field_180058_f;
/*     */     
/*     */     EnumFlags(int p_i45992_3_) {
/*  96 */       this.field_180058_f = p_i45992_3_;
/*     */     }
/*     */     
/*     */     private int func_180055_a() {
/* 100 */       return 1 << this.field_180058_f;
/*     */     }
/*     */     
/*     */     private boolean func_180054_b(int p_180054_1_) {
/* 104 */       return ((p_180054_1_ & func_180055_a()) == func_180055_a());
/*     */     }
/*     */     
/*     */     public static Set<EnumFlags> func_180053_a(int p_180053_0_) {
/* 108 */       Set<EnumFlags> set = EnumSet.noneOf(EnumFlags.class); byte b; int i;
/*     */       EnumFlags[] arrayOfEnumFlags;
/* 110 */       for (i = (arrayOfEnumFlags = values()).length, b = 0; b < i; ) { EnumFlags s08packetplayerposlook$enumflags = arrayOfEnumFlags[b];
/* 111 */         if (s08packetplayerposlook$enumflags.func_180054_b(p_180053_0_)) {
/* 112 */           set.add(s08packetplayerposlook$enumflags);
/*     */         }
/*     */         b++; }
/*     */       
/* 116 */       return set;
/*     */     }
/*     */     
/*     */     public static int func_180056_a(Set<EnumFlags> p_180056_0_) {
/* 120 */       int i = 0;
/*     */       
/* 122 */       for (EnumFlags s08packetplayerposlook$enumflags : p_180056_0_) {
/* 123 */         i |= s08packetplayerposlook$enumflags.func_180055_a();
/*     */       }
/*     */       
/* 126 */       return i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S08PacketPlayerPosLook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */