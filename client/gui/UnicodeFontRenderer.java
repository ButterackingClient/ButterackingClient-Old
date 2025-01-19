/*     */ package client.gui;
/*     */ 
/*     */ import client.Client;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontFormatException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.UnicodeFont;
/*     */ import org.newdawn.slick.font.effects.ColorEffect;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnicodeFontRenderer
/*     */ {
/*     */   public static UnicodeFontRenderer getFontOnPC(String name, int size) {
/*  26 */     return getFontOnPC(name, size, 0);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRenderer getFontOnPC(String name, int size, int fontType) {
/*  30 */     return getFontOnPC(name, size, fontType, 0.0F);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRenderer getFontOnPC(String name, int size, int fontType, float kerning) {
/*  34 */     return getFontOnPC(name, size, fontType, kerning, 3.0F);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRenderer getFontOnPC(String name, int size, int fontType, float kerning, float antiAliasingFactor) {
/*  38 */     return new UnicodeFontRenderer(new Font(name, fontType, size), kerning, antiAliasingFactor);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRenderer getFontFromAssets(String name, int size) {
/*  42 */     return getFontOnPC(name, size, 0);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRenderer getFontFromAssets(String name, int size, int fontType) {
/*  46 */     return getFontOnPC(name, fontType, size, 0.0F);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRenderer getFontFromAssets(String name, int size, float kerning, int fontType) {
/*  50 */     return getFontFromAssets(name, size, fontType, kerning, 3.0F);
/*     */   }
/*     */   
/*     */   public static UnicodeFontRenderer getFontFromAssets(String name, int size, int fontType, float kerning, float antiAliasingFactor) {
/*  54 */     return new UnicodeFontRenderer(name, fontType, size, kerning, antiAliasingFactor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public final int FONT_HEIGHT = 9;
/*  61 */   private final int[] colorCodes = new int[32];
/*     */   private final float kerning;
/*  63 */   private final Map<String, Float> cachedStringWidth = new HashMap<>();
/*     */   private float antiAliasingFactor;
/*     */   private UnicodeFont unicodeFont;
/*     */   
/*     */   private UnicodeFontRenderer(String fontName, int fontType, float fontSize, float kerning, float antiAliasingFactor) {
/*  68 */     this.antiAliasingFactor = antiAliasingFactor;
/*     */     try {
/*  70 */       this.unicodeFont = new UnicodeFont(getFontByName(fontName).deriveFont(fontSize * this.antiAliasingFactor));
/*  71 */     } catch (FontFormatException|IOException e) {
/*  72 */       e.printStackTrace();
/*     */     } 
/*  74 */     this.kerning = kerning;
/*     */     
/*  76 */     this.unicodeFont.addAsciiGlyphs();
/*  77 */     this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
/*     */     
/*     */     try {
/*  80 */       this.unicodeFont.loadGlyphs();
/*  81 */     } catch (Exception e) {
/*  82 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  85 */     for (int i = 0; i < 32; i++) {
/*  86 */       int shadow = (i >> 3 & 0x1) * 85;
/*  87 */       int red = (i >> 2 & 0x1) * 170 + shadow;
/*  88 */       int green = (i >> 1 & 0x1) * 170 + shadow;
/*  89 */       int blue = (i & 0x1) * 170 + shadow;
/*     */       
/*  91 */       if (i == 6) {
/*  92 */         red += 85;
/*     */       }
/*     */       
/*  95 */       if (i >= 16) {
/*  96 */         red /= 4;
/*  97 */         green /= 4;
/*  98 */         blue /= 4;
/*     */       } 
/*     */       
/* 101 */       this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */   }
/*     */   
/*     */   private UnicodeFontRenderer(Font font, float kerning, float antiAliasingFactor) {
/* 106 */     this.antiAliasingFactor = antiAliasingFactor;
/* 107 */     this.unicodeFont = new UnicodeFont(new Font(font.getName(), font.getStyle(), (int)(font.getSize() * antiAliasingFactor)));
/* 108 */     this.kerning = kerning;
/*     */     
/* 110 */     this.unicodeFont.addAsciiGlyphs();
/* 111 */     this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
/*     */     
/*     */     try {
/* 114 */       this.unicodeFont.loadGlyphs();
/* 115 */     } catch (Exception e) {
/* 116 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 119 */     for (int i = 0; i < 32; i++) {
/* 120 */       int shadow = (i >> 3 & 0x1) * 85;
/* 121 */       int red = (i >> 2 & 0x1) * 170 + shadow;
/* 122 */       int green = (i >> 1 & 0x1) * 170 + shadow;
/* 123 */       int blue = (i & 0x1) * 170 + shadow;
/*     */       
/* 125 */       if (i == 6) {
/* 126 */         red += 85;
/*     */       }
/*     */       
/* 129 */       if (i >= 16) {
/* 130 */         red /= 4;
/* 131 */         green /= 4;
/* 132 */         blue /= 4;
/*     */       } 
/*     */       
/* 135 */       this.colorCodes[i] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Font getFontByName(String name) throws IOException, FontFormatException {
/* 140 */     return getFontFromInput("/assets/minecraft/clientname/fonts/" + name + ".ttf");
/*     */   }
/*     */   
/*     */   private Font getFontFromInput(String path) throws IOException, FontFormatException {
/* 144 */     return Font.createFont(0, Client.class.getResourceAsStream(path));
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawStringScaled(String text, int givenX, int givenY, int color, double givenScale) {
/* 149 */     GL11.glPushMatrix();
/* 150 */     GL11.glTranslated(givenX, givenY, 0.0D);
/* 151 */     GL11.glScaled(givenScale, givenScale, givenScale);
/* 152 */     drawString(text, 0.0F, 0.0F, color);
/* 153 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public int drawString(String text, float x, float y, int color) {
/* 158 */     if (text == null) {
/* 159 */       return 0;
/*     */     }
/* 161 */     x *= 2.0F;
/* 162 */     y *= 2.0F;
/*     */     
/* 164 */     float originalX = x;
/*     */     
/* 166 */     GL11.glPushMatrix();
/* 167 */     GlStateManager.scale(1.0F / this.antiAliasingFactor, 1.0F / this.antiAliasingFactor, 1.0F / this.antiAliasingFactor);
/* 168 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 169 */     x *= this.antiAliasingFactor;
/* 170 */     y *= this.antiAliasingFactor;
/* 171 */     float red = (color >> 16 & 0xFF) / 255.0F;
/* 172 */     float green = (color >> 8 & 0xFF) / 255.0F;
/* 173 */     float blue = (color & 0xFF) / 255.0F;
/* 174 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/* 175 */     GlStateManager.color(red, green, blue, alpha);
/*     */     
/* 177 */     boolean blend = GL11.glIsEnabled(3042);
/* 178 */     boolean lighting = GL11.glIsEnabled(2896);
/* 179 */     boolean texture = GL11.glIsEnabled(3553);
/* 180 */     if (!blend)
/* 181 */       GL11.glEnable(3042); 
/* 182 */     if (lighting)
/* 183 */       GL11.glDisable(2896); 
/* 184 */     if (texture) {
/* 185 */       GL11.glDisable(3553);
/*     */     }
/* 187 */     int currentColor = color;
/* 188 */     char[] characters = text.toCharArray();
/*     */     
/* 190 */     int index = 0; byte b; int i; char[] arrayOfChar1;
/* 191 */     for (i = (arrayOfChar1 = characters).length, b = 0; b < i; ) { char c = arrayOfChar1[b];
/* 192 */       if (c == '\r') {
/* 193 */         x = originalX;
/*     */       }
/* 195 */       if (c == '\n') {
/* 196 */         y += getHeight(Character.toString(c)) * 2.0F;
/*     */       }
/* 198 */       if (c != '§' && (index == 0 || index == characters.length - 1 || characters[index - 1] != '§')) {
/*     */         
/* 200 */         this.unicodeFont.drawString(x, y, Character.toString(c), new Color(currentColor));
/* 201 */         x += getWidth(Character.toString(c)) * 2.0F * this.antiAliasingFactor;
/* 202 */       } else if (c == ' ') {
/* 203 */         x += this.unicodeFont.getSpaceWidth();
/* 204 */       } else if (c == '§' && index != characters.length - 1) {
/* 205 */         int codeIndex = "0123456789abcdefg".indexOf(text.charAt(index + 1));
/* 206 */         if (codeIndex < 0)
/*     */           continue; 
/* 208 */         currentColor = this.colorCodes[codeIndex];
/*     */       } 
/*     */       
/* 211 */       index++; continue;
/*     */       b++; }
/*     */     
/* 214 */     GL11.glScaled(2.0D, 2.0D, 2.0D);
/* 215 */     if (texture)
/* 216 */       GL11.glEnable(3553); 
/* 217 */     if (lighting)
/* 218 */       GL11.glEnable(2896); 
/* 219 */     if (!blend)
/* 220 */       GL11.glDisable(3042); 
/* 221 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 222 */     GL11.glPopMatrix();
/* 223 */     return (int)x / 2;
/*     */   }
/*     */   
/*     */   public int drawStringWithShadow(String text, float x, float y, int color) {
/* 227 */     drawString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, 0);
/* 228 */     return drawString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public void drawCenteredString(String text, float x, float y, int color) {
/* 232 */     drawString(text, x - ((int)getWidth(text) / 2), y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredTextScaled(String text, int givenX, int givenY, int color, double givenScale) {
/* 237 */     GL11.glPushMatrix();
/* 238 */     GL11.glTranslated(givenX, givenY, 0.0D);
/* 239 */     GL11.glScaled(givenScale, givenScale, givenScale);
/* 240 */     drawCenteredString(text, 0.0F, 0.0F, color);
/* 241 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
/* 246 */     drawCenteredString(StringUtils.stripControlCodes(text), x + 0.5F, y + 0.5F, color);
/* 247 */     drawCenteredString(text, x, y, color);
/*     */   }
/*     */   
/*     */   public float getWidth(String s) {
/* 251 */     if (this.cachedStringWidth.size() > 1000)
/* 252 */       this.cachedStringWidth.clear(); 
/* 253 */     return ((Float)this.cachedStringWidth.computeIfAbsent(s, e -> { float width = 0.0F; String str = StringUtils.stripControlCodes(paramString1); char[] arrayOfChar; int i = (arrayOfChar = str.toCharArray()).length; for (byte b = 0; b < i; b++) { char c = arrayOfChar[b]; width += this.unicodeFont.getWidth(Character.toString(c)) + this.kerning; }  return Float.valueOf(width / 2.0F / this.antiAliasingFactor); })).floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStringWidth(String text) {
/* 266 */     if (text == null) {
/* 267 */       return 0;
/*     */     }
/* 269 */     int i = 0;
/* 270 */     boolean flag = false;
/*     */     
/* 272 */     for (int j = 0; j < text.length(); j++) {
/* 273 */       char c0 = text.charAt(j);
/* 274 */       float k = getWidth(String.valueOf(c0));
/*     */       
/* 276 */       if (k < 0.0F && j < text.length() - 1) {
/* 277 */         j++;
/* 278 */         c0 = text.charAt(j);
/*     */         
/* 280 */         if (c0 != 'l' && c0 != 'L') {
/* 281 */           if (c0 == 'r' || c0 == 'R') {
/* 282 */             flag = false;
/*     */           }
/*     */         } else {
/* 285 */           flag = true;
/*     */         } 
/*     */         
/* 288 */         k = 0.0F;
/*     */       } 
/*     */       
/* 291 */       i = (int)(i + k);
/*     */       
/* 293 */       if (flag && k > 0.0F) {
/* 294 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 298 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCharWidth(char c) {
/* 303 */     return this.unicodeFont.getWidth(String.valueOf(c));
/*     */   }
/*     */   
/*     */   public float getHeight(String s) {
/* 307 */     return this.unicodeFont.getHeight(s) / 2.0F;
/*     */   }
/*     */   
/*     */   public UnicodeFont getFont() {
/* 311 */     return this.unicodeFont;
/*     */   }
/*     */   
/*     */   public String trimStringToWidth(String par1Str, int par2) {
/* 315 */     StringBuilder var4 = new StringBuilder();
/* 316 */     float var5 = 0.0F;
/* 317 */     int var6 = 0;
/* 318 */     int var7 = 1;
/* 319 */     boolean var8 = false;
/* 320 */     boolean var9 = false;
/*     */     
/* 322 */     for (int var10 = var6; var10 >= 0 && var10 < par1Str.length() && var5 < par2; var10 += var7) {
/* 323 */       char var11 = par1Str.charAt(var10);
/* 324 */       float var12 = getCharWidth(var11);
/*     */       
/* 326 */       if (var8) {
/* 327 */         var8 = false;
/*     */         
/* 329 */         if (var11 != 'l' && var11 != 'L') {
/* 330 */           if (var11 == 'r' || var11 == 'R') {
/* 331 */             var9 = false;
/*     */           }
/*     */         } else {
/* 334 */           var9 = true;
/*     */         } 
/* 336 */       } else if (var12 < 0.0F) {
/* 337 */         var8 = true;
/*     */       } else {
/* 339 */         var5 += var12;
/*     */         
/* 341 */         if (var9) {
/* 342 */           var5++;
/*     */         }
/*     */       } 
/*     */       
/* 346 */       if (var5 > par2) {
/*     */         break;
/*     */       }
/* 349 */       var4.append(var11);
/*     */     } 
/*     */ 
/*     */     
/* 353 */     return var4.toString();
/*     */   }
/*     */   
/*     */   public void drawSplitString(ArrayList<String> lines, int x, int y, int color) {
/* 357 */     drawString(
/* 358 */         String.join("\n\r", (Iterable)lines), 
/* 359 */         x, 
/* 360 */         y, 
/* 361 */         color);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> splitString(String text, int wrapWidth) {
/* 366 */     List<String> lines = new ArrayList<>();
/*     */     
/* 368 */     String[] splitText = text.split(" ");
/* 369 */     StringBuilder currentString = new StringBuilder(); byte b; int i;
/*     */     String[] arrayOfString1;
/* 371 */     for (i = (arrayOfString1 = splitText).length, b = 0; b < i; ) { String word = arrayOfString1[b];
/* 372 */       String potential = currentString + " " + word;
/*     */       
/* 374 */       if (getWidth(potential) >= wrapWidth) {
/* 375 */         lines.add(currentString.toString());
/* 376 */         currentString = new StringBuilder();
/*     */       } 
/* 378 */       currentString.append(word).append(" "); b++; }
/*     */     
/* 380 */     lines.add(currentString.toString());
/* 381 */     return lines;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\gui\UnicodeFontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */