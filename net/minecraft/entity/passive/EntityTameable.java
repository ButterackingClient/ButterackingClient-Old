/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.entity.ai.EntityAISit;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.server.management.PreYggdrasilConverter;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityTameable extends EntityAnimal implements IEntityOwnable {
/*  17 */   protected EntityAISit aiSit = new EntityAISit(this);
/*     */   
/*     */   public EntityTameable(World worldIn) {
/*  20 */     super(worldIn);
/*  21 */     setupTamedAI();
/*     */   }
/*     */   
/*     */   protected void entityInit() {
/*  25 */     super.entityInit();
/*  26 */     this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
/*  27 */     this.dataWatcher.addObject(17, "");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  34 */     super.writeEntityToNBT(tagCompound);
/*     */     
/*  36 */     if (getOwnerId() == null) {
/*  37 */       tagCompound.setString("OwnerUUID", "");
/*     */     } else {
/*  39 */       tagCompound.setString("OwnerUUID", getOwnerId());
/*     */     } 
/*     */     
/*  42 */     tagCompound.setBoolean("Sitting", isSitting());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  49 */     super.readEntityFromNBT(tagCompund);
/*  50 */     String s = "";
/*     */     
/*  52 */     if (tagCompund.hasKey("OwnerUUID", 8)) {
/*  53 */       s = tagCompund.getString("OwnerUUID");
/*     */     } else {
/*  55 */       String s1 = tagCompund.getString("Owner");
/*  56 */       s = PreYggdrasilConverter.getStringUUIDFromName(s1);
/*     */     } 
/*     */     
/*  59 */     if (s.length() > 0) {
/*  60 */       setOwnerId(s);
/*  61 */       setTamed(true);
/*     */     } 
/*     */     
/*  64 */     this.aiSit.setSitting(tagCompund.getBoolean("Sitting"));
/*  65 */     setSitting(tagCompund.getBoolean("Sitting"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void playTameEffect(boolean play) {
/*  72 */     EnumParticleTypes enumparticletypes = EnumParticleTypes.HEART;
/*     */     
/*  74 */     if (!play) {
/*  75 */       enumparticletypes = EnumParticleTypes.SMOKE_NORMAL;
/*     */     }
/*     */     
/*  78 */     for (int i = 0; i < 7; i++) {
/*  79 */       double d0 = this.rand.nextGaussian() * 0.02D;
/*  80 */       double d1 = this.rand.nextGaussian() * 0.02D;
/*  81 */       double d2 = this.rand.nextGaussian() * 0.02D;
/*  82 */       this.worldObj.spawnParticle(enumparticletypes, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/*  87 */     if (id == 7) {
/*  88 */       playTameEffect(true);
/*  89 */     } else if (id == 6) {
/*  90 */       playTameEffect(false);
/*     */     } else {
/*  92 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isTamed() {
/*  97 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x4) != 0);
/*     */   }
/*     */   
/*     */   public void setTamed(boolean tamed) {
/* 101 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 103 */     if (tamed) {
/* 104 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x4)));
/*     */     } else {
/* 106 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFB)));
/*     */     } 
/*     */     
/* 109 */     setupTamedAI();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setupTamedAI() {}
/*     */   
/*     */   public boolean isSitting() {
/* 116 */     return ((this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setSitting(boolean sitting) {
/* 120 */     byte b0 = this.dataWatcher.getWatchableObjectByte(16);
/*     */     
/* 122 */     if (sitting) {
/* 123 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     } else {
/* 125 */       this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getOwnerId() {
/* 130 */     return this.dataWatcher.getWatchableObjectString(17);
/*     */   }
/*     */   
/*     */   public void setOwnerId(String ownerUuid) {
/* 134 */     this.dataWatcher.updateObject(17, ownerUuid);
/*     */   }
/*     */   
/*     */   public EntityLivingBase getOwner() {
/*     */     try {
/* 139 */       UUID uuid = UUID.fromString(getOwnerId());
/* 140 */       return (uuid == null) ? null : (EntityLivingBase)this.worldObj.getPlayerEntityByUUID(uuid);
/* 141 */     } catch (IllegalArgumentException var2) {
/* 142 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isOwner(EntityLivingBase entityIn) {
/* 147 */     return (entityIn == getOwner());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityAISit getAISit() {
/* 154 */     return this.aiSit;
/*     */   }
/*     */   
/*     */   public boolean shouldAttackEntity(EntityLivingBase p_142018_1_, EntityLivingBase p_142018_2_) {
/* 158 */     return true;
/*     */   }
/*     */   
/*     */   public Team getTeam() {
/* 162 */     if (isTamed()) {
/* 163 */       EntityLivingBase entitylivingbase = getOwner();
/*     */       
/* 165 */       if (entitylivingbase != null) {
/* 166 */         return entitylivingbase.getTeam();
/*     */       }
/*     */     } 
/*     */     
/* 170 */     return super.getTeam();
/*     */   }
/*     */   
/*     */   public boolean isOnSameTeam(EntityLivingBase otherEntity) {
/* 174 */     if (isTamed()) {
/* 175 */       EntityLivingBase entitylivingbase = getOwner();
/*     */       
/* 177 */       if (otherEntity == entitylivingbase) {
/* 178 */         return true;
/*     */       }
/*     */       
/* 181 */       if (entitylivingbase != null) {
/* 182 */         return entitylivingbase.isOnSameTeam(otherEntity);
/*     */       }
/*     */     } 
/*     */     
/* 186 */     return super.isOnSameTeam(otherEntity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 193 */     if (!this.worldObj.isRemote && this.worldObj.getGameRules().getBoolean("showDeathMessages") && hasCustomName() && getOwner() instanceof EntityPlayerMP) {
/* 194 */       ((EntityPlayerMP)getOwner()).addChatMessage(getCombatTracker().getDeathMessage());
/*     */     }
/*     */     
/* 197 */     super.onDeath(cause);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\passive\EntityTameable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */