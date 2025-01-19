/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelQuadruped;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ public abstract class ModelAdapterQuadruped extends ModelAdapter {
/*    */   public ModelAdapterQuadruped(Class entityClass, String name, float shadowSize) {
/*  9 */     super(entityClass, name, shadowSize);
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 13 */     if (!(model instanceof ModelQuadruped)) {
/* 14 */       return null;
/*    */     }
/* 16 */     ModelQuadruped modelquadruped = (ModelQuadruped)model;
/* 17 */     return modelPart.equals("head") ? modelquadruped.head : (modelPart.equals("body") ? modelquadruped.body : (modelPart.equals("leg1") ? modelquadruped.leg1 : (modelPart.equals("leg2") ? modelquadruped.leg2 : (modelPart.equals("leg3") ? modelquadruped.leg3 : (modelPart.equals("leg4") ? modelquadruped.leg4 : null)))));
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 22 */     return new String[] { "head", "body", "leg1", "leg2", "leg3", "leg4" };
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterQuadruped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */