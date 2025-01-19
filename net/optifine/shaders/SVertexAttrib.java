/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*    */ 
/*    */ public class SVertexAttrib {
/*    */   public int index;
/*    */   public int count;
/*    */   public VertexFormatElement.EnumType type;
/*    */   public int offset;
/*    */   
/*    */   public SVertexAttrib(int index, int count, VertexFormatElement.EnumType type) {
/* 12 */     this.index = index;
/* 13 */     this.count = count;
/* 14 */     this.type = type;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\SVertexAttrib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */