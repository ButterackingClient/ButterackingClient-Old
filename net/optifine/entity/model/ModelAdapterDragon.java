/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelDragon;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderDragon;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterDragon extends ModelAdapter {
/*    */   public ModelAdapterDragon() {
/* 14 */     super(EntityDragon.class, "dragon", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 18 */     return (ModelBase)new ModelDragon(0.0F);
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 22 */     if (!(model instanceof ModelDragon)) {
/* 23 */       return null;
/*    */     }
/* 25 */     ModelDragon modeldragon = (ModelDragon)model;
/* 26 */     return modelPart.equals("head") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 0) : (modelPart.equals("spine") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 1) : (modelPart.equals("jaw") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 2) : (modelPart.equals("body") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 3) : (modelPart.equals("rear_leg") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 4) : (modelPart.equals("front_leg") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 5) : (modelPart.equals("rear_leg_tip") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 6) : (modelPart.equals("front_leg_tip") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 7) : (modelPart.equals("rear_foot") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 8) : (modelPart.equals("front_foot") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 9) : (modelPart.equals("wing") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 10) : (modelPart.equals("wing_tip") ? (ModelRenderer)Reflector.getFieldValue(modeldragon, Reflector.ModelDragon_ModelRenderers, 11) : null)))))))))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 31 */     return new String[] { "head", "spine", "jaw", "body", "rear_leg", "front_leg", "rear_leg_tip", "front_leg_tip", "rear_foot", "front_foot", "wing", "wing_tip" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 35 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 36 */     RenderDragon renderdragon = new RenderDragon(rendermanager);
/* 37 */     renderdragon.mainModel = modelBase;
/* 38 */     renderdragon.shadowSize = shadowSize;
/* 39 */     return (IEntityRenderer)renderdragon;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterDragon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */