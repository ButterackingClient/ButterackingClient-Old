/*    */ package net.minecraft.village;
/*    */ 
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.Vec3i;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VillageDoorInfo
/*    */ {
/*    */   private final BlockPos doorBlockPos;
/*    */   private final BlockPos insideBlock;
/*    */   private final EnumFacing insideDirection;
/*    */   private int lastActivityTimestamp;
/*    */   private boolean isDetachedFromVillageFlag;
/*    */   private int doorOpeningRestrictionCounter;
/*    */   
/*    */   public VillageDoorInfo(BlockPos pos, int p_i45871_2_, int p_i45871_3_, int p_i45871_4_) {
/* 22 */     this(pos, getFaceDirection(p_i45871_2_, p_i45871_3_), p_i45871_4_);
/*    */   }
/*    */   
/*    */   private static EnumFacing getFaceDirection(int deltaX, int deltaZ) {
/* 26 */     return (deltaX < 0) ? EnumFacing.WEST : ((deltaX > 0) ? EnumFacing.EAST : ((deltaZ < 0) ? EnumFacing.NORTH : EnumFacing.SOUTH));
/*    */   }
/*    */   
/*    */   public VillageDoorInfo(BlockPos pos, EnumFacing facing, int timestamp) {
/* 30 */     this.doorBlockPos = pos;
/* 31 */     this.insideDirection = facing;
/* 32 */     this.insideBlock = pos.offset(facing, 2);
/* 33 */     this.lastActivityTimestamp = timestamp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDistanceSquared(int x, int y, int z) {
/* 40 */     return (int)this.doorBlockPos.distanceSq(x, y, z);
/*    */   }
/*    */   
/*    */   public int getDistanceToDoorBlockSq(BlockPos pos) {
/* 44 */     return (int)pos.distanceSq((Vec3i)getDoorBlockPos());
/*    */   }
/*    */   
/*    */   public int getDistanceToInsideBlockSq(BlockPos pos) {
/* 48 */     return (int)this.insideBlock.distanceSq((Vec3i)pos);
/*    */   }
/*    */   
/*    */   public boolean func_179850_c(BlockPos pos) {
/* 52 */     int i = pos.getX() - this.doorBlockPos.getX();
/* 53 */     int j = pos.getZ() - this.doorBlockPos.getY();
/* 54 */     return (i * this.insideDirection.getFrontOffsetX() + j * this.insideDirection.getFrontOffsetZ() >= 0);
/*    */   }
/*    */   
/*    */   public void resetDoorOpeningRestrictionCounter() {
/* 58 */     this.doorOpeningRestrictionCounter = 0;
/*    */   }
/*    */   
/*    */   public void incrementDoorOpeningRestrictionCounter() {
/* 62 */     this.doorOpeningRestrictionCounter++;
/*    */   }
/*    */   
/*    */   public int getDoorOpeningRestrictionCounter() {
/* 66 */     return this.doorOpeningRestrictionCounter;
/*    */   }
/*    */   
/*    */   public BlockPos getDoorBlockPos() {
/* 70 */     return this.doorBlockPos;
/*    */   }
/*    */   
/*    */   public BlockPos getInsideBlockPos() {
/* 74 */     return this.insideBlock;
/*    */   }
/*    */   
/*    */   public int getInsideOffsetX() {
/* 78 */     return this.insideDirection.getFrontOffsetX() * 2;
/*    */   }
/*    */   
/*    */   public int getInsideOffsetZ() {
/* 82 */     return this.insideDirection.getFrontOffsetZ() * 2;
/*    */   }
/*    */   
/*    */   public int getInsidePosY() {
/* 86 */     return this.lastActivityTimestamp;
/*    */   }
/*    */   
/*    */   public void func_179849_a(int timestamp) {
/* 90 */     this.lastActivityTimestamp = timestamp;
/*    */   }
/*    */   
/*    */   public boolean getIsDetachedFromVillageFlag() {
/* 94 */     return this.isDetachedFromVillageFlag;
/*    */   }
/*    */   
/*    */   public void setIsDetachedFromVillageFlag(boolean detached) {
/* 98 */     this.isDetachedFromVillageFlag = detached;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\village\VillageDoorInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */