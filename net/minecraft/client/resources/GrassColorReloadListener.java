/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class GrassColorReloadListener
/*    */   implements IResourceManagerReloadListener {
/* 10 */   private static final ResourceLocation LOC_GRASS_PNG = new ResourceLocation("textures/colormap/grass.png");
/*    */   
/*    */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*    */     try {
/* 14 */       ColorizerGrass.setGrassBiomeColorizer(TextureUtil.readImageData(resourceManager, LOC_GRASS_PNG));
/* 15 */     } catch (IOException iOException) {}
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\GrassColorReloadListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */