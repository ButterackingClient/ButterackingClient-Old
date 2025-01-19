/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ 
/*     */ public class S21PacketChunkData
/*     */   implements Packet<INetHandlerPlayClient>
/*     */ {
/*     */   private int chunkX;
/*     */   private int chunkZ;
/*     */   private Extracted extractedData;
/*     */   private boolean field_149279_g;
/*     */   
/*     */   public S21PacketChunkData() {}
/*     */   
/*     */   public S21PacketChunkData(Chunk chunkIn, boolean p_i45196_2_, int p_i45196_3_) {
/*  24 */     this.chunkX = chunkIn.xPosition;
/*  25 */     this.chunkZ = chunkIn.zPosition;
/*  26 */     this.field_149279_g = p_i45196_2_;
/*  27 */     this.extractedData = getExtractedData(chunkIn, p_i45196_2_, !(chunkIn.getWorld()).provider.getHasNoSky(), p_i45196_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  34 */     this.chunkX = buf.readInt();
/*  35 */     this.chunkZ = buf.readInt();
/*  36 */     this.field_149279_g = buf.readBoolean();
/*  37 */     this.extractedData = new Extracted();
/*  38 */     this.extractedData.dataSize = buf.readShort();
/*  39 */     this.extractedData.data = buf.readByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/*  46 */     buf.writeInt(this.chunkX);
/*  47 */     buf.writeInt(this.chunkZ);
/*  48 */     buf.writeBoolean(this.field_149279_g);
/*  49 */     buf.writeShort((short)(this.extractedData.dataSize & 0xFFFF));
/*  50 */     buf.writeByteArray(this.extractedData.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/*  57 */     handler.handleChunkData(this);
/*     */   }
/*     */   
/*     */   public byte[] getExtractedDataBytes() {
/*  61 */     return this.extractedData.data;
/*     */   }
/*     */   
/*     */   protected static int func_180737_a(int p_180737_0_, boolean p_180737_1_, boolean p_180737_2_) {
/*  65 */     int i = p_180737_0_ * 2 * 16 * 16 * 16;
/*  66 */     int j = p_180737_0_ * 16 * 16 * 16 / 2;
/*  67 */     int k = p_180737_1_ ? (p_180737_0_ * 16 * 16 * 16 / 2) : 0;
/*  68 */     int l = p_180737_2_ ? 256 : 0;
/*  69 */     return i + j + k + l;
/*     */   }
/*     */   
/*     */   public static Extracted getExtractedData(Chunk p_179756_0_, boolean p_179756_1_, boolean p_179756_2_, int p_179756_3_) {
/*  73 */     ExtendedBlockStorage[] aextendedblockstorage = p_179756_0_.getBlockStorageArray();
/*  74 */     Extracted s21packetchunkdata$extracted = new Extracted();
/*  75 */     List<ExtendedBlockStorage> list = Lists.newArrayList();
/*     */     
/*  77 */     for (int i = 0; i < aextendedblockstorage.length; i++) {
/*  78 */       ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[i];
/*     */       
/*  80 */       if (extendedblockstorage != null && (!p_179756_1_ || !extendedblockstorage.isEmpty()) && (p_179756_3_ & 1 << i) != 0) {
/*  81 */         s21packetchunkdata$extracted.dataSize |= 1 << i;
/*  82 */         list.add(extendedblockstorage);
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     s21packetchunkdata$extracted.data = new byte[func_180737_a(Integer.bitCount(s21packetchunkdata$extracted.dataSize), p_179756_2_, p_179756_1_)];
/*  87 */     int j = 0;
/*     */     
/*  89 */     for (ExtendedBlockStorage extendedblockstorage1 : list) {
/*  90 */       char[] achar = extendedblockstorage1.getData(); byte b; int k;
/*     */       char[] arrayOfChar1;
/*  92 */       for (k = (arrayOfChar1 = achar).length, b = 0; b < k; ) { char c0 = arrayOfChar1[b];
/*  93 */         s21packetchunkdata$extracted.data[j++] = (byte)(c0 & 0xFF);
/*  94 */         s21packetchunkdata$extracted.data[j++] = (byte)(c0 >> 8 & 0xFF);
/*     */         b++; }
/*     */     
/*     */     } 
/*  98 */     for (ExtendedBlockStorage extendedblockstorage2 : list) {
/*  99 */       j = func_179757_a(extendedblockstorage2.getBlocklightArray().getData(), s21packetchunkdata$extracted.data, j);
/*     */     }
/*     */     
/* 102 */     if (p_179756_2_) {
/* 103 */       for (ExtendedBlockStorage extendedblockstorage3 : list) {
/* 104 */         j = func_179757_a(extendedblockstorage3.getSkylightArray().getData(), s21packetchunkdata$extracted.data, j);
/*     */       }
/*     */     }
/*     */     
/* 108 */     if (p_179756_1_) {
/* 109 */       func_179757_a(p_179756_0_.getBiomeArray(), s21packetchunkdata$extracted.data, j);
/*     */     }
/*     */     
/* 112 */     return s21packetchunkdata$extracted;
/*     */   }
/*     */   
/*     */   private static int func_179757_a(byte[] p_179757_0_, byte[] p_179757_1_, int p_179757_2_) {
/* 116 */     System.arraycopy(p_179757_0_, 0, p_179757_1_, p_179757_2_, p_179757_0_.length);
/* 117 */     return p_179757_2_ + p_179757_0_.length;
/*     */   }
/*     */   
/*     */   public int getChunkX() {
/* 121 */     return this.chunkX;
/*     */   }
/*     */   
/*     */   public int getChunkZ() {
/* 125 */     return this.chunkZ;
/*     */   }
/*     */   
/*     */   public int getExtractedSize() {
/* 129 */     return this.extractedData.dataSize;
/*     */   }
/*     */   
/*     */   public boolean func_149274_i() {
/* 133 */     return this.field_149279_g;
/*     */   }
/*     */   
/*     */   public static class Extracted {
/*     */     public byte[] data;
/*     */     public int dataSize;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\play\server\S21PacketChunkData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */