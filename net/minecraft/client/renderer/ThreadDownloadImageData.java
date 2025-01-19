/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.http.HttpPipeline;
/*     */ import net.optifine.http.HttpRequest;
/*     */ import net.optifine.http.HttpResponse;
/*     */ import net.optifine.player.CapeImageBuffer;
/*     */ import net.optifine.shaders.ShadersTex;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ThreadDownloadImageData
/*     */   extends SimpleTexture
/*     */ {
/*  30 */   private static final Logger logger = LogManager.getLogger();
/*  31 */   private static final AtomicInteger threadDownloadCounter = new AtomicInteger(0);
/*     */   private final File cacheFile;
/*     */   private final String imageUrl;
/*     */   private final IImageBuffer imageBuffer;
/*     */   private BufferedImage bufferedImage;
/*     */   private Thread imageThread;
/*     */   private boolean textureUploaded;
/*  38 */   public Boolean imageFound = null;
/*     */   public boolean pipeline = false;
/*     */   
/*     */   public ThreadDownloadImageData(File cacheFileIn, String imageUrlIn, ResourceLocation textureResourceLocation, IImageBuffer imageBufferIn) {
/*  42 */     super(textureResourceLocation);
/*  43 */     this.cacheFile = cacheFileIn;
/*  44 */     this.imageUrl = imageUrlIn;
/*  45 */     this.imageBuffer = imageBufferIn;
/*     */   }
/*     */   
/*     */   private void checkTextureUploaded() {
/*  49 */     if (!this.textureUploaded && this.bufferedImage != null) {
/*  50 */       this.textureUploaded = true;
/*     */       
/*  52 */       if (this.textureLocation != null) {
/*  53 */         deleteGlTexture();
/*     */       }
/*     */       
/*  56 */       if (Config.isShaders()) {
/*  57 */         ShadersTex.loadSimpleTexture(super.getGlTextureId(), this.bufferedImage, false, false, Config.getResourceManager(), this.textureLocation, getMultiTexID());
/*     */       } else {
/*  59 */         TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getGlTextureId() {
/*  65 */     checkTextureUploaded();
/*  66 */     return super.getGlTextureId();
/*     */   }
/*     */   
/*     */   public void setBufferedImage(BufferedImage bufferedImageIn) {
/*  70 */     this.bufferedImage = bufferedImageIn;
/*     */     
/*  72 */     if (this.imageBuffer != null) {
/*  73 */       this.imageBuffer.skinAvailable();
/*     */     }
/*     */     
/*  76 */     this.imageFound = Boolean.valueOf((this.bufferedImage != null));
/*     */   }
/*     */   
/*     */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/*  80 */     if (this.bufferedImage == null && this.textureLocation != null) {
/*  81 */       super.loadTexture(resourceManager);
/*     */     }
/*     */     
/*  84 */     if (this.imageThread == null) {
/*  85 */       if (this.cacheFile != null && this.cacheFile.isFile()) {
/*  86 */         logger.debug("Loading http texture from local cache ({})", new Object[] { this.cacheFile });
/*     */         
/*     */         try {
/*  89 */           this.bufferedImage = ImageIO.read(this.cacheFile);
/*     */           
/*  91 */           if (this.imageBuffer != null) {
/*  92 */             setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
/*     */           }
/*     */           
/*  95 */           loadingFinished();
/*  96 */         } catch (IOException ioexception) {
/*  97 */           logger.error("Couldn't load skin " + this.cacheFile, ioexception);
/*  98 */           loadTextureFromServer();
/*     */         } 
/*     */       } else {
/* 101 */         loadTextureFromServer();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void loadTextureFromServer() {
/* 107 */     this.imageThread = new Thread("Texture Downloader #" + threadDownloadCounter.incrementAndGet()) {
/*     */         public void run() {
/* 109 */           HttpURLConnection httpurlconnection = null;
/* 110 */           ThreadDownloadImageData.logger.debug("Downloading http texture from {} to {}", new Object[] { ThreadDownloadImageData.access$1(this.this$0), ThreadDownloadImageData.access$2(this.this$0) });
/*     */           
/* 112 */           if (ThreadDownloadImageData.this.shouldPipeline()) {
/* 113 */             ThreadDownloadImageData.this.loadPipelined();
/*     */           } else {
/*     */             
/* 116 */             try { BufferedImage bufferedimage; httpurlconnection = (HttpURLConnection)(new URL(ThreadDownloadImageData.this.imageUrl)).openConnection(Minecraft.getMinecraft().getProxy());
/* 117 */               httpurlconnection.setDoInput(true);
/* 118 */               httpurlconnection.setDoOutput(false);
/* 119 */               httpurlconnection.connect();
/*     */               
/* 121 */               if (httpurlconnection.getResponseCode() / 100 != 2) {
/* 122 */                 if (httpurlconnection.getErrorStream() != null) {
/* 123 */                   Config.readAll(httpurlconnection.getErrorStream());
/*     */                 }
/*     */ 
/*     */                 
/*     */                 return;
/*     */               } 
/*     */ 
/*     */               
/* 131 */               if (ThreadDownloadImageData.this.cacheFile != null) {
/* 132 */                 FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), ThreadDownloadImageData.this.cacheFile);
/* 133 */                 bufferedimage = ImageIO.read(ThreadDownloadImageData.this.cacheFile);
/*     */               } else {
/* 135 */                 bufferedimage = TextureUtil.readBufferedImage(httpurlconnection.getInputStream());
/*     */ 
/*     */               
/*     */               }
/*     */ 
/*     */               
/*     */                }
/*     */             
/* 143 */             catch (Exception exception)
/* 144 */             { ThreadDownloadImageData.logger.error("Couldn't download http texture: " + exception.getClass().getName() + ": " + exception.getMessage());
/*     */               return; }
/*     */             finally
/* 147 */             { if (httpurlconnection != null) {
/* 148 */                 httpurlconnection.disconnect();
/*     */               }
/*     */               
/* 151 */               ThreadDownloadImageData.this.loadingFinished(); }  if (httpurlconnection != null) httpurlconnection.disconnect();  ThreadDownloadImageData.this.loadingFinished();
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 156 */     this.imageThread.setDaemon(true);
/* 157 */     this.imageThread.start();
/*     */   }
/*     */   
/*     */   private boolean shouldPipeline() {
/* 161 */     if (!this.pipeline) {
/* 162 */       return false;
/*     */     }
/* 164 */     Proxy proxy = Minecraft.getMinecraft().getProxy();
/* 165 */     return (proxy.type() != Proxy.Type.DIRECT && proxy.type() != Proxy.Type.SOCKS) ? false : this.imageUrl.startsWith("http://");
/*     */   }
/*     */   
/*     */   private void loadPipelined() {
/*     */     try {
/*     */       BufferedImage bufferedimage;
/* 171 */       HttpRequest httprequest = HttpPipeline.makeRequest(this.imageUrl, Minecraft.getMinecraft().getProxy());
/* 172 */       HttpResponse httpresponse = HttpPipeline.executeRequest(httprequest);
/*     */       
/* 174 */       if (httpresponse.getStatus() / 100 != 2) {
/*     */         return;
/*     */       }
/*     */       
/* 178 */       byte[] abyte = httpresponse.getBody();
/* 179 */       ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte);
/*     */ 
/*     */       
/* 182 */       if (this.cacheFile != null) {
/* 183 */         FileUtils.copyInputStreamToFile(bytearrayinputstream, this.cacheFile);
/* 184 */         bufferedimage = ImageIO.read(this.cacheFile);
/*     */       } else {
/* 186 */         bufferedimage = TextureUtil.readBufferedImage(bytearrayinputstream);
/*     */       } 
/*     */       
/* 189 */       if (this.imageBuffer != null) {
/* 190 */         bufferedimage = this.imageBuffer.parseUserSkin(bufferedimage);
/*     */       }
/*     */       
/* 193 */       setBufferedImage(bufferedimage);
/* 194 */     } catch (Exception exception) {
/* 195 */       logger.error("Couldn't download http texture: " + exception.getClass().getName() + ": " + exception.getMessage());
/*     */       return;
/*     */     } finally {
/* 198 */       loadingFinished();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadingFinished() {
/* 203 */     this.imageFound = Boolean.valueOf((this.bufferedImage != null));
/*     */     
/* 205 */     if (this.imageBuffer instanceof CapeImageBuffer) {
/* 206 */       CapeImageBuffer capeimagebuffer = (CapeImageBuffer)this.imageBuffer;
/* 207 */       capeimagebuffer.cleanup();
/*     */     } 
/*     */   }
/*     */   
/*     */   public IImageBuffer getImageBuffer() {
/* 212 */     return this.imageBuffer;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\ThreadDownloadImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */