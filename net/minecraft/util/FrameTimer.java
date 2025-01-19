/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FrameTimer
/*    */ {
/*  7 */   private final long[] frames = new long[240];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int lastIndex;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int counter;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int index;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addFrame(long runningTime) {
/* 30 */     this.frames[this.index] = runningTime;
/* 31 */     this.index++;
/*    */     
/* 33 */     if (this.index == 240) {
/* 34 */       this.index = 0;
/*    */     }
/*    */     
/* 37 */     if (this.counter < 240) {
/* 38 */       this.lastIndex = 0;
/* 39 */       this.counter++;
/*    */     } else {
/* 41 */       this.lastIndex = parseIndex(this.index + 1);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLagometerValue(long time, int multiplier) {
/* 52 */     double d0 = time / 1.6666666E7D;
/* 53 */     return (int)(d0 * multiplier);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLastIndex() {
/* 60 */     return this.lastIndex;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIndex() {
/* 67 */     return this.index;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int parseIndex(int rawIndex) {
/* 76 */     return rawIndex % 240;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long[] getFrames() {
/* 83 */     return this.frames;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\FrameTimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */