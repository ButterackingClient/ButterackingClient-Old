/*     */ package net.optifine.shaders.gui;
/*     */ 
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.gui.GuiScreenOF;
/*     */ import net.optifine.gui.TooltipManager;
/*     */ import net.optifine.gui.TooltipProvider;
/*     */ import net.optifine.gui.TooltipProviderShaderOptions;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.shaders.config.ShaderOption;
/*     */ import net.optifine.shaders.config.ShaderOptionProfile;
/*     */ import net.optifine.shaders.config.ShaderOptionScreen;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiShaderOptions
/*     */   extends GuiScreenOF
/*     */ {
/*     */   private GuiScreen prevScreen;
/*     */   protected String title;
/*     */   private GameSettings settings;
/*     */   private TooltipManager tooltipManager;
/*     */   private String screenName;
/*     */   
/*     */   public GuiShaderOptions(GuiScreen guiscreen, GameSettings gamesettings) {
/*  32 */     this.tooltipManager = new TooltipManager((GuiScreen)this, (TooltipProvider)new TooltipProviderShaderOptions());
/*  33 */     this.screenName = null;
/*  34 */     this.screenText = null;
/*  35 */     this.changed = false;
/*  36 */     this.title = "Shader Options";
/*  37 */     this.prevScreen = guiscreen;
/*  38 */     this.settings = gamesettings;
/*     */   }
/*     */   private String screenText; private boolean changed; public static final String OPTION_PROFILE = "<profile>"; public static final String OPTION_EMPTY = "<empty>"; public static final String OPTION_REST = "*";
/*     */   public GuiShaderOptions(GuiScreen guiscreen, GameSettings gamesettings, String screenName) {
/*  42 */     this(guiscreen, gamesettings);
/*  43 */     this.screenName = screenName;
/*     */     
/*  45 */     if (screenName != null) {
/*  46 */       this.screenText = Shaders.translate("screen." + screenName, screenName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  55 */     this.title = I18n.format("of.options.shaderOptionsTitle", new Object[0]);
/*  56 */     int i = 100;
/*  57 */     int j = 0;
/*  58 */     int k = 30;
/*  59 */     int l = 20;
/*  60 */     int i1 = 120;
/*  61 */     int j1 = 20;
/*  62 */     int k1 = Shaders.getShaderPackColumns(this.screenName, 2);
/*  63 */     ShaderOption[] ashaderoption = Shaders.getShaderPackOptions(this.screenName);
/*     */     
/*  65 */     if (ashaderoption != null) {
/*  66 */       int l1 = MathHelper.ceiling_double_int(ashaderoption.length / 9.0D);
/*     */       
/*  68 */       if (k1 < l1) {
/*  69 */         k1 = l1;
/*     */       }
/*     */       
/*  72 */       for (int i2 = 0; i2 < ashaderoption.length; i2++) {
/*  73 */         ShaderOption shaderoption = ashaderoption[i2];
/*     */         
/*  75 */         if (shaderoption != null && shaderoption.isVisible()) {
/*  76 */           GuiButtonShaderOption guibuttonshaderoption; int j2 = i2 % k1;
/*  77 */           int k2 = i2 / k1;
/*  78 */           int l2 = Math.min(width / k1, 200);
/*  79 */           j = (width - l2 * k1) / 2;
/*  80 */           int i3 = j2 * l2 + 5 + j;
/*  81 */           int j3 = k + k2 * l;
/*  82 */           int k3 = l2 - 10;
/*  83 */           String s = getButtonText(shaderoption, k3);
/*     */ 
/*     */           
/*  86 */           if (Shaders.isShaderPackOptionSlider(shaderoption.getName())) {
/*  87 */             guibuttonshaderoption = new GuiSliderShaderOption(i + i2, i3, j3, k3, j1, shaderoption, s);
/*     */           } else {
/*  89 */             guibuttonshaderoption = new GuiButtonShaderOption(i + i2, i3, j3, k3, j1, shaderoption, s);
/*     */           } 
/*     */           
/*  92 */           guibuttonshaderoption.enabled = shaderoption.isEnabled();
/*  93 */           this.buttonList.add(guibuttonshaderoption);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     this.buttonList.add(new GuiButton(201, width / 2 - i1 - 20, height / 6 + 168 + 11, i1, j1, I18n.format("controls.reset", new Object[0])));
/*  99 */     this.buttonList.add(new GuiButton(200, width / 2 + 20, height / 6 + 168 + 11, i1, j1, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */   
/*     */   public static String getButtonText(ShaderOption so, int btnWidth) {
/* 103 */     String s = so.getNameText();
/*     */     
/* 105 */     if (so instanceof ShaderOptionScreen) {
/* 106 */       ShaderOptionScreen shaderoptionscreen = (ShaderOptionScreen)so;
/* 107 */       return String.valueOf(s) + "...";
/*     */     } 
/* 109 */     FontRenderer fontrenderer = (Config.getMinecraft()).fontRendererObj;
/*     */     
/* 111 */     for (int i = fontrenderer.getStringWidth(": " + Lang.getOff()) + 5; fontrenderer.getStringWidth(s) + i >= btnWidth && s.length() > 0; s = s.substring(0, s.length() - 1));
/*     */ 
/*     */ 
/*     */     
/* 115 */     String s1 = so.isChanged() ? so.getValueColor(so.getValue()) : "";
/* 116 */     String s2 = so.getValueText(so.getValue());
/* 117 */     return String.valueOf(s) + ": " + s1 + s2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton guibutton) {
/* 125 */     if (guibutton.enabled) {
/* 126 */       if (guibutton.id < 200 && guibutton instanceof GuiButtonShaderOption) {
/* 127 */         GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)guibutton;
/* 128 */         ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/*     */         
/* 130 */         if (shaderoption instanceof ShaderOptionScreen) {
/* 131 */           String s = shaderoption.getName();
/* 132 */           GuiShaderOptions guishaderoptions = new GuiShaderOptions((GuiScreen)this, this.settings, s);
/* 133 */           this.mc.displayGuiScreen((GuiScreen)guishaderoptions);
/*     */           
/*     */           return;
/*     */         } 
/* 137 */         if (isShiftKeyDown()) {
/* 138 */           shaderoption.resetValue();
/* 139 */         } else if (guibuttonshaderoption.isSwitchable()) {
/* 140 */           shaderoption.nextValue();
/*     */         } 
/*     */         
/* 143 */         updateAllButtons();
/* 144 */         this.changed = true;
/*     */       } 
/*     */       
/* 147 */       if (guibutton.id == 201) {
/* 148 */         ShaderOption[] ashaderoption = Shaders.getChangedOptions(Shaders.getShaderPackOptions());
/*     */         
/* 150 */         for (int i = 0; i < ashaderoption.length; i++) {
/* 151 */           ShaderOption shaderoption1 = ashaderoption[i];
/* 152 */           shaderoption1.resetValue();
/* 153 */           this.changed = true;
/*     */         } 
/*     */         
/* 156 */         updateAllButtons();
/*     */       } 
/*     */       
/* 159 */       if (guibutton.id == 200) {
/* 160 */         if (this.changed) {
/* 161 */           Shaders.saveShaderPackOptions();
/* 162 */           this.changed = false;
/* 163 */           Shaders.uninit();
/*     */         } 
/*     */         
/* 166 */         this.mc.displayGuiScreen(this.prevScreen);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void actionPerformedRightClick(GuiButton btn) {
/* 172 */     if (btn instanceof GuiButtonShaderOption) {
/* 173 */       GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)btn;
/* 174 */       ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/*     */       
/* 176 */       if (isShiftKeyDown()) {
/* 177 */         shaderoption.resetValue();
/* 178 */       } else if (guibuttonshaderoption.isSwitchable()) {
/* 179 */         shaderoption.prevValue();
/*     */       } 
/*     */       
/* 182 */       updateAllButtons();
/* 183 */       this.changed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 191 */     super.onGuiClosed();
/*     */     
/* 193 */     if (this.changed) {
/* 194 */       Shaders.saveShaderPackOptions();
/* 195 */       this.changed = false;
/* 196 */       Shaders.uninit();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateAllButtons() {
/* 201 */     for (GuiButton guibutton : this.buttonList) {
/* 202 */       if (guibutton instanceof GuiButtonShaderOption) {
/* 203 */         GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)guibutton;
/* 204 */         ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/*     */         
/* 206 */         if (shaderoption instanceof ShaderOptionProfile) {
/* 207 */           ShaderOptionProfile shaderoptionprofile = (ShaderOptionProfile)shaderoption;
/* 208 */           shaderoptionprofile.updateProfile();
/*     */         } 
/*     */         
/* 211 */         guibuttonshaderoption.displayString = getButtonText(shaderoption, guibuttonshaderoption.getButtonWidth());
/* 212 */         guibuttonshaderoption.valueChanged();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int x, int y, float f) {
/* 221 */     drawDefaultBackground();
/*     */     
/* 223 */     if (this.screenText != null) {
/* 224 */       drawCenteredString(this.fontRendererObj, this.screenText, width / 2, 15, 16777215);
/*     */     } else {
/* 226 */       drawCenteredString(this.fontRendererObj, this.title, width / 2, 15, 16777215);
/*     */     } 
/*     */     
/* 229 */     super.drawScreen(x, y, f);
/* 230 */     this.tooltipManager.drawTooltips(x, y, this.buttonList);
/*     */   }
/*     */ }


/* Location:              C:\User\\user\Desktop\Butteracking Client v4\Butteracking Client v4.jar!\net\optifine\shaders\gui\GuiShaderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */