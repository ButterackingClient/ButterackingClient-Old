/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityFireball;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderFireball
/*    */   extends Render<EntityFireball> {
/*    */   public RenderFireball(RenderManager renderManagerIn, float scaleIn) {
/* 18 */     super(renderManagerIn);
/* 19 */     this.scale = scaleIn;
/*    */   }
/*    */ 
/*    */   
/*    */   private float scale;
/*    */   
/*    */   public void doRender(EntityFireball entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 26 */     GlStateManager.pushMatrix();
/* 27 */     bindEntityTexture(entity);
/* 28 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 29 */     GlStateManager.enableRescaleNormal();
/* 30 */     GlStateManager.scale(this.scale, this.scale, this.scale);
/* 31 */     TextureAtlasSprite textureatlassprite = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(Items.fire_charge);
/* 32 */     Tessellator tessellator = Tessellator.getInstance();
/* 33 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 34 */     float f = textureatlassprite.getMinU();
/* 35 */     float f1 = textureatlassprite.getMaxU();
/* 36 */     float f2 = textureatlassprite.getMinV();
/* 37 */     float f3 = textureatlassprite.getMaxV();
/* 38 */     float f4 = 1.0F;
/* 39 */     float f5 = 0.5F;
/* 40 */     float f6 = 0.25F;
/* 41 */     GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 42 */     GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 43 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
/* 44 */     worldrenderer.pos(-0.5D, -0.25D, 0.0D).tex(f, f3).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 45 */     worldrenderer.pos(0.5D, -0.25D, 0.0D).tex(f1, f3).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 46 */     worldrenderer.pos(0.5D, 0.75D, 0.0D).tex(f1, f2).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 47 */     worldrenderer.pos(-0.5D, 0.75D, 0.0D).tex(f, f2).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 48 */     tessellator.draw();
/* 49 */     GlStateManager.disableRescaleNormal();
/* 50 */     GlStateManager.popMatrix();
/* 51 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityFireball entity) {
/* 58 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */