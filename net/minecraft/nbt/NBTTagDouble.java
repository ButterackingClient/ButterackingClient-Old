/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagDouble
/*    */   extends NBTBase.NBTPrimitive
/*    */ {
/*    */   private double data;
/*    */   
/*    */   NBTTagDouble() {}
/*    */   
/*    */   public NBTTagDouble(double data) {
/* 19 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(DataOutput output) throws IOException {
/* 26 */     output.writeDouble(this.data);
/*    */   }
/*    */   
/*    */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 30 */     sizeTracker.read(128L);
/* 31 */     this.data = input.readDouble();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 38 */     return 6;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     return this.data + "d";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTBase copy() {
/* 49 */     return new NBTTagDouble(this.data);
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 53 */     if (super.equals(p_equals_1_)) {
/* 54 */       NBTTagDouble nbttagdouble = (NBTTagDouble)p_equals_1_;
/* 55 */       return (this.data == nbttagdouble.data);
/*    */     } 
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 62 */     long i = Double.doubleToLongBits(this.data);
/* 63 */     return super.hashCode() ^ (int)(i ^ i >>> 32L);
/*    */   }
/*    */   
/*    */   public long getLong() {
/* 67 */     return (long)Math.floor(this.data);
/*    */   }
/*    */   
/*    */   public int getInt() {
/* 71 */     return MathHelper.floor_double(this.data);
/*    */   }
/*    */   
/*    */   public short getShort() {
/* 75 */     return (short)(MathHelper.floor_double(this.data) & 0xFFFF);
/*    */   }
/*    */   
/*    */   public byte getByte() {
/* 79 */     return (byte)(MathHelper.floor_double(this.data) & 0xFF);
/*    */   }
/*    */   
/*    */   public double getDouble() {
/* 83 */     return this.data;
/*    */   }
/*    */   
/*    */   public float getFloat() {
/* 87 */     return (float)this.data;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\nbt\NBTTagDouble.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */