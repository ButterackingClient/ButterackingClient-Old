/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSign;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntitySign;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterSign extends ModelAdapter {
/*    */   public ModelAdapterSign() {
/* 15 */     super(TileEntitySign.class, "sign", 0.0F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelSign();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelSign)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelSign modelsign = (ModelSign)model;
/* 27 */     return modelPart.equals("board") ? modelsign.signBoard : (modelPart.equals("stick") ? modelsign.signStick : null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 32 */     return new String[] { "board", "stick" };
/*    */   }
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntitySignRenderer tileEntitySignRenderer;
/* 36 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 37 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntitySign.class);
/*    */     
/* 39 */     if (!(tileentityspecialrenderer instanceof TileEntitySignRenderer)) {
/* 40 */       return null;
/*    */     }
/* 42 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/* 43 */       tileEntitySignRenderer = new TileEntitySignRenderer();
/* 44 */       tileEntitySignRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 47 */     if (!Reflector.TileEntitySignRenderer_model.exists()) {
/* 48 */       Config.warn("Field not found: TileEntitySignRenderer.model");
/* 49 */       return null;
/*    */     } 
/* 51 */     Reflector.setFieldValue(tileEntitySignRenderer, Reflector.TileEntitySignRenderer_model, modelBase);
/* 52 */     return (IEntityRenderer)tileEntitySignRenderer;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */