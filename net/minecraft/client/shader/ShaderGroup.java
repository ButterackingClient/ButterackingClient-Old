/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.JsonSyntaxException;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.util.JsonException;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ 
/*     */ 
/*     */ public class ShaderGroup
/*     */ {
/*     */   private Framebuffer mainFramebuffer;
/*     */   private IResourceManager resourceManager;
/*     */   private String shaderGroupName;
/*  33 */   private final List<Shader> listShaders = Lists.newArrayList();
/*  34 */   private final Map<String, Framebuffer> mapFramebuffers = Maps.newHashMap();
/*  35 */   private final List<Framebuffer> listFramebuffers = Lists.newArrayList();
/*     */   private Matrix4f projectionMatrix;
/*     */   private int mainFramebufferWidth;
/*     */   private int mainFramebufferHeight;
/*     */   private float field_148036_j;
/*     */   private float field_148037_k;
/*     */   
/*     */   public ShaderGroup(TextureManager p_i1050_1_, IResourceManager p_i1050_2_, Framebuffer p_i1050_3_, ResourceLocation p_i1050_4_) throws JsonException, IOException, JsonSyntaxException {
/*  43 */     this.resourceManager = p_i1050_2_;
/*  44 */     this.mainFramebuffer = p_i1050_3_;
/*  45 */     this.field_148036_j = 0.0F;
/*  46 */     this.field_148037_k = 0.0F;
/*  47 */     this.mainFramebufferWidth = p_i1050_3_.framebufferWidth;
/*  48 */     this.mainFramebufferHeight = p_i1050_3_.framebufferHeight;
/*  49 */     this.shaderGroupName = p_i1050_4_.toString();
/*  50 */     resetProjectionMatrix();
/*  51 */     parseGroup(p_i1050_1_, p_i1050_4_);
/*     */   }
/*     */   
/*     */   public void parseGroup(TextureManager p_152765_1_, ResourceLocation p_152765_2_) throws JsonException, IOException, JsonSyntaxException {
/*  55 */     JsonParser jsonparser = new JsonParser();
/*  56 */     InputStream inputstream = null;
/*     */     
/*     */     try {
/*  59 */       IResource iresource = this.resourceManager.getResource(p_152765_2_);
/*  60 */       inputstream = iresource.getInputStream();
/*  61 */       JsonObject jsonobject = jsonparser.parse(IOUtils.toString(inputstream, Charsets.UTF_8)).getAsJsonObject();
/*     */       
/*  63 */       if (JsonUtils.isJsonArray(jsonobject, "targets")) {
/*  64 */         JsonArray jsonarray = jsonobject.getAsJsonArray("targets");
/*  65 */         int i = 0;
/*     */         
/*  67 */         for (JsonElement jsonelement : jsonarray) {
/*     */           try {
/*  69 */             initTarget(jsonelement);
/*  70 */           } catch (Exception exception1) {
/*  71 */             JsonException jsonexception1 = JsonException.func_151379_a(exception1);
/*  72 */             jsonexception1.func_151380_a("targets[" + i + "]");
/*  73 */             throw jsonexception1;
/*     */           } 
/*     */           
/*  76 */           i++;
/*     */         } 
/*     */       } 
/*     */       
/*  80 */       if (JsonUtils.isJsonArray(jsonobject, "passes")) {
/*  81 */         JsonArray jsonarray1 = jsonobject.getAsJsonArray("passes");
/*  82 */         int j = 0;
/*     */         
/*  84 */         for (JsonElement jsonelement1 : jsonarray1) {
/*     */           try {
/*  86 */             parsePass(p_152765_1_, jsonelement1);
/*  87 */           } catch (Exception exception) {
/*  88 */             JsonException jsonexception2 = JsonException.func_151379_a(exception);
/*  89 */             jsonexception2.func_151380_a("passes[" + j + "]");
/*  90 */             throw jsonexception2;
/*     */           } 
/*     */           
/*  93 */           j++;
/*     */         } 
/*     */       } 
/*  96 */     } catch (Exception exception2) {
/*  97 */       JsonException jsonexception = JsonException.func_151379_a(exception2);
/*  98 */       jsonexception.func_151381_b(p_152765_2_.getResourcePath());
/*  99 */       throw jsonexception;
/*     */     } finally {
/* 101 */       IOUtils.closeQuietly(inputstream);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initTarget(JsonElement p_148027_1_) throws JsonException {
/* 106 */     if (JsonUtils.isString(p_148027_1_)) {
/* 107 */       addFramebuffer(p_148027_1_.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
/*     */     } else {
/* 109 */       JsonObject jsonobject = JsonUtils.getJsonObject(p_148027_1_, "target");
/* 110 */       String s = JsonUtils.getString(jsonobject, "name");
/* 111 */       int i = JsonUtils.getInt(jsonobject, "width", this.mainFramebufferWidth);
/* 112 */       int j = JsonUtils.getInt(jsonobject, "height", this.mainFramebufferHeight);
/*     */       
/* 114 */       if (this.mapFramebuffers.containsKey(s)) {
/* 115 */         throw new JsonException(String.valueOf(s) + " is already defined");
/*     */       }
/*     */       
/* 118 */       addFramebuffer(s, i, j);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void parsePass(TextureManager p_152764_1_, JsonElement p_152764_2_) throws JsonException, IOException {
/* 123 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_152764_2_, "pass");
/* 124 */     String s = JsonUtils.getString(jsonobject, "name");
/* 125 */     String s1 = JsonUtils.getString(jsonobject, "intarget");
/* 126 */     String s2 = JsonUtils.getString(jsonobject, "outtarget");
/* 127 */     Framebuffer framebuffer = getFramebuffer(s1);
/* 128 */     Framebuffer framebuffer1 = getFramebuffer(s2);
/*     */     
/* 130 */     if (framebuffer == null)
/* 131 */       throw new JsonException("Input target '" + s1 + "' does not exist"); 
/* 132 */     if (framebuffer1 == null) {
/* 133 */       throw new JsonException("Output target '" + s2 + "' does not exist");
/*     */     }
/* 135 */     Shader shader = addShader(s, framebuffer, framebuffer1);
/* 136 */     JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "auxtargets", null);
/*     */     
/* 138 */     if (jsonarray != null) {
/* 139 */       int i = 0;
/*     */       
/* 141 */       for (JsonElement jsonelement : jsonarray) {
/*     */         try {
/* 143 */           JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonelement, "auxtarget");
/* 144 */           String s4 = JsonUtils.getString(jsonobject1, "name");
/* 145 */           String s3 = JsonUtils.getString(jsonobject1, "id");
/* 146 */           Framebuffer framebuffer2 = getFramebuffer(s3);
/*     */           
/* 148 */           if (framebuffer2 == null) {
/* 149 */             ResourceLocation resourcelocation = new ResourceLocation("textures/effect/" + s3 + ".png");
/*     */             
/*     */             try {
/* 152 */               this.resourceManager.getResource(resourcelocation);
/* 153 */             } catch (FileNotFoundException var24) {
/* 154 */               throw new JsonException("Render target or texture '" + s3 + "' does not exist");
/*     */             } 
/*     */             
/* 157 */             p_152764_1_.bindTexture(resourcelocation);
/* 158 */             ITextureObject itextureobject = p_152764_1_.getTexture(resourcelocation);
/* 159 */             int j = JsonUtils.getInt(jsonobject1, "width");
/* 160 */             int k = JsonUtils.getInt(jsonobject1, "height");
/* 161 */             boolean flag = JsonUtils.getBoolean(jsonobject1, "bilinear");
/*     */             
/* 163 */             if (flag) {
/* 164 */               GL11.glTexParameteri(3553, 10241, 9729);
/* 165 */               GL11.glTexParameteri(3553, 10240, 9729);
/*     */             } else {
/* 167 */               GL11.glTexParameteri(3553, 10241, 9728);
/* 168 */               GL11.glTexParameteri(3553, 10240, 9728);
/*     */             } 
/*     */             
/* 171 */             shader.addAuxFramebuffer(s4, Integer.valueOf(itextureobject.getGlTextureId()), j, k);
/*     */           } else {
/* 173 */             shader.addAuxFramebuffer(s4, framebuffer2, framebuffer2.framebufferTextureWidth, framebuffer2.framebufferTextureHeight);
/*     */           } 
/* 175 */         } catch (Exception exception1) {
/* 176 */           JsonException jsonexception = JsonException.func_151379_a(exception1);
/* 177 */           jsonexception.func_151380_a("auxtargets[" + i + "]");
/* 178 */           throw jsonexception;
/*     */         } 
/*     */         
/* 181 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     JsonArray jsonarray1 = JsonUtils.getJsonArray(jsonobject, "uniforms", null);
/*     */     
/* 187 */     if (jsonarray1 != null) {
/* 188 */       int l = 0;
/*     */       
/* 190 */       for (JsonElement jsonelement1 : jsonarray1) {
/*     */         try {
/* 192 */           initUniform(jsonelement1);
/* 193 */         } catch (Exception exception) {
/* 194 */           JsonException jsonexception1 = JsonException.func_151379_a(exception);
/* 195 */           jsonexception1.func_151380_a("uniforms[" + l + "]");
/* 196 */           throw jsonexception1;
/*     */         } 
/*     */         
/* 199 */         l++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initUniform(JsonElement p_148028_1_) throws JsonException {
/* 206 */     JsonObject jsonobject = JsonUtils.getJsonObject(p_148028_1_, "uniform");
/* 207 */     String s = JsonUtils.getString(jsonobject, "name");
/* 208 */     ShaderUniform shaderuniform = ((Shader)this.listShaders.get(this.listShaders.size() - 1)).getShaderManager().getShaderUniform(s);
/*     */     
/* 210 */     if (shaderuniform == null) {
/* 211 */       throw new JsonException("Uniform '" + s + "' does not exist");
/*     */     }
/* 213 */     float[] afloat = new float[4];
/* 214 */     int i = 0;
/*     */     
/* 216 */     for (JsonElement jsonelement : JsonUtils.getJsonArray(jsonobject, "values")) {
/*     */       try {
/* 218 */         afloat[i] = JsonUtils.getFloat(jsonelement, "value");
/* 219 */       } catch (Exception exception) {
/* 220 */         JsonException jsonexception = JsonException.func_151379_a(exception);
/* 221 */         jsonexception.func_151380_a("values[" + i + "]");
/* 222 */         throw jsonexception;
/*     */       } 
/*     */       
/* 225 */       i++;
/*     */     } 
/*     */     
/* 228 */     switch (i) {
/*     */       default:
/*     */         return;
/*     */ 
/*     */       
/*     */       case 1:
/* 234 */         shaderuniform.set(afloat[0]);
/*     */ 
/*     */       
/*     */       case 2:
/* 238 */         shaderuniform.set(afloat[0], afloat[1]);
/*     */ 
/*     */       
/*     */       case 3:
/* 242 */         shaderuniform.set(afloat[0], afloat[1], afloat[2]);
/*     */       case 4:
/*     */         break;
/*     */     } 
/* 246 */     shaderuniform.set(afloat[0], afloat[1], afloat[2], afloat[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Framebuffer getFramebufferRaw(String p_177066_1_) {
/* 252 */     return this.mapFramebuffers.get(p_177066_1_);
/*     */   }
/*     */   
/*     */   public void addFramebuffer(String p_148020_1_, int p_148020_2_, int p_148020_3_) {
/* 256 */     Framebuffer framebuffer = new Framebuffer(p_148020_2_, p_148020_3_, true);
/* 257 */     framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 258 */     this.mapFramebuffers.put(p_148020_1_, framebuffer);
/*     */     
/* 260 */     if (p_148020_2_ == this.mainFramebufferWidth && p_148020_3_ == this.mainFramebufferHeight) {
/* 261 */       this.listFramebuffers.add(framebuffer);
/*     */     }
/*     */   }
/*     */   
/*     */   public void deleteShaderGroup() {
/* 266 */     for (Framebuffer framebuffer : this.mapFramebuffers.values()) {
/* 267 */       framebuffer.deleteFramebuffer();
/*     */     }
/*     */     
/* 270 */     for (Shader shader : this.listShaders) {
/* 271 */       shader.deleteShader();
/*     */     }
/*     */     
/* 274 */     this.listShaders.clear();
/*     */   }
/*     */   
/*     */   public Shader addShader(String p_148023_1_, Framebuffer p_148023_2_, Framebuffer p_148023_3_) throws JsonException, IOException {
/* 278 */     Shader shader = new Shader(this.resourceManager, p_148023_1_, p_148023_2_, p_148023_3_);
/* 279 */     this.listShaders.add(this.listShaders.size(), shader);
/* 280 */     return shader;
/*     */   }
/*     */   
/*     */   private void resetProjectionMatrix() {
/* 284 */     this.projectionMatrix = new Matrix4f();
/* 285 */     this.projectionMatrix.setIdentity();
/* 286 */     this.projectionMatrix.m00 = 2.0F / this.mainFramebuffer.framebufferTextureWidth;
/* 287 */     this.projectionMatrix.m11 = 2.0F / -this.mainFramebuffer.framebufferTextureHeight;
/* 288 */     this.projectionMatrix.m22 = -0.0020001999F;
/* 289 */     this.projectionMatrix.m33 = 1.0F;
/* 290 */     this.projectionMatrix.m03 = -1.0F;
/* 291 */     this.projectionMatrix.m13 = 1.0F;
/* 292 */     this.projectionMatrix.m23 = -1.0001999F;
/*     */   }
/*     */   
/*     */   public void createBindFramebuffers(int width, int height) {
/* 296 */     this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
/* 297 */     this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
/* 298 */     resetProjectionMatrix();
/*     */     
/* 300 */     for (Shader shader : this.listShaders) {
/* 301 */       shader.setProjectionMatrix(this.projectionMatrix);
/*     */     }
/*     */     
/* 304 */     for (Framebuffer framebuffer : this.listFramebuffers) {
/* 305 */       framebuffer.createBindFramebuffer(width, height);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadShaderGroup(float partialTicks) {
/* 310 */     if (partialTicks < this.field_148037_k) {
/* 311 */       this.field_148036_j += 1.0F - this.field_148037_k;
/* 312 */       this.field_148036_j += partialTicks;
/*     */     } else {
/* 314 */       this.field_148036_j += partialTicks - this.field_148037_k;
/*     */     } 
/*     */     
/* 317 */     for (this.field_148037_k = partialTicks; this.field_148036_j > 20.0F; this.field_148036_j -= 20.0F);
/*     */ 
/*     */ 
/*     */     
/* 321 */     for (Shader shader : this.listShaders) {
/* 322 */       shader.loadShader(this.field_148036_j / 20.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public final String getShaderGroupName() {
/* 327 */     return this.shaderGroupName;
/*     */   }
/*     */   
/*     */   private Framebuffer getFramebuffer(String p_148017_1_) {
/* 331 */     return (p_148017_1_ == null) ? null : (p_148017_1_.equals("minecraft:main") ? this.mainFramebuffer : this.mapFramebuffers.get(p_148017_1_));
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\shader\ShaderGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */