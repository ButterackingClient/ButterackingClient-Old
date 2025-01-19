/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelWolf;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderWolf;
/*    */ import net.minecraft.entity.passive.EntityWolf;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterWolf extends ModelAdapter {
/*    */   public ModelAdapterWolf() {
/* 14 */     super(EntityWolf.class, "wolf", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 18 */     return (ModelBase)new ModelWolf();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 22 */     if (!(model instanceof ModelWolf)) {
/* 23 */       return null;
/*    */     }
/* 25 */     ModelWolf modelwolf = (ModelWolf)model;
/* 26 */     return modelPart.equals("head") ? modelwolf.wolfHeadMain : (modelPart.equals("body") ? modelwolf.wolfBody : (modelPart.equals("leg1") ? modelwolf.wolfLeg1 : (modelPart.equals("leg2") ? modelwolf.wolfLeg2 : (modelPart.equals("leg3") ? modelwolf.wolfLeg3 : (modelPart.equals("leg4") ? modelwolf.wolfLeg4 : (modelPart.equals("tail") ? (ModelRenderer)Reflector.getFieldValue(modelwolf, Reflector.ModelWolf_tail) : (modelPart.equals("mane") ? (ModelRenderer)Reflector.getFieldValue(modelwolf, Reflector.ModelWolf_mane) : null)))))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 31 */     return new String[] { "head", "body", "leg1", "leg2", "leg3", "leg4", "tail", "mane" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 35 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 36 */     RenderWolf renderwolf = new RenderWolf(rendermanager, modelBase, shadowSize);
/* 37 */     return (IEntityRenderer)renderwolf;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterWolf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */