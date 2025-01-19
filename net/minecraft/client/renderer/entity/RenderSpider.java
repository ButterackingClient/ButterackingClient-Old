/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSpider;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSpider<T extends EntitySpider> extends RenderLiving<T> {
/*  9 */   private static final ResourceLocation spiderTextures = new ResourceLocation("textures/entity/spider/spider.png");
/*    */   
/*    */   public RenderSpider(RenderManager renderManagerIn) {
/* 12 */     super(renderManagerIn, (ModelBase)new ModelSpider(), 1.0F);
/* 13 */     addLayer(new LayerSpiderEyes(this));
/*    */   }
/*    */   
/*    */   protected float getDeathMaxRotation(T entityLivingBaseIn) {
/* 17 */     return 180.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(T entity) {
/* 24 */     return spiderTextures;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderSpider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */