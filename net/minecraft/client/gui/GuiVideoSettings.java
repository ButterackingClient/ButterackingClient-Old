/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.gui.GuiAnimationSettingsOF;
/*     */ import net.optifine.gui.GuiDetailSettingsOF;
/*     */ import net.optifine.gui.GuiOptionButtonOF;
/*     */ import net.optifine.gui.GuiOptionSliderOF;
/*     */ import net.optifine.gui.GuiOtherSettingsOF;
/*     */ import net.optifine.gui.GuiPerformanceSettingsOF;
/*     */ import net.optifine.gui.GuiQualitySettingsOF;
/*     */ import net.optifine.gui.GuiScreenOF;
/*     */ import net.optifine.gui.TooltipManager;
/*     */ import net.optifine.gui.TooltipProvider;
/*     */ import net.optifine.gui.TooltipProviderOptions;
/*     */ import net.optifine.shaders.gui.GuiShaders;
/*     */ 
/*     */ public class GuiVideoSettings extends GuiScreenOF {
/*     */   private GuiScreen parentGuiScreen;
/*  23 */   protected String screenTitle = "Video Settings";
/*     */ 
/*     */   
/*     */   private GameSettings guiGameSettings;
/*     */ 
/*     */   
/*  29 */   private static GameSettings.Options[] videoOptions = new GameSettings.Options[] { GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.USE_VBO, GameSettings.Options.GAMMA, GameSettings.Options.BLOCK_ALTERNATIVES, GameSettings.Options.DYNAMIC_LIGHTS, GameSettings.Options.DYNAMIC_FOV };
/*  30 */   private TooltipManager tooltipManager = new TooltipManager((GuiScreen)this, (TooltipProvider)new TooltipProviderOptions());
/*     */   
/*     */   public GuiVideoSettings(GuiScreen parentScreenIn, GameSettings gameSettingsIn) {
/*  33 */     this.parentGuiScreen = parentScreenIn;
/*  34 */     this.guiGameSettings = gameSettingsIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  42 */     this.screenTitle = I18n.format("options.videoTitle", new Object[0]);
/*  43 */     this.buttonList.clear();
/*     */     
/*  45 */     for (int i = 0; i < videoOptions.length; i++) {
/*  46 */       GameSettings.Options gamesettings$options = videoOptions[i];
/*     */       
/*  48 */       if (gamesettings$options != null) {
/*  49 */         int j = width / 2 - 155 + i % 2 * 160;
/*  50 */         int k = height / 6 + 21 * i / 2 - 12;
/*     */         
/*  52 */         if (gamesettings$options.getEnumFloat()) {
/*  53 */           this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
/*     */         } else {
/*  55 */           this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.guiGameSettings.getKeyBinding(gamesettings$options)));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  60 */     int l = height / 6 + 21 * videoOptions.length / 2 - 12;
/*  61 */     int i1 = 0;
/*  62 */     i1 = width / 2 - 155 + 0;
/*  63 */     this.buttonList.add(new GuiOptionButton(231, i1, l, Lang.get("of.options.shaders")));
/*  64 */     i1 = width / 2 - 155 + 160;
/*  65 */     this.buttonList.add(new GuiOptionButton(202, i1, l, Lang.get("of.options.quality")));
/*  66 */     l += 21;
/*  67 */     i1 = width / 2 - 155 + 0;
/*  68 */     this.buttonList.add(new GuiOptionButton(201, i1, l, Lang.get("of.options.details")));
/*  69 */     i1 = width / 2 - 155 + 160;
/*  70 */     this.buttonList.add(new GuiOptionButton(212, i1, l, Lang.get("of.options.performance")));
/*  71 */     l += 21;
/*  72 */     i1 = width / 2 - 155 + 0;
/*  73 */     this.buttonList.add(new GuiOptionButton(211, i1, l, Lang.get("of.options.animations")));
/*  74 */     i1 = width / 2 - 155 + 160;
/*  75 */     this.buttonList.add(new GuiOptionButton(222, i1, l, Lang.get("of.options.other")));
/*  76 */     l += 21;
/*  77 */     this.buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  84 */     actionPerformed(button, 1);
/*     */   }
/*     */   
/*     */   protected void actionPerformedRightClick(GuiButton p_actionPerformedRightClick_1_) {
/*  88 */     if (p_actionPerformedRightClick_1_.id == GameSettings.Options.GUI_SCALE.ordinal()) {
/*  89 */       actionPerformed(p_actionPerformedRightClick_1_, -1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void actionPerformed(GuiButton p_actionPerformed_1_, int p_actionPerformed_2_) {
/*  94 */     if (p_actionPerformed_1_.enabled) {
/*  95 */       int i = this.guiGameSettings.particleSetting;
/*     */       
/*  97 */       if (p_actionPerformed_1_.id < 200 && p_actionPerformed_1_ instanceof GuiOptionButton) {
/*  98 */         this.guiGameSettings.setOptionValue(((GuiOptionButton)p_actionPerformed_1_).returnEnumOptions(), p_actionPerformed_2_);
/*  99 */         p_actionPerformed_1_.displayString = this.guiGameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(p_actionPerformed_1_.id));
/*     */       } 
/*     */       
/* 102 */       if (p_actionPerformed_1_.id == 200) {
/* 103 */         this.mc.gameSettings.saveOptions();
/* 104 */         this.mc.displayGuiScreen(this.parentGuiScreen);
/*     */       } 
/*     */       
/* 107 */       if (this.guiGameSettings.particleSetting != i) {
/* 108 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 109 */         int j = scaledresolution.getScaledWidth();
/* 110 */         int k = scaledresolution.getScaledHeight();
/* 111 */         setWorldAndResolution(this.mc, j, k);
/*     */       } 
/*     */       
/* 114 */       if (p_actionPerformed_1_.id == 201) {
/* 115 */         this.mc.gameSettings.saveOptions();
/* 116 */         GuiDetailSettingsOF guidetailsettingsof = new GuiDetailSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 117 */         this.mc.displayGuiScreen((GuiScreen)guidetailsettingsof);
/*     */       } 
/*     */       
/* 120 */       if (p_actionPerformed_1_.id == 202) {
/* 121 */         this.mc.gameSettings.saveOptions();
/* 122 */         GuiQualitySettingsOF guiqualitysettingsof = new GuiQualitySettingsOF((GuiScreen)this, this.guiGameSettings);
/* 123 */         this.mc.displayGuiScreen((GuiScreen)guiqualitysettingsof);
/*     */       } 
/*     */       
/* 126 */       if (p_actionPerformed_1_.id == 211) {
/* 127 */         this.mc.gameSettings.saveOptions();
/* 128 */         GuiAnimationSettingsOF guianimationsettingsof = new GuiAnimationSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 129 */         this.mc.displayGuiScreen((GuiScreen)guianimationsettingsof);
/*     */       } 
/*     */       
/* 132 */       if (p_actionPerformed_1_.id == 212) {
/* 133 */         this.mc.gameSettings.saveOptions();
/* 134 */         GuiPerformanceSettingsOF guiperformancesettingsof = new GuiPerformanceSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 135 */         this.mc.displayGuiScreen((GuiScreen)guiperformancesettingsof);
/*     */       } 
/*     */       
/* 138 */       if (p_actionPerformed_1_.id == 222) {
/* 139 */         this.mc.gameSettings.saveOptions();
/* 140 */         GuiOtherSettingsOF guiothersettingsof = new GuiOtherSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 141 */         this.mc.displayGuiScreen((GuiScreen)guiothersettingsof);
/*     */       } 
/*     */       
/* 144 */       if (p_actionPerformed_1_.id == 231) {
/* 145 */         if (Config.isAntialiasing() || Config.isAntialiasingConfigured()) {
/* 146 */           Config.showGuiMessage(Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
/*     */           
/*     */           return;
/*     */         } 
/* 150 */         if (Config.isAnisotropicFiltering()) {
/* 151 */           Config.showGuiMessage(Lang.get("of.message.shaders.af1"), Lang.get("of.message.shaders.af2"));
/*     */           
/*     */           return;
/*     */         } 
/* 155 */         if (Config.isFastRender()) {
/* 156 */           Config.showGuiMessage(Lang.get("of.message.shaders.fr1"), Lang.get("of.message.shaders.fr2"));
/*     */           
/*     */           return;
/*     */         } 
/* 160 */         if ((Config.getGameSettings()).anaglyph) {
/* 161 */           Config.showGuiMessage(Lang.get("of.message.shaders.an1"), Lang.get("of.message.shaders.an2"));
/*     */           
/*     */           return;
/*     */         } 
/* 165 */         this.mc.gameSettings.saveOptions();
/* 166 */         GuiShaders guishaders = new GuiShaders((GuiScreen)this, this.guiGameSettings);
/* 167 */         this.mc.displayGuiScreen((GuiScreen)guishaders);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 176 */     drawDefaultBackground();
/* 177 */     drawCenteredString(this.fontRendererObj, this.screenTitle, width / 2, 15, 16777215);
/* 178 */     String s = Config.getVersion();
/* 179 */     String s1 = "HD_U";
/*     */     
/* 181 */     if (s1.equals("HD")) {
/* 182 */       s = "OptiFine HD M5";
/*     */     }
/*     */     
/* 185 */     if (s1.equals("HD_U")) {
/* 186 */       s = "OptiFine HD M5 Ultra";
/*     */     }
/*     */     
/* 189 */     if (s1.equals("L")) {
/* 190 */       s = "OptiFine M5 Light";
/*     */     }
/*     */     
/* 193 */     drawString(this.fontRendererObj, s, 2, height - 10, 8421504);
/* 194 */     String s2 = "Minecraft 1.8.9";
/* 195 */     int i = this.fontRendererObj.getStringWidth(s2);
/* 196 */     drawString(this.fontRendererObj, s2, width - i - 2, height - 10, 8421504);
/* 197 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 198 */     this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
/*     */   }
/*     */   
/*     */   public static int getButtonWidth(GuiButton p_getButtonWidth_0_) {
/* 202 */     return p_getButtonWidth_0_.width;
/*     */   }
/*     */   
/*     */   public static int getButtonHeight(GuiButton p_getButtonHeight_0_) {
/* 206 */     return p_getButtonHeight_0_.height;
/*     */   }
/*     */   
/*     */   public static void drawGradientRect(GuiScreen p_drawGradientRect_0_, int p_drawGradientRect_1_, int p_drawGradientRect_2_, int p_drawGradientRect_3_, int p_drawGradientRect_4_, int p_drawGradientRect_5_, int p_drawGradientRect_6_) {
/* 210 */     p_drawGradientRect_0_.drawGradientRect(p_drawGradientRect_1_, p_drawGradientRect_2_, p_drawGradientRect_3_, p_drawGradientRect_4_, p_drawGradientRect_5_, p_drawGradientRect_6_);
/*     */   }
/*     */   
/*     */   public static String getGuiChatText(GuiChat p_getGuiChatText_0_) {
/* 214 */     return p_getGuiChatText_0_.inputField.getText();
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiVideoSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */