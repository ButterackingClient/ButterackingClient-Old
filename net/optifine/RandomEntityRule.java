/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.Matches;
/*     */ import net.optifine.config.NbtTagValue;
/*     */ import net.optifine.config.RangeInt;
/*     */ import net.optifine.config.RangeListInt;
/*     */ import net.optifine.config.VillagerProfession;
/*     */ import net.optifine.config.Weather;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.util.ArrayUtils;
/*     */ import net.optifine.util.MathUtils;
/*     */ 
/*     */ public class RandomEntityRule {
/*  27 */   private String pathProps = null;
/*  28 */   private ResourceLocation baseResLoc = null;
/*     */   private int index;
/*  30 */   private int[] textures = null;
/*  31 */   private ResourceLocation[] resourceLocations = null;
/*  32 */   private int[] weights = null;
/*  33 */   private BiomeGenBase[] biomes = null;
/*  34 */   private RangeListInt heights = null;
/*  35 */   private RangeListInt healthRange = null;
/*     */   private boolean healthPercent = false;
/*  37 */   private NbtTagValue nbtName = null;
/*  38 */   public int[] sumWeights = null;
/*  39 */   public int sumAllWeights = 1;
/*  40 */   private VillagerProfession[] professions = null;
/*  41 */   private EnumDyeColor[] collarColors = null;
/*  42 */   private Boolean baby = null;
/*  43 */   private RangeListInt moonPhases = null;
/*  44 */   private RangeListInt dayTimes = null;
/*  45 */   private Weather[] weatherList = null;
/*     */   
/*     */   public RandomEntityRule(Properties props, String pathProps, ResourceLocation baseResLoc, int index, String valTextures, ConnectedParser cp) {
/*  48 */     this.pathProps = pathProps;
/*  49 */     this.baseResLoc = baseResLoc;
/*  50 */     this.index = index;
/*  51 */     this.textures = cp.parseIntList(valTextures);
/*  52 */     this.weights = cp.parseIntList(props.getProperty("weights." + index));
/*  53 */     this.biomes = cp.parseBiomes(props.getProperty("biomes." + index));
/*  54 */     this.heights = cp.parseRangeListInt(props.getProperty("heights." + index));
/*     */     
/*  56 */     if (this.heights == null) {
/*  57 */       this.heights = parseMinMaxHeight(props, index);
/*     */     }
/*     */     
/*  60 */     String s = props.getProperty("health." + index);
/*     */     
/*  62 */     if (s != null) {
/*  63 */       this.healthPercent = s.contains("%");
/*  64 */       s = s.replace("%", "");
/*  65 */       this.healthRange = cp.parseRangeListInt(s);
/*     */     } 
/*     */     
/*  68 */     this.nbtName = cp.parseNbtTagValue("name", props.getProperty("name." + index));
/*  69 */     this.professions = cp.parseProfessions(props.getProperty("professions." + index));
/*  70 */     this.collarColors = cp.parseDyeColors(props.getProperty("collarColors." + index), "collar color", ConnectedParser.DYE_COLORS_INVALID);
/*  71 */     this.baby = cp.parseBooleanObject(props.getProperty("baby." + index));
/*  72 */     this.moonPhases = cp.parseRangeListInt(props.getProperty("moonPhase." + index));
/*  73 */     this.dayTimes = cp.parseRangeListInt(props.getProperty("dayTime." + index));
/*  74 */     this.weatherList = cp.parseWeather(props.getProperty("weather." + index), "weather." + index, null);
/*     */   }
/*     */   
/*     */   private RangeListInt parseMinMaxHeight(Properties props, int index) {
/*  78 */     String s = props.getProperty("minHeight." + index);
/*  79 */     String s1 = props.getProperty("maxHeight." + index);
/*     */     
/*  81 */     if (s == null && s1 == null) {
/*  82 */       return null;
/*     */     }
/*  84 */     int i = 0;
/*     */     
/*  86 */     if (s != null) {
/*  87 */       i = Config.parseInt(s, -1);
/*     */       
/*  89 */       if (i < 0) {
/*  90 */         Config.warn("Invalid minHeight: " + s);
/*  91 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     int j = 256;
/*     */     
/*  97 */     if (s1 != null) {
/*  98 */       j = Config.parseInt(s1, -1);
/*     */       
/* 100 */       if (j < 0) {
/* 101 */         Config.warn("Invalid maxHeight: " + s1);
/* 102 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     if (j < 0) {
/* 107 */       Config.warn("Invalid minHeight, maxHeight: " + s + ", " + s1);
/* 108 */       return null;
/*     */     } 
/* 110 */     RangeListInt rangelistint = new RangeListInt();
/* 111 */     rangelistint.addRange(new RangeInt(i, j));
/* 112 */     return rangelistint;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(String path) {
/* 118 */     if (this.textures != null && this.textures.length != 0) {
/* 119 */       if (this.resourceLocations != null) {
/* 120 */         return true;
/*     */       }
/* 122 */       this.resourceLocations = new ResourceLocation[this.textures.length];
/* 123 */       boolean flag = this.pathProps.startsWith("mcpatcher/mob/");
/* 124 */       ResourceLocation resourcelocation = RandomEntities.getLocationRandom(this.baseResLoc, flag);
/*     */       
/* 126 */       if (resourcelocation == null) {
/* 127 */         Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
/* 128 */         return false;
/*     */       } 
/* 130 */       for (int i = 0; i < this.resourceLocations.length; i++) {
/* 131 */         int j = this.textures[i];
/*     */         
/* 133 */         if (j <= 1) {
/* 134 */           this.resourceLocations[i] = this.baseResLoc;
/*     */         } else {
/* 136 */           ResourceLocation resourcelocation1 = RandomEntities.getLocationIndexed(resourcelocation, j);
/*     */           
/* 138 */           if (resourcelocation1 == null) {
/* 139 */             Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
/* 140 */             return false;
/*     */           } 
/*     */           
/* 143 */           if (!Config.hasResource(resourcelocation1)) {
/* 144 */             Config.warn("Texture not found: " + resourcelocation1.getResourcePath());
/* 145 */             return false;
/*     */           } 
/*     */           
/* 148 */           this.resourceLocations[i] = resourcelocation1;
/*     */         } 
/*     */       } 
/*     */       
/* 152 */       if (this.weights != null) {
/* 153 */         if (this.weights.length > this.resourceLocations.length) {
/* 154 */           Config.warn("More weights defined than skins, trimming weights: " + path);
/* 155 */           int[] aint = new int[this.resourceLocations.length];
/* 156 */           System.arraycopy(this.weights, 0, aint, 0, aint.length);
/* 157 */           this.weights = aint;
/*     */         } 
/*     */         
/* 160 */         if (this.weights.length < this.resourceLocations.length) {
/* 161 */           Config.warn("Less weights defined than skins, expanding weights: " + path);
/* 162 */           int[] aint1 = new int[this.resourceLocations.length];
/* 163 */           System.arraycopy(this.weights, 0, aint1, 0, this.weights.length);
/* 164 */           int l = MathUtils.getAverage(this.weights);
/*     */           
/* 166 */           for (int j1 = this.weights.length; j1 < aint1.length; j1++) {
/* 167 */             aint1[j1] = l;
/*     */           }
/*     */           
/* 170 */           this.weights = aint1;
/*     */         } 
/*     */         
/* 173 */         this.sumWeights = new int[this.weights.length];
/* 174 */         int k = 0;
/*     */         
/* 176 */         for (int i1 = 0; i1 < this.weights.length; i1++) {
/* 177 */           if (this.weights[i1] < 0) {
/* 178 */             Config.warn("Invalid weight: " + this.weights[i1]);
/* 179 */             return false;
/*     */           } 
/*     */           
/* 182 */           k += this.weights[i1];
/* 183 */           this.sumWeights[i1] = k;
/*     */         } 
/*     */         
/* 186 */         this.sumAllWeights = k;
/*     */         
/* 188 */         if (this.sumAllWeights <= 0) {
/* 189 */           Config.warn("Invalid sum of all weights: " + k);
/* 190 */           this.sumAllWeights = 1;
/*     */         } 
/*     */       } 
/*     */       
/* 194 */       if (this.professions == ConnectedParser.PROFESSIONS_INVALID) {
/* 195 */         Config.warn("Invalid professions or careers: " + path);
/* 196 */         return false;
/* 197 */       }  if (this.collarColors == ConnectedParser.DYE_COLORS_INVALID) {
/* 198 */         Config.warn("Invalid collar colors: " + path);
/* 199 */         return false;
/*     */       } 
/* 201 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 206 */     Config.warn("Invalid skins for rule: " + this.index);
/* 207 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(IRandomEntity randomEntity) {
/* 212 */     if (this.biomes != null && !Matches.biome(randomEntity.getSpawnBiome(), this.biomes)) {
/* 213 */       return false;
/*     */     }
/* 215 */     if (this.heights != null) {
/* 216 */       BlockPos blockpos = randomEntity.getSpawnPosition();
/*     */       
/* 218 */       if (blockpos != null && !this.heights.isInRange(blockpos.getY())) {
/* 219 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 223 */     if (this.healthRange != null) {
/* 224 */       int i1 = randomEntity.getHealth();
/*     */       
/* 226 */       if (this.healthPercent) {
/* 227 */         int i = randomEntity.getMaxHealth();
/*     */         
/* 229 */         if (i > 0) {
/* 230 */           i1 = (int)((i1 * 100) / i);
/*     */         }
/*     */       } 
/*     */       
/* 234 */       if (!this.healthRange.isInRange(i1)) {
/* 235 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 239 */     if (this.nbtName != null) {
/* 240 */       String s = randomEntity.getName();
/*     */       
/* 242 */       if (!this.nbtName.matchesValue(s)) {
/* 243 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 247 */     if (this.professions != null && randomEntity instanceof RandomEntity) {
/* 248 */       RandomEntity randomentity = (RandomEntity)randomEntity;
/* 249 */       Entity entity = randomentity.getEntity();
/*     */       
/* 251 */       if (entity instanceof EntityVillager) {
/* 252 */         EntityVillager entityvillager = (EntityVillager)entity;
/* 253 */         int j = entityvillager.getProfession();
/* 254 */         int k = Reflector.getFieldValueInt(entityvillager, Reflector.EntityVillager_careerId, -1);
/*     */         
/* 256 */         if (j < 0 || k < 0) {
/* 257 */           return false;
/*     */         }
/*     */         
/* 260 */         boolean flag = false;
/*     */         
/* 262 */         for (int l = 0; l < this.professions.length; l++) {
/* 263 */           VillagerProfession villagerprofession = this.professions[l];
/*     */           
/* 265 */           if (villagerprofession.matches(j, k)) {
/* 266 */             flag = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 271 */         if (!flag) {
/* 272 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 277 */     if (this.collarColors != null && randomEntity instanceof RandomEntity) {
/* 278 */       RandomEntity randomentity1 = (RandomEntity)randomEntity;
/* 279 */       Entity entity1 = randomentity1.getEntity();
/*     */       
/* 281 */       if (entity1 instanceof EntityWolf) {
/* 282 */         EntityWolf entitywolf = (EntityWolf)entity1;
/*     */         
/* 284 */         if (!entitywolf.isTamed()) {
/* 285 */           return false;
/*     */         }
/*     */         
/* 288 */         EnumDyeColor enumdyecolor = entitywolf.getCollarColor();
/*     */         
/* 290 */         if (!Config.equalsOne(enumdyecolor, (Object[])this.collarColors)) {
/* 291 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     if (this.baby != null && randomEntity instanceof RandomEntity) {
/* 297 */       RandomEntity randomentity2 = (RandomEntity)randomEntity;
/* 298 */       Entity entity2 = randomentity2.getEntity();
/*     */       
/* 300 */       if (entity2 instanceof EntityLiving) {
/* 301 */         EntityLiving entityliving = (EntityLiving)entity2;
/*     */         
/* 303 */         if (entityliving.isChild() != this.baby.booleanValue()) {
/* 304 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 309 */     if (this.moonPhases != null) {
/* 310 */       WorldClient worldClient = (Config.getMinecraft()).theWorld;
/*     */       
/* 312 */       if (worldClient != null) {
/* 313 */         int j1 = worldClient.getMoonPhase();
/*     */         
/* 315 */         if (!this.moonPhases.isInRange(j1)) {
/* 316 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 321 */     if (this.dayTimes != null) {
/* 322 */       WorldClient worldClient = (Config.getMinecraft()).theWorld;
/*     */       
/* 324 */       if (worldClient != null) {
/* 325 */         int k1 = (int)worldClient.getWorldInfo().getWorldTime();
/*     */         
/* 327 */         if (!this.dayTimes.isInRange(k1)) {
/* 328 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 333 */     if (this.weatherList != null) {
/* 334 */       WorldClient worldClient = (Config.getMinecraft()).theWorld;
/*     */       
/* 336 */       if (worldClient != null) {
/* 337 */         Weather weather = Weather.getWeather((World)worldClient, 0.0F);
/*     */         
/* 339 */         if (!ArrayUtils.contains((Object[])this.weatherList, weather)) {
/* 340 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 345 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(ResourceLocation loc, int randomId) {
/* 350 */     if (this.resourceLocations != null && this.resourceLocations.length != 0) {
/* 351 */       int i = 0;
/*     */       
/* 353 */       if (this.weights == null) {
/* 354 */         i = randomId % this.resourceLocations.length;
/*     */       } else {
/* 356 */         int j = randomId % this.sumAllWeights;
/*     */         
/* 358 */         for (int k = 0; k < this.sumWeights.length; k++) {
/* 359 */           if (this.sumWeights[k] > j) {
/* 360 */             i = k;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 366 */       return this.resourceLocations[i];
/*     */     } 
/* 368 */     return loc;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\RandomEntityRule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */