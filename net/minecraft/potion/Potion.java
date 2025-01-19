/*     */ package net.minecraft.potion;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Potion
/*     */ {
/*  26 */   public static final Potion[] potionTypes = new Potion[32];
/*  27 */   private static final Map<ResourceLocation, Potion> field_180150_I = Maps.newHashMap();
/*  28 */   public static final Potion field_180151_b = null;
/*  29 */   public static final Potion moveSpeed = (new Potion(1, new ResourceLocation("speed"), false, 8171462)).setPotionName("potion.moveSpeed").setIconIndex(0, 0).registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2);
/*  30 */   public static final Potion moveSlowdown = (new Potion(2, new ResourceLocation("slowness"), true, 5926017)).setPotionName("potion.moveSlowdown").setIconIndex(1, 0).registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448D, 2);
/*  31 */   public static final Potion digSpeed = (new Potion(3, new ResourceLocation("haste"), false, 14270531)).setPotionName("potion.digSpeed").setIconIndex(2, 0).setEffectiveness(1.5D);
/*  32 */   public static final Potion digSlowdown = (new Potion(4, new ResourceLocation("mining_fatigue"), true, 4866583)).setPotionName("potion.digSlowDown").setIconIndex(3, 0);
/*  33 */   public static final Potion damageBoost = (new PotionAttackDamage(5, new ResourceLocation("strength"), false, 9643043)).setPotionName("potion.damageBoost").setIconIndex(4, 0).registerPotionAttributeModifier(SharedMonsterAttributes.attackDamage, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2.5D, 2);
/*  34 */   public static final Potion heal = (new PotionHealth(6, new ResourceLocation("instant_health"), false, 16262179)).setPotionName("potion.heal");
/*  35 */   public static final Potion harm = (new PotionHealth(7, new ResourceLocation("instant_damage"), true, 4393481)).setPotionName("potion.harm");
/*  36 */   public static final Potion jump = (new Potion(8, new ResourceLocation("jump_boost"), false, 2293580)).setPotionName("potion.jump").setIconIndex(2, 1);
/*  37 */   public static final Potion confusion = (new Potion(9, new ResourceLocation("nausea"), true, 5578058)).setPotionName("potion.confusion").setIconIndex(3, 1).setEffectiveness(0.25D);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public static final Potion regeneration = (new Potion(10, new ResourceLocation("regeneration"), false, 13458603)).setPotionName("potion.regeneration").setIconIndex(7, 0).setEffectiveness(0.25D);
/*  43 */   public static final Potion resistance = (new Potion(11, new ResourceLocation("resistance"), false, 10044730)).setPotionName("potion.resistance").setIconIndex(6, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final Potion fireResistance = (new Potion(12, new ResourceLocation("fire_resistance"), false, 14981690)).setPotionName("potion.fireResistance").setIconIndex(7, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static final Potion waterBreathing = (new Potion(13, new ResourceLocation("water_breathing"), false, 3035801)).setPotionName("potion.waterBreathing").setIconIndex(0, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final Potion invisibility = (new Potion(14, new ResourceLocation("invisibility"), false, 8356754)).setPotionName("potion.invisibility").setIconIndex(0, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final Potion blindness = (new Potion(15, new ResourceLocation("blindness"), true, 2039587)).setPotionName("potion.blindness").setIconIndex(5, 1).setEffectiveness(0.25D);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final Potion nightVision = (new Potion(16, new ResourceLocation("night_vision"), false, 2039713)).setPotionName("potion.nightVision").setIconIndex(4, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final Potion hunger = (new Potion(17, new ResourceLocation("hunger"), true, 5797459)).setPotionName("potion.hunger").setIconIndex(1, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   public static final Potion weakness = (new PotionAttackDamage(18, new ResourceLocation("weakness"), true, 4738376)).setPotionName("potion.weakness").setIconIndex(5, 0).registerPotionAttributeModifier(SharedMonsterAttributes.attackDamage, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0D, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final Potion poison = (new Potion(19, new ResourceLocation("poison"), true, 5149489)).setPotionName("potion.poison").setIconIndex(6, 0).setEffectiveness(0.25D);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static final Potion wither = (new Potion(20, new ResourceLocation("wither"), true, 3484199)).setPotionName("potion.wither").setIconIndex(1, 2).setEffectiveness(0.25D);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   public static final Potion healthBoost = (new PotionHealthBoost(21, new ResourceLocation("health_boost"), false, 16284963)).setPotionName("potion.healthBoost").setIconIndex(2, 2).registerPotionAttributeModifier(SharedMonsterAttributes.maxHealth, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public static final Potion absorption = (new PotionAbsorption(22, new ResourceLocation("absorption"), false, 2445989)).setPotionName("potion.absorption").setIconIndex(2, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static final Potion saturation = (new PotionHealth(23, new ResourceLocation("saturation"), false, 16262179)).setPotionName("potion.saturation");
/* 104 */   public static final Potion field_180153_z = null;
/* 105 */   public static final Potion field_180147_A = null;
/* 106 */   public static final Potion field_180148_B = null;
/* 107 */   public static final Potion field_180149_C = null;
/* 108 */   public static final Potion field_180143_D = null;
/* 109 */   public static final Potion field_180144_E = null;
/* 110 */   public static final Potion field_180145_F = null;
/* 111 */   public static final Potion field_180146_G = null;
/*     */ 
/*     */   
/*     */   public final int id;
/*     */ 
/*     */   
/* 117 */   private final Map<IAttribute, AttributeModifier> attributeModifierMap = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isBadEffect;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int liquidColor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   private String name = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   private int statusIconIndex = -1;
/*     */   private double effectiveness;
/*     */   private boolean usable;
/*     */   
/*     */   protected Potion(int potionID, ResourceLocation location, boolean badEffect, int potionColor) {
/* 142 */     this.id = potionID;
/* 143 */     potionTypes[potionID] = this;
/* 144 */     field_180150_I.put(location, this);
/* 145 */     this.isBadEffect = badEffect;
/*     */     
/* 147 */     if (badEffect) {
/* 148 */       this.effectiveness = 0.5D;
/*     */     } else {
/* 150 */       this.effectiveness = 1.0D;
/*     */     } 
/*     */     
/* 153 */     this.liquidColor = potionColor;
/*     */   }
/*     */   
/*     */   public static Potion getPotionFromResourceLocation(String location) {
/* 157 */     return field_180150_I.get(new ResourceLocation(location));
/*     */   }
/*     */   
/*     */   public static Set<ResourceLocation> getPotionLocations() {
/* 161 */     return field_180150_I.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Potion setIconIndex(int p_76399_1_, int p_76399_2_) {
/* 168 */     this.statusIconIndex = p_76399_1_ + p_76399_2_ * 8;
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/* 176 */     return this.id;
/*     */   }
/*     */   
/*     */   public void performEffect(EntityLivingBase entityLivingBaseIn, int p_76394_2_) {
/* 180 */     if (this.id == regeneration.id) {
/* 181 */       if (entityLivingBaseIn.getHealth() < entityLivingBaseIn.getMaxHealth()) {
/* 182 */         entityLivingBaseIn.heal(1.0F);
/*     */       }
/* 184 */     } else if (this.id == poison.id) {
/* 185 */       if (entityLivingBaseIn.getHealth() > 1.0F) {
/* 186 */         entityLivingBaseIn.attackEntityFrom(DamageSource.magic, 1.0F);
/*     */       }
/* 188 */     } else if (this.id == wither.id) {
/* 189 */       entityLivingBaseIn.attackEntityFrom(DamageSource.wither, 1.0F);
/* 190 */     } else if (this.id == hunger.id && entityLivingBaseIn instanceof EntityPlayer) {
/* 191 */       ((EntityPlayer)entityLivingBaseIn).addExhaustion(0.025F * (p_76394_2_ + 1));
/* 192 */     } else if (this.id == saturation.id && entityLivingBaseIn instanceof EntityPlayer) {
/* 193 */       if (!entityLivingBaseIn.worldObj.isRemote) {
/* 194 */         ((EntityPlayer)entityLivingBaseIn).getFoodStats().addStats(p_76394_2_ + 1, 1.0F);
/*     */       }
/* 196 */     } else if ((this.id != heal.id || entityLivingBaseIn.isEntityUndead()) && (this.id != harm.id || !entityLivingBaseIn.isEntityUndead())) {
/* 197 */       if ((this.id == harm.id && !entityLivingBaseIn.isEntityUndead()) || (this.id == heal.id && entityLivingBaseIn.isEntityUndead())) {
/* 198 */         entityLivingBaseIn.attackEntityFrom(DamageSource.magic, (6 << p_76394_2_));
/*     */       }
/*     */     } else {
/* 201 */       entityLivingBaseIn.heal(Math.max(4 << p_76394_2_, 0));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void affectEntity(Entity p_180793_1_, Entity p_180793_2_, EntityLivingBase entityLivingBaseIn, int p_180793_4_, double p_180793_5_) {
/* 206 */     if ((this.id != heal.id || entityLivingBaseIn.isEntityUndead()) && (this.id != harm.id || !entityLivingBaseIn.isEntityUndead())) {
/* 207 */       if ((this.id == harm.id && !entityLivingBaseIn.isEntityUndead()) || (this.id == heal.id && entityLivingBaseIn.isEntityUndead())) {
/* 208 */         int j = (int)(p_180793_5_ * (6 << p_180793_4_) + 0.5D);
/*     */         
/* 210 */         if (p_180793_1_ == null) {
/* 211 */           entityLivingBaseIn.attackEntityFrom(DamageSource.magic, j);
/*     */         } else {
/* 213 */           entityLivingBaseIn.attackEntityFrom(DamageSource.causeIndirectMagicDamage(p_180793_1_, p_180793_2_), j);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 217 */       int i = (int)(p_180793_5_ * (4 << p_180793_4_) + 0.5D);
/* 218 */       entityLivingBaseIn.heal(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInstant() {
/* 226 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReady(int p_76397_1_, int p_76397_2_) {
/* 233 */     if (this.id == regeneration.id) {
/* 234 */       int k = 50 >> p_76397_2_;
/* 235 */       return (k > 0) ? ((p_76397_1_ % k == 0)) : true;
/* 236 */     }  if (this.id == poison.id) {
/* 237 */       int j = 25 >> p_76397_2_;
/* 238 */       return (j > 0) ? ((p_76397_1_ % j == 0)) : true;
/* 239 */     }  if (this.id == wither.id) {
/* 240 */       int i = 40 >> p_76397_2_;
/* 241 */       return (i > 0) ? ((p_76397_1_ % i == 0)) : true;
/*     */     } 
/* 243 */     return (this.id == hunger.id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Potion setPotionName(String nameIn) {
/* 251 */     this.name = nameIn;
/* 252 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 259 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasStatusIcon() {
/* 266 */     return (this.statusIconIndex >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStatusIconIndex() {
/* 273 */     return this.statusIconIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBadEffect() {
/* 280 */     return this.isBadEffect;
/*     */   }
/*     */   
/*     */   public static String getDurationString(PotionEffect effect) {
/* 284 */     if (effect.getIsPotionDurationMax()) {
/* 285 */       return "**:**";
/*     */     }
/* 287 */     int i = effect.getDuration();
/* 288 */     return StringUtils.ticksToElapsedTime(i);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Potion setEffectiveness(double effectivenessIn) {
/* 293 */     this.effectiveness = effectivenessIn;
/* 294 */     return this;
/*     */   }
/*     */   
/*     */   public double getEffectiveness() {
/* 298 */     return this.effectiveness;
/*     */   }
/*     */   
/*     */   public boolean isUsable() {
/* 302 */     return this.usable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLiquidColor() {
/* 309 */     return this.liquidColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Potion registerPotionAttributeModifier(IAttribute p_111184_1_, String p_111184_2_, double p_111184_3_, int p_111184_5_) {
/* 316 */     AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(p_111184_2_), getName(), p_111184_3_, p_111184_5_);
/* 317 */     this.attributeModifierMap.put(p_111184_1_, attributemodifier);
/* 318 */     return this;
/*     */   }
/*     */   
/*     */   public Map<IAttribute, AttributeModifier> getAttributeModifierMap() {
/* 322 */     return this.attributeModifierMap;
/*     */   }
/*     */   
/*     */   public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, BaseAttributeMap p_111187_2_, int amplifier) {
/* 326 */     for (Map.Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
/* 327 */       IAttributeInstance iattributeinstance = p_111187_2_.getAttributeInstance(entry.getKey());
/*     */       
/* 329 */       if (iattributeinstance != null) {
/* 330 */         iattributeinstance.removeModifier(entry.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, BaseAttributeMap p_111185_2_, int amplifier) {
/* 336 */     for (Map.Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet()) {
/* 337 */       IAttributeInstance iattributeinstance = p_111185_2_.getAttributeInstance(entry.getKey());
/*     */       
/* 339 */       if (iattributeinstance != null) {
/* 340 */         AttributeModifier attributemodifier = entry.getValue();
/* 341 */         iattributeinstance.removeModifier(attributemodifier);
/* 342 */         iattributeinstance.applyModifier(new AttributeModifier(attributemodifier.getID(), String.valueOf(getName()) + " " + amplifier, getAttributeModifierAmount(amplifier, attributemodifier), attributemodifier.getOperation()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getAttributeModifierAmount(int p_111183_1_, AttributeModifier modifier) {
/* 348 */     return modifier.getAmount() * (p_111183_1_ + 1);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\potion\Potion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */