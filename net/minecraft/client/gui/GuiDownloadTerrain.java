/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*    */ import net.optifine.CustomLoadingScreen;
/*    */ import net.optifine.CustomLoadingScreens;
/*    */ 
/*    */ public class GuiDownloadTerrain extends GuiScreen {
/*    */   private NetHandlerPlayClient netHandlerPlayClient;
/*    */   private int progress;
/* 14 */   private CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();
/*    */   
/*    */   public GuiDownloadTerrain(NetHandlerPlayClient netHandler) {
/* 17 */     this.netHandlerPlayClient = netHandler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 32 */     this.buttonList.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 39 */     this.progress++;
/*    */     
/* 41 */     if (this.progress % 20 == 0) {
/* 42 */       this.netHandlerPlayClient.addToSendQueue((Packet)new C00PacketKeepAlive());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 50 */     if (this.customLoadingScreen != null) {
/* 51 */       this.customLoadingScreen.drawBackground(width, height);
/*    */     } else {
/* 53 */       drawBackground(0);
/*    */     } 
/*    */     
/* 56 */     drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), width / 2, height / 2 - 50, 16777215);
/* 57 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean doesGuiPauseGame() {
/* 64 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiDownloadTerrain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */