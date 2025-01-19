/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityCow;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderCow extends RenderLiving<EntityCow> {
/*  8 */   private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
/*    */   
/*    */   public RenderCow(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 11 */     super(renderManagerIn, modelBaseIn, shadowSizeIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityCow entity) {
/* 18 */     return cowTextures;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderCow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */