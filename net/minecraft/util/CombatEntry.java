/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ public class CombatEntry
/*    */ {
/*    */   private final DamageSource damageSrc;
/*    */   private final int field_94567_b;
/*    */   private final float damage;
/*    */   private final float health;
/*    */   private final String field_94566_e;
/*    */   private final float fallDistance;
/*    */   
/*    */   public CombatEntry(DamageSource damageSrcIn, int p_i1564_2_, float healthAmount, float damageAmount, String p_i1564_5_, float fallDistanceIn) {
/* 14 */     this.damageSrc = damageSrcIn;
/* 15 */     this.field_94567_b = p_i1564_2_;
/* 16 */     this.damage = damageAmount;
/* 17 */     this.health = healthAmount;
/* 18 */     this.field_94566_e = p_i1564_5_;
/* 19 */     this.fallDistance = fallDistanceIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DamageSource getDamageSrc() {
/* 26 */     return this.damageSrc;
/*    */   }
/*    */   
/*    */   public float func_94563_c() {
/* 30 */     return this.damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isLivingDamageSrc() {
/* 37 */     return this.damageSrc.getEntity() instanceof net.minecraft.entity.EntityLivingBase;
/*    */   }
/*    */   
/*    */   public String func_94562_g() {
/* 41 */     return this.field_94566_e;
/*    */   }
/*    */   
/*    */   public IChatComponent getDamageSrcDisplayName() {
/* 45 */     return (getDamageSrc().getEntity() == null) ? null : getDamageSrc().getEntity().getDisplayName();
/*    */   }
/*    */   
/*    */   public float getDamageAmount() {
/* 49 */     return (this.damageSrc == DamageSource.outOfWorld) ? Float.MAX_VALUE : this.fallDistance;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\CombatEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */