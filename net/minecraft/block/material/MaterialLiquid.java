/*    */ package net.minecraft.block.material;
/*    */ 
/*    */ public class MaterialLiquid extends Material {
/*    */   public MaterialLiquid(MapColor color) {
/*  5 */     super(color);
/*  6 */     setReplaceable();
/*  7 */     setNoPushMobility();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isLiquid() {
/* 14 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksMovement() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSolid() {
/* 28 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\material\MaterialLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */