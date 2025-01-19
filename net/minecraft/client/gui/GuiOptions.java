/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundCategory;
/*     */ import net.minecraft.client.audio.SoundEventAccessorComposite;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.gui.stream.GuiStreamOptions;
/*     */ import net.minecraft.client.gui.stream.GuiStreamUnavailable;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.client.stream.IStream;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ 
/*     */ public class GuiOptions extends GuiScreen implements GuiYesNoCallback {
/*  20 */   private static final GameSettings.Options[] FOVOPTIONS = new GameSettings.Options[] { GameSettings.Options.FOV };
/*     */   
/*     */   private final GuiScreen field_146441_g;
/*     */   
/*     */   private final GameSettings game_settings_1;
/*     */   
/*     */   private GuiButton field_175357_i;
/*     */   
/*     */   private GuiLockIconButton field_175356_r;
/*  29 */   protected String field_146442_a = "Options";
/*     */   
/*     */   public GuiOptions(GuiScreen p_i1046_1_, GameSettings p_i1046_2_) {
/*  32 */     this.field_146441_g = p_i1046_1_;
/*  33 */     this.game_settings_1 = p_i1046_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  41 */     int i = 0;
/*  42 */     this.field_146442_a = I18n.format("options.title", new Object[0]); byte b; int j;
/*     */     GameSettings.Options[] arrayOfOptions;
/*  44 */     for (j = (arrayOfOptions = FOVOPTIONS).length, b = 0; b < j; ) { GameSettings.Options gamesettings$options = arrayOfOptions[b];
/*  45 */       if (gamesettings$options.getEnumFloat()) {
/*  46 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), width / 2 - 155 + i % 2 * 160, height / 6 - 12 + 24 * (i >> 1), gamesettings$options));
/*     */       } else {
/*  48 */         GuiOptionButton guioptionbutton = new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), width / 2 - 155 + i % 2 * 160, height / 6 - 12 + 24 * (i >> 1), gamesettings$options, this.game_settings_1.getKeyBinding(gamesettings$options));
/*  49 */         this.buttonList.add(guioptionbutton);
/*     */       } 
/*     */       
/*  52 */       i++;
/*     */       b++; }
/*     */     
/*  55 */     if (this.mc.theWorld != null) {
/*  56 */       EnumDifficulty enumdifficulty = this.mc.theWorld.getDifficulty();
/*  57 */       this.field_175357_i = new GuiButton(108, width / 2 - 155 + i % 2 * 160, height / 6 - 12 + 24 * (i >> 1), 150, 20, func_175355_a(enumdifficulty));
/*  58 */       this.buttonList.add(this.field_175357_i);
/*     */       
/*  60 */       if (this.mc.isSingleplayer() && !this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
/*  61 */         this.field_175357_i.setWidth(this.field_175357_i.getButtonWidth() - 20);
/*  62 */         this.field_175356_r = new GuiLockIconButton(109, this.field_175357_i.xPosition + this.field_175357_i.getButtonWidth(), this.field_175357_i.yPosition);
/*  63 */         this.buttonList.add(this.field_175356_r);
/*  64 */         this.field_175356_r.func_175229_b(this.mc.theWorld.getWorldInfo().isDifficultyLocked());
/*  65 */         this.field_175356_r.enabled = !this.field_175356_r.func_175230_c();
/*  66 */         this.field_175357_i.enabled = !this.field_175356_r.func_175230_c();
/*     */       } else {
/*  68 */         this.field_175357_i.enabled = false;
/*     */       } 
/*     */     } else {
/*  71 */       GuiOptionButton guioptionbutton1 = new GuiOptionButton(GameSettings.Options.enumFloat.returnEnumOrdinal(), width / 2 - 155 + i % 2 * 160, height / 6 - 12 + 24 * (i >> 1), GameSettings.Options.enumFloat, this.game_settings_1.getKeyBinding(GameSettings.Options.enumFloat));
/*  72 */       this.buttonList.add(guioptionbutton1);
/*     */     } 
/*     */     
/*  75 */     this.buttonList.add(new GuiButton(110, width / 2 - 155, height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation", new Object[0])));
/*  76 */     this.buttonList.add(new GuiButton(8675309, width / 2 + 5, height / 6 + 48 - 6, 150, 20, "Super Secret Settings...") {
/*     */           public void playPressSound(SoundHandler soundHandlerIn) {
/*  78 */             SoundEventAccessorComposite soundeventaccessorcomposite = soundHandlerIn.getRandomSoundFromCategories(new SoundCategory[] { SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER });
/*     */             
/*  80 */             if (soundeventaccessorcomposite != null) {
/*  81 */               soundHandlerIn.playSound((ISound)PositionedSoundRecord.create(soundeventaccessorcomposite.getSoundEventLocation(), 0.5F));
/*     */             }
/*     */           }
/*     */         });
/*  85 */     this.buttonList.add(new GuiButton(106, width / 2 - 155, height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds", new Object[0])));
/*  86 */     this.buttonList.add(new GuiButton(107, width / 2 + 5, height / 6 + 72 - 6, 150, 20, I18n.format("options.stream", new Object[0])));
/*  87 */     this.buttonList.add(new GuiButton(101, width / 2 - 155, height / 6 + 96 - 6, 150, 20, I18n.format("options.video", new Object[0])));
/*  88 */     this.buttonList.add(new GuiButton(100, width / 2 + 5, height / 6 + 96 - 6, 150, 20, I18n.format("options.controls", new Object[0])));
/*  89 */     this.buttonList.add(new GuiButton(102, width / 2 - 155, height / 6 + 120 - 6, 150, 20, I18n.format("options.language", new Object[0])));
/*  90 */     this.buttonList.add(new GuiButton(103, width / 2 + 5, height / 6 + 120 - 6, 150, 20, I18n.format("options.chat.title", new Object[0])));
/*  91 */     this.buttonList.add(new GuiButton(105, width / 2 - 155, height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack", new Object[0])));
/*  92 */     this.buttonList.add(new GuiButton(104, width / 2 + 5, height / 6 + 144 - 6, 150, 20, I18n.format("options.snooper.view", new Object[0])));
/*  93 */     this.buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */   
/*     */   public String func_175355_a(EnumDifficulty p_175355_1_) {
/*  97 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*  98 */     chatComponentText.appendSibling((IChatComponent)new ChatComponentTranslation("options.difficulty", new Object[0]));
/*  99 */     chatComponentText.appendText(": ");
/* 100 */     chatComponentText.appendSibling((IChatComponent)new ChatComponentTranslation(p_175355_1_.getDifficultyResourceKey(), new Object[0]));
/* 101 */     return chatComponentText.getFormattedText();
/*     */   }
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 105 */     this.mc.displayGuiScreen(this);
/*     */     
/* 107 */     if (id == 109 && result && this.mc.theWorld != null) {
/* 108 */       this.mc.theWorld.getWorldInfo().setDifficultyLocked(true);
/* 109 */       this.field_175356_r.func_175229_b(true);
/* 110 */       this.field_175356_r.enabled = false;
/* 111 */       this.field_175357_i.enabled = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 119 */     if (button.enabled) {
/* 120 */       if (button.id < 100 && button instanceof GuiOptionButton) {
/* 121 */         GameSettings.Options gamesettings$options = ((GuiOptionButton)button).returnEnumOptions();
/* 122 */         this.game_settings_1.setOptionValue(gamesettings$options, 1);
/* 123 */         button.displayString = this.game_settings_1.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*     */       } 
/*     */       
/* 126 */       if (button.id == 108) {
/* 127 */         this.mc.theWorld.getWorldInfo().setDifficulty(EnumDifficulty.getDifficultyEnum(this.mc.theWorld.getDifficulty().getDifficultyId() + 1));
/* 128 */         this.field_175357_i.displayString = func_175355_a(this.mc.theWorld.getDifficulty());
/*     */       } 
/*     */       
/* 131 */       if (button.id == 109) {
/* 132 */         this.mc.displayGuiScreen(new GuiYesNo(this, (new ChatComponentTranslation("difficulty.lock.title", new Object[0])).getFormattedText(), (new ChatComponentTranslation("difficulty.lock.question", new Object[] { new ChatComponentTranslation(this.mc.theWorld.getWorldInfo().getDifficulty().getDifficultyResourceKey(), new Object[0]) })).getFormattedText(), 109));
/*     */       }
/*     */       
/* 135 */       if (button.id == 110) {
/* 136 */         this.mc.gameSettings.saveOptions();
/* 137 */         this.mc.displayGuiScreen(new GuiCustomizeSkin(this));
/*     */       } 
/*     */       
/* 140 */       if (button.id == 8675309) {
/* 141 */         this.mc.entityRenderer.activateNextShader();
/*     */       }
/*     */       
/* 144 */       if (button.id == 101) {
/* 145 */         this.mc.gameSettings.saveOptions();
/* 146 */         this.mc.displayGuiScreen((GuiScreen)new GuiVideoSettings(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 149 */       if (button.id == 100) {
/* 150 */         this.mc.gameSettings.saveOptions();
/* 151 */         this.mc.displayGuiScreen(new GuiControls(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 154 */       if (button.id == 102) {
/* 155 */         this.mc.gameSettings.saveOptions();
/* 156 */         this.mc.displayGuiScreen(new GuiLanguage(this, this.game_settings_1, this.mc.getLanguageManager()));
/*     */       } 
/*     */       
/* 159 */       if (button.id == 103) {
/* 160 */         this.mc.gameSettings.saveOptions();
/* 161 */         this.mc.displayGuiScreen(new ScreenChatOptions(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 164 */       if (button.id == 104) {
/* 165 */         this.mc.gameSettings.saveOptions();
/* 166 */         this.mc.displayGuiScreen(new GuiSnooper(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 169 */       if (button.id == 200) {
/* 170 */         this.mc.gameSettings.saveOptions();
/* 171 */         this.mc.displayGuiScreen(this.field_146441_g);
/*     */       } 
/*     */       
/* 174 */       if (button.id == 105) {
/* 175 */         this.mc.gameSettings.saveOptions();
/* 176 */         this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
/*     */       } 
/*     */       
/* 179 */       if (button.id == 106) {
/* 180 */         this.mc.gameSettings.saveOptions();
/* 181 */         this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 184 */       if (button.id == 107) {
/* 185 */         this.mc.gameSettings.saveOptions();
/* 186 */         IStream istream = this.mc.getTwitchStream();
/*     */         
/* 188 */         if (istream.func_152936_l() && istream.func_152928_D()) {
/* 189 */           this.mc.displayGuiScreen((GuiScreen)new GuiStreamOptions(this, this.game_settings_1));
/*     */         } else {
/* 191 */           GuiStreamUnavailable.func_152321_a(this);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 201 */     drawDefaultBackground();
/* 202 */     drawCenteredString(this.fontRendererObj, this.field_146442_a, width / 2, 15, 16777215);
/* 203 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */