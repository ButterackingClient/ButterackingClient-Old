/*    */ package net.optifine.util;
/*    */ 
/*    */ public class IntArray {
/*  4 */   private int[] array = null;
/*  5 */   private int position = 0;
/*  6 */   private int limit = 0;
/*    */   
/*    */   public IntArray(int size) {
/*  9 */     this.array = new int[size];
/*    */   }
/*    */   
/*    */   public void put(int x) {
/* 13 */     this.array[this.position] = x;
/* 14 */     this.position++;
/*    */     
/* 16 */     if (this.limit < this.position) {
/* 17 */       this.limit = this.position;
/*    */     }
/*    */   }
/*    */   
/*    */   public void put(int pos, int x) {
/* 22 */     this.array[pos] = x;
/*    */     
/* 24 */     if (this.limit < pos) {
/* 25 */       this.limit = pos;
/*    */     }
/*    */   }
/*    */   
/*    */   public void position(int pos) {
/* 30 */     this.position = pos;
/*    */   }
/*    */   
/*    */   public void put(int[] ints) {
/* 34 */     int i = ints.length;
/*    */     
/* 36 */     for (int j = 0; j < i; j++) {
/* 37 */       this.array[this.position] = ints[j];
/* 38 */       this.position++;
/*    */     } 
/*    */     
/* 41 */     if (this.limit < this.position) {
/* 42 */       this.limit = this.position;
/*    */     }
/*    */   }
/*    */   
/*    */   public int get(int pos) {
/* 47 */     return this.array[pos];
/*    */   }
/*    */   
/*    */   public int[] getArray() {
/* 51 */     return this.array;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 55 */     this.position = 0;
/* 56 */     this.limit = 0;
/*    */   }
/*    */   
/*    */   public int getLimit() {
/* 60 */     return this.limit;
/*    */   }
/*    */   
/*    */   public int getPosition() {
/* 64 */     return this.position;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\IntArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */