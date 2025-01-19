/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.render.Blender;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class CustomSky
/*     */ {
/*  20 */   private static CustomSkyLayer[][] worldSkyLayers = null;
/*     */   
/*     */   public static void reset() {
/*  23 */     worldSkyLayers = null;
/*     */   }
/*     */   
/*     */   public static void update() {
/*  27 */     reset();
/*     */     
/*  29 */     if (Config.isCustomSky()) {
/*  30 */       worldSkyLayers = readCustomSkies();
/*     */     }
/*     */   }
/*     */   
/*     */   private static CustomSkyLayer[][] readCustomSkies() {
/*  35 */     CustomSkyLayer[][] acustomskylayer = new CustomSkyLayer[10][0];
/*  36 */     String s = "mcpatcher/sky/world";
/*  37 */     int i = -1;
/*     */     
/*  39 */     for (int j = 0; j < acustomskylayer.length; j++) {
/*  40 */       String s1 = String.valueOf(s) + j + "/sky";
/*  41 */       List<CustomSkyLayer> list = new ArrayList();
/*     */       
/*  43 */       for (int k = 1; k < 1000; k++) {
/*  44 */         String s2 = String.valueOf(s1) + k + ".properties";
/*     */         
/*     */         try {
/*  47 */           ResourceLocation resourcelocation = new ResourceLocation(s2);
/*  48 */           InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */           
/*  50 */           if (inputstream == null) {
/*     */             break;
/*     */           }
/*     */           
/*  54 */           PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  55 */           propertiesOrdered.load(inputstream);
/*  56 */           inputstream.close();
/*  57 */           Config.dbg("CustomSky properties: " + s2);
/*  58 */           String s3 = String.valueOf(s1) + k + ".png";
/*  59 */           CustomSkyLayer customskylayer = new CustomSkyLayer((Properties)propertiesOrdered, s3);
/*     */           
/*  61 */           if (customskylayer.isValid(s2)) {
/*  62 */             ResourceLocation resourcelocation1 = new ResourceLocation(customskylayer.source);
/*  63 */             ITextureObject itextureobject = TextureUtils.getTexture(resourcelocation1);
/*     */             
/*  65 */             if (itextureobject == null) {
/*  66 */               Config.log("CustomSky: Texture not found: " + resourcelocation1);
/*     */             } else {
/*  68 */               customskylayer.textureId = itextureobject.getGlTextureId();
/*  69 */               list.add(customskylayer);
/*  70 */               inputstream.close();
/*     */             } 
/*     */           } 
/*  73 */         } catch (FileNotFoundException var15) {
/*     */           break;
/*  75 */         } catch (IOException ioexception) {
/*  76 */           ioexception.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/*  80 */       if (list.size() > 0) {
/*  81 */         CustomSkyLayer[] acustomskylayer2 = list.<CustomSkyLayer>toArray(new CustomSkyLayer[list.size()]);
/*  82 */         acustomskylayer[j] = acustomskylayer2;
/*  83 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     if (i < 0) {
/*  88 */       return null;
/*     */     }
/*  90 */     int l = i + 1;
/*  91 */     CustomSkyLayer[][] acustomskylayer1 = new CustomSkyLayer[l][0];
/*     */     
/*  93 */     for (int i1 = 0; i1 < acustomskylayer1.length; i1++) {
/*  94 */       acustomskylayer1[i1] = acustomskylayer[i1];
/*     */     }
/*     */     
/*  97 */     return acustomskylayer1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderSky(World world, TextureManager re, float partialTicks) {
/* 102 */     if (worldSkyLayers != null) {
/* 103 */       int i = world.provider.getDimensionId();
/*     */       
/* 105 */       if (i >= 0 && i < worldSkyLayers.length) {
/* 106 */         CustomSkyLayer[] acustomskylayer = worldSkyLayers[i];
/*     */         
/* 108 */         if (acustomskylayer != null) {
/* 109 */           long j = world.getWorldTime();
/* 110 */           int k = (int)(j % 24000L);
/* 111 */           float f = world.getCelestialAngle(partialTicks);
/* 112 */           float f1 = world.getRainStrength(partialTicks);
/* 113 */           float f2 = world.getThunderStrength(partialTicks);
/*     */           
/* 115 */           if (f1 > 0.0F) {
/* 116 */             f2 /= f1;
/*     */           }
/*     */           
/* 119 */           for (int l = 0; l < acustomskylayer.length; l++) {
/* 120 */             CustomSkyLayer customskylayer = acustomskylayer[l];
/*     */             
/* 122 */             if (customskylayer.isActive(world, k)) {
/* 123 */               customskylayer.render(world, k, f, f1, f2);
/*     */             }
/*     */           } 
/*     */           
/* 127 */           float f3 = 1.0F - f1;
/* 128 */           Blender.clearBlend(f3);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean hasSkyLayers(World world) {
/* 135 */     if (worldSkyLayers == null) {
/* 136 */       return false;
/*     */     }
/* 138 */     int i = world.provider.getDimensionId();
/*     */     
/* 140 */     if (i >= 0 && i < worldSkyLayers.length) {
/* 141 */       CustomSkyLayer[] acustomskylayer = worldSkyLayers[i];
/* 142 */       return (acustomskylayer == null) ? false : ((acustomskylayer.length > 0));
/*     */     } 
/* 144 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomSky.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */