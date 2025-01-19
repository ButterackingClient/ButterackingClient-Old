/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBanner;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntityBanner;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterBanner extends ModelAdapter {
/*    */   public ModelAdapterBanner() {
/* 15 */     super(TileEntityBanner.class, "banner", 0.0F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelBanner();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelBanner)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelBanner modelbanner = (ModelBanner)model;
/* 27 */     return modelPart.equals("slate") ? modelbanner.bannerSlate : (modelPart.equals("stand") ? modelbanner.bannerStand : (modelPart.equals("top") ? modelbanner.bannerTop : null));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 32 */     return new String[] { "slate", "stand", "top" };
/*    */   }
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntityBannerRenderer tileEntityBannerRenderer;
/* 36 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 37 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntityBanner.class);
/*    */     
/* 39 */     if (!(tileentityspecialrenderer instanceof TileEntityBannerRenderer)) {
/* 40 */       return null;
/*    */     }
/* 42 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/* 43 */       tileEntityBannerRenderer = new TileEntityBannerRenderer();
/* 44 */       tileEntityBannerRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 47 */     if (!Reflector.TileEntityBannerRenderer_bannerModel.exists()) {
/* 48 */       Config.warn("Field not found: TileEntityBannerRenderer.bannerModel");
/* 49 */       return null;
/*    */     } 
/* 51 */     Reflector.setFieldValue(tileEntityBannerRenderer, Reflector.TileEntityBannerRenderer_bannerModel, modelBase);
/* 52 */     return (IEntityRenderer)tileEntityBannerRenderer;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterBanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */