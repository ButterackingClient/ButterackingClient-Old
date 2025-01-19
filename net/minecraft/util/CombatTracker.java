/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ public class CombatTracker
/*     */ {
/*  15 */   private final List<CombatEntry> combatEntries = Lists.newArrayList();
/*     */   
/*     */   private final EntityLivingBase fighter;
/*     */   
/*     */   private int field_94555_c;
/*     */   
/*     */   private int field_152775_d;
/*     */   
/*     */   private int field_152776_e;
/*     */   private boolean field_94552_d;
/*     */   private boolean field_94553_e;
/*     */   private String field_94551_f;
/*     */   
/*     */   public CombatTracker(EntityLivingBase fighterIn) {
/*  29 */     this.fighter = fighterIn;
/*     */   }
/*     */   
/*     */   public void func_94545_a() {
/*  33 */     func_94542_g();
/*     */     
/*  35 */     if (this.fighter.isOnLadder()) {
/*  36 */       Block block = this.fighter.worldObj.getBlockState(new BlockPos(this.fighter.posX, (this.fighter.getEntityBoundingBox()).minY, this.fighter.posZ)).getBlock();
/*     */       
/*  38 */       if (block == Blocks.ladder) {
/*  39 */         this.field_94551_f = "ladder";
/*  40 */       } else if (block == Blocks.vine) {
/*  41 */         this.field_94551_f = "vines";
/*     */       } 
/*  43 */     } else if (this.fighter.isInWater()) {
/*  44 */       this.field_94551_f = "water";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trackDamage(DamageSource damageSrc, float healthIn, float damageAmount) {
/*  52 */     reset();
/*  53 */     func_94545_a();
/*  54 */     CombatEntry combatentry = new CombatEntry(damageSrc, this.fighter.ticksExisted, healthIn, damageAmount, this.field_94551_f, this.fighter.fallDistance);
/*  55 */     this.combatEntries.add(combatentry);
/*  56 */     this.field_94555_c = this.fighter.ticksExisted;
/*  57 */     this.field_94553_e = true;
/*     */     
/*  59 */     if (combatentry.isLivingDamageSrc() && !this.field_94552_d && this.fighter.isEntityAlive()) {
/*  60 */       this.field_94552_d = true;
/*  61 */       this.field_152775_d = this.fighter.ticksExisted;
/*  62 */       this.field_152776_e = this.field_152775_d;
/*  63 */       this.fighter.sendEnterCombat();
/*     */     } 
/*     */   }
/*     */   public IChatComponent getDeathMessage() {
/*     */     IChatComponent ichatcomponent;
/*  68 */     if (this.combatEntries.size() == 0) {
/*  69 */       return new ChatComponentTranslation("death.attack.generic", new Object[] { this.fighter.getDisplayName() });
/*     */     }
/*  71 */     CombatEntry combatentry = func_94544_f();
/*  72 */     CombatEntry combatentry1 = this.combatEntries.get(this.combatEntries.size() - 1);
/*  73 */     IChatComponent ichatcomponent1 = combatentry1.getDamageSrcDisplayName();
/*  74 */     Entity entity = combatentry1.getDamageSrc().getEntity();
/*     */ 
/*     */     
/*  77 */     if (combatentry != null && combatentry1.getDamageSrc() == DamageSource.fall) {
/*  78 */       IChatComponent ichatcomponent2 = combatentry.getDamageSrcDisplayName();
/*     */       
/*  80 */       if (combatentry.getDamageSrc() != DamageSource.fall && combatentry.getDamageSrc() != DamageSource.outOfWorld) {
/*  81 */         if (ichatcomponent2 != null && (ichatcomponent1 == null || !ichatcomponent2.equals(ichatcomponent1))) {
/*  82 */           Entity entity1 = combatentry.getDamageSrc().getEntity();
/*  83 */           ItemStack itemstack1 = (entity1 instanceof EntityLivingBase) ? ((EntityLivingBase)entity1).getHeldItem() : null;
/*     */           
/*  85 */           if (itemstack1 != null && itemstack1.hasDisplayName()) {
/*  86 */             ichatcomponent = new ChatComponentTranslation("death.fell.assist.item", new Object[] { this.fighter.getDisplayName(), ichatcomponent2, itemstack1.getChatComponent() });
/*     */           } else {
/*  88 */             ichatcomponent = new ChatComponentTranslation("death.fell.assist", new Object[] { this.fighter.getDisplayName(), ichatcomponent2 });
/*     */           } 
/*  90 */         } else if (ichatcomponent1 != null) {
/*  91 */           ItemStack itemstack = (entity instanceof EntityLivingBase) ? ((EntityLivingBase)entity).getHeldItem() : null;
/*     */           
/*  93 */           if (itemstack != null && itemstack.hasDisplayName()) {
/*  94 */             ichatcomponent = new ChatComponentTranslation("death.fell.finish.item", new Object[] { this.fighter.getDisplayName(), ichatcomponent1, itemstack.getChatComponent() });
/*     */           } else {
/*  96 */             ichatcomponent = new ChatComponentTranslation("death.fell.finish", new Object[] { this.fighter.getDisplayName(), ichatcomponent1 });
/*     */           } 
/*     */         } else {
/*  99 */           ichatcomponent = new ChatComponentTranslation("death.fell.killer", new Object[] { this.fighter.getDisplayName() });
/*     */         } 
/*     */       } else {
/* 102 */         ichatcomponent = new ChatComponentTranslation("death.fell.accident." + func_94548_b(combatentry), new Object[] { this.fighter.getDisplayName() });
/*     */       } 
/*     */     } else {
/* 105 */       ichatcomponent = combatentry1.getDamageSrc().getDeathMessage(this.fighter);
/*     */     } 
/*     */     
/* 108 */     return ichatcomponent;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLivingBase func_94550_c() {
/* 113 */     EntityLivingBase entitylivingbase = null;
/* 114 */     EntityPlayer entityplayer = null;
/* 115 */     float f = 0.0F;
/* 116 */     float f1 = 0.0F;
/*     */     
/* 118 */     for (CombatEntry combatentry : this.combatEntries) {
/* 119 */       if (combatentry.getDamageSrc().getEntity() instanceof EntityPlayer && (entityplayer == null || combatentry.func_94563_c() > f1)) {
/* 120 */         f1 = combatentry.func_94563_c();
/* 121 */         entityplayer = (EntityPlayer)combatentry.getDamageSrc().getEntity();
/*     */       } 
/*     */       
/* 124 */       if (combatentry.getDamageSrc().getEntity() instanceof EntityLivingBase && (entitylivingbase == null || combatentry.func_94563_c() > f)) {
/* 125 */         f = combatentry.func_94563_c();
/* 126 */         entitylivingbase = (EntityLivingBase)combatentry.getDamageSrc().getEntity();
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     if (entityplayer != null && f1 >= f / 3.0F) {
/* 131 */       return (EntityLivingBase)entityplayer;
/*     */     }
/* 133 */     return entitylivingbase;
/*     */   }
/*     */ 
/*     */   
/*     */   private CombatEntry func_94544_f() {
/* 138 */     CombatEntry combatentry = null;
/* 139 */     CombatEntry combatentry1 = null;
/* 140 */     int i = 0;
/* 141 */     float f = 0.0F;
/*     */     
/* 143 */     for (int j = 0; j < this.combatEntries.size(); j++) {
/* 144 */       CombatEntry combatentry2 = this.combatEntries.get(j);
/* 145 */       CombatEntry combatentry3 = (j > 0) ? this.combatEntries.get(j - 1) : null;
/*     */       
/* 147 */       if ((combatentry2.getDamageSrc() == DamageSource.fall || combatentry2.getDamageSrc() == DamageSource.outOfWorld) && combatentry2.getDamageAmount() > 0.0F && (combatentry == null || combatentry2.getDamageAmount() > f)) {
/* 148 */         if (j > 0) {
/* 149 */           combatentry = combatentry3;
/*     */         } else {
/* 151 */           combatentry = combatentry2;
/*     */         } 
/*     */         
/* 154 */         f = combatentry2.getDamageAmount();
/*     */       } 
/*     */       
/* 157 */       if (combatentry2.func_94562_g() != null && (combatentry1 == null || combatentry2.func_94563_c() > i)) {
/* 158 */         combatentry1 = combatentry2;
/*     */       }
/*     */     } 
/*     */     
/* 162 */     if (f > 5.0F && combatentry != null)
/* 163 */       return combatentry; 
/* 164 */     if (i > 5 && combatentry1 != null) {
/* 165 */       return combatentry1;
/*     */     }
/* 167 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private String func_94548_b(CombatEntry p_94548_1_) {
/* 172 */     return (p_94548_1_.func_94562_g() == null) ? "generic" : p_94548_1_.func_94562_g();
/*     */   }
/*     */   
/*     */   public int func_180134_f() {
/* 176 */     return this.field_94552_d ? (this.fighter.ticksExisted - this.field_152775_d) : (this.field_152776_e - this.field_152775_d);
/*     */   }
/*     */   
/*     */   private void func_94542_g() {
/* 180 */     this.field_94551_f = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 187 */     int i = this.field_94552_d ? 300 : 100;
/*     */     
/* 189 */     if (this.field_94553_e && (!this.fighter.isEntityAlive() || this.fighter.ticksExisted - this.field_94555_c > i)) {
/* 190 */       boolean flag = this.field_94552_d;
/* 191 */       this.field_94553_e = false;
/* 192 */       this.field_94552_d = false;
/* 193 */       this.field_152776_e = this.fighter.ticksExisted;
/*     */       
/* 195 */       if (flag) {
/* 196 */         this.fighter.sendEndCombat();
/*     */       }
/*     */       
/* 199 */       this.combatEntries.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityLivingBase getFighter() {
/* 207 */     return this.fighter;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\CombatTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */