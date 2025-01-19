/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiConfirmOpenLink
/*    */   extends GuiYesNo
/*    */ {
/*    */   private final String openLinkWarning;
/*    */   private final String copyLinkButtonText;
/*    */   private final String linkText;
/*    */   private boolean showSecurityWarning = true;
/*    */   
/*    */   public GuiConfirmOpenLink(GuiYesNoCallback p_i1084_1_, String linkTextIn, int p_i1084_3_, boolean p_i1084_4_) {
/* 21 */     super(p_i1084_1_, I18n.format(p_i1084_4_ ? "chat.link.confirmTrusted" : "chat.link.confirm", new Object[0]), linkTextIn, p_i1084_3_);
/* 22 */     this.confirmButtonText = I18n.format(p_i1084_4_ ? "chat.link.open" : "gui.yes", new Object[0]);
/* 23 */     this.cancelButtonText = I18n.format(p_i1084_4_ ? "gui.cancel" : "gui.no", new Object[0]);
/* 24 */     this.copyLinkButtonText = I18n.format("chat.copy", new Object[0]);
/* 25 */     this.openLinkWarning = I18n.format("chat.link.warning", new Object[0]);
/* 26 */     this.linkText = linkTextIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 34 */     super.initGui();
/* 35 */     this.buttonList.clear();
/* 36 */     this.buttonList.add(new GuiButton(0, width / 2 - 50 - 105, height / 6 + 96, 100, 20, this.confirmButtonText));
/* 37 */     this.buttonList.add(new GuiButton(2, width / 2 - 50, height / 6 + 96, 100, 20, this.copyLinkButtonText));
/* 38 */     this.buttonList.add(new GuiButton(1, width / 2 - 50 + 105, height / 6 + 96, 100, 20, this.cancelButtonText));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 45 */     if (button.id == 2) {
/* 46 */       copyLinkToClipboard();
/*    */     }
/*    */     
/* 49 */     this.parentScreen.confirmClicked((button.id == 0), this.parentButtonClickedId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void copyLinkToClipboard() {
/* 56 */     setClipboardString(this.linkText);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 63 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */     
/* 65 */     if (this.showSecurityWarning) {
/* 66 */       drawCenteredString(this.fontRendererObj, this.openLinkWarning, width / 2, 110, 16764108);
/*    */     }
/*    */   }
/*    */   
/*    */   public void disableSecurityWarning() {
/* 71 */     this.showSecurityWarning = false;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiConfirmOpenLink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */