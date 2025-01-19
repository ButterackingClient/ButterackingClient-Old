/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.EntityClassLocator;
/*     */ import net.optifine.config.IObjectLocator;
/*     */ import net.optifine.config.ItemLocator;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DynamicLights
/*     */ {
/*  38 */   private static DynamicLightsMap mapDynamicLights = new DynamicLightsMap();
/*  39 */   private static Map<Class, Integer> mapEntityLightLevels = (Map)new HashMap<>();
/*  40 */   private static Map<Item, Integer> mapItemLightLevels = new HashMap<>();
/*  41 */   private static long timeUpdateMs = 0L;
/*     */   
/*     */   private static final double MAX_DIST = 7.5D;
/*     */   private static final double MAX_DIST_SQ = 56.25D;
/*     */   private static final int LIGHT_LEVEL_MAX = 15;
/*     */   private static final int LIGHT_LEVEL_FIRE = 15;
/*     */   private static final int LIGHT_LEVEL_BLAZE = 10;
/*     */   private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
/*     */   private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
/*     */   private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
/*     */   private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
/*     */   private static boolean initialized;
/*     */   
/*     */   public static void entityAdded(Entity entityIn, RenderGlobal renderGlobal) {}
/*     */   
/*     */   public static void entityRemoved(Entity entityIn, RenderGlobal renderGlobal) {
/*  57 */     synchronized (mapDynamicLights) {
/*  58 */       DynamicLight dynamiclight = mapDynamicLights.remove(entityIn.getEntityId());
/*     */       
/*  60 */       if (dynamiclight != null) {
/*  61 */         dynamiclight.updateLitChunks(renderGlobal);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void update(RenderGlobal renderGlobal) {
/*  67 */     long i = System.currentTimeMillis();
/*     */     
/*  69 */     if (i >= timeUpdateMs + 50L) {
/*  70 */       timeUpdateMs = i;
/*     */       
/*  72 */       if (!initialized) {
/*  73 */         initialize();
/*     */       }
/*     */       
/*  76 */       synchronized (mapDynamicLights) {
/*  77 */         updateMapDynamicLights(renderGlobal);
/*     */         
/*  79 */         if (mapDynamicLights.size() > 0) {
/*  80 */           List<DynamicLight> list = mapDynamicLights.valueList();
/*     */           
/*  82 */           for (int j = 0; j < list.size(); j++) {
/*  83 */             DynamicLight dynamiclight = list.get(j);
/*  84 */             dynamiclight.update(renderGlobal);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void initialize() {
/*  92 */     initialized = true;
/*  93 */     mapEntityLightLevels.clear();
/*  94 */     mapItemLightLevels.clear();
/*  95 */     String[] astring = ReflectorForge.getForgeModIds();
/*     */     
/*  97 */     for (int i = 0; i < astring.length; i++) {
/*  98 */       String s = astring[i];
/*     */       
/*     */       try {
/* 101 */         ResourceLocation resourcelocation = new ResourceLocation(s, "optifine/dynamic_lights.properties");
/* 102 */         InputStream inputstream = Config.getResourceStream(resourcelocation);
/* 103 */         loadModConfiguration(inputstream, resourcelocation.toString(), s);
/* 104 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 109 */     if (mapEntityLightLevels.size() > 0) {
/* 110 */       Config.dbg("DynamicLights entities: " + mapEntityLightLevels.size());
/*     */     }
/*     */     
/* 113 */     if (mapItemLightLevels.size() > 0) {
/* 114 */       Config.dbg("DynamicLights items: " + mapItemLightLevels.size());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void loadModConfiguration(InputStream in, String path, String modId) {
/* 119 */     if (in != null) {
/*     */       try {
/* 121 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 122 */         propertiesOrdered.load(in);
/* 123 */         in.close();
/* 124 */         Config.dbg("DynamicLights: Parsing " + path);
/* 125 */         ConnectedParser connectedparser = new ConnectedParser("DynamicLights");
/* 126 */         loadModLightLevels(propertiesOrdered.getProperty("entities"), mapEntityLightLevels, (IObjectLocator)new EntityClassLocator(), connectedparser, path, modId);
/* 127 */         loadModLightLevels(propertiesOrdered.getProperty("items"), mapItemLightLevels, (IObjectLocator)new ItemLocator(), connectedparser, path, modId);
/* 128 */       } catch (IOException var5) {
/* 129 */         Config.warn("DynamicLights: Error reading " + path);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void loadModLightLevels(String prop, Map<Object, Integer> mapLightLevels, IObjectLocator ol, ConnectedParser cp, String path, String modId) {
/* 135 */     if (prop != null) {
/* 136 */       String[] astring = Config.tokenize(prop, " ");
/*     */       
/* 138 */       for (int i = 0; i < astring.length; i++) {
/* 139 */         String s = astring[i];
/* 140 */         String[] astring1 = Config.tokenize(s, ":");
/*     */         
/* 142 */         if (astring1.length != 2) {
/* 143 */           cp.warn("Invalid entry: " + s + ", in:" + path);
/*     */         } else {
/* 145 */           String s1 = astring1[0];
/* 146 */           String s2 = astring1[1];
/* 147 */           String s3 = String.valueOf(modId) + ":" + s1;
/* 148 */           ResourceLocation resourcelocation = new ResourceLocation(s3);
/* 149 */           Object object = ol.getObject(resourcelocation);
/*     */           
/* 151 */           if (object == null) {
/* 152 */             cp.warn("Object not found: " + s3);
/*     */           } else {
/* 154 */             int j = cp.parseInt(s2, -1);
/*     */             
/* 156 */             if (j >= 0 && j <= 15) {
/* 157 */               mapLightLevels.put(object, new Integer(j));
/*     */             } else {
/* 159 */               cp.warn("Invalid light level: " + s);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void updateMapDynamicLights(RenderGlobal renderGlobal) {
/* 168 */     WorldClient worldClient = renderGlobal.getWorld();
/*     */     
/* 170 */     if (worldClient != null) {
/* 171 */       for (Entity entity : worldClient.getLoadedEntityList()) {
/* 172 */         int i = getLightLevel(entity);
/*     */         
/* 174 */         if (i > 0) {
/* 175 */           int j = entity.getEntityId();
/* 176 */           DynamicLight dynamiclight = mapDynamicLights.get(j);
/*     */           
/* 178 */           if (dynamiclight == null) {
/* 179 */             dynamiclight = new DynamicLight(entity);
/* 180 */             mapDynamicLights.put(j, dynamiclight);
/*     */           }  continue;
/*     */         } 
/* 183 */         int k = entity.getEntityId();
/* 184 */         DynamicLight dynamiclight1 = mapDynamicLights.remove(k);
/*     */         
/* 186 */         if (dynamiclight1 != null) {
/* 187 */           dynamiclight1.updateLitChunks(renderGlobal);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getCombinedLight(BlockPos pos, int combinedLight) {
/* 195 */     double d0 = getLightLevel(pos);
/* 196 */     combinedLight = getCombinedLight(d0, combinedLight);
/* 197 */     return combinedLight;
/*     */   }
/*     */   
/*     */   public static int getCombinedLight(Entity entity, int combinedLight) {
/* 201 */     double d0 = getLightLevel(entity);
/* 202 */     combinedLight = getCombinedLight(d0, combinedLight);
/* 203 */     return combinedLight;
/*     */   }
/*     */   
/*     */   public static int getCombinedLight(double lightPlayer, int combinedLight) {
/* 207 */     if (lightPlayer > 0.0D) {
/* 208 */       int i = (int)(lightPlayer * 16.0D);
/* 209 */       int j = combinedLight & 0xFF;
/*     */       
/* 211 */       if (i > j) {
/* 212 */         combinedLight &= 0xFFFFFF00;
/* 213 */         combinedLight |= i;
/*     */       } 
/*     */     } 
/*     */     
/* 217 */     return combinedLight;
/*     */   }
/*     */   
/*     */   public static double getLightLevel(BlockPos pos) {
/* 221 */     double d0 = 0.0D;
/*     */     
/* 223 */     synchronized (mapDynamicLights) {
/* 224 */       List<DynamicLight> list = mapDynamicLights.valueList();
/* 225 */       int i = list.size();
/*     */       
/* 227 */       for (int j = 0; j < i; j++) {
/* 228 */         DynamicLight dynamiclight = list.get(j);
/* 229 */         int k = dynamiclight.getLastLightLevel();
/*     */         
/* 231 */         if (k > 0) {
/* 232 */           double d1 = dynamiclight.getLastPosX();
/* 233 */           double d2 = dynamiclight.getLastPosY();
/* 234 */           double d3 = dynamiclight.getLastPosZ();
/* 235 */           double d4 = pos.getX() - d1;
/* 236 */           double d5 = pos.getY() - d2;
/* 237 */           double d6 = pos.getZ() - d3;
/* 238 */           double d7 = d4 * d4 + d5 * d5 + d6 * d6;
/*     */           
/* 240 */           if (dynamiclight.isUnderwater() && !Config.isClearWater()) {
/* 241 */             k = Config.limit(k - 2, 0, 15);
/* 242 */             d7 *= 2.0D;
/*     */           } 
/*     */           
/* 245 */           if (d7 <= 56.25D) {
/* 246 */             double d8 = Math.sqrt(d7);
/* 247 */             double d9 = 1.0D - d8 / 7.5D;
/* 248 */             double d10 = d9 * k;
/*     */             
/* 250 */             if (d10 > d0) {
/* 251 */               d0 = d10;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 258 */     double d11 = Config.limit(d0, 0.0D, 15.0D);
/* 259 */     return d11;
/*     */   }
/*     */   
/*     */   public static int getLightLevel(ItemStack itemStack) {
/* 263 */     if (itemStack == null) {
/* 264 */       return 0;
/*     */     }
/* 266 */     Item item = itemStack.getItem();
/*     */     
/* 268 */     if (item instanceof ItemBlock) {
/* 269 */       ItemBlock itemblock = (ItemBlock)item;
/* 270 */       Block block = itemblock.getBlock();
/*     */       
/* 272 */       if (block != null) {
/* 273 */         return block.getLightValue();
/*     */       }
/*     */     } 
/*     */     
/* 277 */     if (item == Items.lava_bucket)
/* 278 */       return Blocks.lava.getLightValue(); 
/* 279 */     if (item != Items.blaze_rod && item != Items.blaze_powder) {
/* 280 */       if (item == Items.glowstone_dust)
/* 281 */         return 8; 
/* 282 */       if (item == Items.prismarine_crystals)
/* 283 */         return 8; 
/* 284 */       if (item == Items.magma_cream)
/* 285 */         return 8; 
/* 286 */       if (item == Items.nether_star) {
/* 287 */         return Blocks.beacon.getLightValue() / 2;
/*     */       }
/* 289 */       if (!mapItemLightLevels.isEmpty()) {
/* 290 */         Integer integer = mapItemLightLevels.get(item);
/*     */         
/* 292 */         if (integer != null) {
/* 293 */           return integer.intValue();
/*     */         }
/*     */       } 
/*     */       
/* 297 */       return 0;
/*     */     } 
/*     */     
/* 300 */     return 10;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLightLevel(Entity entity) {
/* 306 */     if (entity == Config.getMinecraft().getRenderViewEntity() && !Config.isDynamicHandLight()) {
/* 307 */       return 0;
/*     */     }
/* 309 */     if (entity instanceof EntityPlayer) {
/* 310 */       EntityPlayer entityplayer = (EntityPlayer)entity;
/*     */       
/* 312 */       if (entityplayer.isSpectator()) {
/* 313 */         return 0;
/*     */       }
/*     */     } 
/*     */     
/* 317 */     if (entity.isBurning()) {
/* 318 */       return 15;
/*     */     }
/* 320 */     if (!mapEntityLightLevels.isEmpty()) {
/* 321 */       Integer integer = mapEntityLightLevels.get(entity.getClass());
/*     */       
/* 323 */       if (integer != null) {
/* 324 */         return integer.intValue();
/*     */       }
/*     */     } 
/*     */     
/* 328 */     if (entity instanceof net.minecraft.entity.projectile.EntityFireball)
/* 329 */       return 15; 
/* 330 */     if (entity instanceof net.minecraft.entity.item.EntityTNTPrimed)
/* 331 */       return 15; 
/* 332 */     if (entity instanceof EntityBlaze) {
/* 333 */       EntityBlaze entityblaze = (EntityBlaze)entity;
/* 334 */       return entityblaze.func_70845_n() ? 15 : 10;
/* 335 */     }  if (entity instanceof EntityMagmaCube) {
/* 336 */       EntityMagmaCube entitymagmacube = (EntityMagmaCube)entity;
/* 337 */       return (entitymagmacube.squishFactor > 0.6D) ? 13 : 8;
/*     */     } 
/* 339 */     if (entity instanceof EntityCreeper) {
/* 340 */       EntityCreeper entitycreeper = (EntityCreeper)entity;
/*     */       
/* 342 */       if (entitycreeper.getCreeperFlashIntensity(0.0F) > 0.001D) {
/* 343 */         return 15;
/*     */       }
/*     */     } 
/*     */     
/* 347 */     if (entity instanceof EntityLivingBase) {
/* 348 */       EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/* 349 */       ItemStack itemstack2 = entitylivingbase.getHeldItem();
/* 350 */       int i = getLightLevel(itemstack2);
/* 351 */       ItemStack itemstack1 = entitylivingbase.getEquipmentInSlot(4);
/* 352 */       int j = getLightLevel(itemstack1);
/* 353 */       return Math.max(i, j);
/* 354 */     }  if (entity instanceof EntityItem) {
/* 355 */       EntityItem entityitem = (EntityItem)entity;
/* 356 */       ItemStack itemstack = getItemStack(entityitem);
/* 357 */       return getLightLevel(itemstack);
/*     */     } 
/* 359 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeLights(RenderGlobal renderGlobal) {
/* 367 */     synchronized (mapDynamicLights) {
/* 368 */       List<DynamicLight> list = mapDynamicLights.valueList();
/*     */       
/* 370 */       for (int i = 0; i < list.size(); i++) {
/* 371 */         DynamicLight dynamiclight = list.get(i);
/* 372 */         dynamiclight.updateLitChunks(renderGlobal);
/*     */       } 
/*     */       
/* 375 */       mapDynamicLights.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void clear() {
/* 380 */     synchronized (mapDynamicLights) {
/* 381 */       mapDynamicLights.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getCount() {
/* 386 */     synchronized (mapDynamicLights) {
/* 387 */       return mapDynamicLights.size();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ItemStack getItemStack(EntityItem entityItem) {
/* 392 */     ItemStack itemstack = entityItem.getDataWatcher().getWatchableObjectItemStack(10);
/* 393 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\DynamicLights.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */