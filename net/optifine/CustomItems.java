/*     */ package net.optifine;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.block.model.ItemModelGenerator;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelBakery;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.config.NbtTagValue;
/*     */ import net.optifine.render.Blender;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.shaders.ShadersRender;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.ResUtils;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public class CustomItems {
/*  46 */   private static CustomItemProperties[][] itemProperties = null;
/*  47 */   private static CustomItemProperties[][] enchantmentProperties = null;
/*  48 */   private static Map mapPotionIds = null;
/*  49 */   private static ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
/*     */   private static boolean useGlint = true;
/*     */   private static boolean renderOffHand = false;
/*     */   public static final int MASK_POTION_SPLASH = 16384;
/*     */   public static final int MASK_POTION_NAME = 63;
/*     */   public static final int MASK_POTION_EXTENDED = 64;
/*     */   public static final String KEY_TEXTURE_OVERLAY = "texture.potion_overlay";
/*     */   public static final String KEY_TEXTURE_SPLASH = "texture.potion_bottle_splash";
/*     */   public static final String KEY_TEXTURE_DRINKABLE = "texture.potion_bottle_drinkable";
/*     */   public static final String DEFAULT_TEXTURE_OVERLAY = "items/potion_overlay";
/*     */   public static final String DEFAULT_TEXTURE_SPLASH = "items/potion_bottle_splash";
/*     */   public static final String DEFAULT_TEXTURE_DRINKABLE = "items/potion_bottle_drinkable";
/*  61 */   private static final int[][] EMPTY_INT2_ARRAY = new int[0][];
/*     */   private static final String TYPE_POTION_NORMAL = "normal";
/*     */   private static final String TYPE_POTION_SPLASH = "splash";
/*     */   private static final String TYPE_POTION_LINGER = "linger";
/*     */   
/*     */   public static void update() {
/*  67 */     itemProperties = null;
/*  68 */     enchantmentProperties = null;
/*  69 */     useGlint = true;
/*     */     
/*  71 */     if (Config.isCustomItems()) {
/*  72 */       readCitProperties("mcpatcher/cit.properties");
/*  73 */       IResourcePack[] airesourcepack = Config.getResourcePacks();
/*     */       
/*  75 */       for (int i = airesourcepack.length - 1; i >= 0; i--) {
/*  76 */         IResourcePack iresourcepack = airesourcepack[i];
/*  77 */         update(iresourcepack);
/*     */       } 
/*     */       
/*  80 */       update((IResourcePack)Config.getDefaultResourcePack());
/*     */       
/*  82 */       if (itemProperties.length <= 0) {
/*  83 */         itemProperties = null;
/*     */       }
/*     */       
/*  86 */       if (enchantmentProperties.length <= 0) {
/*  87 */         enchantmentProperties = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void readCitProperties(String fileName) {
/*     */     try {
/*  94 */       ResourceLocation resourcelocation = new ResourceLocation(fileName);
/*  95 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/*  97 */       if (inputstream == null) {
/*     */         return;
/*     */       }
/*     */       
/* 101 */       Config.dbg("CustomItems: Loading " + fileName);
/* 102 */       PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 103 */       propertiesOrdered.load(inputstream);
/* 104 */       inputstream.close();
/* 105 */       useGlint = Config.parseBoolean(propertiesOrdered.getProperty("useGlint"), true);
/* 106 */     } catch (FileNotFoundException var4) {
/*     */       return;
/* 108 */     } catch (IOException ioexception) {
/* 109 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void update(IResourcePack rp) {
/* 114 */     String[] astring = ResUtils.collectFiles(rp, "mcpatcher/cit/", ".properties", null);
/* 115 */     Map map = makeAutoImageProperties(rp);
/*     */     
/* 117 */     if (map.size() > 0) {
/* 118 */       Set set = map.keySet();
/* 119 */       String[] astring1 = (String[])set.toArray((Object[])new String[set.size()]);
/* 120 */       astring = (String[])Config.addObjectsToArray((Object[])astring, (Object[])astring1);
/*     */     } 
/*     */     
/* 123 */     Arrays.sort((Object[])astring);
/* 124 */     List list = makePropertyList(itemProperties);
/* 125 */     List list1 = makePropertyList(enchantmentProperties);
/*     */     
/* 127 */     for (int i = 0; i < astring.length; i++) {
/* 128 */       String s = astring[i];
/* 129 */       Config.dbg("CustomItems: " + s);
/*     */       
/*     */       try {
/* 132 */         CustomItemProperties customitemproperties = null;
/*     */         
/* 134 */         if (map.containsKey(s)) {
/* 135 */           customitemproperties = (CustomItemProperties)map.get(s);
/*     */         }
/*     */         
/* 138 */         if (customitemproperties == null)
/* 139 */         { ResourceLocation resourcelocation = new ResourceLocation(s);
/* 140 */           InputStream inputstream = rp.getInputStream(resourcelocation);
/*     */           
/* 142 */           if (inputstream == null)
/* 143 */           { Config.warn("CustomItems file not found: " + s); }
/*     */           
/*     */           else
/*     */           
/* 147 */           { PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 148 */             propertiesOrdered.load(inputstream);
/* 149 */             inputstream.close();
/* 150 */             customitemproperties = new CustomItemProperties((Properties)propertiesOrdered, s);
/*     */ 
/*     */             
/* 153 */             if (customitemproperties.isValid(s))
/* 154 */             { addToItemList(customitemproperties, list);
/* 155 */               addToEnchantmentList(customitemproperties, list1); }  }  continue; }  if (customitemproperties.isValid(s)) { addToItemList(customitemproperties, list); addToEnchantmentList(customitemproperties, list1); }
/*     */       
/* 157 */       } catch (FileNotFoundException var11) {
/* 158 */         Config.warn("CustomItems file not found: " + s); continue;
/* 159 */       } catch (Exception exception) {
/* 160 */         exception.printStackTrace();
/*     */         continue;
/*     */       } 
/*     */     } 
/* 164 */     itemProperties = propertyListToArray(list);
/* 165 */     enchantmentProperties = propertyListToArray(list1);
/* 166 */     Comparator<? super CustomItemProperties> comparator = getPropertiesComparator();
/*     */     
/* 168 */     for (int j = 0; j < itemProperties.length; j++) {
/* 169 */       CustomItemProperties[] acustomitemproperties = itemProperties[j];
/*     */       
/* 171 */       if (acustomitemproperties != null) {
/* 172 */         Arrays.sort(acustomitemproperties, comparator);
/*     */       }
/*     */     } 
/*     */     
/* 176 */     for (int k = 0; k < enchantmentProperties.length; k++) {
/* 177 */       CustomItemProperties[] acustomitemproperties1 = enchantmentProperties[k];
/*     */       
/* 179 */       if (acustomitemproperties1 != null) {
/* 180 */         Arrays.sort(acustomitemproperties1, comparator);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Comparator getPropertiesComparator() {
/* 186 */     Comparator comparator = new Comparator() {
/*     */         public int compare(Object o1, Object o2) {
/* 188 */           CustomItemProperties customitemproperties = (CustomItemProperties)o1;
/* 189 */           CustomItemProperties customitemproperties1 = (CustomItemProperties)o2;
/* 190 */           return (customitemproperties.layer != customitemproperties1.layer) ? (customitemproperties.layer - customitemproperties1.layer) : ((customitemproperties.weight != customitemproperties1.weight) ? (customitemproperties1.weight - customitemproperties.weight) : (!customitemproperties.basePath.equals(customitemproperties1.basePath) ? customitemproperties.basePath.compareTo(customitemproperties1.basePath) : customitemproperties.name.compareTo(customitemproperties1.name)));
/*     */         }
/*     */       };
/* 193 */     return comparator;
/*     */   }
/*     */   
/*     */   public static void updateIcons(TextureMap textureMap) {
/* 197 */     for (CustomItemProperties customitemproperties : getAllProperties()) {
/* 198 */       customitemproperties.updateIcons(textureMap);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void loadModels(ModelBakery modelBakery) {
/* 203 */     for (CustomItemProperties customitemproperties : getAllProperties()) {
/* 204 */       customitemproperties.loadModels(modelBakery);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void updateModels() {
/* 209 */     for (CustomItemProperties customitemproperties : getAllProperties()) {
/* 210 */       if (customitemproperties.type == 1) {
/* 211 */         TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
/* 212 */         customitemproperties.updateModelTexture(texturemap, itemModelGenerator);
/* 213 */         customitemproperties.updateModelsFull();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static List<CustomItemProperties> getAllProperties() {
/* 219 */     List<CustomItemProperties> list = new ArrayList<>();
/* 220 */     addAll(itemProperties, list);
/* 221 */     addAll(enchantmentProperties, list);
/* 222 */     return list;
/*     */   }
/*     */   
/*     */   private static void addAll(CustomItemProperties[][] cipsArr, List<CustomItemProperties> list) {
/* 226 */     if (cipsArr != null) {
/* 227 */       for (int i = 0; i < cipsArr.length; i++) {
/* 228 */         CustomItemProperties[] acustomitemproperties = cipsArr[i];
/*     */         
/* 230 */         if (acustomitemproperties != null) {
/* 231 */           for (int j = 0; j < acustomitemproperties.length; j++) {
/* 232 */             CustomItemProperties customitemproperties = acustomitemproperties[j];
/*     */             
/* 234 */             if (customitemproperties != null) {
/* 235 */               list.add(customitemproperties);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static Map makeAutoImageProperties(IResourcePack rp) {
/* 244 */     Map<Object, Object> map = new HashMap<>();
/* 245 */     map.putAll(makePotionImageProperties(rp, "normal", Item.getIdFromItem((Item)Items.potionitem)));
/* 246 */     map.putAll(makePotionImageProperties(rp, "splash", Item.getIdFromItem((Item)Items.potionitem)));
/* 247 */     map.putAll(makePotionImageProperties(rp, "linger", Item.getIdFromItem((Item)Items.potionitem)));
/* 248 */     return map;
/*     */   }
/*     */   
/*     */   private static Map makePotionImageProperties(IResourcePack rp, String type, int itemId) {
/* 252 */     Map<Object, Object> map = new HashMap<>();
/* 253 */     String s = String.valueOf(type) + "/";
/* 254 */     String[] astring = { "mcpatcher/cit/potion/" + s, "mcpatcher/cit/Potion/" + s };
/* 255 */     String[] astring1 = { ".png" };
/* 256 */     String[] astring2 = ResUtils.collectFiles(rp, astring, astring1);
/*     */     
/* 258 */     for (int i = 0; i < astring2.length; i++) {
/* 259 */       String s1 = astring2[i];
/* 260 */       String name = StrUtils.removePrefixSuffix(s1, astring, astring1);
/* 261 */       Properties properties = makePotionProperties(name, type, itemId, s1);
/*     */       
/* 263 */       if (properties != null) {
/* 264 */         String s3 = String.valueOf(StrUtils.removeSuffix(s1, astring1)) + ".properties";
/* 265 */         CustomItemProperties customitemproperties = new CustomItemProperties(properties, s3);
/* 266 */         map.put(s3, customitemproperties);
/*     */       } 
/*     */     } 
/*     */     
/* 270 */     return map;
/*     */   }
/*     */   
/*     */   private static Properties makePotionProperties(String name, String type, int itemId, String path) {
/* 274 */     if (StrUtils.endsWith(name, new String[] { "_n", "_s" }))
/* 275 */       return null; 
/* 276 */     if (name.equals("empty") && type.equals("normal")) {
/* 277 */       itemId = Item.getIdFromItem(Items.glass_bottle);
/* 278 */       PropertiesOrdered propertiesOrdered1 = new PropertiesOrdered();
/* 279 */       propertiesOrdered1.put("type", "item");
/* 280 */       propertiesOrdered1.put("items", itemId);
/* 281 */       return (Properties)propertiesOrdered1;
/*     */     } 
/* 283 */     int[] aint = (int[])getMapPotionIds().get(name);
/*     */     
/* 285 */     if (aint == null) {
/* 286 */       Config.warn("Potion not found for image: " + path);
/* 287 */       return null;
/*     */     } 
/* 289 */     StringBuffer stringbuffer = new StringBuffer();
/*     */     
/* 291 */     for (int i = 0; i < aint.length; i++) {
/* 292 */       int j = aint[i];
/*     */       
/* 294 */       if (type.equals("splash")) {
/* 295 */         j |= 0x4000;
/*     */       }
/*     */       
/* 298 */       if (i > 0) {
/* 299 */         stringbuffer.append(" ");
/*     */       }
/*     */       
/* 302 */       stringbuffer.append(j);
/*     */     } 
/*     */     
/* 305 */     int k = 16447;
/*     */     
/* 307 */     if (name.equals("water") || name.equals("mundane")) {
/* 308 */       k |= 0x40;
/*     */     }
/*     */     
/* 311 */     PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 312 */     propertiesOrdered.put("type", "item");
/* 313 */     propertiesOrdered.put("items", itemId);
/* 314 */     propertiesOrdered.put("damage", stringbuffer.toString());
/* 315 */     propertiesOrdered.put("damageMask", k);
/*     */     
/* 317 */     if (type.equals("splash")) {
/* 318 */       propertiesOrdered.put("texture.potion_bottle_splash", name);
/*     */     } else {
/* 320 */       propertiesOrdered.put("texture.potion_bottle_drinkable", name);
/*     */     } 
/*     */     
/* 323 */     return (Properties)propertiesOrdered;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map getMapPotionIds() {
/* 329 */     if (mapPotionIds == null) {
/* 330 */       mapPotionIds = new LinkedHashMap<>();
/* 331 */       mapPotionIds.put("water", getPotionId(0, 0));
/* 332 */       mapPotionIds.put("awkward", getPotionId(0, 1));
/* 333 */       mapPotionIds.put("thick", getPotionId(0, 2));
/* 334 */       mapPotionIds.put("potent", getPotionId(0, 3));
/* 335 */       mapPotionIds.put("regeneration", getPotionIds(1));
/* 336 */       mapPotionIds.put("movespeed", getPotionIds(2));
/* 337 */       mapPotionIds.put("fireresistance", getPotionIds(3));
/* 338 */       mapPotionIds.put("poison", getPotionIds(4));
/* 339 */       mapPotionIds.put("heal", getPotionIds(5));
/* 340 */       mapPotionIds.put("nightvision", getPotionIds(6));
/* 341 */       mapPotionIds.put("clear", getPotionId(7, 0));
/* 342 */       mapPotionIds.put("bungling", getPotionId(7, 1));
/* 343 */       mapPotionIds.put("charming", getPotionId(7, 2));
/* 344 */       mapPotionIds.put("rank", getPotionId(7, 3));
/* 345 */       mapPotionIds.put("weakness", getPotionIds(8));
/* 346 */       mapPotionIds.put("damageboost", getPotionIds(9));
/* 347 */       mapPotionIds.put("moveslowdown", getPotionIds(10));
/* 348 */       mapPotionIds.put("leaping", getPotionIds(11));
/* 349 */       mapPotionIds.put("harm", getPotionIds(12));
/* 350 */       mapPotionIds.put("waterbreathing", getPotionIds(13));
/* 351 */       mapPotionIds.put("invisibility", getPotionIds(14));
/* 352 */       mapPotionIds.put("thin", getPotionId(15, 0));
/* 353 */       mapPotionIds.put("debonair", getPotionId(15, 1));
/* 354 */       mapPotionIds.put("sparkling", getPotionId(15, 2));
/* 355 */       mapPotionIds.put("stinky", getPotionId(15, 3));
/* 356 */       mapPotionIds.put("mundane", getPotionId(0, 4));
/* 357 */       mapPotionIds.put("speed", mapPotionIds.get("movespeed"));
/* 358 */       mapPotionIds.put("fire_resistance", mapPotionIds.get("fireresistance"));
/* 359 */       mapPotionIds.put("instant_health", mapPotionIds.get("heal"));
/* 360 */       mapPotionIds.put("night_vision", mapPotionIds.get("nightvision"));
/* 361 */       mapPotionIds.put("strength", mapPotionIds.get("damageboost"));
/* 362 */       mapPotionIds.put("slowness", mapPotionIds.get("moveslowdown"));
/* 363 */       mapPotionIds.put("instant_damage", mapPotionIds.get("harm"));
/* 364 */       mapPotionIds.put("water_breathing", mapPotionIds.get("waterbreathing"));
/*     */     } 
/*     */     
/* 367 */     return mapPotionIds;
/*     */   }
/*     */   
/*     */   private static int[] getPotionIds(int baseId) {
/* 371 */     return new int[] { baseId, baseId + 16, baseId + 32, baseId + 48 };
/*     */   }
/*     */   
/*     */   private static int[] getPotionId(int baseId, int subId) {
/* 375 */     return new int[] { baseId + subId * 16 };
/*     */   }
/*     */   
/*     */   private static int getPotionNameDamage(String name) {
/* 379 */     String s = "potion." + name;
/* 380 */     Potion[] apotion = Potion.potionTypes;
/*     */     
/* 382 */     for (int i = 0; i < apotion.length; i++) {
/* 383 */       Potion potion = apotion[i];
/*     */       
/* 385 */       if (potion != null) {
/* 386 */         String s1 = potion.getName();
/*     */         
/* 388 */         if (s.equals(s1)) {
/* 389 */           return potion.getId();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 394 */     return -1;
/*     */   }
/*     */   
/*     */   private static List makePropertyList(CustomItemProperties[][] propsArr) {
/* 398 */     List<List> list = new ArrayList();
/*     */     
/* 400 */     if (propsArr != null) {
/* 401 */       for (int i = 0; i < propsArr.length; i++) {
/* 402 */         CustomItemProperties[] acustomitemproperties = propsArr[i];
/* 403 */         List list1 = null;
/*     */         
/* 405 */         if (acustomitemproperties != null) {
/* 406 */           list1 = new ArrayList(Arrays.asList((Object[])acustomitemproperties));
/*     */         }
/*     */         
/* 409 */         list.add(list1);
/*     */       } 
/*     */     }
/*     */     
/* 413 */     return list;
/*     */   }
/*     */   
/*     */   private static CustomItemProperties[][] propertyListToArray(List<List> list) {
/* 417 */     CustomItemProperties[][] acustomitemproperties = new CustomItemProperties[list.size()][];
/*     */     
/* 419 */     for (int i = 0; i < list.size(); i++) {
/* 420 */       List list1 = list.get(i);
/*     */       
/* 422 */       if (list1 != null) {
/* 423 */         CustomItemProperties[] acustomitemproperties1 = (CustomItemProperties[])list1.toArray((Object[])new CustomItemProperties[list1.size()]);
/* 424 */         Arrays.sort(acustomitemproperties1, new CustomItemsComparator());
/* 425 */         acustomitemproperties[i] = acustomitemproperties1;
/*     */       } 
/*     */     } 
/*     */     
/* 429 */     return acustomitemproperties;
/*     */   }
/*     */   
/*     */   private static void addToItemList(CustomItemProperties cp, List itemList) {
/* 433 */     if (cp.items != null) {
/* 434 */       for (int i = 0; i < cp.items.length; i++) {
/* 435 */         int j = cp.items[i];
/*     */         
/* 437 */         if (j <= 0) {
/* 438 */           Config.warn("Invalid item ID: " + j);
/*     */         } else {
/* 440 */           addToList(cp, itemList, j);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static void addToEnchantmentList(CustomItemProperties cp, List enchantmentList) {
/* 447 */     if (cp.type == 2 && 
/* 448 */       cp.enchantmentIds != null) {
/* 449 */       for (int i = 0; i < 256; i++) {
/* 450 */         if (cp.enchantmentIds.isInRange(i)) {
/* 451 */           addToList(cp, enchantmentList, i);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToList(CustomItemProperties cp, List<List> list, int id) {
/* 459 */     while (id >= list.size()) {
/* 460 */       list.add(null);
/*     */     }
/*     */     
/* 463 */     List<CustomItemProperties> list1 = list.get(id);
/*     */     
/* 465 */     if (list1 == null) {
/* 466 */       list1 = new ArrayList();
/* 467 */       list.set(id, list1);
/*     */     } 
/*     */     
/* 470 */     list1.add(cp);
/*     */   }
/*     */   
/*     */   public static IBakedModel getCustomItemModel(ItemStack itemStack, IBakedModel model, ResourceLocation modelLocation, boolean fullModel) {
/* 474 */     if (!fullModel && model.isGui3d())
/* 475 */       return model; 
/* 476 */     if (itemProperties == null) {
/* 477 */       return model;
/*     */     }
/* 479 */     CustomItemProperties customitemproperties = getCustomItemProperties(itemStack, 1);
/*     */     
/* 481 */     if (customitemproperties == null) {
/* 482 */       return model;
/*     */     }
/* 484 */     IBakedModel ibakedmodel = customitemproperties.getBakedModel(modelLocation, fullModel);
/* 485 */     return (ibakedmodel != null) ? ibakedmodel : model;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean bindCustomArmorTexture(ItemStack itemStack, int layer, String overlay) {
/* 491 */     if (itemProperties == null) {
/* 492 */       return false;
/*     */     }
/* 494 */     ResourceLocation resourcelocation = getCustomArmorLocation(itemStack, layer, overlay);
/*     */     
/* 496 */     if (resourcelocation == null) {
/* 497 */       return false;
/*     */     }
/* 499 */     Config.getTextureManager().bindTexture(resourcelocation);
/* 500 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ResourceLocation getCustomArmorLocation(ItemStack itemStack, int layer, String overlay) {
/* 506 */     CustomItemProperties customitemproperties = getCustomItemProperties(itemStack, 3);
/*     */     
/* 508 */     if (customitemproperties == null)
/* 509 */       return null; 
/* 510 */     if (customitemproperties.mapTextureLocations == null) {
/* 511 */       return customitemproperties.textureLocation;
/*     */     }
/* 513 */     Item item = itemStack.getItem();
/*     */     
/* 515 */     if (!(item instanceof ItemArmor)) {
/* 516 */       return null;
/*     */     }
/* 518 */     ItemArmor itemarmor = (ItemArmor)item;
/* 519 */     String s = itemarmor.getArmorMaterial().getName();
/* 520 */     StringBuffer stringbuffer = new StringBuffer();
/* 521 */     stringbuffer.append("texture.");
/* 522 */     stringbuffer.append(s);
/* 523 */     stringbuffer.append("_layer_");
/* 524 */     stringbuffer.append(layer);
/*     */     
/* 526 */     if (overlay != null) {
/* 527 */       stringbuffer.append("_");
/* 528 */       stringbuffer.append(overlay);
/*     */     } 
/*     */     
/* 531 */     String s1 = stringbuffer.toString();
/* 532 */     ResourceLocation resourcelocation = (ResourceLocation)customitemproperties.mapTextureLocations.get(s1);
/* 533 */     return (resourcelocation == null) ? customitemproperties.textureLocation : resourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static CustomItemProperties getCustomItemProperties(ItemStack itemStack, int type) {
/* 539 */     if (itemProperties == null)
/* 540 */       return null; 
/* 541 */     if (itemStack == null) {
/* 542 */       return null;
/*     */     }
/* 544 */     Item item = itemStack.getItem();
/* 545 */     int i = Item.getIdFromItem(item);
/*     */     
/* 547 */     if (i >= 0 && i < itemProperties.length) {
/* 548 */       CustomItemProperties[] acustomitemproperties = itemProperties[i];
/*     */       
/* 550 */       if (acustomitemproperties != null) {
/* 551 */         for (int j = 0; j < acustomitemproperties.length; j++) {
/* 552 */           CustomItemProperties customitemproperties = acustomitemproperties[j];
/*     */           
/* 554 */           if (customitemproperties.type == type && matchesProperties(customitemproperties, itemStack, null)) {
/* 555 */             return customitemproperties;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 561 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean matchesProperties(CustomItemProperties cip, ItemStack itemStack, int[][] enchantmentIdLevels) {
/* 566 */     Item item = itemStack.getItem();
/*     */     
/* 568 */     if (cip.damage != null) {
/* 569 */       int i = itemStack.getItemDamage();
/*     */       
/* 571 */       if (cip.damageMask != 0) {
/* 572 */         i &= cip.damageMask;
/*     */       }
/*     */       
/* 575 */       if (cip.damagePercent) {
/* 576 */         int j = item.getMaxDamage();
/* 577 */         i = (int)((i * 100) / j);
/*     */       } 
/*     */       
/* 580 */       if (!cip.damage.isInRange(i)) {
/* 581 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 585 */     if (cip.stackSize != null && !cip.stackSize.isInRange(itemStack.stackSize)) {
/* 586 */       return false;
/*     */     }
/* 588 */     int[][] aint = enchantmentIdLevels;
/*     */     
/* 590 */     if (cip.enchantmentIds != null) {
/* 591 */       if (enchantmentIdLevels == null) {
/* 592 */         aint = getEnchantmentIdLevels(itemStack);
/*     */       }
/*     */       
/* 595 */       boolean flag = false;
/*     */       
/* 597 */       for (int k = 0; k < aint.length; k++) {
/* 598 */         int l = aint[k][0];
/*     */         
/* 600 */         if (cip.enchantmentIds.isInRange(l)) {
/* 601 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 606 */       if (!flag) {
/* 607 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 611 */     if (cip.enchantmentLevels != null) {
/* 612 */       if (aint == null) {
/* 613 */         aint = getEnchantmentIdLevels(itemStack);
/*     */       }
/*     */       
/* 616 */       boolean flag1 = false;
/*     */       
/* 618 */       for (int i1 = 0; i1 < aint.length; i1++) {
/* 619 */         int k1 = aint[i1][1];
/*     */         
/* 621 */         if (cip.enchantmentLevels.isInRange(k1)) {
/* 622 */           flag1 = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 627 */       if (!flag1) {
/* 628 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 632 */     if (cip.nbtTagValues != null) {
/* 633 */       NBTTagCompound nbttagcompound = itemStack.getTagCompound();
/*     */       
/* 635 */       for (int j1 = 0; j1 < cip.nbtTagValues.length; j1++) {
/* 636 */         NbtTagValue nbttagvalue = cip.nbtTagValues[j1];
/*     */         
/* 638 */         if (!nbttagvalue.matches(nbttagcompound)) {
/* 639 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 644 */     if (cip.hand != 0) {
/* 645 */       if (cip.hand == 1 && renderOffHand) {
/* 646 */         return false;
/*     */       }
/*     */       
/* 649 */       if (cip.hand == 2 && !renderOffHand) {
/* 650 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 654 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[][] getEnchantmentIdLevels(ItemStack itemStack) {
/* 659 */     Item item = itemStack.getItem();
/* 660 */     NBTTagList nbttaglist = (item == Items.enchanted_book) ? Items.enchanted_book.getEnchantments(itemStack) : itemStack.getEnchantmentTagList();
/*     */     
/* 662 */     if (nbttaglist != null && nbttaglist.tagCount() > 0) {
/* 663 */       int[][] aint = new int[nbttaglist.tagCount()][2];
/*     */       
/* 665 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/* 666 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 667 */         int j = nbttagcompound.getShort("id");
/* 668 */         int k = nbttagcompound.getShort("lvl");
/* 669 */         aint[i][0] = j;
/* 670 */         aint[i][1] = k;
/*     */       } 
/*     */       
/* 673 */       return aint;
/*     */     } 
/* 675 */     return EMPTY_INT2_ARRAY;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean renderCustomEffect(RenderItem renderItem, ItemStack itemStack, IBakedModel model) {
/* 680 */     if (enchantmentProperties == null)
/* 681 */       return false; 
/* 682 */     if (itemStack == null) {
/* 683 */       return false;
/*     */     }
/* 685 */     int[][] aint = getEnchantmentIdLevels(itemStack);
/*     */     
/* 687 */     if (aint.length <= 0) {
/* 688 */       return false;
/*     */     }
/* 690 */     Set<Integer> set = null;
/* 691 */     boolean flag = false;
/* 692 */     TextureManager texturemanager = Config.getTextureManager();
/*     */     
/* 694 */     for (int i = 0; i < aint.length; i++) {
/* 695 */       int j = aint[i][0];
/*     */       
/* 697 */       if (j >= 0 && j < enchantmentProperties.length) {
/* 698 */         CustomItemProperties[] acustomitemproperties = enchantmentProperties[j];
/*     */         
/* 700 */         if (acustomitemproperties != null) {
/* 701 */           for (int k = 0; k < acustomitemproperties.length; k++) {
/* 702 */             CustomItemProperties customitemproperties = acustomitemproperties[k];
/*     */             
/* 704 */             if (set == null) {
/* 705 */               set = new HashSet();
/*     */             }
/*     */             
/* 708 */             if (set.add(Integer.valueOf(j)) && matchesProperties(customitemproperties, itemStack, aint) && customitemproperties.textureLocation != null) {
/* 709 */               texturemanager.bindTexture(customitemproperties.textureLocation);
/* 710 */               float f = customitemproperties.getTextureWidth(texturemanager);
/*     */               
/* 712 */               if (!flag) {
/* 713 */                 flag = true;
/* 714 */                 GlStateManager.depthMask(false);
/* 715 */                 GlStateManager.depthFunc(514);
/* 716 */                 GlStateManager.disableLighting();
/* 717 */                 GlStateManager.matrixMode(5890);
/*     */               } 
/*     */               
/* 720 */               Blender.setupBlend(customitemproperties.blend, 1.0F);
/* 721 */               GlStateManager.pushMatrix();
/* 722 */               GlStateManager.scale(f / 2.0F, f / 2.0F, f / 2.0F);
/* 723 */               float f1 = customitemproperties.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/* 724 */               GlStateManager.translate(f1, 0.0F, 0.0F);
/* 725 */               GlStateManager.rotate(customitemproperties.rotation, 0.0F, 0.0F, 1.0F);
/* 726 */               renderItem.renderModel(model, -1);
/* 727 */               GlStateManager.popMatrix();
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 734 */     if (flag) {
/* 735 */       GlStateManager.enableAlpha();
/* 736 */       GlStateManager.enableBlend();
/* 737 */       GlStateManager.blendFunc(770, 771);
/* 738 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 739 */       GlStateManager.matrixMode(5888);
/* 740 */       GlStateManager.enableLighting();
/* 741 */       GlStateManager.depthFunc(515);
/* 742 */       GlStateManager.depthMask(true);
/* 743 */       texturemanager.bindTexture(TextureMap.locationBlocksTexture);
/*     */     } 
/*     */     
/* 746 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean renderCustomArmorEffect(EntityLivingBase entity, ItemStack itemStack, ModelBase model, float limbSwing, float prevLimbSwing, float partialTicks, float timeLimbSwing, float yaw, float pitch, float scale) {
/* 752 */     if (enchantmentProperties == null)
/* 753 */       return false; 
/* 754 */     if (Config.isShaders() && Shaders.isShadowPass)
/* 755 */       return false; 
/* 756 */     if (itemStack == null) {
/* 757 */       return false;
/*     */     }
/* 759 */     int[][] aint = getEnchantmentIdLevels(itemStack);
/*     */     
/* 761 */     if (aint.length <= 0) {
/* 762 */       return false;
/*     */     }
/* 764 */     Set<Integer> set = null;
/* 765 */     boolean flag = false;
/* 766 */     TextureManager texturemanager = Config.getTextureManager();
/*     */     
/* 768 */     for (int i = 0; i < aint.length; i++) {
/* 769 */       int j = aint[i][0];
/*     */       
/* 771 */       if (j >= 0 && j < enchantmentProperties.length) {
/* 772 */         CustomItemProperties[] acustomitemproperties = enchantmentProperties[j];
/*     */         
/* 774 */         if (acustomitemproperties != null) {
/* 775 */           for (int k = 0; k < acustomitemproperties.length; k++) {
/* 776 */             CustomItemProperties customitemproperties = acustomitemproperties[k];
/*     */             
/* 778 */             if (set == null) {
/* 779 */               set = new HashSet();
/*     */             }
/*     */             
/* 782 */             if (set.add(Integer.valueOf(j)) && matchesProperties(customitemproperties, itemStack, aint) && customitemproperties.textureLocation != null) {
/* 783 */               texturemanager.bindTexture(customitemproperties.textureLocation);
/* 784 */               float f = customitemproperties.getTextureWidth(texturemanager);
/*     */               
/* 786 */               if (!flag) {
/* 787 */                 flag = true;
/*     */                 
/* 789 */                 if (Config.isShaders()) {
/* 790 */                   ShadersRender.renderEnchantedGlintBegin();
/*     */                 }
/*     */                 
/* 793 */                 GlStateManager.enableBlend();
/* 794 */                 GlStateManager.depthFunc(514);
/* 795 */                 GlStateManager.depthMask(false);
/*     */               } 
/*     */               
/* 798 */               Blender.setupBlend(customitemproperties.blend, 1.0F);
/* 799 */               GlStateManager.disableLighting();
/* 800 */               GlStateManager.matrixMode(5890);
/* 801 */               GlStateManager.loadIdentity();
/* 802 */               GlStateManager.rotate(customitemproperties.rotation, 0.0F, 0.0F, 1.0F);
/* 803 */               float f1 = f / 8.0F;
/* 804 */               GlStateManager.scale(f1, f1 / 2.0F, f1);
/* 805 */               float f2 = customitemproperties.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
/* 806 */               GlStateManager.translate(0.0F, f2, 0.0F);
/* 807 */               GlStateManager.matrixMode(5888);
/* 808 */               model.render((Entity)entity, limbSwing, prevLimbSwing, timeLimbSwing, yaw, pitch, scale);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 815 */     if (flag) {
/* 816 */       GlStateManager.enableAlpha();
/* 817 */       GlStateManager.enableBlend();
/* 818 */       GlStateManager.blendFunc(770, 771);
/* 819 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 820 */       GlStateManager.matrixMode(5890);
/* 821 */       GlStateManager.loadIdentity();
/* 822 */       GlStateManager.matrixMode(5888);
/* 823 */       GlStateManager.enableLighting();
/* 824 */       GlStateManager.depthMask(true);
/* 825 */       GlStateManager.depthFunc(515);
/* 826 */       GlStateManager.disableBlend();
/*     */       
/* 828 */       if (Config.isShaders()) {
/* 829 */         ShadersRender.renderEnchantedGlintEnd();
/*     */       }
/*     */     } 
/*     */     
/* 833 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isUseGlint() {
/* 839 */     return useGlint;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\CustomItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */