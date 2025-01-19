/*     */ package net.minecraft.util;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityFireball;
/*     */ import net.minecraft.world.Explosion;
/*     */ 
/*     */ public class DamageSource {
/*  11 */   public static DamageSource inFire = (new DamageSource("inFire")).setFireDamage();
/*  12 */   public static DamageSource lightningBolt = new DamageSource("lightningBolt");
/*  13 */   public static DamageSource onFire = (new DamageSource("onFire")).setDamageBypassesArmor().setFireDamage();
/*  14 */   public static DamageSource lava = (new DamageSource("lava")).setFireDamage();
/*  15 */   public static DamageSource inWall = (new DamageSource("inWall")).setDamageBypassesArmor();
/*  16 */   public static DamageSource drown = (new DamageSource("drown")).setDamageBypassesArmor();
/*  17 */   public static DamageSource starve = (new DamageSource("starve")).setDamageBypassesArmor().setDamageIsAbsolute();
/*  18 */   public static DamageSource cactus = new DamageSource("cactus");
/*  19 */   public static DamageSource fall = (new DamageSource("fall")).setDamageBypassesArmor();
/*  20 */   public static DamageSource outOfWorld = (new DamageSource("outOfWorld")).setDamageBypassesArmor().setDamageAllowedInCreativeMode();
/*  21 */   public static DamageSource generic = (new DamageSource("generic")).setDamageBypassesArmor();
/*  22 */   public static DamageSource magic = (new DamageSource("magic")).setDamageBypassesArmor().setMagicDamage();
/*  23 */   public static DamageSource wither = (new DamageSource("wither")).setDamageBypassesArmor();
/*  24 */   public static DamageSource anvil = new DamageSource("anvil");
/*  25 */   public static DamageSource fallingBlock = new DamageSource("fallingBlock");
/*     */ 
/*     */   
/*     */   private boolean isUnblockable;
/*     */ 
/*     */   
/*     */   private boolean isDamageAllowedInCreativeMode;
/*     */ 
/*     */   
/*     */   private boolean damageIsAbsolute;
/*     */ 
/*     */   
/*  37 */   private float hungerDamage = 0.3F;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean fireDamage;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean projectile;
/*     */ 
/*     */   
/*     */   private boolean difficultyScaled;
/*     */ 
/*     */   
/*     */   private boolean magicDamage;
/*     */ 
/*     */   
/*     */   private boolean explosion;
/*     */ 
/*     */   
/*     */   public String damageType;
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causeMobDamage(EntityLivingBase mob) {
/*  62 */     return new EntityDamageSource("mob", (Entity)mob);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causePlayerDamage(EntityPlayer player) {
/*  69 */     return new EntityDamageSource("player", (Entity)player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causeArrowDamage(EntityArrow arrow, Entity indirectEntityIn) {
/*  78 */     return (new EntityDamageSourceIndirect("arrow", (Entity)arrow, indirectEntityIn)).setProjectile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causeFireballDamage(EntityFireball fireball, Entity indirectEntityIn) {
/*  87 */     return (indirectEntityIn == null) ? (new EntityDamageSourceIndirect("onFire", (Entity)fireball, (Entity)fireball)).setFireDamage().setProjectile() : (new EntityDamageSourceIndirect("fireball", (Entity)fireball, indirectEntityIn)).setFireDamage().setProjectile();
/*     */   }
/*     */   
/*     */   public static DamageSource causeThrownDamage(Entity source, Entity indirectEntityIn) {
/*  91 */     return (new EntityDamageSourceIndirect("thrown", source, indirectEntityIn)).setProjectile();
/*     */   }
/*     */   
/*     */   public static DamageSource causeIndirectMagicDamage(Entity source, Entity indirectEntityIn) {
/*  95 */     return (new EntityDamageSourceIndirect("indirectMagic", source, indirectEntityIn)).setDamageBypassesArmor().setMagicDamage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DamageSource causeThornsDamage(Entity source) {
/* 104 */     return (new EntityDamageSource("thorns", source)).setIsThornsDamage().setMagicDamage();
/*     */   }
/*     */   
/*     */   public static DamageSource setExplosionSource(Explosion explosionIn) {
/* 108 */     return (explosionIn != null && explosionIn.getExplosivePlacedBy() != null) ? (new EntityDamageSource("explosion.player", (Entity)explosionIn.getExplosivePlacedBy())).setDifficultyScaled().setExplosion() : (new DamageSource("explosion")).setDifficultyScaled().setExplosion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProjectile() {
/* 115 */     return this.projectile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageSource setProjectile() {
/* 122 */     this.projectile = true;
/* 123 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isExplosion() {
/* 127 */     return this.explosion;
/*     */   }
/*     */   
/*     */   public DamageSource setExplosion() {
/* 131 */     this.explosion = true;
/* 132 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isUnblockable() {
/* 136 */     return this.isUnblockable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHungerDamage() {
/* 143 */     return this.hungerDamage;
/*     */   }
/*     */   
/*     */   public boolean canHarmInCreative() {
/* 147 */     return this.isDamageAllowedInCreativeMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDamageAbsolute() {
/* 154 */     return this.damageIsAbsolute;
/*     */   }
/*     */   
/*     */   protected DamageSource(String damageTypeIn) {
/* 158 */     this.damageType = damageTypeIn;
/*     */   }
/*     */   
/*     */   public Entity getSourceOfDamage() {
/* 162 */     return getEntity();
/*     */   }
/*     */   
/*     */   public Entity getEntity() {
/* 166 */     return null;
/*     */   }
/*     */   
/*     */   protected DamageSource setDamageBypassesArmor() {
/* 170 */     this.isUnblockable = true;
/* 171 */     this.hungerDamage = 0.0F;
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   protected DamageSource setDamageAllowedInCreativeMode() {
/* 176 */     this.isDamageAllowedInCreativeMode = true;
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DamageSource setDamageIsAbsolute() {
/* 185 */     this.damageIsAbsolute = true;
/* 186 */     this.hungerDamage = 0.0F;
/* 187 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DamageSource setFireDamage() {
/* 194 */     this.fireDamage = true;
/* 195 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
/* 204 */     EntityLivingBase entitylivingbase = entityLivingBaseIn.getAttackingEntity();
/* 205 */     String s = "death.attack." + this.damageType;
/* 206 */     String s1 = String.valueOf(s) + ".player";
/* 207 */     return (entitylivingbase != null && StatCollector.canTranslate(s1)) ? new ChatComponentTranslation(s1, new Object[] { entityLivingBaseIn.getDisplayName(), entitylivingbase.getDisplayName() }) : new ChatComponentTranslation(s, new Object[] { entityLivingBaseIn.getDisplayName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFireDamage() {
/* 214 */     return this.fireDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDamageType() {
/* 221 */     return this.damageType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageSource setDifficultyScaled() {
/* 228 */     this.difficultyScaled = true;
/* 229 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDifficultyScaled() {
/* 236 */     return this.difficultyScaled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMagicDamage() {
/* 243 */     return this.magicDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageSource setMagicDamage() {
/* 250 */     this.magicDamage = true;
/* 251 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isCreativePlayer() {
/* 255 */     Entity entity = getEntity();
/* 256 */     return (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\DamageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */