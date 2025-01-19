/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.realms.RealmsButton;
/*    */ 
/*    */ public class GuiButtonRealmsProxy extends GuiButton {
/*    */   private RealmsButton realmsButton;
/*    */   
/*    */   public GuiButtonRealmsProxy(RealmsButton realmsButtonIn, int buttonId, int x, int y, String text) {
/* 10 */     super(buttonId, x, y, text);
/* 11 */     this.realmsButton = realmsButtonIn;
/*    */   }
/*    */   
/*    */   public GuiButtonRealmsProxy(RealmsButton realmsButtonIn, int buttonId, int x, int y, String text, int widthIn, int heightIn) {
/* 15 */     super(buttonId, x, y, widthIn, heightIn, text);
/* 16 */     this.realmsButton = realmsButtonIn;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 20 */     return this.id;
/*    */   }
/*    */   
/*    */   public boolean getEnabled() {
/* 24 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public void setEnabled(boolean isEnabled) {
/* 28 */     this.enabled = isEnabled;
/*    */   }
/*    */   
/*    */   public void setText(String text) {
/* 32 */     this.displayString = text;
/*    */   }
/*    */   
/*    */   public int getButtonWidth() {
/* 36 */     return super.getButtonWidth();
/*    */   }
/*    */   
/*    */   public int getPositionY() {
/* 40 */     return this.yPosition;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 48 */     if (super.mousePressed(mc, mouseX, mouseY)) {
/* 49 */       this.realmsButton.clicked(mouseX, mouseY);
/*    */     }
/*    */     
/* 52 */     return super.mousePressed(mc, mouseX, mouseY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseReleased(int mouseX, int mouseY) {
/* 59 */     this.realmsButton.released(mouseX, mouseY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
/* 66 */     this.realmsButton.renderBg(mouseX, mouseY);
/*    */   }
/*    */   
/*    */   public RealmsButton getRealmsButton() {
/* 70 */     return this.realmsButton;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHoverState(boolean mouseOver) {
/* 78 */     return this.realmsButton.getYImage(mouseOver);
/*    */   }
/*    */   
/*    */   public int func_154312_c(boolean p_154312_1_) {
/* 82 */     return super.getHoverState(p_154312_1_);
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 86 */     return this.height;
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiButtonRealmsProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */