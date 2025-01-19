/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelQuadruped;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderMooshroom;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityMooshroom;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class LayerMooshroomMushroom implements LayerRenderer<EntityMooshroom> {
/*    */   private final RenderMooshroom mooshroomRenderer;
/* 19 */   private static final ResourceLocation LOCATION_MUSHROOM_RED = new ResourceLocation("textures/entity/cow/mushroom_red.png"); private ModelRenderer modelRendererMushroom;
/*    */   private static boolean hasTextureMushroom = false;
/*    */   
/*    */   public static void update() {
/* 23 */     hasTextureMushroom = Config.hasResource(LOCATION_MUSHROOM_RED);
/*    */   }
/*    */   
/*    */   public LayerMooshroomMushroom(RenderMooshroom mooshroomRendererIn) {
/* 27 */     this.mooshroomRenderer = mooshroomRendererIn;
/* 28 */     this.modelRendererMushroom = new ModelRenderer(this.mooshroomRenderer.mainModel);
/* 29 */     this.modelRendererMushroom.setTextureSize(16, 16);
/* 30 */     this.modelRendererMushroom.rotationPointX = -6.0F;
/* 31 */     this.modelRendererMushroom.rotationPointZ = -8.0F;
/* 32 */     this.modelRendererMushroom.rotateAngleY = MathHelper.PI / 4.0F;
/* 33 */     int[][] aint = { null, null, { 16, 16 }, { 16, 16 } };
/* 34 */     this.modelRendererMushroom.addBox(aint, 0.0F, 0.0F, 10.0F, 20.0F, 16.0F, 0.0F, 0.0F);
/* 35 */     int[][] aint1 = { null, null, null, null, { 16, 16 }, { 16, 16 } };
/* 36 */     this.modelRendererMushroom.addBox(aint1, 10.0F, 0.0F, 0.0F, 0.0F, 16.0F, 20.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public void doRenderLayer(EntityMooshroom entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 40 */     if (!entitylivingbaseIn.isChild() && !entitylivingbaseIn.isInvisible()) {
/* 41 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/*    */       
/* 43 */       if (hasTextureMushroom) {
/* 44 */         this.mooshroomRenderer.bindTexture(LOCATION_MUSHROOM_RED);
/*    */       } else {
/* 46 */         this.mooshroomRenderer.bindTexture(TextureMap.locationBlocksTexture);
/*    */       } 
/*    */       
/* 49 */       GlStateManager.enableCull();
/* 50 */       GlStateManager.cullFace(1028);
/* 51 */       GlStateManager.pushMatrix();
/* 52 */       GlStateManager.scale(1.0F, -1.0F, 1.0F);
/* 53 */       GlStateManager.translate(0.2F, 0.35F, 0.5F);
/* 54 */       GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
/* 55 */       GlStateManager.pushMatrix();
/* 56 */       GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/*    */       
/* 58 */       if (hasTextureMushroom) {
/* 59 */         this.modelRendererMushroom.render(0.0625F);
/*    */       } else {
/* 61 */         blockrendererdispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0F);
/*    */       } 
/*    */       
/* 64 */       GlStateManager.popMatrix();
/* 65 */       GlStateManager.pushMatrix();
/* 66 */       GlStateManager.translate(0.1F, 0.0F, -0.6F);
/* 67 */       GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
/* 68 */       GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/*    */       
/* 70 */       if (hasTextureMushroom) {
/* 71 */         this.modelRendererMushroom.render(0.0625F);
/*    */       } else {
/* 73 */         blockrendererdispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0F);
/*    */       } 
/*    */       
/* 76 */       GlStateManager.popMatrix();
/* 77 */       GlStateManager.popMatrix();
/* 78 */       GlStateManager.pushMatrix();
/* 79 */       ((ModelQuadruped)this.mooshroomRenderer.getMainModel()).head.postRender(0.0625F);
/* 80 */       GlStateManager.scale(1.0F, -1.0F, 1.0F);
/* 81 */       GlStateManager.translate(0.0F, 0.7F, -0.2F);
/* 82 */       GlStateManager.rotate(12.0F, 0.0F, 1.0F, 0.0F);
/* 83 */       GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/*    */       
/* 85 */       if (hasTextureMushroom) {
/* 86 */         this.modelRendererMushroom.render(0.0625F);
/*    */       } else {
/* 88 */         blockrendererdispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0F);
/*    */       } 
/*    */       
/* 91 */       GlStateManager.popMatrix();
/* 92 */       GlStateManager.cullFace(1029);
/* 93 */       GlStateManager.disableCull();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 98 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\layers\LayerMooshroomMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */