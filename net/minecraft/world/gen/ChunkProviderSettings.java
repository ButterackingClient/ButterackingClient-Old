/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ 
/*     */ public class ChunkProviderSettings
/*     */ {
/*     */   public final float coordinateScale;
/*     */   public final float heightScale;
/*     */   public final float upperLimitScale;
/*     */   public final float lowerLimitScale;
/*     */   public final float depthNoiseScaleX;
/*     */   public final float depthNoiseScaleZ;
/*     */   public final float depthNoiseScaleExponent;
/*     */   public final float mainNoiseScaleX;
/*     */   public final float mainNoiseScaleY;
/*     */   public final float mainNoiseScaleZ;
/*     */   public final float baseSize;
/*     */   public final float stretchY;
/*     */   public final float biomeDepthWeight;
/*     */   public final float biomeDepthOffSet;
/*     */   public final float biomeScaleWeight;
/*     */   public final float biomeScaleOffset;
/*     */   public final int seaLevel;
/*     */   public final boolean useCaves;
/*     */   public final boolean useDungeons;
/*     */   public final int dungeonChance;
/*     */   public final boolean useStrongholds;
/*     */   public final boolean useVillages;
/*     */   public final boolean useMineShafts;
/*     */   public final boolean useTemples;
/*     */   public final boolean useMonuments;
/*     */   public final boolean useRavines;
/*     */   public final boolean useWaterLakes;
/*     */   public final int waterLakeChance;
/*     */   public final boolean useLavaLakes;
/*     */   public final int lavaLakeChance;
/*     */   public final boolean useLavaOceans;
/*     */   public final int fixedBiome;
/*     */   public final int biomeSize;
/*     */   public final int riverSize;
/*     */   public final int dirtSize;
/*     */   public final int dirtCount;
/*     */   public final int dirtMinHeight;
/*     */   public final int dirtMaxHeight;
/*     */   public final int gravelSize;
/*     */   public final int gravelCount;
/*     */   public final int gravelMinHeight;
/*     */   public final int gravelMaxHeight;
/*     */   public final int graniteSize;
/*     */   public final int graniteCount;
/*     */   public final int graniteMinHeight;
/*     */   public final int graniteMaxHeight;
/*     */   public final int dioriteSize;
/*     */   public final int dioriteCount;
/*     */   public final int dioriteMinHeight;
/*     */   public final int dioriteMaxHeight;
/*     */   public final int andesiteSize;
/*     */   public final int andesiteCount;
/*     */   public final int andesiteMinHeight;
/*     */   public final int andesiteMaxHeight;
/*     */   public final int coalSize;
/*     */   public final int coalCount;
/*     */   public final int coalMinHeight;
/*     */   public final int coalMaxHeight;
/*     */   public final int ironSize;
/*     */   public final int ironCount;
/*     */   public final int ironMinHeight;
/*     */   public final int ironMaxHeight;
/*     */   public final int goldSize;
/*     */   public final int goldCount;
/*     */   public final int goldMinHeight;
/*     */   public final int goldMaxHeight;
/*     */   public final int redstoneSize;
/*     */   public final int redstoneCount;
/*     */   public final int redstoneMinHeight;
/*     */   public final int redstoneMaxHeight;
/*     */   public final int diamondSize;
/*     */   public final int diamondCount;
/*     */   public final int diamondMinHeight;
/*     */   public final int diamondMaxHeight;
/*     */   public final int lapisSize;
/*     */   public final int lapisCount;
/*     */   public final int lapisCenterHeight;
/*     */   public final int lapisSpread;
/*     */   
/*     */   private ChunkProviderSettings(Factory settingsFactory) {
/*  99 */     this.coordinateScale = settingsFactory.coordinateScale;
/* 100 */     this.heightScale = settingsFactory.heightScale;
/* 101 */     this.upperLimitScale = settingsFactory.upperLimitScale;
/* 102 */     this.lowerLimitScale = settingsFactory.lowerLimitScale;
/* 103 */     this.depthNoiseScaleX = settingsFactory.depthNoiseScaleX;
/* 104 */     this.depthNoiseScaleZ = settingsFactory.depthNoiseScaleZ;
/* 105 */     this.depthNoiseScaleExponent = settingsFactory.depthNoiseScaleExponent;
/* 106 */     this.mainNoiseScaleX = settingsFactory.mainNoiseScaleX;
/* 107 */     this.mainNoiseScaleY = settingsFactory.mainNoiseScaleY;
/* 108 */     this.mainNoiseScaleZ = settingsFactory.mainNoiseScaleZ;
/* 109 */     this.baseSize = settingsFactory.baseSize;
/* 110 */     this.stretchY = settingsFactory.stretchY;
/* 111 */     this.biomeDepthWeight = settingsFactory.biomeDepthWeight;
/* 112 */     this.biomeDepthOffSet = settingsFactory.biomeDepthOffset;
/* 113 */     this.biomeScaleWeight = settingsFactory.biomeScaleWeight;
/* 114 */     this.biomeScaleOffset = settingsFactory.biomeScaleOffset;
/* 115 */     this.seaLevel = settingsFactory.seaLevel;
/* 116 */     this.useCaves = settingsFactory.useCaves;
/* 117 */     this.useDungeons = settingsFactory.useDungeons;
/* 118 */     this.dungeonChance = settingsFactory.dungeonChance;
/* 119 */     this.useStrongholds = settingsFactory.useStrongholds;
/* 120 */     this.useVillages = settingsFactory.useVillages;
/* 121 */     this.useMineShafts = settingsFactory.useMineShafts;
/* 122 */     this.useTemples = settingsFactory.useTemples;
/* 123 */     this.useMonuments = settingsFactory.useMonuments;
/* 124 */     this.useRavines = settingsFactory.useRavines;
/* 125 */     this.useWaterLakes = settingsFactory.useWaterLakes;
/* 126 */     this.waterLakeChance = settingsFactory.waterLakeChance;
/* 127 */     this.useLavaLakes = settingsFactory.useLavaLakes;
/* 128 */     this.lavaLakeChance = settingsFactory.lavaLakeChance;
/* 129 */     this.useLavaOceans = settingsFactory.useLavaOceans;
/* 130 */     this.fixedBiome = settingsFactory.fixedBiome;
/* 131 */     this.biomeSize = settingsFactory.biomeSize;
/* 132 */     this.riverSize = settingsFactory.riverSize;
/* 133 */     this.dirtSize = settingsFactory.dirtSize;
/* 134 */     this.dirtCount = settingsFactory.dirtCount;
/* 135 */     this.dirtMinHeight = settingsFactory.dirtMinHeight;
/* 136 */     this.dirtMaxHeight = settingsFactory.dirtMaxHeight;
/* 137 */     this.gravelSize = settingsFactory.gravelSize;
/* 138 */     this.gravelCount = settingsFactory.gravelCount;
/* 139 */     this.gravelMinHeight = settingsFactory.gravelMinHeight;
/* 140 */     this.gravelMaxHeight = settingsFactory.gravelMaxHeight;
/* 141 */     this.graniteSize = settingsFactory.graniteSize;
/* 142 */     this.graniteCount = settingsFactory.graniteCount;
/* 143 */     this.graniteMinHeight = settingsFactory.graniteMinHeight;
/* 144 */     this.graniteMaxHeight = settingsFactory.graniteMaxHeight;
/* 145 */     this.dioriteSize = settingsFactory.dioriteSize;
/* 146 */     this.dioriteCount = settingsFactory.dioriteCount;
/* 147 */     this.dioriteMinHeight = settingsFactory.dioriteMinHeight;
/* 148 */     this.dioriteMaxHeight = settingsFactory.dioriteMaxHeight;
/* 149 */     this.andesiteSize = settingsFactory.andesiteSize;
/* 150 */     this.andesiteCount = settingsFactory.andesiteCount;
/* 151 */     this.andesiteMinHeight = settingsFactory.andesiteMinHeight;
/* 152 */     this.andesiteMaxHeight = settingsFactory.andesiteMaxHeight;
/* 153 */     this.coalSize = settingsFactory.coalSize;
/* 154 */     this.coalCount = settingsFactory.coalCount;
/* 155 */     this.coalMinHeight = settingsFactory.coalMinHeight;
/* 156 */     this.coalMaxHeight = settingsFactory.coalMaxHeight;
/* 157 */     this.ironSize = settingsFactory.ironSize;
/* 158 */     this.ironCount = settingsFactory.ironCount;
/* 159 */     this.ironMinHeight = settingsFactory.ironMinHeight;
/* 160 */     this.ironMaxHeight = settingsFactory.ironMaxHeight;
/* 161 */     this.goldSize = settingsFactory.goldSize;
/* 162 */     this.goldCount = settingsFactory.goldCount;
/* 163 */     this.goldMinHeight = settingsFactory.goldMinHeight;
/* 164 */     this.goldMaxHeight = settingsFactory.goldMaxHeight;
/* 165 */     this.redstoneSize = settingsFactory.redstoneSize;
/* 166 */     this.redstoneCount = settingsFactory.redstoneCount;
/* 167 */     this.redstoneMinHeight = settingsFactory.redstoneMinHeight;
/* 168 */     this.redstoneMaxHeight = settingsFactory.redstoneMaxHeight;
/* 169 */     this.diamondSize = settingsFactory.diamondSize;
/* 170 */     this.diamondCount = settingsFactory.diamondCount;
/* 171 */     this.diamondMinHeight = settingsFactory.diamondMinHeight;
/* 172 */     this.diamondMaxHeight = settingsFactory.diamondMaxHeight;
/* 173 */     this.lapisSize = settingsFactory.lapisSize;
/* 174 */     this.lapisCount = settingsFactory.lapisCount;
/* 175 */     this.lapisCenterHeight = settingsFactory.lapisCenterHeight;
/* 176 */     this.lapisSpread = settingsFactory.lapisSpread;
/*     */   }
/*     */   
/*     */   public static class Factory {
/* 180 */     static final Gson JSON_ADAPTER = (new GsonBuilder()).registerTypeAdapter(Factory.class, new ChunkProviderSettings.Serializer()).create();
/* 181 */     public float coordinateScale = 684.412F;
/* 182 */     public float heightScale = 684.412F;
/* 183 */     public float upperLimitScale = 512.0F;
/* 184 */     public float lowerLimitScale = 512.0F;
/* 185 */     public float depthNoiseScaleX = 200.0F;
/* 186 */     public float depthNoiseScaleZ = 200.0F;
/* 187 */     public float depthNoiseScaleExponent = 0.5F;
/* 188 */     public float mainNoiseScaleX = 80.0F;
/* 189 */     public float mainNoiseScaleY = 160.0F;
/* 190 */     public float mainNoiseScaleZ = 80.0F;
/* 191 */     public float baseSize = 8.5F;
/* 192 */     public float stretchY = 12.0F;
/* 193 */     public float biomeDepthWeight = 1.0F;
/* 194 */     public float biomeDepthOffset = 0.0F;
/* 195 */     public float biomeScaleWeight = 1.0F;
/* 196 */     public float biomeScaleOffset = 0.0F;
/* 197 */     public int seaLevel = 63;
/*     */     public boolean useCaves = true;
/*     */     public boolean useDungeons = true;
/* 200 */     public int dungeonChance = 8;
/*     */     public boolean useStrongholds = true;
/*     */     public boolean useVillages = true;
/*     */     public boolean useMineShafts = true;
/*     */     public boolean useTemples = true;
/*     */     public boolean useMonuments = true;
/*     */     public boolean useRavines = true;
/*     */     public boolean useWaterLakes = true;
/* 208 */     public int waterLakeChance = 4;
/*     */     public boolean useLavaLakes = true;
/* 210 */     public int lavaLakeChance = 80;
/*     */     public boolean useLavaOceans = false;
/* 212 */     public int fixedBiome = -1;
/* 213 */     public int biomeSize = 4;
/* 214 */     public int riverSize = 4;
/* 215 */     public int dirtSize = 33;
/* 216 */     public int dirtCount = 10;
/* 217 */     public int dirtMinHeight = 0;
/* 218 */     public int dirtMaxHeight = 256;
/* 219 */     public int gravelSize = 33;
/* 220 */     public int gravelCount = 8;
/* 221 */     public int gravelMinHeight = 0;
/* 222 */     public int gravelMaxHeight = 256;
/* 223 */     public int graniteSize = 33;
/* 224 */     public int graniteCount = 10;
/* 225 */     public int graniteMinHeight = 0;
/* 226 */     public int graniteMaxHeight = 80;
/* 227 */     public int dioriteSize = 33;
/* 228 */     public int dioriteCount = 10;
/* 229 */     public int dioriteMinHeight = 0;
/* 230 */     public int dioriteMaxHeight = 80;
/* 231 */     public int andesiteSize = 33;
/* 232 */     public int andesiteCount = 10;
/* 233 */     public int andesiteMinHeight = 0;
/* 234 */     public int andesiteMaxHeight = 80;
/* 235 */     public int coalSize = 17;
/* 236 */     public int coalCount = 20;
/* 237 */     public int coalMinHeight = 0;
/* 238 */     public int coalMaxHeight = 128;
/* 239 */     public int ironSize = 9;
/* 240 */     public int ironCount = 20;
/* 241 */     public int ironMinHeight = 0;
/* 242 */     public int ironMaxHeight = 64;
/* 243 */     public int goldSize = 9;
/* 244 */     public int goldCount = 2;
/* 245 */     public int goldMinHeight = 0;
/* 246 */     public int goldMaxHeight = 32;
/* 247 */     public int redstoneSize = 8;
/* 248 */     public int redstoneCount = 8;
/* 249 */     public int redstoneMinHeight = 0;
/* 250 */     public int redstoneMaxHeight = 16;
/* 251 */     public int diamondSize = 8;
/* 252 */     public int diamondCount = 1;
/* 253 */     public int diamondMinHeight = 0;
/* 254 */     public int diamondMaxHeight = 16;
/* 255 */     public int lapisSize = 7;
/* 256 */     public int lapisCount = 1;
/* 257 */     public int lapisCenterHeight = 16;
/* 258 */     public int lapisSpread = 16;
/*     */     
/*     */     public static Factory jsonToFactory(String p_177865_0_) {
/* 261 */       if (p_177865_0_.length() == 0) {
/* 262 */         return new Factory();
/*     */       }
/*     */       try {
/* 265 */         return (Factory)JSON_ADAPTER.fromJson(p_177865_0_, Factory.class);
/* 266 */       } catch (Exception var2) {
/* 267 */         return new Factory();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 273 */       return JSON_ADAPTER.toJson(this);
/*     */     }
/*     */     
/*     */     public Factory() {
/* 277 */       func_177863_a();
/*     */     }
/*     */     
/*     */     public void func_177863_a() {
/* 281 */       this.coordinateScale = 684.412F;
/* 282 */       this.heightScale = 684.412F;
/* 283 */       this.upperLimitScale = 512.0F;
/* 284 */       this.lowerLimitScale = 512.0F;
/* 285 */       this.depthNoiseScaleX = 200.0F;
/* 286 */       this.depthNoiseScaleZ = 200.0F;
/* 287 */       this.depthNoiseScaleExponent = 0.5F;
/* 288 */       this.mainNoiseScaleX = 80.0F;
/* 289 */       this.mainNoiseScaleY = 160.0F;
/* 290 */       this.mainNoiseScaleZ = 80.0F;
/* 291 */       this.baseSize = 8.5F;
/* 292 */       this.stretchY = 12.0F;
/* 293 */       this.biomeDepthWeight = 1.0F;
/* 294 */       this.biomeDepthOffset = 0.0F;
/* 295 */       this.biomeScaleWeight = 1.0F;
/* 296 */       this.biomeScaleOffset = 0.0F;
/* 297 */       this.seaLevel = 63;
/* 298 */       this.useCaves = true;
/* 299 */       this.useDungeons = true;
/* 300 */       this.dungeonChance = 8;
/* 301 */       this.useStrongholds = true;
/* 302 */       this.useVillages = true;
/* 303 */       this.useMineShafts = true;
/* 304 */       this.useTemples = true;
/* 305 */       this.useMonuments = true;
/* 306 */       this.useRavines = true;
/* 307 */       this.useWaterLakes = true;
/* 308 */       this.waterLakeChance = 4;
/* 309 */       this.useLavaLakes = true;
/* 310 */       this.lavaLakeChance = 80;
/* 311 */       this.useLavaOceans = false;
/* 312 */       this.fixedBiome = -1;
/* 313 */       this.biomeSize = 4;
/* 314 */       this.riverSize = 4;
/* 315 */       this.dirtSize = 33;
/* 316 */       this.dirtCount = 10;
/* 317 */       this.dirtMinHeight = 0;
/* 318 */       this.dirtMaxHeight = 256;
/* 319 */       this.gravelSize = 33;
/* 320 */       this.gravelCount = 8;
/* 321 */       this.gravelMinHeight = 0;
/* 322 */       this.gravelMaxHeight = 256;
/* 323 */       this.graniteSize = 33;
/* 324 */       this.graniteCount = 10;
/* 325 */       this.graniteMinHeight = 0;
/* 326 */       this.graniteMaxHeight = 80;
/* 327 */       this.dioriteSize = 33;
/* 328 */       this.dioriteCount = 10;
/* 329 */       this.dioriteMinHeight = 0;
/* 330 */       this.dioriteMaxHeight = 80;
/* 331 */       this.andesiteSize = 33;
/* 332 */       this.andesiteCount = 10;
/* 333 */       this.andesiteMinHeight = 0;
/* 334 */       this.andesiteMaxHeight = 80;
/* 335 */       this.coalSize = 17;
/* 336 */       this.coalCount = 20;
/* 337 */       this.coalMinHeight = 0;
/* 338 */       this.coalMaxHeight = 128;
/* 339 */       this.ironSize = 9;
/* 340 */       this.ironCount = 20;
/* 341 */       this.ironMinHeight = 0;
/* 342 */       this.ironMaxHeight = 64;
/* 343 */       this.goldSize = 9;
/* 344 */       this.goldCount = 2;
/* 345 */       this.goldMinHeight = 0;
/* 346 */       this.goldMaxHeight = 32;
/* 347 */       this.redstoneSize = 8;
/* 348 */       this.redstoneCount = 8;
/* 349 */       this.redstoneMinHeight = 0;
/* 350 */       this.redstoneMaxHeight = 16;
/* 351 */       this.diamondSize = 8;
/* 352 */       this.diamondCount = 1;
/* 353 */       this.diamondMinHeight = 0;
/* 354 */       this.diamondMaxHeight = 16;
/* 355 */       this.lapisSize = 7;
/* 356 */       this.lapisCount = 1;
/* 357 */       this.lapisCenterHeight = 16;
/* 358 */       this.lapisSpread = 16;
/*     */     }
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 362 */       if (this == p_equals_1_)
/* 363 */         return true; 
/* 364 */       if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/* 365 */         Factory chunkprovidersettings$factory = (Factory)p_equals_1_;
/* 366 */         return (this.andesiteCount != chunkprovidersettings$factory.andesiteCount) ? false : ((this.andesiteMaxHeight != chunkprovidersettings$factory.andesiteMaxHeight) ? false : ((this.andesiteMinHeight != chunkprovidersettings$factory.andesiteMinHeight) ? false : ((this.andesiteSize != chunkprovidersettings$factory.andesiteSize) ? false : ((Float.compare(chunkprovidersettings$factory.baseSize, this.baseSize) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.biomeDepthOffset, this.biomeDepthOffset) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.biomeDepthWeight, this.biomeDepthWeight) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.biomeScaleOffset, this.biomeScaleOffset) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.biomeScaleWeight, this.biomeScaleWeight) != 0) ? false : ((this.biomeSize != chunkprovidersettings$factory.biomeSize) ? false : ((this.coalCount != chunkprovidersettings$factory.coalCount) ? false : ((this.coalMaxHeight != chunkprovidersettings$factory.coalMaxHeight) ? false : ((this.coalMinHeight != chunkprovidersettings$factory.coalMinHeight) ? false : ((this.coalSize != chunkprovidersettings$factory.coalSize) ? false : ((Float.compare(chunkprovidersettings$factory.coordinateScale, this.coordinateScale) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.depthNoiseScaleExponent, this.depthNoiseScaleExponent) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.depthNoiseScaleX, this.depthNoiseScaleX) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.depthNoiseScaleZ, this.depthNoiseScaleZ) != 0) ? false : ((this.diamondCount != chunkprovidersettings$factory.diamondCount) ? false : ((this.diamondMaxHeight != chunkprovidersettings$factory.diamondMaxHeight) ? false : ((this.diamondMinHeight != chunkprovidersettings$factory.diamondMinHeight) ? false : ((this.diamondSize != chunkprovidersettings$factory.diamondSize) ? false : ((this.dioriteCount != chunkprovidersettings$factory.dioriteCount) ? false : ((this.dioriteMaxHeight != chunkprovidersettings$factory.dioriteMaxHeight) ? false : ((this.dioriteMinHeight != chunkprovidersettings$factory.dioriteMinHeight) ? false : ((this.dioriteSize != chunkprovidersettings$factory.dioriteSize) ? false : ((this.dirtCount != chunkprovidersettings$factory.dirtCount) ? false : ((this.dirtMaxHeight != chunkprovidersettings$factory.dirtMaxHeight) ? false : ((this.dirtMinHeight != chunkprovidersettings$factory.dirtMinHeight) ? false : ((this.dirtSize != chunkprovidersettings$factory.dirtSize) ? false : ((this.dungeonChance != chunkprovidersettings$factory.dungeonChance) ? false : ((this.fixedBiome != chunkprovidersettings$factory.fixedBiome) ? false : ((this.goldCount != chunkprovidersettings$factory.goldCount) ? false : ((this.goldMaxHeight != chunkprovidersettings$factory.goldMaxHeight) ? false : ((this.goldMinHeight != chunkprovidersettings$factory.goldMinHeight) ? false : ((this.goldSize != chunkprovidersettings$factory.goldSize) ? false : ((this.graniteCount != chunkprovidersettings$factory.graniteCount) ? false : ((this.graniteMaxHeight != chunkprovidersettings$factory.graniteMaxHeight) ? false : ((this.graniteMinHeight != chunkprovidersettings$factory.graniteMinHeight) ? false : ((this.graniteSize != chunkprovidersettings$factory.graniteSize) ? false : ((this.gravelCount != chunkprovidersettings$factory.gravelCount) ? false : ((this.gravelMaxHeight != chunkprovidersettings$factory.gravelMaxHeight) ? false : ((this.gravelMinHeight != chunkprovidersettings$factory.gravelMinHeight) ? false : ((this.gravelSize != chunkprovidersettings$factory.gravelSize) ? false : ((Float.compare(chunkprovidersettings$factory.heightScale, this.heightScale) != 0) ? false : ((this.ironCount != chunkprovidersettings$factory.ironCount) ? false : ((this.ironMaxHeight != chunkprovidersettings$factory.ironMaxHeight) ? false : ((this.ironMinHeight != chunkprovidersettings$factory.ironMinHeight) ? false : ((this.ironSize != chunkprovidersettings$factory.ironSize) ? false : ((this.lapisCenterHeight != chunkprovidersettings$factory.lapisCenterHeight) ? false : ((this.lapisCount != chunkprovidersettings$factory.lapisCount) ? false : ((this.lapisSize != chunkprovidersettings$factory.lapisSize) ? false : ((this.lapisSpread != chunkprovidersettings$factory.lapisSpread) ? false : ((this.lavaLakeChance != chunkprovidersettings$factory.lavaLakeChance) ? false : ((Float.compare(chunkprovidersettings$factory.lowerLimitScale, this.lowerLimitScale) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.mainNoiseScaleX, this.mainNoiseScaleX) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.mainNoiseScaleY, this.mainNoiseScaleY) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.mainNoiseScaleZ, this.mainNoiseScaleZ) != 0) ? false : ((this.redstoneCount != chunkprovidersettings$factory.redstoneCount) ? false : ((this.redstoneMaxHeight != chunkprovidersettings$factory.redstoneMaxHeight) ? false : ((this.redstoneMinHeight != chunkprovidersettings$factory.redstoneMinHeight) ? false : ((this.redstoneSize != chunkprovidersettings$factory.redstoneSize) ? false : ((this.riverSize != chunkprovidersettings$factory.riverSize) ? false : ((this.seaLevel != chunkprovidersettings$factory.seaLevel) ? false : ((Float.compare(chunkprovidersettings$factory.stretchY, this.stretchY) != 0) ? false : ((Float.compare(chunkprovidersettings$factory.upperLimitScale, this.upperLimitScale) != 0) ? false : ((this.useCaves != chunkprovidersettings$factory.useCaves) ? false : ((this.useDungeons != chunkprovidersettings$factory.useDungeons) ? false : ((this.useLavaLakes != chunkprovidersettings$factory.useLavaLakes) ? false : ((this.useLavaOceans != chunkprovidersettings$factory.useLavaOceans) ? false : ((this.useMineShafts != chunkprovidersettings$factory.useMineShafts) ? false : ((this.useRavines != chunkprovidersettings$factory.useRavines) ? false : ((this.useStrongholds != chunkprovidersettings$factory.useStrongholds) ? false : ((this.useTemples != chunkprovidersettings$factory.useTemples) ? false : ((this.useMonuments != chunkprovidersettings$factory.useMonuments) ? false : ((this.useVillages != chunkprovidersettings$factory.useVillages) ? false : ((this.useWaterLakes != chunkprovidersettings$factory.useWaterLakes) ? false : ((this.waterLakeChance == chunkprovidersettings$factory.waterLakeChance))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))));
/*     */       } 
/* 368 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 373 */       int i = (this.coordinateScale != 0.0F) ? Float.floatToIntBits(this.coordinateScale) : 0;
/* 374 */       i = 31 * i + ((this.heightScale != 0.0F) ? Float.floatToIntBits(this.heightScale) : 0);
/* 375 */       i = 31 * i + ((this.upperLimitScale != 0.0F) ? Float.floatToIntBits(this.upperLimitScale) : 0);
/* 376 */       i = 31 * i + ((this.lowerLimitScale != 0.0F) ? Float.floatToIntBits(this.lowerLimitScale) : 0);
/* 377 */       i = 31 * i + ((this.depthNoiseScaleX != 0.0F) ? Float.floatToIntBits(this.depthNoiseScaleX) : 0);
/* 378 */       i = 31 * i + ((this.depthNoiseScaleZ != 0.0F) ? Float.floatToIntBits(this.depthNoiseScaleZ) : 0);
/* 379 */       i = 31 * i + ((this.depthNoiseScaleExponent != 0.0F) ? Float.floatToIntBits(this.depthNoiseScaleExponent) : 0);
/* 380 */       i = 31 * i + ((this.mainNoiseScaleX != 0.0F) ? Float.floatToIntBits(this.mainNoiseScaleX) : 0);
/* 381 */       i = 31 * i + ((this.mainNoiseScaleY != 0.0F) ? Float.floatToIntBits(this.mainNoiseScaleY) : 0);
/* 382 */       i = 31 * i + ((this.mainNoiseScaleZ != 0.0F) ? Float.floatToIntBits(this.mainNoiseScaleZ) : 0);
/* 383 */       i = 31 * i + ((this.baseSize != 0.0F) ? Float.floatToIntBits(this.baseSize) : 0);
/* 384 */       i = 31 * i + ((this.stretchY != 0.0F) ? Float.floatToIntBits(this.stretchY) : 0);
/* 385 */       i = 31 * i + ((this.biomeDepthWeight != 0.0F) ? Float.floatToIntBits(this.biomeDepthWeight) : 0);
/* 386 */       i = 31 * i + ((this.biomeDepthOffset != 0.0F) ? Float.floatToIntBits(this.biomeDepthOffset) : 0);
/* 387 */       i = 31 * i + ((this.biomeScaleWeight != 0.0F) ? Float.floatToIntBits(this.biomeScaleWeight) : 0);
/* 388 */       i = 31 * i + ((this.biomeScaleOffset != 0.0F) ? Float.floatToIntBits(this.biomeScaleOffset) : 0);
/* 389 */       i = 31 * i + this.seaLevel;
/* 390 */       i = 31 * i + (this.useCaves ? 1 : 0);
/* 391 */       i = 31 * i + (this.useDungeons ? 1 : 0);
/* 392 */       i = 31 * i + this.dungeonChance;
/* 393 */       i = 31 * i + (this.useStrongholds ? 1 : 0);
/* 394 */       i = 31 * i + (this.useVillages ? 1 : 0);
/* 395 */       i = 31 * i + (this.useMineShafts ? 1 : 0);
/* 396 */       i = 31 * i + (this.useTemples ? 1 : 0);
/* 397 */       i = 31 * i + (this.useMonuments ? 1 : 0);
/* 398 */       i = 31 * i + (this.useRavines ? 1 : 0);
/* 399 */       i = 31 * i + (this.useWaterLakes ? 1 : 0);
/* 400 */       i = 31 * i + this.waterLakeChance;
/* 401 */       i = 31 * i + (this.useLavaLakes ? 1 : 0);
/* 402 */       i = 31 * i + this.lavaLakeChance;
/* 403 */       i = 31 * i + (this.useLavaOceans ? 1 : 0);
/* 404 */       i = 31 * i + this.fixedBiome;
/* 405 */       i = 31 * i + this.biomeSize;
/* 406 */       i = 31 * i + this.riverSize;
/* 407 */       i = 31 * i + this.dirtSize;
/* 408 */       i = 31 * i + this.dirtCount;
/* 409 */       i = 31 * i + this.dirtMinHeight;
/* 410 */       i = 31 * i + this.dirtMaxHeight;
/* 411 */       i = 31 * i + this.gravelSize;
/* 412 */       i = 31 * i + this.gravelCount;
/* 413 */       i = 31 * i + this.gravelMinHeight;
/* 414 */       i = 31 * i + this.gravelMaxHeight;
/* 415 */       i = 31 * i + this.graniteSize;
/* 416 */       i = 31 * i + this.graniteCount;
/* 417 */       i = 31 * i + this.graniteMinHeight;
/* 418 */       i = 31 * i + this.graniteMaxHeight;
/* 419 */       i = 31 * i + this.dioriteSize;
/* 420 */       i = 31 * i + this.dioriteCount;
/* 421 */       i = 31 * i + this.dioriteMinHeight;
/* 422 */       i = 31 * i + this.dioriteMaxHeight;
/* 423 */       i = 31 * i + this.andesiteSize;
/* 424 */       i = 31 * i + this.andesiteCount;
/* 425 */       i = 31 * i + this.andesiteMinHeight;
/* 426 */       i = 31 * i + this.andesiteMaxHeight;
/* 427 */       i = 31 * i + this.coalSize;
/* 428 */       i = 31 * i + this.coalCount;
/* 429 */       i = 31 * i + this.coalMinHeight;
/* 430 */       i = 31 * i + this.coalMaxHeight;
/* 431 */       i = 31 * i + this.ironSize;
/* 432 */       i = 31 * i + this.ironCount;
/* 433 */       i = 31 * i + this.ironMinHeight;
/* 434 */       i = 31 * i + this.ironMaxHeight;
/* 435 */       i = 31 * i + this.goldSize;
/* 436 */       i = 31 * i + this.goldCount;
/* 437 */       i = 31 * i + this.goldMinHeight;
/* 438 */       i = 31 * i + this.goldMaxHeight;
/* 439 */       i = 31 * i + this.redstoneSize;
/* 440 */       i = 31 * i + this.redstoneCount;
/* 441 */       i = 31 * i + this.redstoneMinHeight;
/* 442 */       i = 31 * i + this.redstoneMaxHeight;
/* 443 */       i = 31 * i + this.diamondSize;
/* 444 */       i = 31 * i + this.diamondCount;
/* 445 */       i = 31 * i + this.diamondMinHeight;
/* 446 */       i = 31 * i + this.diamondMaxHeight;
/* 447 */       i = 31 * i + this.lapisSize;
/* 448 */       i = 31 * i + this.lapisCount;
/* 449 */       i = 31 * i + this.lapisCenterHeight;
/* 450 */       i = 31 * i + this.lapisSpread;
/* 451 */       return i;
/*     */     }
/*     */     
/*     */     public ChunkProviderSettings func_177864_b() {
/* 455 */       return new ChunkProviderSettings(this, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Serializer implements JsonDeserializer<Factory>, JsonSerializer<Factory> {
/*     */     public ChunkProviderSettings.Factory deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 461 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 462 */       ChunkProviderSettings.Factory chunkprovidersettings$factory = new ChunkProviderSettings.Factory();
/*     */       
/*     */       try {
/* 465 */         chunkprovidersettings$factory.coordinateScale = JsonUtils.getFloat(jsonobject, "coordinateScale", chunkprovidersettings$factory.coordinateScale);
/* 466 */         chunkprovidersettings$factory.heightScale = JsonUtils.getFloat(jsonobject, "heightScale", chunkprovidersettings$factory.heightScale);
/* 467 */         chunkprovidersettings$factory.lowerLimitScale = JsonUtils.getFloat(jsonobject, "lowerLimitScale", chunkprovidersettings$factory.lowerLimitScale);
/* 468 */         chunkprovidersettings$factory.upperLimitScale = JsonUtils.getFloat(jsonobject, "upperLimitScale", chunkprovidersettings$factory.upperLimitScale);
/* 469 */         chunkprovidersettings$factory.depthNoiseScaleX = JsonUtils.getFloat(jsonobject, "depthNoiseScaleX", chunkprovidersettings$factory.depthNoiseScaleX);
/* 470 */         chunkprovidersettings$factory.depthNoiseScaleZ = JsonUtils.getFloat(jsonobject, "depthNoiseScaleZ", chunkprovidersettings$factory.depthNoiseScaleZ);
/* 471 */         chunkprovidersettings$factory.depthNoiseScaleExponent = JsonUtils.getFloat(jsonobject, "depthNoiseScaleExponent", chunkprovidersettings$factory.depthNoiseScaleExponent);
/* 472 */         chunkprovidersettings$factory.mainNoiseScaleX = JsonUtils.getFloat(jsonobject, "mainNoiseScaleX", chunkprovidersettings$factory.mainNoiseScaleX);
/* 473 */         chunkprovidersettings$factory.mainNoiseScaleY = JsonUtils.getFloat(jsonobject, "mainNoiseScaleY", chunkprovidersettings$factory.mainNoiseScaleY);
/* 474 */         chunkprovidersettings$factory.mainNoiseScaleZ = JsonUtils.getFloat(jsonobject, "mainNoiseScaleZ", chunkprovidersettings$factory.mainNoiseScaleZ);
/* 475 */         chunkprovidersettings$factory.baseSize = JsonUtils.getFloat(jsonobject, "baseSize", chunkprovidersettings$factory.baseSize);
/* 476 */         chunkprovidersettings$factory.stretchY = JsonUtils.getFloat(jsonobject, "stretchY", chunkprovidersettings$factory.stretchY);
/* 477 */         chunkprovidersettings$factory.biomeDepthWeight = JsonUtils.getFloat(jsonobject, "biomeDepthWeight", chunkprovidersettings$factory.biomeDepthWeight);
/* 478 */         chunkprovidersettings$factory.biomeDepthOffset = JsonUtils.getFloat(jsonobject, "biomeDepthOffset", chunkprovidersettings$factory.biomeDepthOffset);
/* 479 */         chunkprovidersettings$factory.biomeScaleWeight = JsonUtils.getFloat(jsonobject, "biomeScaleWeight", chunkprovidersettings$factory.biomeScaleWeight);
/* 480 */         chunkprovidersettings$factory.biomeScaleOffset = JsonUtils.getFloat(jsonobject, "biomeScaleOffset", chunkprovidersettings$factory.biomeScaleOffset);
/* 481 */         chunkprovidersettings$factory.seaLevel = JsonUtils.getInt(jsonobject, "seaLevel", chunkprovidersettings$factory.seaLevel);
/* 482 */         chunkprovidersettings$factory.useCaves = JsonUtils.getBoolean(jsonobject, "useCaves", chunkprovidersettings$factory.useCaves);
/* 483 */         chunkprovidersettings$factory.useDungeons = JsonUtils.getBoolean(jsonobject, "useDungeons", chunkprovidersettings$factory.useDungeons);
/* 484 */         chunkprovidersettings$factory.dungeonChance = JsonUtils.getInt(jsonobject, "dungeonChance", chunkprovidersettings$factory.dungeonChance);
/* 485 */         chunkprovidersettings$factory.useStrongholds = JsonUtils.getBoolean(jsonobject, "useStrongholds", chunkprovidersettings$factory.useStrongholds);
/* 486 */         chunkprovidersettings$factory.useVillages = JsonUtils.getBoolean(jsonobject, "useVillages", chunkprovidersettings$factory.useVillages);
/* 487 */         chunkprovidersettings$factory.useMineShafts = JsonUtils.getBoolean(jsonobject, "useMineShafts", chunkprovidersettings$factory.useMineShafts);
/* 488 */         chunkprovidersettings$factory.useTemples = JsonUtils.getBoolean(jsonobject, "useTemples", chunkprovidersettings$factory.useTemples);
/* 489 */         chunkprovidersettings$factory.useMonuments = JsonUtils.getBoolean(jsonobject, "useMonuments", chunkprovidersettings$factory.useMonuments);
/* 490 */         chunkprovidersettings$factory.useRavines = JsonUtils.getBoolean(jsonobject, "useRavines", chunkprovidersettings$factory.useRavines);
/* 491 */         chunkprovidersettings$factory.useWaterLakes = JsonUtils.getBoolean(jsonobject, "useWaterLakes", chunkprovidersettings$factory.useWaterLakes);
/* 492 */         chunkprovidersettings$factory.waterLakeChance = JsonUtils.getInt(jsonobject, "waterLakeChance", chunkprovidersettings$factory.waterLakeChance);
/* 493 */         chunkprovidersettings$factory.useLavaLakes = JsonUtils.getBoolean(jsonobject, "useLavaLakes", chunkprovidersettings$factory.useLavaLakes);
/* 494 */         chunkprovidersettings$factory.lavaLakeChance = JsonUtils.getInt(jsonobject, "lavaLakeChance", chunkprovidersettings$factory.lavaLakeChance);
/* 495 */         chunkprovidersettings$factory.useLavaOceans = JsonUtils.getBoolean(jsonobject, "useLavaOceans", chunkprovidersettings$factory.useLavaOceans);
/* 496 */         chunkprovidersettings$factory.fixedBiome = JsonUtils.getInt(jsonobject, "fixedBiome", chunkprovidersettings$factory.fixedBiome);
/*     */         
/* 498 */         if (chunkprovidersettings$factory.fixedBiome < 38 && chunkprovidersettings$factory.fixedBiome >= -1) {
/* 499 */           if (chunkprovidersettings$factory.fixedBiome >= BiomeGenBase.hell.biomeID) {
/* 500 */             chunkprovidersettings$factory.fixedBiome += 2;
/*     */           }
/*     */         } else {
/* 503 */           chunkprovidersettings$factory.fixedBiome = -1;
/*     */         } 
/*     */         
/* 506 */         chunkprovidersettings$factory.biomeSize = JsonUtils.getInt(jsonobject, "biomeSize", chunkprovidersettings$factory.biomeSize);
/* 507 */         chunkprovidersettings$factory.riverSize = JsonUtils.getInt(jsonobject, "riverSize", chunkprovidersettings$factory.riverSize);
/* 508 */         chunkprovidersettings$factory.dirtSize = JsonUtils.getInt(jsonobject, "dirtSize", chunkprovidersettings$factory.dirtSize);
/* 509 */         chunkprovidersettings$factory.dirtCount = JsonUtils.getInt(jsonobject, "dirtCount", chunkprovidersettings$factory.dirtCount);
/* 510 */         chunkprovidersettings$factory.dirtMinHeight = JsonUtils.getInt(jsonobject, "dirtMinHeight", chunkprovidersettings$factory.dirtMinHeight);
/* 511 */         chunkprovidersettings$factory.dirtMaxHeight = JsonUtils.getInt(jsonobject, "dirtMaxHeight", chunkprovidersettings$factory.dirtMaxHeight);
/* 512 */         chunkprovidersettings$factory.gravelSize = JsonUtils.getInt(jsonobject, "gravelSize", chunkprovidersettings$factory.gravelSize);
/* 513 */         chunkprovidersettings$factory.gravelCount = JsonUtils.getInt(jsonobject, "gravelCount", chunkprovidersettings$factory.gravelCount);
/* 514 */         chunkprovidersettings$factory.gravelMinHeight = JsonUtils.getInt(jsonobject, "gravelMinHeight", chunkprovidersettings$factory.gravelMinHeight);
/* 515 */         chunkprovidersettings$factory.gravelMaxHeight = JsonUtils.getInt(jsonobject, "gravelMaxHeight", chunkprovidersettings$factory.gravelMaxHeight);
/* 516 */         chunkprovidersettings$factory.graniteSize = JsonUtils.getInt(jsonobject, "graniteSize", chunkprovidersettings$factory.graniteSize);
/* 517 */         chunkprovidersettings$factory.graniteCount = JsonUtils.getInt(jsonobject, "graniteCount", chunkprovidersettings$factory.graniteCount);
/* 518 */         chunkprovidersettings$factory.graniteMinHeight = JsonUtils.getInt(jsonobject, "graniteMinHeight", chunkprovidersettings$factory.graniteMinHeight);
/* 519 */         chunkprovidersettings$factory.graniteMaxHeight = JsonUtils.getInt(jsonobject, "graniteMaxHeight", chunkprovidersettings$factory.graniteMaxHeight);
/* 520 */         chunkprovidersettings$factory.dioriteSize = JsonUtils.getInt(jsonobject, "dioriteSize", chunkprovidersettings$factory.dioriteSize);
/* 521 */         chunkprovidersettings$factory.dioriteCount = JsonUtils.getInt(jsonobject, "dioriteCount", chunkprovidersettings$factory.dioriteCount);
/* 522 */         chunkprovidersettings$factory.dioriteMinHeight = JsonUtils.getInt(jsonobject, "dioriteMinHeight", chunkprovidersettings$factory.dioriteMinHeight);
/* 523 */         chunkprovidersettings$factory.dioriteMaxHeight = JsonUtils.getInt(jsonobject, "dioriteMaxHeight", chunkprovidersettings$factory.dioriteMaxHeight);
/* 524 */         chunkprovidersettings$factory.andesiteSize = JsonUtils.getInt(jsonobject, "andesiteSize", chunkprovidersettings$factory.andesiteSize);
/* 525 */         chunkprovidersettings$factory.andesiteCount = JsonUtils.getInt(jsonobject, "andesiteCount", chunkprovidersettings$factory.andesiteCount);
/* 526 */         chunkprovidersettings$factory.andesiteMinHeight = JsonUtils.getInt(jsonobject, "andesiteMinHeight", chunkprovidersettings$factory.andesiteMinHeight);
/* 527 */         chunkprovidersettings$factory.andesiteMaxHeight = JsonUtils.getInt(jsonobject, "andesiteMaxHeight", chunkprovidersettings$factory.andesiteMaxHeight);
/* 528 */         chunkprovidersettings$factory.coalSize = JsonUtils.getInt(jsonobject, "coalSize", chunkprovidersettings$factory.coalSize);
/* 529 */         chunkprovidersettings$factory.coalCount = JsonUtils.getInt(jsonobject, "coalCount", chunkprovidersettings$factory.coalCount);
/* 530 */         chunkprovidersettings$factory.coalMinHeight = JsonUtils.getInt(jsonobject, "coalMinHeight", chunkprovidersettings$factory.coalMinHeight);
/* 531 */         chunkprovidersettings$factory.coalMaxHeight = JsonUtils.getInt(jsonobject, "coalMaxHeight", chunkprovidersettings$factory.coalMaxHeight);
/* 532 */         chunkprovidersettings$factory.ironSize = JsonUtils.getInt(jsonobject, "ironSize", chunkprovidersettings$factory.ironSize);
/* 533 */         chunkprovidersettings$factory.ironCount = JsonUtils.getInt(jsonobject, "ironCount", chunkprovidersettings$factory.ironCount);
/* 534 */         chunkprovidersettings$factory.ironMinHeight = JsonUtils.getInt(jsonobject, "ironMinHeight", chunkprovidersettings$factory.ironMinHeight);
/* 535 */         chunkprovidersettings$factory.ironMaxHeight = JsonUtils.getInt(jsonobject, "ironMaxHeight", chunkprovidersettings$factory.ironMaxHeight);
/* 536 */         chunkprovidersettings$factory.goldSize = JsonUtils.getInt(jsonobject, "goldSize", chunkprovidersettings$factory.goldSize);
/* 537 */         chunkprovidersettings$factory.goldCount = JsonUtils.getInt(jsonobject, "goldCount", chunkprovidersettings$factory.goldCount);
/* 538 */         chunkprovidersettings$factory.goldMinHeight = JsonUtils.getInt(jsonobject, "goldMinHeight", chunkprovidersettings$factory.goldMinHeight);
/* 539 */         chunkprovidersettings$factory.goldMaxHeight = JsonUtils.getInt(jsonobject, "goldMaxHeight", chunkprovidersettings$factory.goldMaxHeight);
/* 540 */         chunkprovidersettings$factory.redstoneSize = JsonUtils.getInt(jsonobject, "redstoneSize", chunkprovidersettings$factory.redstoneSize);
/* 541 */         chunkprovidersettings$factory.redstoneCount = JsonUtils.getInt(jsonobject, "redstoneCount", chunkprovidersettings$factory.redstoneCount);
/* 542 */         chunkprovidersettings$factory.redstoneMinHeight = JsonUtils.getInt(jsonobject, "redstoneMinHeight", chunkprovidersettings$factory.redstoneMinHeight);
/* 543 */         chunkprovidersettings$factory.redstoneMaxHeight = JsonUtils.getInt(jsonobject, "redstoneMaxHeight", chunkprovidersettings$factory.redstoneMaxHeight);
/* 544 */         chunkprovidersettings$factory.diamondSize = JsonUtils.getInt(jsonobject, "diamondSize", chunkprovidersettings$factory.diamondSize);
/* 545 */         chunkprovidersettings$factory.diamondCount = JsonUtils.getInt(jsonobject, "diamondCount", chunkprovidersettings$factory.diamondCount);
/* 546 */         chunkprovidersettings$factory.diamondMinHeight = JsonUtils.getInt(jsonobject, "diamondMinHeight", chunkprovidersettings$factory.diamondMinHeight);
/* 547 */         chunkprovidersettings$factory.diamondMaxHeight = JsonUtils.getInt(jsonobject, "diamondMaxHeight", chunkprovidersettings$factory.diamondMaxHeight);
/* 548 */         chunkprovidersettings$factory.lapisSize = JsonUtils.getInt(jsonobject, "lapisSize", chunkprovidersettings$factory.lapisSize);
/* 549 */         chunkprovidersettings$factory.lapisCount = JsonUtils.getInt(jsonobject, "lapisCount", chunkprovidersettings$factory.lapisCount);
/* 550 */         chunkprovidersettings$factory.lapisCenterHeight = JsonUtils.getInt(jsonobject, "lapisCenterHeight", chunkprovidersettings$factory.lapisCenterHeight);
/* 551 */         chunkprovidersettings$factory.lapisSpread = JsonUtils.getInt(jsonobject, "lapisSpread", chunkprovidersettings$factory.lapisSpread);
/* 552 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */       
/* 556 */       return chunkprovidersettings$factory;
/*     */     }
/*     */     
/*     */     public JsonElement serialize(ChunkProviderSettings.Factory p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 560 */       JsonObject jsonobject = new JsonObject();
/* 561 */       jsonobject.addProperty("coordinateScale", Float.valueOf(p_serialize_1_.coordinateScale));
/* 562 */       jsonobject.addProperty("heightScale", Float.valueOf(p_serialize_1_.heightScale));
/* 563 */       jsonobject.addProperty("lowerLimitScale", Float.valueOf(p_serialize_1_.lowerLimitScale));
/* 564 */       jsonobject.addProperty("upperLimitScale", Float.valueOf(p_serialize_1_.upperLimitScale));
/* 565 */       jsonobject.addProperty("depthNoiseScaleX", Float.valueOf(p_serialize_1_.depthNoiseScaleX));
/* 566 */       jsonobject.addProperty("depthNoiseScaleZ", Float.valueOf(p_serialize_1_.depthNoiseScaleZ));
/* 567 */       jsonobject.addProperty("depthNoiseScaleExponent", Float.valueOf(p_serialize_1_.depthNoiseScaleExponent));
/* 568 */       jsonobject.addProperty("mainNoiseScaleX", Float.valueOf(p_serialize_1_.mainNoiseScaleX));
/* 569 */       jsonobject.addProperty("mainNoiseScaleY", Float.valueOf(p_serialize_1_.mainNoiseScaleY));
/* 570 */       jsonobject.addProperty("mainNoiseScaleZ", Float.valueOf(p_serialize_1_.mainNoiseScaleZ));
/* 571 */       jsonobject.addProperty("baseSize", Float.valueOf(p_serialize_1_.baseSize));
/* 572 */       jsonobject.addProperty("stretchY", Float.valueOf(p_serialize_1_.stretchY));
/* 573 */       jsonobject.addProperty("biomeDepthWeight", Float.valueOf(p_serialize_1_.biomeDepthWeight));
/* 574 */       jsonobject.addProperty("biomeDepthOffset", Float.valueOf(p_serialize_1_.biomeDepthOffset));
/* 575 */       jsonobject.addProperty("biomeScaleWeight", Float.valueOf(p_serialize_1_.biomeScaleWeight));
/* 576 */       jsonobject.addProperty("biomeScaleOffset", Float.valueOf(p_serialize_1_.biomeScaleOffset));
/* 577 */       jsonobject.addProperty("seaLevel", Integer.valueOf(p_serialize_1_.seaLevel));
/* 578 */       jsonobject.addProperty("useCaves", Boolean.valueOf(p_serialize_1_.useCaves));
/* 579 */       jsonobject.addProperty("useDungeons", Boolean.valueOf(p_serialize_1_.useDungeons));
/* 580 */       jsonobject.addProperty("dungeonChance", Integer.valueOf(p_serialize_1_.dungeonChance));
/* 581 */       jsonobject.addProperty("useStrongholds", Boolean.valueOf(p_serialize_1_.useStrongholds));
/* 582 */       jsonobject.addProperty("useVillages", Boolean.valueOf(p_serialize_1_.useVillages));
/* 583 */       jsonobject.addProperty("useMineShafts", Boolean.valueOf(p_serialize_1_.useMineShafts));
/* 584 */       jsonobject.addProperty("useTemples", Boolean.valueOf(p_serialize_1_.useTemples));
/* 585 */       jsonobject.addProperty("useMonuments", Boolean.valueOf(p_serialize_1_.useMonuments));
/* 586 */       jsonobject.addProperty("useRavines", Boolean.valueOf(p_serialize_1_.useRavines));
/* 587 */       jsonobject.addProperty("useWaterLakes", Boolean.valueOf(p_serialize_1_.useWaterLakes));
/* 588 */       jsonobject.addProperty("waterLakeChance", Integer.valueOf(p_serialize_1_.waterLakeChance));
/* 589 */       jsonobject.addProperty("useLavaLakes", Boolean.valueOf(p_serialize_1_.useLavaLakes));
/* 590 */       jsonobject.addProperty("lavaLakeChance", Integer.valueOf(p_serialize_1_.lavaLakeChance));
/* 591 */       jsonobject.addProperty("useLavaOceans", Boolean.valueOf(p_serialize_1_.useLavaOceans));
/* 592 */       jsonobject.addProperty("fixedBiome", Integer.valueOf(p_serialize_1_.fixedBiome));
/* 593 */       jsonobject.addProperty("biomeSize", Integer.valueOf(p_serialize_1_.biomeSize));
/* 594 */       jsonobject.addProperty("riverSize", Integer.valueOf(p_serialize_1_.riverSize));
/* 595 */       jsonobject.addProperty("dirtSize", Integer.valueOf(p_serialize_1_.dirtSize));
/* 596 */       jsonobject.addProperty("dirtCount", Integer.valueOf(p_serialize_1_.dirtCount));
/* 597 */       jsonobject.addProperty("dirtMinHeight", Integer.valueOf(p_serialize_1_.dirtMinHeight));
/* 598 */       jsonobject.addProperty("dirtMaxHeight", Integer.valueOf(p_serialize_1_.dirtMaxHeight));
/* 599 */       jsonobject.addProperty("gravelSize", Integer.valueOf(p_serialize_1_.gravelSize));
/* 600 */       jsonobject.addProperty("gravelCount", Integer.valueOf(p_serialize_1_.gravelCount));
/* 601 */       jsonobject.addProperty("gravelMinHeight", Integer.valueOf(p_serialize_1_.gravelMinHeight));
/* 602 */       jsonobject.addProperty("gravelMaxHeight", Integer.valueOf(p_serialize_1_.gravelMaxHeight));
/* 603 */       jsonobject.addProperty("graniteSize", Integer.valueOf(p_serialize_1_.graniteSize));
/* 604 */       jsonobject.addProperty("graniteCount", Integer.valueOf(p_serialize_1_.graniteCount));
/* 605 */       jsonobject.addProperty("graniteMinHeight", Integer.valueOf(p_serialize_1_.graniteMinHeight));
/* 606 */       jsonobject.addProperty("graniteMaxHeight", Integer.valueOf(p_serialize_1_.graniteMaxHeight));
/* 607 */       jsonobject.addProperty("dioriteSize", Integer.valueOf(p_serialize_1_.dioriteSize));
/* 608 */       jsonobject.addProperty("dioriteCount", Integer.valueOf(p_serialize_1_.dioriteCount));
/* 609 */       jsonobject.addProperty("dioriteMinHeight", Integer.valueOf(p_serialize_1_.dioriteMinHeight));
/* 610 */       jsonobject.addProperty("dioriteMaxHeight", Integer.valueOf(p_serialize_1_.dioriteMaxHeight));
/* 611 */       jsonobject.addProperty("andesiteSize", Integer.valueOf(p_serialize_1_.andesiteSize));
/* 612 */       jsonobject.addProperty("andesiteCount", Integer.valueOf(p_serialize_1_.andesiteCount));
/* 613 */       jsonobject.addProperty("andesiteMinHeight", Integer.valueOf(p_serialize_1_.andesiteMinHeight));
/* 614 */       jsonobject.addProperty("andesiteMaxHeight", Integer.valueOf(p_serialize_1_.andesiteMaxHeight));
/* 615 */       jsonobject.addProperty("coalSize", Integer.valueOf(p_serialize_1_.coalSize));
/* 616 */       jsonobject.addProperty("coalCount", Integer.valueOf(p_serialize_1_.coalCount));
/* 617 */       jsonobject.addProperty("coalMinHeight", Integer.valueOf(p_serialize_1_.coalMinHeight));
/* 618 */       jsonobject.addProperty("coalMaxHeight", Integer.valueOf(p_serialize_1_.coalMaxHeight));
/* 619 */       jsonobject.addProperty("ironSize", Integer.valueOf(p_serialize_1_.ironSize));
/* 620 */       jsonobject.addProperty("ironCount", Integer.valueOf(p_serialize_1_.ironCount));
/* 621 */       jsonobject.addProperty("ironMinHeight", Integer.valueOf(p_serialize_1_.ironMinHeight));
/* 622 */       jsonobject.addProperty("ironMaxHeight", Integer.valueOf(p_serialize_1_.ironMaxHeight));
/* 623 */       jsonobject.addProperty("goldSize", Integer.valueOf(p_serialize_1_.goldSize));
/* 624 */       jsonobject.addProperty("goldCount", Integer.valueOf(p_serialize_1_.goldCount));
/* 625 */       jsonobject.addProperty("goldMinHeight", Integer.valueOf(p_serialize_1_.goldMinHeight));
/* 626 */       jsonobject.addProperty("goldMaxHeight", Integer.valueOf(p_serialize_1_.goldMaxHeight));
/* 627 */       jsonobject.addProperty("redstoneSize", Integer.valueOf(p_serialize_1_.redstoneSize));
/* 628 */       jsonobject.addProperty("redstoneCount", Integer.valueOf(p_serialize_1_.redstoneCount));
/* 629 */       jsonobject.addProperty("redstoneMinHeight", Integer.valueOf(p_serialize_1_.redstoneMinHeight));
/* 630 */       jsonobject.addProperty("redstoneMaxHeight", Integer.valueOf(p_serialize_1_.redstoneMaxHeight));
/* 631 */       jsonobject.addProperty("diamondSize", Integer.valueOf(p_serialize_1_.diamondSize));
/* 632 */       jsonobject.addProperty("diamondCount", Integer.valueOf(p_serialize_1_.diamondCount));
/* 633 */       jsonobject.addProperty("diamondMinHeight", Integer.valueOf(p_serialize_1_.diamondMinHeight));
/* 634 */       jsonobject.addProperty("diamondMaxHeight", Integer.valueOf(p_serialize_1_.diamondMaxHeight));
/* 635 */       jsonobject.addProperty("lapisSize", Integer.valueOf(p_serialize_1_.lapisSize));
/* 636 */       jsonobject.addProperty("lapisCount", Integer.valueOf(p_serialize_1_.lapisCount));
/* 637 */       jsonobject.addProperty("lapisCenterHeight", Integer.valueOf(p_serialize_1_.lapisCenterHeight));
/* 638 */       jsonobject.addProperty("lapisSpread", Integer.valueOf(p_serialize_1_.lapisSpread));
/* 639 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\ChunkProviderSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */