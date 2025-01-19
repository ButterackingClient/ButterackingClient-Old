/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.util.ChatComponentText;
/*    */ import net.minecraft.util.ChatComponentTranslation;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ import net.minecraft.world.IInteractionObject;
/*    */ import net.minecraft.world.ILockableContainer;
/*    */ import net.minecraft.world.LockCode;
/*    */ 
/*    */ public abstract class TileEntityLockable extends TileEntity implements IInteractionObject, ILockableContainer {
/* 12 */   private LockCode code = LockCode.EMPTY_CODE;
/*    */   
/*    */   public void readFromNBT(NBTTagCompound compound) {
/* 15 */     super.readFromNBT(compound);
/* 16 */     this.code = LockCode.fromNBT(compound);
/*    */   }
/*    */   
/*    */   public void writeToNBT(NBTTagCompound compound) {
/* 20 */     super.writeToNBT(compound);
/*    */     
/* 22 */     if (this.code != null) {
/* 23 */       this.code.toNBT(compound);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isLocked() {
/* 28 */     return (this.code != null && !this.code.isEmpty());
/*    */   }
/*    */   
/*    */   public LockCode getLockCode() {
/* 32 */     return this.code;
/*    */   }
/*    */   
/*    */   public void setLockCode(LockCode code) {
/* 36 */     this.code = code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChatComponent getDisplayName() {
/* 43 */     return hasCustomName() ? (IChatComponent)new ChatComponentText(getName()) : (IChatComponent)new ChatComponentTranslation(getName(), new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityLockable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */