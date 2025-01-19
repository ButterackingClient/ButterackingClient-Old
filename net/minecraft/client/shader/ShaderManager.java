/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.util.JsonBlendingMode;
/*     */ import net.minecraft.client.util.JsonException;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ShaderManager
/*     */ {
/*  29 */   private static final Logger logger = LogManager.getLogger();
/*  30 */   private static final ShaderDefault defaultShaderUniform = new ShaderDefault();
/*  31 */   private static ShaderManager staticShaderManager = null;
/*  32 */   private static int currentProgram = -1;
/*     */   private static boolean field_148000_e = true;
/*  34 */   private final Map<String, Object> shaderSamplers = Maps.newHashMap();
/*  35 */   private final List<String> samplerNames = Lists.newArrayList();
/*  36 */   private final List<Integer> shaderSamplerLocations = Lists.newArrayList();
/*  37 */   private final List<ShaderUniform> shaderUniforms = Lists.newArrayList();
/*  38 */   private final List<Integer> shaderUniformLocations = Lists.newArrayList();
/*  39 */   private final Map<String, ShaderUniform> mappedShaderUniforms = Maps.newHashMap();
/*     */   private final int program;
/*     */   private final String programFilename;
/*     */   private final boolean useFaceCulling;
/*     */   private boolean isDirty;
/*     */   private final JsonBlendingMode field_148016_p;
/*     */   private final List<Integer> attribLocations;
/*     */   private final List<String> attributes;
/*     */   private final ShaderLoader vertexShaderLoader;
/*     */   private final ShaderLoader fragmentShaderLoader;
/*     */   
/*     */   public ShaderManager(IResourceManager resourceManager, String programName) throws JsonException, IOException {
/*  51 */     JsonParser jsonparser = new JsonParser();
/*  52 */     ResourceLocation resourcelocation = new ResourceLocation("shaders/program/" + programName + ".json");
/*  53 */     this.programFilename = programName;
/*  54 */     InputStream inputstream = null;
/*     */     
/*     */     try {
/*  57 */       inputstream = resourceManager.getResource(resourcelocation).getInputStream();
/*  58 */       JsonObject jsonobject = jsonparser.parse(IOUtils.toString(inputstream, Charsets.UTF_8)).getAsJsonObject();
/*  59 */       String s = JsonUtils.getString(jsonobject, "vertex");
/*  60 */       String s1 = JsonUtils.getString(jsonobject, "fragment");
/*  61 */       JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "samplers", null);
/*     */       
/*  63 */       if (jsonarray != null) {
/*  64 */         int i = 0;
/*     */         
/*  66 */         for (JsonElement jsonelement : jsonarray) {
/*     */           try {
/*  68 */             parseSampler(jsonelement);
/*  69 */           } catch (Exception exception2) {
/*  70 */             JsonException jsonexception1 = JsonException.func_151379_a(exception2);
/*  71 */             jsonexception1.func_151380_a("samplers[" + i + "]");
/*  72 */             throw jsonexception1;
/*     */           } 
/*     */           
/*  75 */           i++;
/*     */         } 
/*     */       } 
/*     */       
/*  79 */       JsonArray jsonarray1 = JsonUtils.getJsonArray(jsonobject, "attributes", null);
/*     */       
/*  81 */       if (jsonarray1 != null) {
/*  82 */         int j = 0;
/*  83 */         this.attribLocations = Lists.newArrayListWithCapacity(jsonarray1.size());
/*  84 */         this.attributes = Lists.newArrayListWithCapacity(jsonarray1.size());
/*     */         
/*  86 */         for (JsonElement jsonelement1 : jsonarray1) {
/*     */           try {
/*  88 */             this.attributes.add(JsonUtils.getString(jsonelement1, "attribute"));
/*  89 */           } catch (Exception exception1) {
/*  90 */             JsonException jsonexception2 = JsonException.func_151379_a(exception1);
/*  91 */             jsonexception2.func_151380_a("attributes[" + j + "]");
/*  92 */             throw jsonexception2;
/*     */           } 
/*     */           
/*  95 */           j++;
/*     */         } 
/*     */       } else {
/*  98 */         this.attribLocations = null;
/*  99 */         this.attributes = null;
/*     */       } 
/*     */       
/* 102 */       JsonArray jsonarray2 = JsonUtils.getJsonArray(jsonobject, "uniforms", null);
/*     */       
/* 104 */       if (jsonarray2 != null) {
/* 105 */         int k = 0;
/*     */         
/* 107 */         for (JsonElement jsonelement2 : jsonarray2) {
/*     */           try {
/* 109 */             parseUniform(jsonelement2);
/* 110 */           } catch (Exception exception) {
/* 111 */             JsonException jsonexception3 = JsonException.func_151379_a(exception);
/* 112 */             jsonexception3.func_151380_a("uniforms[" + k + "]");
/* 113 */             throw jsonexception3;
/*     */           } 
/*     */           
/* 116 */           k++;
/*     */         } 
/*     */       } 
/*     */       
/* 120 */       this.field_148016_p = JsonBlendingMode.func_148110_a(JsonUtils.getJsonObject(jsonobject, "blend", null));
/* 121 */       this.useFaceCulling = JsonUtils.getBoolean(jsonobject, "cull", true);
/* 122 */       this.vertexShaderLoader = ShaderLoader.loadShader(resourceManager, ShaderLoader.ShaderType.VERTEX, s);
/* 123 */       this.fragmentShaderLoader = ShaderLoader.loadShader(resourceManager, ShaderLoader.ShaderType.FRAGMENT, s1);
/* 124 */       this.program = ShaderLinkHelper.getStaticShaderLinkHelper().createProgram();
/* 125 */       ShaderLinkHelper.getStaticShaderLinkHelper().linkProgram(this);
/* 126 */       setupUniforms();
/*     */       
/* 128 */       if (this.attributes != null) {
/* 129 */         for (String s2 : this.attributes) {
/* 130 */           int l = OpenGlHelper.glGetAttribLocation(this.program, s2);
/* 131 */           this.attribLocations.add(Integer.valueOf(l));
/*     */         } 
/*     */       }
/* 134 */     } catch (Exception exception3) {
/* 135 */       JsonException jsonexception = JsonException.func_151379_a(exception3);
/* 136 */       jsonexception.func_151381_b(resourcelocation.getResourcePath());
/* 137 */       throw jsonexception;
/*     */     } finally {
/* 139 */       IOUtils.closeQuietly(inputstream);
/*     */     } 
/*     */     
/* 142 */     markDirty();
/*     */   }
/*     */   
/*     */   public void deleteShader() {
/* 146 */     ShaderLinkHelper.getStaticShaderLinkHelper().deleteShader(this);
/*     */   }
/*     */   
/*     */   public void endShader() {
/* 150 */     OpenGlHelper.glUseProgram(0);
/* 151 */     currentProgram = -1;
/* 152 */     staticShaderManager = null;
/* 153 */     field_148000_e = true;
/*     */     
/* 155 */     for (int i = 0; i < this.shaderSamplerLocations.size(); i++) {
/* 156 */       if (this.shaderSamplers.get(this.samplerNames.get(i)) != null) {
/* 157 */         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + i);
/* 158 */         GlStateManager.bindTexture(0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void useShader() {
/* 164 */     this.isDirty = false;
/* 165 */     staticShaderManager = this;
/* 166 */     this.field_148016_p.func_148109_a();
/*     */     
/* 168 */     if (this.program != currentProgram) {
/* 169 */       OpenGlHelper.glUseProgram(this.program);
/* 170 */       currentProgram = this.program;
/*     */     } 
/*     */     
/* 173 */     if (this.useFaceCulling) {
/* 174 */       GlStateManager.enableCull();
/*     */     } else {
/* 176 */       GlStateManager.disableCull();
/*     */     } 
/*     */     
/* 179 */     for (int i = 0; i < this.shaderSamplerLocations.size(); i++) {
/* 180 */       if (this.shaderSamplers.get(this.samplerNames.get(i)) != null) {
/* 181 */         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + i);
/* 182 */         GlStateManager.enableTexture2D();
/* 183 */         Object object = this.shaderSamplers.get(this.samplerNames.get(i));
/* 184 */         int j = -1;
/*     */         
/* 186 */         if (object instanceof Framebuffer) {
/* 187 */           j = ((Framebuffer)object).framebufferTexture;
/* 188 */         } else if (object instanceof ITextureObject) {
/* 189 */           j = ((ITextureObject)object).getGlTextureId();
/* 190 */         } else if (object instanceof Integer) {
/* 191 */           j = ((Integer)object).intValue();
/*     */         } 
/*     */         
/* 194 */         if (j != -1) {
/* 195 */           GlStateManager.bindTexture(j);
/* 196 */           OpenGlHelper.glUniform1i(OpenGlHelper.glGetUniformLocation(this.program, this.samplerNames.get(i)), i);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     for (ShaderUniform shaderuniform : this.shaderUniforms) {
/* 202 */       shaderuniform.upload();
/*     */     }
/*     */   }
/*     */   
/*     */   public void markDirty() {
/* 207 */     this.isDirty = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderUniform getShaderUniform(String p_147991_1_) {
/* 214 */     return this.mappedShaderUniforms.containsKey(p_147991_1_) ? this.mappedShaderUniforms.get(p_147991_1_) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShaderUniform getShaderUniformOrDefault(String p_147984_1_) {
/* 221 */     return this.mappedShaderUniforms.containsKey(p_147984_1_) ? this.mappedShaderUniforms.get(p_147984_1_) : defaultShaderUniform;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupUniforms() {
/* 228 */     int i = 0;
/*     */     
/* 230 */     for (int j = 0; i < this.samplerNames.size(); j++) {
/* 231 */       String s = this.samplerNames.get(i);
/* 232 */       int k = OpenGlHelper.glGetUniformLocation(this.program, s);
/*     */       
/* 234 */       if (k == -1) {
/* 235 */         logger.warn("Shader " + this.programFilename + "could not find sampler named " + s + " in the specified shader program.");
/* 236 */         this.shaderSamplers.remove(s);
/* 237 */         this.samplerNames.remove(j);
/* 238 */         j--;
/*     */       } else {
/* 240 */         this.shaderSamplerLocations.add(Integer.valueOf(k));
/*     */       } 
/*     */       
/* 243 */       i++;
/*     */     } 
/*     */     
/* 246 */     for (ShaderUniform shaderuniform : this.shaderUniforms) {
/* 247 */       String s1 = shaderuniform.getShaderName();
/* 248 */       int l = OpenGlHelper.glGetUniformLocation(this.program, s1);
/*     */       
/* 250 */       if (l == -1) {
/* 251 */         logger.warn("Could not find uniform named " + s1 + " in the specified" + " shader program."); continue;
/*     */       } 
/* 253 */       this.shaderUniformLocations.add(Integer.valueOf(l));
/* 254 */       shaderuniform.setUniformLocation(l);
/* 255 */       this.mappedShaderUniforms.put(s1, shaderuniform);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseSampler(JsonElement p_147996_1_) throws JsonException {
/* 261 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_147996_1_, "sampler");
/* 262 */     String s = JsonUtils.getString(jsonobject, "name");
/*     */     
/* 264 */     if (!JsonUtils.isString(jsonobject, "file")) {
/* 265 */       this.shaderSamplers.put(s, null);
/* 266 */       this.samplerNames.add(s);
/*     */     } else {
/* 268 */       this.samplerNames.add(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSamplerTexture(String p_147992_1_, Object p_147992_2_) {
/* 276 */     if (this.shaderSamplers.containsKey(p_147992_1_)) {
/* 277 */       this.shaderSamplers.remove(p_147992_1_);
/*     */     }
/*     */     
/* 280 */     this.shaderSamplers.put(p_147992_1_, p_147992_2_);
/* 281 */     markDirty();
/*     */   }
/*     */   
/*     */   private void parseUniform(JsonElement p_147987_1_) throws JsonException {
/* 285 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_147987_1_, "uniform");
/* 286 */     String s = JsonUtils.getString(jsonobject, "name");
/* 287 */     int i = ShaderUniform.parseType(JsonUtils.getString(jsonobject, "type"));
/* 288 */     int j = JsonUtils.getInt(jsonobject, "count");
/* 289 */     float[] afloat = new float[Math.max(j, 16)];
/* 290 */     JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "values");
/*     */     
/* 292 */     if (jsonarray.size() != j && jsonarray.size() > 1) {
/* 293 */       throw new JsonException("Invalid amount of values specified (expected " + j + ", found " + jsonarray.size() + ")");
/*     */     }
/* 295 */     int k = 0;
/*     */     
/* 297 */     for (JsonElement jsonelement : jsonarray) {
/*     */       try {
/* 299 */         afloat[k] = JsonUtils.getFloat(jsonelement, "value");
/* 300 */       } catch (Exception exception) {
/* 301 */         JsonException jsonexception = JsonException.func_151379_a(exception);
/* 302 */         jsonexception.func_151380_a("values[" + k + "]");
/* 303 */         throw jsonexception;
/*     */       } 
/*     */       
/* 306 */       k++;
/*     */     } 
/*     */     
/* 309 */     if (j > 1 && jsonarray.size() == 1) {
/* 310 */       while (k < j) {
/* 311 */         afloat[k] = afloat[0];
/* 312 */         k++;
/*     */       } 
/*     */     }
/*     */     
/* 316 */     int l = (j > 1 && j <= 4 && i < 8) ? (j - 1) : 0;
/* 317 */     ShaderUniform shaderuniform = new ShaderUniform(s, i + l, j, this);
/*     */     
/* 319 */     if (i <= 3) {
/* 320 */       shaderuniform.set((int)afloat[0], (int)afloat[1], (int)afloat[2], (int)afloat[3]);
/* 321 */     } else if (i <= 7) {
/* 322 */       shaderuniform.func_148092_b(afloat[0], afloat[1], afloat[2], afloat[3]);
/*     */     } else {
/* 324 */       shaderuniform.set(afloat);
/*     */     } 
/*     */     
/* 327 */     this.shaderUniforms.add(shaderuniform);
/*     */   }
/*     */ 
/*     */   
/*     */   public ShaderLoader getVertexShaderLoader() {
/* 332 */     return this.vertexShaderLoader;
/*     */   }
/*     */   
/*     */   public ShaderLoader getFragmentShaderLoader() {
/* 336 */     return this.fragmentShaderLoader;
/*     */   }
/*     */   
/*     */   public int getProgram() {
/* 340 */     return this.program;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\shader\ShaderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */