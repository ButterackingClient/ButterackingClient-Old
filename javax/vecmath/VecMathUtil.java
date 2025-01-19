/*    */ package javax.vecmath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class VecMathUtil
/*    */ {
/*    */   static final long hashLongBits(long hash, long l) {
/* 41 */     hash *= 31L;
/* 42 */     return hash + l;
/*    */   }
/*    */   
/*    */   static final long hashFloatBits(long hash, float f) {
/* 46 */     hash *= 31L;
/*    */     
/* 48 */     if (f == 0.0F) {
/* 49 */       return hash;
/*    */     }
/* 51 */     return hash + Float.floatToIntBits(f);
/*    */   }
/*    */   
/*    */   static final long hashDoubleBits(long hash, double d) {
/* 55 */     hash *= 31L;
/*    */     
/* 57 */     if (d == 0.0D) {
/* 58 */       return hash;
/*    */     }
/* 60 */     return hash + Double.doubleToLongBits(d);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static final int hashFinish(long hash) {
/* 67 */     return (int)(hash ^ hash >> 32L);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\javax\vecmath\VecMathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */