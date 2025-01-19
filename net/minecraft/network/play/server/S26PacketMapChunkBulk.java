/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public class S26PacketMapChunkBulk
/*     */   implements Packet<INetHandlerPlayClient> {
/*     */   private int[] xPositions;
/*     */   private int[] zPositions;
/*     */   private S21PacketChunkData.Extracted[] chunksData;
/*     */   private boolean isOverworld;
/*     */   
/*     */   public S26PacketMapChunkBulk() {}
/*     */   
/*     */   public S26PacketMapChunkBulk(List<Chunk> chunks) {
/*  21 */     int i = chunks.size();
/*  22 */     this.xPositions = new int[i];
/*  23 */     this.zPositions = new int[i];
/*  24 */     this.chunksData = new S21PacketChunkData.Extracted[i];
/*  25 */     this.isOverworld = !(((Chunk)chunks.get(0)).getWorld()).provider.getHasNoSky();
/*     */     
/*  27 */     for (int j = 0; j < i; j++) {
/*  28 */       Chunk chunk = chunks.get(j);
/*  29 */       S21PacketChunkData.Extracted s21packetchunkdata$extracted = S21PacketChunkData.getExtractedData(chunk, true, this.isOverworld, 65535);
/*  30 */       this.xPositions[j] = chunk.xPosition;
/*  31 */       this.zPositions[j] = chunk.zPosition;
/*  32 */       this.chunksData[j] = s21packetchunkdata$extracted;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  40 */     this.isOverworld = buf.readBoolean();
/*  41 */     int i = buf.readVarIntFromBuffer();
/*  42 */     this.xPositions = new int[i];
/*  43 */     this.zPositions = new int[i];
/*  44 */     this.chunksData = new S21PacketChunkData.Extracted[i];
/*     */     
/*  46 */     for (int j = 0; j < i; j++) {
/*  47 */       this.xPositions[j] = buf.readInt();
/*  48 */       this.zPositions[j] = buf.readInt();
/*  49 */       this.chunksData[j] = new S21PacketChunkData.Extracted();
/*  50 */       (this.chunksData[j]).dataSize = buf.readShort() & 0xFFFF;
/*  51 */       (this.chunksData[j]).data = new byte[S21PacketChunkData.func_180737_a(Integer.bitCount((this.chunksData[j]).dataSize), this.isOverworld, true)];
/*     */     } 
/*     */     
/*  54 */     for (int k = 0; k < i; k++) {
/*  55 */       buf.readBytes((this.chunksData[k]).data);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  63 */     buf.writeBoolean(this.isOverworld);
/*  64 */     buf.writeVarIntToBuffer(this.chunksData.length);
/*     */     
/*  66 */     for (int i = 0; i < this.xPositions.length; i++) {
/*  67 */       buf.writeInt(this.xPositions[i]);
/*  68 */       buf.writeInt(this.zPositions[i]);
/*  69 */       buf.writeShort((short)((this.chunksData[i]).dataSize & 0xFFFF));
/*     */     } 
/*     */     
/*  72 */     for (int j = 0; j < this.xPositions.length; j++) {
/*  73 */       buf.writeBytes((this.chunksData[j]).data);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  81 */     handler.handleMapChunkBulk(this);
/*     */   }
/*     */   
/*     */   public int getChunkX(int p_149255_1_) {
/*  85 */     return this.xPositions[p_149255_1_];
/*     */   }
/*     */   
/*     */   public int getChunkZ(int p_149253_1_) {
/*  89 */     return this.zPositions[p_149253_1_];
/*     */   }
/*     */   
/*     */   public int getChunkCount() {
/*  93 */     return this.xPositions.length;
/*     */   }
/*     */   
/*     */   public byte[] getChunkBytes(int p_149256_1_) {
/*  97 */     return (this.chunksData[p_149256_1_]).data;
/*     */   }
/*     */   
/*     */   public int getChunkSize(int p_179754_1_) {
/* 101 */     return (this.chunksData[p_179754_1_]).dataSize;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S26PacketMapChunkBulk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */