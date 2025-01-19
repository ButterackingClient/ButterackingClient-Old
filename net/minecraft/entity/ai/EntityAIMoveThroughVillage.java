/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.village.Village;
/*     */ import net.minecraft.village.VillageDoorInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAIMoveThroughVillage
/*     */   extends EntityAIBase
/*     */ {
/*     */   private EntityCreature theEntity;
/*     */   private double movementSpeed;
/*     */   private PathEntity entityPathNavigate;
/*     */   private VillageDoorInfo doorInfo;
/*     */   private boolean isNocturnal;
/*  26 */   private List<VillageDoorInfo> doorList = Lists.newArrayList();
/*     */   
/*     */   public EntityAIMoveThroughVillage(EntityCreature theEntityIn, double movementSpeedIn, boolean isNocturnalIn) {
/*  29 */     this.theEntity = theEntityIn;
/*  30 */     this.movementSpeed = movementSpeedIn;
/*  31 */     this.isNocturnal = isNocturnalIn;
/*  32 */     setMutexBits(1);
/*     */     
/*  34 */     if (!(theEntityIn.getNavigator() instanceof PathNavigateGround)) {
/*  35 */       throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  43 */     resizeDoorList();
/*     */     
/*  45 */     if (this.isNocturnal && this.theEntity.worldObj.isDaytime()) {
/*  46 */       return false;
/*     */     }
/*  48 */     Village village = this.theEntity.worldObj.getVillageCollection().getNearestVillage(new BlockPos((Entity)this.theEntity), 0);
/*     */     
/*  50 */     if (village == null) {
/*  51 */       return false;
/*     */     }
/*  53 */     this.doorInfo = findNearestDoor(village);
/*     */     
/*  55 */     if (this.doorInfo == null) {
/*  56 */       return false;
/*     */     }
/*  58 */     PathNavigateGround pathnavigateground = (PathNavigateGround)this.theEntity.getNavigator();
/*  59 */     boolean flag = pathnavigateground.getEnterDoors();
/*  60 */     pathnavigateground.setBreakDoors(false);
/*  61 */     this.entityPathNavigate = pathnavigateground.getPathToPos(this.doorInfo.getDoorBlockPos());
/*  62 */     pathnavigateground.setBreakDoors(flag);
/*     */     
/*  64 */     if (this.entityPathNavigate != null) {
/*  65 */       return true;
/*     */     }
/*  67 */     Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 10, 7, new Vec3(this.doorInfo.getDoorBlockPos().getX(), this.doorInfo.getDoorBlockPos().getY(), this.doorInfo.getDoorBlockPos().getZ()));
/*     */     
/*  69 */     if (vec3 == null) {
/*  70 */       return false;
/*     */     }
/*  72 */     pathnavigateground.setBreakDoors(false);
/*  73 */     this.entityPathNavigate = this.theEntity.getNavigator().getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
/*  74 */     pathnavigateground.setBreakDoors(flag);
/*  75 */     return (this.entityPathNavigate != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  87 */     if (this.theEntity.getNavigator().noPath()) {
/*  88 */       return false;
/*     */     }
/*  90 */     float f = this.theEntity.width + 4.0F;
/*  91 */     return (this.theEntity.getDistanceSq(this.doorInfo.getDoorBlockPos()) > (f * f));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  99 */     this.theEntity.getNavigator().setPath(this.entityPathNavigate, this.movementSpeed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 106 */     if (this.theEntity.getNavigator().noPath() || this.theEntity.getDistanceSq(this.doorInfo.getDoorBlockPos()) < 16.0D) {
/* 107 */       this.doorList.add(this.doorInfo);
/*     */     }
/*     */   }
/*     */   
/*     */   private VillageDoorInfo findNearestDoor(Village villageIn) {
/* 112 */     VillageDoorInfo villagedoorinfo = null;
/* 113 */     int i = Integer.MAX_VALUE;
/*     */     
/* 115 */     for (VillageDoorInfo villagedoorinfo1 : villageIn.getVillageDoorInfoList()) {
/* 116 */       int j = villagedoorinfo1.getDistanceSquared(MathHelper.floor_double(this.theEntity.posX), MathHelper.floor_double(this.theEntity.posY), MathHelper.floor_double(this.theEntity.posZ));
/*     */       
/* 118 */       if (j < i && !doesDoorListContain(villagedoorinfo1)) {
/* 119 */         villagedoorinfo = villagedoorinfo1;
/* 120 */         i = j;
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     return villagedoorinfo;
/*     */   }
/*     */   
/*     */   private boolean doesDoorListContain(VillageDoorInfo doorInfoIn) {
/* 128 */     for (VillageDoorInfo villagedoorinfo : this.doorList) {
/* 129 */       if (doorInfoIn.getDoorBlockPos().equals(villagedoorinfo.getDoorBlockPos())) {
/* 130 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */   
/*     */   private void resizeDoorList() {
/* 138 */     if (this.doorList.size() > 15)
/* 139 */       this.doorList.remove(0); 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\EntityAIMoveThroughVillage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */