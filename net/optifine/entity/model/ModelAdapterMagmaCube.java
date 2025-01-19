/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelMagmaCube;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderMagmaCube;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityMagmaCube;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterMagmaCube extends ModelAdapter {
/*    */   public ModelAdapterMagmaCube() {
/* 15 */     super(EntityMagmaCube.class, "magma_cube", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelMagmaCube();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelMagmaCube)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelMagmaCube modelmagmacube = (ModelMagmaCube)model;
/*    */     
/* 28 */     if (modelPart.equals("core")) {
/* 29 */       return (ModelRenderer)Reflector.getFieldValue(modelmagmacube, Reflector.ModelMagmaCube_core);
/*    */     }
/* 31 */     String s = "segment";
/*    */     
/* 33 */     if (modelPart.startsWith(s)) {
/* 34 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelmagmacube, Reflector.ModelMagmaCube_segments);
/*    */       
/* 36 */       if (amodelrenderer == null) {
/* 37 */         return null;
/*    */       }
/* 39 */       String s1 = modelPart.substring(s.length());
/* 40 */       int i = Config.parseInt(s1, -1);
/* 41 */       i--; return (
/* 42 */         i >= 0 && i < amodelrenderer.length) ? amodelrenderer[i] : null;
/*    */     } 
/*    */     
/* 45 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 52 */     return new String[] { "core", "segment1", "segment2", "segment3", "segment4", "segment5", "segment6", "segment7", "segment8" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 56 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 57 */     RenderMagmaCube rendermagmacube = new RenderMagmaCube(rendermanager);
/* 58 */     rendermagmacube.mainModel = modelBase;
/* 59 */     rendermagmacube.shadowSize = shadowSize;
/* 60 */     return (IEntityRenderer)rendermagmacube;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterMagmaCube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */