/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.world.storage.ISaveFormat;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ public class GuiRenameWorld
/*    */   extends GuiScreen {
/*    */   private GuiScreen parentScreen;
/*    */   private GuiTextField field_146583_f;
/*    */   private final String saveName;
/*    */   
/*    */   public GuiRenameWorld(GuiScreen parentScreenIn, String saveNameIn) {
/* 16 */     this.parentScreen = parentScreenIn;
/* 17 */     this.saveName = saveNameIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 24 */     this.field_146583_f.updateCursorCounter();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 32 */     Keyboard.enableRepeatEvents(true);
/* 33 */     this.buttonList.clear();
/* 34 */     this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, I18n.format("selectWorld.renameButton", new Object[0])));
/* 35 */     this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
/* 36 */     ISaveFormat isaveformat = this.mc.getSaveLoader();
/* 37 */     WorldInfo worldinfo = isaveformat.getWorldInfo(this.saveName);
/* 38 */     String s = worldinfo.getWorldName();
/* 39 */     this.field_146583_f = new GuiTextField(2, this.fontRendererObj, width / 2 - 100, 60, 200, 20);
/* 40 */     this.field_146583_f.setFocused(true);
/* 41 */     this.field_146583_f.setText(s);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onGuiClosed() {
/* 48 */     Keyboard.enableRepeatEvents(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 55 */     if (button.enabled) {
/* 56 */       if (button.id == 1) {
/* 57 */         this.mc.displayGuiScreen(this.parentScreen);
/* 58 */       } else if (button.id == 0) {
/* 59 */         ISaveFormat isaveformat = this.mc.getSaveLoader();
/* 60 */         isaveformat.renameWorld(this.saveName, this.field_146583_f.getText().trim());
/* 61 */         this.mc.displayGuiScreen(this.parentScreen);
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 71 */     this.field_146583_f.textboxKeyTyped(typedChar, keyCode);
/* 72 */     ((GuiButton)this.buttonList.get(0)).enabled = (this.field_146583_f.getText().trim().length() > 0);
/*    */     
/* 74 */     if (keyCode == 28 || keyCode == 156) {
/* 75 */       actionPerformed(this.buttonList.get(0));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 83 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 84 */     this.field_146583_f.mouseClicked(mouseX, mouseY, mouseButton);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 91 */     drawDefaultBackground();
/* 92 */     drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.renameTitle", new Object[0]), width / 2, 20, 16777215);
/* 93 */     drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), width / 2 - 100, 47, 10526880);
/* 94 */     this.field_146583_f.drawTextBox();
/* 95 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiRenameWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */