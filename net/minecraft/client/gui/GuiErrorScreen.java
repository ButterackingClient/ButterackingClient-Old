/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ public class GuiErrorScreen
/*    */   extends GuiScreen {
/*    */   private String field_146313_a;
/*    */   private String field_146312_f;
/*    */   
/*    */   public GuiErrorScreen(String p_i46319_1_, String p_i46319_2_) {
/* 12 */     this.field_146313_a = p_i46319_1_;
/* 13 */     this.field_146312_f = p_i46319_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 21 */     super.initGui();
/* 22 */     this.buttonList.add(new GuiButton(0, width / 2 - 100, 140, I18n.format("gui.cancel", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 29 */     drawGradientRect(0, 0, width, height, -12574688, -11530224);
/* 30 */     drawCenteredString(this.fontRendererObj, this.field_146313_a, width / 2, 90, 16777215);
/* 31 */     drawCenteredString(this.fontRendererObj, this.field_146312_f, width / 2, 110, 16777215);
/* 32 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
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
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 46 */     this.mc.displayGuiScreen(null);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiErrorScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */