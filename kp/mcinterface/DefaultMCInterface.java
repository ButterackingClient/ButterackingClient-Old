/*    */ package kp.mcinterface;
/*    */ 
/*    */ import client.Client;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ public class DefaultMCInterface
/*    */   implements IMinecraftInterface
/*    */ {
/*    */   public boolean isAllowedCharacter(char c) {
/* 11 */     return (c != (Client.getInstance()).chars && c >= ' ' && c != '');
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isKeyDown(int key) {
/* 16 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCtrlKeyDown() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isShiftKeyDown() {
/* 26 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAltKeyDown() {
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOnGuiChat() {
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public File getMinecraftDir() {
/* 41 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getEventButtonState() {
/* 46 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\kp\mcinterface\DefaultMCInterface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */