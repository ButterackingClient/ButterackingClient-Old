/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public abstract class NBTBase {
/*  8 */   public static final String[] NBT_TYPES = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
/*    */ 
/*    */ 
/*    */   
/*    */   abstract void write(DataOutput paramDataOutput) throws IOException;
/*    */ 
/*    */ 
/*    */   
/*    */   abstract void read(DataInput paramDataInput, int paramInt, NBTSizeTracker paramNBTSizeTracker) throws IOException;
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract String toString();
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract byte getId();
/*    */ 
/*    */   
/*    */   protected static NBTBase createNewByType(byte id) {
/* 28 */     switch (id) {
/*    */       case 0:
/* 30 */         return new NBTTagEnd();
/*    */       
/*    */       case 1:
/* 33 */         return new NBTTagByte();
/*    */       
/*    */       case 2:
/* 36 */         return new NBTTagShort();
/*    */       
/*    */       case 3:
/* 39 */         return new NBTTagInt();
/*    */       
/*    */       case 4:
/* 42 */         return new NBTTagLong();
/*    */       
/*    */       case 5:
/* 45 */         return new NBTTagFloat();
/*    */       
/*    */       case 6:
/* 48 */         return new NBTTagDouble();
/*    */       
/*    */       case 7:
/* 51 */         return new NBTTagByteArray();
/*    */       
/*    */       case 8:
/* 54 */         return new NBTTagString();
/*    */       
/*    */       case 9:
/* 57 */         return new NBTTagList();
/*    */       
/*    */       case 10:
/* 60 */         return new NBTTagCompound();
/*    */       
/*    */       case 11:
/* 63 */         return new NBTTagIntArray();
/*    */     } 
/*    */     
/* 66 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract NBTBase copy();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasNoTags() {
/* 79 */     return false;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 83 */     if (!(p_equals_1_ instanceof NBTBase)) {
/* 84 */       return false;
/*    */     }
/* 86 */     NBTBase nbtbase = (NBTBase)p_equals_1_;
/* 87 */     return (getId() == nbtbase.getId());
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 92 */     return getId();
/*    */   }
/*    */   
/*    */   protected String getString() {
/* 96 */     return toString();
/*    */   }
/*    */   
/*    */   public static abstract class NBTPrimitive extends NBTBase {
/*    */     public abstract long getLong();
/*    */     
/*    */     public abstract int getInt();
/*    */     
/*    */     public abstract short getShort();
/*    */     
/*    */     public abstract byte getByte();
/*    */     
/*    */     public abstract double getDouble();
/*    */     
/*    */     public abstract float getFloat();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\nbt\NBTBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */