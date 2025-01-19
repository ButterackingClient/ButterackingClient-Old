/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform3f extends ShaderUniformBase {
/*    */   private float[][] programValues;
/*    */   private static final float VALUE_UNKNOWN = -3.4028235E38F;
/*    */   
/*    */   public ShaderUniform3f(String name) {
/* 10 */     super(name);
/* 11 */     resetValue();
/*    */   }
/*    */   
/*    */   public void setValue(float v0, float v1, float v2) {
/* 15 */     int i = getProgram();
/* 16 */     float[] afloat = this.programValues[i];
/*    */     
/* 18 */     if (afloat[0] != v0 || afloat[1] != v1 || afloat[2] != v2) {
/* 19 */       afloat[0] = v0;
/* 20 */       afloat[1] = v1;
/* 21 */       afloat[2] = v2;
/* 22 */       int j = getLocation();
/*    */       
/* 24 */       if (j >= 0) {
/* 25 */         ARBShaderObjects.glUniform3fARB(j, v0, v1, v2);
/* 26 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public float[] getValue() {
/* 32 */     int i = getProgram();
/* 33 */     float[] afloat = this.programValues[i];
/* 34 */     return afloat;
/*    */   }
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 38 */     if (program >= this.programValues.length) {
/* 39 */       float[][] afloat = this.programValues;
/* 40 */       float[][] afloat1 = new float[program + 10][];
/* 41 */       System.arraycopy(afloat, 0, afloat1, 0, afloat.length);
/* 42 */       this.programValues = afloat1;
/*    */     } 
/*    */     
/* 45 */     if (this.programValues[program] == null) {
/* 46 */       (new float[3])[0] = -3.4028235E38F; (new float[3])[1] = -3.4028235E38F; (new float[3])[2] = -3.4028235E38F; this.programValues[program] = new float[3];
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void resetValue() {
/* 51 */     this.programValues = new float[][] { { -3.4028235E38F, -3.4028235E38F, -3.4028235E38F } };
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderUniform3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */