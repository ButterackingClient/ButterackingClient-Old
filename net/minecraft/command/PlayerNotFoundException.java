/*   */ package net.minecraft.command;
/*   */ 
/*   */ public class PlayerNotFoundException extends CommandException {
/*   */   public PlayerNotFoundException() {
/* 5 */     this("commands.generic.player.notFound", new Object[0]);
/*   */   }
/*   */   
/*   */   public PlayerNotFoundException(String message, Object... replacements) {
/* 9 */     super(message, replacements);
/*   */   }
/*   */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\PlayerNotFoundException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */