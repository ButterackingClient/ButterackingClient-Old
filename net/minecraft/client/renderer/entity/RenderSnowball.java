/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderSnowball<T extends Entity> extends Render<T> {
/*    */   protected final Item field_177084_a;
/*    */   private final RenderItem field_177083_e;
/*    */   
/*    */   public RenderSnowball(RenderManager renderManagerIn, Item p_i46137_2_, RenderItem p_i46137_3_) {
/* 16 */     super(renderManagerIn);
/* 17 */     this.field_177084_a = p_i46137_2_;
/* 18 */     this.field_177083_e = p_i46137_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 25 */     GlStateManager.pushMatrix();
/* 26 */     GlStateManager.translate((float)x, (float)y, (float)z);
/* 27 */     GlStateManager.enableRescaleNormal();
/* 28 */     GlStateManager.scale(0.5F, 0.5F, 0.5F);
/* 29 */     GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 30 */     GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 31 */     bindTexture(TextureMap.locationBlocksTexture);
/* 32 */     this.field_177083_e.renderItem(func_177082_d(entity), ItemCameraTransforms.TransformType.GROUND);
/* 33 */     GlStateManager.disableRescaleNormal();
/* 34 */     GlStateManager.popMatrix();
/* 35 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */   
/*    */   public ItemStack func_177082_d(T entityIn) {
/* 39 */     return new ItemStack(this.field_177084_a, 1, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(Entity entity) {
/* 46 */     return TextureMap.locationBlocksTexture;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderSnowball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */