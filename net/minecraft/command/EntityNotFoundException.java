/*   */ package net.minecraft.command;
/*   */ 
/*   */ public class EntityNotFoundException extends CommandException {
/*   */   public EntityNotFoundException() {
/* 5 */     this("commands.generic.entity.notFound", new Object[0]);
/*   */   }
/*   */   
/*   */   public EntityNotFoundException(String p_i46035_1_, Object... p_i46035_2_) {
/* 9 */     super(p_i46035_1_, p_i46035_2_);
/*   */   }
/*   */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\EntityNotFoundException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */