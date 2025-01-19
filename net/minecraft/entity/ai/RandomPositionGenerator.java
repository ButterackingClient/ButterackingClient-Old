/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomPositionGenerator
/*     */ {
/*  15 */   private static Vec3 staticVector = new Vec3(0.0D, 0.0D, 0.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3 findRandomTarget(EntityCreature entitycreatureIn, int xz, int y) {
/*  21 */     return findRandomTargetBlock(entitycreatureIn, xz, y, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3 findRandomTargetBlockTowards(EntityCreature entitycreatureIn, int xz, int y, Vec3 targetVec3) {
/*  28 */     staticVector = targetVec3.subtract(entitycreatureIn.posX, entitycreatureIn.posY, entitycreatureIn.posZ);
/*  29 */     return findRandomTargetBlock(entitycreatureIn, xz, y, staticVector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3 findRandomTargetBlockAwayFrom(EntityCreature entitycreatureIn, int xz, int y, Vec3 targetVec3) {
/*  36 */     staticVector = (new Vec3(entitycreatureIn.posX, entitycreatureIn.posY, entitycreatureIn.posZ)).subtract(targetVec3);
/*  37 */     return findRandomTargetBlock(entitycreatureIn, xz, y, staticVector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Vec3 findRandomTargetBlock(EntityCreature entitycreatureIn, int xz, int y, Vec3 targetVec3) {
/*     */     boolean flag1;
/*  45 */     Random random = entitycreatureIn.getRNG();
/*  46 */     boolean flag = false;
/*  47 */     int i = 0;
/*  48 */     int j = 0;
/*  49 */     int k = 0;
/*  50 */     float f = -99999.0F;
/*     */ 
/*     */     
/*  53 */     if (entitycreatureIn.hasHome()) {
/*  54 */       double d0 = entitycreatureIn.getHomePosition().distanceSq(MathHelper.floor_double(entitycreatureIn.posX), MathHelper.floor_double(entitycreatureIn.posY), MathHelper.floor_double(entitycreatureIn.posZ)) + 4.0D;
/*  55 */       double d1 = (entitycreatureIn.getMaximumHomeDistance() + xz);
/*  56 */       flag1 = (d0 < d1 * d1);
/*     */     } else {
/*  58 */       flag1 = false;
/*     */     } 
/*     */     
/*  61 */     for (int j1 = 0; j1 < 10; j1++) {
/*  62 */       int l = random.nextInt(2 * xz + 1) - xz;
/*  63 */       int k1 = random.nextInt(2 * y + 1) - y;
/*  64 */       int i1 = random.nextInt(2 * xz + 1) - xz;
/*     */       
/*  66 */       if (targetVec3 == null || l * targetVec3.xCoord + i1 * targetVec3.zCoord >= 0.0D) {
/*  67 */         if (entitycreatureIn.hasHome() && xz > 1) {
/*  68 */           BlockPos blockpos = entitycreatureIn.getHomePosition();
/*     */           
/*  70 */           if (entitycreatureIn.posX > blockpos.getX()) {
/*  71 */             l -= random.nextInt(xz / 2);
/*     */           } else {
/*  73 */             l += random.nextInt(xz / 2);
/*     */           } 
/*     */           
/*  76 */           if (entitycreatureIn.posZ > blockpos.getZ()) {
/*  77 */             i1 -= random.nextInt(xz / 2);
/*     */           } else {
/*  79 */             i1 += random.nextInt(xz / 2);
/*     */           } 
/*     */         } 
/*     */         
/*  83 */         l += MathHelper.floor_double(entitycreatureIn.posX);
/*  84 */         k1 += MathHelper.floor_double(entitycreatureIn.posY);
/*  85 */         i1 += MathHelper.floor_double(entitycreatureIn.posZ);
/*  86 */         BlockPos blockpos1 = new BlockPos(l, k1, i1);
/*     */         
/*  88 */         if (!flag1 || entitycreatureIn.isWithinHomeDistanceFromPosition(blockpos1)) {
/*  89 */           float f1 = entitycreatureIn.getBlockPathWeight(blockpos1);
/*     */           
/*  91 */           if (f1 > f) {
/*  92 */             f = f1;
/*  93 */             i = l;
/*  94 */             j = k1;
/*  95 */             k = i1;
/*  96 */             flag = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     if (flag) {
/* 103 */       return new Vec3(i, j, k);
/*     */     }
/* 105 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\entity\ai\RandomPositionGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */