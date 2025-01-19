/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderMinecartMobSpawner;
/*    */ import net.minecraft.entity.ai.EntityMinecartMobSpawner;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterMinecartMobSpawner extends ModelAdapterMinecart {
/*    */   public ModelAdapterMinecartMobSpawner() {
/* 13 */     super(EntityMinecartMobSpawner.class, "spawner_minecart", 0.5F);
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 17 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 18 */     RenderMinecartMobSpawner renderminecartmobspawner = new RenderMinecartMobSpawner(rendermanager);
/*    */     
/* 20 */     if (!Reflector.RenderMinecart_modelMinecart.exists()) {
/* 21 */       Config.warn("Field not found: RenderMinecart.modelMinecart");
/* 22 */       return null;
/*    */     } 
/* 24 */     Reflector.setFieldValue(renderminecartmobspawner, Reflector.RenderMinecart_modelMinecart, modelBase);
/* 25 */     renderminecartmobspawner.shadowSize = shadowSize;
/* 26 */     return (IEntityRenderer)renderminecartmobspawner;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterMinecartMobSpawner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */