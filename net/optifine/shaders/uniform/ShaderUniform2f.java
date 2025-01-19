/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform2f extends ShaderUniformBase {
/*    */   private float[][] programValues;
/*    */   private static final float VALUE_UNKNOWN = -3.4028235E38F;
/*    */   
/*    */   public ShaderUniform2f(String name) {
/* 10 */     super(name);
/* 11 */     resetValue();
/*    */   }
/*    */   
/*    */   public void setValue(float v0, float v1) {
/* 15 */     int i = getProgram();
/* 16 */     float[] afloat = this.programValues[i];
/*    */     
/* 18 */     if (afloat[0] != v0 || afloat[1] != v1) {
/* 19 */       afloat[0] = v0;
/* 20 */       afloat[1] = v1;
/* 21 */       int j = getLocation();
/*    */       
/* 23 */       if (j >= 0) {
/* 24 */         ARBShaderObjects.glUniform2fARB(j, v0, v1);
/* 25 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public float[] getValue() {
/* 31 */     int i = getProgram();
/* 32 */     float[] afloat = this.programValues[i];
/* 33 */     return afloat;
/*    */   }
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 37 */     if (program >= this.programValues.length) {
/* 38 */       float[][] afloat = this.programValues;
/* 39 */       float[][] afloat1 = new float[program + 10][];
/* 40 */       System.arraycopy(afloat, 0, afloat1, 0, afloat.length);
/* 41 */       this.programValues = afloat1;
/*    */     } 
/*    */     
/* 44 */     if (this.programValues[program] == null) {
/* 45 */       (new float[2])[0] = -3.4028235E38F; (new float[2])[1] = -3.4028235E38F; this.programValues[program] = new float[2];
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void resetValue() {
/* 50 */     this.programValues = new float[][] { { -3.4028235E38F, -3.4028235E38F } };
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderUniform2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */