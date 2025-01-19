/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelCreeper;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderCreeper;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityCreeper;
/*    */ 
/*    */ public class ModelAdapterCreeper extends ModelAdapter {
/*    */   public ModelAdapterCreeper() {
/* 13 */     super(EntityCreeper.class, "creeper", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 17 */     return (ModelBase)new ModelCreeper();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 21 */     if (!(model instanceof ModelCreeper)) {
/* 22 */       return null;
/*    */     }
/* 24 */     ModelCreeper modelcreeper = (ModelCreeper)model;
/* 25 */     return modelPart.equals("head") ? modelcreeper.head : (modelPart.equals("armor") ? modelcreeper.creeperArmor : (modelPart.equals("body") ? modelcreeper.body : (modelPart.equals("leg1") ? modelcreeper.leg1 : (modelPart.equals("leg2") ? modelcreeper.leg2 : (modelPart.equals("leg3") ? modelcreeper.leg3 : (modelPart.equals("leg4") ? modelcreeper.leg4 : null))))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 30 */     return new String[] { "head", "armor", "body", "leg1", "leg2", "leg3", "leg4" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 34 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 35 */     RenderCreeper rendercreeper = new RenderCreeper(rendermanager);
/* 36 */     rendercreeper.mainModel = modelBase;
/* 37 */     rendercreeper.shadowSize = shadowSize;
/* 38 */     return (IEntityRenderer)rendercreeper;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterCreeper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */