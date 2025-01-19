/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBlaze;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderBlaze;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityBlaze;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterBlaze extends ModelAdapter {
/*    */   public ModelAdapterBlaze() {
/* 15 */     super(EntityBlaze.class, "blaze", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelBlaze();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelBlaze)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelBlaze modelblaze = (ModelBlaze)model;
/*    */     
/* 28 */     if (modelPart.equals("head")) {
/* 29 */       return (ModelRenderer)Reflector.getFieldValue(modelblaze, Reflector.ModelBlaze_blazeHead);
/*    */     }
/* 31 */     String s = "stick";
/*    */     
/* 33 */     if (modelPart.startsWith(s)) {
/* 34 */       ModelRenderer[] amodelrenderer = (ModelRenderer[])Reflector.getFieldValue(modelblaze, Reflector.ModelBlaze_blazeSticks);
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
/* 52 */     return new String[] { "head", "stick1", "stick2", "stick3", "stick4", "stick5", "stick6", "stick7", "stick8", "stick9", "stick10", "stick11", "stick12" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 56 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 57 */     RenderBlaze renderblaze = new RenderBlaze(rendermanager);
/* 58 */     renderblaze.mainModel = modelBase;
/* 59 */     renderblaze.shadowSize = shadowSize;
/* 60 */     return (IEntityRenderer)renderblaze;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterBlaze.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */