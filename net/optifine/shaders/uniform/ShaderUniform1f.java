/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform1f extends ShaderUniformBase {
/*    */   private float[] programValues;
/*    */   private static final float VALUE_UNKNOWN = -3.4028235E38F;
/*    */   
/*    */   public ShaderUniform1f(String name) {
/* 10 */     super(name);
/* 11 */     resetValue();
/*    */   }
/*    */   
/*    */   public void setValue(float valueNew) {
/* 15 */     int i = getProgram();
/* 16 */     float f = this.programValues[i];
/*    */     
/* 18 */     if (valueNew != f) {
/* 19 */       this.programValues[i] = valueNew;
/* 20 */       int j = getLocation();
/*    */       
/* 22 */       if (j >= 0) {
/* 23 */         ARBShaderObjects.glUniform1fARB(j, valueNew);
/* 24 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public float getValue() {
/* 30 */     int i = getProgram();
/* 31 */     float f = this.programValues[i];
/* 32 */     return f;
/*    */   }
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 36 */     if (program >= this.programValues.length) {
/* 37 */       float[] afloat = this.programValues;
/* 38 */       float[] afloat1 = new float[program + 10];
/* 39 */       System.arraycopy(afloat, 0, afloat1, 0, afloat.length);
/*    */       
/* 41 */       for (int i = afloat.length; i < afloat1.length; i++) {
/* 42 */         afloat1[i] = -3.4028235E38F;
/*    */       }
/*    */       
/* 45 */       this.programValues = afloat1;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void resetValue() {
/* 50 */     this.programValues = new float[] { -3.4028235E38F };
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderUniform1f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */