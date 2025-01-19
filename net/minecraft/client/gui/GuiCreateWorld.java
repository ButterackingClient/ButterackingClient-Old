/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiCreateWorld
/*     */   extends GuiScreen {
/*     */   private GuiScreen parentScreen;
/*     */   private GuiTextField worldNameField;
/*     */   private GuiTextField worldSeedField;
/*     */   private String saveDirName;
/*  20 */   private String gameMode = "survival";
/*     */   
/*     */   private String savedGameMode;
/*     */   
/*     */   private boolean generateStructuresEnabled = true;
/*     */   
/*     */   private boolean allowCheats;
/*     */   
/*     */   private boolean allowCheatsWasSetByUser;
/*     */   
/*     */   private boolean bonusChestEnabled;
/*     */   
/*     */   private boolean hardCoreMode;
/*     */   
/*     */   private boolean alreadyGenerated;
/*     */   
/*     */   private boolean inMoreWorldOptionsDisplay;
/*     */   
/*     */   private GuiButton btnGameMode;
/*     */   
/*     */   private GuiButton btnMoreOptions;
/*     */   
/*     */   private GuiButton btnMapFeatures;
/*     */   
/*     */   private GuiButton btnBonusItems;
/*     */   
/*     */   private GuiButton btnMapType;
/*     */   
/*     */   private GuiButton btnAllowCommands;
/*     */   
/*     */   private GuiButton btnCustomizeType;
/*     */   
/*     */   private String gameModeDesc1;
/*     */   
/*     */   private String gameModeDesc2;
/*     */   private String worldSeed;
/*     */   private String worldName;
/*     */   private int selectedIndex;
/*  58 */   public String chunkProviderSettingsJson = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private static final String[] disallowedFilenames = new String[] { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
/*     */   
/*     */   public GuiCreateWorld(GuiScreen p_i46320_1_) {
/*  66 */     this.f = false;
/*  67 */     this.parentScreen = p_i46320_1_;
/*  68 */     this.worldSeed = "";
/*  69 */     this.worldName = I18n.format("selectWorld.newWorld", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  76 */     this.worldNameField.updateCursorCounter();
/*  77 */     this.worldSeedField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  85 */     Keyboard.enableRepeatEvents(true);
/*  86 */     this.buttonList.clear();
/*  87 */     this.buttonList.add(new GuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("selectWorld.create", new Object[0])));
/*  88 */     this.buttonList.add(new GuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  89 */     this.buttonList.add(this.btnGameMode = new GuiButton(2, width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
/*  90 */     this.buttonList.add(this.btnMoreOptions = new GuiButton(3, width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions", new Object[0])));
/*  91 */     this.buttonList.add(this.btnMapFeatures = new GuiButton(4, width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures", new Object[0])));
/*  92 */     this.btnMapFeatures.visible = false;
/*  93 */     this.buttonList.add(this.btnBonusItems = new GuiButton(7, width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems", new Object[0])));
/*  94 */     this.btnBonusItems.visible = false;
/*  95 */     this.buttonList.add(this.btnMapType = new GuiButton(5, width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType", new Object[0])));
/*  96 */     this.btnMapType.visible = false;
/*  97 */     this.buttonList.add(this.btnAllowCommands = new GuiButton(6, width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
/*  98 */     this.btnAllowCommands.visible = false;
/*  99 */     this.buttonList.add(this.btnCustomizeType = new GuiButton(8, width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType", new Object[0])));
/* 100 */     this.btnCustomizeType.visible = false;
/* 101 */     this.worldNameField = new GuiTextField(9, this.fontRendererObj, width / 2 - 100, 60, 200, 20);
/* 102 */     this.worldNameField.setFocused(true);
/* 103 */     this.worldNameField.setText(this.worldName);
/* 104 */     this.worldSeedField = new GuiTextField(10, this.fontRendererObj, width / 2 - 100, 60, 200, 20);
/* 105 */     this.worldSeedField.setText(this.worldSeed);
/* 106 */     showMoreWorldOptions(this.inMoreWorldOptionsDisplay);
/* 107 */     calcSaveDirName();
/* 108 */     updateDisplayState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calcSaveDirName() {
/* 115 */     this.saveDirName = this.worldNameField.getText().trim(); byte b; int i;
/*     */     char[] arrayOfChar;
/* 117 */     for (i = (arrayOfChar = ChatAllowedCharacters.allowedCharactersArray).length, b = 0; b < i; ) { char c0 = arrayOfChar[b];
/* 118 */       this.saveDirName = this.saveDirName.replace(c0, '_');
/*     */       b++; }
/*     */     
/* 121 */     if (StringUtils.isEmpty(this.saveDirName)) {
/* 122 */       this.saveDirName = "World";
/*     */     }
/*     */     
/* 125 */     this.saveDirName = getUncollidingSaveDirName(this.mc.getSaveLoader(), this.saveDirName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateDisplayState() {
/* 132 */     this.btnGameMode.displayString = String.valueOf(I18n.format("selectWorld.gameMode", new Object[0])) + ": " + I18n.format("selectWorld.gameMode." + this.gameMode, new Object[0]);
/* 133 */     this.gameModeDesc1 = I18n.format("selectWorld.gameMode." + this.gameMode + ".line1", new Object[0]);
/* 134 */     this.gameModeDesc2 = I18n.format("selectWorld.gameMode." + this.gameMode + ".line2", new Object[0]);
/* 135 */     this.btnMapFeatures.displayString = String.valueOf(I18n.format("selectWorld.mapFeatures", new Object[0])) + " ";
/*     */     
/* 137 */     if (this.generateStructuresEnabled) {
/* 138 */       this.btnMapFeatures.displayString = String.valueOf(this.btnMapFeatures.displayString) + I18n.format("options.on", new Object[0]);
/*     */     } else {
/* 140 */       this.btnMapFeatures.displayString = String.valueOf(this.btnMapFeatures.displayString) + I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/* 143 */     this.btnBonusItems.displayString = String.valueOf(I18n.format("selectWorld.bonusItems", new Object[0])) + " ";
/*     */     
/* 145 */     if (this.bonusChestEnabled && !this.hardCoreMode) {
/* 146 */       this.btnBonusItems.displayString = String.valueOf(this.btnBonusItems.displayString) + I18n.format("options.on", new Object[0]);
/*     */     } else {
/* 148 */       this.btnBonusItems.displayString = String.valueOf(this.btnBonusItems.displayString) + I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/* 151 */     this.btnMapType.displayString = String.valueOf(I18n.format("selectWorld.mapType", new Object[0])) + " " + I18n.format(WorldType.worldTypes[this.selectedIndex].getTranslateName(), new Object[0]);
/* 152 */     this.btnAllowCommands.displayString = String.valueOf(I18n.format("selectWorld.allowCommands", new Object[0])) + " ";
/*     */     
/* 154 */     if (this.allowCheats && !this.hardCoreMode) {
/* 155 */       this.btnAllowCommands.displayString = String.valueOf(this.btnAllowCommands.displayString) + I18n.format("options.on", new Object[0]);
/*     */     } else {
/* 157 */       this.btnAllowCommands.displayString = String.valueOf(this.btnAllowCommands.displayString) + I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getUncollidingSaveDirName(ISaveFormat saveLoader, String name) {
/* 169 */     name = name.replaceAll("[\\./\"]", "_"); byte b; int i;
/*     */     String[] arrayOfString;
/* 171 */     for (i = (arrayOfString = disallowedFilenames).length, b = 0; b < i; ) { String s = arrayOfString[b];
/* 172 */       if (name.equalsIgnoreCase(s)) {
/* 173 */         name = "_" + name + "_";
/*     */       }
/*     */       b++; }
/*     */     
/* 177 */     while (saveLoader.getWorldInfo(name) != null) {
/* 178 */       name = String.valueOf(name) + "-";
/*     */     }
/*     */     
/* 181 */     return name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 188 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 195 */     if (button.enabled) {
/* 196 */       if (button.id == 1) {
/* 197 */         this.mc.displayGuiScreen(this.parentScreen);
/* 198 */       } else if (button.id == 0) {
/* 199 */         this.mc.displayGuiScreen(null);
/*     */         
/* 201 */         if (this.alreadyGenerated) {
/*     */           return;
/*     */         }
/*     */         
/* 205 */         this.alreadyGenerated = true;
/* 206 */         long i = (new Random()).nextLong();
/* 207 */         String s = this.worldSeedField.getText();
/*     */         
/* 209 */         if (!StringUtils.isEmpty(s)) {
/*     */           try {
/* 211 */             long j = Long.parseLong(s);
/*     */             
/* 213 */             if (j != 0L) {
/* 214 */               i = j;
/*     */             }
/* 216 */           } catch (NumberFormatException var7) {
/* 217 */             i = s.hashCode();
/*     */           } 
/*     */         }
/*     */         
/* 221 */         WorldSettings.GameType worldsettings$gametype = WorldSettings.GameType.getByName(this.gameMode);
/* 222 */         WorldSettings worldsettings = new WorldSettings(i, worldsettings$gametype, this.generateStructuresEnabled, this.hardCoreMode, WorldType.worldTypes[this.selectedIndex]);
/* 223 */         worldsettings.setWorldName(this.chunkProviderSettingsJson);
/*     */         
/* 225 */         if (this.bonusChestEnabled && !this.hardCoreMode) {
/* 226 */           worldsettings.enableBonusChest();
/*     */         }
/*     */         
/* 229 */         if (this.allowCheats && !this.hardCoreMode) {
/* 230 */           worldsettings.enableCommands();
/*     */         }
/*     */         
/* 233 */         this.mc.launchIntegratedServer(this.saveDirName, this.worldNameField.getText().trim(), worldsettings);
/* 234 */       } else if (button.id == 3) {
/* 235 */         toggleMoreWorldOptions();
/* 236 */       } else if (button.id == 2) {
/* 237 */         if (this.gameMode.equals("survival")) {
/* 238 */           if (!this.allowCheatsWasSetByUser) {
/* 239 */             this.allowCheats = false;
/*     */           }
/*     */           
/* 242 */           this.hardCoreMode = false;
/* 243 */           this.gameMode = "hardcore";
/* 244 */           this.hardCoreMode = true;
/* 245 */           this.btnAllowCommands.enabled = false;
/* 246 */           this.btnBonusItems.enabled = false;
/* 247 */           updateDisplayState();
/* 248 */         } else if (this.gameMode.equals("hardcore")) {
/* 249 */           if (!this.allowCheatsWasSetByUser) {
/* 250 */             this.allowCheats = true;
/*     */           }
/*     */           
/* 253 */           this.hardCoreMode = false;
/* 254 */           this.gameMode = "creative";
/* 255 */           updateDisplayState();
/* 256 */           this.hardCoreMode = false;
/* 257 */           this.btnAllowCommands.enabled = true;
/* 258 */           this.btnBonusItems.enabled = true;
/*     */         } else {
/* 260 */           if (!this.allowCheatsWasSetByUser) {
/* 261 */             this.allowCheats = false;
/*     */           }
/*     */           
/* 264 */           this.gameMode = "survival";
/* 265 */           updateDisplayState();
/* 266 */           this.btnAllowCommands.enabled = true;
/* 267 */           this.btnBonusItems.enabled = true;
/* 268 */           this.hardCoreMode = false;
/*     */         } 
/*     */         
/* 271 */         updateDisplayState();
/* 272 */       } else if (button.id == 4) {
/* 273 */         this.generateStructuresEnabled = !this.generateStructuresEnabled;
/* 274 */         updateDisplayState();
/* 275 */       } else if (button.id == 7) {
/* 276 */         this.bonusChestEnabled = !this.bonusChestEnabled;
/* 277 */         updateDisplayState();
/* 278 */       } else if (button.id == 5) {
/* 279 */         this.selectedIndex++;
/*     */         
/* 281 */         if (this.selectedIndex >= WorldType.worldTypes.length) {
/* 282 */           this.selectedIndex = 0;
/*     */         }
/*     */         
/* 285 */         while (!canSelectCurWorldType()) {
/* 286 */           this.selectedIndex++;
/*     */           
/* 288 */           if (this.selectedIndex >= WorldType.worldTypes.length) {
/* 289 */             this.selectedIndex = 0;
/*     */           }
/*     */         } 
/*     */         
/* 293 */         this.chunkProviderSettingsJson = "";
/* 294 */         updateDisplayState();
/* 295 */         showMoreWorldOptions(this.inMoreWorldOptionsDisplay);
/* 296 */       } else if (button.id == 6) {
/* 297 */         this.allowCheatsWasSetByUser = true;
/* 298 */         this.allowCheats = !this.allowCheats;
/* 299 */         updateDisplayState();
/* 300 */       } else if (button.id == 8) {
/* 301 */         if (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT) {
/* 302 */           this.mc.displayGuiScreen(new GuiCreateFlatWorld(this, this.chunkProviderSettingsJson));
/*     */         } else {
/* 304 */           this.mc.displayGuiScreen(new GuiCustomizeWorldScreen(this, this.chunkProviderSettingsJson));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canSelectCurWorldType() {
/* 315 */     WorldType worldtype = WorldType.worldTypes[this.selectedIndex];
/* 316 */     return (worldtype != null && worldtype.getCanBeCreated()) ? ((worldtype == WorldType.DEBUG_WORLD) ? isShiftKeyDown() : true) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void toggleMoreWorldOptions() {
/* 325 */     showMoreWorldOptions(!this.inMoreWorldOptionsDisplay);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void showMoreWorldOptions(boolean toggle) {
/* 332 */     this.inMoreWorldOptionsDisplay = toggle;
/*     */     
/* 334 */     if (WorldType.worldTypes[this.selectedIndex] == WorldType.DEBUG_WORLD) {
/* 335 */       this.btnGameMode.visible = !this.inMoreWorldOptionsDisplay;
/* 336 */       this.btnGameMode.enabled = false;
/*     */       
/* 338 */       if (this.savedGameMode == null) {
/* 339 */         this.savedGameMode = this.gameMode;
/*     */       }
/*     */       
/* 342 */       this.gameMode = "spectator";
/* 343 */       this.btnMapFeatures.visible = false;
/* 344 */       this.btnBonusItems.visible = false;
/* 345 */       this.btnMapType.visible = this.inMoreWorldOptionsDisplay;
/* 346 */       this.btnAllowCommands.visible = false;
/* 347 */       this.btnCustomizeType.visible = false;
/*     */     } else {
/* 349 */       this.btnGameMode.visible = !this.inMoreWorldOptionsDisplay;
/* 350 */       this.btnGameMode.enabled = true;
/*     */       
/* 352 */       if (this.savedGameMode != null) {
/* 353 */         this.gameMode = this.savedGameMode;
/* 354 */         this.savedGameMode = null;
/*     */       } 
/*     */       
/* 357 */       this.btnMapFeatures.visible = (this.inMoreWorldOptionsDisplay && WorldType.worldTypes[this.selectedIndex] != WorldType.CUSTOMIZED);
/* 358 */       this.btnBonusItems.visible = this.inMoreWorldOptionsDisplay;
/* 359 */       this.btnMapType.visible = this.inMoreWorldOptionsDisplay;
/* 360 */       this.btnAllowCommands.visible = this.inMoreWorldOptionsDisplay;
/* 361 */       this.btnCustomizeType.visible = (this.inMoreWorldOptionsDisplay && (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT || WorldType.worldTypes[this.selectedIndex] == WorldType.CUSTOMIZED));
/*     */     } 
/*     */     
/* 364 */     updateDisplayState();
/*     */     
/* 366 */     if (this.inMoreWorldOptionsDisplay) {
/* 367 */       this.btnMoreOptions.displayString = I18n.format("gui.done", new Object[0]);
/*     */     } else {
/* 369 */       this.btnMoreOptions.displayString = I18n.format("selectWorld.moreWorldOptions", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 378 */     if (this.worldNameField.isFocused() && !this.inMoreWorldOptionsDisplay) {
/* 379 */       this.worldNameField.textboxKeyTyped(typedChar, keyCode);
/* 380 */       this.worldName = this.worldNameField.getText();
/* 381 */     } else if (this.worldSeedField.isFocused() && this.inMoreWorldOptionsDisplay) {
/* 382 */       this.worldSeedField.textboxKeyTyped(typedChar, keyCode);
/* 383 */       this.worldSeed = this.worldSeedField.getText();
/*     */     } 
/*     */     
/* 386 */     if (keyCode == 28 || keyCode == 156) {
/* 387 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */     
/* 390 */     ((GuiButton)this.buttonList.get(0)).enabled = (this.worldNameField.getText().length() > 0);
/* 391 */     calcSaveDirName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 398 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 400 */     if (this.inMoreWorldOptionsDisplay) {
/* 401 */       this.worldSeedField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     } else {
/* 403 */       this.worldNameField.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 411 */     drawDefaultBackground();
/* 412 */     drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.create", new Object[0]), width / 2, 20, -1);
/*     */     
/* 414 */     if (this.inMoreWorldOptionsDisplay) {
/* 415 */       drawString(this.fontRendererObj, I18n.format("selectWorld.enterSeed", new Object[0]), width / 2 - 100, 47, -6250336);
/* 416 */       drawString(this.fontRendererObj, I18n.format("selectWorld.seedInfo", new Object[0]), width / 2 - 100, 85, -6250336);
/*     */       
/* 418 */       if (this.btnMapFeatures.visible) {
/* 419 */         drawString(this.fontRendererObj, I18n.format("selectWorld.mapFeatures.info", new Object[0]), width / 2 - 150, 122, -6250336);
/*     */       }
/*     */       
/* 422 */       if (this.btnAllowCommands.visible) {
/* 423 */         drawString(this.fontRendererObj, I18n.format("selectWorld.allowCommands.info", new Object[0]), width / 2 - 150, 172, -6250336);
/*     */       }
/*     */       
/* 426 */       this.worldSeedField.drawTextBox();
/*     */       
/* 428 */       if (WorldType.worldTypes[this.selectedIndex].showWorldInfoNotice()) {
/* 429 */         this.fontRendererObj.drawSplitString(I18n.format(WorldType.worldTypes[this.selectedIndex].getTranslatedInfo(), new Object[0]), this.btnMapType.xPosition + 2, this.btnMapType.yPosition + 22, this.btnMapType.getButtonWidth(), 10526880);
/*     */       }
/*     */     } else {
/* 432 */       drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), width / 2 - 100, 47, -6250336);
/* 433 */       drawString(this.fontRendererObj, String.valueOf(I18n.format("selectWorld.resultFolder", new Object[0])) + " " + this.saveDirName, width / 2 - 100, 85, -6250336);
/* 434 */       this.worldNameField.drawTextBox();
/* 435 */       drawString(this.fontRendererObj, this.gameModeDesc1, width / 2 - 100, 137, -6250336);
/* 436 */       drawString(this.fontRendererObj, this.gameModeDesc2, width / 2 - 100, 149, -6250336);
/*     */     } 
/*     */     
/* 439 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recreateFromExistingWorld(WorldInfo original) {
/* 450 */     this.worldName = I18n.format("selectWorld.newWorld.copyOf", new Object[] { original.getWorldName() });
/* 451 */     this.worldSeed = (new StringBuilder(String.valueOf(original.getSeed()))).toString();
/* 452 */     this.selectedIndex = original.getTerrainType().getWorldTypeID();
/* 453 */     this.chunkProviderSettingsJson = original.getGeneratorOptions();
/* 454 */     this.generateStructuresEnabled = original.isMapFeaturesEnabled();
/* 455 */     this.allowCheats = original.areCommandsAllowed();
/*     */     
/* 457 */     if (original.isHardcoreModeEnabled()) {
/* 458 */       this.gameMode = "hardcore";
/* 459 */     } else if (original.getGameType().isSurvivalOrAdventure()) {
/* 460 */       this.gameMode = "survival";
/* 461 */     } else if (original.getGameType().isCreative()) {
/* 462 */       this.gameMode = "creative";
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiCreateWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */