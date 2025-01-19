/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class NaturalTextures
/*     */ {
/*  16 */   private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];
/*     */   
/*     */   public static void update() {
/*  19 */     propertiesByIndex = new NaturalProperties[0];
/*     */     
/*  21 */     if (Config.isNaturalTextures()) {
/*  22 */       String s = "optifine/natural.properties";
/*     */       
/*     */       try {
/*  25 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */         
/*  27 */         if (!Config.hasResource(resourcelocation)) {
/*  28 */           Config.dbg("NaturalTextures: configuration \"" + s + "\" not found");
/*     */           
/*     */           return;
/*     */         } 
/*  32 */         boolean flag = Config.isFromDefaultResourcePack(resourcelocation);
/*  33 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/*  34 */         ArrayList<NaturalProperties> arraylist = new ArrayList(256);
/*  35 */         String s1 = Config.readInputStream(inputstream);
/*  36 */         inputstream.close();
/*  37 */         String[] astring = Config.tokenize(s1, "\n\r");
/*     */         
/*  39 */         if (flag) {
/*  40 */           Config.dbg("Natural Textures: Parsing default configuration \"" + s + "\"");
/*  41 */           Config.dbg("Natural Textures: Valid only for textures from default resource pack");
/*     */         } else {
/*  43 */           Config.dbg("Natural Textures: Parsing configuration \"" + s + "\"");
/*     */         } 
/*     */         
/*  46 */         TextureMap texturemap = TextureUtils.getTextureMapBlocks();
/*     */         
/*  48 */         for (int i = 0; i < astring.length; i++) {
/*  49 */           String s2 = astring[i].trim();
/*     */           
/*  51 */           if (!s2.startsWith("#")) {
/*  52 */             String[] astring1 = Config.tokenize(s2, "=");
/*     */             
/*  54 */             if (astring1.length != 2) {
/*  55 */               Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s2);
/*     */             } else {
/*  57 */               String s3 = astring1[0].trim();
/*  58 */               String s4 = astring1[1].trim();
/*  59 */               TextureAtlasSprite textureatlassprite = texturemap.getSpriteSafe("minecraft:blocks/" + s3);
/*     */               
/*  61 */               if (textureatlassprite == null) {
/*  62 */                 Config.warn("Natural Textures: Texture not found: \"" + s + "\" line: " + s2);
/*     */               } else {
/*  64 */                 int j = textureatlassprite.getIndexInMap();
/*     */                 
/*  66 */                 if (j < 0) {
/*  67 */                   Config.warn("Natural Textures: Invalid \"" + s + "\" line: " + s2);
/*     */                 } else {
/*  69 */                   if (flag && !Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/" + s3 + ".png"))) {
/*     */                     return;
/*     */                   }
/*     */                   
/*  73 */                   NaturalProperties naturalproperties = new NaturalProperties(s4);
/*     */                   
/*  75 */                   if (naturalproperties.isValid()) {
/*  76 */                     while (arraylist.size() <= j) {
/*  77 */                       arraylist.add(null);
/*     */                     }
/*     */                     
/*  80 */                     arraylist.set(j, naturalproperties);
/*  81 */                     Config.dbg("NaturalTextures: " + s3 + " = " + s4);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  89 */         propertiesByIndex = arraylist.<NaturalProperties>toArray(new NaturalProperties[arraylist.size()]);
/*  90 */       } catch (FileNotFoundException var17) {
/*  91 */         Config.warn("NaturalTextures: configuration \"" + s + "\" not found");
/*     */         return;
/*  93 */       } catch (Exception exception) {
/*  94 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static BakedQuad getNaturalTexture(BlockPos blockPosIn, BakedQuad quad) {
/* 100 */     TextureAtlasSprite textureatlassprite = quad.getSprite();
/*     */     
/* 102 */     if (textureatlassprite == null) {
/* 103 */       return quad;
/*     */     }
/* 105 */     NaturalProperties naturalproperties = getNaturalProperties(textureatlassprite);
/*     */     
/* 107 */     if (naturalproperties == null) {
/* 108 */       return quad;
/*     */     }
/* 110 */     int i = ConnectedTextures.getSide(quad.getFace());
/* 111 */     int j = Config.getRandom(blockPosIn, i);
/* 112 */     int k = 0;
/* 113 */     boolean flag = false;
/*     */     
/* 115 */     if (naturalproperties.rotation > 1) {
/* 116 */       k = j & 0x3;
/*     */     }
/*     */     
/* 119 */     if (naturalproperties.rotation == 2) {
/* 120 */       k = k / 2 * 2;
/*     */     }
/*     */     
/* 123 */     if (naturalproperties.flip) {
/* 124 */       flag = ((j & 0x4) != 0);
/*     */     }
/*     */     
/* 127 */     return naturalproperties.getQuad(quad, k, flag);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static NaturalProperties getNaturalProperties(TextureAtlasSprite icon) {
/* 133 */     if (!(icon instanceof TextureAtlasSprite)) {
/* 134 */       return null;
/*     */     }
/* 136 */     int i = icon.getIndexInMap();
/*     */     
/* 138 */     if (i >= 0 && i < propertiesByIndex.length) {
/* 139 */       NaturalProperties naturalproperties = propertiesByIndex[i];
/* 140 */       return naturalproperties;
/*     */     } 
/* 142 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\NaturalTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */