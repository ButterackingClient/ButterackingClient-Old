/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSnowMan;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSnowMan;
/*    */ import net.minecraft.entity.monster.EntitySnowman;
/*    */ 
/*    */ public class ModelAdapterSnowman extends ModelAdapter {
/*    */   public ModelAdapterSnowman() {
/* 13 */     super(EntitySnowman.class, "snow_golem", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 17 */     return (ModelBase)new ModelSnowMan();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 21 */     if (!(model instanceof ModelSnowMan)) {
/* 22 */       return null;
/*    */     }
/* 24 */     ModelSnowMan modelsnowman = (ModelSnowMan)model;
/* 25 */     return modelPart.equals("body") ? modelsnowman.body : (modelPart.equals("body_bottom") ? modelsnowman.bottomBody : (modelPart.equals("head") ? modelsnowman.head : (modelPart.equals("left_hand") ? modelsnowman.leftHand : (modelPart.equals("right_hand") ? modelsnowman.rightHand : null))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 30 */     return new String[] { "body", "body_bottom", "head", "right_hand", "left_hand" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 34 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 35 */     RenderSnowMan rendersnowman = new RenderSnowMan(rendermanager);
/* 36 */     rendersnowman.mainModel = modelBase;
/* 37 */     rendersnowman.shadowSize = shadowSize;
/* 38 */     return (IEntityRenderer)rendersnowman;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterSnowman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */