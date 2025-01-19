/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.util.IntHashMap;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.chunk.Chunk;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTracker
/*     */ {
/*  46 */   private static final Logger logger = LogManager.getLogger();
/*     */   private final WorldServer theWorld;
/*  48 */   private Set<EntityTrackerEntry> trackedEntities = Sets.newHashSet();
/*  49 */   private IntHashMap<EntityTrackerEntry> trackedEntityHashTable = new IntHashMap();
/*     */   private int maxTrackingDistanceThreshold;
/*     */   
/*     */   public EntityTracker(WorldServer theWorldIn) {
/*  53 */     this.theWorld = theWorldIn;
/*  54 */     this.maxTrackingDistanceThreshold = theWorldIn.getMinecraftServer().getConfigurationManager().getEntityViewDistance();
/*     */   }
/*     */   
/*     */   public void trackEntity(Entity entityIn) {
/*  58 */     if (entityIn instanceof EntityPlayerMP) {
/*  59 */       trackEntity(entityIn, 512, 2);
/*  60 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)entityIn;
/*     */       
/*  62 */       for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
/*  63 */         if (entitytrackerentry.trackedEntity != entityplayermp) {
/*  64 */           entitytrackerentry.updatePlayerEntity(entityplayermp);
/*     */         }
/*     */       } 
/*  67 */     } else if (entityIn instanceof net.minecraft.entity.projectile.EntityFishHook) {
/*  68 */       addEntityToTracker(entityIn, 64, 5, true);
/*  69 */     } else if (entityIn instanceof net.minecraft.entity.projectile.EntityArrow) {
/*  70 */       addEntityToTracker(entityIn, 64, 20, false);
/*  71 */     } else if (entityIn instanceof net.minecraft.entity.projectile.EntitySmallFireball) {
/*  72 */       addEntityToTracker(entityIn, 64, 10, false);
/*  73 */     } else if (entityIn instanceof net.minecraft.entity.projectile.EntityFireball) {
/*  74 */       addEntityToTracker(entityIn, 64, 10, false);
/*  75 */     } else if (entityIn instanceof net.minecraft.entity.projectile.EntitySnowball) {
/*  76 */       addEntityToTracker(entityIn, 64, 10, true);
/*  77 */     } else if (entityIn instanceof net.minecraft.entity.item.EntityEnderPearl) {
/*  78 */       addEntityToTracker(entityIn, 64, 10, true);
/*  79 */     } else if (entityIn instanceof net.minecraft.entity.item.EntityEnderEye) {
/*  80 */       addEntityToTracker(entityIn, 64, 4, true);
/*  81 */     } else if (entityIn instanceof net.minecraft.entity.projectile.EntityEgg) {
/*  82 */       addEntityToTracker(entityIn, 64, 10, true);
/*  83 */     } else if (entityIn instanceof net.minecraft.entity.projectile.EntityPotion) {
/*  84 */       addEntityToTracker(entityIn, 64, 10, true);
/*  85 */     } else if (entityIn instanceof net.minecraft.entity.item.EntityExpBottle) {
/*  86 */       addEntityToTracker(entityIn, 64, 10, true);
/*  87 */     } else if (entityIn instanceof net.minecraft.entity.item.EntityFireworkRocket) {
/*  88 */       addEntityToTracker(entityIn, 64, 10, true);
/*  89 */     } else if (entityIn instanceof net.minecraft.entity.item.EntityItem) {
/*  90 */       addEntityToTracker(entityIn, 64, 20, true);
/*  91 */     } else if (entityIn instanceof net.minecraft.entity.item.EntityMinecart) {
/*  92 */       addEntityToTracker(entityIn, 80, 3, true);
/*  93 */     } else if (entityIn instanceof net.minecraft.entity.item.EntityBoat) {
/*  94 */       addEntityToTracker(entityIn, 80, 3, true);
/*  95 */     } else if (entityIn instanceof net.minecraft.entity.passive.EntitySquid) {
/*  96 */       addEntityToTracker(entityIn, 64, 3, true);
/*  97 */     } else if (entityIn instanceof net.minecraft.entity.boss.EntityWither) {
/*  98 */       addEntityToTracker(entityIn, 80, 3, false);
/*  99 */     } else if (entityIn instanceof net.minecraft.entity.passive.EntityBat) {
/* 100 */       addEntityToTracker(entityIn, 80, 3, false);
/* 101 */     } else if (entityIn instanceof net.minecraft.entity.boss.EntityDragon) {
/* 102 */       addEntityToTracker(entityIn, 160, 3, true);
/* 103 */     } else if (entityIn instanceof net.minecraft.entity.passive.IAnimals) {
/* 104 */       addEntityToTracker(entityIn, 80, 3, true);
/* 105 */     } else if (entityIn instanceof net.minecraft.entity.item.EntityTNTPrimed) {
/* 106 */       addEntityToTracker(entityIn, 160, 10, true);
/* 107 */     } else if (entityIn instanceof net.minecraft.entity.item.EntityFallingBlock) {
/* 108 */       addEntityToTracker(entityIn, 160, 20, true);
/* 109 */     } else if (entityIn instanceof EntityHanging) {
/* 110 */       addEntityToTracker(entityIn, 160, 2147483647, false);
/* 111 */     } else if (entityIn instanceof net.minecraft.entity.item.EntityArmorStand) {
/* 112 */       addEntityToTracker(entityIn, 160, 3, true);
/* 113 */     } else if (entityIn instanceof net.minecraft.entity.item.EntityXPOrb) {
/* 114 */       addEntityToTracker(entityIn, 160, 20, true);
/* 115 */     } else if (entityIn instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/* 116 */       addEntityToTracker(entityIn, 256, 2147483647, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void trackEntity(Entity entityIn, int trackingRange, int updateFrequency) {
/* 121 */     addEntityToTracker(entityIn, trackingRange, updateFrequency, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntityToTracker(Entity entityIn, int trackingRange, final int updateFrequency, boolean sendVelocityUpdates) {
/* 128 */     if (trackingRange > this.maxTrackingDistanceThreshold) {
/* 129 */       trackingRange = this.maxTrackingDistanceThreshold;
/*     */     }
/*     */     
/*     */     try {
/* 133 */       if (this.trackedEntityHashTable.containsItem(entityIn.getEntityId())) {
/* 134 */         throw new IllegalStateException("Entity is already tracked!");
/*     */       }
/*     */       
/* 137 */       EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entityIn, trackingRange, updateFrequency, sendVelocityUpdates);
/* 138 */       this.trackedEntities.add(entitytrackerentry);
/* 139 */       this.trackedEntityHashTable.addKey(entityIn.getEntityId(), entitytrackerentry);
/* 140 */       entitytrackerentry.updatePlayerEntities(this.theWorld.playerEntities);
/* 141 */     } catch (Throwable throwable) {
/* 142 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding entity to track");
/* 143 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity To Track");
/* 144 */       crashreportcategory.addCrashSection("Tracking range", String.valueOf(trackingRange) + " blocks");
/* 145 */       crashreportcategory.addCrashSectionCallable("Update interval", new Callable<String>() {
/*     */             public String call() throws Exception {
/* 147 */               String s = "Once per " + updateFrequency + " ticks";
/*     */               
/* 149 */               if (updateFrequency == Integer.MAX_VALUE) {
/* 150 */                 s = "Maximum (" + s + ")";
/*     */               }
/*     */               
/* 153 */               return s;
/*     */             }
/*     */           });
/* 156 */       entityIn.addEntityCrashInfo(crashreportcategory);
/* 157 */       CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Entity That Is Already Tracked");
/* 158 */       ((EntityTrackerEntry)this.trackedEntityHashTable.lookup(entityIn.getEntityId())).trackedEntity.addEntityCrashInfo(crashreportcategory1);
/*     */       
/*     */       try {
/* 161 */         throw new ReportedException(crashreport);
/* 162 */       } catch (ReportedException reportedexception) {
/* 163 */         logger.error("\"Silently\" catching entity tracking error.", (Throwable)reportedexception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void untrackEntity(Entity entityIn) {
/* 169 */     if (entityIn instanceof EntityPlayerMP) {
/* 170 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)entityIn;
/*     */       
/* 172 */       for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
/* 173 */         entitytrackerentry.removeFromTrackedPlayers(entityplayermp);
/*     */       }
/*     */     } 
/*     */     
/* 177 */     EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)this.trackedEntityHashTable.removeObject(entityIn.getEntityId());
/*     */     
/* 179 */     if (entitytrackerentry1 != null) {
/* 180 */       this.trackedEntities.remove(entitytrackerentry1);
/* 181 */       entitytrackerentry1.sendDestroyEntityPacketToTrackedPlayers();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateTrackedEntities() {
/* 186 */     List<EntityPlayerMP> list = Lists.newArrayList();
/*     */     
/* 188 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
/* 189 */       entitytrackerentry.updatePlayerList(this.theWorld.playerEntities);
/*     */       
/* 191 */       if (entitytrackerentry.playerEntitiesUpdated && entitytrackerentry.trackedEntity instanceof EntityPlayerMP) {
/* 192 */         list.add((EntityPlayerMP)entitytrackerentry.trackedEntity);
/*     */       }
/*     */     } 
/*     */     
/* 196 */     for (int i = 0; i < list.size(); i++) {
/* 197 */       EntityPlayerMP entityplayermp = list.get(i);
/*     */       
/* 199 */       for (EntityTrackerEntry entitytrackerentry1 : this.trackedEntities) {
/* 200 */         if (entitytrackerentry1.trackedEntity != entityplayermp) {
/* 201 */           entitytrackerentry1.updatePlayerEntity(entityplayermp);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_180245_a(EntityPlayerMP p_180245_1_) {
/* 208 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
/* 209 */       if (entitytrackerentry.trackedEntity == p_180245_1_) {
/* 210 */         entitytrackerentry.updatePlayerEntities(this.theWorld.playerEntities); continue;
/*     */       } 
/* 212 */       entitytrackerentry.updatePlayerEntity(p_180245_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendToAllTrackingEntity(Entity entityIn, Packet p_151247_2_) {
/* 218 */     EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntityHashTable.lookup(entityIn.getEntityId());
/*     */     
/* 220 */     if (entitytrackerentry != null) {
/* 221 */       entitytrackerentry.sendPacketToTrackedPlayers(p_151247_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_151248_b(Entity entityIn, Packet p_151248_2_) {
/* 226 */     EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntityHashTable.lookup(entityIn.getEntityId());
/*     */     
/* 228 */     if (entitytrackerentry != null) {
/* 229 */       entitytrackerentry.func_151261_b(p_151248_2_);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removePlayerFromTrackers(EntityPlayerMP p_72787_1_) {
/* 234 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
/* 235 */       entitytrackerentry.removeTrackedPlayerSymmetric(p_72787_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   public void func_85172_a(EntityPlayerMP p_85172_1_, Chunk p_85172_2_) {
/* 240 */     for (EntityTrackerEntry entitytrackerentry : this.trackedEntities) {
/* 241 */       if (entitytrackerentry.trackedEntity != p_85172_1_ && entitytrackerentry.trackedEntity.chunkCoordX == p_85172_2_.xPosition && entitytrackerentry.trackedEntity.chunkCoordZ == p_85172_2_.zPosition)
/* 242 */         entitytrackerentry.updatePlayerEntity(p_85172_1_); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\EntityTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */