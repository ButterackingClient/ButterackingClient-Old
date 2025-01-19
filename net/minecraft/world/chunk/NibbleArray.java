/*    */ package net.minecraft.world.chunk;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NibbleArray
/*    */ {
/*    */   private final byte[] data;
/*    */   
/*    */   public NibbleArray() {
/* 11 */     this.data = new byte[2048];
/*    */   }
/*    */   
/*    */   public NibbleArray(byte[] storageArray) {
/* 15 */     this.data = storageArray;
/*    */     
/* 17 */     if (storageArray.length != 2048) {
/* 18 */       throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + storageArray.length);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int get(int x, int y, int z) {
/* 26 */     return getFromIndex(getCoordinateIndex(x, y, z));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void set(int x, int y, int z, int value) {
/* 33 */     setIndex(getCoordinateIndex(x, y, z), value);
/*    */   }
/*    */   
/*    */   private int getCoordinateIndex(int x, int y, int z) {
/* 37 */     return y << 8 | z << 4 | x;
/*    */   }
/*    */   
/*    */   public int getFromIndex(int index) {
/* 41 */     int i = getNibbleIndex(index);
/* 42 */     return isLowerNibble(index) ? (this.data[i] & 0xF) : (this.data[i] >> 4 & 0xF);
/*    */   }
/*    */   
/*    */   public void setIndex(int index, int value) {
/* 46 */     int i = getNibbleIndex(index);
/*    */     
/* 48 */     if (isLowerNibble(index)) {
/* 49 */       this.data[i] = (byte)(this.data[i] & 0xF0 | value & 0xF);
/*    */     } else {
/* 51 */       this.data[i] = (byte)(this.data[i] & 0xF | (value & 0xF) << 4);
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean isLowerNibble(int index) {
/* 56 */     return ((index & 0x1) == 0);
/*    */   }
/*    */   
/*    */   private int getNibbleIndex(int index) {
/* 60 */     return index >> 1;
/*    */   }
/*    */   
/*    */   public byte[] getData() {
/* 64 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\chunk\NibbleArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */