/*      */ package net.minecraft.entity;
/*      */ 
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockLiquid;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.block.state.pattern.BlockPattern;
/*      */ import net.minecraft.command.CommandResultStats;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.enchantment.EnchantmentProtection;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.event.HoverEvent;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagDouble;
/*      */ import net.minecraft.nbt.NBTTagFloat;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.StatCollector;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ 
/*      */ 
/*      */ public abstract class Entity
/*      */   implements ICommandSender
/*      */ {
/*   52 */   private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*      */ 
/*      */ 
/*      */   
/*      */   private static int nextEntityID;
/*      */ 
/*      */ 
/*      */   
/*      */   private int entityId;
/*      */ 
/*      */ 
/*      */   
/*      */   public double renderDistanceWeight;
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean preventEntitySpawning;
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity riddenByEntity;
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity ridingEntity;
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean forceSpawn;
/*      */ 
/*      */ 
/*      */   
/*      */   public World worldObj;
/*      */ 
/*      */ 
/*      */   
/*      */   public double prevPosX;
/*      */ 
/*      */ 
/*      */   
/*      */   public double prevPosY;
/*      */ 
/*      */ 
/*      */   
/*      */   public double prevPosZ;
/*      */ 
/*      */ 
/*      */   
/*      */   public double posX;
/*      */ 
/*      */ 
/*      */   
/*      */   public double posY;
/*      */ 
/*      */ 
/*      */   
/*      */   public double posZ;
/*      */ 
/*      */ 
/*      */   
/*      */   public double motionX;
/*      */ 
/*      */   
/*      */   public double motionY;
/*      */ 
/*      */   
/*      */   public double motionZ;
/*      */ 
/*      */   
/*      */   public float rotationYaw;
/*      */ 
/*      */   
/*      */   public float rotationPitch;
/*      */ 
/*      */   
/*      */   public float prevRotationYaw;
/*      */ 
/*      */   
/*      */   public float prevRotationPitch;
/*      */ 
/*      */   
/*      */   private AxisAlignedBB boundingBox;
/*      */ 
/*      */   
/*      */   public boolean onGround;
/*      */ 
/*      */   
/*      */   public boolean isCollidedHorizontally;
/*      */ 
/*      */   
/*      */   public boolean isCollidedVertically;
/*      */ 
/*      */   
/*      */   public boolean isCollided;
/*      */ 
/*      */   
/*      */   public boolean velocityChanged;
/*      */ 
/*      */   
/*      */   protected boolean isInWeb;
/*      */ 
/*      */   
/*      */   private boolean isOutsideBorder;
/*      */ 
/*      */   
/*      */   public boolean isDead;
/*      */ 
/*      */   
/*      */   public float width;
/*      */ 
/*      */   
/*      */   public float height;
/*      */ 
/*      */   
/*      */   public float prevDistanceWalkedModified;
/*      */ 
/*      */   
/*      */   public float distanceWalkedModified;
/*      */ 
/*      */   
/*      */   public float distanceWalkedOnStepModified;
/*      */ 
/*      */   
/*      */   public float fallDistance;
/*      */ 
/*      */   
/*      */   private int nextStepDistance;
/*      */ 
/*      */   
/*      */   public double lastTickPosX;
/*      */ 
/*      */   
/*      */   public double lastTickPosY;
/*      */ 
/*      */   
/*      */   public double lastTickPosZ;
/*      */ 
/*      */   
/*      */   public float stepHeight;
/*      */ 
/*      */   
/*      */   public boolean noClip;
/*      */ 
/*      */   
/*      */   public float entityCollisionReduction;
/*      */ 
/*      */   
/*      */   protected Random rand;
/*      */ 
/*      */   
/*      */   public int ticksExisted;
/*      */ 
/*      */   
/*      */   public int fireResistance;
/*      */ 
/*      */   
/*      */   private int fire;
/*      */ 
/*      */   
/*      */   protected boolean inWater;
/*      */ 
/*      */   
/*      */   public int hurtResistantTime;
/*      */ 
/*      */   
/*      */   protected boolean firstUpdate;
/*      */ 
/*      */   
/*      */   protected boolean isImmuneToFire;
/*      */ 
/*      */   
/*      */   protected DataWatcher dataWatcher;
/*      */ 
/*      */   
/*      */   private double entityRiderPitchDelta;
/*      */ 
/*      */   
/*      */   private double entityRiderYawDelta;
/*      */ 
/*      */   
/*      */   public boolean addedToChunk;
/*      */ 
/*      */   
/*      */   public int chunkCoordX;
/*      */ 
/*      */   
/*      */   public int chunkCoordY;
/*      */ 
/*      */   
/*      */   public int chunkCoordZ;
/*      */ 
/*      */   
/*      */   public int serverPosX;
/*      */ 
/*      */   
/*      */   public int serverPosY;
/*      */ 
/*      */   
/*      */   public int serverPosZ;
/*      */ 
/*      */   
/*      */   public boolean ignoreFrustumCheck;
/*      */ 
/*      */   
/*      */   public boolean isAirBorne;
/*      */ 
/*      */   
/*      */   public int timeUntilPortal;
/*      */ 
/*      */   
/*      */   protected boolean inPortal;
/*      */ 
/*      */   
/*      */   protected int portalCounter;
/*      */ 
/*      */   
/*      */   public int dimension;
/*      */ 
/*      */   
/*      */   protected BlockPos lastPortalPos;
/*      */ 
/*      */   
/*      */   protected Vec3 lastPortalVec;
/*      */ 
/*      */   
/*      */   protected EnumFacing teleportDirection;
/*      */ 
/*      */   
/*      */   private boolean invulnerable;
/*      */ 
/*      */   
/*      */   protected UUID entityUniqueID;
/*      */ 
/*      */   
/*      */   private final CommandResultStats cmdResultStats;
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEntityId() {
/*  291 */     return this.entityId;
/*      */   }
/*      */   
/*      */   public void setEntityId(int id) {
/*  295 */     this.entityId = id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillCommand() {
/*  302 */     setDead();
/*      */   }
/*      */   
/*      */   public Entity(World worldIn) {
/*  306 */     this.entityId = nextEntityID++;
/*  307 */     this.renderDistanceWeight = 1.0D;
/*  308 */     this.boundingBox = ZERO_AABB;
/*  309 */     this.width = 0.6F;
/*  310 */     this.height = 1.8F;
/*  311 */     this.nextStepDistance = 1;
/*  312 */     this.rand = new Random();
/*  313 */     this.fireResistance = 1;
/*  314 */     this.firstUpdate = true;
/*  315 */     this.entityUniqueID = MathHelper.getRandomUuid(this.rand);
/*  316 */     this.cmdResultStats = new CommandResultStats();
/*  317 */     this.worldObj = worldIn;
/*  318 */     setPosition(0.0D, 0.0D, 0.0D);
/*      */     
/*  320 */     if (worldIn != null) {
/*  321 */       this.dimension = worldIn.provider.getDimensionId();
/*      */     }
/*      */     
/*  324 */     this.dataWatcher = new DataWatcher(this);
/*  325 */     this.dataWatcher.addObject(0, Byte.valueOf((byte)0));
/*  326 */     this.dataWatcher.addObject(1, Short.valueOf((short)300));
/*  327 */     this.dataWatcher.addObject(3, Byte.valueOf((byte)0));
/*  328 */     this.dataWatcher.addObject(2, "");
/*  329 */     this.dataWatcher.addObject(4, Byte.valueOf((byte)0));
/*  330 */     entityInit();
/*      */   }
/*      */   
/*      */   protected abstract void entityInit();
/*      */   
/*      */   public DataWatcher getDataWatcher() {
/*  336 */     return this.dataWatcher;
/*      */   }
/*      */   
/*      */   public boolean equals(Object p_equals_1_) {
/*  340 */     return (p_equals_1_ instanceof Entity) ? ((((Entity)p_equals_1_).entityId == this.entityId)) : false;
/*      */   }
/*      */   
/*      */   public int hashCode() {
/*  344 */     return this.entityId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preparePlayerToSpawn() {
/*  352 */     if (this.worldObj != null) {
/*  353 */       while (this.posY > 0.0D && this.posY < 256.0D) {
/*  354 */         setPosition(this.posX, this.posY, this.posZ);
/*      */         
/*  356 */         if (this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()) {
/*      */           break;
/*      */         }
/*      */         
/*  360 */         this.posY++;
/*      */       } 
/*      */       
/*  363 */       this.motionX = this.motionY = this.motionZ = 0.0D;
/*  364 */       this.rotationPitch = 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDead() {
/*  372 */     this.isDead = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setSize(float width, float height) {
/*  379 */     if (width != this.width || height != this.height) {
/*  380 */       float f = this.width;
/*  381 */       this.width = width;
/*  382 */       this.height = height;
/*  383 */       setEntityBoundingBox(new AxisAlignedBB((getEntityBoundingBox()).minX, (getEntityBoundingBox()).minY, (getEntityBoundingBox()).minZ, (getEntityBoundingBox()).minX + this.width, (getEntityBoundingBox()).minY + this.height, (getEntityBoundingBox()).minZ + this.width));
/*      */       
/*  385 */       if (this.width > f && !this.firstUpdate && !this.worldObj.isRemote) {
/*  386 */         moveEntity((f - this.width), 0.0D, (f - this.width));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setRotation(float yaw, float pitch) {
/*  395 */     this.rotationYaw = yaw % 360.0F;
/*  396 */     this.rotationPitch = pitch % 360.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPosition(double x, double y, double z) {
/*  403 */     this.posX = x;
/*  404 */     this.posY = y;
/*  405 */     this.posZ = z;
/*  406 */     float f = this.width / 2.0F;
/*  407 */     float f1 = this.height;
/*  408 */     setEntityBoundingBox(new AxisAlignedBB(x - f, y, z - f, x + f, y + f1, z + f));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngles(float yaw, float pitch) {
/*  416 */     float f = this.rotationPitch;
/*  417 */     float f1 = this.rotationYaw;
/*  418 */     this.rotationYaw = (float)(this.rotationYaw + yaw * 0.15D);
/*  419 */     this.rotationPitch = (float)(this.rotationPitch - pitch * 0.15D);
/*  420 */     this.rotationPitch = MathHelper.clamp_float(this.rotationPitch, -90.0F, 90.0F);
/*  421 */     this.prevRotationPitch += this.rotationPitch - f;
/*  422 */     this.prevRotationYaw += this.rotationYaw - f1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  429 */     onEntityUpdate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  436 */     this.worldObj.theProfiler.startSection("entityBaseTick");
/*      */     
/*  438 */     if (this.ridingEntity != null && this.ridingEntity.isDead) {
/*  439 */       this.ridingEntity = null;
/*      */     }
/*      */     
/*  442 */     this.prevDistanceWalkedModified = this.distanceWalkedModified;
/*  443 */     this.prevPosX = this.posX;
/*  444 */     this.prevPosY = this.posY;
/*  445 */     this.prevPosZ = this.posZ;
/*  446 */     this.prevRotationPitch = this.rotationPitch;
/*  447 */     this.prevRotationYaw = this.rotationYaw;
/*      */     
/*  449 */     if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
/*  450 */       this.worldObj.theProfiler.startSection("portal");
/*  451 */       MinecraftServer minecraftserver = ((WorldServer)this.worldObj).getMinecraftServer();
/*  452 */       int i = getMaxInPortalTime();
/*      */       
/*  454 */       if (this.inPortal) {
/*  455 */         if (minecraftserver.getAllowNether()) {
/*  456 */           if (this.ridingEntity == null && this.portalCounter++ >= i) {
/*  457 */             int j; this.portalCounter = i;
/*  458 */             this.timeUntilPortal = getPortalCooldown();
/*      */ 
/*      */             
/*  461 */             if (this.worldObj.provider.getDimensionId() == -1) {
/*  462 */               j = 0;
/*      */             } else {
/*  464 */               j = -1;
/*      */             } 
/*      */             
/*  467 */             travelToDimension(j);
/*      */           } 
/*      */           
/*  470 */           this.inPortal = false;
/*      */         } 
/*      */       } else {
/*  473 */         if (this.portalCounter > 0) {
/*  474 */           this.portalCounter -= 4;
/*      */         }
/*      */         
/*  477 */         if (this.portalCounter < 0) {
/*  478 */           this.portalCounter = 0;
/*      */         }
/*      */       } 
/*      */       
/*  482 */       if (this.timeUntilPortal > 0) {
/*  483 */         this.timeUntilPortal--;
/*      */       }
/*      */       
/*  486 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */     
/*  489 */     spawnRunningParticles();
/*  490 */     handleWaterMovement();
/*      */     
/*  492 */     if (this.worldObj.isRemote) {
/*  493 */       this.fire = 0;
/*  494 */     } else if (this.fire > 0) {
/*  495 */       if (this.isImmuneToFire) {
/*  496 */         this.fire -= 4;
/*      */         
/*  498 */         if (this.fire < 0) {
/*  499 */           this.fire = 0;
/*      */         }
/*      */       } else {
/*  502 */         if (this.fire % 20 == 0) {
/*  503 */           attackEntityFrom(DamageSource.onFire, 1.0F);
/*      */         }
/*      */         
/*  506 */         this.fire--;
/*      */       } 
/*      */     } 
/*      */     
/*  510 */     if (isInLava()) {
/*  511 */       setOnFireFromLava();
/*  512 */       this.fallDistance *= 0.5F;
/*      */     } 
/*      */     
/*  515 */     if (this.posY < -64.0D) {
/*  516 */       kill();
/*      */     }
/*      */     
/*  519 */     if (!this.worldObj.isRemote) {
/*  520 */       setFlag(0, (this.fire > 0));
/*      */     }
/*      */     
/*  523 */     this.firstUpdate = false;
/*  524 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxInPortalTime() {
/*  531 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setOnFireFromLava() {
/*  538 */     if (!this.isImmuneToFire) {
/*  539 */       attackEntityFrom(DamageSource.lava, 4.0F);
/*  540 */       setFire(15);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFire(int seconds) {
/*  548 */     int i = seconds * 20;
/*  549 */     i = EnchantmentProtection.getFireTimeForEntity(this, i);
/*      */     
/*  551 */     if (this.fire < i) {
/*  552 */       this.fire = i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void extinguish() {
/*  560 */     this.fire = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void kill() {
/*  567 */     setDead();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOffsetPositionInLiquid(double x, double y, double z) {
/*  574 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox().offset(x, y, z);
/*  575 */     return isLiquidPresentInAABB(axisalignedbb);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLiquidPresentInAABB(AxisAlignedBB bb) {
/*  582 */     return (this.worldObj.getCollidingBoundingBoxes(this, bb).isEmpty() && !this.worldObj.isAnyLiquid(bb));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntity(double x, double y, double z) {
/*  589 */     if (this.noClip) {
/*  590 */       setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
/*  591 */       resetPositionToBB();
/*      */     } else {
/*  593 */       this.worldObj.theProfiler.startSection("move");
/*  594 */       double d0 = this.posX;
/*  595 */       double d1 = this.posY;
/*  596 */       double d2 = this.posZ;
/*      */       
/*  598 */       if (this.isInWeb) {
/*  599 */         this.isInWeb = false;
/*  600 */         x *= 0.25D;
/*  601 */         y *= 0.05000000074505806D;
/*  602 */         z *= 0.25D;
/*  603 */         this.motionX = 0.0D;
/*  604 */         this.motionY = 0.0D;
/*  605 */         this.motionZ = 0.0D;
/*      */       } 
/*      */       
/*  608 */       double d3 = x;
/*  609 */       double d4 = y;
/*  610 */       double d5 = z;
/*  611 */       boolean flag = (this.onGround && isSneaking() && this instanceof EntityPlayer);
/*      */       
/*  613 */       if (flag) {
/*      */         double d6;
/*      */         
/*  616 */         for (d6 = 0.05D; x != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(x, -1.0D, 0.0D)).isEmpty(); d3 = x) {
/*  617 */           if (x < d6 && x >= -d6) {
/*  618 */             x = 0.0D;
/*  619 */           } else if (x > 0.0D) {
/*  620 */             x -= d6;
/*      */           } else {
/*  622 */             x += d6;
/*      */           } 
/*      */         } 
/*      */         
/*  626 */         for (; z != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(0.0D, -1.0D, z)).isEmpty(); d5 = z) {
/*  627 */           if (z < d6 && z >= -d6) {
/*  628 */             z = 0.0D;
/*  629 */           } else if (z > 0.0D) {
/*  630 */             z -= d6;
/*      */           } else {
/*  632 */             z += d6;
/*      */           } 
/*      */         } 
/*      */         
/*  636 */         for (; x != 0.0D && z != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().offset(x, -1.0D, z)).isEmpty(); d5 = z) {
/*  637 */           if (x < d6 && x >= -d6) {
/*  638 */             x = 0.0D;
/*  639 */           } else if (x > 0.0D) {
/*  640 */             x -= d6;
/*      */           } else {
/*  642 */             x += d6;
/*      */           } 
/*      */           
/*  645 */           d3 = x;
/*      */           
/*  647 */           if (z < d6 && z >= -d6) {
/*  648 */             z = 0.0D;
/*  649 */           } else if (z > 0.0D) {
/*  650 */             z -= d6;
/*      */           } else {
/*  652 */             z += d6;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  657 */       List<AxisAlignedBB> list1 = this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().addCoord(x, y, z));
/*  658 */       AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*      */       
/*  660 */       for (AxisAlignedBB axisalignedbb1 : list1) {
/*  661 */         y = axisalignedbb1.calculateYOffset(getEntityBoundingBox(), y);
/*      */       }
/*      */       
/*  664 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*  665 */       boolean flag1 = !(!this.onGround && (d4 == y || d4 >= 0.0D));
/*      */       
/*  667 */       for (AxisAlignedBB axisalignedbb2 : list1) {
/*  668 */         x = axisalignedbb2.calculateXOffset(getEntityBoundingBox(), x);
/*      */       }
/*      */       
/*  671 */       setEntityBoundingBox(getEntityBoundingBox().offset(x, 0.0D, 0.0D));
/*      */       
/*  673 */       for (AxisAlignedBB axisalignedbb13 : list1) {
/*  674 */         z = axisalignedbb13.calculateZOffset(getEntityBoundingBox(), z);
/*      */       }
/*      */       
/*  677 */       setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, z));
/*      */       
/*  679 */       if (this.stepHeight > 0.0F && flag1 && (d3 != x || d5 != z)) {
/*  680 */         double d11 = x;
/*  681 */         double d7 = y;
/*  682 */         double d8 = z;
/*  683 */         AxisAlignedBB axisalignedbb3 = getEntityBoundingBox();
/*  684 */         setEntityBoundingBox(axisalignedbb);
/*  685 */         y = this.stepHeight;
/*  686 */         List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().addCoord(d3, y, d5));
/*  687 */         AxisAlignedBB axisalignedbb4 = getEntityBoundingBox();
/*  688 */         AxisAlignedBB axisalignedbb5 = axisalignedbb4.addCoord(d3, 0.0D, d5);
/*  689 */         double d9 = y;
/*      */         
/*  691 */         for (AxisAlignedBB axisalignedbb6 : list) {
/*  692 */           d9 = axisalignedbb6.calculateYOffset(axisalignedbb5, d9);
/*      */         }
/*      */         
/*  695 */         axisalignedbb4 = axisalignedbb4.offset(0.0D, d9, 0.0D);
/*  696 */         double d15 = d3;
/*      */         
/*  698 */         for (AxisAlignedBB axisalignedbb7 : list) {
/*  699 */           d15 = axisalignedbb7.calculateXOffset(axisalignedbb4, d15);
/*      */         }
/*      */         
/*  702 */         axisalignedbb4 = axisalignedbb4.offset(d15, 0.0D, 0.0D);
/*  703 */         double d16 = d5;
/*      */         
/*  705 */         for (AxisAlignedBB axisalignedbb8 : list) {
/*  706 */           d16 = axisalignedbb8.calculateZOffset(axisalignedbb4, d16);
/*      */         }
/*      */         
/*  709 */         axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d16);
/*  710 */         AxisAlignedBB axisalignedbb14 = getEntityBoundingBox();
/*  711 */         double d17 = y;
/*      */         
/*  713 */         for (AxisAlignedBB axisalignedbb9 : list) {
/*  714 */           d17 = axisalignedbb9.calculateYOffset(axisalignedbb14, d17);
/*      */         }
/*      */         
/*  717 */         axisalignedbb14 = axisalignedbb14.offset(0.0D, d17, 0.0D);
/*  718 */         double d18 = d3;
/*      */         
/*  720 */         for (AxisAlignedBB axisalignedbb10 : list) {
/*  721 */           d18 = axisalignedbb10.calculateXOffset(axisalignedbb14, d18);
/*      */         }
/*      */         
/*  724 */         axisalignedbb14 = axisalignedbb14.offset(d18, 0.0D, 0.0D);
/*  725 */         double d19 = d5;
/*      */         
/*  727 */         for (AxisAlignedBB axisalignedbb11 : list) {
/*  728 */           d19 = axisalignedbb11.calculateZOffset(axisalignedbb14, d19);
/*      */         }
/*      */         
/*  731 */         axisalignedbb14 = axisalignedbb14.offset(0.0D, 0.0D, d19);
/*  732 */         double d20 = d15 * d15 + d16 * d16;
/*  733 */         double d10 = d18 * d18 + d19 * d19;
/*      */         
/*  735 */         if (d20 > d10) {
/*  736 */           x = d15;
/*  737 */           z = d16;
/*  738 */           y = -d9;
/*  739 */           setEntityBoundingBox(axisalignedbb4);
/*      */         } else {
/*  741 */           x = d18;
/*  742 */           z = d19;
/*  743 */           y = -d17;
/*  744 */           setEntityBoundingBox(axisalignedbb14);
/*      */         } 
/*      */         
/*  747 */         for (AxisAlignedBB axisalignedbb12 : list) {
/*  748 */           y = axisalignedbb12.calculateYOffset(getEntityBoundingBox(), y);
/*      */         }
/*      */         
/*  751 */         setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, y, 0.0D));
/*      */         
/*  753 */         if (d11 * d11 + d8 * d8 >= x * x + z * z) {
/*  754 */           x = d11;
/*  755 */           y = d7;
/*  756 */           z = d8;
/*  757 */           setEntityBoundingBox(axisalignedbb3);
/*      */         } 
/*      */       } 
/*      */       
/*  761 */       this.worldObj.theProfiler.endSection();
/*  762 */       this.worldObj.theProfiler.startSection("rest");
/*  763 */       resetPositionToBB();
/*  764 */       this.isCollidedHorizontally = !(d3 == x && d5 == z);
/*  765 */       this.isCollidedVertically = (d4 != y);
/*  766 */       this.onGround = (this.isCollidedVertically && d4 < 0.0D);
/*  767 */       this.isCollided = !(!this.isCollidedHorizontally && !this.isCollidedVertically);
/*  768 */       int i = MathHelper.floor_double(this.posX);
/*  769 */       int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/*  770 */       int k = MathHelper.floor_double(this.posZ);
/*  771 */       BlockPos blockpos = new BlockPos(i, j, k);
/*  772 */       Block block1 = this.worldObj.getBlockState(blockpos).getBlock();
/*      */       
/*  774 */       if (block1.getMaterial() == Material.air) {
/*  775 */         Block block = this.worldObj.getBlockState(blockpos.down()).getBlock();
/*      */         
/*  777 */         if (block instanceof net.minecraft.block.BlockFence || block instanceof net.minecraft.block.BlockWall || block instanceof net.minecraft.block.BlockFenceGate) {
/*  778 */           block1 = block;
/*  779 */           blockpos = blockpos.down();
/*      */         } 
/*      */       } 
/*      */       
/*  783 */       updateFallState(y, this.onGround, block1, blockpos);
/*      */       
/*  785 */       if (d3 != x) {
/*  786 */         this.motionX = 0.0D;
/*      */       }
/*      */       
/*  789 */       if (d5 != z) {
/*  790 */         this.motionZ = 0.0D;
/*      */       }
/*      */       
/*  793 */       if (d4 != y) {
/*  794 */         block1.onLanded(this.worldObj, this);
/*      */       }
/*      */       
/*  797 */       if (canTriggerWalking() && !flag && this.ridingEntity == null) {
/*  798 */         double d12 = this.posX - d0;
/*  799 */         double d13 = this.posY - d1;
/*  800 */         double d14 = this.posZ - d2;
/*      */         
/*  802 */         if (block1 != Blocks.ladder) {
/*  803 */           d13 = 0.0D;
/*      */         }
/*      */         
/*  806 */         if (block1 != null && this.onGround) {
/*  807 */           block1.onEntityCollidedWithBlock(this.worldObj, blockpos, this);
/*      */         }
/*      */         
/*  810 */         this.distanceWalkedModified = (float)(this.distanceWalkedModified + MathHelper.sqrt_double(d12 * d12 + d14 * d14) * 0.6D);
/*  811 */         this.distanceWalkedOnStepModified = (float)(this.distanceWalkedOnStepModified + MathHelper.sqrt_double(d12 * d12 + d13 * d13 + d14 * d14) * 0.6D);
/*      */         
/*  813 */         if (this.distanceWalkedOnStepModified > this.nextStepDistance && block1.getMaterial() != Material.air) {
/*  814 */           this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
/*      */           
/*  816 */           if (isInWater()) {
/*  817 */             float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224D) * 0.35F;
/*      */             
/*  819 */             if (f > 1.0F) {
/*  820 */               f = 1.0F;
/*      */             }
/*      */             
/*  823 */             playSound(getSwimSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/*      */           } 
/*      */           
/*  826 */           playStepSound(blockpos, block1);
/*      */         } 
/*      */       } 
/*      */       
/*      */       try {
/*  831 */         doBlockCollisions();
/*  832 */       } catch (Throwable throwable) {
/*  833 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
/*  834 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
/*  835 */         addEntityCrashInfo(crashreportcategory);
/*  836 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*  839 */       boolean flag2 = isWet();
/*      */       
/*  841 */       if (this.worldObj.isFlammableWithin(getEntityBoundingBox().contract(0.001D, 0.001D, 0.001D))) {
/*  842 */         dealFireDamage(1);
/*      */         
/*  844 */         if (!flag2) {
/*  845 */           this.fire++;
/*      */           
/*  847 */           if (this.fire == 0) {
/*  848 */             setFire(8);
/*      */           }
/*      */         } 
/*  851 */       } else if (this.fire <= 0) {
/*  852 */         this.fire = -this.fireResistance;
/*      */       } 
/*      */       
/*  855 */       if (flag2 && this.fire > 0) {
/*  856 */         playSound("random.fizz", 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/*  857 */         this.fire = -this.fireResistance;
/*      */       } 
/*      */       
/*  860 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetPositionToBB() {
/*  868 */     this.posX = ((getEntityBoundingBox()).minX + (getEntityBoundingBox()).maxX) / 2.0D;
/*  869 */     this.posY = (getEntityBoundingBox()).minY;
/*  870 */     this.posZ = ((getEntityBoundingBox()).minZ + (getEntityBoundingBox()).maxZ) / 2.0D;
/*      */   }
/*      */   
/*      */   protected String getSwimSound() {
/*  874 */     return "game.neutral.swim";
/*      */   }
/*      */   
/*      */   protected void doBlockCollisions() {
/*  878 */     BlockPos blockpos = new BlockPos((getEntityBoundingBox()).minX + 0.001D, (getEntityBoundingBox()).minY + 0.001D, (getEntityBoundingBox()).minZ + 0.001D);
/*  879 */     BlockPos blockpos1 = new BlockPos((getEntityBoundingBox()).maxX - 0.001D, (getEntityBoundingBox()).maxY - 0.001D, (getEntityBoundingBox()).maxZ - 0.001D);
/*      */     
/*  881 */     if (this.worldObj.isAreaLoaded(blockpos, blockpos1)) {
/*  882 */       for (int i = blockpos.getX(); i <= blockpos1.getX(); i++) {
/*  883 */         for (int j = blockpos.getY(); j <= blockpos1.getY(); j++) {
/*  884 */           for (int k = blockpos.getZ(); k <= blockpos1.getZ(); k++) {
/*  885 */             BlockPos blockpos2 = new BlockPos(i, j, k);
/*  886 */             IBlockState iblockstate = this.worldObj.getBlockState(blockpos2);
/*      */             
/*      */             try {
/*  889 */               iblockstate.getBlock().onEntityCollidedWithBlock(this.worldObj, blockpos2, iblockstate, this);
/*  890 */             } catch (Throwable throwable) {
/*  891 */               CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Colliding entity with block");
/*  892 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being collided with");
/*  893 */               CrashReportCategory.addBlockInfo(crashreportcategory, blockpos2, iblockstate);
/*  894 */               throw new ReportedException(crashreport);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  903 */     Block.SoundType block$soundtype = blockIn.stepSound;
/*      */     
/*  905 */     if (this.worldObj.getBlockState(pos.up()).getBlock() == Blocks.snow_layer) {
/*  906 */       block$soundtype = Blocks.snow_layer.stepSound;
/*  907 */       playSound(block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*  908 */     } else if (!blockIn.getMaterial().isLiquid()) {
/*  909 */       playSound(block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void playSound(String name, float volume, float pitch) {
/*  914 */     if (!isSilent()) {
/*  915 */       this.worldObj.playSoundAtEntity(this, name, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSilent() {
/*  923 */     return (this.dataWatcher.getWatchableObjectByte(4) == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSilent(boolean isSilent) {
/*  930 */     this.dataWatcher.updateObject(4, Byte.valueOf((byte)(isSilent ? 1 : 0)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canTriggerWalking() {
/*  938 */     return true;
/*      */   }
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
/*  942 */     if (onGroundIn) {
/*  943 */       if (this.fallDistance > 0.0F) {
/*  944 */         if (blockIn != null) {
/*  945 */           blockIn.onFallenUpon(this.worldObj, pos, this, this.fallDistance);
/*      */         } else {
/*  947 */           fall(this.fallDistance, 1.0F);
/*      */         } 
/*      */         
/*  950 */         this.fallDistance = 0.0F;
/*      */       } 
/*  952 */     } else if (y < 0.0D) {
/*  953 */       this.fallDistance = (float)(this.fallDistance - y);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getCollisionBoundingBox() {
/*  961 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dealFireDamage(int amount) {
/*  969 */     if (!this.isImmuneToFire) {
/*  970 */       attackEntityFrom(DamageSource.inFire, amount);
/*      */     }
/*      */   }
/*      */   
/*      */   public final boolean isImmuneToFire() {
/*  975 */     return this.isImmuneToFire;
/*      */   }
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/*  979 */     if (this.riddenByEntity != null) {
/*  980 */       this.riddenByEntity.fall(distance, damageMultiplier);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWet() {
/*  988 */     return !(!this.inWater && !this.worldObj.isRainingAt(new BlockPos(this.posX, this.posY, this.posZ)) && !this.worldObj.isRainingAt(new BlockPos(this.posX, this.posY + this.height, this.posZ)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInWater() {
/*  996 */     return this.inWater;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleWaterMovement() {
/* 1003 */     if (this.worldObj.handleMaterialAcceleration(getEntityBoundingBox().expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.water, this)) {
/* 1004 */       if (!this.inWater && !this.firstUpdate) {
/* 1005 */         resetHeight();
/*      */       }
/*      */       
/* 1008 */       this.fallDistance = 0.0F;
/* 1009 */       this.inWater = true;
/* 1010 */       this.fire = 0;
/*      */     } else {
/* 1012 */       this.inWater = false;
/*      */     } 
/*      */     
/* 1015 */     return this.inWater;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetHeight() {
/* 1022 */     float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224D) * 0.2F;
/*      */     
/* 1024 */     if (f > 1.0F) {
/* 1025 */       f = 1.0F;
/*      */     }
/*      */     
/* 1028 */     playSound(getSplashSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/* 1029 */     float f1 = MathHelper.floor_double((getEntityBoundingBox()).minY);
/*      */     
/* 1031 */     for (int i = 0; i < 1.0F + this.width * 20.0F; i++) {
/* 1032 */       float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1033 */       float f3 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1034 */       this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + f2, (f1 + 1.0F), this.posZ + f3, this.motionX, this.motionY - (this.rand.nextFloat() * 0.2F), this.motionZ, new int[0]);
/*      */     } 
/*      */     
/* 1037 */     for (int j = 0; j < 1.0F + this.width * 20.0F; j++) {
/* 1038 */       float f4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1039 */       float f5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1040 */       this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + f4, (f1 + 1.0F), this.posZ + f5, this.motionX, this.motionY, this.motionZ, new int[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnRunningParticles() {
/* 1048 */     if (isSprinting() && !isInWater()) {
/* 1049 */       createRunningParticles();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void createRunningParticles() {
/* 1054 */     int i = MathHelper.floor_double(this.posX);
/* 1055 */     int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/* 1056 */     int k = MathHelper.floor_double(this.posZ);
/* 1057 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 1058 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 1059 */     Block block = iblockstate.getBlock();
/*      */     
/* 1061 */     if (block.getRenderType() != -1) {
/* 1062 */       this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, (getEntityBoundingBox()).minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, -this.motionX * 4.0D, 1.5D, -this.motionZ * 4.0D, new int[] { Block.getStateId(iblockstate) });
/*      */     }
/*      */   }
/*      */   
/*      */   protected String getSplashSound() {
/* 1067 */     return "game.neutral.swim.splash";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInsideOfMaterial(Material materialIn) {
/* 1074 */     double d0 = this.posY + getEyeHeight();
/* 1075 */     BlockPos blockpos = new BlockPos(this.posX, d0, this.posZ);
/* 1076 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 1077 */     Block block = iblockstate.getBlock();
/*      */     
/* 1079 */     if (block.getMaterial() == materialIn) {
/* 1080 */       float f = BlockLiquid.getLiquidHeightPercent(iblockstate.getBlock().getMetaFromState(iblockstate)) - 0.11111111F;
/* 1081 */       float f1 = (blockpos.getY() + 1) - f;
/* 1082 */       boolean flag = (d0 < f1);
/* 1083 */       return (!flag && this instanceof EntityPlayer) ? false : flag;
/*      */     } 
/* 1085 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInLava() {
/* 1090 */     return this.worldObj.isMaterialInBB(getEntityBoundingBox().expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.lava);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveFlying(float strafe, float forward, float friction) {
/* 1097 */     float f = strafe * strafe + forward * forward;
/*      */     
/* 1099 */     if (f >= 1.0E-4F) {
/* 1100 */       f = MathHelper.sqrt_float(f);
/*      */       
/* 1102 */       if (f < 1.0F) {
/* 1103 */         f = 1.0F;
/*      */       }
/*      */       
/* 1106 */       f = friction / f;
/* 1107 */       strafe *= f;
/* 1108 */       forward *= f;
/* 1109 */       float f1 = MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F);
/* 1110 */       float f2 = MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F);
/* 1111 */       this.motionX += (strafe * f2 - forward * f1);
/* 1112 */       this.motionZ += (forward * f2 + strafe * f1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getBrightnessForRender(float partialTicks) {
/* 1117 */     BlockPos blockpos = new BlockPos(this.posX, this.posY + getEyeHeight(), this.posZ);
/* 1118 */     return this.worldObj.isBlockLoaded(blockpos) ? this.worldObj.getCombinedLight(blockpos, 0) : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBrightness(float partialTicks) {
/* 1125 */     BlockPos blockpos = new BlockPos(this.posX, this.posY + getEyeHeight(), this.posZ);
/* 1126 */     return this.worldObj.isBlockLoaded(blockpos) ? this.worldObj.getLightBrightness(blockpos) : 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorld(World worldIn) {
/* 1133 */     this.worldObj = worldIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
/* 1140 */     this.prevPosX = this.posX = x;
/* 1141 */     this.prevPosY = this.posY = y;
/* 1142 */     this.prevPosZ = this.posZ = z;
/* 1143 */     this.prevRotationYaw = this.rotationYaw = yaw;
/* 1144 */     this.prevRotationPitch = this.rotationPitch = pitch;
/* 1145 */     double d0 = (this.prevRotationYaw - yaw);
/*      */     
/* 1147 */     if (d0 < -180.0D) {
/* 1148 */       this.prevRotationYaw += 360.0F;
/*      */     }
/*      */     
/* 1151 */     if (d0 >= 180.0D) {
/* 1152 */       this.prevRotationYaw -= 360.0F;
/*      */     }
/*      */     
/* 1155 */     setPosition(this.posX, this.posY, this.posZ);
/* 1156 */     setRotation(yaw, pitch);
/*      */   }
/*      */   
/*      */   public void moveToBlockPosAndAngles(BlockPos pos, float rotationYawIn, float rotationPitchIn) {
/* 1160 */     setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, rotationYawIn, rotationPitchIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
/* 1167 */     this.lastTickPosX = this.prevPosX = this.posX = x;
/* 1168 */     this.lastTickPosY = this.prevPosY = this.posY = y;
/* 1169 */     this.lastTickPosZ = this.prevPosZ = this.posZ = z;
/* 1170 */     this.rotationYaw = yaw;
/* 1171 */     this.rotationPitch = pitch;
/* 1172 */     setPosition(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDistanceToEntity(Entity entityIn) {
/* 1179 */     float f = (float)(this.posX - entityIn.posX);
/* 1180 */     float f1 = (float)(this.posY - entityIn.posY);
/* 1181 */     float f2 = (float)(this.posZ - entityIn.posZ);
/* 1182 */     return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistanceSq(double x, double y, double z) {
/* 1189 */     double d0 = this.posX - x;
/* 1190 */     double d1 = this.posY - y;
/* 1191 */     double d2 = this.posZ - z;
/* 1192 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */   
/*      */   public double getDistanceSq(BlockPos pos) {
/* 1196 */     return pos.distanceSq(this.posX, this.posY, this.posZ);
/*      */   }
/*      */   
/*      */   public double getDistanceSqToCenter(BlockPos pos) {
/* 1200 */     return pos.distanceSqToCenter(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistance(double x, double y, double z) {
/* 1207 */     double d0 = this.posX - x;
/* 1208 */     double d1 = this.posY - y;
/* 1209 */     double d2 = this.posZ - z;
/* 1210 */     return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistanceSqToEntity(Entity entityIn) {
/* 1217 */     double d0 = this.posX - entityIn.posX;
/* 1218 */     double d1 = this.posY - entityIn.posY;
/* 1219 */     double d2 = this.posZ - entityIn.posZ;
/* 1220 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCollideWithPlayer(EntityPlayer entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyEntityCollision(Entity entityIn) {
/* 1233 */     if (entityIn.riddenByEntity != this && entityIn.ridingEntity != this && 
/* 1234 */       !entityIn.noClip && !this.noClip) {
/* 1235 */       double d0 = entityIn.posX - this.posX;
/* 1236 */       double d1 = entityIn.posZ - this.posZ;
/* 1237 */       double d2 = MathHelper.abs_max(d0, d1);
/*      */       
/* 1239 */       if (d2 >= 0.009999999776482582D) {
/* 1240 */         d2 = MathHelper.sqrt_double(d2);
/* 1241 */         d0 /= d2;
/* 1242 */         d1 /= d2;
/* 1243 */         double d3 = 1.0D / d2;
/*      */         
/* 1245 */         if (d3 > 1.0D) {
/* 1246 */           d3 = 1.0D;
/*      */         }
/*      */         
/* 1249 */         d0 *= d3;
/* 1250 */         d1 *= d3;
/* 1251 */         d0 *= 0.05000000074505806D;
/* 1252 */         d1 *= 0.05000000074505806D;
/* 1253 */         d0 *= (1.0F - this.entityCollisionReduction);
/* 1254 */         d1 *= (1.0F - this.entityCollisionReduction);
/*      */         
/* 1256 */         if (this.riddenByEntity == null) {
/* 1257 */           addVelocity(-d0, 0.0D, -d1);
/*      */         }
/*      */         
/* 1260 */         if (entityIn.riddenByEntity == null) {
/* 1261 */           entityIn.addVelocity(d0, 0.0D, d1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addVelocity(double x, double y, double z) {
/* 1272 */     this.motionX += x;
/* 1273 */     this.motionY += y;
/* 1274 */     this.motionZ += z;
/* 1275 */     this.isAirBorne = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setBeenAttacked() {
/* 1282 */     this.velocityChanged = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 1289 */     if (isEntityInvulnerable(source)) {
/* 1290 */       return false;
/*      */     }
/* 1292 */     setBeenAttacked();
/* 1293 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLook(float partialTicks) {
/* 1301 */     if (partialTicks == 1.0F) {
/* 1302 */       return getVectorForRotation(this.rotationPitch, this.rotationYaw);
/*      */     }
/* 1304 */     float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * partialTicks;
/* 1305 */     float f1 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * partialTicks;
/* 1306 */     return getVectorForRotation(f, f1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Vec3 getVectorForRotation(float pitch, float yaw) {
/* 1314 */     float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
/* 1315 */     float f1 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
/* 1316 */     float f2 = -MathHelper.cos(-pitch * 0.017453292F);
/* 1317 */     float f3 = MathHelper.sin(-pitch * 0.017453292F);
/* 1318 */     return new Vec3((f1 * f2), f3, (f * f2));
/*      */   }
/*      */   
/*      */   public Vec3 getPositionEyes(float partialTicks) {
/* 1322 */     if (partialTicks == 1.0F) {
/* 1323 */       return new Vec3(this.posX, this.posY + getEyeHeight(), this.posZ);
/*      */     }
/* 1325 */     double d0 = this.prevPosX + (this.posX - this.prevPosX) * partialTicks;
/* 1326 */     double d1 = this.prevPosY + (this.posY - this.prevPosY) * partialTicks + getEyeHeight();
/* 1327 */     double d2 = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks;
/* 1328 */     return new Vec3(d0, d1, d2);
/*      */   }
/*      */ 
/*      */   
/*      */   public MovingObjectPosition rayTrace(double blockReachDistance, float partialTicks) {
/* 1333 */     Vec3 vec3 = getPositionEyes(partialTicks);
/* 1334 */     Vec3 vec31 = getLook(partialTicks);
/* 1335 */     Vec3 vec32 = vec3.addVector(vec31.xCoord * blockReachDistance, vec31.yCoord * blockReachDistance, vec31.zCoord * blockReachDistance);
/* 1336 */     return this.worldObj.rayTraceBlocks(vec3, vec32, false, false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/* 1343 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/* 1350 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addToPlayerScore(Entity entityIn, int amount) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInRangeToRender3d(double x, double y, double z) {
/* 1361 */     double d0 = this.posX - x;
/* 1362 */     double d1 = this.posY - y;
/* 1363 */     double d2 = this.posZ - z;
/* 1364 */     double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 1365 */     return isInRangeToRenderDist(d3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInRangeToRenderDist(double distance) {
/* 1373 */     double d0 = getEntityBoundingBox().getAverageEdgeLength();
/*      */     
/* 1375 */     if (Double.isNaN(d0)) {
/* 1376 */       d0 = 1.0D;
/*      */     }
/*      */     
/* 1379 */     d0 = d0 * 64.0D * this.renderDistanceWeight;
/* 1380 */     return (distance < d0 * d0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeMountToNBT(NBTTagCompound tagCompund) {
/* 1388 */     String s = getEntityString();
/*      */     
/* 1390 */     if (!this.isDead && s != null) {
/* 1391 */       tagCompund.setString("id", s);
/* 1392 */       writeToNBT(tagCompund);
/* 1393 */       return true;
/*      */     } 
/* 1395 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeToNBTOptional(NBTTagCompound tagCompund) {
/* 1405 */     String s = getEntityString();
/*      */     
/* 1407 */     if (!this.isDead && s != null && this.riddenByEntity == null) {
/* 1408 */       tagCompund.setString("id", s);
/* 1409 */       writeToNBT(tagCompund);
/* 1410 */       return true;
/*      */     } 
/* 1412 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeToNBT(NBTTagCompound tagCompund) {
/*      */     try {
/* 1421 */       tagCompund.setTag("Pos", (NBTBase)newDoubleNBTList(new double[] { this.posX, this.posY, this.posZ }));
/* 1422 */       tagCompund.setTag("Motion", (NBTBase)newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
/* 1423 */       tagCompund.setTag("Rotation", (NBTBase)newFloatNBTList(new float[] { this.rotationYaw, this.rotationPitch }));
/* 1424 */       tagCompund.setFloat("FallDistance", this.fallDistance);
/* 1425 */       tagCompund.setShort("Fire", (short)this.fire);
/* 1426 */       tagCompund.setShort("Air", (short)getAir());
/* 1427 */       tagCompund.setBoolean("OnGround", this.onGround);
/* 1428 */       tagCompund.setInteger("Dimension", this.dimension);
/* 1429 */       tagCompund.setBoolean("Invulnerable", this.invulnerable);
/* 1430 */       tagCompund.setInteger("PortalCooldown", this.timeUntilPortal);
/* 1431 */       tagCompund.setLong("UUIDMost", getUniqueID().getMostSignificantBits());
/* 1432 */       tagCompund.setLong("UUIDLeast", getUniqueID().getLeastSignificantBits());
/*      */       
/* 1434 */       if (getCustomNameTag() != null && getCustomNameTag().length() > 0) {
/* 1435 */         tagCompund.setString("CustomName", getCustomNameTag());
/* 1436 */         tagCompund.setBoolean("CustomNameVisible", getAlwaysRenderNameTag());
/*      */       } 
/*      */       
/* 1439 */       this.cmdResultStats.writeStatsToNBT(tagCompund);
/*      */       
/* 1441 */       if (isSilent()) {
/* 1442 */         tagCompund.setBoolean("Silent", isSilent());
/*      */       }
/*      */       
/* 1445 */       writeEntityToNBT(tagCompund);
/*      */       
/* 1447 */       if (this.ridingEntity != null) {
/* 1448 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */         
/* 1450 */         if (this.ridingEntity.writeMountToNBT(nbttagcompound)) {
/* 1451 */           tagCompund.setTag("Riding", (NBTBase)nbttagcompound);
/*      */         }
/*      */       } 
/* 1454 */     } catch (Throwable throwable) {
/* 1455 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Saving entity NBT");
/* 1456 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being saved");
/* 1457 */       addEntityCrashInfo(crashreportcategory);
/* 1458 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readFromNBT(NBTTagCompound tagCompund) {
/*      */     try {
/* 1467 */       NBTTagList nbttaglist = tagCompund.getTagList("Pos", 6);
/* 1468 */       NBTTagList nbttaglist1 = tagCompund.getTagList("Motion", 6);
/* 1469 */       NBTTagList nbttaglist2 = tagCompund.getTagList("Rotation", 5);
/* 1470 */       this.motionX = nbttaglist1.getDoubleAt(0);
/* 1471 */       this.motionY = nbttaglist1.getDoubleAt(1);
/* 1472 */       this.motionZ = nbttaglist1.getDoubleAt(2);
/*      */       
/* 1474 */       if (Math.abs(this.motionX) > 10.0D) {
/* 1475 */         this.motionX = 0.0D;
/*      */       }
/*      */       
/* 1478 */       if (Math.abs(this.motionY) > 10.0D) {
/* 1479 */         this.motionY = 0.0D;
/*      */       }
/*      */       
/* 1482 */       if (Math.abs(this.motionZ) > 10.0D) {
/* 1483 */         this.motionZ = 0.0D;
/*      */       }
/*      */       
/* 1486 */       this.prevPosX = this.lastTickPosX = this.posX = nbttaglist.getDoubleAt(0);
/* 1487 */       this.prevPosY = this.lastTickPosY = this.posY = nbttaglist.getDoubleAt(1);
/* 1488 */       this.prevPosZ = this.lastTickPosZ = this.posZ = nbttaglist.getDoubleAt(2);
/* 1489 */       this.prevRotationYaw = this.rotationYaw = nbttaglist2.getFloatAt(0);
/* 1490 */       this.prevRotationPitch = this.rotationPitch = nbttaglist2.getFloatAt(1);
/* 1491 */       setRotationYawHead(this.rotationYaw);
/* 1492 */       setRenderYawOffset(this.rotationYaw);
/* 1493 */       this.fallDistance = tagCompund.getFloat("FallDistance");
/* 1494 */       this.fire = tagCompund.getShort("Fire");
/* 1495 */       setAir(tagCompund.getShort("Air"));
/* 1496 */       this.onGround = tagCompund.getBoolean("OnGround");
/* 1497 */       this.dimension = tagCompund.getInteger("Dimension");
/* 1498 */       this.invulnerable = tagCompund.getBoolean("Invulnerable");
/* 1499 */       this.timeUntilPortal = tagCompund.getInteger("PortalCooldown");
/*      */       
/* 1501 */       if (tagCompund.hasKey("UUIDMost", 4) && tagCompund.hasKey("UUIDLeast", 4)) {
/* 1502 */         this.entityUniqueID = new UUID(tagCompund.getLong("UUIDMost"), tagCompund.getLong("UUIDLeast"));
/* 1503 */       } else if (tagCompund.hasKey("UUID", 8)) {
/* 1504 */         this.entityUniqueID = UUID.fromString(tagCompund.getString("UUID"));
/*      */       } 
/*      */       
/* 1507 */       setPosition(this.posX, this.posY, this.posZ);
/* 1508 */       setRotation(this.rotationYaw, this.rotationPitch);
/*      */       
/* 1510 */       if (tagCompund.hasKey("CustomName", 8) && tagCompund.getString("CustomName").length() > 0) {
/* 1511 */         setCustomNameTag(tagCompund.getString("CustomName"));
/*      */       }
/*      */       
/* 1514 */       setAlwaysRenderNameTag(tagCompund.getBoolean("CustomNameVisible"));
/* 1515 */       this.cmdResultStats.readStatsFromNBT(tagCompund);
/* 1516 */       setSilent(tagCompund.getBoolean("Silent"));
/* 1517 */       readEntityFromNBT(tagCompund);
/*      */       
/* 1519 */       if (shouldSetPosAfterLoading()) {
/* 1520 */         setPosition(this.posX, this.posY, this.posZ);
/*      */       }
/* 1522 */     } catch (Throwable throwable) {
/* 1523 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Loading entity NBT");
/* 1524 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being loaded");
/* 1525 */       addEntityCrashInfo(crashreportcategory);
/* 1526 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean shouldSetPosAfterLoading() {
/* 1531 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String getEntityString() {
/* 1538 */     return EntityList.getEntityString(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void readEntityFromNBT(NBTTagCompound paramNBTTagCompound);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void writeEntityToNBT(NBTTagCompound paramNBTTagCompound);
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkLoad() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected NBTTagList newDoubleNBTList(double... numbers) {
/* 1558 */     NBTTagList nbttaglist = new NBTTagList(); byte b; int i;
/*      */     double[] arrayOfDouble;
/* 1560 */     for (i = (arrayOfDouble = numbers).length, b = 0; b < i; ) { double d0 = arrayOfDouble[b];
/* 1561 */       nbttaglist.appendTag((NBTBase)new NBTTagDouble(d0));
/*      */       b++; }
/*      */     
/* 1564 */     return nbttaglist;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NBTTagList newFloatNBTList(float... numbers) {
/* 1571 */     NBTTagList nbttaglist = new NBTTagList(); byte b; int i;
/*      */     float[] arrayOfFloat;
/* 1573 */     for (i = (arrayOfFloat = numbers).length, b = 0; b < i; ) { float f = arrayOfFloat[b];
/* 1574 */       nbttaglist.appendTag((NBTBase)new NBTTagFloat(f));
/*      */       b++; }
/*      */     
/* 1577 */     return nbttaglist;
/*      */   }
/*      */   
/*      */   public EntityItem dropItem(Item itemIn, int size) {
/* 1581 */     return dropItemWithOffset(itemIn, size, 0.0F);
/*      */   }
/*      */   
/*      */   public EntityItem dropItemWithOffset(Item itemIn, int size, float offsetY) {
/* 1585 */     return entityDropItem(new ItemStack(itemIn, size, 0), offsetY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItem entityDropItem(ItemStack itemStackIn, float offsetY) {
/* 1592 */     if (itemStackIn.stackSize != 0 && itemStackIn.getItem() != null) {
/* 1593 */       EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY + offsetY, this.posZ, itemStackIn);
/* 1594 */       entityitem.setDefaultPickupDelay();
/* 1595 */       this.worldObj.spawnEntityInWorld((Entity)entityitem);
/* 1596 */       return entityitem;
/*      */     } 
/* 1598 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityAlive() {
/* 1606 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityInsideOpaqueBlock() {
/* 1613 */     if (this.noClip) {
/* 1614 */       return false;
/*      */     }
/* 1616 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(-2147483648, -2147483648, -2147483648);
/*      */     
/* 1618 */     for (int i = 0; i < 8; i++) {
/* 1619 */       int j = MathHelper.floor_double(this.posY + ((((i >> 0) % 2) - 0.5F) * 0.1F) + getEyeHeight());
/* 1620 */       int k = MathHelper.floor_double(this.posX + ((((i >> 1) % 2) - 0.5F) * this.width * 0.8F));
/* 1621 */       int l = MathHelper.floor_double(this.posZ + ((((i >> 2) % 2) - 0.5F) * this.width * 0.8F));
/*      */       
/* 1623 */       if (blockpos$mutableblockpos.getX() != k || blockpos$mutableblockpos.getY() != j || blockpos$mutableblockpos.getZ() != l) {
/* 1624 */         blockpos$mutableblockpos.set(k, j, l);
/*      */         
/* 1626 */         if (this.worldObj.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().isVisuallyOpaque()) {
/* 1627 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1632 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interactFirst(EntityPlayer playerIn) {
/* 1640 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getCollisionBox(Entity entityIn) {
/* 1648 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/* 1655 */     if (this.ridingEntity.isDead) {
/* 1656 */       this.ridingEntity = null;
/*      */     } else {
/* 1658 */       this.motionX = 0.0D;
/* 1659 */       this.motionY = 0.0D;
/* 1660 */       this.motionZ = 0.0D;
/* 1661 */       onUpdate();
/*      */       
/* 1663 */       if (this.ridingEntity != null) {
/* 1664 */         this.ridingEntity.updateRiderPosition();
/* 1665 */         this.entityRiderYawDelta += (this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw);
/*      */         
/* 1667 */         for (this.entityRiderPitchDelta += (this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch); this.entityRiderYawDelta >= 180.0D; this.entityRiderYawDelta -= 360.0D);
/*      */ 
/*      */ 
/*      */         
/* 1671 */         while (this.entityRiderYawDelta < -180.0D) {
/* 1672 */           this.entityRiderYawDelta += 360.0D;
/*      */         }
/*      */         
/* 1675 */         while (this.entityRiderPitchDelta >= 180.0D) {
/* 1676 */           this.entityRiderPitchDelta -= 360.0D;
/*      */         }
/*      */         
/* 1679 */         while (this.entityRiderPitchDelta < -180.0D) {
/* 1680 */           this.entityRiderPitchDelta += 360.0D;
/*      */         }
/*      */         
/* 1683 */         double d0 = this.entityRiderYawDelta * 0.5D;
/* 1684 */         double d1 = this.entityRiderPitchDelta * 0.5D;
/* 1685 */         float f = 10.0F;
/*      */         
/* 1687 */         if (d0 > f) {
/* 1688 */           d0 = f;
/*      */         }
/*      */         
/* 1691 */         if (d0 < -f) {
/* 1692 */           d0 = -f;
/*      */         }
/*      */         
/* 1695 */         if (d1 > f) {
/* 1696 */           d1 = f;
/*      */         }
/*      */         
/* 1699 */         if (d1 < -f) {
/* 1700 */           d1 = -f;
/*      */         }
/*      */         
/* 1703 */         this.entityRiderYawDelta -= d0;
/* 1704 */         this.entityRiderPitchDelta -= d1;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void updateRiderPosition() {
/* 1710 */     if (this.riddenByEntity != null) {
/* 1711 */       this.riddenByEntity.setPosition(this.posX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/* 1719 */     return 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMountedYOffset() {
/* 1726 */     return this.height * 0.75D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mountEntity(Entity entityIn) {
/* 1733 */     this.entityRiderPitchDelta = 0.0D;
/* 1734 */     this.entityRiderYawDelta = 0.0D;
/*      */     
/* 1736 */     if (entityIn == null) {
/* 1737 */       if (this.ridingEntity != null) {
/* 1738 */         setLocationAndAngles(this.ridingEntity.posX, (this.ridingEntity.getEntityBoundingBox()).minY + this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
/* 1739 */         this.ridingEntity.riddenByEntity = null;
/*      */       } 
/*      */       
/* 1742 */       this.ridingEntity = null;
/*      */     } else {
/* 1744 */       if (this.ridingEntity != null) {
/* 1745 */         this.ridingEntity.riddenByEntity = null;
/*      */       }
/*      */       
/* 1748 */       if (entityIn != null) {
/* 1749 */         for (Entity entity = entityIn.ridingEntity; entity != null; entity = entity.ridingEntity) {
/* 1750 */           if (entity == this) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 1756 */       this.ridingEntity = entityIn;
/* 1757 */       entityIn.riddenByEntity = this;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 1762 */     setPosition(x, y, z);
/* 1763 */     setRotation(yaw, pitch);
/* 1764 */     List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox().contract(0.03125D, 0.0D, 0.03125D));
/*      */     
/* 1766 */     if (!list.isEmpty()) {
/* 1767 */       double d0 = 0.0D;
/*      */       
/* 1769 */       for (AxisAlignedBB axisalignedbb : list) {
/* 1770 */         if (axisalignedbb.maxY > d0) {
/* 1771 */           d0 = axisalignedbb.maxY;
/*      */         }
/*      */       } 
/*      */       
/* 1775 */       y += d0 - (getEntityBoundingBox()).minY;
/* 1776 */       setPosition(x, y, z);
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getCollisionBorderSize() {
/* 1781 */     return 0.1F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLookVec() {
/* 1788 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPortal(BlockPos pos) {
/* 1798 */     if (this.timeUntilPortal > 0) {
/* 1799 */       this.timeUntilPortal = getPortalCooldown();
/*      */     } else {
/* 1801 */       if (!this.worldObj.isRemote && !pos.equals(this.lastPortalPos)) {
/* 1802 */         this.lastPortalPos = pos;
/* 1803 */         BlockPattern.PatternHelper blockpattern$patternhelper = Blocks.portal.func_181089_f(this.worldObj, pos);
/* 1804 */         double d0 = (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? blockpattern$patternhelper.getPos().getZ() : blockpattern$patternhelper.getPos().getX();
/* 1805 */         double d1 = (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? this.posZ : this.posX;
/* 1806 */         d1 = Math.abs(MathHelper.func_181160_c(d1 - ((blockpattern$patternhelper.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) ? true : false), d0, d0 - blockpattern$patternhelper.func_181118_d()));
/* 1807 */         double d2 = MathHelper.func_181160_c(this.posY - 1.0D, blockpattern$patternhelper.getPos().getY(), (blockpattern$patternhelper.getPos().getY() - blockpattern$patternhelper.func_181119_e()));
/* 1808 */         this.lastPortalVec = new Vec3(d1, d2, 0.0D);
/* 1809 */         this.teleportDirection = blockpattern$patternhelper.getFinger();
/*      */       } 
/*      */       
/* 1812 */       this.inPortal = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPortalCooldown() {
/* 1820 */     return 300;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVelocity(double x, double y, double z) {
/* 1827 */     this.motionX = x;
/* 1828 */     this.motionY = y;
/* 1829 */     this.motionZ = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performHurtAnimation() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack[] getInventory() {
/* 1845 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBurning() {
/* 1858 */     boolean flag = (this.worldObj != null && this.worldObj.isRemote);
/* 1859 */     return (!this.isImmuneToFire && (this.fire > 0 || (flag && getFlag(0))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRiding() {
/* 1867 */     return (this.ridingEntity != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSneaking() {
/* 1874 */     return getFlag(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSneaking(boolean sneaking) {
/* 1881 */     setFlag(1, sneaking);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSprinting() {
/* 1888 */     return getFlag(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSprinting(boolean sprinting) {
/* 1895 */     setFlag(3, sprinting);
/*      */   }
/*      */   
/*      */   public boolean isInvisible() {
/* 1899 */     return getFlag(5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInvisibleToPlayer(EntityPlayer player) {
/* 1908 */     return player.isSpectator() ? false : isInvisible();
/*      */   }
/*      */   
/*      */   public void setInvisible(boolean invisible) {
/* 1912 */     setFlag(5, invisible);
/*      */   }
/*      */   
/*      */   public boolean isEating() {
/* 1916 */     return getFlag(4);
/*      */   }
/*      */   
/*      */   public void setEating(boolean eating) {
/* 1920 */     setFlag(4, eating);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean getFlag(int flag) {
/* 1928 */     return ((this.dataWatcher.getWatchableObjectByte(0) & 1 << flag) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFlag(int flag, boolean set) {
/* 1935 */     byte b0 = this.dataWatcher.getWatchableObjectByte(0);
/*      */     
/* 1937 */     if (set) {
/* 1938 */       this.dataWatcher.updateObject(0, Byte.valueOf((byte)(b0 | 1 << flag)));
/*      */     } else {
/* 1940 */       this.dataWatcher.updateObject(0, Byte.valueOf((byte)(b0 & (1 << flag ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getAir() {
/* 1945 */     return this.dataWatcher.getWatchableObjectShort(1);
/*      */   }
/*      */   
/*      */   public void setAir(int air) {
/* 1949 */     this.dataWatcher.updateObject(1, Short.valueOf((short)air));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 1956 */     attackEntityFrom(DamageSource.lightningBolt, 5.0F);
/* 1957 */     this.fire++;
/*      */     
/* 1959 */     if (this.fire == 0) {
/* 1960 */       setFire(8);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillEntity(EntityLivingBase entityLivingIn) {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean pushOutOfBlocks(double x, double y, double z) {
/* 1971 */     BlockPos blockpos = new BlockPos(x, y, z);
/* 1972 */     double d0 = x - blockpos.getX();
/* 1973 */     double d1 = y - blockpos.getY();
/* 1974 */     double d2 = z - blockpos.getZ();
/* 1975 */     List<AxisAlignedBB> list = this.worldObj.getCollisionBoxes(getEntityBoundingBox());
/*      */     
/* 1977 */     if (list.isEmpty() && !this.worldObj.isBlockFullCube(blockpos)) {
/* 1978 */       return false;
/*      */     }
/* 1980 */     int i = 3;
/* 1981 */     double d3 = 9999.0D;
/*      */     
/* 1983 */     if (!this.worldObj.isBlockFullCube(blockpos.west()) && d0 < d3) {
/* 1984 */       d3 = d0;
/* 1985 */       i = 0;
/*      */     } 
/*      */     
/* 1988 */     if (!this.worldObj.isBlockFullCube(blockpos.east()) && 1.0D - d0 < d3) {
/* 1989 */       d3 = 1.0D - d0;
/* 1990 */       i = 1;
/*      */     } 
/*      */     
/* 1993 */     if (!this.worldObj.isBlockFullCube(blockpos.up()) && 1.0D - d1 < d3) {
/* 1994 */       d3 = 1.0D - d1;
/* 1995 */       i = 3;
/*      */     } 
/*      */     
/* 1998 */     if (!this.worldObj.isBlockFullCube(blockpos.north()) && d2 < d3) {
/* 1999 */       d3 = d2;
/* 2000 */       i = 4;
/*      */     } 
/*      */     
/* 2003 */     if (!this.worldObj.isBlockFullCube(blockpos.south()) && 1.0D - d2 < d3) {
/* 2004 */       d3 = 1.0D - d2;
/* 2005 */       i = 5;
/*      */     } 
/*      */     
/* 2008 */     float f = this.rand.nextFloat() * 0.2F + 0.1F;
/*      */     
/* 2010 */     if (i == 0) {
/* 2011 */       this.motionX = -f;
/*      */     }
/*      */     
/* 2014 */     if (i == 1) {
/* 2015 */       this.motionX = f;
/*      */     }
/*      */     
/* 2018 */     if (i == 3) {
/* 2019 */       this.motionY = f;
/*      */     }
/*      */     
/* 2022 */     if (i == 4) {
/* 2023 */       this.motionZ = -f;
/*      */     }
/*      */     
/* 2026 */     if (i == 5) {
/* 2027 */       this.motionZ = f;
/*      */     }
/*      */     
/* 2030 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInWeb() {
/* 2038 */     this.isInWeb = true;
/* 2039 */     this.fallDistance = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 2046 */     if (hasCustomName()) {
/* 2047 */       return getCustomNameTag();
/*      */     }
/* 2049 */     String s = EntityList.getEntityString(this);
/*      */     
/* 2051 */     if (s == null) {
/* 2052 */       s = "generic";
/*      */     }
/*      */     
/* 2055 */     return StatCollector.translateToLocal("entity." + s + ".name");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity[] getParts() {
/* 2063 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityEqual(Entity entityIn) {
/* 2070 */     return (this == entityIn);
/*      */   }
/*      */   
/*      */   public float getRotationYawHead() {
/* 2074 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRotationYawHead(float rotation) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderYawOffset(float offset) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackWithItem() {
/* 2095 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hitByEntity(Entity entityIn) {
/* 2102 */     return false;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 2106 */     return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", new Object[] { getClass().getSimpleName(), getName(), Integer.valueOf(this.entityId), (this.worldObj == null) ? "~NULL~" : this.worldObj.getWorldInfo().getWorldName(), Double.valueOf(this.posX), Double.valueOf(this.posY), Double.valueOf(this.posZ) });
/*      */   }
/*      */   
/*      */   public boolean isEntityInvulnerable(DamageSource source) {
/* 2110 */     return (this.invulnerable && source != DamageSource.outOfWorld && !source.isCreativePlayer());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyLocationAndAnglesFrom(Entity entityIn) {
/* 2117 */     setLocationAndAngles(entityIn.posX, entityIn.posY, entityIn.posZ, entityIn.rotationYaw, entityIn.rotationPitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyDataFromOld(Entity entityIn) {
/* 2124 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 2125 */     entityIn.writeToNBT(nbttagcompound);
/* 2126 */     readFromNBT(nbttagcompound);
/* 2127 */     this.timeUntilPortal = entityIn.timeUntilPortal;
/* 2128 */     this.lastPortalPos = entityIn.lastPortalPos;
/* 2129 */     this.lastPortalVec = entityIn.lastPortalVec;
/* 2130 */     this.teleportDirection = entityIn.teleportDirection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void travelToDimension(int dimensionId) {
/* 2137 */     if (!this.worldObj.isRemote && !this.isDead) {
/* 2138 */       this.worldObj.theProfiler.startSection("changeDimension");
/* 2139 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 2140 */       int i = this.dimension;
/* 2141 */       WorldServer worldserver = minecraftserver.worldServerForDimension(i);
/* 2142 */       WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionId);
/* 2143 */       this.dimension = dimensionId;
/*      */       
/* 2145 */       if (i == 1 && dimensionId == 1) {
/* 2146 */         worldserver1 = minecraftserver.worldServerForDimension(0);
/* 2147 */         this.dimension = 0;
/*      */       } 
/*      */       
/* 2150 */       this.worldObj.removeEntity(this);
/* 2151 */       this.isDead = false;
/* 2152 */       this.worldObj.theProfiler.startSection("reposition");
/* 2153 */       minecraftserver.getConfigurationManager().transferEntityToWorld(this, i, worldserver, worldserver1);
/* 2154 */       this.worldObj.theProfiler.endStartSection("reloading");
/* 2155 */       Entity entity = EntityList.createEntityByName(EntityList.getEntityString(this), (World)worldserver1);
/*      */       
/* 2157 */       if (entity != null) {
/* 2158 */         entity.copyDataFromOld(this);
/*      */         
/* 2160 */         if (i == 1 && dimensionId == 1) {
/* 2161 */           BlockPos blockpos = this.worldObj.getTopSolidOrLiquidBlock(worldserver1.getSpawnPoint());
/* 2162 */           entity.moveToBlockPosAndAngles(blockpos, entity.rotationYaw, entity.rotationPitch);
/*      */         } 
/*      */         
/* 2165 */         worldserver1.spawnEntityInWorld(entity);
/*      */       } 
/*      */       
/* 2168 */       this.isDead = true;
/* 2169 */       this.worldObj.theProfiler.endSection();
/* 2170 */       worldserver.resetUpdateEntityTick();
/* 2171 */       worldserver1.resetUpdateEntityTick();
/* 2172 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
/* 2180 */     return blockStateIn.getBlock().getExplosionResistance(this);
/*      */   }
/*      */   
/*      */   public boolean verifyExplosion(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn, float p_174816_5_) {
/* 2184 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxFallHeight() {
/* 2191 */     return 3;
/*      */   }
/*      */   
/*      */   public Vec3 func_181014_aG() {
/* 2195 */     return this.lastPortalVec;
/*      */   }
/*      */   
/*      */   public EnumFacing getTeleportDirection() {
/* 2199 */     return this.teleportDirection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean doesEntityNotTriggerPressurePlate() {
/* 2206 */     return false;
/*      */   }
/*      */   
/*      */   public void addEntityCrashInfo(CrashReportCategory category) {
/* 2210 */     category.addCrashSectionCallable("Entity Type", new Callable<String>() {
/*      */           public String call() throws Exception {
/* 2212 */             return String.valueOf(EntityList.getEntityString(Entity.this)) + " (" + Entity.this.getClass().getCanonicalName() + ")";
/*      */           }
/*      */         });
/* 2215 */     category.addCrashSection("Entity ID", Integer.valueOf(this.entityId));
/* 2216 */     category.addCrashSectionCallable("Entity Name", new Callable<String>() {
/*      */           public String call() throws Exception {
/* 2218 */             return Entity.this.getName();
/*      */           }
/*      */         });
/* 2221 */     category.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.posX), Double.valueOf(this.posY), Double.valueOf(this.posZ) }));
/* 2222 */     category.addCrashSection("Entity's Block location", CrashReportCategory.getCoordinateInfo(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
/* 2223 */     category.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.motionX), Double.valueOf(this.motionY), Double.valueOf(this.motionZ) }));
/* 2224 */     category.addCrashSectionCallable("Entity's Rider", new Callable<String>() {
/*      */           public String call() throws Exception {
/* 2226 */             return Entity.this.riddenByEntity.toString();
/*      */           }
/*      */         });
/* 2229 */     category.addCrashSectionCallable("Entity's Vehicle", new Callable<String>() {
/*      */           public String call() throws Exception {
/* 2231 */             return Entity.this.ridingEntity.toString();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRenderOnFire() {
/* 2240 */     return isBurning();
/*      */   }
/*      */   
/*      */   public UUID getUniqueID() {
/* 2244 */     return this.entityUniqueID;
/*      */   }
/*      */   
/*      */   public boolean isPushedByWater() {
/* 2248 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/* 2255 */     ChatComponentText chatcomponenttext = new ChatComponentText(getName());
/* 2256 */     chatcomponenttext.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 2257 */     chatcomponenttext.getChatStyle().setInsertion(getUniqueID().toString());
/* 2258 */     return (IChatComponent)chatcomponenttext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCustomNameTag(String name) {
/* 2265 */     this.dataWatcher.updateObject(2, name);
/*      */   }
/*      */   
/*      */   public String getCustomNameTag() {
/* 2269 */     return this.dataWatcher.getWatchableObjectString(2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasCustomName() {
/* 2276 */     return (this.dataWatcher.getWatchableObjectString(2).length() > 0);
/*      */   }
/*      */   
/*      */   public void setAlwaysRenderNameTag(boolean alwaysRenderNameTag) {
/* 2280 */     this.dataWatcher.updateObject(3, Byte.valueOf((byte)(alwaysRenderNameTag ? 1 : 0)));
/*      */   }
/*      */   
/*      */   public boolean getAlwaysRenderNameTag() {
/* 2284 */     return (this.dataWatcher.getWatchableObjectByte(3) == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndUpdate(double x, double y, double z) {
/* 2291 */     setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
/*      */   }
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 2295 */     return getAlwaysRenderNameTag();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDataWatcherUpdate(int dataID) {}
/*      */   
/*      */   public EnumFacing getHorizontalFacing() {
/* 2302 */     return EnumFacing.getHorizontal(MathHelper.floor_double((this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3);
/*      */   }
/*      */   
/*      */   protected HoverEvent getHoverEvent() {
/* 2306 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 2307 */     String s = EntityList.getEntityString(this);
/* 2308 */     nbttagcompound.setString("id", getUniqueID().toString());
/*      */     
/* 2310 */     if (s != null) {
/* 2311 */       nbttagcompound.setString("type", s);
/*      */     }
/*      */     
/* 2314 */     nbttagcompound.setString("name", getName());
/* 2315 */     return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, (IChatComponent)new ChatComponentText(nbttagcompound.toString()));
/*      */   }
/*      */   
/*      */   public boolean isSpectatedByPlayer(EntityPlayerMP player) {
/* 2319 */     return true;
/*      */   }
/*      */   
/*      */   public AxisAlignedBB getEntityBoundingBox() {
/* 2323 */     return this.boundingBox;
/*      */   }
/*      */   
/*      */   public void setEntityBoundingBox(AxisAlignedBB bb) {
/* 2327 */     this.boundingBox = bb;
/*      */   }
/*      */   
/*      */   public float getEyeHeight() {
/* 2331 */     return this.height * 0.85F;
/*      */   }
/*      */   
/*      */   public boolean isOutsideBorder() {
/* 2335 */     return this.isOutsideBorder;
/*      */   }
/*      */   
/*      */   public void setOutsideBorder(boolean outsideBorder) {
/* 2339 */     this.isOutsideBorder = outsideBorder;
/*      */   }
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 2343 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(IChatComponent component) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 2356 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 2364 */     return new BlockPos(this.posX, this.posY + 0.5D, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getPositionVector() {
/* 2372 */     return new Vec3(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public World getEntityWorld() {
/* 2380 */     return this.worldObj;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getCommandSenderEntity() {
/* 2387 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 2394 */     return false;
/*      */   }
/*      */   
/*      */   public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 2398 */     this.cmdResultStats.setCommandStatScore(this, type, amount);
/*      */   }
/*      */   
/*      */   public CommandResultStats getCommandStats() {
/* 2402 */     return this.cmdResultStats;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCommandStats(Entity entityIn) {
/* 2409 */     this.cmdResultStats.addAllStats(entityIn.getCommandStats());
/*      */   }
/*      */   
/*      */   public NBTTagCompound getNBTTagCompound() {
/* 2413 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clientUpdateEntityNBT(NBTTagCompound compound) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interactAt(EntityPlayer player, Vec3 targetVec3) {
/* 2426 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isImmuneToExplosions() {
/* 2430 */     return false;
/*      */   }
/*      */   
/*      */   protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
/* 2434 */     if (entityIn instanceof EntityLivingBase) {
/* 2435 */       EnchantmentHelper.applyThornEnchantments((EntityLivingBase)entityIn, entityLivingBaseIn);
/*      */     }
/*      */     
/* 2438 */     EnchantmentHelper.applyArthropodEnchantments(entityLivingBaseIn, entityIn);
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\Entity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */