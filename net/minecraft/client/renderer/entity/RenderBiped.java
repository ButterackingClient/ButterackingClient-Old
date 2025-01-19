/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderBiped<T extends EntityLiving> extends RenderLiving<T> {
/* 11 */   private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation("textures/entity/steve.png");
/*    */   protected ModelBiped modelBipedMain;
/*    */   protected float field_77070_b;
/*    */   
/*    */   public RenderBiped(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize) {
/* 16 */     this(renderManagerIn, modelBipedIn, shadowSize, 1.0F);
/* 17 */     addLayer(new LayerHeldItem(this));
/*    */   }
/*    */   
/*    */   public RenderBiped(RenderManager renderManagerIn, ModelBiped modelBipedIn, float shadowSize, float p_i46169_4_) {
/* 21 */     super(renderManagerIn, (ModelBase)modelBipedIn, shadowSize);
/* 22 */     this.modelBipedMain = modelBipedIn;
/* 23 */     this.field_77070_b = p_i46169_4_;
/* 24 */     addLayer(new LayerCustomHead(modelBipedIn.bipedHead));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(T entity) {
/* 31 */     return DEFAULT_RES_LOC;
/*    */   }
/*    */   
/*    */   public void transformHeldFull3DItemLayer() {
/* 35 */     GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderBiped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */