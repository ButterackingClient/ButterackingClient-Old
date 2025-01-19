/*    */ package net.optifine.expr;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PushbackReader;
/*    */ import java.io.Reader;
/*    */ import java.io.StringReader;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class TokenParser {
/*    */   public static Token[] parse(String str) throws IOException, ParseException {
/* 12 */     Reader reader = new StringReader(str);
/* 13 */     PushbackReader pushbackreader = new PushbackReader(reader);
/* 14 */     List<Token> list = new ArrayList<>();
/*    */     
/*    */     while (true) {
/* 17 */       int i = pushbackreader.read();
/*    */       
/* 19 */       if (i < 0) {
/* 20 */         Token[] atoken = list.<Token>toArray(new Token[list.size()]);
/* 21 */         return atoken;
/*    */       } 
/*    */       
/* 24 */       char c0 = (char)i;
/*    */       
/* 26 */       if (!Character.isWhitespace(c0)) {
/* 27 */         TokenType tokentype = TokenType.getTypeByFirstChar(c0);
/*    */         
/* 29 */         if (tokentype == null) {
/* 30 */           throw new ParseException("Invalid character: '" + c0 + "', in: " + str);
/*    */         }
/*    */         
/* 33 */         Token token = readToken(c0, tokentype, pushbackreader);
/* 34 */         list.add(token);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private static Token readToken(char chFirst, TokenType type, PushbackReader pr) throws IOException {
/* 40 */     StringBuffer stringbuffer = new StringBuffer();
/* 41 */     stringbuffer.append(chFirst);
/*    */     
/*    */     while (true) {
/* 44 */       int i = pr.read();
/*    */       
/* 46 */       if (i < 0) {
/*    */         break;
/*    */       }
/*    */       
/* 50 */       char c0 = (char)i;
/*    */       
/* 52 */       if (!type.hasCharNext(c0)) {
/* 53 */         pr.unread(c0);
/*    */         
/*    */         break;
/*    */       } 
/* 57 */       stringbuffer.append(c0);
/*    */     } 
/*    */     
/* 60 */     return new Token(type, stringbuffer.toString());
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\expr\TokenParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */