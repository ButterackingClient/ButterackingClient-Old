/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ public class S45PacketTitle
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private Type type;
/*     */   private IChatComponent message;
/*     */   private int fadeInTime;
/*     */   private int displayTime;
/*     */   private int fadeOutTime;
/*     */   
/*     */   public S45PacketTitle() {}
/*     */   
/*     */   public S45PacketTitle(Type type, IChatComponent message) {
/*  21 */     this(type, message, -1, -1, -1);
/*     */   }
/*     */   
/*     */   public S45PacketTitle(int fadeInTime, int displayTime, int fadeOutTime) {
/*  25 */     this(Type.TIMES, null, fadeInTime, displayTime, fadeOutTime);
/*     */   }
/*     */   
/*     */   public S45PacketTitle(Type type, IChatComponent message, int fadeInTime, int displayTime, int fadeOutTime) {
/*  29 */     this.type = type;
/*  30 */     this.message = message;
/*  31 */     this.fadeInTime = fadeInTime;
/*  32 */     this.displayTime = displayTime;
/*  33 */     this.fadeOutTime = fadeOutTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  40 */     this.type = (Type)buf.readEnumValue(Type.class);
/*     */     
/*  42 */     if (this.type == Type.TITLE || this.type == Type.SUBTITLE) {
/*  43 */       this.message = buf.readChatComponent();
/*     */     }
/*     */     
/*  46 */     if (this.type == Type.TIMES) {
/*  47 */       this.fadeInTime = buf.readInt();
/*  48 */       this.displayTime = buf.readInt();
/*  49 */       this.fadeOutTime = buf.readInt();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  57 */     buf.writeEnumValue(this.type);
/*     */     
/*  59 */     if (this.type == Type.TITLE || this.type == Type.SUBTITLE) {
/*  60 */       buf.writeChatComponent(this.message);
/*     */     }
/*     */     
/*  63 */     if (this.type == Type.TIMES) {
/*  64 */       buf.writeInt(this.fadeInTime);
/*  65 */       buf.writeInt(this.displayTime);
/*  66 */       buf.writeInt(this.fadeOutTime);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  74 */     handler.handleTitle(this);
/*     */   }
/*     */   
/*     */   public Type getType() {
/*  78 */     return this.type;
/*     */   }
/*     */   
/*     */   public IChatComponent getMessage() {
/*  82 */     return this.message;
/*     */   }
/*     */   
/*     */   public int getFadeInTime() {
/*  86 */     return this.fadeInTime;
/*     */   }
/*     */   
/*     */   public int getDisplayTime() {
/*  90 */     return this.displayTime;
/*     */   }
/*     */   
/*     */   public int getFadeOutTime() {
/*  94 */     return this.fadeOutTime;
/*     */   }
/*     */   
/*     */   public enum Type {
/*  98 */     TITLE,
/*  99 */     SUBTITLE,
/* 100 */     TIMES,
/* 101 */     CLEAR,
/* 102 */     RESET; public static Type byName(String name) { byte b;
/*     */       int i;
/*     */       Type[] arrayOfType;
/* 105 */       for (i = (arrayOfType = values()).length, b = 0; b < i; ) { Type s45packettitle$type = arrayOfType[b];
/* 106 */         if (s45packettitle$type.name().equalsIgnoreCase(name)) {
/* 107 */           return s45packettitle$type;
/*     */         }
/*     */         b++; }
/*     */       
/* 111 */       return TITLE; }
/*     */ 
/*     */     
/*     */     public static String[] getNames() {
/* 115 */       String[] astring = new String[(values()).length];
/* 116 */       int i = 0; byte b; int j;
/*     */       Type[] arrayOfType;
/* 118 */       for (j = (arrayOfType = values()).length, b = 0; b < j; ) { Type s45packettitle$type = arrayOfType[b];
/* 119 */         astring[i++] = s45packettitle$type.name().toLowerCase();
/*     */         b++; }
/*     */       
/* 122 */       return astring;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S45PacketTitle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */