/*    */ package net.minecraft.block.material;
/*    */ 
/*    */ public class MaterialTransparent extends Material {
/*    */   public MaterialTransparent(MapColor color) {
/*  5 */     super(color);
/*  6 */     setReplaceable();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSolid() {
/* 13 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksLight() {
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksMovement() {
/* 27 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\block\material\MaterialTransparent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */