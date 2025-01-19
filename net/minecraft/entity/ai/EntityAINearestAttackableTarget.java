/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAINearestAttackableTarget<T extends EntityLivingBase>
/*     */   extends EntityAITarget
/*     */ {
/*     */   protected final Class<T> targetClass;
/*     */   private final int targetChance;
/*     */   protected final Sorter theNearestAttackableTargetSorter;
/*     */   protected Predicate<? super T> targetEntitySelector;
/*     */   protected EntityLivingBase targetEntity;
/*     */   
/*     */   public EntityAINearestAttackableTarget(EntityCreature creature, Class<T> classTarget, boolean checkSight) {
/*  28 */     this(creature, classTarget, checkSight, false);
/*     */   }
/*     */   
/*     */   public EntityAINearestAttackableTarget(EntityCreature creature, Class<T> classTarget, boolean checkSight, boolean onlyNearby) {
/*  32 */     this(creature, classTarget, 10, checkSight, onlyNearby, (Predicate<? super T>)null);
/*     */   }
/*     */   
/*     */   public EntityAINearestAttackableTarget(EntityCreature creature, Class<T> classTarget, int chance, boolean checkSight, boolean onlyNearby, final Predicate<? super T> targetSelector) {
/*  36 */     super(creature, checkSight, onlyNearby);
/*  37 */     this.targetClass = classTarget;
/*  38 */     this.targetChance = chance;
/*  39 */     this.theNearestAttackableTargetSorter = new Sorter((Entity)creature);
/*  40 */     setMutexBits(1);
/*  41 */     this.targetEntitySelector = new Predicate<T>() {
/*     */         public boolean apply(T p_apply_1_) {
/*  43 */           if (targetSelector != null && !targetSelector.apply(p_apply_1_)) {
/*  44 */             return false;
/*     */           }
/*  46 */           if (p_apply_1_ instanceof EntityPlayer) {
/*  47 */             double d0 = EntityAINearestAttackableTarget.this.getTargetDistance();
/*     */             
/*  49 */             if (p_apply_1_.isSneaking()) {
/*  50 */               d0 *= 0.800000011920929D;
/*     */             }
/*     */             
/*  53 */             if (p_apply_1_.isInvisible()) {
/*  54 */               float f = ((EntityPlayer)p_apply_1_).getArmorVisibility();
/*     */               
/*  56 */               if (f < 0.1F) {
/*  57 */                 f = 0.1F;
/*     */               }
/*     */               
/*  60 */               d0 *= (0.7F * f);
/*     */             } 
/*     */             
/*  63 */             if (p_apply_1_.getDistanceToEntity((Entity)EntityAINearestAttackableTarget.this.taskOwner) > d0) {
/*  64 */               return false;
/*     */             }
/*     */           } 
/*     */           
/*  68 */           return EntityAINearestAttackableTarget.this.isSuitableTarget((EntityLivingBase)p_apply_1_, false);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  78 */     if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
/*  79 */       return false;
/*     */     }
/*  81 */     double d0 = getTargetDistance();
/*  82 */     List<T> list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.targetClass, this.taskOwner.getEntityBoundingBox().expand(d0, 4.0D, d0), Predicates.and(this.targetEntitySelector, EntitySelectors.NOT_SPECTATING));
/*  83 */     Collections.sort(list, this.theNearestAttackableTargetSorter);
/*     */     
/*  85 */     if (list.isEmpty()) {
/*  86 */       return false;
/*     */     }
/*  88 */     this.targetEntity = (EntityLivingBase)list.get(0);
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  98 */     this.taskOwner.setAttackTarget(this.targetEntity);
/*  99 */     super.startExecuting();
/*     */   }
/*     */   
/*     */   public static class Sorter implements Comparator<Entity> {
/*     */     private final Entity theEntity;
/*     */     
/*     */     public Sorter(Entity theEntityIn) {
/* 106 */       this.theEntity = theEntityIn;
/*     */     }
/*     */     
/*     */     public int compare(Entity p_compare_1_, Entity p_compare_2_) {
/* 110 */       double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
/* 111 */       double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
/* 112 */       return (d0 < d1) ? -1 : ((d0 > d1) ? 1 : 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAINearestAttackableTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */