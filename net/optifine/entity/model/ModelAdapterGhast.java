/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelGhast;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderGhast;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityGhast;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterGhast extends ModelAdapter {
/*    */   public ModelAdapterGhast() {
/* 15 */     super(EntityGhast.class, "ghast", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelGhast();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelGhast)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelGhast modelghast = (ModelGhast)model;
/*    */     
/* 28 */     if (modelPart.equals("body")) {
/* 29 */       return (ModelRenderer)Reflector.getFieldValue(modelghast, Reflector.ModelGhast_body);
/*    */     }
/* 31 */     String s = "tentacle";
/*    */     
/* 33 */     if (modelPart.startsWith(s)) {
/* 34 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelghast, Reflector.ModelGhast_tentacles);
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
/* 52 */     return new String[] { "body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8", "tentacle9" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 56 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 57 */     RenderGhast renderghast = new RenderGhast(rendermanager);
/* 58 */     renderghast.mainModel = modelBase;
/* 59 */     renderghast.shadowSize = shadowSize;
/* 60 */     return (IEntityRenderer)renderghast;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterGhast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */