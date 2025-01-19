/*      */ package net.minecraft.server;
/*      */ 
/*      */ import com.google.common.base.Charsets;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Queues;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListenableFutureTask;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.GameProfileRepository;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufOutputStream;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.handler.codec.base64.Base64;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.net.Proxy;
/*      */ import java.security.KeyPair;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Queue;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.command.CommandBase;
/*      */ import net.minecraft.command.CommandResultStats;
/*      */ import net.minecraft.command.ICommandManager;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.command.ServerCommandManager;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.network.NetworkSystem;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.ServerStatusResponse;
/*      */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*      */ import net.minecraft.profiler.IPlayerUsage;
/*      */ import net.minecraft.profiler.PlayerUsageSnooper;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.server.management.PlayerProfileCache;
/*      */ import net.minecraft.server.management.ServerConfigurationManager;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IProgressUpdate;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.ITickable;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.Util;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.IWorldAccess;
/*      */ import net.minecraft.world.MinecraftException;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldManager;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.WorldServerMulti;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.WorldType;
/*      */ import net.minecraft.world.chunk.storage.AnvilSaveConverter;
/*      */ import net.minecraft.world.demo.DemoWorldServer;
/*      */ import net.minecraft.world.storage.ISaveFormat;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public abstract class MinecraftServer implements Runnable, ICommandSender, IThreadListener, IPlayerUsage {
/*   83 */   private static final Logger logger = LogManager.getLogger();
/*   84 */   public static final File USER_CACHE_FILE = new File("usercache.json");
/*      */ 
/*      */ 
/*      */   
/*      */   private static MinecraftServer mcServer;
/*      */ 
/*      */ 
/*      */   
/*      */   private final ISaveFormat anvilConverterForAnvilFile;
/*      */ 
/*      */   
/*   95 */   private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
/*      */   private final File anvilFile;
/*   97 */   private final List<ITickable> playersOnline = Lists.newArrayList();
/*      */   protected final ICommandManager commandManager;
/*   99 */   public final Profiler theProfiler = new Profiler();
/*      */   private final NetworkSystem networkSystem;
/*  101 */   private final ServerStatusResponse statusResponse = new ServerStatusResponse();
/*  102 */   private final Random random = new Random();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  107 */   private int serverPort = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldServer[] worldServers;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ServerConfigurationManager serverConfigManager;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean serverRunning = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean serverStopped;
/*      */ 
/*      */ 
/*      */   
/*      */   private int tickCounter;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Proxy serverProxy;
/*      */ 
/*      */ 
/*      */   
/*      */   public String currentTask;
/*      */ 
/*      */ 
/*      */   
/*      */   public int percentDone;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean onlineMode;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canSpawnAnimals;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canSpawnNPCs;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean pvpEnabled;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean allowFlight;
/*      */ 
/*      */ 
/*      */   
/*      */   private String motd;
/*      */ 
/*      */ 
/*      */   
/*      */   private int buildLimit;
/*      */ 
/*      */ 
/*      */   
/*  175 */   private int maxPlayerIdleMinutes = 0;
/*  176 */   public final long[] tickTimeArray = new long[100];
/*      */ 
/*      */   
/*      */   public long[][] timeOfLastDimensionTick;
/*      */ 
/*      */   
/*      */   private KeyPair serverKeyPair;
/*      */ 
/*      */   
/*      */   private String serverOwner;
/*      */ 
/*      */   
/*      */   private String folderName;
/*      */ 
/*      */   
/*      */   private String worldName;
/*      */ 
/*      */   
/*      */   private boolean isDemo;
/*      */ 
/*      */   
/*      */   private boolean enableBonusChest;
/*      */   
/*      */   private boolean worldIsBeingDeleted;
/*      */   
/*  201 */   private String resourcePackUrl = "";
/*  202 */   private String resourcePackHash = "";
/*      */   
/*      */   private boolean serverIsRunning;
/*      */   
/*      */   private long timeOfLastWarning;
/*      */   
/*      */   private String userMessage;
/*      */   
/*      */   private boolean startProfiling;
/*      */   private boolean isGamemodeForced;
/*      */   private final YggdrasilAuthenticationService authService;
/*      */   private final MinecraftSessionService sessionService;
/*  214 */   private long nanoTimeSinceStatusRefresh = 0L;
/*      */   private final GameProfileRepository profileRepo;
/*      */   private final PlayerProfileCache profileCache;
/*  217 */   protected final Queue<FutureTask<?>> futureTaskQueue = Queues.newArrayDeque();
/*      */   private Thread serverThread;
/*  219 */   private long currentTime = getCurrentTimeMillis();
/*      */   
/*      */   public MinecraftServer(Proxy proxy, File workDir) {
/*  222 */     this.serverProxy = proxy;
/*  223 */     mcServer = this;
/*  224 */     this.anvilFile = null;
/*  225 */     this.networkSystem = null;
/*  226 */     this.profileCache = new PlayerProfileCache(this, workDir);
/*  227 */     this.commandManager = null;
/*  228 */     this.anvilConverterForAnvilFile = null;
/*  229 */     this.authService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
/*  230 */     this.sessionService = this.authService.createMinecraftSessionService();
/*  231 */     this.profileRepo = this.authService.createProfileRepository();
/*      */   }
/*      */   
/*      */   public MinecraftServer(File workDir, Proxy proxy, File profileCacheDir) {
/*  235 */     this.serverProxy = proxy;
/*  236 */     mcServer = this;
/*  237 */     this.anvilFile = workDir;
/*  238 */     this.networkSystem = new NetworkSystem(this);
/*  239 */     this.profileCache = new PlayerProfileCache(this, profileCacheDir);
/*  240 */     this.commandManager = (ICommandManager)createNewCommandManager();
/*  241 */     this.anvilConverterForAnvilFile = (ISaveFormat)new AnvilSaveConverter(workDir);
/*  242 */     this.authService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
/*  243 */     this.sessionService = this.authService.createMinecraftSessionService();
/*  244 */     this.profileRepo = this.authService.createProfileRepository();
/*      */   }
/*      */   
/*      */   protected ServerCommandManager createNewCommandManager() {
/*  248 */     return new ServerCommandManager();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void convertMapIfNeeded(String worldNameIn) {
/*  257 */     if (getActiveAnvilConverter().isOldMapFormat(worldNameIn)) {
/*  258 */       logger.info("Converting map!");
/*  259 */       setUserMessage("menu.convertingLevel");
/*  260 */       getActiveAnvilConverter().convertMapFormat(worldNameIn, new IProgressUpdate() {
/*  261 */             private long startTime = System.currentTimeMillis();
/*      */ 
/*      */             
/*      */             public void displaySavingString(String message) {}
/*      */ 
/*      */             
/*      */             public void resetProgressAndMessage(String message) {}
/*      */             
/*      */             public void setLoadingProgress(int progress) {
/*  270 */               if (System.currentTimeMillis() - this.startTime >= 1000L) {
/*  271 */                 this.startTime = System.currentTimeMillis();
/*  272 */                 MinecraftServer.logger.info("Converting... " + progress + "%");
/*      */               } 
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public void setDoneWorking() {}
/*      */ 
/*      */ 
/*      */             
/*      */             public void displayLoadingString(String message) {}
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected synchronized void setUserMessage(String message) {
/*  289 */     this.userMessage = message;
/*      */   }
/*      */   
/*      */   public synchronized String getUserMessage() {
/*  293 */     return this.userMessage;
/*      */   }
/*      */   protected void loadAllWorlds(String saveName, String worldNameIn, long seed, WorldType type, String worldNameIn2) {
/*      */     WorldSettings worldsettings;
/*  297 */     convertMapIfNeeded(saveName);
/*  298 */     setUserMessage("menu.loadingLevel");
/*  299 */     this.worldServers = new WorldServer[3];
/*  300 */     this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
/*  301 */     ISaveHandler isavehandler = this.anvilConverterForAnvilFile.getSaveLoader(saveName, true);
/*  302 */     setResourcePackFromWorld(getFolderName(), isavehandler);
/*  303 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*      */ 
/*      */     
/*  306 */     if (worldinfo == null) {
/*  307 */       if (isDemo()) {
/*  308 */         worldsettings = DemoWorldServer.demoWorldSettings;
/*      */       } else {
/*  310 */         worldsettings = new WorldSettings(seed, getGameType(), canStructuresSpawn(), isHardcore(), type);
/*  311 */         worldsettings.setWorldName(worldNameIn2);
/*      */         
/*  313 */         if (this.enableBonusChest) {
/*  314 */           worldsettings.enableBonusChest();
/*      */         }
/*      */       } 
/*      */       
/*  318 */       worldinfo = new WorldInfo(worldsettings, worldNameIn);
/*      */     } else {
/*  320 */       worldinfo.setWorldName(worldNameIn);
/*  321 */       worldsettings = new WorldSettings(worldinfo);
/*      */     } 
/*      */     
/*  324 */     for (int i = 0; i < this.worldServers.length; i++) {
/*  325 */       int j = 0;
/*      */       
/*  327 */       if (i == 1) {
/*  328 */         j = -1;
/*      */       }
/*      */       
/*  331 */       if (i == 2) {
/*  332 */         j = 1;
/*      */       }
/*      */       
/*  335 */       if (i == 0) {
/*  336 */         if (isDemo()) {
/*  337 */           this.worldServers[i] = (WorldServer)(new DemoWorldServer(this, isavehandler, worldinfo, j, this.theProfiler)).init();
/*      */         } else {
/*  339 */           this.worldServers[i] = (WorldServer)(new WorldServer(this, isavehandler, worldinfo, j, this.theProfiler)).init();
/*      */         } 
/*      */         
/*  342 */         this.worldServers[i].initialize(worldsettings);
/*      */       } else {
/*  344 */         this.worldServers[i] = (WorldServer)(new WorldServerMulti(this, isavehandler, j, this.worldServers[0], this.theProfiler)).init();
/*      */       } 
/*      */       
/*  347 */       this.worldServers[i].addWorldAccess((IWorldAccess)new WorldManager(this, this.worldServers[i]));
/*      */       
/*  349 */       if (!isSinglePlayer()) {
/*  350 */         this.worldServers[i].getWorldInfo().setGameType(getGameType());
/*      */       }
/*      */     } 
/*      */     
/*  354 */     this.serverConfigManager.setPlayerManager(this.worldServers);
/*  355 */     setDifficultyForAllWorlds(getDifficulty());
/*  356 */     initialWorldChunkLoad();
/*      */   }
/*      */   
/*      */   protected void initialWorldChunkLoad() {
/*  360 */     int i = 16;
/*  361 */     int j = 4;
/*  362 */     int k = 192;
/*  363 */     int l = 625;
/*  364 */     int i1 = 0;
/*  365 */     setUserMessage("menu.generatingTerrain");
/*  366 */     int j1 = 0;
/*  367 */     logger.info("Preparing start region for level " + j1);
/*  368 */     WorldServer worldserver = this.worldServers[j1];
/*  369 */     BlockPos blockpos = worldserver.getSpawnPoint();
/*  370 */     long k1 = getCurrentTimeMillis();
/*      */     
/*  372 */     for (int l1 = -192; l1 <= 192 && isServerRunning(); l1 += 16) {
/*  373 */       for (int i2 = -192; i2 <= 192 && isServerRunning(); i2 += 16) {
/*  374 */         long j2 = getCurrentTimeMillis();
/*      */         
/*  376 */         if (j2 - k1 > 1000L) {
/*  377 */           outputPercentRemaining("Preparing spawn area", i1 * 100 / 625);
/*  378 */           k1 = j2;
/*      */         } 
/*      */         
/*  381 */         i1++;
/*  382 */         worldserver.theChunkProviderServer.loadChunk(blockpos.getX() + l1 >> 4, blockpos.getZ() + i2 >> 4);
/*      */       } 
/*      */     } 
/*      */     
/*  386 */     clearCurrentTask();
/*      */   }
/*      */   
/*      */   protected void setResourcePackFromWorld(String worldNameIn, ISaveHandler saveHandlerIn) {
/*  390 */     File file1 = new File(saveHandlerIn.getWorldDirectory(), "resources.zip");
/*      */     
/*  392 */     if (file1.isFile()) {
/*  393 */       setResourcePack("level://" + worldNameIn + "/" + file1.getName(), "");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void outputPercentRemaining(String message, int percent) {
/*  427 */     this.currentTask = message;
/*  428 */     this.percentDone = percent;
/*  429 */     logger.info(String.valueOf(message) + ": " + percent + "%");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void clearCurrentTask() {
/*  436 */     this.currentTask = null;
/*  437 */     this.percentDone = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void saveAllWorlds(boolean dontLog) {
/*  444 */     if (!this.worldIsBeingDeleted) {
/*  445 */       byte b; int i; WorldServer[] arrayOfWorldServer; for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/*  446 */         if (worldserver != null) {
/*  447 */           if (!dontLog) {
/*  448 */             logger.info("Saving chunks for level '" + worldserver.getWorldInfo().getWorldName() + "'/" + worldserver.provider.getDimensionName());
/*      */           }
/*      */           
/*      */           try {
/*  452 */             worldserver.saveAllChunks(true, null);
/*  453 */           } catch (MinecraftException minecraftexception) {
/*  454 */             logger.warn(minecraftexception.getMessage());
/*      */           } 
/*      */         } 
/*      */         b++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void stopServer() {
/*  465 */     if (!this.worldIsBeingDeleted) {
/*  466 */       logger.info("Stopping server");
/*      */       
/*  468 */       if (getNetworkSystem() != null) {
/*  469 */         getNetworkSystem().terminateEndpoints();
/*      */       }
/*      */       
/*  472 */       if (this.serverConfigManager != null) {
/*  473 */         logger.info("Saving players");
/*  474 */         this.serverConfigManager.saveAllPlayerData();
/*  475 */         this.serverConfigManager.removeAllPlayers();
/*      */       } 
/*      */       
/*  478 */       if (this.worldServers != null) {
/*  479 */         logger.info("Saving worlds");
/*  480 */         saveAllWorlds(false);
/*      */         
/*  482 */         for (int i = 0; i < this.worldServers.length; i++) {
/*  483 */           WorldServer worldserver = this.worldServers[i];
/*  484 */           worldserver.flush();
/*      */         } 
/*      */       } 
/*      */       
/*  488 */       if (this.usageSnooper.isSnooperRunning()) {
/*  489 */         this.usageSnooper.stopSnooper();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isServerRunning() {
/*  495 */     return this.serverRunning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initiateShutdown() {
/*  502 */     this.serverRunning = false;
/*      */   }
/*      */   
/*      */   protected void setInstance() {
/*  506 */     mcServer = this;
/*      */   }
/*      */   
/*      */   public void run() {
/*      */     try {
/*  511 */       if (startServer()) {
/*  512 */         this.currentTime = getCurrentTimeMillis();
/*  513 */         long i = 0L;
/*  514 */         this.statusResponse.setServerDescription((IChatComponent)new ChatComponentText(this.motd));
/*  515 */         this.statusResponse.setProtocolVersionInfo(new ServerStatusResponse.MinecraftProtocolVersionIdentifier("1.8.9", 47));
/*  516 */         addFaviconToStatusResponse(this.statusResponse);
/*      */         
/*  518 */         while (this.serverRunning) {
/*  519 */           long k = getCurrentTimeMillis();
/*  520 */           long j = k - this.currentTime;
/*      */           
/*  522 */           if (j > 2000L && this.currentTime - this.timeOfLastWarning >= 15000L) {
/*  523 */             logger.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[] { Long.valueOf(j), Long.valueOf(j / 50L) });
/*  524 */             j = 2000L;
/*  525 */             this.timeOfLastWarning = this.currentTime;
/*      */           } 
/*      */           
/*  528 */           if (j < 0L) {
/*  529 */             logger.warn("Time ran backwards! Did the system time change?");
/*  530 */             j = 0L;
/*      */           } 
/*      */           
/*  533 */           i += j;
/*  534 */           this.currentTime = k;
/*      */           
/*  536 */           if (this.worldServers[0].areAllPlayersAsleep()) {
/*  537 */             tick();
/*  538 */             i = 0L;
/*      */           } else {
/*  540 */             while (i > 50L) {
/*  541 */               i -= 50L;
/*  542 */               tick();
/*      */             } 
/*      */           } 
/*      */           
/*  546 */           Thread.sleep(Math.max(1L, 50L - i));
/*  547 */           this.serverIsRunning = true;
/*      */         } 
/*      */       } else {
/*  550 */         finalTick(null);
/*      */       } 
/*  552 */     } catch (Throwable throwable1) {
/*  553 */       logger.error("Encountered an unexpected exception", throwable1);
/*  554 */       CrashReport crashreport = null;
/*      */       
/*  556 */       if (throwable1 instanceof ReportedException) {
/*  557 */         crashreport = addServerInfoToCrashReport(((ReportedException)throwable1).getCrashReport());
/*      */       } else {
/*  559 */         crashreport = addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", throwable1));
/*      */       } 
/*      */       
/*  562 */       File file1 = new File(new File(getDataDirectory(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
/*      */       
/*  564 */       if (crashreport.saveToFile(file1)) {
/*  565 */         logger.error("This crash report has been saved to: " + file1.getAbsolutePath());
/*      */       } else {
/*  567 */         logger.error("We were unable to save this crash report to disk.");
/*      */       } 
/*      */       
/*  570 */       finalTick(crashreport);
/*      */     } finally {
/*      */       try {
/*  573 */         this.serverStopped = true;
/*  574 */         stopServer();
/*  575 */       } catch (Throwable throwable) {
/*  576 */         logger.error("Exception stopping the server", throwable);
/*      */       } finally {
/*  578 */         systemExitNow();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addFaviconToStatusResponse(ServerStatusResponse response) {
/*  584 */     File file1 = getFile("server-icon.png");
/*      */     
/*  586 */     if (file1.isFile()) {
/*  587 */       ByteBuf bytebuf = Unpooled.buffer();
/*      */       
/*      */       try {
/*  590 */         BufferedImage bufferedimage = ImageIO.read(file1);
/*  591 */         Validate.validState((bufferedimage.getWidth() == 64), "Must be 64 pixels wide", new Object[0]);
/*  592 */         Validate.validState((bufferedimage.getHeight() == 64), "Must be 64 pixels high", new Object[0]);
/*  593 */         ImageIO.write(bufferedimage, "PNG", (OutputStream)new ByteBufOutputStream(bytebuf));
/*  594 */         ByteBuf bytebuf1 = Base64.encode(bytebuf);
/*  595 */         response.setFavicon("data:image/png;base64," + bytebuf1.toString(Charsets.UTF_8));
/*  596 */       } catch (Exception exception) {
/*  597 */         logger.error("Couldn't load server icon", exception);
/*      */       } finally {
/*  599 */         bytebuf.release();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public File getDataDirectory() {
/*  605 */     return new File(".");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalTick(CrashReport report) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void systemExitNow() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick() {
/*  624 */     long i = System.nanoTime();
/*  625 */     this.tickCounter++;
/*      */     
/*  627 */     if (this.startProfiling) {
/*  628 */       this.startProfiling = false;
/*  629 */       this.theProfiler.profilingEnabled = true;
/*  630 */       this.theProfiler.clearProfiling();
/*      */     } 
/*      */     
/*  633 */     this.theProfiler.startSection("root");
/*  634 */     updateTimeLightAndEntities();
/*      */     
/*  636 */     if (i - this.nanoTimeSinceStatusRefresh >= 5000000000L) {
/*  637 */       this.nanoTimeSinceStatusRefresh = i;
/*  638 */       this.statusResponse.setPlayerCountData(new ServerStatusResponse.PlayerCountData(getMaxPlayers(), getCurrentPlayerCount()));
/*  639 */       GameProfile[] agameprofile = new GameProfile[Math.min(getCurrentPlayerCount(), 12)];
/*  640 */       int j = MathHelper.getRandomIntegerInRange(this.random, 0, getCurrentPlayerCount() - agameprofile.length);
/*      */       
/*  642 */       for (int k = 0; k < agameprofile.length; k++) {
/*  643 */         agameprofile[k] = ((EntityPlayerMP)this.serverConfigManager.getPlayerList().get(j + k)).getGameProfile();
/*      */       }
/*      */       
/*  646 */       Collections.shuffle(Arrays.asList((Object[])agameprofile));
/*  647 */       this.statusResponse.getPlayerCountData().setPlayers(agameprofile);
/*      */     } 
/*      */     
/*  650 */     if (this.tickCounter % 900 == 0) {
/*  651 */       this.theProfiler.startSection("save");
/*  652 */       this.serverConfigManager.saveAllPlayerData();
/*  653 */       saveAllWorlds(true);
/*  654 */       this.theProfiler.endSection();
/*      */     } 
/*      */     
/*  657 */     this.theProfiler.startSection("tallying");
/*  658 */     this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - i;
/*  659 */     this.theProfiler.endSection();
/*  660 */     this.theProfiler.startSection("snooper");
/*      */     
/*  662 */     if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100) {
/*  663 */       this.usageSnooper.startSnooper();
/*      */     }
/*      */     
/*  666 */     if (this.tickCounter % 6000 == 0) {
/*  667 */       this.usageSnooper.addMemoryStatsToSnooper();
/*      */     }
/*      */     
/*  670 */     this.theProfiler.endSection();
/*  671 */     this.theProfiler.endSection();
/*      */   }
/*      */   
/*      */   public void updateTimeLightAndEntities() {
/*  675 */     this.theProfiler.startSection("jobs");
/*      */     
/*  677 */     synchronized (this.futureTaskQueue) {
/*  678 */       while (!this.futureTaskQueue.isEmpty()) {
/*  679 */         Util.runTask(this.futureTaskQueue.poll(), logger);
/*      */       }
/*      */     } 
/*      */     
/*  683 */     this.theProfiler.endStartSection("levels");
/*      */     
/*  685 */     for (int j = 0; j < this.worldServers.length; j++) {
/*  686 */       long i = System.nanoTime();
/*      */       
/*  688 */       if (j == 0 || getAllowNether()) {
/*  689 */         WorldServer worldserver = this.worldServers[j];
/*  690 */         this.theProfiler.startSection(worldserver.getWorldInfo().getWorldName());
/*      */         
/*  692 */         if (this.tickCounter % 20 == 0) {
/*  693 */           this.theProfiler.startSection("timeSync");
/*  694 */           this.serverConfigManager.sendPacketToAllPlayersInDimension((Packet)new S03PacketTimeUpdate(worldserver.getTotalWorldTime(), worldserver.getWorldTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")), worldserver.provider.getDimensionId());
/*  695 */           this.theProfiler.endSection();
/*      */         } 
/*      */         
/*  698 */         this.theProfiler.startSection("tick");
/*      */         
/*      */         try {
/*  701 */           worldserver.tick();
/*  702 */         } catch (Throwable throwable1) {
/*  703 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Exception ticking world");
/*  704 */           worldserver.addWorldInfoToCrashReport(crashreport);
/*  705 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */         
/*      */         try {
/*  709 */           worldserver.updateEntities();
/*  710 */         } catch (Throwable throwable) {
/*  711 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Exception ticking world entities");
/*  712 */           worldserver.addWorldInfoToCrashReport(crashreport1);
/*  713 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */         
/*  716 */         this.theProfiler.endSection();
/*  717 */         this.theProfiler.startSection("tracker");
/*  718 */         worldserver.getEntityTracker().updateTrackedEntities();
/*  719 */         this.theProfiler.endSection();
/*  720 */         this.theProfiler.endSection();
/*      */       } 
/*      */       
/*  723 */       this.timeOfLastDimensionTick[j][this.tickCounter % 100] = System.nanoTime() - i;
/*      */     } 
/*      */     
/*  726 */     this.theProfiler.endStartSection("connection");
/*  727 */     getNetworkSystem().networkTick();
/*  728 */     this.theProfiler.endStartSection("players");
/*  729 */     this.serverConfigManager.onTick();
/*  730 */     this.theProfiler.endStartSection("tickables");
/*      */     
/*  732 */     for (int k = 0; k < this.playersOnline.size(); k++) {
/*  733 */       ((ITickable)this.playersOnline.get(k)).update();
/*      */     }
/*      */     
/*  736 */     this.theProfiler.endSection();
/*      */   }
/*      */   
/*      */   public boolean getAllowNether() {
/*  740 */     return true;
/*      */   }
/*      */   
/*      */   public void startServerThread() {
/*  744 */     this.serverThread = new Thread(this, "Server thread");
/*  745 */     this.serverThread.start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getFile(String fileName) {
/*  752 */     return new File(getDataDirectory(), fileName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void logWarning(String msg) {
/*  759 */     logger.warn(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldServer worldServerForDimension(int dimension) {
/*  766 */     return (dimension == -1) ? this.worldServers[1] : ((dimension == 1) ? this.worldServers[2] : this.worldServers[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMinecraftVersion() {
/*  773 */     return "1.8.9";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCurrentPlayerCount() {
/*  780 */     return this.serverConfigManager.getCurrentPlayerCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxPlayers() {
/*  787 */     return this.serverConfigManager.getMaxPlayers();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAllUsernames() {
/*  794 */     return this.serverConfigManager.getAllUsernames();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameProfile[] getGameProfiles() {
/*  801 */     return this.serverConfigManager.getAllProfiles();
/*      */   }
/*      */   
/*      */   public String getServerModName() {
/*  805 */     return "vanilla";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReport addServerInfoToCrashReport(CrashReport report) {
/*  812 */     report.getCategory().addCrashSectionCallable("Profiler Position", new Callable<String>() {
/*      */           public String call() throws Exception {
/*  814 */             return MinecraftServer.this.theProfiler.profilingEnabled ? MinecraftServer.this.theProfiler.getNameOfLastSection() : "N/A (disabled)";
/*      */           }
/*      */         });
/*      */     
/*  818 */     if (this.serverConfigManager != null) {
/*  819 */       report.getCategory().addCrashSectionCallable("Player Count", new Callable<String>() {
/*      */             public String call() {
/*  821 */               return String.valueOf(MinecraftServer.this.serverConfigManager.getCurrentPlayerCount()) + " / " + MinecraftServer.this.serverConfigManager.getMaxPlayers() + "; " + MinecraftServer.this.serverConfigManager.getPlayerList();
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*  826 */     return report;
/*      */   }
/*      */   
/*      */   public List<String> getTabCompletions(ICommandSender sender, String input, BlockPos pos) {
/*  830 */     List<String> list = Lists.newArrayList();
/*      */     
/*  832 */     if (input.startsWith("/")) {
/*  833 */       input = input.substring(1);
/*  834 */       boolean flag = !input.contains(" ");
/*  835 */       List<String> list1 = this.commandManager.getTabCompletionOptions(sender, input, pos);
/*      */       
/*  837 */       if (list1 != null) {
/*  838 */         for (String s2 : list1) {
/*  839 */           if (flag) {
/*  840 */             list.add("/" + s2); continue;
/*      */           } 
/*  842 */           list.add(s2);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  847 */       return list;
/*      */     } 
/*  849 */     String[] astring = input.split(" ", -1);
/*  850 */     String s = astring[astring.length - 1]; byte b; int i;
/*      */     String[] arrayOfString1;
/*  852 */     for (i = (arrayOfString1 = this.serverConfigManager.getAllUsernames()).length, b = 0; b < i; ) { String s1 = arrayOfString1[b];
/*  853 */       if (CommandBase.doesStringStartWith(s, s1)) {
/*  854 */         list.add(s1);
/*      */       }
/*      */       b++; }
/*      */     
/*  858 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MinecraftServer getServer() {
/*  866 */     return mcServer;
/*      */   }
/*      */   
/*      */   public boolean isAnvilFileSet() {
/*  870 */     return (this.anvilFile != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  877 */     return "Server";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(IChatComponent component) {
/*  884 */     logger.info(component.getUnformattedText());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  891 */     return true;
/*      */   }
/*      */   
/*      */   public ICommandManager getCommandManager() {
/*  895 */     return this.commandManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyPair getKeyPair() {
/*  902 */     return this.serverKeyPair;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getServerOwner() {
/*  909 */     return this.serverOwner;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerOwner(String owner) {
/*  916 */     this.serverOwner = owner;
/*      */   }
/*      */   
/*      */   public boolean isSinglePlayer() {
/*  920 */     return (this.serverOwner != null);
/*      */   }
/*      */   
/*      */   public String getFolderName() {
/*  924 */     return this.folderName;
/*      */   }
/*      */   
/*      */   public void setFolderName(String name) {
/*  928 */     this.folderName = name;
/*      */   }
/*      */   
/*      */   public void setWorldName(String p_71246_1_) {
/*  932 */     this.worldName = p_71246_1_;
/*      */   }
/*      */   
/*      */   public String getWorldName() {
/*  936 */     return this.worldName;
/*      */   }
/*      */   
/*      */   public void setKeyPair(KeyPair keyPair) {
/*  940 */     this.serverKeyPair = keyPair;
/*      */   }
/*      */   
/*      */   public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {
/*  944 */     for (int i = 0; i < this.worldServers.length; i++) {
/*  945 */       WorldServer worldServer = this.worldServers[i];
/*      */       
/*  947 */       if (worldServer != null) {
/*  948 */         if (worldServer.getWorldInfo().isHardcoreModeEnabled()) {
/*  949 */           worldServer.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
/*  950 */           worldServer.setAllowedSpawnTypes(true, true);
/*  951 */         } else if (isSinglePlayer()) {
/*  952 */           worldServer.getWorldInfo().setDifficulty(difficulty);
/*  953 */           worldServer.setAllowedSpawnTypes((worldServer.getDifficulty() != EnumDifficulty.PEACEFUL), true);
/*      */         } else {
/*  955 */           worldServer.getWorldInfo().setDifficulty(difficulty);
/*  956 */           worldServer.setAllowedSpawnTypes(allowSpawnMonsters(), this.canSpawnAnimals);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean allowSpawnMonsters() {
/*  963 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDemo() {
/*  970 */     return this.isDemo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDemo(boolean demo) {
/*  977 */     this.isDemo = demo;
/*      */   }
/*      */   
/*      */   public void canCreateBonusChest(boolean enable) {
/*  981 */     this.enableBonusChest = enable;
/*      */   }
/*      */   
/*      */   public ISaveFormat getActiveAnvilConverter() {
/*  985 */     return this.anvilConverterForAnvilFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteWorldAndStopServer() {
/*  993 */     this.worldIsBeingDeleted = true;
/*  994 */     getActiveAnvilConverter().flushCache();
/*      */     
/*  996 */     for (int i = 0; i < this.worldServers.length; i++) {
/*  997 */       WorldServer worldserver = this.worldServers[i];
/*      */       
/*  999 */       if (worldserver != null) {
/* 1000 */         worldserver.flush();
/*      */       }
/*      */     } 
/*      */     
/* 1004 */     getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
/* 1005 */     initiateShutdown();
/*      */   }
/*      */   
/*      */   public String getResourcePackUrl() {
/* 1009 */     return this.resourcePackUrl;
/*      */   }
/*      */   
/*      */   public String getResourcePackHash() {
/* 1013 */     return this.resourcePackHash;
/*      */   }
/*      */   
/*      */   public void setResourcePack(String url, String hash) {
/* 1017 */     this.resourcePackUrl = url;
/* 1018 */     this.resourcePackHash = hash;
/*      */   }
/*      */   
/*      */   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
/* 1022 */     playerSnooper.addClientStat("whitelist_enabled", Boolean.valueOf(false));
/* 1023 */     playerSnooper.addClientStat("whitelist_count", Integer.valueOf(0));
/*      */     
/* 1025 */     if (this.serverConfigManager != null) {
/* 1026 */       playerSnooper.addClientStat("players_current", Integer.valueOf(getCurrentPlayerCount()));
/* 1027 */       playerSnooper.addClientStat("players_max", Integer.valueOf(getMaxPlayers()));
/* 1028 */       playerSnooper.addClientStat("players_seen", Integer.valueOf((this.serverConfigManager.getAvailablePlayerDat()).length));
/*      */     } 
/*      */     
/* 1031 */     playerSnooper.addClientStat("uses_auth", Boolean.valueOf(this.onlineMode));
/* 1032 */     playerSnooper.addClientStat("gui_state", getGuiEnabled() ? "enabled" : "disabled");
/* 1033 */     playerSnooper.addClientStat("run_time", Long.valueOf((getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
/* 1034 */     playerSnooper.addClientStat("avg_tick_ms", Integer.valueOf((int)(MathHelper.average(this.tickTimeArray) * 1.0E-6D)));
/* 1035 */     int i = 0;
/*      */     
/* 1037 */     if (this.worldServers != null) {
/* 1038 */       for (int j = 0; j < this.worldServers.length; j++) {
/* 1039 */         if (this.worldServers[j] != null) {
/* 1040 */           WorldServer worldserver = this.worldServers[j];
/* 1041 */           WorldInfo worldinfo = worldserver.getWorldInfo();
/* 1042 */           playerSnooper.addClientStat("world[" + i + "][dimension]", Integer.valueOf(worldserver.provider.getDimensionId()));
/* 1043 */           playerSnooper.addClientStat("world[" + i + "][mode]", worldinfo.getGameType());
/* 1044 */           playerSnooper.addClientStat("world[" + i + "][difficulty]", worldserver.getDifficulty());
/* 1045 */           playerSnooper.addClientStat("world[" + i + "][hardcore]", Boolean.valueOf(worldinfo.isHardcoreModeEnabled()));
/* 1046 */           playerSnooper.addClientStat("world[" + i + "][generator_name]", worldinfo.getTerrainType().getWorldTypeName());
/* 1047 */           playerSnooper.addClientStat("world[" + i + "][generator_version]", Integer.valueOf(worldinfo.getTerrainType().getGeneratorVersion()));
/* 1048 */           playerSnooper.addClientStat("world[" + i + "][height]", Integer.valueOf(this.buildLimit));
/* 1049 */           playerSnooper.addClientStat("world[" + i + "][chunks_loaded]", Integer.valueOf(worldserver.getChunkProvider().getLoadedChunkCount()));
/* 1050 */           i++;
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1055 */     playerSnooper.addClientStat("worlds", Integer.valueOf(i));
/*      */   }
/*      */   
/*      */   public void addServerTypeToSnooper(PlayerUsageSnooper playerSnooper) {
/* 1059 */     playerSnooper.addStatToSnooper("singleplayer", Boolean.valueOf(isSinglePlayer()));
/* 1060 */     playerSnooper.addStatToSnooper("server_brand", getServerModName());
/* 1061 */     playerSnooper.addStatToSnooper("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
/* 1062 */     playerSnooper.addStatToSnooper("dedicated", Boolean.valueOf(isDedicatedServer()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSnooperEnabled() {
/* 1069 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerInOnlineMode() {
/* 1075 */     return this.onlineMode;
/*      */   }
/*      */   
/*      */   public void setOnlineMode(boolean online) {
/* 1079 */     this.onlineMode = online;
/*      */   }
/*      */   
/*      */   public boolean getCanSpawnAnimals() {
/* 1083 */     return this.canSpawnAnimals;
/*      */   }
/*      */   
/*      */   public void setCanSpawnAnimals(boolean spawnAnimals) {
/* 1087 */     this.canSpawnAnimals = spawnAnimals;
/*      */   }
/*      */   
/*      */   public boolean getCanSpawnNPCs() {
/* 1091 */     return this.canSpawnNPCs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCanSpawnNPCs(boolean spawnNpcs) {
/* 1101 */     this.canSpawnNPCs = spawnNpcs;
/*      */   }
/*      */   
/*      */   public boolean isPVPEnabled() {
/* 1105 */     return this.pvpEnabled;
/*      */   }
/*      */   
/*      */   public void setAllowPvp(boolean allowPvp) {
/* 1109 */     this.pvpEnabled = allowPvp;
/*      */   }
/*      */   
/*      */   public boolean isFlightAllowed() {
/* 1113 */     return this.allowFlight;
/*      */   }
/*      */   
/*      */   public void setAllowFlight(boolean allow) {
/* 1117 */     this.allowFlight = allow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMOTD() {
/* 1126 */     return this.motd;
/*      */   }
/*      */   
/*      */   public void setMOTD(String motdIn) {
/* 1130 */     this.motd = motdIn;
/*      */   }
/*      */   
/*      */   public int getBuildLimit() {
/* 1134 */     return this.buildLimit;
/*      */   }
/*      */   
/*      */   public void setBuildLimit(int maxBuildHeight) {
/* 1138 */     this.buildLimit = maxBuildHeight;
/*      */   }
/*      */   
/*      */   public boolean isServerStopped() {
/* 1142 */     return this.serverStopped;
/*      */   }
/*      */   
/*      */   public ServerConfigurationManager getConfigurationManager() {
/* 1146 */     return this.serverConfigManager;
/*      */   }
/*      */   
/*      */   public void setConfigManager(ServerConfigurationManager configManager) {
/* 1150 */     this.serverConfigManager = configManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(WorldSettings.GameType gameMode) {
/* 1157 */     for (int i = 0; i < this.worldServers.length; i++) {
/* 1158 */       (getServer()).worldServers[i].getWorldInfo().setGameType(gameMode);
/*      */     }
/*      */   }
/*      */   
/*      */   public NetworkSystem getNetworkSystem() {
/* 1163 */     return this.networkSystem;
/*      */   }
/*      */   
/*      */   public boolean serverIsInRunLoop() {
/* 1167 */     return this.serverIsRunning;
/*      */   }
/*      */   
/*      */   public boolean getGuiEnabled() {
/* 1171 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTickCounter() {
/* 1180 */     return this.tickCounter;
/*      */   }
/*      */   
/*      */   public void enableProfiling() {
/* 1184 */     this.startProfiling = true;
/*      */   }
/*      */   
/*      */   public PlayerUsageSnooper getPlayerUsageSnooper() {
/* 1188 */     return this.usageSnooper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 1196 */     return BlockPos.ORIGIN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getPositionVector() {
/* 1204 */     return new Vec3(0.0D, 0.0D, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public World getEntityWorld() {
/* 1212 */     return (World)this.worldServers[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getCommandSenderEntity() {
/* 1219 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSpawnProtectionSize() {
/* 1226 */     return 16;
/*      */   }
/*      */   
/*      */   public boolean isBlockProtected(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/* 1230 */     return false;
/*      */   }
/*      */   
/*      */   public boolean getForceGamemode() {
/* 1234 */     return this.isGamemodeForced;
/*      */   }
/*      */   
/*      */   public Proxy getServerProxy() {
/* 1238 */     return this.serverProxy;
/*      */   }
/*      */   
/*      */   public static long getCurrentTimeMillis() {
/* 1242 */     return System.currentTimeMillis();
/*      */   }
/*      */   
/*      */   public int getMaxPlayerIdleMinutes() {
/* 1246 */     return this.maxPlayerIdleMinutes;
/*      */   }
/*      */   
/*      */   public void setPlayerIdleTimeout(int idleTimeout) {
/* 1250 */     this.maxPlayerIdleMinutes = idleTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/* 1257 */     return (IChatComponent)new ChatComponentText(getName());
/*      */   }
/*      */   
/*      */   public boolean isAnnouncingPlayerAchievements() {
/* 1261 */     return true;
/*      */   }
/*      */   
/*      */   public MinecraftSessionService getMinecraftSessionService() {
/* 1265 */     return this.sessionService;
/*      */   }
/*      */   
/*      */   public GameProfileRepository getGameProfileRepository() {
/* 1269 */     return this.profileRepo;
/*      */   }
/*      */   
/*      */   public PlayerProfileCache getPlayerProfileCache() {
/* 1273 */     return this.profileCache;
/*      */   }
/*      */   
/*      */   public ServerStatusResponse getServerStatusResponse() {
/* 1277 */     return this.statusResponse;
/*      */   }
/*      */   
/*      */   public void refreshStatusNextTick() {
/* 1281 */     this.nanoTimeSinceStatusRefresh = 0L; } public Entity getEntityFromUuid(UUID uuid) {
/*      */     byte b;
/*      */     int i;
/*      */     WorldServer[] arrayOfWorldServer;
/* 1285 */     for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/* 1286 */       if (worldserver != null) {
/* 1287 */         Entity entity = worldserver.getEntityFromUuid(uuid);
/*      */         
/* 1289 */         if (entity != null) {
/* 1290 */           return entity;
/*      */         }
/*      */       } 
/*      */       b++; }
/*      */     
/* 1295 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 1302 */     return (getServer()).worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCommandStat(CommandResultStats.Type type, int amount) {}
/*      */   
/*      */   public int getMaxWorldSize() {
/* 1309 */     return 29999984;
/*      */   }
/*      */   
/*      */   public <V> ListenableFuture<V> callFromMainThread(Callable<V> callable) {
/* 1313 */     Validate.notNull(callable);
/*      */     
/* 1315 */     if (!isCallingFromMinecraftThread() && !isServerStopped()) {
/* 1316 */       ListenableFutureTask<V> listenablefuturetask = ListenableFutureTask.create(callable);
/*      */       
/* 1318 */       synchronized (this.futureTaskQueue) {
/* 1319 */         this.futureTaskQueue.add(listenablefuturetask);
/* 1320 */         return (ListenableFuture<V>)listenablefuturetask;
/*      */       } 
/*      */     } 
/*      */     try {
/* 1324 */       return Futures.immediateFuture(callable.call());
/* 1325 */     } catch (Exception exception) {
/* 1326 */       return (ListenableFuture<V>)Futures.immediateFailedCheckedFuture(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 1332 */     Validate.notNull(runnableToSchedule);
/* 1333 */     return callFromMainThread(Executors.callable(runnableToSchedule));
/*      */   }
/*      */   
/*      */   public boolean isCallingFromMinecraftThread() {
/* 1337 */     return (Thread.currentThread() == this.serverThread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNetworkCompressionTreshold() {
/* 1344 */     return 256;
/*      */   }
/*      */   
/*      */   protected abstract boolean startServer() throws IOException;
/*      */   
/*      */   public abstract boolean canStructuresSpawn();
/*      */   
/*      */   public abstract WorldSettings.GameType getGameType();
/*      */   
/*      */   public abstract EnumDifficulty getDifficulty();
/*      */   
/*      */   public abstract boolean isHardcore();
/*      */   
/*      */   public abstract int getOpPermissionLevel();
/*      */   
/*      */   public abstract boolean shouldBroadcastRconToOps();
/*      */   
/*      */   public abstract boolean shouldBroadcastConsoleToOps();
/*      */   
/*      */   public abstract boolean isDedicatedServer();
/*      */   
/*      */   public abstract boolean shouldUseNativeTransport();
/*      */   
/*      */   public abstract boolean isCommandBlockEnabled();
/*      */   
/*      */   public abstract String shareToLAN(WorldSettings.GameType paramGameType, boolean paramBoolean);
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\server\MinecraftServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */