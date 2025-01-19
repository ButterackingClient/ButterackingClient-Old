/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.Language;
/*     */ import net.minecraft.client.resources.LanguageManager;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiLanguage
/*     */   extends GuiScreen
/*     */ {
/*     */   protected GuiScreen parentScreen;
/*     */   private List list;
/*     */   private final GameSettings game_settings_3;
/*     */   private final LanguageManager languageManager;
/*     */   private GuiOptionButton forceUnicodeFontBtn;
/*     */   private GuiOptionButton confirmSettingsBtn;
/*     */   
/*     */   public GuiLanguage(GuiScreen screen, GameSettings gameSettingsObj, LanguageManager manager) {
/*  47 */     this.parentScreen = screen;
/*  48 */     this.game_settings_3 = gameSettingsObj;
/*  49 */     this.languageManager = manager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  57 */     this.buttonList.add(this.forceUnicodeFontBtn = new GuiOptionButton(100, width / 2 - 155, height - 38, GameSettings.Options.FORCE_UNICODE_FONT, this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT)));
/*  58 */     this.buttonList.add(this.confirmSettingsBtn = new GuiOptionButton(6, width / 2 - 155 + 160, height - 38, I18n.format("gui.done", new Object[0])));
/*  59 */     this.list = new List(this.mc);
/*  60 */     this.list.registerScrollButtons(7, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  67 */     super.handleMouseInput();
/*  68 */     this.list.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  75 */     if (button.enabled) {
/*  76 */       switch (button.id) {
/*     */         case 5:
/*     */           return;
/*     */         
/*     */         case 6:
/*  81 */           this.mc.displayGuiScreen(this.parentScreen);
/*     */ 
/*     */         
/*     */         case 100:
/*  85 */           if (button instanceof GuiOptionButton) {
/*  86 */             this.game_settings_3.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/*  87 */             button.displayString = this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
/*  88 */             ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  89 */             int i = scaledresolution.getScaledWidth();
/*  90 */             int j = scaledresolution.getScaledHeight();
/*  91 */             setWorldAndResolution(this.mc, i, j);
/*     */           } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  97 */       this.list.actionPerformed(button);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 106 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/* 107 */     drawCenteredString(this.fontRendererObj, I18n.format("options.language", new Object[0]), width / 2, 16, 16777215);
/* 108 */     drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")", width / 2, height - 56, 8421504);
/* 109 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   class List extends GuiSlot {
/* 113 */     private final java.util.List<String> langCodeList = Lists.newArrayList();
/* 114 */     private final Map<String, Language> languageMap = Maps.newHashMap();
/*     */     
/*     */     public List(Minecraft mcIn) {
/* 117 */       super(mcIn, GuiLanguage.width, GuiLanguage.height, 32, GuiLanguage.height - 65 + 4, 18);
/*     */       
/* 119 */       for (Language language : GuiLanguage.this.languageManager.getLanguages()) {
/* 120 */         this.languageMap.put(language.getLanguageCode(), language);
/* 121 */         this.langCodeList.add(language.getLanguageCode());
/*     */       } 
/*     */     }
/*     */     
/*     */     protected int getSize() {
/* 126 */       return this.langCodeList.size();
/*     */     }
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 130 */       Language language = this.languageMap.get(this.langCodeList.get(slotIndex));
/* 131 */       GuiLanguage.this.languageManager.setCurrentLanguage(language);
/* 132 */       GuiLanguage.this.game_settings_3.forceUnicodeFont = language.getLanguageCode();
/* 133 */       this.mc.refreshResources();
/* 134 */       GuiLanguage.this.fontRendererObj.setUnicodeFlag(!(!GuiLanguage.this.languageManager.isCurrentLocaleUnicode() && !GuiLanguage.this.game_settings_3.logger));
/* 135 */       GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.isCurrentLanguageBidirectional());
/* 136 */       GuiLanguage.this.confirmSettingsBtn.displayString = I18n.format("gui.done", new Object[0]);
/* 137 */       GuiLanguage.this.forceUnicodeFontBtn.displayString = GuiLanguage.this.game_settings_3.getKeyBinding(GameSettings.Options.FORCE_UNICODE_FONT);
/* 138 */       GuiLanguage.this.game_settings_3.saveOptions();
/*     */     }
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 142 */       return ((String)this.langCodeList.get(slotIndex)).equals(GuiLanguage.this.languageManager.getCurrentLanguage().getLanguageCode());
/*     */     }
/*     */     
/*     */     protected int getContentHeight() {
/* 146 */       return getSize() * 18;
/*     */     }
/*     */     
/*     */     protected void drawBackground() {
/* 150 */       GuiLanguage.this.drawDefaultBackground();
/*     */     }
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 154 */       GuiLanguage.this.fontRendererObj.setBidiFlag(true);
/* 155 */       GuiLanguage.this.drawCenteredString(GuiLanguage.this.fontRendererObj, ((Language)this.languageMap.get(this.langCodeList.get(entryID))).toString(), this.width / 2, p_180791_3_ + 1, 16777215);
/* 156 */       GuiLanguage.this.fontRendererObj.setBidiFlag(GuiLanguage.this.languageManager.getCurrentLanguage().isBidirectional());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiLanguage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */