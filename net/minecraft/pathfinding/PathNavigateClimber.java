/*    */ package net.minecraft.pathfinding;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class PathNavigateClimber
/*    */   extends PathNavigateGround
/*    */ {
/*    */   private BlockPos targetPosition;
/*    */   
/*    */   public PathNavigateClimber(EntityLiving entityLivingIn, World worldIn) {
/* 16 */     super(entityLivingIn, worldIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PathEntity getPathToPos(BlockPos pos) {
/* 23 */     this.targetPosition = pos;
/* 24 */     return super.getPathToPos(pos);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PathEntity getPathToEntityLiving(Entity entityIn) {
/* 31 */     this.targetPosition = new BlockPos(entityIn);
/* 32 */     return super.getPathToEntityLiving(entityIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn) {
/* 39 */     PathEntity pathentity = getPathToEntityLiving(entityIn);
/*    */     
/* 41 */     if (pathentity != null) {
/* 42 */       return setPath(pathentity, speedIn);
/*    */     }
/* 44 */     this.targetPosition = new BlockPos(entityIn);
/* 45 */     this.speed = speedIn;
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdateNavigation() {
/* 51 */     if (!noPath()) {
/* 52 */       super.onUpdateNavigation();
/*    */     }
/* 54 */     else if (this.targetPosition != null) {
/* 55 */       double d0 = (this.theEntity.width * this.theEntity.width);
/*    */       
/* 57 */       if (this.theEntity.getDistanceSqToCenter(this.targetPosition) >= d0 && (this.theEntity.posY <= this.targetPosition.getY() || this.theEntity.getDistanceSqToCenter(new BlockPos(this.targetPosition.getX(), MathHelper.floor_double(this.theEntity.posY), this.targetPosition.getZ())) >= d0)) {
/* 58 */         this.theEntity.getMoveHelper().setMoveTo(this.targetPosition.getX(), this.targetPosition.getY(), this.targetPosition.getZ(), this.speed);
/*    */       } else {
/* 60 */         this.targetPosition = null;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\pathfinding\PathNavigateClimber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */