/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatAllowedCharacters
/*    */ {
/*  7 */   public static final char[] allowedCharactersArray = new char[] { '/', '\n', '\r', '\t', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };
/*    */   
/*    */   public static boolean isAllowedCharacter(char character) {
/* 10 */     return (character != '§' && character >= ' ' && character != '');
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String filterAllowedCharacters(String input) {
/* 17 */     StringBuilder stringbuilder = new StringBuilder(); byte b; int i;
/*    */     char[] arrayOfChar;
/* 19 */     for (i = (arrayOfChar = input.toCharArray()).length, b = 0; b < i; ) { char c0 = arrayOfChar[b];
/* 20 */       if (isAllowedCharacter(c0)) {
/* 21 */         stringbuilder.append(c0);
/*    */       }
/*    */       b++; }
/*    */     
/* 25 */     return stringbuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ChatAllowedCharacters.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */