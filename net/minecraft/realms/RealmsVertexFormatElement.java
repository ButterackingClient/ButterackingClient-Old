/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*    */ 
/*    */ public class RealmsVertexFormatElement {
/*    */   private VertexFormatElement v;
/*    */   
/*    */   public RealmsVertexFormatElement(VertexFormatElement vIn) {
/*  9 */     this.v = vIn;
/*    */   }
/*    */   
/*    */   public VertexFormatElement getVertexFormatElement() {
/* 13 */     return this.v;
/*    */   }
/*    */   
/*    */   public boolean isPosition() {
/* 17 */     return this.v.isPositionElement();
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 21 */     return this.v.getIndex();
/*    */   }
/*    */   
/*    */   public int getByteSize() {
/* 25 */     return this.v.getSize();
/*    */   }
/*    */   
/*    */   public int getCount() {
/* 29 */     return this.v.getElementCount();
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 33 */     return this.v.hashCode();
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 37 */     return this.v.equals(p_equals_1_);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 41 */     return this.v.toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\RealmsVertexFormatElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */