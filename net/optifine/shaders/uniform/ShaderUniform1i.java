/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform1i extends ShaderUniformBase {
/*    */   private int[] programValues;
/*    */   private static final int VALUE_UNKNOWN = -2147483648;
/*    */   
/*    */   public ShaderUniform1i(String name) {
/* 10 */     super(name);
/* 11 */     resetValue();
/*    */   }
/*    */   
/*    */   public void setValue(int valueNew) {
/* 15 */     int i = getProgram();
/* 16 */     int j = this.programValues[i];
/*    */     
/* 18 */     if (valueNew != j) {
/* 19 */       this.programValues[i] = valueNew;
/* 20 */       int k = getLocation();
/*    */       
/* 22 */       if (k >= 0) {
/* 23 */         ARBShaderObjects.glUniform1iARB(k, valueNew);
/* 24 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 30 */     int i = getProgram();
/* 31 */     int j = this.programValues[i];
/* 32 */     return j;
/*    */   }
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 36 */     if (program >= this.programValues.length) {
/* 37 */       int[] aint = this.programValues;
/* 38 */       int[] aint1 = new int[program + 10];
/* 39 */       System.arraycopy(aint, 0, aint1, 0, aint.length);
/*    */       
/* 41 */       for (int i = aint.length; i < aint1.length; i++) {
/* 42 */         aint1[i] = Integer.MIN_VALUE;
/*    */       }
/*    */       
/* 45 */       this.programValues = aint1;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void resetValue() {
/* 50 */     this.programValues = new int[] { Integer.MIN_VALUE };
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderUniform1i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */