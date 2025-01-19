/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelVillager;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderVillager;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ 
/*    */ public class ModelAdapterVillager extends ModelAdapter {
/*    */   public ModelAdapterVillager() {
/* 13 */     super(EntityVillager.class, "villager", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 17 */     return (ModelBase)new ModelVillager(0.0F);
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 21 */     if (!(model instanceof ModelVillager)) {
/* 22 */       return null;
/*    */     }
/* 24 */     ModelVillager modelvillager = (ModelVillager)model;
/* 25 */     return modelPart.equals("head") ? modelvillager.villagerHead : (modelPart.equals("body") ? modelvillager.villagerBody : (modelPart.equals("arms") ? modelvillager.villagerArms : (modelPart.equals("left_leg") ? modelvillager.leftVillagerLeg : (modelPart.equals("right_leg") ? modelvillager.rightVillagerLeg : (modelPart.equals("nose") ? modelvillager.villagerNose : null)))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 30 */     return new String[] { "head", "body", "arms", "right_leg", "left_leg", "nose" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 34 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 35 */     RenderVillager rendervillager = new RenderVillager(rendermanager);
/* 36 */     rendervillager.mainModel = modelBase;
/* 37 */     rendervillager.shadowSize = shadowSize;
/* 38 */     return (IEntityRenderer)rendervillager;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */