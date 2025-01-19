/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerSaddle;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityPig;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderPig extends RenderLiving<EntityPig> {
/*  9 */   private static final ResourceLocation pigTextures = new ResourceLocation("textures/entity/pig/pig.png");
/*    */   
/*    */   public RenderPig(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 12 */     super(renderManagerIn, modelBaseIn, shadowSizeIn);
/* 13 */     addLayer(new LayerSaddle(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityPig entity) {
/* 20 */     return pigTextures;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderPig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */