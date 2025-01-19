/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.Vec3;
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
/*     */ public class EntityAIAvoidEntity<T extends Entity>
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final Predicate<Entity> canBeSeenSelector;
/*     */   protected EntityCreature theEntity;
/*     */   private double farSpeed;
/*     */   private double nearSpeed;
/*     */   protected T closestLivingEntity;
/*     */   private float avoidDistance;
/*     */   private PathEntity entityPathEntity;
/*     */   private PathNavigate entityPathNavigate;
/*     */   private Class<T> classToAvoid;
/*     */   private Predicate<? super T> avoidTargetSelector;
/*     */   
/*     */   public EntityAIAvoidEntity(EntityCreature theEntityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
/*  40 */     this(theEntityIn, classToAvoidIn, Predicates.alwaysTrue(), avoidDistanceIn, farSpeedIn, nearSpeedIn);
/*     */   }
/*     */   
/*     */   public EntityAIAvoidEntity(EntityCreature theEntityIn, Class<T> classToAvoidIn, Predicate<? super T> avoidTargetSelectorIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
/*  44 */     this.canBeSeenSelector = new Predicate<Entity>() {
/*     */         public boolean apply(Entity p_apply_1_) {
/*  46 */           return (p_apply_1_.isEntityAlive() && EntityAIAvoidEntity.this.theEntity.getEntitySenses().canSee(p_apply_1_));
/*     */         }
/*     */       };
/*  49 */     this.theEntity = theEntityIn;
/*  50 */     this.classToAvoid = classToAvoidIn;
/*  51 */     this.avoidTargetSelector = avoidTargetSelectorIn;
/*  52 */     this.avoidDistance = avoidDistanceIn;
/*  53 */     this.farSpeed = farSpeedIn;
/*  54 */     this.nearSpeed = nearSpeedIn;
/*  55 */     this.entityPathNavigate = theEntityIn.getNavigator();
/*  56 */     setMutexBits(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  63 */     List<T> list = this.theEntity.worldObj.getEntitiesWithinAABB(this.classToAvoid, this.theEntity.getEntityBoundingBox().expand(this.avoidDistance, 3.0D, this.avoidDistance), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING, this.canBeSeenSelector, this.avoidTargetSelector }));
/*     */     
/*  65 */     if (list.isEmpty()) {
/*  66 */       return false;
/*     */     }
/*  68 */     this.closestLivingEntity = list.get(0);
/*  69 */     Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, new Vec3(((Entity)this.closestLivingEntity).posX, ((Entity)this.closestLivingEntity).posY, ((Entity)this.closestLivingEntity).posZ));
/*     */     
/*  71 */     if (vec3 == null)
/*  72 */       return false; 
/*  73 */     if (this.closestLivingEntity.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord) < this.closestLivingEntity.getDistanceSqToEntity((Entity)this.theEntity)) {
/*  74 */       return false;
/*     */     }
/*  76 */     this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
/*  77 */     return (this.entityPathEntity == null) ? false : this.entityPathEntity.isDestinationSame(vec3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  86 */     return !this.entityPathNavigate.noPath();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  93 */     this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 100 */     this.closestLivingEntity = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 107 */     if (this.theEntity.getDistanceSqToEntity((Entity)this.closestLivingEntity) < 49.0D) {
/* 108 */       this.theEntity.getNavigator().setSpeed(this.nearSpeed);
/*     */     } else {
/* 110 */       this.theEntity.getNavigator().setSpeed(this.farSpeed);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIAvoidEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */