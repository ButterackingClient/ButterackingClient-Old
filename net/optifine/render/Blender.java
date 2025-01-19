/*     */ package net.optifine.render;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class Blender {
/*     */   public static final int BLEND_ALPHA = 0;
/*     */   public static final int BLEND_ADD = 1;
/*     */   public static final int BLEND_SUBSTRACT = 2;
/*     */   public static final int BLEND_MULTIPLY = 3;
/*     */   public static final int BLEND_DODGE = 4;
/*     */   public static final int BLEND_BURN = 5;
/*     */   public static final int BLEND_SCREEN = 6;
/*     */   public static final int BLEND_OVERLAY = 7;
/*     */   public static final int BLEND_REPLACE = 8;
/*     */   public static final int BLEND_DEFAULT = 1;
/*     */   
/*     */   public static int parseBlend(String str) {
/*  19 */     if (str == null) {
/*  20 */       return 1;
/*     */     }
/*  22 */     str = str.toLowerCase().trim();
/*     */     
/*  24 */     if (str.equals("alpha"))
/*  25 */       return 0; 
/*  26 */     if (str.equals("add"))
/*  27 */       return 1; 
/*  28 */     if (str.equals("subtract"))
/*  29 */       return 2; 
/*  30 */     if (str.equals("multiply"))
/*  31 */       return 3; 
/*  32 */     if (str.equals("dodge"))
/*  33 */       return 4; 
/*  34 */     if (str.equals("burn"))
/*  35 */       return 5; 
/*  36 */     if (str.equals("screen"))
/*  37 */       return 6; 
/*  38 */     if (str.equals("overlay"))
/*  39 */       return 7; 
/*  40 */     if (str.equals("replace")) {
/*  41 */       return 8;
/*     */     }
/*  43 */     Config.warn("Unknown blend: " + str);
/*  44 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setupBlend(int blend, float brightness) {
/*  50 */     switch (blend) {
/*     */       case 0:
/*  52 */         GlStateManager.disableAlpha();
/*  53 */         GlStateManager.enableBlend();
/*  54 */         GlStateManager.blendFunc(770, 771);
/*  55 */         GlStateManager.color(1.0F, 1.0F, 1.0F, brightness);
/*     */         break;
/*     */       
/*     */       case 1:
/*  59 */         GlStateManager.disableAlpha();
/*  60 */         GlStateManager.enableBlend();
/*  61 */         GlStateManager.blendFunc(770, 1);
/*  62 */         GlStateManager.color(1.0F, 1.0F, 1.0F, brightness);
/*     */         break;
/*     */       
/*     */       case 2:
/*  66 */         GlStateManager.disableAlpha();
/*  67 */         GlStateManager.enableBlend();
/*  68 */         GlStateManager.blendFunc(775, 0);
/*  69 */         GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       
/*     */       case 3:
/*  73 */         GlStateManager.disableAlpha();
/*  74 */         GlStateManager.enableBlend();
/*  75 */         GlStateManager.blendFunc(774, 771);
/*  76 */         GlStateManager.color(brightness, brightness, brightness, brightness);
/*     */         break;
/*     */       
/*     */       case 4:
/*  80 */         GlStateManager.disableAlpha();
/*  81 */         GlStateManager.enableBlend();
/*  82 */         GlStateManager.blendFunc(1, 1);
/*  83 */         GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       
/*     */       case 5:
/*  87 */         GlStateManager.disableAlpha();
/*  88 */         GlStateManager.enableBlend();
/*  89 */         GlStateManager.blendFunc(0, 769);
/*  90 */         GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       
/*     */       case 6:
/*  94 */         GlStateManager.disableAlpha();
/*  95 */         GlStateManager.enableBlend();
/*  96 */         GlStateManager.blendFunc(1, 769);
/*  97 */         GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       
/*     */       case 7:
/* 101 */         GlStateManager.disableAlpha();
/* 102 */         GlStateManager.enableBlend();
/* 103 */         GlStateManager.blendFunc(774, 768);
/* 104 */         GlStateManager.color(brightness, brightness, brightness, 1.0F);
/*     */         break;
/*     */       
/*     */       case 8:
/* 108 */         GlStateManager.enableAlpha();
/* 109 */         GlStateManager.disableBlend();
/* 110 */         GlStateManager.color(1.0F, 1.0F, 1.0F, brightness);
/*     */         break;
/*     */     } 
/* 113 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */   
/*     */   public static void clearBlend(float rainBrightness) {
/* 117 */     GlStateManager.disableAlpha();
/* 118 */     GlStateManager.enableBlend();
/* 119 */     GlStateManager.blendFunc(770, 1);
/* 120 */     GlStateManager.color(1.0F, 1.0F, 1.0F, rainBrightness);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\render\Blender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */