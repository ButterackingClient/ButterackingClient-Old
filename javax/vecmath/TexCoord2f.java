/*    */ package javax.vecmath;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TexCoord2f
/*    */   extends Tuple2f
/*    */   implements Serializable
/*    */ {
/*    */   static final long serialVersionUID = 7998248474800032487L;
/*    */   
/*    */   public TexCoord2f(float x, float y) {
/* 46 */     super(x, y);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord2f(float[] v) {
/* 56 */     super(v);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord2f(TexCoord2f v1) {
/* 66 */     super(v1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TexCoord2f(Tuple2f t1) {
/* 76 */     super(t1);
/*    */   }
/*    */   
/*    */   public TexCoord2f() {}
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\TexCoord2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */