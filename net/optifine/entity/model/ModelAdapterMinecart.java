/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelMinecart;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderMinecart;
/*    */ import net.minecraft.entity.item.EntityMinecart;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterMinecart extends ModelAdapter {
/*    */   public ModelAdapterMinecart() {
/* 15 */     super(EntityMinecart.class, "minecart", 0.5F);
/*    */   }
/*    */   
/*    */   protected ModelAdapterMinecart(Class entityClass, String name, float shadow) {
/* 19 */     super(entityClass, name, shadow);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 23 */     return (ModelBase)new ModelMinecart();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 27 */     if (!(model instanceof ModelMinecart)) {
/* 28 */       return null;
/*    */     }
/* 30 */     ModelMinecart modelminecart = (ModelMinecart)model;
/* 31 */     return modelPart.equals("bottom") ? modelminecart.sideModels[0] : (modelPart.equals("back") ? modelminecart.sideModels[1] : (modelPart.equals("front") ? modelminecart.sideModels[2] : (modelPart.equals("right") ? modelminecart.sideModels[3] : (modelPart.equals("left") ? modelminecart.sideModels[4] : (modelPart.equals("dirt") ? modelminecart.sideModels[5] : null)))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 36 */     return new String[] { "bottom", "back", "front", "right", "left", "dirt" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 40 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 41 */     RenderMinecart renderminecart = new RenderMinecart(rendermanager);
/*    */     
/* 43 */     if (!Reflector.RenderMinecart_modelMinecart.exists()) {
/* 44 */       Config.warn("Field not found: RenderMinecart.modelMinecart");
/* 45 */       return null;
/*    */     } 
/* 47 */     Reflector.setFieldValue(renderminecart, Reflector.RenderMinecart_modelMinecart, modelBase);
/* 48 */     renderminecart.shadowSize = shadowSize;
/* 49 */     return (IEntityRenderer)renderminecart;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */