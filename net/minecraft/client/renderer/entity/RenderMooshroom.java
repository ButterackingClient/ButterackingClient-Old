/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerMooshroomMushroom;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityMooshroom;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderMooshroom extends RenderLiving<EntityMooshroom> {
/*  9 */   private static final ResourceLocation mooshroomTextures = new ResourceLocation("textures/entity/cow/mooshroom.png");
/*    */   
/*    */   public RenderMooshroom(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 12 */     super(renderManagerIn, modelBaseIn, shadowSizeIn);
/* 13 */     addLayer(new LayerMooshroomMushroom(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityMooshroom entity) {
/* 20 */     return mooshroomTextures;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderMooshroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */