/*    */ package net.optifine.shaders.uniform;
/*    */ 
/*    */ import org.lwjgl.opengl.ARBShaderObjects;
/*    */ 
/*    */ public class ShaderUniform4f extends ShaderUniformBase {
/*    */   private float[][] programValues;
/*    */   private static final float VALUE_UNKNOWN = -3.4028235E38F;
/*    */   
/*    */   public ShaderUniform4f(String name) {
/* 10 */     super(name);
/* 11 */     resetValue();
/*    */   }
/*    */   
/*    */   public void setValue(float v0, float v1, float v2, float v3) {
/* 15 */     int i = getProgram();
/* 16 */     float[] afloat = this.programValues[i];
/*    */     
/* 18 */     if (afloat[0] != v0 || afloat[1] != v1 || afloat[2] != v2 || afloat[3] != v3) {
/* 19 */       afloat[0] = v0;
/* 20 */       afloat[1] = v1;
/* 21 */       afloat[2] = v2;
/* 22 */       afloat[3] = v3;
/* 23 */       int j = getLocation();
/*    */       
/* 25 */       if (j >= 0) {
/* 26 */         ARBShaderObjects.glUniform4fARB(j, v0, v1, v2, v3);
/* 27 */         checkGLError();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public float[] getValue() {
/* 33 */     int i = getProgram();
/* 34 */     float[] afloat = this.programValues[i];
/* 35 */     return afloat;
/*    */   }
/*    */   
/*    */   protected void onProgramSet(int program) {
/* 39 */     if (program >= this.programValues.length) {
/* 40 */       float[][] afloat = this.programValues;
/* 41 */       float[][] afloat1 = new float[program + 10][];
/* 42 */       System.arraycopy(afloat, 0, afloat1, 0, afloat.length);
/* 43 */       this.programValues = afloat1;
/*    */     } 
/*    */     
/* 46 */     if (this.programValues[program] == null) {
/* 47 */       (new float[4])[0] = -3.4028235E38F; (new float[4])[1] = -3.4028235E38F; (new float[4])[2] = -3.4028235E38F; (new float[4])[3] = -3.4028235E38F; this.programValues[program] = new float[4];
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void resetValue() {
/* 52 */     this.programValues = new float[][] { { -3.4028235E38F, -3.4028235E38F, -3.4028235E38F, -3.4028235E38F } };
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shader\\uniform\ShaderUniform4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */