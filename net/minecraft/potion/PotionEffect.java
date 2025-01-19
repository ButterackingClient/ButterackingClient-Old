/*     */ package net.minecraft.potion;
/*     */ 
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class PotionEffect {
/*   9 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   private int potionID;
/*     */ 
/*     */ 
/*     */   
/*     */   private int duration;
/*     */ 
/*     */ 
/*     */   
/*     */   private int amplifier;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSplashPotion;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAmbient;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isPotionDurationMax;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean showParticles;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionEffect(int id, int effectDuration) {
/*  43 */     this(id, effectDuration, 0);
/*     */   }
/*     */   
/*     */   public PotionEffect(int id, int effectDuration, int effectAmplifier) {
/*  47 */     this(id, effectDuration, effectAmplifier, false, true);
/*     */   }
/*     */   
/*     */   public PotionEffect(int id, int effectDuration, int effectAmplifier, boolean ambient, boolean showParticles) {
/*  51 */     this.potionID = id;
/*  52 */     this.duration = effectDuration;
/*  53 */     this.amplifier = effectAmplifier;
/*  54 */     this.isAmbient = ambient;
/*  55 */     this.showParticles = showParticles;
/*     */   }
/*     */   
/*     */   public PotionEffect(PotionEffect other) {
/*  59 */     this.potionID = other.potionID;
/*  60 */     this.duration = other.duration;
/*  61 */     this.amplifier = other.amplifier;
/*  62 */     this.isAmbient = other.isAmbient;
/*  63 */     this.showParticles = other.showParticles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void combine(PotionEffect other) {
/*  71 */     if (this.potionID != other.potionID) {
/*  72 */       LOGGER.warn("This method should only be called for matching effects!");
/*     */     }
/*     */     
/*  75 */     if (other.amplifier > this.amplifier) {
/*  76 */       this.amplifier = other.amplifier;
/*  77 */       this.duration = other.duration;
/*  78 */     } else if (other.amplifier == this.amplifier && this.duration < other.duration) {
/*  79 */       this.duration = other.duration;
/*  80 */     } else if (!other.isAmbient && this.isAmbient) {
/*  81 */       this.isAmbient = other.isAmbient;
/*     */     } 
/*     */     
/*  84 */     this.showParticles = other.showParticles;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPotionID() {
/*  91 */     return this.potionID;
/*     */   }
/*     */   
/*     */   public int getDuration() {
/*  95 */     return this.duration;
/*     */   }
/*     */   
/*     */   public int getAmplifier() {
/*  99 */     return this.amplifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSplashPotion(boolean splashPotion) {
/* 106 */     this.isSplashPotion = splashPotion;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsAmbient() {
/* 113 */     return this.isAmbient;
/*     */   }
/*     */   
/*     */   public boolean getIsShowParticles() {
/* 117 */     return this.showParticles;
/*     */   }
/*     */   
/*     */   public boolean onUpdate(EntityLivingBase entityIn) {
/* 121 */     if (this.duration > 0) {
/* 122 */       if (Potion.potionTypes[this.potionID].isReady(this.duration, this.amplifier)) {
/* 123 */         performEffect(entityIn);
/*     */       }
/*     */       
/* 126 */       deincrementDuration();
/*     */     } 
/*     */     
/* 129 */     return (this.duration > 0);
/*     */   }
/*     */   
/*     */   private int deincrementDuration() {
/* 133 */     return --this.duration;
/*     */   }
/*     */   
/*     */   public void performEffect(EntityLivingBase entityIn) {
/* 137 */     if (this.duration > 0) {
/* 138 */       Potion.potionTypes[this.potionID].performEffect(entityIn, this.amplifier);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getEffectName() {
/* 143 */     return Potion.potionTypes[this.potionID].getName();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 147 */     return this.potionID;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 151 */     String s = "";
/*     */     
/* 153 */     if (getAmplifier() > 0) {
/* 154 */       s = String.valueOf(getEffectName()) + " x " + (getAmplifier() + 1) + ", Duration: " + getDuration();
/*     */     } else {
/* 156 */       s = String.valueOf(getEffectName()) + ", Duration: " + getDuration();
/*     */     } 
/*     */     
/* 159 */     if (this.isSplashPotion) {
/* 160 */       s = String.valueOf(s) + ", Splash: true";
/*     */     }
/*     */     
/* 163 */     if (!this.showParticles) {
/* 164 */       s = String.valueOf(s) + ", Particles: false";
/*     */     }
/*     */     
/* 167 */     return Potion.potionTypes[this.potionID].isUsable() ? ("(" + s + ")") : s;
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 171 */     if (!(p_equals_1_ instanceof PotionEffect)) {
/* 172 */       return false;
/*     */     }
/* 174 */     PotionEffect potioneffect = (PotionEffect)p_equals_1_;
/* 175 */     return (this.potionID == potioneffect.potionID && this.amplifier == potioneffect.amplifier && this.duration == potioneffect.duration && this.isSplashPotion == potioneffect.isSplashPotion && this.isAmbient == potioneffect.isAmbient);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeCustomPotionEffectToNBT(NBTTagCompound nbt) {
/* 183 */     nbt.setByte("Id", (byte)getPotionID());
/* 184 */     nbt.setByte("Amplifier", (byte)getAmplifier());
/* 185 */     nbt.setInteger("Duration", getDuration());
/* 186 */     nbt.setBoolean("Ambient", getIsAmbient());
/* 187 */     nbt.setBoolean("ShowParticles", getIsShowParticles());
/* 188 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PotionEffect readCustomPotionEffectFromNBT(NBTTagCompound nbt) {
/* 195 */     int i = nbt.getByte("Id");
/*     */     
/* 197 */     if (i >= 0 && i < Potion.potionTypes.length && Potion.potionTypes[i] != null) {
/* 198 */       int j = nbt.getByte("Amplifier");
/* 199 */       int k = nbt.getInteger("Duration");
/* 200 */       boolean flag = nbt.getBoolean("Ambient");
/* 201 */       boolean flag1 = true;
/*     */       
/* 203 */       if (nbt.hasKey("ShowParticles", 1)) {
/* 204 */         flag1 = nbt.getBoolean("ShowParticles");
/*     */       }
/*     */       
/* 207 */       return new PotionEffect(i, k, j, flag, flag1);
/*     */     } 
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPotionDurationMax(boolean maxDuration) {
/* 217 */     this.isPotionDurationMax = maxDuration;
/*     */   }
/*     */   
/*     */   public boolean getIsPotionDurationMax() {
/* 221 */     return this.isPotionDurationMax;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\potion\PotionEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */