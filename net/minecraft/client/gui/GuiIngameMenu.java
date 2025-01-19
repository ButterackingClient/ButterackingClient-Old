/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.achievement.GuiAchievements;
/*     */ import net.minecraft.client.gui.achievement.GuiStats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.realms.RealmsBridge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiIngameMenu
/*     */   extends GuiScreen
/*     */ {
/*     */   private int field_146445_a;
/*     */   private int field_146444_f;
/*     */   
/*     */   public void initGui() {
/*  20 */     this.field_146445_a = 0;
/*  21 */     this.buttonList.clear();
/*  22 */     int i = -16;
/*  23 */     int j = 98;
/*  24 */     this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + i, I18n.format("menu.returnToMenu", new Object[0])));
/*     */     
/*  26 */     if (!this.mc.isIntegratedServerRunning()) {
/*  27 */       ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
/*     */     }
/*     */     
/*  30 */     this.buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24 + i, I18n.format("menu.returnToGame", new Object[0])));
/*  31 */     this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + i, 98, 20, I18n.format("menu.options", new Object[0])));
/*     */     GuiButton guibutton;
/*  33 */     this.buttonList.add(guibutton = new GuiButton(7, width / 2 + 2, height / 4 + 96 + i, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
/*  34 */     this.buttonList.add(new GuiButton(5, width / 2 - 100, height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements", new Object[0])));
/*  35 */     this.buttonList.add(new GuiButton(6, width / 2 + 2, height / 4 + 48 + i, 98, 20, I18n.format("gui.stats", new Object[0])));
/*  36 */     guibutton.enabled = (this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*     */     boolean flag, flag1;
/*  43 */     switch (button.id) {
/*     */       case 0:
/*  45 */         this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
/*     */ 
/*     */       
/*     */       case 1:
/*  49 */         flag = this.mc.isIntegratedServerRunning();
/*  50 */         flag1 = this.mc.isConnectedToRealms();
/*  51 */         button.enabled = false;
/*  52 */         this.mc.theWorld.sendQuittingDisconnectingPacket();
/*  53 */         this.mc.loadWorld(null);
/*     */         
/*  55 */         if (flag) {
/*  56 */           this.mc.displayGuiScreen(new GuiMainMenu());
/*  57 */         } else if (flag1) {
/*  58 */           RealmsBridge realmsbridge = new RealmsBridge();
/*  59 */           realmsbridge.switchToRealms(new GuiMainMenu());
/*     */         } else {
/*  61 */           this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
/*     */         } 
/*     */ 
/*     */       
/*     */       default:
/*     */         return;
/*     */ 
/*     */       
/*     */       case 4:
/*  70 */         this.mc.displayGuiScreen(null);
/*  71 */         this.mc.setIngameFocus();
/*     */ 
/*     */       
/*     */       case 5:
/*  75 */         this.mc.displayGuiScreen((GuiScreen)new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
/*     */ 
/*     */       
/*     */       case 6:
/*  79 */         this.mc.displayGuiScreen((GuiScreen)new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
/*     */       case 7:
/*     */         break;
/*     */     } 
/*  83 */     this.mc.displayGuiScreen(new GuiShareToLan(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  91 */     super.updateScreen();
/*  92 */     this.field_146444_f++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  99 */     drawDefaultBackground();
/* 100 */     drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), width / 2, 40, 16777215);
/* 101 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiIngameMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */