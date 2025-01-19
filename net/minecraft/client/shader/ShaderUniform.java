/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ 
/*     */ public class ShaderUniform
/*     */ {
/*  13 */   private static final Logger logger = LogManager.getLogger();
/*     */   private int uniformLocation;
/*     */   private final int uniformCount;
/*     */   private final int uniformType;
/*     */   private final IntBuffer uniformIntBuffer;
/*     */   private final FloatBuffer uniformFloatBuffer;
/*     */   private final String shaderName;
/*     */   private boolean dirty;
/*     */   private final ShaderManager shaderManager;
/*     */   
/*     */   public ShaderUniform(String name, int type, int count, ShaderManager manager) {
/*  24 */     this.shaderName = name;
/*  25 */     this.uniformCount = count;
/*  26 */     this.uniformType = type;
/*  27 */     this.shaderManager = manager;
/*     */     
/*  29 */     if (type <= 3) {
/*  30 */       this.uniformIntBuffer = BufferUtils.createIntBuffer(count);
/*  31 */       this.uniformFloatBuffer = null;
/*     */     } else {
/*  33 */       this.uniformIntBuffer = null;
/*  34 */       this.uniformFloatBuffer = BufferUtils.createFloatBuffer(count);
/*     */     } 
/*     */     
/*  37 */     this.uniformLocation = -1;
/*  38 */     markDirty();
/*     */   }
/*     */   
/*     */   private void markDirty() {
/*  42 */     this.dirty = true;
/*     */     
/*  44 */     if (this.shaderManager != null) {
/*  45 */       this.shaderManager.markDirty();
/*     */     }
/*     */   }
/*     */   
/*     */   public static int parseType(String p_148085_0_) {
/*  50 */     int i = -1;
/*     */     
/*  52 */     if (p_148085_0_.equals("int")) {
/*  53 */       i = 0;
/*  54 */     } else if (p_148085_0_.equals("float")) {
/*  55 */       i = 4;
/*  56 */     } else if (p_148085_0_.startsWith("matrix")) {
/*  57 */       if (p_148085_0_.endsWith("2x2")) {
/*  58 */         i = 8;
/*  59 */       } else if (p_148085_0_.endsWith("3x3")) {
/*  60 */         i = 9;
/*  61 */       } else if (p_148085_0_.endsWith("4x4")) {
/*  62 */         i = 10;
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     return i;
/*     */   }
/*     */   
/*     */   public void setUniformLocation(int p_148084_1_) {
/*  70 */     this.uniformLocation = p_148084_1_;
/*     */   }
/*     */   
/*     */   public String getShaderName() {
/*  74 */     return this.shaderName;
/*     */   }
/*     */   
/*     */   public void set(float p_148090_1_) {
/*  78 */     this.uniformFloatBuffer.position(0);
/*  79 */     this.uniformFloatBuffer.put(0, p_148090_1_);
/*  80 */     markDirty();
/*     */   }
/*     */   
/*     */   public void set(float p_148087_1_, float p_148087_2_) {
/*  84 */     this.uniformFloatBuffer.position(0);
/*  85 */     this.uniformFloatBuffer.put(0, p_148087_1_);
/*  86 */     this.uniformFloatBuffer.put(1, p_148087_2_);
/*  87 */     markDirty();
/*     */   }
/*     */   
/*     */   public void set(float p_148095_1_, float p_148095_2_, float p_148095_3_) {
/*  91 */     this.uniformFloatBuffer.position(0);
/*  92 */     this.uniformFloatBuffer.put(0, p_148095_1_);
/*  93 */     this.uniformFloatBuffer.put(1, p_148095_2_);
/*  94 */     this.uniformFloatBuffer.put(2, p_148095_3_);
/*  95 */     markDirty();
/*     */   }
/*     */   
/*     */   public void set(float p_148081_1_, float p_148081_2_, float p_148081_3_, float p_148081_4_) {
/*  99 */     this.uniformFloatBuffer.position(0);
/* 100 */     this.uniformFloatBuffer.put(p_148081_1_);
/* 101 */     this.uniformFloatBuffer.put(p_148081_2_);
/* 102 */     this.uniformFloatBuffer.put(p_148081_3_);
/* 103 */     this.uniformFloatBuffer.put(p_148081_4_);
/* 104 */     this.uniformFloatBuffer.flip();
/* 105 */     markDirty();
/*     */   }
/*     */   
/*     */   public void func_148092_b(float p_148092_1_, float p_148092_2_, float p_148092_3_, float p_148092_4_) {
/* 109 */     this.uniformFloatBuffer.position(0);
/*     */     
/* 111 */     if (this.uniformType >= 4) {
/* 112 */       this.uniformFloatBuffer.put(0, p_148092_1_);
/*     */     }
/*     */     
/* 115 */     if (this.uniformType >= 5) {
/* 116 */       this.uniformFloatBuffer.put(1, p_148092_2_);
/*     */     }
/*     */     
/* 119 */     if (this.uniformType >= 6) {
/* 120 */       this.uniformFloatBuffer.put(2, p_148092_3_);
/*     */     }
/*     */     
/* 123 */     if (this.uniformType >= 7) {
/* 124 */       this.uniformFloatBuffer.put(3, p_148092_4_);
/*     */     }
/*     */     
/* 127 */     markDirty();
/*     */   }
/*     */   
/*     */   public void set(int p_148083_1_, int p_148083_2_, int p_148083_3_, int p_148083_4_) {
/* 131 */     this.uniformIntBuffer.position(0);
/*     */     
/* 133 */     if (this.uniformType >= 0) {
/* 134 */       this.uniformIntBuffer.put(0, p_148083_1_);
/*     */     }
/*     */     
/* 137 */     if (this.uniformType >= 1) {
/* 138 */       this.uniformIntBuffer.put(1, p_148083_2_);
/*     */     }
/*     */     
/* 141 */     if (this.uniformType >= 2) {
/* 142 */       this.uniformIntBuffer.put(2, p_148083_3_);
/*     */     }
/*     */     
/* 145 */     if (this.uniformType >= 3) {
/* 146 */       this.uniformIntBuffer.put(3, p_148083_4_);
/*     */     }
/*     */     
/* 149 */     markDirty();
/*     */   }
/*     */   
/*     */   public void set(float[] p_148097_1_) {
/* 153 */     if (p_148097_1_.length < this.uniformCount) {
/* 154 */       logger.warn("Uniform.set called with a too-small value array (expected " + this.uniformCount + ", got " + p_148097_1_.length + "). Ignoring.");
/*     */     } else {
/* 156 */       this.uniformFloatBuffer.position(0);
/* 157 */       this.uniformFloatBuffer.put(p_148097_1_);
/* 158 */       this.uniformFloatBuffer.position(0);
/* 159 */       markDirty();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void set(float p_148094_1_, float p_148094_2_, float p_148094_3_, float p_148094_4_, float p_148094_5_, float p_148094_6_, float p_148094_7_, float p_148094_8_, float p_148094_9_, float p_148094_10_, float p_148094_11_, float p_148094_12_, float p_148094_13_, float p_148094_14_, float p_148094_15_, float p_148094_16_) {
/* 164 */     this.uniformFloatBuffer.position(0);
/* 165 */     this.uniformFloatBuffer.put(0, p_148094_1_);
/* 166 */     this.uniformFloatBuffer.put(1, p_148094_2_);
/* 167 */     this.uniformFloatBuffer.put(2, p_148094_3_);
/* 168 */     this.uniformFloatBuffer.put(3, p_148094_4_);
/* 169 */     this.uniformFloatBuffer.put(4, p_148094_5_);
/* 170 */     this.uniformFloatBuffer.put(5, p_148094_6_);
/* 171 */     this.uniformFloatBuffer.put(6, p_148094_7_);
/* 172 */     this.uniformFloatBuffer.put(7, p_148094_8_);
/* 173 */     this.uniformFloatBuffer.put(8, p_148094_9_);
/* 174 */     this.uniformFloatBuffer.put(9, p_148094_10_);
/* 175 */     this.uniformFloatBuffer.put(10, p_148094_11_);
/* 176 */     this.uniformFloatBuffer.put(11, p_148094_12_);
/* 177 */     this.uniformFloatBuffer.put(12, p_148094_13_);
/* 178 */     this.uniformFloatBuffer.put(13, p_148094_14_);
/* 179 */     this.uniformFloatBuffer.put(14, p_148094_15_);
/* 180 */     this.uniformFloatBuffer.put(15, p_148094_16_);
/* 181 */     markDirty();
/*     */   }
/*     */   
/*     */   public void set(Matrix4f p_148088_1_) {
/* 185 */     set(p_148088_1_.m00, p_148088_1_.m01, p_148088_1_.m02, p_148088_1_.m03, p_148088_1_.m10, p_148088_1_.m11, p_148088_1_.m12, p_148088_1_.m13, p_148088_1_.m20, p_148088_1_.m21, p_148088_1_.m22, p_148088_1_.m23, p_148088_1_.m30, p_148088_1_.m31, p_148088_1_.m32, p_148088_1_.m33);
/*     */   }
/*     */   
/*     */   public void upload() {
/* 189 */     if (!this.dirty);
/*     */ 
/*     */ 
/*     */     
/* 193 */     this.dirty = false;
/*     */     
/* 195 */     if (this.uniformType <= 3) {
/* 196 */       uploadInt();
/* 197 */     } else if (this.uniformType <= 7) {
/* 198 */       uploadFloat();
/*     */     } else {
/* 200 */       if (this.uniformType > 10) {
/* 201 */         logger.warn("Uniform.upload called, but type value (" + this.uniformType + ") is not " + "a valid type. Ignoring.");
/*     */         
/*     */         return;
/*     */       } 
/* 205 */       uploadFloatMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void uploadInt() {
/* 210 */     switch (this.uniformType) {
/*     */       case 0:
/* 212 */         OpenGlHelper.glUniform1(this.uniformLocation, this.uniformIntBuffer);
/*     */         return;
/*     */       
/*     */       case 1:
/* 216 */         OpenGlHelper.glUniform2(this.uniformLocation, this.uniformIntBuffer);
/*     */         return;
/*     */       
/*     */       case 2:
/* 220 */         OpenGlHelper.glUniform3(this.uniformLocation, this.uniformIntBuffer);
/*     */         return;
/*     */       
/*     */       case 3:
/* 224 */         OpenGlHelper.glUniform4(this.uniformLocation, this.uniformIntBuffer);
/*     */         return;
/*     */     } 
/*     */     
/* 228 */     logger.warn("Uniform.upload called, but count value (" + this.uniformCount + ") is " + " not in the range of 1 to 4. Ignoring.");
/*     */   }
/*     */ 
/*     */   
/*     */   private void uploadFloat() {
/* 233 */     switch (this.uniformType) {
/*     */       case 4:
/* 235 */         OpenGlHelper.glUniform1(this.uniformLocation, this.uniformFloatBuffer);
/*     */         return;
/*     */       
/*     */       case 5:
/* 239 */         OpenGlHelper.glUniform2(this.uniformLocation, this.uniformFloatBuffer);
/*     */         return;
/*     */       
/*     */       case 6:
/* 243 */         OpenGlHelper.glUniform3(this.uniformLocation, this.uniformFloatBuffer);
/*     */         return;
/*     */       
/*     */       case 7:
/* 247 */         OpenGlHelper.glUniform4(this.uniformLocation, this.uniformFloatBuffer);
/*     */         return;
/*     */     } 
/*     */     
/* 251 */     logger.warn("Uniform.upload called, but count value (" + this.uniformCount + ") is " + "not in the range of 1 to 4. Ignoring.");
/*     */   }
/*     */ 
/*     */   
/*     */   private void uploadFloatMatrix() {
/* 256 */     switch (this.uniformType) {
/*     */       case 8:
/* 258 */         OpenGlHelper.glUniformMatrix2(this.uniformLocation, true, this.uniformFloatBuffer);
/*     */         break;
/*     */       
/*     */       case 9:
/* 262 */         OpenGlHelper.glUniformMatrix3(this.uniformLocation, true, this.uniformFloatBuffer);
/*     */         break;
/*     */       
/*     */       case 10:
/* 266 */         OpenGlHelper.glUniformMatrix4(this.uniformLocation, true, this.uniformFloatBuffer);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\shader\ShaderUniform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */