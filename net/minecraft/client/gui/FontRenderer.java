/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import client.Client;
/*     */ import com.ibm.icu.text.ArabicShaping;
/*     */ import com.ibm.icu.text.ArabicShapingException;
/*     */ import com.ibm.icu.text.Bidi;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.CustomColors;
/*     */ import net.optifine.render.GlBlendState;
/*     */ import net.optifine.util.FontUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontRenderer
/*     */   implements IResourceManagerReloadListener
/*     */ {
/*  38 */   private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private final int[] charWidth = new int[256];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public int FONT_HEIGHT = 9;
/*  49 */   public Random fontRandom = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private byte[] glyphWidth = new byte[65536];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private int[] colorCode = new int[32];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceLocation locationFontTexture;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final TextureManager renderEngine;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float posX;
/*     */ 
/*     */ 
/*     */   
/*     */   private float posY;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean unicodeFlag;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean bidiFlag;
/*     */ 
/*     */ 
/*     */   
/*     */   private float red;
/*     */ 
/*     */ 
/*     */   
/*     */   private float blue;
/*     */ 
/*     */ 
/*     */   
/*     */   private float green;
/*     */ 
/*     */ 
/*     */   
/*     */   private float alpha;
/*     */ 
/*     */ 
/*     */   
/*     */   private int textColor;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean randomStyle;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean boldStyle;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean italicStyle;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean underlineStyle;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean strikethroughStyle;
/*     */ 
/*     */ 
/*     */   
/*     */   public GameSettings gameSettings;
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation locationFontTextureBase;
/*     */ 
/*     */ 
/*     */   
/* 139 */   public float offsetBold = 1.0F;
/* 140 */   private float[] charWidthFloat = new float[256];
/*     */   private boolean blend = false;
/* 142 */   private GlBlendState oldBlendState = new GlBlendState();
/*     */   
/*     */   public FontRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
/* 145 */     this.gameSettings = gameSettingsIn;
/* 146 */     this.locationFontTextureBase = location;
/* 147 */     this.locationFontTexture = location;
/* 148 */     this.renderEngine = textureManagerIn;
/* 149 */     this.unicodeFlag = unicode;
/* 150 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/* 151 */     bindTexture(this.locationFontTexture);
/*     */     
/* 153 */     for (int i = 0; i < 32; i++) {
/* 154 */       int j = (i >> 3 & 0x1) * 85;
/* 155 */       int k = (i >> 2 & 0x1) * 170 + j;
/* 156 */       int l = (i >> 1 & 0x1) * 170 + j;
/* 157 */       int i1 = (i >> 0 & 0x1) * 170 + j;
/*     */       
/* 159 */       if (i == 6) {
/* 160 */         k += 85;
/*     */       }
/*     */       
/* 163 */       if (gameSettingsIn.anaglyph) {
/* 164 */         int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
/* 165 */         int k1 = (k * 30 + l * 70) / 100;
/* 166 */         int l1 = (k * 30 + i1 * 70) / 100;
/* 167 */         k = j1;
/* 168 */         l = k1;
/* 169 */         i1 = l1;
/*     */       } 
/*     */       
/* 172 */       if (i >= 16) {
/* 173 */         k /= 4;
/* 174 */         l /= 4;
/* 175 */         i1 /= 4;
/*     */       } 
/*     */       
/* 178 */       this.colorCode[i] = (k & 0xFF) << 16 | (l & 0xFF) << 8 | i1 & 0xFF;
/*     */     } 
/*     */     
/* 181 */     readGlyphSizes();
/*     */   }
/*     */   
/*     */   public void onResourceManagerReload(IResourceManager resourceManager) {
/* 185 */     this.locationFontTexture = FontUtils.getHdFontLocation(this.locationFontTextureBase);
/*     */     
/* 187 */     for (int i = 0; i < unicodePageLocations.length; i++) {
/* 188 */       unicodePageLocations[i] = null;
/*     */     }
/*     */     
/* 191 */     readFontTexture();
/* 192 */     readGlyphSizes();
/*     */   }
/*     */ 
/*     */   
/*     */   private void readFontTexture() {
/*     */     BufferedImage bufferedimage;
/*     */     try {
/* 199 */       bufferedimage = TextureUtil.readBufferedImage(getResourceInputStream(this.locationFontTexture));
/* 200 */     } catch (IOException ioexception1) {
/* 201 */       throw new RuntimeException(ioexception1);
/*     */     } 
/*     */     
/* 204 */     Properties properties = FontUtils.readFontProperties(this.locationFontTexture);
/* 205 */     this.blend = FontUtils.readBoolean(properties, "blend", false);
/* 206 */     int i = bufferedimage.getWidth();
/* 207 */     int j = bufferedimage.getHeight();
/* 208 */     int k = i / 16;
/* 209 */     int l = j / 16;
/* 210 */     float f = i / 128.0F;
/* 211 */     float f1 = Config.limit(f, 1.0F, 2.0F);
/* 212 */     this.offsetBold = 1.0F / f1;
/* 213 */     float f2 = FontUtils.readFloat(properties, "offsetBold", -1.0F);
/*     */     
/* 215 */     if (f2 >= 0.0F) {
/* 216 */       this.offsetBold = f2;
/*     */     }
/*     */     
/* 219 */     int[] aint = new int[i * j];
/* 220 */     bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
/*     */     
/* 222 */     for (int i1 = 0; i1 < 256; i1++) {
/* 223 */       int j1 = i1 % 16;
/* 224 */       int k1 = i1 / 16;
/* 225 */       int l1 = 0;
/*     */       
/* 227 */       for (l1 = k - 1; l1 >= 0; l1--) {
/* 228 */         int i2 = j1 * k + l1;
/* 229 */         boolean flag = true;
/*     */         
/* 231 */         for (int j2 = 0; j2 < l && flag; j2++) {
/* 232 */           int k2 = (k1 * l + j2) * i;
/* 233 */           int l2 = aint[i2 + k2];
/* 234 */           int i3 = l2 >> 24 & 0xFF;
/*     */           
/* 236 */           if (i3 > 16) {
/* 237 */             flag = false;
/*     */           }
/*     */         } 
/*     */         
/* 241 */         if (!flag) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 246 */       if (i1 == 65) {
/* 247 */         i1 = i1;
/*     */       }
/*     */       
/* 250 */       if (i1 == 32) {
/* 251 */         if (k <= 8) {
/* 252 */           l1 = (int)(2.0F * f);
/*     */         } else {
/* 254 */           l1 = (int)(1.5F * f);
/*     */         } 
/*     */       }
/*     */       
/* 258 */       this.charWidthFloat[i1] = (l1 + 1) / f + 1.0F;
/*     */     } 
/*     */     
/* 261 */     FontUtils.readCustomCharWidths(properties, this.charWidthFloat);
/*     */     
/* 263 */     for (int j3 = 0; j3 < this.charWidth.length; j3++) {
/* 264 */       this.charWidth[j3] = Math.round(this.charWidthFloat[j3]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void readGlyphSizes() {
/* 269 */     InputStream inputstream = null;
/*     */     
/*     */     try {
/* 272 */       inputstream = getResourceInputStream(new ResourceLocation("font/glyph_sizes.bin"));
/* 273 */       inputstream.read(this.glyphWidth);
/* 274 */     } catch (IOException ioexception) {
/* 275 */       throw new RuntimeException(ioexception);
/*     */     } finally {
/* 277 */       IOUtils.closeQuietly(inputstream);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float renderChar(char ch, boolean italic) {
/* 285 */     if (ch != ' ' && ch != ' ') {
/* 286 */       int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(ch);
/* 287 */       return (i != -1 && !this.unicodeFlag) ? renderDefaultChar(i, italic) : renderUnicodeChar(ch, italic);
/*     */     } 
/* 289 */     return !this.unicodeFlag ? this.charWidthFloat[ch] : 4.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float renderDefaultChar(int ch, boolean italic) {
/* 297 */     int i = ch % 16 * 8;
/* 298 */     int j = ch / 16 * 8;
/* 299 */     int k = italic ? 1 : 0;
/* 300 */     bindTexture(this.locationFontTexture);
/* 301 */     float f = this.charWidthFloat[ch];
/* 302 */     float f1 = 7.99F;
/* 303 */     GL11.glBegin(5);
/* 304 */     GL11.glTexCoord2f(i / 128.0F, j / 128.0F);
/* 305 */     GL11.glVertex3f(this.posX + k, this.posY, 0.0F);
/* 306 */     GL11.glTexCoord2f(i / 128.0F, (j + 7.99F) / 128.0F);
/* 307 */     GL11.glVertex3f(this.posX - k, this.posY + 7.99F, 0.0F);
/* 308 */     GL11.glTexCoord2f((i + f1 - 1.0F) / 128.0F, j / 128.0F);
/* 309 */     GL11.glVertex3f(this.posX + f1 - 1.0F + k, this.posY, 0.0F);
/* 310 */     GL11.glTexCoord2f((i + f1 - 1.0F) / 128.0F, (j + 7.99F) / 128.0F);
/* 311 */     GL11.glVertex3f(this.posX + f1 - 1.0F - k, this.posY + 7.99F, 0.0F);
/* 312 */     GL11.glEnd();
/* 313 */     return f;
/*     */   }
/*     */   
/*     */   private ResourceLocation getUnicodePageLocation(int page) {
/* 317 */     if (unicodePageLocations[page] == null) {
/* 318 */       unicodePageLocations[page] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", new Object[] { Integer.valueOf(page) }));
/* 319 */       unicodePageLocations[page] = FontUtils.getHdFontLocation(unicodePageLocations[page]);
/*     */     } 
/*     */     
/* 322 */     return unicodePageLocations[page];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadGlyphTexture(int page) {
/* 329 */     bindTexture(getUnicodePageLocation(page));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float renderUnicodeChar(char ch, boolean italic) {
/* 336 */     if (this.glyphWidth[ch] == 0) {
/* 337 */       return 0.0F;
/*     */     }
/* 339 */     int i = ch / 256;
/* 340 */     loadGlyphTexture(i);
/* 341 */     int j = this.glyphWidth[ch] >>> 4;
/* 342 */     int k = this.glyphWidth[ch] & 0xF;
/* 343 */     float f = j;
/* 344 */     float f1 = (k + 1);
/* 345 */     float f2 = (ch % 16 * 16) + f;
/* 346 */     float f3 = ((ch & 0xFF) / 16 * 16);
/* 347 */     float f4 = f1 - f - 0.02F;
/* 348 */     float f5 = italic ? 1.0F : 0.0F;
/* 349 */     GL11.glBegin(5);
/* 350 */     GL11.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
/* 351 */     GL11.glVertex3f(this.posX + f5, this.posY, 0.0F);
/* 352 */     GL11.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
/* 353 */     GL11.glVertex3f(this.posX - f5, this.posY + 7.99F, 0.0F);
/* 354 */     GL11.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
/* 355 */     GL11.glVertex3f(this.posX + f4 / 2.0F + f5, this.posY, 0.0F);
/* 356 */     GL11.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
/* 357 */     GL11.glVertex3f(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F);
/* 358 */     GL11.glEnd();
/* 359 */     return (f1 - f) / 2.0F + 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int drawStringWithShadow(String text, float x, float y, int color) {
/* 367 */     return drawString(text, x, y, color, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int drawString(String text, int x, int y, int color) {
/* 374 */     return drawString(text, x, y, color, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int drawString(String text, float x, float y, int color, boolean dropShadow) {
/*     */     int i;
/* 381 */     enableAlpha();
/*     */     
/* 383 */     if (this.blend) {
/* 384 */       GlStateManager.getBlendState(this.oldBlendState);
/* 385 */       GlStateManager.enableBlend();
/* 386 */       GlStateManager.blendFunc(770, 771);
/*     */     } 
/*     */     
/* 389 */     resetStyles();
/*     */ 
/*     */     
/* 392 */     if (dropShadow) {
/* 393 */       i = renderString(text, x + 1.0F, y + 1.0F, color, true);
/* 394 */       i = Math.max(i, renderString(text, x, y, color, false));
/*     */     } else {
/* 396 */       i = renderString(text, x, y, color, false);
/*     */     } 
/*     */     
/* 399 */     if (this.blend) {
/* 400 */       GlStateManager.setBlendState(this.oldBlendState);
/*     */     }
/*     */     
/* 403 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String bidiReorder(String text) {
/*     */     try {
/* 411 */       Bidi bidi = new Bidi((new ArabicShaping(8)).shape(text), 127);
/* 412 */       bidi.setReorderingMode(0);
/* 413 */       return bidi.writeReordered(2);
/* 414 */     } catch (ArabicShapingException var3) {
/* 415 */       return text;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resetStyles() {
/* 423 */     this.randomStyle = false;
/* 424 */     this.boldStyle = false;
/* 425 */     this.italicStyle = false;
/* 426 */     this.underlineStyle = false;
/* 427 */     this.strikethroughStyle = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderStringAtPos(String text, boolean shadow) {
/* 434 */     for (int i = 0; i < text.length(); i++) {
/* 435 */       char c0 = text.charAt(i);
/*     */       
/* 437 */       if (c0 == '§' && i + 1 < text.length()) {
/* 438 */         int l = "0123456789abcdefklmnors".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
/*     */         
/* 440 */         if (l < 16) {
/* 441 */           this.randomStyle = false;
/* 442 */           this.boldStyle = false;
/* 443 */           this.strikethroughStyle = false;
/* 444 */           this.underlineStyle = false;
/* 445 */           this.italicStyle = false;
/*     */           
/* 447 */           if (l < 0 || l > 15) {
/* 448 */             l = 15;
/*     */           }
/*     */           
/* 451 */           if (shadow) {
/* 452 */             l += 16;
/*     */           }
/*     */           
/* 455 */           int i1 = this.colorCode[l];
/*     */           
/* 457 */           if (Config.isCustomColors()) {
/* 458 */             i1 = CustomColors.getTextColor(l, i1);
/*     */           }
/*     */           
/* 461 */           this.textColor = i1;
/* 462 */           setColor((i1 >> 16) / 255.0F, (i1 >> 8 & 0xFF) / 255.0F, (i1 & 0xFF) / 255.0F, this.alpha);
/* 463 */         } else if (l == 16) {
/* 464 */           this.randomStyle = true;
/* 465 */         } else if (l == 17) {
/* 466 */           this.boldStyle = true;
/* 467 */         } else if (l == 18) {
/* 468 */           this.strikethroughStyle = true;
/* 469 */         } else if (l == 19) {
/* 470 */           this.underlineStyle = true;
/* 471 */         } else if (l == 20) {
/* 472 */           this.italicStyle = true;
/* 473 */         } else if (l == 21) {
/* 474 */           this.randomStyle = false;
/* 475 */           this.boldStyle = false;
/* 476 */           this.strikethroughStyle = false;
/* 477 */           this.underlineStyle = false;
/* 478 */           this.italicStyle = false;
/* 479 */           setColor(this.red, this.blue, this.green, this.alpha);
/* 480 */         }  if (l == 22 && !shadow) {
/* 481 */           setColor((Client.getInstance()).blockOutLineRed, (Client.getInstance()).blockOutLineGreen, (Client.getInstance()).blockOutLineBlue, this.alpha);
/*     */         }
/*     */         
/* 484 */         i++;
/*     */       } else {
/* 486 */         int j = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(c0);
/*     */         
/* 488 */         if (this.randomStyle && j != -1) {
/* 489 */           char c1; int k = getCharWidth(c0);
/*     */ 
/*     */           
/*     */           do {
/* 493 */             j = this.fontRandom.nextInt("ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".length());
/* 494 */             c1 = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".charAt(j);
/*     */           }
/* 496 */           while (k != getCharWidth(c1));
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 501 */           c0 = c1;
/*     */         } 
/*     */         
/* 504 */         float f1 = (j != -1 && !this.unicodeFlag) ? this.offsetBold : 0.5F;
/* 505 */         boolean flag = ((c0 == '\000' || j == -1 || this.unicodeFlag) && shadow);
/*     */         
/* 507 */         if (flag) {
/* 508 */           this.posX -= f1;
/* 509 */           this.posY -= f1;
/*     */         } 
/*     */         
/* 512 */         float f = renderChar(c0, this.italicStyle);
/*     */         
/* 514 */         if (flag) {
/* 515 */           this.posX += f1;
/* 516 */           this.posY += f1;
/*     */         } 
/*     */         
/* 519 */         if (this.boldStyle) {
/* 520 */           this.posX += f1;
/*     */           
/* 522 */           if (flag) {
/* 523 */             this.posX -= f1;
/* 524 */             this.posY -= f1;
/*     */           } 
/*     */           
/* 527 */           renderChar(c0, this.italicStyle);
/* 528 */           this.posX -= f1;
/*     */           
/* 530 */           if (flag) {
/* 531 */             this.posX += f1;
/* 532 */             this.posY += f1;
/*     */           } 
/*     */           
/* 535 */           f += f1;
/*     */         } 
/*     */         
/* 538 */         doDraw(f);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void doDraw(float p_doDraw_1_) {
/* 544 */     if (this.strikethroughStyle) {
/* 545 */       Tessellator tessellator = Tessellator.getInstance();
/* 546 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 547 */       GlStateManager.disableTexture2D();
/* 548 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/* 549 */       worldrenderer.pos(this.posX, (this.posY + (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
/* 550 */       worldrenderer.pos((this.posX + p_doDraw_1_), (this.posY + (this.FONT_HEIGHT / 2)), 0.0D).endVertex();
/* 551 */       worldrenderer.pos((this.posX + p_doDraw_1_), (this.posY + (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
/* 552 */       worldrenderer.pos(this.posX, (this.posY + (this.FONT_HEIGHT / 2) - 1.0F), 0.0D).endVertex();
/* 553 */       tessellator.draw();
/* 554 */       GlStateManager.enableTexture2D();
/*     */     } 
/*     */     
/* 557 */     if (this.underlineStyle) {
/* 558 */       Tessellator tessellator1 = Tessellator.getInstance();
/* 559 */       WorldRenderer worldrenderer1 = tessellator1.getWorldRenderer();
/* 560 */       GlStateManager.disableTexture2D();
/* 561 */       worldrenderer1.begin(7, DefaultVertexFormats.POSITION);
/* 562 */       int i = this.underlineStyle ? -1 : 0;
/* 563 */       worldrenderer1.pos((this.posX + i), (this.posY + this.FONT_HEIGHT), 0.0D).endVertex();
/* 564 */       worldrenderer1.pos((this.posX + p_doDraw_1_), (this.posY + this.FONT_HEIGHT), 0.0D).endVertex();
/* 565 */       worldrenderer1.pos((this.posX + p_doDraw_1_), (this.posY + this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
/* 566 */       worldrenderer1.pos((this.posX + i), (this.posY + this.FONT_HEIGHT - 1.0F), 0.0D).endVertex();
/* 567 */       tessellator1.draw();
/* 568 */       GlStateManager.enableTexture2D();
/*     */     } 
/*     */     
/* 571 */     this.posX += p_doDraw_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int renderStringAligned(String text, int x, int y, int width, int color, boolean dropShadow) {
/* 578 */     if (this.bidiFlag) {
/* 579 */       int i = getStringWidth(bidiReorder(text));
/* 580 */       x = x + width - i;
/*     */     } 
/*     */     
/* 583 */     return renderString(text, x, y, color, dropShadow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int renderString(String text, float x, float y, int color, boolean dropShadow) {
/* 590 */     if (text == null) {
/* 591 */       return 0;
/*     */     }
/* 593 */     if (this.bidiFlag) {
/* 594 */       text = bidiReorder(text);
/*     */     }
/*     */     
/* 597 */     if ((color & 0xFC000000) == 0) {
/* 598 */       color |= 0xFF000000;
/*     */     }
/*     */     
/* 601 */     if (dropShadow) {
/* 602 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*     */     }
/*     */     
/* 605 */     this.red = (color >> 16 & 0xFF) / 255.0F;
/* 606 */     this.blue = (color >> 8 & 0xFF) / 255.0F;
/* 607 */     this.green = (color & 0xFF) / 255.0F;
/* 608 */     this.alpha = (color >> 24 & 0xFF) / 255.0F;
/* 609 */     setColor(this.red, this.blue, this.green, this.alpha);
/* 610 */     this.posX = x;
/* 611 */     this.posY = y;
/* 612 */     renderStringAtPos(text, dropShadow);
/* 613 */     return (int)this.posX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStringWidth(String text) {
/* 621 */     if (text == null) {
/* 622 */       return 0;
/*     */     }
/* 624 */     float f = 0.0F;
/* 625 */     boolean flag = false;
/*     */     
/* 627 */     for (int i = 0; i < text.length(); i++) {
/* 628 */       char c0 = text.charAt(i);
/* 629 */       float f1 = getCharWidthFloat(c0);
/*     */       
/* 631 */       if (f1 < 0.0F && i < text.length() - 1) {
/* 632 */         i++;
/* 633 */         c0 = text.charAt(i);
/*     */         
/* 635 */         if (c0 != 'l' && c0 != 'L') {
/* 636 */           if (c0 == 'r' || c0 == 'R') {
/* 637 */             flag = false;
/*     */           }
/*     */         } else {
/* 640 */           flag = true;
/*     */         } 
/*     */         
/* 643 */         f1 = 0.0F;
/*     */       } 
/*     */       
/* 646 */       f += f1;
/*     */       
/* 648 */       if (flag && f1 > 0.0F) {
/* 649 */         f += this.unicodeFlag ? 1.0F : this.offsetBold;
/*     */       }
/*     */     } 
/*     */     
/* 653 */     return Math.round(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCharWidth(char character) {
/* 661 */     return Math.round(getCharWidthFloat(character));
/*     */   }
/*     */   
/*     */   private float getCharWidthFloat(char p_getCharWidthFloat_1_) {
/* 665 */     if (p_getCharWidthFloat_1_ == '§')
/* 666 */       return -1.0F; 
/* 667 */     if (p_getCharWidthFloat_1_ != ' ' && p_getCharWidthFloat_1_ != ' ') {
/* 668 */       int i = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\000\000\000\000\000\000\000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\000".indexOf(p_getCharWidthFloat_1_);
/*     */       
/* 670 */       if (p_getCharWidthFloat_1_ > '\000' && i != -1 && !this.unicodeFlag)
/* 671 */         return this.charWidthFloat[i]; 
/* 672 */       if (this.glyphWidth[p_getCharWidthFloat_1_] != 0) {
/* 673 */         int j = this.glyphWidth[p_getCharWidthFloat_1_] >>> 4;
/* 674 */         int k = this.glyphWidth[p_getCharWidthFloat_1_] & 0xF;
/*     */         
/* 676 */         if (k > 7) {
/* 677 */           k = 15;
/* 678 */           j = 0;
/*     */         } 
/*     */         
/* 681 */         k++;
/* 682 */         return ((k - j) / 2 + 1);
/*     */       } 
/* 684 */       return 0.0F;
/*     */     } 
/*     */     
/* 687 */     return this.charWidthFloat[32];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String trimStringToWidth(String text, int width) {
/* 695 */     return trimStringToWidth(text, width, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String trimStringToWidth(String text, int width, boolean reverse) {
/* 702 */     StringBuilder stringbuilder = new StringBuilder();
/* 703 */     float f = 0.0F;
/* 704 */     int i = reverse ? (text.length() - 1) : 0;
/* 705 */     int j = reverse ? -1 : 1;
/* 706 */     boolean flag = false;
/* 707 */     boolean flag1 = false;
/*     */     
/* 709 */     for (int k = i; k >= 0 && k < text.length() && f < width; k += j) {
/* 710 */       char c0 = text.charAt(k);
/* 711 */       float f1 = getCharWidthFloat(c0);
/*     */       
/* 713 */       if (flag) {
/* 714 */         flag = false;
/*     */         
/* 716 */         if (c0 != 'l' && c0 != 'L') {
/* 717 */           if (c0 == 'r' || c0 == 'R') {
/* 718 */             flag1 = false;
/*     */           }
/*     */         } else {
/* 721 */           flag1 = true;
/*     */         } 
/* 723 */       } else if (f1 < 0.0F) {
/* 724 */         flag = true;
/*     */       } else {
/* 726 */         f += f1;
/*     */         
/* 728 */         if (flag1) {
/* 729 */           f++;
/*     */         }
/*     */       } 
/*     */       
/* 733 */       if (f > width) {
/*     */         break;
/*     */       }
/*     */       
/* 737 */       if (reverse) {
/* 738 */         stringbuilder.insert(0, c0);
/*     */       } else {
/* 740 */         stringbuilder.append(c0);
/*     */       } 
/*     */     } 
/*     */     
/* 744 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String trimStringNewline(String text) {
/* 751 */     while (text != null && text.endsWith("\n")) {
/* 752 */       text = text.substring(0, text.length() - 1);
/*     */     }
/*     */     
/* 755 */     return text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawSplitString(String str, int x, int y, int wrapWidth, int textColor) {
/* 762 */     if (this.blend) {
/* 763 */       GlStateManager.getBlendState(this.oldBlendState);
/* 764 */       GlStateManager.enableBlend();
/* 765 */       GlStateManager.blendFunc(770, 771);
/*     */     } 
/*     */     
/* 768 */     resetStyles();
/* 769 */     this.textColor = textColor;
/* 770 */     str = trimStringNewline(str);
/* 771 */     renderSplitString(str, x, y, wrapWidth, false);
/*     */     
/* 773 */     if (this.blend) {
/* 774 */       GlStateManager.setBlendState(this.oldBlendState);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderSplitString(String str, int x, int y, int wrapWidth, boolean addShadow) {
/* 783 */     for (String s : listFormattedStringToWidth(str, wrapWidth)) {
/* 784 */       renderStringAligned(s, x, y, wrapWidth, this.textColor, addShadow);
/* 785 */       y += this.FONT_HEIGHT;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int splitStringWidth(String str, int maxLength) {
/* 796 */     return this.FONT_HEIGHT * listFormattedStringToWidth(str, maxLength).size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnicodeFlag(boolean unicodeFlagIn) {
/* 804 */     this.unicodeFlag = unicodeFlagIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getUnicodeFlag() {
/* 812 */     return this.unicodeFlag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBidiFlag(boolean bidiFlagIn) {
/* 819 */     this.bidiFlag = bidiFlagIn;
/*     */   }
/*     */   
/*     */   public List<String> listFormattedStringToWidth(String str, int wrapWidth) {
/* 823 */     return Arrays.asList(wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String wrapFormattedStringToWidth(String str, int wrapWidth) {
/* 830 */     if (str.length() <= 1) {
/* 831 */       return str;
/*     */     }
/* 833 */     int i = sizeStringToWidth(str, wrapWidth);
/*     */     
/* 835 */     if (str.length() <= i) {
/* 836 */       return str;
/*     */     }
/* 838 */     String s = str.substring(0, i);
/* 839 */     char c0 = str.charAt(i);
/* 840 */     boolean flag = !(c0 != ' ' && c0 != '\n');
/* 841 */     String s1 = String.valueOf(getFormatFromString(s)) + str.substring(i + (flag ? 1 : 0));
/* 842 */     return String.valueOf(s) + "\n" + wrapFormattedStringToWidth(s1, wrapWidth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int sizeStringToWidth(String str, int wrapWidth) {
/* 851 */     int i = str.length();
/* 852 */     float f = 0.0F;
/* 853 */     int j = 0;
/* 854 */     int k = -1;
/*     */     
/* 856 */     for (boolean flag = false; j < i; j++) {
/* 857 */       char c0 = str.charAt(j);
/*     */       
/* 859 */       switch (c0) {
/*     */         case '\n':
/* 861 */           j--;
/*     */           break;
/*     */         
/*     */         case ' ':
/* 865 */           k = j;
/*     */         
/*     */         default:
/* 868 */           f += getCharWidth(c0);
/*     */           
/* 870 */           if (flag) {
/* 871 */             f++;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case '§':
/* 877 */           if (j < i - 1) {
/* 878 */             j++;
/* 879 */             char c1 = str.charAt(j);
/*     */             
/* 881 */             if (c1 != 'l' && c1 != 'L') {
/* 882 */               if (c1 == 'r' || c1 == 'R' || isFormatColor(c1))
/* 883 */                 flag = false; 
/*     */               break;
/*     */             } 
/* 886 */             flag = true;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 891 */       if (c0 == '\n') {
/*     */         
/* 893 */         k = ++j;
/*     */         
/*     */         break;
/*     */       } 
/* 897 */       if (Math.round(f) > wrapWidth) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 902 */     return (j != i && k != -1 && k < j) ? k : j;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isFormatColor(char colorChar) {
/* 909 */     return !((colorChar < '0' || colorChar > '9') && (colorChar < 'a' || colorChar > 'f') && (colorChar < 'A' || colorChar > 'F'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isFormatSpecial(char formatChar) {
/* 916 */     return !((formatChar < 'k' || formatChar > 'o') && (formatChar < 'K' || formatChar > 'O') && formatChar != 'r' && formatChar != 'R');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getFormatFromString(String text) {
/* 923 */     String s = "";
/* 924 */     int i = -1;
/* 925 */     int j = text.length();
/*     */     
/* 927 */     while ((i = text.indexOf('§', i + 1)) != -1) {
/* 928 */       if (i < j - 1) {
/* 929 */         char c0 = text.charAt(i + 1);
/*     */         
/* 931 */         if (isFormatColor(c0)) {
/* 932 */           s = "§" + c0; continue;
/* 933 */         }  if (isFormatSpecial(c0)) {
/* 934 */           s = String.valueOf(s) + "§" + c0;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 939 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBidiFlag() {
/* 946 */     return this.bidiFlag;
/*     */   }
/*     */   
/*     */   public int getColorCode(char character) {
/* 950 */     int i = "0123456789abcdef".indexOf(character);
/*     */     
/* 952 */     if (i >= 0 && i < this.colorCode.length) {
/* 953 */       int j = this.colorCode[i];
/*     */       
/* 955 */       if (Config.isCustomColors()) {
/* 956 */         j = CustomColors.getTextColor(i, j);
/*     */       }
/*     */       
/* 959 */       return j;
/*     */     } 
/* 961 */     return 16777215;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setColor(float p_setColor_1_, float p_setColor_2_, float p_setColor_3_, float p_setColor_4_) {
/* 966 */     GlStateManager.color(p_setColor_1_, p_setColor_2_, p_setColor_3_, p_setColor_4_);
/*     */   }
/*     */   
/*     */   protected void enableAlpha() {
/* 970 */     GlStateManager.enableAlpha();
/*     */   }
/*     */   
/*     */   protected void bindTexture(ResourceLocation p_bindTexture_1_) {
/* 974 */     this.renderEngine.bindTexture(p_bindTexture_1_);
/*     */   }
/*     */   
/*     */   protected InputStream getResourceInputStream(ResourceLocation p_getResourceInputStream_1_) throws IOException {
/* 978 */     return Minecraft.getMinecraft().getResourceManager().getResource(p_getResourceInputStream_1_).getInputStream();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\FontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */