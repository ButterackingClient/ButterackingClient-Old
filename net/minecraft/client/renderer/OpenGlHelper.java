/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import org.lwjgl.opengl.ARBCopyBuffer;
/*     */ import org.lwjgl.opengl.ARBFramebufferObject;
/*     */ import org.lwjgl.opengl.ARBMultitexture;
/*     */ import org.lwjgl.opengl.ARBShaderObjects;
/*     */ import org.lwjgl.opengl.ARBVertexBufferObject;
/*     */ import org.lwjgl.opengl.ARBVertexShader;
/*     */ import org.lwjgl.opengl.ContextCapabilities;
/*     */ import org.lwjgl.opengl.EXTBlendFuncSeparate;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL13;
/*     */ import org.lwjgl.opengl.GL14;
/*     */ import org.lwjgl.opengl.GL15;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ import org.lwjgl.opengl.GL31;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import oshi.SystemInfo;
/*     */ import oshi.hardware.Processor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OpenGlHelper
/*     */ {
/*     */   public static boolean nvidia;
/*     */   public static boolean ati;
/*     */   public static int GL_FRAMEBUFFER;
/*     */   public static int GL_RENDERBUFFER;
/*     */   public static int GL_COLOR_ATTACHMENT0;
/*     */   public static int GL_DEPTH_ATTACHMENT;
/*     */   public static int GL_FRAMEBUFFER_COMPLETE;
/*     */   public static int GL_FB_INCOMPLETE_ATTACHMENT;
/*     */   public static int GL_FB_INCOMPLETE_MISS_ATTACH;
/*     */   public static int GL_FB_INCOMPLETE_DRAW_BUFFER;
/*     */   public static int GL_FB_INCOMPLETE_READ_BUFFER;
/*     */   private static int framebufferType;
/*     */   public static boolean framebufferSupported;
/*     */   private static boolean shadersAvailable;
/*     */   private static boolean arbShaders;
/*     */   public static int GL_LINK_STATUS;
/*     */   public static int GL_COMPILE_STATUS;
/*     */   public static int GL_VERTEX_SHADER;
/*     */   public static int GL_FRAGMENT_SHADER;
/*     */   private static boolean arbMultitexture;
/*     */   public static int defaultTexUnit;
/*     */   public static int lightmapTexUnit;
/*     */   public static int GL_TEXTURE2;
/*     */   private static boolean arbTextureEnvCombine;
/*     */   public static int GL_COMBINE;
/*     */   public static int GL_INTERPOLATE;
/*     */   public static int GL_PRIMARY_COLOR;
/*     */   public static int GL_CONSTANT;
/*     */   public static int GL_PREVIOUS;
/*     */   public static int GL_COMBINE_RGB;
/*     */   public static int GL_SOURCE0_RGB;
/*     */   public static int GL_SOURCE1_RGB;
/*     */   public static int GL_SOURCE2_RGB;
/*     */   public static int GL_OPERAND0_RGB;
/*     */   public static int GL_OPERAND1_RGB;
/*     */   public static int GL_OPERAND2_RGB;
/*     */   public static int GL_COMBINE_ALPHA;
/*     */   public static int GL_SOURCE0_ALPHA;
/*     */   public static int GL_SOURCE1_ALPHA;
/*     */   public static int GL_SOURCE2_ALPHA;
/*     */   public static int GL_OPERAND0_ALPHA;
/*     */   public static int GL_OPERAND1_ALPHA;
/*     */   public static int GL_OPERAND2_ALPHA;
/*     */   private static boolean openGL14;
/*     */   public static boolean extBlendFuncSeparate;
/*     */   public static boolean openGL21;
/*     */   public static boolean shadersSupported;
/*  90 */   private static String logText = "";
/*     */   private static String cpu;
/*     */   public static boolean vboSupported;
/*     */   public static boolean vboSupportedAti;
/*     */   private static boolean arbVbo;
/*     */   public static int GL_ARRAY_BUFFER;
/*     */   public static int GL_STATIC_DRAW;
/*  97 */   public static float lastBrightnessX = 0.0F;
/*  98 */   public static float lastBrightnessY = 0.0F;
/*     */   
/*     */   public static boolean openGL31;
/*     */   
/*     */   public static boolean vboRegions;
/*     */   
/*     */   public static int GL_COPY_READ_BUFFER;
/*     */   public static int GL_COPY_WRITE_BUFFER;
/*     */   public static final int GL_QUADS = 7;
/*     */   public static final int GL_TRIANGLES = 4;
/*     */   
/*     */   public static void initializeTextures() {
/* 110 */     Config.initDisplay();
/* 111 */     ContextCapabilities contextcapabilities = GLContext.getCapabilities();
/* 112 */     arbMultitexture = (contextcapabilities.GL_ARB_multitexture && !contextcapabilities.OpenGL13);
/* 113 */     arbTextureEnvCombine = (contextcapabilities.GL_ARB_texture_env_combine && !contextcapabilities.OpenGL13);
/* 114 */     openGL31 = contextcapabilities.OpenGL31;
/*     */     
/* 116 */     if (openGL31) {
/* 117 */       GL_COPY_READ_BUFFER = 36662;
/* 118 */       GL_COPY_WRITE_BUFFER = 36663;
/*     */     } else {
/* 120 */       GL_COPY_READ_BUFFER = 36662;
/* 121 */       GL_COPY_WRITE_BUFFER = 36663;
/*     */     } 
/*     */     
/* 124 */     boolean flag = !(!openGL31 && !contextcapabilities.GL_ARB_copy_buffer);
/* 125 */     boolean flag1 = contextcapabilities.OpenGL14;
/* 126 */     vboRegions = (flag && flag1);
/*     */     
/* 128 */     if (!vboRegions) {
/* 129 */       List<String> list = new ArrayList<>();
/*     */       
/* 131 */       if (!flag) {
/* 132 */         list.add("OpenGL 1.3, ARB_copy_buffer");
/*     */       }
/*     */       
/* 135 */       if (!flag1) {
/* 136 */         list.add("OpenGL 1.4");
/*     */       }
/*     */       
/* 139 */       String s = "VboRegions not supported, missing: " + Config.listToString(list);
/* 140 */       Config.dbg(s);
/* 141 */       logText = String.valueOf(logText) + s + "\n";
/*     */     } 
/*     */     
/* 144 */     if (arbMultitexture) {
/* 145 */       logText = String.valueOf(logText) + "Using ARB_multitexture.\n";
/* 146 */       defaultTexUnit = 33984;
/* 147 */       lightmapTexUnit = 33985;
/* 148 */       GL_TEXTURE2 = 33986;
/*     */     } else {
/* 150 */       logText = String.valueOf(logText) + "Using GL 1.3 multitexturing.\n";
/* 151 */       defaultTexUnit = 33984;
/* 152 */       lightmapTexUnit = 33985;
/* 153 */       GL_TEXTURE2 = 33986;
/*     */     } 
/*     */     
/* 156 */     if (arbTextureEnvCombine) {
/* 157 */       logText = String.valueOf(logText) + "Using ARB_texture_env_combine.\n";
/* 158 */       GL_COMBINE = 34160;
/* 159 */       GL_INTERPOLATE = 34165;
/* 160 */       GL_PRIMARY_COLOR = 34167;
/* 161 */       GL_CONSTANT = 34166;
/* 162 */       GL_PREVIOUS = 34168;
/* 163 */       GL_COMBINE_RGB = 34161;
/* 164 */       GL_SOURCE0_RGB = 34176;
/* 165 */       GL_SOURCE1_RGB = 34177;
/* 166 */       GL_SOURCE2_RGB = 34178;
/* 167 */       GL_OPERAND0_RGB = 34192;
/* 168 */       GL_OPERAND1_RGB = 34193;
/* 169 */       GL_OPERAND2_RGB = 34194;
/* 170 */       GL_COMBINE_ALPHA = 34162;
/* 171 */       GL_SOURCE0_ALPHA = 34184;
/* 172 */       GL_SOURCE1_ALPHA = 34185;
/* 173 */       GL_SOURCE2_ALPHA = 34186;
/* 174 */       GL_OPERAND0_ALPHA = 34200;
/* 175 */       GL_OPERAND1_ALPHA = 34201;
/* 176 */       GL_OPERAND2_ALPHA = 34202;
/*     */     } else {
/* 178 */       logText = String.valueOf(logText) + "Using GL 1.3 texture combiners.\n";
/* 179 */       GL_COMBINE = 34160;
/* 180 */       GL_INTERPOLATE = 34165;
/* 181 */       GL_PRIMARY_COLOR = 34167;
/* 182 */       GL_CONSTANT = 34166;
/* 183 */       GL_PREVIOUS = 34168;
/* 184 */       GL_COMBINE_RGB = 34161;
/* 185 */       GL_SOURCE0_RGB = 34176;
/* 186 */       GL_SOURCE1_RGB = 34177;
/* 187 */       GL_SOURCE2_RGB = 34178;
/* 188 */       GL_OPERAND0_RGB = 34192;
/* 189 */       GL_OPERAND1_RGB = 34193;
/* 190 */       GL_OPERAND2_RGB = 34194;
/* 191 */       GL_COMBINE_ALPHA = 34162;
/* 192 */       GL_SOURCE0_ALPHA = 34184;
/* 193 */       GL_SOURCE1_ALPHA = 34185;
/* 194 */       GL_SOURCE2_ALPHA = 34186;
/* 195 */       GL_OPERAND0_ALPHA = 34200;
/* 196 */       GL_OPERAND1_ALPHA = 34201;
/* 197 */       GL_OPERAND2_ALPHA = 34202;
/*     */     } 
/*     */     
/* 200 */     extBlendFuncSeparate = (contextcapabilities.GL_EXT_blend_func_separate && !contextcapabilities.OpenGL14);
/* 201 */     openGL14 = !(!contextcapabilities.OpenGL14 && !contextcapabilities.GL_EXT_blend_func_separate);
/* 202 */     framebufferSupported = (openGL14 && (contextcapabilities.GL_ARB_framebuffer_object || contextcapabilities.GL_EXT_framebuffer_object || contextcapabilities.OpenGL30));
/*     */     
/* 204 */     if (framebufferSupported) {
/* 205 */       logText = String.valueOf(logText) + "Using framebuffer objects because ";
/*     */       
/* 207 */       if (contextcapabilities.OpenGL30) {
/* 208 */         logText = String.valueOf(logText) + "OpenGL 3.0 is supported and separate blending is supported.\n";
/* 209 */         framebufferType = 0;
/* 210 */         GL_FRAMEBUFFER = 36160;
/* 211 */         GL_RENDERBUFFER = 36161;
/* 212 */         GL_COLOR_ATTACHMENT0 = 36064;
/* 213 */         GL_DEPTH_ATTACHMENT = 36096;
/* 214 */         GL_FRAMEBUFFER_COMPLETE = 36053;
/* 215 */         GL_FB_INCOMPLETE_ATTACHMENT = 36054;
/* 216 */         GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
/* 217 */         GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
/* 218 */         GL_FB_INCOMPLETE_READ_BUFFER = 36060;
/* 219 */       } else if (contextcapabilities.GL_ARB_framebuffer_object) {
/* 220 */         logText = String.valueOf(logText) + "ARB_framebuffer_object is supported and separate blending is supported.\n";
/* 221 */         framebufferType = 1;
/* 222 */         GL_FRAMEBUFFER = 36160;
/* 223 */         GL_RENDERBUFFER = 36161;
/* 224 */         GL_COLOR_ATTACHMENT0 = 36064;
/* 225 */         GL_DEPTH_ATTACHMENT = 36096;
/* 226 */         GL_FRAMEBUFFER_COMPLETE = 36053;
/* 227 */         GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
/* 228 */         GL_FB_INCOMPLETE_ATTACHMENT = 36054;
/* 229 */         GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
/* 230 */         GL_FB_INCOMPLETE_READ_BUFFER = 36060;
/* 231 */       } else if (contextcapabilities.GL_EXT_framebuffer_object) {
/* 232 */         logText = String.valueOf(logText) + "EXT_framebuffer_object is supported.\n";
/* 233 */         framebufferType = 2;
/* 234 */         GL_FRAMEBUFFER = 36160;
/* 235 */         GL_RENDERBUFFER = 36161;
/* 236 */         GL_COLOR_ATTACHMENT0 = 36064;
/* 237 */         GL_DEPTH_ATTACHMENT = 36096;
/* 238 */         GL_FRAMEBUFFER_COMPLETE = 36053;
/* 239 */         GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
/* 240 */         GL_FB_INCOMPLETE_ATTACHMENT = 36054;
/* 241 */         GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
/* 242 */         GL_FB_INCOMPLETE_READ_BUFFER = 36060;
/*     */       } 
/*     */     } else {
/* 245 */       logText = String.valueOf(logText) + "Not using framebuffer objects because ";
/* 246 */       logText = String.valueOf(logText) + "OpenGL 1.4 is " + (contextcapabilities.OpenGL14 ? "" : "not ") + "supported, ";
/* 247 */       logText = String.valueOf(logText) + "EXT_blend_func_separate is " + (contextcapabilities.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
/* 248 */       logText = String.valueOf(logText) + "OpenGL 3.0 is " + (contextcapabilities.OpenGL30 ? "" : "not ") + "supported, ";
/* 249 */       logText = String.valueOf(logText) + "ARB_framebuffer_object is " + (contextcapabilities.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
/* 250 */       logText = String.valueOf(logText) + "EXT_framebuffer_object is " + (contextcapabilities.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
/*     */     } 
/*     */     
/* 253 */     openGL21 = contextcapabilities.OpenGL21;
/* 254 */     shadersAvailable = !(!openGL21 && (!contextcapabilities.GL_ARB_vertex_shader || !contextcapabilities.GL_ARB_fragment_shader || !contextcapabilities.GL_ARB_shader_objects));
/* 255 */     logText = String.valueOf(logText) + "Shaders are " + (shadersAvailable ? "" : "not ") + "available because ";
/*     */     
/* 257 */     if (shadersAvailable) {
/* 258 */       if (contextcapabilities.OpenGL21) {
/* 259 */         logText = String.valueOf(logText) + "OpenGL 2.1 is supported.\n";
/* 260 */         arbShaders = false;
/* 261 */         GL_LINK_STATUS = 35714;
/* 262 */         GL_COMPILE_STATUS = 35713;
/* 263 */         GL_VERTEX_SHADER = 35633;
/* 264 */         GL_FRAGMENT_SHADER = 35632;
/*     */       } else {
/* 266 */         logText = String.valueOf(logText) + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
/* 267 */         arbShaders = true;
/* 268 */         GL_LINK_STATUS = 35714;
/* 269 */         GL_COMPILE_STATUS = 35713;
/* 270 */         GL_VERTEX_SHADER = 35633;
/* 271 */         GL_FRAGMENT_SHADER = 35632;
/*     */       } 
/*     */     } else {
/* 274 */       logText = String.valueOf(logText) + "OpenGL 2.1 is " + (contextcapabilities.OpenGL21 ? "" : "not ") + "supported, ";
/* 275 */       logText = String.valueOf(logText) + "ARB_shader_objects is " + (contextcapabilities.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
/* 276 */       logText = String.valueOf(logText) + "ARB_vertex_shader is " + (contextcapabilities.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
/* 277 */       logText = String.valueOf(logText) + "ARB_fragment_shader is " + (contextcapabilities.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
/*     */     } 
/*     */     
/* 280 */     shadersSupported = (framebufferSupported && shadersAvailable);
/* 281 */     String s1 = GL11.glGetString(7936).toLowerCase();
/* 282 */     nvidia = s1.contains("nvidia");
/* 283 */     arbVbo = (!contextcapabilities.OpenGL15 && contextcapabilities.GL_ARB_vertex_buffer_object);
/* 284 */     vboSupported = !(!contextcapabilities.OpenGL15 && !arbVbo);
/* 285 */     logText = String.valueOf(logText) + "VBOs are " + (vboSupported ? "" : "not ") + "available because ";
/*     */     
/* 287 */     if (vboSupported) {
/* 288 */       if (arbVbo) {
/* 289 */         logText = String.valueOf(logText) + "ARB_vertex_buffer_object is supported.\n";
/* 290 */         GL_STATIC_DRAW = 35044;
/* 291 */         GL_ARRAY_BUFFER = 34962;
/*     */       } else {
/* 293 */         logText = String.valueOf(logText) + "OpenGL 1.5 is supported.\n";
/* 294 */         GL_STATIC_DRAW = 35044;
/* 295 */         GL_ARRAY_BUFFER = 34962;
/*     */       } 
/*     */     }
/*     */     
/* 299 */     ati = s1.contains("ati");
/*     */     
/* 301 */     if (ati) {
/* 302 */       if (vboSupported) {
/* 303 */         vboSupportedAti = true;
/*     */       } else {
/* 305 */         GameSettings.Options.RENDER_DISTANCE.setValueMax(16.0F);
/*     */       } 
/*     */     }
/*     */     
/*     */     try {
/* 310 */       Processor[] aprocessor = (new SystemInfo()).getHardware().getProcessors();
/* 311 */       cpu = String.format("%dx %s", new Object[] { Integer.valueOf(aprocessor.length), aprocessor[0] }).replaceAll("\\s+", " ");
/* 312 */     } catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean areShadersSupported() {
/* 318 */     return shadersSupported;
/*     */   }
/*     */   
/*     */   public static String getLogText() {
/* 322 */     return logText;
/*     */   }
/*     */   
/*     */   public static int glGetProgrami(int program, int pname) {
/* 326 */     return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(program, pname) : GL20.glGetProgrami(program, pname);
/*     */   }
/*     */   
/*     */   public static void glAttachShader(int program, int shaderIn) {
/* 330 */     if (arbShaders) {
/* 331 */       ARBShaderObjects.glAttachObjectARB(program, shaderIn);
/*     */     } else {
/* 333 */       GL20.glAttachShader(program, shaderIn);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glDeleteShader(int p_153180_0_) {
/* 338 */     if (arbShaders) {
/* 339 */       ARBShaderObjects.glDeleteObjectARB(p_153180_0_);
/*     */     } else {
/* 341 */       GL20.glDeleteShader(p_153180_0_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int glCreateShader(int type) {
/* 349 */     return arbShaders ? ARBShaderObjects.glCreateShaderObjectARB(type) : GL20.glCreateShader(type);
/*     */   }
/*     */   
/*     */   public static void glShaderSource(int shaderIn, ByteBuffer string) {
/* 353 */     if (arbShaders) {
/* 354 */       ARBShaderObjects.glShaderSourceARB(shaderIn, string);
/*     */     } else {
/* 356 */       GL20.glShaderSource(shaderIn, string);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glCompileShader(int shaderIn) {
/* 361 */     if (arbShaders) {
/* 362 */       ARBShaderObjects.glCompileShaderARB(shaderIn);
/*     */     } else {
/* 364 */       GL20.glCompileShader(shaderIn);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int glGetShaderi(int shaderIn, int pname) {
/* 369 */     return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(shaderIn, pname) : GL20.glGetShaderi(shaderIn, pname);
/*     */   }
/*     */   
/*     */   public static String glGetShaderInfoLog(int shaderIn, int maxLength) {
/* 373 */     return arbShaders ? ARBShaderObjects.glGetInfoLogARB(shaderIn, maxLength) : GL20.glGetShaderInfoLog(shaderIn, maxLength);
/*     */   }
/*     */   
/*     */   public static String glGetProgramInfoLog(int program, int maxLength) {
/* 377 */     return arbShaders ? ARBShaderObjects.glGetInfoLogARB(program, maxLength) : GL20.glGetProgramInfoLog(program, maxLength);
/*     */   }
/*     */   
/*     */   public static void glUseProgram(int program) {
/* 381 */     if (arbShaders) {
/* 382 */       ARBShaderObjects.glUseProgramObjectARB(program);
/*     */     } else {
/* 384 */       GL20.glUseProgram(program);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int glCreateProgram() {
/* 389 */     return arbShaders ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
/*     */   }
/*     */   
/*     */   public static void glDeleteProgram(int program) {
/* 393 */     if (arbShaders) {
/* 394 */       ARBShaderObjects.glDeleteObjectARB(program);
/*     */     } else {
/* 396 */       GL20.glDeleteProgram(program);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glLinkProgram(int program) {
/* 401 */     if (arbShaders) {
/* 402 */       ARBShaderObjects.glLinkProgramARB(program);
/*     */     } else {
/* 404 */       GL20.glLinkProgram(program);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int glGetUniformLocation(int programObj, CharSequence name) {
/* 409 */     return arbShaders ? ARBShaderObjects.glGetUniformLocationARB(programObj, name) : GL20.glGetUniformLocation(programObj, name);
/*     */   }
/*     */   
/*     */   public static void glUniform1(int location, IntBuffer values) {
/* 413 */     if (arbShaders) {
/* 414 */       ARBShaderObjects.glUniform1ARB(location, values);
/*     */     } else {
/* 416 */       GL20.glUniform1(location, values);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glUniform1i(int location, int v0) {
/* 421 */     if (arbShaders) {
/* 422 */       ARBShaderObjects.glUniform1iARB(location, v0);
/*     */     } else {
/* 424 */       GL20.glUniform1i(location, v0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glUniform1(int location, FloatBuffer values) {
/* 429 */     if (arbShaders) {
/* 430 */       ARBShaderObjects.glUniform1ARB(location, values);
/*     */     } else {
/* 432 */       GL20.glUniform1(location, values);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glUniform2(int location, IntBuffer values) {
/* 437 */     if (arbShaders) {
/* 438 */       ARBShaderObjects.glUniform2ARB(location, values);
/*     */     } else {
/* 440 */       GL20.glUniform2(location, values);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glUniform2(int location, FloatBuffer values) {
/* 445 */     if (arbShaders) {
/* 446 */       ARBShaderObjects.glUniform2ARB(location, values);
/*     */     } else {
/* 448 */       GL20.glUniform2(location, values);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glUniform3(int location, IntBuffer values) {
/* 453 */     if (arbShaders) {
/* 454 */       ARBShaderObjects.glUniform3ARB(location, values);
/*     */     } else {
/* 456 */       GL20.glUniform3(location, values);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glUniform3(int location, FloatBuffer values) {
/* 461 */     if (arbShaders) {
/* 462 */       ARBShaderObjects.glUniform3ARB(location, values);
/*     */     } else {
/* 464 */       GL20.glUniform3(location, values);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glUniform4(int location, IntBuffer values) {
/* 469 */     if (arbShaders) {
/* 470 */       ARBShaderObjects.glUniform4ARB(location, values);
/*     */     } else {
/* 472 */       GL20.glUniform4(location, values);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glUniform4(int location, FloatBuffer values) {
/* 477 */     if (arbShaders) {
/* 478 */       ARBShaderObjects.glUniform4ARB(location, values);
/*     */     } else {
/* 480 */       GL20.glUniform4(location, values);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glUniformMatrix2(int location, boolean transpose, FloatBuffer matrices) {
/* 485 */     if (arbShaders) {
/* 486 */       ARBShaderObjects.glUniformMatrix2ARB(location, transpose, matrices);
/*     */     } else {
/* 488 */       GL20.glUniformMatrix2(location, transpose, matrices);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glUniformMatrix3(int location, boolean transpose, FloatBuffer matrices) {
/* 493 */     if (arbShaders) {
/* 494 */       ARBShaderObjects.glUniformMatrix3ARB(location, transpose, matrices);
/*     */     } else {
/* 496 */       GL20.glUniformMatrix3(location, transpose, matrices);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices) {
/* 501 */     if (arbShaders) {
/* 502 */       ARBShaderObjects.glUniformMatrix4ARB(location, transpose, matrices);
/*     */     } else {
/* 504 */       GL20.glUniformMatrix4(location, transpose, matrices);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int glGetAttribLocation(int p_153164_0_, CharSequence p_153164_1_) {
/* 509 */     return arbShaders ? ARBVertexShader.glGetAttribLocationARB(p_153164_0_, p_153164_1_) : GL20.glGetAttribLocation(p_153164_0_, p_153164_1_);
/*     */   }
/*     */   
/*     */   public static int glGenBuffers() {
/* 513 */     return arbVbo ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
/*     */   }
/*     */   
/*     */   public static void glBindBuffer(int target, int buffer) {
/* 517 */     if (arbVbo) {
/* 518 */       ARBVertexBufferObject.glBindBufferARB(target, buffer);
/*     */     } else {
/* 520 */       GL15.glBindBuffer(target, buffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glBufferData(int target, ByteBuffer data, int usage) {
/* 525 */     if (arbVbo) {
/* 526 */       ARBVertexBufferObject.glBufferDataARB(target, data, usage);
/*     */     } else {
/* 528 */       GL15.glBufferData(target, data, usage);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glDeleteBuffers(int buffer) {
/* 533 */     if (arbVbo) {
/* 534 */       ARBVertexBufferObject.glDeleteBuffersARB(buffer);
/*     */     } else {
/* 536 */       GL15.glDeleteBuffers(buffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean useVbo() {
/* 541 */     return Config.isMultiTexture() ? false : ((Config.isRenderRegions() && !vboRegions) ? false : ((vboSupported && (Minecraft.getMinecraft()).gameSettings.useVbo)));
/*     */   }
/*     */   
/*     */   public static void glBindFramebuffer(int target, int framebufferIn) {
/* 545 */     if (framebufferSupported)
/* 546 */       switch (framebufferType) {
/*     */         case 0:
/* 548 */           GL30.glBindFramebuffer(target, framebufferIn);
/*     */           break;
/*     */         
/*     */         case 1:
/* 552 */           ARBFramebufferObject.glBindFramebuffer(target, framebufferIn);
/*     */           break;
/*     */         
/*     */         case 2:
/* 556 */           EXTFramebufferObject.glBindFramebufferEXT(target, framebufferIn);
/*     */           break;
/*     */       }  
/*     */   }
/*     */   
/*     */   public static void glBindRenderbuffer(int target, int renderbuffer) {
/* 562 */     if (framebufferSupported)
/* 563 */       switch (framebufferType) {
/*     */         case 0:
/* 565 */           GL30.glBindRenderbuffer(target, renderbuffer);
/*     */           break;
/*     */         
/*     */         case 1:
/* 569 */           ARBFramebufferObject.glBindRenderbuffer(target, renderbuffer);
/*     */           break;
/*     */         
/*     */         case 2:
/* 573 */           EXTFramebufferObject.glBindRenderbufferEXT(target, renderbuffer);
/*     */           break;
/*     */       }  
/*     */   }
/*     */   
/*     */   public static void glDeleteRenderbuffers(int renderbuffer) {
/* 579 */     if (framebufferSupported)
/* 580 */       switch (framebufferType) {
/*     */         case 0:
/* 582 */           GL30.glDeleteRenderbuffers(renderbuffer);
/*     */           break;
/*     */         
/*     */         case 1:
/* 586 */           ARBFramebufferObject.glDeleteRenderbuffers(renderbuffer);
/*     */           break;
/*     */         
/*     */         case 2:
/* 590 */           EXTFramebufferObject.glDeleteRenderbuffersEXT(renderbuffer);
/*     */           break;
/*     */       }  
/*     */   }
/*     */   
/*     */   public static void glDeleteFramebuffers(int framebufferIn) {
/* 596 */     if (framebufferSupported) {
/* 597 */       switch (framebufferType) {
/*     */         case 0:
/* 599 */           GL30.glDeleteFramebuffers(framebufferIn);
/*     */           break;
/*     */         
/*     */         case 1:
/* 603 */           ARBFramebufferObject.glDeleteFramebuffers(framebufferIn);
/*     */           break;
/*     */         
/*     */         case 2:
/* 607 */           EXTFramebufferObject.glDeleteFramebuffersEXT(framebufferIn);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int glGenFramebuffers() {
/* 616 */     if (!framebufferSupported) {
/* 617 */       return -1;
/*     */     }
/* 619 */     switch (framebufferType) {
/*     */       case 0:
/* 621 */         return GL30.glGenFramebuffers();
/*     */       
/*     */       case 1:
/* 624 */         return ARBFramebufferObject.glGenFramebuffers();
/*     */       
/*     */       case 2:
/* 627 */         return EXTFramebufferObject.glGenFramebuffersEXT();
/*     */     } 
/*     */     
/* 630 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int glGenRenderbuffers() {
/* 636 */     if (!framebufferSupported) {
/* 637 */       return -1;
/*     */     }
/* 639 */     switch (framebufferType) {
/*     */       case 0:
/* 641 */         return GL30.glGenRenderbuffers();
/*     */       
/*     */       case 1:
/* 644 */         return ARBFramebufferObject.glGenRenderbuffers();
/*     */       
/*     */       case 2:
/* 647 */         return EXTFramebufferObject.glGenRenderbuffersEXT();
/*     */     } 
/*     */     
/* 650 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void glRenderbufferStorage(int target, int internalFormat, int width, int height) {
/* 656 */     if (framebufferSupported)
/* 657 */       switch (framebufferType) {
/*     */         case 0:
/* 659 */           GL30.glRenderbufferStorage(target, internalFormat, width, height);
/*     */           break;
/*     */         
/*     */         case 1:
/* 663 */           ARBFramebufferObject.glRenderbufferStorage(target, internalFormat, width, height);
/*     */           break;
/*     */         
/*     */         case 2:
/* 667 */           EXTFramebufferObject.glRenderbufferStorageEXT(target, internalFormat, width, height);
/*     */           break;
/*     */       }  
/*     */   }
/*     */   
/*     */   public static void glFramebufferRenderbuffer(int target, int attachment, int renderBufferTarget, int renderBuffer) {
/* 673 */     if (framebufferSupported)
/* 674 */       switch (framebufferType) {
/*     */         case 0:
/* 676 */           GL30.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
/*     */           break;
/*     */         
/*     */         case 1:
/* 680 */           ARBFramebufferObject.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
/*     */           break;
/*     */         
/*     */         case 2:
/* 684 */           EXTFramebufferObject.glFramebufferRenderbufferEXT(target, attachment, renderBufferTarget, renderBuffer);
/*     */           break;
/*     */       }  
/*     */   }
/*     */   
/*     */   public static int glCheckFramebufferStatus(int target) {
/* 690 */     if (!framebufferSupported) {
/* 691 */       return -1;
/*     */     }
/* 693 */     switch (framebufferType) {
/*     */       case 0:
/* 695 */         return GL30.glCheckFramebufferStatus(target);
/*     */       
/*     */       case 1:
/* 698 */         return ARBFramebufferObject.glCheckFramebufferStatus(target);
/*     */       
/*     */       case 2:
/* 701 */         return EXTFramebufferObject.glCheckFramebufferStatusEXT(target);
/*     */     } 
/*     */     
/* 704 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
/* 710 */     if (framebufferSupported) {
/* 711 */       switch (framebufferType) {
/*     */         case 0:
/* 713 */           GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
/*     */           break;
/*     */         
/*     */         case 1:
/* 717 */           ARBFramebufferObject.glFramebufferTexture2D(target, attachment, textarget, texture, level);
/*     */           break;
/*     */         
/*     */         case 2:
/* 721 */           EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, textarget, texture, level);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setActiveTexture(int texture) {
/* 730 */     if (arbMultitexture) {
/* 731 */       ARBMultitexture.glActiveTextureARB(texture);
/*     */     } else {
/* 733 */       GL13.glActiveTexture(texture);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setClientActiveTexture(int texture) {
/* 741 */     if (arbMultitexture) {
/* 742 */       ARBMultitexture.glClientActiveTextureARB(texture);
/*     */     } else {
/* 744 */       GL13.glClientActiveTexture(texture);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setLightmapTextureCoords(int target, float p_77475_1_, float p_77475_2_) {
/* 752 */     if (arbMultitexture) {
/* 753 */       ARBMultitexture.glMultiTexCoord2fARB(target, p_77475_1_, p_77475_2_);
/*     */     } else {
/* 755 */       GL13.glMultiTexCoord2f(target, p_77475_1_, p_77475_2_);
/*     */     } 
/*     */     
/* 758 */     if (target == lightmapTexUnit) {
/* 759 */       lastBrightnessX = p_77475_1_;
/* 760 */       lastBrightnessY = p_77475_2_;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glBlendFunc(int sFactorRGB, int dFactorRGB, int sfactorAlpha, int dfactorAlpha) {
/* 765 */     if (openGL14) {
/* 766 */       if (extBlendFuncSeparate) {
/* 767 */         EXTBlendFuncSeparate.glBlendFuncSeparateEXT(sFactorRGB, dFactorRGB, sfactorAlpha, dfactorAlpha);
/*     */       } else {
/* 769 */         GL14.glBlendFuncSeparate(sFactorRGB, dFactorRGB, sfactorAlpha, dfactorAlpha);
/*     */       } 
/*     */     } else {
/* 772 */       GL11.glBlendFunc(sFactorRGB, dFactorRGB);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isFramebufferEnabled() {
/* 777 */     return Config.isFastRender() ? false : (Config.isAntialiasing() ? false : ((framebufferSupported && (Minecraft.getMinecraft()).gameSettings.fboEnable)));
/*     */   }
/*     */   
/*     */   public static void glBufferData(int p_glBufferData_0_, long p_glBufferData_1_, int p_glBufferData_3_) {
/* 781 */     if (arbVbo) {
/* 782 */       ARBVertexBufferObject.glBufferDataARB(p_glBufferData_0_, p_glBufferData_1_, p_glBufferData_3_);
/*     */     } else {
/* 784 */       GL15.glBufferData(p_glBufferData_0_, p_glBufferData_1_, p_glBufferData_3_);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glBufferSubData(int p_glBufferSubData_0_, long p_glBufferSubData_1_, ByteBuffer p_glBufferSubData_3_) {
/* 789 */     if (arbVbo) {
/* 790 */       ARBVertexBufferObject.glBufferSubDataARB(p_glBufferSubData_0_, p_glBufferSubData_1_, p_glBufferSubData_3_);
/*     */     } else {
/* 792 */       GL15.glBufferSubData(p_glBufferSubData_0_, p_glBufferSubData_1_, p_glBufferSubData_3_);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void glCopyBufferSubData(int p_glCopyBufferSubData_0_, int p_glCopyBufferSubData_1_, long p_glCopyBufferSubData_2_, long p_glCopyBufferSubData_4_, long p_glCopyBufferSubData_6_) {
/* 797 */     if (openGL31) {
/* 798 */       GL31.glCopyBufferSubData(p_glCopyBufferSubData_0_, p_glCopyBufferSubData_1_, p_glCopyBufferSubData_2_, p_glCopyBufferSubData_4_, p_glCopyBufferSubData_6_);
/*     */     } else {
/* 800 */       ARBCopyBuffer.glCopyBufferSubData(p_glCopyBufferSubData_0_, p_glCopyBufferSubData_1_, p_glCopyBufferSubData_2_, p_glCopyBufferSubData_4_, p_glCopyBufferSubData_6_);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getCpu() {
/* 805 */     return (cpu == null) ? "<unknown>" : cpu;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\OpenGlHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */