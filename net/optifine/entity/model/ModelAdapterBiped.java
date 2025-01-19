/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ public abstract class ModelAdapterBiped extends ModelAdapter {
/*    */   public ModelAdapterBiped(Class entityClass, String name, float shadowSize) {
/*  9 */     super(entityClass, name, shadowSize);
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 13 */     if (!(model instanceof ModelBiped)) {
/* 14 */       return null;
/*    */     }
/* 16 */     ModelBiped modelbiped = (ModelBiped)model;
/* 17 */     return modelPart.equals("head") ? modelbiped.bipedHead : (modelPart.equals("headwear") ? modelbiped.bipedHeadwear : (modelPart.equals("body") ? modelbiped.bipedBody : (modelPart.equals("left_arm") ? modelbiped.bipedLeftArm : (modelPart.equals("right_arm") ? modelbiped.bipedRightArm : (modelPart.equals("left_leg") ? modelbiped.bipedLeftLeg : (modelPart.equals("right_leg") ? modelbiped.bipedRightLeg : null))))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 22 */     return new String[] { "head", "headwear", "body", "left_arm", "right_arm", "left_leg", "right_leg" };
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterBiped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */