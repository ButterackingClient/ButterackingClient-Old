/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBoat;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityBoat;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderBoat extends Render<EntityBoat> {
/* 11 */   private static final ResourceLocation boatTextures = new ResourceLocation("textures/entity/boat.png");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 16 */   protected ModelBase modelBoat = (ModelBase)new ModelBoat();
/*    */   
/*    */   public RenderBoat(RenderManager renderManagerIn) {
/* 19 */     super(renderManagerIn);
/* 20 */     this.shadowSize = 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityBoat entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 27 */     GlStateManager.pushMatrix();
/* 28 */     GlStateManager.translate((float)x, (float)y + 0.25F, (float)z);
/* 29 */     GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
/* 30 */     float f = entity.getTimeSinceHit() - partialTicks;
/* 31 */     float f1 = entity.getDamageTaken() - partialTicks;
/*    */     
/* 33 */     if (f1 < 0.0F) {
/* 34 */       f1 = 0.0F;
/*    */     }
/*    */     
/* 37 */     if (f > 0.0F) {
/* 38 */       GlStateManager.rotate(MathHelper.sin(f) * f * f1 / 10.0F * entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
/*    */     }
/*    */     
/* 41 */     float f2 = 0.75F;
/* 42 */     GlStateManager.scale(f2, f2, f2);
/* 43 */     GlStateManager.scale(1.0F / f2, 1.0F / f2, 1.0F / f2);
/* 44 */     bindEntityTexture(entity);
/* 45 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 46 */     this.modelBoat.render((Entity)entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/* 47 */     GlStateManager.popMatrix();
/* 48 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityBoat entity) {
/* 55 */     return boatTextures;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */