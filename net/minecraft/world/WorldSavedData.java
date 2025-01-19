/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WorldSavedData
/*    */ {
/*    */   public final String mapName;
/*    */   private boolean dirty;
/*    */   
/*    */   public WorldSavedData(String name) {
/* 17 */     this.mapName = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void readFromNBT(NBTTagCompound paramNBTTagCompound);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void writeToNBT(NBTTagCompound paramNBTTagCompound);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void markDirty() {
/* 34 */     setDirty(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDirty(boolean isDirty) {
/* 41 */     this.dirty = isDirty;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDirty() {
/* 48 */     return this.dirty;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\WorldSavedData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */