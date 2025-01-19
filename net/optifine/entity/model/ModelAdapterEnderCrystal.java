/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderCrystal;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
/*    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterEnderCrystal extends ModelAdapter {
/*    */   public ModelAdapterEnderCrystal() {
/* 16 */     this("end_crystal");
/*    */   }
/*    */   
/*    */   protected ModelAdapterEnderCrystal(String name) {
/* 20 */     super(EntityEnderCrystal.class, name, 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 24 */     return (ModelBase)new ModelEnderCrystal(0.0F, true);
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 28 */     if (!(model instanceof ModelEnderCrystal)) {
/* 29 */       return null;
/*    */     }
/* 31 */     ModelEnderCrystal modelendercrystal = (ModelEnderCrystal)model;
/* 32 */     return modelPart.equals("cube") ? (ModelRenderer)Reflector.getFieldValue(modelendercrystal, Reflector.ModelEnderCrystal_ModelRenderers, 0) : (modelPart.equals("glass") ? (ModelRenderer)Reflector.getFieldValue(modelendercrystal, Reflector.ModelEnderCrystal_ModelRenderers, 1) : (modelPart.equals("base") ? (ModelRenderer)Reflector.getFieldValue(modelendercrystal, Reflector.ModelEnderCrystal_ModelRenderers, 2) : null));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 37 */     return new String[] { "cube", "glass", "base" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 41 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 42 */     Render render = (Render)rendermanager.getEntityRenderMap().get(EntityEnderCrystal.class);
/*    */     
/* 44 */     if (!(render instanceof RenderEnderCrystal)) {
/* 45 */       Config.warn("Not an instance of RenderEnderCrystal: " + render);
/* 46 */       return null;
/*    */     } 
/* 48 */     RenderEnderCrystal renderendercrystal = (RenderEnderCrystal)render;
/*    */     
/* 50 */     if (!Reflector.RenderEnderCrystal_modelEnderCrystal.exists()) {
/* 51 */       Config.warn("Field not found: RenderEnderCrystal.modelEnderCrystal");
/* 52 */       return null;
/*    */     } 
/* 54 */     Reflector.setFieldValue(renderendercrystal, Reflector.RenderEnderCrystal_modelEnderCrystal, modelBase);
/* 55 */     renderendercrystal.shadowSize = shadowSize;
/* 56 */     return (IEntityRenderer)renderendercrystal;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */