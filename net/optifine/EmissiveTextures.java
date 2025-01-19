/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ 
/*     */ 
/*     */ public class EmissiveTextures
/*     */ {
/*  18 */   private static String suffixEmissive = null;
/*  19 */   private static String suffixEmissivePng = null;
/*     */   private static boolean active = false;
/*     */   private static boolean render = false;
/*     */   private static boolean hasEmissive = false;
/*     */   private static boolean renderEmissive = false;
/*     */   private static float lightMapX;
/*     */   private static float lightMapY;
/*     */   private static final String SUFFIX_PNG = ".png";
/*  27 */   private static final ResourceLocation LOCATION_EMPTY = new ResourceLocation("mcpatcher/ctm/default/empty.png");
/*     */   
/*     */   public static boolean isActive() {
/*  30 */     return active;
/*     */   }
/*     */   
/*     */   public static String getSuffixEmissive() {
/*  34 */     return suffixEmissive;
/*     */   }
/*     */   
/*     */   public static void beginRender() {
/*  38 */     render = true;
/*  39 */     hasEmissive = false;
/*     */   }
/*     */   public static ITextureObject getEmissiveTexture(ITextureObject texture, Map<ResourceLocation, ITextureObject> mapTextures) {
/*     */     SimpleTexture simpleTexture1;
/*  43 */     if (!render)
/*  44 */       return texture; 
/*  45 */     if (!(texture instanceof SimpleTexture)) {
/*  46 */       return texture;
/*     */     }
/*  48 */     SimpleTexture simpletexture = (SimpleTexture)texture;
/*  49 */     ResourceLocation resourcelocation = simpletexture.locationEmissive;
/*     */     
/*  51 */     if (!renderEmissive) {
/*  52 */       if (resourcelocation != null) {
/*  53 */         hasEmissive = true;
/*     */       }
/*     */       
/*  56 */       return texture;
/*     */     } 
/*  58 */     if (resourcelocation == null) {
/*  59 */       resourcelocation = LOCATION_EMPTY;
/*     */     }
/*     */     
/*  62 */     ITextureObject itextureobject = mapTextures.get(resourcelocation);
/*     */     
/*  64 */     if (itextureobject == null) {
/*  65 */       simpleTexture1 = new SimpleTexture(resourcelocation);
/*  66 */       TextureManager texturemanager = Config.getTextureManager();
/*  67 */       texturemanager.loadTexture(resourcelocation, (ITextureObject)simpleTexture1);
/*     */     } 
/*     */     
/*  70 */     return (ITextureObject)simpleTexture1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasEmissive() {
/*  76 */     return hasEmissive;
/*     */   }
/*     */   
/*     */   public static void beginRenderEmissive() {
/*  80 */     lightMapX = OpenGlHelper.lastBrightnessX;
/*  81 */     lightMapY = OpenGlHelper.lastBrightnessY;
/*  82 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, lightMapY);
/*  83 */     renderEmissive = true;
/*     */   }
/*     */   
/*     */   public static void endRenderEmissive() {
/*  87 */     renderEmissive = false;
/*  88 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightMapX, lightMapY);
/*     */   }
/*     */   
/*     */   public static void endRender() {
/*  92 */     render = false;
/*  93 */     hasEmissive = false;
/*     */   }
/*     */   
/*     */   public static void update() {
/*  97 */     active = false;
/*  98 */     suffixEmissive = null;
/*  99 */     suffixEmissivePng = null;
/*     */     
/* 101 */     if (Config.isEmissiveTextures()) {
/*     */       try {
/* 103 */         String s = "optifine/emissive.properties";
/* 104 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 105 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */         
/* 107 */         if (inputstream == null) {
/*     */           return;
/*     */         }
/*     */         
/* 111 */         dbg("Loading " + s);
/* 112 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 113 */         propertiesOrdered.load(inputstream);
/* 114 */         inputstream.close();
/* 115 */         suffixEmissive = propertiesOrdered.getProperty("suffix.emissive");
/*     */         
/* 117 */         if (suffixEmissive != null) {
/* 118 */           suffixEmissivePng = String.valueOf(suffixEmissive) + ".png";
/*     */         }
/*     */         
/* 121 */         active = (suffixEmissive != null);
/* 122 */       } catch (FileNotFoundException var4) {
/*     */         return;
/* 124 */       } catch (IOException ioexception) {
/* 125 */         ioexception.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void dbg(String str) {
/* 131 */     Config.dbg("EmissiveTextures: " + str);
/*     */   }
/*     */   
/*     */   private static void warn(String str) {
/* 135 */     Config.warn("EmissiveTextures: " + str);
/*     */   }
/*     */   
/*     */   public static boolean isEmissive(ResourceLocation loc) {
/* 139 */     return (suffixEmissivePng == null) ? false : loc.getResourcePath().endsWith(suffixEmissivePng);
/*     */   }
/*     */   
/*     */   public static void loadTexture(ResourceLocation loc, SimpleTexture tex) {
/* 143 */     if (loc != null && tex != null) {
/* 144 */       tex.isEmissive = false;
/* 145 */       tex.locationEmissive = null;
/*     */       
/* 147 */       if (suffixEmissivePng != null) {
/* 148 */         String s = loc.getResourcePath();
/*     */         
/* 150 */         if (s.endsWith(".png"))
/* 151 */           if (s.endsWith(suffixEmissivePng)) {
/* 152 */             tex.isEmissive = true;
/*     */           } else {
/* 154 */             String s1 = String.valueOf(s.substring(0, s.length() - ".png".length())) + suffixEmissivePng;
/* 155 */             ResourceLocation resourcelocation = new ResourceLocation(loc.getResourceDomain(), s1);
/*     */             
/* 157 */             if (Config.hasResource(resourcelocation))
/* 158 */               tex.locationEmissive = resourcelocation; 
/*     */           }  
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\EmissiveTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */