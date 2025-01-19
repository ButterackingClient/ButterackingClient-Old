/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ public abstract class EntityAIDoorInteract extends EntityAIBase {
/*  14 */   protected BlockPos doorPosition = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*     */   protected EntityLiving theEntity;
/*     */   
/*     */   protected BlockDoor doorBlock;
/*     */   
/*     */   boolean hasStoppedDoorInteraction;
/*     */   
/*     */   float entityPositionX;
/*     */   
/*     */   float entityPositionZ;
/*     */ 
/*     */   
/*     */   public EntityAIDoorInteract(EntityLiving entityIn) {
/*  29 */     this.theEntity = entityIn;
/*     */     
/*  31 */     if (!(entityIn.getNavigator() instanceof PathNavigateGround)) {
/*  32 */       throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  40 */     if (!this.theEntity.isCollidedHorizontally) {
/*  41 */       return false;
/*     */     }
/*  43 */     PathNavigateGround pathnavigateground = (PathNavigateGround)this.theEntity.getNavigator();
/*  44 */     PathEntity pathentity = pathnavigateground.getPath();
/*     */     
/*  46 */     if (pathentity != null && !pathentity.isFinished() && pathnavigateground.getEnterDoors()) {
/*  47 */       for (int i = 0; i < Math.min(pathentity.getCurrentPathIndex() + 2, pathentity.getCurrentPathLength()); i++) {
/*  48 */         PathPoint pathpoint = pathentity.getPathPointFromIndex(i);
/*  49 */         this.doorPosition = new BlockPos(pathpoint.xCoord, pathpoint.yCoord + 1, pathpoint.zCoord);
/*     */         
/*  51 */         if (this.theEntity.getDistanceSq(this.doorPosition.getX(), this.theEntity.posY, this.doorPosition.getZ()) <= 2.25D) {
/*  52 */           this.doorBlock = getBlockDoor(this.doorPosition);
/*     */           
/*  54 */           if (this.doorBlock != null) {
/*  55 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  60 */       this.doorPosition = (new BlockPos((Entity)this.theEntity)).up();
/*  61 */       this.doorBlock = getBlockDoor(this.doorPosition);
/*  62 */       return (this.doorBlock != null);
/*     */     } 
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  73 */     return !this.hasStoppedDoorInteraction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  80 */     this.hasStoppedDoorInteraction = false;
/*  81 */     this.entityPositionX = (float)((this.doorPosition.getX() + 0.5F) - this.theEntity.posX);
/*  82 */     this.entityPositionZ = (float)((this.doorPosition.getZ() + 0.5F) - this.theEntity.posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  89 */     float f = (float)((this.doorPosition.getX() + 0.5F) - this.theEntity.posX);
/*  90 */     float f1 = (float)((this.doorPosition.getZ() + 0.5F) - this.theEntity.posZ);
/*  91 */     float f2 = this.entityPositionX * f + this.entityPositionZ * f1;
/*     */     
/*  93 */     if (f2 < 0.0F) {
/*  94 */       this.hasStoppedDoorInteraction = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private BlockDoor getBlockDoor(BlockPos pos) {
/*  99 */     Block block = this.theEntity.worldObj.getBlockState(pos).getBlock();
/* 100 */     return (block instanceof BlockDoor && block.getMaterial() == Material.wood) ? (BlockDoor)block : null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIDoorInteract.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */