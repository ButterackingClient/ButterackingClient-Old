/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RenderEntityItem extends Render<EntityItem> {
/*     */   private final RenderItem itemRenderer;
/*  17 */   private Random field_177079_e = new Random();
/*     */   
/*     */   public RenderEntityItem(RenderManager renderManagerIn, RenderItem p_i46167_2_) {
/*  20 */     super(renderManagerIn);
/*  21 */     this.itemRenderer = p_i46167_2_;
/*  22 */     this.shadowSize = 0.15F;
/*  23 */     this.shadowOpaque = 0.75F;
/*     */   }
/*     */   
/*     */   private int func_177077_a(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_) {
/*  27 */     ItemStack itemstack = itemIn.getEntityItem();
/*  28 */     Item item = itemstack.getItem();
/*     */     
/*  30 */     if (item == null) {
/*  31 */       return 0;
/*     */     }
/*  33 */     boolean flag = p_177077_9_.isGui3d();
/*  34 */     int i = func_177078_a(itemstack);
/*  35 */     float f = 0.25F;
/*  36 */     float f1 = MathHelper.sin((itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
/*  37 */     float f2 = (p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND)).scale.y;
/*  38 */     GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + f1 + 0.25F * f2, (float)p_177077_6_);
/*     */     
/*  40 */     if (flag || this.renderManager.options != null) {
/*  41 */       float f3 = ((itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * 57.295776F;
/*  42 */       GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
/*     */     } 
/*     */     
/*  45 */     if (!flag) {
/*  46 */       float f6 = -0.0F * (i - 1) * 0.5F;
/*  47 */       float f4 = -0.0F * (i - 1) * 0.5F;
/*  48 */       float f5 = -0.046875F * (i - 1) * 0.5F;
/*  49 */       GlStateManager.translate(f6, f4, f5);
/*     */     } 
/*     */     
/*  52 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  53 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_177078_a(ItemStack stack) {
/*  58 */     int i = 1;
/*     */     
/*  60 */     if (stack.stackSize > 48) {
/*  61 */       i = 5;
/*  62 */     } else if (stack.stackSize > 32) {
/*  63 */       i = 4;
/*  64 */     } else if (stack.stackSize > 16) {
/*  65 */       i = 3;
/*  66 */     } else if (stack.stackSize > 1) {
/*  67 */       i = 2;
/*     */     } 
/*     */     
/*  70 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  77 */     ItemStack itemstack = entity.getEntityItem();
/*  78 */     this.field_177079_e.setSeed(187L);
/*  79 */     boolean flag = false;
/*     */     
/*  81 */     if (bindEntityTexture(entity)) {
/*  82 */       this.renderManager.renderEngine.getTexture(getEntityTexture(entity)).setBlurMipmap(false, false);
/*  83 */       flag = true;
/*     */     } 
/*     */     
/*  86 */     GlStateManager.enableRescaleNormal();
/*  87 */     GlStateManager.alphaFunc(516, 0.1F);
/*  88 */     GlStateManager.enableBlend();
/*  89 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  90 */     GlStateManager.pushMatrix();
/*  91 */     IBakedModel ibakedmodel = this.itemRenderer.getItemModelMesher().getItemModel(itemstack);
/*  92 */     int i = func_177077_a(entity, x, y, z, partialTicks, ibakedmodel);
/*     */     
/*  94 */     for (int j = 0; j < i; j++) {
/*  95 */       if (ibakedmodel.isGui3d()) {
/*  96 */         GlStateManager.pushMatrix();
/*     */         
/*  98 */         if (j > 0) {
/*  99 */           float f = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 100 */           float f1 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 101 */           float f2 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 102 */           GlStateManager.translate(f, f1, f2);
/*     */         } 
/*     */         
/* 105 */         GlStateManager.scale(0.5F, 0.5F, 0.5F);
/* 106 */         ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
/* 107 */         this.itemRenderer.renderItem(itemstack, ibakedmodel);
/* 108 */         GlStateManager.popMatrix();
/*     */       } else {
/* 110 */         GlStateManager.pushMatrix();
/* 111 */         ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
/* 112 */         this.itemRenderer.renderItem(itemstack, ibakedmodel);
/* 113 */         GlStateManager.popMatrix();
/* 114 */         float f3 = (ibakedmodel.getItemCameraTransforms()).ground.scale.x;
/* 115 */         float f4 = (ibakedmodel.getItemCameraTransforms()).ground.scale.y;
/* 116 */         float f5 = (ibakedmodel.getItemCameraTransforms()).ground.scale.z;
/* 117 */         GlStateManager.translate(0.0F * f3, 0.0F * f4, 0.046875F * f5);
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     GlStateManager.popMatrix();
/* 122 */     GlStateManager.disableRescaleNormal();
/* 123 */     GlStateManager.disableBlend();
/* 124 */     bindEntityTexture(entity);
/*     */     
/* 126 */     if (flag) {
/* 127 */       this.renderManager.renderEngine.getTexture(getEntityTexture(entity)).restoreLastBlurMipmap();
/*     */     }
/*     */     
/* 130 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityItem entity) {
/* 137 */     return TextureMap.locationBlocksTexture;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderEntityItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */