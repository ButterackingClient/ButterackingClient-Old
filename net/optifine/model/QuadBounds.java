/*     */ package net.optifine.model;
/*     */ 
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public class QuadBounds {
/*   6 */   private float minX = Float.MAX_VALUE;
/*   7 */   private float minY = Float.MAX_VALUE;
/*   8 */   private float minZ = Float.MAX_VALUE;
/*   9 */   private float maxX = -3.4028235E38F;
/*  10 */   private float maxY = -3.4028235E38F;
/*  11 */   private float maxZ = -3.4028235E38F;
/*     */   
/*     */   public QuadBounds(int[] vertexData) {
/*  14 */     int i = vertexData.length / 4;
/*     */     
/*  16 */     for (int j = 0; j < 4; j++) {
/*  17 */       int k = j * i;
/*  18 */       float f = Float.intBitsToFloat(vertexData[k + 0]);
/*  19 */       float f1 = Float.intBitsToFloat(vertexData[k + 1]);
/*  20 */       float f2 = Float.intBitsToFloat(vertexData[k + 2]);
/*     */       
/*  22 */       if (this.minX > f) {
/*  23 */         this.minX = f;
/*     */       }
/*     */       
/*  26 */       if (this.minY > f1) {
/*  27 */         this.minY = f1;
/*     */       }
/*     */       
/*  30 */       if (this.minZ > f2) {
/*  31 */         this.minZ = f2;
/*     */       }
/*     */       
/*  34 */       if (this.maxX < f) {
/*  35 */         this.maxX = f;
/*     */       }
/*     */       
/*  38 */       if (this.maxY < f1) {
/*  39 */         this.maxY = f1;
/*     */       }
/*     */       
/*  42 */       if (this.maxZ < f2) {
/*  43 */         this.maxZ = f2;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getMinX() {
/*  49 */     return this.minX;
/*     */   }
/*     */   
/*     */   public float getMinY() {
/*  53 */     return this.minY;
/*     */   }
/*     */   
/*     */   public float getMinZ() {
/*  57 */     return this.minZ;
/*     */   }
/*     */   
/*     */   public float getMaxX() {
/*  61 */     return this.maxX;
/*     */   }
/*     */   
/*     */   public float getMaxY() {
/*  65 */     return this.maxY;
/*     */   }
/*     */   
/*     */   public float getMaxZ() {
/*  69 */     return this.maxZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFaceQuad(EnumFacing face) {
/*     */     float f;
/*     */     float f1;
/*     */     float f2;
/*  77 */     switch (face) {
/*     */       case null:
/*  79 */         f = getMinY();
/*  80 */         f1 = getMaxY();
/*  81 */         f2 = 0.0F;
/*     */         break;
/*     */       
/*     */       case UP:
/*  85 */         f = getMinY();
/*  86 */         f1 = getMaxY();
/*  87 */         f2 = 1.0F;
/*     */         break;
/*     */       
/*     */       case NORTH:
/*  91 */         f = getMinZ();
/*  92 */         f1 = getMaxZ();
/*  93 */         f2 = 0.0F;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/*  97 */         f = getMinZ();
/*  98 */         f1 = getMaxZ();
/*  99 */         f2 = 1.0F;
/*     */         break;
/*     */       
/*     */       case WEST:
/* 103 */         f = getMinX();
/* 104 */         f1 = getMaxX();
/* 105 */         f2 = 0.0F;
/*     */         break;
/*     */       
/*     */       case EAST:
/* 109 */         f = getMinX();
/* 110 */         f1 = getMaxX();
/* 111 */         f2 = 1.0F;
/*     */         break;
/*     */       
/*     */       default:
/* 115 */         return false;
/*     */     } 
/*     */     
/* 118 */     return (f == f2 && f1 == f2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullQuad(EnumFacing face) {
/*     */     float f;
/*     */     float f1;
/*     */     float f2;
/*     */     float f3;
/* 127 */     switch (face) {
/*     */       case null:
/*     */       case UP:
/* 130 */         f = getMinX();
/* 131 */         f1 = getMaxX();
/* 132 */         f2 = getMinZ();
/* 133 */         f3 = getMaxZ();
/*     */         break;
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/* 138 */         f = getMinX();
/* 139 */         f1 = getMaxX();
/* 140 */         f2 = getMinY();
/* 141 */         f3 = getMaxY();
/*     */         break;
/*     */       
/*     */       case WEST:
/*     */       case EAST:
/* 146 */         f = getMinY();
/* 147 */         f1 = getMaxY();
/* 148 */         f2 = getMinZ();
/* 149 */         f3 = getMaxZ();
/*     */         break;
/*     */       
/*     */       default:
/* 153 */         return false;
/*     */     } 
/*     */     
/* 156 */     return (f == 0.0F && f1 == 1.0F && f2 == 0.0F && f3 == 1.0F);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\model\QuadBounds.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */