/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DynamicTexture
/*    */   extends AbstractTexture
/*    */ {
/*    */   private final int[] dynamicTextureData;
/*    */   private final int width;
/*    */   private final int height;
/*    */   
/*    */   public DynamicTexture(BufferedImage bufferedImage) {
/* 22 */     this(bufferedImage.getWidth(), bufferedImage.getHeight());
/* 23 */     bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.dynamicTextureData, 0, bufferedImage.getWidth());
/* 24 */     updateDynamicTexture();
/*    */   }
/*    */   
/*    */   public DynamicTexture(int textureWidth, int textureHeight) {
/* 28 */     this.width = textureWidth;
/* 29 */     this.height = textureHeight;
/* 30 */     this.dynamicTextureData = new int[textureWidth * textureHeight];
/* 31 */     TextureUtil.allocateTexture(getGlTextureId(), textureWidth, textureHeight);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadTexture(IResourceManager resourceManager) throws IOException {}
/*    */   
/*    */   public void updateDynamicTexture() {
/* 38 */     TextureUtil.uploadTexture(getGlTextureId(), this.dynamicTextureData, this.width, this.height);
/*    */   }
/*    */   
/*    */   public int[] getTextureData() {
/* 42 */     return this.dynamicTextureData;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\texture\DynamicTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */