/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class CustomLoadingScreen
/*     */ {
/*     */   private ResourceLocation locationTexture;
/*  14 */   private int scaleMode = 0;
/*  15 */   private int scale = 2;
/*     */   private boolean center;
/*     */   private static final int SCALE_DEFAULT = 2;
/*     */   private static final int SCALE_MODE_FIXED = 0;
/*     */   private static final int SCALE_MODE_FULL = 1;
/*     */   private static final int SCALE_MODE_STRETCH = 2;
/*     */   
/*     */   public CustomLoadingScreen(ResourceLocation locationTexture, int scaleMode, int scale, boolean center) {
/*  23 */     this.locationTexture = locationTexture;
/*  24 */     this.scaleMode = scaleMode;
/*  25 */     this.scale = scale;
/*  26 */     this.center = center;
/*     */   }
/*     */   
/*     */   public static CustomLoadingScreen parseScreen(String path, int dimId, Properties props) {
/*  30 */     ResourceLocation resourcelocation = new ResourceLocation(path);
/*  31 */     int i = parseScaleMode(getProperty("scaleMode", dimId, props));
/*  32 */     int j = (i == 0) ? 2 : 1;
/*  33 */     int k = parseScale(getProperty("scale", dimId, props), j);
/*  34 */     boolean flag = Config.parseBoolean(getProperty("center", dimId, props), false);
/*  35 */     CustomLoadingScreen customloadingscreen = new CustomLoadingScreen(resourcelocation, i, k, flag);
/*  36 */     return customloadingscreen;
/*     */   }
/*     */   
/*     */   private static String getProperty(String key, int dim, Properties props) {
/*  40 */     if (props == null) {
/*  41 */       return null;
/*     */     }
/*  43 */     String s = props.getProperty("dim" + dim + "." + key);
/*     */     
/*  45 */     if (s != null) {
/*  46 */       return s;
/*     */     }
/*  48 */     s = props.getProperty(key);
/*  49 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseScaleMode(String str) {
/*  55 */     if (str == null) {
/*  56 */       return 0;
/*     */     }
/*  58 */     str = str.toLowerCase().trim();
/*     */     
/*  60 */     if (str.equals("fixed"))
/*  61 */       return 0; 
/*  62 */     if (str.equals("full"))
/*  63 */       return 1; 
/*  64 */     if (str.equals("stretch")) {
/*  65 */       return 2;
/*     */     }
/*  67 */     CustomLoadingScreens.warn("Invalid scale mode: " + str);
/*  68 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseScale(String str, int def) {
/*  74 */     if (str == null) {
/*  75 */       return def;
/*     */     }
/*  77 */     str = str.trim();
/*  78 */     int i = Config.parseInt(str, -1);
/*     */     
/*  80 */     if (i < 1) {
/*  81 */       CustomLoadingScreens.warn("Invalid scale: " + str);
/*  82 */       return def;
/*     */     } 
/*  84 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBackground(int width, int height) {
/*  90 */     GlStateManager.disableLighting();
/*  91 */     GlStateManager.disableFog();
/*  92 */     Tessellator tessellator = Tessellator.getInstance();
/*  93 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  94 */     Config.getTextureManager().bindTexture(this.locationTexture);
/*  95 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  96 */     double d0 = (16 * this.scale);
/*  97 */     double d1 = width / d0;
/*  98 */     double d2 = height / d0;
/*  99 */     double d3 = 0.0D;
/* 100 */     double d4 = 0.0D;
/*     */     
/* 102 */     if (this.center) {
/* 103 */       d3 = (d0 - width) / d0 * 2.0D;
/* 104 */       d4 = (d0 - height) / d0 * 2.0D;
/*     */     } 
/*     */     
/* 107 */     switch (this.scaleMode) {
/*     */       case 1:
/* 109 */         d0 = Math.max(width, height);
/* 110 */         d1 = (this.scale * width) / d0;
/* 111 */         d2 = (this.scale * height) / d0;
/*     */         
/* 113 */         if (this.center) {
/* 114 */           d3 = this.scale * (d0 - width) / d0 * 2.0D;
/* 115 */           d4 = this.scale * (d0 - height) / d0 * 2.0D;
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 121 */         d1 = this.scale;
/* 122 */         d2 = this.scale;
/* 123 */         d3 = 0.0D;
/* 124 */         d4 = 0.0D;
/*     */         break;
/*     */     } 
/* 127 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 128 */     worldrenderer.pos(0.0D, height, 0.0D).tex(d3, d4 + d2).color(255, 255, 255, 255).endVertex();
/* 129 */     worldrenderer.pos(width, height, 0.0D).tex(d3 + d1, d4 + d2).color(255, 255, 255, 255).endVertex();
/* 130 */     worldrenderer.pos(width, 0.0D, 0.0D).tex(d3 + d1, d4).color(255, 255, 255, 255).endVertex();
/* 131 */     worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(d3, d4).color(255, 255, 255, 255).endVertex();
/* 132 */     tessellator.draw();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomLoadingScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */