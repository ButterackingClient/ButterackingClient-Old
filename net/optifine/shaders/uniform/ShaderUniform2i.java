/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform2i extends ShaderUniformBase {
/*    */   private int[][] programValues;
/*    */   private static final int VALUE_UNKNOWN = -2147483648;
/*    */   
/*    */   public ShaderUniform2i(String name) {
/* 10 */     super(name);
/* 11 */     resetValue();
/*    */   }
/*    */   
/*    */   public void setValue(int v0, int v1) {
/* 15 */     int i = getProgram();
/* 16 */     int[] aint = this.programValues[i];
/*    */     
/* 18 */     if (aint[0] != v0 || aint[1] != v1) {
/* 19 */       aint[0] = v0;
/* 20 */       aint[1] = v1;
/* 21 */       int j = getLocation();
/*    */       
/* 23 */       if (j >= 0) {
/* 24 */         ARBShaderObjects.glUniform2iARB(j, v0, v1);
/* 25 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public int[] getValue() {
/* 31 */     int i = getProgram();
/* 32 */     int[] aint = this.programValues[i];
/* 33 */     return aint;
/*    */   }
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 37 */     if (program >= this.programValues.length) {
/* 38 */       int[][] aint = this.programValues;
/* 39 */       int[][] aint1 = new int[program + 10][];
/* 40 */       System.arraycopy(aint, 0, aint1, 0, aint.length);
/* 41 */       this.programValues = aint1;
/*    */     } 
/*    */     
/* 44 */     if (this.programValues[program] == null) {
/* 45 */       (new int[2])[0] = Integer.MIN_VALUE; (new int[2])[1] = Integer.MIN_VALUE; this.programValues[program] = new int[2];
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void resetValue() {
/* 50 */     this.programValues = new int[][] { { Integer.MIN_VALUE, Integer.MIN_VALUE } };
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderUniform2i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */