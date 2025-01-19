/*      */ package net.minecraft.client.entity;
/*      */ 
/*      */ import client.event.impl.EventUpdate;
/*      */ import client.timer.DeltaTimer;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.audio.ISound;
/*      */ import net.minecraft.client.audio.MovingSoundMinecartRiding;
/*      */ import net.minecraft.client.audio.PositionedSoundRecord;
/*      */ import net.minecraft.client.gui.GuiCommandBlock;
/*      */ import net.minecraft.client.gui.GuiEnchantment;
/*      */ import net.minecraft.client.gui.GuiHopper;
/*      */ import net.minecraft.client.gui.GuiMerchant;
/*      */ import net.minecraft.client.gui.GuiRepair;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiScreenBook;
/*      */ import net.minecraft.client.gui.inventory.GuiBeacon;
/*      */ import net.minecraft.client.gui.inventory.GuiBrewingStand;
/*      */ import net.minecraft.client.gui.inventory.GuiChest;
/*      */ import net.minecraft.client.gui.inventory.GuiCrafting;
/*      */ import net.minecraft.client.gui.inventory.GuiDispenser;
/*      */ import net.minecraft.client.gui.inventory.GuiEditSign;
/*      */ import net.minecraft.client.gui.inventory.GuiFurnace;
/*      */ import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
/*      */ import net.minecraft.client.network.NetHandlerPlayClient;
/*      */ import net.minecraft.command.server.CommandBlockLogic;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.passive.EntityHorse;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*      */ import net.minecraft.network.play.client.C03PacketPlayer;
/*      */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*      */ import net.minecraft.network.play.client.C0APacketAnimation;
/*      */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*      */ import net.minecraft.network.play.client.C0CPacketInput;
/*      */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*      */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*      */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.stats.StatFileWriter;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MovementInput;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.world.IInteractionObject;
/*      */ import net.minecraft.world.IWorldNameable;
/*      */ import net.minecraft.world.World;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EntityPlayerSP
/*      */   extends AbstractClientPlayer
/*      */ {
/*      */   public final NetHandlerPlayClient sendQueue;
/*      */   private final StatFileWriter statWriter;
/*      */   private double lastReportedPosX;
/*      */   private double lastReportedPosY;
/*      */   private double lastReportedPosZ;
/*      */   private float lastReportedYaw;
/*      */   private float lastReportedPitch;
/*      */   private boolean serverSneakState;
/*      */   private boolean serverSprintState;
/*      */   private int positionUpdateTicks;
/*      */   private boolean hasValidHealth;
/*      */   private String clientBrand;
/*      */   public MovementInput movementInput;
/*      */   protected Minecraft mc;
/*      */   protected int sprintToggleTimer;
/*      */   public int sprintingTicksLeft;
/*      */   public float renderArmYaw;
/*      */   public float renderArmPitch;
/*      */   public float prevRenderArmYaw;
/*      */   public float prevRenderArmPitch;
/*      */   private int horseJumpPowerCounter;
/*      */   private float horseJumpPower;
/*      */   public float timeInPortal;
/*      */   public float prevTimeInPortal;
/*      */   
/*      */   public EntityPlayerSP(Minecraft mcIn, World worldIn, NetHandlerPlayClient netHandler, StatFileWriter statFile) {
/*  961 */     super(worldIn, netHandler.getGameProfile());
/*  962 */     this.sendQueue = netHandler;
/*  963 */     this.statWriter = statFile;
/*  964 */     this.mc = mcIn;
/*  965 */     this.dimension = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  970 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void heal(float healAmount) {}
/*      */ 
/*      */   
/*      */   public void mountEntity(Entity entityIn) {
/*  979 */     super.mountEntity(entityIn);
/*  980 */     if (entityIn instanceof EntityMinecart) {
/*  981 */       this.mc.getSoundHandler().playSound((ISound)new MovingSoundMinecartRiding(this, (EntityMinecart)entityIn));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  988 */     DeltaTimer.D.start();
/*  989 */     EventUpdate event = new EventUpdate();
/*  990 */     event.call();
/*  991 */     if (this.worldObj.isBlockLoaded(new BlockPos(this.posX, 0.0D, this.posZ))) {
/*  992 */       super.onUpdate();
/*  993 */       if (isRiding()) {
/*  994 */         this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
/*  995 */         this.sendQueue.addToSendQueue((Packet)new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
/*      */       } else {
/*  997 */         onUpdateWalkingPlayer();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onUpdateWalkingPlayer() {
/* 1003 */     boolean flag = isSprinting();
/* 1004 */     if (flag != this.serverSprintState) {
/* 1005 */       if (flag) {
/* 1006 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.START_SPRINTING));
/*      */       } else {
/* 1008 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.STOP_SPRINTING));
/*      */       } 
/* 1010 */       this.serverSprintState = flag;
/*      */     } 
/* 1012 */     boolean flag2 = isSneaking();
/* 1013 */     if (flag2 != this.serverSneakState) {
/* 1014 */       if (flag2) {
/* 1015 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.START_SNEAKING));
/*      */       } else {
/* 1017 */         this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.STOP_SNEAKING));
/*      */       } 
/* 1019 */       this.serverSneakState = flag2;
/*      */     } 
/* 1021 */     if (isCurrentViewEntity()) {
/* 1022 */       double d0 = this.posX - this.lastReportedPosX;
/* 1023 */       double d2 = (getEntityBoundingBox()).minY - this.lastReportedPosY;
/* 1024 */       double d3 = this.posZ - this.lastReportedPosZ;
/* 1025 */       double d4 = (this.rotationYaw - this.lastReportedYaw);
/* 1026 */       double d5 = (this.rotationPitch - this.lastReportedPitch);
/* 1027 */       boolean flag3 = !(d0 * d0 + d2 * d2 + d3 * d3 <= 9.0E-4D && this.positionUpdateTicks < 20);
/* 1028 */       boolean flag4 = !(d4 == 0.0D && d5 == 0.0D);
/* 1029 */       if (this.ridingEntity == null) {
/* 1030 */         if (flag3 && flag4) {
/* 1031 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, (getEntityBoundingBox()).minY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
/* 1032 */         } else if (flag3) {
/* 1033 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(this.posX, (getEntityBoundingBox()).minY, this.posZ, this.onGround));
/* 1034 */         } else if (flag4) {
/* 1035 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
/*      */         } else {
/* 1037 */           this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer(this.onGround));
/*      */         } 
/*      */       } else {
/* 1040 */         this.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
/* 1041 */         flag3 = false;
/*      */       } 
/* 1043 */       this.positionUpdateTicks++;
/* 1044 */       if (flag3) {
/* 1045 */         this.lastReportedPosX = this.posX;
/* 1046 */         this.lastReportedPosY = (getEntityBoundingBox()).minY;
/* 1047 */         this.lastReportedPosZ = this.posZ;
/* 1048 */         this.positionUpdateTicks = 0;
/*      */       } 
/* 1050 */       if (flag4) {
/* 1051 */         this.lastReportedYaw = this.rotationYaw;
/* 1052 */         this.lastReportedPitch = this.rotationPitch;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityItem dropOneItem(boolean dropAll) {
/* 1059 */     C07PacketPlayerDigging.Action c07packetplayerdigging$action = dropAll ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
/* 1060 */     this.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(c07packetplayerdigging$action, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 1061 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void joinEntityItemWithWorld(EntityItem itemIn) {}
/*      */ 
/*      */   
/*      */   public void sendChatMessage(String message) {
/* 1069 */     this.sendQueue.addToSendQueue((Packet)new C01PacketChatMessage(message));
/*      */   }
/*      */ 
/*      */   
/*      */   public void swingItem() {
/* 1074 */     super.swingItem();
/* 1075 */     this.sendQueue.addToSendQueue((Packet)new C0APacketAnimation());
/*      */   }
/*      */   
/*      */   public void FakeswingItem() {
/* 1079 */     super.swingItem();
/*      */   }
/*      */ 
/*      */   
/*      */   public void respawnPlayer() {
/* 1084 */     this.sendQueue.addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 1089 */     if (!isEntityInvulnerable(damageSrc)) {
/* 1090 */       setHealth(getHealth() - damageAmount);
/*      */     }
/*      */   }
/*      */   
/*      */   public void closeScreen() {
/* 1095 */     this.sendQueue.addToSendQueue((Packet)new C0DPacketCloseWindow(this.openContainer.windowId));
/* 1096 */     closeScreenAndDropStack();
/*      */   }
/*      */   
/*      */   public void closeScreenAndDropStack() {
/* 1100 */     this.inventory.setItemStack(null);
/* 1101 */     super.closeScreen();
/* 1102 */     this.mc.displayGuiScreen(null);
/*      */   }
/*      */   
/*      */   public void setPlayerSPHealth(float health) {
/* 1106 */     if (this.hasValidHealth) {
/* 1107 */       float f = getHealth() - health;
/* 1108 */       if (f <= 0.0F) {
/* 1109 */         setHealth(health);
/* 1110 */         if (f < 0.0F) {
/* 1111 */           this.hurtResistantTime = this.maxHurtResistantTime / 2;
/*      */         }
/*      */       } else {
/* 1114 */         this.lastDamage = f;
/* 1115 */         setHealth(getHealth());
/* 1116 */         this.hurtResistantTime = this.maxHurtResistantTime;
/* 1117 */         damageEntity(DamageSource.generic, f);
/* 1118 */         int n = 10;
/* 1119 */         this.maxHurtTime = 10;
/* 1120 */         this.hurtTime = 10;
/*      */       } 
/*      */     } else {
/* 1123 */       setHealth(health);
/* 1124 */       this.hasValidHealth = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void addStat(StatBase stat, int amount) {
/* 1130 */     if (stat != null && stat.isIndependent) {
/* 1131 */       super.addStat(stat, amount);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPlayerAbilities() {
/* 1137 */     this.sendQueue.addToSendQueue((Packet)new C13PacketPlayerAbilities(this.capabilities));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isUser() {
/* 1142 */     return true;
/*      */   }
/*      */   
/*      */   protected void sendHorseJump() {
/* 1146 */     this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(getHorseJumpPower() * 100.0F)));
/*      */   }
/*      */   
/*      */   public void sendHorseInventory() {
/* 1150 */     this.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this, C0BPacketEntityAction.Action.OPEN_INVENTORY));
/*      */   }
/*      */   
/*      */   public void setClientBrand(String brand) {
/* 1154 */     this.clientBrand = brand;
/*      */   }
/*      */   
/*      */   public String getClientBrand() {
/* 1158 */     return this.clientBrand;
/*      */   }
/*      */   
/*      */   public StatFileWriter getStatFileWriter() {
/* 1162 */     return this.statWriter;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addChatComponentMessage(IChatComponent chatComponent) {
/* 1167 */     this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponent);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean pushOutOfBlocks(double x, double y, double z) {
/* 1172 */     if (this.noClip) {
/* 1173 */       return false;
/*      */     }
/* 1175 */     BlockPos blockpos = new BlockPos(x, y, z);
/* 1176 */     double d0 = x - blockpos.getX();
/* 1177 */     double d2 = z - blockpos.getZ();
/* 1178 */     if (!isOpenBlockSpace(blockpos)) {
/* 1179 */       int i = -1;
/* 1180 */       double d3 = 9999.0D;
/* 1181 */       if (isOpenBlockSpace(blockpos.west()) && d0 < d3) {
/* 1182 */         d3 = d0;
/* 1183 */         i = 0;
/*      */       } 
/* 1185 */       if (isOpenBlockSpace(blockpos.east()) && 1.0D - d0 < d3) {
/* 1186 */         d3 = 1.0D - d0;
/* 1187 */         i = 1;
/*      */       } 
/* 1189 */       if (isOpenBlockSpace(blockpos.north()) && d2 < d3) {
/* 1190 */         d3 = d2;
/* 1191 */         i = 4;
/*      */       } 
/* 1193 */       if (isOpenBlockSpace(blockpos.south()) && 1.0D - d2 < d3) {
/* 1194 */         d3 = 1.0D - d2;
/* 1195 */         i = 5;
/*      */       } 
/* 1197 */       float f = 0.1F;
/* 1198 */       if (i == 0) {
/* 1199 */         this.motionX = -0.10000000149011612D;
/*      */       }
/* 1201 */       if (i == 1) {
/* 1202 */         this.motionX = 0.10000000149011612D;
/*      */       }
/* 1204 */       if (i == 4) {
/* 1205 */         this.motionZ = -0.10000000149011612D;
/*      */       }
/* 1207 */       if (i == 5) {
/* 1208 */         this.motionZ = 0.10000000149011612D;
/*      */       }
/*      */     } 
/* 1211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean isOpenBlockSpace(BlockPos pos) {
/* 1215 */     return (!this.worldObj.getBlockState(pos).getBlock().isNormalCube() && !this.worldObj.getBlockState(pos.up()).getBlock().isNormalCube());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSprinting(boolean sprinting) {
/* 1220 */     super.setSprinting(sprinting);
/* 1221 */     this.sprintingTicksLeft = sprinting ? 600 : 0;
/*      */   }
/*      */   
/*      */   public void setXPStats(float currentXP, int maxXP, int level) {
/* 1225 */     this.experience = currentXP;
/* 1226 */     this.experienceTotal = maxXP;
/* 1227 */     this.experienceLevel = level;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addChatMessage(IChatComponent component) {
/* 1232 */     this.mc.ingameGUI.getChatGUI().printChatMessage(component);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 1237 */     return (permLevel <= 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 1242 */     return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSound(String name, float volume, float pitch) {
/* 1247 */     this.worldObj.playSound(this.posX, this.posY, this.posZ, name, volume, pitch, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isServerWorld() {
/* 1252 */     return true;
/*      */   }
/*      */   
/*      */   public boolean isRidingHorse() {
/* 1256 */     return (this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled());
/*      */   }
/*      */   
/*      */   public float getHorseJumpPower() {
/* 1260 */     return this.horseJumpPower;
/*      */   }
/*      */ 
/*      */   
/*      */   public void openEditSign(TileEntitySign signTile) {
/* 1265 */     this.mc.displayGuiScreen((GuiScreen)new GuiEditSign(signTile));
/*      */   }
/*      */ 
/*      */   
/*      */   public void openEditCommandBlock(CommandBlockLogic cmdBlockLogic) {
/* 1270 */     this.mc.displayGuiScreen((GuiScreen)new GuiCommandBlock(cmdBlockLogic));
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGUIBook(ItemStack bookStack) {
/* 1275 */     Item item = bookStack.getItem();
/* 1276 */     if (item == Items.writable_book) {
/* 1277 */       this.mc.displayGuiScreen((GuiScreen)new GuiScreenBook(this, bookStack, true));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGUIChest(IInventory chestInventory) {
/* 1283 */     String s = (chestInventory instanceof IInteractionObject) ? ((IInteractionObject)chestInventory).getGuiID() : "minecraft:container";
/* 1284 */     if ("minecraft:chest".equals(s)) {
/* 1285 */       this.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)this.inventory, chestInventory));
/* 1286 */     } else if ("minecraft:hopper".equals(s)) {
/* 1287 */       this.mc.displayGuiScreen((GuiScreen)new GuiHopper(this.inventory, chestInventory));
/* 1288 */     } else if ("minecraft:furnace".equals(s)) {
/* 1289 */       this.mc.displayGuiScreen((GuiScreen)new GuiFurnace(this.inventory, chestInventory));
/* 1290 */     } else if ("minecraft:brewing_stand".equals(s)) {
/* 1291 */       this.mc.displayGuiScreen((GuiScreen)new GuiBrewingStand(this.inventory, chestInventory));
/* 1292 */     } else if ("minecraft:beacon".equals(s)) {
/* 1293 */       this.mc.displayGuiScreen((GuiScreen)new GuiBeacon(this.inventory, chestInventory));
/* 1294 */     } else if (!"minecraft:dispenser".equals(s) && !"minecraft:dropper".equals(s)) {
/* 1295 */       this.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)this.inventory, chestInventory));
/*      */     } else {
/* 1297 */       this.mc.displayGuiScreen((GuiScreen)new GuiDispenser(this.inventory, chestInventory));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGUIHorse(EntityHorse horse, IInventory horseInventory) {
/* 1303 */     this.mc.displayGuiScreen((GuiScreen)new GuiScreenHorseInventory((IInventory)this.inventory, horseInventory, horse));
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGui(IInteractionObject guiOwner) {
/* 1308 */     String s = guiOwner.getGuiID();
/* 1309 */     if ("minecraft:crafting_table".equals(s)) {
/* 1310 */       this.mc.displayGuiScreen((GuiScreen)new GuiCrafting(this.inventory, this.worldObj));
/* 1311 */     } else if ("minecraft:enchanting_table".equals(s)) {
/* 1312 */       this.mc.displayGuiScreen((GuiScreen)new GuiEnchantment(this.inventory, this.worldObj, (IWorldNameable)guiOwner));
/* 1313 */     } else if ("minecraft:anvil".equals(s)) {
/* 1314 */       this.mc.displayGuiScreen((GuiScreen)new GuiRepair(this.inventory, this.worldObj));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayVillagerTradeGui(IMerchant villager) {
/* 1320 */     this.mc.displayGuiScreen((GuiScreen)new GuiMerchant(this.inventory, villager, this.worldObj));
/*      */   }
/*      */ 
/*      */   
/*      */   public void onCriticalHit(Entity entityHit) {
/* 1325 */     this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onEnchantmentCritical(Entity entityHit) {
/* 1330 */     this.mc.effectRenderer.emitParticleAtEntity(entityHit, EnumParticleTypes.CRIT_MAGIC);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSneaking() {
/* 1335 */     boolean flag = (this.movementInput != null && this.movementInput.sneak);
/* 1336 */     return (flag && !this.sleeping);
/*      */   }
/*      */   
/*      */   public void updateEntityActionState() {
/* 1340 */     super.updateEntityActionState();
/* 1341 */     if (isCurrentViewEntity()) {
/* 1342 */       this.moveStrafing = this.movementInput.moveStrafe;
/* 1343 */       this.moveForward = this.movementInput.moveForward;
/* 1344 */       this.isJumping = this.movementInput.jump;
/* 1345 */       this.prevRenderArmYaw = this.renderArmYaw;
/* 1346 */       this.prevRenderArmPitch = this.renderArmPitch;
/* 1347 */       this.renderArmPitch += (float)((this.rotationPitch - this.renderArmPitch) * 0.5D);
/* 1348 */       this.renderArmYaw += (float)((this.rotationYaw - this.renderArmYaw) * 0.5D);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean isCurrentViewEntity() {
/* 1353 */     return (this.mc.getRenderViewEntity() == this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/* 1358 */     if (this.sprintingTicksLeft > 0) {
/* 1359 */       this.sprintingTicksLeft--;
/* 1360 */       if (this.sprintingTicksLeft == 0) {
/* 1361 */         setSprinting(false);
/*      */       }
/*      */     } 
/* 1364 */     if (this.sprintToggleTimer > 0) {
/* 1365 */       this.sprintToggleTimer--;
/*      */     }
/* 1367 */     this.prevTimeInPortal = this.timeInPortal;
/* 1368 */     if (this.inPortal) {
/* 1369 */       if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame()) {
/* 1370 */         this.mc.displayGuiScreen(null);
/*      */       }
/* 1372 */       if (this.timeInPortal == 0.0F) {
/* 1373 */         this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
/*      */       }
/* 1375 */       this.timeInPortal += 0.0125F;
/* 1376 */       if (this.timeInPortal >= 1.0F) {
/* 1377 */         this.timeInPortal = 1.0F;
/*      */       }
/* 1379 */       this.inPortal = false;
/* 1380 */     } else if (isPotionActive(Potion.confusion) && getActivePotionEffect(Potion.confusion).getDuration() > 60) {
/* 1381 */       this.timeInPortal += 0.006666667F;
/* 1382 */       if (this.timeInPortal > 1.0F) {
/* 1383 */         this.timeInPortal = 1.0F;
/*      */       }
/*      */     } else {
/* 1386 */       if (this.timeInPortal > 0.0F) {
/* 1387 */         this.timeInPortal -= 0.05F;
/*      */       }
/* 1389 */       if (this.timeInPortal < 0.0F) {
/* 1390 */         this.timeInPortal = 0.0F;
/*      */       }
/*      */     } 
/* 1393 */     if (this.timeUntilPortal > 0) {
/* 1394 */       this.timeUntilPortal--;
/*      */     }
/* 1396 */     boolean flag = this.movementInput.jump;
/* 1397 */     boolean flag2 = this.movementInput.sneak;
/* 1398 */     float f = 0.8F;
/* 1399 */     boolean flag3 = (this.movementInput.moveForward >= 0.8F);
/* 1400 */     this.movementInput.updatePlayerMoveState();
/* 1401 */     if (isUsingItem() && !isRiding()) {
/* 1402 */       MovementInput movementInput = this.movementInput;
/* 1403 */       movementInput.moveStrafe *= 0.2F;
/* 1404 */       MovementInput movementInput2 = this.movementInput;
/* 1405 */       movementInput2.moveForward *= 0.2F;
/* 1406 */       this.sprintToggleTimer = 0;
/*      */     } 
/* 1408 */     pushOutOfBlocks(this.posX - this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ + this.width * 0.35D);
/* 1409 */     pushOutOfBlocks(this.posX - this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ - this.width * 0.35D);
/* 1410 */     pushOutOfBlocks(this.posX + this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ - this.width * 0.35D);
/* 1411 */     pushOutOfBlocks(this.posX + this.width * 0.35D, (getEntityBoundingBox()).minY + 0.5D, this.posZ + this.width * 0.35D);
/* 1412 */     boolean flag4 = !(getFoodStats().getFoodLevel() <= 6.0F && !this.capabilities.allowFlying);
/* 1413 */     if (this.onGround && !flag2 && !flag3 && this.movementInput.moveForward >= 0.8F && !isSprinting() && flag4 && !isUsingItem() && !isPotionActive(Potion.blindness)) {
/* 1414 */       if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindInventory.isKeyDown()) {
/* 1415 */         this.sprintToggleTimer = 7;
/*      */       } else {
/* 1417 */         setSprinting(true);
/*      */       } 
/*      */     }
/* 1420 */     if (!isSprinting() && this.movementInput.moveForward >= 0.8F && flag4 && !isUsingItem() && !isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindInventory.isKeyDown()) {
/* 1421 */       setSprinting(true);
/*      */     }
/* 1423 */     if (isSprinting() && (this.movementInput.moveForward < 0.8F || this.isCollidedHorizontally || !flag4)) {
/* 1424 */       setSprinting(false);
/*      */     }
/* 1426 */     if (this.capabilities.allowFlying) {
/* 1427 */       if (this.mc.playerController.isSpectatorMode()) {
/* 1428 */         if (!this.capabilities.isFlying) {
/* 1429 */           this.capabilities.isFlying = true;
/* 1430 */           sendPlayerAbilities();
/*      */         } 
/* 1432 */       } else if (!flag && this.movementInput.jump) {
/* 1433 */         if (this.flyToggleTimer == 0) {
/* 1434 */           this.flyToggleTimer = 7;
/*      */         } else {
/* 1436 */           this.capabilities.isFlying = !this.capabilities.isFlying;
/* 1437 */           sendPlayerAbilities();
/* 1438 */           this.flyToggleTimer = 0;
/*      */         } 
/*      */       } 
/*      */     }
/* 1442 */     if (this.capabilities.isFlying && isCurrentViewEntity()) {
/* 1443 */       if (this.movementInput.sneak) {
/* 1444 */         this.motionY -= (this.capabilities.getFlySpeed() * 3.0F);
/*      */       }
/* 1446 */       if (this.movementInput.jump) {
/* 1447 */         this.motionY += (this.capabilities.getFlySpeed() * 3.0F);
/*      */       }
/*      */     } 
/* 1450 */     if (isRidingHorse()) {
/* 1451 */       if (this.horseJumpPowerCounter < 0) {
/* 1452 */         this.horseJumpPowerCounter++;
/* 1453 */         if (this.horseJumpPowerCounter == 0) {
/* 1454 */           this.horseJumpPower = 0.0F;
/*      */         }
/*      */       } 
/* 1457 */       if (flag && !this.movementInput.jump) {
/* 1458 */         this.horseJumpPowerCounter = -10;
/* 1459 */         sendHorseJump();
/* 1460 */       } else if (!flag && this.movementInput.jump) {
/* 1461 */         this.horseJumpPowerCounter = 0;
/* 1462 */         this.horseJumpPower = 0.0F;
/* 1463 */       } else if (flag) {
/* 1464 */         this.horseJumpPowerCounter++;
/* 1465 */         if (this.horseJumpPowerCounter < 10) {
/* 1466 */           this.horseJumpPower = this.horseJumpPowerCounter * 0.1F;
/*      */         } else {
/* 1468 */           this.horseJumpPower = 0.8F + 2.0F / (this.horseJumpPowerCounter - 9) * 0.1F;
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1472 */       this.horseJumpPower = 0.0F;
/*      */     } 
/* 1474 */     super.onLivingUpdate();
/* 1475 */     if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode()) {
/* 1476 */       this.capabilities.isFlying = false;
/* 1477 */       sendPlayerAbilities();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\entity\EntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */