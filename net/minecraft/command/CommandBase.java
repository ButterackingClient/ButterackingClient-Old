/*     */ package net.minecraft.command;
/*     */ 
/*     */ import com.google.common.base.Functions;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.primitives.Doubles;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CommandBase
/*     */   implements ICommand
/*     */ {
/*     */   private static IAdminCommand theAdmin;
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  31 */     return 4;
/*     */   }
/*     */   
/*     */   public List<String> getCommandAliases() {
/*  35 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(ICommandSender sender) {
/*  42 */     return sender.canCommandSenderUseCommand(getRequiredPermissionLevel(), getCommandName());
/*     */   }
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  46 */     return null;
/*     */   }
/*     */   
/*     */   public static int parseInt(String input) throws NumberInvalidException {
/*     */     try {
/*  51 */       return Integer.parseInt(input);
/*  52 */     } catch (NumberFormatException var2) {
/*  53 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int parseInt(String input, int min) throws NumberInvalidException {
/*  58 */     return parseInt(input, min, 2147483647);
/*     */   }
/*     */   
/*     */   public static int parseInt(String input, int min, int max) throws NumberInvalidException {
/*  62 */     int i = parseInt(input);
/*     */     
/*  64 */     if (i < min)
/*  65 */       throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { Integer.valueOf(i), Integer.valueOf(min) }); 
/*  66 */     if (i > max) {
/*  67 */       throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { Integer.valueOf(i), Integer.valueOf(max) });
/*     */     }
/*  69 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static long parseLong(String input) throws NumberInvalidException {
/*     */     try {
/*  75 */       return Long.parseLong(input);
/*  76 */     } catch (NumberFormatException var2) {
/*  77 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static long parseLong(String input, long min, long max) throws NumberInvalidException {
/*  82 */     long i = parseLong(input);
/*     */     
/*  84 */     if (i < min)
/*  85 */       throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[] { Long.valueOf(i), Long.valueOf(min) }); 
/*  86 */     if (i > max) {
/*  87 */       throw new NumberInvalidException("commands.generic.num.tooBig", new Object[] { Long.valueOf(i), Long.valueOf(max) });
/*     */     }
/*  89 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockPos parseBlockPos(ICommandSender sender, String[] args, int startIndex, boolean centerBlock) throws NumberInvalidException {
/*  94 */     BlockPos blockpos = sender.getPosition();
/*  95 */     return new BlockPos(parseDouble(blockpos.getX(), args[startIndex], -30000000, 30000000, centerBlock), parseDouble(blockpos.getY(), args[startIndex + 1], 0, 256, false), parseDouble(blockpos.getZ(), args[startIndex + 2], -30000000, 30000000, centerBlock));
/*     */   }
/*     */   
/*     */   public static double parseDouble(String input) throws NumberInvalidException {
/*     */     try {
/* 100 */       double d0 = Double.parseDouble(input);
/*     */       
/* 102 */       if (!Doubles.isFinite(d0)) {
/* 103 */         throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */       }
/* 105 */       return d0;
/*     */     }
/* 107 */     catch (NumberFormatException var3) {
/* 108 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { input });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static double parseDouble(String input, double min) throws NumberInvalidException {
/* 113 */     return parseDouble(input, min, Double.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public static double parseDouble(String input, double min, double max) throws NumberInvalidException {
/* 117 */     double d0 = parseDouble(input);
/*     */     
/* 119 */     if (d0 < min)
/* 120 */       throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d0), Double.valueOf(min) }); 
/* 121 */     if (d0 > max) {
/* 122 */       throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(d0), Double.valueOf(max) });
/*     */     }
/* 124 */     return d0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean parseBoolean(String input) throws CommandException {
/* 129 */     if (!input.equals("true") && !input.equals("1")) {
/* 130 */       if (!input.equals("false") && !input.equals("0")) {
/* 131 */         throw new CommandException("commands.generic.boolean.invalid", new Object[] { input });
/*     */       }
/* 133 */       return false;
/*     */     } 
/*     */     
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityPlayerMP getCommandSenderAsPlayer(ICommandSender sender) throws PlayerNotFoundException {
/* 144 */     if (sender instanceof EntityPlayerMP) {
/* 145 */       return (EntityPlayerMP)sender;
/*     */     }
/* 147 */     throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public static EntityPlayerMP getPlayer(ICommandSender sender, String username) throws PlayerNotFoundException {
/* 152 */     EntityPlayerMP entityplayermp = PlayerSelector.matchOnePlayer(sender, username);
/*     */     
/* 154 */     if (entityplayermp == null) {
/*     */       try {
/* 156 */         entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUUID(UUID.fromString(username));
/* 157 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 162 */     if (entityplayermp == null) {
/* 163 */       entityplayermp = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(username);
/*     */     }
/*     */     
/* 166 */     if (entityplayermp == null) {
/* 167 */       throw new PlayerNotFoundException();
/*     */     }
/* 169 */     return entityplayermp;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Entity getEntity(ICommandSender p_175768_0_, String p_175768_1_) throws EntityNotFoundException {
/* 174 */     return getEntity(p_175768_0_, p_175768_1_, Entity.class);
/*     */   }
/*     */   public static <T extends Entity> T getEntity(ICommandSender commandSender, String p_175759_1_, Class<? extends T> p_175759_2_) throws EntityNotFoundException {
/*     */     EntityPlayerMP entityPlayerMP;
/* 178 */     Entity entity = PlayerSelector.matchOneEntity(commandSender, p_175759_1_, p_175759_2_);
/* 179 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/* 181 */     if (entity == null) {
/* 182 */       entityPlayerMP = minecraftserver.getConfigurationManager().getPlayerByUsername(p_175759_1_);
/*     */     }
/*     */     
/* 185 */     if (entityPlayerMP == null) {
/*     */       try {
/* 187 */         UUID uuid = UUID.fromString(p_175759_1_);
/* 188 */         Entity entity1 = minecraftserver.getEntityFromUuid(uuid);
/*     */         
/* 190 */         if (entity1 == null) {
/* 191 */           entityPlayerMP = minecraftserver.getConfigurationManager().getPlayerByUUID(uuid);
/*     */         }
/* 193 */       } catch (IllegalArgumentException var6) {
/* 194 */         throw new EntityNotFoundException("commands.generic.entity.invalidUuid", new Object[0]);
/*     */       } 
/*     */     }
/*     */     
/* 198 */     if (entityPlayerMP != null && p_175759_2_.isAssignableFrom(entityPlayerMP.getClass())) {
/* 199 */       return (T)entityPlayerMP;
/*     */     }
/* 201 */     throw new EntityNotFoundException();
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<Entity> func_175763_c(ICommandSender p_175763_0_, String p_175763_1_) throws EntityNotFoundException {
/* 206 */     return PlayerSelector.hasArguments(p_175763_1_) ? PlayerSelector.<Entity>matchEntities(p_175763_0_, p_175763_1_, Entity.class) : Lists.newArrayList((Object[])new Entity[] { getEntity(p_175763_0_, p_175763_1_) });
/*     */   }
/*     */   
/*     */   public static String getPlayerName(ICommandSender sender, String query) throws PlayerNotFoundException {
/*     */     try {
/* 211 */       return getPlayer(sender, query).getName();
/* 212 */     } catch (PlayerNotFoundException playernotfoundexception) {
/* 213 */       if (PlayerSelector.hasArguments(query)) {
/* 214 */         throw playernotfoundexception;
/*     */       }
/* 216 */       return query;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getEntityName(ICommandSender p_175758_0_, String p_175758_1_) throws EntityNotFoundException {
/*     */     try {
/* 227 */       return getPlayer(p_175758_0_, p_175758_1_).getName();
/* 228 */     } catch (PlayerNotFoundException var5) {
/*     */       try {
/* 230 */         return getEntity(p_175758_0_, p_175758_1_).getUniqueID().toString();
/* 231 */       } catch (EntityNotFoundException entitynotfoundexception) {
/* 232 */         if (PlayerSelector.hasArguments(p_175758_1_)) {
/* 233 */           throw entitynotfoundexception;
/*     */         }
/* 235 */         return p_175758_1_;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static IChatComponent getChatComponentFromNthArg(ICommandSender sender, String[] args, int p_147178_2_) throws CommandException, PlayerNotFoundException {
/* 242 */     return getChatComponentFromNthArg(sender, args, p_147178_2_, false);
/*     */   }
/*     */   
/*     */   public static IChatComponent getChatComponentFromNthArg(ICommandSender sender, String[] args, int index, boolean p_147176_3_) throws PlayerNotFoundException {
/* 246 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*     */     
/* 248 */     for (int i = index; i < args.length; i++) {
/* 249 */       IChatComponent iChatComponent; if (i > index) {
/* 250 */         chatComponentText.appendText(" ");
/*     */       }
/*     */       
/* 253 */       ChatComponentText chatComponentText1 = new ChatComponentText(args[i]);
/*     */       
/* 255 */       if (p_147176_3_) {
/* 256 */         IChatComponent ichatcomponent2 = PlayerSelector.matchEntitiesToChatComponent(sender, args[i]);
/*     */         
/* 258 */         if (ichatcomponent2 == null) {
/* 259 */           if (PlayerSelector.hasArguments(args[i])) {
/* 260 */             throw new PlayerNotFoundException();
/*     */           }
/*     */         } else {
/* 263 */           iChatComponent = ichatcomponent2;
/*     */         } 
/*     */       } 
/*     */       
/* 267 */       chatComponentText.appendSibling(iChatComponent);
/*     */     } 
/*     */     
/* 270 */     return (IChatComponent)chatComponentText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String buildString(String[] args, int startPos) {
/* 277 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 279 */     for (int i = startPos; i < args.length; i++) {
/* 280 */       if (i > startPos) {
/* 281 */         stringbuilder.append(" ");
/*     */       }
/*     */       
/* 284 */       String s = args[i];
/* 285 */       stringbuilder.append(s);
/*     */     } 
/*     */     
/* 288 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   public static CoordinateArg parseCoordinate(double base, String p_175770_2_, boolean centerBlock) throws NumberInvalidException {
/* 292 */     return parseCoordinate(base, p_175770_2_, -30000000, 30000000, centerBlock);
/*     */   }
/*     */   
/*     */   public static CoordinateArg parseCoordinate(double p_175767_0_, String p_175767_2_, int min, int max, boolean centerBlock) throws NumberInvalidException {
/* 296 */     boolean flag = p_175767_2_.startsWith("~");
/*     */     
/* 298 */     if (flag && Double.isNaN(p_175767_0_)) {
/* 299 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { Double.valueOf(p_175767_0_) });
/*     */     }
/* 301 */     double d0 = 0.0D;
/*     */     
/* 303 */     if (!flag || p_175767_2_.length() > 1) {
/* 304 */       boolean flag1 = p_175767_2_.contains(".");
/*     */       
/* 306 */       if (flag) {
/* 307 */         p_175767_2_ = p_175767_2_.substring(1);
/*     */       }
/*     */       
/* 310 */       d0 += parseDouble(p_175767_2_);
/*     */       
/* 312 */       if (!flag1 && !flag && centerBlock) {
/* 313 */         d0 += 0.5D;
/*     */       }
/*     */     } 
/*     */     
/* 317 */     if (min != 0 || max != 0) {
/* 318 */       if (d0 < min) {
/* 319 */         throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d0), Integer.valueOf(min) });
/*     */       }
/*     */       
/* 322 */       if (d0 > max) {
/* 323 */         throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(d0), Integer.valueOf(max) });
/*     */       }
/*     */     } 
/*     */     
/* 327 */     return new CoordinateArg(d0 + (flag ? p_175767_0_ : 0.0D), d0, flag);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double parseDouble(double base, String input, boolean centerBlock) throws NumberInvalidException {
/* 332 */     return parseDouble(base, input, -30000000, 30000000, centerBlock);
/*     */   }
/*     */   
/*     */   public static double parseDouble(double base, String input, int min, int max, boolean centerBlock) throws NumberInvalidException {
/* 336 */     boolean flag = input.startsWith("~");
/*     */     
/* 338 */     if (flag && Double.isNaN(base)) {
/* 339 */       throw new NumberInvalidException("commands.generic.num.invalid", new Object[] { Double.valueOf(base) });
/*     */     }
/* 341 */     double d0 = flag ? base : 0.0D;
/*     */     
/* 343 */     if (!flag || input.length() > 1) {
/* 344 */       boolean flag1 = input.contains(".");
/*     */       
/* 346 */       if (flag) {
/* 347 */         input = input.substring(1);
/*     */       }
/*     */       
/* 350 */       d0 += parseDouble(input);
/*     */       
/* 352 */       if (!flag1 && !flag && centerBlock) {
/* 353 */         d0 += 0.5D;
/*     */       }
/*     */     } 
/*     */     
/* 357 */     if (min != 0 || max != 0) {
/* 358 */       if (d0 < min) {
/* 359 */         throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d0), Integer.valueOf(min) });
/*     */       }
/*     */       
/* 362 */       if (d0 > max) {
/* 363 */         throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(d0), Integer.valueOf(max) });
/*     */       }
/*     */     } 
/*     */     
/* 367 */     return d0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Item getItemByText(ICommandSender sender, String id) throws NumberInvalidException {
/* 377 */     ResourceLocation resourcelocation = new ResourceLocation(id);
/* 378 */     Item item = (Item)Item.itemRegistry.getObject(resourcelocation);
/*     */     
/* 380 */     if (item == null) {
/* 381 */       throw new NumberInvalidException("commands.give.item.notFound", new Object[] { resourcelocation });
/*     */     }
/* 383 */     return item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Block getBlockByText(ICommandSender sender, String id) throws NumberInvalidException {
/* 393 */     ResourceLocation resourcelocation = new ResourceLocation(id);
/*     */     
/* 395 */     if (!Block.blockRegistry.containsKey(resourcelocation)) {
/* 396 */       throw new NumberInvalidException("commands.give.block.notFound", new Object[] { resourcelocation });
/*     */     }
/* 398 */     Block block = (Block)Block.blockRegistry.getObject(resourcelocation);
/*     */     
/* 400 */     if (block == null) {
/* 401 */       throw new NumberInvalidException("commands.give.block.notFound", new Object[] { resourcelocation });
/*     */     }
/* 403 */     return block;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String joinNiceString(Object[] elements) {
/* 413 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 415 */     for (int i = 0; i < elements.length; i++) {
/* 416 */       String s = elements[i].toString();
/*     */       
/* 418 */       if (i > 0) {
/* 419 */         if (i == elements.length - 1) {
/* 420 */           stringbuilder.append(" and ");
/*     */         } else {
/* 422 */           stringbuilder.append(", ");
/*     */         } 
/*     */       }
/*     */       
/* 426 */       stringbuilder.append(s);
/*     */     } 
/*     */     
/* 429 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   public static IChatComponent join(List<IChatComponent> components) {
/* 433 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*     */     
/* 435 */     for (int i = 0; i < components.size(); i++) {
/* 436 */       if (i > 0) {
/* 437 */         if (i == components.size() - 1) {
/* 438 */           chatComponentText.appendText(" and ");
/* 439 */         } else if (i > 0) {
/* 440 */           chatComponentText.appendText(", ");
/*     */         } 
/*     */       }
/*     */       
/* 444 */       chatComponentText.appendSibling(components.get(i));
/*     */     } 
/*     */     
/* 447 */     return (IChatComponent)chatComponentText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String joinNiceStringFromCollection(Collection<String> strings) {
/* 456 */     return joinNiceString(strings.toArray((Object[])new String[strings.size()]));
/*     */   }
/*     */   public static List<String> func_175771_a(String[] p_175771_0_, int p_175771_1_, BlockPos p_175771_2_) {
/*     */     String s;
/* 460 */     if (p_175771_2_ == null) {
/* 461 */       return null;
/*     */     }
/* 463 */     int i = p_175771_0_.length - 1;
/*     */ 
/*     */     
/* 466 */     if (i == p_175771_1_) {
/* 467 */       s = Integer.toString(p_175771_2_.getX());
/* 468 */     } else if (i == p_175771_1_ + 1) {
/* 469 */       s = Integer.toString(p_175771_2_.getY());
/*     */     } else {
/* 471 */       if (i != p_175771_1_ + 2) {
/* 472 */         return null;
/*     */       }
/*     */       
/* 475 */       s = Integer.toString(p_175771_2_.getZ());
/*     */     } 
/*     */     
/* 478 */     return Lists.newArrayList((Object[])new String[] { s });
/*     */   }
/*     */   
/*     */   public static List<String> func_181043_b(String[] p_181043_0_, int p_181043_1_, BlockPos p_181043_2_) {
/*     */     String s;
/* 483 */     if (p_181043_2_ == null) {
/* 484 */       return null;
/*     */     }
/* 486 */     int i = p_181043_0_.length - 1;
/*     */ 
/*     */     
/* 489 */     if (i == p_181043_1_) {
/* 490 */       s = Integer.toString(p_181043_2_.getX());
/*     */     } else {
/* 492 */       if (i != p_181043_1_ + 1) {
/* 493 */         return null;
/*     */       }
/*     */       
/* 496 */       s = Integer.toString(p_181043_2_.getZ());
/*     */     } 
/*     */     
/* 499 */     return Lists.newArrayList((Object[])new String[] { s });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean doesStringStartWith(String original, String region) {
/* 507 */     return region.regionMatches(true, 0, original, 0, original.length());
/*     */   }
/*     */   
/*     */   public static List<String> getListOfStringsMatchingLastWord(String[] args, String... possibilities) {
/* 511 */     return getListOfStringsMatchingLastWord(args, Arrays.asList((Object[])possibilities));
/*     */   }
/*     */   
/*     */   public static List<String> getListOfStringsMatchingLastWord(String[] p_175762_0_, Collection<?> p_175762_1_) {
/* 515 */     String s = p_175762_0_[p_175762_0_.length - 1];
/* 516 */     List<String> list = Lists.newArrayList();
/*     */     
/* 518 */     if (!p_175762_1_.isEmpty()) {
/* 519 */       for (String s1 : Iterables.transform(p_175762_1_, Functions.toStringFunction())) {
/* 520 */         if (doesStringStartWith(s, s1)) {
/* 521 */           list.add(s1);
/*     */         }
/*     */       } 
/*     */       
/* 525 */       if (list.isEmpty()) {
/* 526 */         for (Object object : p_175762_1_) {
/* 527 */           if (object instanceof ResourceLocation && doesStringStartWith(s, ((ResourceLocation)object).getResourcePath())) {
/* 528 */             list.add(String.valueOf(object));
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 534 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 541 */     return false;
/*     */   }
/*     */   
/*     */   public static void notifyOperators(ICommandSender sender, ICommand command, String msgFormat, Object... msgParams) {
/* 545 */     notifyOperators(sender, command, 0, msgFormat, msgParams);
/*     */   }
/*     */   
/*     */   public static void notifyOperators(ICommandSender sender, ICommand command, int p_152374_2_, String msgFormat, Object... msgParams) {
/* 549 */     if (theAdmin != null) {
/* 550 */       theAdmin.notifyOperators(sender, command, p_152374_2_, msgFormat, msgParams);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setAdminCommander(IAdminCommand command) {
/* 558 */     theAdmin = command;
/*     */   }
/*     */   
/*     */   public int compareTo(ICommand p_compareTo_1_) {
/* 562 */     return getCommandName().compareTo(p_compareTo_1_.getCommandName());
/*     */   }
/*     */   
/*     */   public static class CoordinateArg {
/*     */     private final double field_179633_a;
/*     */     private final double field_179631_b;
/*     */     private final boolean field_179632_c;
/*     */     
/*     */     protected CoordinateArg(double p_i46051_1_, double p_i46051_3_, boolean p_i46051_5_) {
/* 571 */       this.field_179633_a = p_i46051_1_;
/* 572 */       this.field_179631_b = p_i46051_3_;
/* 573 */       this.field_179632_c = p_i46051_5_;
/*     */     }
/*     */     
/*     */     public double func_179628_a() {
/* 577 */       return this.field_179633_a;
/*     */     }
/*     */     
/*     */     public double func_179629_b() {
/* 581 */       return this.field_179631_b;
/*     */     }
/*     */     
/*     */     public boolean func_179630_c() {
/* 585 */       return this.field_179632_c;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */