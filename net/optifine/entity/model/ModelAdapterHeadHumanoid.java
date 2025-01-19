/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelHumanoidHead;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntitySkull;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterHeadHumanoid extends ModelAdapter {
/*    */   public ModelAdapterHeadHumanoid() {
/* 15 */     super(TileEntitySkull.class, "head_humanoid", 0.0F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelHumanoidHead();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelHumanoidHead)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelHumanoidHead modelhumanoidhead = (ModelHumanoidHead)model;
/* 27 */     return modelPart.equals("head") ? modelhumanoidhead.skeletonHead : (modelPart.equals("head2") ? (!Reflector.ModelHumanoidHead_head.exists() ? null : (ModelRenderer)Reflector.getFieldValue(modelhumanoidhead, Reflector.ModelHumanoidHead_head)) : null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 32 */     return new String[] { "head" };
/*    */   }
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     TileEntitySkullRenderer tileEntitySkullRenderer;
/* 36 */     TileEntityRendererDispatcher tileentityrendererdispatcher = TileEntityRendererDispatcher.instance;
/* 37 */     TileEntitySpecialRenderer tileentityspecialrenderer = tileentityrendererdispatcher.getSpecialRendererByClass(TileEntitySkull.class);
/*    */     
/* 39 */     if (!(tileentityspecialrenderer instanceof TileEntitySkullRenderer)) {
/* 40 */       return null;
/*    */     }
/* 42 */     if (tileentityspecialrenderer.getEntityClass() == null) {
/* 43 */       tileEntitySkullRenderer = new TileEntitySkullRenderer();
/* 44 */       tileEntitySkullRenderer.setRendererDispatcher(tileentityrendererdispatcher);
/*    */     } 
/*    */     
/* 47 */     if (!Reflector.TileEntitySkullRenderer_humanoidHead.exists()) {
/* 48 */       Config.warn("Field not found: TileEntitySkullRenderer.humanoidHead");
/* 49 */       return null;
/*    */     } 
/* 51 */     Reflector.setFieldValue(tileEntitySkullRenderer, Reflector.TileEntitySkullRenderer_humanoidHead, modelBase);
/* 52 */     return (IEntityRenderer)tileEntitySkullRenderer;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterHeadHumanoid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */