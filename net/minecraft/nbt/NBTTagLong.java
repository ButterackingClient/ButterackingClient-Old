/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagLong
/*    */   extends NBTBase.NBTPrimitive
/*    */ {
/*    */   private long data;
/*    */   
/*    */   NBTTagLong() {}
/*    */   
/*    */   public NBTTagLong(long data) {
/* 17 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(DataOutput output) throws IOException {
/* 24 */     output.writeLong(this.data);
/*    */   }
/*    */   
/*    */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 28 */     sizeTracker.read(128L);
/* 29 */     this.data = input.readLong();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 36 */     return 4;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     return this.data + "L";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTBase copy() {
/* 47 */     return new NBTTagLong(this.data);
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 51 */     if (super.equals(p_equals_1_)) {
/* 52 */       NBTTagLong nbttaglong = (NBTTagLong)p_equals_1_;
/* 53 */       return (this.data == nbttaglong.data);
/*    */     } 
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 60 */     return super.hashCode() ^ (int)(this.data ^ this.data >>> 32L);
/*    */   }
/*    */   
/*    */   public long getLong() {
/* 64 */     return this.data;
/*    */   }
/*    */   
/*    */   public int getInt() {
/* 68 */     return (int)(this.data & 0xFFFFFFFFFFFFFFFFL);
/*    */   }
/*    */   
/*    */   public short getShort() {
/* 72 */     return (short)(int)(this.data & 0xFFFFL);
/*    */   }
/*    */   
/*    */   public byte getByte() {
/* 76 */     return (byte)(int)(this.data & 0xFFL);
/*    */   }
/*    */   
/*    */   public double getDouble() {
/* 80 */     return this.data;
/*    */   }
/*    */   
/*    */   public float getFloat() {
/* 84 */     return (float)this.data;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\nbt\NBTTagLong.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */