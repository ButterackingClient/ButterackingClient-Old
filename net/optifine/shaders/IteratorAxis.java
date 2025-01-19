/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.optifine.BlockPosM;
/*    */ 
/*    */ public class IteratorAxis
/*    */   implements Iterator<BlockPos> {
/*    */   private double yDelta;
/*    */   private double zDelta;
/*    */   private int xStart;
/*    */   private int xEnd;
/*    */   private double yStart;
/*    */   private double yEnd;
/*    */   private double zStart;
/*    */   private double zEnd;
/*    */   private int xNext;
/*    */   private double yNext;
/*    */   private double zNext;
/* 21 */   private BlockPosM pos = new BlockPosM(0, 0, 0);
/*    */   private boolean hasNext = false;
/*    */   
/*    */   public IteratorAxis(BlockPos posStart, BlockPos posEnd, double yDelta, double zDelta) {
/* 25 */     this.yDelta = yDelta;
/* 26 */     this.zDelta = zDelta;
/* 27 */     this.xStart = posStart.getX();
/* 28 */     this.xEnd = posEnd.getX();
/* 29 */     this.yStart = posStart.getY();
/* 30 */     this.yEnd = posEnd.getY() - 0.5D;
/* 31 */     this.zStart = posStart.getZ();
/* 32 */     this.zEnd = posEnd.getZ() - 0.5D;
/* 33 */     this.xNext = this.xStart;
/* 34 */     this.yNext = this.yStart;
/* 35 */     this.zNext = this.zStart;
/* 36 */     this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 40 */     return this.hasNext;
/*    */   }
/*    */   
/*    */   public BlockPos next() {
/* 44 */     if (!this.hasNext) {
/* 45 */       throw new NoSuchElementException();
/*    */     }
/* 47 */     this.pos.setXyz(this.xNext, this.yNext, this.zNext);
/* 48 */     nextPos();
/* 49 */     this.hasNext = (this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd);
/* 50 */     return (BlockPos)this.pos;
/*    */   }
/*    */ 
/*    */   
/*    */   private void nextPos() {
/* 55 */     this.zNext++;
/*    */     
/* 57 */     if (this.zNext >= this.zEnd) {
/* 58 */       this.zNext = this.zStart;
/* 59 */       this.yNext++;
/*    */       
/* 61 */       if (this.yNext >= this.yEnd) {
/* 62 */         this.yNext = this.yStart;
/* 63 */         this.yStart += this.yDelta;
/* 64 */         this.yEnd += this.yDelta;
/* 65 */         this.yNext = this.yStart;
/* 66 */         this.zStart += this.zDelta;
/* 67 */         this.zEnd += this.zDelta;
/* 68 */         this.zNext = this.zStart;
/* 69 */         this.xNext++;
/*    */         
/* 71 */         if (this.xNext >= this.xEnd);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void remove() {
/* 79 */     throw new RuntimeException("Not implemented");
/*    */   }
/*    */   
/*    */   public static void main(String[] args) throws Exception {
/* 83 */     BlockPos blockpos = new BlockPos(-2, 10, 20);
/* 84 */     BlockPos blockpos1 = new BlockPos(2, 12, 22);
/* 85 */     double d0 = -0.5D;
/* 86 */     double d1 = 0.5D;
/* 87 */     IteratorAxis iteratoraxis = new IteratorAxis(blockpos, blockpos1, d0, d1);
/* 88 */     System.out.println("Start: " + blockpos + ", end: " + blockpos1 + ", yDelta: " + d0 + ", zDelta: " + d1);
/*    */     
/* 90 */     while (iteratoraxis.hasNext()) {
/* 91 */       BlockPos blockpos2 = iteratoraxis.next();
/* 92 */       System.out.println((String)blockpos2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\IteratorAxis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */