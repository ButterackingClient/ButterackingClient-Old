/*     */ package net.optifine;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.TextureUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class TextureAnimation
/*     */ {
/*  15 */   private String srcTex = null;
/*  16 */   private String dstTex = null;
/*  17 */   ResourceLocation dstTexLoc = null;
/*  18 */   private int dstTextId = -1;
/*  19 */   private int dstX = 0;
/*  20 */   private int dstY = 0;
/*  21 */   private int frameWidth = 0;
/*  22 */   private int frameHeight = 0;
/*  23 */   private TextureAnimationFrame[] frames = null;
/*  24 */   private int currentFrameIndex = 0;
/*     */   private boolean interpolate = false;
/*  26 */   private int interpolateSkip = 0;
/*  27 */   private ByteBuffer interpolateData = null;
/*  28 */   byte[] srcData = null;
/*  29 */   private ByteBuffer imageData = null;
/*     */   private boolean active = true;
/*     */   private boolean valid = true;
/*     */   
/*     */   public TextureAnimation(String texFrom, byte[] srcData, String texTo, ResourceLocation locTexTo, int dstX, int dstY, int frameWidth, int frameHeight, Properties props) {
/*  34 */     this.srcTex = texFrom;
/*  35 */     this.dstTex = texTo;
/*  36 */     this.dstTexLoc = locTexTo;
/*  37 */     this.dstX = dstX;
/*  38 */     this.dstY = dstY;
/*  39 */     this.frameWidth = frameWidth;
/*  40 */     this.frameHeight = frameHeight;
/*  41 */     int i = frameWidth * frameHeight * 4;
/*     */     
/*  43 */     if (srcData.length % i != 0) {
/*  44 */       Config.warn("Invalid animated texture length: " + srcData.length + ", frameWidth: " + frameWidth + ", frameHeight: " + frameHeight);
/*     */     }
/*     */     
/*  47 */     this.srcData = srcData;
/*  48 */     int j = srcData.length / i;
/*     */     
/*  50 */     if (props.get("tile.0") != null) {
/*  51 */       for (int k = 0; props.get("tile." + k) != null; k++) {
/*  52 */         j = k + 1;
/*     */       }
/*     */     }
/*     */     
/*  56 */     String s2 = (String)props.get("duration");
/*  57 */     int l = Math.max(Config.parseInt(s2, 1), 1);
/*  58 */     this.frames = new TextureAnimationFrame[j];
/*     */     
/*  60 */     for (int i1 = 0; i1 < this.frames.length; i1++) {
/*  61 */       String s = (String)props.get("tile." + i1);
/*  62 */       int j1 = Config.parseInt(s, i1);
/*  63 */       String s1 = (String)props.get("duration." + i1);
/*  64 */       int k1 = Math.max(Config.parseInt(s1, l), 1);
/*  65 */       TextureAnimationFrame textureanimationframe = new TextureAnimationFrame(j1, k1);
/*  66 */       this.frames[i1] = textureanimationframe;
/*     */     } 
/*     */     
/*  69 */     this.interpolate = Config.parseBoolean(props.getProperty("interpolate"), false);
/*  70 */     this.interpolateSkip = Config.parseInt(props.getProperty("skip"), 0);
/*     */     
/*  72 */     if (this.interpolate) {
/*  73 */       this.interpolateData = GLAllocation.createDirectByteBuffer(i);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean nextFrame() {
/*  78 */     TextureAnimationFrame textureanimationframe = getCurrentFrame();
/*     */     
/*  80 */     if (textureanimationframe == null) {
/*  81 */       return false;
/*     */     }
/*  83 */     textureanimationframe.counter++;
/*     */     
/*  85 */     if (textureanimationframe.counter < textureanimationframe.duration) {
/*  86 */       return this.interpolate;
/*     */     }
/*  88 */     textureanimationframe.counter = 0;
/*  89 */     this.currentFrameIndex++;
/*     */     
/*  91 */     if (this.currentFrameIndex >= this.frames.length) {
/*  92 */       this.currentFrameIndex = 0;
/*     */     }
/*     */     
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureAnimationFrame getCurrentFrame() {
/* 101 */     return getFrame(this.currentFrameIndex);
/*     */   }
/*     */   
/*     */   public TextureAnimationFrame getFrame(int index) {
/* 105 */     if (this.frames.length <= 0) {
/* 106 */       return null;
/*     */     }
/* 108 */     if (index < 0 || index >= this.frames.length) {
/* 109 */       index = 0;
/*     */     }
/*     */     
/* 112 */     TextureAnimationFrame textureanimationframe = this.frames[index];
/* 113 */     return textureanimationframe;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFrameCount() {
/* 118 */     return this.frames.length;
/*     */   }
/*     */   
/*     */   public void updateTexture() {
/* 122 */     if (this.valid) {
/* 123 */       if (this.dstTextId < 0) {
/* 124 */         ITextureObject itextureobject = TextureUtils.getTexture(this.dstTexLoc);
/*     */         
/* 126 */         if (itextureobject == null) {
/* 127 */           this.valid = false;
/*     */           
/*     */           return;
/*     */         } 
/* 131 */         this.dstTextId = itextureobject.getGlTextureId();
/*     */       } 
/*     */       
/* 134 */       if (this.imageData == null) {
/* 135 */         this.imageData = GLAllocation.createDirectByteBuffer(this.srcData.length);
/* 136 */         this.imageData.put(this.srcData);
/* 137 */         this.imageData.flip();
/* 138 */         this.srcData = null;
/*     */       } 
/*     */       
/* 141 */       this.active = SmartAnimations.isActive() ? SmartAnimations.isTextureRendered(this.dstTextId) : true;
/*     */       
/* 143 */       if (nextFrame() && 
/* 144 */         this.active) {
/* 145 */         int j = this.frameWidth * this.frameHeight * 4;
/* 146 */         TextureAnimationFrame textureanimationframe = getCurrentFrame();
/*     */         
/* 148 */         if (textureanimationframe != null) {
/* 149 */           int i = j * textureanimationframe.index;
/*     */           
/* 151 */           if (i + j <= this.imageData.limit()) {
/* 152 */             if (this.interpolate && textureanimationframe.counter > 0) {
/* 153 */               if (this.interpolateSkip <= 1 || textureanimationframe.counter % this.interpolateSkip == 0) {
/* 154 */                 TextureAnimationFrame textureanimationframe1 = getFrame(this.currentFrameIndex + 1);
/* 155 */                 double d0 = 1.0D * textureanimationframe.counter / textureanimationframe.duration;
/* 156 */                 updateTextureInerpolate(textureanimationframe, textureanimationframe1, d0);
/*     */               } 
/*     */             } else {
/* 159 */               this.imageData.position(i);
/* 160 */               GlStateManager.bindTexture(this.dstTextId);
/* 161 */               GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.imageData);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateTextureInerpolate(TextureAnimationFrame frame1, TextureAnimationFrame frame2, double k) {
/* 171 */     int i = this.frameWidth * this.frameHeight * 4;
/* 172 */     int j = i * frame1.index;
/*     */     
/* 174 */     if (j + i <= this.imageData.limit()) {
/* 175 */       int k1 = i * frame2.index;
/*     */       
/* 177 */       if (k1 + i <= this.imageData.limit()) {
/* 178 */         this.interpolateData.clear();
/*     */         
/* 180 */         for (int l = 0; l < i; l++) {
/* 181 */           int i1 = this.imageData.get(j + l) & 0xFF;
/* 182 */           int j1 = this.imageData.get(k1 + l) & 0xFF;
/* 183 */           int k3 = mix(i1, j1, k);
/* 184 */           byte b0 = (byte)k3;
/* 185 */           this.interpolateData.put(b0);
/*     */         } 
/*     */         
/* 188 */         this.interpolateData.flip();
/* 189 */         GlStateManager.bindTexture(this.dstTextId);
/* 190 */         GL11.glTexSubImage2D(3553, 0, this.dstX, this.dstY, this.frameWidth, this.frameHeight, 6408, 5121, this.interpolateData);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int mix(int col1, int col2, double k) {
/* 196 */     return (int)(col1 * (1.0D - k) + col2 * k);
/*     */   }
/*     */   
/*     */   public String getSrcTex() {
/* 200 */     return this.srcTex;
/*     */   }
/*     */   
/*     */   public String getDstTex() {
/* 204 */     return this.dstTex;
/*     */   }
/*     */   
/*     */   public ResourceLocation getDstTexLoc() {
/* 208 */     return this.dstTexLoc;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 212 */     return this.active;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\TextureAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */