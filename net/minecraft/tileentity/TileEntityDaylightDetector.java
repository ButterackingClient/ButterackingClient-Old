/*    */ package net.minecraft.tileentity;
/*    */ 
/*    */ import net.minecraft.block.BlockDaylightDetector;
/*    */ import net.minecraft.util.ITickable;
/*    */ 
/*    */ public class TileEntityDaylightDetector
/*    */   extends TileEntity
/*    */   implements ITickable
/*    */ {
/*    */   public void update() {
/* 11 */     if (this.worldObj != null && !this.worldObj.isRemote && this.worldObj.getTotalWorldTime() % 20L == 0L) {
/* 12 */       this.blockType = getBlockType();
/*    */       
/* 14 */       if (this.blockType instanceof BlockDaylightDetector)
/* 15 */         ((BlockDaylightDetector)this.blockType).updatePower(this.worldObj, this.pos); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\tileentity\TileEntityDaylightDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */