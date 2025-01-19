/*    */ package net.optifine.util;
/*    */ 
/*    */ public class CacheLocalByte {
/*  4 */   private int maxX = 18;
/*  5 */   private int maxY = 128;
/*  6 */   private int maxZ = 18;
/*  7 */   private int offsetX = 0;
/*  8 */   private int offsetY = 0;
/*  9 */   private int offsetZ = 0;
/* 10 */   private byte[][][] cache = null;
/* 11 */   private byte[] lastZs = null;
/* 12 */   private int lastDz = 0;
/*    */   
/*    */   public CacheLocalByte(int maxX, int maxY, int maxZ) {
/* 15 */     this.maxX = maxX;
/* 16 */     this.maxY = maxY;
/* 17 */     this.maxZ = maxZ;
/* 18 */     this.cache = new byte[maxX][maxY][maxZ];
/* 19 */     resetCache();
/*    */   }
/*    */   
/*    */   public void resetCache() {
/* 23 */     for (int i = 0; i < this.maxX; i++) {
/* 24 */       byte[][] abyte = this.cache[i];
/*    */       
/* 26 */       for (int j = 0; j < this.maxY; j++) {
/* 27 */         byte[] abyte1 = abyte[j];
/*    */         
/* 29 */         for (int k = 0; k < this.maxZ; k++) {
/* 30 */           abyte1[k] = -1;
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setOffset(int x, int y, int z) {
/* 37 */     this.offsetX = x;
/* 38 */     this.offsetY = y;
/* 39 */     this.offsetZ = z;
/* 40 */     resetCache();
/*    */   }
/*    */   
/*    */   public byte get(int x, int y, int z) {
/*    */     try {
/* 45 */       this.lastZs = this.cache[x - this.offsetX][y - this.offsetY];
/* 46 */       this.lastDz = z - this.offsetZ;
/* 47 */       return this.lastZs[this.lastDz];
/* 48 */     } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
/* 49 */       arrayindexoutofboundsexception.printStackTrace();
/* 50 */       return -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setLast(byte val) {
/*    */     try {
/* 56 */       this.lastZs[this.lastDz] = val;
/* 57 */     } catch (Exception exception) {
/* 58 */       exception.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\CacheLocalByte.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */