/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class Framebuffer
/*     */ {
/*     */   public int framebufferTextureWidth;
/*     */   public int framebufferTextureHeight;
/*     */   public int framebufferWidth;
/*     */   public int framebufferHeight;
/*     */   public boolean useDepth;
/*     */   public int framebufferObject;
/*     */   public int framebufferTexture;
/*     */   public int depthBuffer;
/*     */   public float[] framebufferColor;
/*     */   public int framebufferFilter;
/*     */   
/*     */   public Framebuffer(int p_i45078_1_, int p_i45078_2_, boolean p_i45078_3_) {
/*  26 */     this.useDepth = p_i45078_3_;
/*  27 */     this.framebufferObject = -1;
/*  28 */     this.framebufferTexture = -1;
/*  29 */     this.depthBuffer = -1;
/*  30 */     this.framebufferColor = new float[4];
/*  31 */     this.framebufferColor[0] = 1.0F;
/*  32 */     this.framebufferColor[1] = 1.0F;
/*  33 */     this.framebufferColor[2] = 1.0F;
/*  34 */     this.framebufferColor[3] = 0.0F;
/*  35 */     createBindFramebuffer(p_i45078_1_, p_i45078_2_);
/*     */   }
/*     */   
/*     */   public void createBindFramebuffer(int width, int height) {
/*  39 */     if (!OpenGlHelper.isFramebufferEnabled()) {
/*  40 */       this.framebufferWidth = width;
/*  41 */       this.framebufferHeight = height;
/*     */     } else {
/*  43 */       GlStateManager.enableDepth();
/*     */       
/*  45 */       if (this.framebufferObject >= 0) {
/*  46 */         deleteFramebuffer();
/*     */       }
/*     */       
/*  49 */       createFramebuffer(width, height);
/*  50 */       checkFramebufferComplete();
/*  51 */       OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deleteFramebuffer() {
/*  56 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*  57 */       unbindFramebufferTexture();
/*  58 */       unbindFramebuffer();
/*     */       
/*  60 */       if (this.depthBuffer > -1) {
/*  61 */         OpenGlHelper.glDeleteRenderbuffers(this.depthBuffer);
/*  62 */         this.depthBuffer = -1;
/*     */       } 
/*     */       
/*  65 */       if (this.framebufferTexture > -1) {
/*  66 */         TextureUtil.deleteTexture(this.framebufferTexture);
/*  67 */         this.framebufferTexture = -1;
/*     */       } 
/*     */       
/*  70 */       if (this.framebufferObject > -1) {
/*  71 */         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
/*  72 */         OpenGlHelper.glDeleteFramebuffers(this.framebufferObject);
/*  73 */         this.framebufferObject = -1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void createFramebuffer(int width, int height) {
/*  79 */     this.framebufferWidth = width;
/*  80 */     this.framebufferHeight = height;
/*  81 */     this.framebufferTextureWidth = width;
/*  82 */     this.framebufferTextureHeight = height;
/*     */     
/*  84 */     if (!OpenGlHelper.isFramebufferEnabled()) {
/*  85 */       framebufferClear();
/*     */     } else {
/*  87 */       this.framebufferObject = OpenGlHelper.glGenFramebuffers();
/*  88 */       this.framebufferTexture = TextureUtil.glGenTextures();
/*     */       
/*  90 */       if (this.useDepth) {
/*  91 */         this.depthBuffer = OpenGlHelper.glGenRenderbuffers();
/*     */       }
/*     */       
/*  94 */       setFramebufferFilter(9728);
/*  95 */       GlStateManager.bindTexture(this.framebufferTexture);
/*  96 */       GL11.glTexImage2D(3553, 0, 32856, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6408, 5121, null);
/*  97 */       OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
/*  98 */       OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, this.framebufferTexture, 0);
/*     */       
/* 100 */       if (this.useDepth) {
/* 101 */         OpenGlHelper.glBindRenderbuffer(OpenGlHelper.GL_RENDERBUFFER, this.depthBuffer);
/* 102 */         OpenGlHelper.glRenderbufferStorage(OpenGlHelper.GL_RENDERBUFFER, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
/* 103 */         OpenGlHelper.glFramebufferRenderbuffer(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT, OpenGlHelper.GL_RENDERBUFFER, this.depthBuffer);
/*     */       } 
/*     */       
/* 106 */       framebufferClear();
/* 107 */       unbindFramebufferTexture();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFramebufferFilter(int p_147607_1_) {
/* 112 */     if (OpenGlHelper.isFramebufferEnabled()) {
/* 113 */       this.framebufferFilter = p_147607_1_;
/* 114 */       GlStateManager.bindTexture(this.framebufferTexture);
/* 115 */       GL11.glTexParameterf(3553, 10241, p_147607_1_);
/* 116 */       GL11.glTexParameterf(3553, 10240, p_147607_1_);
/* 117 */       GL11.glTexParameterf(3553, 10242, 10496.0F);
/* 118 */       GL11.glTexParameterf(3553, 10243, 10496.0F);
/* 119 */       GlStateManager.bindTexture(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void checkFramebufferComplete() {
/* 124 */     int i = OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER);
/*     */     
/* 126 */     if (i != OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
/* 127 */       if (i == OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT)
/* 128 */         throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT"); 
/* 129 */       if (i == OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH)
/* 130 */         throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT"); 
/* 131 */       if (i == OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER)
/* 132 */         throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER"); 
/* 133 */       if (i == OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER) {
/* 134 */         throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
/*     */       }
/* 136 */       throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindFramebufferTexture() {
/* 142 */     if (OpenGlHelper.isFramebufferEnabled()) {
/* 143 */       GlStateManager.bindTexture(this.framebufferTexture);
/*     */     }
/*     */   }
/*     */   
/*     */   public void unbindFramebufferTexture() {
/* 148 */     if (OpenGlHelper.isFramebufferEnabled()) {
/* 149 */       GlStateManager.bindTexture(0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void bindFramebuffer(boolean p_147610_1_) {
/* 154 */     if (OpenGlHelper.isFramebufferEnabled()) {
/* 155 */       OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
/*     */       
/* 157 */       if (p_147610_1_) {
/* 158 */         GlStateManager.viewport(0, 0, this.framebufferWidth, this.framebufferHeight);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unbindFramebuffer() {
/* 164 */     if (OpenGlHelper.isFramebufferEnabled()) {
/* 165 */       OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setFramebufferColor(float p_147604_1_, float p_147604_2_, float p_147604_3_, float p_147604_4_) {
/* 170 */     this.framebufferColor[0] = p_147604_1_;
/* 171 */     this.framebufferColor[1] = p_147604_2_;
/* 172 */     this.framebufferColor[2] = p_147604_3_;
/* 173 */     this.framebufferColor[3] = p_147604_4_;
/*     */   }
/*     */   
/*     */   public void framebufferRender(int p_147615_1_, int p_147615_2_) {
/* 177 */     framebufferRenderExt(p_147615_1_, p_147615_2_, true);
/*     */   }
/*     */   
/*     */   public void framebufferRenderExt(int p_178038_1_, int p_178038_2_, boolean p_178038_3_) {
/* 181 */     if (OpenGlHelper.isFramebufferEnabled()) {
/* 182 */       GlStateManager.colorMask(true, true, true, false);
/* 183 */       GlStateManager.disableDepth();
/* 184 */       GlStateManager.depthMask(false);
/* 185 */       GlStateManager.matrixMode(5889);
/* 186 */       GlStateManager.loadIdentity();
/* 187 */       GlStateManager.ortho(0.0D, p_178038_1_, p_178038_2_, 0.0D, 1000.0D, 3000.0D);
/* 188 */       GlStateManager.matrixMode(5888);
/* 189 */       GlStateManager.loadIdentity();
/* 190 */       GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 191 */       GlStateManager.viewport(0, 0, p_178038_1_, p_178038_2_);
/* 192 */       GlStateManager.enableTexture2D();
/* 193 */       GlStateManager.disableLighting();
/* 194 */       GlStateManager.disableAlpha();
/*     */       
/* 196 */       if (p_178038_3_) {
/* 197 */         GlStateManager.disableBlend();
/* 198 */         GlStateManager.enableColorMaterial();
/*     */       } 
/*     */       
/* 201 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 202 */       bindFramebufferTexture();
/* 203 */       float f = p_178038_1_;
/* 204 */       float f1 = p_178038_2_;
/* 205 */       float f2 = this.framebufferWidth / this.framebufferTextureWidth;
/* 206 */       float f3 = this.framebufferHeight / this.framebufferTextureHeight;
/* 207 */       Tessellator tessellator = Tessellator.getInstance();
/* 208 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 209 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 210 */       worldrenderer.pos(0.0D, f1, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/* 211 */       worldrenderer.pos(f, f1, 0.0D).tex(f2, 0.0D).color(255, 255, 255, 255).endVertex();
/* 212 */       worldrenderer.pos(f, 0.0D, 0.0D).tex(f2, f3).color(255, 255, 255, 255).endVertex();
/* 213 */       worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, f3).color(255, 255, 255, 255).endVertex();
/* 214 */       tessellator.draw();
/* 215 */       unbindFramebufferTexture();
/* 216 */       GlStateManager.depthMask(true);
/* 217 */       GlStateManager.colorMask(true, true, true, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void framebufferClear() {
/* 222 */     bindFramebuffer(true);
/* 223 */     GlStateManager.clearColor(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
/* 224 */     int i = 16384;
/*     */     
/* 226 */     if (this.useDepth) {
/* 227 */       GlStateManager.clearDepth(1.0D);
/* 228 */       i |= 0x100;
/*     */     } 
/*     */     
/* 231 */     GlStateManager.clear(i);
/* 232 */     unbindFramebuffer();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\shader\Framebuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */