/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class EntityAIFindEntityNearestPlayer
/*     */   extends EntityAIBase
/*     */ {
/*  21 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityLiving entityLiving;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Predicate<Entity> predicate;
/*     */ 
/*     */   
/*     */   private final EntityAINearestAttackableTarget.Sorter sorter;
/*     */ 
/*     */   
/*     */   private EntityLivingBase entityTarget;
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityAIFindEntityNearestPlayer(EntityLiving entityLivingIn) {
/*  40 */     this.entityLiving = entityLivingIn;
/*     */     
/*  42 */     if (entityLivingIn instanceof net.minecraft.entity.EntityCreature) {
/*  43 */       LOGGER.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
/*     */     }
/*     */     
/*  46 */     this.predicate = new Predicate<Entity>() {
/*     */         public boolean apply(Entity p_apply_1_) {
/*  48 */           if (!(p_apply_1_ instanceof EntityPlayer))
/*  49 */             return false; 
/*  50 */           if (((EntityPlayer)p_apply_1_).capabilities.disableDamage) {
/*  51 */             return false;
/*     */           }
/*  53 */           double d0 = EntityAIFindEntityNearestPlayer.this.maxTargetRange();
/*     */           
/*  55 */           if (p_apply_1_.isSneaking()) {
/*  56 */             d0 *= 0.800000011920929D;
/*     */           }
/*     */           
/*  59 */           if (p_apply_1_.isInvisible()) {
/*  60 */             float f = ((EntityPlayer)p_apply_1_).getArmorVisibility();
/*     */             
/*  62 */             if (f < 0.1F) {
/*  63 */               f = 0.1F;
/*     */             }
/*     */             
/*  66 */             d0 *= (0.7F * f);
/*     */           } 
/*     */           
/*  69 */           return (p_apply_1_.getDistanceToEntity((Entity)EntityAIFindEntityNearestPlayer.this.entityLiving) > d0) ? false : EntityAITarget.isSuitableTarget(EntityAIFindEntityNearestPlayer.this.entityLiving, (EntityLivingBase)p_apply_1_, false, true);
/*     */         }
/*     */       };
/*     */     
/*  73 */     this.sorter = new EntityAINearestAttackableTarget.Sorter((Entity)entityLivingIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  80 */     double d0 = maxTargetRange();
/*  81 */     List<EntityPlayer> list = this.entityLiving.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.entityLiving.getEntityBoundingBox().expand(d0, 4.0D, d0), this.predicate);
/*  82 */     Collections.sort(list, this.sorter);
/*     */     
/*  84 */     if (list.isEmpty()) {
/*  85 */       return false;
/*     */     }
/*  87 */     this.entityTarget = (EntityLivingBase)list.get(0);
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  96 */     EntityLivingBase entitylivingbase = this.entityLiving.getAttackTarget();
/*     */     
/*  98 */     if (entitylivingbase == null)
/*  99 */       return false; 
/* 100 */     if (!entitylivingbase.isEntityAlive())
/* 101 */       return false; 
/* 102 */     if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage) {
/* 103 */       return false;
/*     */     }
/* 105 */     Team team = this.entityLiving.getTeam();
/* 106 */     Team team1 = entitylivingbase.getTeam();
/*     */     
/* 108 */     if (team != null && team1 == team) {
/* 109 */       return false;
/*     */     }
/* 111 */     double d0 = maxTargetRange();
/* 112 */     return (this.entityLiving.getDistanceSqToEntity((Entity)entitylivingbase) > d0 * d0) ? false : (!(entitylivingbase instanceof EntityPlayerMP && ((EntityPlayerMP)entitylivingbase).theItemInWorldManager.isCreative()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 121 */     this.entityLiving.setAttackTarget(this.entityTarget);
/* 122 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 129 */     this.entityLiving.setAttackTarget(null);
/* 130 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double maxTargetRange() {
/* 137 */     IAttributeInstance iattributeinstance = this.entityLiving.getEntityAttribute(SharedMonsterAttributes.followRange);
/* 138 */     return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIFindEntityNearestPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */