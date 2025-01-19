/*    */ package net.minecraft.block.material;
/*    */ 
/*    */ public class MaterialPortal extends Material {
/*    */   public MaterialPortal(MapColor color) {
/*  5 */     super(color);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSolid() {
/* 12 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksLight() {
/* 19 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksMovement() {
/* 26 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\material\MaterialPortal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */