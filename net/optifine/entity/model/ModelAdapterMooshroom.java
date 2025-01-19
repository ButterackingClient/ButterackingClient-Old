/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelCow;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderMooshroom;
/*    */ import net.minecraft.entity.passive.EntityMooshroom;
/*    */ 
/*    */ public class ModelAdapterMooshroom extends ModelAdapterQuadruped {
/*    */   public ModelAdapterMooshroom() {
/* 12 */     super(EntityMooshroom.class, "mooshroom", 0.7F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 16 */     return (ModelBase)new ModelCow();
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 20 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 21 */     RenderMooshroom rendermooshroom = new RenderMooshroom(rendermanager, modelBase, shadowSize);
/* 22 */     return (IEntityRenderer)rendermooshroom;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterMooshroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */