/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.RenderCaveSpider;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityCaveSpider;
/*    */ 
/*    */ public class ModelAdapterCaveSpider extends ModelAdapterSpider {
/*    */   public ModelAdapterCaveSpider() {
/* 11 */     super(EntityCaveSpider.class, "cave_spider", 0.7F);
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 15 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 16 */     RenderCaveSpider rendercavespider = new RenderCaveSpider(rendermanager);
/* 17 */     rendercavespider.mainModel = modelBase;
/* 18 */     rendercavespider.shadowSize = shadowSize;
/* 19 */     return (IEntityRenderer)rendercavespider;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterCaveSpider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */