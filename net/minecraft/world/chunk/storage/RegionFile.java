/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.List;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ 
/*     */ public class RegionFile
/*     */ {
/*  21 */   private static final byte[] emptySector = new byte[4096];
/*     */   private final File fileName;
/*     */   private RandomAccessFile dataFile;
/*  24 */   private final int[] offsets = new int[1024];
/*  25 */   private final int[] chunkTimestamps = new int[1024];
/*     */   
/*     */   private List<Boolean> sectorFree;
/*     */   
/*     */   private int sizeDelta;
/*     */   
/*     */   private long lastModified;
/*     */ 
/*     */   
/*     */   public RegionFile(File fileNameIn) {
/*  35 */     this.fileName = fileNameIn;
/*  36 */     this.sizeDelta = 0;
/*     */     
/*     */     try {
/*  39 */       if (fileNameIn.exists()) {
/*  40 */         this.lastModified = fileNameIn.lastModified();
/*     */       }
/*     */       
/*  43 */       this.dataFile = new RandomAccessFile(fileNameIn, "rw");
/*     */       
/*  45 */       if (this.dataFile.length() < 4096L) {
/*  46 */         for (int i = 0; i < 1024; i++) {
/*  47 */           this.dataFile.writeInt(0);
/*     */         }
/*     */         
/*  50 */         for (int i1 = 0; i1 < 1024; i1++) {
/*  51 */           this.dataFile.writeInt(0);
/*     */         }
/*     */         
/*  54 */         this.sizeDelta += 8192;
/*     */       } 
/*     */       
/*  57 */       if ((this.dataFile.length() & 0xFFFL) != 0L) {
/*  58 */         for (int j1 = 0; j1 < (this.dataFile.length() & 0xFFFL); j1++) {
/*  59 */           this.dataFile.write(0);
/*     */         }
/*     */       }
/*     */       
/*  63 */       int k1 = (int)this.dataFile.length() / 4096;
/*  64 */       this.sectorFree = Lists.newArrayListWithCapacity(k1);
/*     */       
/*  66 */       for (int j = 0; j < k1; j++) {
/*  67 */         this.sectorFree.add(Boolean.valueOf(true));
/*     */       }
/*     */       
/*  70 */       this.sectorFree.set(0, Boolean.valueOf(false));
/*  71 */       this.sectorFree.set(1, Boolean.valueOf(false));
/*  72 */       this.dataFile.seek(0L);
/*     */       
/*  74 */       for (int l1 = 0; l1 < 1024; l1++) {
/*  75 */         int k = this.dataFile.readInt();
/*  76 */         this.offsets[l1] = k;
/*     */         
/*  78 */         if (k != 0 && (k >> 8) + (k & 0xFF) <= this.sectorFree.size()) {
/*  79 */           for (int l = 0; l < (k & 0xFF); l++) {
/*  80 */             this.sectorFree.set((k >> 8) + l, Boolean.valueOf(false));
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/*  85 */       for (int i2 = 0; i2 < 1024; i2++) {
/*  86 */         int j2 = this.dataFile.readInt();
/*  87 */         this.chunkTimestamps[i2] = j2;
/*     */       } 
/*  89 */     } catch (IOException ioexception) {
/*  90 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized DataInputStream getChunkDataInputStream(int x, int z) {
/*  98 */     if (outOfBounds(x, z)) {
/*  99 */       return null;
/*     */     }
/*     */     try {
/* 102 */       int i = getOffset(x, z);
/*     */       
/* 104 */       if (i == 0) {
/* 105 */         return null;
/*     */       }
/* 107 */       int j = i >> 8;
/* 108 */       int k = i & 0xFF;
/*     */       
/* 110 */       if (j + k > this.sectorFree.size()) {
/* 111 */         return null;
/*     */       }
/* 113 */       this.dataFile.seek((j * 4096));
/* 114 */       int l = this.dataFile.readInt();
/*     */       
/* 116 */       if (l > 4096 * k)
/* 117 */         return null; 
/* 118 */       if (l <= 0) {
/* 119 */         return null;
/*     */       }
/* 121 */       byte b0 = this.dataFile.readByte();
/*     */       
/* 123 */       if (b0 == 1) {
/* 124 */         byte[] abyte1 = new byte[l - 1];
/* 125 */         this.dataFile.read(abyte1);
/* 126 */         return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte1))));
/* 127 */       }  if (b0 == 2) {
/* 128 */         byte[] abyte = new byte[l - 1];
/* 129 */         this.dataFile.read(abyte);
/* 130 */         return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte))));
/*     */       } 
/* 132 */       return null;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 137 */     catch (IOException var9) {
/* 138 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataOutputStream getChunkDataOutputStream(int x, int z) {
/* 147 */     return outOfBounds(x, z) ? null : new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(x, z)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void write(int x, int z, byte[] data, int length) {
/*     */     try {
/* 155 */       int i = getOffset(x, z);
/* 156 */       int j = i >> 8;
/* 157 */       int k = i & 0xFF;
/* 158 */       int l = (length + 5) / 4096 + 1;
/*     */       
/* 160 */       if (l >= 256) {
/*     */         return;
/*     */       }
/*     */       
/* 164 */       if (j != 0 && k == l) {
/* 165 */         write(j, data, length);
/*     */       } else {
/* 167 */         for (int i1 = 0; i1 < k; i1++) {
/* 168 */           this.sectorFree.set(j + i1, Boolean.valueOf(true));
/*     */         }
/*     */         
/* 171 */         int l1 = this.sectorFree.indexOf(Boolean.valueOf(true));
/* 172 */         int j1 = 0;
/*     */         
/* 174 */         if (l1 != -1) {
/* 175 */           for (int k1 = l1; k1 < this.sectorFree.size(); k1++) {
/* 176 */             if (j1 != 0) {
/* 177 */               if (((Boolean)this.sectorFree.get(k1)).booleanValue()) {
/* 178 */                 j1++;
/*     */               } else {
/* 180 */                 j1 = 0;
/*     */               } 
/* 182 */             } else if (((Boolean)this.sectorFree.get(k1)).booleanValue()) {
/* 183 */               l1 = k1;
/* 184 */               j1 = 1;
/*     */             } 
/*     */             
/* 187 */             if (j1 >= l) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 193 */         if (j1 >= l) {
/* 194 */           j = l1;
/* 195 */           setOffset(x, z, l1 << 8 | l);
/*     */           
/* 197 */           for (int j2 = 0; j2 < l; j2++) {
/* 198 */             this.sectorFree.set(j + j2, Boolean.valueOf(false));
/*     */           }
/*     */           
/* 201 */           write(j, data, length);
/*     */         } else {
/* 203 */           this.dataFile.seek(this.dataFile.length());
/* 204 */           j = this.sectorFree.size();
/*     */           
/* 206 */           for (int i2 = 0; i2 < l; i2++) {
/* 207 */             this.dataFile.write(emptySector);
/* 208 */             this.sectorFree.add(Boolean.valueOf(false));
/*     */           } 
/*     */           
/* 211 */           this.sizeDelta += 4096 * l;
/* 212 */           write(j, data, length);
/* 213 */           setOffset(x, z, j << 8 | l);
/*     */         } 
/*     */       } 
/*     */       
/* 217 */       setChunkTimestamp(x, z, (int)(MinecraftServer.getCurrentTimeMillis() / 1000L));
/* 218 */     } catch (IOException ioexception) {
/* 219 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void write(int sectorNumber, byte[] data, int length) throws IOException {
/* 227 */     this.dataFile.seek((sectorNumber * 4096));
/* 228 */     this.dataFile.writeInt(length + 1);
/* 229 */     this.dataFile.writeByte(2);
/* 230 */     this.dataFile.write(data, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean outOfBounds(int x, int z) {
/* 237 */     return !(x >= 0 && x < 32 && z >= 0 && z < 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getOffset(int x, int z) {
/* 244 */     return this.offsets[x + z * 32];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChunkSaved(int x, int z) {
/* 251 */     return (getOffset(x, z) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setOffset(int x, int z, int offset) throws IOException {
/* 258 */     this.offsets[x + z * 32] = offset;
/* 259 */     this.dataFile.seek(((x + z * 32) * 4));
/* 260 */     this.dataFile.writeInt(offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setChunkTimestamp(int x, int z, int timestamp) throws IOException {
/* 267 */     this.chunkTimestamps[x + z * 32] = timestamp;
/* 268 */     this.dataFile.seek((4096 + (x + z * 32) * 4));
/* 269 */     this.dataFile.writeInt(timestamp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 276 */     if (this.dataFile != null)
/* 277 */       this.dataFile.close(); 
/*     */   }
/*     */   
/*     */   class ChunkBuffer
/*     */     extends ByteArrayOutputStream {
/*     */     private int chunkX;
/*     */     private int chunkZ;
/*     */     
/*     */     public ChunkBuffer(int x, int z) {
/* 286 */       super(8096);
/* 287 */       this.chunkX = x;
/* 288 */       this.chunkZ = z;
/*     */     }
/*     */     
/*     */     public void close() throws IOException {
/* 292 */       RegionFile.this.write(this.chunkX, this.chunkZ, this.buf, this.count);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\chunk\storage\RegionFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */