/*    */ package net.optifine.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class GuiQualitySettingsOF extends GuiScreen {
/*    */   private GuiScreen prevScreen;
/*    */   protected String title;
/*    */   private GameSettings settings;
/* 14 */   private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.MIPMAP_TYPE, GameSettings.Options.AF_LEVEL, GameSettings.Options.AA_LEVEL, GameSettings.Options.CLEAR_WATER, GameSettings.Options.RANDOM_ENTITIES, GameSettings.Options.BETTER_GRASS, GameSettings.Options.BETTER_SNOW, GameSettings.Options.CUSTOM_FONTS, GameSettings.Options.CUSTOM_COLORS, GameSettings.Options.CONNECTED_TEXTURES, GameSettings.Options.NATURAL_TEXTURES, GameSettings.Options.CUSTOM_SKY, GameSettings.Options.CUSTOM_ITEMS, GameSettings.Options.CUSTOM_ENTITY_MODELS, GameSettings.Options.CUSTOM_GUIS, GameSettings.Options.EMISSIVE_TEXTURES };
/* 15 */   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());
/*    */   
/*    */   public GuiQualitySettingsOF(GuiScreen guiscreen, GameSettings gamesettings) {
/* 18 */     this.prevScreen = guiscreen;
/* 19 */     this.settings = gamesettings;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 27 */     this.title = I18n.format("of.options.qualityTitle", new Object[0]);
/* 28 */     this.buttonList.clear();
/*    */     
/* 30 */     for (int i = 0; i < enumOptions.length; i++) {
/* 31 */       GameSettings.Options gamesettings$options = enumOptions[i];
/* 32 */       int j = width / 2 - 155 + i % 2 * 160;
/* 33 */       int k = height / 6 + 21 * i / 2 - 12;
/*    */       
/* 35 */       if (!gamesettings$options.getEnumFloat()) {
/* 36 */         this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
/*    */       } else {
/* 38 */         this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
/*    */       } 
/*    */     } 
/*    */     
/* 42 */     this.buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton guibutton) {
/* 49 */     if (guibutton.enabled) {
/* 50 */       if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
/* 51 */         this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
/* 52 */         guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
/*    */       } 
/*    */       
/* 55 */       if (guibutton.id == 200) {
/* 56 */         this.mc.gameSettings.saveOptions();
/* 57 */         this.mc.displayGuiScreen(this.prevScreen);
/*    */       } 
/*    */       
/* 60 */       if (guibutton.id != GameSettings.Options.AA_LEVEL.ordinal()) {
/* 61 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 62 */         setWorldAndResolution(this.mc, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int x, int y, float f) {
/* 71 */     drawDefaultBackground();
/* 72 */     drawCenteredString(this.fontRendererObj, this.title, width / 2, 15, 16777215);
/* 73 */     super.drawScreen(x, y, f);
/* 74 */     this.tooltipManager.drawTooltips(x, y, this.buttonList);
/*    */   }
/*    */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\gui\GuiQualitySettingsOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */