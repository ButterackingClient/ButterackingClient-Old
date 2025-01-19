/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.util.Vec4b;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ public class S34PacketMaps
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private int mapId;
/*     */   private byte mapScale;
/*     */   private Vec4b[] mapVisiblePlayersVec4b;
/*     */   private int mapMinX;
/*     */   private int mapMinY;
/*     */   private int mapMaxX;
/*     */   private int mapMaxY;
/*     */   private byte[] mapDataBytes;
/*     */   
/*     */   public S34PacketMaps() {}
/*     */   
/*     */   public S34PacketMaps(int mapIdIn, byte scale, Collection<Vec4b> visiblePlayers, byte[] colors, int minX, int minY, int maxX, int maxY) {
/*  26 */     this.mapId = mapIdIn;
/*  27 */     this.mapScale = scale;
/*  28 */     this.mapVisiblePlayersVec4b = visiblePlayers.<Vec4b>toArray(new Vec4b[visiblePlayers.size()]);
/*  29 */     this.mapMinX = minX;
/*  30 */     this.mapMinY = minY;
/*  31 */     this.mapMaxX = maxX;
/*  32 */     this.mapMaxY = maxY;
/*  33 */     this.mapDataBytes = new byte[maxX * maxY];
/*     */     
/*  35 */     for (int i = 0; i < maxX; i++) {
/*  36 */       for (int j = 0; j < maxY; j++) {
/*  37 */         this.mapDataBytes[i + j * maxX] = colors[minX + i + (minY + j) * 128];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  46 */     this.mapId = buf.readVarIntFromBuffer();
/*  47 */     this.mapScale = buf.readByte();
/*  48 */     this.mapVisiblePlayersVec4b = new Vec4b[buf.readVarIntFromBuffer()];
/*     */     
/*  50 */     for (int i = 0; i < this.mapVisiblePlayersVec4b.length; i++) {
/*  51 */       short short1 = (short)buf.readByte();
/*  52 */       this.mapVisiblePlayersVec4b[i] = new Vec4b((byte)(short1 >> 4 & 0xF), buf.readByte(), buf.readByte(), (byte)(short1 & 0xF));
/*     */     } 
/*     */     
/*  55 */     this.mapMaxX = buf.readUnsignedByte();
/*     */     
/*  57 */     if (this.mapMaxX > 0) {
/*  58 */       this.mapMaxY = buf.readUnsignedByte();
/*  59 */       this.mapMinX = buf.readUnsignedByte();
/*  60 */       this.mapMinY = buf.readUnsignedByte();
/*  61 */       this.mapDataBytes = buf.readByteArray();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  69 */     buf.writeVarIntToBuffer(this.mapId);
/*  70 */     buf.writeByte(this.mapScale);
/*  71 */     buf.writeVarIntToBuffer(this.mapVisiblePlayersVec4b.length); byte b; int i;
/*     */     Vec4b[] arrayOfVec4b;
/*  73 */     for (i = (arrayOfVec4b = this.mapVisiblePlayersVec4b).length, b = 0; b < i; ) { Vec4b vec4b = arrayOfVec4b[b];
/*  74 */       buf.writeByte((vec4b.func_176110_a() & 0xF) << 4 | vec4b.func_176111_d() & 0xF);
/*  75 */       buf.writeByte(vec4b.func_176112_b());
/*  76 */       buf.writeByte(vec4b.func_176113_c());
/*     */       b++; }
/*     */     
/*  79 */     buf.writeByte(this.mapMaxX);
/*     */     
/*  81 */     if (this.mapMaxX > 0) {
/*  82 */       buf.writeByte(this.mapMaxY);
/*  83 */       buf.writeByte(this.mapMinX);
/*  84 */       buf.writeByte(this.mapMinY);
/*  85 */       buf.writeByteArray(this.mapDataBytes);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  93 */     handler.handleMaps(this);
/*     */   }
/*     */   
/*     */   public int getMapId() {
/*  97 */     return this.mapId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMapdataTo(MapData mapdataIn) {
/* 104 */     mapdataIn.scale = this.mapScale;
/* 105 */     mapdataIn.mapDecorations.clear();
/*     */     
/* 107 */     for (int i = 0; i < this.mapVisiblePlayersVec4b.length; i++) {
/* 108 */       Vec4b vec4b = this.mapVisiblePlayersVec4b[i];
/* 109 */       mapdataIn.mapDecorations.put("icon-" + i, vec4b);
/*     */     } 
/*     */     
/* 112 */     for (int j = 0; j < this.mapMaxX; j++) {
/* 113 */       for (int k = 0; k < this.mapMaxY; k++)
/* 114 */         mapdataIn.colors[this.mapMinX + j + (this.mapMinY + k) * 128] = this.mapDataBytes[j + k * this.mapMaxX]; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S34PacketMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */