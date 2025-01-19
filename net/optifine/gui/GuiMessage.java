/*    */ package net.optifine.gui;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class GuiMessage
/*    */   extends GuiScreen
/*    */ {
/*    */   private GuiScreen parentScreen;
/*    */   private String messageLine1;
/*    */   private String messageLine2;
/* 18 */   private final List listLines2 = Lists.newArrayList();
/*    */   protected String confirmButtonText;
/*    */   private int ticksUntilEnable;
/*    */   
/*    */   public GuiMessage(GuiScreen parentScreen, String line1, String line2) {
/* 23 */     this.parentScreen = parentScreen;
/* 24 */     this.messageLine1 = line1;
/* 25 */     this.messageLine2 = line2;
/* 26 */     this.confirmButtonText = I18n.format("gui.done", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 34 */     this.buttonList.add(new GuiOptionButton(0, width / 2 - 74, height / 6 + 96, this.confirmButtonText));
/* 35 */     this.listLines2.clear();
/* 36 */     this.listLines2.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, width - 50));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 43 */     Config.getMinecraft().displayGuiScreen(this.parentScreen);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 50 */     drawDefaultBackground();
/* 51 */     drawCenteredString(this.fontRendererObj, this.messageLine1, width / 2, 70, 16777215);
/* 52 */     int i = 90;
/*    */     
/* 54 */     for (Object s : this.listLines2) {
/* 55 */       drawCenteredString(this.fontRendererObj, (String)s, width / 2, i, 16777215);
/* 56 */       i += this.fontRendererObj.FONT_HEIGHT;
/*    */     } 
/*    */     
/* 59 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */   
/*    */   public void setButtonDelay(int ticksUntilEnable) {
/* 63 */     this.ticksUntilEnable = ticksUntilEnable;
/*    */     
/* 65 */     for (GuiButton guibutton : this.buttonList) {
/* 66 */       guibutton.enabled = false;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 74 */     super.updateScreen();
/*    */     
/* 76 */     if (--this.ticksUntilEnable == 0)
/* 77 */       for (GuiButton guibutton : this.buttonList)
/* 78 */         guibutton.enabled = true;  
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\gui\GuiMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */