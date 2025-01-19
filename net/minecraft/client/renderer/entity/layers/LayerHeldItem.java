/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class LayerHeldItem
/*    */   implements LayerRenderer<EntityLivingBase> {
/*    */   private final RendererLivingEntity<?> livingEntityRenderer;
/*    */   
/*    */   public LayerHeldItem(RendererLivingEntity<?> livingEntityRendererIn) {
/* 20 */     this.livingEntityRenderer = livingEntityRendererIn;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 24 */     ItemStack itemstack = entitylivingbaseIn.getHeldItem();
/*    */     
/* 26 */     if (itemstack != null) {
/* 27 */       GlStateManager.pushMatrix();
/*    */       
/* 29 */       if ((this.livingEntityRenderer.getMainModel()).isChild) {
/* 30 */         float f = 0.5F;
/* 31 */         GlStateManager.translate(0.0F, 0.625F, 0.0F);
/* 32 */         GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
/* 33 */         GlStateManager.scale(f, f, f);
/*    */       } 
/*    */       
/* 36 */       ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F);
/* 37 */       GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);
/*    */       
/* 39 */       if (entitylivingbaseIn instanceof EntityPlayer && ((EntityPlayer)entitylivingbaseIn).fishEntity != null) {
/* 40 */         itemstack = new ItemStack((Item)Items.fishing_rod, 0);
/*    */       }
/*    */       
/* 43 */       Item item = itemstack.getItem();
/* 44 */       Minecraft minecraft = Minecraft.getMinecraft();
/*    */       
/* 46 */       if (item instanceof net.minecraft.item.ItemBlock && Block.getBlockFromItem(item).getRenderType() == 2) {
/* 47 */         GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
/* 48 */         GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
/* 49 */         GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 50 */         float f1 = 0.375F;
/* 51 */         GlStateManager.scale(-f1, -f1, f1);
/*    */       } 
/*    */       
/* 54 */       if (entitylivingbaseIn.isSneaking()) {
/* 55 */         GlStateManager.translate(0.0F, 0.203125F, 0.0F);
/*    */       }
/*    */       
/* 58 */       minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON);
/* 59 */       GlStateManager.popMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 64 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerHeldItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */