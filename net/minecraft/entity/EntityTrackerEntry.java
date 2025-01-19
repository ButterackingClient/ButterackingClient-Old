/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.item.EntityPainting;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityFireball;
/*     */ import net.minecraft.entity.projectile.EntityFishHook;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*     */ import net.minecraft.network.play.server.S0APacketUseBed;
/*     */ import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
/*     */ import net.minecraft.network.play.server.S0EPacketSpawnObject;
/*     */ import net.minecraft.network.play.server.S0FPacketSpawnMob;
/*     */ import net.minecraft.network.play.server.S10PacketSpawnPainting;
/*     */ import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
/*     */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*     */ import net.minecraft.network.play.server.S14PacketEntity;
/*     */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*     */ import net.minecraft.network.play.server.S19PacketEntityHeadLook;
/*     */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*     */ import net.minecraft.network.play.server.S1CPacketEntityMetadata;
/*     */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*     */ import net.minecraft.network.play.server.S20PacketEntityProperties;
/*     */ import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTrackerEntry
/*     */ {
/*  66 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   public Entity trackedEntity;
/*     */ 
/*     */   
/*     */   public int trackingDistanceThreshold;
/*     */ 
/*     */   
/*     */   public int updateFrequency;
/*     */ 
/*     */   
/*     */   public int encodedPosX;
/*     */ 
/*     */   
/*     */   public int encodedPosY;
/*     */ 
/*     */   
/*     */   public int encodedPosZ;
/*     */ 
/*     */   
/*     */   public int encodedRotationYaw;
/*     */ 
/*     */   
/*     */   public int encodedRotationPitch;
/*     */ 
/*     */   
/*     */   public int lastHeadMotion;
/*     */   
/*     */   public double lastTrackedEntityMotionX;
/*     */   
/*     */   public double lastTrackedEntityMotionY;
/*     */   
/*     */   public double motionZ;
/*     */   
/*     */   public int updateCounter;
/*     */   
/*     */   private double lastTrackedEntityPosX;
/*     */   
/*     */   private double lastTrackedEntityPosY;
/*     */   
/*     */   private double lastTrackedEntityPosZ;
/*     */   
/*     */   private boolean firstUpdateDone;
/*     */   
/*     */   private boolean sendVelocityUpdates;
/*     */   
/*     */   private int ticksSinceLastForcedTeleport;
/*     */   
/*     */   private Entity field_85178_v;
/*     */   
/*     */   private boolean ridingEntity;
/*     */   
/*     */   private boolean onGround;
/*     */   
/*     */   public boolean playerEntitiesUpdated;
/*     */   
/* 123 */   public Set<EntityPlayerMP> trackingPlayers = Sets.newHashSet();
/*     */   
/*     */   public EntityTrackerEntry(Entity trackedEntityIn, int trackingDistanceThresholdIn, int updateFrequencyIn, boolean sendVelocityUpdatesIn) {
/* 126 */     this.trackedEntity = trackedEntityIn;
/* 127 */     this.trackingDistanceThreshold = trackingDistanceThresholdIn;
/* 128 */     this.updateFrequency = updateFrequencyIn;
/* 129 */     this.sendVelocityUpdates = sendVelocityUpdatesIn;
/* 130 */     this.encodedPosX = MathHelper.floor_double(trackedEntityIn.posX * 32.0D);
/* 131 */     this.encodedPosY = MathHelper.floor_double(trackedEntityIn.posY * 32.0D);
/* 132 */     this.encodedPosZ = MathHelper.floor_double(trackedEntityIn.posZ * 32.0D);
/* 133 */     this.encodedRotationYaw = MathHelper.floor_float(trackedEntityIn.rotationYaw * 256.0F / 360.0F);
/* 134 */     this.encodedRotationPitch = MathHelper.floor_float(trackedEntityIn.rotationPitch * 256.0F / 360.0F);
/* 135 */     this.lastHeadMotion = MathHelper.floor_float(trackedEntityIn.getRotationYawHead() * 256.0F / 360.0F);
/* 136 */     this.onGround = trackedEntityIn.onGround;
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 140 */     return (p_equals_1_ instanceof EntityTrackerEntry) ? ((((EntityTrackerEntry)p_equals_1_).trackedEntity.getEntityId() == this.trackedEntity.getEntityId())) : false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 144 */     return this.trackedEntity.getEntityId();
/*     */   }
/*     */   
/*     */   public void updatePlayerList(List<EntityPlayer> players) {
/* 148 */     this.playerEntitiesUpdated = false;
/*     */     
/* 150 */     if (!this.firstUpdateDone || this.trackedEntity.getDistanceSq(this.lastTrackedEntityPosX, this.lastTrackedEntityPosY, this.lastTrackedEntityPosZ) > 16.0D) {
/* 151 */       this.lastTrackedEntityPosX = this.trackedEntity.posX;
/* 152 */       this.lastTrackedEntityPosY = this.trackedEntity.posY;
/* 153 */       this.lastTrackedEntityPosZ = this.trackedEntity.posZ;
/* 154 */       this.firstUpdateDone = true;
/* 155 */       this.playerEntitiesUpdated = true;
/* 156 */       updatePlayerEntities(players);
/*     */     } 
/*     */     
/* 159 */     if (this.field_85178_v != this.trackedEntity.ridingEntity || (this.trackedEntity.ridingEntity != null && this.updateCounter % 60 == 0)) {
/* 160 */       this.field_85178_v = this.trackedEntity.ridingEntity;
/* 161 */       sendPacketToTrackedPlayers((Packet)new S1BPacketEntityAttach(0, this.trackedEntity, this.trackedEntity.ridingEntity));
/*     */     } 
/*     */     
/* 164 */     if (this.trackedEntity instanceof EntityItemFrame && this.updateCounter % 10 == 0) {
/* 165 */       EntityItemFrame entityitemframe = (EntityItemFrame)this.trackedEntity;
/* 166 */       ItemStack itemstack = entityitemframe.getDisplayedItem();
/*     */       
/* 168 */       if (itemstack != null && itemstack.getItem() instanceof net.minecraft.item.ItemMap) {
/* 169 */         MapData mapdata = Items.filled_map.getMapData(itemstack, this.trackedEntity.worldObj);
/*     */         
/* 171 */         for (EntityPlayer entityplayer : players) {
/* 172 */           EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
/* 173 */           mapdata.updateVisiblePlayers((EntityPlayer)entityplayermp, itemstack);
/* 174 */           Packet packet = Items.filled_map.createMapDataPacket(itemstack, this.trackedEntity.worldObj, (EntityPlayer)entityplayermp);
/*     */           
/* 176 */           if (packet != null) {
/* 177 */             entityplayermp.playerNetServerHandler.sendPacket(packet);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 182 */       sendMetadataToAllAssociatedPlayers();
/*     */     } 
/*     */     
/* 185 */     if (this.updateCounter % this.updateFrequency == 0 || this.trackedEntity.isAirBorne || this.trackedEntity.getDataWatcher().hasObjectChanged()) {
/* 186 */       if (this.trackedEntity.ridingEntity == null) {
/* 187 */         S18PacketEntityTeleport s18PacketEntityTeleport; this.ticksSinceLastForcedTeleport++;
/* 188 */         int k = MathHelper.floor_double(this.trackedEntity.posX * 32.0D);
/* 189 */         int j1 = MathHelper.floor_double(this.trackedEntity.posY * 32.0D);
/* 190 */         int k1 = MathHelper.floor_double(this.trackedEntity.posZ * 32.0D);
/* 191 */         int l1 = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
/* 192 */         int i2 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
/* 193 */         int j2 = k - this.encodedPosX;
/* 194 */         int k2 = j1 - this.encodedPosY;
/* 195 */         int i = k1 - this.encodedPosZ;
/* 196 */         Packet packet1 = null;
/* 197 */         boolean flag = !(Math.abs(j2) < 4 && Math.abs(k2) < 4 && Math.abs(i) < 4 && this.updateCounter % 60 != 0);
/* 198 */         boolean flag1 = !(Math.abs(l1 - this.encodedRotationYaw) < 4 && Math.abs(i2 - this.encodedRotationPitch) < 4);
/*     */         
/* 200 */         if (this.updateCounter > 0 || this.trackedEntity instanceof EntityArrow) {
/* 201 */           if (j2 >= -128 && j2 < 128 && k2 >= -128 && k2 < 128 && i >= -128 && i < 128 && this.ticksSinceLastForcedTeleport <= 400 && !this.ridingEntity && this.onGround == this.trackedEntity.onGround) {
/* 202 */             if ((!flag || !flag1) && !(this.trackedEntity instanceof EntityArrow)) {
/* 203 */               if (flag) {
/* 204 */                 S14PacketEntity.S15PacketEntityRelMove s15PacketEntityRelMove = new S14PacketEntity.S15PacketEntityRelMove(this.trackedEntity.getEntityId(), (byte)j2, (byte)k2, (byte)i, this.trackedEntity.onGround);
/* 205 */               } else if (flag1) {
/* 206 */                 S14PacketEntity.S16PacketEntityLook s16PacketEntityLook = new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)l1, (byte)i2, this.trackedEntity.onGround);
/*     */               } 
/*     */             } else {
/* 209 */               S14PacketEntity.S17PacketEntityLookMove s17PacketEntityLookMove = new S14PacketEntity.S17PacketEntityLookMove(this.trackedEntity.getEntityId(), (byte)j2, (byte)k2, (byte)i, (byte)l1, (byte)i2, this.trackedEntity.onGround);
/*     */             } 
/*     */           } else {
/* 212 */             this.onGround = this.trackedEntity.onGround;
/* 213 */             this.ticksSinceLastForcedTeleport = 0;
/* 214 */             s18PacketEntityTeleport = new S18PacketEntityTeleport(this.trackedEntity.getEntityId(), k, j1, k1, (byte)l1, (byte)i2, this.trackedEntity.onGround);
/*     */           } 
/*     */         }
/*     */         
/* 218 */         if (this.sendVelocityUpdates) {
/* 219 */           double d0 = this.trackedEntity.motionX - this.lastTrackedEntityMotionX;
/* 220 */           double d1 = this.trackedEntity.motionY - this.lastTrackedEntityMotionY;
/* 221 */           double d2 = this.trackedEntity.motionZ - this.motionZ;
/* 222 */           double d3 = 0.02D;
/* 223 */           double d4 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */           
/* 225 */           if (d4 > d3 * d3 || (d4 > 0.0D && this.trackedEntity.motionX == 0.0D && this.trackedEntity.motionY == 0.0D && this.trackedEntity.motionZ == 0.0D)) {
/* 226 */             this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
/* 227 */             this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
/* 228 */             this.motionZ = this.trackedEntity.motionZ;
/* 229 */             sendPacketToTrackedPlayers((Packet)new S12PacketEntityVelocity(this.trackedEntity.getEntityId(), this.lastTrackedEntityMotionX, this.lastTrackedEntityMotionY, this.motionZ));
/*     */           } 
/*     */         } 
/*     */         
/* 233 */         if (s18PacketEntityTeleport != null) {
/* 234 */           sendPacketToTrackedPlayers((Packet)s18PacketEntityTeleport);
/*     */         }
/*     */         
/* 237 */         sendMetadataToAllAssociatedPlayers();
/*     */         
/* 239 */         if (flag) {
/* 240 */           this.encodedPosX = k;
/* 241 */           this.encodedPosY = j1;
/* 242 */           this.encodedPosZ = k1;
/*     */         } 
/*     */         
/* 245 */         if (flag1) {
/* 246 */           this.encodedRotationYaw = l1;
/* 247 */           this.encodedRotationPitch = i2;
/*     */         } 
/*     */         
/* 250 */         this.ridingEntity = false;
/*     */       } else {
/* 252 */         int j = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
/* 253 */         int i1 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
/* 254 */         boolean flag2 = !(Math.abs(j - this.encodedRotationYaw) < 4 && Math.abs(i1 - this.encodedRotationPitch) < 4);
/*     */         
/* 256 */         if (flag2) {
/* 257 */           sendPacketToTrackedPlayers((Packet)new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)j, (byte)i1, this.trackedEntity.onGround));
/* 258 */           this.encodedRotationYaw = j;
/* 259 */           this.encodedRotationPitch = i1;
/*     */         } 
/*     */         
/* 262 */         this.encodedPosX = MathHelper.floor_double(this.trackedEntity.posX * 32.0D);
/* 263 */         this.encodedPosY = MathHelper.floor_double(this.trackedEntity.posY * 32.0D);
/* 264 */         this.encodedPosZ = MathHelper.floor_double(this.trackedEntity.posZ * 32.0D);
/* 265 */         sendMetadataToAllAssociatedPlayers();
/* 266 */         this.ridingEntity = true;
/*     */       } 
/*     */       
/* 269 */       int l = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
/*     */       
/* 271 */       if (Math.abs(l - this.lastHeadMotion) >= 4) {
/* 272 */         sendPacketToTrackedPlayers((Packet)new S19PacketEntityHeadLook(this.trackedEntity, (byte)l));
/* 273 */         this.lastHeadMotion = l;
/*     */       } 
/*     */       
/* 276 */       this.trackedEntity.isAirBorne = false;
/*     */     } 
/*     */     
/* 279 */     this.updateCounter++;
/*     */     
/* 281 */     if (this.trackedEntity.velocityChanged) {
/* 282 */       func_151261_b((Packet)new S12PacketEntityVelocity(this.trackedEntity));
/* 283 */       this.trackedEntity.velocityChanged = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendMetadataToAllAssociatedPlayers() {
/* 292 */     DataWatcher datawatcher = this.trackedEntity.getDataWatcher();
/*     */     
/* 294 */     if (datawatcher.hasObjectChanged()) {
/* 295 */       func_151261_b((Packet)new S1CPacketEntityMetadata(this.trackedEntity.getEntityId(), datawatcher, false));
/*     */     }
/*     */     
/* 298 */     if (this.trackedEntity instanceof EntityLivingBase) {
/* 299 */       ServersideAttributeMap serversideattributemap = (ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap();
/* 300 */       Set<IAttributeInstance> set = serversideattributemap.getAttributeInstanceSet();
/*     */       
/* 302 */       if (!set.isEmpty()) {
/* 303 */         func_151261_b((Packet)new S20PacketEntityProperties(this.trackedEntity.getEntityId(), set));
/*     */       }
/*     */       
/* 306 */       set.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacketToTrackedPlayers(Packet packetIn) {
/* 314 */     for (EntityPlayerMP entityplayermp : this.trackingPlayers) {
/* 315 */       entityplayermp.playerNetServerHandler.sendPacket(packetIn);
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_151261_b(Packet packetIn) {
/* 320 */     sendPacketToTrackedPlayers(packetIn);
/*     */     
/* 322 */     if (this.trackedEntity instanceof EntityPlayerMP) {
/* 323 */       ((EntityPlayerMP)this.trackedEntity).playerNetServerHandler.sendPacket(packetIn);
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendDestroyEntityPacketToTrackedPlayers() {
/* 328 */     for (EntityPlayerMP entityplayermp : this.trackingPlayers) {
/* 329 */       entityplayermp.removeEntity(this.trackedEntity);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeFromTrackedPlayers(EntityPlayerMP playerMP) {
/* 334 */     if (this.trackingPlayers.contains(playerMP)) {
/* 335 */       playerMP.removeEntity(this.trackedEntity);
/* 336 */       this.trackingPlayers.remove(playerMP);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updatePlayerEntity(EntityPlayerMP playerMP) {
/* 341 */     if (playerMP != this.trackedEntity) {
/* 342 */       if (func_180233_c(playerMP)) {
/* 343 */         if (!this.trackingPlayers.contains(playerMP) && (isPlayerWatchingThisChunk(playerMP) || this.trackedEntity.forceSpawn)) {
/* 344 */           this.trackingPlayers.add(playerMP);
/* 345 */           Packet packet = createSpawnPacket();
/* 346 */           playerMP.playerNetServerHandler.sendPacket(packet);
/*     */           
/* 348 */           if (!this.trackedEntity.getDataWatcher().getIsBlank()) {
/* 349 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S1CPacketEntityMetadata(this.trackedEntity.getEntityId(), this.trackedEntity.getDataWatcher(), true));
/*     */           }
/*     */           
/* 352 */           NBTTagCompound nbttagcompound = this.trackedEntity.getNBTTagCompound();
/*     */           
/* 354 */           if (nbttagcompound != null) {
/* 355 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S49PacketUpdateEntityNBT(this.trackedEntity.getEntityId(), nbttagcompound));
/*     */           }
/*     */           
/* 358 */           if (this.trackedEntity instanceof EntityLivingBase) {
/* 359 */             ServersideAttributeMap serversideattributemap = (ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap();
/* 360 */             Collection<IAttributeInstance> collection = serversideattributemap.getWatchedAttributes();
/*     */             
/* 362 */             if (!collection.isEmpty()) {
/* 363 */               playerMP.playerNetServerHandler.sendPacket((Packet)new S20PacketEntityProperties(this.trackedEntity.getEntityId(), collection));
/*     */             }
/*     */           } 
/*     */           
/* 367 */           this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
/* 368 */           this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
/* 369 */           this.motionZ = this.trackedEntity.motionZ;
/*     */           
/* 371 */           if (this.sendVelocityUpdates && !(packet instanceof S0FPacketSpawnMob)) {
/* 372 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S12PacketEntityVelocity(this.trackedEntity.getEntityId(), this.trackedEntity.motionX, this.trackedEntity.motionY, this.trackedEntity.motionZ));
/*     */           }
/*     */           
/* 375 */           if (this.trackedEntity.ridingEntity != null) {
/* 376 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S1BPacketEntityAttach(0, this.trackedEntity, this.trackedEntity.ridingEntity));
/*     */           }
/*     */           
/* 379 */           if (this.trackedEntity instanceof EntityLiving && ((EntityLiving)this.trackedEntity).getLeashedToEntity() != null) {
/* 380 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S1BPacketEntityAttach(1, this.trackedEntity, ((EntityLiving)this.trackedEntity).getLeashedToEntity()));
/*     */           }
/*     */           
/* 383 */           if (this.trackedEntity instanceof EntityLivingBase) {
/* 384 */             for (int i = 0; i < 5; i++) {
/* 385 */               ItemStack itemstack = ((EntityLivingBase)this.trackedEntity).getEquipmentInSlot(i);
/*     */               
/* 387 */               if (itemstack != null) {
/* 388 */                 playerMP.playerNetServerHandler.sendPacket((Packet)new S04PacketEntityEquipment(this.trackedEntity.getEntityId(), i, itemstack));
/*     */               }
/*     */             } 
/*     */           }
/*     */           
/* 393 */           if (this.trackedEntity instanceof EntityPlayer) {
/* 394 */             EntityPlayer entityplayer = (EntityPlayer)this.trackedEntity;
/*     */             
/* 396 */             if (entityplayer.isPlayerSleeping()) {
/* 397 */               playerMP.playerNetServerHandler.sendPacket((Packet)new S0APacketUseBed(entityplayer, new BlockPos(this.trackedEntity)));
/*     */             }
/*     */           } 
/*     */           
/* 401 */           if (this.trackedEntity instanceof EntityLivingBase) {
/* 402 */             EntityLivingBase entitylivingbase = (EntityLivingBase)this.trackedEntity;
/*     */             
/* 404 */             for (PotionEffect potioneffect : entitylivingbase.getActivePotionEffects()) {
/* 405 */               playerMP.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(this.trackedEntity.getEntityId(), potioneffect));
/*     */             }
/*     */           } 
/*     */         } 
/* 409 */       } else if (this.trackingPlayers.contains(playerMP)) {
/* 410 */         this.trackingPlayers.remove(playerMP);
/* 411 */         playerMP.removeEntity(this.trackedEntity);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean func_180233_c(EntityPlayerMP playerMP) {
/* 417 */     double d0 = playerMP.posX - (this.encodedPosX / 32);
/* 418 */     double d1 = playerMP.posZ - (this.encodedPosZ / 32);
/* 419 */     return (d0 >= -this.trackingDistanceThreshold && d0 <= this.trackingDistanceThreshold && d1 >= -this.trackingDistanceThreshold && d1 <= this.trackingDistanceThreshold && this.trackedEntity.isSpectatedByPlayer(playerMP));
/*     */   }
/*     */   
/*     */   private boolean isPlayerWatchingThisChunk(EntityPlayerMP playerMP) {
/* 423 */     return playerMP.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(playerMP, this.trackedEntity.chunkCoordX, this.trackedEntity.chunkCoordZ);
/*     */   }
/*     */   
/*     */   public void updatePlayerEntities(List<EntityPlayer> players) {
/* 427 */     for (int i = 0; i < players.size(); i++) {
/* 428 */       updatePlayerEntity((EntityPlayerMP)players.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Packet createSpawnPacket() {
/* 436 */     if (this.trackedEntity.isDead) {
/* 437 */       logger.warn("Fetching addPacket for removed entity");
/*     */     }
/*     */     
/* 440 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityItem)
/* 441 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 2, 1); 
/* 442 */     if (this.trackedEntity instanceof EntityPlayerMP)
/* 443 */       return (Packet)new S0CPacketSpawnPlayer((EntityPlayer)this.trackedEntity); 
/* 444 */     if (this.trackedEntity instanceof EntityMinecart) {
/* 445 */       EntityMinecart entityminecart = (EntityMinecart)this.trackedEntity;
/* 446 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 10, entityminecart.getMinecartType().getNetworkID());
/* 447 */     }  if (this.trackedEntity instanceof net.minecraft.entity.item.EntityBoat)
/* 448 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 1); 
/* 449 */     if (this.trackedEntity instanceof net.minecraft.entity.passive.IAnimals) {
/* 450 */       this.lastHeadMotion = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
/* 451 */       return (Packet)new S0FPacketSpawnMob((EntityLivingBase)this.trackedEntity);
/* 452 */     }  if (this.trackedEntity instanceof EntityFishHook) {
/* 453 */       EntityPlayer entityPlayer = ((EntityFishHook)this.trackedEntity).angler;
/* 454 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 90, (entityPlayer != null) ? entityPlayer.getEntityId() : this.trackedEntity.getEntityId());
/* 455 */     }  if (this.trackedEntity instanceof EntityArrow) {
/* 456 */       Entity entity = ((EntityArrow)this.trackedEntity).shootingEntity;
/* 457 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 60, (entity != null) ? entity.getEntityId() : this.trackedEntity.getEntityId());
/* 458 */     }  if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntitySnowball)
/* 459 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 61); 
/* 460 */     if (this.trackedEntity instanceof EntityPotion)
/* 461 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 73, ((EntityPotion)this.trackedEntity).getPotionDamage()); 
/* 462 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityExpBottle)
/* 463 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 75); 
/* 464 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityEnderPearl)
/* 465 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 65); 
/* 466 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityEnderEye)
/* 467 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 72); 
/* 468 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityFireworkRocket)
/* 469 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 76); 
/* 470 */     if (this.trackedEntity instanceof EntityFireball) {
/* 471 */       EntityFireball entityfireball = (EntityFireball)this.trackedEntity;
/* 472 */       S0EPacketSpawnObject s0epacketspawnobject2 = null;
/* 473 */       int i = 63;
/*     */       
/* 475 */       if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntitySmallFireball) {
/* 476 */         i = 64;
/* 477 */       } else if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityWitherSkull) {
/* 478 */         i = 66;
/*     */       } 
/*     */       
/* 481 */       if (entityfireball.shootingEntity != null) {
/* 482 */         s0epacketspawnobject2 = new S0EPacketSpawnObject(this.trackedEntity, i, ((EntityFireball)this.trackedEntity).shootingEntity.getEntityId());
/*     */       } else {
/* 484 */         s0epacketspawnobject2 = new S0EPacketSpawnObject(this.trackedEntity, i, 0);
/*     */       } 
/*     */       
/* 487 */       s0epacketspawnobject2.setSpeedX((int)(entityfireball.accelerationX * 8000.0D));
/* 488 */       s0epacketspawnobject2.setSpeedY((int)(entityfireball.accelerationY * 8000.0D));
/* 489 */       s0epacketspawnobject2.setSpeedZ((int)(entityfireball.accelerationZ * 8000.0D));
/* 490 */       return (Packet)s0epacketspawnobject2;
/* 491 */     }  if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityEgg)
/* 492 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 62); 
/* 493 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityTNTPrimed)
/* 494 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 50); 
/* 495 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityEnderCrystal)
/* 496 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 51); 
/* 497 */     if (this.trackedEntity instanceof EntityFallingBlock) {
/* 498 */       EntityFallingBlock entityfallingblock = (EntityFallingBlock)this.trackedEntity;
/* 499 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 70, Block.getStateId(entityfallingblock.getBlock()));
/* 500 */     }  if (this.trackedEntity instanceof net.minecraft.entity.item.EntityArmorStand)
/* 501 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 78); 
/* 502 */     if (this.trackedEntity instanceof EntityPainting)
/* 503 */       return (Packet)new S10PacketSpawnPainting((EntityPainting)this.trackedEntity); 
/* 504 */     if (this.trackedEntity instanceof EntityItemFrame) {
/* 505 */       EntityItemFrame entityitemframe = (EntityItemFrame)this.trackedEntity;
/* 506 */       S0EPacketSpawnObject s0epacketspawnobject1 = new S0EPacketSpawnObject(this.trackedEntity, 71, entityitemframe.facingDirection.getHorizontalIndex());
/* 507 */       BlockPos blockpos1 = entityitemframe.getHangingPosition();
/* 508 */       s0epacketspawnobject1.setX(MathHelper.floor_float((blockpos1.getX() * 32)));
/* 509 */       s0epacketspawnobject1.setY(MathHelper.floor_float((blockpos1.getY() * 32)));
/* 510 */       s0epacketspawnobject1.setZ(MathHelper.floor_float((blockpos1.getZ() * 32)));
/* 511 */       return (Packet)s0epacketspawnobject1;
/* 512 */     }  if (this.trackedEntity instanceof EntityLeashKnot) {
/* 513 */       EntityLeashKnot entityleashknot = (EntityLeashKnot)this.trackedEntity;
/* 514 */       S0EPacketSpawnObject s0epacketspawnobject = new S0EPacketSpawnObject(this.trackedEntity, 77);
/* 515 */       BlockPos blockpos = entityleashknot.getHangingPosition();
/* 516 */       s0epacketspawnobject.setX(MathHelper.floor_float((blockpos.getX() * 32)));
/* 517 */       s0epacketspawnobject.setY(MathHelper.floor_float((blockpos.getY() * 32)));
/* 518 */       s0epacketspawnobject.setZ(MathHelper.floor_float((blockpos.getZ() * 32)));
/* 519 */       return (Packet)s0epacketspawnobject;
/* 520 */     }  if (this.trackedEntity instanceof EntityXPOrb) {
/* 521 */       return (Packet)new S11PacketSpawnExperienceOrb((EntityXPOrb)this.trackedEntity);
/*     */     }
/* 523 */     throw new IllegalArgumentException("Don't know how to add " + this.trackedEntity.getClass() + "!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTrackedPlayerSymmetric(EntityPlayerMP playerMP) {
/* 531 */     if (this.trackingPlayers.contains(playerMP)) {
/* 532 */       this.trackingPlayers.remove(playerMP);
/* 533 */       playerMP.removeEntity(this.trackedEntity);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\EntityTrackerEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */