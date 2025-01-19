/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSilverfish;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSilverfish;
/*    */ import net.minecraft.entity.monster.EntitySilverfish;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterSilverfish extends ModelAdapter {
/*    */   public ModelAdapterSilverfish() {
/* 15 */     super(EntitySilverfish.class, "silverfish", 0.3F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelSilverfish();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelSilverfish)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelSilverfish modelsilverfish = (ModelSilverfish)model;
/* 27 */     String s = "body";
/*    */     
/* 29 */     if (modelPart.startsWith(s)) {
/* 30 */       ModelRenderer[] amodelrenderer1 = (ModelRenderer[])Reflector.getFieldValue(modelsilverfish, Reflector.ModelSilverfish_bodyParts);
/*    */       
/* 32 */       if (amodelrenderer1 == null) {
/* 33 */         return null;
/*    */       }
/* 35 */       String s3 = modelPart.substring(s.length());
/* 36 */       int j = Config.parseInt(s3, -1);
/* 37 */       j--; return (
/* 38 */         j >= 0 && j < amodelrenderer1.length) ? amodelrenderer1[j] : null;
/*    */     } 
/*    */     
/* 41 */     String s1 = "wing";
/*    */     
/* 43 */     if (modelPart.startsWith(s1)) {
/* 44 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelsilverfish, Reflector.ModelSilverfish_wingParts);
/*    */       
/* 46 */       if (amodelrenderer == null) {
/* 47 */         return null;
/*    */       }
/* 49 */       String s2 = modelPart.substring(s1.length());
/* 50 */       int i = Config.parseInt(s2, -1);
/* 51 */       i--; return (
/* 52 */         i >= 0 && i < amodelrenderer.length) ? amodelrenderer[i] : null;
/*    */     } 
/*    */     
/* 55 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 62 */     return new String[] { "body1", "body2", "body3", "body4", "body5", "body6", "body7", "wing1", "wing2", "wing3" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 66 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 67 */     RenderSilverfish rendersilverfish = new RenderSilverfish(rendermanager);
/* 68 */     rendersilverfish.mainModel = modelBase;
/* 69 */     rendersilverfish.shadowSize = shadowSize;
/* 70 */     return (IEntityRenderer)rendersilverfish;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterSilverfish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */