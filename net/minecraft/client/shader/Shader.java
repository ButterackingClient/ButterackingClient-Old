/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.util.JsonException;
/*     */ import org.lwjgl.util.vector.Matrix4f;
/*     */ 
/*     */ 
/*     */ public class Shader
/*     */ {
/*     */   private final ShaderManager manager;
/*     */   public final Framebuffer framebufferIn;
/*     */   public final Framebuffer framebufferOut;
/*  21 */   private final List<Object> listAuxFramebuffers = Lists.newArrayList();
/*  22 */   private final List<String> listAuxNames = Lists.newArrayList();
/*  23 */   private final List<Integer> listAuxWidths = Lists.newArrayList();
/*  24 */   private final List<Integer> listAuxHeights = Lists.newArrayList();
/*     */   private Matrix4f projectionMatrix;
/*     */   
/*     */   public Shader(IResourceManager p_i45089_1_, String p_i45089_2_, Framebuffer p_i45089_3_, Framebuffer p_i45089_4_) throws JsonException, IOException {
/*  28 */     this.manager = new ShaderManager(p_i45089_1_, p_i45089_2_);
/*  29 */     this.framebufferIn = p_i45089_3_;
/*  30 */     this.framebufferOut = p_i45089_4_;
/*     */   }
/*     */   
/*     */   public void deleteShader() {
/*  34 */     this.manager.deleteShader();
/*     */   }
/*     */   
/*     */   public void addAuxFramebuffer(String p_148041_1_, Object p_148041_2_, int p_148041_3_, int p_148041_4_) {
/*  38 */     this.listAuxNames.add(this.listAuxNames.size(), p_148041_1_);
/*  39 */     this.listAuxFramebuffers.add(this.listAuxFramebuffers.size(), p_148041_2_);
/*  40 */     this.listAuxWidths.add(this.listAuxWidths.size(), Integer.valueOf(p_148041_3_));
/*  41 */     this.listAuxHeights.add(this.listAuxHeights.size(), Integer.valueOf(p_148041_4_));
/*     */   }
/*     */   
/*     */   private void preLoadShader() {
/*  45 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  46 */     GlStateManager.disableBlend();
/*  47 */     GlStateManager.disableDepth();
/*  48 */     GlStateManager.disableAlpha();
/*  49 */     GlStateManager.disableFog();
/*  50 */     GlStateManager.disableLighting();
/*  51 */     GlStateManager.disableColorMaterial();
/*  52 */     GlStateManager.enableTexture2D();
/*  53 */     GlStateManager.bindTexture(0);
/*     */   }
/*     */   
/*     */   public void setProjectionMatrix(Matrix4f p_148045_1_) {
/*  57 */     this.projectionMatrix = p_148045_1_;
/*     */   }
/*     */   
/*     */   public void loadShader(float p_148042_1_) {
/*  61 */     preLoadShader();
/*  62 */     this.framebufferIn.unbindFramebuffer();
/*  63 */     float f = this.framebufferOut.framebufferTextureWidth;
/*  64 */     float f1 = this.framebufferOut.framebufferTextureHeight;
/*  65 */     GlStateManager.viewport(0, 0, (int)f, (int)f1);
/*  66 */     this.manager.addSamplerTexture("DiffuseSampler", this.framebufferIn);
/*     */     
/*  68 */     for (int i = 0; i < this.listAuxFramebuffers.size(); i++) {
/*  69 */       this.manager.addSamplerTexture(this.listAuxNames.get(i), this.listAuxFramebuffers.get(i));
/*  70 */       this.manager.getShaderUniformOrDefault("AuxSize" + i).set(((Integer)this.listAuxWidths.get(i)).intValue(), ((Integer)this.listAuxHeights.get(i)).intValue());
/*     */     } 
/*     */     
/*  73 */     this.manager.getShaderUniformOrDefault("ProjMat").set(this.projectionMatrix);
/*  74 */     this.manager.getShaderUniformOrDefault("InSize").set(this.framebufferIn.framebufferTextureWidth, this.framebufferIn.framebufferTextureHeight);
/*  75 */     this.manager.getShaderUniformOrDefault("OutSize").set(f, f1);
/*  76 */     this.manager.getShaderUniformOrDefault("Time").set(p_148042_1_);
/*  77 */     Minecraft minecraft = Minecraft.getMinecraft();
/*  78 */     this.manager.getShaderUniformOrDefault("ScreenSize").set(minecraft.displayWidth, minecraft.displayHeight);
/*  79 */     this.manager.useShader();
/*  80 */     this.framebufferOut.framebufferClear();
/*  81 */     this.framebufferOut.bindFramebuffer(false);
/*  82 */     GlStateManager.depthMask(false);
/*  83 */     GlStateManager.colorMask(true, true, true, true);
/*  84 */     Tessellator tessellator = Tessellator.getInstance();
/*  85 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  86 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  87 */     worldrenderer.pos(0.0D, f1, 500.0D).color(255, 255, 255, 255).endVertex();
/*  88 */     worldrenderer.pos(f, f1, 500.0D).color(255, 255, 255, 255).endVertex();
/*  89 */     worldrenderer.pos(f, 0.0D, 500.0D).color(255, 255, 255, 255).endVertex();
/*  90 */     worldrenderer.pos(0.0D, 0.0D, 500.0D).color(255, 255, 255, 255).endVertex();
/*  91 */     tessellator.draw();
/*  92 */     GlStateManager.depthMask(true);
/*  93 */     GlStateManager.colorMask(true, true, true, true);
/*  94 */     this.manager.endShader();
/*  95 */     this.framebufferOut.unbindFramebuffer();
/*  96 */     this.framebufferIn.unbindFramebufferTexture();
/*     */     
/*  98 */     for (Object object : this.listAuxFramebuffers) {
/*  99 */       if (object instanceof Framebuffer) {
/* 100 */         ((Framebuffer)object).unbindFramebufferTexture();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public ShaderManager getShaderManager() {
/* 106 */     return this.manager;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\shader\Shader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */