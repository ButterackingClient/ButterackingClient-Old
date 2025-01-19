/*    */ package client.hud;
/*    */ 
/*    */ import client.Client;
/*    */ import client.gui.click.ClickGui;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.util.StatCollector;
/*    */ 
/*    */ 
/*    */ public class HUDConfigScreen
/*    */   extends GuiScreen
/*    */ {
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 15 */     drawDefaultBackground();
/* 16 */     for (HudMod m : (Client.getInstance()).hudManager.hudMods) {
/* 17 */       if (m.isEnabled()) {
/* 18 */         m.renderDummy(mouseX, mouseY);
/*    */       }
/*    */     } 
/* 21 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */   
/*    */   public void initGui() {
/* 25 */     super.initGui();
/* 26 */     this.buttonList.add(new GuiButton(1, width / 2 - 50, height / 5 * 4, 100, 20, StatCollector.translateToLocal("key.clickgui")));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 31 */     super.actionPerformed(button);
/* 32 */     if (button.id == 1) {
/* 33 */       this.mc.displayGuiScreen((GuiScreen)new ClickGui());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean doesGuiPauseGame() {
/* 39 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\client\hud\HUDConfigScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */