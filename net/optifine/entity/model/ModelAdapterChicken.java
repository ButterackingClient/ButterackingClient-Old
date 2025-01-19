/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelChicken;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderChicken;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.passive.EntityChicken;
/*    */ 
/*    */ public class ModelAdapterChicken extends ModelAdapter {
/*    */   public ModelAdapterChicken() {
/* 13 */     super(EntityChicken.class, "chicken", 0.3F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 17 */     return (ModelBase)new ModelChicken();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 21 */     if (!(model instanceof ModelChicken)) {
/* 22 */       return null;
/*    */     }
/* 24 */     ModelChicken modelchicken = (ModelChicken)model;
/* 25 */     return modelPart.equals("head") ? modelchicken.head : (modelPart.equals("body") ? modelchicken.body : (modelPart.equals("right_leg") ? modelchicken.rightLeg : (modelPart.equals("left_leg") ? modelchicken.leftLeg : (modelPart.equals("right_wing") ? modelchicken.rightWing : (modelPart.equals("left_wing") ? modelchicken.leftWing : (modelPart.equals("bill") ? modelchicken.bill : (modelPart.equals("chin") ? modelchicken.chin : null)))))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 30 */     return new String[] { "head", "body", "right_leg", "left_leg", "right_wing", "left_wing", "bill", "chin" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 34 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 35 */     RenderChicken renderchicken = new RenderChicken(rendermanager, modelBase, shadowSize);
/* 36 */     return (IEntityRenderer)renderchicken;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterChicken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */