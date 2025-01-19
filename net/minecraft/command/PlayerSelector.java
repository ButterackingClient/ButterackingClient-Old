/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerSelector
/*     */ {
/*  43 */   private static final Pattern tokenPattern = Pattern.compile("^@([pare])(?:\\[([\\w=,!-]*)\\])?$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private static final Pattern intListPattern = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private static final Pattern keyValueListPattern = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
/*  54 */   private static final Set<String> WORLD_BINDING_ARGS = Sets.newHashSet((Object[])new String[] { "x", "y", "z", "dx", "dy", "dz", "rm", "r" });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayerMP matchOnePlayer(ICommandSender sender, String token) {
/*  60 */     return matchOneEntity(sender, token, EntityPlayerMP.class);
/*     */   }
/*     */   
/*     */   public static <T extends Entity> T matchOneEntity(ICommandSender sender, String token, Class<? extends T> targetClass) {
/*  64 */     List<T> list = matchEntities(sender, token, targetClass);
/*  65 */     return (list.size() == 1) ? list.get(0) : null;
/*     */   }
/*     */   
/*     */   public static IChatComponent matchEntitiesToChatComponent(ICommandSender sender, String token) {
/*  69 */     List<Entity> list = matchEntities(sender, token, Entity.class);
/*     */     
/*  71 */     if (list.isEmpty()) {
/*  72 */       return null;
/*     */     }
/*  74 */     List<IChatComponent> list1 = Lists.newArrayList();
/*     */     
/*  76 */     for (Entity entity : list) {
/*  77 */       list1.add(entity.getDisplayName());
/*     */     }
/*     */     
/*  80 */     return CommandBase.join(list1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T extends Entity> List<T> matchEntities(ICommandSender sender, String token, Class<? extends T> targetClass) {
/*  85 */     Matcher matcher = tokenPattern.matcher(token);
/*     */     
/*  87 */     if (matcher.matches() && sender.canCommandSenderUseCommand(1, "@")) {
/*  88 */       Map<String, String> map = getArgumentMap(matcher.group(2));
/*     */       
/*  90 */       if (!isEntityTypeValid(sender, map)) {
/*  91 */         return Collections.emptyList();
/*     */       }
/*  93 */       String s = matcher.group(1);
/*  94 */       BlockPos blockpos = func_179664_b(map, sender.getPosition());
/*  95 */       List<World> list = getWorlds(sender, map);
/*  96 */       List<T> list1 = Lists.newArrayList();
/*     */       
/*  98 */       for (World world : list) {
/*  99 */         if (world != null) {
/* 100 */           List<Predicate<Entity>> list2 = Lists.newArrayList();
/* 101 */           list2.addAll(func_179663_a(map, s));
/* 102 */           list2.addAll(getXpLevelPredicates(map));
/* 103 */           list2.addAll(getGamemodePredicates(map));
/* 104 */           list2.addAll(getTeamPredicates(map));
/* 105 */           list2.addAll(getScorePredicates(map));
/* 106 */           list2.addAll(getNamePredicates(map));
/* 107 */           list2.addAll(func_180698_a(map, blockpos));
/* 108 */           list2.addAll(getRotationsPredicates(map));
/* 109 */           list1.addAll(filterResults(map, targetClass, list2, s, world, blockpos));
/*     */         } 
/*     */       } 
/*     */       
/* 113 */       return func_179658_a(list1, map, sender, targetClass, s, blockpos);
/*     */     } 
/*     */     
/* 116 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<World> getWorlds(ICommandSender sender, Map<String, String> argumentMap) {
/* 121 */     List<World> list = Lists.newArrayList();
/*     */     
/* 123 */     if (func_179665_h(argumentMap)) {
/* 124 */       list.add(sender.getEntityWorld());
/*     */     } else {
/* 126 */       Collections.addAll(list, (Object[])(MinecraftServer.getServer()).worldServers);
/*     */     } 
/*     */     
/* 129 */     return list;
/*     */   }
/*     */   
/*     */   private static <T extends Entity> boolean isEntityTypeValid(ICommandSender commandSender, Map<String, String> params) {
/* 133 */     String s = func_179651_b(params, "type");
/* 134 */     s = (s != null && s.startsWith("!")) ? s.substring(1) : s;
/*     */     
/* 136 */     if (s != null && !EntityList.isStringValidEntityName(s)) {
/* 137 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("commands.generic.entity.invalidType", new Object[] { s });
/* 138 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/* 139 */       commandSender.addChatMessage((IChatComponent)chatcomponenttranslation);
/* 140 */       return false;
/*     */     } 
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Predicate<Entity>> func_179663_a(Map<String, String> p_179663_0_, String p_179663_1_) {
/* 147 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 148 */     String s = func_179651_b(p_179663_0_, "type");
/* 149 */     final boolean flag = (s != null && s.startsWith("!"));
/*     */     
/* 151 */     if (flag) {
/* 152 */       s = s.substring(1);
/*     */     }
/*     */     
/* 155 */     boolean flag1 = !p_179663_1_.equals("e");
/* 156 */     boolean flag2 = (p_179663_1_.equals("r") && s != null);
/*     */     
/* 158 */     if ((s == null || !p_179663_1_.equals("e")) && !flag2) {
/* 159 */       if (flag1) {
/* 160 */         list.add(new Predicate<Entity>() {
/*     */               public boolean apply(Entity p_apply_1_) {
/* 162 */                 return p_apply_1_ instanceof net.minecraft.entity.player.EntityPlayer;
/*     */               }
/*     */             });
/*     */       }
/*     */     } else {
/* 167 */       final String s_f = s;
/* 168 */       list.add(new Predicate<Entity>() {
/*     */             public boolean apply(Entity p_apply_1_) {
/* 170 */               return EntityList.isStringEntityName(p_apply_1_, s_f) ^ flag;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 175 */     return list;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> getXpLevelPredicates(Map<String, String> p_179648_0_) {
/* 179 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 180 */     final int i = parseIntWithDefault(p_179648_0_, "lm", -1);
/* 181 */     final int j = parseIntWithDefault(p_179648_0_, "l", -1);
/*     */     
/* 183 */     if (i > -1 || j > -1) {
/* 184 */       list.add(new Predicate<Entity>() {
/*     */             public boolean apply(Entity p_apply_1_) {
/* 186 */               if (!(p_apply_1_ instanceof EntityPlayerMP)) {
/* 187 */                 return false;
/*     */               }
/* 189 */               EntityPlayerMP entityplayermp = (EntityPlayerMP)p_apply_1_;
/* 190 */               return ((i <= -1 || entityplayermp.experienceLevel >= i) && (j <= -1 || entityplayermp.experienceLevel <= j));
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 196 */     return list;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> getGamemodePredicates(Map<String, String> p_179649_0_) {
/* 200 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 201 */     final int i = parseIntWithDefault(p_179649_0_, "m", WorldSettings.GameType.NOT_SET.getID());
/*     */     
/* 203 */     if (i != WorldSettings.GameType.NOT_SET.getID()) {
/* 204 */       list.add(new Predicate<Entity>() {
/*     */             public boolean apply(Entity p_apply_1_) {
/* 206 */               if (!(p_apply_1_ instanceof EntityPlayerMP)) {
/* 207 */                 return false;
/*     */               }
/* 209 */               EntityPlayerMP entityplayermp = (EntityPlayerMP)p_apply_1_;
/* 210 */               return (entityplayermp.theItemInWorldManager.getGameType().getID() == i);
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 216 */     return list;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> getTeamPredicates(Map<String, String> p_179659_0_) {
/* 220 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 221 */     String s = func_179651_b(p_179659_0_, "team");
/* 222 */     final boolean flag = (s != null && s.startsWith("!"));
/*     */     
/* 224 */     if (flag) {
/* 225 */       s = s.substring(1);
/*     */     }
/*     */     
/* 228 */     if (s != null) {
/* 229 */       final String s_f = s;
/* 230 */       list.add(new Predicate<Entity>() {
/*     */             public boolean apply(Entity p_apply_1_) {
/* 232 */               if (!(p_apply_1_ instanceof EntityLivingBase)) {
/* 233 */                 return false;
/*     */               }
/* 235 */               EntityLivingBase entitylivingbase = (EntityLivingBase)p_apply_1_;
/* 236 */               Team team = entitylivingbase.getTeam();
/* 237 */               String s1 = (team == null) ? "" : team.getRegisteredName();
/* 238 */               return s1.equals(s_f) ^ flag;
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/* 244 */     return list;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> getScorePredicates(Map<String, String> p_179657_0_) {
/* 248 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 249 */     final Map<String, Integer> map = func_96560_a(p_179657_0_);
/*     */     
/* 251 */     if (map != null && map.size() > 0) {
/* 252 */       list.add(new Predicate<Entity>() {
/*     */             public boolean apply(Entity p_apply_1_) {
/* 254 */               Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
/*     */               
/* 256 */               for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>)map.entrySet()) {
/* 257 */                 String s = entry.getKey();
/* 258 */                 boolean flag = false;
/*     */                 
/* 260 */                 if (s.endsWith("_min") && s.length() > 4) {
/* 261 */                   flag = true;
/* 262 */                   s = s.substring(0, s.length() - 4);
/*     */                 } 
/*     */                 
/* 265 */                 ScoreObjective scoreobjective = scoreboard.getObjective(s);
/*     */                 
/* 267 */                 if (scoreobjective == null) {
/* 268 */                   return false;
/*     */                 }
/*     */                 
/* 271 */                 String s1 = (p_apply_1_ instanceof EntityPlayerMP) ? p_apply_1_.getName() : p_apply_1_.getUniqueID().toString();
/*     */                 
/* 273 */                 if (!scoreboard.entityHasObjective(s1, scoreobjective)) {
/* 274 */                   return false;
/*     */                 }
/*     */                 
/* 277 */                 Score score = scoreboard.getValueFromObjective(s1, scoreobjective);
/* 278 */                 int i = score.getScorePoints();
/*     */                 
/* 280 */                 if (i < ((Integer)entry.getValue()).intValue() && flag) {
/* 281 */                   return false;
/*     */                 }
/*     */                 
/* 284 */                 if (i > ((Integer)entry.getValue()).intValue() && !flag) {
/* 285 */                   return false;
/*     */                 }
/*     */               } 
/*     */               
/* 289 */               return true;
/*     */             }
/*     */           });
/*     */     }
/*     */     
/* 294 */     return list;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> getNamePredicates(Map<String, String> p_179647_0_) {
/* 298 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 299 */     String s = func_179651_b(p_179647_0_, "name");
/* 300 */     final boolean flag = (s != null && s.startsWith("!"));
/*     */     
/* 302 */     if (flag) {
/* 303 */       s = s.substring(1);
/*     */     }
/*     */     
/* 306 */     if (s != null) {
/* 307 */       final String s_f = s;
/* 308 */       list.add(new Predicate<Entity>() {
/*     */             public boolean apply(Entity p_apply_1_) {
/* 310 */               return p_apply_1_.getName().equals(s_f) ^ flag;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 315 */     return list;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> func_180698_a(Map<String, String> p_180698_0_, final BlockPos p_180698_1_) {
/* 319 */     List<Predicate<Entity>> list = Lists.newArrayList();
/* 320 */     final int i = parseIntWithDefault(p_180698_0_, "rm", -1);
/* 321 */     final int j = parseIntWithDefault(p_180698_0_, "r", -1);
/*     */     
/* 323 */     if (p_180698_1_ != null && (i >= 0 || j >= 0)) {
/* 324 */       final int k = i * i;
/* 325 */       final int l = j * j;
/* 326 */       list.add(new Predicate<Entity>() {
/*     */             public boolean apply(Entity p_apply_1_) {
/* 328 */               int i1 = (int)p_apply_1_.getDistanceSqToCenter(p_180698_1_);
/* 329 */               return ((i < 0 || i1 >= k) && (j < 0 || i1 <= l));
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 334 */     return list;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> getRotationsPredicates(Map<String, String> p_179662_0_) {
/* 338 */     List<Predicate<Entity>> list = Lists.newArrayList();
/*     */     
/* 340 */     if (p_179662_0_.containsKey("rym") || p_179662_0_.containsKey("ry")) {
/* 341 */       final int i = func_179650_a(parseIntWithDefault(p_179662_0_, "rym", 0));
/* 342 */       final int j = func_179650_a(parseIntWithDefault(p_179662_0_, "ry", 359));
/* 343 */       list.add(new Predicate<Entity>() {
/*     */             public boolean apply(Entity p_apply_1_) {
/* 345 */               int i1 = PlayerSelector.func_179650_a((int)Math.floor(p_apply_1_.rotationYaw));
/* 346 */               return (i > j) ? (!(i1 < i && i1 > j)) : ((i1 >= i && i1 <= j));
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 351 */     if (p_179662_0_.containsKey("rxm") || p_179662_0_.containsKey("rx")) {
/* 352 */       final int k = func_179650_a(parseIntWithDefault(p_179662_0_, "rxm", 0));
/* 353 */       final int l = func_179650_a(parseIntWithDefault(p_179662_0_, "rx", 359));
/* 354 */       list.add(new Predicate<Entity>() {
/*     */             public boolean apply(Entity p_apply_1_) {
/* 356 */               int i1 = PlayerSelector.func_179650_a((int)Math.floor(p_apply_1_.rotationPitch));
/* 357 */               return (k > l) ? (!(i1 < k && i1 > l)) : ((i1 >= k && i1 <= l));
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 362 */     return list;
/*     */   }
/*     */   
/*     */   private static <T extends Entity> List<T> filterResults(Map<String, String> params, Class<? extends T> entityClass, List<Predicate<Entity>> inputList, String type, World worldIn, BlockPos position) {
/* 366 */     List<T> list = Lists.newArrayList();
/* 367 */     String s = func_179651_b(params, "type");
/* 368 */     s = (s != null && s.startsWith("!")) ? s.substring(1) : s;
/* 369 */     boolean flag = !type.equals("e");
/* 370 */     boolean flag1 = (type.equals("r") && s != null);
/* 371 */     int i = parseIntWithDefault(params, "dx", 0);
/* 372 */     int j = parseIntWithDefault(params, "dy", 0);
/* 373 */     int k = parseIntWithDefault(params, "dz", 0);
/* 374 */     int l = parseIntWithDefault(params, "r", -1);
/* 375 */     Predicate<Entity> predicate = Predicates.and(inputList);
/* 376 */     Predicate<Entity> predicate1 = Predicates.and(EntitySelectors.selectAnything, predicate);
/*     */     
/* 378 */     if (position != null) {
/* 379 */       int i1 = worldIn.playerEntities.size();
/* 380 */       int j1 = worldIn.loadedEntityList.size();
/* 381 */       boolean flag2 = (i1 < j1 / 16);
/*     */       
/* 383 */       if (!params.containsKey("dx") && !params.containsKey("dy") && !params.containsKey("dz")) {
/* 384 */         if (l >= 0) {
/* 385 */           AxisAlignedBB axisalignedbb1 = new AxisAlignedBB((position.getX() - l), (position.getY() - l), (position.getZ() - l), (position.getX() + l + 1), (position.getY() + l + 1), (position.getZ() + l + 1));
/*     */           
/* 387 */           if (flag && flag2 && !flag1) {
/* 388 */             list.addAll(worldIn.getPlayers(entityClass, predicate1));
/*     */           } else {
/* 390 */             list.addAll(worldIn.getEntitiesWithinAABB(entityClass, axisalignedbb1, predicate1));
/*     */           } 
/* 392 */         } else if (type.equals("a")) {
/* 393 */           list.addAll(worldIn.getPlayers(entityClass, predicate));
/* 394 */         } else if (!type.equals("p") && (!type.equals("r") || flag1)) {
/* 395 */           list.addAll(worldIn.getEntities(entityClass, predicate1));
/*     */         } else {
/* 397 */           list.addAll(worldIn.getPlayers(entityClass, predicate1));
/*     */         } 
/*     */       } else {
/* 400 */         final AxisAlignedBB axisalignedbb = func_179661_a(position, i, j, k);
/*     */         
/* 402 */         if (flag && flag2 && !flag1) {
/* 403 */           Predicate<Entity> predicate2 = new Predicate<Entity>() {
/*     */               public boolean apply(Entity p_apply_1_) {
/* 405 */                 return (p_apply_1_.posX >= axisalignedbb.minX && p_apply_1_.posY >= axisalignedbb.minY && p_apply_1_.posZ >= axisalignedbb.minZ) ? ((p_apply_1_.posX < axisalignedbb.maxX && p_apply_1_.posY < axisalignedbb.maxY && p_apply_1_.posZ < axisalignedbb.maxZ)) : false;
/*     */               }
/*     */             };
/* 408 */           list.addAll(worldIn.getPlayers(entityClass, Predicates.and(predicate1, predicate2)));
/*     */         } else {
/* 410 */           list.addAll(worldIn.getEntitiesWithinAABB(entityClass, axisalignedbb, predicate1));
/*     */         } 
/*     */       } 
/* 413 */     } else if (type.equals("a")) {
/* 414 */       list.addAll(worldIn.getPlayers(entityClass, predicate));
/* 415 */     } else if (!type.equals("p") && (!type.equals("r") || flag1)) {
/* 416 */       list.addAll(worldIn.getEntities(entityClass, predicate1));
/*     */     } else {
/* 418 */       list.addAll(worldIn.getPlayers(entityClass, predicate1));
/*     */     } 
/*     */     
/* 421 */     return list;
/*     */   }
/*     */   
/*     */   private static <T extends Entity> List<T> func_179658_a(List<T> p_179658_0_, Map<String, String> p_179658_1_, ICommandSender p_179658_2_, Class<? extends T> p_179658_3_, String p_179658_4_, final BlockPos p_179658_5_) {
/* 425 */     int i = parseIntWithDefault(p_179658_1_, "c", (!p_179658_4_.equals("a") && !p_179658_4_.equals("e")) ? 1 : 0);
/*     */     
/* 427 */     if (!p_179658_4_.equals("p") && !p_179658_4_.equals("a") && !p_179658_4_.equals("e")) {
/* 428 */       if (p_179658_4_.equals("r")) {
/* 429 */         Collections.shuffle(p_179658_0_);
/*     */       }
/* 431 */     } else if (p_179658_5_ != null) {
/* 432 */       Collections.sort(p_179658_0_, (Comparator)new Comparator<Entity>() {
/*     */             public int compare(Entity p_compare_1_, Entity p_compare_2_) {
/* 434 */               return ComparisonChain.start().compare(p_compare_1_.getDistanceSq(p_179658_5_), p_compare_2_.getDistanceSq(p_179658_5_)).result();
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 439 */     Entity entity = p_179658_2_.getCommandSenderEntity();
/*     */     
/* 441 */     if (entity != null && p_179658_3_.isAssignableFrom(entity.getClass()) && i == 1 && p_179658_0_.contains(entity) && !"r".equals(p_179658_4_)) {
/* 442 */       p_179658_0_ = Lists.newArrayList((Object[])new Entity[] { entity });
/*     */     }
/*     */     
/* 445 */     if (i != 0) {
/* 446 */       if (i < 0) {
/* 447 */         Collections.reverse(p_179658_0_);
/*     */       }
/*     */       
/* 450 */       p_179658_0_ = p_179658_0_.subList(0, Math.min(Math.abs(i), p_179658_0_.size()));
/*     */     } 
/*     */     
/* 453 */     return p_179658_0_;
/*     */   }
/*     */   
/*     */   private static AxisAlignedBB func_179661_a(BlockPos p_179661_0_, int p_179661_1_, int p_179661_2_, int p_179661_3_) {
/* 457 */     boolean flag = (p_179661_1_ < 0);
/* 458 */     boolean flag1 = (p_179661_2_ < 0);
/* 459 */     boolean flag2 = (p_179661_3_ < 0);
/* 460 */     int i = p_179661_0_.getX() + (flag ? p_179661_1_ : 0);
/* 461 */     int j = p_179661_0_.getY() + (flag1 ? p_179661_2_ : 0);
/* 462 */     int k = p_179661_0_.getZ() + (flag2 ? p_179661_3_ : 0);
/* 463 */     int l = p_179661_0_.getX() + (flag ? 0 : p_179661_1_) + 1;
/* 464 */     int i1 = p_179661_0_.getY() + (flag1 ? 0 : p_179661_2_) + 1;
/* 465 */     int j1 = p_179661_0_.getZ() + (flag2 ? 0 : p_179661_3_) + 1;
/* 466 */     return new AxisAlignedBB(i, j, k, l, i1, j1);
/*     */   }
/*     */   
/*     */   public static int func_179650_a(int p_179650_0_) {
/* 470 */     p_179650_0_ %= 360;
/*     */     
/* 472 */     if (p_179650_0_ >= 160) {
/* 473 */       p_179650_0_ -= 360;
/*     */     }
/*     */     
/* 476 */     if (p_179650_0_ < 0) {
/* 477 */       p_179650_0_ += 360;
/*     */     }
/*     */     
/* 480 */     return p_179650_0_;
/*     */   }
/*     */   
/*     */   private static BlockPos func_179664_b(Map<String, String> p_179664_0_, BlockPos p_179664_1_) {
/* 484 */     return new BlockPos(parseIntWithDefault(p_179664_0_, "x", p_179664_1_.getX()), parseIntWithDefault(p_179664_0_, "y", p_179664_1_.getY()), parseIntWithDefault(p_179664_0_, "z", p_179664_1_.getZ()));
/*     */   }
/*     */   
/*     */   private static boolean func_179665_h(Map<String, String> p_179665_0_) {
/* 488 */     for (String s : WORLD_BINDING_ARGS) {
/* 489 */       if (p_179665_0_.containsKey(s)) {
/* 490 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 494 */     return false;
/*     */   }
/*     */   
/*     */   private static int parseIntWithDefault(Map<String, String> p_179653_0_, String p_179653_1_, int p_179653_2_) {
/* 498 */     return p_179653_0_.containsKey(p_179653_1_) ? MathHelper.parseIntWithDefault(p_179653_0_.get(p_179653_1_), p_179653_2_) : p_179653_2_;
/*     */   }
/*     */   
/*     */   private static String func_179651_b(Map<String, String> p_179651_0_, String p_179651_1_) {
/* 502 */     return p_179651_0_.get(p_179651_1_);
/*     */   }
/*     */   
/*     */   public static Map<String, Integer> func_96560_a(Map<String, String> p_96560_0_) {
/* 506 */     Map<String, Integer> map = Maps.newHashMap();
/*     */     
/* 508 */     for (String s : p_96560_0_.keySet()) {
/* 509 */       if (s.startsWith("score_") && s.length() > "score_".length()) {
/* 510 */         map.put(s.substring("score_".length()), Integer.valueOf(MathHelper.parseIntWithDefault(p_96560_0_.get(s), 1)));
/*     */       }
/*     */     } 
/*     */     
/* 514 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean matchesMultiplePlayers(String p_82377_0_) {
/* 521 */     Matcher matcher = tokenPattern.matcher(p_82377_0_);
/*     */     
/* 523 */     if (!matcher.matches()) {
/* 524 */       return false;
/*     */     }
/* 526 */     Map<String, String> map = getArgumentMap(matcher.group(2));
/* 527 */     String s = matcher.group(1);
/* 528 */     int i = (!"a".equals(s) && !"e".equals(s)) ? 1 : 0;
/* 529 */     return (parseIntWithDefault(map, "c", i) != 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasArguments(String p_82378_0_) {
/* 537 */     return tokenPattern.matcher(p_82378_0_).matches();
/*     */   }
/*     */   
/*     */   private static Map<String, String> getArgumentMap(String argumentString) {
/* 541 */     Map<String, String> map = Maps.newHashMap();
/*     */     
/* 543 */     if (argumentString == null) {
/* 544 */       return map;
/*     */     }
/* 546 */     int i = 0;
/* 547 */     int j = -1;
/*     */     
/* 549 */     for (Matcher matcher = intListPattern.matcher(argumentString); matcher.find(); j = matcher.end()) {
/* 550 */       String s = null;
/*     */       
/* 552 */       switch (i++) {
/*     */         case 0:
/* 554 */           s = "x";
/*     */           break;
/*     */         
/*     */         case 1:
/* 558 */           s = "y";
/*     */           break;
/*     */         
/*     */         case 2:
/* 562 */           s = "z";
/*     */           break;
/*     */         
/*     */         case 3:
/* 566 */           s = "r";
/*     */           break;
/*     */       } 
/* 569 */       if (s != null && matcher.group(1).length() > 0) {
/* 570 */         map.put(s, matcher.group(1));
/*     */       }
/*     */     } 
/*     */     
/* 574 */     if (j < argumentString.length()) {
/* 575 */       Matcher matcher1 = keyValueListPattern.matcher((j == -1) ? argumentString : argumentString.substring(j));
/*     */       
/* 577 */       while (matcher1.find()) {
/* 578 */         map.put(matcher1.group(1), matcher1.group(2));
/*     */       }
/*     */     } 
/*     */     
/* 582 */     return map;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\PlayerSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */