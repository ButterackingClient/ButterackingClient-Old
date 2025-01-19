/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityTameable;
/*    */ 
/*    */ public class EntityAITargetNonTamed<T extends EntityLivingBase>
/*    */   extends EntityAINearestAttackableTarget {
/*    */   public EntityAITargetNonTamed(EntityTameable entityIn, Class<T> classTarget, boolean checkSight, Predicate<? super T> targetSelector) {
/* 11 */     super((EntityCreature)entityIn, classTarget, 10, checkSight, false, targetSelector);
/* 12 */     this.theTameable = entityIn;
/*    */   }
/*    */ 
/*    */   
/*    */   private EntityTameable theTameable;
/*    */   
/*    */   public boolean shouldExecute() {
/* 19 */     return (!this.theTameable.isTamed() && super.shouldExecute());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAITargetNonTamed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */