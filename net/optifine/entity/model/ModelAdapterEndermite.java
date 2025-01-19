/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderMite;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderEndermite;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityEndermite;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterEndermite extends ModelAdapter {
/*    */   public ModelAdapterEndermite() {
/* 15 */     super(EntityEndermite.class, "endermite", 0.3F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelEnderMite();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelEnderMite)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelEnderMite modelendermite = (ModelEnderMite)model;
/* 27 */     String s = "body";
/*    */     
/* 29 */     if (modelPart.startsWith(s)) {
/* 30 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelendermite, Reflector.ModelEnderMite_bodyParts);
/*    */       
/* 32 */       if (amodelrenderer == null) {
/* 33 */         return null;
/*    */       }
/* 35 */       String s1 = modelPart.substring(s.length());
/* 36 */       int i = Config.parseInt(s1, -1);
/* 37 */       i--; return (
/* 38 */         i >= 0 && i < amodelrenderer.length) ? amodelrenderer[i] : null;
/*    */     } 
/*    */     
/* 41 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 47 */     return new String[] { "body1", "body2", "body3", "body4" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 51 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 52 */     RenderEndermite renderendermite = new RenderEndermite(rendermanager);
/* 53 */     renderendermite.mainModel = modelBase;
/* 54 */     renderendermite.shadowSize = shadowSize;
/* 55 */     return (IEntityRenderer)renderendermite;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterEndermite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */