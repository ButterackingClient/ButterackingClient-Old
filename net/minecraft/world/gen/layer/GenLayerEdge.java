/*     */ package net.minecraft.world.gen.layer;
/*     */ 
/*     */ public class GenLayerEdge extends GenLayer {
/*     */   private final Mode field_151627_c;
/*     */   
/*     */   public GenLayerEdge(long p_i45474_1_, GenLayer p_i45474_3_, Mode p_i45474_4_) {
/*   7 */     super(p_i45474_1_);
/*   8 */     this.parent = p_i45474_3_;
/*   9 */     this.field_151627_c = p_i45474_4_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/*  17 */     switch (this.field_151627_c) {
/*     */       
/*     */       default:
/*  20 */         return getIntsCoolWarm(areaX, areaY, areaWidth, areaHeight);
/*     */       
/*     */       case HEAT_ICE:
/*  23 */         return getIntsHeatIce(areaX, areaY, areaWidth, areaHeight);
/*     */       case SPECIAL:
/*     */         break;
/*  26 */     }  return getIntsSpecial(areaX, areaY, areaWidth, areaHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] getIntsCoolWarm(int p_151626_1_, int p_151626_2_, int p_151626_3_, int p_151626_4_) {
/*  31 */     int i = p_151626_1_ - 1;
/*  32 */     int j = p_151626_2_ - 1;
/*  33 */     int k = 1 + p_151626_3_ + 1;
/*  34 */     int l = 1 + p_151626_4_ + 1;
/*  35 */     int[] aint = this.parent.getInts(i, j, k, l);
/*  36 */     int[] aint1 = IntCache.getIntCache(p_151626_3_ * p_151626_4_);
/*     */     
/*  38 */     for (int i1 = 0; i1 < p_151626_4_; i1++) {
/*  39 */       for (int j1 = 0; j1 < p_151626_3_; j1++) {
/*  40 */         initChunkSeed((j1 + p_151626_1_), (i1 + p_151626_2_));
/*  41 */         int k1 = aint[j1 + 1 + (i1 + 1) * k];
/*     */         
/*  43 */         if (k1 == 1) {
/*  44 */           int l1 = aint[j1 + 1 + (i1 + 1 - 1) * k];
/*  45 */           int i2 = aint[j1 + 1 + 1 + (i1 + 1) * k];
/*  46 */           int j2 = aint[j1 + 1 - 1 + (i1 + 1) * k];
/*  47 */           int k2 = aint[j1 + 1 + (i1 + 1 + 1) * k];
/*  48 */           boolean flag = !(l1 != 3 && i2 != 3 && j2 != 3 && k2 != 3);
/*  49 */           boolean flag1 = !(l1 != 4 && i2 != 4 && j2 != 4 && k2 != 4);
/*     */           
/*  51 */           if (flag || flag1) {
/*  52 */             k1 = 2;
/*     */           }
/*     */         } 
/*     */         
/*  56 */         aint1[j1 + i1 * p_151626_3_] = k1;
/*     */       } 
/*     */     } 
/*     */     
/*  60 */     return aint1;
/*     */   }
/*     */   
/*     */   private int[] getIntsHeatIce(int p_151624_1_, int p_151624_2_, int p_151624_3_, int p_151624_4_) {
/*  64 */     int i = p_151624_1_ - 1;
/*  65 */     int j = p_151624_2_ - 1;
/*  66 */     int k = 1 + p_151624_3_ + 1;
/*  67 */     int l = 1 + p_151624_4_ + 1;
/*  68 */     int[] aint = this.parent.getInts(i, j, k, l);
/*  69 */     int[] aint1 = IntCache.getIntCache(p_151624_3_ * p_151624_4_);
/*     */     
/*  71 */     for (int i1 = 0; i1 < p_151624_4_; i1++) {
/*  72 */       for (int j1 = 0; j1 < p_151624_3_; j1++) {
/*  73 */         int k1 = aint[j1 + 1 + (i1 + 1) * k];
/*     */         
/*  75 */         if (k1 == 4) {
/*  76 */           int l1 = aint[j1 + 1 + (i1 + 1 - 1) * k];
/*  77 */           int i2 = aint[j1 + 1 + 1 + (i1 + 1) * k];
/*  78 */           int j2 = aint[j1 + 1 - 1 + (i1 + 1) * k];
/*  79 */           int k2 = aint[j1 + 1 + (i1 + 1 + 1) * k];
/*  80 */           boolean flag = !(l1 != 2 && i2 != 2 && j2 != 2 && k2 != 2);
/*  81 */           boolean flag1 = !(l1 != 1 && i2 != 1 && j2 != 1 && k2 != 1);
/*     */           
/*  83 */           if (flag1 || flag) {
/*  84 */             k1 = 3;
/*     */           }
/*     */         } 
/*     */         
/*  88 */         aint1[j1 + i1 * p_151624_3_] = k1;
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     return aint1;
/*     */   }
/*     */   
/*     */   private int[] getIntsSpecial(int p_151625_1_, int p_151625_2_, int p_151625_3_, int p_151625_4_) {
/*  96 */     int[] aint = this.parent.getInts(p_151625_1_, p_151625_2_, p_151625_3_, p_151625_4_);
/*  97 */     int[] aint1 = IntCache.getIntCache(p_151625_3_ * p_151625_4_);
/*     */     
/*  99 */     for (int i = 0; i < p_151625_4_; i++) {
/* 100 */       for (int j = 0; j < p_151625_3_; j++) {
/* 101 */         initChunkSeed((j + p_151625_1_), (i + p_151625_2_));
/* 102 */         int k = aint[j + i * p_151625_3_];
/*     */         
/* 104 */         if (k != 0 && nextInt(13) == 0) {
/* 105 */           k |= 1 + nextInt(15) << 8 & 0xF00;
/*     */         }
/*     */         
/* 108 */         aint1[j + i * p_151625_3_] = k;
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     return aint1;
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 116 */     COOL_WARM,
/* 117 */     HEAT_ICE,
/* 118 */     SPECIAL;
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\world\gen\layer\GenLayerEdge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */