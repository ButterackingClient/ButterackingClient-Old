/*      */ package net.minecraft.client.network;
/*      */ 
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.util.concurrent.FutureCallback;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.client.ClientBrandRetriever;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.audio.GuardianSound;
/*      */ import net.minecraft.client.audio.ISound;
/*      */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.GuiChat;
/*      */ import net.minecraft.client.gui.GuiDisconnected;
/*      */ import net.minecraft.client.gui.GuiDownloadTerrain;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.GuiMerchant;
/*      */ import net.minecraft.client.gui.GuiMultiplayer;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiScreenBook;
/*      */ import net.minecraft.client.gui.GuiScreenDemo;
/*      */ import net.minecraft.client.gui.GuiScreenRealmsProxy;
/*      */ import net.minecraft.client.gui.GuiWinGame;
/*      */ import net.minecraft.client.gui.GuiYesNo;
/*      */ import net.minecraft.client.gui.GuiYesNoCallback;
/*      */ import net.minecraft.client.gui.IProgressMeter;
/*      */ import net.minecraft.client.gui.inventory.GuiContainerCreative;
/*      */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*      */ import net.minecraft.client.multiplayer.ServerData;
/*      */ import net.minecraft.client.multiplayer.ServerList;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.EntityFX;
/*      */ import net.minecraft.client.particle.EntityPickupFX;
/*      */ import net.minecraft.client.player.inventory.ContainerLocalMenu;
/*      */ import net.minecraft.client.player.inventory.LocalBlockIntercommunication;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.stream.Metadata;
/*      */ import net.minecraft.client.stream.MetadataAchievement;
/*      */ import net.minecraft.client.stream.MetadataCombat;
/*      */ import net.minecraft.client.stream.MetadataPlayerDeath;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.entity.DataWatcher;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLeashKnot;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.NpcMerchant;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*      */ import net.minecraft.entity.ai.attributes.IAttribute;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.item.EntityArmorStand;
/*      */ import net.minecraft.entity.item.EntityBoat;
/*      */ import net.minecraft.entity.item.EntityEnderCrystal;
/*      */ import net.minecraft.entity.item.EntityEnderEye;
/*      */ import net.minecraft.entity.item.EntityEnderPearl;
/*      */ import net.minecraft.entity.item.EntityExpBottle;
/*      */ import net.minecraft.entity.item.EntityFallingBlock;
/*      */ import net.minecraft.entity.item.EntityFireworkRocket;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.item.EntityPainting;
/*      */ import net.minecraft.entity.item.EntityTNTPrimed;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.monster.EntityGuardian;
/*      */ import net.minecraft.entity.passive.EntityHorse;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.entity.projectile.EntityArrow;
/*      */ import net.minecraft.entity.projectile.EntityEgg;
/*      */ import net.minecraft.entity.projectile.EntityFishHook;
/*      */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*      */ import net.minecraft.entity.projectile.EntityPotion;
/*      */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*      */ import net.minecraft.entity.projectile.EntitySnowball;
/*      */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.AnimalChest;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryBasic;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemMap;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.network.INetHandler;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.PacketBuffer;
/*      */ import net.minecraft.network.PacketThreadUtil;
/*      */ import net.minecraft.network.play.INetHandlerPlayClient;
/*      */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*      */ import net.minecraft.network.play.client.C03PacketPlayer;
/*      */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*      */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*      */ import net.minecraft.network.play.client.C19PacketResourcePackStatus;
/*      */ import net.minecraft.network.play.server.S00PacketKeepAlive;
/*      */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*      */ import net.minecraft.network.play.server.S02PacketChat;
/*      */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*      */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*      */ import net.minecraft.network.play.server.S05PacketSpawnPosition;
/*      */ import net.minecraft.network.play.server.S06PacketUpdateHealth;
/*      */ import net.minecraft.network.play.server.S07PacketRespawn;
/*      */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*      */ import net.minecraft.network.play.server.S09PacketHeldItemChange;
/*      */ import net.minecraft.network.play.server.S0APacketUseBed;
/*      */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*      */ import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
/*      */ import net.minecraft.network.play.server.S0DPacketCollectItem;
/*      */ import net.minecraft.network.play.server.S0EPacketSpawnObject;
/*      */ import net.minecraft.network.play.server.S0FPacketSpawnMob;
/*      */ import net.minecraft.network.play.server.S10PacketSpawnPainting;
/*      */ import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
/*      */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*      */ import net.minecraft.network.play.server.S13PacketDestroyEntities;
/*      */ import net.minecraft.network.play.server.S14PacketEntity;
/*      */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*      */ import net.minecraft.network.play.server.S19PacketEntityHeadLook;
/*      */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*      */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*      */ import net.minecraft.network.play.server.S1CPacketEntityMetadata;
/*      */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*      */ import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
/*      */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*      */ import net.minecraft.network.play.server.S20PacketEntityProperties;
/*      */ import net.minecraft.network.play.server.S21PacketChunkData;
/*      */ import net.minecraft.network.play.server.S22PacketMultiBlockChange;
/*      */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*      */ import net.minecraft.network.play.server.S24PacketBlockAction;
/*      */ import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
/*      */ import net.minecraft.network.play.server.S26PacketMapChunkBulk;
/*      */ import net.minecraft.network.play.server.S27PacketExplosion;
/*      */ import net.minecraft.network.play.server.S28PacketEffect;
/*      */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*      */ import net.minecraft.network.play.server.S2APacketParticles;
/*      */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
/*      */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/*      */ import net.minecraft.network.play.server.S2EPacketCloseWindow;
/*      */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*      */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*      */ import net.minecraft.network.play.server.S31PacketWindowProperty;
/*      */ import net.minecraft.network.play.server.S32PacketConfirmTransaction;
/*      */ import net.minecraft.network.play.server.S33PacketUpdateSign;
/*      */ import net.minecraft.network.play.server.S34PacketMaps;
/*      */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*      */ import net.minecraft.network.play.server.S36PacketSignEditorOpen;
/*      */ import net.minecraft.network.play.server.S37PacketStatistics;
/*      */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*      */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*      */ import net.minecraft.network.play.server.S3APacketTabComplete;
/*      */ import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
/*      */ import net.minecraft.network.play.server.S3CPacketUpdateScore;
/*      */ import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
/*      */ import net.minecraft.network.play.server.S3EPacketTeams;
/*      */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/*      */ import net.minecraft.network.play.server.S40PacketDisconnect;
/*      */ import net.minecraft.network.play.server.S41PacketServerDifficulty;
/*      */ import net.minecraft.network.play.server.S42PacketCombatEvent;
/*      */ import net.minecraft.network.play.server.S43PacketCamera;
/*      */ import net.minecraft.network.play.server.S44PacketWorldBorder;
/*      */ import net.minecraft.network.play.server.S45PacketTitle;
/*      */ import net.minecraft.network.play.server.S46PacketSetCompressionLevel;
/*      */ import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
/*      */ import net.minecraft.network.play.server.S48PacketResourcePackSend;
/*      */ import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.realms.DisconnectedRealmsScreen;
/*      */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.stats.Achievement;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.StringUtils;
/*      */ import net.minecraft.village.MerchantRecipeList;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.IInteractionObject;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.storage.MapData;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class NetHandlerPlayClient
/*      */   implements INetHandlerPlayClient
/*      */ {
/*  218 */   private static final Logger logger = LogManager.getLogger();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final NetworkManager netManager;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final GameProfile profile;
/*      */ 
/*      */ 
/*      */   
/*      */   private final GuiScreen guiScreenServer;
/*      */ 
/*      */ 
/*      */   
/*      */   private Minecraft gameController;
/*      */ 
/*      */ 
/*      */   
/*      */   private WorldClient clientWorldController;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean doneLoadingTerrain;
/*      */ 
/*      */ 
/*      */   
/*  248 */   private final Map<UUID, NetworkPlayerInfo> playerInfoMap = Maps.newHashMap();
/*  249 */   public int currentServerMaxPlayers = 20;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean field_147308_k = false;
/*      */ 
/*      */   
/*  256 */   private final Random avRandomizer = new Random();
/*      */   
/*      */   public NetHandlerPlayClient(Minecraft mcIn, GuiScreen p_i46300_2_, NetworkManager p_i46300_3_, GameProfile p_i46300_4_) {
/*  259 */     this.gameController = mcIn;
/*  260 */     this.guiScreenServer = p_i46300_2_;
/*  261 */     this.netManager = p_i46300_3_;
/*  262 */     this.profile = p_i46300_4_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanup() {
/*  269 */     this.clientWorldController = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleJoinGame(S01PacketJoinGame packetIn) {
/*  277 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  278 */     this.gameController.playerController = new PlayerControllerMP(this.gameController, this);
/*  279 */     this.clientWorldController = new WorldClient(this, new WorldSettings(0L, packetIn.getGameType(), false, packetIn.isHardcoreMode(), packetIn.getWorldType()), packetIn.getDimension(), packetIn.getDifficulty(), this.gameController.mcProfiler);
/*  280 */     this.gameController.gameSettings.hideGUI = packetIn.getDifficulty();
/*  281 */     this.gameController.loadWorld(this.clientWorldController);
/*  282 */     this.gameController.thePlayer.dimension = packetIn.getDimension();
/*  283 */     this.gameController.displayGuiScreen((GuiScreen)new GuiDownloadTerrain(this));
/*  284 */     this.gameController.thePlayer.setEntityId(packetIn.getEntityId());
/*  285 */     this.currentServerMaxPlayers = packetIn.getMaxPlayers();
/*  286 */     this.gameController.thePlayer.setReducedDebug(packetIn.isReducedDebugInfo());
/*  287 */     this.gameController.playerController.setGameType(packetIn.getGameType());
/*  288 */     this.gameController.gameSettings.sendSettingsToServer();
/*  289 */     this.netManager.sendPacket((Packet)new C17PacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnObject(S0EPacketSpawnObject packetIn) {
/*      */     EntityFallingBlock entityFallingBlock;
/*  296 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  297 */     double d0 = packetIn.getX() / 32.0D;
/*  298 */     double d1 = packetIn.getY() / 32.0D;
/*  299 */     double d2 = packetIn.getZ() / 32.0D;
/*  300 */     Entity entity = null;
/*      */     
/*  302 */     if (packetIn.getType() == 10) {
/*  303 */       EntityMinecart entityMinecart = EntityMinecart.getMinecart((World)this.clientWorldController, d0, d1, d2, EntityMinecart.EnumMinecartType.byNetworkID(packetIn.func_149009_m()));
/*  304 */     } else if (packetIn.getType() == 90) {
/*  305 */       Entity entity1 = this.clientWorldController.getEntityByID(packetIn.func_149009_m());
/*      */       
/*  307 */       if (entity1 instanceof EntityPlayer) {
/*  308 */         EntityFishHook entityFishHook = new EntityFishHook((World)this.clientWorldController, d0, d1, d2, (EntityPlayer)entity1);
/*      */       }
/*      */       
/*  311 */       packetIn.func_149002_g(0);
/*  312 */     } else if (packetIn.getType() == 60) {
/*  313 */       EntityArrow entityArrow = new EntityArrow((World)this.clientWorldController, d0, d1, d2);
/*  314 */     } else if (packetIn.getType() == 61) {
/*  315 */       EntitySnowball entitySnowball = new EntitySnowball((World)this.clientWorldController, d0, d1, d2);
/*  316 */     } else if (packetIn.getType() == 71) {
/*  317 */       EntityItemFrame entityItemFrame = new EntityItemFrame((World)this.clientWorldController, new BlockPos(MathHelper.floor_double(d0), MathHelper.floor_double(d1), MathHelper.floor_double(d2)), EnumFacing.getHorizontal(packetIn.func_149009_m()));
/*  318 */       packetIn.func_149002_g(0);
/*  319 */     } else if (packetIn.getType() == 77) {
/*  320 */       EntityLeashKnot entityLeashKnot = new EntityLeashKnot((World)this.clientWorldController, new BlockPos(MathHelper.floor_double(d0), MathHelper.floor_double(d1), MathHelper.floor_double(d2)));
/*  321 */       packetIn.func_149002_g(0);
/*  322 */     } else if (packetIn.getType() == 65) {
/*  323 */       EntityEnderPearl entityEnderPearl = new EntityEnderPearl((World)this.clientWorldController, d0, d1, d2);
/*  324 */     } else if (packetIn.getType() == 72) {
/*  325 */       EntityEnderEye entityEnderEye = new EntityEnderEye((World)this.clientWorldController, d0, d1, d2);
/*  326 */     } else if (packetIn.getType() == 76) {
/*  327 */       EntityFireworkRocket entityFireworkRocket = new EntityFireworkRocket((World)this.clientWorldController, d0, d1, d2, null);
/*  328 */     } else if (packetIn.getType() == 63) {
/*  329 */       EntityLargeFireball entityLargeFireball = new EntityLargeFireball((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  330 */       packetIn.func_149002_g(0);
/*  331 */     } else if (packetIn.getType() == 64) {
/*  332 */       EntitySmallFireball entitySmallFireball = new EntitySmallFireball((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  333 */       packetIn.func_149002_g(0);
/*  334 */     } else if (packetIn.getType() == 66) {
/*  335 */       EntityWitherSkull entityWitherSkull = new EntityWitherSkull((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  336 */       packetIn.func_149002_g(0);
/*  337 */     } else if (packetIn.getType() == 62) {
/*  338 */       EntityEgg entityEgg = new EntityEgg((World)this.clientWorldController, d0, d1, d2);
/*  339 */     } else if (packetIn.getType() == 73) {
/*  340 */       EntityPotion entityPotion = new EntityPotion((World)this.clientWorldController, d0, d1, d2, packetIn.func_149009_m());
/*  341 */       packetIn.func_149002_g(0);
/*  342 */     } else if (packetIn.getType() == 75) {
/*  343 */       EntityExpBottle entityExpBottle = new EntityExpBottle((World)this.clientWorldController, d0, d1, d2);
/*  344 */       packetIn.func_149002_g(0);
/*  345 */     } else if (packetIn.getType() == 1) {
/*  346 */       EntityBoat entityBoat = new EntityBoat((World)this.clientWorldController, d0, d1, d2);
/*  347 */     } else if (packetIn.getType() == 50) {
/*  348 */       EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed((World)this.clientWorldController, d0, d1, d2, null);
/*  349 */     } else if (packetIn.getType() == 78) {
/*  350 */       EntityArmorStand entityArmorStand = new EntityArmorStand((World)this.clientWorldController, d0, d1, d2);
/*  351 */     } else if (packetIn.getType() == 51) {
/*  352 */       EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal((World)this.clientWorldController, d0, d1, d2);
/*  353 */     } else if (packetIn.getType() == 2) {
/*  354 */       EntityItem entityItem = new EntityItem((World)this.clientWorldController, d0, d1, d2);
/*  355 */     } else if (packetIn.getType() == 70) {
/*  356 */       entityFallingBlock = new EntityFallingBlock((World)this.clientWorldController, d0, d1, d2, Block.getStateById(packetIn.func_149009_m() & 0xFFFF));
/*  357 */       packetIn.func_149002_g(0);
/*      */     } 
/*      */     
/*  360 */     if (entityFallingBlock != null) {
/*  361 */       ((Entity)entityFallingBlock).serverPosX = packetIn.getX();
/*  362 */       ((Entity)entityFallingBlock).serverPosY = packetIn.getY();
/*  363 */       ((Entity)entityFallingBlock).serverPosZ = packetIn.getZ();
/*  364 */       ((Entity)entityFallingBlock).rotationPitch = (packetIn.getPitch() * 360) / 256.0F;
/*  365 */       ((Entity)entityFallingBlock).rotationYaw = (packetIn.getYaw() * 360) / 256.0F;
/*  366 */       Entity[] aentity = entityFallingBlock.getParts();
/*      */       
/*  368 */       if (aentity != null) {
/*  369 */         int i = packetIn.getEntityID() - entityFallingBlock.getEntityId();
/*      */         
/*  371 */         for (int j = 0; j < aentity.length; j++) {
/*  372 */           aentity[j].setEntityId(aentity[j].getEntityId() + i);
/*      */         }
/*      */       } 
/*      */       
/*  376 */       entityFallingBlock.setEntityId(packetIn.getEntityID());
/*  377 */       this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityFallingBlock);
/*      */       
/*  379 */       if (packetIn.func_149009_m() > 0) {
/*  380 */         if (packetIn.getType() == 60) {
/*  381 */           Entity entity2 = this.clientWorldController.getEntityByID(packetIn.func_149009_m());
/*      */           
/*  383 */           if (entity2 instanceof EntityLivingBase && entityFallingBlock instanceof EntityArrow) {
/*  384 */             ((EntityArrow)entityFallingBlock).shootingEntity = entity2;
/*      */           }
/*      */         } 
/*      */         
/*  388 */         entityFallingBlock.setVelocity(packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb packetIn) {
/*  397 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  398 */     EntityXPOrb entityXPOrb = new EntityXPOrb((World)this.clientWorldController, packetIn.getX() / 32.0D, packetIn.getY() / 32.0D, packetIn.getZ() / 32.0D, packetIn.getXPValue());
/*  399 */     ((Entity)entityXPOrb).serverPosX = packetIn.getX();
/*  400 */     ((Entity)entityXPOrb).serverPosY = packetIn.getY();
/*  401 */     ((Entity)entityXPOrb).serverPosZ = packetIn.getZ();
/*  402 */     ((Entity)entityXPOrb).rotationYaw = 0.0F;
/*  403 */     ((Entity)entityXPOrb).rotationPitch = 0.0F;
/*  404 */     entityXPOrb.setEntityId(packetIn.getEntityID());
/*  405 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityXPOrb);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity packetIn) {
/*      */     EntityLightningBolt entityLightningBolt;
/*  412 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  413 */     double d0 = packetIn.func_149051_d() / 32.0D;
/*  414 */     double d1 = packetIn.func_149050_e() / 32.0D;
/*  415 */     double d2 = packetIn.func_149049_f() / 32.0D;
/*  416 */     Entity entity = null;
/*      */     
/*  418 */     if (packetIn.func_149053_g() == 1) {
/*  419 */       entityLightningBolt = new EntityLightningBolt((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*      */     
/*  422 */     if (entityLightningBolt != null) {
/*  423 */       ((Entity)entityLightningBolt).serverPosX = packetIn.func_149051_d();
/*  424 */       ((Entity)entityLightningBolt).serverPosY = packetIn.func_149050_e();
/*  425 */       ((Entity)entityLightningBolt).serverPosZ = packetIn.func_149049_f();
/*  426 */       ((Entity)entityLightningBolt).rotationYaw = 0.0F;
/*  427 */       ((Entity)entityLightningBolt).rotationPitch = 0.0F;
/*  428 */       entityLightningBolt.setEntityId(packetIn.func_149052_c());
/*  429 */       this.clientWorldController.addWeatherEffect((Entity)entityLightningBolt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnPainting(S10PacketSpawnPainting packetIn) {
/*  437 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  438 */     EntityPainting entitypainting = new EntityPainting((World)this.clientWorldController, packetIn.getPosition(), packetIn.getFacing(), packetIn.getTitle());
/*  439 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entitypainting);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityVelocity(S12PacketEntityVelocity packetIn) {
/*  446 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  447 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  449 */     if (entity != null) {
/*  450 */       entity.setVelocity(packetIn.getMotionX() / 8000.0D, packetIn.getMotionY() / 8000.0D, packetIn.getMotionZ() / 8000.0D);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityMetadata(S1CPacketEntityMetadata packetIn) {
/*  459 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  460 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/*  462 */     if (entity != null && packetIn.func_149376_c() != null) {
/*  463 */       entity.getDataWatcher().updateWatchedObjectsFromList(packetIn.func_149376_c());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnPlayer(S0CPacketSpawnPlayer packetIn) {
/*  471 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  472 */     double d0 = packetIn.getX() / 32.0D;
/*  473 */     double d1 = packetIn.getY() / 32.0D;
/*  474 */     double d2 = packetIn.getZ() / 32.0D;
/*  475 */     float f = (packetIn.getYaw() * 360) / 256.0F;
/*  476 */     float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*  477 */     EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP((World)this.gameController.theWorld, getPlayerInfo(packetIn.getPlayer()).getGameProfile());
/*  478 */     entityotherplayermp.prevPosX = entityotherplayermp.lastTickPosX = (entityotherplayermp.serverPosX = packetIn.getX());
/*  479 */     entityotherplayermp.prevPosY = entityotherplayermp.lastTickPosY = (entityotherplayermp.serverPosY = packetIn.getY());
/*  480 */     entityotherplayermp.prevPosZ = entityotherplayermp.lastTickPosZ = (entityotherplayermp.serverPosZ = packetIn.getZ());
/*  481 */     int i = packetIn.getCurrentItemID();
/*      */     
/*  483 */     if (i == 0) {
/*  484 */       entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = null;
/*      */     } else {
/*  486 */       entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = new ItemStack(Item.getItemById(i), 1, 0);
/*      */     } 
/*      */     
/*  489 */     entityotherplayermp.setPositionAndRotation(d0, d1, d2, f, f1);
/*  490 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityotherplayermp);
/*  491 */     List<DataWatcher.WatchableObject> list = packetIn.func_148944_c();
/*      */     
/*  493 */     if (list != null) {
/*  494 */       entityotherplayermp.getDataWatcher().updateWatchedObjectsFromList(list);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityTeleport(S18PacketEntityTeleport packetIn) {
/*  502 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  503 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/*  505 */     if (entity != null) {
/*  506 */       entity.serverPosX = packetIn.getX();
/*  507 */       entity.serverPosY = packetIn.getY();
/*  508 */       entity.serverPosZ = packetIn.getZ();
/*  509 */       double d0 = entity.serverPosX / 32.0D;
/*  510 */       double d1 = entity.serverPosY / 32.0D;
/*  511 */       double d2 = entity.serverPosZ / 32.0D;
/*  512 */       float f = (packetIn.getYaw() * 360) / 256.0F;
/*  513 */       float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*      */       
/*  515 */       if (Math.abs(entity.posX - d0) < 0.03125D && Math.abs(entity.posY - d1) < 0.015625D && Math.abs(entity.posZ - d2) < 0.03125D) {
/*  516 */         entity.setPositionAndRotation2(entity.posX, entity.posY, entity.posZ, f, f1, 3, true);
/*      */       } else {
/*  518 */         entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3, true);
/*      */       } 
/*      */       
/*  521 */       entity.onGround = packetIn.getOnGround();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleHeldItemChange(S09PacketHeldItemChange packetIn) {
/*  529 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  531 */     if (packetIn.getHeldItemHotbarIndex() >= 0 && packetIn.getHeldItemHotbarIndex() < InventoryPlayer.getHotbarSize()) {
/*  532 */       this.gameController.thePlayer.inventory.currentItem = packetIn.getHeldItemHotbarIndex();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityMovement(S14PacketEntity packetIn) {
/*  542 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  543 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/*  545 */     if (entity != null) {
/*  546 */       entity.serverPosX += packetIn.func_149062_c();
/*  547 */       entity.serverPosY += packetIn.func_149061_d();
/*  548 */       entity.serverPosZ += packetIn.func_149064_e();
/*  549 */       double d0 = entity.serverPosX / 32.0D;
/*  550 */       double d1 = entity.serverPosY / 32.0D;
/*  551 */       double d2 = entity.serverPosZ / 32.0D;
/*  552 */       float f = packetIn.func_149060_h() ? ((packetIn.func_149066_f() * 360) / 256.0F) : entity.rotationYaw;
/*  553 */       float f1 = packetIn.func_149060_h() ? ((packetIn.func_149063_g() * 360) / 256.0F) : entity.rotationPitch;
/*  554 */       entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3, false);
/*  555 */       entity.onGround = packetIn.getOnGround();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityHeadLook(S19PacketEntityHeadLook packetIn) {
/*  564 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  565 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/*  567 */     if (entity != null) {
/*  568 */       float f = (packetIn.getYaw() * 360) / 256.0F;
/*  569 */       entity.setRotationYawHead(f);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDestroyEntities(S13PacketDestroyEntities packetIn) {
/*  579 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  581 */     for (int i = 0; i < (packetIn.getEntityIDs()).length; i++) {
/*  582 */       this.clientWorldController.removeEntityFromWorld(packetIn.getEntityIDs()[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handlePlayerPosLook(S08PacketPlayerPosLook packetIn) {
/*  592 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  593 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*  594 */     double d0 = packetIn.getX();
/*  595 */     double d1 = packetIn.getY();
/*  596 */     double d2 = packetIn.getZ();
/*  597 */     float f = packetIn.getYaw();
/*  598 */     float f1 = packetIn.getPitch();
/*      */     
/*  600 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
/*  601 */       d0 += ((EntityPlayer)entityPlayerSP).posX;
/*      */     } else {
/*  603 */       ((EntityPlayer)entityPlayerSP).motionX = 0.0D;
/*      */     } 
/*      */     
/*  606 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
/*  607 */       d1 += ((EntityPlayer)entityPlayerSP).posY;
/*      */     } else {
/*  609 */       ((EntityPlayer)entityPlayerSP).motionY = 0.0D;
/*      */     } 
/*      */     
/*  612 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
/*  613 */       d2 += ((EntityPlayer)entityPlayerSP).posZ;
/*      */     } else {
/*  615 */       ((EntityPlayer)entityPlayerSP).motionZ = 0.0D;
/*      */     } 
/*      */     
/*  618 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
/*  619 */       f1 += ((EntityPlayer)entityPlayerSP).rotationPitch;
/*      */     }
/*      */     
/*  622 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
/*  623 */       f += ((EntityPlayer)entityPlayerSP).rotationYaw;
/*      */     }
/*      */     
/*  626 */     entityPlayerSP.setPositionAndRotation(d0, d1, d2, f, f1);
/*  627 */     this.netManager.sendPacket((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((EntityPlayer)entityPlayerSP).posX, (entityPlayerSP.getEntityBoundingBox()).minY, ((EntityPlayer)entityPlayerSP).posZ, ((EntityPlayer)entityPlayerSP).rotationYaw, ((EntityPlayer)entityPlayerSP).rotationPitch, false));
/*      */     
/*  629 */     if (!this.doneLoadingTerrain) {
/*  630 */       this.gameController.thePlayer.prevPosX = this.gameController.thePlayer.posX;
/*  631 */       this.gameController.thePlayer.prevPosY = this.gameController.thePlayer.posY;
/*  632 */       this.gameController.thePlayer.prevPosZ = this.gameController.thePlayer.posZ;
/*  633 */       this.doneLoadingTerrain = true;
/*  634 */       this.gameController.displayGuiScreen(null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMultiBlockChange(S22PacketMultiBlockChange packetIn) {
/*  644 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController); byte b; int i;
/*      */     S22PacketMultiBlockChange.BlockUpdateData[] arrayOfBlockUpdateData;
/*  646 */     for (i = (arrayOfBlockUpdateData = packetIn.getChangedBlocks()).length, b = 0; b < i; ) { S22PacketMultiBlockChange.BlockUpdateData s22packetmultiblockchange$blockupdatedata = arrayOfBlockUpdateData[b];
/*  647 */       this.clientWorldController.invalidateRegionAndSetBlock(s22packetmultiblockchange$blockupdatedata.getPos(), s22packetmultiblockchange$blockupdatedata.getBlockState());
/*      */       b++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleChunkData(S21PacketChunkData packetIn) {
/*  655 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  657 */     if (packetIn.func_149274_i()) {
/*  658 */       if (packetIn.getExtractedSize() == 0) {
/*  659 */         this.clientWorldController.doPreChunk(packetIn.getChunkX(), packetIn.getChunkZ(), false);
/*      */         
/*      */         return;
/*      */       } 
/*  663 */       this.clientWorldController.doPreChunk(packetIn.getChunkX(), packetIn.getChunkZ(), true);
/*      */     } 
/*      */     
/*  666 */     this.clientWorldController.invalidateBlockReceiveRegion(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
/*  667 */     Chunk chunk = this.clientWorldController.getChunkFromChunkCoords(packetIn.getChunkX(), packetIn.getChunkZ());
/*  668 */     chunk.fillChunk(packetIn.getExtractedDataBytes(), packetIn.getExtractedSize(), packetIn.func_149274_i());
/*  669 */     this.clientWorldController.markBlockRangeForRenderUpdate(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
/*      */     
/*  671 */     if (!packetIn.func_149274_i() || !(this.clientWorldController.provider instanceof net.minecraft.world.WorldProviderSurface)) {
/*  672 */       chunk.resetRelightChecks();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockChange(S23PacketBlockChange packetIn) {
/*  680 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  681 */     this.clientWorldController.invalidateRegionAndSetBlock(packetIn.getBlockPosition(), packetIn.getBlockState());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDisconnect(S40PacketDisconnect packetIn) {
/*  688 */     this.netManager.closeChannel(packetIn.getReason());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDisconnect(IChatComponent reason) {
/*  695 */     this.gameController.loadWorld(null);
/*      */     
/*  697 */     if (this.guiScreenServer != null) {
/*  698 */       if (this.guiScreenServer instanceof GuiScreenRealmsProxy) {
/*  699 */         this.gameController.displayGuiScreen((GuiScreen)(new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.guiScreenServer).func_154321_a(), "disconnect.lost", reason)).getProxy());
/*      */       } else {
/*  701 */         this.gameController.displayGuiScreen((GuiScreen)new GuiDisconnected(this.guiScreenServer, "disconnect.lost", reason));
/*      */       } 
/*      */     } else {
/*  704 */       this.gameController.displayGuiScreen((GuiScreen)new GuiDisconnected((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()), "disconnect.lost", reason));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addToSendQueue(Packet p_147297_1_) {
/*  709 */     this.netManager.sendPacket(p_147297_1_);
/*      */   }
/*      */   public void handleCollectItem(S0DPacketCollectItem packetIn) {
/*      */     EntityPlayerSP entityPlayerSP;
/*  713 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  714 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getCollectedItemEntityID());
/*  715 */     EntityLivingBase entitylivingbase = (EntityLivingBase)this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  717 */     if (entitylivingbase == null) {
/*  718 */       entityPlayerSP = this.gameController.thePlayer;
/*      */     }
/*      */     
/*  721 */     if (entity != null) {
/*  722 */       if (entity instanceof EntityXPOrb) {
/*  723 */         this.clientWorldController.playSoundAtEntity(entity, "random.orb", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*      */       } else {
/*  725 */         this.clientWorldController.playSoundAtEntity(entity, "random.pop", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*      */       } 
/*      */       
/*  728 */       this.gameController.effectRenderer.addEffect((EntityFX)new EntityPickupFX((World)this.clientWorldController, entity, (Entity)entityPlayerSP, 0.5F));
/*  729 */       this.clientWorldController.removeEntityFromWorld(packetIn.getCollectedItemEntityID());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleChat(S02PacketChat packetIn) {
/*  737 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  739 */     if (packetIn.getType() == 2) {
/*  740 */       this.gameController.ingameGUI.setRecordPlaying(packetIn.getChatComponent(), false);
/*      */     } else {
/*  742 */       this.gameController.ingameGUI.getChatGUI().printChatMessage(packetIn.getChatComponent());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleAnimation(S0BPacketAnimation packetIn) {
/*  751 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  752 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  754 */     if (entity != null) {
/*  755 */       if (packetIn.getAnimationType() == 0) {
/*  756 */         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/*  757 */         entitylivingbase.swingItem();
/*  758 */       } else if (packetIn.getAnimationType() == 1) {
/*  759 */         entity.performHurtAnimation();
/*  760 */       } else if (packetIn.getAnimationType() == 2) {
/*  761 */         EntityPlayer entityplayer = (EntityPlayer)entity;
/*  762 */         entityplayer.wakeUpPlayer(false, false, false);
/*  763 */       } else if (packetIn.getAnimationType() == 4) {
/*  764 */         this.gameController.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT);
/*  765 */       } else if (packetIn.getAnimationType() == 5) {
/*  766 */         this.gameController.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUseBed(S0APacketUseBed packetIn) {
/*  776 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  777 */     packetIn.getPlayer((World)this.clientWorldController).trySleep(packetIn.getBedPosition());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnMob(S0FPacketSpawnMob packetIn) {
/*  785 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  786 */     double d0 = packetIn.getX() / 32.0D;
/*  787 */     double d1 = packetIn.getY() / 32.0D;
/*  788 */     double d2 = packetIn.getZ() / 32.0D;
/*  789 */     float f = (packetIn.getYaw() * 360) / 256.0F;
/*  790 */     float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*  791 */     EntityLivingBase entitylivingbase = (EntityLivingBase)EntityList.createEntityByID(packetIn.getEntityType(), (World)this.gameController.theWorld);
/*  792 */     entitylivingbase.serverPosX = packetIn.getX();
/*  793 */     entitylivingbase.serverPosY = packetIn.getY();
/*  794 */     entitylivingbase.serverPosZ = packetIn.getZ();
/*  795 */     entitylivingbase.renderYawOffset = entitylivingbase.rotationYawHead = (packetIn.getHeadPitch() * 360) / 256.0F;
/*  796 */     Entity[] aentity = entitylivingbase.getParts();
/*      */     
/*  798 */     if (aentity != null) {
/*  799 */       int i = packetIn.getEntityID() - entitylivingbase.getEntityId();
/*      */       
/*  801 */       for (int j = 0; j < aentity.length; j++) {
/*  802 */         aentity[j].setEntityId(aentity[j].getEntityId() + i);
/*      */       }
/*      */     } 
/*      */     
/*  806 */     entitylivingbase.setEntityId(packetIn.getEntityID());
/*  807 */     entitylivingbase.setPositionAndRotation(d0, d1, d2, f, f1);
/*  808 */     entitylivingbase.motionX = (packetIn.getVelocityX() / 8000.0F);
/*  809 */     entitylivingbase.motionY = (packetIn.getVelocityY() / 8000.0F);
/*  810 */     entitylivingbase.motionZ = (packetIn.getVelocityZ() / 8000.0F);
/*  811 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entitylivingbase);
/*  812 */     List<DataWatcher.WatchableObject> list = packetIn.func_149027_c();
/*      */     
/*  814 */     if (list != null) {
/*  815 */       entitylivingbase.getDataWatcher().updateWatchedObjectsFromList(list);
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleTimeUpdate(S03PacketTimeUpdate packetIn) {
/*  820 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  821 */     this.gameController.theWorld.setTotalWorldTime(packetIn.getTotalWorldTime());
/*  822 */     this.gameController.theWorld.setWorldTime(packetIn.getWorldTime());
/*      */   }
/*      */   
/*      */   public void handleSpawnPosition(S05PacketSpawnPosition packetIn) {
/*  826 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  827 */     this.gameController.thePlayer.setSpawnPoint(packetIn.getSpawnPos(), true);
/*  828 */     this.gameController.theWorld.getWorldInfo().setSpawn(packetIn.getSpawnPos());
/*      */   }
/*      */   public void handleEntityAttach(S1BPacketEntityAttach packetIn) {
/*      */     EntityPlayerSP entityPlayerSP;
/*  832 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  833 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*  834 */     Entity entity1 = this.clientWorldController.getEntityByID(packetIn.getVehicleEntityId());
/*      */     
/*  836 */     if (packetIn.getLeash() == 0) {
/*  837 */       boolean flag = false;
/*      */       
/*  839 */       if (packetIn.getEntityId() == this.gameController.thePlayer.getEntityId()) {
/*  840 */         entityPlayerSP = this.gameController.thePlayer;
/*      */         
/*  842 */         if (entity1 instanceof EntityBoat) {
/*  843 */           ((EntityBoat)entity1).setIsBoatEmpty(false);
/*      */         }
/*      */         
/*  846 */         flag = (((Entity)entityPlayerSP).ridingEntity == null && entity1 != null);
/*  847 */       } else if (entity1 instanceof EntityBoat) {
/*  848 */         ((EntityBoat)entity1).setIsBoatEmpty(true);
/*      */       } 
/*      */       
/*  851 */       if (entityPlayerSP == null) {
/*      */         return;
/*      */       }
/*      */       
/*  855 */       entityPlayerSP.mountEntity(entity1);
/*      */       
/*  857 */       if (flag) {
/*  858 */         GameSettings gamesettings = this.gameController.gameSettings;
/*  859 */         this.gameController.ingameGUI.setRecordPlaying(I18n.format("mount.onboard", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindSprint.getKeyCode()) }), false);
/*      */       } 
/*  861 */     } else if (packetIn.getLeash() == 1 && entityPlayerSP instanceof EntityLiving) {
/*  862 */       if (entity1 != null) {
/*  863 */         ((EntityLiving)entityPlayerSP).setLeashedToEntity(entity1, false);
/*      */       } else {
/*  865 */         ((EntityLiving)entityPlayerSP).clearLeashed(false, false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityStatus(S19PacketEntityStatus packetIn) {
/*  877 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  878 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/*  880 */     if (entity != null) {
/*  881 */       if (packetIn.getOpCode() == 21) {
/*  882 */         this.gameController.getSoundHandler().playSound((ISound)new GuardianSound((EntityGuardian)entity));
/*      */       } else {
/*  884 */         entity.handleStatusUpdate(packetIn.getOpCode());
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleUpdateHealth(S06PacketUpdateHealth packetIn) {
/*  890 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  891 */     this.gameController.thePlayer.setPlayerSPHealth(packetIn.getHealth());
/*  892 */     this.gameController.thePlayer.getFoodStats().setFoodLevel(packetIn.getFoodLevel());
/*  893 */     this.gameController.thePlayer.getFoodStats().setFoodSaturationLevel(packetIn.getSaturationLevel());
/*      */   }
/*      */   
/*      */   public void handleSetExperience(S1FPacketSetExperience packetIn) {
/*  897 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  898 */     this.gameController.thePlayer.setXPStats(packetIn.func_149397_c(), packetIn.getTotalExperience(), packetIn.getLevel());
/*      */   }
/*      */   
/*      */   public void handleRespawn(S07PacketRespawn packetIn) {
/*  902 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  904 */     if (packetIn.getDimensionID() != this.gameController.thePlayer.dimension) {
/*  905 */       this.doneLoadingTerrain = false;
/*  906 */       Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*  907 */       this.clientWorldController = new WorldClient(this, new WorldSettings(0L, packetIn.getGameType(), false, this.gameController.theWorld.getWorldInfo().isHardcoreModeEnabled(), packetIn.getWorldType()), packetIn.getDimensionID(), packetIn.getDifficulty(), this.gameController.mcProfiler);
/*  908 */       this.clientWorldController.setWorldScoreboard(scoreboard);
/*  909 */       this.gameController.loadWorld(this.clientWorldController);
/*  910 */       this.gameController.thePlayer.dimension = packetIn.getDimensionID();
/*  911 */       this.gameController.displayGuiScreen((GuiScreen)new GuiDownloadTerrain(this));
/*      */     } 
/*      */     
/*  914 */     this.gameController.setDimensionAndSpawnPlayer(packetIn.getDimensionID());
/*  915 */     this.gameController.playerController.setGameType(packetIn.getGameType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleExplosion(S27PacketExplosion packetIn) {
/*  922 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  923 */     Explosion explosion = new Explosion((World)this.gameController.theWorld, null, packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getStrength(), packetIn.getAffectedBlockPositions());
/*  924 */     explosion.doExplosionB(true);
/*  925 */     this.gameController.thePlayer.motionX += packetIn.func_149149_c();
/*  926 */     this.gameController.thePlayer.motionY += packetIn.func_149144_d();
/*  927 */     this.gameController.thePlayer.motionZ += packetIn.func_149147_e();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleOpenWindow(S2DPacketOpenWindow packetIn) {
/*  935 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  936 */     EntityPlayerSP entityplayersp = this.gameController.thePlayer;
/*      */     
/*  938 */     if ("minecraft:container".equals(packetIn.getGuiId())) {
/*  939 */       entityplayersp.displayGUIChest((IInventory)new InventoryBasic(packetIn.getWindowTitle(), packetIn.getSlotCount()));
/*  940 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*  941 */     } else if ("minecraft:villager".equals(packetIn.getGuiId())) {
/*  942 */       entityplayersp.displayVillagerTradeGui((IMerchant)new NpcMerchant((EntityPlayer)entityplayersp, packetIn.getWindowTitle()));
/*  943 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*  944 */     } else if ("EntityHorse".equals(packetIn.getGuiId())) {
/*  945 */       Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */       
/*  947 */       if (entity instanceof EntityHorse) {
/*  948 */         entityplayersp.displayGUIHorse((EntityHorse)entity, (IInventory)new AnimalChest(packetIn.getWindowTitle(), packetIn.getSlotCount()));
/*  949 */         entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */       } 
/*  951 */     } else if (!packetIn.hasSlots()) {
/*  952 */       entityplayersp.displayGui((IInteractionObject)new LocalBlockIntercommunication(packetIn.getGuiId(), packetIn.getWindowTitle()));
/*  953 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     } else {
/*  955 */       ContainerLocalMenu containerlocalmenu = new ContainerLocalMenu(packetIn.getGuiId(), packetIn.getWindowTitle(), packetIn.getSlotCount());
/*  956 */       entityplayersp.displayGUIChest((IInventory)containerlocalmenu);
/*  957 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSetSlot(S2FPacketSetSlot packetIn) {
/*  965 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  966 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/*  968 */     if (packetIn.func_149175_c() == -1) {
/*  969 */       ((EntityPlayer)entityPlayerSP).inventory.setItemStack(packetIn.func_149174_e());
/*      */     } else {
/*  971 */       boolean flag = false;
/*      */       
/*  973 */       if (this.gameController.currentScreen instanceof GuiContainerCreative) {
/*  974 */         GuiContainerCreative guicontainercreative = (GuiContainerCreative)this.gameController.currentScreen;
/*  975 */         flag = (guicontainercreative.getSelectedTabIndex() != CreativeTabs.tabInventory.getTabIndex());
/*      */       } 
/*      */       
/*  978 */       if (packetIn.func_149175_c() == 0 && packetIn.func_149173_d() >= 36 && packetIn.func_149173_d() < 45) {
/*  979 */         ItemStack itemstack = ((EntityPlayer)entityPlayerSP).inventoryContainer.getSlot(packetIn.func_149173_d()).getStack();
/*      */         
/*  981 */         if (packetIn.func_149174_e() != null && (itemstack == null || itemstack.stackSize < (packetIn.func_149174_e()).stackSize)) {
/*  982 */           (packetIn.func_149174_e()).animationsToGo = 5;
/*      */         }
/*      */         
/*  985 */         ((EntityPlayer)entityPlayerSP).inventoryContainer.putStackInSlot(packetIn.func_149173_d(), packetIn.func_149174_e());
/*  986 */       } else if (packetIn.func_149175_c() == ((EntityPlayer)entityPlayerSP).openContainer.windowId && (packetIn.func_149175_c() != 0 || !flag)) {
/*  987 */         ((EntityPlayer)entityPlayerSP).openContainer.putStackInSlot(packetIn.func_149173_d(), packetIn.func_149174_e());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleConfirmTransaction(S32PacketConfirmTransaction packetIn) {
/*  997 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  998 */     Container container = null;
/*  999 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/* 1001 */     if (packetIn.getWindowId() == 0) {
/* 1002 */       container = ((EntityPlayer)entityPlayerSP).inventoryContainer;
/* 1003 */     } else if (packetIn.getWindowId() == ((EntityPlayer)entityPlayerSP).openContainer.windowId) {
/* 1004 */       container = ((EntityPlayer)entityPlayerSP).openContainer;
/*      */     } 
/*      */     
/* 1007 */     if (container != null && !packetIn.func_148888_e()) {
/* 1008 */       addToSendQueue((Packet)new C0FPacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleWindowItems(S30PacketWindowItems packetIn) {
/* 1016 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1017 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/* 1019 */     if (packetIn.func_148911_c() == 0) {
/* 1020 */       ((EntityPlayer)entityPlayerSP).inventoryContainer.putStacksInSlots(packetIn.getItemStacks());
/* 1021 */     } else if (packetIn.func_148911_c() == ((EntityPlayer)entityPlayerSP).openContainer.windowId) {
/* 1022 */       ((EntityPlayer)entityPlayerSP).openContainer.putStacksInSlots(packetIn.getItemStacks());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSignEditorOpen(S36PacketSignEditorOpen packetIn) {
/*      */     TileEntitySign tileEntitySign;
/* 1030 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1031 */     TileEntity tileentity = this.clientWorldController.getTileEntity(packetIn.getSignPosition());
/*      */     
/* 1033 */     if (!(tileentity instanceof TileEntitySign)) {
/* 1034 */       tileEntitySign = new TileEntitySign();
/* 1035 */       tileEntitySign.setWorldObj((World)this.clientWorldController);
/* 1036 */       tileEntitySign.setPos(packetIn.getSignPosition());
/*      */     } 
/*      */     
/* 1039 */     this.gameController.thePlayer.openEditSign(tileEntitySign);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateSign(S33PacketUpdateSign packetIn) {
/* 1046 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1047 */     boolean flag = false;
/*      */     
/* 1049 */     if (this.gameController.theWorld.isBlockLoaded(packetIn.getPos())) {
/* 1050 */       TileEntity tileentity = this.gameController.theWorld.getTileEntity(packetIn.getPos());
/*      */       
/* 1052 */       if (tileentity instanceof TileEntitySign) {
/* 1053 */         TileEntitySign tileentitysign = (TileEntitySign)tileentity;
/*      */         
/* 1055 */         if (tileentitysign.getIsEditable()) {
/* 1056 */           System.arraycopy(packetIn.getLines(), 0, tileentitysign.signText, 0, 4);
/* 1057 */           tileentitysign.markDirty();
/*      */         } 
/*      */         
/* 1060 */         flag = true;
/*      */       } 
/*      */     } 
/*      */     
/* 1064 */     if (!flag && this.gameController.thePlayer != null) {
/* 1065 */       this.gameController.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Unable to locate sign at " + packetIn.getPos().getX() + ", " + packetIn.getPos().getY() + ", " + packetIn.getPos().getZ()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateTileEntity(S35PacketUpdateTileEntity packetIn) {
/* 1074 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1076 */     if (this.gameController.theWorld.isBlockLoaded(packetIn.getPos())) {
/* 1077 */       TileEntity tileentity = this.gameController.theWorld.getTileEntity(packetIn.getPos());
/* 1078 */       int i = packetIn.getTileEntityType();
/*      */       
/* 1080 */       if ((i == 1 && tileentity instanceof net.minecraft.tileentity.TileEntityMobSpawner) || (i == 2 && tileentity instanceof net.minecraft.tileentity.TileEntityCommandBlock) || (i == 3 && tileentity instanceof net.minecraft.tileentity.TileEntityBeacon) || (i == 4 && tileentity instanceof net.minecraft.tileentity.TileEntitySkull) || (i == 5 && tileentity instanceof net.minecraft.tileentity.TileEntityFlowerPot) || (i == 6 && tileentity instanceof net.minecraft.tileentity.TileEntityBanner)) {
/* 1081 */         tileentity.readFromNBT(packetIn.getNbtCompound());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleWindowProperty(S31PacketWindowProperty packetIn) {
/* 1090 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1091 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/* 1093 */     if (((EntityPlayer)entityPlayerSP).openContainer != null && ((EntityPlayer)entityPlayerSP).openContainer.windowId == packetIn.getWindowId()) {
/* 1094 */       ((EntityPlayer)entityPlayerSP).openContainer.updateProgressBar(packetIn.getVarIndex(), packetIn.getVarValue());
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleEntityEquipment(S04PacketEntityEquipment packetIn) {
/* 1099 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1100 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/* 1102 */     if (entity != null) {
/* 1103 */       entity.setCurrentItemOrArmor(packetIn.getEquipmentSlot(), packetIn.getItemStack());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleCloseWindow(S2EPacketCloseWindow packetIn) {
/* 1111 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1112 */     this.gameController.thePlayer.closeScreenAndDropStack();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockAction(S24PacketBlockAction packetIn) {
/* 1121 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1122 */     this.gameController.theWorld.addBlockEvent(packetIn.getBlockPosition(), packetIn.getBlockType(), packetIn.getData1(), packetIn.getData2());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockBreakAnim(S25PacketBlockBreakAnim packetIn) {
/* 1129 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1130 */     this.gameController.theWorld.sendBlockBreakProgress(packetIn.getBreakerId(), packetIn.getPosition(), packetIn.getProgress());
/*      */   }
/*      */   
/*      */   public void handleMapChunkBulk(S26PacketMapChunkBulk packetIn) {
/* 1134 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1136 */     for (int i = 0; i < packetIn.getChunkCount(); i++) {
/* 1137 */       int j = packetIn.getChunkX(i);
/* 1138 */       int k = packetIn.getChunkZ(i);
/* 1139 */       this.clientWorldController.doPreChunk(j, k, true);
/* 1140 */       this.clientWorldController.invalidateBlockReceiveRegion(j << 4, 0, k << 4, (j << 4) + 15, 256, (k << 4) + 15);
/* 1141 */       Chunk chunk = this.clientWorldController.getChunkFromChunkCoords(j, k);
/* 1142 */       chunk.fillChunk(packetIn.getChunkBytes(i), packetIn.getChunkSize(i), true);
/* 1143 */       this.clientWorldController.markBlockRangeForRenderUpdate(j << 4, 0, k << 4, (j << 4) + 15, 256, (k << 4) + 15);
/*      */       
/* 1145 */       if (!(this.clientWorldController.provider instanceof net.minecraft.world.WorldProviderSurface)) {
/* 1146 */         chunk.resetRelightChecks();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleChangeGameState(S2BPacketChangeGameState packetIn) {
/* 1152 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1153 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/* 1154 */     int i = packetIn.getGameState();
/* 1155 */     float f = packetIn.func_149137_d();
/* 1156 */     int j = MathHelper.floor_float(f + 0.5F);
/*      */     
/* 1158 */     if (i >= 0 && i < S2BPacketChangeGameState.MESSAGE_NAMES.length && S2BPacketChangeGameState.MESSAGE_NAMES[i] != null) {
/* 1159 */       entityPlayerSP.addChatComponentMessage((IChatComponent)new ChatComponentTranslation(S2BPacketChangeGameState.MESSAGE_NAMES[i], new Object[0]));
/*      */     }
/*      */     
/* 1162 */     if (i == 1) {
/* 1163 */       this.clientWorldController.getWorldInfo().setRaining(true);
/* 1164 */       this.clientWorldController.setRainStrength(0.0F);
/* 1165 */     } else if (i == 2) {
/* 1166 */       this.clientWorldController.getWorldInfo().setRaining(false);
/* 1167 */       this.clientWorldController.setRainStrength(1.0F);
/* 1168 */     } else if (i == 3) {
/* 1169 */       this.gameController.playerController.setGameType(WorldSettings.GameType.getByID(j));
/* 1170 */     } else if (i == 4) {
/* 1171 */       this.gameController.displayGuiScreen((GuiScreen)new GuiWinGame());
/* 1172 */     } else if (i == 5) {
/* 1173 */       GameSettings gamesettings = this.gameController.gameSettings;
/*      */       
/* 1175 */       if (f == 0.0F) {
/* 1176 */         this.gameController.displayGuiScreen((GuiScreen)new GuiScreenDemo());
/* 1177 */       } else if (f == 101.0F) {
/* 1178 */         this.gameController.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentTranslation("demo.help.movement", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindRight.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindJump.getKeyCode()) }));
/* 1179 */       } else if (f == 102.0F) {
/* 1180 */         this.gameController.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentTranslation("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindSneak.getKeyCode()) }));
/* 1181 */       } else if (f == 103.0F) {
/* 1182 */         this.gameController.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentTranslation("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindUseItem.getKeyCode()) }));
/*      */       } 
/* 1184 */     } else if (i == 6) {
/* 1185 */       this.clientWorldController.playSound(((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY + entityPlayerSP.getEyeHeight(), ((EntityPlayer)entityPlayerSP).posZ, "random.successful_hit", 0.18F, 0.45F, false);
/* 1186 */     } else if (i == 7) {
/* 1187 */       this.clientWorldController.setRainStrength(f);
/* 1188 */     } else if (i == 8) {
/* 1189 */       this.clientWorldController.setThunderStrength(f);
/* 1190 */     } else if (i == 10) {
/* 1191 */       this.clientWorldController.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, ((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY, ((EntityPlayer)entityPlayerSP).posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/* 1192 */       this.clientWorldController.playSound(((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY, ((EntityPlayer)entityPlayerSP).posZ, "mob.guardian.curse", 1.0F, 1.0F, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMaps(S34PacketMaps packetIn) {
/* 1201 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1202 */     MapData mapdata = ItemMap.loadMapData(packetIn.getMapId(), (World)this.gameController.theWorld);
/* 1203 */     packetIn.setMapdataTo(mapdata);
/* 1204 */     this.gameController.entityRenderer.getMapItemRenderer().updateMapTexture(mapdata);
/*      */   }
/*      */   
/*      */   public void handleEffect(S28PacketEffect packetIn) {
/* 1208 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1210 */     if (packetIn.isSoundServerwide()) {
/* 1211 */       this.gameController.theWorld.playBroadcastSound(packetIn.getSoundType(), packetIn.getSoundPos(), packetIn.getSoundData());
/*      */     } else {
/* 1213 */       this.gameController.theWorld.playAuxSFX(packetIn.getSoundType(), packetIn.getSoundPos(), packetIn.getSoundData());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleStatistics(S37PacketStatistics packetIn) {
/* 1221 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1222 */     boolean flag = false;
/*      */     
/* 1224 */     for (Map.Entry<StatBase, Integer> entry : (Iterable<Map.Entry<StatBase, Integer>>)packetIn.func_148974_c().entrySet()) {
/* 1225 */       StatBase statbase = entry.getKey();
/* 1226 */       int i = ((Integer)entry.getValue()).intValue();
/*      */       
/* 1228 */       if (statbase.isAchievement() && i > 0) {
/* 1229 */         if (this.field_147308_k && this.gameController.thePlayer.getStatFileWriter().readStat(statbase) == 0) {
/* 1230 */           Achievement achievement = (Achievement)statbase;
/* 1231 */           this.gameController.guiAchievement.displayAchievement(achievement);
/* 1232 */           this.gameController.getTwitchStream().func_152911_a((Metadata)new MetadataAchievement(achievement), 0L);
/*      */           
/* 1234 */           if (statbase == AchievementList.openInventory) {
/* 1235 */             this.gameController.gameSettings.showInventoryAchievementHint = false;
/* 1236 */             this.gameController.gameSettings.saveOptions();
/*      */           } 
/*      */         } 
/*      */         
/* 1240 */         flag = true;
/*      */       } 
/*      */       
/* 1243 */       this.gameController.thePlayer.getStatFileWriter().unlockAchievement((EntityPlayer)this.gameController.thePlayer, statbase, i);
/*      */     } 
/*      */     
/* 1246 */     if (!this.field_147308_k && !flag && this.gameController.gameSettings.showInventoryAchievementHint) {
/* 1247 */       this.gameController.guiAchievement.displayUnformattedAchievement(AchievementList.openInventory);
/*      */     }
/*      */     
/* 1250 */     this.field_147308_k = true;
/*      */     
/* 1252 */     if (this.gameController.currentScreen instanceof IProgressMeter) {
/* 1253 */       ((IProgressMeter)this.gameController.currentScreen).doneLoading();
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleEntityEffect(S1DPacketEntityEffect packetIn) {
/* 1258 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1259 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1261 */     if (entity instanceof EntityLivingBase) {
/* 1262 */       PotionEffect potioneffect = new PotionEffect(packetIn.getEffectId(), packetIn.getDuration(), packetIn.getAmplifier(), false, packetIn.func_179707_f());
/* 1263 */       potioneffect.setPotionDurationMax(packetIn.func_149429_c());
/* 1264 */       ((EntityLivingBase)entity).addPotionEffect(potioneffect);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleCombatEvent(S42PacketCombatEvent packetIn) {
/* 1269 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1270 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.field_179775_c);
/* 1271 */     EntityLivingBase entitylivingbase = (entity instanceof EntityLivingBase) ? (EntityLivingBase)entity : null;
/*      */     
/* 1273 */     if (packetIn.eventType == S42PacketCombatEvent.Event.END_COMBAT) {
/* 1274 */       long i = (1000 * packetIn.field_179772_d / 20);
/* 1275 */       MetadataCombat metadatacombat = new MetadataCombat((EntityLivingBase)this.gameController.thePlayer, entitylivingbase);
/* 1276 */       this.gameController.getTwitchStream().func_176026_a((Metadata)metadatacombat, 0L - i, 0L);
/* 1277 */     } else if (packetIn.eventType == S42PacketCombatEvent.Event.ENTITY_DIED) {
/* 1278 */       Entity entity1 = this.clientWorldController.getEntityByID(packetIn.field_179774_b);
/*      */       
/* 1280 */       if (entity1 instanceof EntityPlayer) {
/* 1281 */         MetadataPlayerDeath metadataplayerdeath = new MetadataPlayerDeath((EntityLivingBase)entity1, entitylivingbase);
/* 1282 */         metadataplayerdeath.func_152807_a(packetIn.deathMessage);
/* 1283 */         this.gameController.getTwitchStream().func_152911_a((Metadata)metadataplayerdeath, 0L);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleServerDifficulty(S41PacketServerDifficulty packetIn) {
/* 1289 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1290 */     this.gameController.theWorld.getWorldInfo().setDifficulty(packetIn.getDifficulty());
/* 1291 */     this.gameController.theWorld.getWorldInfo().setDifficultyLocked(packetIn.isDifficultyLocked());
/*      */   }
/*      */   
/*      */   public void handleCamera(S43PacketCamera packetIn) {
/* 1295 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1296 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1298 */     if (entity != null) {
/* 1299 */       this.gameController.setRenderViewEntity(entity);
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleWorldBorder(S44PacketWorldBorder packetIn) {
/* 1304 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1305 */     packetIn.func_179788_a(this.clientWorldController.getWorldBorder());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleTitle(S45PacketTitle packetIn) {
/* 1310 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1311 */     S45PacketTitle.Type s45packettitle$type = packetIn.getType();
/* 1312 */     String s = null;
/* 1313 */     String s1 = null;
/* 1314 */     String s2 = (packetIn.getMessage() != null) ? packetIn.getMessage().getFormattedText() : "";
/*      */     
/* 1316 */     switch (s45packettitle$type) {
/*      */       case TITLE:
/* 1318 */         s = s2;
/*      */         break;
/*      */       
/*      */       case SUBTITLE:
/* 1322 */         s1 = s2;
/*      */         break;
/*      */       
/*      */       case RESET:
/* 1326 */         this.gameController.ingameGUI.displayTitle("", "", -1, -1, -1);
/* 1327 */         this.gameController.ingameGUI.setDefaultTitlesTimes();
/*      */         return;
/*      */     } 
/*      */     
/* 1331 */     this.gameController.ingameGUI.displayTitle(s, s1, packetIn.getFadeInTime(), packetIn.getDisplayTime(), packetIn.getFadeOutTime());
/*      */   }
/*      */   
/*      */   public void handleSetCompressionLevel(S46PacketSetCompressionLevel packetIn) {
/* 1335 */     if (!this.netManager.isLocalChannel()) {
/* 1336 */       this.netManager.setCompressionTreshold(packetIn.getThreshold());
/*      */     }
/*      */   }
/*      */   
/*      */   public void handlePlayerListHeaderFooter(S47PacketPlayerListHeaderFooter packetIn) {
/* 1341 */     this.gameController.ingameGUI.getTabList().setHeader((packetIn.getHeader().getFormattedText().length() == 0) ? null : packetIn.getHeader());
/* 1342 */     this.gameController.ingameGUI.getTabList().setFooter((packetIn.getFooter().getFormattedText().length() == 0) ? null : packetIn.getFooter());
/*      */   }
/*      */   
/*      */   public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect packetIn) {
/* 1346 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1347 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1349 */     if (entity instanceof EntityLivingBase) {
/* 1350 */       ((EntityLivingBase)entity).removePotionEffectClient(packetIn.getEffectId());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerListItem(S38PacketPlayerListItem packetIn) {
/* 1356 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1358 */     for (S38PacketPlayerListItem.AddPlayerData s38packetplayerlistitem$addplayerdata : packetIn.getEntries()) {
/* 1359 */       if (packetIn.getAction() == S38PacketPlayerListItem.Action.REMOVE_PLAYER) {
/* 1360 */         this.playerInfoMap.remove(s38packetplayerlistitem$addplayerdata.getProfile().getId()); continue;
/*      */       } 
/* 1362 */       NetworkPlayerInfo networkplayerinfo = this.playerInfoMap.get(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/*      */       
/* 1364 */       if (packetIn.getAction() == S38PacketPlayerListItem.Action.ADD_PLAYER) {
/* 1365 */         networkplayerinfo = new NetworkPlayerInfo(s38packetplayerlistitem$addplayerdata);
/* 1366 */         this.playerInfoMap.put(networkplayerinfo.getGameProfile().getId(), networkplayerinfo);
/*      */       } 
/*      */       
/* 1369 */       if (networkplayerinfo != null) {
/* 1370 */         switch (packetIn.getAction()) {
/*      */           case null:
/* 1372 */             networkplayerinfo.setGameType(s38packetplayerlistitem$addplayerdata.getGameMode());
/* 1373 */             networkplayerinfo.setResponseTime(s38packetplayerlistitem$addplayerdata.getPing());
/*      */ 
/*      */           
/*      */           case UPDATE_GAME_MODE:
/* 1377 */             networkplayerinfo.setGameType(s38packetplayerlistitem$addplayerdata.getGameMode());
/*      */ 
/*      */           
/*      */           case UPDATE_LATENCY:
/* 1381 */             networkplayerinfo.setResponseTime(s38packetplayerlistitem$addplayerdata.getPing());
/*      */ 
/*      */           
/*      */           case UPDATE_DISPLAY_NAME:
/* 1385 */             networkplayerinfo.setDisplayName(s38packetplayerlistitem$addplayerdata.getDisplayName());
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleKeepAlive(S00PacketKeepAlive packetIn) {
/* 1393 */     addToSendQueue((Packet)new C00PacketKeepAlive(packetIn.func_149134_c()));
/*      */   }
/*      */   
/*      */   public void handlePlayerAbilities(S39PacketPlayerAbilities packetIn) {
/* 1397 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1398 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/* 1399 */     ((EntityPlayer)entityPlayerSP).capabilities.isFlying = packetIn.isFlying();
/* 1400 */     ((EntityPlayer)entityPlayerSP).capabilities.isCreativeMode = packetIn.isCreativeMode();
/* 1401 */     ((EntityPlayer)entityPlayerSP).capabilities.disableDamage = packetIn.isInvulnerable();
/* 1402 */     ((EntityPlayer)entityPlayerSP).capabilities.allowFlying = packetIn.isAllowFlying();
/* 1403 */     ((EntityPlayer)entityPlayerSP).capabilities.setFlySpeed(packetIn.getFlySpeed());
/* 1404 */     ((EntityPlayer)entityPlayerSP).capabilities.setPlayerWalkSpeed(packetIn.getWalkSpeed());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleTabComplete(S3APacketTabComplete packetIn) {
/* 1411 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1412 */     String[] astring = packetIn.func_149630_c();
/*      */     
/* 1414 */     if (this.gameController.currentScreen instanceof GuiChat) {
/* 1415 */       GuiChat guichat = (GuiChat)this.gameController.currentScreen;
/* 1416 */       guichat.onAutocompleteResponse(astring);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleSoundEffect(S29PacketSoundEffect packetIn) {
/* 1421 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1422 */     this.gameController.theWorld.playSound(packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getSoundName(), packetIn.getVolume(), packetIn.getPitch(), false);
/*      */   }
/*      */   
/*      */   public void handleResourcePack(S48PacketResourcePackSend packetIn) {
/* 1426 */     final String s = packetIn.getURL();
/* 1427 */     final String s1 = packetIn.getHash();
/*      */     
/* 1429 */     if (s.startsWith("level://")) {
/* 1430 */       String s2 = s.substring("level://".length());
/* 1431 */       File file1 = new File(this.gameController.mcDataDir, "saves");
/* 1432 */       File file2 = new File(file1, s2);
/*      */       
/* 1434 */       if (file2.isFile()) {
/* 1435 */         this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.ACCEPTED));
/* 1436 */         Futures.addCallback(this.gameController.getResourcePackRepository().setResourcePackInstance(file2), new FutureCallback<Object>() {
/*      */               public void onSuccess(Object p_onSuccess_1_) {
/* 1438 */                 NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/*      */               }
/*      */               
/*      */               public void onFailure(Throwable p_onFailure_1_) {
/* 1442 */                 NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */               }
/*      */             });
/*      */       } else {
/* 1446 */         this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */       }
/*      */     
/* 1449 */     } else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() == ServerData.ServerResourceMode.ENABLED) {
/* 1450 */       this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.ACCEPTED));
/* 1451 */       Futures.addCallback(this.gameController.getResourcePackRepository().downloadResourcePack(s, s1), new FutureCallback<Object>() {
/*      */             public void onSuccess(Object p_onSuccess_1_) {
/* 1453 */               NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/*      */             }
/*      */             
/*      */             public void onFailure(Throwable p_onFailure_1_) {
/* 1457 */               NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */             }
/*      */           });
/* 1460 */     } else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() != ServerData.ServerResourceMode.PROMPT) {
/* 1461 */       this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.DECLINED));
/*      */     } else {
/* 1463 */       this.gameController.addScheduledTask(new Runnable() {
/*      */             public void run() {
/* 1465 */               NetHandlerPlayClient.this.gameController.displayGuiScreen((GuiScreen)new GuiYesNo(new GuiYesNoCallback() {
/*      */                       public void confirmClicked(boolean result, int id) {
/* 1467 */                         (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController = Minecraft.getMinecraft();
/*      */                         
/* 1469 */                         if (result) {
/* 1470 */                           if ((NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getCurrentServerData() != null) {
/* 1471 */                             (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.ENABLED);
/*      */                           }
/*      */                           
/* 1474 */                           (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.ACCEPTED));
/* 1475 */                           Futures.addCallback((NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getResourcePackRepository().downloadResourcePack(s, s1), new FutureCallback<Object>() {
/*      */                                 public void onSuccess(Object p_onSuccess_1_) {
/* 1477 */                                   (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.null.access$0(NetHandlerPlayClient.null.null.this))).netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/*      */                                 }
/*      */                                 
/*      */                                 public void onFailure(Throwable p_onFailure_1_) {
/* 1481 */                                   (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.null.access$0(NetHandlerPlayClient.null.null.this))).netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */                                 }
/*      */                               });
/*      */                         } else {
/* 1485 */                           if ((NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getCurrentServerData() != null) {
/* 1486 */                             (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.DISABLED);
/*      */                           }
/*      */                           
/* 1489 */                           (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.DECLINED));
/*      */                         } 
/*      */                         
/* 1492 */                         ServerList.func_147414_b((NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getCurrentServerData());
/* 1493 */                         (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.displayGuiScreen(null);
/*      */                       }
/* 1495 */                     }I18n.format("multiplayer.texturePrompt.line1", new Object[0]), I18n.format("multiplayer.texturePrompt.line2", new Object[0]), 0));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityNBT(S49PacketUpdateEntityNBT packetIn) {
/* 1503 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1504 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1506 */     if (entity != null) {
/* 1507 */       entity.clientUpdateEntityNBT(packetIn.getTagCompound());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleCustomPayload(S3FPacketCustomPayload packetIn) {
/* 1518 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1520 */     if ("MC|TrList".equals(packetIn.getChannelName())) {
/* 1521 */       PacketBuffer packetbuffer = packetIn.getBufferData();
/*      */       
/*      */       try {
/* 1524 */         int i = packetbuffer.readInt();
/* 1525 */         GuiScreen guiscreen = this.gameController.currentScreen;
/*      */         
/* 1527 */         if (guiscreen != null && guiscreen instanceof GuiMerchant && i == this.gameController.thePlayer.openContainer.windowId) {
/* 1528 */           IMerchant imerchant = ((GuiMerchant)guiscreen).getMerchant();
/* 1529 */           MerchantRecipeList merchantrecipelist = MerchantRecipeList.readFromBuf(packetbuffer);
/* 1530 */           imerchant.setRecipes(merchantrecipelist);
/*      */         } 
/* 1532 */       } catch (IOException ioexception) {
/* 1533 */         logger.error("Couldn't load trade info", ioexception);
/*      */       } finally {
/* 1535 */         packetbuffer.release();
/*      */       } 
/* 1537 */     } else if ("MC|Brand".equals(packetIn.getChannelName())) {
/* 1538 */       this.gameController.thePlayer.setClientBrand(packetIn.getBufferData().readStringFromBuffer(32767));
/* 1539 */     } else if ("MC|BOpen".equals(packetIn.getChannelName())) {
/* 1540 */       ItemStack itemstack = this.gameController.thePlayer.getCurrentEquippedItem();
/*      */       
/* 1542 */       if (itemstack != null && itemstack.getItem() == Items.written_book) {
/* 1543 */         this.gameController.displayGuiScreen((GuiScreen)new GuiScreenBook((EntityPlayer)this.gameController.thePlayer, itemstack, false));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleScoreboardObjective(S3BPacketScoreboardObjective packetIn) {
/* 1552 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1553 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */     
/* 1555 */     if (packetIn.func_149338_e() == 0) {
/* 1556 */       ScoreObjective scoreobjective = scoreboard.addScoreObjective(packetIn.func_149339_c(), IScoreObjectiveCriteria.DUMMY);
/* 1557 */       scoreobjective.setDisplayName(packetIn.func_149337_d());
/* 1558 */       scoreobjective.setRenderType(packetIn.func_179817_d());
/*      */     } else {
/* 1560 */       ScoreObjective scoreobjective1 = scoreboard.getObjective(packetIn.func_149339_c());
/*      */       
/* 1562 */       if (packetIn.func_149338_e() == 1) {
/* 1563 */         scoreboard.removeObjective(scoreobjective1);
/* 1564 */       } else if (packetIn.func_149338_e() == 2) {
/* 1565 */         scoreobjective1.setDisplayName(packetIn.func_149337_d());
/* 1566 */         scoreobjective1.setRenderType(packetIn.func_179817_d());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateScore(S3CPacketUpdateScore packetIn) {
/* 1575 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1576 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/* 1577 */     ScoreObjective scoreobjective = scoreboard.getObjective(packetIn.getObjectiveName());
/*      */     
/* 1579 */     if (packetIn.getScoreAction() == S3CPacketUpdateScore.Action.CHANGE) {
/* 1580 */       Score score = scoreboard.getValueFromObjective(packetIn.getPlayerName(), scoreobjective);
/* 1581 */       score.setScorePoints(packetIn.getScoreValue());
/* 1582 */     } else if (packetIn.getScoreAction() == S3CPacketUpdateScore.Action.REMOVE) {
/* 1583 */       if (StringUtils.isNullOrEmpty(packetIn.getObjectiveName())) {
/* 1584 */         scoreboard.removeObjectiveFromEntity(packetIn.getPlayerName(), null);
/* 1585 */       } else if (scoreobjective != null) {
/* 1586 */         scoreboard.removeObjectiveFromEntity(packetIn.getPlayerName(), scoreobjective);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDisplayScoreboard(S3DPacketDisplayScoreboard packetIn) {
/* 1596 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1597 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */     
/* 1599 */     if (packetIn.func_149370_d().length() == 0) {
/* 1600 */       scoreboard.setObjectiveInDisplaySlot(packetIn.func_149371_c(), null);
/*      */     } else {
/* 1602 */       ScoreObjective scoreobjective = scoreboard.getObjective(packetIn.func_149370_d());
/* 1603 */       scoreboard.setObjectiveInDisplaySlot(packetIn.func_149371_c(), scoreobjective);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleTeams(S3EPacketTeams packetIn) {
/*      */     ScorePlayerTeam scoreplayerteam;
/* 1612 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1613 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */ 
/*      */     
/* 1616 */     if (packetIn.getAction() == 0) {
/* 1617 */       scoreplayerteam = scoreboard.createTeam(packetIn.getName());
/*      */     } else {
/* 1619 */       scoreplayerteam = scoreboard.getTeam(packetIn.getName());
/*      */     } 
/*      */     
/* 1622 */     if (packetIn.getAction() == 0 || packetIn.getAction() == 2) {
/* 1623 */       scoreplayerteam.setTeamName(packetIn.getDisplayName());
/* 1624 */       scoreplayerteam.setNamePrefix(packetIn.getPrefix());
/* 1625 */       scoreplayerteam.setNameSuffix(packetIn.getSuffix());
/* 1626 */       scoreplayerteam.setChatFormat(EnumChatFormatting.FromID(packetIn.getColor()));
/* 1627 */       scoreplayerteam.func_98298_a(packetIn.getFriendlyFlags());
/* 1628 */       Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(packetIn.getNameTagVisibility());
/*      */       
/* 1630 */       if (team$enumvisible != null) {
/* 1631 */         scoreplayerteam.setNameTagVisibility(team$enumvisible);
/*      */       }
/*      */     } 
/*      */     
/* 1635 */     if (packetIn.getAction() == 0 || packetIn.getAction() == 3) {
/* 1636 */       for (String s : packetIn.getPlayers()) {
/* 1637 */         scoreboard.addPlayerToTeam(s, packetIn.getName());
/*      */       }
/*      */     }
/*      */     
/* 1641 */     if (packetIn.getAction() == 4) {
/* 1642 */       for (String s1 : packetIn.getPlayers()) {
/* 1643 */         scoreboard.removePlayerFromTeam(s1, scoreplayerteam);
/*      */       }
/*      */     }
/*      */     
/* 1647 */     if (packetIn.getAction() == 1) {
/* 1648 */       scoreboard.removeTeam(scoreplayerteam);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleParticles(S2APacketParticles packetIn) {
/* 1657 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1659 */     if (packetIn.getParticleCount() == 0) {
/* 1660 */       double d0 = (packetIn.getParticleSpeed() * packetIn.getXOffset());
/* 1661 */       double d2 = (packetIn.getParticleSpeed() * packetIn.getYOffset());
/* 1662 */       double d4 = (packetIn.getParticleSpeed() * packetIn.getZOffset());
/*      */       
/*      */       try {
/* 1665 */         this.clientWorldController.spawnParticle(packetIn.getParticleType(), packetIn.isLongDistance(), packetIn.getXCoordinate(), packetIn.getYCoordinate(), packetIn.getZCoordinate(), d0, d2, d4, packetIn.getParticleArgs());
/* 1666 */       } catch (Throwable var17) {
/* 1667 */         logger.warn("Could not spawn particle effect " + packetIn.getParticleType());
/*      */       } 
/*      */     } else {
/* 1670 */       for (int i = 0; i < packetIn.getParticleCount(); i++) {
/* 1671 */         double d1 = this.avRandomizer.nextGaussian() * packetIn.getXOffset();
/* 1672 */         double d3 = this.avRandomizer.nextGaussian() * packetIn.getYOffset();
/* 1673 */         double d5 = this.avRandomizer.nextGaussian() * packetIn.getZOffset();
/* 1674 */         double d6 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/* 1675 */         double d7 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/* 1676 */         double d8 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/*      */         
/*      */         try {
/* 1679 */           this.clientWorldController.spawnParticle(packetIn.getParticleType(), packetIn.isLongDistance(), packetIn.getXCoordinate() + d1, packetIn.getYCoordinate() + d3, packetIn.getZCoordinate() + d5, d6, d7, d8, packetIn.getParticleArgs());
/* 1680 */         } catch (Throwable var16) {
/* 1681 */           logger.warn("Could not spawn particle effect " + packetIn.getParticleType());
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityProperties(S20PacketEntityProperties packetIn) {
/* 1694 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1695 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1697 */     if (entity != null) {
/* 1698 */       if (!(entity instanceof EntityLivingBase)) {
/* 1699 */         throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entity + ")");
/*      */       }
/* 1701 */       BaseAttributeMap baseattributemap = ((EntityLivingBase)entity).getAttributeMap();
/*      */       
/* 1703 */       for (S20PacketEntityProperties.Snapshot s20packetentityproperties$snapshot : packetIn.func_149441_d()) {
/* 1704 */         IAttributeInstance iattributeinstance = baseattributemap.getAttributeInstanceByName(s20packetentityproperties$snapshot.func_151409_a());
/*      */         
/* 1706 */         if (iattributeinstance == null) {
/* 1707 */           iattributeinstance = baseattributemap.registerAttribute((IAttribute)new RangedAttribute(null, s20packetentityproperties$snapshot.func_151409_a(), 0.0D, 2.2250738585072014E-308D, Double.MAX_VALUE));
/*      */         }
/*      */         
/* 1710 */         iattributeinstance.setBaseValue(s20packetentityproperties$snapshot.func_151410_b());
/* 1711 */         iattributeinstance.removeAllModifiers();
/*      */         
/* 1713 */         for (AttributeModifier attributemodifier : s20packetentityproperties$snapshot.func_151408_c()) {
/* 1714 */           iattributeinstance.applyModifier(attributemodifier);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NetworkManager getNetworkManager() {
/* 1725 */     return this.netManager;
/*      */   }
/*      */   
/*      */   public Collection<NetworkPlayerInfo> getPlayerInfoMap() {
/* 1729 */     return this.playerInfoMap.values();
/*      */   }
/*      */   
/*      */   public NetworkPlayerInfo getPlayerInfo(UUID p_175102_1_) {
/* 1733 */     return this.playerInfoMap.get(p_175102_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NetworkPlayerInfo getPlayerInfo(String p_175104_1_) {
/* 1740 */     for (NetworkPlayerInfo networkplayerinfo : this.playerInfoMap.values()) {
/* 1741 */       if (networkplayerinfo.getGameProfile().getName().equals(p_175104_1_)) {
/* 1742 */         return networkplayerinfo;
/*      */       }
/*      */     } 
/*      */     
/* 1746 */     return null;
/*      */   }
/*      */   
/*      */   public GameProfile getGameProfile() {
/* 1750 */     return this.profile;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\network\NetHandlerPlayClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */