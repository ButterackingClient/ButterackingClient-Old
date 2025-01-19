/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBufferInt;
/*     */ 
/*     */ public class ImageBufferDownload
/*     */   implements IImageBuffer {
/*     */   private int[] imageData;
/*     */   private int imageWidth;
/*     */   private int imageHeight;
/*     */   
/*     */   public BufferedImage parseUserSkin(BufferedImage image) {
/*  14 */     if (image == null) {
/*  15 */       return null;
/*     */     }
/*  17 */     this.imageWidth = 64;
/*  18 */     this.imageHeight = 64;
/*  19 */     int i = image.getWidth();
/*  20 */     int j = image.getHeight();
/*     */     
/*     */     int k;
/*  23 */     for (k = 1; this.imageWidth < i || this.imageHeight < j; k *= 2) {
/*  24 */       this.imageWidth *= 2;
/*  25 */       this.imageHeight *= 2;
/*     */     } 
/*     */     
/*  28 */     BufferedImage bufferedimage = new BufferedImage(this.imageWidth, this.imageHeight, 2);
/*  29 */     Graphics graphics = bufferedimage.getGraphics();
/*  30 */     graphics.drawImage(image, 0, 0, null);
/*     */     
/*  32 */     if (image.getHeight() == 32 * k) {
/*  33 */       graphics.drawImage(bufferedimage, 24 * k, 48 * k, 20 * k, 52 * k, 4 * k, 16 * k, 8 * k, 20 * k, null);
/*  34 */       graphics.drawImage(bufferedimage, 28 * k, 48 * k, 24 * k, 52 * k, 8 * k, 16 * k, 12 * k, 20 * k, null);
/*  35 */       graphics.drawImage(bufferedimage, 20 * k, 52 * k, 16 * k, 64 * k, 8 * k, 20 * k, 12 * k, 32 * k, null);
/*  36 */       graphics.drawImage(bufferedimage, 24 * k, 52 * k, 20 * k, 64 * k, 4 * k, 20 * k, 8 * k, 32 * k, null);
/*  37 */       graphics.drawImage(bufferedimage, 28 * k, 52 * k, 24 * k, 64 * k, 0 * k, 20 * k, 4 * k, 32 * k, null);
/*  38 */       graphics.drawImage(bufferedimage, 32 * k, 52 * k, 28 * k, 64 * k, 12 * k, 20 * k, 16 * k, 32 * k, null);
/*  39 */       graphics.drawImage(bufferedimage, 40 * k, 48 * k, 36 * k, 52 * k, 44 * k, 16 * k, 48 * k, 20 * k, null);
/*  40 */       graphics.drawImage(bufferedimage, 44 * k, 48 * k, 40 * k, 52 * k, 48 * k, 16 * k, 52 * k, 20 * k, null);
/*  41 */       graphics.drawImage(bufferedimage, 36 * k, 52 * k, 32 * k, 64 * k, 48 * k, 20 * k, 52 * k, 32 * k, null);
/*  42 */       graphics.drawImage(bufferedimage, 40 * k, 52 * k, 36 * k, 64 * k, 44 * k, 20 * k, 48 * k, 32 * k, null);
/*  43 */       graphics.drawImage(bufferedimage, 44 * k, 52 * k, 40 * k, 64 * k, 40 * k, 20 * k, 44 * k, 32 * k, null);
/*  44 */       graphics.drawImage(bufferedimage, 48 * k, 52 * k, 44 * k, 64 * k, 52 * k, 20 * k, 56 * k, 32 * k, null);
/*     */     } 
/*     */     
/*  47 */     graphics.dispose();
/*  48 */     this.imageData = ((DataBufferInt)bufferedimage.getRaster().getDataBuffer()).getData();
/*  49 */     setAreaOpaque(0 * k, 0 * k, 32 * k, 16 * k);
/*  50 */     setAreaTransparent(32 * k, 0 * k, 64 * k, 32 * k);
/*  51 */     setAreaOpaque(0 * k, 16 * k, 64 * k, 32 * k);
/*  52 */     setAreaTransparent(0 * k, 32 * k, 16 * k, 48 * k);
/*  53 */     setAreaTransparent(16 * k, 32 * k, 40 * k, 48 * k);
/*  54 */     setAreaTransparent(40 * k, 32 * k, 56 * k, 48 * k);
/*  55 */     setAreaTransparent(0 * k, 48 * k, 16 * k, 64 * k);
/*  56 */     setAreaOpaque(16 * k, 48 * k, 48 * k, 64 * k);
/*  57 */     setAreaTransparent(48 * k, 48 * k, 64 * k, 64 * k);
/*  58 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skinAvailable() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setAreaTransparent(int p_78434_1_, int p_78434_2_, int p_78434_3_, int p_78434_4_) {
/*  71 */     if (!hasTransparency(p_78434_1_, p_78434_2_, p_78434_3_, p_78434_4_)) {
/*  72 */       for (int i = p_78434_1_; i < p_78434_3_; i++) {
/*  73 */         for (int j = p_78434_2_; j < p_78434_4_; j++) {
/*  74 */           this.imageData[i + j * this.imageWidth] = this.imageData[i + j * this.imageWidth] & 0xFFFFFF;
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setAreaOpaque(int p_78433_1_, int p_78433_2_, int p_78433_3_, int p_78433_4_) {
/*  84 */     for (int i = p_78433_1_; i < p_78433_3_; i++) {
/*  85 */       for (int j = p_78433_2_; j < p_78433_4_; j++) {
/*  86 */         this.imageData[i + j * this.imageWidth] = this.imageData[i + j * this.imageWidth] | 0xFF000000;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasTransparency(int p_78435_1_, int p_78435_2_, int p_78435_3_, int p_78435_4_) {
/*  95 */     for (int i = p_78435_1_; i < p_78435_3_; i++) {
/*  96 */       for (int j = p_78435_2_; j < p_78435_4_; j++) {
/*  97 */         int k = this.imageData[i + j * this.imageWidth];
/*     */         
/*  99 */         if ((k >> 24 & 0xFF) < 128) {
/* 100 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\ImageBufferDownload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */