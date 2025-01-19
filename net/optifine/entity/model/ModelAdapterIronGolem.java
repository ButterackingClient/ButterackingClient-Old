/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelIronGolem;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderIronGolem;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityIronGolem;
/*    */ 
/*    */ public class ModelAdapterIronGolem extends ModelAdapter {
/*    */   public ModelAdapterIronGolem() {
/* 13 */     super(EntityIronGolem.class, "iron_golem", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 17 */     return (ModelBase)new ModelIronGolem();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 21 */     if (!(model instanceof ModelIronGolem)) {
/* 22 */       return null;
/*    */     }
/* 24 */     ModelIronGolem modelirongolem = (ModelIronGolem)model;
/* 25 */     return modelPart.equals("head") ? modelirongolem.ironGolemHead : (modelPart.equals("body") ? modelirongolem.ironGolemBody : (modelPart.equals("left_arm") ? modelirongolem.ironGolemLeftArm : (modelPart.equals("right_arm") ? modelirongolem.ironGolemRightArm : (modelPart.equals("left_leg") ? modelirongolem.ironGolemLeftLeg : (modelPart.equals("right_leg") ? modelirongolem.ironGolemRightLeg : null)))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 30 */     return new String[] { "head", "body", "right_arm", "left_arm", "left_leg", "right_leg" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 34 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 35 */     RenderIronGolem renderirongolem = new RenderIronGolem(rendermanager);
/* 36 */     renderirongolem.mainModel = modelBase;
/* 37 */     renderirongolem.shadowSize = shadowSize;
/* 38 */     return (IEntityRenderer)renderirongolem;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterIronGolem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */