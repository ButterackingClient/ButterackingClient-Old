/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelArmorStand;
/*    */ import net.minecraft.client.model.ModelArmorStandArmor;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.item.EntityArmorStand;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class ArmorStandRenderer extends RendererLivingEntity<EntityArmorStand> {
/* 16 */   public static final ResourceLocation TEXTURE_ARMOR_STAND = new ResourceLocation("textures/entity/armorstand/wood.png");
/*    */   
/*    */   public ArmorStandRenderer(RenderManager p_i46195_1_) {
/* 19 */     super(p_i46195_1_, (ModelBase)new ModelArmorStand(), 0.0F);
/* 20 */     LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {
/*    */         protected void initArmor() {
/* 22 */           this.modelLeggings = (ModelBase)new ModelArmorStandArmor(0.5F);
/* 23 */           this.modelArmor = (ModelBase)new ModelArmorStandArmor(1.0F);
/*    */         }
/*    */       };
/* 26 */     addLayer(layerbipedarmor);
/* 27 */     addLayer(new LayerHeldItem(this));
/* 28 */     addLayer(new LayerCustomHead((getMainModel()).bipedHead));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityArmorStand entity) {
/* 35 */     return TEXTURE_ARMOR_STAND;
/*    */   }
/*    */   
/*    */   public ModelArmorStand getMainModel() {
/* 39 */     return (ModelArmorStand)super.getMainModel();
/*    */   }
/*    */   
/*    */   protected void rotateCorpse(EntityArmorStand bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 43 */     GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
/*    */   }
/*    */   
/*    */   protected boolean canRenderName(EntityArmorStand entity) {
/* 47 */     return entity.getAlwaysRenderNameTag();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\ArmorStandRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */