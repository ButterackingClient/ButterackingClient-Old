/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.File;
/*     */ import java.net.SocketAddress;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.network.NetworkManager;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*     */ import net.minecraft.network.play.server.S02PacketChat;
/*     */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*     */ import net.minecraft.network.play.server.S05PacketSpawnPosition;
/*     */ import net.minecraft.network.play.server.S07PacketRespawn;
/*     */ import net.minecraft.network.play.server.S09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*     */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*     */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*     */ import net.minecraft.network.play.server.S3EPacketTeams;
/*     */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/*     */ import net.minecraft.network.play.server.S41PacketServerDifficulty;
/*     */ import net.minecraft.network.play.server.S44PacketWorldBorder;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.ServerScoreboard;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.stats.StatisticsFile;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.border.IBorderListener;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ import net.minecraft.world.demo.DemoWorldManager;
/*     */ import net.minecraft.world.storage.IPlayerFileData;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public abstract class ServerConfigurationManager
/*     */ {
/*  66 */   public static final File FILE_PLAYERBANS = new File("banned-players.json");
/*  67 */   public static final File FILE_IPBANS = new File("banned-ips.json");
/*  68 */   public static final File FILE_OPS = new File("ops.json");
/*  69 */   public static final File FILE_WHITELIST = new File("whitelist.json");
/*  70 */   private static final Logger logger = LogManager.getLogger();
/*  71 */   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
/*     */ 
/*     */   
/*     */   private final MinecraftServer mcServer;
/*     */ 
/*     */   
/*  77 */   private final List<EntityPlayerMP> playerEntityList = Lists.newArrayList();
/*  78 */   private final Map<UUID, EntityPlayerMP> uuidToPlayerMap = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   private final UserListBans bannedPlayers;
/*     */ 
/*     */ 
/*     */   
/*     */   private final BanList bannedIPs;
/*     */ 
/*     */   
/*     */   private final UserListOps ops;
/*     */ 
/*     */   
/*     */   private final UserListWhitelist whiteListedPlayers;
/*     */ 
/*     */   
/*     */   private final Map<UUID, StatisticsFile> playerStatFiles;
/*     */ 
/*     */   
/*     */   private IPlayerFileData playerNBTManagerObj;
/*     */ 
/*     */   
/*     */   private boolean whiteListEnforced;
/*     */ 
/*     */   
/*     */   protected int maxPlayers;
/*     */ 
/*     */   
/*     */   private int viewDistance;
/*     */ 
/*     */   
/*     */   private WorldSettings.GameType gameType;
/*     */ 
/*     */   
/*     */   private boolean commandsAllowedForAll;
/*     */ 
/*     */   
/*     */   private int playerPingIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerConfigurationManager(MinecraftServer server) {
/* 121 */     this.bannedPlayers = new UserListBans(FILE_PLAYERBANS);
/* 122 */     this.bannedIPs = new BanList(FILE_IPBANS);
/* 123 */     this.ops = new UserListOps(FILE_OPS);
/* 124 */     this.whiteListedPlayers = new UserListWhitelist(FILE_WHITELIST);
/* 125 */     this.playerStatFiles = Maps.newHashMap();
/* 126 */     this.mcServer = server;
/* 127 */     this.bannedPlayers.setLanServer(false);
/* 128 */     this.bannedIPs.setLanServer(false);
/* 129 */     this.maxPlayers = 8;
/*     */   }
/*     */   public void initializeConnectionToPlayer(NetworkManager netManager, EntityPlayerMP playerIn) {
/*     */     ChatComponentTranslation chatcomponenttranslation;
/* 133 */     GameProfile gameprofile = playerIn.getGameProfile();
/* 134 */     PlayerProfileCache playerprofilecache = this.mcServer.getPlayerProfileCache();
/* 135 */     GameProfile gameprofile1 = playerprofilecache.getProfileByUUID(gameprofile.getId());
/* 136 */     String s = (gameprofile1 == null) ? gameprofile.getName() : gameprofile1.getName();
/* 137 */     playerprofilecache.addEntry(gameprofile);
/* 138 */     NBTTagCompound nbttagcompound = readPlayerDataFromFile(playerIn);
/* 139 */     playerIn.setWorld((World)this.mcServer.worldServerForDimension(playerIn.dimension));
/* 140 */     playerIn.theItemInWorldManager.setWorld((WorldServer)playerIn.worldObj);
/* 141 */     String s1 = "local";
/*     */     
/* 143 */     if (netManager.getRemoteAddress() != null) {
/* 144 */       s1 = netManager.getRemoteAddress().toString();
/*     */     }
/*     */     
/* 147 */     logger.info(String.valueOf(playerIn.getName()) + "[" + s1 + "] logged in with entity id " + playerIn.getEntityId() + " at (" + playerIn.posX + ", " + playerIn.posY + ", " + playerIn.posZ + ")");
/* 148 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/* 149 */     WorldInfo worldinfo = worldserver.getWorldInfo();
/* 150 */     BlockPos blockpos = worldserver.getSpawnPoint();
/* 151 */     setPlayerGameTypeBasedOnOther(playerIn, null, (World)worldserver);
/* 152 */     NetHandlerPlayServer nethandlerplayserver = new NetHandlerPlayServer(this.mcServer, netManager, playerIn);
/* 153 */     nethandlerplayserver.sendPacket((Packet)new S01PacketJoinGame(playerIn.getEntityId(), playerIn.theItemInWorldManager.getGameType(), worldinfo.isHardcoreModeEnabled(), worldserver.provider.getDimensionId(), worldserver.getDifficulty(), getMaxPlayers(), worldinfo.getTerrainType(), worldserver.getGameRules().getBoolean("reducedDebugInfo")));
/* 154 */     nethandlerplayserver.sendPacket((Packet)new S3FPacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(getServerInstance().getServerModName())));
/* 155 */     nethandlerplayserver.sendPacket((Packet)new S41PacketServerDifficulty(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
/* 156 */     nethandlerplayserver.sendPacket((Packet)new S05PacketSpawnPosition(blockpos));
/* 157 */     nethandlerplayserver.sendPacket((Packet)new S39PacketPlayerAbilities(playerIn.capabilities));
/* 158 */     nethandlerplayserver.sendPacket((Packet)new S09PacketHeldItemChange(playerIn.inventory.currentItem));
/* 159 */     playerIn.getStatFile().func_150877_d();
/* 160 */     playerIn.getStatFile().sendAchievements(playerIn);
/* 161 */     sendScoreboard((ServerScoreboard)worldserver.getScoreboard(), playerIn);
/* 162 */     this.mcServer.refreshStatusNextTick();
/*     */ 
/*     */     
/* 165 */     if (!playerIn.getName().equalsIgnoreCase(s)) {
/* 166 */       chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.joined.renamed", new Object[] { playerIn.getDisplayName(), s });
/*     */     } else {
/* 168 */       chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.joined", new Object[] { playerIn.getDisplayName() });
/*     */     } 
/*     */     
/* 171 */     chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
/* 172 */     sendChatMsg((IChatComponent)chatcomponenttranslation);
/* 173 */     playerLoggedIn(playerIn);
/* 174 */     nethandlerplayserver.setPlayerLocation(playerIn.posX, playerIn.posY, playerIn.posZ, playerIn.rotationYaw, playerIn.rotationPitch);
/* 175 */     updateTimeAndWeatherForPlayer(playerIn, worldserver);
/*     */     
/* 177 */     if (this.mcServer.getResourcePackUrl().length() > 0) {
/* 178 */       playerIn.loadResourcePack(this.mcServer.getResourcePackUrl(), this.mcServer.getResourcePackHash());
/*     */     }
/*     */     
/* 181 */     for (PotionEffect potioneffect : playerIn.getActivePotionEffects()) {
/* 182 */       nethandlerplayserver.sendPacket((Packet)new S1DPacketEntityEffect(playerIn.getEntityId(), potioneffect));
/*     */     }
/*     */     
/* 185 */     playerIn.addSelfToInternalCraftingInventory();
/*     */     
/* 187 */     if (nbttagcompound != null && nbttagcompound.hasKey("Riding", 10)) {
/* 188 */       Entity entity = EntityList.createEntityFromNBT(nbttagcompound.getCompoundTag("Riding"), (World)worldserver);
/*     */       
/* 190 */       if (entity != null) {
/* 191 */         entity.forceSpawn = true;
/* 192 */         worldserver.spawnEntityInWorld(entity);
/* 193 */         playerIn.mountEntity(entity);
/* 194 */         entity.forceSpawn = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void sendScoreboard(ServerScoreboard scoreboardIn, EntityPlayerMP playerIn) {
/* 200 */     Set<ScoreObjective> set = Sets.newHashSet();
/*     */     
/* 202 */     for (ScorePlayerTeam scoreplayerteam : scoreboardIn.getTeams()) {
/* 203 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S3EPacketTeams(scoreplayerteam, 0));
/*     */     }
/*     */     
/* 206 */     for (int i = 0; i < 19; i++) {
/* 207 */       ScoreObjective scoreobjective = scoreboardIn.getObjectiveInDisplaySlot(i);
/*     */       
/* 209 */       if (scoreobjective != null && !set.contains(scoreobjective)) {
/* 210 */         for (Packet packet : scoreboardIn.func_96550_d(scoreobjective)) {
/* 211 */           playerIn.playerNetServerHandler.sendPacket(packet);
/*     */         }
/*     */         
/* 214 */         set.add(scoreobjective);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerManager(WorldServer[] worldServers) {
/* 223 */     this.playerNBTManagerObj = worldServers[0].getSaveHandler().getPlayerNBTManager();
/* 224 */     worldServers[0].getWorldBorder().addListener(new IBorderListener() {
/*     */           public void onSizeChanged(WorldBorder border, double newSize) {
/* 226 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_SIZE));
/*     */           }
/*     */           
/*     */           public void onTransitionStarted(WorldBorder border, double oldSize, double newSize, long time) {
/* 230 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.LERP_SIZE));
/*     */           }
/*     */           
/*     */           public void onCenterChanged(WorldBorder border, double x, double z) {
/* 234 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_CENTER));
/*     */           }
/*     */           
/*     */           public void onWarningTimeChanged(WorldBorder border, int newTime) {
/* 238 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_TIME));
/*     */           }
/*     */           
/*     */           public void onWarningDistanceChanged(WorldBorder border, int newDistance) {
/* 242 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS));
/*     */           }
/*     */ 
/*     */           
/*     */           public void onDamageAmountChanged(WorldBorder border, double newAmount) {}
/*     */ 
/*     */           
/*     */           public void onDamageBufferChanged(WorldBorder border, double newSize) {}
/*     */         });
/*     */   }
/*     */   
/*     */   public void preparePlayer(EntityPlayerMP playerIn, WorldServer worldIn) {
/* 254 */     WorldServer worldserver = playerIn.getServerForPlayer();
/*     */     
/* 256 */     if (worldIn != null) {
/* 257 */       worldIn.getPlayerManager().removePlayer(playerIn);
/*     */     }
/*     */     
/* 260 */     worldserver.getPlayerManager().addPlayer(playerIn);
/* 261 */     worldserver.theChunkProviderServer.loadChunk((int)playerIn.posX >> 4, (int)playerIn.posZ >> 4);
/*     */   }
/*     */   
/*     */   public int getEntityViewDistance() {
/* 265 */     return PlayerManager.getFurthestViewableBlock(getViewDistance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound readPlayerDataFromFile(EntityPlayerMP playerIn) {
/* 272 */     NBTTagCompound nbttagcompound1, nbttagcompound = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
/*     */ 
/*     */     
/* 275 */     if (playerIn.getName().equals(this.mcServer.getServerOwner()) && nbttagcompound != null) {
/* 276 */       playerIn.readFromNBT(nbttagcompound);
/* 277 */       nbttagcompound1 = nbttagcompound;
/* 278 */       logger.debug("loading single player");
/*     */     } else {
/* 280 */       nbttagcompound1 = this.playerNBTManagerObj.readPlayerData((EntityPlayer)playerIn);
/*     */     } 
/*     */     
/* 283 */     return nbttagcompound1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writePlayerData(EntityPlayerMP playerIn) {
/* 290 */     this.playerNBTManagerObj.writePlayerData((EntityPlayer)playerIn);
/* 291 */     StatisticsFile statisticsfile = this.playerStatFiles.get(playerIn.getUniqueID());
/*     */     
/* 293 */     if (statisticsfile != null) {
/* 294 */       statisticsfile.saveStatFile();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playerLoggedIn(EntityPlayerMP playerIn) {
/* 302 */     this.playerEntityList.add(playerIn);
/* 303 */     this.uuidToPlayerMap.put(playerIn.getUniqueID(), playerIn);
/* 304 */     sendPacketToAllPlayers((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { playerIn }));
/* 305 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/* 306 */     worldserver.spawnEntityInWorld((Entity)playerIn);
/* 307 */     preparePlayer(playerIn, null);
/*     */     
/* 309 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/* 310 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/* 311 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { entityplayermp }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serverUpdateMountedMovingPlayer(EntityPlayerMP playerIn) {
/* 319 */     playerIn.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void playerLoggedOut(EntityPlayerMP playerIn) {
/* 326 */     playerIn.triggerAchievement(StatList.leaveGameStat);
/* 327 */     writePlayerData(playerIn);
/* 328 */     WorldServer worldserver = playerIn.getServerForPlayer();
/*     */     
/* 330 */     if (playerIn.ridingEntity != null) {
/* 331 */       worldserver.removePlayerEntityDangerously(playerIn.ridingEntity);
/* 332 */       logger.debug("removing player mount");
/*     */     } 
/*     */     
/* 335 */     worldserver.removeEntity((Entity)playerIn);
/* 336 */     worldserver.getPlayerManager().removePlayer(playerIn);
/* 337 */     this.playerEntityList.remove(playerIn);
/* 338 */     UUID uuid = playerIn.getUniqueID();
/* 339 */     EntityPlayerMP entityplayermp = this.uuidToPlayerMap.get(uuid);
/*     */     
/* 341 */     if (entityplayermp == playerIn) {
/* 342 */       this.uuidToPlayerMap.remove(uuid);
/* 343 */       this.playerStatFiles.remove(uuid);
/*     */     } 
/*     */     
/* 346 */     sendPacketToAllPlayers((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.REMOVE_PLAYER, new EntityPlayerMP[] { playerIn }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String allowUserToConnect(SocketAddress address, GameProfile profile) {
/* 353 */     if (this.bannedPlayers.isBanned(profile)) {
/* 354 */       UserListBansEntry userlistbansentry = this.bannedPlayers.getEntry(profile);
/* 355 */       String s1 = "You are banned from this server!\nReason: " + userlistbansentry.getBanReason();
/*     */       
/* 357 */       if (userlistbansentry.getBanEndDate() != null) {
/* 358 */         s1 = String.valueOf(s1) + "\nYour ban will be removed on " + dateFormat.format(userlistbansentry.getBanEndDate());
/*     */       }
/*     */       
/* 361 */       return s1;
/* 362 */     }  if (!canJoin(profile))
/* 363 */       return "You are not white-listed on this server!"; 
/* 364 */     if (this.bannedIPs.isBanned(address)) {
/* 365 */       IPBanEntry ipbanentry = this.bannedIPs.getBanEntry(address);
/* 366 */       String s = "Your IP address is banned from this server!\nReason: " + ipbanentry.getBanReason();
/*     */       
/* 368 */       if (ipbanentry.getBanEndDate() != null) {
/* 369 */         s = String.valueOf(s) + "\nYour ban will be removed on " + dateFormat.format(ipbanentry.getBanEndDate());
/*     */       }
/*     */       
/* 372 */       return s;
/*     */     } 
/* 374 */     return (this.playerEntityList.size() >= this.maxPlayers && !bypassesPlayerLimit(profile)) ? "The server is full!" : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayerMP createPlayerForUser(GameProfile profile) {
/*     */     ItemInWorldManager iteminworldmanager;
/* 382 */     UUID uuid = EntityPlayer.getUUID(profile);
/* 383 */     List<EntityPlayerMP> list = Lists.newArrayList();
/*     */     
/* 385 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/* 386 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*     */       
/* 388 */       if (entityplayermp.getUniqueID().equals(uuid)) {
/* 389 */         list.add(entityplayermp);
/*     */       }
/*     */     } 
/*     */     
/* 393 */     EntityPlayerMP entityplayermp2 = this.uuidToPlayerMap.get(profile.getId());
/*     */     
/* 395 */     if (entityplayermp2 != null && !list.contains(entityplayermp2)) {
/* 396 */       list.add(entityplayermp2);
/*     */     }
/*     */     
/* 399 */     for (EntityPlayerMP entityplayermp1 : list) {
/* 400 */       entityplayermp1.playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 405 */     if (this.mcServer.isDemo()) {
/* 406 */       DemoWorldManager demoWorldManager = new DemoWorldManager((World)this.mcServer.worldServerForDimension(0));
/*     */     } else {
/* 408 */       iteminworldmanager = new ItemInWorldManager((World)this.mcServer.worldServerForDimension(0));
/*     */     } 
/*     */     
/* 411 */     return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), profile, iteminworldmanager);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayerMP recreatePlayerEntity(EntityPlayerMP playerIn, int dimension, boolean conqueredEnd) {
/*     */     ItemInWorldManager iteminworldmanager;
/* 418 */     playerIn.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(playerIn);
/* 419 */     playerIn.getServerForPlayer().getEntityTracker().untrackEntity((Entity)playerIn);
/* 420 */     playerIn.getServerForPlayer().getPlayerManager().removePlayer(playerIn);
/* 421 */     this.playerEntityList.remove(playerIn);
/* 422 */     this.mcServer.worldServerForDimension(playerIn.dimension).removePlayerEntityDangerously((Entity)playerIn);
/* 423 */     BlockPos blockpos = playerIn.getBedLocation();
/* 424 */     boolean flag = playerIn.isSpawnForced();
/* 425 */     playerIn.dimension = dimension;
/*     */ 
/*     */     
/* 428 */     if (this.mcServer.isDemo()) {
/* 429 */       DemoWorldManager demoWorldManager = new DemoWorldManager((World)this.mcServer.worldServerForDimension(playerIn.dimension));
/*     */     } else {
/* 431 */       iteminworldmanager = new ItemInWorldManager((World)this.mcServer.worldServerForDimension(playerIn.dimension));
/*     */     } 
/*     */     
/* 434 */     EntityPlayerMP entityplayermp = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(playerIn.dimension), playerIn.getGameProfile(), iteminworldmanager);
/* 435 */     entityplayermp.playerNetServerHandler = playerIn.playerNetServerHandler;
/* 436 */     entityplayermp.clonePlayer((EntityPlayer)playerIn, conqueredEnd);
/* 437 */     entityplayermp.setEntityId(playerIn.getEntityId());
/* 438 */     entityplayermp.setCommandStats((Entity)playerIn);
/* 439 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/* 440 */     setPlayerGameTypeBasedOnOther(entityplayermp, playerIn, (World)worldserver);
/*     */     
/* 442 */     if (blockpos != null) {
/* 443 */       BlockPos blockpos1 = EntityPlayer.getBedSpawnLocation((World)this.mcServer.worldServerForDimension(playerIn.dimension), blockpos, flag);
/*     */       
/* 445 */       if (blockpos1 != null) {
/* 446 */         entityplayermp.setLocationAndAngles((blockpos1.getX() + 0.5F), (blockpos1.getY() + 0.1F), (blockpos1.getZ() + 0.5F), 0.0F, 0.0F);
/* 447 */         entityplayermp.setSpawnPoint(blockpos, flag);
/*     */       } else {
/* 449 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(0, 0.0F));
/*     */       } 
/*     */     } 
/*     */     
/* 453 */     worldserver.theChunkProviderServer.loadChunk((int)entityplayermp.posX >> 4, (int)entityplayermp.posZ >> 4);
/*     */     
/* 455 */     while (!worldserver.getCollidingBoundingBoxes((Entity)entityplayermp, entityplayermp.getEntityBoundingBox()).isEmpty() && entityplayermp.posY < 256.0D) {
/* 456 */       entityplayermp.setPosition(entityplayermp.posX, entityplayermp.posY + 1.0D, entityplayermp.posZ);
/*     */     }
/*     */     
/* 459 */     entityplayermp.playerNetServerHandler.sendPacket((Packet)new S07PacketRespawn(entityplayermp.dimension, entityplayermp.worldObj.getDifficulty(), entityplayermp.worldObj.getWorldInfo().getTerrainType(), entityplayermp.theItemInWorldManager.getGameType()));
/* 460 */     BlockPos blockpos2 = worldserver.getSpawnPoint();
/* 461 */     entityplayermp.playerNetServerHandler.setPlayerLocation(entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
/* 462 */     entityplayermp.playerNetServerHandler.sendPacket((Packet)new S05PacketSpawnPosition(blockpos2));
/* 463 */     entityplayermp.playerNetServerHandler.sendPacket((Packet)new S1FPacketSetExperience(entityplayermp.experience, entityplayermp.experienceTotal, entityplayermp.experienceLevel));
/* 464 */     updateTimeAndWeatherForPlayer(entityplayermp, worldserver);
/* 465 */     worldserver.getPlayerManager().addPlayer(entityplayermp);
/* 466 */     worldserver.spawnEntityInWorld((Entity)entityplayermp);
/* 467 */     this.playerEntityList.add(entityplayermp);
/* 468 */     this.uuidToPlayerMap.put(entityplayermp.getUniqueID(), entityplayermp);
/* 469 */     entityplayermp.addSelfToInternalCraftingInventory();
/* 470 */     entityplayermp.setHealth(entityplayermp.getHealth());
/* 471 */     return entityplayermp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transferPlayerToDimension(EntityPlayerMP playerIn, int dimension) {
/* 478 */     int i = playerIn.dimension;
/* 479 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/* 480 */     playerIn.dimension = dimension;
/* 481 */     WorldServer worldserver1 = this.mcServer.worldServerForDimension(playerIn.dimension);
/* 482 */     playerIn.playerNetServerHandler.sendPacket((Packet)new S07PacketRespawn(playerIn.dimension, playerIn.worldObj.getDifficulty(), playerIn.worldObj.getWorldInfo().getTerrainType(), playerIn.theItemInWorldManager.getGameType()));
/* 483 */     worldserver.removePlayerEntityDangerously((Entity)playerIn);
/* 484 */     playerIn.isDead = false;
/* 485 */     transferEntityToWorld((Entity)playerIn, i, worldserver, worldserver1);
/* 486 */     preparePlayer(playerIn, worldserver);
/* 487 */     playerIn.playerNetServerHandler.setPlayerLocation(playerIn.posX, playerIn.posY, playerIn.posZ, playerIn.rotationYaw, playerIn.rotationPitch);
/* 488 */     playerIn.theItemInWorldManager.setWorld(worldserver1);
/* 489 */     updateTimeAndWeatherForPlayer(playerIn, worldserver1);
/* 490 */     syncPlayerInventory(playerIn);
/*     */     
/* 492 */     for (PotionEffect potioneffect : playerIn.getActivePotionEffects()) {
/* 493 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(playerIn.getEntityId(), potioneffect));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transferEntityToWorld(Entity entityIn, int p_82448_2_, WorldServer oldWorldIn, WorldServer toWorldIn) {
/* 504 */     double d0 = entityIn.posX;
/* 505 */     double d1 = entityIn.posZ;
/* 506 */     double d2 = 8.0D;
/* 507 */     float f = entityIn.rotationYaw;
/* 508 */     oldWorldIn.theProfiler.startSection("moving");
/*     */     
/* 510 */     if (entityIn.dimension == -1) {
/* 511 */       d0 = MathHelper.clamp_double(d0 / d2, toWorldIn.getWorldBorder().minX() + 16.0D, toWorldIn.getWorldBorder().maxX() - 16.0D);
/* 512 */       d1 = MathHelper.clamp_double(d1 / d2, toWorldIn.getWorldBorder().minZ() + 16.0D, toWorldIn.getWorldBorder().maxZ() - 16.0D);
/* 513 */       entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
/*     */       
/* 515 */       if (entityIn.isEntityAlive()) {
/* 516 */         oldWorldIn.updateEntityWithOptionalForce(entityIn, false);
/*     */       }
/* 518 */     } else if (entityIn.dimension == 0) {
/* 519 */       d0 = MathHelper.clamp_double(d0 * d2, toWorldIn.getWorldBorder().minX() + 16.0D, toWorldIn.getWorldBorder().maxX() - 16.0D);
/* 520 */       d1 = MathHelper.clamp_double(d1 * d2, toWorldIn.getWorldBorder().minZ() + 16.0D, toWorldIn.getWorldBorder().maxZ() - 16.0D);
/* 521 */       entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
/*     */       
/* 523 */       if (entityIn.isEntityAlive()) {
/* 524 */         oldWorldIn.updateEntityWithOptionalForce(entityIn, false);
/*     */       }
/*     */     } else {
/*     */       BlockPos blockpos;
/*     */       
/* 529 */       if (p_82448_2_ == 1) {
/* 530 */         blockpos = toWorldIn.getSpawnPoint();
/*     */       } else {
/* 532 */         blockpos = toWorldIn.getSpawnCoordinate();
/*     */       } 
/*     */       
/* 535 */       d0 = blockpos.getX();
/* 536 */       entityIn.posY = blockpos.getY();
/* 537 */       d1 = blockpos.getZ();
/* 538 */       entityIn.setLocationAndAngles(d0, entityIn.posY, d1, 90.0F, 0.0F);
/*     */       
/* 540 */       if (entityIn.isEntityAlive()) {
/* 541 */         oldWorldIn.updateEntityWithOptionalForce(entityIn, false);
/*     */       }
/*     */     } 
/*     */     
/* 545 */     oldWorldIn.theProfiler.endSection();
/*     */     
/* 547 */     if (p_82448_2_ != 1) {
/* 548 */       oldWorldIn.theProfiler.startSection("placing");
/* 549 */       d0 = MathHelper.clamp_int((int)d0, -29999872, 29999872);
/* 550 */       d1 = MathHelper.clamp_int((int)d1, -29999872, 29999872);
/*     */       
/* 552 */       if (entityIn.isEntityAlive()) {
/* 553 */         entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
/* 554 */         toWorldIn.getDefaultTeleporter().placeInPortal(entityIn, f);
/* 555 */         toWorldIn.spawnEntityInWorld(entityIn);
/* 556 */         toWorldIn.updateEntityWithOptionalForce(entityIn, false);
/*     */       } 
/*     */       
/* 559 */       oldWorldIn.theProfiler.endSection();
/*     */     } 
/*     */     
/* 562 */     entityIn.setWorld((World)toWorldIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 569 */     if (++this.playerPingIndex > 600) {
/* 570 */       sendPacketToAllPlayers((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_LATENCY, this.playerEntityList));
/* 571 */       this.playerPingIndex = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendPacketToAllPlayers(Packet packetIn) {
/* 576 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/* 577 */       ((EntityPlayerMP)this.playerEntityList.get(i)).playerNetServerHandler.sendPacket(packetIn);
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendPacketToAllPlayersInDimension(Packet packetIn, int dimension) {
/* 582 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/* 583 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*     */       
/* 585 */       if (entityplayermp.dimension == dimension) {
/* 586 */         entityplayermp.playerNetServerHandler.sendPacket(packetIn);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendMessageToAllTeamMembers(EntityPlayer player, IChatComponent message) {
/* 592 */     Team team = player.getTeam();
/*     */     
/* 594 */     if (team != null) {
/* 595 */       for (String s : team.getMembershipCollection()) {
/* 596 */         EntityPlayerMP entityplayermp = getPlayerByUsername(s);
/*     */         
/* 598 */         if (entityplayermp != null && entityplayermp != player) {
/* 599 */           entityplayermp.addChatMessage(message);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendMessageToTeamOrEvryPlayer(EntityPlayer player, IChatComponent message) {
/* 606 */     Team team = player.getTeam();
/*     */     
/* 608 */     if (team == null) {
/* 609 */       sendChatMsg(message);
/*     */     } else {
/* 611 */       for (int i = 0; i < this.playerEntityList.size(); i++) {
/* 612 */         EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*     */         
/* 614 */         if (entityplayermp.getTeam() != team) {
/* 615 */           entityplayermp.addChatMessage(message);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String func_181058_b(boolean p_181058_1_) {
/* 622 */     String s = "";
/* 623 */     List<EntityPlayerMP> list = Lists.newArrayList(this.playerEntityList);
/*     */     
/* 625 */     for (int i = 0; i < list.size(); i++) {
/* 626 */       if (i > 0) {
/* 627 */         s = String.valueOf(s) + ", ";
/*     */       }
/*     */       
/* 630 */       s = String.valueOf(s) + ((EntityPlayerMP)list.get(i)).getName();
/*     */       
/* 632 */       if (p_181058_1_) {
/* 633 */         s = String.valueOf(s) + " (" + ((EntityPlayerMP)list.get(i)).getUniqueID().toString() + ")";
/*     */       }
/*     */     } 
/*     */     
/* 637 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAllUsernames() {
/* 644 */     String[] astring = new String[this.playerEntityList.size()];
/*     */     
/* 646 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/* 647 */       astring[i] = ((EntityPlayerMP)this.playerEntityList.get(i)).getName();
/*     */     }
/*     */     
/* 650 */     return astring;
/*     */   }
/*     */   
/*     */   public GameProfile[] getAllProfiles() {
/* 654 */     GameProfile[] agameprofile = new GameProfile[this.playerEntityList.size()];
/*     */     
/* 656 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/* 657 */       agameprofile[i] = ((EntityPlayerMP)this.playerEntityList.get(i)).getGameProfile();
/*     */     }
/*     */     
/* 660 */     return agameprofile;
/*     */   }
/*     */   
/*     */   public UserListBans getBannedPlayers() {
/* 664 */     return this.bannedPlayers;
/*     */   }
/*     */   
/*     */   public BanList getBannedIPs() {
/* 668 */     return this.bannedIPs;
/*     */   }
/*     */   
/*     */   public void addOp(GameProfile profile) {
/* 672 */     this.ops.addEntry(new UserListOpsEntry(profile, this.mcServer.getOpPermissionLevel(), this.ops.bypassesPlayerLimit(profile)));
/*     */   }
/*     */   
/*     */   public void removeOp(GameProfile profile) {
/* 676 */     this.ops.removeEntry(profile);
/*     */   }
/*     */   
/*     */   public boolean canJoin(GameProfile profile) {
/* 680 */     return !(this.whiteListEnforced && !this.ops.hasEntry(profile) && !this.whiteListedPlayers.hasEntry(profile));
/*     */   }
/*     */   
/*     */   public boolean canSendCommands(GameProfile profile) {
/* 684 */     return !(!this.ops.hasEntry(profile) && (!this.mcServer.isSinglePlayer() || !this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() || !this.mcServer.getServerOwner().equalsIgnoreCase(profile.getName())) && !this.commandsAllowedForAll);
/*     */   }
/*     */   
/*     */   public EntityPlayerMP getPlayerByUsername(String username) {
/* 688 */     for (EntityPlayerMP entityplayermp : this.playerEntityList) {
/* 689 */       if (entityplayermp.getName().equalsIgnoreCase(username)) {
/* 690 */         return entityplayermp;
/*     */       }
/*     */     } 
/*     */     
/* 694 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAllNear(double x, double y, double z, double radius, int dimension, Packet packetIn) {
/* 701 */     sendToAllNearExcept(null, x, y, z, radius, dimension, packetIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendToAllNearExcept(EntityPlayer p_148543_1_, double x, double y, double z, double radius, int dimension, Packet p_148543_11_) {
/* 709 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/* 710 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*     */       
/* 712 */       if (entityplayermp != p_148543_1_ && entityplayermp.dimension == dimension) {
/* 713 */         double d0 = x - entityplayermp.posX;
/* 714 */         double d1 = y - entityplayermp.posY;
/* 715 */         double d2 = z - entityplayermp.posZ;
/*     */         
/* 717 */         if (d0 * d0 + d1 * d1 + d2 * d2 < radius * radius) {
/* 718 */           entityplayermp.playerNetServerHandler.sendPacket(p_148543_11_);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAllPlayerData() {
/* 728 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/* 729 */       writePlayerData(this.playerEntityList.get(i));
/*     */     }
/*     */   }
/*     */   
/*     */   public void addWhitelistedPlayer(GameProfile profile) {
/* 734 */     this.whiteListedPlayers.addEntry(new UserListWhitelistEntry(profile));
/*     */   }
/*     */   
/*     */   public void removePlayerFromWhitelist(GameProfile profile) {
/* 738 */     this.whiteListedPlayers.removeEntry(profile);
/*     */   }
/*     */   
/*     */   public UserListWhitelist getWhitelistedPlayers() {
/* 742 */     return this.whiteListedPlayers;
/*     */   }
/*     */   
/*     */   public String[] getWhitelistedPlayerNames() {
/* 746 */     return this.whiteListedPlayers.getKeys();
/*     */   }
/*     */   
/*     */   public UserListOps getOppedPlayers() {
/* 750 */     return this.ops;
/*     */   }
/*     */   
/*     */   public String[] getOppedPlayerNames() {
/* 754 */     return this.ops.getKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadWhiteList() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTimeAndWeatherForPlayer(EntityPlayerMP playerIn, WorldServer worldIn) {
/* 767 */     WorldBorder worldborder = this.mcServer.worldServers[0].getWorldBorder();
/* 768 */     playerIn.playerNetServerHandler.sendPacket((Packet)new S44PacketWorldBorder(worldborder, S44PacketWorldBorder.Action.INITIALIZE));
/* 769 */     playerIn.playerNetServerHandler.sendPacket((Packet)new S03PacketTimeUpdate(worldIn.getTotalWorldTime(), worldIn.getWorldTime(), worldIn.getGameRules().getBoolean("doDaylightCycle")));
/*     */     
/* 771 */     if (worldIn.isRaining()) {
/* 772 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(1, 0.0F));
/* 773 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(7, worldIn.getRainStrength(1.0F)));
/* 774 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(8, worldIn.getThunderStrength(1.0F)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void syncPlayerInventory(EntityPlayerMP playerIn) {
/* 782 */     playerIn.sendContainerToPlayer(playerIn.inventoryContainer);
/* 783 */     playerIn.setPlayerHealthUpdated();
/* 784 */     playerIn.playerNetServerHandler.sendPacket((Packet)new S09PacketHeldItemChange(playerIn.inventory.currentItem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentPlayerCount() {
/* 791 */     return this.playerEntityList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxPlayers() {
/* 798 */     return this.maxPlayers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAvailablePlayerDat() {
/* 805 */     return this.mcServer.worldServers[0].getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
/*     */   }
/*     */   
/*     */   public void setWhiteListEnabled(boolean whitelistEnabled) {
/* 809 */     this.whiteListEnforced = whitelistEnabled;
/*     */   }
/*     */   
/*     */   public List<EntityPlayerMP> getPlayersMatchingAddress(String address) {
/* 813 */     List<EntityPlayerMP> list = Lists.newArrayList();
/*     */     
/* 815 */     for (EntityPlayerMP entityplayermp : this.playerEntityList) {
/* 816 */       if (entityplayermp.getPlayerIP().equals(address)) {
/* 817 */         list.add(entityplayermp);
/*     */       }
/*     */     } 
/*     */     
/* 821 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getViewDistance() {
/* 828 */     return this.viewDistance;
/*     */   }
/*     */   
/*     */   public MinecraftServer getServerInstance() {
/* 832 */     return this.mcServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getHostPlayerData() {
/* 839 */     return null;
/*     */   }
/*     */   
/*     */   public void setGameType(WorldSettings.GameType p_152604_1_) {
/* 843 */     this.gameType = p_152604_1_;
/*     */   }
/*     */   
/*     */   private void setPlayerGameTypeBasedOnOther(EntityPlayerMP p_72381_1_, EntityPlayerMP p_72381_2_, World worldIn) {
/* 847 */     if (p_72381_2_ != null) {
/* 848 */       p_72381_1_.theItemInWorldManager.setGameType(p_72381_2_.theItemInWorldManager.getGameType());
/* 849 */     } else if (this.gameType != null) {
/* 850 */       p_72381_1_.theItemInWorldManager.setGameType(this.gameType);
/*     */     } 
/*     */     
/* 853 */     p_72381_1_.theItemInWorldManager.initializeGameType(worldIn.getWorldInfo().getGameType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCommandsAllowedForAll(boolean p_72387_1_) {
/* 860 */     this.commandsAllowedForAll = p_72387_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllPlayers() {
/* 867 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/* 868 */       ((EntityPlayerMP)this.playerEntityList.get(i)).playerNetServerHandler.kickPlayerFromServer("Server closed");
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendChatMsgImpl(IChatComponent component, boolean isChat) {
/* 873 */     this.mcServer.addChatMessage(component);
/* 874 */     byte b0 = (byte)(isChat ? 1 : 0);
/* 875 */     sendPacketToAllPlayers((Packet)new S02PacketChat(component, b0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendChatMsg(IChatComponent component) {
/* 882 */     sendChatMsgImpl(component, true);
/*     */   }
/*     */   
/*     */   public StatisticsFile getPlayerStatsFile(EntityPlayer playerIn) {
/* 886 */     UUID uuid = playerIn.getUniqueID();
/* 887 */     StatisticsFile statisticsfile = (uuid == null) ? null : this.playerStatFiles.get(uuid);
/*     */     
/* 889 */     if (statisticsfile == null) {
/* 890 */       File file1 = new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats");
/* 891 */       File file2 = new File(file1, String.valueOf(uuid.toString()) + ".json");
/*     */       
/* 893 */       if (!file2.exists()) {
/* 894 */         File file3 = new File(file1, String.valueOf(playerIn.getName()) + ".json");
/*     */         
/* 896 */         if (file3.exists() && file3.isFile()) {
/* 897 */           file3.renameTo(file2);
/*     */         }
/*     */       } 
/*     */       
/* 901 */       statisticsfile = new StatisticsFile(this.mcServer, file2);
/* 902 */       statisticsfile.readStatFile();
/* 903 */       this.playerStatFiles.put(uuid, statisticsfile);
/*     */     } 
/*     */     
/* 906 */     return statisticsfile;
/*     */   }
/*     */   
/*     */   public void setViewDistance(int distance) {
/* 910 */     this.viewDistance = distance;
/*     */     
/* 912 */     if (this.mcServer.worldServers != null) {
/* 913 */       byte b; int i; WorldServer[] arrayOfWorldServer; for (i = (arrayOfWorldServer = this.mcServer.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/* 914 */         if (worldserver != null)
/* 915 */           worldserver.getPlayerManager().setPlayerViewRadius(distance); 
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<EntityPlayerMP> getPlayerList() {
/* 922 */     return this.playerEntityList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayerMP getPlayerByUUID(UUID playerUUID) {
/* 929 */     return this.uuidToPlayerMap.get(playerUUID);
/*     */   }
/*     */   
/*     */   public boolean bypassesPlayerLimit(GameProfile p_183023_1_) {
/* 933 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\management\ServerConfigurationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */