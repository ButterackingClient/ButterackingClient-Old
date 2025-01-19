/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelPig;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPig;
/*    */ import net.minecraft.entity.passive.EntityPig;
/*    */ 
/*    */ public class ModelAdapterPig extends ModelAdapterQuadruped {
/*    */   public ModelAdapterPig() {
/* 12 */     super(EntityPig.class, "pig", 0.7F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 16 */     return (ModelBase)new ModelPig();
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 20 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 21 */     return (IEntityRenderer)new RenderPig(rendermanager, modelBase, shadowSize);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterPig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */