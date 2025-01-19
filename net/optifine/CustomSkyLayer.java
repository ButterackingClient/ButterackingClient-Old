/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.Matches;
/*     */ import net.optifine.config.RangeListInt;
/*     */ import net.optifine.render.Blender;
/*     */ import net.optifine.util.NumUtils;
/*     */ import net.optifine.util.SmoothFloat;
/*     */ import net.optifine.util.TextureUtils;
/*     */ 
/*     */ public class CustomSkyLayer
/*     */ {
/*  27 */   public String source = null;
/*  28 */   private int startFadeIn = -1;
/*  29 */   private int endFadeIn = -1;
/*  30 */   private int startFadeOut = -1;
/*  31 */   private int endFadeOut = -1;
/*  32 */   private int blend = 1;
/*     */   private boolean rotate = false;
/*  34 */   private float speed = 1.0F;
/*     */   private float[] axis;
/*     */   private RangeListInt days;
/*     */   private int daysLoop;
/*     */   private boolean weatherClear;
/*     */   private boolean weatherRain;
/*     */   private boolean weatherThunder;
/*     */   public BiomeGenBase[] biomes;
/*     */   public RangeListInt heights;
/*     */   private float transition;
/*     */   private SmoothFloat smoothPositionBrightness;
/*     */   public int textureId;
/*     */   private World lastWorld;
/*  47 */   public static final float[] DEFAULT_AXIS = new float[] { 1.0F, 0.0F, 0.0F };
/*     */   private static final String WEATHER_CLEAR = "clear";
/*     */   private static final String WEATHER_RAIN = "rain";
/*     */   private static final String WEATHER_THUNDER = "thunder";
/*     */   
/*     */   public CustomSkyLayer(Properties props, String defSource) {
/*  53 */     this.axis = DEFAULT_AXIS;
/*  54 */     this.days = null;
/*  55 */     this.daysLoop = 8;
/*  56 */     this.weatherClear = true;
/*  57 */     this.weatherRain = false;
/*  58 */     this.weatherThunder = false;
/*  59 */     this.biomes = null;
/*  60 */     this.heights = null;
/*  61 */     this.transition = 1.0F;
/*  62 */     this.smoothPositionBrightness = null;
/*  63 */     this.textureId = -1;
/*  64 */     this.lastWorld = null;
/*  65 */     ConnectedParser connectedparser = new ConnectedParser("CustomSky");
/*  66 */     this.source = props.getProperty("source", defSource);
/*  67 */     this.startFadeIn = parseTime(props.getProperty("startFadeIn"));
/*  68 */     this.endFadeIn = parseTime(props.getProperty("endFadeIn"));
/*  69 */     this.startFadeOut = parseTime(props.getProperty("startFadeOut"));
/*  70 */     this.endFadeOut = parseTime(props.getProperty("endFadeOut"));
/*  71 */     this.blend = Blender.parseBlend(props.getProperty("blend"));
/*  72 */     this.rotate = parseBoolean(props.getProperty("rotate"), true);
/*  73 */     this.speed = parseFloat(props.getProperty("speed"), 1.0F);
/*  74 */     this.axis = parseAxis(props.getProperty("axis"), DEFAULT_AXIS);
/*  75 */     this.days = connectedparser.parseRangeListInt(props.getProperty("days"));
/*  76 */     this.daysLoop = connectedparser.parseInt(props.getProperty("daysLoop"), 8);
/*  77 */     List<String> list = parseWeatherList(props.getProperty("weather", "clear"));
/*  78 */     this.weatherClear = list.contains("clear");
/*  79 */     this.weatherRain = list.contains("rain");
/*  80 */     this.weatherThunder = list.contains("thunder");
/*  81 */     this.biomes = connectedparser.parseBiomes(props.getProperty("biomes"));
/*  82 */     this.heights = connectedparser.parseRangeListInt(props.getProperty("heights"));
/*  83 */     this.transition = parseFloat(props.getProperty("transition"), 1.0F);
/*     */   }
/*     */   
/*     */   private List<String> parseWeatherList(String str) {
/*  87 */     List<String> list = Arrays.asList(new String[] { "clear", "rain", "thunder" });
/*  88 */     List<String> list1 = new ArrayList<>();
/*  89 */     String[] astring = Config.tokenize(str, " ");
/*     */     
/*  91 */     for (int i = 0; i < astring.length; i++) {
/*  92 */       String s = astring[i];
/*     */       
/*  94 */       if (!list.contains(s)) {
/*  95 */         Config.warn("Unknown weather: " + s);
/*     */       } else {
/*  97 */         list1.add(s);
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     return list1;
/*     */   }
/*     */   
/*     */   private int parseTime(String str) {
/* 105 */     if (str == null) {
/* 106 */       return -1;
/*     */     }
/* 108 */     String[] astring = Config.tokenize(str, ":");
/*     */     
/* 110 */     if (astring.length != 2) {
/* 111 */       Config.warn("Invalid time: " + str);
/* 112 */       return -1;
/*     */     } 
/* 114 */     String s = astring[0];
/* 115 */     String s1 = astring[1];
/* 116 */     int i = Config.parseInt(s, -1);
/* 117 */     int j = Config.parseInt(s1, -1);
/*     */     
/* 119 */     if (i >= 0 && i <= 23 && j >= 0 && j <= 59) {
/* 120 */       i -= 6;
/*     */       
/* 122 */       if (i < 0) {
/* 123 */         i += 24;
/*     */       }
/*     */       
/* 126 */       int k = i * 1000 + (int)(j / 60.0D * 1000.0D);
/* 127 */       return k;
/*     */     } 
/* 129 */     Config.warn("Invalid time: " + str);
/* 130 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean parseBoolean(String str, boolean defVal) {
/* 137 */     if (str == null)
/* 138 */       return defVal; 
/* 139 */     if (str.toLowerCase().equals("true"))
/* 140 */       return true; 
/* 141 */     if (str.toLowerCase().equals("false")) {
/* 142 */       return false;
/*     */     }
/* 144 */     Config.warn("Unknown boolean: " + str);
/* 145 */     return defVal;
/*     */   }
/*     */ 
/*     */   
/*     */   private float parseFloat(String str, float defVal) {
/* 150 */     if (str == null) {
/* 151 */       return defVal;
/*     */     }
/* 153 */     float f = Config.parseFloat(str, Float.MIN_VALUE);
/*     */     
/* 155 */     if (f == Float.MIN_VALUE) {
/* 156 */       Config.warn("Invalid value: " + str);
/* 157 */       return defVal;
/*     */     } 
/* 159 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] parseAxis(String str, float[] defVal) {
/* 165 */     if (str == null) {
/* 166 */       return defVal;
/*     */     }
/* 168 */     String[] astring = Config.tokenize(str, " ");
/*     */     
/* 170 */     if (astring.length != 3) {
/* 171 */       Config.warn("Invalid axis: " + str);
/* 172 */       return defVal;
/*     */     } 
/* 174 */     float[] afloat = new float[3];
/*     */     
/* 176 */     for (int i = 0; i < astring.length; i++) {
/* 177 */       afloat[i] = Config.parseFloat(astring[i], Float.MIN_VALUE);
/*     */       
/* 179 */       if (afloat[i] == Float.MIN_VALUE) {
/* 180 */         Config.warn("Invalid axis: " + str);
/* 181 */         return defVal;
/*     */       } 
/*     */       
/* 184 */       if (afloat[i] < -1.0F || afloat[i] > 1.0F) {
/* 185 */         Config.warn("Invalid axis values: " + str);
/* 186 */         return defVal;
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     float f2 = afloat[0];
/* 191 */     float f = afloat[1];
/* 192 */     float f1 = afloat[2];
/*     */     
/* 194 */     if (f2 * f2 + f * f + f1 * f1 < 1.0E-5F) {
/* 195 */       Config.warn("Invalid axis values: " + str);
/* 196 */       return defVal;
/*     */     } 
/* 198 */     float[] afloat1 = { f1, f, -f2 };
/* 199 */     return afloat1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 206 */     if (this.source == null) {
/* 207 */       Config.warn("No source texture: " + path);
/* 208 */       return false;
/*     */     } 
/* 210 */     this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(path));
/*     */     
/* 212 */     if (this.startFadeIn >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0) {
/* 213 */       int i = normalizeTime(this.endFadeIn - this.startFadeIn);
/*     */       
/* 215 */       if (this.startFadeOut < 0) {
/* 216 */         this.startFadeOut = normalizeTime(this.endFadeOut - i);
/*     */         
/* 218 */         if (timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn)) {
/* 219 */           this.startFadeOut = this.endFadeIn;
/*     */         }
/*     */       } 
/*     */       
/* 223 */       int j = normalizeTime(this.startFadeOut - this.endFadeIn);
/* 224 */       int k = normalizeTime(this.endFadeOut - this.startFadeOut);
/* 225 */       int l = normalizeTime(this.startFadeIn - this.endFadeOut);
/* 226 */       int i1 = i + j + k + l;
/*     */       
/* 228 */       if (i1 != 24000) {
/* 229 */         Config.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + i1);
/* 230 */         return false;
/* 231 */       }  if (this.speed < 0.0F) {
/* 232 */         Config.warn("Invalid speed: " + this.speed);
/* 233 */         return false;
/* 234 */       }  if (this.daysLoop <= 0) {
/* 235 */         Config.warn("Invalid daysLoop: " + this.daysLoop);
/* 236 */         return false;
/*     */       } 
/* 238 */       return true;
/*     */     } 
/*     */     
/* 241 */     Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int normalizeTime(int timeMc) {
/* 248 */     while (timeMc >= 24000) {
/* 249 */       timeMc -= 24000;
/*     */     }
/*     */     
/* 252 */     while (timeMc < 0) {
/* 253 */       timeMc += 24000;
/*     */     }
/*     */     
/* 256 */     return timeMc;
/*     */   }
/*     */   
/*     */   public void render(World world, int timeOfDay, float celestialAngle, float rainStrength, float thunderStrength) {
/* 260 */     float f = getPositionBrightness(world);
/* 261 */     float f1 = getWeatherBrightness(rainStrength, thunderStrength);
/* 262 */     float f2 = getFadeBrightness(timeOfDay);
/* 263 */     float f3 = f * f1 * f2;
/* 264 */     f3 = Config.limit(f3, 0.0F, 1.0F);
/*     */     
/* 266 */     if (f3 >= 1.0E-4F) {
/* 267 */       GlStateManager.bindTexture(this.textureId);
/* 268 */       Blender.setupBlend(this.blend, f3);
/* 269 */       GlStateManager.pushMatrix();
/*     */       
/* 271 */       if (this.rotate) {
/* 272 */         float f4 = 0.0F;
/*     */         
/* 274 */         if (this.speed != Math.round(this.speed)) {
/* 275 */           long i = (world.getWorldTime() + 18000L) / 24000L;
/* 276 */           double d0 = (this.speed % 1.0F);
/* 277 */           double d1 = i * d0;
/* 278 */           f4 = (float)(d1 % 1.0D);
/*     */         } 
/*     */         
/* 281 */         GlStateManager.rotate(360.0F * (f4 + celestialAngle * this.speed), this.axis[0], this.axis[1], this.axis[2]);
/*     */       } 
/*     */       
/* 284 */       Tessellator tessellator = Tessellator.getInstance();
/* 285 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 286 */       GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
/* 287 */       renderSide(tessellator, 4);
/* 288 */       GlStateManager.pushMatrix();
/* 289 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 290 */       renderSide(tessellator, 1);
/* 291 */       GlStateManager.popMatrix();
/* 292 */       GlStateManager.pushMatrix();
/* 293 */       GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/* 294 */       renderSide(tessellator, 0);
/* 295 */       GlStateManager.popMatrix();
/* 296 */       GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 297 */       renderSide(tessellator, 5);
/* 298 */       GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 299 */       renderSide(tessellator, 2);
/* 300 */       GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 301 */       renderSide(tessellator, 3);
/* 302 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   private float getPositionBrightness(World world) {
/* 307 */     if (this.biomes == null && this.heights == null) {
/* 308 */       return 1.0F;
/*     */     }
/* 310 */     float f = getPositionBrightnessRaw(world);
/*     */     
/* 312 */     if (this.smoothPositionBrightness == null) {
/* 313 */       this.smoothPositionBrightness = new SmoothFloat(f, this.transition);
/*     */     }
/*     */     
/* 316 */     f = this.smoothPositionBrightness.getSmoothValue(f);
/* 317 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   private float getPositionBrightnessRaw(World world) {
/* 322 */     Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
/*     */     
/* 324 */     if (entity == null) {
/* 325 */       return 0.0F;
/*     */     }
/* 327 */     BlockPos blockpos = entity.getPosition();
/*     */     
/* 329 */     if (this.biomes != null) {
/* 330 */       BiomeGenBase biomegenbase = world.getBiomeGenForCoords(blockpos);
/*     */       
/* 332 */       if (biomegenbase == null) {
/* 333 */         return 0.0F;
/*     */       }
/*     */       
/* 336 */       if (!Matches.biome(biomegenbase, this.biomes)) {
/* 337 */         return 0.0F;
/*     */       }
/*     */     } 
/*     */     
/* 341 */     return (this.heights != null && !this.heights.isInRange(blockpos.getY())) ? 0.0F : 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private float getWeatherBrightness(float rainStrength, float thunderStrength) {
/* 346 */     float f = 1.0F - rainStrength;
/* 347 */     float f1 = rainStrength - thunderStrength;
/* 348 */     float f2 = 0.0F;
/*     */     
/* 350 */     if (this.weatherClear) {
/* 351 */       f2 += f;
/*     */     }
/*     */     
/* 354 */     if (this.weatherRain) {
/* 355 */       f2 += f1;
/*     */     }
/*     */     
/* 358 */     if (this.weatherThunder) {
/* 359 */       f2 += thunderStrength;
/*     */     }
/*     */     
/* 362 */     f2 = NumUtils.limit(f2, 0.0F, 1.0F);
/* 363 */     return f2;
/*     */   }
/*     */   
/*     */   private float getFadeBrightness(int timeOfDay) {
/* 367 */     if (timeBetween(timeOfDay, this.startFadeIn, this.endFadeIn)) {
/* 368 */       int k = normalizeTime(this.endFadeIn - this.startFadeIn);
/* 369 */       int l = normalizeTime(timeOfDay - this.startFadeIn);
/* 370 */       return l / k;
/* 371 */     }  if (timeBetween(timeOfDay, this.endFadeIn, this.startFadeOut))
/* 372 */       return 1.0F; 
/* 373 */     if (timeBetween(timeOfDay, this.startFadeOut, this.endFadeOut)) {
/* 374 */       int i = normalizeTime(this.endFadeOut - this.startFadeOut);
/* 375 */       int j = normalizeTime(timeOfDay - this.startFadeOut);
/* 376 */       return 1.0F - j / i;
/*     */     } 
/* 378 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderSide(Tessellator tess, int side) {
/* 383 */     WorldRenderer worldrenderer = tess.getWorldRenderer();
/* 384 */     double d0 = (side % 3) / 3.0D;
/* 385 */     double d1 = (side / 3) / 2.0D;
/* 386 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 387 */     worldrenderer.pos(-100.0D, -100.0D, -100.0D).tex(d0, d1).endVertex();
/* 388 */     worldrenderer.pos(-100.0D, -100.0D, 100.0D).tex(d0, d1 + 0.5D).endVertex();
/* 389 */     worldrenderer.pos(100.0D, -100.0D, 100.0D).tex(d0 + 0.3333333333333333D, d1 + 0.5D).endVertex();
/* 390 */     worldrenderer.pos(100.0D, -100.0D, -100.0D).tex(d0 + 0.3333333333333333D, d1).endVertex();
/* 391 */     tess.draw();
/*     */   }
/*     */   
/*     */   public boolean isActive(World world, int timeOfDay) {
/* 395 */     if (world != this.lastWorld) {
/* 396 */       this.lastWorld = world;
/* 397 */       this.smoothPositionBrightness = null;
/*     */     } 
/*     */     
/* 400 */     if (timeBetween(timeOfDay, this.endFadeOut, this.startFadeIn)) {
/* 401 */       return false;
/*     */     }
/* 403 */     if (this.days != null) {
/* 404 */       long i = world.getWorldTime();
/*     */       
/*     */       long j;
/* 407 */       for (j = i - this.startFadeIn; j < 0L; j += (24000 * this.daysLoop));
/*     */ 
/*     */ 
/*     */       
/* 411 */       int k = (int)(j / 24000L);
/* 412 */       int l = k % this.daysLoop;
/*     */       
/* 414 */       if (!this.days.isInRange(l)) {
/* 415 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 419 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean timeBetween(int timeOfDay, int timeStart, int timeEnd) {
/* 424 */     return (timeStart <= timeEnd) ? ((timeOfDay >= timeStart && timeOfDay <= timeEnd)) : (!(timeOfDay < timeStart && timeOfDay > timeEnd));
/*     */   }
/*     */   
/*     */   public String toString() {
/* 428 */     return this.source + ", " + this.startFadeIn + "-" + this.endFadeIn + " " + this.startFadeOut + "-" + this.endFadeOut;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomSkyLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */