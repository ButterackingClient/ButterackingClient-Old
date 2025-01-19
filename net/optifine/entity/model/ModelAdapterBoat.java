/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBoat;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderBoat;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.item.EntityBoat;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterBoat extends ModelAdapter {
/*    */   public ModelAdapterBoat() {
/* 15 */     super(EntityBoat.class, "boat", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelBoat();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelBoat)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelBoat modelboat = (ModelBoat)model;
/* 27 */     return modelPart.equals("bottom") ? modelboat.boatSides[0] : (modelPart.equals("back") ? modelboat.boatSides[1] : (modelPart.equals("front") ? modelboat.boatSides[2] : (modelPart.equals("right") ? modelboat.boatSides[3] : (modelPart.equals("left") ? modelboat.boatSides[4] : null))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 32 */     return new String[] { "bottom", "back", "front", "right", "left" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 36 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 37 */     RenderBoat renderboat = new RenderBoat(rendermanager);
/*    */     
/* 39 */     if (!Reflector.RenderBoat_modelBoat.exists()) {
/* 40 */       Config.warn("Field not found: RenderBoat.modelBoat");
/* 41 */       return null;
/*    */     } 
/* 43 */     Reflector.setFieldValue(renderboat, Reflector.RenderBoat_modelBoat, modelBase);
/* 44 */     renderboat.shadowSize = shadowSize;
/* 45 */     return (IEntityRenderer)renderboat;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */