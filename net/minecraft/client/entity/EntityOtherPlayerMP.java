/*     */ package net.minecraft.client.entity;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityOtherPlayerMP extends AbstractClientPlayer {
/*     */   private boolean isItemInUse;
/*     */   private int otherPlayerMPPosRotationIncrements;
/*     */   private double otherPlayerMPX;
/*     */   private double otherPlayerMPY;
/*     */   private double otherPlayerMPZ;
/*     */   private double otherPlayerMPYaw;
/*     */   private double otherPlayerMPPitch;
/*     */   
/*     */   public EntityOtherPlayerMP(World worldIn, GameProfile gameProfileIn) {
/*  22 */     super(worldIn, gameProfileIn);
/*  23 */     this.stepHeight = 0.0F;
/*  24 */     this.noClip = true;
/*  25 */     this.renderOffsetY = 0.25F;
/*  26 */     this.renderDistanceWeight = 10.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  33 */     return true;
/*     */   }
/*     */   
/*     */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/*  37 */     this.otherPlayerMPX = x;
/*  38 */     this.otherPlayerMPY = y;
/*  39 */     this.otherPlayerMPZ = z;
/*  40 */     this.otherPlayerMPYaw = yaw;
/*  41 */     this.otherPlayerMPPitch = pitch;
/*  42 */     this.otherPlayerMPPosRotationIncrements = posRotationIncrements;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  49 */     this.renderOffsetY = 0.0F;
/*  50 */     super.onUpdate();
/*  51 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/*  52 */     double d0 = this.posX - this.prevPosX;
/*  53 */     double d1 = this.posZ - this.prevPosZ;
/*  54 */     float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;
/*     */     
/*  56 */     if (f > 1.0F) {
/*  57 */       f = 1.0F;
/*     */     }
/*     */     
/*  60 */     this.limbSwingAmount += (f - this.limbSwingAmount) * 0.4F;
/*  61 */     this.limbSwing += this.limbSwingAmount;
/*     */     
/*  63 */     if (!this.isItemInUse && isEating() && this.inventory.mainInventory[this.inventory.currentItem] != null) {
/*  64 */       ItemStack itemstack = this.inventory.mainInventory[this.inventory.currentItem];
/*  65 */       setItemInUse(this.inventory.mainInventory[this.inventory.currentItem], itemstack.getItem().getMaxItemUseDuration(itemstack));
/*  66 */       this.isItemInUse = true;
/*  67 */     } else if (this.isItemInUse && !isEating()) {
/*  68 */       clearItemInUse();
/*  69 */       this.isItemInUse = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/*  78 */     if (this.otherPlayerMPPosRotationIncrements > 0) {
/*  79 */       double d0 = this.posX + (this.otherPlayerMPX - this.posX) / this.otherPlayerMPPosRotationIncrements;
/*  80 */       double d1 = this.posY + (this.otherPlayerMPY - this.posY) / this.otherPlayerMPPosRotationIncrements;
/*  81 */       double d2 = this.posZ + (this.otherPlayerMPZ - this.posZ) / this.otherPlayerMPPosRotationIncrements;
/*     */       
/*     */       double d3;
/*  84 */       for (d3 = this.otherPlayerMPYaw - this.rotationYaw; d3 < -180.0D; d3 += 360.0D);
/*     */ 
/*     */ 
/*     */       
/*  88 */       while (d3 >= 180.0D) {
/*  89 */         d3 -= 360.0D;
/*     */       }
/*     */       
/*  92 */       this.rotationYaw = (float)(this.rotationYaw + d3 / this.otherPlayerMPPosRotationIncrements);
/*  93 */       this.rotationPitch = (float)(this.rotationPitch + (this.otherPlayerMPPitch - this.rotationPitch) / this.otherPlayerMPPosRotationIncrements);
/*  94 */       this.otherPlayerMPPosRotationIncrements--;
/*  95 */       setPosition(d0, d1, d2);
/*  96 */       setRotation(this.rotationYaw, this.rotationPitch);
/*     */     } 
/*     */     
/*  99 */     this.prevCameraYaw = this.cameraYaw;
/* 100 */     updateArmSwingProgress();
/* 101 */     float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 102 */     float f = (float)Math.atan(-this.motionY * 0.20000000298023224D) * 15.0F;
/*     */     
/* 104 */     if (f1 > 0.1F) {
/* 105 */       f1 = 0.1F;
/*     */     }
/*     */     
/* 108 */     if (!this.onGround || getHealth() <= 0.0F) {
/* 109 */       f1 = 0.0F;
/*     */     }
/*     */     
/* 112 */     if (this.onGround || getHealth() <= 0.0F) {
/* 113 */       f = 0.0F;
/*     */     }
/*     */     
/* 116 */     this.cameraYaw += (f1 - this.cameraYaw) * 0.4F;
/* 117 */     this.cameraPitch += (f - this.cameraPitch) * 0.8F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
/* 124 */     if (slotIn == 0) {
/* 125 */       this.inventory.mainInventory[this.inventory.currentItem] = stack;
/*     */     } else {
/* 127 */       this.inventory.armorInventory[slotIn - 1] = stack;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChatMessage(IChatComponent component) {
/* 135 */     (Minecraft.getMinecraft()).ingameGUI.getChatGUI().printChatMessage(component);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 150 */     return new BlockPos(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\entity\EntityOtherPlayerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */