/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagFloat;
/*    */ import net.minecraft.nbt.NBTTagList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Rotations
/*    */ {
/*    */   protected final float x;
/*    */   protected final float y;
/*    */   protected final float z;
/*    */   
/*    */   public Rotations(float x, float y, float z) {
/* 23 */     this.x = x;
/* 24 */     this.y = y;
/* 25 */     this.z = z;
/*    */   }
/*    */   
/*    */   public Rotations(NBTTagList nbt) {
/* 29 */     this.x = nbt.getFloatAt(0);
/* 30 */     this.y = nbt.getFloatAt(1);
/* 31 */     this.z = nbt.getFloatAt(2);
/*    */   }
/*    */   
/*    */   public NBTTagList writeToNBT() {
/* 35 */     NBTTagList nbttaglist = new NBTTagList();
/* 36 */     nbttaglist.appendTag((NBTBase)new NBTTagFloat(this.x));
/* 37 */     nbttaglist.appendTag((NBTBase)new NBTTagFloat(this.y));
/* 38 */     nbttaglist.appendTag((NBTBase)new NBTTagFloat(this.z));
/* 39 */     return nbttaglist;
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 43 */     if (!(p_equals_1_ instanceof Rotations)) {
/* 44 */       return false;
/*    */     }
/* 46 */     Rotations rotations = (Rotations)p_equals_1_;
/* 47 */     return (this.x == rotations.x && this.y == rotations.y && this.z == rotations.z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getX() {
/* 55 */     return this.x;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getY() {
/* 62 */     return this.y;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getZ() {
/* 69 */     return this.z;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\Rotations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */