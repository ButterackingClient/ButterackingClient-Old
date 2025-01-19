/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.EmissiveTextures;
/*    */ import net.optifine.shaders.ShadersTex;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class SimpleTexture
/*    */   extends AbstractTexture {
/* 18 */   private static final Logger logger = LogManager.getLogger();
/*    */   protected final ResourceLocation textureLocation;
/*    */   public ResourceLocation locationEmissive;
/*    */   public boolean isEmissive;
/*    */   
/*    */   public SimpleTexture(ResourceLocation textureResourceLocation) {
/* 24 */     this.textureLocation = textureResourceLocation;
/*    */   }
/*    */   
/*    */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/* 28 */     deleteGlTexture();
/* 29 */     InputStream inputstream = null;
/*    */     
/*    */     try {
/* 32 */       IResource iresource = resourceManager.getResource(this.textureLocation);
/* 33 */       inputstream = iresource.getInputStream();
/* 34 */       BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
/* 35 */       boolean flag = false;
/* 36 */       boolean flag1 = false;
/*    */       
/* 38 */       if (iresource.hasMetadata()) {
/*    */         try {
/* 40 */           TextureMetadataSection texturemetadatasection = (TextureMetadataSection)iresource.getMetadata("texture");
/*    */           
/* 42 */           if (texturemetadatasection != null) {
/* 43 */             flag = texturemetadatasection.getTextureBlur();
/* 44 */             flag1 = texturemetadatasection.getTextureClamp();
/*    */           } 
/* 46 */         } catch (RuntimeException runtimeexception) {
/* 47 */           logger.warn("Failed reading metadata of: " + this.textureLocation, runtimeexception);
/*    */         } 
/*    */       }
/*    */       
/* 51 */       if (Config.isShaders()) {
/* 52 */         ShadersTex.loadSimpleTexture(getGlTextureId(), bufferedimage, flag, flag1, resourceManager, this.textureLocation, getMultiTexID());
/*    */       } else {
/* 54 */         TextureUtil.uploadTextureImageAllocate(getGlTextureId(), bufferedimage, flag, flag1);
/*    */       } 
/*    */       
/* 57 */       if (EmissiveTextures.isActive()) {
/* 58 */         EmissiveTextures.loadTexture(this.textureLocation, this);
/*    */       }
/*    */     } finally {
/* 61 */       if (inputstream != null)
/* 62 */         inputstream.close(); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\texture\SimpleTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */