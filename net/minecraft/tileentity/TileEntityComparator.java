/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public class TileEntityComparator extends TileEntity {
/*    */   private int outputSignal;
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/*  9 */     super.writeToNBT(compound);
/* 10 */     compound.setInteger("OutputSignal", this.outputSignal);
/*    */   }
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 14 */     super.readFromNBT(compound);
/* 15 */     this.outputSignal = compound.getInteger("OutputSignal");
/*    */   }
/*    */   
/*    */   public int getOutputSignal() {
/* 19 */     return this.outputSignal;
/*    */   }
/*    */   
/*    */   public void setOutputSignal(int p_145995_1_) {
/* 23 */     this.outputSignal = p_145995_1_;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */