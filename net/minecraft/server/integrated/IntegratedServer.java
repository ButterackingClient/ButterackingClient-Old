/*     */ package net.minecraft.server.integrated;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import net.minecraft.client.ClientBrandRetriever;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ThreadLanServerPing;
/*     */ import net.minecraft.command.ServerCommandManager;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketThreadUtil;
/*     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*     */ import net.minecraft.profiler.PlayerUsageSnooper;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.Util;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.IWorldAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldManager;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.WorldServerMulti;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.demo.DemoWorldServer;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import net.optifine.ClearWater;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class IntegratedServer
/*     */   extends MinecraftServer {
/*  46 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*     */   private final WorldSettings theWorldSettings;
/*     */   
/*     */   private boolean isGamePaused;
/*     */   
/*     */   private boolean isPublic;
/*     */   private ThreadLanServerPing lanServerPing;
/*  56 */   private long ticksSaveLast = 0L;
/*  57 */   public World difficultyUpdateWorld = null;
/*  58 */   public BlockPos difficultyUpdatePos = null;
/*  59 */   public DifficultyInstance difficultyLast = null;
/*     */   
/*     */   public IntegratedServer(Minecraft mcIn) {
/*  62 */     super(mcIn.getProxy(), new File(mcIn.mcDataDir, USER_CACHE_FILE.getName()));
/*  63 */     this.mc = mcIn;
/*  64 */     this.theWorldSettings = null;
/*     */   }
/*     */   
/*     */   public IntegratedServer(Minecraft mcIn, String folderName, String worldName, WorldSettings settings) {
/*  68 */     super(new File(mcIn.mcDataDir, "saves"), mcIn.getProxy(), new File(mcIn.mcDataDir, USER_CACHE_FILE.getName()));
/*  69 */     setServerOwner(mcIn.getSession().getUsername());
/*  70 */     setFolderName(folderName);
/*  71 */     setWorldName(worldName);
/*  72 */     setDemo(mcIn.isDemo());
/*  73 */     canCreateBonusChest(settings.isBonusChestEnabled());
/*  74 */     setBuildLimit(256);
/*  75 */     setConfigManager(new IntegratedPlayerList(this));
/*  76 */     this.mc = mcIn;
/*  77 */     this.theWorldSettings = isDemo() ? DemoWorldServer.demoWorldSettings : settings;
/*  78 */     ISaveHandler isavehandler = getActiveAnvilConverter().getSaveLoader(folderName, false);
/*  79 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*     */     
/*  81 */     if (worldinfo != null) {
/*  82 */       NBTTagCompound nbttagcompound = worldinfo.getPlayerNBTTagCompound();
/*     */       
/*  84 */       if (nbttagcompound != null && nbttagcompound.hasKey("Dimension")) {
/*  85 */         int i = nbttagcompound.getInteger("Dimension");
/*  86 */         PacketThreadUtil.lastDimensionId = i;
/*  87 */         this.mc.loadingScreen.setLoadingProgress(-1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected ServerCommandManager createNewCommandManager() {
/*  93 */     return new IntegratedServerCommandManager();
/*     */   }
/*     */   
/*     */   protected void loadAllWorlds(String saveName, String worldNameIn, long seed, WorldType type, String worldNameIn2) {
/*  97 */     convertMapIfNeeded(saveName);
/*  98 */     boolean flag = Reflector.DimensionManager.exists();
/*     */     
/* 100 */     if (!flag) {
/* 101 */       this.worldServers = new WorldServer[3];
/* 102 */       this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
/*     */     } 
/*     */     
/* 105 */     ISaveHandler isavehandler = getActiveAnvilConverter().getSaveLoader(saveName, true);
/* 106 */     setResourcePackFromWorld(getFolderName(), isavehandler);
/* 107 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*     */     
/* 109 */     if (worldinfo == null) {
/* 110 */       worldinfo = new WorldInfo(this.theWorldSettings, worldNameIn);
/*     */     } else {
/* 112 */       worldinfo.setWorldName(worldNameIn);
/*     */     } 
/*     */     
/* 115 */     if (flag) {
/* 116 */       WorldServer worldserver = isDemo() ? (WorldServer)(new DemoWorldServer(this, isavehandler, worldinfo, 0, this.theProfiler)).init() : (WorldServer)(new WorldServer(this, isavehandler, worldinfo, 0, this.theProfiler)).init();
/* 117 */       worldserver.initialize(this.theWorldSettings);
/* 118 */       Integer[] ainteger = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0]);
/* 119 */       Integer[] ainteger1 = ainteger;
/* 120 */       int i = ainteger.length;
/*     */       
/* 122 */       for (int j = 0; j < i; j++) {
/* 123 */         int k = ainteger1[j].intValue();
/* 124 */         WorldServer worldserver1 = (k == 0) ? worldserver : (WorldServer)(new WorldServerMulti(this, isavehandler, k, worldserver, this.theProfiler)).init();
/* 125 */         worldserver1.addWorldAccess((IWorldAccess)new WorldManager(this, worldserver1));
/*     */         
/* 127 */         if (!isSinglePlayer()) {
/* 128 */           worldserver1.getWorldInfo().setGameType(getGameType());
/*     */         }
/*     */         
/* 131 */         if (Reflector.EventBus.exists()) {
/* 132 */           Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { worldserver1 });
/*     */         }
/*     */       } 
/*     */       
/* 136 */       getConfigurationManager().setPlayerManager(new WorldServer[] { worldserver });
/*     */       
/* 138 */       if (worldserver.getWorldInfo().getDifficulty() == null) {
/* 139 */         setDifficultyForAllWorlds(this.mc.gameSettings.hideGUI);
/*     */       }
/*     */     } else {
/* 142 */       for (int l = 0; l < this.worldServers.length; l++) {
/* 143 */         int i1 = 0;
/*     */         
/* 145 */         if (l == 1) {
/* 146 */           i1 = -1;
/*     */         }
/*     */         
/* 149 */         if (l == 2) {
/* 150 */           i1 = 1;
/*     */         }
/*     */         
/* 153 */         if (l == 0) {
/* 154 */           if (isDemo()) {
/* 155 */             this.worldServers[l] = (WorldServer)(new DemoWorldServer(this, isavehandler, worldinfo, i1, this.theProfiler)).init();
/*     */           } else {
/* 157 */             this.worldServers[l] = (WorldServer)(new WorldServer(this, isavehandler, worldinfo, i1, this.theProfiler)).init();
/*     */           } 
/*     */           
/* 160 */           this.worldServers[l].initialize(this.theWorldSettings);
/*     */         } else {
/* 162 */           this.worldServers[l] = (WorldServer)(new WorldServerMulti(this, isavehandler, i1, this.worldServers[0], this.theProfiler)).init();
/*     */         } 
/*     */         
/* 165 */         this.worldServers[l].addWorldAccess((IWorldAccess)new WorldManager(this, this.worldServers[l]));
/*     */       } 
/*     */       
/* 168 */       getConfigurationManager().setPlayerManager(this.worldServers);
/*     */       
/* 170 */       if (this.worldServers[0].getWorldInfo().getDifficulty() == null) {
/* 171 */         setDifficultyForAllWorlds(this.mc.gameSettings.hideGUI);
/*     */       }
/*     */     } 
/*     */     
/* 175 */     initialWorldChunkLoad();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean startServer() throws IOException {
/* 182 */     logger.info("Starting integrated minecraft server version 1.9");
/* 183 */     setOnlineMode(true);
/* 184 */     setCanSpawnAnimals(true);
/* 185 */     setCanSpawnNPCs(true);
/* 186 */     setAllowPvp(true);
/* 187 */     setAllowFlight(true);
/* 188 */     logger.info("Generating keypair");
/* 189 */     setKeyPair(CryptManager.generateKeyPair());
/*     */ 
/*     */     
/* 192 */     Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/*     */     
/* 194 */     if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists() && !Reflector.callBoolean(object, Reflector.FMLCommonHandler_handleServerAboutToStart, new Object[] { this })) {
/* 195 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 199 */     loadAllWorlds(getFolderName(), getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getWorldName());
/* 200 */     setMOTD(String.valueOf(getServerOwner()) + " - " + this.worldServers[0].getWorldInfo().getWorldName());
/*     */     
/* 202 */     if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
/* 203 */       Object object1 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/*     */       
/* 205 */       if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == boolean.class) {
/* 206 */         return Reflector.callBoolean(object1, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
/*     */       }
/*     */       
/* 209 */       Reflector.callVoid(object1, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
/*     */     } 
/*     */     
/* 212 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 219 */     onTick();
/* 220 */     boolean flag = this.isGamePaused;
/* 221 */     this.isGamePaused = (Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().isGamePaused());
/*     */     
/* 223 */     if (!flag && this.isGamePaused) {
/* 224 */       logger.info("Saving and pausing game...");
/* 225 */       getConfigurationManager().saveAllPlayerData();
/* 226 */       saveAllWorlds(false);
/*     */     } 
/*     */     
/* 229 */     if (this.isGamePaused) {
/* 230 */       synchronized (this.futureTaskQueue) {
/* 231 */         while (!this.futureTaskQueue.isEmpty()) {
/* 232 */           Util.runTask(this.futureTaskQueue.poll(), logger);
/*     */         }
/*     */       } 
/*     */     } else {
/* 236 */       super.tick();
/*     */       
/* 238 */       if (this.mc.gameSettings.renderDistanceChunks != getConfigurationManager().getViewDistance()) {
/* 239 */         logger.info("Changing view distance to {}, from {}", new Object[] { Integer.valueOf(this.mc.gameSettings.renderDistanceChunks), Integer.valueOf(getConfigurationManager().getViewDistance()) });
/* 240 */         getConfigurationManager().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
/*     */       } 
/*     */       
/* 243 */       if (this.mc.theWorld != null) {
/* 244 */         WorldInfo worldinfo1 = this.worldServers[0].getWorldInfo();
/* 245 */         WorldInfo worldinfo = this.mc.theWorld.getWorldInfo();
/*     */         
/* 247 */         if (!worldinfo1.isDifficultyLocked() && worldinfo.getDifficulty() != worldinfo1.getDifficulty()) {
/* 248 */           logger.info("Changing difficulty to {}, from {}", new Object[] { worldinfo.getDifficulty(), worldinfo1.getDifficulty() });
/* 249 */           setDifficultyForAllWorlds(worldinfo.getDifficulty());
/* 250 */         } else if (worldinfo.isDifficultyLocked() && !worldinfo1.isDifficultyLocked()) {
/* 251 */           logger.info("Locking difficulty to {}", new Object[] { worldinfo.getDifficulty() }); byte b; int i;
/*     */           WorldServer[] arrayOfWorldServer;
/* 253 */           for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/* 254 */             if (worldserver != null)
/* 255 */               worldserver.getWorldInfo().setDifficultyLocked(true); 
/*     */             b++; }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canStructuresSpawn() {
/* 264 */     return false;
/*     */   }
/*     */   
/*     */   public WorldSettings.GameType getGameType() {
/* 268 */     return this.theWorldSettings.getGameType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 275 */     return (this.mc.theWorld == null) ? this.mc.gameSettings.hideGUI : this.mc.theWorld.getWorldInfo().getDifficulty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHardcore() {
/* 282 */     return this.theWorldSettings.getHardcoreEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldBroadcastRconToOps() {
/* 289 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldBroadcastConsoleToOps() {
/* 296 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAllWorlds(boolean dontLog) {
/* 303 */     if (dontLog) {
/* 304 */       int i = getTickCounter();
/* 305 */       int j = this.mc.gameSettings.ofAutoSaveTicks;
/*     */       
/* 307 */       if (i < this.ticksSaveLast + j) {
/*     */         return;
/*     */       }
/*     */       
/* 311 */       this.ticksSaveLast = i;
/*     */     } 
/*     */     
/* 314 */     super.saveAllWorlds(dontLog);
/*     */   }
/*     */   
/*     */   public File getDataDirectory() {
/* 318 */     return this.mc.mcDataDir;
/*     */   }
/*     */   
/*     */   public boolean isDedicatedServer() {
/* 322 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUseNativeTransport() {
/* 330 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalTick(CrashReport report) {
/* 337 */     this.mc.crashed(report);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReport addServerInfoToCrashReport(CrashReport report) {
/* 344 */     report = super.addServerInfoToCrashReport(report);
/* 345 */     report.getCategory().addCrashSectionCallable("Type", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 347 */             return "Integrated Server (map_client.txt)";
/*     */           }
/*     */         });
/* 350 */     report.getCategory().addCrashSectionCallable("Is Modded", new Callable<String>() {
/*     */           public String call() throws Exception {
/* 352 */             String s = ClientBrandRetriever.getClientModName();
/*     */             
/* 354 */             if (!s.equals("vanilla")) {
/* 355 */               return "Definitely; Client brand changed to '" + s + "'";
/*     */             }
/* 357 */             s = IntegratedServer.this.getServerModName();
/* 358 */             return !s.equals("vanilla") ? ("Definitely; Server brand changed to '" + s + "'") : ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.");
/*     */           }
/*     */         });
/*     */     
/* 362 */     return report;
/*     */   }
/*     */   
/*     */   public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {
/* 366 */     super.setDifficultyForAllWorlds(difficulty);
/*     */     
/* 368 */     if (this.mc.theWorld != null) {
/* 369 */       this.mc.theWorld.getWorldInfo().setDifficulty(difficulty);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
/* 374 */     super.addServerStatsToSnooper(playerSnooper);
/* 375 */     playerSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSnooperEnabled() {
/* 382 */     return Minecraft.getMinecraft().isSnooperEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String shareToLAN(WorldSettings.GameType type, boolean allowCheats) {
/*     */     try {
/* 390 */       int i = -1;
/*     */       
/*     */       try {
/* 393 */         i = HttpUtil.getSuitableLanPort();
/* 394 */       } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */       
/* 398 */       if (i <= 0) {
/* 399 */         i = 25564;
/*     */       }
/*     */       
/* 402 */       getNetworkSystem().addLanEndpoint(null, i);
/* 403 */       logger.info("Started on " + i);
/* 404 */       this.isPublic = true;
/* 405 */       this.lanServerPing = new ThreadLanServerPing(getMOTD(), (new StringBuilder(String.valueOf(i))).toString());
/* 406 */       this.lanServerPing.start();
/* 407 */       getConfigurationManager().setGameType(type);
/* 408 */       getConfigurationManager().setCommandsAllowedForAll(allowCheats);
/* 409 */       return (new StringBuilder(String.valueOf(i))).toString();
/* 410 */     } catch (IOException var6) {
/* 411 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopServer() {
/* 419 */     super.stopServer();
/*     */     
/* 421 */     if (this.lanServerPing != null) {
/* 422 */       this.lanServerPing.interrupt();
/* 423 */       this.lanServerPing = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateShutdown() {
/* 431 */     if (!Reflector.MinecraftForge.exists() || isServerRunning()) {
/* 432 */       Futures.getUnchecked((Future)addScheduledTask(new Runnable() {
/*     */               public void run() {
/* 434 */                 for (EntityPlayerMP entityplayermp : Lists.newArrayList(IntegratedServer.this.getConfigurationManager().getPlayerList())) {
/* 435 */                   IntegratedServer.this.getConfigurationManager().playerLoggedOut(entityplayermp);
/*     */                 }
/*     */               }
/*     */             }));
/*     */     }
/*     */     
/* 441 */     super.initiateShutdown();
/*     */     
/* 443 */     if (this.lanServerPing != null) {
/* 444 */       this.lanServerPing.interrupt();
/* 445 */       this.lanServerPing = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setStaticInstance() {
/* 450 */     setInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPublic() {
/* 457 */     return this.isPublic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(WorldSettings.GameType gameMode) {
/* 464 */     getConfigurationManager().setGameType(gameMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCommandBlockEnabled() {
/* 471 */     return true;
/*     */   }
/*     */   
/*     */   public int getOpPermissionLevel() {
/* 475 */     return 4;
/*     */   }
/*     */   
/*     */   private void onTick() {
/* 479 */     for (WorldServer worldserver : Arrays.<WorldServer>asList(this.worldServers)) {
/* 480 */       onTick(worldserver);
/*     */     }
/*     */   }
/*     */   
/*     */   public DifficultyInstance getDifficultyAsync(World p_getDifficultyAsync_1_, BlockPos p_getDifficultyAsync_2_) {
/* 485 */     this.difficultyUpdateWorld = p_getDifficultyAsync_1_;
/* 486 */     this.difficultyUpdatePos = p_getDifficultyAsync_2_;
/* 487 */     return this.difficultyLast;
/*     */   }
/*     */   
/*     */   private void onTick(WorldServer p_onTick_1_) {
/* 491 */     if (!Config.isTimeDefault()) {
/* 492 */       fixWorldTime(p_onTick_1_);
/*     */     }
/*     */     
/* 495 */     if (!Config.isWeatherEnabled()) {
/* 496 */       fixWorldWeather(p_onTick_1_);
/*     */     }
/*     */     
/* 499 */     if (Config.waterOpacityChanged) {
/* 500 */       Config.waterOpacityChanged = false;
/* 501 */       ClearWater.updateWaterOpacity(Config.getGameSettings(), (World)p_onTick_1_);
/*     */     } 
/*     */     
/* 504 */     if (this.difficultyUpdateWorld == p_onTick_1_ && this.difficultyUpdatePos != null) {
/* 505 */       this.difficultyLast = p_onTick_1_.getDifficultyForLocation(this.difficultyUpdatePos);
/* 506 */       this.difficultyUpdateWorld = null;
/* 507 */       this.difficultyUpdatePos = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fixWorldWeather(WorldServer p_fixWorldWeather_1_) {
/* 512 */     WorldInfo worldinfo = p_fixWorldWeather_1_.getWorldInfo();
/*     */     
/* 514 */     if (worldinfo.isRaining() || worldinfo.isThundering()) {
/* 515 */       worldinfo.setRainTime(0);
/* 516 */       worldinfo.setRaining(false);
/* 517 */       p_fixWorldWeather_1_.setRainStrength(0.0F);
/* 518 */       worldinfo.setThunderTime(0);
/* 519 */       worldinfo.setThundering(false);
/* 520 */       p_fixWorldWeather_1_.setThunderStrength(0.0F);
/* 521 */       getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(2, 0.0F));
/* 522 */       getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(7, 0.0F));
/* 523 */       getConfigurationManager().sendPacketToAllPlayers((Packet)new S2BPacketChangeGameState(8, 0.0F));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fixWorldTime(WorldServer p_fixWorldTime_1_) {
/* 528 */     WorldInfo worldinfo = p_fixWorldTime_1_.getWorldInfo();
/*     */     
/* 530 */     if (worldinfo.getGameType().getID() == 1) {
/* 531 */       long i = p_fixWorldTime_1_.getWorldTime();
/* 532 */       long j = i % 24000L;
/*     */       
/* 534 */       if (Config.isTimeDayOnly()) {
/* 535 */         if (j <= 1000L) {
/* 536 */           p_fixWorldTime_1_.setWorldTime(i - j + 1001L);
/*     */         }
/*     */         
/* 539 */         if (j >= 11000L) {
/* 540 */           p_fixWorldTime_1_.setWorldTime(i - j + 24001L);
/*     */         }
/*     */       } 
/*     */       
/* 544 */       if (Config.isTimeNightOnly()) {
/* 545 */         if (j <= 14000L) {
/* 546 */           p_fixWorldTime_1_.setWorldTime(i - j + 14001L);
/*     */         }
/*     */         
/* 549 */         if (j >= 22000L)
/* 550 */           p_fixWorldTime_1_.setWorldTime(i - j + 24000L + 14001L); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\integrated\IntegratedServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */