/*    */ package net.optifine.util;
/*    */ 
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class MathUtilsTest {
/*    */   public static void main(String[] args) throws Exception {
/*  7 */     OPER[] amathutilstest$oper = OPER.values();
/*    */     
/*  9 */     for (int i = 0; i < amathutilstest$oper.length; i++) {
/* 10 */       OPER mathutilstest$oper = amathutilstest$oper[i];
/* 11 */       dbg("******** " + mathutilstest$oper + " ***********");
/* 12 */       test(mathutilstest$oper, false);
/*    */     } 
/*    */   }
/*    */   private static void test(OPER oper, boolean fast) {
/*    */     double d0, d1;
/* 17 */     MathHelper.fastMath = fast;
/*    */ 
/*    */ 
/*    */     
/* 21 */     switch (oper) {
/*    */       case SIN:
/*    */       case COS:
/* 24 */         d0 = -MathHelper.PI;
/* 25 */         d1 = MathHelper.PI;
/*    */         break;
/*    */       
/*    */       case ASIN:
/*    */       case null:
/* 30 */         d0 = -1.0D;
/* 31 */         d1 = 1.0D;
/*    */         break;
/*    */       
/*    */       default:
/*    */         return;
/*    */     } 
/*    */     
/* 38 */     int i = 10;
/*    */     
/* 40 */     for (int j = 0; j <= i; j++) {
/* 41 */       float f, f1; double d2 = d0 + j * (d1 - d0) / i;
/*    */ 
/*    */ 
/*    */       
/* 45 */       switch (oper) {
/*    */         case SIN:
/* 47 */           f = (float)Math.sin(d2);
/* 48 */           f1 = MathHelper.sin((float)d2);
/*    */           break;
/*    */         
/*    */         case COS:
/* 52 */           f = (float)Math.cos(d2);
/* 53 */           f1 = MathHelper.cos((float)d2);
/*    */           break;
/*    */         
/*    */         case ASIN:
/* 57 */           f = (float)Math.asin(d2);
/* 58 */           f1 = MathUtils.asin((float)d2);
/*    */           break;
/*    */         
/*    */         case null:
/* 62 */           f = (float)Math.acos(d2);
/* 63 */           f1 = MathUtils.acos((float)d2);
/*    */           break;
/*    */         
/*    */         default:
/*    */           return;
/*    */       } 
/*    */       
/* 70 */       dbg(String.format("%.2f, Math: %f, Helper: %f, diff: %f", new Object[] { Double.valueOf(d2), Float.valueOf(f), Float.valueOf(f1), Float.valueOf(Math.abs(f - f1)) }));
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void dbg(String str) {
/* 75 */     System.out.println(str);
/*    */   }
/*    */   
/*    */   private enum OPER {
/* 79 */     SIN,
/* 80 */     COS,
/* 81 */     ASIN,
/* 82 */     ACOS;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifin\\util\MathUtilsTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */