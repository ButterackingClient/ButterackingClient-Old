/*   */ package net.minecraft.entity.ai;
/*   */ 
/*   */ import net.minecraft.entity.Entity;
/*   */ import net.minecraft.entity.EntityLiving;
/*   */ 
/*   */ public class EntityAIWatchClosest2 extends EntityAIWatchClosest {
/*   */   public EntityAIWatchClosest2(EntityLiving entitylivingIn, Class<? extends Entity> watchTargetClass, float maxDistance, float chanceIn) {
/* 8 */     super(entitylivingIn, watchTargetClass, maxDistance, chanceIn);
/* 9 */     setMutexBits(3);
/*   */   }
/*   */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIWatchClosest2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */