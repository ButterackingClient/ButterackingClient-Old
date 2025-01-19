/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelZombie;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.monster.EntityPigZombie;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderPigZombie extends RenderBiped<EntityPigZombie> {
/* 10 */   private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("textures/entity/zombie_pigman.png");
/*    */   
/*    */   public RenderPigZombie(RenderManager renderManagerIn) {
/* 13 */     super(renderManagerIn, (ModelBiped)new ModelZombie(), 0.5F, 1.0F);
/* 14 */     addLayer(new LayerHeldItem(this));
/* 15 */     addLayer(new LayerBipedArmor(this) {
/*    */           protected void initArmor() {
/* 17 */             this.modelLeggings = (ModelBase)new ModelZombie(0.5F, true);
/* 18 */             this.modelArmor = (ModelBase)new ModelZombie(1.0F, true);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityPigZombie entity) {
/* 27 */     return ZOMBIE_PIGMAN_TEXTURE;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderPigZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */