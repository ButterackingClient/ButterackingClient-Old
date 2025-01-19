/*    */ package net.minecraft.world.gen.structure;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.WorldSavedData;
/*    */ 
/*    */ public class MapGenStructureData extends WorldSavedData {
/*  7 */   private NBTTagCompound tagCompound = new NBTTagCompound();
/*    */   
/*    */   public MapGenStructureData(String name) {
/* 10 */     super(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readFromNBT(NBTTagCompound nbt) {
/* 17 */     this.tagCompound = nbt.getCompoundTag("Features");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeToNBT(NBTTagCompound nbt) {
/* 24 */     nbt.setTag("Features", (NBTBase)this.tagCompound);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeInstance(NBTTagCompound tagCompoundIn, int chunkX, int chunkZ) {
/* 32 */     this.tagCompound.setTag(formatChunkCoords(chunkX, chunkZ), (NBTBase)tagCompoundIn);
/*    */   }
/*    */   
/*    */   public static String formatChunkCoords(int chunkX, int chunkZ) {
/* 36 */     return "[" + chunkX + "," + chunkZ + "]";
/*    */   }
/*    */   
/*    */   public NBTTagCompound getTagCompound() {
/* 40 */     return this.tagCompound;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\structure\MapGenStructureData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */