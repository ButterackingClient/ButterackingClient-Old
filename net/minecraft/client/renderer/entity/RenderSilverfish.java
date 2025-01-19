/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntitySilverfish;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSilverfish extends RenderLiving<EntitySilverfish> {
/*  8 */   private static final ResourceLocation silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");
/*    */   
/*    */   public RenderSilverfish(RenderManager renderManagerIn) {
/* 11 */     super(renderManagerIn, (ModelBase)new ModelSilverfish(), 0.3F);
/*    */   }
/*    */   
/*    */   protected float getDeathMaxRotation(EntitySilverfish entityLivingBaseIn) {
/* 15 */     return 180.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntitySilverfish entity) {
/* 22 */     return silverfishTextures;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderSilverfish.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */