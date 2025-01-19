/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class ChatComponentTranslationFormatException extends IllegalArgumentException {
/*    */   public ChatComponentTranslationFormatException(ChatComponentTranslation component, String message) {
/*  5 */     super(String.format("Error parsing: %s: %s", new Object[] { component, message }));
/*    */   }
/*    */   
/*    */   public ChatComponentTranslationFormatException(ChatComponentTranslation component, int index) {
/*  9 */     super(String.format("Invalid index %d requested for %s", new Object[] { Integer.valueOf(index), component }));
/*    */   }
/*    */   
/*    */   public ChatComponentTranslationFormatException(ChatComponentTranslation component, Throwable cause) {
/* 13 */     super(String.format("Error while parsing: %s", new Object[] { component }), cause);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraf\\util\ChatComponentTranslationFormatException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */