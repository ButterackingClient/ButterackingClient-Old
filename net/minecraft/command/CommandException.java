/*    */ package net.minecraft.command;
/*    */ 
/*    */ public class CommandException extends Exception {
/*    */   private final Object[] errorObjects;
/*    */   
/*    */   public CommandException(String message, Object... objects) {
/*  7 */     super(message);
/*  8 */     this.errorObjects = objects;
/*    */   }
/*    */   
/*    */   public Object[] getErrorObjects() {
/* 12 */     return this.errorObjects;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\command\CommandException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */