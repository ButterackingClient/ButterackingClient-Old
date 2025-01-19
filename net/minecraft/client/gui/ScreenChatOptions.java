/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class ScreenChatOptions
/*    */   extends GuiScreen {
/*  9 */   private static final GameSettings.Options[] field_146399_a = new GameSettings.Options[] { GameSettings.Options.CHAT_VISIBILITY, GameSettings.Options.CHAT_COLOR, GameSettings.Options.CHAT_LINKS, GameSettings.Options.CHAT_OPACITY, GameSettings.Options.CHAT_LINKS_PROMPT, GameSettings.Options.CHAT_SCALE, GameSettings.Options.CHAT_HEIGHT_FOCUSED, GameSettings.Options.CHAT_HEIGHT_UNFOCUSED, GameSettings.Options.CHAT_WIDTH, GameSettings.Options.REDUCED_DEBUG_INFO };
/*    */   private final GuiScreen parentScreen;
/*    */   private final GameSettings game_settings;
/*    */   private String field_146401_i;
/*    */   
/*    */   public ScreenChatOptions(GuiScreen parentScreenIn, GameSettings gameSettingsIn) {
/* 15 */     this.parentScreen = parentScreenIn;
/* 16 */     this.game_settings = gameSettingsIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 24 */     int i = 0;
/* 25 */     this.field_146401_i = I18n.format("options.chat.title", new Object[0]); byte b; int j;
/*    */     GameSettings.Options[] arrayOfOptions;
/* 27 */     for (j = (arrayOfOptions = field_146399_a).length, b = 0; b < j; ) { GameSettings.Options gamesettings$options = arrayOfOptions[b];
/* 28 */       if (gamesettings$options.getEnumFloat()) {
/* 29 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), width / 2 - 155 + i % 2 * 160, height / 6 + 24 * (i >> 1), gamesettings$options));
/*    */       } else {
/* 31 */         this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), width / 2 - 155 + i % 2 * 160, height / 6 + 24 * (i >> 1), gamesettings$options, this.game_settings.getKeyBinding(gamesettings$options)));
/*    */       } 
/*    */       
/* 34 */       i++;
/*    */       b++; }
/*    */     
/* 37 */     this.buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 120, I18n.format("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 44 */     if (button.enabled) {
/* 45 */       if (button.id < 100 && button instanceof GuiOptionButton) {
/* 46 */         this.game_settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/* 47 */         button.displayString = this.game_settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*    */       } 
/*    */       
/* 50 */       if (button.id == 200) {
/* 51 */         this.mc.gameSettings.saveOptions();
/* 52 */         this.mc.displayGuiScreen(this.parentScreen);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 61 */     drawDefaultBackground();
/* 62 */     drawCenteredString(this.fontRendererObj, this.field_146401_i, width / 2, 20, 16777215);
/* 63 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\ScreenChatOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */