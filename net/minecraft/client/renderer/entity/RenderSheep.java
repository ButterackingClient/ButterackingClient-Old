/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSheep extends RenderLiving<EntitySheep> {
/*  9 */   private static final ResourceLocation shearedSheepTextures = new ResourceLocation("textures/entity/sheep/sheep.png");
/*    */   
/*    */   public RenderSheep(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 12 */     super(renderManagerIn, modelBaseIn, shadowSizeIn);
/* 13 */     addLayer(new LayerSheepWool(this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntitySheep entity) {
/* 20 */     return shearedSheepTextures;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderSheep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */