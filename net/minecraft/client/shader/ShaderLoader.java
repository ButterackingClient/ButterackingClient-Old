/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.util.JsonException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShaderLoader
/*     */ {
/*     */   private final ShaderType shaderType;
/*     */   private final String shaderFilename;
/*     */   private int shader;
/*  23 */   private int shaderAttachCount = 0;
/*     */   
/*     */   private ShaderLoader(ShaderType type, int shaderId, String filename) {
/*  26 */     this.shaderType = type;
/*  27 */     this.shader = shaderId;
/*  28 */     this.shaderFilename = filename;
/*     */   }
/*     */   
/*     */   public void attachShader(ShaderManager manager) {
/*  32 */     this.shaderAttachCount++;
/*  33 */     OpenGlHelper.glAttachShader(manager.getProgram(), this.shader);
/*     */   }
/*     */   
/*     */   public void deleteShader(ShaderManager manager) {
/*  37 */     this.shaderAttachCount--;
/*     */     
/*  39 */     if (this.shaderAttachCount <= 0) {
/*  40 */       OpenGlHelper.glDeleteShader(this.shader);
/*  41 */       this.shaderType.getLoadedShaders().remove(this.shaderFilename);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getShaderFilename() {
/*  46 */     return this.shaderFilename;
/*     */   }
/*     */   
/*     */   public static ShaderLoader loadShader(IResourceManager resourceManager, ShaderType type, String filename) throws IOException {
/*  50 */     ShaderLoader shaderloader = type.getLoadedShaders().get(filename);
/*     */     
/*  52 */     if (shaderloader == null) {
/*  53 */       ResourceLocation resourcelocation = new ResourceLocation("shaders/program/" + filename + type.getShaderExtension());
/*  54 */       BufferedInputStream bufferedinputstream = new BufferedInputStream(resourceManager.getResource(resourcelocation).getInputStream());
/*  55 */       byte[] abyte = toByteArray(bufferedinputstream);
/*  56 */       ByteBuffer bytebuffer = BufferUtils.createByteBuffer(abyte.length);
/*  57 */       bytebuffer.put(abyte);
/*  58 */       bytebuffer.position(0);
/*  59 */       int i = OpenGlHelper.glCreateShader(type.getShaderMode());
/*  60 */       OpenGlHelper.glShaderSource(i, bytebuffer);
/*  61 */       OpenGlHelper.glCompileShader(i);
/*     */       
/*  63 */       if (OpenGlHelper.glGetShaderi(i, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
/*  64 */         String s = StringUtils.trim(OpenGlHelper.glGetShaderInfoLog(i, 32768));
/*  65 */         JsonException jsonexception = new JsonException("Couldn't compile " + type.getShaderName() + " program: " + s);
/*  66 */         jsonexception.func_151381_b(resourcelocation.getResourcePath());
/*  67 */         throw jsonexception;
/*     */       } 
/*     */       
/*  70 */       shaderloader = new ShaderLoader(type, i, filename);
/*  71 */       type.getLoadedShaders().put(filename, shaderloader);
/*     */     } 
/*     */     
/*  74 */     return shaderloader;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static byte[] toByteArray(BufferedInputStream p_177064_0_) throws IOException {
/*     */     byte[] abyte;
/*     */     try {
/*  81 */       abyte = IOUtils.toByteArray(p_177064_0_);
/*     */     } finally {
/*  83 */       p_177064_0_.close();
/*     */     } 
/*     */     
/*  86 */     return abyte;
/*     */   }
/*     */   
/*     */   public enum ShaderType {
/*  90 */     VERTEX("vertex", ".vsh", OpenGlHelper.GL_VERTEX_SHADER),
/*  91 */     FRAGMENT("fragment", ".fsh", OpenGlHelper.GL_FRAGMENT_SHADER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     private final Map<String, ShaderLoader> loadedShaders = Maps.newHashMap(); private final String shaderName;
/*     */     
/*     */     ShaderType(String p_i45090_3_, String p_i45090_4_, int p_i45090_5_) {
/*  99 */       this.shaderName = p_i45090_3_;
/* 100 */       this.shaderExtension = p_i45090_4_;
/* 101 */       this.shaderMode = p_i45090_5_;
/*     */     }
/*     */     private final String shaderExtension; private final int shaderMode;
/*     */     public String getShaderName() {
/* 105 */       return this.shaderName;
/*     */     }
/*     */     
/*     */     protected String getShaderExtension() {
/* 109 */       return this.shaderExtension;
/*     */     }
/*     */     
/*     */     protected int getShaderMode() {
/* 113 */       return this.shaderMode;
/*     */     }
/*     */     
/*     */     protected Map<String, ShaderLoader> getLoadedShaders() {
/* 117 */       return this.loadedShaders;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\shader\ShaderLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */