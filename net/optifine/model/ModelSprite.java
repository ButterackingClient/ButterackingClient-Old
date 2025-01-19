/*     */ package net.optifine.model;
/*     */ 
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ModelSprite {
/*  12 */   private ModelRenderer modelRenderer = null;
/*  13 */   private int textureOffsetX = 0;
/*  14 */   private int textureOffsetY = 0;
/*  15 */   private float posX = 0.0F;
/*  16 */   private float posY = 0.0F;
/*  17 */   private float posZ = 0.0F;
/*  18 */   private int sizeX = 0;
/*  19 */   private int sizeY = 0;
/*  20 */   private int sizeZ = 0;
/*  21 */   private float sizeAdd = 0.0F;
/*  22 */   private float minU = 0.0F;
/*  23 */   private float minV = 0.0F;
/*  24 */   private float maxU = 0.0F;
/*  25 */   private float maxV = 0.0F;
/*     */   
/*     */   public ModelSprite(ModelRenderer modelRenderer, int textureOffsetX, int textureOffsetY, float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, float sizeAdd) {
/*  28 */     this.modelRenderer = modelRenderer;
/*  29 */     this.textureOffsetX = textureOffsetX;
/*  30 */     this.textureOffsetY = textureOffsetY;
/*  31 */     this.posX = posX;
/*  32 */     this.posY = posY;
/*  33 */     this.posZ = posZ;
/*  34 */     this.sizeX = sizeX;
/*  35 */     this.sizeY = sizeY;
/*  36 */     this.sizeZ = sizeZ;
/*  37 */     this.sizeAdd = sizeAdd;
/*  38 */     this.minU = textureOffsetX / modelRenderer.textureWidth;
/*  39 */     this.minV = textureOffsetY / modelRenderer.textureHeight;
/*  40 */     this.maxU = (textureOffsetX + sizeX) / modelRenderer.textureWidth;
/*  41 */     this.maxV = (textureOffsetY + sizeY) / modelRenderer.textureHeight;
/*     */   }
/*     */   
/*     */   public void render(Tessellator tessellator, float scale) {
/*  45 */     GlStateManager.translate(this.posX * scale, this.posY * scale, this.posZ * scale);
/*  46 */     float f = this.minU;
/*  47 */     float f1 = this.maxU;
/*  48 */     float f2 = this.minV;
/*  49 */     float f3 = this.maxV;
/*     */     
/*  51 */     if (this.modelRenderer.mirror) {
/*  52 */       f = this.maxU;
/*  53 */       f1 = this.minU;
/*     */     } 
/*     */     
/*  56 */     if (this.modelRenderer.mirrorV) {
/*  57 */       f2 = this.maxV;
/*  58 */       f3 = this.minV;
/*     */     } 
/*     */     
/*  61 */     renderItemIn2D(tessellator, f, f2, f1, f3, this.sizeX, this.sizeY, scale * this.sizeZ, this.modelRenderer.textureWidth, this.modelRenderer.textureHeight);
/*  62 */     GlStateManager.translate(-this.posX * scale, -this.posY * scale, -this.posZ * scale);
/*     */   }
/*     */   
/*     */   public static void renderItemIn2D(Tessellator tess, float minU, float minV, float maxU, float maxV, int sizeX, int sizeY, float width, float texWidth, float texHeight) {
/*  66 */     if (width < 6.25E-4F) {
/*  67 */       width = 6.25E-4F;
/*     */     }
/*     */     
/*  70 */     float f = maxU - minU;
/*  71 */     float f1 = maxV - minV;
/*  72 */     double d0 = (MathHelper.abs(f) * texWidth / 16.0F);
/*  73 */     double d1 = (MathHelper.abs(f1) * texHeight / 16.0F);
/*  74 */     WorldRenderer worldrenderer = tess.getWorldRenderer();
/*  75 */     GL11.glNormal3f(0.0F, 0.0F, -1.0F);
/*  76 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*  77 */     worldrenderer.pos(0.0D, d1, 0.0D).tex(minU, maxV).endVertex();
/*  78 */     worldrenderer.pos(d0, d1, 0.0D).tex(maxU, maxV).endVertex();
/*  79 */     worldrenderer.pos(d0, 0.0D, 0.0D).tex(maxU, minV).endVertex();
/*  80 */     worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(minU, minV).endVertex();
/*  81 */     tess.draw();
/*  82 */     GL11.glNormal3f(0.0F, 0.0F, 1.0F);
/*  83 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*  84 */     worldrenderer.pos(0.0D, 0.0D, width).tex(minU, minV).endVertex();
/*  85 */     worldrenderer.pos(d0, 0.0D, width).tex(maxU, minV).endVertex();
/*  86 */     worldrenderer.pos(d0, d1, width).tex(maxU, maxV).endVertex();
/*  87 */     worldrenderer.pos(0.0D, d1, width).tex(minU, maxV).endVertex();
/*  88 */     tess.draw();
/*  89 */     float f2 = 0.5F * f / sizeX;
/*  90 */     float f3 = 0.5F * f1 / sizeY;
/*  91 */     GL11.glNormal3f(-1.0F, 0.0F, 0.0F);
/*  92 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/*  94 */     for (int i = 0; i < sizeX; i++) {
/*  95 */       float f4 = i / sizeX;
/*  96 */       float f5 = minU + f * f4 + f2;
/*  97 */       worldrenderer.pos(f4 * d0, d1, width).tex(f5, maxV).endVertex();
/*  98 */       worldrenderer.pos(f4 * d0, d1, 0.0D).tex(f5, maxV).endVertex();
/*  99 */       worldrenderer.pos(f4 * d0, 0.0D, 0.0D).tex(f5, minV).endVertex();
/* 100 */       worldrenderer.pos(f4 * d0, 0.0D, width).tex(f5, minV).endVertex();
/*     */     } 
/*     */     
/* 103 */     tess.draw();
/* 104 */     GL11.glNormal3f(1.0F, 0.0F, 0.0F);
/* 105 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/* 107 */     for (int j = 0; j < sizeX; j++) {
/* 108 */       float f7 = j / sizeX;
/* 109 */       float f10 = minU + f * f7 + f2;
/* 110 */       float f6 = f7 + 1.0F / sizeX;
/* 111 */       worldrenderer.pos(f6 * d0, 0.0D, width).tex(f10, minV).endVertex();
/* 112 */       worldrenderer.pos(f6 * d0, 0.0D, 0.0D).tex(f10, minV).endVertex();
/* 113 */       worldrenderer.pos(f6 * d0, d1, 0.0D).tex(f10, maxV).endVertex();
/* 114 */       worldrenderer.pos(f6 * d0, d1, width).tex(f10, maxV).endVertex();
/*     */     } 
/*     */     
/* 117 */     tess.draw();
/* 118 */     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 119 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/* 121 */     for (int k = 0; k < sizeY; k++) {
/* 122 */       float f8 = k / sizeY;
/* 123 */       float f11 = minV + f1 * f8 + f3;
/* 124 */       float f13 = f8 + 1.0F / sizeY;
/* 125 */       worldrenderer.pos(0.0D, f13 * d1, width).tex(minU, f11).endVertex();
/* 126 */       worldrenderer.pos(d0, f13 * d1, width).tex(maxU, f11).endVertex();
/* 127 */       worldrenderer.pos(d0, f13 * d1, 0.0D).tex(maxU, f11).endVertex();
/* 128 */       worldrenderer.pos(0.0D, f13 * d1, 0.0D).tex(minU, f11).endVertex();
/*     */     } 
/*     */     
/* 131 */     tess.draw();
/* 132 */     GL11.glNormal3f(0.0F, -1.0F, 0.0F);
/* 133 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*     */     
/* 135 */     for (int l = 0; l < sizeY; l++) {
/* 136 */       float f9 = l / sizeY;
/* 137 */       float f12 = minV + f1 * f9 + f3;
/* 138 */       worldrenderer.pos(d0, f9 * d1, width).tex(maxU, f12).endVertex();
/* 139 */       worldrenderer.pos(0.0D, f9 * d1, width).tex(minU, f12).endVertex();
/* 140 */       worldrenderer.pos(0.0D, f9 * d1, 0.0D).tex(minU, f12).endVertex();
/* 141 */       worldrenderer.pos(d0, f9 * d1, 0.0D).tex(maxU, f12).endVertex();
/*     */     } 
/*     */     
/* 144 */     tess.draw();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\model\ModelSprite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */