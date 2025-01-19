/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSquid;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSquid;
/*    */ import net.minecraft.entity.passive.EntitySquid;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterSquid extends ModelAdapter {
/*    */   public ModelAdapterSquid() {
/* 15 */     super(EntitySquid.class, "squid", 0.7F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelSquid();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelSquid)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelSquid modelsquid = (ModelSquid)model;
/*    */     
/* 28 */     if (modelPart.equals("body")) {
/* 29 */       return (ModelRenderer)Reflector.getFieldValue(modelsquid, Reflector.ModelSquid_body);
/*    */     }
/* 31 */     String s = "tentacle";
/*    */     
/* 33 */     if (modelPart.startsWith(s)) {
/* 34 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelsquid, Reflector.ModelSquid_tentacles);
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
/* 52 */     return new String[] { "body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 56 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 57 */     RenderSquid rendersquid = new RenderSquid(rendermanager, modelBase, shadowSize);
/* 58 */     return (IEntityRenderer)rendersquid;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterSquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */