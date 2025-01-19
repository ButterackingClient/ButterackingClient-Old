/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSkeletonHead;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntitySkull;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterHeadSkeleton extends ModelAdapter {
/*    */   public ModelAdapterHeadSkeleton() {
/* 15 */     super(TileEntitySkull.class, "head_skeleton", 0.0F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelSkeletonHead(0, 0, 64, 32);
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelSkeletonHead)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelSkeletonHead modelskeletonhead = (ModelSkeletonHead)model;
/* 27 */     return modelPart.equals("head") ? modelskeletonhead.skeletonHead : null;
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


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterHeadSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */