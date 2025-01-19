/*     */ package net.minecraft.world;
/*     */ 
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkCoordIntPair
/*     */ {
/*     */   public final int chunkXPos;
/*     */   public final int chunkZPos;
/*  15 */   private int cachedHashCode = 0;
/*     */   
/*     */   public ChunkCoordIntPair(int x, int z) {
/*  18 */     this.chunkXPos = x;
/*  19 */     this.chunkZPos = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long chunkXZ2Int(int x, int z) {
/*  26 */     return x & 0xFFFFFFFFL | (z & 0xFFFFFFFFL) << 32L;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  30 */     if (this.cachedHashCode == 0) {
/*  31 */       int i = 1664525 * this.chunkXPos + 1013904223;
/*  32 */       int j = 1664525 * (this.chunkZPos ^ 0xDEADBEEF) + 1013904223;
/*  33 */       this.cachedHashCode = i ^ j;
/*     */     } 
/*     */     
/*  36 */     return this.cachedHashCode;
/*     */   }
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  40 */     if (this == p_equals_1_)
/*  41 */       return true; 
/*  42 */     if (!(p_equals_1_ instanceof ChunkCoordIntPair)) {
/*  43 */       return false;
/*     */     }
/*  45 */     ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)p_equals_1_;
/*  46 */     return (this.chunkXPos == chunkcoordintpair.chunkXPos && this.chunkZPos == chunkcoordintpair.chunkZPos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCenterXPos() {
/*  51 */     return (this.chunkXPos << 4) + 8;
/*     */   }
/*     */   
/*     */   public int getCenterZPosition() {
/*  55 */     return (this.chunkZPos << 4) + 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXStart() {
/*  62 */     return this.chunkXPos << 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZStart() {
/*  69 */     return this.chunkZPos << 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXEnd() {
/*  76 */     return (this.chunkXPos << 4) + 15;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZEnd() {
/*  83 */     return (this.chunkZPos << 4) + 15;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getBlock(int x, int y, int z) {
/*  90 */     return new BlockPos((this.chunkXPos << 4) + x, y, (this.chunkZPos << 4) + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getCenterBlock(int y) {
/*  97 */     return new BlockPos(getCenterXPos(), y, getCenterZPosition());
/*     */   }
/*     */   
/*     */   public String toString() {
/* 101 */     return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\ChunkCoordIntPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */