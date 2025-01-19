/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ 
/*     */ public class GuiControls
/*     */   extends GuiScreen {
/*  11 */   private static final GameSettings.Options[] optionsArr = new GameSettings.Options[] { GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN };
/*     */ 
/*     */   
/*     */   private GuiScreen parentScreen;
/*     */ 
/*     */   
/*  17 */   protected String screenTitle = "Controls";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GameSettings options;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   public KeyBinding buttonId = null;
/*     */   public long time;
/*     */   private GuiKeyBindingList keyBindingList;
/*     */   private GuiButton buttonReset;
/*     */   
/*     */   public GuiControls(GuiScreen screen, GameSettings settings) {
/*  33 */     this.parentScreen = screen;
/*  34 */     this.options = settings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  42 */     this.keyBindingList = new GuiKeyBindingList(this, this.mc);
/*  43 */     this.buttonList.add(new GuiButton(200, width / 2 - 155, height - 29, 150, 20, I18n.format("gui.done", new Object[0])));
/*  44 */     this.buttonList.add(this.buttonReset = new GuiButton(201, width / 2 - 155 + 160, height - 29, 150, 20, I18n.format("controls.resetAll", new Object[0])));
/*  45 */     this.screenTitle = I18n.format("controls.title", new Object[0]);
/*  46 */     int i = 0; byte b; int j;
/*     */     GameSettings.Options[] arrayOfOptions;
/*  48 */     for (j = (arrayOfOptions = optionsArr).length, b = 0; b < j; ) { GameSettings.Options gamesettings$options = arrayOfOptions[b];
/*  49 */       if (gamesettings$options.getEnumFloat()) {
/*  50 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), width / 2 - 155 + i % 2 * 160, 18 + 24 * (i >> 1), gamesettings$options));
/*     */       } else {
/*  52 */         this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), width / 2 - 155 + i % 2 * 160, 18 + 24 * (i >> 1), gamesettings$options, this.options.getKeyBinding(gamesettings$options)));
/*     */       } 
/*     */       
/*  55 */       i++;
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  63 */     super.handleMouseInput();
/*  64 */     this.keyBindingList.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  71 */     if (button.id == 200) {
/*  72 */       this.mc.displayGuiScreen(this.parentScreen);
/*  73 */     } else if (button.id == 201) {
/*  74 */       byte b; int i; KeyBinding[] arrayOfKeyBinding; for (i = (arrayOfKeyBinding = this.mc.gameSettings.mc).length, b = 0; b < i; ) { KeyBinding keybinding = arrayOfKeyBinding[b];
/*  75 */         keybinding.setKeyCode(keybinding.getKeyCodeDefault());
/*     */         b++; }
/*     */       
/*  78 */       KeyBinding.resetKeyBindingArrayAndHash();
/*  79 */     } else if (button.id < 100 && button instanceof GuiOptionButton) {
/*  80 */       this.options.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
/*  81 */       button.displayString = this.options.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  89 */     if (this.buttonId != null) {
/*  90 */       this.options.setOptionKeyBinding(this.buttonId, -100 + mouseButton);
/*  91 */       this.buttonId = null;
/*  92 */       KeyBinding.resetKeyBindingArrayAndHash();
/*  93 */     } else if (mouseButton != 0 || !this.keyBindingList.mouseClicked(mouseX, mouseY, mouseButton)) {
/*  94 */       super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 102 */     if (state != 0 || !this.keyBindingList.mouseReleased(mouseX, mouseY, state)) {
/* 103 */       super.mouseReleased(mouseX, mouseY, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 112 */     if (this.buttonId != null) {
/* 113 */       if (keyCode == 1) {
/* 114 */         this.options.setOptionKeyBinding(this.buttonId, 0);
/* 115 */       } else if (keyCode != 0) {
/* 116 */         this.options.setOptionKeyBinding(this.buttonId, keyCode);
/* 117 */       } else if (typedChar > '\000') {
/* 118 */         this.options.setOptionKeyBinding(this.buttonId, typedChar + 256);
/*     */       } 
/*     */       
/* 121 */       this.buttonId = null;
/* 122 */       this.time = Minecraft.getSystemTime();
/* 123 */       KeyBinding.resetKeyBindingArrayAndHash();
/*     */     } else {
/* 125 */       super.keyTyped(typedChar, keyCode);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 133 */     drawDefaultBackground();
/* 134 */     this.keyBindingList.drawScreen(mouseX, mouseY, partialTicks);
/* 135 */     drawCenteredString(this.fontRendererObj, this.screenTitle, width / 2, 8, 16777215);
/* 136 */     boolean flag = true; byte b; int i;
/*     */     KeyBinding[] arrayOfKeyBinding;
/* 138 */     for (i = (arrayOfKeyBinding = this.options.mc).length, b = 0; b < i; ) { KeyBinding keybinding = arrayOfKeyBinding[b];
/* 139 */       if (keybinding.getKeyCode() != keybinding.getKeyCodeDefault()) {
/* 140 */         flag = false;
/*     */         break;
/*     */       } 
/*     */       b++; }
/*     */     
/* 145 */     this.buttonReset.enabled = !flag;
/* 146 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\GuiControls.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */