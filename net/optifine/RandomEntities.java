/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.entity.DataWatcher;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorRaw;
/*     */ import net.optifine.util.IntegratedServerUtils;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.ResUtils;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public class RandomEntities
/*     */ {
/*  33 */   private static Map<String, RandomEntityProperties> mapProperties = new HashMap<>();
/*     */   private static boolean active = false;
/*     */   private static RenderGlobal renderGlobal;
/*  36 */   private static RandomEntity randomEntity = new RandomEntity();
/*     */   private static TileEntityRendererDispatcher tileEntityRendererDispatcher;
/*  38 */   private static RandomTileEntity randomTileEntity = new RandomTileEntity();
/*     */   private static boolean working = false;
/*     */   public static final String SUFFIX_PNG = ".png";
/*     */   public static final String SUFFIX_PROPERTIES = ".properties";
/*     */   public static final String PREFIX_TEXTURES_ENTITY = "textures/entity/";
/*     */   public static final String PREFIX_TEXTURES_PAINTING = "textures/painting/";
/*     */   public static final String PREFIX_TEXTURES = "textures/";
/*     */   public static final String PREFIX_OPTIFINE_RANDOM = "optifine/random/";
/*     */   public static final String PREFIX_MCPATCHER_MOB = "mcpatcher/mob/";
/*  47 */   private static final String[] DEPENDANT_SUFFIXES = new String[] { "_armor", "_eyes", "_exploding", "_shooting", "_fur", "_eyes", "_invulnerable", "_angry", "_tame", "_collar" };
/*     */   private static final String PREFIX_DYNAMIC_TEXTURE_HORSE = "horse/";
/*  49 */   private static final String[] HORSE_TEXTURES = (String[])ReflectorRaw.getFieldValue(null, EntityHorse.class, String[].class, 2);
/*  50 */   private static final String[] HORSE_TEXTURES_ABBR = (String[])ReflectorRaw.getFieldValue(null, EntityHorse.class, String[].class, 3);
/*     */   
/*     */   public static void entityLoaded(Entity entity, World world) {
/*  53 */     if (world != null) {
/*  54 */       DataWatcher datawatcher = entity.getDataWatcher();
/*  55 */       datawatcher.spawnPosition = entity.getPosition();
/*  56 */       datawatcher.spawnBiome = world.getBiomeGenForCoords(datawatcher.spawnPosition);
/*  57 */       UUID uuid = entity.getUniqueID();
/*     */       
/*  59 */       if (entity instanceof EntityVillager) {
/*  60 */         updateEntityVillager(uuid, (EntityVillager)entity);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void entityUnloaded(Entity entity, World world) {}
/*     */   
/*     */   private static void updateEntityVillager(UUID uuid, EntityVillager ev) {
/*  69 */     Entity entity = IntegratedServerUtils.getEntity(uuid);
/*     */     
/*  71 */     if (entity instanceof EntityVillager) {
/*  72 */       EntityVillager entityvillager = (EntityVillager)entity;
/*  73 */       int i = entityvillager.getProfession();
/*  74 */       ev.setProfession(i);
/*  75 */       int j = Reflector.getFieldValueInt(entityvillager, Reflector.EntityVillager_careerId, 0);
/*  76 */       Reflector.setFieldValueInt(ev, Reflector.EntityVillager_careerId, j);
/*  77 */       int k = Reflector.getFieldValueInt(entityvillager, Reflector.EntityVillager_careerLevel, 0);
/*  78 */       Reflector.setFieldValueInt(ev, Reflector.EntityVillager_careerLevel, k);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void worldChanged(World oldWorld, World newWorld) {
/*  83 */     if (newWorld != null) {
/*  84 */       List<Entity> list = newWorld.getLoadedEntityList();
/*     */       
/*  86 */       for (int i = 0; i < list.size(); i++) {
/*  87 */         Entity entity = list.get(i);
/*  88 */         entityLoaded(entity, newWorld);
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     randomEntity.setEntity(null);
/*  93 */     randomTileEntity.setTileEntity(null);
/*     */   }
/*     */   public static ResourceLocation getTextureLocation(ResourceLocation loc) {
/*     */     ResourceLocation name;
/*  97 */     if (!active)
/*  98 */       return loc; 
/*  99 */     if (working) {
/* 100 */       return loc;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 105 */     try { working = true;
/* 106 */       IRandomEntity irandomentity = getRandomEntityRendered();
/*     */       
/* 108 */       if (irandomentity != null) {
/* 109 */         String s = loc.getResourcePath();
/*     */         
/* 111 */         if (s.startsWith("horse/")) {
/* 112 */           s = getHorseTexturePath(s, "horse/".length());
/*     */         }
/*     */         
/* 115 */         if (!s.startsWith("textures/entity/") && !s.startsWith("textures/painting/")) {
/* 116 */           ResourceLocation resourcelocation2 = loc;
/* 117 */           return resourcelocation2;
/*     */         } 
/*     */         
/* 120 */         RandomEntityProperties randomentityproperties = mapProperties.get(s);
/*     */         
/* 122 */         if (randomentityproperties == null) {
/* 123 */           ResourceLocation resourcelocation3 = loc;
/* 124 */           return resourcelocation3;
/*     */         } 
/*     */         
/* 127 */         ResourceLocation resourcelocation1 = randomentityproperties.getTextureLocation(loc, irandomentity);
/* 128 */         return resourcelocation1;
/*     */       }
/*     */        }
/*     */     finally
/*     */     
/* 133 */     { working = false; }  working = false;
/*     */ 
/*     */     
/* 136 */     return name;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getHorseTexturePath(String path, int pos) {
/* 141 */     if (HORSE_TEXTURES != null && HORSE_TEXTURES_ABBR != null) {
/* 142 */       for (int i = 0; i < HORSE_TEXTURES_ABBR.length; i++) {
/* 143 */         String s = HORSE_TEXTURES_ABBR[i];
/*     */         
/* 145 */         if (path.startsWith(s, pos)) {
/* 146 */           return HORSE_TEXTURES[i];
/*     */         }
/*     */       } 
/*     */       
/* 150 */       return path;
/*     */     } 
/* 152 */     return path;
/*     */   }
/*     */ 
/*     */   
/*     */   private static IRandomEntity getRandomEntityRendered() {
/* 157 */     if (renderGlobal.renderedEntity != null) {
/* 158 */       randomEntity.setEntity(renderGlobal.renderedEntity);
/* 159 */       return randomEntity;
/*     */     } 
/* 161 */     if (tileEntityRendererDispatcher.tileEntityRendered != null) {
/* 162 */       TileEntity tileentity = tileEntityRendererDispatcher.tileEntityRendered;
/*     */       
/* 164 */       if (tileentity.getWorld() != null) {
/* 165 */         randomTileEntity.setTileEntity(tileentity);
/* 166 */         return randomTileEntity;
/*     */       } 
/*     */     } 
/*     */     
/* 170 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static RandomEntityProperties makeProperties(ResourceLocation loc, boolean mcpatcher) {
/* 175 */     String s = loc.getResourcePath();
/* 176 */     ResourceLocation resourcelocation = getLocationProperties(loc, mcpatcher);
/*     */     
/* 178 */     if (resourcelocation != null) {
/* 179 */       RandomEntityProperties randomentityproperties = parseProperties(resourcelocation, loc);
/*     */       
/* 181 */       if (randomentityproperties != null) {
/* 182 */         return randomentityproperties;
/*     */       }
/*     */     } 
/*     */     
/* 186 */     ResourceLocation[] aresourcelocation = getLocationsVariants(loc, mcpatcher);
/* 187 */     return (aresourcelocation == null) ? null : new RandomEntityProperties(s, aresourcelocation);
/*     */   }
/*     */   
/*     */   private static RandomEntityProperties parseProperties(ResourceLocation propLoc, ResourceLocation resLoc) {
/*     */     try {
/* 192 */       String s = propLoc.getResourcePath();
/* 193 */       dbg(String.valueOf(resLoc.getResourcePath()) + ", properties: " + s);
/* 194 */       InputStream inputstream = Config.getResourceStream(propLoc);
/*     */       
/* 196 */       if (inputstream == null) {
/* 197 */         warn("Properties not found: " + s);
/* 198 */         return null;
/*     */       } 
/* 200 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 201 */       propertiesOrdered.load(inputstream);
/* 202 */       inputstream.close();
/* 203 */       RandomEntityProperties randomentityproperties = new RandomEntityProperties((Properties)propertiesOrdered, s, resLoc);
/* 204 */       return !randomentityproperties.isValid(s) ? null : randomentityproperties;
/*     */     }
/* 206 */     catch (FileNotFoundException var6) {
/* 207 */       warn("File not found: " + resLoc.getResourcePath());
/* 208 */       return null;
/* 209 */     } catch (IOException ioexception) {
/* 210 */       ioexception.printStackTrace();
/* 211 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ResourceLocation getLocationProperties(ResourceLocation loc, boolean mcpatcher) {
/* 216 */     ResourceLocation resourcelocation = getLocationRandom(loc, mcpatcher);
/*     */     
/* 218 */     if (resourcelocation == null) {
/* 219 */       return null;
/*     */     }
/* 221 */     String s = resourcelocation.getResourceDomain();
/* 222 */     String s1 = resourcelocation.getResourcePath();
/* 223 */     String s2 = StrUtils.removeSuffix(s1, ".png");
/* 224 */     String s3 = String.valueOf(s2) + ".properties";
/* 225 */     ResourceLocation resourcelocation1 = new ResourceLocation(s, s3);
/*     */     
/* 227 */     if (Config.hasResource(resourcelocation1)) {
/* 228 */       return resourcelocation1;
/*     */     }
/* 230 */     String s4 = getParentTexturePath(s2);
/*     */     
/* 232 */     if (s4 == null) {
/* 233 */       return null;
/*     */     }
/* 235 */     ResourceLocation resourcelocation2 = new ResourceLocation(s, String.valueOf(s4) + ".properties");
/* 236 */     return Config.hasResource(resourcelocation2) ? resourcelocation2 : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static ResourceLocation getLocationRandom(ResourceLocation loc, boolean mcpatcher) {
/* 243 */     String s = loc.getResourceDomain();
/* 244 */     String s1 = loc.getResourcePath();
/* 245 */     String s2 = "textures/";
/* 246 */     String s3 = "optifine/random/";
/*     */     
/* 248 */     if (mcpatcher) {
/* 249 */       s2 = "textures/entity/";
/* 250 */       s3 = "mcpatcher/mob/";
/*     */     } 
/*     */     
/* 253 */     if (!s1.startsWith(s2)) {
/* 254 */       return null;
/*     */     }
/* 256 */     String s4 = StrUtils.replacePrefix(s1, s2, s3);
/* 257 */     return new ResourceLocation(s, s4);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getPathBase(String pathRandom) {
/* 262 */     return pathRandom.startsWith("optifine/random/") ? StrUtils.replacePrefix(pathRandom, "optifine/random/", "textures/") : (pathRandom.startsWith("mcpatcher/mob/") ? StrUtils.replacePrefix(pathRandom, "mcpatcher/mob/", "textures/entity/") : null);
/*     */   }
/*     */   
/*     */   protected static ResourceLocation getLocationIndexed(ResourceLocation loc, int index) {
/* 266 */     if (loc == null) {
/* 267 */       return null;
/*     */     }
/* 269 */     String s = loc.getResourcePath();
/* 270 */     int i = s.lastIndexOf('.');
/*     */     
/* 272 */     if (i < 0) {
/* 273 */       return null;
/*     */     }
/* 275 */     String s1 = s.substring(0, i);
/* 276 */     String s2 = s.substring(i);
/* 277 */     String s3 = String.valueOf(s1) + index + s2;
/* 278 */     ResourceLocation resourcelocation = new ResourceLocation(loc.getResourceDomain(), s3);
/* 279 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getParentTexturePath(String path) {
/* 285 */     for (int i = 0; i < DEPENDANT_SUFFIXES.length; i++) {
/* 286 */       String s = DEPENDANT_SUFFIXES[i];
/*     */       
/* 288 */       if (path.endsWith(s)) {
/* 289 */         String s1 = StrUtils.removeSuffix(path, s);
/* 290 */         return s1;
/*     */       } 
/*     */     } 
/*     */     
/* 294 */     return null;
/*     */   }
/*     */   
/*     */   private static ResourceLocation[] getLocationsVariants(ResourceLocation loc, boolean mcpatcher) {
/* 298 */     List<ResourceLocation> list = new ArrayList();
/* 299 */     list.add(loc);
/* 300 */     ResourceLocation resourcelocation = getLocationRandom(loc, mcpatcher);
/*     */     
/* 302 */     if (resourcelocation == null) {
/* 303 */       return null;
/*     */     }
/* 305 */     for (int i = 1; i < list.size() + 10; i++) {
/* 306 */       int j = i + 1;
/* 307 */       ResourceLocation resourcelocation1 = getLocationIndexed(resourcelocation, j);
/*     */       
/* 309 */       if (Config.hasResource(resourcelocation1)) {
/* 310 */         list.add(resourcelocation1);
/*     */       }
/*     */     } 
/*     */     
/* 314 */     if (list.size() <= 1) {
/* 315 */       return null;
/*     */     }
/* 317 */     ResourceLocation[] aresourcelocation = list.<ResourceLocation>toArray(new ResourceLocation[list.size()]);
/* 318 */     dbg(String.valueOf(loc.getResourcePath()) + ", variants: " + aresourcelocation.length);
/* 319 */     return aresourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/* 325 */     mapProperties.clear();
/* 326 */     active = false;
/*     */     
/* 328 */     if (Config.isRandomEntities()) {
/* 329 */       initialize();
/*     */     }
/*     */   }
/*     */   
/*     */   private static void initialize() {
/* 334 */     renderGlobal = Config.getRenderGlobal();
/* 335 */     tileEntityRendererDispatcher = TileEntityRendererDispatcher.instance;
/* 336 */     String[] astring = { "optifine/random/", "mcpatcher/mob/" };
/* 337 */     String[] astring1 = { ".png", ".properties" };
/* 338 */     String[] astring2 = ResUtils.collectFiles(astring, astring1);
/* 339 */     Set<String> set = new HashSet();
/*     */     
/* 341 */     for (int i = 0; i < astring2.length; i++) {
/* 342 */       String s = astring2[i];
/* 343 */       s = StrUtils.removeSuffix(s, astring1);
/* 344 */       s = StrUtils.trimTrailing(s, "0123456789");
/* 345 */       s = String.valueOf(s) + ".png";
/* 346 */       String s1 = getPathBase(s);
/*     */       
/* 348 */       if (!set.contains(s1)) {
/* 349 */         set.add(s1);
/* 350 */         ResourceLocation resourcelocation = new ResourceLocation(s1);
/*     */         
/* 352 */         if (Config.hasResource(resourcelocation)) {
/* 353 */           RandomEntityProperties randomentityproperties = mapProperties.get(s1);
/*     */           
/* 355 */           if (randomentityproperties == null) {
/* 356 */             randomentityproperties = makeProperties(resourcelocation, false);
/*     */             
/* 358 */             if (randomentityproperties == null) {
/* 359 */               randomentityproperties = makeProperties(resourcelocation, true);
/*     */             }
/*     */             
/* 362 */             if (randomentityproperties != null) {
/* 363 */               mapProperties.put(s1, randomentityproperties);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 370 */     active = !mapProperties.isEmpty();
/*     */   }
/*     */   
/*     */   public static void dbg(String str) {
/* 374 */     Config.dbg("RandomEntities: " + str);
/*     */   }
/*     */   
/*     */   public static void warn(String str) {
/* 378 */     Config.warn("RandomEntities: " + str);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\RandomEntities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */