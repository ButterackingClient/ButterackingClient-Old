/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiGameOver
/*     */   extends GuiScreen
/*     */   implements GuiYesNoCallback
/*     */ {
/*     */   private int enableButtonsTimer;
/*     */   private boolean field_146346_f = false;
/*     */   
/*     */   public void initGui() {
/*  22 */     this.buttonList.clear();
/*     */     
/*  24 */     if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
/*  25 */       if (this.mc.isIntegratedServerRunning()) {
/*  26 */         this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96, I18n.format("deathScreen.deleteWorld", new Object[0])));
/*     */       } else {
/*  28 */         this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96, I18n.format("deathScreen.leaveServer", new Object[0])));
/*     */       } 
/*     */     } else {
/*  31 */       this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 72, I18n.format("deathScreen.respawn", new Object[0])));
/*  32 */       this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96, I18n.format("deathScreen.titleScreen", new Object[0])));
/*     */       
/*  34 */       if (this.mc.getSession() == null) {
/*  35 */         ((GuiButton)this.buttonList.get(1)).enabled = false;
/*     */       }
/*     */     } 
/*     */     
/*  39 */     for (GuiButton guibutton : this.buttonList) {
/*  40 */       guibutton.enabled = false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*     */     GuiYesNo guiyesno;
/*  55 */     switch (button.id) {
/*     */       case 0:
/*  57 */         this.mc.thePlayer.respawnPlayer();
/*  58 */         this.mc.displayGuiScreen(null);
/*     */         break;
/*     */       
/*     */       case 1:
/*  62 */         if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
/*  63 */           this.mc.displayGuiScreen(new GuiMainMenu()); break;
/*     */         } 
/*  65 */         guiyesno = new GuiYesNo(this, I18n.format("deathScreen.quit.confirm", new Object[0]), "", I18n.format("deathScreen.titleScreen", new Object[0]), I18n.format("deathScreen.respawn", new Object[0]), 0);
/*  66 */         this.mc.displayGuiScreen(guiyesno);
/*  67 */         guiyesno.setButtonDelay(20);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/*  73 */     if (result) {
/*  74 */       this.mc.theWorld.sendQuittingDisconnectingPacket();
/*  75 */       this.mc.loadWorld(null);
/*  76 */       this.mc.displayGuiScreen(new GuiMainMenu());
/*     */     } else {
/*  78 */       this.mc.thePlayer.respawnPlayer();
/*  79 */       this.mc.displayGuiScreen(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  87 */     drawGradientRect(0, 0, width, height, 1615855616, -1602211792);
/*  88 */     GlStateManager.pushMatrix();
/*  89 */     GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*  90 */     boolean flag = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
/*  91 */     String s = flag ? I18n.format("deathScreen.title.hardcore", new Object[0]) : I18n.format("deathScreen.title", new Object[0]);
/*  92 */     drawCenteredString(this.fontRendererObj, s, width / 2 / 2, 30, 16777215);
/*  93 */     GlStateManager.popMatrix();
/*     */     
/*  95 */     if (flag) {
/*  96 */       drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo", new Object[0]), width / 2, 144, 16777215);
/*     */     }
/*     */     
/*  99 */     drawCenteredString(this.fontRendererObj, String.valueOf(I18n.format("deathScreen.score", new Object[0])) + ": " + EnumChatFormatting.YELLOW + this.mc.thePlayer.getScore(), width / 2, 100, 16777215);
/* 100 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 114 */     super.updateScreen();
/* 115 */     this.enableButtonsTimer++;
/*     */     
/* 117 */     if (this.enableButtonsTimer == 20)
/* 118 */       for (GuiButton guibutton : this.buttonList)
/* 119 */         guibutton.enabled = true;  
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiGameOver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */