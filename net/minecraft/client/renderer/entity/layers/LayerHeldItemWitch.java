/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelWitch;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.entity.RenderWitch;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityWitch;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class LayerHeldItemWitch implements LayerRenderer<EntityWitch> {
/*    */   private final RenderWitch witchRenderer;
/*    */   
/*    */   public LayerHeldItemWitch(RenderWitch witchRendererIn) {
/* 19 */     this.witchRenderer = witchRendererIn;
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntityWitch entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 23 */     ItemStack itemstack = entitylivingbaseIn.getHeldItem();
/*    */     
/* 25 */     if (itemstack != null) {
/* 26 */       GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 27 */       GlStateManager.pushMatrix();
/*    */       
/* 29 */       if ((this.witchRenderer.getMainModel()).isChild) {
/* 30 */         GlStateManager.translate(0.0F, 0.625F, 0.0F);
/* 31 */         GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
/* 32 */         float f = 0.5F;
/* 33 */         GlStateManager.scale(f, f, f);
/*    */       } 
/*    */       
/* 36 */       ((ModelWitch)this.witchRenderer.getMainModel()).villagerNose.postRender(0.0625F);
/* 37 */       GlStateManager.translate(-0.0625F, 0.53125F, 0.21875F);
/* 38 */       Item item = itemstack.getItem();
/* 39 */       Minecraft minecraft = Minecraft.getMinecraft();
/*    */       
/* 41 */       if (item instanceof net.minecraft.item.ItemBlock && minecraft.getBlockRendererDispatcher().isRenderTypeChest(Block.getBlockFromItem(item), itemstack.getMetadata())) {
/* 42 */         GlStateManager.translate(0.0F, 0.0625F, -0.25F);
/* 43 */         GlStateManager.rotate(30.0F, 1.0F, 0.0F, 0.0F);
/* 44 */         GlStateManager.rotate(-5.0F, 0.0F, 1.0F, 0.0F);
/* 45 */         float f4 = 0.375F;
/* 46 */         GlStateManager.scale(f4, -f4, f4);
/* 47 */       } else if (item == Items.bow) {
/* 48 */         GlStateManager.translate(0.0F, 0.125F, -0.125F);
/* 49 */         GlStateManager.rotate(-45.0F, 0.0F, 1.0F, 0.0F);
/* 50 */         float f1 = 0.625F;
/* 51 */         GlStateManager.scale(f1, -f1, f1);
/* 52 */         GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
/* 53 */         GlStateManager.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
/* 54 */       } else if (item.isFull3D()) {
/* 55 */         if (item.shouldRotateAroundWhenRendering()) {
/* 56 */           GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 57 */           GlStateManager.translate(0.0F, -0.0625F, 0.0F);
/*    */         } 
/*    */         
/* 60 */         this.witchRenderer.transformHeldFull3DItemLayer();
/* 61 */         GlStateManager.translate(0.0625F, -0.125F, 0.0F);
/* 62 */         float f2 = 0.625F;
/* 63 */         GlStateManager.scale(f2, -f2, f2);
/* 64 */         GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
/* 65 */         GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
/*    */       } else {
/* 67 */         GlStateManager.translate(0.1875F, 0.1875F, 0.0F);
/* 68 */         float f3 = 0.875F;
/* 69 */         GlStateManager.scale(f3, f3, f3);
/* 70 */         GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
/* 71 */         GlStateManager.rotate(-60.0F, 1.0F, 0.0F, 0.0F);
/* 72 */         GlStateManager.rotate(-30.0F, 0.0F, 0.0F, 1.0F);
/*    */       } 
/*    */       
/* 75 */       GlStateManager.rotate(-15.0F, 1.0F, 0.0F, 0.0F);
/* 76 */       GlStateManager.rotate(40.0F, 0.0F, 0.0F, 1.0F);
/* 77 */       minecraft.getItemRenderer().renderItem((EntityLivingBase)entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON);
/* 78 */       GlStateManager.popMatrix();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerHeldItemWitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */