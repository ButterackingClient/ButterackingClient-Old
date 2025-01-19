/*      */ package net.minecraft.network;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.primitives.Doubles;
/*      */ import com.google.common.primitives.Floats;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import java.io.IOException;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Future;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.command.server.CommandBlockLogic;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityMinecartCommandBlock;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.passive.EntityHorse;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.ContainerBeacon;
/*      */ import net.minecraft.inventory.ContainerMerchant;
/*      */ import net.minecraft.inventory.ContainerRepair;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.Slot;
/*      */ import net.minecraft.item.ItemEditableBook;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.ItemWritableBook;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagString;
/*      */ import net.minecraft.network.play.INetHandlerPlayServer;
/*      */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*      */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*      */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*      */ import net.minecraft.network.play.client.C03PacketPlayer;
/*      */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*      */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*      */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*      */ import net.minecraft.network.play.client.C0APacketAnimation;
/*      */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*      */ import net.minecraft.network.play.client.C0CPacketInput;
/*      */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*      */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*      */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*      */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*      */ import net.minecraft.network.play.client.C11PacketEnchantItem;
/*      */ import net.minecraft.network.play.client.C12PacketUpdateSign;
/*      */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*      */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*      */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*      */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*      */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*      */ import net.minecraft.network.play.client.C18PacketSpectate;
/*      */ import net.minecraft.network.play.client.C19PacketResourcePackStatus;
/*      */ import net.minecraft.network.play.server.S00PacketKeepAlive;
/*      */ import net.minecraft.network.play.server.S02PacketChat;
/*      */ import net.minecraft.network.play.server.S07PacketRespawn;
/*      */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*      */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*      */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*      */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*      */ import net.minecraft.network.play.server.S32PacketConfirmTransaction;
/*      */ import net.minecraft.network.play.server.S3APacketTabComplete;
/*      */ import net.minecraft.network.play.server.S40PacketDisconnect;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.management.UserListBansEntry;
/*      */ import net.minecraft.server.management.UserListEntry;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatAllowedCharacters;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.ITickable;
/*      */ import net.minecraft.util.IntHashMap;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import org.apache.commons.lang3.StringUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class NetHandlerPlayServer implements INetHandlerPlayServer, ITickable {
/*  101 */   private static final Logger logger = LogManager.getLogger();
/*      */   
/*      */   public final NetworkManager netManager;
/*      */   
/*      */   private final MinecraftServer serverController;
/*      */   
/*      */   public EntityPlayerMP playerEntity;
/*      */   
/*      */   private int networkTickCount;
/*      */   
/*      */   private int field_175090_f;
/*      */   
/*      */   private int floatingTickCount;
/*      */   
/*      */   private boolean field_147366_g;
/*      */   
/*      */   private int field_147378_h;
/*      */   
/*      */   private long lastPingTime;
/*      */   
/*      */   private long lastSentPingPacket;
/*      */   private int chatSpamThresholdCount;
/*      */   private int itemDropThreshold;
/*  124 */   private IntHashMap<Short> field_147372_n = new IntHashMap();
/*      */   private double lastPosX;
/*      */   private double lastPosY;
/*      */   private double lastPosZ;
/*      */   private boolean hasMoved = true;
/*      */   
/*      */   public NetHandlerPlayServer(MinecraftServer server, NetworkManager networkManagerIn, EntityPlayerMP playerIn) {
/*  131 */     this.serverController = server;
/*  132 */     this.netManager = networkManagerIn;
/*  133 */     networkManagerIn.setNetHandler((INetHandler)this);
/*  134 */     this.playerEntity = playerIn;
/*  135 */     playerIn.playerNetServerHandler = this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update() {
/*  142 */     this.field_147366_g = false;
/*  143 */     this.networkTickCount++;
/*  144 */     this.serverController.theProfiler.startSection("keepAlive");
/*      */     
/*  146 */     if (this.networkTickCount - this.lastSentPingPacket > 40L) {
/*  147 */       this.lastSentPingPacket = this.networkTickCount;
/*  148 */       this.lastPingTime = currentTimeMillis();
/*  149 */       this.field_147378_h = (int)this.lastPingTime;
/*  150 */       sendPacket((Packet)new S00PacketKeepAlive(this.field_147378_h));
/*      */     } 
/*      */     
/*  153 */     this.serverController.theProfiler.endSection();
/*      */     
/*  155 */     if (this.chatSpamThresholdCount > 0) {
/*  156 */       this.chatSpamThresholdCount--;
/*      */     }
/*      */     
/*  159 */     if (this.itemDropThreshold > 0) {
/*  160 */       this.itemDropThreshold--;
/*      */     }
/*      */     
/*  163 */     if (this.playerEntity.getLastActiveTime() > 0L && this.serverController.getMaxPlayerIdleMinutes() > 0 && MinecraftServer.getCurrentTimeMillis() - this.playerEntity.getLastActiveTime() > (this.serverController.getMaxPlayerIdleMinutes() * 1000 * 60)) {
/*  164 */       kickPlayerFromServer("You have been idle for too long!");
/*      */     }
/*      */   }
/*      */   
/*      */   public NetworkManager getNetworkManager() {
/*  169 */     return this.netManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void kickPlayerFromServer(String reason) {
/*  176 */     final ChatComponentText chatcomponenttext = new ChatComponentText(reason);
/*  177 */     this.netManager.sendPacket((Packet)new S40PacketDisconnect((IChatComponent)chatcomponenttext), new GenericFutureListener<Future<? super Void>>() {
/*      */           public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception {
/*  179 */             NetHandlerPlayServer.this.netManager.closeChannel((IChatComponent)chatcomponenttext);
/*      */           }
/*  181 */         },  (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener[0]);
/*  182 */     this.netManager.disableAutoRead();
/*  183 */     Futures.getUnchecked((Future)this.serverController.addScheduledTask(new Runnable() {
/*      */             public void run() {
/*  185 */               NetHandlerPlayServer.this.netManager.checkDisconnected();
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processInput(C0CPacketInput packetIn) {
/*  195 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  196 */     this.playerEntity.setEntityActionState(packetIn.getStrafeSpeed(), packetIn.getForwardSpeed(), packetIn.isJumping(), packetIn.isSneaking());
/*      */   }
/*      */   
/*      */   private boolean func_183006_b(C03PacketPlayer p_183006_1_) {
/*  200 */     return !(Doubles.isFinite(p_183006_1_.getPositionX()) && Doubles.isFinite(p_183006_1_.getPositionY()) && Doubles.isFinite(p_183006_1_.getPositionZ()) && Floats.isFinite(p_183006_1_.getPitch()) && Floats.isFinite(p_183006_1_.getYaw()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayer(C03PacketPlayer packetIn) {
/*  207 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  209 */     if (func_183006_b(packetIn)) {
/*  210 */       kickPlayerFromServer("Invalid move packet received");
/*      */     } else {
/*  212 */       WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  213 */       this.field_147366_g = true;
/*      */       
/*  215 */       if (!this.playerEntity.playerConqueredTheEnd) {
/*  216 */         double d0 = this.playerEntity.posX;
/*  217 */         double d1 = this.playerEntity.posY;
/*  218 */         double d2 = this.playerEntity.posZ;
/*  219 */         double d3 = 0.0D;
/*  220 */         double d4 = packetIn.getPositionX() - this.lastPosX;
/*  221 */         double d5 = packetIn.getPositionY() - this.lastPosY;
/*  222 */         double d6 = packetIn.getPositionZ() - this.lastPosZ;
/*      */         
/*  224 */         if (packetIn.isMoving()) {
/*  225 */           d3 = d4 * d4 + d5 * d5 + d6 * d6;
/*      */           
/*  227 */           if (!this.hasMoved && d3 < 0.25D) {
/*  228 */             this.hasMoved = true;
/*      */           }
/*      */         } 
/*      */         
/*  232 */         if (this.hasMoved) {
/*  233 */           this.field_175090_f = this.networkTickCount;
/*      */           
/*  235 */           if (this.playerEntity.ridingEntity != null) {
/*  236 */             float f4 = this.playerEntity.rotationYaw;
/*  237 */             float f = this.playerEntity.rotationPitch;
/*  238 */             this.playerEntity.ridingEntity.updateRiderPosition();
/*  239 */             double d16 = this.playerEntity.posX;
/*  240 */             double d17 = this.playerEntity.posY;
/*  241 */             double d18 = this.playerEntity.posZ;
/*      */             
/*  243 */             if (packetIn.getRotating()) {
/*  244 */               f4 = packetIn.getYaw();
/*  245 */               f = packetIn.getPitch();
/*      */             } 
/*      */             
/*  248 */             this.playerEntity.onGround = packetIn.isOnGround();
/*  249 */             this.playerEntity.onUpdateEntity();
/*  250 */             this.playerEntity.setPositionAndRotation(d16, d17, d18, f4, f);
/*      */             
/*  252 */             if (this.playerEntity.ridingEntity != null) {
/*  253 */               this.playerEntity.ridingEntity.updateRiderPosition();
/*      */             }
/*      */             
/*  256 */             this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
/*      */             
/*  258 */             if (this.playerEntity.ridingEntity != null) {
/*  259 */               if (d3 > 4.0D) {
/*  260 */                 Entity entity = this.playerEntity.ridingEntity;
/*  261 */                 this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S18PacketEntityTeleport(entity));
/*  262 */                 setPlayerLocation(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */               } 
/*      */               
/*  265 */               this.playerEntity.ridingEntity.isAirBorne = true;
/*      */             } 
/*      */             
/*  268 */             if (this.hasMoved) {
/*  269 */               this.lastPosX = this.playerEntity.posX;
/*  270 */               this.lastPosY = this.playerEntity.posY;
/*  271 */               this.lastPosZ = this.playerEntity.posZ;
/*      */             } 
/*      */             
/*  274 */             worldserver.updateEntity((Entity)this.playerEntity);
/*      */             
/*      */             return;
/*      */           } 
/*  278 */           if (this.playerEntity.isPlayerSleeping()) {
/*  279 */             this.playerEntity.onUpdateEntity();
/*  280 */             this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*  281 */             worldserver.updateEntity((Entity)this.playerEntity);
/*      */             
/*      */             return;
/*      */           } 
/*  285 */           double d7 = this.playerEntity.posY;
/*  286 */           this.lastPosX = this.playerEntity.posX;
/*  287 */           this.lastPosY = this.playerEntity.posY;
/*  288 */           this.lastPosZ = this.playerEntity.posZ;
/*  289 */           double d8 = this.playerEntity.posX;
/*  290 */           double d9 = this.playerEntity.posY;
/*  291 */           double d10 = this.playerEntity.posZ;
/*  292 */           float f1 = this.playerEntity.rotationYaw;
/*  293 */           float f2 = this.playerEntity.rotationPitch;
/*      */           
/*  295 */           if (packetIn.isMoving() && packetIn.getPositionY() == -999.0D) {
/*  296 */             packetIn.setMoving(false);
/*      */           }
/*      */           
/*  299 */           if (packetIn.isMoving()) {
/*  300 */             d8 = packetIn.getPositionX();
/*  301 */             d9 = packetIn.getPositionY();
/*  302 */             d10 = packetIn.getPositionZ();
/*      */             
/*  304 */             if (Math.abs(packetIn.getPositionX()) > 3.0E7D || Math.abs(packetIn.getPositionZ()) > 3.0E7D) {
/*  305 */               kickPlayerFromServer("Illegal position");
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*  310 */           if (packetIn.getRotating()) {
/*  311 */             f1 = packetIn.getYaw();
/*  312 */             f2 = packetIn.getPitch();
/*      */           } 
/*      */           
/*  315 */           this.playerEntity.onUpdateEntity();
/*  316 */           this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, f1, f2);
/*      */           
/*  318 */           if (!this.hasMoved) {
/*      */             return;
/*      */           }
/*      */           
/*  322 */           double d11 = d8 - this.playerEntity.posX;
/*  323 */           double d12 = d9 - this.playerEntity.posY;
/*  324 */           double d13 = d10 - this.playerEntity.posZ;
/*  325 */           double d14 = this.playerEntity.motionX * this.playerEntity.motionX + this.playerEntity.motionY * this.playerEntity.motionY + this.playerEntity.motionZ * this.playerEntity.motionZ;
/*  326 */           double d15 = d11 * d11 + d12 * d12 + d13 * d13;
/*      */           
/*  328 */           if (d15 - d14 > 100.0D && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.playerEntity.getName()))) {
/*  329 */             logger.warn(String.valueOf(this.playerEntity.getName()) + " moved too quickly! " + d11 + "," + d12 + "," + d13 + " (" + d11 + ", " + d12 + ", " + d13 + ")");
/*  330 */             setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */             
/*      */             return;
/*      */           } 
/*  334 */           float f3 = 0.0625F;
/*  335 */           boolean flag = worldserver.getCollidingBoundingBoxes((Entity)this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(f3, f3, f3)).isEmpty();
/*      */           
/*  337 */           if (this.playerEntity.onGround && !packetIn.isOnGround() && d12 > 0.0D) {
/*  338 */             this.playerEntity.jump();
/*      */           }
/*      */           
/*  341 */           this.playerEntity.moveEntity(d11, d12, d13);
/*  342 */           this.playerEntity.onGround = packetIn.isOnGround();
/*  343 */           d11 = d8 - this.playerEntity.posX;
/*  344 */           d12 = d9 - this.playerEntity.posY;
/*      */           
/*  346 */           if (d12 > -0.5D || d12 < 0.5D) {
/*  347 */             d12 = 0.0D;
/*      */           }
/*      */           
/*  350 */           d13 = d10 - this.playerEntity.posZ;
/*  351 */           d15 = d11 * d11 + d12 * d12 + d13 * d13;
/*  352 */           boolean flag1 = false;
/*      */           
/*  354 */           if (d15 > 0.0625D && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
/*  355 */             flag1 = true;
/*  356 */             logger.warn(String.valueOf(this.playerEntity.getName()) + " moved wrongly!");
/*      */           } 
/*      */           
/*  359 */           this.playerEntity.setPositionAndRotation(d8, d9, d10, f1, f2);
/*  360 */           this.playerEntity.addMovementStat(this.playerEntity.posX - d0, this.playerEntity.posY - d1, this.playerEntity.posZ - d2);
/*      */           
/*  362 */           if (!this.playerEntity.noClip) {
/*  363 */             boolean flag2 = worldserver.getCollidingBoundingBoxes((Entity)this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(f3, f3, f3)).isEmpty();
/*      */             
/*  365 */             if (flag && (flag1 || !flag2) && !this.playerEntity.isPlayerSleeping()) {
/*  366 */               setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, f1, f2);
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*  371 */           AxisAlignedBB axisalignedbb = this.playerEntity.getEntityBoundingBox().expand(f3, f3, f3).addCoord(0.0D, -0.55D, 0.0D);
/*      */           
/*  373 */           if (!this.serverController.isFlightAllowed() && !this.playerEntity.capabilities.allowFlying && !worldserver.checkBlockCollision(axisalignedbb)) {
/*  374 */             if (d12 >= -0.03125D) {
/*  375 */               this.floatingTickCount++;
/*      */               
/*  377 */               if (this.floatingTickCount > 80) {
/*  378 */                 logger.warn(String.valueOf(this.playerEntity.getName()) + " was kicked for floating too long!");
/*  379 */                 kickPlayerFromServer("Flying is not enabled on this server");
/*      */                 return;
/*      */               } 
/*      */             } 
/*      */           } else {
/*  384 */             this.floatingTickCount = 0;
/*      */           } 
/*      */           
/*  387 */           this.playerEntity.onGround = packetIn.isOnGround();
/*  388 */           this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
/*  389 */           this.playerEntity.handleFalling(this.playerEntity.posY - d7, packetIn.isOnGround());
/*  390 */         } else if (this.networkTickCount - this.field_175090_f > 20) {
/*  391 */           setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setPlayerLocation(double x, double y, double z, float yaw, float pitch) {
/*  398 */     setPlayerLocation(x, y, z, yaw, pitch, Collections.emptySet());
/*      */   }
/*      */   
/*      */   public void setPlayerLocation(double x, double y, double z, float yaw, float pitch, Set<S08PacketPlayerPosLook.EnumFlags> relativeSet) {
/*  402 */     this.hasMoved = false;
/*  403 */     this.lastPosX = x;
/*  404 */     this.lastPosY = y;
/*  405 */     this.lastPosZ = z;
/*      */     
/*  407 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.X)) {
/*  408 */       this.lastPosX += this.playerEntity.posX;
/*      */     }
/*      */     
/*  411 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
/*  412 */       this.lastPosY += this.playerEntity.posY;
/*      */     }
/*      */     
/*  415 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
/*  416 */       this.lastPosZ += this.playerEntity.posZ;
/*      */     }
/*      */     
/*  419 */     float f = yaw;
/*  420 */     float f1 = pitch;
/*      */     
/*  422 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
/*  423 */       f = yaw + this.playerEntity.rotationYaw;
/*      */     }
/*      */     
/*  426 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
/*  427 */       f1 = pitch + this.playerEntity.rotationPitch;
/*      */     }
/*      */     
/*  430 */     this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, f, f1);
/*  431 */     this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S08PacketPlayerPosLook(x, y, z, yaw, pitch, relativeSet));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerDigging(C07PacketPlayerDigging packetIn) {
/*      */     double d0, d1, d2, d3;
/*  440 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  441 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  442 */     BlockPos blockpos = packetIn.getPosition();
/*  443 */     this.playerEntity.markPlayerActive();
/*      */     
/*  445 */     switch (packetIn.getStatus()) {
/*      */       case DROP_ITEM:
/*  447 */         if (!this.playerEntity.isSpectator()) {
/*  448 */           this.playerEntity.dropOneItem(false);
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case DROP_ALL_ITEMS:
/*  454 */         if (!this.playerEntity.isSpectator()) {
/*  455 */           this.playerEntity.dropOneItem(true);
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case RELEASE_USE_ITEM:
/*  461 */         this.playerEntity.stopUsingItem();
/*      */         return;
/*      */       
/*      */       case START_DESTROY_BLOCK:
/*      */       case null:
/*      */       case STOP_DESTROY_BLOCK:
/*  467 */         d0 = this.playerEntity.posX - blockpos.getX() + 0.5D;
/*  468 */         d1 = this.playerEntity.posY - blockpos.getY() + 0.5D + 1.5D;
/*  469 */         d2 = this.playerEntity.posZ - blockpos.getZ() + 0.5D;
/*  470 */         d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */         
/*  472 */         if (d3 > 36.0D)
/*      */           return; 
/*  474 */         if (blockpos.getY() >= this.serverController.getBuildLimit()) {
/*      */           return;
/*      */         }
/*  477 */         if (packetIn.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
/*  478 */           if (!this.serverController.isBlockProtected((World)worldserver, blockpos, (EntityPlayer)this.playerEntity) && worldserver.getWorldBorder().contains(blockpos)) {
/*  479 */             this.playerEntity.theItemInWorldManager.onBlockClicked(blockpos, packetIn.getFacing());
/*      */           } else {
/*  481 */             this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos));
/*      */           } 
/*      */         } else {
/*  484 */           if (packetIn.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
/*  485 */             this.playerEntity.theItemInWorldManager.blockRemoving(blockpos);
/*  486 */           } else if (packetIn.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
/*  487 */             this.playerEntity.theItemInWorldManager.cancelDestroyingBlock();
/*      */           } 
/*      */           
/*  490 */           if (worldserver.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
/*  491 */             this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos));
/*      */           }
/*      */         } 
/*      */         return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  499 */     throw new IllegalArgumentException("Invalid player action");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerBlockPlacement(C08PacketPlayerBlockPlacement packetIn) {
/*  507 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  508 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  509 */     ItemStack itemstack = this.playerEntity.inventory.getCurrentItem();
/*  510 */     boolean flag = false;
/*  511 */     BlockPos blockpos = packetIn.getPosition();
/*  512 */     EnumFacing enumfacing = EnumFacing.getFront(packetIn.getPlacedBlockDirection());
/*  513 */     this.playerEntity.markPlayerActive();
/*      */     
/*  515 */     if (packetIn.getPlacedBlockDirection() == 255) {
/*  516 */       if (itemstack == null) {
/*      */         return;
/*      */       }
/*      */       
/*  520 */       this.playerEntity.theItemInWorldManager.tryUseItem((EntityPlayer)this.playerEntity, (World)worldserver, itemstack);
/*  521 */     } else if (blockpos.getY() < this.serverController.getBuildLimit() - 1 || (enumfacing != EnumFacing.UP && blockpos.getY() < this.serverController.getBuildLimit())) {
/*  522 */       if (this.hasMoved && this.playerEntity.getDistanceSq(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D) < 64.0D && !this.serverController.isBlockProtected((World)worldserver, blockpos, (EntityPlayer)this.playerEntity) && worldserver.getWorldBorder().contains(blockpos)) {
/*  523 */         this.playerEntity.theItemInWorldManager.activateBlockOrUseItem((EntityPlayer)this.playerEntity, (World)worldserver, itemstack, blockpos, enumfacing, packetIn.getPlacedBlockOffsetX(), packetIn.getPlacedBlockOffsetY(), packetIn.getPlacedBlockOffsetZ());
/*      */       }
/*      */       
/*  526 */       flag = true;
/*      */     } else {
/*  528 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("build.tooHigh", new Object[] { Integer.valueOf(this.serverController.getBuildLimit()) });
/*  529 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/*  530 */       this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S02PacketChat((IChatComponent)chatcomponenttranslation));
/*  531 */       flag = true;
/*      */     } 
/*      */     
/*  534 */     if (flag) {
/*  535 */       this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos));
/*  536 */       this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos.offset(enumfacing)));
/*      */     } 
/*      */     
/*  539 */     itemstack = this.playerEntity.inventory.getCurrentItem();
/*      */     
/*  541 */     if (itemstack != null && itemstack.stackSize == 0) {
/*  542 */       this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
/*  543 */       itemstack = null;
/*      */     } 
/*      */     
/*  546 */     if (itemstack == null || itemstack.getMaxItemUseDuration() == 0) {
/*  547 */       this.playerEntity.isChangingQuantityOnly = true;
/*  548 */       this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
/*  549 */       Slot slot = this.playerEntity.openContainer.getSlotFromInventory((IInventory)this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
/*  550 */       this.playerEntity.openContainer.detectAndSendChanges();
/*  551 */       this.playerEntity.isChangingQuantityOnly = false;
/*      */       
/*  553 */       if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), packetIn.getStack())) {
/*  554 */         sendPacket((Packet)new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, slot.slotNumber, this.playerEntity.inventory.getCurrentItem()));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleSpectate(C18PacketSpectate packetIn) {
/*  560 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  562 */     if (this.playerEntity.isSpectator()) {
/*  563 */       Entity entity = null; byte b; int i;
/*      */       WorldServer[] arrayOfWorldServer;
/*  565 */       for (i = (arrayOfWorldServer = this.serverController.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/*  566 */         if (worldserver != null) {
/*  567 */           entity = packetIn.getEntity(worldserver);
/*      */           
/*  569 */           if (entity != null) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         b++; }
/*      */       
/*  575 */       if (entity != null) {
/*  576 */         this.playerEntity.setSpectatingEntity((Entity)this.playerEntity);
/*  577 */         this.playerEntity.mountEntity(null);
/*      */         
/*  579 */         if (entity.worldObj != this.playerEntity.worldObj) {
/*  580 */           WorldServer worldserver1 = this.playerEntity.getServerForPlayer();
/*  581 */           WorldServer worldserver2 = (WorldServer)entity.worldObj;
/*  582 */           this.playerEntity.dimension = entity.dimension;
/*  583 */           sendPacket((Packet)new S07PacketRespawn(this.playerEntity.dimension, worldserver1.getDifficulty(), worldserver1.getWorldInfo().getTerrainType(), this.playerEntity.theItemInWorldManager.getGameType()));
/*  584 */           worldserver1.removePlayerEntityDangerously((Entity)this.playerEntity);
/*  585 */           this.playerEntity.isDead = false;
/*  586 */           this.playerEntity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
/*      */           
/*  588 */           if (this.playerEntity.isEntityAlive()) {
/*  589 */             worldserver1.updateEntityWithOptionalForce((Entity)this.playerEntity, false);
/*  590 */             worldserver2.spawnEntityInWorld((Entity)this.playerEntity);
/*  591 */             worldserver2.updateEntityWithOptionalForce((Entity)this.playerEntity, false);
/*      */           } 
/*      */           
/*  594 */           this.playerEntity.setWorld((World)worldserver2);
/*  595 */           this.serverController.getConfigurationManager().preparePlayer(this.playerEntity, worldserver1);
/*  596 */           this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
/*  597 */           this.playerEntity.theItemInWorldManager.setWorld(worldserver2);
/*  598 */           this.serverController.getConfigurationManager().updateTimeAndWeatherForPlayer(this.playerEntity, worldserver2);
/*  599 */           this.serverController.getConfigurationManager().syncPlayerInventory(this.playerEntity);
/*      */         } else {
/*  601 */           this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleResourcePackStatus(C19PacketResourcePackStatus packetIn) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDisconnect(IChatComponent reason) {
/*  614 */     logger.info(String.valueOf(this.playerEntity.getName()) + " lost connection: " + reason);
/*  615 */     this.serverController.refreshStatusNextTick();
/*  616 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.left", new Object[] { this.playerEntity.getDisplayName() });
/*  617 */     chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
/*  618 */     this.serverController.getConfigurationManager().sendChatMsg((IChatComponent)chatcomponenttranslation);
/*  619 */     this.playerEntity.mountEntityAndWakeUp();
/*  620 */     this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
/*      */     
/*  622 */     if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
/*  623 */       logger.info("Stopping singleplayer server as player logged out");
/*  624 */       this.serverController.initiateShutdown();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void sendPacket(final Packet packetIn) {
/*  629 */     if (packetIn instanceof S02PacketChat) {
/*  630 */       S02PacketChat s02packetchat = (S02PacketChat)packetIn;
/*  631 */       EntityPlayer.EnumChatVisibility entityplayer$enumchatvisibility = this.playerEntity.getChatVisibility();
/*      */       
/*  633 */       if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.HIDDEN) {
/*      */         return;
/*      */       }
/*      */       
/*  637 */       if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.SYSTEM && !s02packetchat.isChat()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/*  643 */       this.netManager.sendPacket(packetIn);
/*  644 */     } catch (Throwable throwable) {
/*  645 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Sending packet");
/*  646 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Packet being sent");
/*  647 */       crashreportcategory.addCrashSectionCallable("Packet class", new Callable<String>() {
/*      */             public String call() throws Exception {
/*  649 */               return packetIn.getClass().getCanonicalName();
/*      */             }
/*      */           });
/*  652 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processHeldItemChange(C09PacketHeldItemChange packetIn) {
/*  660 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  662 */     if (packetIn.getSlotId() >= 0 && packetIn.getSlotId() < InventoryPlayer.getHotbarSize()) {
/*  663 */       this.playerEntity.inventory.currentItem = packetIn.getSlotId();
/*  664 */       this.playerEntity.markPlayerActive();
/*      */     } else {
/*  666 */       logger.warn(String.valueOf(this.playerEntity.getName()) + " tried to set an invalid carried item");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processChatMessage(C01PacketChatMessage packetIn) {
/*  674 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  676 */     if (this.playerEntity.getChatVisibility() == EntityPlayer.EnumChatVisibility.HIDDEN) {
/*  677 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
/*  678 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/*  679 */       sendPacket((Packet)new S02PacketChat((IChatComponent)chatcomponenttranslation));
/*      */     } else {
/*  681 */       this.playerEntity.markPlayerActive();
/*  682 */       String s = packetIn.getMessage();
/*  683 */       s = StringUtils.normalizeSpace(s);
/*      */       
/*  685 */       for (int i = 0; i < s.length(); i++) {
/*  686 */         if (!ChatAllowedCharacters.isAllowedCharacter(s.charAt(i))) {
/*  687 */           kickPlayerFromServer("Illegal characters in chat");
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  692 */       if (s.startsWith("/")) {
/*  693 */         handleSlashCommand(s);
/*      */       } else {
/*  695 */         ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("chat.type.text", new Object[] { this.playerEntity.getDisplayName(), s });
/*  696 */         this.serverController.getConfigurationManager().sendChatMsgImpl((IChatComponent)chatComponentTranslation, false);
/*      */       } 
/*      */       
/*  699 */       this.chatSpamThresholdCount += 20;
/*      */       
/*  701 */       if (this.chatSpamThresholdCount > 200 && !this.serverController.getConfigurationManager().canSendCommands(this.playerEntity.getGameProfile())) {
/*  702 */         kickPlayerFromServer("disconnect.spam");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleSlashCommand(String command) {
/*  711 */     this.serverController.getCommandManager().executeCommand((ICommandSender)this.playerEntity, command);
/*      */   }
/*      */   
/*      */   public void handleAnimation(C0APacketAnimation packetIn) {
/*  715 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  716 */     this.playerEntity.markPlayerActive();
/*  717 */     this.playerEntity.swingItem();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processEntityAction(C0BPacketEntityAction packetIn) {
/*  725 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  726 */     this.playerEntity.markPlayerActive();
/*      */     
/*  728 */     switch (packetIn.getAction()) {
/*      */       case START_SNEAKING:
/*  730 */         this.playerEntity.setSneaking(true);
/*      */         return;
/*      */       
/*      */       case STOP_SNEAKING:
/*  734 */         this.playerEntity.setSneaking(false);
/*      */         return;
/*      */       
/*      */       case START_SPRINTING:
/*  738 */         this.playerEntity.setSprinting(true);
/*      */         return;
/*      */       
/*      */       case STOP_SPRINTING:
/*  742 */         this.playerEntity.setSprinting(false);
/*      */         return;
/*      */       
/*      */       case STOP_SLEEPING:
/*  746 */         this.playerEntity.wakeUpPlayer(false, true, true);
/*  747 */         this.hasMoved = false;
/*      */         return;
/*      */       
/*      */       case RIDING_JUMP:
/*  751 */         if (this.playerEntity.ridingEntity instanceof EntityHorse) {
/*  752 */           ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(packetIn.getAuxData());
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case null:
/*  758 */         if (this.playerEntity.ridingEntity instanceof EntityHorse) {
/*  759 */           ((EntityHorse)this.playerEntity.ridingEntity).openGUI((EntityPlayer)this.playerEntity);
/*      */         }
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  765 */     throw new IllegalArgumentException("Invalid client command!");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processUseEntity(C02PacketUseEntity packetIn) {
/*  774 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  775 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  776 */     Entity entity = packetIn.getEntityFromWorld((World)worldserver);
/*  777 */     this.playerEntity.markPlayerActive();
/*      */     
/*  779 */     if (entity != null) {
/*  780 */       boolean flag = this.playerEntity.canEntityBeSeen(entity);
/*  781 */       double d0 = 36.0D;
/*      */       
/*  783 */       if (!flag) {
/*  784 */         d0 = 9.0D;
/*      */       }
/*      */       
/*  787 */       if (this.playerEntity.getDistanceSqToEntity(entity) < d0) {
/*  788 */         if (packetIn.getAction() == C02PacketUseEntity.Action.INTERACT) {
/*  789 */           this.playerEntity.interactWith(entity);
/*  790 */         } else if (packetIn.getAction() == C02PacketUseEntity.Action.INTERACT_AT) {
/*  791 */           entity.interactAt((EntityPlayer)this.playerEntity, packetIn.getHitVec());
/*  792 */         } else if (packetIn.getAction() == C02PacketUseEntity.Action.ATTACK) {
/*  793 */           if (entity instanceof EntityItem || entity instanceof net.minecraft.entity.item.EntityXPOrb || entity instanceof net.minecraft.entity.projectile.EntityArrow || entity == this.playerEntity) {
/*  794 */             kickPlayerFromServer("Attempting to attack an invalid entity");
/*  795 */             this.serverController.logWarning("Player " + this.playerEntity.getName() + " tried to attack an invalid entity");
/*      */             
/*      */             return;
/*      */           } 
/*  799 */           this.playerEntity.attackTargetEntityWithCurrentItem(entity);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClientStatus(C16PacketClientStatus packetIn) {
/*  810 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  811 */     this.playerEntity.markPlayerActive();
/*  812 */     C16PacketClientStatus.EnumState c16packetclientstatus$enumstate = packetIn.getStatus();
/*      */     
/*  814 */     switch (c16packetclientstatus$enumstate) {
/*      */       case PERFORM_RESPAWN:
/*  816 */         if (this.playerEntity.playerConqueredTheEnd) {
/*  817 */           this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, true); break;
/*  818 */         }  if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
/*  819 */           if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
/*  820 */             this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
/*  821 */             this.serverController.deleteWorldAndStopServer(); break;
/*      */           } 
/*  823 */           UserListBansEntry userlistbansentry = new UserListBansEntry(this.playerEntity.getGameProfile(), null, "(You just lost the game)", null, "Death in Hardcore");
/*  824 */           this.serverController.getConfigurationManager().getBannedPlayers().addEntry((UserListEntry)userlistbansentry);
/*  825 */           this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
/*      */           break;
/*      */         } 
/*  828 */         if (this.playerEntity.getHealth() > 0.0F) {
/*      */           return;
/*      */         }
/*      */         
/*  832 */         this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, false);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case REQUEST_STATS:
/*  838 */         this.playerEntity.getStatFile().func_150876_a(this.playerEntity);
/*      */         break;
/*      */       
/*      */       case null:
/*  842 */         this.playerEntity.triggerAchievement((StatBase)AchievementList.openInventory);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCloseWindow(C0DPacketCloseWindow packetIn) {
/*  850 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  851 */     this.playerEntity.closeContainer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClickWindow(C0EPacketClickWindow packetIn) {
/*  860 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  861 */     this.playerEntity.markPlayerActive();
/*      */     
/*  863 */     if (this.playerEntity.openContainer.windowId == packetIn.getWindowId() && this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity)) {
/*  864 */       if (this.playerEntity.isSpectator()) {
/*  865 */         List<ItemStack> list = Lists.newArrayList();
/*      */         
/*  867 */         for (int i = 0; i < this.playerEntity.openContainer.inventorySlots.size(); i++) {
/*  868 */           list.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(i)).getStack());
/*      */         }
/*      */         
/*  871 */         this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, list);
/*      */       } else {
/*  873 */         ItemStack itemstack = this.playerEntity.openContainer.slotClick(packetIn.getSlotId(), packetIn.getUsedButton(), packetIn.getMode(), (EntityPlayer)this.playerEntity);
/*      */         
/*  875 */         if (ItemStack.areItemStacksEqual(packetIn.getClickedItem(), itemstack)) {
/*  876 */           this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S32PacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
/*  877 */           this.playerEntity.isChangingQuantityOnly = true;
/*  878 */           this.playerEntity.openContainer.detectAndSendChanges();
/*  879 */           this.playerEntity.updateHeldItem();
/*  880 */           this.playerEntity.isChangingQuantityOnly = false;
/*      */         } else {
/*  882 */           this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, Short.valueOf(packetIn.getActionNumber()));
/*  883 */           this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S32PacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), false));
/*  884 */           this.playerEntity.openContainer.setCanCraft((EntityPlayer)this.playerEntity, false);
/*  885 */           List<ItemStack> list1 = Lists.newArrayList();
/*      */           
/*  887 */           for (int j = 0; j < this.playerEntity.openContainer.inventorySlots.size(); j++) {
/*  888 */             list1.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(j)).getStack());
/*      */           }
/*      */           
/*  891 */           this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, list1);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processEnchantItem(C11PacketEnchantItem packetIn) {
/*  902 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  903 */     this.playerEntity.markPlayerActive();
/*      */     
/*  905 */     if (this.playerEntity.openContainer.windowId == packetIn.getWindowId() && this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity) && !this.playerEntity.isSpectator()) {
/*  906 */       this.playerEntity.openContainer.enchantItem((EntityPlayer)this.playerEntity, packetIn.getButton());
/*  907 */       this.playerEntity.openContainer.detectAndSendChanges();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCreativeInventoryAction(C10PacketCreativeInventoryAction packetIn) {
/*  915 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  917 */     if (this.playerEntity.theItemInWorldManager.isCreative()) {
/*  918 */       boolean flag = (packetIn.getSlotId() < 0);
/*  919 */       ItemStack itemstack = packetIn.getStack();
/*      */       
/*  921 */       if (itemstack != null && itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("BlockEntityTag", 10)) {
/*  922 */         NBTTagCompound nbttagcompound = itemstack.getTagCompound().getCompoundTag("BlockEntityTag");
/*      */         
/*  924 */         if (nbttagcompound.hasKey("x") && nbttagcompound.hasKey("y") && nbttagcompound.hasKey("z")) {
/*  925 */           BlockPos blockpos = new BlockPos(nbttagcompound.getInteger("x"), nbttagcompound.getInteger("y"), nbttagcompound.getInteger("z"));
/*  926 */           TileEntity tileentity = this.playerEntity.worldObj.getTileEntity(blockpos);
/*      */           
/*  928 */           if (tileentity != null) {
/*  929 */             NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  930 */             tileentity.writeToNBT(nbttagcompound1);
/*  931 */             nbttagcompound1.removeTag("x");
/*  932 */             nbttagcompound1.removeTag("y");
/*  933 */             nbttagcompound1.removeTag("z");
/*  934 */             itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  939 */       boolean flag1 = (packetIn.getSlotId() >= 1 && packetIn.getSlotId() < 36 + InventoryPlayer.getHotbarSize());
/*  940 */       boolean flag2 = !(itemstack != null && itemstack.getItem() == null);
/*  941 */       boolean flag3 = !(itemstack != null && (itemstack.getMetadata() < 0 || itemstack.stackSize > 64 || itemstack.stackSize <= 0));
/*      */       
/*  943 */       if (flag1 && flag2 && flag3) {
/*  944 */         if (itemstack == null) {
/*  945 */           this.playerEntity.inventoryContainer.putStackInSlot(packetIn.getSlotId(), null);
/*      */         } else {
/*  947 */           this.playerEntity.inventoryContainer.putStackInSlot(packetIn.getSlotId(), itemstack);
/*      */         } 
/*      */         
/*  950 */         this.playerEntity.inventoryContainer.setCanCraft((EntityPlayer)this.playerEntity, true);
/*  951 */       } else if (flag && flag2 && flag3 && this.itemDropThreshold < 200) {
/*  952 */         this.itemDropThreshold += 20;
/*  953 */         EntityItem entityitem = this.playerEntity.dropPlayerItemWithRandomChoice(itemstack, true);
/*      */         
/*  955 */         if (entityitem != null) {
/*  956 */           entityitem.setAgeToCreativeDespawnTime();
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
/*      */   public void processConfirmTransaction(C0FPacketConfirmTransaction packetIn) {
/*  968 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  969 */     Short oshort = (Short)this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
/*      */     
/*  971 */     if (oshort != null && packetIn.getUid() == oshort.shortValue() && this.playerEntity.openContainer.windowId == packetIn.getWindowId() && !this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity) && !this.playerEntity.isSpectator()) {
/*  972 */       this.playerEntity.openContainer.setCanCraft((EntityPlayer)this.playerEntity, true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void processUpdateSign(C12PacketUpdateSign packetIn) {
/*  977 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  978 */     this.playerEntity.markPlayerActive();
/*  979 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  980 */     BlockPos blockpos = packetIn.getPosition();
/*      */     
/*  982 */     if (worldserver.isBlockLoaded(blockpos)) {
/*  983 */       TileEntity tileentity = worldserver.getTileEntity(blockpos);
/*      */       
/*  985 */       if (!(tileentity instanceof TileEntitySign)) {
/*      */         return;
/*      */       }
/*      */       
/*  989 */       TileEntitySign tileentitysign = (TileEntitySign)tileentity;
/*      */       
/*  991 */       if (!tileentitysign.getIsEditable() || tileentitysign.getPlayer() != this.playerEntity) {
/*  992 */         this.serverController.logWarning("Player " + this.playerEntity.getName() + " just tried to change non-editable sign");
/*      */         
/*      */         return;
/*      */       } 
/*  996 */       IChatComponent[] aichatcomponent = packetIn.getLines();
/*      */       
/*  998 */       for (int i = 0; i < aichatcomponent.length; i++) {
/*  999 */         tileentitysign.signText[i] = (IChatComponent)new ChatComponentText(EnumChatFormatting.getTextWithoutFormattingCodes(aichatcomponent[i].getUnformattedText()));
/*      */       }
/*      */       
/* 1002 */       tileentitysign.markDirty();
/* 1003 */       worldserver.markBlockForUpdate(blockpos);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processKeepAlive(C00PacketKeepAlive packetIn) {
/* 1011 */     if (packetIn.getKey() == this.field_147378_h) {
/* 1012 */       int i = (int)(currentTimeMillis() - this.lastPingTime);
/* 1013 */       this.playerEntity.ping = (this.playerEntity.ping * 3 + i) / 4;
/*      */     } 
/*      */   }
/*      */   
/*      */   private long currentTimeMillis() {
/* 1018 */     return System.nanoTime() / 1000000L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerAbilities(C13PacketPlayerAbilities packetIn) {
/* 1025 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1026 */     this.playerEntity.capabilities.isFlying = (packetIn.isFlying() && this.playerEntity.capabilities.allowFlying);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processTabComplete(C14PacketTabComplete packetIn) {
/* 1033 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1034 */     List<String> list = Lists.newArrayList();
/*      */     
/* 1036 */     for (String s : this.serverController.getTabCompletions((ICommandSender)this.playerEntity, packetIn.getMessage(), packetIn.getTargetBlock())) {
/* 1037 */       list.add(s);
/*      */     }
/*      */     
/* 1040 */     this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S3APacketTabComplete(list.<String>toArray(new String[list.size()])));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClientSettings(C15PacketClientSettings packetIn) {
/* 1048 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1049 */     this.playerEntity.handleClientSettings(packetIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processVanilla250Packet(C17PacketCustomPayload packetIn) {
/* 1056 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/* 1058 */     if ("MC|BEdit".equals(packetIn.getChannelName())) {
/* 1059 */       PacketBuffer packetbuffer3 = new PacketBuffer(Unpooled.wrappedBuffer(packetIn.getBufferData()));
/*      */       
/*      */       try {
/* 1062 */         ItemStack itemstack1 = packetbuffer3.readItemStackFromBuffer();
/*      */         
/* 1064 */         if (itemstack1 != null) {
/* 1065 */           if (!ItemWritableBook.isNBTValid(itemstack1.getTagCompound())) {
/* 1066 */             throw new IOException("Invalid book tag!");
/*      */           }
/*      */           
/* 1069 */           ItemStack itemstack3 = this.playerEntity.inventory.getCurrentItem();
/*      */           
/* 1071 */           if (itemstack3 == null) {
/*      */             return;
/*      */           }
/*      */           
/* 1075 */           if (itemstack1.getItem() == Items.writable_book && itemstack1.getItem() == itemstack3.getItem()) {
/* 1076 */             itemstack3.setTagInfo("pages", (NBTBase)itemstack1.getTagCompound().getTagList("pages", 8));
/*      */           }
/*      */           
/*      */           return;
/*      */         } 
/* 1081 */       } catch (Exception exception3) {
/* 1082 */         logger.error("Couldn't handle book info", exception3);
/*      */         return;
/*      */       } finally {
/* 1085 */         packetbuffer3.release();
/*      */       } 
/*      */       return;
/*      */     } 
/* 1089 */     if ("MC|BSign".equals(packetIn.getChannelName())) {
/* 1090 */       PacketBuffer packetbuffer2 = new PacketBuffer(Unpooled.wrappedBuffer(packetIn.getBufferData()));
/*      */       
/*      */       try {
/* 1093 */         ItemStack itemstack = packetbuffer2.readItemStackFromBuffer();
/*      */         
/* 1095 */         if (itemstack != null) {
/* 1096 */           if (!ItemEditableBook.validBookTagContents(itemstack.getTagCompound())) {
/* 1097 */             throw new IOException("Invalid book tag!");
/*      */           }
/*      */           
/* 1100 */           ItemStack itemstack2 = this.playerEntity.inventory.getCurrentItem();
/*      */           
/* 1102 */           if (itemstack2 == null) {
/*      */             return;
/*      */           }
/*      */           
/* 1106 */           if (itemstack.getItem() == Items.written_book && itemstack2.getItem() == Items.writable_book) {
/* 1107 */             itemstack2.setTagInfo("author", (NBTBase)new NBTTagString(this.playerEntity.getName()));
/* 1108 */             itemstack2.setTagInfo("title", (NBTBase)new NBTTagString(itemstack.getTagCompound().getString("title")));
/* 1109 */             itemstack2.setTagInfo("pages", (NBTBase)itemstack.getTagCompound().getTagList("pages", 8));
/* 1110 */             itemstack2.setItem(Items.written_book);
/*      */           } 
/*      */           
/*      */           return;
/*      */         } 
/* 1115 */       } catch (Exception exception4) {
/* 1116 */         logger.error("Couldn't sign book", exception4);
/*      */         return;
/*      */       } finally {
/* 1119 */         packetbuffer2.release();
/*      */       } 
/*      */       return;
/*      */     } 
/* 1123 */     if ("MC|TrSel".equals(packetIn.getChannelName())) {
/*      */       try {
/* 1125 */         int i = packetIn.getBufferData().readInt();
/* 1126 */         Container container = this.playerEntity.openContainer;
/*      */         
/* 1128 */         if (container instanceof ContainerMerchant) {
/* 1129 */           ((ContainerMerchant)container).setCurrentRecipeIndex(i);
/*      */         }
/* 1131 */       } catch (Exception exception2) {
/* 1132 */         logger.error("Couldn't select trade", exception2);
/*      */       } 
/* 1134 */     } else if ("MC|AdvCdm".equals(packetIn.getChannelName())) {
/* 1135 */       if (!this.serverController.isCommandBlockEnabled()) {
/* 1136 */         this.playerEntity.addChatMessage((IChatComponent)new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
/* 1137 */       } else if (this.playerEntity.canCommandSenderUseCommand(2, "") && this.playerEntity.capabilities.isCreativeMode) {
/* 1138 */         PacketBuffer packetbuffer = packetIn.getBufferData();
/*      */         
/*      */         try {
/* 1141 */           int j = packetbuffer.readByte();
/* 1142 */           CommandBlockLogic commandblocklogic = null;
/*      */           
/* 1144 */           if (j == 0) {
/* 1145 */             TileEntity tileentity = this.playerEntity.worldObj.getTileEntity(new BlockPos(packetbuffer.readInt(), packetbuffer.readInt(), packetbuffer.readInt()));
/*      */             
/* 1147 */             if (tileentity instanceof TileEntityCommandBlock) {
/* 1148 */               commandblocklogic = ((TileEntityCommandBlock)tileentity).getCommandBlockLogic();
/*      */             }
/* 1150 */           } else if (j == 1) {
/* 1151 */             Entity entity = this.playerEntity.worldObj.getEntityByID(packetbuffer.readInt());
/*      */             
/* 1153 */             if (entity instanceof EntityMinecartCommandBlock) {
/* 1154 */               commandblocklogic = ((EntityMinecartCommandBlock)entity).getCommandBlockLogic();
/*      */             }
/*      */           } 
/*      */           
/* 1158 */           String s1 = packetbuffer.readStringFromBuffer(packetbuffer.readableBytes());
/* 1159 */           boolean flag = packetbuffer.readBoolean();
/*      */           
/* 1161 */           if (commandblocklogic != null) {
/* 1162 */             commandblocklogic.setCommand(s1);
/* 1163 */             commandblocklogic.setTrackOutput(flag);
/*      */             
/* 1165 */             if (!flag) {
/* 1166 */               commandblocklogic.setLastOutput(null);
/*      */             }
/*      */             
/* 1169 */             commandblocklogic.updateCommand();
/* 1170 */             this.playerEntity.addChatMessage((IChatComponent)new ChatComponentTranslation("advMode.setCommand.success", new Object[] { s1 }));
/*      */           } 
/* 1172 */         } catch (Exception exception1) {
/* 1173 */           logger.error("Couldn't set command block", exception1);
/*      */         } finally {
/* 1175 */           packetbuffer.release();
/*      */         } 
/*      */       } else {
/* 1178 */         this.playerEntity.addChatMessage((IChatComponent)new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
/*      */       } 
/* 1180 */     } else if ("MC|Beacon".equals(packetIn.getChannelName())) {
/* 1181 */       if (this.playerEntity.openContainer instanceof ContainerBeacon) {
/*      */         try {
/* 1183 */           PacketBuffer packetbuffer1 = packetIn.getBufferData();
/* 1184 */           int k = packetbuffer1.readInt();
/* 1185 */           int l = packetbuffer1.readInt();
/* 1186 */           ContainerBeacon containerbeacon = (ContainerBeacon)this.playerEntity.openContainer;
/* 1187 */           Slot slot = containerbeacon.getSlot(0);
/*      */           
/* 1189 */           if (slot.getHasStack()) {
/* 1190 */             slot.decrStackSize(1);
/* 1191 */             IInventory iinventory = containerbeacon.func_180611_e();
/* 1192 */             iinventory.setField(1, k);
/* 1193 */             iinventory.setField(2, l);
/* 1194 */             iinventory.markDirty();
/*      */           } 
/* 1196 */         } catch (Exception exception) {
/* 1197 */           logger.error("Couldn't set beacon", exception);
/*      */         } 
/*      */       }
/* 1200 */     } else if ("MC|ItemName".equals(packetIn.getChannelName()) && this.playerEntity.openContainer instanceof ContainerRepair) {
/* 1201 */       ContainerRepair containerrepair = (ContainerRepair)this.playerEntity.openContainer;
/*      */       
/* 1203 */       if (packetIn.getBufferData() != null && packetIn.getBufferData().readableBytes() >= 1) {
/* 1204 */         String s = ChatAllowedCharacters.filterAllowedCharacters(packetIn.getBufferData().readStringFromBuffer(32767));
/*      */         
/* 1206 */         if (s.length() <= 30) {
/* 1207 */           containerrepair.updateItemName(s);
/*      */         }
/*      */       } else {
/* 1210 */         containerrepair.updateItemName("");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\network\NetHandlerPlayServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */