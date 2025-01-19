/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityOcelot;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderOcelot extends RenderLiving<EntityOcelot> {
/*  9 */   private static final ResourceLocation blackOcelotTextures = new ResourceLocation("textures/entity/cat/black.png");
/* 10 */   private static final ResourceLocation ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
/* 11 */   private static final ResourceLocation redOcelotTextures = new ResourceLocation("textures/entity/cat/red.png");
/* 12 */   private static final ResourceLocation siameseOcelotTextures = new ResourceLocation("textures/entity/cat/siamese.png");
/*    */   
/*    */   public RenderOcelot(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 15 */     super(renderManagerIn, modelBaseIn, shadowSizeIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityOcelot entity) {
/* 22 */     switch (entity.getTameSkin()) {
/*    */       
/*    */       default:
/* 25 */         return ocelotTextures;
/*    */       
/*    */       case 1:
/* 28 */         return blackOcelotTextures;
/*    */       
/*    */       case 2:
/* 31 */         return redOcelotTextures;
/*    */       case 3:
/*    */         break;
/* 34 */     }  return siameseOcelotTextures;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void preRenderCallback(EntityOcelot entitylivingbaseIn, float partialTickTime) {
/* 43 */     super.preRenderCallback(entitylivingbaseIn, partialTickTime);
/*    */     
/* 45 */     if (entitylivingbaseIn.isTamed())
/* 46 */       GlStateManager.scale(0.8F, 0.8F, 0.8F); 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderOcelot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */