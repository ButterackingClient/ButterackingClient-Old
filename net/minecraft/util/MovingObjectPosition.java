/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MovingObjectPosition
/*    */ {
/*    */   private BlockPos blockPos;
/*    */   public MovingObjectType typeOfHit;
/*    */   public EnumFacing sideHit;
/*    */   public Vec3 hitVec;
/*    */   public Entity entityHit;
/*    */   
/*    */   public MovingObjectPosition(Vec3 hitVecIn, EnumFacing facing, BlockPos blockPosIn) {
/* 25 */     this(MovingObjectType.BLOCK, hitVecIn, facing, blockPosIn);
/*    */   }
/*    */   
/*    */   public MovingObjectPosition(Vec3 p_i45552_1_, EnumFacing facing) {
/* 29 */     this(MovingObjectType.BLOCK, p_i45552_1_, facing, BlockPos.ORIGIN);
/*    */   }
/*    */   
/*    */   public MovingObjectPosition(Entity entityIn) {
/* 33 */     this(entityIn, new Vec3(entityIn.posX, entityIn.posY, entityIn.posZ));
/*    */   }
/*    */   
/*    */   public MovingObjectPosition(MovingObjectType typeOfHitIn, Vec3 hitVecIn, EnumFacing sideHitIn, BlockPos blockPosIn) {
/* 37 */     this.typeOfHit = typeOfHitIn;
/* 38 */     this.blockPos = blockPosIn;
/* 39 */     this.sideHit = sideHitIn;
/* 40 */     this.hitVec = new Vec3(hitVecIn.xCoord, hitVecIn.yCoord, hitVecIn.zCoord);
/*    */   }
/*    */   
/*    */   public MovingObjectPosition(Entity entityHitIn, Vec3 hitVecIn) {
/* 44 */     this.typeOfHit = MovingObjectType.ENTITY;
/* 45 */     this.entityHit = entityHitIn;
/* 46 */     this.hitVec = hitVecIn;
/*    */   }
/*    */   
/*    */   public BlockPos getBlockPos() {
/* 50 */     return this.blockPos;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 54 */     return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
/*    */   }
/*    */   
/*    */   public enum MovingObjectType {
/* 58 */     MISS,
/* 59 */     BLOCK,
/* 60 */     ENTITY;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\MovingObjectPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */