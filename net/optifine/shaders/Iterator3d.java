/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.optifine.BlockPosM;
/*     */ 
/*     */ public class Iterator3d
/*     */   implements Iterator<BlockPos> {
/*     */   private IteratorAxis iteratorAxis;
/*  11 */   private BlockPosM blockPos = new BlockPosM(0, 0, 0);
/*  12 */   private int axis = 0;
/*     */   private int kX;
/*     */   private int kY;
/*     */   private int kZ;
/*     */   private static final int AXIS_X = 0;
/*     */   private static final int AXIS_Y = 1;
/*     */   private static final int AXIS_Z = 2;
/*     */   
/*     */   public Iterator3d(BlockPos posStart, BlockPos posEnd, int width, int height) {
/*  21 */     boolean flag = (posStart.getX() > posEnd.getX());
/*  22 */     boolean flag1 = (posStart.getY() > posEnd.getY());
/*  23 */     boolean flag2 = (posStart.getZ() > posEnd.getZ());
/*  24 */     posStart = reverseCoord(posStart, flag, flag1, flag2);
/*  25 */     posEnd = reverseCoord(posEnd, flag, flag1, flag2);
/*  26 */     this.kX = flag ? -1 : 1;
/*  27 */     this.kY = flag1 ? -1 : 1;
/*  28 */     this.kZ = flag2 ? -1 : 1;
/*  29 */     Vec3 vec3 = new Vec3((posEnd.getX() - posStart.getX()), (posEnd.getY() - posStart.getY()), (posEnd.getZ() - posStart.getZ()));
/*  30 */     Vec3 vec31 = vec3.normalize();
/*  31 */     Vec3 vec32 = new Vec3(1.0D, 0.0D, 0.0D);
/*  32 */     double d0 = vec31.dotProduct(vec32);
/*  33 */     double d1 = Math.abs(d0);
/*  34 */     Vec3 vec33 = new Vec3(0.0D, 1.0D, 0.0D);
/*  35 */     double d2 = vec31.dotProduct(vec33);
/*  36 */     double d3 = Math.abs(d2);
/*  37 */     Vec3 vec34 = new Vec3(0.0D, 0.0D, 1.0D);
/*  38 */     double d4 = vec31.dotProduct(vec34);
/*  39 */     double d5 = Math.abs(d4);
/*     */     
/*  41 */     if (d5 >= d3 && d5 >= d1) {
/*  42 */       this.axis = 2;
/*  43 */       BlockPos blockpos3 = new BlockPos(posStart.getZ(), posStart.getY() - width, posStart.getX() - height);
/*  44 */       BlockPos blockpos5 = new BlockPos(posEnd.getZ(), posStart.getY() + width + 1, posStart.getX() + height + 1);
/*  45 */       int k = posEnd.getZ() - posStart.getZ();
/*  46 */       double d9 = (posEnd.getY() - posStart.getY()) / 1.0D * k;
/*  47 */       double d11 = (posEnd.getX() - posStart.getX()) / 1.0D * k;
/*  48 */       this.iteratorAxis = new IteratorAxis(blockpos3, blockpos5, d9, d11);
/*  49 */     } else if (d3 >= d1 && d3 >= d5) {
/*  50 */       this.axis = 1;
/*  51 */       BlockPos blockpos2 = new BlockPos(posStart.getY(), posStart.getX() - width, posStart.getZ() - height);
/*  52 */       BlockPos blockpos4 = new BlockPos(posEnd.getY(), posStart.getX() + width + 1, posStart.getZ() + height + 1);
/*  53 */       int j = posEnd.getY() - posStart.getY();
/*  54 */       double d8 = (posEnd.getX() - posStart.getX()) / 1.0D * j;
/*  55 */       double d10 = (posEnd.getZ() - posStart.getZ()) / 1.0D * j;
/*  56 */       this.iteratorAxis = new IteratorAxis(blockpos2, blockpos4, d8, d10);
/*     */     } else {
/*  58 */       this.axis = 0;
/*  59 */       BlockPos blockpos = new BlockPos(posStart.getX(), posStart.getY() - width, posStart.getZ() - height);
/*  60 */       BlockPos blockpos1 = new BlockPos(posEnd.getX(), posStart.getY() + width + 1, posStart.getZ() + height + 1);
/*  61 */       int i = posEnd.getX() - posStart.getX();
/*  62 */       double d6 = (posEnd.getY() - posStart.getY()) / 1.0D * i;
/*  63 */       double d7 = (posEnd.getZ() - posStart.getZ()) / 1.0D * i;
/*  64 */       this.iteratorAxis = new IteratorAxis(blockpos, blockpos1, d6, d7);
/*     */     } 
/*     */   }
/*     */   
/*     */   private BlockPos reverseCoord(BlockPos pos, boolean revX, boolean revY, boolean revZ) {
/*  69 */     if (revX) {
/*  70 */       pos = new BlockPos(-pos.getX(), pos.getY(), pos.getZ());
/*     */     }
/*     */     
/*  73 */     if (revY) {
/*  74 */       pos = new BlockPos(pos.getX(), -pos.getY(), pos.getZ());
/*     */     }
/*     */     
/*  77 */     if (revZ) {
/*  78 */       pos = new BlockPos(pos.getX(), pos.getY(), -pos.getZ());
/*     */     }
/*     */     
/*  81 */     return pos;
/*     */   }
/*     */   
/*     */   public boolean hasNext() {
/*  85 */     return this.iteratorAxis.hasNext();
/*     */   }
/*     */   
/*     */   public BlockPos next() {
/*  89 */     BlockPos blockpos = this.iteratorAxis.next();
/*     */     
/*  91 */     switch (this.axis) {
/*     */       case 0:
/*  93 */         this.blockPos.setXyz(blockpos.getX() * this.kX, blockpos.getY() * this.kY, blockpos.getZ() * this.kZ);
/*  94 */         return (BlockPos)this.blockPos;
/*     */       
/*     */       case 1:
/*  97 */         this.blockPos.setXyz(blockpos.getY() * this.kX, blockpos.getX() * this.kY, blockpos.getZ() * this.kZ);
/*  98 */         return (BlockPos)this.blockPos;
/*     */       
/*     */       case 2:
/* 101 */         this.blockPos.setXyz(blockpos.getZ() * this.kX, blockpos.getY() * this.kY, blockpos.getX() * this.kZ);
/* 102 */         return (BlockPos)this.blockPos;
/*     */     } 
/*     */     
/* 105 */     this.blockPos.setXyz(blockpos.getX() * this.kX, blockpos.getY() * this.kY, blockpos.getZ() * this.kZ);
/* 106 */     return (BlockPos)this.blockPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove() {
/* 111 */     throw new RuntimeException("Not supported");
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 115 */     BlockPos blockpos = new BlockPos(10, 20, 30);
/* 116 */     BlockPos blockpos1 = new BlockPos(30, 40, 20);
/* 117 */     Iterator3d iterator3d = new Iterator3d(blockpos, blockpos1, 1, 1);
/*     */     
/* 119 */     while (iterator3d.hasNext()) {
/* 120 */       BlockPos blockpos2 = iterator3d.next();
/* 121 */       System.out.println((String)blockpos2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\Iterator3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */