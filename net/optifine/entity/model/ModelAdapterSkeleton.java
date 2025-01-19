/*    */ package net.optifine.entity.model;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelSkeleton;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderBiped;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSkeleton;
/*    */ import net.minecraft.entity.monster.EntitySkeleton;
/*    */ 
/*    */ public class ModelAdapterSkeleton extends ModelAdapterBiped {
/*    */   public ModelAdapterSkeleton() {
/* 14 */     super(EntitySkeleton.class, "skeleton", 0.7F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 18 */     return (ModelBase)new ModelSkeleton();
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 22 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 23 */     RenderSkeleton renderskeleton = new RenderSkeleton(rendermanager);
/* 24 */     Render.setModelBipedMain((RenderBiped)renderskeleton, (ModelBiped)modelBase);
/* 25 */     renderskeleton.mainModel = modelBase;
/* 26 */     renderskeleton.shadowSize = shadowSize;
/* 27 */     return (IEntityRenderer)renderskeleton;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */