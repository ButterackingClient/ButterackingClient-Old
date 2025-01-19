/*   */ package net.minecraft.command;
/*   */ 
/*   */ public class SyntaxErrorException extends CommandException {
/*   */   public SyntaxErrorException() {
/* 5 */     this("commands.generic.snytax", new Object[0]);
/*   */   }
/*   */   
/*   */   public SyntaxErrorException(String message, Object... replacements) {
/* 9 */     super(message, replacements);
/*   */   }
/*   */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\SyntaxErrorException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */