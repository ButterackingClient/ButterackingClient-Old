/*     */ package net.optifine;
/*     */ 
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3i;
/*     */ 
/*     */ public class BlockPosM
/*     */   extends BlockPos
/*     */ {
/*     */   private int mx;
/*     */   private int my;
/*     */   private int mz;
/*     */   private int level;
/*     */   private BlockPosM[] facings;
/*     */   private boolean needsUpdate;
/*     */   
/*     */   public BlockPosM(int x, int y, int z) {
/*  21 */     this(x, y, z, 0);
/*     */   }
/*     */   
/*     */   public BlockPosM(double xIn, double yIn, double zIn) {
/*  25 */     this(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
/*     */   }
/*     */   
/*     */   public BlockPosM(int x, int y, int z, int level) {
/*  29 */     super(0, 0, 0);
/*  30 */     this.mx = x;
/*  31 */     this.my = y;
/*  32 */     this.mz = z;
/*  33 */     this.level = level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/*  40 */     return this.mx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/*  47 */     return this.my;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZ() {
/*  54 */     return this.mz;
/*     */   }
/*     */   
/*     */   public void setXyz(int x, int y, int z) {
/*  58 */     this.mx = x;
/*  59 */     this.my = y;
/*  60 */     this.mz = z;
/*  61 */     this.needsUpdate = true;
/*     */   }
/*     */   
/*     */   public void setXyz(double xIn, double yIn, double zIn) {
/*  65 */     setXyz(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
/*     */   }
/*     */   
/*     */   public BlockPosM set(Vec3i vec) {
/*  69 */     setXyz(vec.getX(), vec.getY(), vec.getZ());
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public BlockPosM set(int xIn, int yIn, int zIn) {
/*  74 */     setXyz(xIn, yIn, zIn);
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   public BlockPos offsetMutable(EnumFacing facing) {
/*  79 */     return offset(facing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing) {
/*  86 */     if (this.level <= 0) {
/*  87 */       return super.offset(facing, 1);
/*     */     }
/*  89 */     if (this.facings == null) {
/*  90 */       this.facings = new BlockPosM[EnumFacing.VALUES.length];
/*     */     }
/*     */     
/*  93 */     if (this.needsUpdate) {
/*  94 */       update();
/*     */     }
/*     */     
/*  97 */     int i = facing.getIndex();
/*  98 */     BlockPosM blockposm = this.facings[i];
/*     */     
/* 100 */     if (blockposm == null) {
/* 101 */       int j = this.mx + facing.getFrontOffsetX();
/* 102 */       int k = this.my + facing.getFrontOffsetY();
/* 103 */       int l = this.mz + facing.getFrontOffsetZ();
/* 104 */       blockposm = new BlockPosM(j, k, l, this.level - 1);
/* 105 */       this.facings[i] = blockposm;
/*     */     } 
/*     */     
/* 108 */     return blockposm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos offset(EnumFacing facing, int n) {
/* 116 */     return (n == 1) ? offset(facing) : super.offset(facing, n);
/*     */   }
/*     */   
/*     */   private void update() {
/* 120 */     for (int i = 0; i < 6; i++) {
/* 121 */       BlockPosM blockposm = this.facings[i];
/*     */       
/* 123 */       if (blockposm != null) {
/* 124 */         EnumFacing enumfacing = EnumFacing.VALUES[i];
/* 125 */         int j = this.mx + enumfacing.getFrontOffsetX();
/* 126 */         int k = this.my + enumfacing.getFrontOffsetY();
/* 127 */         int l = this.mz + enumfacing.getFrontOffsetZ();
/* 128 */         blockposm.setXyz(j, k, l);
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     this.needsUpdate = false;
/*     */   }
/*     */   
/*     */   public BlockPos toImmutable() {
/* 136 */     return new BlockPos(this.mx, this.my, this.mz);
/*     */   }
/*     */   
/*     */   public static Iterable getAllInBoxMutable(BlockPos from, BlockPos to) {
/* 140 */     final BlockPos blockpos = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
/* 141 */     final BlockPos blockpos1 = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
/* 142 */     return new Iterable() {
/*     */         public Iterator iterator() {
/* 144 */           return (Iterator)new AbstractIterator() {
/* 145 */               private BlockPosM theBlockPosM = null;
/*     */               
/*     */               protected BlockPosM computeNext0() {
/* 148 */                 if (this.theBlockPosM == null) {
/* 149 */                   this.theBlockPosM = new BlockPosM(blockpos.getX(), blockpos.getY(), blockpos.getZ(), 3);
/* 150 */                   return this.theBlockPosM;
/* 151 */                 }  if (this.theBlockPosM.equals(blockpos1)) {
/* 152 */                   return (BlockPosM)endOfData();
/*     */                 }
/* 154 */                 int i = this.theBlockPosM.getX();
/* 155 */                 int j = this.theBlockPosM.getY();
/* 156 */                 int k = this.theBlockPosM.getZ();
/*     */                 
/* 158 */                 if (i < blockpos1.getX()) {
/* 159 */                   i++;
/* 160 */                 } else if (j < blockpos1.getY()) {
/* 161 */                   i = blockpos.getX();
/* 162 */                   j++;
/* 163 */                 } else if (k < blockpos1.getZ()) {
/* 164 */                   i = blockpos.getX();
/* 165 */                   j = blockpos.getY();
/* 166 */                   k++;
/*     */                 } 
/*     */                 
/* 169 */                 this.theBlockPosM.setXyz(i, j, k);
/* 170 */                 return this.theBlockPosM;
/*     */               }
/*     */ 
/*     */               
/*     */               protected Object computeNext() {
/* 175 */                 return computeNext0();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\BlockPosM.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */