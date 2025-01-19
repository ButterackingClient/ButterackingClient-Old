/*    */ package net.optifine.entity.model;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelZombie;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderBiped;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPigZombie;
/*    */ import net.minecraft.entity.monster.EntityPigZombie;
/*    */ 
/*    */ public class ModelAdapterPigZombie extends ModelAdapterBiped {
/*    */   public ModelAdapterPigZombie() {
/* 14 */     super(EntityPigZombie.class, "zombie_pigman", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 18 */     return (ModelBase)new ModelZombie();
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 22 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 23 */     RenderPigZombie renderpigzombie = new RenderPigZombie(rendermanager);
/* 24 */     Render.setModelBipedMain((RenderBiped)renderpigzombie, (ModelBiped)modelBase);
/* 25 */     renderpigzombie.mainModel = modelBase;
/* 26 */     renderpigzombie.shadowSize = shadowSize;
/* 27 */     return (IEntityRenderer)renderpigzombie;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterPigZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */