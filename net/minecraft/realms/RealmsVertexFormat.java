/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*    */ 
/*    */ public class RealmsVertexFormat
/*    */ {
/*    */   private VertexFormat v;
/*    */   
/*    */   public RealmsVertexFormat(VertexFormat vIn) {
/* 13 */     this.v = vIn;
/*    */   }
/*    */   
/*    */   public RealmsVertexFormat from(VertexFormat p_from_1_) {
/* 17 */     this.v = p_from_1_;
/* 18 */     return this;
/*    */   }
/*    */   
/*    */   public VertexFormat getVertexFormat() {
/* 22 */     return this.v;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 26 */     this.v.clear();
/*    */   }
/*    */   
/*    */   public int getUvOffset(int p_getUvOffset_1_) {
/* 30 */     return this.v.getUvOffsetById(p_getUvOffset_1_);
/*    */   }
/*    */   
/*    */   public int getElementCount() {
/* 34 */     return this.v.getElementCount();
/*    */   }
/*    */   
/*    */   public boolean hasColor() {
/* 38 */     return this.v.hasColor();
/*    */   }
/*    */   
/*    */   public boolean hasUv(int p_hasUv_1_) {
/* 42 */     return this.v.hasUvOffset(p_hasUv_1_);
/*    */   }
/*    */   
/*    */   public RealmsVertexFormatElement getElement(int p_getElement_1_) {
/* 46 */     return new RealmsVertexFormatElement(this.v.getElement(p_getElement_1_));
/*    */   }
/*    */   
/*    */   public RealmsVertexFormat addElement(RealmsVertexFormatElement p_addElement_1_) {
/* 50 */     return from(this.v.addElement(p_addElement_1_.getVertexFormatElement()));
/*    */   }
/*    */   
/*    */   public int getColorOffset() {
/* 54 */     return this.v.getColorOffset();
/*    */   }
/*    */   
/*    */   public List<RealmsVertexFormatElement> getElements() {
/* 58 */     List<RealmsVertexFormatElement> list = new ArrayList<>();
/*    */     
/* 60 */     for (VertexFormatElement vertexformatelement : this.v.getElements()) {
/* 61 */       list.add(new RealmsVertexFormatElement(vertexformatelement));
/*    */     }
/*    */     
/* 64 */     return list;
/*    */   }
/*    */   
/*    */   public boolean hasNormal() {
/* 68 */     return this.v.hasNormal();
/*    */   }
/*    */   
/*    */   public int getVertexSize() {
/* 72 */     return this.v.getNextOffset();
/*    */   }
/*    */   
/*    */   public int getOffset(int p_getOffset_1_) {
/* 76 */     return this.v.getOffset(p_getOffset_1_);
/*    */   }
/*    */   
/*    */   public int getNormalOffset() {
/* 80 */     return this.v.getNormalOffset();
/*    */   }
/*    */   
/*    */   public int getIntegerSize() {
/* 84 */     return this.v.getIntegerSize();
/*    */   }
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 88 */     return this.v.equals(p_equals_1_);
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 92 */     return this.v.hashCode();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 96 */     return this.v.toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\realms\RealmsVertexFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */