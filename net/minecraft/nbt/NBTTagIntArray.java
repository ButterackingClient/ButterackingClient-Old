/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagIntArray
/*    */   extends NBTBase
/*    */ {
/*    */   private int[] intArray;
/*    */   
/*    */   NBTTagIntArray() {}
/*    */   
/*    */   public NBTTagIntArray(int[] p_i45132_1_) {
/* 18 */     this.intArray = p_i45132_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(DataOutput output) throws IOException {
/* 25 */     output.writeInt(this.intArray.length);
/*    */     
/* 27 */     for (int i = 0; i < this.intArray.length; i++) {
/* 28 */       output.writeInt(this.intArray[i]);
/*    */     }
/*    */   }
/*    */   
/*    */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 33 */     sizeTracker.read(192L);
/* 34 */     int i = input.readInt();
/* 35 */     sizeTracker.read((32 * i));
/* 36 */     this.intArray = new int[i];
/*    */     
/* 38 */     for (int j = 0; j < i; j++) {
/* 39 */       this.intArray[j] = input.readInt();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 47 */     return 11;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 51 */     String s = "["; byte b;
/*    */     int i, arrayOfInt[];
/* 53 */     for (i = (arrayOfInt = this.intArray).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/* 54 */       s = String.valueOf(s) + j + ",";
/*    */       b++; }
/*    */     
/* 57 */     return String.valueOf(s) + "]";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTBase copy() {
/* 64 */     int[] aint = new int[this.intArray.length];
/* 65 */     System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
/* 66 */     return new NBTTagIntArray(aint);
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 70 */     return super.equals(p_equals_1_) ? Arrays.equals(this.intArray, ((NBTTagIntArray)p_equals_1_).intArray) : false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 74 */     return super.hashCode() ^ Arrays.hashCode(this.intArray);
/*    */   }
/*    */   
/*    */   public int[] getIntArray() {
/* 78 */     return this.intArray;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\nbt\NBTTagIntArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */