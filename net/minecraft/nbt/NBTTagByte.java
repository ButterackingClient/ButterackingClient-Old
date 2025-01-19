/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagByte
/*    */   extends NBTBase.NBTPrimitive
/*    */ {
/*    */   private byte data;
/*    */   
/*    */   NBTTagByte() {}
/*    */   
/*    */   public NBTTagByte(byte data) {
/* 17 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(DataOutput output) throws IOException {
/* 24 */     output.writeByte(this.data);
/*    */   }
/*    */   
/*    */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 28 */     sizeTracker.read(72L);
/* 29 */     this.data = input.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 36 */     return 1;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     return this.data + "b";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTBase copy() {
/* 47 */     return new NBTTagByte(this.data);
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 51 */     if (super.equals(p_equals_1_)) {
/* 52 */       NBTTagByte nbttagbyte = (NBTTagByte)p_equals_1_;
/* 53 */       return (this.data == nbttagbyte.data);
/*    */     } 
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 60 */     return super.hashCode() ^ this.data;
/*    */   }
/*    */   
/*    */   public long getLong() {
/* 64 */     return this.data;
/*    */   }
/*    */   
/*    */   public int getInt() {
/* 68 */     return this.data;
/*    */   }
/*    */   
/*    */   public short getShort() {
/* 72 */     return (short)this.data;
/*    */   }
/*    */   
/*    */   public byte getByte() {
/* 76 */     return this.data;
/*    */   }
/*    */   
/*    */   public double getDouble() {
/* 80 */     return this.data;
/*    */   }
/*    */   
/*    */   public float getFloat() {
/* 84 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\nbt\NBTTagByte.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */