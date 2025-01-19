/*    */ package org.json;
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
/*    */ public class HTTPTokener
/*    */   extends JSONTokener
/*    */ {
/*    */   public HTTPTokener(String string) {
/* 20 */     super(string);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String nextToken() throws JSONException {
/* 32 */     StringBuilder sb = new StringBuilder();
/*    */     while (true) {
/* 34 */       char c = next();
/* 35 */       if (!Character.isWhitespace(c)) {
/* 36 */         if (c == '"' || c == '\'') {
/* 37 */           char q = c;
/*    */           while (true) {
/* 39 */             c = next();
/* 40 */             if (c < ' ') {
/* 41 */               throw syntaxError("Unterminated string.");
/*    */             }
/* 43 */             if (c == q) {
/* 44 */               return sb.toString();
/*    */             }
/* 46 */             sb.append(c);
/*    */           }  break;
/*    */         } 
/*    */         while (true) {
/* 50 */           if (c == '\000' || Character.isWhitespace(c)) {
/* 51 */             return sb.toString();
/*    */           }
/* 53 */           sb.append(c);
/* 54 */           c = next();
/*    */         } 
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\org\json\HTTPTokener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */