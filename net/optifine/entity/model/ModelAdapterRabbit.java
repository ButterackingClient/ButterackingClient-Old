/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRabbit;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderRabbit;
/*    */ import net.minecraft.entity.passive.EntityRabbit;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterRabbit
/*    */   extends ModelAdapter {
/* 16 */   private static Map<String, Integer> mapPartFields = null;
/*    */   
/*    */   public ModelAdapterRabbit() {
/* 19 */     super(EntityRabbit.class, "rabbit", 0.3F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 23 */     return (ModelBase)new ModelRabbit();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelRabbit)) {
/* 28 */       return null;
/*    */     }
/* 30 */     ModelRabbit modelrabbit = (ModelRabbit)model;
/* 31 */     Map<String, Integer> map = getMapPartFields();
/*    */     
/* 33 */     if (map.containsKey(modelPart)) {
/* 34 */       int i = ((Integer)map.get(modelPart)).intValue();
/* 35 */       return (ModelRenderer)Reflector.getFieldValue(modelrabbit, Reflector.ModelRabbit_renderers, i);
/*    */     } 
/* 37 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 43 */     return new String[] { "left_foot", "right_foot", "left_thigh", "right_thigh", "body", "left_arm", "right_arm", "head", "right_ear", "left_ear", "tail", "nose" };
/*    */   }
/*    */   
/*    */   private static Map<String, Integer> getMapPartFields() {
/* 47 */     if (mapPartFields != null) {
/* 48 */       return mapPartFields;
/*    */     }
/* 50 */     mapPartFields = new HashMap<>();
/* 51 */     mapPartFields.put("left_foot", Integer.valueOf(0));
/* 52 */     mapPartFields.put("right_foot", Integer.valueOf(1));
/* 53 */     mapPartFields.put("left_thigh", Integer.valueOf(2));
/* 54 */     mapPartFields.put("right_thigh", Integer.valueOf(3));
/* 55 */     mapPartFields.put("body", Integer.valueOf(4));
/* 56 */     mapPartFields.put("left_arm", Integer.valueOf(5));
/* 57 */     mapPartFields.put("right_arm", Integer.valueOf(6));
/* 58 */     mapPartFields.put("head", Integer.valueOf(7));
/* 59 */     mapPartFields.put("right_ear", Integer.valueOf(8));
/* 60 */     mapPartFields.put("left_ear", Integer.valueOf(9));
/* 61 */     mapPartFields.put("tail", Integer.valueOf(10));
/* 62 */     mapPartFields.put("nose", Integer.valueOf(11));
/* 63 */     return mapPartFields;
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 68 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 69 */     RenderRabbit renderrabbit = new RenderRabbit(rendermanager, modelBase, shadowSize);
/* 70 */     return (IEntityRenderer)renderrabbit;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterRabbit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */