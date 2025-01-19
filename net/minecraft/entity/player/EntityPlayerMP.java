/*      */ package net.minecraft.entity.player;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.passive.EntityHorse;
/*      */ import net.minecraft.entity.projectile.EntityArrow;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.ContainerChest;
/*      */ import net.minecraft.inventory.ContainerHorseInventory;
/*      */ import net.minecraft.inventory.ContainerMerchant;
/*      */ import net.minecraft.inventory.ICrafting;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryMerchant;
/*      */ import net.minecraft.item.EnumAction;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemMapBase;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.NetHandlerPlayServer;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.PacketBuffer;
/*      */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*      */ import net.minecraft.network.play.server.S02PacketChat;
/*      */ import net.minecraft.network.play.server.S06PacketUpdateHealth;
/*      */ import net.minecraft.network.play.server.S0APacketUseBed;
/*      */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*      */ import net.minecraft.network.play.server.S13PacketDestroyEntities;
/*      */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*      */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*      */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*      */ import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
/*      */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*      */ import net.minecraft.network.play.server.S21PacketChunkData;
/*      */ import net.minecraft.network.play.server.S26PacketMapChunkBulk;
/*      */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*      */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/*      */ import net.minecraft.network.play.server.S2EPacketCloseWindow;
/*      */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*      */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*      */ import net.minecraft.network.play.server.S31PacketWindowProperty;
/*      */ import net.minecraft.network.play.server.S36PacketSignEditorOpen;
/*      */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*      */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/*      */ import net.minecraft.network.play.server.S42PacketCombatEvent;
/*      */ import net.minecraft.network.play.server.S43PacketCamera;
/*      */ import net.minecraft.network.play.server.S48PacketResourcePackSend;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.management.ItemInWorldManager;
/*      */ import net.minecraft.server.management.UserListOpsEntry;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.stats.StatisticsFile;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IJsonSerializable;
/*      */ import net.minecraft.util.JsonSerializableSet;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.village.MerchantRecipeList;
/*      */ import net.minecraft.world.ChunkCoordIntPair;
/*      */ import net.minecraft.world.IInteractionObject;
/*      */ import net.minecraft.world.ILockableContainer;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ 
/*      */ public class EntityPlayerMP
/*      */   extends EntityPlayer
/*      */   implements ICrafting
/*      */ {
/*  102 */   private static final Logger logger = LogManager.getLogger();
/*  103 */   private String translator = "en_US";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NetHandlerPlayServer playerNetServerHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final MinecraftServer mcServer;
/*      */ 
/*      */ 
/*      */   
/*      */   public final ItemInWorldManager theItemInWorldManager;
/*      */ 
/*      */ 
/*      */   
/*      */   public double managedPosX;
/*      */ 
/*      */ 
/*      */   
/*      */   public double managedPosZ;
/*      */ 
/*      */ 
/*      */   
/*  129 */   public final List<ChunkCoordIntPair> loadedChunks = Lists.newLinkedList();
/*  130 */   private final List<Integer> destroyedItemsNetCache = Lists.newLinkedList();
/*      */ 
/*      */   
/*      */   private final StatisticsFile statsFile;
/*      */ 
/*      */   
/*  136 */   private float combinedHealth = Float.MIN_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  141 */   private float lastHealth = -1.0E8F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  146 */   private int lastFoodLevel = -99999999;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean wasHungry = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  156 */   private int lastExperience = -99999999;
/*  157 */   private int respawnInvulnerabilityTicks = 60;
/*      */   private EntityPlayer.EnumChatVisibility chatVisibility;
/*      */   private boolean chatColours = true;
/*  160 */   private long playerLastActiveTime = System.currentTimeMillis();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  165 */   private Entity spectatingEntity = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private int currentWindowId;
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isChangingQuantityOnly;
/*      */ 
/*      */ 
/*      */   
/*      */   public int ping;
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean playerConqueredTheEnd;
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayerMP(MinecraftServer server, WorldServer worldIn, GameProfile profile, ItemInWorldManager interactionManager) {
/*  186 */     super((World)worldIn, profile);
/*  187 */     interactionManager.thisPlayerMP = this;
/*  188 */     this.theItemInWorldManager = interactionManager;
/*  189 */     BlockPos blockpos = worldIn.getSpawnPoint();
/*      */     
/*  191 */     if (!worldIn.provider.getHasNoSky() && worldIn.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE) {
/*  192 */       int i = Math.max(5, server.getSpawnProtectionSize() - 6);
/*  193 */       int j = MathHelper.floor_double(worldIn.getWorldBorder().getClosestDistance(blockpos.getX(), blockpos.getZ()));
/*      */       
/*  195 */       if (j < i) {
/*  196 */         i = j;
/*      */       }
/*      */       
/*  199 */       if (j <= 1) {
/*  200 */         i = 1;
/*      */       }
/*      */       
/*  203 */       blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos.add(this.rand.nextInt(i * 2) - i, 0, this.rand.nextInt(i * 2) - i));
/*      */     } 
/*      */     
/*  206 */     this.mcServer = server;
/*  207 */     this.statsFile = server.getConfigurationManager().getPlayerStatsFile(this);
/*  208 */     this.stepHeight = 0.0F;
/*  209 */     moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
/*      */     
/*  211 */     while (!worldIn.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && this.posY < 255.0D) {
/*  212 */       setPosition(this.posX, this.posY + 1.0D, this.posZ);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  220 */     super.readEntityFromNBT(tagCompund);
/*      */     
/*  222 */     if (tagCompund.hasKey("playerGameType", 99)) {
/*  223 */       if (MinecraftServer.getServer().getForceGamemode()) {
/*  224 */         this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
/*      */       } else {
/*  226 */         this.theItemInWorldManager.setGameType(WorldSettings.GameType.getByID(tagCompund.getInteger("playerGameType")));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  235 */     super.writeEntityToNBT(tagCompound);
/*  236 */     tagCompound.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExperienceLevel(int levels) {
/*  243 */     super.addExperienceLevel(levels);
/*  244 */     this.lastExperience = -1;
/*      */   }
/*      */   
/*      */   public void removeExperienceLevel(int levels) {
/*  248 */     super.removeExperienceLevel(levels);
/*  249 */     this.lastExperience = -1;
/*      */   }
/*      */   
/*      */   public void addSelfToInternalCraftingInventory() {
/*  253 */     this.openContainer.onCraftGuiOpened(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEnterCombat() {
/*  260 */     super.sendEnterCombat();
/*  261 */     this.playerNetServerHandler.sendPacket((Packet)new S42PacketCombatEvent(getCombatTracker(), S42PacketCombatEvent.Event.ENTER_COMBAT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEndCombat() {
/*  268 */     super.sendEndCombat();
/*  269 */     this.playerNetServerHandler.sendPacket((Packet)new S42PacketCombatEvent(getCombatTracker(), S42PacketCombatEvent.Event.END_COMBAT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  276 */     this.theItemInWorldManager.updateBlockRemoving();
/*  277 */     this.respawnInvulnerabilityTicks--;
/*      */     
/*  279 */     if (this.hurtResistantTime > 0) {
/*  280 */       this.hurtResistantTime--;
/*      */     }
/*      */     
/*  283 */     this.openContainer.detectAndSendChanges();
/*      */     
/*  285 */     if (!this.worldObj.isRemote && !this.openContainer.canInteractWith(this)) {
/*  286 */       closeScreen();
/*  287 */       this.openContainer = this.inventoryContainer;
/*      */     } 
/*      */     
/*  290 */     while (!this.destroyedItemsNetCache.isEmpty()) {
/*  291 */       int i = Math.min(this.destroyedItemsNetCache.size(), 2147483647);
/*  292 */       int[] aint = new int[i];
/*  293 */       Iterator<Integer> iterator = this.destroyedItemsNetCache.iterator();
/*  294 */       int j = 0;
/*      */       
/*  296 */       while (iterator.hasNext() && j < i) {
/*  297 */         aint[j++] = ((Integer)iterator.next()).intValue();
/*  298 */         iterator.remove();
/*      */       } 
/*      */       
/*  301 */       this.playerNetServerHandler.sendPacket((Packet)new S13PacketDestroyEntities(aint));
/*      */     } 
/*      */     
/*  304 */     if (!this.loadedChunks.isEmpty()) {
/*  305 */       List<Chunk> list = Lists.newArrayList();
/*  306 */       Iterator<ChunkCoordIntPair> iterator1 = this.loadedChunks.iterator();
/*  307 */       List<TileEntity> list1 = Lists.newArrayList();
/*      */       
/*  309 */       while (iterator1.hasNext() && list.size() < 10) {
/*  310 */         ChunkCoordIntPair chunkcoordintpair = iterator1.next();
/*      */         
/*  312 */         if (chunkcoordintpair != null) {
/*  313 */           if (this.worldObj.isBlockLoaded(new BlockPos(chunkcoordintpair.chunkXPos << 4, 0, chunkcoordintpair.chunkZPos << 4))) {
/*  314 */             Chunk chunk = this.worldObj.getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
/*      */             
/*  316 */             if (chunk.isPopulated()) {
/*  317 */               list.add(chunk);
/*  318 */               list1.addAll(((WorldServer)this.worldObj).getTileEntitiesIn(chunkcoordintpair.chunkXPos * 16, 0, chunkcoordintpair.chunkZPos * 16, chunkcoordintpair.chunkXPos * 16 + 16, 256, chunkcoordintpair.chunkZPos * 16 + 16));
/*  319 */               iterator1.remove();
/*      */             } 
/*      */           }  continue;
/*      */         } 
/*  323 */         iterator1.remove();
/*      */       } 
/*      */ 
/*      */       
/*  327 */       if (!list.isEmpty()) {
/*  328 */         if (list.size() == 1) {
/*  329 */           this.playerNetServerHandler.sendPacket((Packet)new S21PacketChunkData(list.get(0), true, 65535));
/*      */         } else {
/*  331 */           this.playerNetServerHandler.sendPacket((Packet)new S26PacketMapChunkBulk(list));
/*      */         } 
/*      */         
/*  334 */         for (TileEntity tileentity : list1) {
/*  335 */           sendTileEntityUpdate(tileentity);
/*      */         }
/*      */         
/*  338 */         for (Chunk chunk1 : list) {
/*  339 */           getServerForPlayer().getEntityTracker().func_85172_a(this, chunk1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  344 */     Entity entity = getSpectatingEntity();
/*      */     
/*  346 */     if (entity != this) {
/*  347 */       if (!entity.isEntityAlive()) {
/*  348 */         setSpectatingEntity((Entity)this);
/*      */       } else {
/*  350 */         setPositionAndRotation(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
/*  351 */         this.mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this);
/*      */         
/*  353 */         if (isSneaking()) {
/*  354 */           setSpectatingEntity((Entity)this);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void onUpdateEntity() {
/*      */     try {
/*  362 */       super.onUpdate();
/*      */       
/*  364 */       for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
/*  365 */         ItemStack itemstack = this.inventory.getStackInSlot(i);
/*      */         
/*  367 */         if (itemstack != null && itemstack.getItem().isMap()) {
/*  368 */           Packet packet = ((ItemMapBase)itemstack.getItem()).createMapDataPacket(itemstack, this.worldObj, this);
/*      */           
/*  370 */           if (packet != null) {
/*  371 */             this.playerNetServerHandler.sendPacket(packet);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  376 */       if (getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || ((this.foodStats.getSaturationLevel() == 0.0F)) != this.wasHungry) {
/*  377 */         this.playerNetServerHandler.sendPacket((Packet)new S06PacketUpdateHealth(getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
/*  378 */         this.lastHealth = getHealth();
/*  379 */         this.lastFoodLevel = this.foodStats.getFoodLevel();
/*  380 */         this.wasHungry = (this.foodStats.getSaturationLevel() == 0.0F);
/*      */       } 
/*      */       
/*  383 */       if (getHealth() + getAbsorptionAmount() != this.combinedHealth) {
/*  384 */         this.combinedHealth = getHealth() + getAbsorptionAmount();
/*      */         
/*  386 */         for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.health)) {
/*  387 */           getWorldScoreboard().getValueFromObjective(getName(), scoreobjective).func_96651_a(Arrays.asList(new EntityPlayer[] { this }));
/*      */         } 
/*      */       } 
/*      */       
/*  391 */       if (this.experienceTotal != this.lastExperience) {
/*  392 */         this.lastExperience = this.experienceTotal;
/*  393 */         this.playerNetServerHandler.sendPacket((Packet)new S1FPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
/*      */       } 
/*      */       
/*  396 */       if (this.ticksExisted % 20 * 5 == 0 && !getStatFile().hasAchievementUnlocked(AchievementList.exploreAllBiomes)) {
/*  397 */         updateBiomesExplored();
/*      */       }
/*  399 */     } catch (Throwable throwable) {
/*  400 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking player");
/*  401 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Player being ticked");
/*  402 */       addEntityCrashInfo(crashreportcategory);
/*  403 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateBiomesExplored() {
/*  411 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)));
/*  412 */     String s = biomegenbase.biomeName;
/*  413 */     JsonSerializableSet jsonserializableset = (JsonSerializableSet)getStatFile().func_150870_b((StatBase)AchievementList.exploreAllBiomes);
/*      */     
/*  415 */     if (jsonserializableset == null) {
/*  416 */       jsonserializableset = (JsonSerializableSet)getStatFile().func_150872_a((StatBase)AchievementList.exploreAllBiomes, (IJsonSerializable)new JsonSerializableSet());
/*      */     }
/*      */     
/*  419 */     jsonserializableset.add(s);
/*      */     
/*  421 */     if (getStatFile().canUnlockAchievement(AchievementList.exploreAllBiomes) && jsonserializableset.size() >= BiomeGenBase.explorationBiomesList.size()) {
/*  422 */       Set<BiomeGenBase> set = Sets.newHashSet(BiomeGenBase.explorationBiomesList);
/*      */       
/*  424 */       for (String s1 : jsonserializableset) {
/*  425 */         Iterator<BiomeGenBase> iterator = set.iterator();
/*      */         
/*  427 */         while (iterator.hasNext()) {
/*  428 */           BiomeGenBase biomegenbase1 = iterator.next();
/*      */           
/*  430 */           if (biomegenbase1.biomeName.equals(s1)) {
/*  431 */             iterator.remove();
/*      */           }
/*      */         } 
/*      */         
/*  435 */         if (set.isEmpty()) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/*  440 */       if (set.isEmpty()) {
/*  441 */         triggerAchievement((StatBase)AchievementList.exploreAllBiomes);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  450 */     if (this.worldObj.getGameRules().getBoolean("showDeathMessages")) {
/*  451 */       Team team = getTeam();
/*      */       
/*  453 */       if (team != null && team.getDeathMessageVisibility() != Team.EnumVisible.ALWAYS) {
/*  454 */         if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
/*  455 */           this.mcServer.getConfigurationManager().sendMessageToAllTeamMembers(this, getCombatTracker().getDeathMessage());
/*  456 */         } else if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OWN_TEAM) {
/*  457 */           this.mcServer.getConfigurationManager().sendMessageToTeamOrEvryPlayer(this, getCombatTracker().getDeathMessage());
/*      */         } 
/*      */       } else {
/*  460 */         this.mcServer.getConfigurationManager().sendChatMsg(getCombatTracker().getDeathMessage());
/*      */       } 
/*      */     } 
/*      */     
/*  464 */     if (!this.worldObj.getGameRules().getBoolean("keepInventory")) {
/*  465 */       this.inventory.dropAllItems();
/*      */     }
/*      */     
/*  468 */     for (ScoreObjective scoreobjective : this.worldObj.getScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.deathCount)) {
/*  469 */       Score score = getWorldScoreboard().getValueFromObjective(getName(), scoreobjective);
/*  470 */       score.func_96648_a();
/*      */     } 
/*      */     
/*  473 */     EntityLivingBase entitylivingbase = getAttackingEntity();
/*      */     
/*  475 */     if (entitylivingbase != null) {
/*  476 */       EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(EntityList.getEntityID((Entity)entitylivingbase)));
/*      */       
/*  478 */       if (entitylist$entityegginfo != null) {
/*  479 */         triggerAchievement(entitylist$entityegginfo.field_151513_e);
/*      */       }
/*      */       
/*  482 */       entitylivingbase.addToPlayerScore((Entity)this, this.scoreValue);
/*      */     } 
/*      */     
/*  485 */     triggerAchievement(StatList.deathsStat);
/*  486 */     func_175145_a(StatList.timeSinceDeathStat);
/*  487 */     getCombatTracker().reset();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  494 */     if (isEntityInvulnerable(source)) {
/*  495 */       return false;
/*      */     }
/*  497 */     boolean flag = (this.mcServer.isDedicatedServer() && canPlayersAttack() && "fall".equals(source.damageType));
/*      */     
/*  499 */     if (!flag && this.respawnInvulnerabilityTicks > 0 && source != DamageSource.outOfWorld) {
/*  500 */       return false;
/*      */     }
/*  502 */     if (source instanceof net.minecraft.util.EntityDamageSource) {
/*  503 */       Entity entity = source.getEntity();
/*      */       
/*  505 */       if (entity instanceof EntityPlayer && !canAttackPlayer((EntityPlayer)entity)) {
/*  506 */         return false;
/*      */       }
/*      */       
/*  509 */       if (entity instanceof EntityArrow) {
/*  510 */         EntityArrow entityarrow = (EntityArrow)entity;
/*      */         
/*  512 */         if (entityarrow.shootingEntity instanceof EntityPlayer && !canAttackPlayer((EntityPlayer)entityarrow.shootingEntity)) {
/*  513 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  518 */     return super.attackEntityFrom(source, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackPlayer(EntityPlayer other) {
/*  524 */     return !canPlayersAttack() ? false : super.canAttackPlayer(other);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canPlayersAttack() {
/*  531 */     return this.mcServer.isPVPEnabled();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void travelToDimension(int dimensionId) {
/*  538 */     if (this.dimension == 1 && dimensionId == 1) {
/*  539 */       triggerAchievement((StatBase)AchievementList.theEnd2);
/*  540 */       this.worldObj.removeEntity((Entity)this);
/*  541 */       this.playerConqueredTheEnd = true;
/*  542 */       this.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(4, 0.0F));
/*      */     } else {
/*  544 */       if (this.dimension == 0 && dimensionId == 1) {
/*  545 */         triggerAchievement((StatBase)AchievementList.theEnd);
/*  546 */         BlockPos blockpos = this.mcServer.worldServerForDimension(dimensionId).getSpawnCoordinate();
/*      */         
/*  548 */         if (blockpos != null) {
/*  549 */           this.playerNetServerHandler.setPlayerLocation(blockpos.getX(), blockpos.getY(), blockpos.getZ(), 0.0F, 0.0F);
/*      */         }
/*      */         
/*  552 */         dimensionId = 1;
/*      */       } else {
/*  554 */         triggerAchievement((StatBase)AchievementList.portal);
/*      */       } 
/*      */       
/*  557 */       this.mcServer.getConfigurationManager().transferPlayerToDimension(this, dimensionId);
/*  558 */       this.lastExperience = -1;
/*  559 */       this.lastHealth = -1.0F;
/*  560 */       this.lastFoodLevel = -1;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isSpectatedByPlayer(EntityPlayerMP player) {
/*  565 */     return player.isSpectator() ? ((getSpectatingEntity() == this)) : (isSpectator() ? false : super.isSpectatedByPlayer(player));
/*      */   }
/*      */   
/*      */   private void sendTileEntityUpdate(TileEntity p_147097_1_) {
/*  569 */     if (p_147097_1_ != null) {
/*  570 */       Packet packet = p_147097_1_.getDescriptionPacket();
/*      */       
/*  572 */       if (packet != null) {
/*  573 */         this.playerNetServerHandler.sendPacket(packet);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onItemPickup(Entity p_71001_1_, int p_71001_2_) {
/*  582 */     super.onItemPickup(p_71001_1_, p_71001_2_);
/*  583 */     this.openContainer.detectAndSendChanges();
/*      */   }
/*      */   
/*      */   public EntityPlayer.EnumStatus trySleep(BlockPos bedLocation) {
/*  587 */     EntityPlayer.EnumStatus entityplayer$enumstatus = super.trySleep(bedLocation);
/*      */     
/*  589 */     if (entityplayer$enumstatus == EntityPlayer.EnumStatus.OK) {
/*  590 */       S0APacketUseBed s0APacketUseBed = new S0APacketUseBed(this, bedLocation);
/*  591 */       getServerForPlayer().getEntityTracker().sendToAllTrackingEntity((Entity)this, (Packet)s0APacketUseBed);
/*  592 */       this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*  593 */       this.playerNetServerHandler.sendPacket((Packet)s0APacketUseBed);
/*      */     } 
/*      */     
/*  596 */     return entityplayer$enumstatus;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void wakeUpPlayer(boolean immediately, boolean updateWorldFlag, boolean setSpawn) {
/*  603 */     if (isPlayerSleeping()) {
/*  604 */       getServerForPlayer().getEntityTracker().func_151248_b((Entity)this, (Packet)new S0BPacketAnimation((Entity)this, 2));
/*      */     }
/*      */     
/*  607 */     super.wakeUpPlayer(immediately, updateWorldFlag, setSpawn);
/*      */     
/*  609 */     if (this.playerNetServerHandler != null) {
/*  610 */       this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mountEntity(Entity entityIn) {
/*  618 */     Entity entity = this.ridingEntity;
/*  619 */     super.mountEntity(entityIn);
/*      */     
/*  621 */     if (entityIn != entity) {
/*  622 */       this.playerNetServerHandler.sendPacket((Packet)new S1BPacketEntityAttach(0, (Entity)this, this.ridingEntity));
/*  623 */       this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleFalling(double p_71122_1_, boolean p_71122_3_) {
/*  634 */     int i = MathHelper.floor_double(this.posX);
/*  635 */     int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/*  636 */     int k = MathHelper.floor_double(this.posZ);
/*  637 */     BlockPos blockpos = new BlockPos(i, j, k);
/*  638 */     Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*      */     
/*  640 */     if (block.getMaterial() == Material.air) {
/*  641 */       Block block1 = this.worldObj.getBlockState(blockpos.down()).getBlock();
/*      */       
/*  643 */       if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/*  644 */         blockpos = blockpos.down();
/*  645 */         block = this.worldObj.getBlockState(blockpos).getBlock();
/*      */       } 
/*      */     } 
/*      */     
/*  649 */     super.updateFallState(p_71122_1_, p_71122_3_, block, blockpos);
/*      */   }
/*      */   
/*      */   public void openEditSign(TileEntitySign signTile) {
/*  653 */     signTile.setPlayer(this);
/*  654 */     this.playerNetServerHandler.sendPacket((Packet)new S36PacketSignEditorOpen(signTile.getPos()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getNextWindowId() {
/*  661 */     this.currentWindowId = this.currentWindowId % 100 + 1;
/*      */   }
/*      */   
/*      */   public void displayGui(IInteractionObject guiOwner) {
/*  665 */     getNextWindowId();
/*  666 */     this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, guiOwner.getGuiID(), guiOwner.getDisplayName()));
/*  667 */     this.openContainer = guiOwner.createContainer(this.inventory, this);
/*  668 */     this.openContainer.windowId = this.currentWindowId;
/*  669 */     this.openContainer.onCraftGuiOpened(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIChest(IInventory chestInventory) {
/*  676 */     if (this.openContainer != this.inventoryContainer) {
/*  677 */       closeScreen();
/*      */     }
/*      */     
/*  680 */     if (chestInventory instanceof ILockableContainer) {
/*  681 */       ILockableContainer ilockablecontainer = (ILockableContainer)chestInventory;
/*      */       
/*  683 */       if (ilockablecontainer.isLocked() && !canOpen(ilockablecontainer.getLockCode()) && !isSpectator()) {
/*  684 */         this.playerNetServerHandler.sendPacket((Packet)new S02PacketChat((IChatComponent)new ChatComponentTranslation("container.isLocked", new Object[] { chestInventory.getDisplayName() }), (byte)2));
/*  685 */         this.playerNetServerHandler.sendPacket((Packet)new S29PacketSoundEffect("random.door_close", this.posX, this.posY, this.posZ, 1.0F, 1.0F));
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  690 */     getNextWindowId();
/*      */     
/*  692 */     if (chestInventory instanceof IInteractionObject) {
/*  693 */       this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, ((IInteractionObject)chestInventory).getGuiID(), chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
/*  694 */       this.openContainer = ((IInteractionObject)chestInventory).createContainer(this.inventory, this);
/*      */     } else {
/*  696 */       this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, "minecraft:container", chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
/*  697 */       this.openContainer = (Container)new ContainerChest(this.inventory, chestInventory, this);
/*      */     } 
/*      */     
/*  700 */     this.openContainer.windowId = this.currentWindowId;
/*  701 */     this.openContainer.onCraftGuiOpened(this);
/*      */   }
/*      */   
/*      */   public void displayVillagerTradeGui(IMerchant villager) {
/*  705 */     getNextWindowId();
/*  706 */     this.openContainer = (Container)new ContainerMerchant(this.inventory, villager, this.worldObj);
/*  707 */     this.openContainer.windowId = this.currentWindowId;
/*  708 */     this.openContainer.onCraftGuiOpened(this);
/*  709 */     InventoryMerchant inventoryMerchant = ((ContainerMerchant)this.openContainer).getMerchantInventory();
/*  710 */     IChatComponent ichatcomponent = villager.getDisplayName();
/*  711 */     this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, "minecraft:villager", ichatcomponent, inventoryMerchant.getSizeInventory()));
/*  712 */     MerchantRecipeList merchantrecipelist = villager.getRecipes(this);
/*      */     
/*  714 */     if (merchantrecipelist != null) {
/*  715 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/*  716 */       packetbuffer.writeInt(this.currentWindowId);
/*  717 */       merchantrecipelist.writeToBuf(packetbuffer);
/*  718 */       this.playerNetServerHandler.sendPacket((Packet)new S3FPacketCustomPayload("MC|TrList", packetbuffer));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void displayGUIHorse(EntityHorse horse, IInventory horseInventory) {
/*  723 */     if (this.openContainer != this.inventoryContainer) {
/*  724 */       closeScreen();
/*      */     }
/*      */     
/*  727 */     getNextWindowId();
/*  728 */     this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, "EntityHorse", horseInventory.getDisplayName(), horseInventory.getSizeInventory(), horse.getEntityId()));
/*  729 */     this.openContainer = (Container)new ContainerHorseInventory(this.inventory, horseInventory, horse, this);
/*  730 */     this.openContainer.windowId = this.currentWindowId;
/*  731 */     this.openContainer.onCraftGuiOpened(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIBook(ItemStack bookStack) {
/*  738 */     Item item = bookStack.getItem();
/*      */     
/*  740 */     if (item == Items.written_book) {
/*  741 */       this.playerNetServerHandler.sendPacket((Packet)new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer())));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
/*  750 */     if (!(containerToSend.getSlot(slotInd) instanceof net.minecraft.inventory.SlotCrafting) && 
/*  751 */       !this.isChangingQuantityOnly) {
/*  752 */       this.playerNetServerHandler.sendPacket((Packet)new S2FPacketSetSlot(containerToSend.windowId, slotInd, stack));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendContainerToPlayer(Container p_71120_1_) {
/*  758 */     updateCraftingInventory(p_71120_1_, p_71120_1_.getInventory());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateCraftingInventory(Container containerToSend, List<ItemStack> itemsList) {
/*  765 */     this.playerNetServerHandler.sendPacket((Packet)new S30PacketWindowItems(containerToSend.windowId, itemsList));
/*  766 */     this.playerNetServerHandler.sendPacket((Packet)new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) {
/*  775 */     this.playerNetServerHandler.sendPacket((Packet)new S31PacketWindowProperty(containerIn.windowId, varToUpdate, newValue));
/*      */   }
/*      */   
/*      */   public void sendAllWindowProperties(Container p_175173_1_, IInventory p_175173_2_) {
/*  779 */     for (int i = 0; i < p_175173_2_.getFieldCount(); i++) {
/*  780 */       this.playerNetServerHandler.sendPacket((Packet)new S31PacketWindowProperty(p_175173_1_.windowId, i, p_175173_2_.getField(i)));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeScreen() {
/*  788 */     this.playerNetServerHandler.sendPacket((Packet)new S2EPacketCloseWindow(this.openContainer.windowId));
/*  789 */     closeContainer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateHeldItem() {
/*  796 */     if (!this.isChangingQuantityOnly) {
/*  797 */       this.playerNetServerHandler.sendPacket((Packet)new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeContainer() {
/*  805 */     this.openContainer.onContainerClosed(this);
/*  806 */     this.openContainer = this.inventoryContainer;
/*      */   }
/*      */   
/*      */   public void setEntityActionState(float p_110430_1_, float p_110430_2_, boolean p_110430_3_, boolean sneaking) {
/*  810 */     if (this.ridingEntity != null) {
/*  811 */       if (p_110430_1_ >= -1.0F && p_110430_1_ <= 1.0F) {
/*  812 */         this.moveStrafing = p_110430_1_;
/*      */       }
/*      */       
/*  815 */       if (p_110430_2_ >= -1.0F && p_110430_2_ <= 1.0F) {
/*  816 */         this.moveForward = p_110430_2_;
/*      */       }
/*      */       
/*  819 */       this.isJumping = p_110430_3_;
/*  820 */       setSneaking(sneaking);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addStat(StatBase stat, int amount) {
/*  828 */     if (stat != null) {
/*  829 */       this.statsFile.increaseStat(this, stat, amount);
/*      */       
/*  831 */       for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(stat.getCriteria())) {
/*  832 */         getWorldScoreboard().getValueFromObjective(getName(), scoreobjective).increseScore(amount);
/*      */       }
/*      */       
/*  835 */       if (this.statsFile.func_150879_e()) {
/*  836 */         this.statsFile.func_150876_a(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void func_175145_a(StatBase p_175145_1_) {
/*  842 */     if (p_175145_1_ != null) {
/*  843 */       this.statsFile.unlockAchievement(this, p_175145_1_, 0);
/*      */       
/*  845 */       for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(p_175145_1_.getCriteria())) {
/*  846 */         getWorldScoreboard().getValueFromObjective(getName(), scoreobjective).setScorePoints(0);
/*      */       }
/*      */       
/*  849 */       if (this.statsFile.func_150879_e()) {
/*  850 */         this.statsFile.func_150876_a(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void mountEntityAndWakeUp() {
/*  856 */     if (this.riddenByEntity != null) {
/*  857 */       this.riddenByEntity.mountEntity((Entity)this);
/*      */     }
/*      */     
/*  860 */     if (this.sleeping) {
/*  861 */       wakeUpPlayer(true, false, false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerHealthUpdated() {
/*  870 */     this.lastHealth = -1.0E8F;
/*      */   }
/*      */   
/*      */   public void addChatComponentMessage(IChatComponent chatComponent) {
/*  874 */     this.playerNetServerHandler.sendPacket((Packet)new S02PacketChat(chatComponent));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onItemUseFinish() {
/*  881 */     this.playerNetServerHandler.sendPacket((Packet)new S19PacketEntityStatus((Entity)this, (byte)9));
/*  882 */     super.onItemUseFinish();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemInUse(ItemStack stack, int duration) {
/*  889 */     super.setItemInUse(stack, duration);
/*      */     
/*  891 */     if (stack != null && stack.getItem() != null && stack.getItem().getItemUseAction(stack) == EnumAction.EAT) {
/*  892 */       getServerForPlayer().getEntityTracker().func_151248_b((Entity)this, (Packet)new S0BPacketAnimation((Entity)this, 3));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clonePlayer(EntityPlayer oldPlayer, boolean respawnFromEnd) {
/*  901 */     super.clonePlayer(oldPlayer, respawnFromEnd);
/*  902 */     this.lastExperience = -1;
/*  903 */     this.lastHealth = -1.0F;
/*  904 */     this.lastFoodLevel = -1;
/*  905 */     this.destroyedItemsNetCache.addAll(((EntityPlayerMP)oldPlayer).destroyedItemsNetCache);
/*      */   }
/*      */   
/*      */   protected void onNewPotionEffect(PotionEffect id) {
/*  909 */     super.onNewPotionEffect(id);
/*  910 */     this.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(getEntityId(), id));
/*      */   }
/*      */   
/*      */   protected void onChangedPotionEffect(PotionEffect id, boolean p_70695_2_) {
/*  914 */     super.onChangedPotionEffect(id, p_70695_2_);
/*  915 */     this.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(getEntityId(), id));
/*      */   }
/*      */   
/*      */   protected void onFinishedPotionEffect(PotionEffect effect) {
/*  919 */     super.onFinishedPotionEffect(effect);
/*  920 */     this.playerNetServerHandler.sendPacket((Packet)new S1EPacketRemoveEntityEffect(getEntityId(), effect));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndUpdate(double x, double y, double z) {
/*  927 */     this.playerNetServerHandler.setPlayerLocation(x, y, z, this.rotationYaw, this.rotationPitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCriticalHit(Entity entityHit) {
/*  934 */     getServerForPlayer().getEntityTracker().func_151248_b((Entity)this, (Packet)new S0BPacketAnimation(entityHit, 4));
/*      */   }
/*      */   
/*      */   public void onEnchantmentCritical(Entity entityHit) {
/*  938 */     getServerForPlayer().getEntityTracker().func_151248_b((Entity)this, (Packet)new S0BPacketAnimation(entityHit, 5));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendPlayerAbilities() {
/*  945 */     if (this.playerNetServerHandler != null) {
/*  946 */       this.playerNetServerHandler.sendPacket((Packet)new S39PacketPlayerAbilities(this.capabilities));
/*  947 */       updatePotionMetadata();
/*      */     } 
/*      */   }
/*      */   
/*      */   public WorldServer getServerForPlayer() {
/*  952 */     return (WorldServer)this.worldObj;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(WorldSettings.GameType gameType) {
/*  959 */     this.theItemInWorldManager.setGameType(gameType);
/*  960 */     this.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(3, gameType.getID()));
/*      */     
/*  962 */     if (gameType == WorldSettings.GameType.SPECTATOR) {
/*  963 */       mountEntity((Entity)null);
/*      */     } else {
/*  965 */       setSpectatingEntity((Entity)this);
/*      */     } 
/*      */     
/*  968 */     sendPlayerAbilities();
/*  969 */     markPotionsDirty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSpectator() {
/*  976 */     return (this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(IChatComponent component) {
/*  983 */     this.playerNetServerHandler.sendPacket((Packet)new S02PacketChat(component));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  990 */     if ("seed".equals(commandName) && !this.mcServer.isDedicatedServer())
/*  991 */       return true; 
/*  992 */     if (!"tell".equals(commandName) && !"help".equals(commandName) && !"me".equals(commandName) && !"trigger".equals(commandName)) {
/*  993 */       if (this.mcServer.getConfigurationManager().canSendCommands(getGameProfile())) {
/*  994 */         UserListOpsEntry userlistopsentry = (UserListOpsEntry)this.mcServer.getConfigurationManager().getOppedPlayers().getEntry(getGameProfile());
/*  995 */         return (userlistopsentry != null) ? ((userlistopsentry.getPermissionLevel() >= permLevel)) : ((this.mcServer.getOpPermissionLevel() >= permLevel));
/*      */       } 
/*  997 */       return false;
/*      */     } 
/*      */     
/* 1000 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlayerIP() {
/* 1008 */     String s = this.playerNetServerHandler.netManager.getRemoteAddress().toString();
/* 1009 */     s = s.substring(s.indexOf("/") + 1);
/* 1010 */     s = s.substring(0, s.indexOf(":"));
/* 1011 */     return s;
/*      */   }
/*      */   
/*      */   public void handleClientSettings(C15PacketClientSettings packetIn) {
/* 1015 */     this.translator = packetIn.getLang();
/* 1016 */     this.chatVisibility = packetIn.getChatVisibility();
/* 1017 */     this.chatColours = packetIn.isColorsEnabled();
/* 1018 */     getDataWatcher().updateObject(10, Byte.valueOf((byte)packetIn.getModelPartFlags()));
/*      */   }
/*      */   
/*      */   public EntityPlayer.EnumChatVisibility getChatVisibility() {
/* 1022 */     return this.chatVisibility;
/*      */   }
/*      */   
/*      */   public void loadResourcePack(String url, String hash) {
/* 1026 */     this.playerNetServerHandler.sendPacket((Packet)new S48PacketResourcePackSend(url, hash));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 1034 */     return new BlockPos(this.posX, this.posY + 0.5D, this.posZ);
/*      */   }
/*      */   
/*      */   public void markPlayerActive() {
/* 1038 */     this.playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StatisticsFile getStatFile() {
/* 1045 */     return this.statsFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntity(Entity p_152339_1_) {
/* 1052 */     if (p_152339_1_ instanceof EntityPlayer) {
/* 1053 */       this.playerNetServerHandler.sendPacket((Packet)new S13PacketDestroyEntities(new int[] { p_152339_1_.getEntityId() }));
/*      */     } else {
/* 1055 */       this.destroyedItemsNetCache.add(Integer.valueOf(p_152339_1_.getEntityId()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updatePotionMetadata() {
/* 1064 */     if (isSpectator()) {
/* 1065 */       resetPotionEffectMetadata();
/* 1066 */       setInvisible(true);
/*      */     } else {
/* 1068 */       super.updatePotionMetadata();
/*      */     } 
/*      */     
/* 1071 */     getServerForPlayer().getEntityTracker().func_180245_a(this);
/*      */   }
/*      */   
/*      */   public Entity getSpectatingEntity() {
/* 1075 */     return (this.spectatingEntity == null) ? (Entity)this : this.spectatingEntity;
/*      */   }
/*      */   
/*      */   public void setSpectatingEntity(Entity entityToSpectate) {
/* 1079 */     Entity entity = getSpectatingEntity();
/* 1080 */     this.spectatingEntity = (entityToSpectate == null) ? (Entity)this : entityToSpectate;
/*      */     
/* 1082 */     if (entity != this.spectatingEntity) {
/* 1083 */       this.playerNetServerHandler.sendPacket((Packet)new S43PacketCamera(this.spectatingEntity));
/* 1084 */       setPositionAndUpdate(this.spectatingEntity.posX, this.spectatingEntity.posY, this.spectatingEntity.posZ);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
/* 1093 */     if (this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR) {
/* 1094 */       setSpectatingEntity(targetEntity);
/*      */     } else {
/* 1096 */       super.attackTargetEntityWithCurrentItem(targetEntity);
/*      */     } 
/*      */   }
/*      */   
/*      */   public long getLastActiveTime() {
/* 1101 */     return this.playerLastActiveTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getTabListDisplayName() {
/* 1109 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\player\EntityPlayerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */