/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSheep2;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSheep;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ 
/*    */ public class ModelAdapterSheep extends ModelAdapterQuadruped {
/*    */   public ModelAdapterSheep() {
/* 12 */     super(EntitySheep.class, "sheep", 0.7F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 16 */     return (ModelBase)new ModelSheep2();
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 20 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 21 */     return (IEntityRenderer)new RenderSheep(rendermanager, modelBase, shadowSize);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterSheep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */