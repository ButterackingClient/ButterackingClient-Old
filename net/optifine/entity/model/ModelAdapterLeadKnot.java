/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelLeashKnot;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderLeashKnot;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterLeadKnot extends ModelAdapter {
/*    */   public ModelAdapterLeadKnot() {
/* 15 */     super(EntityLeashKnot.class, "lead_knot", 0.0F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelLeashKnot();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelLeashKnot)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelLeashKnot modelleashknot = (ModelLeashKnot)model;
/* 27 */     return modelPart.equals("knot") ? modelleashknot.field_110723_a : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 32 */     return new String[] { "knot" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 36 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 37 */     RenderLeashKnot renderleashknot = new RenderLeashKnot(rendermanager);
/*    */     
/* 39 */     if (!Reflector.RenderLeashKnot_leashKnotModel.exists()) {
/* 40 */       Config.warn("Field not found: RenderLeashKnot.leashKnotModel");
/* 41 */       return null;
/*    */     } 
/* 43 */     Reflector.setFieldValue(renderleashknot, Reflector.RenderLeashKnot_leashKnotModel, modelBase);
/* 44 */     renderleashknot.shadowSize = shadowSize;
/* 45 */     return (IEntityRenderer)renderleashknot;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterLeadKnot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */