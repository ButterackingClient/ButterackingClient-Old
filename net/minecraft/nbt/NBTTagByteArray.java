/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagByteArray
/*    */   extends NBTBase
/*    */ {
/*    */   private byte[] data;
/*    */   
/*    */   NBTTagByteArray() {}
/*    */   
/*    */   public NBTTagByteArray(byte[] data) {
/* 18 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(DataOutput output) throws IOException {
/* 25 */     output.writeInt(this.data.length);
/* 26 */     output.write(this.data);
/*    */   }
/*    */   
/*    */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 30 */     sizeTracker.read(192L);
/* 31 */     int i = input.readInt();
/* 32 */     sizeTracker.read((8 * i));
/* 33 */     this.data = new byte[i];
/* 34 */     input.readFully(this.data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 41 */     return 7;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 45 */     return "[" + this.data.length + " bytes]";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTBase copy() {
/* 52 */     byte[] abyte = new byte[this.data.length];
/* 53 */     System.arraycopy(this.data, 0, abyte, 0, this.data.length);
/* 54 */     return new NBTTagByteArray(abyte);
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 58 */     return super.equals(p_equals_1_) ? Arrays.equals(this.data, ((NBTTagByteArray)p_equals_1_).data) : false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 62 */     return super.hashCode() ^ Arrays.hashCode(this.data);
/*    */   }
/*    */   
/*    */   public byte[] getByteArray() {
/* 66 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\nbt\NBTTagByteArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */