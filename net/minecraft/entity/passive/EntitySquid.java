/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
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
/*     */ public class EntitySquid
/*     */   extends EntityWaterMob
/*     */ {
/*     */   public float squidPitch;
/*     */   public float prevSquidPitch;
/*     */   public float squidYaw;
/*     */   public float prevSquidYaw;
/*     */   public float squidRotation;
/*     */   public float prevSquidRotation;
/*     */   public float tentacleAngle;
/*     */   public float lastTentacleAngle;
/*     */   private float randomMotionSpeed;
/*     */   private float rotationVelocity;
/*     */   private float field_70871_bB;
/*     */   private float randomMotionVecX;
/*     */   private float randomMotionVecY;
/*     */   private float randomMotionVecZ;
/*     */   
/*     */   public EntitySquid(World worldIn) {
/*  50 */     super(worldIn);
/*  51 */     setSize(0.95F, 0.95F);
/*  52 */     this.rand.setSeed((1 + getEntityId()));
/*  53 */     this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
/*  54 */     this.tasks.addTask(0, new AIMoveRandom(this));
/*     */   }
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  58 */     super.applyEntityAttributes();
/*  59 */     getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
/*     */   }
/*     */   
/*     */   public float getEyeHeight() {
/*  63 */     return this.height * 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getLivingSound() {
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHurtSound() {
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDeathSound() {
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/*  91 */     return 0.4F;
/*     */   }
/*     */   
/*     */   protected Item getDropItem() {
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
/* 114 */     int i = this.rand.nextInt(3 + lootingModifier) + 1;
/*     */     
/* 116 */     for (int j = 0; j < i; j++) {
/* 117 */       entityDropItem(new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()), 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInWater() {
/* 126 */     return this.worldObj.handleMaterialAcceleration(getEntityBoundingBox().expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, (Entity)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 134 */     super.onLivingUpdate();
/* 135 */     this.prevSquidPitch = this.squidPitch;
/* 136 */     this.prevSquidYaw = this.squidYaw;
/* 137 */     this.prevSquidRotation = this.squidRotation;
/* 138 */     this.lastTentacleAngle = this.tentacleAngle;
/* 139 */     this.squidRotation += this.rotationVelocity;
/*     */     
/* 141 */     if (this.squidRotation > 6.283185307179586D) {
/* 142 */       if (this.worldObj.isRemote) {
/* 143 */         this.squidRotation = 6.2831855F;
/*     */       } else {
/* 145 */         this.squidRotation = (float)(this.squidRotation - 6.283185307179586D);
/*     */         
/* 147 */         if (this.rand.nextInt(10) == 0) {
/* 148 */           this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
/*     */         }
/*     */         
/* 151 */         this.worldObj.setEntityState((Entity)this, (byte)19);
/*     */       } 
/*     */     }
/*     */     
/* 155 */     if (this.inWater) {
/* 156 */       if (this.squidRotation < 3.1415927F) {
/* 157 */         float f = this.squidRotation / 3.1415927F;
/* 158 */         this.tentacleAngle = MathHelper.sin(f * f * 3.1415927F) * 3.1415927F * 0.25F;
/*     */         
/* 160 */         if (f > 0.75D) {
/* 161 */           this.randomMotionSpeed = 1.0F;
/* 162 */           this.field_70871_bB = 1.0F;
/*     */         } else {
/* 164 */           this.field_70871_bB *= 0.8F;
/*     */         } 
/*     */       } else {
/* 167 */         this.tentacleAngle = 0.0F;
/* 168 */         this.randomMotionSpeed *= 0.9F;
/* 169 */         this.field_70871_bB *= 0.99F;
/*     */       } 
/*     */       
/* 172 */       if (!this.worldObj.isRemote) {
/* 173 */         this.motionX = (this.randomMotionVecX * this.randomMotionSpeed);
/* 174 */         this.motionY = (this.randomMotionVecY * this.randomMotionSpeed);
/* 175 */         this.motionZ = (this.randomMotionVecZ * this.randomMotionSpeed);
/*     */       } 
/*     */       
/* 178 */       float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 179 */       this.renderYawOffset += (-((float)MathHelper.atan2(this.motionX, this.motionZ)) * 180.0F / 3.1415927F - this.renderYawOffset) * 0.1F;
/* 180 */       this.rotationYaw = this.renderYawOffset;
/* 181 */       this.squidYaw = (float)(this.squidYaw + Math.PI * this.field_70871_bB * 1.5D);
/* 182 */       this.squidPitch += (-((float)MathHelper.atan2(f1, this.motionY)) * 180.0F / 3.1415927F - this.squidPitch) * 0.1F;
/*     */     } else {
/* 184 */       this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * 3.1415927F * 0.25F;
/*     */       
/* 186 */       if (!this.worldObj.isRemote) {
/* 187 */         this.motionX = 0.0D;
/* 188 */         this.motionY -= 0.08D;
/* 189 */         this.motionY *= 0.9800000190734863D;
/* 190 */         this.motionZ = 0.0D;
/*     */       } 
/*     */       
/* 193 */       this.squidPitch = (float)(this.squidPitch + (-90.0F - this.squidPitch) * 0.02D);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntityWithHeading(float strafe, float forward) {
/* 201 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 208 */     return (this.posY > 45.0D && this.posY < this.worldObj.getSeaLevel() && super.getCanSpawnHere());
/*     */   }
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 212 */     if (id == 19) {
/* 213 */       this.squidRotation = 0.0F;
/*     */     } else {
/* 215 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_175568_b(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn) {
/* 220 */     this.randomMotionVecX = randomMotionVecXIn;
/* 221 */     this.randomMotionVecY = randomMotionVecYIn;
/* 222 */     this.randomMotionVecZ = randomMotionVecZIn;
/*     */   }
/*     */   
/*     */   public boolean func_175567_n() {
/* 226 */     return !(this.randomMotionVecX == 0.0F && this.randomMotionVecY == 0.0F && this.randomMotionVecZ == 0.0F);
/*     */   }
/*     */   
/*     */   static class AIMoveRandom extends EntityAIBase {
/*     */     private EntitySquid squid;
/*     */     
/*     */     public AIMoveRandom(EntitySquid p_i45859_1_) {
/* 233 */       this.squid = p_i45859_1_;
/*     */     }
/*     */     
/*     */     public boolean shouldExecute() {
/* 237 */       return true;
/*     */     }
/*     */     
/*     */     public void updateTask() {
/* 241 */       int i = this.squid.getAge();
/*     */       
/* 243 */       if (i > 100) {
/* 244 */         this.squid.func_175568_b(0.0F, 0.0F, 0.0F);
/* 245 */       } else if (this.squid.getRNG().nextInt(50) == 0 || !this.squid.inWater || !this.squid.func_175567_n()) {
/* 246 */         float f = this.squid.getRNG().nextFloat() * 3.1415927F * 2.0F;
/* 247 */         float f1 = MathHelper.cos(f) * 0.2F;
/* 248 */         float f2 = -0.1F + this.squid.getRNG().nextFloat() * 0.2F;
/* 249 */         float f3 = MathHelper.sin(f) * 0.2F;
/* 250 */         this.squid.func_175568_b(f1, f2, f3);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntitySquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */