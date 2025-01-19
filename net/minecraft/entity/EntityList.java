/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.ai.EntityMinecartMobSpawner;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.item.EntityEnderEye;
/*     */ import net.minecraft.entity.item.EntityEnderPearl;
/*     */ import net.minecraft.entity.item.EntityExpBottle;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.item.EntityMinecartChest;
/*     */ import net.minecraft.entity.item.EntityMinecartEmpty;
/*     */ import net.minecraft.entity.item.EntityMinecartFurnace;
/*     */ import net.minecraft.entity.item.EntityMinecartHopper;
/*     */ import net.minecraft.entity.item.EntityMinecartTNT;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCaveSpider;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityEndermite;
/*     */ import net.minecraft.entity.monster.EntityGhast;
/*     */ import net.minecraft.entity.monster.EntityGiantZombie;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntitySnowman;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.entity.passive.EntityMooshroom;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.passive.EntitySquid;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityEgg;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.entity.projectile.EntitySnowball;
/*     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityList
/*     */ {
/*  80 */   private static final Logger logger = LogManager.getLogger();
/*  81 */   private static final Map<String, Class<? extends Entity>> stringToClassMapping = Maps.newHashMap();
/*  82 */   private static final Map<Class<? extends Entity>, String> classToStringMapping = Maps.newHashMap();
/*  83 */   private static final Map<Integer, Class<? extends Entity>> idToClassMapping = Maps.newHashMap();
/*  84 */   private static final Map<Class<? extends Entity>, Integer> classToIDMapping = Maps.newHashMap();
/*  85 */   private static final Map<String, Integer> stringToIDMapping = Maps.newHashMap();
/*  86 */   public static final Map<Integer, EntityEggInfo> entityEggs = Maps.newLinkedHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addMapping(Class<? extends Entity> entityClass, String entityName, int id) {
/*  92 */     if (stringToClassMapping.containsKey(entityName))
/*  93 */       throw new IllegalArgumentException("ID is already registered: " + entityName); 
/*  94 */     if (idToClassMapping.containsKey(Integer.valueOf(id)))
/*  95 */       throw new IllegalArgumentException("ID is already registered: " + id); 
/*  96 */     if (id == 0)
/*  97 */       throw new IllegalArgumentException("Cannot register to reserved id: " + id); 
/*  98 */     if (entityClass == null) {
/*  99 */       throw new IllegalArgumentException("Cannot register null clazz for id: " + id);
/*     */     }
/* 101 */     stringToClassMapping.put(entityName, entityClass);
/* 102 */     classToStringMapping.put(entityClass, entityName);
/* 103 */     idToClassMapping.put(Integer.valueOf(id), entityClass);
/* 104 */     classToIDMapping.put(entityClass, Integer.valueOf(id));
/* 105 */     stringToIDMapping.put(entityName, Integer.valueOf(id));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addMapping(Class<? extends Entity> entityClass, String entityName, int entityID, int baseColor, int spotColor) {
/* 113 */     addMapping(entityClass, entityName, entityID);
/* 114 */     entityEggs.put(Integer.valueOf(entityID), new EntityEggInfo(entityID, baseColor, spotColor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity createEntityByName(String entityName, World worldIn) {
/* 121 */     Entity entity = null;
/*     */     
/*     */     try {
/* 124 */       Class<? extends Entity> oclass = stringToClassMapping.get(entityName);
/*     */       
/* 126 */       if (oclass != null) {
/* 127 */         entity = oclass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */       }
/* 129 */     } catch (Exception exception) {
/* 130 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 133 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity createEntityFromNBT(NBTTagCompound nbt, World worldIn) {
/* 140 */     Entity entity = null;
/*     */     
/* 142 */     if ("Minecart".equals(nbt.getString("id"))) {
/* 143 */       nbt.setString("id", EntityMinecart.EnumMinecartType.byNetworkID(nbt.getInteger("Type")).getName());
/* 144 */       nbt.removeTag("Type");
/*     */     } 
/*     */     
/*     */     try {
/* 148 */       Class<? extends Entity> oclass = stringToClassMapping.get(nbt.getString("id"));
/*     */       
/* 150 */       if (oclass != null) {
/* 151 */         entity = oclass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */       }
/* 153 */     } catch (Exception exception) {
/* 154 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 157 */     if (entity != null) {
/* 158 */       entity.readFromNBT(nbt);
/*     */     } else {
/* 160 */       logger.warn("Skipping Entity with id " + nbt.getString("id"));
/*     */     } 
/*     */     
/* 163 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity createEntityByID(int entityID, World worldIn) {
/* 170 */     Entity entity = null;
/*     */     
/*     */     try {
/* 173 */       Class<? extends Entity> oclass = getClassFromID(entityID);
/*     */       
/* 175 */       if (oclass != null) {
/* 176 */         entity = oclass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */       }
/* 178 */     } catch (Exception exception) {
/* 179 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 182 */     if (entity == null) {
/* 183 */       logger.warn("Skipping Entity with id " + entityID);
/*     */     }
/*     */     
/* 186 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEntityID(Entity entityIn) {
/* 193 */     Integer integer = classToIDMapping.get(entityIn.getClass());
/* 194 */     return (integer == null) ? 0 : integer.intValue();
/*     */   }
/*     */   
/*     */   public static Class<? extends Entity> getClassFromID(int entityID) {
/* 198 */     return idToClassMapping.get(Integer.valueOf(entityID));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEntityString(Entity entityIn) {
/* 205 */     return classToStringMapping.get(entityIn.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getIDFromString(String entityName) {
/* 212 */     Integer integer = stringToIDMapping.get(entityName);
/* 213 */     return (integer == null) ? 90 : integer.intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getStringFromID(int entityID) {
/* 220 */     return classToStringMapping.get(getClassFromID(entityID));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_151514_a() {}
/*     */   
/*     */   public static List<String> getEntityNameList() {
/* 227 */     Set<String> set = stringToClassMapping.keySet();
/* 228 */     List<String> list = Lists.newArrayList();
/*     */     
/* 230 */     for (String s : set) {
/* 231 */       Class<? extends Entity> oclass = stringToClassMapping.get(s);
/*     */       
/* 233 */       if ((oclass.getModifiers() & 0x400) != 1024) {
/* 234 */         list.add(s);
/*     */       }
/*     */     } 
/*     */     
/* 238 */     list.add("LightningBolt");
/* 239 */     return list;
/*     */   }
/*     */   
/*     */   public static boolean isStringEntityName(Entity entityIn, String entityName) {
/* 243 */     String s = getEntityString(entityIn);
/*     */     
/* 245 */     if (s == null && entityIn instanceof net.minecraft.entity.player.EntityPlayer) {
/* 246 */       s = "Player";
/* 247 */     } else if (s == null && entityIn instanceof net.minecraft.entity.effect.EntityLightningBolt) {
/* 248 */       s = "LightningBolt";
/*     */     } 
/*     */     
/* 251 */     return entityName.equals(s);
/*     */   }
/*     */   
/*     */   public static boolean isStringValidEntityName(String entityName) {
/* 255 */     return !(!"Player".equals(entityName) && !getEntityNameList().contains(entityName));
/*     */   }
/*     */   
/*     */   static {
/* 259 */     addMapping((Class)EntityItem.class, "Item", 1);
/* 260 */     addMapping((Class)EntityXPOrb.class, "XPOrb", 2);
/* 261 */     addMapping((Class)EntityEgg.class, "ThrownEgg", 7);
/* 262 */     addMapping((Class)EntityLeashKnot.class, "LeashKnot", 8);
/* 263 */     addMapping((Class)EntityPainting.class, "Painting", 9);
/* 264 */     addMapping((Class)EntityArrow.class, "Arrow", 10);
/* 265 */     addMapping((Class)EntitySnowball.class, "Snowball", 11);
/* 266 */     addMapping((Class)EntityLargeFireball.class, "Fireball", 12);
/* 267 */     addMapping((Class)EntitySmallFireball.class, "SmallFireball", 13);
/* 268 */     addMapping((Class)EntityEnderPearl.class, "ThrownEnderpearl", 14);
/* 269 */     addMapping((Class)EntityEnderEye.class, "EyeOfEnderSignal", 15);
/* 270 */     addMapping((Class)EntityPotion.class, "ThrownPotion", 16);
/* 271 */     addMapping((Class)EntityExpBottle.class, "ThrownExpBottle", 17);
/* 272 */     addMapping((Class)EntityItemFrame.class, "ItemFrame", 18);
/* 273 */     addMapping((Class)EntityWitherSkull.class, "WitherSkull", 19);
/* 274 */     addMapping((Class)EntityTNTPrimed.class, "PrimedTnt", 20);
/* 275 */     addMapping((Class)EntityFallingBlock.class, "FallingSand", 21);
/* 276 */     addMapping((Class)EntityFireworkRocket.class, "FireworksRocketEntity", 22);
/* 277 */     addMapping((Class)EntityArmorStand.class, "ArmorStand", 30);
/* 278 */     addMapping((Class)EntityBoat.class, "Boat", 41);
/* 279 */     addMapping((Class)EntityMinecartEmpty.class, EntityMinecart.EnumMinecartType.RIDEABLE.getName(), 42);
/* 280 */     addMapping((Class)EntityMinecartChest.class, EntityMinecart.EnumMinecartType.CHEST.getName(), 43);
/* 281 */     addMapping((Class)EntityMinecartFurnace.class, EntityMinecart.EnumMinecartType.FURNACE.getName(), 44);
/* 282 */     addMapping((Class)EntityMinecartTNT.class, EntityMinecart.EnumMinecartType.TNT.getName(), 45);
/* 283 */     addMapping((Class)EntityMinecartHopper.class, EntityMinecart.EnumMinecartType.HOPPER.getName(), 46);
/* 284 */     addMapping((Class)EntityMinecartMobSpawner.class, EntityMinecart.EnumMinecartType.SPAWNER.getName(), 47);
/* 285 */     addMapping((Class)EntityMinecartCommandBlock.class, EntityMinecart.EnumMinecartType.COMMAND_BLOCK.getName(), 40);
/* 286 */     addMapping((Class)EntityLiving.class, "Mob", 48);
/* 287 */     addMapping((Class)EntityMob.class, "Monster", 49);
/* 288 */     addMapping((Class)EntityCreeper.class, "Creeper", 50, 894731, 0);
/* 289 */     addMapping((Class)EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
/* 290 */     addMapping((Class)EntitySpider.class, "Spider", 52, 3419431, 11013646);
/* 291 */     addMapping((Class)EntityGiantZombie.class, "Giant", 53);
/* 292 */     addMapping((Class)EntityZombie.class, "Zombie", 54, 44975, 7969893);
/* 293 */     addMapping((Class)EntitySlime.class, "Slime", 55, 5349438, 8306542);
/* 294 */     addMapping((Class)EntityGhast.class, "Ghast", 56, 16382457, 12369084);
/* 295 */     addMapping((Class)EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
/* 296 */     addMapping((Class)EntityEnderman.class, "Enderman", 58, 1447446, 0);
/* 297 */     addMapping((Class)EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
/* 298 */     addMapping((Class)EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
/* 299 */     addMapping((Class)EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
/* 300 */     addMapping((Class)EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
/* 301 */     addMapping((Class)EntityDragon.class, "EnderDragon", 63);
/* 302 */     addMapping((Class)EntityWither.class, "WitherBoss", 64);
/* 303 */     addMapping((Class)EntityBat.class, "Bat", 65, 4996656, 986895);
/* 304 */     addMapping((Class)EntityWitch.class, "Witch", 66, 3407872, 5349438);
/* 305 */     addMapping((Class)EntityEndermite.class, "Endermite", 67, 1447446, 7237230);
/* 306 */     addMapping((Class)EntityGuardian.class, "Guardian", 68, 5931634, 15826224);
/* 307 */     addMapping((Class)EntityPig.class, "Pig", 90, 15771042, 14377823);
/* 308 */     addMapping((Class)EntitySheep.class, "Sheep", 91, 15198183, 16758197);
/* 309 */     addMapping((Class)EntityCow.class, "Cow", 92, 4470310, 10592673);
/* 310 */     addMapping((Class)EntityChicken.class, "Chicken", 93, 10592673, 16711680);
/* 311 */     addMapping((Class)EntitySquid.class, "Squid", 94, 2243405, 7375001);
/* 312 */     addMapping((Class)EntityWolf.class, "Wolf", 95, 14144467, 13545366);
/* 313 */     addMapping((Class)EntityMooshroom.class, "MushroomCow", 96, 10489616, 12040119);
/* 314 */     addMapping((Class)EntitySnowman.class, "SnowMan", 97);
/* 315 */     addMapping((Class)EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
/* 316 */     addMapping((Class)EntityIronGolem.class, "VillagerGolem", 99);
/* 317 */     addMapping((Class)EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
/* 318 */     addMapping((Class)EntityRabbit.class, "Rabbit", 101, 10051392, 7555121);
/* 319 */     addMapping((Class)EntityVillager.class, "Villager", 120, 5651507, 12422002);
/* 320 */     addMapping((Class)EntityEnderCrystal.class, "EnderCrystal", 200);
/*     */   }
/*     */   
/*     */   public static class EntityEggInfo {
/*     */     public final int spawnedID;
/*     */     public final int primaryColor;
/*     */     public final int secondaryColor;
/*     */     public final StatBase field_151512_d;
/*     */     public final StatBase field_151513_e;
/*     */     
/*     */     public EntityEggInfo(int id, int baseColor, int spotColor) {
/* 331 */       this.spawnedID = id;
/* 332 */       this.primaryColor = baseColor;
/* 333 */       this.secondaryColor = spotColor;
/* 334 */       this.field_151512_d = StatList.getStatKillEntity(this);
/* 335 */       this.field_151513_e = StatList.getStatEntityKilledBy(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\EntityList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */