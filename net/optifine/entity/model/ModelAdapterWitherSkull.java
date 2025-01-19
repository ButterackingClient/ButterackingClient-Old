/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.model.ModelSkeletonHead;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
/*    */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterWitherSkull extends ModelAdapter {
/*    */   public ModelAdapterWitherSkull() {
/* 15 */     super(EntityWitherSkull.class, "wither_skull", 0.0F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 19 */     return (ModelBase)new ModelSkeletonHead();
/*    */   }
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 23 */     if (!(model instanceof ModelSkeletonHead)) {
/* 24 */       return null;
/*    */     }
/* 26 */     ModelSkeletonHead modelskeletonhead = (ModelSkeletonHead)model;
/* 27 */     return modelPart.equals("head") ? modelskeletonhead.skeletonHead : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 32 */     return new String[] { "head" };
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 36 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 37 */     RenderWitherSkull renderwitherskull = new RenderWitherSkull(rendermanager);
/*    */     
/* 39 */     if (!Reflector.RenderWitherSkull_model.exists()) {
/* 40 */       Config.warn("Field not found: RenderWitherSkull_model");
/* 41 */       return null;
/*    */     } 
/* 43 */     Reflector.setFieldValue(renderwitherskull, Reflector.RenderWitherSkull_model, modelBase);
/* 44 */     renderwitherskull.shadowSize = shadowSize;
/* 45 */     return (IEntityRenderer)renderwitherskull;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterWitherSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */