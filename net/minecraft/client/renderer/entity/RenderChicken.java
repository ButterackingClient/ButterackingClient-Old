/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityChicken;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderChicken extends RenderLiving<EntityChicken> {
/*  9 */   private static final ResourceLocation chickenTextures = new ResourceLocation("textures/entity/chicken.png");
/*    */   
/*    */   public RenderChicken(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 12 */     super(renderManagerIn, modelBaseIn, shadowSizeIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityChicken entity) {
/* 19 */     return chickenTextures;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected float handleRotationFloat(EntityChicken livingBase, float partialTicks) {
/* 26 */     float f = livingBase.field_70888_h + (livingBase.wingRotation - livingBase.field_70888_h) * partialTicks;
/* 27 */     float f1 = livingBase.field_70884_g + (livingBase.destPos - livingBase.field_70884_g) * partialTicks;
/* 28 */     return (MathHelper.sin(f) + 1.0F) * f1;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderChicken.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */