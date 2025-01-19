/*    */ package net.optifine.expr;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ParametersVariable implements IParameters {
/*    */   private ExpressionType[] first;
/*    */   private ExpressionType[] repeat;
/*    */   private ExpressionType[] last;
/*    */   private int maxCount;
/* 12 */   private static final ExpressionType[] EMPTY = new ExpressionType[0];
/*    */   
/*    */   public ParametersVariable() {
/* 15 */     this(null, null, null);
/*    */   }
/*    */   
/*    */   public ParametersVariable(ExpressionType[] first, ExpressionType[] repeat, ExpressionType[] last) {
/* 19 */     this(first, repeat, last, 2147483647);
/*    */   }
/*    */   
/*    */   public ParametersVariable(ExpressionType[] first, ExpressionType[] repeat, ExpressionType[] last, int maxCount) {
/* 23 */     this.maxCount = Integer.MAX_VALUE;
/* 24 */     this.first = normalize(first);
/* 25 */     this.repeat = normalize(repeat);
/* 26 */     this.last = normalize(last);
/* 27 */     this.maxCount = maxCount;
/*    */   }
/*    */   
/*    */   private static ExpressionType[] normalize(ExpressionType[] exprs) {
/* 31 */     return (exprs == null) ? EMPTY : exprs;
/*    */   }
/*    */   
/*    */   public ExpressionType[] getFirst() {
/* 35 */     return this.first;
/*    */   }
/*    */   
/*    */   public ExpressionType[] getRepeat() {
/* 39 */     return this.repeat;
/*    */   }
/*    */   
/*    */   public ExpressionType[] getLast() {
/* 43 */     return this.last;
/*    */   }
/*    */   
/*    */   public int getCountRepeat() {
/* 47 */     return (this.first == null) ? 0 : this.first.length;
/*    */   }
/*    */   
/*    */   public ExpressionType[] getParameterTypes(IExpression[] arguments) {
/* 51 */     int i = this.first.length + this.last.length;
/* 52 */     int j = arguments.length - i;
/* 53 */     int k = 0;
/*    */     
/* 55 */     for (int l = 0; l + this.repeat.length <= j && i + l + this.repeat.length <= this.maxCount; l += this.repeat.length) {
/* 56 */       k++;
/*    */     }
/*    */     
/* 59 */     List<ExpressionType> list = new ArrayList<>();
/* 60 */     list.addAll(Arrays.asList(this.first));
/*    */     
/* 62 */     for (int i1 = 0; i1 < k; i1++) {
/* 63 */       list.addAll(Arrays.asList(this.repeat));
/*    */     }
/*    */     
/* 66 */     list.addAll(Arrays.asList(this.last));
/* 67 */     ExpressionType[] aexpressiontype = list.<ExpressionType>toArray(new ExpressionType[list.size()]);
/* 68 */     return aexpressiontype;
/*    */   }
/*    */   
/*    */   public ParametersVariable first(ExpressionType... first) {
/* 72 */     return new ParametersVariable(first, this.repeat, this.last);
/*    */   }
/*    */   
/*    */   public ParametersVariable repeat(ExpressionType... repeat) {
/* 76 */     return new ParametersVariable(this.first, repeat, this.last);
/*    */   }
/*    */   
/*    */   public ParametersVariable last(ExpressionType... last) {
/* 80 */     return new ParametersVariable(this.first, this.repeat, last);
/*    */   }
/*    */   
/*    */   public ParametersVariable maxCount(int maxCount) {
/* 84 */     return new ParametersVariable(this.first, this.repeat, this.last, maxCount);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\expr\ParametersVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */