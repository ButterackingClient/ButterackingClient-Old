/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniformM4
/*    */   extends ShaderUniformBase {
/*    */   private boolean transpose;
/*    */   private FloatBuffer matrix;
/*    */   
/*    */   public ShaderUniformM4(String name) {
/* 12 */     super(name);
/*    */   }
/*    */   
/*    */   public void setValue(boolean transpose, FloatBuffer matrix) {
/* 16 */     this.transpose = transpose;
/* 17 */     this.matrix = matrix;
/* 18 */     int i = getLocation();
/*    */     
/* 20 */     if (i >= 0) {
/* 21 */       ARBShaderObjects.glUniformMatrix4ARB(i, transpose, matrix);
/* 22 */       checkGLError();
/*    */     } 
/*    */   }
/*    */   
/*    */   public float getValue(int row, int col) {
/* 27 */     if (this.matrix == null) {
/* 28 */       return 0.0F;
/*    */     }
/* 30 */     int i = this.transpose ? (col * 4 + row) : (row * 4 + col);
/* 31 */     float f = this.matrix.get(i);
/* 32 */     return f;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onProgramSet(int program) {}
/*    */ 
/*    */   
/*    */   protected void resetValue() {
/* 40 */     this.matrix = null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderUniformM4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */