/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelOcelot;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderOcelot;
/*    */ import net.minecraft.entity.passive.EntityOcelot;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterOcelot
/*    */   extends ModelAdapter {
/* 16 */   private static Map<String, Integer> mapPartFields = null;
/*    */   
/*    */   public ModelAdapterOcelot() {
/* 19 */     super(EntityOcelot.class, "ocelot", 0.4F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 23 */     return (ModelBase)new ModelOcelot();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelOcelot)) {
/* 28 */       return null;
/*    */     }
/* 30 */     ModelOcelot modelocelot = (ModelOcelot)model;
/* 31 */     Map<String, Integer> map = getMapPartFields();
/*    */     
/* 33 */     if (map.containsKey(modelPart)) {
/* 34 */       int i = ((Integer)map.get(modelPart)).intValue();
/* 35 */       return (ModelRenderer)Reflector.getFieldValue(modelocelot, Reflector.ModelOcelot_ModelRenderers, i);
/*    */     } 
/* 37 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 43 */     return new String[] { "back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "tail", "tail2", "head", "body" };
/*    */   }
/*    */   
/*    */   private static Map<String, Integer> getMapPartFields() {
/* 47 */     if (mapPartFields != null) {
/* 48 */       return mapPartFields;
/*    */     }
/* 50 */     mapPartFields = new HashMap<>();
/* 51 */     mapPartFields.put("back_left_leg", Integer.valueOf(0));
/* 52 */     mapPartFields.put("back_right_leg", Integer.valueOf(1));
/* 53 */     mapPartFields.put("front_left_leg", Integer.valueOf(2));
/* 54 */     mapPartFields.put("front_right_leg", Integer.valueOf(3));
/* 55 */     mapPartFields.put("tail", Integer.valueOf(4));
/* 56 */     mapPartFields.put("tail2", Integer.valueOf(5));
/* 57 */     mapPartFields.put("head", Integer.valueOf(6));
/* 58 */     mapPartFields.put("body", Integer.valueOf(7));
/* 59 */     return mapPartFields;
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 64 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 65 */     RenderOcelot renderocelot = new RenderOcelot(rendermanager, modelBase, shadowSize);
/* 66 */     return (IEntityRenderer)renderocelot;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterOcelot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */