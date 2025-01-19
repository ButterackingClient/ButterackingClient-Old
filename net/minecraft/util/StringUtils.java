/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class StringUtils {
/*  6 */   private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String ticksToElapsedTime(int ticks) {
/* 12 */     int i = ticks / 20;
/* 13 */     int j = i / 60;
/* 14 */     i %= 60;
/* 15 */     return (i < 10) ? (String.valueOf(j) + ":0" + i) : (String.valueOf(j) + ":" + i);
/*    */   }
/*    */   
/*    */   public static String stripControlCodes(String text) {
/* 19 */     return patternControlCode.matcher(text).replaceAll("");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isNullOrEmpty(String string) {
/* 26 */     return org.apache.commons.lang3.StringUtils.isEmpty(string);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\StringUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */