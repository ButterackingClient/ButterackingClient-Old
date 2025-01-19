/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform4i extends ShaderUniformBase {
/*    */   private int[][] programValues;
/*    */   private static final int VALUE_UNKNOWN = -2147483648;
/*    */   
/*    */   public ShaderUniform4i(String name) {
/* 10 */     super(name);
/* 11 */     resetValue();
/*    */   }
/*    */   
/*    */   public void setValue(int v0, int v1, int v2, int v3) {
/* 15 */     int i = getProgram();
/* 16 */     int[] aint = this.programValues[i];
/*    */     
/* 18 */     if (aint[0] != v0 || aint[1] != v1 || aint[2] != v2 || aint[3] != v3) {
/* 19 */       aint[0] = v0;
/* 20 */       aint[1] = v1;
/* 21 */       aint[2] = v2;
/* 22 */       aint[3] = v3;
/* 23 */       int j = getLocation();
/*    */       
/* 25 */       if (j >= 0) {
/* 26 */         ARBShaderObjects.glUniform4iARB(j, v0, v1, v2, v3);
/* 27 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public int[] getValue() {
/* 33 */     int i = getProgram();
/* 34 */     int[] aint = this.programValues[i];
/* 35 */     return aint;
/*    */   }
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 39 */     if (program >= this.programValues.length) {
/* 40 */       int[][] aint = this.programValues;
/* 41 */       int[][] aint1 = new int[program + 10][];
/* 42 */       System.arraycopy(aint, 0, aint1, 0, aint.length);
/* 43 */       this.programValues = aint1;
/*    */     } 
/*    */     
/* 46 */     if (this.programValues[program] == null) {
/* 47 */       (new int[4])[0] = Integer.MIN_VALUE; (new int[4])[1] = Integer.MIN_VALUE; (new int[4])[2] = Integer.MIN_VALUE; (new int[4])[3] = Integer.MIN_VALUE; this.programValues[program] = new int[4];
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void resetValue() {
/* 52 */     this.programValues = new int[][] { { Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE } };
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderUniform4i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */