/*    */ package net.optifine.expr;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ 
/*    */ public class TestExpressions {
/*    */   public static void main(String[] args) throws Exception {
/*  8 */     ExpressionParser expressionparser = new ExpressionParser(null);
/*    */     
/*    */     while (true) {
/*    */       try {
/* 12 */         InputStreamReader inputstreamreader = new InputStreamReader(System.in);
/* 13 */         BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 14 */         String s = bufferedreader.readLine();
/*    */         
/* 16 */         if (s.length() <= 0) {
/*    */           return;
/*    */         }
/*    */         
/* 20 */         IExpression iexpression = expressionparser.parse(s);
/*    */         
/* 22 */         if (iexpression instanceof IExpressionFloat) {
/* 23 */           IExpressionFloat iexpressionfloat = (IExpressionFloat)iexpression;
/* 24 */           float f = iexpressionfloat.eval();
/* 25 */           System.out.println(f);
/*    */         } 
/*    */         
/* 28 */         if (iexpression instanceof IExpressionBool) {
/* 29 */           IExpressionBool iexpressionbool = (IExpressionBool)iexpression;
/* 30 */           boolean flag = iexpressionbool.eval();
/* 31 */           System.out.println(flag);
/*    */         } 
/* 33 */       } catch (Exception exception) {
/* 34 */         exception.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\expr\TestExpressions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */