/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ 
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ import net.optifine.entity.model.IEntityRenderer;
/*    */ 
/*    */ public abstract class TileEntitySpecialRenderer<T extends TileEntity> implements IEntityRenderer {
/* 12 */   protected static final ResourceLocation[] DESTROY_STAGES = new ResourceLocation[] { new ResourceLocation("textures/blocks/destroy_stage_0.png"), new ResourceLocation("textures/blocks/destroy_stage_1.png"), new ResourceLocation("textures/blocks/destroy_stage_2.png"), new ResourceLocation("textures/blocks/destroy_stage_3.png"), new ResourceLocation("textures/blocks/destroy_stage_4.png"), new ResourceLocation("textures/blocks/destroy_stage_5.png"), new ResourceLocation("textures/blocks/destroy_stage_6.png"), new ResourceLocation("textures/blocks/destroy_stage_7.png"), new ResourceLocation("textures/blocks/destroy_stage_8.png"), new ResourceLocation("textures/blocks/destroy_stage_9.png") };
/*    */   protected TileEntityRendererDispatcher rendererDispatcher;
/* 14 */   private Class tileEntityClass = null;
/* 15 */   private ResourceLocation locationTextureCustom = null;
/*    */   
/*    */   public abstract void renderTileEntityAt(T paramT, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, int paramInt);
/*    */   
/*    */   protected void bindTexture(ResourceLocation location) {
/* 20 */     TextureManager texturemanager = this.rendererDispatcher.renderEngine;
/*    */     
/* 22 */     if (texturemanager != null) {
/* 23 */       texturemanager.bindTexture(location);
/*    */     }
/*    */   }
/*    */   
/*    */   protected World getWorld() {
/* 28 */     return this.rendererDispatcher.worldObj;
/*    */   }
/*    */   
/*    */   public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn) {
/* 32 */     this.rendererDispatcher = rendererDispatcherIn;
/*    */   }
/*    */   
/*    */   public FontRenderer getFontRenderer() {
/* 36 */     return this.rendererDispatcher.getFontRenderer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean forceTileEntityRender() {
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderTileEntityFast(T p_renderTileEntityFast_1_, double p_renderTileEntityFast_2_, double p_renderTileEntityFast_4_, double p_renderTileEntityFast_6_, float p_renderTileEntityFast_8_, int p_renderTileEntityFast_9_, WorldRenderer p_renderTileEntityFast_10_) {}
/*    */   
/*    */   public Class getEntityClass() {
/* 52 */     return this.tileEntityClass;
/*    */   }
/*    */   
/*    */   public void setEntityClass(Class p_setEntityClass_1_) {
/* 56 */     this.tileEntityClass = p_setEntityClass_1_;
/*    */   }
/*    */   
/*    */   public ResourceLocation getLocationTextureCustom() {
/* 60 */     return this.locationTextureCustom;
/*    */   }
/*    */   
/*    */   public void setLocationTextureCustom(ResourceLocation p_setLocationTextureCustom_1_) {
/* 64 */     this.locationTextureCustom = p_setLocationTextureCustom_1_;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntitySpecialRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */