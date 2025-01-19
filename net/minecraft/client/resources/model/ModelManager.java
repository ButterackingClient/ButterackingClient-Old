/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import net.minecraft.client.renderer.BlockModelShapes;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*    */ import net.minecraft.util.IRegistry;
/*    */ 
/*    */ public class ModelManager implements IResourceManagerReloadListener {
/*    */   private IRegistry<ModelResourceLocation, IBakedModel> modelRegistry;
/*    */   private final TextureMap texMap;
/*    */   private final BlockModelShapes modelProvider;
/*    */   private IBakedModel defaultModel;
/*    */   
/*    */   public ModelManager(TextureMap textures) {
/* 16 */     this.texMap = textures;
/* 17 */     this.modelProvider = new BlockModelShapes(this);
/*    */   }
/*    */   
/*    */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 21 */     ModelBakery modelbakery = new ModelBakery(resourceManager, this.texMap, this.modelProvider);
/* 22 */     this.modelRegistry = modelbakery.setupModelRegistry();
/* 23 */     this.defaultModel = (IBakedModel)this.modelRegistry.getObject(ModelBakery.MODEL_MISSING);
/* 24 */     this.modelProvider.reloadModels();
/*    */   }
/*    */   
/*    */   public IBakedModel getModel(ModelResourceLocation modelLocation) {
/* 28 */     if (modelLocation == null) {
/* 29 */       return this.defaultModel;
/*    */     }
/* 31 */     IBakedModel ibakedmodel = (IBakedModel)this.modelRegistry.getObject(modelLocation);
/* 32 */     return (ibakedmodel == null) ? this.defaultModel : ibakedmodel;
/*    */   }
/*    */ 
/*    */   
/*    */   public IBakedModel getMissingModel() {
/* 37 */     return this.defaultModel;
/*    */   }
/*    */   
/*    */   public TextureMap getTextureMap() {
/* 41 */     return this.texMap;
/*    */   }
/*    */   
/*    */   public BlockModelShapes getBlockModelShapes() {
/* 45 */     return this.modelProvider;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\resources\model\ModelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */