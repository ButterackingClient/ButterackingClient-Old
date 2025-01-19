/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.shaders.ShadersTex;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class LayeredTexture
/*    */   extends AbstractTexture
/*    */ {
/* 19 */   private static final Logger logger = LogManager.getLogger();
/*    */   public final List<String> layeredTextureNames;
/*    */   private ResourceLocation textureLocation;
/*    */   
/*    */   public LayeredTexture(String... textureNames) {
/* 24 */     this.layeredTextureNames = Lists.newArrayList((Object[])textureNames);
/*    */     
/* 26 */     if (textureNames.length > 0 && textureNames[0] != null) {
/* 27 */       this.textureLocation = new ResourceLocation(textureNames[0]);
/*    */     }
/*    */   }
/*    */   
/*    */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/* 32 */     deleteGlTexture();
/* 33 */     BufferedImage bufferedimage = null;
/*    */     
/*    */     try {
/* 36 */       for (String s : this.layeredTextureNames) {
/* 37 */         if (s != null) {
/* 38 */           InputStream inputstream = resourceManager.getResource(new ResourceLocation(s)).getInputStream();
/* 39 */           BufferedImage bufferedimage1 = TextureUtil.readBufferedImage(inputstream);
/*    */           
/* 41 */           if (bufferedimage == null) {
/* 42 */             bufferedimage = new BufferedImage(bufferedimage1.getWidth(), bufferedimage1.getHeight(), 2);
/*    */           }
/*    */           
/* 45 */           bufferedimage.getGraphics().drawImage(bufferedimage1, 0, 0, null);
/*    */         } 
/*    */       } 
/* 48 */     } catch (IOException ioexception) {
/* 49 */       logger.error("Couldn't load layered image", ioexception);
/*    */       
/*    */       return;
/*    */     } 
/* 53 */     if (Config.isShaders()) {
/* 54 */       ShadersTex.loadSimpleTexture(getGlTextureId(), bufferedimage, false, false, resourceManager, this.textureLocation, getMultiTexID());
/*    */     } else {
/* 56 */       TextureUtil.uploadTextureImage(getGlTextureId(), bufferedimage);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\texture\LayeredTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */