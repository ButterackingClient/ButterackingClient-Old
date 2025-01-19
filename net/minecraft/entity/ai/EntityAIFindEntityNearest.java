/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.SharedMonsterAttributes;
/*    */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*    */ import net.minecraft.entity.player.EntityPlayerMP;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class EntityAIFindEntityNearest
/*    */   extends EntityAIBase
/*    */ {
/* 18 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   private EntityLiving mob;
/*    */   private final Predicate<EntityLivingBase> field_179443_c;
/*    */   private final EntityAINearestAttackableTarget.Sorter field_179440_d;
/*    */   private EntityLivingBase target;
/*    */   private Class<? extends EntityLivingBase> field_179439_f;
/*    */   
/*    */   public EntityAIFindEntityNearest(EntityLiving mobIn, Class<? extends EntityLivingBase> p_i45884_2_) {
/* 26 */     this.mob = mobIn;
/* 27 */     this.field_179439_f = p_i45884_2_;
/*    */     
/* 29 */     if (mobIn instanceof net.minecraft.entity.EntityCreature) {
/* 30 */       LOGGER.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
/*    */     }
/*    */     
/* 33 */     this.field_179443_c = new Predicate<EntityLivingBase>() {
/*    */         public boolean apply(EntityLivingBase p_apply_1_) {
/* 35 */           double d0 = EntityAIFindEntityNearest.this.getFollowRange();
/*    */           
/* 37 */           if (p_apply_1_.isSneaking()) {
/* 38 */             d0 *= 0.800000011920929D;
/*    */           }
/*    */           
/* 41 */           return p_apply_1_.isInvisible() ? false : ((p_apply_1_.getDistanceToEntity((Entity)EntityAIFindEntityNearest.this.mob) > d0) ? false : EntityAITarget.isSuitableTarget(EntityAIFindEntityNearest.this.mob, p_apply_1_, false, true));
/*    */         }
/*    */       };
/* 44 */     this.field_179440_d = new EntityAINearestAttackableTarget.Sorter((Entity)mobIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 51 */     double d0 = getFollowRange();
/* 52 */     List<EntityLivingBase> list = this.mob.worldObj.getEntitiesWithinAABB(this.field_179439_f, this.mob.getEntityBoundingBox().expand(d0, 4.0D, d0), this.field_179443_c);
/* 53 */     Collections.sort(list, this.field_179440_d);
/*    */     
/* 55 */     if (list.isEmpty()) {
/* 56 */       return false;
/*    */     }
/* 58 */     this.target = list.get(0);
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 67 */     EntityLivingBase entitylivingbase = this.mob.getAttackTarget();
/*    */     
/* 69 */     if (entitylivingbase == null)
/* 70 */       return false; 
/* 71 */     if (!entitylivingbase.isEntityAlive()) {
/* 72 */       return false;
/*    */     }
/* 74 */     double d0 = getFollowRange();
/* 75 */     return (this.mob.getDistanceSqToEntity((Entity)entitylivingbase) > d0 * d0) ? false : (!(entitylivingbase instanceof EntityPlayerMP && ((EntityPlayerMP)entitylivingbase).theItemInWorldManager.isCreative()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 83 */     this.mob.setAttackTarget(this.target);
/* 84 */     super.startExecuting();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 91 */     this.mob.setAttackTarget(null);
/* 92 */     super.startExecuting();
/*    */   }
/*    */   
/*    */   protected double getFollowRange() {
/* 96 */     IAttributeInstance iattributeinstance = this.mob.getEntityAttribute(SharedMonsterAttributes.followRange);
/* 97 */     return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIFindEntityNearest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */