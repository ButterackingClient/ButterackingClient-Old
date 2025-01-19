/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec4b;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ 
/*     */ public class MapItemRenderer
/*     */ {
/*  19 */   private static final ResourceLocation mapIcons = new ResourceLocation("textures/map/map_icons.png");
/*     */   private final TextureManager textureManager;
/*  21 */   private final Map<String, Instance> loadedMaps = Maps.newHashMap();
/*     */   
/*     */   public MapItemRenderer(TextureManager textureManagerIn) {
/*  24 */     this.textureManager = textureManagerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMapTexture(MapData mapdataIn) {
/*  31 */     getMapRendererInstance(mapdataIn).updateMapTexture();
/*     */   }
/*     */   
/*     */   public void renderMap(MapData mapdataIn, boolean p_148250_2_) {
/*  35 */     getMapRendererInstance(mapdataIn).render(p_148250_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Instance getMapRendererInstance(MapData mapdataIn) {
/*  42 */     Instance mapitemrenderer$instance = this.loadedMaps.get(mapdataIn.mapName);
/*     */     
/*  44 */     if (mapitemrenderer$instance == null) {
/*  45 */       mapitemrenderer$instance = new Instance(mapdataIn, null);
/*  46 */       this.loadedMaps.put(mapdataIn.mapName, mapitemrenderer$instance);
/*     */     } 
/*     */     
/*  49 */     return mapitemrenderer$instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearLoadedMaps() {
/*  56 */     for (Instance mapitemrenderer$instance : this.loadedMaps.values()) {
/*  57 */       this.textureManager.deleteTexture(mapitemrenderer$instance.location);
/*     */     }
/*     */     
/*  60 */     this.loadedMaps.clear();
/*     */   }
/*     */   
/*     */   class Instance {
/*     */     private final MapData mapData;
/*     */     private final DynamicTexture mapTexture;
/*     */     private final ResourceLocation location;
/*     */     private final int[] mapTextureData;
/*     */     
/*     */     private Instance(MapData mapdataIn) {
/*  70 */       this.mapData = mapdataIn;
/*  71 */       this.mapTexture = new DynamicTexture(128, 128);
/*  72 */       this.mapTextureData = this.mapTexture.getTextureData();
/*  73 */       this.location = MapItemRenderer.this.textureManager.getDynamicTextureLocation("map/" + mapdataIn.mapName, this.mapTexture);
/*     */       
/*  75 */       for (int i = 0; i < this.mapTextureData.length; i++) {
/*  76 */         this.mapTextureData[i] = 0;
/*     */       }
/*     */     }
/*     */     
/*     */     private void updateMapTexture() {
/*  81 */       for (int i = 0; i < 16384; i++) {
/*  82 */         int j = this.mapData.colors[i] & 0xFF;
/*     */         
/*  84 */         if (j / 4 == 0) {
/*  85 */           this.mapTextureData[i] = (i + i / 128 & 0x1) * 8 + 16 << 24;
/*     */         } else {
/*  87 */           this.mapTextureData[i] = MapColor.mapColorArray[j / 4].getMapColor(j & 0x3);
/*     */         } 
/*     */       } 
/*     */       
/*  91 */       this.mapTexture.updateDynamicTexture();
/*     */     }
/*     */     
/*     */     private void render(boolean noOverlayRendering) {
/*  95 */       int i = 0;
/*  96 */       int j = 0;
/*  97 */       Tessellator tessellator = Tessellator.getInstance();
/*  98 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  99 */       float f = 0.0F;
/* 100 */       MapItemRenderer.this.textureManager.bindTexture(this.location);
/* 101 */       GlStateManager.enableBlend();
/* 102 */       GlStateManager.tryBlendFuncSeparate(1, 771, 0, 1);
/* 103 */       GlStateManager.disableAlpha();
/* 104 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 105 */       worldrenderer.pos(((i + 0) + f), ((j + 128) - f), -0.009999999776482582D).tex(0.0D, 1.0D).endVertex();
/* 106 */       worldrenderer.pos(((i + 128) - f), ((j + 128) - f), -0.009999999776482582D).tex(1.0D, 1.0D).endVertex();
/* 107 */       worldrenderer.pos(((i + 128) - f), ((j + 0) + f), -0.009999999776482582D).tex(1.0D, 0.0D).endVertex();
/* 108 */       worldrenderer.pos(((i + 0) + f), ((j + 0) + f), -0.009999999776482582D).tex(0.0D, 0.0D).endVertex();
/* 109 */       tessellator.draw();
/* 110 */       GlStateManager.enableAlpha();
/* 111 */       GlStateManager.disableBlend();
/* 112 */       MapItemRenderer.this.textureManager.bindTexture(MapItemRenderer.mapIcons);
/* 113 */       int k = 0;
/*     */       
/* 115 */       for (Vec4b vec4b : this.mapData.mapDecorations.values()) {
/* 116 */         if (!noOverlayRendering || vec4b.func_176110_a() == 1) {
/* 117 */           GlStateManager.pushMatrix();
/* 118 */           GlStateManager.translate(i + vec4b.func_176112_b() / 2.0F + 64.0F, j + vec4b.func_176113_c() / 2.0F + 64.0F, -0.02F);
/* 119 */           GlStateManager.rotate((vec4b.func_176111_d() * 360) / 16.0F, 0.0F, 0.0F, 1.0F);
/* 120 */           GlStateManager.scale(4.0F, 4.0F, 3.0F);
/* 121 */           GlStateManager.translate(-0.125F, 0.125F, 0.0F);
/* 122 */           byte b0 = vec4b.func_176110_a();
/* 123 */           float f1 = (b0 % 4 + 0) / 4.0F;
/* 124 */           float f2 = (b0 / 4 + 0) / 4.0F;
/* 125 */           float f3 = (b0 % 4 + 1) / 4.0F;
/* 126 */           float f4 = (b0 / 4 + 1) / 4.0F;
/* 127 */           worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 128 */           float f5 = -0.001F;
/* 129 */           worldrenderer.pos(-1.0D, 1.0D, (k * -0.001F)).tex(f1, f2).endVertex();
/* 130 */           worldrenderer.pos(1.0D, 1.0D, (k * -0.001F)).tex(f3, f2).endVertex();
/* 131 */           worldrenderer.pos(1.0D, -1.0D, (k * -0.001F)).tex(f3, f4).endVertex();
/* 132 */           worldrenderer.pos(-1.0D, -1.0D, (k * -0.001F)).tex(f1, f4).endVertex();
/* 133 */           tessellator.draw();
/* 134 */           GlStateManager.popMatrix();
/* 135 */           k++;
/*     */         } 
/*     */       } 
/*     */       
/* 139 */       GlStateManager.pushMatrix();
/* 140 */       GlStateManager.translate(0.0F, 0.0F, -0.04F);
/* 141 */       GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 142 */       GlStateManager.popMatrix();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\MapItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */