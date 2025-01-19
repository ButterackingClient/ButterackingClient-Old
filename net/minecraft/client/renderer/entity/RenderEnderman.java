/*    */ package net.minecraft.client.renderer.entity;
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelEnderman;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldBlock;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderEnderman extends RenderLiving<EntityEnderman> {
/* 13 */   private static final ResourceLocation endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
/*    */ 
/*    */   
/*    */   private ModelEnderman endermanModel;
/*    */ 
/*    */   
/* 19 */   private Random rnd = new Random();
/*    */   
/*    */   public RenderEnderman(RenderManager renderManagerIn) {
/* 22 */     super(renderManagerIn, (ModelBase)new ModelEnderman(0.0F), 0.5F);
/* 23 */     this.endermanModel = (ModelEnderman)this.mainModel;
/* 24 */     addLayer(new LayerEndermanEyes(this));
/* 25 */     addLayer(new LayerHeldBlock(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityEnderman entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 32 */     this.endermanModel.isCarrying = (entity.getHeldBlockState().getBlock().getMaterial() != Material.air);
/* 33 */     this.endermanModel.isAttacking = entity.isScreaming();
/*    */     
/* 35 */     if (entity.isScreaming()) {
/* 36 */       double d0 = 0.02D;
/* 37 */       x += this.rnd.nextGaussian() * d0;
/* 38 */       z += this.rnd.nextGaussian() * d0;
/*    */     } 
/*    */     
/* 41 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityEnderman entity) {
/* 48 */     return endermanTextures;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderEnderman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */