/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.world.ChunkCoordIntPair;
/*    */ 
/*    */ public class ChunkPosComparator
/*    */   implements Comparator<ChunkCoordIntPair> {
/*    */   private int chunkPosX;
/*    */   private int chunkPosZ;
/*    */   private double yawRad;
/*    */   private double pitchNorm;
/*    */   
/*    */   public ChunkPosComparator(int chunkPosX, int chunkPosZ, double yawRad, double pitchRad) {
/* 15 */     this.chunkPosX = chunkPosX;
/* 16 */     this.chunkPosZ = chunkPosZ;
/* 17 */     this.yawRad = yawRad;
/* 18 */     this.pitchNorm = 1.0D - MathHelper.clamp_double(Math.abs(pitchRad) / 1.5707963267948966D, 0.0D, 1.0D);
/*    */   }
/*    */   
/*    */   public int compare(ChunkCoordIntPair cp1, ChunkCoordIntPair cp2) {
/* 22 */     int i = getDistSq(cp1);
/* 23 */     int j = getDistSq(cp2);
/* 24 */     return i - j;
/*    */   }
/*    */   
/*    */   private int getDistSq(ChunkCoordIntPair cp) {
/* 28 */     int i = cp.chunkXPos - this.chunkPosX;
/* 29 */     int j = cp.chunkZPos - this.chunkPosZ;
/* 30 */     int k = i * i + j * j;
/* 31 */     double d0 = MathHelper.atan2(j, i);
/* 32 */     double d1 = Math.abs(d0 - this.yawRad);
/*    */     
/* 34 */     if (d1 > Math.PI) {
/* 35 */       d1 = 6.283185307179586D - d1;
/*    */     }
/*    */     
/* 38 */     k = (int)(k * 1000.0D * this.pitchNorm * d1 * d1);
/* 39 */     return k;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\ChunkPosComparator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */