/*   */ package net.minecraft.command;
/*   */ 
/*   */ public class NumberInvalidException extends CommandException {
/*   */   public NumberInvalidException() {
/* 5 */     this("commands.generic.num.invalid", new Object[0]);
/*   */   }
/*   */   
/*   */   public NumberInvalidException(String message, Object... replacements) {
/* 9 */     super(message, replacements);
/*   */   }
/*   */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\NumberInvalidException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */