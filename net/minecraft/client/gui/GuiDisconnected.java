/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ public class GuiDisconnected
/*    */   extends GuiScreen {
/*    */   private String reason;
/*    */   private IChatComponent message;
/*    */   private List<String> multilineMessage;
/*    */   private final GuiScreen parentScreen;
/*    */   private int field_175353_i;
/*    */   
/*    */   public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, IChatComponent chatComp) {
/* 17 */     this.parentScreen = screen;
/* 18 */     this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
/* 19 */     this.message = chatComp;
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
/* 34 */     this.buttonList.clear();
/* 35 */     this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), width - 50);
/* 36 */     this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
/* 37 */     this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 44 */     if (button.id == 0) {
/* 45 */       this.mc.displayGuiScreen(this.parentScreen);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 53 */     drawDefaultBackground();
/* 54 */     drawCenteredString(this.fontRendererObj, this.reason, width / 2, height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
/* 55 */     int i = height / 2 - this.field_175353_i / 2;
/*    */     
/* 57 */     if (this.multilineMessage != null) {
/* 58 */       for (String s : this.multilineMessage) {
/* 59 */         drawCenteredString(this.fontRendererObj, s, width / 2, i, 16777215);
/* 60 */         i += this.fontRendererObj.FONT_HEIGHT;
/*    */       } 
/*    */     }
/*    */     
/* 64 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiDisconnected.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */