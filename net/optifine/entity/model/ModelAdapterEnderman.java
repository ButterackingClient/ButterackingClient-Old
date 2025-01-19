/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderman;
/*    */ import net.minecraft.client.renderer.entity.RenderEnderman;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ 
/*    */ public class ModelAdapterEnderman extends ModelAdapterBiped {
/*    */   public ModelAdapterEnderman() {
/* 12 */     super(EntityEnderman.class, "enderman", 0.5F);
/*    */   }
/*    */   
/*    */   public ModelBase makeModel() {
/* 16 */     return (ModelBase)new ModelEnderman(0.0F);
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 20 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 21 */     RenderEnderman renderenderman = new RenderEnderman(rendermanager);
/* 22 */     renderenderman.mainModel = modelBase;
/* 23 */     renderenderman.shadowSize = shadowSize;
/* 24 */     return (IEntityRenderer)renderenderman;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\entity\model\ModelAdapterEnderman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */