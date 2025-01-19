/*     */ package net.minecraft.client.gui.stream;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiOptionButton;
/*     */ import net.minecraft.client.gui.GuiOptionSlider;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ 
/*     */ public class GuiStreamOptions
/*     */   extends GuiScreen {
/*  14 */   private static final GameSettings.Options[] field_152312_a = new GameSettings.Options[] { GameSettings.Options.STREAM_BYTES_PER_PIXEL, GameSettings.Options.STREAM_FPS, GameSettings.Options.STREAM_KBPS, GameSettings.Options.STREAM_SEND_METADATA, GameSettings.Options.STREAM_VOLUME_MIC, GameSettings.Options.STREAM_VOLUME_SYSTEM, GameSettings.Options.STREAM_MIC_TOGGLE_BEHAVIOR, GameSettings.Options.STREAM_COMPRESSION };
/*  15 */   private static final GameSettings.Options[] field_152316_f = new GameSettings.Options[] { GameSettings.Options.STREAM_CHAT_ENABLED, GameSettings.Options.STREAM_CHAT_USER_FILTER };
/*     */   private final GuiScreen parentScreen;
/*     */   private final GameSettings field_152318_h;
/*     */   private String field_152319_i;
/*     */   private String field_152313_r;
/*     */   private int field_152314_s;
/*     */   private boolean field_152315_t = false;
/*     */   
/*     */   public GuiStreamOptions(GuiScreen parentScreenIn, GameSettings p_i1073_2_) {
/*  24 */     this.parentScreen = parentScreenIn;
/*  25 */     this.field_152318_h = p_i1073_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  33 */     int i = 0;
/*  34 */     this.field_152319_i = I18n.format("options.stream.title", new Object[0]);
/*  35 */     this.field_152313_r = I18n.format("options.stream.chat.title", new Object[0]); byte b; int j;
/*     */     GameSettings.Options[] arrayOfOptions;
/*  37 */     for (j = (arrayOfOptions = field_152312_a).length, b = 0; b < j; ) { GameSettings.Options gamesettings$options = arrayOfOptions[b];
/*  38 */       if (gamesettings$options.getEnumFloat()) {
/*  39 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), width / 2 - 155 + i % 2 * 160, height / 6 + 24 * (i >> 1), gamesettings$options));
/*     */       } else {
/*  41 */         this.buttonList.add(new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), width / 2 - 155 + i % 2 * 160, height / 6 + 24 * (i >> 1), gamesettings$options, this.field_152318_h.getKeyBinding(gamesettings$options)));
/*     */       } 
/*     */       
/*  44 */       i++;
/*     */       b++; }
/*     */     
/*  47 */     if (i % 2 == 1) {
/*  48 */       i++;
/*     */     }
/*     */     
/*  51 */     this.field_152314_s = height / 6 + 24 * (i >> 1) + 6;
/*  52 */     i += 2;
/*     */     
/*  54 */     for (j = (arrayOfOptions = field_152316_f).length, b = 0; b < j; ) { GameSettings.Options gamesettings$options1 = arrayOfOptions[b];
/*  55 */       if (gamesettings$options1.getEnumFloat()) {
/*  56 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options1.returnEnumOrdinal(), width / 2 - 155 + i % 2 * 160, height / 6 + 24 * (i >> 1), gamesettings$options1));
/*     */       } else {
/*  58 */         this.buttonList.add(new GuiOptionButton(gamesettings$options1.returnEnumOrdinal(), width / 2 - 155 + i % 2 * 160, height / 6 + 24 * (i >> 1), gamesettings$options1, this.field_152318_h.getKeyBinding(gamesettings$options1)));
/*     */       } 
/*     */       
/*  61 */       i++;
/*     */       b++; }
/*     */     
/*  64 */     this.buttonList.add(new GuiButton(200, width / 2 - 155, height / 6 + 168, 150, 20, I18n.format("gui.done", new Object[0])));
/*  65 */     GuiButton guibutton = new GuiButton(201, width / 2 + 5, height / 6 + 168, 150, 20, I18n.format("options.stream.ingestSelection", new Object[0]));
/*  66 */     guibutton.enabled = !((!this.mc.getTwitchStream().isReadyToBroadcast() || (this.mc.getTwitchStream().func_152925_v()).length <= 0) && !this.mc.getTwitchStream().func_152908_z());
/*  67 */     this.buttonList.add(guibutton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  74 */     if (button.enabled) {
/*  75 */       if (button.id < 100 && button instanceof GuiOptionButton) {
/*  76 */         GameSettings.Options gamesettings$options = ((GuiOptionButton)button).returnEnumOptions();
/*  77 */         this.field_152318_h.setOptionValue(gamesettings$options, 1);
/*  78 */         button.displayString = this.field_152318_h.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*     */         
/*  80 */         if (this.mc.getTwitchStream().isBroadcasting() && gamesettings$options != GameSettings.Options.STREAM_CHAT_ENABLED && gamesettings$options != GameSettings.Options.STREAM_CHAT_USER_FILTER) {
/*  81 */           this.field_152315_t = true;
/*     */         }
/*  83 */       } else if (button instanceof GuiOptionSlider) {
/*  84 */         if (button.id == GameSettings.Options.STREAM_VOLUME_MIC.returnEnumOrdinal()) {
/*  85 */           this.mc.getTwitchStream().updateStreamVolume();
/*  86 */         } else if (button.id == GameSettings.Options.STREAM_VOLUME_SYSTEM.returnEnumOrdinal()) {
/*  87 */           this.mc.getTwitchStream().updateStreamVolume();
/*  88 */         } else if (this.mc.getTwitchStream().isBroadcasting()) {
/*  89 */           this.field_152315_t = true;
/*     */         } 
/*     */       } 
/*     */       
/*  93 */       if (button.id == 200) {
/*  94 */         this.mc.gameSettings.saveOptions();
/*  95 */         this.mc.displayGuiScreen(this.parentScreen);
/*  96 */       } else if (button.id == 201) {
/*  97 */         this.mc.gameSettings.saveOptions();
/*  98 */         this.mc.displayGuiScreen(new GuiIngestServers(this));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 107 */     drawDefaultBackground();
/* 108 */     drawCenteredString(this.fontRendererObj, this.field_152319_i, width / 2, 20, 16777215);
/* 109 */     drawCenteredString(this.fontRendererObj, this.field_152313_r, width / 2, this.field_152314_s, 16777215);
/*     */     
/* 111 */     if (this.field_152315_t) {
/* 112 */       drawCenteredString(this.fontRendererObj, EnumChatFormatting.RED + I18n.format("options.stream.changes", new Object[0]), width / 2, 20 + this.fontRendererObj.FONT_HEIGHT, 16777215);
/*     */     }
/*     */     
/* 115 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\minecraft\client\gui\stream\GuiStreamOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */