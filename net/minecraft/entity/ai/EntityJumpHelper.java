/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ 
/*    */ public class EntityJumpHelper {
/*    */   private EntityLiving entity;
/*    */   protected boolean isJumping;
/*    */   
/*    */   public EntityJumpHelper(EntityLiving entityIn) {
/* 10 */     this.entity = entityIn;
/*    */   }
/*    */   
/*    */   public void setJumping() {
/* 14 */     this.isJumping = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doJump() {
/* 21 */     this.entity.setJumping(this.isJumping);
/* 22 */     this.isJumping = false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityJumpHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */