/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEndPortal;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.shaders.ShadersRender;
/*     */ 
/*     */ public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer<TileEntityEndPortal> {
/*  19 */   private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
/*  20 */   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
/*  21 */   private static final Random field_147527_e = new Random(31100L);
/*  22 */   FloatBuffer field_147528_b = GLAllocation.createDirectFloatBuffer(16);
/*     */   
/*     */   public void renderTileEntityAt(TileEntityEndPortal te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  25 */     if (!Config.isShaders() || !ShadersRender.renderEndPortal(te, x, y, z, partialTicks, destroyStage, 0.75F)) {
/*  26 */       float f = (float)this.rendererDispatcher.entityX;
/*  27 */       float f1 = (float)this.rendererDispatcher.entityY;
/*  28 */       float f2 = (float)this.rendererDispatcher.entityZ;
/*  29 */       GlStateManager.disableLighting();
/*  30 */       field_147527_e.setSeed(31100L);
/*  31 */       float f3 = 0.75F;
/*     */       
/*  33 */       for (int i = 0; i < 16; i++) {
/*  34 */         GlStateManager.pushMatrix();
/*  35 */         float f4 = (16 - i);
/*  36 */         float f5 = 0.0625F;
/*  37 */         float f6 = 1.0F / (f4 + 1.0F);
/*     */         
/*  39 */         if (i == 0) {
/*  40 */           bindTexture(END_SKY_TEXTURE);
/*  41 */           f6 = 0.1F;
/*  42 */           f4 = 65.0F;
/*  43 */           f5 = 0.125F;
/*  44 */           GlStateManager.enableBlend();
/*  45 */           GlStateManager.blendFunc(770, 771);
/*     */         } 
/*     */         
/*  48 */         if (i >= 1) {
/*  49 */           bindTexture(END_PORTAL_TEXTURE);
/*     */         }
/*     */         
/*  52 */         if (i == 1) {
/*  53 */           GlStateManager.enableBlend();
/*  54 */           GlStateManager.blendFunc(1, 1);
/*  55 */           f5 = 0.5F;
/*     */         } 
/*     */         
/*  58 */         float f7 = (float)-(y + f3);
/*  59 */         float f8 = f7 + (float)(ActiveRenderInfo.getPosition()).yCoord;
/*  60 */         float f9 = f7 + f4 + (float)(ActiveRenderInfo.getPosition()).yCoord;
/*  61 */         float f10 = f8 / f9;
/*  62 */         f10 = (float)(y + f3) + f10;
/*  63 */         GlStateManager.translate(f, f10, f2);
/*  64 */         GlStateManager.texGen(GlStateManager.TexGen.S, 9217);
/*  65 */         GlStateManager.texGen(GlStateManager.TexGen.T, 9217);
/*  66 */         GlStateManager.texGen(GlStateManager.TexGen.R, 9217);
/*  67 */         GlStateManager.texGen(GlStateManager.TexGen.Q, 9216);
/*  68 */         GlStateManager.texGen(GlStateManager.TexGen.S, 9473, func_147525_a(1.0F, 0.0F, 0.0F, 0.0F));
/*  69 */         GlStateManager.texGen(GlStateManager.TexGen.T, 9473, func_147525_a(0.0F, 0.0F, 1.0F, 0.0F));
/*  70 */         GlStateManager.texGen(GlStateManager.TexGen.R, 9473, func_147525_a(0.0F, 0.0F, 0.0F, 1.0F));
/*  71 */         GlStateManager.texGen(GlStateManager.TexGen.Q, 9474, func_147525_a(0.0F, 1.0F, 0.0F, 0.0F));
/*  72 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
/*  73 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
/*  74 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
/*  75 */         GlStateManager.enableTexGenCoord(GlStateManager.TexGen.Q);
/*  76 */         GlStateManager.popMatrix();
/*  77 */         GlStateManager.matrixMode(5890);
/*  78 */         GlStateManager.pushMatrix();
/*  79 */         GlStateManager.loadIdentity();
/*  80 */         GlStateManager.translate(0.0F, (float)(Minecraft.getSystemTime() % 700000L) / 700000.0F, 0.0F);
/*  81 */         GlStateManager.scale(f5, f5, f5);
/*  82 */         GlStateManager.translate(0.5F, 0.5F, 0.0F);
/*  83 */         GlStateManager.rotate((i * i * 4321 + i * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
/*  84 */         GlStateManager.translate(-0.5F, -0.5F, 0.0F);
/*  85 */         GlStateManager.translate(-f, -f2, -f1);
/*  86 */         f8 = f7 + (float)(ActiveRenderInfo.getPosition()).yCoord;
/*  87 */         GlStateManager.translate((float)(ActiveRenderInfo.getPosition()).xCoord * f4 / f8, (float)(ActiveRenderInfo.getPosition()).zCoord * f4 / f8, -f1);
/*  88 */         Tessellator tessellator = Tessellator.getInstance();
/*  89 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  90 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  91 */         float f11 = (field_147527_e.nextFloat() * 0.5F + 0.1F) * f6;
/*  92 */         float f12 = (field_147527_e.nextFloat() * 0.5F + 0.4F) * f6;
/*  93 */         float f13 = (field_147527_e.nextFloat() * 0.5F + 0.5F) * f6;
/*     */         
/*  95 */         if (i == 0) {
/*  96 */           f11 = f12 = f13 = 1.0F * f6;
/*     */         }
/*     */         
/*  99 */         worldrenderer.pos(x, y + f3, z).color(f11, f12, f13, 1.0F).endVertex();
/* 100 */         worldrenderer.pos(x, y + f3, z + 1.0D).color(f11, f12, f13, 1.0F).endVertex();
/* 101 */         worldrenderer.pos(x + 1.0D, y + f3, z + 1.0D).color(f11, f12, f13, 1.0F).endVertex();
/* 102 */         worldrenderer.pos(x + 1.0D, y + f3, z).color(f11, f12, f13, 1.0F).endVertex();
/* 103 */         tessellator.draw();
/* 104 */         GlStateManager.popMatrix();
/* 105 */         GlStateManager.matrixMode(5888);
/* 106 */         bindTexture(END_SKY_TEXTURE);
/*     */       } 
/*     */       
/* 109 */       GlStateManager.disableBlend();
/* 110 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
/* 111 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
/* 112 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
/* 113 */       GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
/* 114 */       GlStateManager.enableLighting();
/*     */     } 
/*     */   }
/*     */   
/*     */   private FloatBuffer func_147525_a(float p_147525_1_, float p_147525_2_, float p_147525_3_, float p_147525_4_) {
/* 119 */     this.field_147528_b.clear();
/* 120 */     this.field_147528_b.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_);
/* 121 */     this.field_147528_b.flip();
/* 122 */     return this.field_147528_b;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\tileentity\TileEntityEndPortalRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */