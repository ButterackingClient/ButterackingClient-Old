/*    */ package net.optifine.player;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.util.regex.Pattern;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.IImageBuffer;
/*    */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class CapeUtils
/*    */ {
/* 19 */   private static final Pattern PATTERN_USERNAME = Pattern.compile("[a-zA-Z0-9_]+");
/*    */   
/*    */   public static void downloadCape(AbstractClientPlayer player) {
/* 22 */     String s = player.getNameClear();
/*    */     
/* 24 */     if (s != null && !s.isEmpty() && !s.contains("\000") && PATTERN_USERNAME.matcher(s).matches()) {
/* 25 */       String s1 = "http://s.optifine.net/capes/" + s + ".png";
/* 26 */       ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s);
/* 27 */       TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 28 */       ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
/*    */       
/* 30 */       if (itextureobject != null && itextureobject instanceof ThreadDownloadImageData) {
/* 31 */         ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)itextureobject;
/*    */         
/* 33 */         if (threaddownloadimagedata.imageFound != null) {
/* 34 */           if (threaddownloadimagedata.imageFound.booleanValue()) {
/* 35 */             player.setLocationOfCape(resourcelocation);
/*    */             
/* 37 */             if (threaddownloadimagedata.getImageBuffer() instanceof CapeImageBuffer) {
/* 38 */               CapeImageBuffer capeimagebuffer1 = (CapeImageBuffer)threaddownloadimagedata.getImageBuffer();
/* 39 */               player.setElytraOfCape(capeimagebuffer1.isElytraOfCape());
/*    */             } 
/*    */           } 
/*    */           
/*    */           return;
/*    */         } 
/*    */       } 
/*    */       
/* 47 */       CapeImageBuffer capeimagebuffer = new CapeImageBuffer(player, resourcelocation);
/* 48 */       ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData(null, s1, null, (IImageBuffer)capeimagebuffer);
/* 49 */       threaddownloadimagedata1.pipeline = true;
/* 50 */       texturemanager.loadTexture(resourcelocation, (ITextureObject)threaddownloadimagedata1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static BufferedImage parseCape(BufferedImage img) {
/* 55 */     int i = 64;
/* 56 */     int j = 32;
/* 57 */     int k = img.getWidth();
/*    */     
/* 59 */     for (int l = img.getHeight(); i < k || j < l; j *= 2) {
/* 60 */       i *= 2;
/*    */     }
/*    */     
/* 63 */     BufferedImage bufferedimage = new BufferedImage(i, j, 2);
/* 64 */     Graphics graphics = bufferedimage.getGraphics();
/* 65 */     graphics.drawImage(img, 0, 0, null);
/* 66 */     graphics.dispose();
/* 67 */     return bufferedimage;
/*    */   }
/*    */   
/*    */   public static boolean isElytraCape(BufferedImage imageRaw, BufferedImage imageFixed) {
/* 71 */     return (imageRaw.getWidth() > imageFixed.getHeight());
/*    */   }
/*    */   
/*    */   public static void reloadCape(AbstractClientPlayer player) {
/* 75 */     String s = player.getNameClear();
/* 76 */     ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s);
/* 77 */     TextureManager texturemanager = Config.getTextureManager();
/* 78 */     ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
/*    */     
/* 80 */     if (itextureobject instanceof SimpleTexture) {
/* 81 */       SimpleTexture simpletexture = (SimpleTexture)itextureobject;
/* 82 */       simpletexture.deleteGlTexture();
/* 83 */       texturemanager.deleteTexture(resourcelocation);
/*    */     } 
/*    */     
/* 86 */     player.setLocationOfCape(null);
/* 87 */     player.setElytraOfCape(false);
/* 88 */     downloadCape(player);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\player\CapeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */