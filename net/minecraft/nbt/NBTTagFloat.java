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
/*    */ public class NBTTagFloat
/*    */   extends NBTBase.NBTPrimitive
/*    */ {
/*    */   private float data;
/*    */   
/*    */   NBTTagFloat() {}
/*    */   
/*    */   public NBTTagFloat(float data) {
/* 19 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(DataOutput output) throws IOException {
/* 26 */     output.writeFloat(this.data);
/*    */   }
/*    */   
/*    */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 30 */     sizeTracker.read(96L);
/* 31 */     this.data = input.readFloat();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 38 */     return 5;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     return this.data + "f";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTBase copy() {
/* 49 */     return new NBTTagFloat(this.data);
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 53 */     if (super.equals(p_equals_1_)) {
/* 54 */       NBTTagFloat nbttagfloat = (NBTTagFloat)p_equals_1_;
/* 55 */       return (this.data == nbttagfloat.data);
/*    */     } 
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 62 */     return super.hashCode() ^ Float.floatToIntBits(this.data);
/*    */   }
/*    */   
/*    */   public long getLong() {
/* 66 */     return (long)this.data;
/*    */   }
/*    */   
/*    */   public int getInt() {
/* 70 */     return MathHelper.floor_float(this.data);
/*    */   }
/*    */   
/*    */   public short getShort() {
/* 74 */     return (short)(MathHelper.floor_float(this.data) & 0xFFFF);
/*    */   }
/*    */   
/*    */   public byte getByte() {
/* 78 */     return (byte)(MathHelper.floor_float(this.data) & 0xFF);
/*    */   }
/*    */   
/*    */   public double getDouble() {
/* 82 */     return this.data;
/*    */   }
/*    */   
/*    */   public float getFloat() {
/* 86 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\nbt\NBTTagFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */