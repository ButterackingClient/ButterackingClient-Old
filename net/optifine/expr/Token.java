/*    */ package net.optifine.expr;
/*    */ 
/*    */ public class Token {
/*    */   private TokenType type;
/*    */   private String text;
/*    */   
/*    */   public Token(TokenType type, String text) {
/*  8 */     this.type = type;
/*  9 */     this.text = text;
/*    */   }
/*    */   
/*    */   public TokenType getType() {
/* 13 */     return this.type;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 17 */     return this.text;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 21 */     return this.text;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\expr\Token.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */