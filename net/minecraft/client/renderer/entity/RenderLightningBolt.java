/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RenderLightningBolt extends Render<EntityLightningBolt> {
/*     */   public RenderLightningBolt(RenderManager renderManagerIn) {
/*  14 */     super(renderManagerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityLightningBolt entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  21 */     Tessellator tessellator = Tessellator.getInstance();
/*  22 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  23 */     GlStateManager.disableTexture2D();
/*  24 */     GlStateManager.disableLighting();
/*  25 */     GlStateManager.enableBlend();
/*  26 */     GlStateManager.blendFunc(770, 1);
/*  27 */     double[] adouble = new double[8];
/*  28 */     double[] adouble1 = new double[8];
/*  29 */     double d0 = 0.0D;
/*  30 */     double d1 = 0.0D;
/*  31 */     Random random = new Random(entity.boltVertex);
/*     */     
/*  33 */     for (int i = 7; i >= 0; i--) {
/*  34 */       adouble[i] = d0;
/*  35 */       adouble1[i] = d1;
/*  36 */       d0 += (random.nextInt(11) - 5);
/*  37 */       d1 += (random.nextInt(11) - 5);
/*     */     } 
/*     */     
/*  40 */     for (int k1 = 0; k1 < 4; k1++) {
/*  41 */       Random random1 = new Random(entity.boltVertex);
/*     */       
/*  43 */       for (int j = 0; j < 3; j++) {
/*  44 */         int k = 7;
/*  45 */         int l = 0;
/*     */         
/*  47 */         if (j > 0) {
/*  48 */           k = 7 - j;
/*     */         }
/*     */         
/*  51 */         if (j > 0) {
/*  52 */           l = k - 2;
/*     */         }
/*     */         
/*  55 */         double d2 = adouble[k] - d0;
/*  56 */         double d3 = adouble1[k] - d1;
/*     */         
/*  58 */         for (int i1 = k; i1 >= l; i1--) {
/*  59 */           double d4 = d2;
/*  60 */           double d5 = d3;
/*     */           
/*  62 */           if (j == 0) {
/*  63 */             d2 += (random1.nextInt(11) - 5);
/*  64 */             d3 += (random1.nextInt(11) - 5);
/*     */           } else {
/*  66 */             d2 += (random1.nextInt(31) - 15);
/*  67 */             d3 += (random1.nextInt(31) - 15);
/*     */           } 
/*     */           
/*  70 */           worldrenderer.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*  71 */           float f = 0.5F;
/*  72 */           float f1 = 0.45F;
/*  73 */           float f2 = 0.45F;
/*  74 */           float f3 = 0.5F;
/*  75 */           double d6 = 0.1D + k1 * 0.2D;
/*     */           
/*  77 */           if (j == 0) {
/*  78 */             d6 *= i1 * 0.1D + 1.0D;
/*     */           }
/*     */           
/*  81 */           double d7 = 0.1D + k1 * 0.2D;
/*     */           
/*  83 */           if (j == 0) {
/*  84 */             d7 *= (i1 - 1) * 0.1D + 1.0D;
/*     */           }
/*     */           
/*  87 */           for (int j1 = 0; j1 < 5; j1++) {
/*  88 */             double d8 = x + 0.5D - d6;
/*  89 */             double d9 = z + 0.5D - d6;
/*     */             
/*  91 */             if (j1 == 1 || j1 == 2) {
/*  92 */               d8 += d6 * 2.0D;
/*     */             }
/*     */             
/*  95 */             if (j1 == 2 || j1 == 3) {
/*  96 */               d9 += d6 * 2.0D;
/*     */             }
/*     */             
/*  99 */             double d10 = x + 0.5D - d7;
/* 100 */             double d11 = z + 0.5D - d7;
/*     */             
/* 102 */             if (j1 == 1 || j1 == 2) {
/* 103 */               d10 += d7 * 2.0D;
/*     */             }
/*     */             
/* 106 */             if (j1 == 2 || j1 == 3) {
/* 107 */               d11 += d7 * 2.0D;
/*     */             }
/*     */             
/* 110 */             worldrenderer.pos(d10 + d2, y + (i1 * 16), d11 + d3).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
/* 111 */             worldrenderer.pos(d8 + d4, y + ((i1 + 1) * 16), d9 + d5).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
/*     */           } 
/*     */           
/* 114 */           tessellator.draw();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     GlStateManager.disableBlend();
/* 120 */     GlStateManager.enableLighting();
/* 121 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityLightningBolt entity) {
/* 128 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\renderer\entity\RenderLightningBolt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */