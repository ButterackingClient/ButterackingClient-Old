/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBat;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderBat;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.passive.EntityBat;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterBat extends ModelAdapter {
/*    */   public ModelAdapterBat() {
/* 14 */     super(EntityBat.class, "bat", 0.25F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 18 */     return (ModelBase)new ModelBat();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 22 */     if (!(model instanceof ModelBat)) {
/* 23 */       return null;
/*    */     }
/* 25 */     ModelBat modelbat = (ModelBat)model;
/* 26 */     return modelPart.equals("head") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 0) : (modelPart.equals("body") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 1) : (modelPart.equals("right_wing") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 2) : (modelPart.equals("left_wing") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 3) : (modelPart.equals("outer_right_wing") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 4) : (modelPart.equals("outer_left_wing") ? (ModelRenderer)Reflector.getFieldValue(modelbat, Reflector.ModelBat_ModelRenderers, 5) : null)))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 31 */     return new String[] { "head", "body", "right_wing", "left_wing", "outer_right_wing", "outer_left_wing" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 35 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 36 */     RenderBat renderbat = new RenderBat(rendermanager);
/* 37 */     renderbat.mainModel = modelBase;
/* 38 */     renderbat.shadowSize = shadowSize;
/* 39 */     return (IEntityRenderer)renderbat;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterBat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */