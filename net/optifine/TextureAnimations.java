/*     */ package net.optifine;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.ResUtils;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ 
/*     */ public class TextureAnimations
/*     */ {
/*  25 */   private static TextureAnimation[] textureAnimations = null;
/*  26 */   private static int countAnimationsActive = 0;
/*  27 */   private static int frameCountAnimations = 0;
/*     */   
/*     */   public static void reset() {
/*  30 */     textureAnimations = null;
/*     */   }
/*     */   
/*     */   public static void update() {
/*  34 */     textureAnimations = null;
/*  35 */     countAnimationsActive = 0;
/*  36 */     IResourcePack[] airesourcepack = Config.getResourcePacks();
/*  37 */     textureAnimations = getTextureAnimations(airesourcepack);
/*  38 */     updateAnimations();
/*     */   }
/*     */   
/*     */   public static void updateAnimations() {
/*  42 */     if (textureAnimations != null && Config.isAnimatedTextures()) {
/*  43 */       int i = 0;
/*     */       
/*  45 */       for (int j = 0; j < textureAnimations.length; j++) {
/*  46 */         TextureAnimation textureanimation = textureAnimations[j];
/*  47 */         textureanimation.updateTexture();
/*     */         
/*  49 */         if (textureanimation.isActive()) {
/*  50 */           i++;
/*     */         }
/*     */       } 
/*     */       
/*  54 */       int k = (Config.getMinecraft()).entityRenderer.frameCount;
/*     */       
/*  56 */       if (k != frameCountAnimations) {
/*  57 */         countAnimationsActive = i;
/*  58 */         frameCountAnimations = k;
/*     */       } 
/*     */       
/*  61 */       if (SmartAnimations.isActive()) {
/*  62 */         SmartAnimations.resetTexturesRendered();
/*     */       }
/*     */     } else {
/*  65 */       countAnimationsActive = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static TextureAnimation[] getTextureAnimations(IResourcePack[] rps) {
/*  70 */     List list = new ArrayList();
/*     */     
/*  72 */     for (int i = 0; i < rps.length; i++) {
/*  73 */       IResourcePack iresourcepack = rps[i];
/*  74 */       TextureAnimation[] atextureanimation = getTextureAnimations(iresourcepack);
/*     */       
/*  76 */       if (atextureanimation != null) {
/*  77 */         list.addAll(Arrays.asList(atextureanimation));
/*     */       }
/*     */     } 
/*     */     
/*  81 */     TextureAnimation[] atextureanimation1 = (TextureAnimation[])list.toArray((Object[])new TextureAnimation[list.size()]);
/*  82 */     return atextureanimation1;
/*     */   }
/*     */   
/*     */   private static TextureAnimation[] getTextureAnimations(IResourcePack rp) {
/*  86 */     String[] astring = ResUtils.collectFiles(rp, "mcpatcher/anim/", ".properties", null);
/*     */     
/*  88 */     if (astring.length <= 0) {
/*  89 */       return null;
/*     */     }
/*  91 */     List<TextureAnimation> list = new ArrayList();
/*     */     
/*  93 */     for (int i = 0; i < astring.length; i++) {
/*  94 */       String s = astring[i];
/*  95 */       Config.dbg("Texture animation: " + s);
/*     */       
/*     */       try {
/*  98 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/*  99 */         InputStream inputstream = rp.getInputStream(resourcelocation);
/* 100 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 101 */         propertiesOrdered.load(inputstream);
/* 102 */         inputstream.close();
/* 103 */         TextureAnimation textureanimation = makeTextureAnimation((Properties)propertiesOrdered, resourcelocation);
/*     */         
/* 105 */         if (textureanimation != null) {
/* 106 */           ResourceLocation resourcelocation1 = new ResourceLocation(textureanimation.getDstTex());
/*     */           
/* 108 */           if (Config.getDefiningResourcePack(resourcelocation1) != rp) {
/* 109 */             Config.dbg("Skipped: " + s + ", target texture not loaded from same resource pack");
/*     */           } else {
/* 111 */             list.add(textureanimation);
/*     */           } 
/*     */         } 
/* 114 */       } catch (FileNotFoundException filenotfoundexception) {
/* 115 */         Config.warn("File not found: " + filenotfoundexception.getMessage());
/* 116 */       } catch (IOException ioexception) {
/* 117 */         ioexception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     TextureAnimation[] atextureanimation = list.<TextureAnimation>toArray(new TextureAnimation[list.size()]);
/* 122 */     return atextureanimation;
/*     */   }
/*     */ 
/*     */   
/*     */   private static TextureAnimation makeTextureAnimation(Properties props, ResourceLocation propLoc) {
/* 127 */     String s = props.getProperty("from");
/* 128 */     String s1 = props.getProperty("to");
/* 129 */     int i = Config.parseInt(props.getProperty("x"), -1);
/* 130 */     int j = Config.parseInt(props.getProperty("y"), -1);
/* 131 */     int k = Config.parseInt(props.getProperty("w"), -1);
/* 132 */     int l = Config.parseInt(props.getProperty("h"), -1);
/*     */     
/* 134 */     if (s != null && s1 != null) {
/* 135 */       if (i >= 0 && j >= 0 && k >= 0 && l >= 0) {
/* 136 */         s = s.trim();
/* 137 */         s1 = s1.trim();
/* 138 */         String s2 = TextureUtils.getBasePath(propLoc.getResourcePath());
/* 139 */         s = TextureUtils.fixResourcePath(s, s2);
/* 140 */         s1 = TextureUtils.fixResourcePath(s1, s2);
/* 141 */         byte[] abyte = getCustomTextureData(s, k);
/*     */         
/* 143 */         if (abyte == null) {
/* 144 */           Config.warn("TextureAnimation: Source texture not found: " + s1);
/* 145 */           return null;
/*     */         } 
/* 147 */         int i1 = abyte.length / 4;
/* 148 */         int j1 = i1 / k * l;
/* 149 */         int k1 = j1 * k * l;
/*     */         
/* 151 */         if (i1 != k1) {
/* 152 */           Config.warn("TextureAnimation: Source texture has invalid number of frames: " + s + ", frames: " + (i1 / (k * l)));
/* 153 */           return null;
/*     */         } 
/* 155 */         ResourceLocation resourcelocation = new ResourceLocation(s1);
/*     */         
/*     */         try {
/* 158 */           InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */           
/* 160 */           if (inputstream == null) {
/* 161 */             Config.warn("TextureAnimation: Target texture not found: " + s1);
/* 162 */             return null;
/*     */           } 
/* 164 */           BufferedImage bufferedimage = readTextureImage(inputstream);
/*     */           
/* 166 */           if (i + k <= bufferedimage.getWidth() && j + l <= bufferedimage.getHeight()) {
/* 167 */             TextureAnimation textureanimation = new TextureAnimation(s, abyte, s1, resourcelocation, i, j, k, l, props);
/* 168 */             return textureanimation;
/*     */           } 
/* 170 */           Config.warn("TextureAnimation: Animation coordinates are outside the target texture: " + s1);
/* 171 */           return null;
/*     */         
/*     */         }
/* 174 */         catch (IOException var17) {
/* 175 */           Config.warn("TextureAnimation: Target texture not found: " + s1);
/* 176 */           return null;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 181 */       Config.warn("TextureAnimation: Invalid coordinates");
/* 182 */       return null;
/*     */     } 
/*     */     
/* 185 */     Config.warn("TextureAnimation: Source or target texture not specified");
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte[] getCustomTextureData(String imagePath, int tileWidth) {
/* 191 */     byte[] abyte = loadImage(imagePath, tileWidth);
/*     */     
/* 193 */     if (abyte == null) {
/* 194 */       abyte = loadImage("/anim" + imagePath, tileWidth);
/*     */     }
/*     */     
/* 197 */     return abyte;
/*     */   }
/*     */   
/*     */   private static byte[] loadImage(String name, int targetWidth) {
/* 201 */     GameSettings gamesettings = Config.getGameSettings();
/*     */     
/*     */     try {
/* 204 */       ResourceLocation resourcelocation = new ResourceLocation(name);
/* 205 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 207 */       if (inputstream == null) {
/* 208 */         return null;
/*     */       }
/* 210 */       BufferedImage bufferedimage = readTextureImage(inputstream);
/* 211 */       inputstream.close();
/*     */       
/* 213 */       if (bufferedimage == null) {
/* 214 */         return null;
/*     */       }
/* 216 */       if (targetWidth > 0 && bufferedimage.getWidth() != targetWidth) {
/* 217 */         double d0 = (bufferedimage.getHeight() / bufferedimage.getWidth());
/* 218 */         int j = (int)(targetWidth * d0);
/* 219 */         bufferedimage = scaleBufferedImage(bufferedimage, targetWidth, j);
/*     */       } 
/*     */       
/* 222 */       int k2 = bufferedimage.getWidth();
/* 223 */       int i = bufferedimage.getHeight();
/* 224 */       int[] aint = new int[k2 * i];
/* 225 */       byte[] abyte = new byte[k2 * i * 4];
/* 226 */       bufferedimage.getRGB(0, 0, k2, i, aint, 0, k2);
/*     */       
/* 228 */       for (int k = 0; k < aint.length; k++) {
/* 229 */         int l = aint[k] >> 24 & 0xFF;
/* 230 */         int i1 = aint[k] >> 16 & 0xFF;
/* 231 */         int j1 = aint[k] >> 8 & 0xFF;
/* 232 */         int k1 = aint[k] & 0xFF;
/*     */         
/* 234 */         if (gamesettings != null && gamesettings.anaglyph) {
/* 235 */           int l1 = (i1 * 30 + j1 * 59 + k1 * 11) / 100;
/* 236 */           int i2 = (i1 * 30 + j1 * 70) / 100;
/* 237 */           int j2 = (i1 * 30 + k1 * 70) / 100;
/* 238 */           i1 = l1;
/* 239 */           j1 = i2;
/* 240 */           k1 = j2;
/*     */         } 
/*     */         
/* 243 */         abyte[k * 4 + 0] = (byte)i1;
/* 244 */         abyte[k * 4 + 1] = (byte)j1;
/* 245 */         abyte[k * 4 + 2] = (byte)k1;
/* 246 */         abyte[k * 4 + 3] = (byte)l;
/*     */       } 
/*     */       
/* 249 */       return abyte;
/*     */     
/*     */     }
/* 252 */     catch (FileNotFoundException var18) {
/* 253 */       return null;
/* 254 */     } catch (Exception exception) {
/* 255 */       exception.printStackTrace();
/* 256 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static BufferedImage readTextureImage(InputStream par1InputStream) throws IOException {
/* 261 */     BufferedImage bufferedimage = ImageIO.read(par1InputStream);
/* 262 */     par1InputStream.close();
/* 263 */     return bufferedimage;
/*     */   }
/*     */   
/*     */   private static BufferedImage scaleBufferedImage(BufferedImage image, int width, int height) {
/* 267 */     BufferedImage bufferedimage = new BufferedImage(width, height, 2);
/* 268 */     Graphics2D graphics2d = bufferedimage.createGraphics();
/* 269 */     graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 270 */     graphics2d.drawImage(image, 0, 0, width, height, null);
/* 271 */     return bufferedimage;
/*     */   }
/*     */   
/*     */   public static int getCountAnimations() {
/* 275 */     return (textureAnimations == null) ? 0 : textureAnimations.length;
/*     */   }
/*     */   
/*     */   public static int getCountAnimationsActive() {
/* 279 */     return countAnimationsActive;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\TextureAnimations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */