/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityMoveHelper
/*     */ {
/*     */   protected EntityLiving entity;
/*     */   protected double posX;
/*     */   protected double posY;
/*     */   protected double posZ;
/*     */   protected double speed;
/*     */   protected boolean update;
/*     */   
/*     */   public EntityMoveHelper(EntityLiving entitylivingIn) {
/*  23 */     this.entity = entitylivingIn;
/*  24 */     this.posX = entitylivingIn.posX;
/*  25 */     this.posY = entitylivingIn.posY;
/*  26 */     this.posZ = entitylivingIn.posZ;
/*     */   }
/*     */   
/*     */   public boolean isUpdating() {
/*  30 */     return this.update;
/*     */   }
/*     */   
/*     */   public double getSpeed() {
/*  34 */     return this.speed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMoveTo(double x, double y, double z, double speedIn) {
/*  41 */     this.posX = x;
/*  42 */     this.posY = y;
/*  43 */     this.posZ = z;
/*  44 */     this.speed = speedIn;
/*  45 */     this.update = true;
/*     */   }
/*     */   
/*     */   public void onUpdateMoveHelper() {
/*  49 */     this.entity.setMoveForward(0.0F);
/*     */     
/*  51 */     if (this.update) {
/*  52 */       this.update = false;
/*  53 */       int i = MathHelper.floor_double((this.entity.getEntityBoundingBox()).minY + 0.5D);
/*  54 */       double d0 = this.posX - this.entity.posX;
/*  55 */       double d1 = this.posZ - this.entity.posZ;
/*  56 */       double d2 = this.posY - i;
/*  57 */       double d3 = d0 * d0 + d2 * d2 + d1 * d1;
/*     */       
/*  59 */       if (d3 >= 2.500000277905201E-7D) {
/*  60 */         float f = (float)(MathHelper.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
/*  61 */         this.entity.rotationYaw = limitAngle(this.entity.rotationYaw, f, 30.0F);
/*  62 */         this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
/*     */         
/*  64 */         if (d2 > 0.0D && d0 * d0 + d1 * d1 < 1.0D) {
/*  65 */           this.entity.getJumpHelper().setJumping();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float limitAngle(float p_75639_1_, float p_75639_2_, float p_75639_3_) {
/*  75 */     float f = MathHelper.wrapAngleTo180_float(p_75639_2_ - p_75639_1_);
/*     */     
/*  77 */     if (f > p_75639_3_) {
/*  78 */       f = p_75639_3_;
/*     */     }
/*     */     
/*  81 */     if (f < -p_75639_3_) {
/*  82 */       f = -p_75639_3_;
/*     */     }
/*     */     
/*  85 */     float f1 = p_75639_1_ + f;
/*     */     
/*  87 */     if (f1 < 0.0F) {
/*  88 */       f1 += 360.0F;
/*  89 */     } else if (f1 > 360.0F) {
/*  90 */       f1 -= 360.0F;
/*     */     } 
/*     */     
/*  93 */     return f1;
/*     */   }
/*     */   
/*     */   public double getX() {
/*  97 */     return this.posX;
/*     */   }
/*     */   
/*     */   public double getY() {
/* 101 */     return this.posY;
/*     */   }
/*     */   
/*     */   public double getZ() {
/* 105 */     return this.posZ;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityMoveHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */