/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class Gui {
/*  11 */   public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
/*  12 */   public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
/*  13 */   public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
/*     */ 
/*     */   
/*     */   protected float zLevel;
/*     */ 
/*     */   
/*     */   protected void drawHorizontalLine(int startX, int endX, int y, int color) {
/*  20 */     if (endX < startX) {
/*  21 */       int i = startX;
/*  22 */       startX = endX;
/*  23 */       endX = i;
/*     */     } 
/*     */     
/*  26 */     drawRect(startX, y, endX + 1, y + 1, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawVerticalLine(int x, int startY, int endY, int color) {
/*  33 */     if (endY < startY) {
/*  34 */       int i = startY;
/*  35 */       startY = endY;
/*  36 */       endY = i;
/*     */     } 
/*     */     
/*  39 */     drawRect(x, startY + 1, x + 1, endY, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawRect(int left, int top, int right, int bottom, int color) {
/*  46 */     if (left < right) {
/*  47 */       int i = left;
/*  48 */       left = right;
/*  49 */       right = i;
/*     */     } 
/*     */     
/*  52 */     if (top < bottom) {
/*  53 */       int j = top;
/*  54 */       top = bottom;
/*  55 */       bottom = j;
/*     */     } 
/*     */     
/*  58 */     float f3 = (color >> 24 & 0xFF) / 255.0F;
/*  59 */     float f = (color >> 16 & 0xFF) / 255.0F;
/*  60 */     float f1 = (color >> 8 & 0xFF) / 255.0F;
/*  61 */     float f2 = (color & 0xFF) / 255.0F;
/*  62 */     Tessellator tessellator = Tessellator.getInstance();
/*  63 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  64 */     GlStateManager.enableBlend();
/*  65 */     GlStateManager.disableTexture2D();
/*  66 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  67 */     GlStateManager.color(f, f1, f2, f3);
/*  68 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/*  69 */     worldrenderer.pos(left, bottom, 0.0D).endVertex();
/*  70 */     worldrenderer.pos(right, bottom, 0.0D).endVertex();
/*  71 */     worldrenderer.pos(right, top, 0.0D).endVertex();
/*  72 */     worldrenderer.pos(left, top, 0.0D).endVertex();
/*  73 */     tessellator.draw();
/*  74 */     GlStateManager.enableTexture2D();
/*  75 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
/*  83 */     float f = (startColor >> 24 & 0xFF) / 255.0F;
/*  84 */     float f1 = (startColor >> 16 & 0xFF) / 255.0F;
/*  85 */     float f2 = (startColor >> 8 & 0xFF) / 255.0F;
/*  86 */     float f3 = (startColor & 0xFF) / 255.0F;
/*  87 */     float f4 = (endColor >> 24 & 0xFF) / 255.0F;
/*  88 */     float f5 = (endColor >> 16 & 0xFF) / 255.0F;
/*  89 */     float f6 = (endColor >> 8 & 0xFF) / 255.0F;
/*  90 */     float f7 = (endColor & 0xFF) / 255.0F;
/*  91 */     GlStateManager.disableTexture2D();
/*  92 */     GlStateManager.enableBlend();
/*  93 */     GlStateManager.disableAlpha();
/*  94 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  95 */     GlStateManager.shadeModel(7425);
/*  96 */     Tessellator tessellator = Tessellator.getInstance();
/*  97 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  98 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  99 */     worldrenderer.pos(right, top, this.zLevel).color(f1, f2, f3, f).endVertex();
/* 100 */     worldrenderer.pos(left, top, this.zLevel).color(f1, f2, f3, f).endVertex();
/* 101 */     worldrenderer.pos(left, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
/* 102 */     worldrenderer.pos(right, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
/* 103 */     tessellator.draw();
/* 104 */     GlStateManager.shadeModel(7424);
/* 105 */     GlStateManager.disableBlend();
/* 106 */     GlStateManager.enableAlpha();
/* 107 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
/* 114 */     fontRendererIn.drawStringWithShadow(text, (x - fontRendererIn.getStringWidth(text) / 2), y, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
/* 121 */     fontRendererIn.drawStringWithShadow(text, x, y, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
/* 128 */     float f = 0.00390625F;
/* 129 */     float f1 = 0.00390625F;
/* 130 */     Tessellator tessellator = Tessellator.getInstance();
/* 131 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 132 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 133 */     worldrenderer.pos((x + 0), (y + height), this.zLevel).tex(((textureX + 0) * f), ((textureY + height) * f1)).endVertex();
/* 134 */     worldrenderer.pos((x + width), (y + height), this.zLevel).tex(((textureX + width) * f), ((textureY + height) * f1)).endVertex();
/* 135 */     worldrenderer.pos((x + width), (y + 0), this.zLevel).tex(((textureX + width) * f), ((textureY + 0) * f1)).endVertex();
/* 136 */     worldrenderer.pos((x + 0), (y + 0), this.zLevel).tex(((textureX + 0) * f), ((textureY + 0) * f1)).endVertex();
/* 137 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
/* 144 */     float f = 0.00390625F;
/* 145 */     float f1 = 0.00390625F;
/* 146 */     Tessellator tessellator = Tessellator.getInstance();
/* 147 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 148 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 149 */     worldrenderer.pos((xCoord + 0.0F), (yCoord + maxV), this.zLevel).tex(((minU + 0) * f), ((minV + maxV) * f1)).endVertex();
/* 150 */     worldrenderer.pos((xCoord + maxU), (yCoord + maxV), this.zLevel).tex(((minU + maxU) * f), ((minV + maxV) * f1)).endVertex();
/* 151 */     worldrenderer.pos((xCoord + maxU), (yCoord + 0.0F), this.zLevel).tex(((minU + maxU) * f), ((minV + 0) * f1)).endVertex();
/* 152 */     worldrenderer.pos((xCoord + 0.0F), (yCoord + 0.0F), this.zLevel).tex(((minU + 0) * f), ((minV + 0) * f1)).endVertex();
/* 153 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
/* 160 */     Tessellator tessellator = Tessellator.getInstance();
/* 161 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 162 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 163 */     worldrenderer.pos((xCoord + 0), (yCoord + heightIn), this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
/* 164 */     worldrenderer.pos((xCoord + widthIn), (yCoord + heightIn), this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
/* 165 */     worldrenderer.pos((xCoord + widthIn), (yCoord + 0), this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
/* 166 */     worldrenderer.pos((xCoord + 0), (yCoord + 0), this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
/* 167 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
/* 174 */     float f = 1.0F / textureWidth;
/* 175 */     float f1 = 1.0F / textureHeight;
/* 176 */     Tessellator tessellator = Tessellator.getInstance();
/* 177 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 178 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 179 */     worldrenderer.pos(x, (y + height), 0.0D).tex((u * f), ((v + height) * f1)).endVertex();
/* 180 */     worldrenderer.pos((x + width), (y + height), 0.0D).tex(((u + width) * f), ((v + height) * f1)).endVertex();
/* 181 */     worldrenderer.pos((x + width), y, 0.0D).tex(((u + width) * f), (v * f1)).endVertex();
/* 182 */     worldrenderer.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
/* 183 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
/* 190 */     float f = 1.0F / tileWidth;
/* 191 */     float f1 = 1.0F / tileHeight;
/* 192 */     Tessellator tessellator = Tessellator.getInstance();
/* 193 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 194 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 195 */     worldrenderer.pos(x, (y + height), 0.0D).tex((u * f), ((v + vHeight) * f1)).endVertex();
/* 196 */     worldrenderer.pos((x + width), (y + height), 0.0D).tex(((u + uWidth) * f), ((v + vHeight) * f1)).endVertex();
/* 197 */     worldrenderer.pos((x + width), y, 0.0D).tex(((u + uWidth) * f), (v * f1)).endVertex();
/* 198 */     worldrenderer.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
/* 199 */     tessellator.draw();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\Gui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */