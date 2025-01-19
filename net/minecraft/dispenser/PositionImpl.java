/*    */ package net.minecraft.dispenser;
/*    */ 
/*    */ public class PositionImpl implements IPosition {
/*    */   protected final double x;
/*    */   protected final double y;
/*    */   protected final double z;
/*    */   
/*    */   public PositionImpl(double xCoord, double yCoord, double zCoord) {
/*  9 */     this.x = xCoord;
/* 10 */     this.y = yCoord;
/* 11 */     this.z = zCoord;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 15 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 19 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 23 */     return this.z;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\dispenser\PositionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */