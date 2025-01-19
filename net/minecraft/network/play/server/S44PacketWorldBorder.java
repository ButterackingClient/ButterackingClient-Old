/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ 
/*     */ public class S44PacketWorldBorder
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private Action action;
/*     */   private int size;
/*     */   private double centerX;
/*     */   private double centerZ;
/*     */   private double targetSize;
/*     */   private double diameter;
/*     */   private long timeUntilTarget;
/*     */   private int warningTime;
/*     */   private int warningDistance;
/*     */   
/*     */   public S44PacketWorldBorder() {}
/*     */   
/*     */   public S44PacketWorldBorder(WorldBorder border, Action actionIn) {
/*  25 */     this.action = actionIn;
/*  26 */     this.centerX = border.getCenterX();
/*  27 */     this.centerZ = border.getCenterZ();
/*  28 */     this.diameter = border.getDiameter();
/*  29 */     this.targetSize = border.getTargetSize();
/*  30 */     this.timeUntilTarget = border.getTimeUntilTarget();
/*  31 */     this.size = border.getSize();
/*  32 */     this.warningDistance = border.getWarningDistance();
/*  33 */     this.warningTime = border.getWarningTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  40 */     this.action = (Action)buf.readEnumValue(Action.class);
/*     */     
/*  42 */     switch (this.action) {
/*     */       case SET_SIZE:
/*  44 */         this.targetSize = buf.readDouble();
/*     */         break;
/*     */       
/*     */       case LERP_SIZE:
/*  48 */         this.diameter = buf.readDouble();
/*  49 */         this.targetSize = buf.readDouble();
/*  50 */         this.timeUntilTarget = buf.readVarLong();
/*     */         break;
/*     */       
/*     */       case SET_CENTER:
/*  54 */         this.centerX = buf.readDouble();
/*  55 */         this.centerZ = buf.readDouble();
/*     */         break;
/*     */       
/*     */       case SET_WARNING_BLOCKS:
/*  59 */         this.warningDistance = buf.readVarIntFromBuffer();
/*     */         break;
/*     */       
/*     */       case SET_WARNING_TIME:
/*  63 */         this.warningTime = buf.readVarIntFromBuffer();
/*     */         break;
/*     */       
/*     */       case null:
/*  67 */         this.centerX = buf.readDouble();
/*  68 */         this.centerZ = buf.readDouble();
/*  69 */         this.diameter = buf.readDouble();
/*  70 */         this.targetSize = buf.readDouble();
/*  71 */         this.timeUntilTarget = buf.readVarLong();
/*  72 */         this.size = buf.readVarIntFromBuffer();
/*  73 */         this.warningDistance = buf.readVarIntFromBuffer();
/*  74 */         this.warningTime = buf.readVarIntFromBuffer();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  82 */     buf.writeEnumValue(this.action);
/*     */     
/*  84 */     switch (this.action) {
/*     */       case SET_SIZE:
/*  86 */         buf.writeDouble(this.targetSize);
/*     */         break;
/*     */       
/*     */       case LERP_SIZE:
/*  90 */         buf.writeDouble(this.diameter);
/*  91 */         buf.writeDouble(this.targetSize);
/*  92 */         buf.writeVarLong(this.timeUntilTarget);
/*     */         break;
/*     */       
/*     */       case SET_CENTER:
/*  96 */         buf.writeDouble(this.centerX);
/*  97 */         buf.writeDouble(this.centerZ);
/*     */         break;
/*     */       
/*     */       case SET_WARNING_BLOCKS:
/* 101 */         buf.writeVarIntToBuffer(this.warningDistance);
/*     */         break;
/*     */       
/*     */       case SET_WARNING_TIME:
/* 105 */         buf.writeVarIntToBuffer(this.warningTime);
/*     */         break;
/*     */       
/*     */       case null:
/* 109 */         buf.writeDouble(this.centerX);
/* 110 */         buf.writeDouble(this.centerZ);
/* 111 */         buf.writeDouble(this.diameter);
/* 112 */         buf.writeDouble(this.targetSize);
/* 113 */         buf.writeVarLong(this.timeUntilTarget);
/* 114 */         buf.writeVarIntToBuffer(this.size);
/* 115 */         buf.writeVarIntToBuffer(this.warningDistance);
/* 116 */         buf.writeVarIntToBuffer(this.warningTime);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 124 */     handler.handleWorldBorder(this);
/*     */   }
/*     */   
/*     */   public void func_179788_a(WorldBorder border) {
/* 128 */     switch (this.action) {
/*     */       case SET_SIZE:
/* 130 */         border.setTransition(this.targetSize);
/*     */         break;
/*     */       
/*     */       case LERP_SIZE:
/* 134 */         border.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
/*     */         break;
/*     */       
/*     */       case SET_CENTER:
/* 138 */         border.setCenter(this.centerX, this.centerZ);
/*     */         break;
/*     */       
/*     */       case SET_WARNING_BLOCKS:
/* 142 */         border.setWarningDistance(this.warningDistance);
/*     */         break;
/*     */       
/*     */       case SET_WARNING_TIME:
/* 146 */         border.setWarningTime(this.warningTime);
/*     */         break;
/*     */       
/*     */       case null:
/* 150 */         border.setCenter(this.centerX, this.centerZ);
/*     */         
/* 152 */         if (this.timeUntilTarget > 0L) {
/* 153 */           border.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
/*     */         } else {
/* 155 */           border.setTransition(this.targetSize);
/*     */         } 
/*     */         
/* 158 */         border.setSize(this.size);
/* 159 */         border.setWarningDistance(this.warningDistance);
/* 160 */         border.setWarningTime(this.warningTime);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/* 165 */   public enum Action { SET_SIZE,
/* 166 */     LERP_SIZE,
/* 167 */     SET_CENTER,
/* 168 */     INITIALIZE,
/* 169 */     SET_WARNING_TIME,
/* 170 */     SET_WARNING_BLOCKS; }
/*     */ 
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S44PacketWorldBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */