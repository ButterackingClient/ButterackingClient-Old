/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.util.MathHelper;
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
/*     */ public class EntityLookHelper
/*     */ {
/*     */   private EntityLiving entity;
/*     */   private float deltaLookYaw;
/*     */   private float deltaLookPitch;
/*     */   private boolean isLooking;
/*     */   private double posX;
/*     */   private double posY;
/*     */   private double posZ;
/*     */   
/*     */   public EntityLookHelper(EntityLiving entitylivingIn) {
/*  30 */     this.entity = entitylivingIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLookPositionWithEntity(Entity entityIn, float deltaYaw, float deltaPitch) {
/*  37 */     this.posX = entityIn.posX;
/*     */     
/*  39 */     if (entityIn instanceof net.minecraft.entity.EntityLivingBase) {
/*  40 */       this.posY = entityIn.posY + entityIn.getEyeHeight();
/*     */     } else {
/*  42 */       this.posY = ((entityIn.getEntityBoundingBox()).minY + (entityIn.getEntityBoundingBox()).maxY) / 2.0D;
/*     */     } 
/*     */     
/*  45 */     this.posZ = entityIn.posZ;
/*  46 */     this.deltaLookYaw = deltaYaw;
/*  47 */     this.deltaLookPitch = deltaPitch;
/*  48 */     this.isLooking = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLookPosition(double x, double y, double z, float deltaYaw, float deltaPitch) {
/*  55 */     this.posX = x;
/*  56 */     this.posY = y;
/*  57 */     this.posZ = z;
/*  58 */     this.deltaLookYaw = deltaYaw;
/*  59 */     this.deltaLookPitch = deltaPitch;
/*  60 */     this.isLooking = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdateLook() {
/*  67 */     this.entity.rotationPitch = 0.0F;
/*     */     
/*  69 */     if (this.isLooking) {
/*  70 */       this.isLooking = false;
/*  71 */       double d0 = this.posX - this.entity.posX;
/*  72 */       double d1 = this.posY - this.entity.posY + this.entity.getEyeHeight();
/*  73 */       double d2 = this.posZ - this.entity.posZ;
/*  74 */       double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
/*  75 */       float f = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
/*  76 */       float f1 = (float)-(MathHelper.atan2(d1, d3) * 180.0D / Math.PI);
/*  77 */       this.entity.rotationPitch = updateRotation(this.entity.rotationPitch, f1, this.deltaLookPitch);
/*  78 */       this.entity.rotationYawHead = updateRotation(this.entity.rotationYawHead, f, this.deltaLookYaw);
/*     */     } else {
/*  80 */       this.entity.rotationYawHead = updateRotation(this.entity.rotationYawHead, this.entity.renderYawOffset, 10.0F);
/*     */     } 
/*     */     
/*  83 */     float f2 = MathHelper.wrapAngleTo180_float(this.entity.rotationYawHead - this.entity.renderYawOffset);
/*     */     
/*  85 */     if (!this.entity.getNavigator().noPath()) {
/*  86 */       if (f2 < -75.0F) {
/*  87 */         this.entity.rotationYawHead = this.entity.renderYawOffset - 75.0F;
/*     */       }
/*     */       
/*  90 */       if (f2 > 75.0F) {
/*  91 */         this.entity.rotationYawHead = this.entity.renderYawOffset + 75.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private float updateRotation(float p_75652_1_, float p_75652_2_, float p_75652_3_) {
/*  97 */     float f = MathHelper.wrapAngleTo180_float(p_75652_2_ - p_75652_1_);
/*     */     
/*  99 */     if (f > p_75652_3_) {
/* 100 */       f = p_75652_3_;
/*     */     }
/*     */     
/* 103 */     if (f < -p_75652_3_) {
/* 104 */       f = -p_75652_3_;
/*     */     }
/*     */     
/* 107 */     return p_75652_1_ + f;
/*     */   }
/*     */   
/*     */   public boolean getIsLooking() {
/* 111 */     return this.isLooking;
/*     */   }
/*     */   
/*     */   public double getLookPosX() {
/* 115 */     return this.posX;
/*     */   }
/*     */   
/*     */   public double getLookPosY() {
/* 119 */     return this.posY;
/*     */   }
/*     */   
/*     */   public double getLookPosZ() {
/* 123 */     return this.posZ;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityLookHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */