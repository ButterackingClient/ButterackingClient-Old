/*    */ package net.optifine.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.GuiVideoSettings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiScreenOF
/*    */   extends GuiScreen
/*    */ {
/*    */   protected void actionPerformedRightClick(GuiButton button) throws IOException {}
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 18 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*    */     
/* 20 */     if (mouseButton == 1) {
/* 21 */       GuiButton guibutton = getSelectedButton(mouseX, mouseY, this.buttonList);
/*    */       
/* 23 */       if (guibutton != null && guibutton.enabled) {
/* 24 */         guibutton.playPressSound(this.mc.getSoundHandler());
/* 25 */         actionPerformedRightClick(guibutton);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static GuiButton getSelectedButton(int x, int y, List<GuiButton> listButtons) {
/* 31 */     for (int i = 0; i < listButtons.size(); i++) {
/* 32 */       GuiButton guibutton = listButtons.get(i);
/*    */       
/* 34 */       if (guibutton.visible) {
/* 35 */         int j = GuiVideoSettings.getButtonWidth(guibutton);
/* 36 */         int k = GuiVideoSettings.getButtonHeight(guibutton);
/*    */         
/* 38 */         if (x >= guibutton.xPosition && y >= guibutton.yPosition && x < guibutton.xPosition + j && y < guibutton.yPosition + k) {
/* 39 */           return guibutton;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\gui\GuiScreenOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */