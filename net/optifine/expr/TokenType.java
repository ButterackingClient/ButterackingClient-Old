/*    */ package net.optifine.expr;
/*    */ 
/*    */ public enum TokenType {
/*  4 */   IDENTIFIER("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_:."),
/*  5 */   NUMBER("0123456789", "0123456789."),
/*  6 */   OPERATOR("+-*/%!&|<>=", "&|="),
/*  7 */   COMMA(","),
/*  8 */   BRACKET_OPEN("("),
/*  9 */   BRACKET_CLOSE(")");
/*    */   private String charsFirst;
/*    */   
/*    */   static {
/* 13 */     VALUES = values();
/*    */   }
/*    */   
/*    */   private String charsNext;
/*    */   public static final TokenType[] VALUES;
/*    */   
/*    */   TokenType(String charsFirst, String charsNext) {
/* 20 */     this.charsFirst = charsFirst;
/* 21 */     this.charsNext = charsNext;
/*    */   }
/*    */   
/*    */   public String getCharsFirst() {
/* 25 */     return this.charsFirst;
/*    */   }
/*    */   
/*    */   public String getCharsNext() {
/* 29 */     return this.charsNext;
/*    */   }
/*    */   
/*    */   public static TokenType getTypeByFirstChar(char ch) {
/* 33 */     for (int i = 0; i < VALUES.length; i++) {
/* 34 */       TokenType tokentype = VALUES[i];
/*    */       
/* 36 */       if (tokentype.getCharsFirst().indexOf(ch) >= 0) {
/* 37 */         return tokentype;
/*    */       }
/*    */     } 
/*    */     
/* 41 */     return null;
/*    */   }
/*    */   
/*    */   public boolean hasCharNext(char ch) {
/* 45 */     return (this.charsNext.indexOf(ch) >= 0);
/*    */   }
/*    */   
/*    */   private static class Const {
/*    */     static final String ALPHAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
/*    */     static final String DIGITS = "0123456789";
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\expr\TokenType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */