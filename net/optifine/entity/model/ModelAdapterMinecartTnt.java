/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderTntMinecart;
/*    */ import net.minecraft.entity.item.EntityMinecartTNT;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.reflect.Reflector;
/*    */ 
/*    */ public class ModelAdapterMinecartTnt extends ModelAdapterMinecart {
/*    */   public ModelAdapterMinecartTnt() {
/* 13 */     super(EntityMinecartTNT.class, "tnt_minecart", 0.5F);
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 17 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 18 */     RenderTntMinecart rendertntminecart = new RenderTntMinecart(rendermanager);
/*    */     
/* 20 */     if (!Reflector.RenderMinecart_modelMinecart.exists()) {
/* 21 */       Config.warn("Field not found: RenderMinecart.modelMinecart");
/* 22 */       return null;
/*    */     } 
/* 24 */     Reflector.setFieldValue(rendertntminecart, Reflector.RenderMinecart_modelMinecart, modelBase);
/* 25 */     rendertntminecart.shadowSize = shadowSize;
/* 26 */     return (IEntityRenderer)rendertntminecart;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterMinecartTnt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */