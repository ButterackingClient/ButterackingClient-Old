/*    */ package net.optifine.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiChat;
/*    */ import net.minecraft.client.gui.GuiVideoSettings;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class GuiChatOF extends GuiChat {
/*    */   private static final String CMD_RELOAD_SHADERS = "/reloadShaders";
/*    */   private static final String CMD_RELOAD_CHUNKS = "/reloadChunks";
/*    */   
/*    */   public GuiChatOF(GuiChat guiChat) {
/* 13 */     super(GuiVideoSettings.getGuiChatText(guiChat));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendChatMessage(String msg) {
/* 20 */     if (checkCustomCommand(msg)) {
/* 21 */       this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
/*    */     } else {
/* 23 */       super.sendChatMessage(msg);
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean checkCustomCommand(String msg) {
/* 28 */     if (msg == null) {
/* 29 */       return false;
/*    */     }
/* 31 */     msg = msg.trim();
/*    */     
/* 33 */     if (msg.equals("/reloadShaders")) {
/* 34 */       if (Config.isShaders()) {
/* 35 */         Shaders.uninit();
/* 36 */         Shaders.loadShaderPack();
/*    */       } 
/*    */       
/* 39 */       return true;
/* 40 */     }  if (msg.equals("/reloadChunks")) {
/* 41 */       this.mc.renderGlobal.loadRenderers();
/* 42 */       return true;
/*    */     } 
/* 44 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\gui\GuiChatOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */