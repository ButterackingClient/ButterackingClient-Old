/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*    */ 
/*    */ 
/*    */ public class GuiSleepMP
/*    */   extends GuiChat
/*    */ {
/*    */   public void initGui() {
/* 15 */     super.initGui();
/* 16 */     this.buttonList.add(new GuiButton(1, width / 2 - 100, height - 40, I18n.format("multiplayer.stopSleeping", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 24 */     if (keyCode == 1) {
/* 25 */       wakeFromSleep();
/* 26 */     } else if (keyCode != 28 && keyCode != 156) {
/* 27 */       super.keyTyped(typedChar, keyCode);
/*    */     } else {
/* 29 */       String s = this.inputField.getText().trim();
/*    */       
/* 31 */       if (!s.isEmpty()) {
/* 32 */         this.mc.thePlayer.sendChatMessage(s);
/*    */       }
/*    */       
/* 35 */       this.inputField.setText("");
/* 36 */       this.mc.ingameGUI.getChatGUI().resetScroll();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 44 */     if (button.id == 1) {
/* 45 */       wakeFromSleep();
/*    */     } else {
/* 47 */       super.actionPerformed(button);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void wakeFromSleep() {
/* 52 */     NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
/* 53 */     nethandlerplayclient.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SLEEPING));
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiSleepMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */