/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityBeacon;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer<TileEntityBeacon> {
/*  17 */   private static final ResourceLocation beaconBeam = new ResourceLocation("textures/entity/beacon_beam.png");
/*     */   
/*     */   public void renderTileEntityAt(TileEntityBeacon te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  20 */     float f = te.shouldBeamRender();
/*     */     
/*  22 */     if (f > 0.0D) {
/*  23 */       if (Config.isShaders()) {
/*  24 */         Shaders.beginBeacon();
/*     */       }
/*     */       
/*  27 */       GlStateManager.alphaFunc(516, 0.1F);
/*     */       
/*  29 */       if (f > 0.0F) {
/*  30 */         Tessellator tessellator = Tessellator.getInstance();
/*  31 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  32 */         GlStateManager.disableFog();
/*  33 */         List<TileEntityBeacon.BeamSegment> list = te.getBeamSegments();
/*  34 */         int i = 0;
/*     */         
/*  36 */         for (int j = 0; j < list.size(); j++) {
/*  37 */           TileEntityBeacon.BeamSegment tileentitybeacon$beamsegment = list.get(j);
/*  38 */           int k = i + tileentitybeacon$beamsegment.getHeight();
/*  39 */           bindTexture(beaconBeam);
/*  40 */           GL11.glTexParameterf(3553, 10242, 10497.0F);
/*  41 */           GL11.glTexParameterf(3553, 10243, 10497.0F);
/*  42 */           GlStateManager.disableLighting();
/*  43 */           GlStateManager.disableCull();
/*  44 */           GlStateManager.disableBlend();
/*  45 */           GlStateManager.depthMask(true);
/*  46 */           GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
/*  47 */           double d0 = te.getWorld().getTotalWorldTime() + partialTicks;
/*  48 */           double d1 = MathHelper.func_181162_h(-d0 * 0.2D - MathHelper.floor_double(-d0 * 0.1D));
/*  49 */           float f1 = tileentitybeacon$beamsegment.getColors()[0];
/*  50 */           float f2 = tileentitybeacon$beamsegment.getColors()[1];
/*  51 */           float f3 = tileentitybeacon$beamsegment.getColors()[2];
/*  52 */           double d2 = d0 * 0.025D * -1.5D;
/*  53 */           double d3 = 0.2D;
/*  54 */           double d4 = 0.5D + Math.cos(d2 + 2.356194490192345D) * 0.2D;
/*  55 */           double d5 = 0.5D + Math.sin(d2 + 2.356194490192345D) * 0.2D;
/*  56 */           double d6 = 0.5D + Math.cos(d2 + 0.7853981633974483D) * 0.2D;
/*  57 */           double d7 = 0.5D + Math.sin(d2 + 0.7853981633974483D) * 0.2D;
/*  58 */           double d8 = 0.5D + Math.cos(d2 + 3.9269908169872414D) * 0.2D;
/*  59 */           double d9 = 0.5D + Math.sin(d2 + 3.9269908169872414D) * 0.2D;
/*  60 */           double d10 = 0.5D + Math.cos(d2 + 5.497787143782138D) * 0.2D;
/*  61 */           double d11 = 0.5D + Math.sin(d2 + 5.497787143782138D) * 0.2D;
/*  62 */           double d12 = 0.0D;
/*  63 */           double d13 = 1.0D;
/*  64 */           double d14 = -1.0D + d1;
/*  65 */           double d15 = (tileentitybeacon$beamsegment.getHeight() * f) * 2.5D + d14;
/*  66 */           worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*  67 */           worldrenderer.pos(x + d4, y + k, z + d5).tex(1.0D, d15).color(f1, f2, f3, 1.0F).endVertex();
/*  68 */           worldrenderer.pos(x + d4, y + i, z + d5).tex(1.0D, d14).color(f1, f2, f3, 1.0F).endVertex();
/*  69 */           worldrenderer.pos(x + d6, y + i, z + d7).tex(0.0D, d14).color(f1, f2, f3, 1.0F).endVertex();
/*  70 */           worldrenderer.pos(x + d6, y + k, z + d7).tex(0.0D, d15).color(f1, f2, f3, 1.0F).endVertex();
/*  71 */           worldrenderer.pos(x + d10, y + k, z + d11).tex(1.0D, d15).color(f1, f2, f3, 1.0F).endVertex();
/*  72 */           worldrenderer.pos(x + d10, y + i, z + d11).tex(1.0D, d14).color(f1, f2, f3, 1.0F).endVertex();
/*  73 */           worldrenderer.pos(x + d8, y + i, z + d9).tex(0.0D, d14).color(f1, f2, f3, 1.0F).endVertex();
/*  74 */           worldrenderer.pos(x + d8, y + k, z + d9).tex(0.0D, d15).color(f1, f2, f3, 1.0F).endVertex();
/*  75 */           worldrenderer.pos(x + d6, y + k, z + d7).tex(1.0D, d15).color(f1, f2, f3, 1.0F).endVertex();
/*  76 */           worldrenderer.pos(x + d6, y + i, z + d7).tex(1.0D, d14).color(f1, f2, f3, 1.0F).endVertex();
/*  77 */           worldrenderer.pos(x + d10, y + i, z + d11).tex(0.0D, d14).color(f1, f2, f3, 1.0F).endVertex();
/*  78 */           worldrenderer.pos(x + d10, y + k, z + d11).tex(0.0D, d15).color(f1, f2, f3, 1.0F).endVertex();
/*  79 */           worldrenderer.pos(x + d8, y + k, z + d9).tex(1.0D, d15).color(f1, f2, f3, 1.0F).endVertex();
/*  80 */           worldrenderer.pos(x + d8, y + i, z + d9).tex(1.0D, d14).color(f1, f2, f3, 1.0F).endVertex();
/*  81 */           worldrenderer.pos(x + d4, y + i, z + d5).tex(0.0D, d14).color(f1, f2, f3, 1.0F).endVertex();
/*  82 */           worldrenderer.pos(x + d4, y + k, z + d5).tex(0.0D, d15).color(f1, f2, f3, 1.0F).endVertex();
/*  83 */           tessellator.draw();
/*  84 */           GlStateManager.enableBlend();
/*  85 */           GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  86 */           GlStateManager.depthMask(false);
/*  87 */           d2 = 0.2D;
/*  88 */           d3 = 0.2D;
/*  89 */           d4 = 0.8D;
/*  90 */           d5 = 0.2D;
/*  91 */           d6 = 0.2D;
/*  92 */           d7 = 0.8D;
/*  93 */           d8 = 0.8D;
/*  94 */           d9 = 0.8D;
/*  95 */           d10 = 0.0D;
/*  96 */           d11 = 1.0D;
/*  97 */           d12 = -1.0D + d1;
/*  98 */           d13 = (tileentitybeacon$beamsegment.getHeight() * f) + d12;
/*  99 */           worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 100 */           worldrenderer.pos(x + 0.2D, y + k, z + 0.2D).tex(1.0D, d13).color(f1, f2, f3, 0.125F).endVertex();
/* 101 */           worldrenderer.pos(x + 0.2D, y + i, z + 0.2D).tex(1.0D, d12).color(f1, f2, f3, 0.125F).endVertex();
/* 102 */           worldrenderer.pos(x + 0.8D, y + i, z + 0.2D).tex(0.0D, d12).color(f1, f2, f3, 0.125F).endVertex();
/* 103 */           worldrenderer.pos(x + 0.8D, y + k, z + 0.2D).tex(0.0D, d13).color(f1, f2, f3, 0.125F).endVertex();
/* 104 */           worldrenderer.pos(x + 0.8D, y + k, z + 0.8D).tex(1.0D, d13).color(f1, f2, f3, 0.125F).endVertex();
/* 105 */           worldrenderer.pos(x + 0.8D, y + i, z + 0.8D).tex(1.0D, d12).color(f1, f2, f3, 0.125F).endVertex();
/* 106 */           worldrenderer.pos(x + 0.2D, y + i, z + 0.8D).tex(0.0D, d12).color(f1, f2, f3, 0.125F).endVertex();
/* 107 */           worldrenderer.pos(x + 0.2D, y + k, z + 0.8D).tex(0.0D, d13).color(f1, f2, f3, 0.125F).endVertex();
/* 108 */           worldrenderer.pos(x + 0.8D, y + k, z + 0.2D).tex(1.0D, d13).color(f1, f2, f3, 0.125F).endVertex();
/* 109 */           worldrenderer.pos(x + 0.8D, y + i, z + 0.2D).tex(1.0D, d12).color(f1, f2, f3, 0.125F).endVertex();
/* 110 */           worldrenderer.pos(x + 0.8D, y + i, z + 0.8D).tex(0.0D, d12).color(f1, f2, f3, 0.125F).endVertex();
/* 111 */           worldrenderer.pos(x + 0.8D, y + k, z + 0.8D).tex(0.0D, d13).color(f1, f2, f3, 0.125F).endVertex();
/* 112 */           worldrenderer.pos(x + 0.2D, y + k, z + 0.8D).tex(1.0D, d13).color(f1, f2, f3, 0.125F).endVertex();
/* 113 */           worldrenderer.pos(x + 0.2D, y + i, z + 0.8D).tex(1.0D, d12).color(f1, f2, f3, 0.125F).endVertex();
/* 114 */           worldrenderer.pos(x + 0.2D, y + i, z + 0.2D).tex(0.0D, d12).color(f1, f2, f3, 0.125F).endVertex();
/* 115 */           worldrenderer.pos(x + 0.2D, y + k, z + 0.2D).tex(0.0D, d13).color(f1, f2, f3, 0.125F).endVertex();
/* 116 */           tessellator.draw();
/* 117 */           GlStateManager.enableLighting();
/* 118 */           GlStateManager.enableTexture2D();
/* 119 */           GlStateManager.depthMask(true);
/* 120 */           i = k;
/*     */         } 
/*     */         
/* 123 */         GlStateManager.enableFog();
/*     */       } 
/*     */       
/* 126 */       if (Config.isShaders()) {
/* 127 */         Shaders.endBeacon();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean forceTileEntityRender() {
/* 138 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntityBeaconRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */